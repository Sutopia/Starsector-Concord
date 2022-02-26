package org.sutopia.starsector.mod.concord.adv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.sutopia.starsector.mod.concord.Codex;
import org.sutopia.starsector.mod.concord.api.OnInstallHullmodEffect;
import org.sutopia.starsector.mod.concord.api.OnRemoveHullmodEffect;
import org.sutopia.starsector.mod.concord.api.TrackedHullmodEffect;
import org.sutopia.starsector.mod.concord.dynamic.MutableShipSystemSpecAPI;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipHullSpecAPI;
import com.fs.starfarer.api.combat.ShipSystemSpecAPI;
import com.fs.starfarer.api.combat.ShipVariantAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.loading.HullModSpecAPI;
import com.fs.starfarer.api.plugins.ShipSystemStatsScript;

public final class ConcordAssembly extends BaseModPlugin {

    @Override
    public void onApplicationLoad() throws Exception {

        for (ShipHullSpecAPI spec : Global.getSettings().getAllShipHullSpecs()) {
            if (!spec.isBuiltInMod(Codex.CONCORD_CAPTAIN_HULLMOD_ID)) {
                spec.addBuiltInMod(Codex.CONCORD_CAPTAIN_HULLMOD_ID);
            }
        }

        for (String id : Global.getSettings().getAllVariantIds()) {
            ShipVariantAPI variant = Global.getSettings().getVariant(id);
            if (!variant.hasHullMod(Codex.CONCORD_CAPTAIN_HULLMOD_ID)) {
                variant.addPermaMod(Codex.CONCORD_CAPTAIN_HULLMOD_ID, false);
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

                // copy og spec to doppelganger
                for (String tag : original.getTags()) {
                    if (!spec.hasTag(tag)) {
                        spec.addTag(tag);
                    }
                }

                for (String tag : original.getUITags()) {
                    if (!spec.hasUITag(tag)) {
                        spec.addUITag(tag);
                    }
                }

                spec.setAlwaysUnlocked(original.isAlwaysUnlocked());
                spec.setBaseValue(original.getBaseValue());
                spec.setCapitalCost(original.getCapitalCost());
                spec.setCruiserCost(original.getCruiserCost());
                spec.setDescriptionFormat(original.getDescriptionFormat());
                spec.setDestroyerCost(original.getDestroyerCost());
                spec.setDisplayName(original.getDisplayName());
                spec.setFrigateCost(original.getFrigateCost());
                spec.setHidden(original.isHidden());
                spec.setHiddenEverywhere(original.isHiddenEverywhere());
                spec.setManufacturer(original.getManufacturer());
                spec.setRarity(original.getRarity());
                spec.setSpriteName(original.getSpriteName());
                spec.setTier(original.getTier());
                
                // swap
                original.setHidden(true);
                String id = original.getId();
                DataEnactDomain.sanctuary.put(id, original.getEffect());
                spec.setId(id);
                Global.getSettings().getAllSpecs(original.getClass()).remove(original);
                Global.getSettings().putSpec(original.getClass(), id, spec);
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
                MutableShipSystemSpecAPI concordSpec = new MutableShipSystemSpecAPI(spec) {
                    @Override
                    public ShipSystemStatsScript getStatsScript() {
                        return concordPhase.getStatsScript();
                    }
                };
                concordSpec.setId(spec.getId());
                Global.getSettings().getAllSpecs(spec.getClass()).remove(spec);
                Global.getSettings().putSpec(spec.getClass(), spec.getId(), concordSpec);
            }
        }

        // Shell for S-Mod display correction
        // Global.setSettings(new ConcordSettings(Global.getSettings()));
        DataEnactDomain.resetCache();
    }

    @Override
    public void onGameLoad(boolean newGame) {
        // TODO: make apply global hullmod interface
        CampaignFleetAPI fleet = Global.getSector().getPlayerFleet();
        if (fleet != null) {
            for (FleetMemberAPI member : fleet.getFleetData().getMembersListCopy()) {
                ShipVariantAPI variant = member.getVariant();
                if (!variant.hasHullMod(Codex.CONCORD_CAPTAIN_HULLMOD_ID)) {
                    variant.addPermaMod(Codex.CONCORD_CAPTAIN_HULLMOD_ID, false);
                }
            }
        }

        // register tracked hullmods
        for (final HullModSpecAPI spec : Global.getSettings().getAllHullModSpecs()) {
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

        FactionAPI player = Global.getSector().getPlayerFaction();
        for (HullModSpecAPI spec : Global.getSettings().getAllHullModSpecs()) {
            String doppelganger = Codex.ID_PREFIX_CONCORD_DOPPELGANGER + spec.getId();
            if (player.knowsHullMod(doppelganger)) {
                player.removeKnownHullMod(doppelganger);
                if (!player.knowsHullMod(spec.getId())) {
                    player.addKnownHullMod(spec.getId());
                }
            }
        }
    }
}
