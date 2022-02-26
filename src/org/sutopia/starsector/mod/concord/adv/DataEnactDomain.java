package org.sutopia.starsector.mod.concord.adv;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.sutopia.starsector.mod.concord.Codex;
import org.sutopia.starsector.mod.concord.phase.ConcordModStats;
import org.sutopia.starsector.mod.concord.phase.PhaseCloakStatsForMod;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.CampaignUIAPI.CoreUITradeMode;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.combat.HullModEffect;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.impl.combat.PhaseCloakStats;
import com.fs.starfarer.api.loading.HullModSpecAPI;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;

public final class DataEnactDomain implements HullModEffect {
    // <id, effect>
    public static final HashMap<String, HullModEffect> sanctuary = new HashMap<>();
    // <topic, <leader, follower>>
    public static final HashMap<String, HashMap<String, HashSet<String>>> innerCircle = new HashMap<>();
    public static final HashMap<String, HashSet<String>> neutralBlackList = new HashMap<>();
    
    private static final HashMap<String, ArrayList<String>> cachedBlackList = new HashMap<>();
    
    private String getId() {
        return spec.getId();
    }
    
    public static void resetCache() {
        cachedBlackList.clear();
    }
    
    public List<String> getBlackList() {
        ArrayList<String> blackList = cachedBlackList.get(getId());
        if (blackList != null) {
            return blackList;
        }
        blackList = new ArrayList<>();
        Set<String> allTags = spec.getTags();
        
        for (String tag: allTags) {
            if (tag.startsWith(Codex.TOPIC_EXCLUSIVE_PREFIX)) {
                String topic = tag.substring(Codex.TOPIC_EXCLUSIVE_PREFIX.length());
                HashMap<String, HashSet<String>> topicHeads = innerCircle.get(topic);
                if (topicHeads == null) {
                    continue;
                }
                for (String head : topicHeads.keySet()) {
                    if (allTags.contains(Codex.DEPENDENT_EXCLUSIVE_PREFIX + head)) {
                        continue;
                    }
                    if (head == spec.getId()) {
                        continue;
                    }
                    // get all xeno followers
                    blackList.addAll(topicHeads.get(head));
                    blackList.add(head);
                }
            } else if (tag.startsWith(Codex.VOLATILE_EXCLUSIVE_PREFIX)) {
                String topic = tag.substring(Codex.VOLATILE_EXCLUSIVE_PREFIX.length());
                HashMap<String, HashSet<String>> topicHeads = innerCircle.get(topic);
                if (topicHeads == null) {
                    continue;
                }
                for (String head : topicHeads.keySet()) {
                    if (head == spec.getId()) {
                        continue;
                    }
                    // get all xeno followers
                    blackList.addAll(topicHeads.get(head));
                    blackList.add(head);
                }
                Set<String> neutral = neutralBlackList.get(spec.getId());
                if (neutral != null) {
                    blackList.addAll(neutral);
                }
            } else if (tag.startsWith(Codex.DEPENDENT_EXCLUSIVE_PREFIX)) {
                String head = tag.substring(Codex.DEPENDENT_EXCLUSIVE_PREFIX.length());
                Set<String> neutral = neutralBlackList.get(head);
                if (neutral != null) {
                    blackList.addAll(neutral);
                }
            }
        }
        cachedBlackList.put(getId(), blackList);
        return blackList;
    }

    private HullModSpecAPI spec;

    @Override
    public void init(HullModSpecAPI spec) {
        this.spec = spec;
    }

