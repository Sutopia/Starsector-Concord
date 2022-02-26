package org.sutopia.starsector.mod.concord.phase;

public final class ConcordModStats {
    public static final String PHASE_TIME_DILATION = "concord_phase_time_dilation"; // base is 3
    
    /** 
     * base is 0, ship start getting phase stress with any hard flux. At 1, ship at max flux has 0 stress
     */
    public static final String PHASE_STRESS_START_FLUX_LEVEL = "concord_phase_stress_start_flux";
    
    // use vanilla "Stats.PHASE_CLOAK_FLUX_LEVEL_FOR_MIN_SPEED_MOD" for max stress flux level
    
    /** 
     * base is 1f = 100%. This attribute is specifically used of scaling maximum penalty
     */
    public static final String PHASE_MAX_STRESS_LEVEL = "concord_phase_stress_cap";
    
    /** 
     * Modify phase ship's phase coil stress directly.
     * Expected to be use by ship system
     */
    public static final String PHASE_STRESS_MOD = "concord_phase_stress_manipulation";
    
    ///////////////////////////////////////////////////////////////////////////////////////
    /** 
     * modify max speed at 100% stress, scale linearly to stress
     * <p>base game already applies a modifyMult of inverse phase max speed
     */
    public static final String PHASE_MAX_STRESS_MAX_SPEED_MOD = "concord_phase_max_spd_mod";
    
    /** 
     * modify zero flux speed at 100% stress, scale linearly to stress
     * <p>base value is the ship's base zero flux speed bonus
     */
    public static final String PHASE_MAX_STRESS_ZERO_FLUX_SPEED_MOD = "concord_phase_zero_flux_spd_mod";
    
    /** 
     * modify ship maneuverability at 100% stress, scale linearly to stress
     * <p>base value is 1 = 100%
     */
    public static final String PHASE_MAX_STRESS_MANEUVER_MOD = "concord_phase_maneuver_mod";
    
    /** 
     * modify ship system recharge rate at 100% stress, scale linearly to stress
     * <p>base value is 1 = 100%
     */
    public static final String PHASE_MAX_STRESS_SYSTEM_RECHARGE_RATE_MOD = "concord_phase_sys_recharge_mod";
    
    /** 
     * modify ship system effective range at 100% stress, scale linearly to stress
     * <p>base value is 1 = 100%
     */
    public static final String PHASE_MAX_STRESS_SYSTEM_RANGE_MOD = "concord_phase_sys_range_mod";
    
    /** 
     * modify ship flux dissipation at 100% stress, scale linearly to stress (soft flux)
     * <p>base value is the ship's flux dissipation
     */
    public static final String PHASE_MAX_STRESS_FLUX_DISSIPATION_MOD = "concord_phase_dissipation";
    
    /** 
     * modify phase upkeep flux cost, scale linearly to stress (soft flux)
     * <p>base value is the ship's phase upkeep flux cost
     */
    public static final String PHASE_MAX_STRESS_PHASE_COST_MOD = "concord_phase_upkeep_mod";
    
    

    /** 
     * modify ship time dilation at 100% stress, scale linearly to stress
     * <p>base value is the ship's getMaxTimeMult
     */
    public static final String PHASE_MAX_STRESS_TIME_DILATION_MOD = "concord_phase_stress_time_dilation";
    
}
