package org.sutopia.starsector.mod.concord.adv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.sutopia.starsector.mod.concord.Codex;
import org.sutopia.starsector.mod.concord.api.OnInstallHullmodEffect;
import org.sutopia.starsector.mod.concord.api.OnRemoveHullmodEffect;
import org.sutopia.starsector.mod.concord.api.SelectiveTransientHullmod;
import org.sutopia.starsector.mod.concord.api.TrackedHullmodEffect;
import org.sutopia.starsector.mod.concord.api.GlobalTransientHullmod;
import org.sutopia.starsector.mod.concord.blackops.Monitor;
import org.sutopia.starsector.mod.concord.dynamic.ConcordUtil;
import org.sutopia.starsector.mod.concord.dynamic.MutableShipHullSpecAPI;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.FleetDataAPI;
import com.fs.starfarer.api.campaign.LocationAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.SubmarketAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipHullSpecAPI;
import com.fs.starfarer.api.combat.ShipSystemSpecAPI;
import com.fs.starfarer.api.combat.ShipVariantAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.loading.HullModSpecAPI;

public final class ConcordAssembly extends BaseModPlugin {

    @Override
    public void onApplicationLoad() throws Exception {
        
        if (Global.getSettings().getBoolean("concord_debug")) {
            Global.getSettings().getHullModSpec("concord_captain").setHiddenEverywhere(false);
        }

        for (final HullModSpecAPI spec : Global.getSettings().getAllHullModSpecs()) {
            try {
                spec.getEffect();
            } catch (Exception e) {
                continue;
            }
            if (spec.getEffect() instanceof GlobalTransientHullmod) {
                HullModSpecAPI hullmod = Global.getSettings().getHullModSpec(spec.getId());
                if (hullmod == null) return;
                for (ShipHullSpecAPI hullSpec : Global.getSettings().getAllShipHullSpecs()) {
                    if (hullSpec.isDHull()) {
                        continue;
                    }
                    if (hullmod.getEffect() instanceof SelectiveTransientHullmod) {
                        if (!((SelectiveTransientHullmod)hullmod.getEffect()).shouldApplyToSpec(hullSpec)) {
                            continue;
                        }
                    }
                    if (!hullSpec.isBuiltInMod(spec.getId())) {
                        hullSpec.addBuiltInMod(spec.getId());
                    }
                }

                for (String id : Global.getSettings().getAllVariantIds()) {
                    ShipVariantAPI variant = Global.getSettings().getVariant(id);
                    if (hullmod.getEffect() instanceof SelectiveTransientHullmod) {
                        if (!((SelectiveTransientHullmod)hullmod.getEffect()).shouldApplyToVariant(variant)) {
                            continue;
                        }
                    }
                    if (!variant.hasHullMod(spec.getId())) {
                        variant.addPermaMod(spec.getId(), false);
                    }
                }
            }
            // register tracked hullmods
            if (spec.getEffect() instanceof TrackedHullmodEffect) {
                ConcordCaptain.trackedHullmods.put(spec.getId(), (TrackedHullmodEffect) spec.getEffect());
            } else if (spec.getEffect() instanceof OnInstallHullmodEffect) {
                ConcordCaptain.trackedHullmods.put(spec.getId(), new TrackedHullmodEffect() {
                    @Override
                    public void onInstall(ShipAPI ship) {
                        ((OnInstallHullmodEffect)spec.getEffect()).onInstall(ship);
                    }
                    @Override
                    public void onRemove(ShipAPI ship) {
                    }
                });
            } else if (spec.getEffect() instanceof OnRemoveHullmodEffect) {
                ConcordCaptain.trackedHullmods.put(spec.getId(), new TrackedHullmodEffect() {
                    @Override
                    public void onInstall(ShipAPI ship) {
                    }
                    @Override
                    public void onRemove(ShipAPI ship) {
                        ((OnRemoveHullmodEffect)spec.getEffect()).onRemove(ship);
                    }
                });
            }
        }
        
        // populate doppelganger
        for (HullModSpecAPI spec : Global.getSettings().getAllHullModSpecs()) {
            if (spec.hasTag(Codex.TAG_CONCORD_OPT_IN) && spec.getId() != null
                    && spec.getId().startsWith(Codex.ID_PREFIX_CONCORD_DOPPELGANGER)) {
                HullModSpecAPI original = Global.getSettings().getHullModSpec(
                        spec.getId().substring(Codex.ID_PREFIX_CONCORD_DOPPELGANGER.length()));
                if (original == null) {
                    continue;
                }
                Global.getSettings().getAllSpecs(spec.getClass()).remove(spec);
                ConcordUtil.copySpec(original, spec);
                // swap
                original.setHidden(true);
                String id = original.getId();
                DataEnactDomain.sanctuary.put(id, original.getEffect());
                spec.setId(id);
                Global.getSettings().getAllSpecs(original.getClass()).remove(original);
                Global.getSettings().putSpec(original.getClass(), id, spec);
            } else if (spec.hasTag(Codex.TAG_CONCORD_OPT_IN) 
                    && spec.hasTag(Codex.TAG_CONCORD_IMPLICIT)
                    && !spec.hasTag(Codex.TAG_CONCORD_SHELLED)
                    && spec.getId() != null) {
                HullModSpecAPI shellSpec = Monitor.CreateModSpec(DataEnactDomain.class.getName());
                ConcordUtil.copySpec(spec, shellSpec);
                shellSpec.addTag(Codex.TAG_CONCORD_SHELLED);
                spec.setHidden(true);
                String id = spec.getId();
                DataEnactDomain.sanctuary.put(id, spec.getEffect());
                Global.getSettings().getAllSpecs(spec.getClass()).remove(spec);
                Global.getSettings().putSpec(spec.getClass(), id, shellSpec);
            }
        }

        HashMap<String, String> leaderTopicMap = new HashMap<>();
        // populate inner circle gang leaders
        for (HullModSpecAPI spec : Global.getSettings().getAllHullModSpecs()) {
            if (spec.hasTag(Codex.TAG_OWN_TOPIC)) {
                spec.addTag(Codex.VOLATILE_EXCLUSIVE_PREFIX + spec.getId());
            }
            ArrayList<String> topicTags = new ArrayList<>();
            for (String tag : spec.getTags()) {
                if (tag.startsWith(Codex.VOLATILE_EXCLUSIVE_PREFIX)) {
                    String topic = tag.substring(Codex.VOLATILE_EXCLUSIVE_PREFIX.length());
                    // spec.addTag(Codex.TOPIC_EXCLUSIVE_PREFIX + topic);
                    topicTags.add(Codex.TOPIC_EXCLUSIVE_PREFIX + topic);
                    leaderTopicMap.put(spec.getId(), topic);

                    if (!DataEnactDomain.innerCircle.containsKey(topic)) {
                        DataEnactDomain.innerCircle.put(topic, new HashMap<String, HashSet<String>>());
                    }

                    HashMap<String, HashSet<String>> topicCluster = DataEnactDomain.innerCircle.get(topic);
                    topicCluster.put(spec.getId(), new HashSet<String>());
                }
            }
            for (String tag : topicTags) {
                spec.addTag(tag);
            }
        }

        // populate inner circle followers
        for (HullModSpecAPI spec : Global.getSettings().getAllHullModSpecs()) {
            ArrayList<String> topicTags = new ArrayList<>();
            for (String tag : spec.getTags()) {
                if (tag.startsWith(Codex.DEPENDENT_EXCLUSIVE_PREFIX)) {
                    String leader = tag.substring(Codex.DEPENDENT_EXCLUSIVE_PREFIX.length());
                    if (leaderTopicMap.containsKey(leader)) {
                        // spec.addTag(Codex.TOPIC_EXCLUSIVE_PREFIX + leaderTopicMap.get(leader));
                        topicTags.add(Codex.TOPIC_EXCLUSIVE_PREFIX + leaderTopicMap.get(leader));
                        HashMap<String, HashSet<String>> topicCluster = DataEnactDomain.innerCircle.get(leaderTopicMap
                                .get(leader));
                        HashSet<String> gang = topicCluster.get(leader);
                        gang.add(spec.getId());
                    }
                }
            }
            for (String tag : topicTags) {
                spec.addTag(tag);
            }
        }

        // populate neutral
        for (HullModSpecAPI spec : Global.getSettings().getAllHullModSpecs()) {
            for (String tag : spec.getTags()) {
                if (tag.startsWith(Codex.NEUTRAL_EXCLUSIVE_PREFIX)) {
                    String target = tag.substring(Codex.NEUTRAL_EXCLUSIVE_PREFIX.length());
                    HashSet<String> neutList = DataEnactDomain.neutralBlackList.get(target);
                    if (neutList == null) {
                        neutList = new HashSet<String>();
                        DataEnactDomain.neutralBlackList.put(target, neutList);
                    }
                    neutList.add(spec.getId());
                }
            }
        }

        // override cloak script
        final ShipSystemSpecAPI concordPhase = Global.getSettings().getShipSystemSpec("concord_mod_phasecloak");
        ShipSystemSpecAPI vanillaPhase = Global.getSettings().getShipSystemSpec("phasecloak");
        for (ShipSystemSpecAPI spec : Global.getSettings().getAllShipSystemSpecs()) {
            if (vanillaPhase.getStatsScriptClassName().equals(spec.getStatsScriptClassName())) {
                ShipSystemSpecAPI concordSpec = Monitor.CreateSysSpec(spec, concordPhase.getStatsScriptClassName());
                ConcordUtil.copySpec(spec, concordSpec);
                Global.getSettings().getAllSpecs(spec.getClass()).remove(spec);
                Global.getSettings().putSpec(spec.getClass(), spec.getId(), concordSpec);
            }
        }
        Global.getSettings().getAllSpecs(concordPhase.getClass()).remove(concordPhase);
        // Shell for S-Mod display correction
        // Global.setSettings(new ConcordSettings(Global.getSettings()));
        DataEnactDomain.resetCache();
    }

