package org.sutopia.starsector.mod.concord.api;

import java.util.Map;

/**
 * Used for Hullmod Effects, tracked and called by ConcordCaptain
 */
public interface WeaponInducedEffect {
    /**
     * The map of weapon ID to their corresponding effect
     */
    public Map<String, ExternalEffect> getWeaponEffects();
}
