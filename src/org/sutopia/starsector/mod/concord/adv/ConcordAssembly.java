package org.sutopia.starsector.mod.concord.adv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.sutopia.starsector.mod.concord.Codex;
import org.sutopia.starsector.mod.concord.api.TrackedHullmodEffect;
import org.sutopia.starsector.mod.concord.dynamic.MutableShipSystemSpecAPI;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.FactionAPI;
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
        
        // create doppelganger
        for (HullModSpecAPI spec : Global.getSettings().getAllHullModSpecs()) {
            if (spec.hasTag(Codex.TAG_CONCORD_OPT_IN) 
                    && spec.getId() != null 
                    && spec.getId().startsWith(Codex.ID_PREFIX_CONCORD_DOPPELGANGER)) {
                HullModSpecAPI original = Global.getSettings()
                        .getHullModSpec(spec.getId().substring(Codex.ID_PREFIX_CONCORD_DOPPELGANGER.length()));
                if (original == null) {
                    continue;
                }
                
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
                spec.setHidden(true);
                spec.setHiddenEverywhere(original.isHiddenEverywhere());
                spec.setManufacturer(original.getManufacturer());
                spec.setRarity(original.getRarity());
                spec.setSpriteName(original.getSpriteName());
                spec.setTier(original.getTier());
                //swap
                String id = original.getId();
                DataEnactDomain.sanctuary.put(id, original.getEffect());
                spec.setId(id);
                original.addTag(Codex.TAG_CONCORD_CUSTODY);
                if (original.isHidden()) {
                    original.addTag(Codex.TAG_CONCORD_HIDDEN);
                } else {
                    ConcordCaptain.doppelgangers.add(spec);
                    ConcordCaptain.specs.add(Global.getSettings().getHullModSpec(spec.getId()));
                    ConcordSettings.needMock.add(spec.getId());
                }
            }
        }
        
        HashMap<String, String> leaderTopicMap = new HashMap<>();
        // populate inner circle gang leaders
        for (HullModSpecAPI spec : Global.getSettings().getAllHullModSpecs()) {
            if (spec.hasTag(Codex.TAG_CONCORD_CUSTODY)) {
                continue;
            }
            if (spec.hasTag(Codex.TAG_OWN_TOPIC)) {
                spec.addTag(Codex.VOLATILE_EXCLUSIVE_PREFIX + spec.getId());
            }
            
            ArrayList<String> topicTags = new ArrayList<>();
            for (String tag : spec.getTags()) {
                if (tag.startsWith(Codex.VOLATILE_EXCLUSIVE_PREFIX)) {
                    String topic = tag.substring(Codex.VOLATILE_EXCLUSIVE_PREFIX.length());
                    //spec.addTag(Codex.TOPIC_EXCLUSIVE_PREFIX + topic);
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
            if (spec.hasTag(Codex.TAG_CONCORD_CUSTODY)) {
                continue;
            }
            
            ArrayList<String> topicTags = new ArrayList<>();
            for (String tag : spec.getTags()) {
                if (tag.startsWith(Codex.DEPENDENT_EXCLUSIVE_PREFIX)) {
                    String leader = tag.substring(Codex.DEPENDENT_EXCLUSIVE_PREFIX.length());
                    if (leaderTopicMap.containsKey(leader)) {
                        //spec.addTag(Codex.TOPIC_EXCLUSIVE_PREFIX + leaderTopicMap.get(leader));
                        topicTags.add(Codex.TOPIC_EXCLUSIVE_PREFIX + leaderTopicMap.get(leader));
                        HashMap<String, HashSet<String>> topicCluster = DataEnactDomain.innerCircle.get(leaderTopicMap.get(leader));
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
        
        // cache need mock
        for (HullModSpecAPI spec: Global.getSettings().getAllHullModSpecs()) {
            if (spec.hasTag(Codex.TAG_CONCORD_OPT_IN) && spec.getEffect() instanceof DataEnactDomain) {
                
                if (!((DataEnactDomain) spec.getEffect()).getBlackList().isEmpty()) {
                    if (!Global.getSettings().getHullModSpec(spec.getId()).isHidden()) {
                        
                    }
                }
            }
        }
        
        // override cloak script
        final ShipSystemSpecAPI concordPhase = Global.getSettings().getShipSystemSpec("concord_mod_phasecloak");
        ShipSystemSpecAPI vanillaPhase = Global.getSettings().getShipSystemSpec("phasecloak");
        HashSet<String> phaseSystems = new HashSet<>();
        for (ShipSystemSpecAPI spec : Global.getSettings().getAllShipSystemSpecs()) {
            if (vanillaPhase.getStatsScriptClassName().equals(spec.getStatsScriptClassName())) {
                MutableShipSystemSpecAPI concordSpec = new MutableShipSystemSpecAPI(spec) {
                    private String id;
                    @Override
                    public String getId() {
                        return id;
                    }
                    @Override
                    public void setId(String id) {
                        this.id = id;
                    }
                    @Override
                    public String getStatsScriptClassName() {
                        return concordPhase.getStatsScriptClassName();
                    }
                    @Override
                    public ShipSystemStatsScript getStatsScript() {
                        return concordPhase.getStatsScript();
                    }
                };
                phaseSystems.add(spec.getId());
                if (!vanillaPhase.getId().equals(spec.getId())) {
                    Global.getSettings().putSpec(spec.getClass()
                            , Codex.CONCORD_PHASE_SYSTEM_GEN_PREFIX + spec.getId()
                            , concordSpec);
                }
            }
        }
        
        for (ShipHullSpecAPI spec : Global.getSettings().getAllShipHullSpecs()) {
            if (phaseSystems.contains(spec.getShipSystemId())) {
                spec.setShipSystemId(Codex.CONCORD_PHASE_SYSTEM_GEN_PREFIX + spec.getShipSystemId());
            }
            if (phaseSystems.contains(spec.getShipDefenseId())) {
                spec.setShipDefenseId(Codex.CONCORD_PHASE_SYSTEM_GEN_PREFIX + spec.getShipDefenseId());
            }
        }
        
        // Shell for S-Mod display correction
        Global.setSettings(new ConcordSettings(Global.getSettings()));
        DataEnactDomain.resetCache();
        //throw new RuntimeException(ConcordCaptain.specs);
    }
    
    @Override
    public void onGameLoad(boolean newGame) {
        
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
        for (HullModSpecAPI spec : Global.getSettings().getAllHullModSpecs()) {
            if (spec.getEffect() instanceof TrackedHullmodEffect) {
                ConcordCaptain.trackedHullmods.put(spec.getId(), spec.getEffect());
            }
        }
        
        mirrorSync();
        
        //Global.getSector().addTransientScript(new ConcordCommander());
    }
    
    @Override
    public void afterGameSave() {
        mirrorSync();
    }
    @Override
    public void beforeGameSave() {
        FactionAPI player = Global.getSector().getPlayerFaction();
        for (HullModSpecAPI spec : ConcordCaptain.specs) {
            if (player.knowsHullMod(spec.getId())) {
                spec.setHidden(false);
            }
        }
    }
    
    private static void mirrorSync() {
        FactionAPI player = Global.getSector().getPlayerFaction();
        
        for (HullModSpecAPI spec : Global.getSettings().getAllHullModSpecs()) {
            String doppelganger = Codex.ID_PREFIX_CONCORD_DOPPELGANGER + spec.getId();
            HullModSpecAPI doppelSpec = Global.getSettings().getHullModSpec(doppelganger);
            if (doppelSpec == null) {
                continue;
            }
            if (player.knowsHullMod(spec.getId()) 
                    && !player.knowsHullMod(doppelganger)) {
                player.addKnownHullMod(doppelganger);
            }
            if (player.knowsHullMod(doppelganger)
                    && !player.knowsHullMod(spec.getId())) {
                player.addKnownHullMod(spec.getId());
            }
            if (ConcordCaptain.specs.contains(spec)) {
                spec.setHidden(false);
                doppelSpec.setHidden(true);
            }
        }
    }
}
