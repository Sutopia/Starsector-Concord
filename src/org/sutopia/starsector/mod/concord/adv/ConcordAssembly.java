package org.sutopia.starsector.mod.concord.adv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import org.sutopia.starsector.mod.concord.Codex;

import com.fs.starfarer.api.BaseModPlugin;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.combat.ShipHullSpecAPI;
import com.fs.starfarer.api.combat.ShipVariantAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.loading.HullModSpecAPI;

public final class ConcordAssembly extends BaseModPlugin {
    
    @Override
    public void onApplicationLoad() throws Exception {
        for (ShipHullSpecAPI spec : Global.getSettings().getAllShipHullSpecs()) {
            if (!spec.isBuiltInMod("concord_captain")) {
                spec.addBuiltInMod("concord_captain");
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
                //original.setId(Codex.ID_PREFIX_CONCORD_DOPPELGANGER + id);
                //original.setHidden(true);
                original.addTag(Codex.TAG_CONCORD_CUSTODY);
                if (original.isHidden()) {
                    original.addTag(Codex.TAG_CONCORD_HIDDEN);
                }
                spec.setId(id);
                //Global.getSettings().putSpec(original.getClass(), id, spec);
                //Global.getSettings().putSpec(original.getClass(), Codex.ID_PREFIX_CONCORD_DOPPELGANGER + id, original);
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
    }
    
    @Override
    public void onGameLoad(boolean newGame) {
        CampaignFleetAPI fleet = Global.getSector().getPlayerFleet();
        if (fleet == null) {
            return;
        }
        for (FleetMemberAPI member : fleet.getFleetData().getMembersListCopy()) {
            ShipVariantAPI variant = member.getVariant();
            if (!variant.hasHullMod("concord_captain")) {
                variant.addPermaMod("concord_captain", false);
            }
        }
        Global.getSector().addTransientScript(new ConcordCommander());
    }
}
