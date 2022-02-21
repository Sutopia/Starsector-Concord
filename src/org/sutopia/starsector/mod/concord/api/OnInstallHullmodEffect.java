package org.sutopia.starsector.mod.concord.api;

import com.fs.starfarer.api.combat.ShipAPI;

@Deprecated
public interface OnInstallHullmodEffect {
    /**
     * Called ONCE when a hullmod is added to the ship REGARDLESS OF FINALIZED OR NOT
     */
    public void onInstall(ShipAPI ship);
}
