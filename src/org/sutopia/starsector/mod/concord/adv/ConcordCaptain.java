package org.sutopia.starsector.mod.concord.adv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.sutopia.starsector.mod.concord.api.OnInstallHullmodEffect;
import org.sutopia.starsector.mod.concord.api.OnRemoveHullmodEffect;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.loading.HullModSpecAPI;

public class ConcordCaptain extends BaseHullMod {
    public static final ArrayList<HullModSpecAPI> doppelgangers = new ArrayList<>();
    public static final ArrayList<HullModSpecAPI> specs = new ArrayList<>();

    private static final HashMap<String, HashSet<String>> installMemory = new HashMap<>();
    
    @Override
    public void applyEffectsAfterShipCreation(ShipAPI ship, String id) {
        if (ship == null) {
            return;
        }
        syncHidden(ship);
        
        ArrayList<OnRemoveHullmodEffect> removeEffects = new ArrayList<>();
        ArrayList<OnInstallHullmodEffect> installEffects = new ArrayList<>();
        
        HashSet<String> installed = installMemory.get(ship.getVariant().getHullVariantId());
        if (installed == null) { // init
            installed = new HashSet<String>();
            installed.addAll(ship.getVariant().getHullMods());
            installMemory.put(ship.getVariant().getHullVariantId(), installed);
        }
        
        Iterator<String> iter = installed.iterator();
        while (iter.hasNext()) {
            String hullmod = iter.next();
            if (!ship.getVariant().hasHullMod(hullmod)) {
                HullModSpecAPI spec = Global.getSettings().getHullModSpec(hullmod);
                if (spec != null && spec.getEffect() instanceof OnRemoveHullmodEffect) {
                    removeEffects.add((OnRemoveHullmodEffect) spec.getEffect());
                }
                iter.remove();
            }
        }
        
        for (String hullmod : ship.getVariant().getHullMods()) {
            HullModSpecAPI spec = Global.getSettings().getHullModSpec(hullmod);
            if (spec != null) {
                if (!installed.contains(hullmod) && spec.getEffect() instanceof OnInstallHullmodEffect) {
                    installEffects.add((OnInstallHullmodEffect) spec.getEffect());
                }
                installed.add(hullmod);
            }
        }
        
        for (OnRemoveHullmodEffect effect : removeEffects) {
            effect.onRemove(ship);
        }
        for (OnInstallHullmodEffect effect : installEffects) {
            effect.onInstall(ship);
        }
    }
    
    public static void syncHidden(ShipAPI ship) {
        if (ship == null) {
            return;
        }
        for (HullModSpecAPI spec: specs) {
            spec.setHidden(!ship.getVariant().hasHullMod(spec.getId()));
        }
        
        for (HullModSpecAPI doppelganger: doppelgangers) {
            doppelganger.setHidden(ship.getVariant().hasHullMod(doppelganger.getId()));
        }
    }
    
    /*@Override
    public void applyEffectsBeforeShipCreation(HullSize hullSize,
            MutableShipStatsAPI stats, String id) {
        for (HullModSpecAPI spec: specs) {
            spec.setHidden(false);
        }
        
        for (HullModSpecAPI doppelganger: doppelgangers) {
            doppelganger.setHidden(true);
        }
    }*/
}
