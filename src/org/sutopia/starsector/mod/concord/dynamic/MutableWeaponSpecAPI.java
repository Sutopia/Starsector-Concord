package org.sutopia.starsector.mod.concord.dynamic;

import java.awt.Color;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.lwjgl.util.vector.Vector2f;
import org.sutopia.starsector.mod.concord.MutableSpecAPI;

import com.fs.starfarer.api.characters.MutableCharacterStatsAPI;
import com.fs.starfarer.api.combat.DamageType;
import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.WeaponAPI.AIHints;
import com.fs.starfarer.api.combat.WeaponAPI.DerivedWeaponStatsAPI;
import com.fs.starfarer.api.combat.WeaponAPI.WeaponSize;
import com.fs.starfarer.api.combat.WeaponAPI.WeaponType;
import com.fs.starfarer.api.loading.WeaponSpecAPI;

public abstract class MutableWeaponSpecAPI extends MutableSpecAPI<WeaponSpecAPI> implements WeaponSpecAPI {
    
    private final String originalId;
    
    public MutableWeaponSpecAPI(WeaponSpecAPI source, String id) {
        super(source);
        originalId = id;
    }
    
    public String getOriginalId() {
        return originalId;
    }

    public float getOrdnancePointCost(MutableCharacterStatsAPI stats) {
        return original.getOrdnancePointCost(stats);
    }

    public EnumSet<AIHints> getAIHints() {
        return original.getAIHints();
    }

    public WeaponType getType() {
        return original.getType();
    }

    public float getAmmoPerSecond() {
        return original.getAmmoPerSecond();
    }

    public int getTier() {
        return original.getTier();
    }

    public float getBaseValue() {
        return original.getBaseValue();
    }

    public boolean usesAmmo() {
        return original.usesAmmo();
    }

    public int getMaxAmmo() {
        return original.getMaxAmmo();
    }

    public String getWeaponId() {
        return original.getWeaponId();
    }

    public WeaponSize getSize() {
        return original.getSize();
    }

    public String getWeaponName() {
        return original.getWeaponName();
    }

    public int getBurstSize() {
        return original.getBurstSize();
    }

    public Set<String> getTags() {
        return original.getTags();
    }

    public void addTag(String tag) {
        original.addTag(tag);
    }

    public boolean hasTag(String tag) {
        return original.hasTag(tag);
    }

    public float getRarity() {
        return original.getRarity();
    }

    public void setRarity(float rarity) {
        original.setRarity(rarity);
    }

    public float getOrdnancePointCost(MutableCharacterStatsAPI stats, MutableShipStatsAPI shipStats) {
        return original.getOrdnancePointCost(stats, shipStats);
    }

    public DerivedWeaponStatsAPI getDerivedStats() {
        return original.getDerivedStats();
    }

    public List<Vector2f> getHardpointFireOffsets() {
        return original.getHardpointFireOffsets();
    }

    public List<Float> getHardpointAngleOffsets() {
        return original.getHardpointAngleOffsets();
    }

    public List<Vector2f> getTurretFireOffsets() {
        return original.getTurretFireOffsets();
    }

    public List<Float> getTurretAngleOffsets() {
        return original.getTurretAngleOffsets();
    }

    public List<Vector2f> getHiddenFireOffsets() {
        return original.getHiddenFireOffsets();
    }

    public List<Float> getHiddenAngleOffsets() {
        return original.getHiddenAngleOffsets();
    }

    public String getHardpointSpriteName() {
        return original.getHardpointSpriteName();
    }

    public String getTurretSpriteName() {
        return original.getTurretSpriteName();
    }

    public String getHardpointUnderSpriteName() {
        return original.getHardpointUnderSpriteName();
    }

    public String getTurretUnderSpriteName() {
        return original.getTurretUnderSpriteName();
    }

    public String getManufacturer() {
        return original.getManufacturer();
    }

    public void setManufacturer(String manufacturer) {
        original.setManufacturer(manufacturer);
    }

    public String getAutofitCategory() {
        return original.getAutofitCategory();
    }

    public List<String> getAutofitCategoriesInPriorityOrder() {
        return original.getAutofitCategoriesInPriorityOrder();
    }

    public String getWeaponGroupTag() {
        return original.getWeaponGroupTag();
    }

    public void setWeaponGroupTag(String weaponGroupTag) {
        original.setWeaponGroupTag(weaponGroupTag);
    }

    public boolean isBeam() {
        return original.isBeam();
    }

    public String getPrimaryRoleStr() {
        return original.getPrimaryRoleStr();
    }

    public void setPrimaryRoleStr(String primaryRoleStr) {
        original.setPrimaryRoleStr(primaryRoleStr);
    }

    public String getSpeedStr() {
        return original.getSpeedStr();
    }

    public void setSpeedStr(String speedStr) {
        original.setSpeedStr(speedStr);
    }

    public String getTrackingStr() {
        return original.getTrackingStr();
    }

    public void setTrackingStr(String trackingStr) {
        original.setTrackingStr(trackingStr);
    }

    public String getTurnRateStr() {
        return original.getTurnRateStr();
    }

