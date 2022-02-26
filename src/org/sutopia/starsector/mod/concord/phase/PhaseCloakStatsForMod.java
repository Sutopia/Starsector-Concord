package org.sutopia.starsector.mod.concord.phase;

import org.sutopia.starsector.mod.concord.Codex;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.MutableStat;
import com.fs.starfarer.api.combat.MutableStat.StatMod;
import com.fs.starfarer.api.combat.PhaseCloakSystemAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipSystemAPI;
import com.fs.starfarer.api.combat.StatBonus;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.impl.combat.PhaseCloakStats;

public class PhaseCloakStatsForMod extends PhaseCloakStats {
    
    protected Object STATUSKEY05 = new Object();
    protected Object STATUSKEY06 = new Object();
    protected Object STATUSKEY07 = new Object();
    protected Object STATUSKEY08 = new Object();
    protected Object STATUSKEY09 = new Object();
    protected Object STATUSKEY10 = new Object();
    protected Object STATUSKEY11 = new Object();
    
    private static final String getString(String key) {
        return Global.getSettings().getString(Codex.CONCORD_STRING_CAT, key);
    }
    
    public static boolean showPhaseModDetails() {
        return Global.getSettings().getBoolean("concord_phase_detail");
    }
    
    public static float getMaxTimeMult(MutableShipStatsAPI stats) {
        return stats.getDynamic().getMod(ConcordModStats.PHASE_TIME_DILATION).computeEffective(getVanillaMaxTimeMult(stats));
    }
    
    public static float getVanillaMaxTimeMult(MutableShipStatsAPI stats) {
        return 1f + (MAX_TIME_MULT - 1f) * stats.getDynamic().getValue(Stats.PHASE_TIME_BONUS_MULT);
    }
    
    @Override
    protected float getDisruptionLevel(ShipAPI ship) {
        return computeEffective(ship, ConcordModStats.PHASE_STRESS_MOD, getUnaffectedStessLevel(ship));
    }
    
    public static float computeEffective(ShipAPI ship, String modId, float baseValue) {
        return computeEffective(ship.getMutableStats(), modId, baseValue);
    }
    
    public static float computeEffective(MutableShipStatsAPI stats, String modId, float baseValue) {
        return stats.getDynamic().getMod(modId).computeEffective(baseValue);
    }
    
    public static float computeEffective(ShipAPI ship, String modId) {
        return computeEffective(ship.getMutableStats(), modId, 1f);
    }
    
    public static float computeEffective(MutableShipStatsAPI stats, String modId) {
        return computeEffective(stats, modId, 1f);
    }
    
    protected float getUnaffectedStessLevel(ShipAPI ship) {
        if (FLUX_LEVEL_AFFECTS_SPEED) {
            float hardFlux = ship.getHardFluxLevel();
            float noPenaltyLevel = computeEffective(ship, ConcordModStats.PHASE_STRESS_START_FLUX_LEVEL, 0f);
            float threshold = computeEffective(ship, Stats.PHASE_CLOAK_FLUX_LEVEL_FOR_MIN_SPEED_MOD, BASE_FLUX_LEVEL_FOR_MIN_SPEED);
            float penaltyMaxMult = computeEffective(ship, ConcordModStats.PHASE_MAX_STRESS_LEVEL);
            if (threshold <= noPenaltyLevel) {
                if (hardFlux > noPenaltyLevel) {
                    return penaltyMaxMult;
                } else {
                    return 0f;
                }
            }
            if (threshold <= 0) return penaltyMaxMult;
            if (hardFlux <= noPenaltyLevel) return 0f;
            float level = (hardFlux - noPenaltyLevel) / (threshold - noPenaltyLevel);
            if (level > penaltyMaxMult) level = penaltyMaxMult;
            return level;
        }
        return 0f;
    }
    
