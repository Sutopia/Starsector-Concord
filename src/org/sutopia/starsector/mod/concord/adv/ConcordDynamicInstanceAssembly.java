package org.sutopia.starsector.mod.concord.adv;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.sutopia.starsector.mod.concord.blackops.Marshal;
import org.sutopia.starsector.mod.concord.dynamic.MutableShipHullSpecAPI;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.ShipHullSpecAPI;
import com.fs.starfarer.api.combat.ShipHullSpecAPI.ShipTypeHints;
import com.fs.starfarer.api.loading.FighterWingSpecAPI;
import com.fs.starfarer.loading.specs.FighterWingSpec;

public final class ConcordDynamicInstanceAssembly {

    private static List<FighterWingSpecAPI> loadedFighterSpecs = null;
    private static HashSet<String> loadedFighterSpecIds = new HashSet<>();
    public static final HashMap<String, String> INJECTED_FIGHTER_TO_VANILLA = new HashMap<>();
    
    private static void ensurePopulated() {
        if (loadedFighterSpecs == null) {
            loadedFighterSpecs = Global.getSettings().getAllFighterWingSpecs();
            for (FighterWingSpecAPI spec : loadedFighterSpecs) {
                loadedFighterSpecIds.add(spec.getId());
            }
        }
    }
    
    /**
     * Returns the fighter wing specs that are loaded from files
     */
    public static List<FighterWingSpecAPI> getOriginalFighterWingSpecs() {
        ensurePopulated();
        return new ArrayList<FighterWingSpecAPI>(loadedFighterSpecs);
    }
    
    /**
     * Returns true if this is a fighter wing loaded from files
     */
    public static boolean isOriginalFighterWingSpec(String wingId) {
        ensurePopulated();
        return loadedFighterSpecIds.contains(wingId);
    }
    
    /**
     * Get a copy of an existing fighter wing spec, used for injecting fighter spec dynamically into the game
     */
    public static FighterWingSpec getFighterSpecCopy(String id) {
        ensurePopulated();
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
     */
    public static void injectFighterSpec(String id, FighterWingSpec spec) {
        if (id != spec.getId()) {
            if (loadedFighterSpecIds != null && loadedFighterSpecIds.contains(spec.getId())) {
                INJECTED_FIGHTER_TO_VANILLA.put(id, spec.getId());
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
        injectShipSpec(id, spec);
    }
    
    /**
     * Inject hull spec (skin) on the fly
     * <p> It is highly recommended you do this onApplicationLoad() to prevent corrupted save
     */
    public static void injectShipSpec(String id, MutableShipHullSpecAPI spec) {
        //spec.setHullId(id);
        Marshal.addShipHullSpec(id, spec.getInstance());
    }
}
