package org.sutopia.starsector.mod.concord;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipVariantAPI;
import com.fs.starfarer.api.loading.HullModSpecAPI;
import com.fs.starfarer.api.impl.campaign.ids.HullMods;

public abstract class MutualExclusiveHullMod extends BaseHullMod {
	
	public static final String INCOMPATIBLE_REPLACE_HULLMOD = "concord_incompatible";
	
	private static boolean isSkipModCheck() {
		return Global.getSettings().getBoolean("concord_check_mod");
	}
	
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
			switch (loadTag) {
				case Codex.TARGETING_UNIT:
					result.add(Codex.VANILLA_EXCLUSIVE_PREFIX + HullMods.INTEGRATED_TARGETING_UNIT);
					result.add(Codex.VANILLA_EXCLUSIVE_PREFIX + HullMods.DEDICATED_TARGETING_CORE);
					result.add(Codex.VANILLA_EXCLUSIVE_PREFIX + HullMods.ADVANCED_TARGETING_CORE);
					result.add(Codex.VANILLA_EXCLUSIVE_PREFIX + "supercomputer");
					break;
				case Codex.PHASE_MAJOR_MOD:
					result.add(Codex.VANILLA_EXCLUSIVE_PREFIX + HullMods.PHASE_ANCHOR);
					result.add(Codex.VANILLA_EXCLUSIVE_PREFIX + HullMods.ADAPTIVE_COILS);
					break;
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
		
		// Deal with conflict injection
		if (ship.getVariant().hasHullMod(spec.getId())) {
			for (String hullModIdWithVex: getVanillaMutualExclusiveTags()) {
				String vanillaHullmodId = hullModIdWithVex.substring(Codex.VANILLA_EXCLUSIVE_PREFIX.length());
				ShipVariantAPI variant = ship.getVariant();
				if (variant.getNonBuiltInHullmods().size() > 0 && variant.getNonBuiltInHullmods().contains(vanillaHullmodId)) {
					variant.removeMod(vanillaHullmodId);
					HullModSpecAPI originalMod = Global.getSettings().getHullModSpec(vanillaHullmodId);
					HullModSpecAPI newMod = Global.getSettings().getHullModSpec(INCOMPATIBLE_REPLACE_HULLMOD);
					newMod.setDisplayName(originalMod.getDisplayName() + " (Incompatible)");
					newMod.setSpriteName(originalMod.getSpriteName());
					variant.addMod(INCOMPATIBLE_REPLACE_HULLMOD);
				}
			}
		}
		
		for (String hullModIdWithVex: getVanillaMutualExclusiveTags()) {
			if (ship.getVariant().hasHullMod(hullModIdWithVex.substring(Codex.VANILLA_EXCLUSIVE_PREFIX.length()))) {
				return false;
			}
		}
		
		if (isSkipModCheck()) {
			for (String tag: getMutualExclusiveTags()) {
				if (spec.hasTag(tag) && shipHasOtherModInCategory(ship, spec.getId(), tag)) {
					return false;
				}
			}
		} else {
			for (String tag: getCustomMutualExclusiveTags()) {
				if (spec.hasTag(tag) && shipHasOtherModInCategory(ship, spec.getId(), tag)) {
					return false;
				}
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
	
	/**
	 * Returns the all of hullmods (in id) that are incompatible with current hullmod.
	 */
	public List<String> getFullIncompatibleIdList() {
		ArrayList<String> incompatibleHullMods = new ArrayList<String>();
		
		for (String hullModIdWithVex: getVanillaMutualExclusiveTags()) {
			HullModSpecAPI hullMod = Global.getSettings().getHullModSpec(hullModIdWithVex.substring(Codex.VANILLA_EXCLUSIVE_PREFIX.length()));
			if (hullMod != null) {
				incompatibleHullMods.add(hullMod.getId());
			}
		}
		
		for (HullModSpecAPI hullMod: Global.getSettings().getAllHullModSpecs()){
			if (hullMod.getId() == spec.getId()) {
				continue;
			}
			
			if (incompatibleHullMods.contains(hullMod.getId())) {
				continue;
			}
			
			for (String tag: getMutualExclusiveTags()) {
				if (spec.hasTag(tag) && hullMod.hasTag(tag)) {
					incompatibleHullMods.add(hullMod.getId());
					break;
				}
			}
		}
		return incompatibleHullMods;
	}
	
	/**
	 * Returns the all of hullmods (in display name) that are incompatible with current ship.
	 */
	public HashSet<String> getCurrentIncompatibleList(ShipAPI ship) {
		HashSet<String> incompatibleHullMods = new HashSet<String>();
		
		for (String hullmodId : ship.getVariant().getHullMods()) {
			HullModSpecAPI hullModSpec = Global.getSettings().getHullModSpec(hullmodId);
			String displayName = hullModSpec.getDisplayName();
			if (displayName == null || displayName == "") {
				continue;
			}
			
			if (getVanillaMutualExclusiveTags().contains(Codex.VANILLA_EXCLUSIVE_PREFIX + hullmodId)){
				incompatibleHullMods.add(displayName);
				continue;
			}
			
			for (String tags: hullModSpec.getTags()) {
				if (getMutualExclusiveTags().contains(tags)){
					incompatibleHullMods.add(displayName);
					break;
				}
			}
		}
		
		return incompatibleHullMods;
	}
	
	@Override
	public String getUnapplicableReason(ShipAPI ship) {
		if (ship == null) return null;
		
		String[] incompatibleHullMods = getCurrentIncompatibleList(ship).toArray(new String[0]);
		
		StringBuilder sb = new StringBuilder();
		for (int i=0; i<incompatibleHullMods.length; i++) {
			if (i != 0) {
				sb.append(", ");
			}
			sb.append(incompatibleHullMods[i]);
		}
		
		return "The following hullmods are incompatible with this hullmod:\n" + sb.toString();
	}
}
