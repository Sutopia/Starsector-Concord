package org.sutopia.starsector.mod.concord.api;

import com.fs.starfarer.api.combat.ShipHullSpecAPI;
import com.fs.starfarer.api.combat.ShipVariantAPI;

public interface SelectiveTransientHullmod extends GlobalTransientHullmod {
    
    public boolean shouldApplyToSpec(ShipHullSpecAPI spec);
    
    public boolean shouldApplyToVariant(ShipVariantAPI variant);
    
}
