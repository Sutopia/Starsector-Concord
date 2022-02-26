package org.sutopia.starsector.mod.concord.api;

import java.util.Map;

/**
 * Used for Hullmod Effects, tracked and called by ConcordCaptain
 */
public interface FighterInducedEffect {
    /**
     * The map of fighter wing ID to their corresponding effect
     */
    public Map<String, ExternalEffect> getWingEffects();
}
