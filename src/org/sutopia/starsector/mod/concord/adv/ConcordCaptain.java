package org.sutopia.starsector.mod.concord.adv;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import org.sutopia.starsector.mod.concord.Codex;
import org.sutopia.starsector.mod.concord.api.ExternalEffect;
import org.sutopia.starsector.mod.concord.api.FighterInducedEffect;
import org.sutopia.starsector.mod.concord.api.TrackedHullmodEffect;
import org.sutopia.starsector.mod.concord.api.GlobalTransientHullmod;
import org.sutopia.starsector.mod.concord.api.WeaponInducedEffect;
import org.sutopia.starsector.mod.concord.dynamic.MutableShipHullSpecAPI;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.combat.ShipVariantAPI;
import com.fs.starfarer.api.loading.HullModSpecAPI;
import com.fs.starfarer.api.ui.Alignment;
import com.fs.starfarer.api.ui.TooltipMakerAPI;

public class ConcordCaptain extends BaseHullMod implements GlobalTransientHullmod {

    public static final HashMap<String, TrackedHullmodEffect> trackedHullmods = new HashMap<>();

    private static final HashMap<String, HashSet<String>> installMemory = new HashMap<>();

    public static void clearMemCache() {
        installMemory.clear();
    }

    @Override
    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
        if (stats == null) {
            return;
        }
        ShipVariantAPI variant = stats.getVariant();
        HashMap<String, Integer> weaponCounts = new HashMap<>();
        for (String slotId : variant.getFittedWeaponSlots()) {
            String wep = variant.getWeaponId(slotId);
            Integer prevCount = weaponCounts.get(wep);
            if (prevCount == null) {
                prevCount = 0;
            }
            weaponCounts.put(wep, prevCount + 1);
        }
        HashMap<String, Integer> wingCounts = new HashMap<>();
        for (String wingId : variant.getFittedWings()) {
            Integer prevCount = wingCounts.get(wingId);
            if (prevCount == null) {
                prevCount = 0;
            }
            wingCounts.put(wingId, prevCount + 1);
        }

        for (String hullmod : stats.getVariant().getHullMods()) {
            HullModSpecAPI spec = Global.getSettings().getHullModSpec(hullmod);
            if (spec.getEffect() instanceof WeaponInducedEffect) {
                Map<String, ExternalEffect> weMap = ((WeaponInducedEffect) spec.getEffect()).getWeaponEffects();
                if (weMap == null) {
                    continue;
                }
                for (String weapon : weaponCounts.keySet()) {
                    if (weMap.containsKey(weapon)) {
                        ExternalEffect eff = weMap.get(weapon);
                        eff.apply(stats, weaponCounts.get(weapon), id + "_" + weapon);
                    }
                }
            }
            if (spec.getEffect() instanceof FighterInducedEffect) {
                Map<String, ExternalEffect> fiMap = ((FighterInducedEffect) spec.getEffect()).getWingEffects();
                if (fiMap == null) {
                    continue;
                }
                for (String wing : wingCounts.keySet()) {
                    if (fiMap.containsKey(wing)) {
                        ExternalEffect eff = fiMap.get(wing);
                        eff.apply(stats, wingCounts.get(wing), id + "_" + wing);
                    }
                }
            }
        }
    }

    @Override
    public void applyEffectsAfterShipCreation(ShipAPI ship, String id) {
        if (ship == null) {
            return;
        }
        String shipId = ship.getFleetMemberId();
        if (shipId == null) {
            return;
        }
        //ConcordCommander.revertFighters();

        ArrayList<TrackedHullmodEffect> removeEffects = new ArrayList<>();
        ArrayList<TrackedHullmodEffect> installEffects = new ArrayList<>();

        HashSet<String> installed = installMemory.get(shipId);
        if (installed == null) { // init
            installed = new HashSet<String>();
            for (String hullmod : trackedHullmods.keySet()) {
                if (ship.getVariant().hasHullMod(hullmod)) {
                    installed.add(hullmod);
                }
            }
            installMemory.put(shipId, installed);
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
        
        boolean hasFighterMutator = false;
        for (String hullmod : ship.getVariant().getHullMods()) {
            HullModSpecAPI modSpec = Global.getSettings().getHullModSpec(hullmod);
            if (modSpec.hasTag(Codex.VOLATILE_EXCLUSIVE_PREFIX + Codex.TOPIC_FIGHTER_SPEC_CHANGE)) {
                hasFighterMutator = true;
            }
        }
        if (!hasFighterMutator) {
            for (int i = 0; i < ship.getVariant().getWings().size(); i++) {
                String wingSpec = ship.getVariant().getWingId(i);
                if (wingSpec != null && ConcordDynamicInstanceAssembly.INJECTED_FIGHTER_TO_VANILLA.containsKey(wingSpec)) {
                    ship.getVariant().setWingId(i, ConcordDynamicInstanceAssembly.INJECTED_FIGHTER_TO_VANILLA.get(wingSpec));
                }
            }
        }
        
        if (Global.getSector() != null && Global.getSector().getPlayerStats() != null) {
            int current = ship.getHullSpec().getOrdnancePoints(Global.getSector().getPlayerStats());
            int original = Global.getSettings().getHullSpec(ship.getHullSpec().getHullId())
                    .getOrdnancePoints(Global.getSector().getPlayerStats());
            int expected = (int) Math.floor(ship.getMutableStats().getDynamic().getMod(Codex.ORDNANCE_POINT_MOD).computeEffective(original));
            if (current != expected) {
                MutableShipHullSpecAPI spec = ConcordDynamicInstanceAssembly.getHullSpecCopy(ship.getHullSpec());
                spec.setOrdnancePoints(expected);
                spec.applyToShip(ship);
            }
        }
        
        for (TrackedHullmodEffect effect : removeEffects) {
            effect.onRemove(ship);
        }
        for (TrackedHullmodEffect effect : installEffects) {
            effect.onInstall(ship);
        }
    }

    @Override
    public void addPostDescriptionSection(TooltipMakerAPI tooltip, HullSize hullSize, ShipAPI ship, float width,
            boolean isForModSpec) {
        if (ship == null || isForModSpec) return;
        
        tooltip.addPara(ship.getFleetMemberId(), 2f);
        
        tooltip.addSpacer(6f);
        tooltip.addSectionHeading("Installed Hullmods", Alignment.MID, 0);
        tooltip.addSpacer(6f);
        
        for (String hullmod : ship.getVariant().getHullMods()) {
            Color c = Color.WHITE;
            HullModSpecAPI spec = Global.getSettings().getHullModSpec(hullmod);
            if (spec.isHiddenEverywhere()) {
                c = Color.GRAY;
            }
            if (trackedHullmods.containsKey(hullmod)) {
                c = Color.BLUE;
            }
            tooltip.addPara(spec.getDisplayName(), c, 2f);
        }
    }
}
