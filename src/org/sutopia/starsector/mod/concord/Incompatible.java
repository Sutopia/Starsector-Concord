package org.sutopia.starsector.mod.concord;

import java.awt.Color;

import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;

public class Incompatible extends BaseHullMod {
	public static final String HULLMOD_UNKNOWN = "An anknown hullmod"; // shouldn't happen
	public static volatile String HULLMOD_ONE = HULLMOD_UNKNOWN;
	public static volatile String HULLMOD_TWO = HULLMOD_UNKNOWN;
	
	
	@Override
	public String getDescriptionParam(int index, HullSize hullSize, ShipAPI ship) {
		switch (index) {
			case 0:
				return HULLMOD_ONE;
			case 1:
				return HULLMOD_TWO;
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
