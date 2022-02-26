package org.sutopia.starsector.mod.concord.phase;

import java.awt.Color;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.impl.combat.PhaseCloakStats;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;

public class PhaseStatusDisplay extends BaseHullMod {
    @Override
    public void addPostDescriptionSection(TooltipMakerAPI tooltip, HullSize hullSize, ShipAPI ship, float width,
            boolean isForModSpec) {
        if (ship == null || isForModSpec)
            return;
        if (!com.fs.starfarer.api.impl.combat.PhaseCloakStats.FLUX_LEVEL_AFFECTS_SPEED)
            return;
        tooltip.addSpacer(6f);
        tooltip.addSectionHeading("Phase Coil Stress Model", Alignment.MID, 0);
        tooltip.addSpacer(6f);

        float noPenaltyLevel = PhaseCloakStatsForMod.computeEffective(ship, ConcordModStats.PHASE_STRESS_START_FLUX_LEVEL, 0f);
        float threshold = PhaseCloakStatsForMod.computeEffective(ship, Stats.PHASE_CLOAK_FLUX_LEVEL_FOR_MIN_SPEED_MOD,
                PhaseCloakStats.BASE_FLUX_LEVEL_FOR_MIN_SPEED);
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
