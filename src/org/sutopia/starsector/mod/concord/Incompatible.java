package org.sutopia.starsector.mod.concord;

import java.awt.Color;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipVariantAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.loading.HullModSpecAPI;

public class Incompatible extends BaseHullMod {
	public static final String HULLMOD_UNKNOWN = "An unknown hullmod"; // shouldn't happen
	public static volatile String HULLMOD_ONE = "";
	public static volatile String HULLMOD_TWO = "";
	
	@Override
	public void applyEffectsAfterShipCreation(ShipAPI ship, String id) {
		ShipVariantAPI variant = ship.getVariant();
		if (!variant.hasHullMod(HULLMOD_ONE)) {
			variant.removeMod(spec.getId());
			variant.addMod(HULLMOD_TWO); // Does this work?
		}
	}
	
	@Override
	public String getDescriptionParam(int index, HullSize hullSize, ShipAPI ship) {
		HullModSpecAPI spec;
		switch (index) {
			case 0:
				spec = Global.getSettings().getHullModSpec(HULLMOD_ONE);
				return spec == null ? HULLMOD_UNKNOWN : spec.getDisplayName();
			case 1:
				spec = Global.getSettings().getHullModSpec(HULLMOD_TWO);
				return spec == null ? HULLMOD_UNKNOWN : spec.getDisplayName();
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