    @Override
    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
        sanctuary.get(getId()).applyEffectsBeforeShipCreation(hullSize, stats, getId());
    }

    @Override
    public void applyEffectsAfterShipCreation(ShipAPI ship, String id) {
        sanctuary.get(getId()).applyEffectsAfterShipCreation(ship, getId());
    }

    @Override
    public String getDescriptionParam(int index, HullSize hullSize) {
        return sanctuary.get(getId()).getDescriptionParam(index, hullSize);
    }

    @Override
    public String getDescriptionParam(int index, HullSize hullSize, ShipAPI ship) {
        return sanctuary.get(getId()).getDescriptionParam(index, hullSize, ship);
    }

    @Override
    public void applyEffectsToFighterSpawnedByShip(ShipAPI fighter, ShipAPI ship, String id) {
        sanctuary.get(getId()).applyEffectsToFighterSpawnedByShip(fighter, ship, getId());
    }

    @Override
    public boolean isApplicableToShip(ShipAPI ship) {
        if (getId() == "safetyoverrides") {
            throw new RuntimeException(getBlackList().size() + "");
        }
        if (!sanctuary.get(getId()).isApplicableToShip(ship)) {
            return false;
        }
        for (String hullmodId : getBlackList()) {
            if (ship.getVariant().getHullMods().contains(hullmodId)) {
                return false;
            }
            
        }
        return true;
    }

    @Override
    public String getUnapplicableReason(ShipAPI ship) {
        if (!sanctuary.get(getId()).isApplicableToShip(ship)) {
            return sanctuary.get(getId()).getUnapplicableReason(ship);
        }
        List<String> allBlackList = getBlackList();
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (String hullmodId : ship.getVariant().getHullMods()) {
            if (allBlackList.contains(hullmodId)) {
                HullModSpecAPI spec = Global.getSettings().getHullModSpec(hullmodId);
                if (spec != null) {
                    if (isFirst) {
                        isFirst = false;
                        sb.append(spec.getDisplayName());
                    } else {
                        sb.append(", ");
                        sb.append(spec.getDisplayName());
                    }
                }
            }
        }
        return Global.getSettings().getString(Codex.CONCORD_STRING_CAT, "incompatible_description") + sb.toString();
    }

    @Override
    public boolean canBeAddedOrRemovedNow(ShipAPI ship, MarketAPI marketOrNull, CoreUITradeMode mode) {
        return sanctuary.get(getId()).canBeAddedOrRemovedNow(ship, marketOrNull, mode);
    }

    @Override
    public String getCanNotBeInstalledNowReason(ShipAPI ship, MarketAPI marketOrNull, CoreUITradeMode mode) {
        return sanctuary.get(getId()).getCanNotBeInstalledNowReason(ship, marketOrNull, mode);
    }

    @Override
    public void advanceInCampaign(FleetMemberAPI member, float amount) {
        sanctuary.get(getId()).advanceInCampaign(member, amount);
    }

    @Override
    public void advanceInCombat(ShipAPI ship, float amount) {
        sanctuary.get(getId()).advanceInCombat(ship, amount);
    }

    @Override
    public boolean affectsOPCosts() {
        return sanctuary.get(getId()).affectsOPCosts();
    }

    @Override
    public boolean shouldAddDescriptionToTooltip(HullSize hullSize, ShipAPI ship, boolean isForModSpec) {
        return sanctuary.get(getId()).shouldAddDescriptionToTooltip(hullSize, ship, isForModSpec);
    }

    @Override
    public void addPostDescriptionSection(TooltipMakerAPI tooltip, HullSize hullSize, ShipAPI ship, float width,
            boolean isForModSpec) {
        sanctuary.get(getId()).addPostDescriptionSection(tooltip, hullSize, ship, width, isForModSpec);
        if (getId().equals("phasefield")) {
            if (ship == null || isForModSpec) return;
            if (!com.fs.starfarer.api.impl.combat.PhaseCloakStats.FLUX_LEVEL_AFFECTS_SPEED) return;
            tooltip.addSpacer(6f);
            tooltip.addSectionHeading("Phase Coil Stress Model", Alignment.MID, 0);
            tooltip.addSpacer(6f);
            
            float noPenaltyLevel = PhaseCloakStatsForMod.computeEffective(ship, ConcordModStats.PHASE_STRESS_START_FLUX_LEVEL, 0f);
            float threshold = PhaseCloakStatsForMod.computeEffective(ship, Stats.PHASE_CLOAK_FLUX_LEVEL_FOR_MIN_SPEED_MOD, PhaseCloakStats.BASE_FLUX_LEVEL_FOR_MIN_SPEED);
            float penaltyMaxMult = PhaseCloakStatsForMod.computeEffective(ship, ConcordModStats.PHASE_MAX_STRESS_LEVEL);
            
            if (threshold <= noPenaltyLevel) {
                if (threshold > 0f && threshold < 1f) {
                    tooltip.addPara("No stress below %s hard flux level.", 0f, Color.ORANGE, String.format("%d%%", Math.round(threshold * 100f)));
                    tooltip.addPara("Max stress above %s hard flux level.", 0f, Color.ORANGE, String.format("%d%%", Math.round(threshold * 100f)));
                } else if (threshold <= 0f) {
                    tooltip.addPara("Always %s stress.", 0f, Color.ORANGE, "maximum");
                } else {
                    tooltip.addPara("Always %s stress.", 0f, Color.ORANGE, "no");
                }
            } else {
                if (noPenaltyLevel >= 1f) {
                    tooltip.addPara("Always %s stress.", 0f, Color.ORANGE, "no");
                } else if (threshold <= 0f) {
                    tooltip.addPara("Always %s stress.", 0f, Color.ORANGE, "maximum");
                } else {
                    float slope = 1f / (threshold - noPenaltyLevel);
                    if (noPenaltyLevel < 0f) {
                        float stressAtZero = (-noPenaltyLevel) / slope;
                        tooltip.addPara("Stress level starts at %s at 0 hard flux.", 0f, Color.ORANGE, String.format("%d%%", Math.round(stressAtZero * 100f)));
                    } else {
                        tooltip.addPara("No stress below %s hard flux level.", 0f, Color.ORANGE, String.format("%d%%", Math.round(noPenaltyLevel * 100f)));
                    }
                    
                   if (threshold * penaltyMaxMult >= 1f) {
                        float stressAtMax = slope * (1f - noPenaltyLevel);
                        tooltip.addPara("Maximum stress %s at full hard flux.", 0f, Color.ORANGE, String.format("%d%%", Math.round(stressAtMax * 100f)));
                    } else {
                        tooltip.addPara("Maximum stress %s at %s hard flux level.", 0f, Color.ORANGE,
                                String.format("%d%%", Math.round(penaltyMaxMult * 100f)),
                                String.format("%d%%", Math.round(threshold * penaltyMaxMult * 100f)));
                    }
                }
            }
        }
    }

    @Override
    public Color getBorderColor() {
        return sanctuary.get(getId()).getBorderColor();
    }

    @Override
    public Color getNameColor() {
        return sanctuary.get(getId()).getNameColor();
    }

    @Override
    public int getDisplaySortOrder() {
        return sanctuary.get(getId()).getDisplaySortOrder();
    }

    @Override
    public int getDisplayCategoryIndex() {
        return sanctuary.get(getId()).getDisplayCategoryIndex();
    }

}