    protected float getEffectValue(ShipAPI ship, float effectLevel, StatBonus modifier, float baseValue) {
        float stressLevel = getDisruptionLevel(ship);
        if (stressLevel <= 0) return baseValue;
        float effectValue = stressLevel * (modifier.computeEffective(baseValue) - baseValue);
        return baseValue + effectValue * effectLevel;
    }
    
    protected float getEffectValue(ShipAPI ship, float effectLevel, StatBonus modifier) {
        return getEffectValue(ship, effectLevel, modifier, 1f);
    }
    
    protected StatBonus getEffectMod(ShipAPI ship, float effectLevel, StatBonus modifier, String id) {
        float stressLevel = getDisruptionLevel(ship);
        StatBonus result = new StatBonus();
        if (stressLevel <= 0) return result;
        
        float mult = (modifier.getBonusMult() - 1f) * stressLevel * effectLevel + 1f;
        float percent = modifier.getPercentMod() * stressLevel * effectLevel;
        float flat = modifier.getFlatBonus() * stressLevel * effectLevel;
        
        result.modifyMult(id + "_2", mult);
        result.modifyPercent(id + "_2", percent);
        result.modifyFlat(id + "_2", flat);
        
        return result;
    }

    @Override
    public void apply(MutableShipStatsAPI stats, String id, State state, float effectLevel) {
        ShipAPI ship = null;
        boolean player = false;
        if (stats.getEntity() instanceof ShipAPI) {
            ship = (ShipAPI) stats.getEntity();
            player = ship == Global.getCombatEngine().getPlayerShip();
            id = id + "_" + ship.getId();
        } else {
            return;
        }

        if (player) {
            maintainStatus(ship, state, effectLevel);
        }

        if (Global.getCombatEngine().isPaused()) {
            return;
        }

        ShipSystemAPI cloak = ship.getPhaseCloak();
        if (cloak == null)
            cloak = ship.getSystem();
        if (cloak == null)
            return;
        
        if (FLUX_LEVEL_AFFECTS_SPEED) {
            stats.getDynamic().getMod(ConcordModStats.PHASE_MAX_STRESS_MAX_SPEED_MOD).modifyMult(id, 1f / getMaxTimeMult(stats));
            
            if (state == State.ACTIVE || state == State.OUT || state == State.IN) {
                ((PhaseCloakSystemAPI) cloak).setMinCoilJitterLevel(getDisruptionLevel(ship));
                
                StatBonus speedMod = getEffectMod(ship, effectLevel, stats.getDynamic().getMod(ConcordModStats.PHASE_MAX_STRESS_MAX_SPEED_MOD), id);
                stats.getMaxSpeed().applyMods(speedMod);
                
                StatBonus zeroFluxMod = getEffectMod(ship, effectLevel, stats.getDynamic().getMod(ConcordModStats.PHASE_MAX_STRESS_ZERO_FLUX_SPEED_MOD), id);
                stats.getZeroFluxSpeedBoost().applyMods(zeroFluxMod);
                
                StatBonus manMod = getEffectMod(ship, effectLevel, stats.getDynamic().getMod(ConcordModStats.PHASE_MAX_STRESS_MANEUVER_MOD), id);
                stats.getAcceleration().applyMods(manMod);
                stats.getDeceleration().applyMods(manMod);
                stats.getMaxTurnRate().applyMods(manMod);
                stats.getTurnAcceleration().applyMods(manMod);
                
                StatBonus sysCDMod = getEffectMod(ship, effectLevel, stats.getDynamic().getMod(ConcordModStats.PHASE_MAX_STRESS_SYSTEM_RECHARGE_RATE_MOD), id);
                stats.getSystemCooldownBonus().applyMods(sysCDMod);
                
                StatBonus sysRangeMod = getEffectMod(ship, effectLevel, stats.getDynamic().getMod(ConcordModStats.PHASE_MAX_STRESS_SYSTEM_RANGE_MOD), id);
                stats.getSystemRangeBonus().applyMods(sysRangeMod);
                
                StatBonus fluxMod = getEffectMod(ship, effectLevel, stats.getDynamic().getMod(ConcordModStats.PHASE_MAX_STRESS_FLUX_DISSIPATION_MOD), id);
                stats.getFluxDissipation().applyMods(fluxMod);
                
                StatBonus phaseCostMod = getEffectMod(ship, effectLevel, stats.getDynamic().getMod(ConcordModStats.PHASE_MAX_STRESS_PHASE_COST_MOD), id);
                stats.getPhaseCloakUpkeepCostBonus().applyMods(phaseCostMod);
                
                StatBonus phaseTDMod = getEffectMod(ship, effectLevel, stats.getDynamic().getMod(ConcordModStats.PHASE_MAX_STRESS_TIME_DILATION_MOD), id);
                stats.getTimeMult().applyMods(phaseTDMod);
            }
        }
        //if (true) throw new RuntimeException(id);
        if (state == State.COOLDOWN || state == State.IDLE) {
            unapply(stats, id);
            return;
        }
        // phase skill bonus, don't touch
        float speedPercentMod = stats.getDynamic().getMod(Stats.PHASE_CLOAK_SPEED_MOD).computeEffective(0f);
        float accelPercentMod = stats.getDynamic().getMod(Stats.PHASE_CLOAK_ACCEL_MOD).computeEffective(0f);
        stats.getMaxSpeed().modifyPercent(id, speedPercentMod * effectLevel);
        stats.getAcceleration().modifyPercent(id, accelPercentMod * effectLevel);
        stats.getDeceleration().modifyPercent(id, accelPercentMod * effectLevel);

        float speedMultMod = stats.getDynamic().getMod(Stats.PHASE_CLOAK_SPEED_MOD).getMult();
        float accelMultMod = stats.getDynamic().getMod(Stats.PHASE_CLOAK_ACCEL_MOD).getMult();
        stats.getMaxSpeed().modifyMult(id, speedMultMod * effectLevel);
        stats.getAcceleration().modifyMult(id, accelMultMod * effectLevel);
        stats.getDeceleration().modifyMult(id, accelMultMod * effectLevel);

        float level = effectLevel;

        //float levelForAlpha = level;

        if (state == State.IN || state == State.ACTIVE) {
            ship.setPhased(true);
        } else if (state == State.OUT) {
            if (level > 0.5f) {
                ship.setPhased(true);
            } else {
                ship.setPhased(false);
            }
        }

        ship.setExtraAlphaMult(1f - (1f - SHIP_ALPHA_MULT) * level);
        ship.setApplyExtraAlphaToEngines(true);
        
        
        
        float shipTimeMult = 1f + (getMaxTimeMult(stats) - 1f) * level;
        stats.getTimeMult().modifyMult(id, shipTimeMult);
        if (player) {
            Global.getCombatEngine().getTimeMult().modifyMult(id, 1f / shipTimeMult);
        } else {
            Global.getCombatEngine().getTimeMult().unmodify(id);
        }
    }

