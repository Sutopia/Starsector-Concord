package org.sutopia.starsector.mod.concord;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipVariantAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.loading.HullModSpecAPI;

public final class Incompatible extends BaseHullMod {
    public static final String HULLMOD_UNKNOWN = "An unknown hullmod"; // shouldn't happen
    private static final HashMap<String, List<String>> INCOMPATIBLE_MAP = new HashMap<>();

    public static void addIncompatible(String shipHullVariantId, String vanillaHullmodId, String modHullmodId) {
        if (Global.getSettings().getHullModSpec(vanillaHullmodId) == null
                || Global.getSettings().getHullModSpec(modHullmodId) == null) {
            throw new IllegalArgumentException("Invalid hullmod ID");
        }

        List<String> dataList = INCOMPATIBLE_MAP.get(shipHullVariantId);
        if (dataList == null) {
            dataList = new ArrayList<>();
            dataList.add(0, vanillaHullmodId);
            INCOMPATIBLE_MAP.put(shipHullVariantId, dataList);
        }
        if (!dataList.contains(modHullmodId)) {
            dataList.add(modHullmodId);
        }
    }

    private static HullModSpecAPI getVanilla(ShipVariantAPI variant) {
        List<String> dataList = INCOMPATIBLE_MAP.get(variant.getHullVariantId());
        if (dataList != null && dataList.size() > 0) {
            return Global.getSettings().getHullModSpec(dataList.get(0));
        }
        return null;
    }

    private static List<String> getIncompatible(ShipVariantAPI variant, String id) {
        List<String> result = new ArrayList<>();

        List<String> dataList = INCOMPATIBLE_MAP.get(variant.getHullVariantId());
        if (dataList == null || dataList.size() == 0) {
            variant.removeMod(id);
            INCOMPATIBLE_MAP.remove(variant.getHullVariantId());
            return result;
        }

        HullModSpecAPI vanillaHullMod = Global.getSettings().getHullModSpec(dataList.get(0));
        if (vanillaHullMod == null) {
            variant.removeMod(id);
            INCOMPATIBLE_MAP.remove(variant.getHullVariantId());
            return result;
        }

        for (int i = 1; i < dataList.size(); i++) {
            String incompatible = dataList.get(i);
            if (variant.hasHullMod(incompatible)) {
                result.add(incompatible);
            }
        }

        return result;
    }

    @Override
    public void applyEffectsBeforeShipCreation(HullSize hullSize, MutableShipStatsAPI stats, String id) {
        ShipVariantAPI variant = stats.getVariant();
        HullModSpecAPI newMod = Global.getSettings().getHullModSpec(spec.getId());
        HullModSpecAPI originalMod = getVanilla(variant);
        if (originalMod != null) {
            newMod.setDisplayName("(Incompatible) " + originalMod.getDisplayName());
            newMod.setSpriteName(originalMod.getSpriteName());
            newMod.setFrigateCost(Math.max(newMod.getFrigateCost(), originalMod.getFrigateCost()));
            newMod.setDestroyerCost(Math.max(newMod.getDestroyerCost(), originalMod.getDestroyerCost()));
            newMod.setCruiserCost(Math.max(newMod.getCruiserCost(), originalMod.getCruiserCost()));
            newMod.setCapitalCost(Math.max(newMod.getCapitalCost(), originalMod.getCapitalCost()));
            newMod.setDescriptionFormat(Global.getSettings().getString(Codex.CONCORD_STRING_CAT, "incompatible_reason"));
        }
    }

    @Override
    public void applyEffectsAfterShipCreation(ShipAPI ship, String id) {
        ShipVariantAPI variant = ship.getVariant();

        List<String> incompatibleList = getIncompatible(variant, id);
        if (incompatibleList.size() == 0) {
            variant.removeMod(spec.getId());
            List<String> dataList = INCOMPATIBLE_MAP.remove(variant.getHullVariantId());
            if (dataList != null && dataList.size() > 0) {
                HullModSpecAPI vanillaHullMod = Global.getSettings().getHullModSpec(dataList.get(0));
                if (vanillaHullMod != null) {
                    variant.addMod(vanillaHullMod.getId());
                }
            }
        }
    }

    @Override
    public String getDescriptionParam(int index, HullSize hullSize, ShipAPI ship) {
        ShipVariantAPI variant = ship.getVariant();
        List<String> incompatibleList = getIncompatible(variant, spec.getId());
        HullModSpecAPI vanillaHullMod = getVanilla(variant);

        switch (index) {
        case 0:
            return vanillaHullMod == null ? HULLMOD_UNKNOWN : vanillaHullMod.getDisplayName();
        case 1:
            String result = "";
            for (String hullmodId : incompatibleList) {
                HullModSpecAPI spec = Global.getSettings().getHullModSpec(hullmodId);
                result += (spec == null) ? HULLMOD_UNKNOWN : spec.getDisplayName();
                result += "\n";
            }
            return result;
        }
        return null;
    }

    @Override
    public Color getBorderColor() {
        return Color.red;
    }

    @Override
    public Color getNameColor() {
        return Color.red;
    }
}
