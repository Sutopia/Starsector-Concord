package org.sutopia.starsector.mod.concord.adv;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.ShipVariantAPI;

public final class CommonDeploy {
    public static Object getPersistentData(ShipVariantAPI ship) {
        if (ship == null || ship.getHullVariantId() == null) {
            return null;
        }
        return Global.getSector().getPersistentData().get(ship.getHullVariantId());
    }
    
    public static void setPersistentData(ShipVariantAPI ship, Object o) {
        if (ship != null && ship.getHullVariantId() != null) {
            Global.getSector().getPersistentData().put(ship.getHullVariantId(), o);
        }
    }
}