    @Override
    public void unapply(MutableShipStatsAPI stats, String id) {
        
        ShipAPI ship = null;
        if (stats.getEntity() instanceof ShipAPI) {
            ship = (ShipAPI) stats.getEntity();
        } else {
            return;
        }
        
        Global.getCombatEngine().getTimeMult().unmodify(id);
        stats.getTimeMult().unmodify(id);

        stats.getMaxSpeed().unmodify(id);
        stats.getAcceleration().unmodify(id);
        stats.getDeceleration().unmodify(id);
        
        // stress removal
        stats.getMaxSpeed().unmodify(id + "_2");
        stats.getZeroFluxSpeedBoost().unmodify(id + "_2");
        stats.getAcceleration().unmodify(id + "_2");
        stats.getDeceleration().unmodify(id + "_2");
        stats.getMaxTurnRate().unmodify(id + "_2");
        stats.getTurnAcceleration().unmodify(id + "_2");
        stats.getSystemCooldownBonus().unmodify(id + "_2");
        stats.getSystemRangeBonus().unmodify(id + "_2");
        stats.getFluxDissipation().unmodify(id + "_2");
        stats.getPhaseCloakUpkeepCostBonus().unmodify(id + "_2");
        stats.getTimeMult().unmodify(id + "_2");

        ship.setPhased(false);
        ship.setExtraAlphaMult(1f);

        ShipSystemAPI cloak = ship.getPhaseCloak();
        if (cloak == null)
            cloak = ship.getSystem();
        if (cloak != null) {
            ((PhaseCloakSystemAPI) cloak).setMinCoilJitterLevel(0f);
        }
    }

