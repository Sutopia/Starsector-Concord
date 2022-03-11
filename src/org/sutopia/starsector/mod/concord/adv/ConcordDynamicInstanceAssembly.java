package org.sutopia.starsector.mod.concord.adv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.sutopia.starsector.mod.concord.blackops.Marshal;
import org.sutopia.starsector.mod.concord.dynamic.MutableShipHullSpecAPI;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.ShipHullSpecAPI;
import com.fs.starfarer.api.combat.ShipVariantAPI;
import com.fs.starfarer.api.combat.ShipHullSpecAPI.ShipTypeHints;
import com.fs.starfarer.api.loading.FighterWingSpecAPI;
import com.fs.starfarer.api.loading.VariantSource;
import com.fs.starfarer.api.loading.WeaponGroupSpec;
import com.fs.starfarer.loading.specs.FighterWingSpec;
import com.fs.starfarer.loading.specs.HullVariantSpec;

public final class ConcordDynamicInstanceAssembly {

    private static List<FighterWingSpecAPI> loadedFighterSpecs;
    private static HashSet<String> loadedFighterSpecIds;
    public static final HashMap<String, String> INJECTED_FIGHTER_TO_VANILLA = new HashMap<>();
    public static final HashSet<String> INJECTED_HULL_SPECS = new HashSet<>();
    
    static 
    {
        loadedFighterSpecs = Global.getSettings().getAllFighterWingSpecs();
        loadedFighterSpecIds = new HashSet<>();
        for (FighterWingSpecAPI spec : loadedFighterSpecs) {
            loadedFighterSpecIds.add(spec.getId());
        }
    }
    
    /**
     * Returns the fighter wing specs that are loaded from files
     */
    public static List<FighterWingSpecAPI> getOriginalFighterWingSpecs() {
        return new ArrayList<FighterWingSpecAPI>(loadedFighterSpecs);
    }
    
    /**
     * Returns true if this is a fighter wing loaded from files
     */
    public static boolean isOriginalFighterWingSpec(String wingId) {
        return loadedFighterSpecIds.contains(wingId);
    }
    
    /**
     * Get a copy of an existing fighter wing spec, used for injecting fighter spec dynamically into the game
     */
    public static FighterWingSpec getFighterSpecCopy(String id) {
        FighterWingSpecAPI specAPI =  Global.getSettings().getFighterWingSpec(id);
        if (specAPI == null || !(specAPI instanceof FighterWingSpec)) {
            return null;
        }
        FighterWingSpec spec = (FighterWingSpec) specAPI;
        FighterWingSpec result = new FighterWingSpec();
        for (String tag : result.getTags()) {
            result.addTag(tag);
        }
        result.setAttackPositionOffset(spec.getAttackPositionOffset());
        result.setAttackRunRange(spec.getAttackRunRange());
        result.setBaseValue(spec.getBaseValue());
        result.setFleetPoints(spec.getFleetPoints());
        result.setFormation(spec.getFormation());
        result.setId(spec.getId());
        result.setNumFighters(spec.getNumFighters());
        result.setOpCost(spec.getOpCost(null));
        result.setRange(spec.getRange());
        result.setRarity(spec.getRarity());
        result.setRefitTime(spec.getRefitTime());
        result.setRole(spec.getRole());
        result.setRoleDesc(spec.getRoleDesc());
        result.setTier(spec.getTier());
        result.setVariantId(spec.getVariantId());
        return result;
    }
    
    /**
     * Inject fighter wing spec on the fly and automatically add all necessary tags to prevent the wing from dropping / showing in codex
     * <p> It is highly recommended you do this onApplicationLoad() to prevent corrupted save
     */
    public static void injectFighterSpecAsHidden(String id, FighterWingSpec spec) {
        spec.addTag("restricted");
        spec.addTag("no_drop");
        spec.addTag("no_sell");
        
        injectFighterSpec(id, spec);
    }
    
