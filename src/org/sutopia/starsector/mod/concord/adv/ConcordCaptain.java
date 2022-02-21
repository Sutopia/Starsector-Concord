package org.sutopia.starsector.mod.concord.adv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.sutopia.starsector.mod.concord.Codex;
import org.sutopia.starsector.mod.concord.api.TrackedHullmodEffect;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.HullModEffect;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.loading.HullModSpecAPI;

public class ConcordCaptain extends BaseHullMod {
    public static final ArrayList<HullModSpecAPI> doppelgangers = new ArrayList<>();
    public static final ArrayList<HullModSpecAPI> specs = new ArrayList<>();
    
    public static final HashMap<String, HullModEffect> trackedHullmods = new HashMap<>();

    private static final HashMap<String, HashSet<String>> installMemory = new HashMap<>();
    
    @SuppressWarnings("deprecation")
    @Override
    public void applyEffectsAfterShipCreation(ShipAPI ship, String id) {
        if (ship == null) {
            return;
        }
        syncHidden(ship);
        
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
                removeEffects.add((TrackedHullmodEffect) trackedHullmods.get(hullmod));
                iter.remove();
            }
        }
        
        for (String hullmod : trackedHullmods.keySet()) {
            if (ship.getVariant().hasHullMod(hullmod) && !installed.contains(hullmod)) {
                installed.add(hullmod);
                installEffects.add((TrackedHullmodEffect) trackedHullmods.get(hullmod));
            }
        }
        
        for (TrackedHullmodEffect effect : removeEffects) {
            effect.onRemove(ship);
        }
        for (TrackedHullmodEffect effect : installEffects) {
            effect.onInstall(ship);
        }
    }
    
    public static void syncHidden(ShipAPI ship) {
        if (ship == null) {
            return;
        }
        FactionAPI player = Global.getSector().getPlayerFaction();
        if (player == null) {
            return;
        }
        
        for (HullModSpecAPI spec: specs) {
            String doppelganger = Codex.ID_PREFIX_CONCORD_DOPPELGANGER + spec.getId();
            if (player.knowsHullMod(spec.getId()) 
                    && !player.knowsHullMod(doppelganger)) {
                player.addKnownHullMod(doppelganger);
            }
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