    @Override
    public void onGameLoad(boolean newGame) {
        for (HullModSpecAPI spec : Global.getSettings().getAllHullModSpecs()) {
            if (spec.getEffect() instanceof GlobalTransientHullmod) {
                addHullmodEverywhere(spec.getId());
            }
        }
        Global.getSector().addTransientListener(new ConcordCommander());
    }
    
    @Override
    public void afterGameSave() {
        for (HullModSpecAPI spec : Global.getSettings().getAllHullModSpecs()) {
            if (spec.getEffect() instanceof GlobalTransientHullmod) {
                addHullmodEverywhere(spec.getId());
            }
        }
    }
    
    @Override
    public void beforeGameSave() {
        boolean hasPurge = true;
        while (hasPurge) {
            hasPurge = false;
            for (HullModSpecAPI spec : Global.getSettings().getAllHullModSpecs()) {
                if (spec.getEffect() instanceof GlobalTransientHullmod) {
                    hasPurge |= removeHullmodEverywhere(spec.getId());
                }
            }
        }
    }
    
    public static void addHullmodEverywhere(String hullmodId) {
        HullModSpecAPI hullmod = Global.getSettings().getHullModSpec(hullmodId);
        if (hullmod == null) return;
        for (ShipHullSpecAPI hullSpec : Global.getSettings().getAllShipHullSpecs()) {
            if (hullSpec.isDHull()) {
                continue;
            }
            if (hullmod.getEffect() instanceof SelectiveTransientHullmod) {
                if (!((SelectiveTransientHullmod)hullmod.getEffect()).shouldApplyToSpec(hullSpec)) {
                    continue;
                }
            }
            if (!hullSpec.isBuiltInMod(hullmodId)) {
                hullSpec.addBuiltInMod(hullmodId);
            }
        }

        for (String id : Global.getSettings().getAllVariantIds()) {
            ShipVariantAPI variant = Global.getSettings().getVariant(id);
            if (hullmod.getEffect() instanceof SelectiveTransientHullmod) {
                if (!((SelectiveTransientHullmod)hullmod.getEffect()).shouldApplyToVariant(variant)) {
                    continue;
                }
            }
            if (!variant.hasHullMod(hullmodId)) {
                variant.addPermaMod(hullmodId, false);
            }
        }
        
        for (FactionAPI faction : Global.getSector().getAllFactions()) {
            Object obj = Global.getSector().getPersistentData()
                    .get(Codex.CONCORD_FACTION_HULLMOD_KNOWN_KEY + faction.getId() + hullmodId);
            if (obj != null) {
                faction.addKnownHullMod(hullmodId);
            }
        }
        FactionAPI playerFaction = Global.getSector().getPlayerFaction();
        if (playerFaction != null) {
            Object obj = Global.getSector().getPersistentData()
                    .get(Codex.CONCORD_FACTION_HULLMOD_KNOWN_KEY + playerFaction.getId() + hullmodId);
            if (obj != null) {
                playerFaction.addKnownHullMod(hullmodId);
            }
        }
        
        for (LocationAPI loc : Global.getSector().getAllLocations()) {
            for (CampaignFleetAPI fleet : loc.getFleets()) {
                for (FleetMemberAPI member : fleet.getMembersWithFightersCopy()) {
                    infest(member.getVariant(), hullmod);
                }
                CargoAPI cargo = fleet.getCargo();
                if (cargo == null) continue;
                FleetDataAPI fleetData = cargo.getMothballedShips();
                if (fleetData != null) {
                    for (FleetMemberAPI member : fleetData.getMembersListCopy()) {
                        infest(member.getVariant(), hullmod);
                    }
                }
                fleetData = cargo.getFleetData();
                if (fleetData == null) continue;
                for (FleetMemberAPI member : fleetData.getMembersListCopy()) {
                    infest(member.getVariant(), hullmod);
                }
            }
            for (MarketAPI market : Global.getSector().getEconomy().getMarkets(loc)) {
                for (SubmarketAPI sub : market.getSubmarketsCopy()) {
                    CargoAPI cargo = sub.getCargo();
                    if (cargo == null) continue;
                    FleetDataAPI fleetData = cargo.getFleetData();
                    if (fleetData != null) {
                        for (FleetMemberAPI member : fleetData.getMembersListCopy()) {
                            ShipVariantAPI variant = member.getVariant();
                            infest(variant, hullmod);
                        }
                    }
                    fleetData = cargo.getMothballedShips();
                    if (fleetData == null) continue;
                    for (FleetMemberAPI member : fleetData.getMembersListCopy()) {
                        ShipVariantAPI variant = member.getVariant();
                        infest(variant, hullmod);
                    }
                }
            }
        }
        
        for (FleetMemberAPI member : Global.getSector().getPlayerFleet().getFleetData().getMembersListCopy()) {
            ShipVariantAPI variant = member.getVariant();
            infest(variant, hullmod);
        }
    }
    
