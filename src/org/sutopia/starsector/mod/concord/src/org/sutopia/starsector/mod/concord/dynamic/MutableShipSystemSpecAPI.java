package org.sutopia.starsector.mod.concord.dynamic;

import java.awt.Color;
import java.util.EnumSet;
import java.util.Set;

import org.json.JSONException;
import org.json.JSONObject;

import com.fs.starfarer.api.combat.DamageType;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipSystemAIScript;
import com.fs.starfarer.api.combat.ShipSystemSpecAPI;
import com.fs.starfarer.api.combat.WeaponAPI.WeaponType;
import com.fs.starfarer.api.plugins.ShipSystemStatsScript;

public abstract class MutableShipSystemSpecAPI extends com.fs.starfarer.loading.specs.oO0O {
    
    protected ShipSystemSpecAPI original;
    
    public MutableShipSystemSpecAPI(ShipSystemSpecAPI source) throws JSONException {
        super(source.getSpecJson());
        this.original = source;
    }
    
    public String getOriginalId() {
        return original.getId();
    }
    
    public String getIconSpriteName() {
        return original.getIconSpriteName();
    }

    public boolean isCanUseWhileRightClickSystemOn() {
        return original.isCanUseWhileRightClickSystemOn();
    }

    public void setCanUseWhileRightClickSystemOn(boolean canUseWhileRightClickSystemOn) {
        original.setCanUseWhileRightClickSystemOn(canUseWhileRightClickSystemOn);
    }

    public float getRange(MutableShipStatsAPI stats) {
        return original.getRange(stats);
    }

    public boolean isPhaseCloak() {
        return original.isPhaseCloak();
    }

    public void setPhaseCloak(boolean isPhaseCloak) {
        original.setPhaseCloak(isPhaseCloak);
    }

    public float getCooldown(MutableShipStatsAPI stats) {
        return original.getCooldown(stats);
    }

    public float getRegen(MutableShipStatsAPI stats) {
        return original.getRegen(stats);
    }

    public int getMaxUses(MutableShipStatsAPI stats) {
        return original.getMaxUses(stats);
    }

    public boolean isRunScriptWhilePaused() {
        return original.isRunScriptWhilePaused();
    }

    public boolean isRunScriptWhileIdle() {
        return original.isRunScriptWhileIdle();
    }

    public boolean isBlockActionsWhileChargingDown() {
        return original.isBlockActionsWhileChargingDown();
    }

    public float getPhaseChargedownVulnerabilityFraction() {
        return original.getPhaseChargedownVulnerabilityFraction();
    }

    public void setPhaseChargedownVulnerabilityFraction(float phaseChargedownVulnerabilityFraction) {
        original.setPhaseChargedownVulnerabilityFraction(phaseChargedownVulnerabilityFraction);
    }

    public float getCrPerUse() {
        return original.getCrPerUse();
    }

    public void setCrPerUse(float crPerUse) {
        original.setCrPerUse(crPerUse);
    }

    public boolean isRenderCopyDuringTeleport() {
        return original.isRenderCopyDuringTeleport();
    }

    public boolean isVulnerableChargeup() {
        return original.isVulnerableChargeup();
    }

    public boolean isVulnerableChargedown() {
        return original.isVulnerableChargedown();
    }

    public boolean isFadeActivationSoundOnChargedown() {
        return original.isFadeActivationSoundOnChargedown();
    }

    public JSONObject getSpecJson() {
        return original.getSpecJson();
    }

    public boolean isEngineActivateHiddenNozzles() {
        return original.isEngineActivateHiddenNozzles();
    }

    public float getEngineGlowMaxBlend() {
        return original.getEngineGlowMaxBlend();
    }

    public float getShipAlpha() {
        return original.getShipAlpha();
    }

    public float getFilterGain() {
        return original.getFilterGain();
    }

    public float getFilterGainLF() {
        return original.getFilterGainLF();
    }

    public float getFilterGainHF() {
        return original.getFilterGainHF();
    }

    public String getImpactSound() {
        return original.getImpactSound();
    }

    public float getThreatRange(MutableShipStatsAPI stats) {
        return original.getThreatRange(stats);
    }

    public float getThreatAmount() {
        return original.getThreatAmount();
    }

    public float getThreatArc() {
        return original.getThreatArc();
    }

    public float getThreatAngle() {
        return original.getThreatAngle();
    }

    public float getEmpDamage() {
        return original.getEmpDamage();
    }

    public float getDamage() {
        return original.getDamage();
    }

    public DamageType getDamageType() {
        return original.getDamageType();
    }

    public Color getEffectColor1() {
        return original.getEffectColor1();
    }

    public Color getEffectColor2() {
        return original.getEffectColor2();
    }

