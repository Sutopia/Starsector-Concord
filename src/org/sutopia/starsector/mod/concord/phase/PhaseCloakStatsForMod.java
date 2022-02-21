package org.sutopia.starsector.mod.concord.phase;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.PhaseCloakSystemAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipSystemAPI;
import com.fs.starfarer.api.impl.campaign.ids.Stats;
import com.fs.starfarer.api.impl.combat.PhaseCloakStats;

public class PhaseCloakStatsForMod extends PhaseCloakStats {
    //public static final String PTSD_HULLMOD_ID = "su_phase_ptsd";
    // public static final float MANEUVERABILITY_BONUS = 50f;
    public static final float PTSD_NO_PENALTY_FLUX_LEVEL = .25f;

    public static float getMaxTimeMult(MutableShipStatsAPI stats) {
        //stats.getDynamic().getValue(id, base)
        return 1f + (MAX_TIME_MULT - 1f) * stats.getDynamic().getValue(Stats.PHASE_TIME_BONUS_MULT);
    }

    @Override
    public void apply(MutableShipStatsAPI stats, String id, State state, float effectLevel) {
        ShipAPI ship = null;
        boolean player = false;
        boolean hasPTSD = false;
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
            if (state == State.ACTIVE || state == State.OUT || state == State.IN) {
                if (hasPTSD) {
                    float mult = getZeroFluxSpeedMult(ship, effectLevel);
                    if (mult < 1f) {
                        stats.getZeroFluxSpeedBoost().modifyMult(id + "_2", mult);
                    } else {
                        stats.getZeroFluxSpeedBoost().unmodifyMult(id + "_2");
                    }
                } else {
                    float mult = getSpeedMult(ship, effectLevel);
                    if (mult < 1f) {
                        stats.getMaxSpeed().modifyMult(id + "_2", mult);
                    } else {
                        stats.getMaxSpeed().unmodifyMult(id + "_2");
                    }
                }
                ((PhaseCloakSystemAPI) cloak).setMinCoilJitterLevel(getDisruptionLevel(ship));
            }
        }

        if (state == State.COOLDOWN || state == State.IDLE) {
            unapply(stats, id);
            return;
        }

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

        if (hasPTSD && (state == State.ACTIVE || state == State.OUT || state == State.IN)) {
            stats.getZeroFluxMinimumFluxLevel().modifyFlat(id, 2f);
            // stats.getAcceleration().modifyPercent(id + "_2",
            // MANEUVERABILITY_BONUS);
            // stats.getDeceleration().modifyPercent(id + "_2",
            // MANEUVERABILITY_BONUS);
            // stats.getTurnAcceleration().modifyPercent(id + "_2",
            // MANEUVERABILITY_BONUS * 2f);
            // stats.getMaxTurnRate().modifyPercent(id + "_2",
            // MANEUVERABILITY_BONUS);
        }

        float level = effectLevel;

        float levelForAlpha = level;

        if (state == State.IN || state == State.ACTIVE) {
            ship.setPhased(true);
            levelForAlpha = level;
        } else if (state == State.OUT) {
            if (level > 0.5f) {
                ship.setPhased(true);
            } else {
                ship.setPhased(false);
            }
            levelForAlpha = level;
        }

        ship.setExtraAlphaMult(1f - (1f - SHIP_ALPHA_MULT) * levelForAlpha);
        ship.setApplyExtraAlphaToEngines(true);

        float extra = 0f;
        float shipTimeMult = 1f + (getMaxTimeMult(stats) - 1f) * levelForAlpha * (1f - extra);
        stats.getTimeMult().modifyMult(id, shipTimeMult);
        if (player) {
            Global.getCombatEngine().getTimeMult().modifyMult(id, 1f / shipTimeMult);
        } else {
            Global.getCombatEngine().getTimeMult().unmodify(id);
        }
    }

    public float getZeroFluxSpeedMult(ShipAPI ship, float effectLevel) {
        if (getDisruptionLevel(ship) <= 0f)
            return 1f;
        return Math.max(0f, 1f - getDisruptionLevel(ship) * effectLevel);
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
        stats.getMaxSpeed().unmodifyMult(id + "_2");

        stats.getZeroFluxMinimumFluxLevel().unmodify(id);
        stats.getZeroFluxSpeedBoost().unmodifyMult(id + "_2");

        stats.getAcceleration().unmodify(id);
        stats.getDeceleration().unmodify(id);

        /*
         * stats.getAcceleration().unmodify(id + "_2");
         * stats.getDeceleration().unmodify(id + "_2");
         * stats.getTurnAcceleration().unmodify(id + "_2");
         * stats.getMaxTurnRate().unmodify(id + "_2");
         */

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
        boolean hasPTSD = false;

        if (level > f && !hasPTSD) {
            Global.getCombatEngine().maintainStatusForPlayerShip(STATUSKEY2, cloak.getSpecAPI().getIconSpriteName(),
                    cloak.getDisplayName(), "time flow altered", false);
        }

        if (FLUX_LEVEL_AFFECTS_SPEED) {
            if (level > f) {
                if (getDisruptionLevel(playerShip) <= 0f) {
                    Global.getCombatEngine().maintainStatusForPlayerShip(STATUSKEY3, cloak.getSpecAPI().getIconSpriteName(),
                            "phase coils stable", hasPTSD ? "zero flux bonus at 100%" : "top speed at 100%", false);
                } else {
                    String speedPercentStr = (int) Math.round((hasPTSD ? getZeroFluxSpeedMult(playerShip, effectLevel)
                            : getSpeedMult(playerShip, effectLevel)) * 100f) + "%";
                    Global.getCombatEngine().maintainStatusForPlayerShip(STATUSKEY3, cloak.getSpecAPI().getIconSpriteName(),
                            "phase coil stress", (hasPTSD ? "zero flux bonus at " : "top speed at ") + speedPercentStr, true);
                }
            }
        }
    }
}