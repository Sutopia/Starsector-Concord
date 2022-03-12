package org.sutopia.starsector.mod.concord.dynamic;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.lwjgl.util.vector.Vector2f;
import org.sutopia.starsector.mod.concord.blackops.Pacifier;

import com.fs.starfarer.api.characters.MutableCharacterStatsAPI;
import com.fs.starfarer.api.combat.ShieldAPI.ShieldType;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.combat.ShipHullSpecAPI;
import com.fs.starfarer.api.loading.WeaponSlotAPI;

public class MutableShipHullSpecAPI implements ShipHullSpecAPI {

    private final Pacifier.HullSpec instance;
    
    public ShipHullSpecAPI getInstance() {
        return instance.getInstance();
    }
    
    public MutableShipHullSpecAPI(String id) {
        instance = Pacifier.HullSpec.getShipHullSpecClone(id);
    }
    
    public MutableShipHullSpecAPI(ShipHullSpecAPI spec) {
        instance = Pacifier.HullSpec.getShipHullSpecClone(spec);
    }

    public void addBuiltInMod(String arg0) {
        instance.addBuiltInMod(arg0);
    }

    public void addBuiltInWeapon(String arg0, String arg1) {
        instance.addBuiltInWeapon(arg0, arg1);
    }

    public void addBuiltInWing(String arg0) {
        instance.addBuiltInWing(arg0);
    }

    public void addTag(String arg0) {
        instance.addTag(arg0);
    }

    public void applyToShip(ShipAPI ship) {
        instance.applyToShip(ship);
    }

    public boolean equals(Object obj) {
        return instance.equals(obj);
    }

    public List<WeaponSlotAPI> getAllWeaponSlotsCopy() {
        return instance.getAllWeaponSlotsCopy();
    }

    public float getArmorRating() {
        return instance.getArmorRating();
    }

    public ShipHullSpecAPI getBaseHull() {
        return instance.getBaseHull();
    }

    public String getBaseHullId() {
        return instance.getBaseHullId();
    }

    public float getBaseShieldFluxPerDamageAbsorbed() {
        return instance.getBaseShieldFluxPerDamageAbsorbed();
    }

    public float getBaseValue() {
        return instance.getBaseValue();
    }

    public float getBreakProb() {
        return instance.getBreakProb();
    }

    public List<String> getBuiltInMods() {
        return instance.getBuiltInMods();
    }

    public HashMap<String, String> getBuiltInWeapons() {
        return instance.getBuiltInWeapons();
    }

    public List<String> getBuiltInWings() {
        return instance.getBuiltInWings();
    }

    public float getCRLossPerSecond() {
        return instance.getCRLossPerSecond();
    }

    public float getCRToDeploy() {
        return instance.getCRToDeploy();
    }

    public float getCargo() {
        return instance.getCargo();
    }

    public ShipHullSpecAPI getDParentHull() {
        return instance.getDParentHull();
    }

    public String getDParentHullId() {
        return instance.getDParentHullId();
    }

    public ShieldType getDefenseType() {
        return instance.getDefenseType();
    }

    public String getDescriptionId() {
        return instance.getDescriptionId();
    }

    public String getDescriptionPrefix() {
        return instance.getDescriptionPrefix();
    }

    public String getDesignation() {
        return instance.getDesignation();
    }

    public EngineSpecAPI getEngineSpec() {
        return instance.getEngineSpec();
    }

    public int getFighterBays() {
        return instance.getFighterBays();
    }

    public int getFleetPoints() {
        return instance.getFleetPoints();
    }

    public float getFluxCapacity() {
        return instance.getFluxCapacity();
    }

    public float getFluxDissipation() {
        return instance.getFluxDissipation();
    }

    public float getFuel() {
        return instance.getFuel();
    }

    public float getFuelPerLY() {
        return instance.getFuelPerLY();
    }

    public EnumSet<ShipTypeHints> getHints() {
        return instance.getHints();
    }

    public float getHitpoints() {
        return instance.getHitpoints();
    }

    public String getHullId() {
        return instance.getHullId();
    }

    public String getHullName() {
        return instance.getHullName();
    }

    public String getHullNameWithDashClass() {
        return instance.getHullNameWithDashClass();
    }

    public HullSize getHullSize() {
        return instance.getHullSize();
    }

    public String getManufacturer() {
        return instance.getManufacturer();
    }

    public float getMaxCrew() {
        return instance.getMaxCrew();
    }

    public float getMaxPieces() {
        return instance.getMaxPieces();
    }

    public float getMinCrew() {
        return instance.getMinCrew();
    }

    public float getMinPieces() {
        return instance.getMinPieces();
    }

    public Vector2f getModuleAnchor() {
        return instance.getModuleAnchor();
    }

