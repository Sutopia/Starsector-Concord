package org.sutopia.starsector.mod.concord.api;

import com.fs.starfarer.api.combat.MutableShipStatsAPI;

public abstract class ExternalEffect {
    /**
     * This will be called during applyEffectsBeforeShipCreation
     * @param stats ships's mutable stats for modifications
     * @param amount amount of the given object
     */
    public void apply(MutableShipStatsAPI stats, int amount, String id) {
    }
}