    /**
     * Inject fighter wing spec on the fly
     * <p> It is highly recommended you do this onApplicationLoad() to prevent corrupted save
     * <p> You're on your own if you modified the Id; Concord otherwise will revert wing back to base to prevent save crash
     */
    public static void injectFighterSpec(String id, FighterWingSpec spec) {
        String specId = spec.getId();
        if (id != specId) {
            if (loadedFighterSpecIds.contains(specId)) {
                INJECTED_FIGHTER_TO_VANILLA.put(id, specId);
            } else if (INJECTED_FIGHTER_TO_VANILLA.containsKey(specId)) {
                INJECTED_FIGHTER_TO_VANILLA.put(id, INJECTED_FIGHTER_TO_VANILLA.get(specId));
            }
            spec.setId(id);
        }
        Marshal.addFighterSpec(id, spec);
    }
    
    public static MutableShipHullSpecAPI getHullSpecCopy(String id) {
        return new MutableShipHullSpecAPI(id);
    }
    
    public static MutableShipHullSpecAPI getHullSpecCopy(ShipHullSpecAPI spec) {
        return new MutableShipHullSpecAPI(spec);
    }
    
    /**
     * Inject hull spec (skin) on the fly and automatically add all necessary tags to prevent the spec from showing in codex
     * <p> It is highly recommended you do this onApplicationLoad() to prevent corrupted save
     */
    public static void injectShipSpecAsHidden(String id, MutableShipHullSpecAPI spec) {
        spec.getHints().add(ShipTypeHints.HIDE_IN_CODEX);
        spec.addTag("no_drop");
        spec.addTag("no_bp_drop");
        injectShipSpec(id, spec);
    }
    
    /**
     * Inject hull spec (skin) on the fly
     * <p> It is highly recommended you do this onApplicationLoad() to prevent corrupted save
     */
    public static void injectShipSpec(String id, MutableShipHullSpecAPI spec) {
        Marshal.addShipHullSpec(id, spec.getInstance());
    }
    
    /**
     * Creates a hull variant spec from an existing ship variant.
     * Used for putting new stock specs (for fighter / station).
     * @param id You need to provide the id of the variant spec when creating a copy
     */
    public static HullVariantSpec getVariantCopy(String id, ShipVariantAPI spec, boolean keepSMod) throws Throwable {
        HullVariantSpec specCopy = Marshal.getEmptyShipVariant(id, spec.getHullSpec());
        specCopy.setSource(VariantSource.STOCK);
        
        if (keepSMod) {
            for (String hullmod : spec.getSMods()) {
                specCopy.addPermaMod(hullmod, true);
            }
        }
        
        for (String hullmod : spec.getPermaMods()) {
            if (specCopy.getHullMods().contains(hullmod) || spec.getSMods().contains(hullmod)) {
                continue;
            }
            specCopy.addPermaMod(hullmod, false);
        }
        
        for (String hullmod : spec.getNonBuiltInHullmods()) {
            specCopy.addMod(hullmod);
        }
        
        for (String slot : spec.getFittedWeaponSlots()) {
            specCopy.addWeapon(slot, spec.getWeaponId(slot));
        }
        
        for (int i = 0; i < spec.getFittedWings().size(); i++) {
            specCopy.setWingId(i, spec.getFittedWings().get(i));
        }
        
        for (String module : spec.getModuleSlots()) {
            specCopy.setModuleVariant(module, spec.getModuleVariant(module));
        }
        
        specCopy.setNumFluxCapacitors(spec.getNumFluxCapacitors());
        specCopy.setNumFluxVents(spec.getNumFluxVents());
        
        for (WeaponGroupSpec group : spec.getWeaponGroups()) {
            specCopy.addWeaponGroup(group.clone());
        }
        
        return specCopy;
    }
    
    /**
     * Inject ship variant spec (stock variant) on the fly and set necessary field to prevent the spec from showing in codex
     * <p> It is highly recommended you do this onApplicationLoad() to prevent corrupted save
     */
    public static void injectShipVariantAsHidden(HullVariantSpec spec) {
        spec.setGoalVariant(false);
        
        injectShipVariant(spec);
    }
    
    /**
     * Inject ship variant spec (stock variant) on the fly
     * <p> It is highly recommended you do this onApplicationLoad() to prevent corrupted save
     */
    public static void injectShipVariant(HullVariantSpec spec) {
        Marshal.addShipVariant(spec);
    }
}
