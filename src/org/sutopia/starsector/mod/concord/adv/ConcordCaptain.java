package org.sutopia.starsector.mod.concord.adv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.sutopia.starsector.mod.concord.api.TrackedHullmodEffect;
import org.sutopia.starsector.mod.concord.api.GlobalTransientHullmod;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;

public class ConcordCaptain extends BaseHullMod implements GlobalTransientHullmod {
    
    public static final HashMap<String, TrackedHullmodEffect> trackedHullmods = new HashMap<>();

    private static final HashMap<String, HashSet<String>> installMemory = new HashMap<>();
    
    @Override
    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
        
    }
    
    @Override
    public void applyEffectsAfterShipCreation(ShipAPI ship, String id) {
        if (ship == null) {
            return;
        }
        
        ArrayList<TrackedHullmodEffect> removeEffects = new ArrayList<>();
        ArrayList<TrackedHullmodEffect> installEffects = new ArrayList<>();
        
        HashSet<String> installed = installMemory.get(ship.getVariant().getHullVariantId());
        if (installed == null) { // init
            installed = new HashSet<String>();
            installed.addAll(ship.getVariant().getHullMods());
            installed.retainAll(trackedHullmods.keySet());
            installMemory.put(ship.getVariant().getHullVariantId(), installed);
        }
        
        Iterator<String> iter = installed.iterator();
        while (iter.hasNext()) {
            String hullmod = iter.next();
            if (!ship.getVariant().hasHullMod(hullmod)) {
                removeEffects.add(trackedHullmods.get(hullmod));
                iter.remove();
            }
        }
        
        for (String hullmod : trackedHullmods.keySet()) {
            if (ship.getVariant().hasHullMod(hullmod) && !installed.contains(hullmod)) {
                installed.add(hullmod);
                installEffects.add(trackedHullmods.get(hullmod));
            }
        }
        
        for (TrackedHullmodEffect effect : removeEffects) {
            effect.onRemove(ship);
        }
        for (TrackedHullmodEffect effect : installEffects) {
            effect.onInstall(ship);
        }
    }
}
