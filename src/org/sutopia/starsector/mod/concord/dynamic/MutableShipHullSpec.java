package org.sutopia.starsector.mod.concord.dynamic;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.lwjgl.util.vector.Vector2f;

import com.fs.starfarer.api.characters.MutableCharacterStatsAPI;
import com.fs.starfarer.api.combat.ShieldAPI.ShieldType;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.combat.ShipHullSpecAPI;
import com.fs.starfarer.api.loading.WeaponSlotAPI;

public class MutableShipHullSpec implements ShipHullSpecAPI {
    
    private final ShipHullSpecAPI original;

    public MutableShipHullSpec(ShipHullSpecAPI source) {
        this.original = source;
    }

    public ShieldSpecAPI getShieldSpec() {
        return original.getShieldSpec();
    }

    public ShieldType getDefenseType() {
        return original.getDefenseType();
    }

    public String getHullId() {
        return original.getHullId();
    }

    public String getHullName() {
        return original.getHullName();
    }

    public EnumSet<ShipTypeHints> getHints() {
        return original.getHints();
    }

    public float getNoCRLossTime() {
        return original.getNoCRLossTime();
    }

    public float getCRToDeploy() {
        return original.getCRToDeploy();
    }

    public float getCRLossPerSecond() {
        return original.getCRLossPerSecond();
    }

    public float getBaseValue() {
        return original.getBaseValue();
    }

    public int getOrdnancePoints(MutableCharacterStatsAPI stats) {
        return original.getOrdnancePoints(stats);
    }

    public HullSize getHullSize() {
        return original.getHullSize();
    }

    public float getHitpoints() {
        return original.getHitpoints();
    }

    public float getArmorRating() {
        return original.getArmorRating();
    }

    public float getFluxCapacity() {
        return original.getFluxCapacity();
    }

    public float getFluxDissipation() {
        return original.getFluxDissipation();
    }

    public ShieldType getShieldType() {
        return original.getShieldType();
    }

    public List<WeaponSlotAPI> getAllWeaponSlotsCopy() {
        return original.getAllWeaponSlotsCopy();
    }

    public String getSpriteName() {
        return original.getSpriteName();
    }

    public boolean isCompatibleWithBase() {
        return original.isCompatibleWithBase();
    }

    public String getBaseHullId() {
        return original.getBaseHullId();
    }

    public float getBaseShieldFluxPerDamageAbsorbed() {
        return original.getBaseShieldFluxPerDamageAbsorbed();
    }

    public String getHullNameWithDashClass() {
        return original.getHullNameWithDashClass();
    }

    public boolean hasHullName() {
        return original.hasHullName();
    }

    public float getBreakProb() {
        return original.getBreakProb();
    }

    public float getMinPieces() {
        return original.getMinPieces();
    }

    public float getMaxPieces() {
        return original.getMaxPieces();
    }

    public int getFighterBays() {
        return original.getFighterBays();
    }

    public float getMinCrew() {
        return original.getMinCrew();
    }

    public float getMaxCrew() {
        return original.getMaxCrew();
    }

    public float getCargo() {
        return original.getCargo();
    }

    public float getFuel() {
        return original.getFuel();
    }

    public float getFuelPerLY() {
        return original.getFuelPerLY();
    }

    public boolean isDHull() {
        return original.isDHull();
    }

    public boolean isDefaultDHull() {
        return original.isDefaultDHull();
    }

    public void setDParentHullId(String dParentHullId) {
        original.setDParentHullId(dParentHullId);
    }

    public String getDParentHullId() {
        return original.getDParentHullId();
    }

    public ShipHullSpecAPI getDParentHull() {
        return original.getDParentHull();
    }

    public ShipHullSpecAPI getBaseHull() {
        return original.getBaseHull();
    }

    public List<String> getBuiltInWings() {
        return original.getBuiltInWings();
    }

    public boolean isBuiltInWing(int index) {
        return original.isBuiltInWing(index);
    }

    public String getDesignation() {
        return original.getDesignation();
    }

    public boolean hasDesignation() {
        return original.hasDesignation();
    }

