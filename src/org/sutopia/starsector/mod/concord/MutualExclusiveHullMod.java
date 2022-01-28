package org.sutopia.starsector.mod.concord;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.loading.HullModSpecAPI;

public abstract class MutualExclusiveHullMod extends BaseHullMod {
	
	/** 
	 * Returns the set of mutual exclusive tags (Override for effect)
	 */
	public Set<String> getCustomMutualExclusiveTags() {
		return new HashSet<String>();
	}
	
	/** 
	 * Returns the set of mutual exclusive vanilla hullmod IDs
	 */
	public Set<String> getVanillaMutualExclusiveTags() {
		HashSet<String> result = new HashSet<String>();
		
		for (String loadTag: spec.getTags()){
			if (loadTag.startsWith(Codex.VANILLA_EXCLUSIVE_PREFIX)) {
				result.add(loadTag);
				/*String vanillaHullModId = loadTag.substring(Codex.VANILLA_EXCLUSIVE_PREFIX.length());
				for (HullModSpecAPI hullMod: Global.getSettings().getAllHullModSpecs()){
					if (hullMod.getId() == vanillaHullModId) {
						result.add(vanillaHullModId);
						break;
					}
				}*/
			}
		}
		
		return result;
	}
	
	public Set<String> getMutualExclusiveTags() {
		if (getCustomMutualExclusiveTags() == null) {
			return getVanillaMutualExclusiveTags();
		}
		HashSet<String> result = new HashSet<String>();
		result.addAll(getVanillaMutualExclusiveTags());
		result.addAll(getCustomMutualExclusiveTags());
		
		return result;
	}
	
	@Override
	public boolean isApplicableToShip(ShipAPI ship) {
		if (ship == null) return false;
		
		for (String hullModIdWithVex: getVanillaMutualExclusiveTags()) {
			if (ship.getVariant().hasHullMod(hullModIdWithVex.substring(Codex.VANILLA_EXCLUSIVE_PREFIX.length()))) {
				
				return false;
			}
		}
		
		for (String tag: getMutualExclusiveTags()) {
			if (spec.hasTag(tag) && shipHasOtherModInCategory(ship, spec.getId(), tag)) {
				return false;
			}
		}
		
		return true; 
	}
	
	/**
	 * Returns the list of hullmods (in display name) that are incompatible with current hullmod.
	 * <p>
	 * Pass in ship if you want to include hidden hullmods (Usually for built in / unique)
	 */
	
	public List<String> getIncompatibleList(ShipAPI ship) {
		ArrayList<String> incompatibleHullMods = new ArrayList<String>();
		
		for (String hullModIdWithVex: getVanillaMutualExclusiveTags()) {
			HullModSpecAPI hullMod = Global.getSettings().getHullModSpec(hullModIdWithVex.substring(Codex.VANILLA_EXCLUSIVE_PREFIX.length()));
			if (hullMod != null) {
				incompatibleHullMods.add(hullMod.getDisplayName());
			}
		}
		
		for (HullModSpecAPI hullMod: Global.getSettings().getAllHullModSpecs()){
			if (hullMod.getId() == spec.getId()) {
				continue;
			}
			
			if (incompatibleHullMods.contains(hullMod.getDisplayName())) {
				continue;
			}
			
			if (hullMod.isHiddenEverywhere()) {
				continue;
			}
			
			if (hullMod.isHidden() && ship != null && !ship.getVariant().hasHullMod(hullMod.getId())) {
				continue;
			}
			
			for (String tag: getMutualExclusiveTags()) {
				if (spec.hasTag(tag) && hullMod.hasTag(tag)) {
					incompatibleHullMods.add(hullMod.getDisplayName());
					break;
				}
			}
		}
		return incompatibleHullMods;
	}
	
	@Override
	public String getUnapplicableReason(ShipAPI ship) {
		if (ship == null) return null;
		
		List<String> incompatibleHullMods = getIncompatibleList(ship);
		
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<incompatibleHullMods.size(); i++) {
			if (i != 0) {
				sb.append(", ");
			}
			sb.append(incompatibleHullMods.get(i));
		}
		
		return "The following hullmods are incompatible with this hullmod:\n" + sb.toString();
	}
}
