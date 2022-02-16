package org.sutopia.starsector.mod.concord;

public class Codex {
    // concord implement *double* check
    // if someone manage to somehow F this up it must be intentional
    public static final String ID_PREFIX_CONCORD_DOPPELGANGER = "concord_";
    public static final String TAG_CONCORD_OPT_IN = "auto_concord";
    
    public static final String TAG_OWN_TOPIC = "vanilla_auto_concord";
    
    public static final String TAG_CONCORD_CUSTODY = "custody_by_concord";
    public static final String TAG_CONCORD_HIDDEN = "concord_immutable";
    
    // topic leader tag
    public static final String VOLATILE_EXCLUSIVE_PREFIX = "vEx_";
    // leader follower tag
    public static final String DEPENDENT_EXCLUSIVE_PREFIX = "dEx_";
    // very specific neutral exclusion, avoid using
    public static final String NEUTRAL_EXCLUSIVE_PREFIX = "nEx_";
    // assigned internally to keep track of topic
    public static final String TOPIC_EXCLUSIVE_PREFIX = "tEx";
    
    
    // well known topics
    // ITU, DTC, Advanced Targeting Unit and stations' Supercomputer
    public static final String TOPIC_TARGETING = "targeting_unit";
    // Advanced Optics vs High Scatter Amp
    public static final String TOPIC_BEAM = "beam_style";
    // Adaptive vs Anchor
    public static final String TOPIC_PHASE = "phase_style";
    // reinforced hull / vast bulk
    public static final String TOPIC_HULL = "hull_configuration";
    // axial rotation / anchor rotation
    public static final String TOPIC_ROTATE = "forced_rotation";
    // do not back off
    public static final String TOPIC_AI = "ai_flag";
    
    // legacy implementations
    public static final String VANILLA_EXCLUSIVE_PREFIX = "vEx_";
    public static final String TARGETING_UNIT = "percent_range_mod";
    public static final String PHASE_MAJOR_MOD = "phase_mutator";
}
