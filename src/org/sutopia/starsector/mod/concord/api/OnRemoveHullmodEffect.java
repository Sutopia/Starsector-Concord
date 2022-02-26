package org.sutopia.starsector.mod.concord.api;

import com.fs.starfarer.api.combat.ShipAPI;

public interface OnRemoveHullmodEffect {
    /**
     * Called when a hullmod is removed, IS CALLED when removed by undo
     */
    public void onRemove(ShipAPI ship);
}