    public void setTurnRateStr(String turnRateStr) {
        original.setTurnRateStr(turnRateStr);
    }

    public String getAccuracyStr() {
        return original.getAccuracyStr();
    }

    public void setAccuracyStr(String accuracyStr) {
        original.setAccuracyStr(accuracyStr);
    }

    public String getCustomPrimary() {
        return original.getCustomPrimary();
    }

    public void setCustomPrimary(String customPrimary) {
        original.setCustomPrimary(customPrimary);
    }

    public String getCustomPrimaryHL() {
        return original.getCustomPrimaryHL();
    }

    public void setCustomPrimaryHL(String customPrimaryHL) {
        original.setCustomPrimaryHL(customPrimaryHL);
    }

    public String getCustomAncillary() {
        return original.getCustomAncillary();
    }

    public void setCustomAncillary(String customAncillary) {
        original.setCustomAncillary(customAncillary);
    }

    public String getCustomAncillaryHL() {
        return original.getCustomAncillaryHL();
    }

    public void setCustomAncillaryHL(String customAncillaryHL) {
        original.setCustomAncillaryHL(customAncillaryHL);
    }

    public boolean isNoDPSInTooltip() {
        return original.isNoDPSInTooltip();
    }

    public void setNoDPSInTooltip(boolean noDPSInTooltip) {
        original.setNoDPSInTooltip(noDPSInTooltip);
    }

    public Color getGlowColor() {
        return original.getGlowColor();
    }

    public boolean isInterruptibleBurst() {
        return original.isInterruptibleBurst();
    }

    public boolean isNoImpactSounds() {
        return original.isNoImpactSounds();
    }

    public void setNoImpactSounds(boolean noImpactSounds) {
        original.setNoImpactSounds(noImpactSounds);
    }

    public DamageType getDamageType() {
        return original.getDamageType();
    }

    public boolean isRenderAboveAllWeapons() {
        return original.isRenderAboveAllWeapons();
    }

    public void setRenderAboveAllWeapons(boolean renderAboveAllWeapons) {
        original.setRenderAboveAllWeapons(renderAboveAllWeapons);
    }

    public boolean isNoShieldImpactSounds() {
        return original.isNoShieldImpactSounds();
    }

    public void setNoShieldImpactSounds(boolean noShieldImpactSounds) {
        original.setNoShieldImpactSounds(noShieldImpactSounds);
    }

    public boolean isNoNonShieldImpactSounds() {
        return original.isNoNonShieldImpactSounds();
    }

    public void setNoNonShieldImpactSounds(boolean noNonShieldImpactSounds) {
        original.setNoNonShieldImpactSounds(noNonShieldImpactSounds);
    }

    public float getMinSpread() {
        return original.getMinSpread();
    }

    public float getMaxSpread() {
        return original.getMaxSpread();
    }

    public float getSpreadDecayRate() {
        return original.getSpreadDecayRate();
    }

    public float getSpreadBuildup() {
        return original.getSpreadBuildup();
    }

    public void setMinSpread(float minSpread) {
        original.setMinSpread(minSpread);
    }

    public void setMaxSpread(float maxSpread) {
        original.setMaxSpread(maxSpread);
    }

    public void setSpreadDecayRate(float spreadDecayRate) {
        original.setSpreadDecayRate(spreadDecayRate);
    }

    public void setSpreadBuildup(float spreadBuildup) {
        original.setSpreadBuildup(spreadBuildup);
    }

    public float getBurstDuration() {
        return original.getBurstDuration();
    }

    public float getAutofireAccBonus() {
        return original.getAutofireAccBonus();
    }

    public void setAutofireAccBonus(float autofireAccBonus) {
        original.setAutofireAccBonus(autofireAccBonus);
    }

    public Object getProjectileSpec() {
        return original.getProjectileSpec();
    }

    public float getBeamChargeupTime() {
        return original.getBeamChargeupTime();
    }

    public float getBeamChargedownTime() {
        return original.getBeamChargedownTime();
    }

    public boolean isUnaffectedByProjectileSpeedBonuses() {
        return original.isUnaffectedByProjectileSpeedBonuses();
    }

    public void setUnaffectedByProjectileSpeedBonuses(boolean unaffectedByProjectileSpeedBonuses) {
        original.setUnaffectedByProjectileSpeedBonuses(unaffectedByProjectileSpeedBonuses);
    }

    public float getChargeTime() {
        return original.getChargeTime();
    }

    public WeaponType getMountType() {
        return original.getMountType();
    }

    public void setMountType(WeaponType mountType) {
        original.setMountType(mountType);
    }

    public float getExtraArcForAI() {
        return original.getExtraArcForAI();
    }

    public void setExtraArcForAI(float extraArcForAI) {
        original.setExtraArcForAI(extraArcForAI);
    }

    public void setWeaponName(String weaponName) {
        original.setWeaponName(weaponName);
    }

    public float getMaxRange() {
        return original.getMaxRange();
    }

    public void setMaxRange(float maxRange) {
        original.setMaxRange(maxRange);
    }

    public void setOrdnancePointCost(float armamentCapacity) {
        original.setOrdnancePointCost(armamentCapacity);
    }

}