    @Override
    protected void maintainStatus(ShipAPI playerShip, State state, float effectLevel) {
        float level = effectLevel;
        float f = VULNERABLE_FRACTION;

        ShipSystemAPI cloak = playerShip.getPhaseCloak();
        if (cloak == null)
            cloak = playerShip.getSystem();
        if (cloak == null)
            return;
        
        if (!playerShip.getMutableStats().getTimeMult().isUnmodified()) {
            String msg = String.format(getString("phase_tidi_content"), playerShip.getMutableStats().getTimeMult().modified);
            Global.getCombatEngine().maintainStatusForPlayerShip(STATUSKEY2, cloak.getSpecAPI().getIconSpriteName(),
                    cloak.getDisplayName(), msg, false);
        }

        if (FLUX_LEVEL_AFFECTS_SPEED) {
            if (level > f) {
                if (getDisruptionLevel(playerShip) <= 0f) {
                    Global.getCombatEngine().maintainStatusForPlayerShip(STATUSKEY3, cloak.getSpecAPI().getIconSpriteName(),
                            getString("phase_coil_stable_title"), getString("phase_coil_stable_content"), false);
                } else {
                    String stressLevel = (int) Math.round((getDisruptionLevel(playerShip)) * 100f) + "%";
                    Global.getCombatEngine().maintainStatusForPlayerShip(STATUSKEY3, cloak.getSpecAPI().getIconSpriteName(),
                            getString("phase_coil_stressed_title"), stressLevel, true);
                    
                    String modifierId = cloak.getId() + " effect_" + playerShip.getId() + "_2"; 
                    
                    if (isModified(playerShip.getMutableStats().getMaxSpeed(), modifierId)) {
                        StringBuilder sb = new StringBuilder();
                        buildModifierString(sb, playerShip.getMutableStats().getMaxSpeed(), modifierId, getString("phase_stress_max_speed_desc"));
                        
                        Global.getCombatEngine().maintainStatusForPlayerShip(STATUSKEY05, cloak.getSpecAPI().getIconSpriteName(),
                                getString("phase_stress_engine_title"), sb.toString(), true);
                    }
                    
                    if (isModified(playerShip.getMutableStats().getZeroFluxSpeedBoost(), modifierId)) {
                        StringBuilder sb = new StringBuilder();
                        buildModifierString(sb, playerShip.getMutableStats().getZeroFluxSpeedBoost(), modifierId, getString("phase_stress_zero_flux_desc"));
                        Global.getCombatEngine().maintainStatusForPlayerShip(STATUSKEY06, "graphics/icons/hullsys/infernium_injector.png",
                                getString("phase_stress_engine_title"), sb.toString(), true);
                    }
                    
                    if (isModified(playerShip.getMutableStats().getAcceleration(), modifierId)) {
                        StringBuilder sb = new StringBuilder();
                        buildModifierString(sb, playerShip.getMutableStats().getAcceleration(), modifierId, getString("phase_stress_maneuverability_desc"));
                        Global.getCombatEngine().maintainStatusForPlayerShip(STATUSKEY07, "graphics/icons/hullsys/maneuvering_jets.png",
                                getString("phase_stress_engine_title"), sb.toString(), true);
                    }
                    
                    if (isModified(playerShip.getMutableStats().getSystemCooldownBonus(), modifierId)) {
                        StringBuilder sb = new StringBuilder();
                        buildModifierString(sb, playerShip.getMutableStats().getSystemCooldownBonus(), modifierId, getString("phase_stress_sys_cd_desc"));
                        Global.getCombatEngine().maintainStatusForPlayerShip(STATUSKEY08, "graphics/icons/hullsys/quantum_disruptor.png",
                                getString("phase_stress_system_title"), sb.toString(), true);
                    }
                    
                    if (isModified(playerShip.getMutableStats().getAcceleration(), modifierId)) {
                        StringBuilder sb = new StringBuilder();
                        buildModifierString(sb, playerShip.getMutableStats().getSystemRangeBonus(), modifierId, getString("phase_stress_sys_range_desc"));
                        Global.getCombatEngine().maintainStatusForPlayerShip(STATUSKEY09, "graphics/icons/hullsys/quantum_disruptor.png",
                                getString("phase_stress_system_title"), sb.toString(), true);
                    }
                    
                    if (isModified(playerShip.getMutableStats().getFluxDissipation(), modifierId)) {
                        StringBuilder sb = new StringBuilder();
                        buildModifierString(sb, playerShip.getMutableStats().getFluxDissipation(), modifierId, getString("phase_stress_flux_diss_desc"));
                        Global.getCombatEngine().maintainStatusForPlayerShip(STATUSKEY10, "graphics/icons/tactical/venting_flux.png",
                                getString("phase_stress_flux_title"), sb.toString(), true);
                    }
                    
                    if (isModified(playerShip.getMutableStats().getPhaseCloakUpkeepCostBonus(), modifierId)) {
                        StringBuilder sb = new StringBuilder();
                        buildModifierString(sb, playerShip.getMutableStats().getPhaseCloakUpkeepCostBonus(), modifierId, getString("phase_stress_upkeep_desc"));
                        Global.getCombatEngine().maintainStatusForPlayerShip(STATUSKEY11, cloak.getSpecAPI().getIconSpriteName(),
                                getString("phase_stress_flux_title"), sb.toString(), true);
                    }
                }
            }
        }
    }
    