    public boolean isAllowFreeRoam() {
        return original.isAllowFreeRoam();
    }

    public float getLaunchDelay() {
        return original.getLaunchDelay();
    }

    public int getMaxDrones() {
        return original.getMaxDrones();
    }

    public float getLaunchSpeed() {
        return original.getLaunchSpeed();
    }

    public String getDroneVariant() {
        return original.getDroneVariant();
    }

    public float getJitterMinRange() {
        return original.getJitterMinRange();
    }

    public Color getJitterUnderEffectColor() {
        return original.getJitterUnderEffectColor();
    }

    public int getJitterUnderCopies() {
        return original.getJitterUnderCopies();
    }

    public float getJitterUnderMinRange() {
        return original.getJitterUnderMinRange();
    }

    public float getJitterUnderRange() {
        return original.getJitterUnderRange();
    }

    public float getJitterUnderRangeRadiusFraction() {
        return original.getJitterUnderRangeRadiusFraction();
    }

    public float getJitterRangeRadiusFraction() {
        return original.getJitterRangeRadiusFraction();
    }

    public float getRandomRange() {
        return original.getRandomRange();
    }

    public int getJitterCopies() {
        return original.getJitterCopies();
    }

    public float getJitterRange() {
        return original.getJitterRange();
    }

    public Color getJitterEffectColor() {
        return original.getJitterEffectColor();
    }

    public Color getWeaponGlowColor() {
        return original.getWeaponGlowColor();
    }

    public String getLoopSound() {
        return original.getLoopSound();
    }

    public String getDeactivateSound() {
        return original.getDeactivateSound();
    }

    public boolean isAlwaysAccelerate() {
        return original.isAlwaysAccelerate();
    }

    public String getStatsScriptClassName() {
        return original.getStatsScriptClassName();
    }

    public ShipSystemStatsScript getStatsScript() {
        return original.getStatsScript();
    }

    public ShipSystemAIScript getAIScript() {
        return original.getAIScript();
    }

    public String getAIScriptClassName() {
        return original.getAIScriptClassName();
    }

    public Color getShieldRingColor() {
        return original.getShieldRingColor();
    }

    public Color getShieldInnerColor() {
        return original.getShieldInnerColor();
    }

    public float getShieldThicknessMult() {
        return original.getShieldThicknessMult();
    }

    public float getShieldFluctuationMult() {
        return original.getShieldFluctuationMult();
    }

    public boolean isClampTurnRateAfter() {
        return original.isClampTurnRateAfter();
    }

    public boolean isClampMaxSpeedAfter() {
        return original.isClampMaxSpeedAfter();
    }

    public void setIconSpriteName(String iconSpriteName) {
        original.setIconSpriteName(iconSpriteName);
    }

    public float getMinFractionToReload() {
        return original.getMinFractionToReload();
    }

    public EnumSet<WeaponType> getWeaponTypes() {
        return original.getWeaponTypes();
    }

    public float getFlameoutOnImpactChance() {
        return original.getFlameoutOnImpactChance();
    }

    public boolean isTriggersExtraEngines() {
        return original.isTriggersExtraEngines();
    }

    public void setTriggersExtraEngines(boolean triggersEngines) {
        original.setTriggersExtraEngines(triggersEngines);
    }

    public Color getEngineGlowContrailColor() {
        return original.getEngineGlowContrailColor();
    }

    public boolean isHardDissipationAllowed() {
        return original.isHardDissipationAllowed();
    }

    public void setHardDissipationAllowed(boolean allowHardDissipation) {
        original.setHardDissipationAllowed(allowHardDissipation);
    }

    public boolean isVentingAllowed() {
        return original.isVentingAllowed();
    }

    public void setVentingAllowed(boolean ventingAllowed) {
        original.setVentingAllowed(ventingAllowed);
    }

    public boolean generatesHardFlux() {
        return original.generatesHardFlux();
    }

    public void setGeneratesHardFlux(boolean generatesHardFlux) {
        original.setGeneratesHardFlux(generatesHardFlux);
    }

    public void setToggle(boolean toggle) {
        original.setToggle(toggle);
    }

    public void setDissipationAllowed(boolean dissipationAllowed) {
        original.setDissipationAllowed(dissipationAllowed);
    }

    public boolean isDissipationAllowed() {
        return original.isDissipationAllowed();
    }

    public Color getEngineGlowColor() {
        return original.getEngineGlowColor();
    }

    public float getEngineGlowLengthMult() {
        return original.getEngineGlowLengthMult();
    }

    public float getEngineGlowWidthMult() {
        return original.getEngineGlowWidthMult();
    }

    public float getEngineGlowGlowMult() {
        return original.getEngineGlowGlowMult();
    }