    private static void infest(ShipVariantAPI variant, HullModSpecAPI hullmod) {
        if (hullmod.getEffect() instanceof SelectiveTransientHullmod) {
            if (!((SelectiveTransientHullmod)hullmod.getEffect()).shouldApplyToVariant(variant)) {
                return;
            }
        }
        if (!variant.hasHullMod(hullmod.getId())) {
            variant.addPermaMod(hullmod.getId(), false);
        }
    }
    
    public static boolean removeHullmodEverywhere(String hullmodId) {
        HullModSpecAPI hullmod = Global.getSettings().getHullModSpec(hullmodId);
        if (hullmod == null) return false;
        boolean hasPurge = false;
        for (ShipHullSpecAPI hullSpec : Global.getSettings().getAllShipHullSpecs()) {
            if (hullSpec.isBuiltInMod(hullmodId)) {
                hullSpec.getBuiltInMods().remove(hullmodId);
                hasPurge = true;
            }
        }

        for (String id : Global.getSettings().getAllVariantIds()) {
            ShipVariantAPI variant = Global.getSettings().getVariant(id);
            if (variant.hasHullMod(hullmodId)) {
                hasPurge |= purge(variant, hullmodId);
            }
        }
        
        for (FactionAPI faction : Global.getSector().getAllFactions()) {
            if (faction.knowsHullMod(hullmodId)) {
                Global.getSector().getPersistentData()
                        .put(Codex.CONCORD_FACTION_HULLMOD_KNOWN_KEY + faction.getId() + hullmodId, "1");
                faction.removeKnownHullMod(hullmodId);
            }
        }
        FactionAPI playerFaction = Global.getSector().getPlayerFaction();
        if (playerFaction != null) {
            if (playerFaction.knowsHullMod(hullmodId)) {
                Global.getSector().getPersistentData()
                        .put(Codex.CONCORD_FACTION_HULLMOD_KNOWN_KEY + playerFaction.getId() + hullmodId, "1");
                playerFaction.removeKnownHullMod(hullmodId);
            }
        }
        
        for (LocationAPI loc : Global.getSector().getAllLocations()) {
            for (CampaignFleetAPI fleet : loc.getFleets()) {
                for (FleetMemberAPI member : fleet.getMembersWithFightersCopy()) {
                    hasPurge |= purge(member.getVariant(), hullmodId);
                }
                CargoAPI cargo = fleet.getCargo();
                if (cargo == null) continue;
                FleetDataAPI fleetData = cargo.getMothballedShips();
                if (fleetData != null) {
                    for (FleetMemberAPI member : fleetData.getMembersListCopy()) {
                        hasPurge |= purge(member.getVariant(), hullmodId);
                    }
                }
                fleetData = cargo.getFleetData();
                if (fleetData == null) continue;
                for (FleetMemberAPI member : fleetData.getMembersListCopy()) {
                    hasPurge |= purge(member.getVariant(), hullmodId);
                }
            }
            
            for (MarketAPI market : Global.getSector().getEconomy().getMarkets(loc)) {
                for (SubmarketAPI sub : market.getSubmarketsCopy()) {
                    CargoAPI cargo = sub.getCargo();
                    if (cargo == null) continue;
                    FleetDataAPI fleetData = cargo.getMothballedShips();
                    if (fleetData != null) {
                        for (FleetMemberAPI member : fleetData.getMembersListCopy()) {
                            hasPurge |= purge(member.getVariant(), hullmodId);
                        }
                    }
                    fleetData = cargo.getFleetData();
                    if (fleetData == null) continue;
                    for (FleetMemberAPI member : fleetData.getMembersListCopy()) {
                        hasPurge |= purge(member.getVariant(), hullmodId);
                    }
                }
            }
        }
        CampaignFleetAPI player = Global.getSector().getPlayerFleet();
        for (FleetMemberAPI member : player.getMembersWithFightersCopy()) {
            hasPurge |= purge(member.getVariant(), hullmodId);
        }
        return hasPurge;
    }
    
    private static boolean purge(ShipVariantAPI variant, String hullmodId) {
        if (variant.hasHullMod(hullmodId)) {
            variant.removeMod(hullmodId);
            variant.removePermaMod(hullmodId);
            variant.getHullSpec().getBuiltInMods().remove(hullmodId);
            MutableShipHullSpecAPI hullSpec = ConcordDynamicInstanceAssembly.getHullSpecCopy(variant.getHullSpec());
            hullSpec.getBuiltInMods().remove(hullmodId);
            variant.setHullSpecAPI(hullSpec.getInstance());
            return true;
        }
        return false;
    }
}