    public String getNameWithDesignationWithDashClass() {
        return instance.getNameWithDesignationWithDashClass();
    }

    public float getNoCRLossSeconds() {
        return instance.getNoCRLossSeconds();
    }

    public float getNoCRLossTime() {
        return instance.getNoCRLossTime();
    }

    public int getOrdnancePoints(MutableCharacterStatsAPI arg0) {
        return instance.getOrdnancePoints(arg0);
    }

    public float getRarity() {
        return instance.getRarity();
    }

    public ShieldSpecAPI getShieldSpec() {
        return instance.getShieldSpec();
    }

    public ShieldType getShieldType() {
        return instance.getShieldType();
    }

    public String getShipDefenseId() {
        return instance.getShipDefenseId();
    }

    public String getShipFilePath() {
        return instance.getShipFilePath();
    }

    public String getShipSystemId() {
        return instance.getShipSystemId();
    }

    public String getSpriteName() {
        return instance.getSpriteName();
    }

    public float getSuppliesPerMonth() {
        return instance.getSuppliesPerMonth();
    }

    public float getSuppliesToRecover() {
        return instance.getSuppliesToRecover();
    }

    public Set<String> getTags() {
        return instance.getTags();
    }

    public String getTravelDriveId() {
        return instance.getTravelDriveId();
    }

    public WeaponSlotAPI getWeaponSlotAPI(String arg0) {
        return instance.getWeaponSlotAPI(arg0);
    }

    public boolean hasDesignation() {
        return instance.hasDesignation();
    }

    public boolean hasHullName() {
        return instance.hasHullName();
    }

    public boolean hasTag(String arg0) {
        return instance.hasTag(arg0);
    }

    public int hashCode() {
        return instance.hashCode();
    }

    public boolean isBaseHull() {
        return instance.isBaseHull();
    }

    public boolean isBuiltIn(String arg0) {
        return instance.isBuiltIn(arg0);
    }

    public boolean isBuiltInMod(String arg0) {
        return instance.isBuiltInMod(arg0);
    }

    public boolean isBuiltInWing(int arg0) {
        return instance.isBuiltInWing(arg0);
    }

    public boolean isCivilianNonCarrier() {
        return instance.isCivilianNonCarrier();
    }

    public boolean isCompatibleWithBase() {
        return instance.isCompatibleWithBase();
    }

    public boolean isDHull() {
        return instance.isDHull();
    }

    public boolean isDefaultDHull() {
        return instance.isDefaultDHull();
    }

    public boolean isPhase() {
        return instance.isPhase();
    }

    public boolean isRestoreToBase() {
        return instance.isRestoreToBase();
    }

    public void setCRLossPerSecond(float arg0) {
        instance.setCRLossPerSecond(arg0);
    }

    public void setCRToDeploy(float arg0) {
        instance.setCRToDeploy(arg0);
    }

    public void setCompatibleWithBase(boolean arg0) {
        instance.setCompatibleWithBase(arg0);
    }

    public void setDParentHullId(String arg0) {
        instance.setDParentHullId(arg0);
    }

    public void setDesignation(String arg0) {
        instance.setDesignation(arg0);
    }

    public void setFighterBays(int arg0) {
        instance.setFighterBays(arg0);
    }

    public void setHullName(String arg0) {
        instance.setHullName(arg0);
    }

    public void setManufacturer(String arg0) {
        instance.setManufacturer(arg0);
    }

    public void setModuleAnchor(Vector2f arg0) {
        instance.setModuleAnchor(arg0);
    }

    public void setNoCRLossSeconds(float arg0) {
        instance.setNoCRLossSeconds(arg0);
    }

    public void setRepairPercentPerDay(float arg0) {
        instance.setRepairPercentPerDay(arg0);
    }

    public void setRestoreToBase(boolean arg0) {
        instance.setRestoreToBase(arg0);
    }

    public void setShipDefenseId(String arg0) {
        instance.setShipDefenseId(arg0);
    }

    public void setShipSystemId(String arg0) {
        instance.setShipSystemId(arg0);
    }

    public void setSuppliesPerMonth(float arg0) {
        instance.setSuppliesPerMonth(arg0);
    }

    public void setSuppliesToRecover(float arg0) {
        instance.setSuppliesToRecover(arg0);
    }

    public void setTravelDriveId(String arg0) {
        instance.setTravelDriveId(arg0);
    }

    public String toString() {
        return instance.toString();
    }

    public void setDescriptionId(String arg0) {
        instance.setDescriptionId(arg0);
    }

    public void setDescriptionPrefix(String arg0) {
        instance.setDescriptionPrefix(arg0);
    }

    public void setOrdnancePoints(int arg0) {
        instance.setOrdnancePoints(arg0);
    }

    public void setHullSize(HullSize arg0) {
        instance.setHullSize(arg0);
    }

}