    public void setRegen(float regen) {
        original.setRegen(regen);
    }

    public float getIn() {
        return original.getIn();
    }

    public void setIn(float in) {
        original.setIn(in);
    }

    public float getActive() {
        return original.getActive();
    }

    public void setActive(float active) {
        original.setActive(active);
    }

    public float getOut() {
        return original.getOut();
    }

    public void setOut(float out) {
        original.setOut(out);
    }

    public void setCooldown(float cooldown) {
        original.setCooldown(cooldown);
    }

    public boolean isToggle() {
        return original.isToggle();
    }

    public boolean isFiringAllowed() {
        return original.isFiringAllowed();
    }

    public void setFiringAllowed(boolean firingAllowed) {
        original.setFiringAllowed(firingAllowed);
    }

    public String getUseSound() {
        return original.getUseSound();
    }

    public void setUseSound(String useSound) {
        original.setUseSound(useSound);
    }

    public String getOutOfUsesSound() {
        return original.getOutOfUsesSound();
    }

    public void setOutOfUsesSound(String outOfAmmoSound) {
        original.setOutOfUsesSound(outOfAmmoSound);
    }

    public String getId() {
        return original.getId();
    }

    public void setId(String id) {
        original.setId(id);
    }

    public String getName() {
        return original.getName();
    }

    public void setName(String name) {
        original.setName(name);
    }

    public String getWeaponId() {
        return original.getWeaponId();
    }

    public void setWeaponId(String weaponId) {
        original.setWeaponId(weaponId);
    }

    public float getFluxPerSecond() {
        return original.getFluxPerSecond();
    }

    public void setFluxPerSecond(float fluxPerSecond) {
        original.setFluxPerSecond(fluxPerSecond);
    }

    public float getFluxPerUse() {
        return original.getFluxPerUse();
    }

    public void setFluxPerUse(float fluxPerUse) {
        original.setFluxPerUse(fluxPerUse);
    }

    public void setMaxUses(int maxUses) {
        original.setMaxUses(maxUses);
    }

    public boolean isTurningAllowed() {
        return original.isTurningAllowed();
    }

    public void setTurningAllowed(boolean turnAllowed) {
        original.setTurningAllowed(turnAllowed);
    }

    public boolean isStrafeAllowed() {
        return original.isStrafeAllowed();
    }

    public void setStrafeAllowed(boolean strafeAllowed) {
        original.setStrafeAllowed(strafeAllowed);
    }

    public boolean isShieldAllowed() {
        return original.isShieldAllowed();
    }

    public void setShieldAllowed(boolean shieldAllowed) {
        original.setShieldAllowed(shieldAllowed);
    }

    public boolean isAccelerateAllowed() {
        return original.isAccelerateAllowed();
    }

    public void setAccelerateAllowed(boolean accelerateAllowed) {
        original.setAccelerateAllowed(accelerateAllowed);
    }

    public float getFluxPerSecondBaseRate() {
        return original.getFluxPerSecondBaseRate();
    }

    public void setFluxPerSecondBaseRate(float fluxPerSecondBaseRate) {
        original.setFluxPerSecondBaseRate(fluxPerSecondBaseRate);
    }

    public float getFluxPerSecondBaseCap() {
        return original.getFluxPerSecondBaseCap();
    }

    public void setFluxPerSecondBaseCap(float fluxPerSecondBaseCap) {
        original.setFluxPerSecondBaseCap(fluxPerSecondBaseCap);
    }

    public float getFluxPerUseBaseRate() {
        return original.getFluxPerUseBaseRate();
    }

    public void setFluxPerUseBaseRate(float fluxPerUseBaseRate) {
        original.setFluxPerUseBaseRate(fluxPerUseBaseRate);
    }

    public float getFluxPerUseBaseCap() {
        return original.getFluxPerUseBaseCap();
    }

    public void setFluxPerUseBaseCap(float fluxPerUseBaseCap) {
        original.setFluxPerUseBaseCap(fluxPerUseBaseCap);
    }

    public boolean isCanNotCauseOverload() {
        return original.isCanNotCauseOverload();
    }

    public void setCanNotCauseOverload(boolean canNotCauseOverload) {
        original.setCanNotCauseOverload(canNotCauseOverload);
    }

    public boolean isRequiresZeroFluxBoost() {
        return original.isRequiresZeroFluxBoost();
    }

    public void setRequiresZeroFluxBoost(boolean requiresZeroFluxBoost) {
        original.setRequiresZeroFluxBoost(requiresZeroFluxBoost);
    }

    public void addTag(String tag) {
        original.addTag(tag);
    }

    public Set<String> getTags() {
        return original.getTags();
    }

    public boolean hasTag(String tag) {
        return original.hasTag(tag);
    }

}