    public boolean isRestoreToBase() {
        return original.isRestoreToBase();
    }

    public void setRestoreToBase(boolean restoreToBase) {
        original.setRestoreToBase(restoreToBase);
    }

    public Vector2f getModuleAnchor() {
        return original.getModuleAnchor();
    }

    public void setModuleAnchor(Vector2f moduleAnchor) {
        original.setModuleAnchor(moduleAnchor);
    }

    public void setCompatibleWithBase(boolean compatibleWithBase) {
        original.setCompatibleWithBase(compatibleWithBase);
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

    public String getNameWithDesignationWithDashClass() {
        return original.getNameWithDesignationWithDashClass();
    }

    public String getDescriptionId() {
        return original.getDescriptionId();
    }

    public boolean isBaseHull() {
        return original.isBaseHull();
    }

    public void setManufacturer(String manufacturer) {
        original.setManufacturer(manufacturer);
    }

    public String getManufacturer() {
        return original.getManufacturer();
    }

    public int getFleetPoints() {
        return original.getFleetPoints();
    }

    public List<String> getBuiltInMods() {
        return original.getBuiltInMods();
    }

    public WeaponSlotAPI getWeaponSlotAPI(String slotId) {
        return original.getWeaponSlotAPI(slotId);
    }

    public String getDescriptionPrefix() {
        return original.getDescriptionPrefix();
    }

    public boolean isBuiltInMod(String modId) {
        return original.isBuiltInMod(modId);
    }

    public void addBuiltInMod(String modId) {
        original.addBuiltInMod(modId);
    }

    public boolean isCivilianNonCarrier() {
        return original.isCivilianNonCarrier();
    }

    public void setHullName(String hullName) {
        original.setHullName(hullName);
    }

    public void setDesignation(String designation) {
        original.setDesignation(designation);
    }

    public boolean isPhase() {
        return original.isPhase();
    }

    public String getShipFilePath() {
        return original.getShipFilePath();
    }

    public String getTravelDriveId() {
        return original.getTravelDriveId();
    }

    public void setTravelDriveId(String travelDriveId) {
        original.setTravelDriveId(travelDriveId);
    }

    public EngineSpecAPI getEngineSpec() {
        return original.getEngineSpec();
    }

    public float getSuppliesToRecover() {
        return original.getSuppliesToRecover();
    }

    public void setSuppliesToRecover(float suppliesToRecover) {
        original.setSuppliesToRecover(suppliesToRecover);
    }

    public float getSuppliesPerMonth() {
        return original.getSuppliesPerMonth();
    }

    public void setSuppliesPerMonth(float suppliesPerMonth) {
        original.setSuppliesPerMonth(suppliesPerMonth);
    }

    public void setRepairPercentPerDay(float repairPercentPerDay) {
        original.setRepairPercentPerDay(repairPercentPerDay);
    }

    public void setCRToDeploy(float crToDeploy) {
        original.setCRToDeploy(crToDeploy);
    }

    public float getNoCRLossSeconds() {
        return original.getNoCRLossSeconds();
    }

    public void setNoCRLossSeconds(float noCRLossSeconds) {
        original.setNoCRLossSeconds(noCRLossSeconds);
    }

    public void setCRLossPerSecond(float crLossPerSecond) {
        original.setCRLossPerSecond(crLossPerSecond);
    }

    public HashMap<String, String> getBuiltInWeapons() {
        return original.getBuiltInWeapons();
    }

    public boolean isBuiltIn(String slotId) {
        return original.isBuiltIn(slotId);
    }

    public void addBuiltInWeapon(String slotId, String weaponId) {
        original.addBuiltInWeapon(slotId, weaponId);
    }

    public String getShipDefenseId() {
        return original.getShipDefenseId();
    }

    public void setShipDefenseId(String shipDefenseId) {
        original.setShipDefenseId(shipDefenseId);
    }

    public String getShipSystemId() {
        return original.getShipSystemId();
    }

    public void setShipSystemId(String shipSystemId) {
        original.setShipSystemId(shipSystemId);
    }

}