    public static boolean isModified(MutableStat stat, String id) {
        if (stat.getFlatMods().containsKey(id)) return true;
        if (stat.getMultMods().containsKey(id)) return true;
        if (stat.getPercentMods().containsKey(id)) return true;
        return false;
    }
    
    public static boolean isModified(StatBonus stat, String id) {
        if (stat.getFlatBonuses().containsKey(id)) return true;
        if (stat.getMultBonuses().containsKey(id)) return true;
        if (stat.getPercentBonuses().containsKey(id)) return true;
        return false;
    }
    
    public static void buildModifierString(StringBuilder sb, MutableStat stat, String id, String description) {
        StatMod multMod = stat.getMultStatMod(id);
        StatMod percentMod = stat.getPercentStatMod(id);
        StatMod flatMod = stat.getFlatStatMod(id);
        boolean isFirst = true;
        if (multMod != null && multMod.getValue() != 1f) {
            isFirst = false;
            sb.append(String.format(description + " at %d%%", Math.round(multMod.getValue() * 100f)));
        }
        
        float flat = 0f;
        if (flatMod != null) {
            flat += flatMod.getValue();
        }
        if (percentMod != null) {
            flat += stat.getBaseValue() * percentMod.getValue() / 100f;
        }
        
        if (flat != 0f) {
            if (!isFirst) {
                sb.append(", ");
            }
            sb.append(String.format(description + " %+d", Math.round(flat)));
        }
    }
    
    public static void buildModifierString(StringBuilder sb, StatBonus stat, String id, String description) {
        StatMod multMod = stat.getMultBonus(id);
        StatMod percentMod = stat.getPercentBonus(id);
        
        float mult = 1f;
        if (multMod != null && multMod.getValue() != 1f) {
            mult *= multMod.getValue();
        }
        
        if (percentMod != null) {
            mult *= (100f + percentMod.getValue());
        } else {
            mult *= 100f;
        }
        
        sb.append(String.format(description + " at %d%%", Math.round(mult)));
    }
}