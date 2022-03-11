package org.sutopia.starsector.mod.concord.dynamic;

import com.fs.starfarer.api.combat.ShipSystemSpecAPI;
import com.fs.starfarer.api.loading.HullModSpecAPI;

public class ConcordUtil {

    public static void copySpec(ShipSystemSpecAPI from, ShipSystemSpecAPI to) {
        to.setName(from.getName());
        to.setId(from.getId());
        to.setActive(from.getActive());
        to.setCanNotCauseOverload(from.isCanNotCauseOverload());
        to.setCanUseWhileRightClickSystemOn(from.isCanUseWhileRightClickSystemOn());
        to.setCooldown(from.getCooldown(null));
        to.setCrPerUse(from.getCrPerUse());
        to.setDissipationAllowed(from.isDissipationAllowed());
        to.setFiringAllowed(from.isFiringAllowed());
        to.setFluxPerSecond(from.getFluxPerSecond());
        to.setFluxPerSecondBaseCap(from.getFluxPerSecondBaseCap());
        to.setFluxPerSecondBaseRate(from.getFluxPerSecondBaseRate());
        to.setFluxPerUse(from.getFluxPerUse());
        to.setFluxPerUseBaseCap(from.getFluxPerUseBaseCap());
        to.setFluxPerUseBaseRate(from.getFluxPerUseBaseRate());
        to.setGeneratesHardFlux(from.generatesHardFlux());
        to.setHardDissipationAllowed(from.isHardDissipationAllowed());
        to.setIconSpriteName(from.getIconSpriteName());
        to.setIn(from.getIn());
        to.setMaxUses(from.getMaxUses(null));
        to.setOut(from.getOut());
        to.setOutOfUsesSound(from.getOutOfUsesSound());
        to.setPhaseChargedownVulnerabilityFraction(from.getPhaseChargedownVulnerabilityFraction());
        to.setPhaseCloak(from.isPhaseCloak());
        to.setRegen(from.getRegen(null));
        to.setRequiresZeroFluxBoost(from.isRequiresZeroFluxBoost());
        to.setShieldAllowed(from.isShieldAllowed());
        to.setStrafeAllowed(from.isStrafeAllowed());
        to.setToggle(from.isToggle());
        to.setTriggersExtraEngines(from.isTriggersExtraEngines());
        to.setTurningAllowed(from.isTurningAllowed());
        to.setUseSound(from.getUseSound());
        to.setVentingAllowed(from.isVentingAllowed());
        to.setWeaponId(from.getWeaponId());
    }
    
    public static void copySpec(HullModSpecAPI from, HullModSpecAPI to) {
        for (String tag : from.getTags()) {
            if (!to.hasTag(tag)) {
                to.addTag(tag);
            }
        }

        for (String tag : from.getUITags()) {
            if (!to.hasUITag(tag)) {
                to.addUITag(tag);
            }
        }
        
        to.setAlwaysUnlocked(from.isAlwaysUnlocked());
        to.setBaseValue(from.getBaseValue());
        to.setCapitalCost(from.getCapitalCost());
        to.setCruiserCost(from.getCruiserCost());
        to.setDescriptionFormat(from.getDescriptionFormat());
        to.setDestroyerCost(from.getDestroyerCost());
        to.setDisplayName(from.getDisplayName());
        to.setFrigateCost(from.getFrigateCost());
        to.setHidden(from.isHidden());
        to.setHiddenEverywhere(from.isHiddenEverywhere());
        to.setManufacturer(from.getManufacturer());
        to.setRarity(from.getRarity());
        to.setSpriteName(from.getSpriteName());
        to.setTier(from.getTier());
        to.setId(from.getId());
    }
}
