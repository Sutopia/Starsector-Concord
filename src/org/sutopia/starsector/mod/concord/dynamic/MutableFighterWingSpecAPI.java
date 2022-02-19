package org.sutopia.starsector.mod.concord.dynamic;

import java.util.List;
import java.util.Set;

import org.sutopia.starsector.mod.concord.MutableSpecAPI;

import com.fs.starfarer.api.combat.MutableShipStatsAPI;
import com.fs.starfarer.api.combat.ShipVariantAPI;
import com.fs.starfarer.api.loading.FighterWingSpecAPI;
import com.fs.starfarer.api.loading.FormationType;
import com.fs.starfarer.api.loading.WingRole;

public abstract class MutableFighterWingSpecAPI extends MutableSpecAPI<FighterWingSpecAPI> implements FighterWingSpecAPI {

    public MutableFighterWingSpecAPI(FighterWingSpecAPI source) {
        super(source);
    }
    
    public String getOriginalId() {
        return original.getId();
    }

    public boolean isBomber() {
        return original.isBomber();
    }

    public boolean isAssault() {
        return original.isAssault();
    }

    public boolean isSupport() {
        return original.isSupport();
    }

    public boolean isInterceptor() {
        return original.isInterceptor();
    }

    public boolean isRegularFighter() {
        return original.isRegularFighter();
    }

    public WingRole getRole() {
        return original.getRole();
    }

    public void setRole(WingRole role) {
        original.setRole(role);
    }

    public FormationType getFormation() {
        return original.getFormation();
    }

    public void setFormation(FormationType formation) {
        original.setFormation(formation);
    }

    public String getId() {
        return original.getId();
    }

    public void setId(String id) {
        original.setId(id);
    }

    public int getNumFighters() {
        return original.getNumFighters();
    }

    public void setNumFighters(int numFighters) {
        original.setNumFighters(numFighters);
    }

    public String getVariantId() {
        return original.getVariantId();
    }

    public void setVariantId(String variantId) {
        original.setVariantId(variantId);
    }

    public float getRefitTime() {
        return original.getRefitTime();
    }

    public void setRefitTime(float refitTime) {
        original.setRefitTime(refitTime);
    }

    public int getFleetPoints() {
        return original.getFleetPoints();
    }

    public void setFleetPoints(int fleetPoints) {
        original.setFleetPoints(fleetPoints);
    }

    public float getBaseValue() {
        return original.getBaseValue();
    }

    public void setBaseValue(float baseValue) {
        original.setBaseValue(baseValue);
    }

    public ShipVariantAPI getVariant() {
        return original.getVariant();
    }

    public float getAttackRunRange() {
        return original.getAttackRunRange();
    }

    public void setAttackRunRange(float attackRunRange) {
        original.setAttackRunRange(attackRunRange);
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

    public int getTier() {
        return original.getTier();
    }

    public void setTier(int tier) {
        original.setTier(tier);
    }

    public String getRoleDesc() {
        return original.getRoleDesc();
    }

    public void setRoleDesc(String roleDesc) {
        original.setRoleDesc(roleDesc);
    }

    public float getRarity() {
        return original.getRarity();
    }

    public void setRarity(float rarity) {
        original.setRarity(rarity);
    }

    public String getWingName() {
        return original.getWingName();
    }

    public String getAutofitCategory() {
        return original.getAutofitCategory();
    }

    public List<String> getAutofitCategoriesInPriorityOrder() {
        return original.getAutofitCategoriesInPriorityOrder();
    }

    public float getAttackPositionOffset() {
        return original.getAttackPositionOffset();
    }

    public void setAttackPositionOffset(float attackPositionOffset) {
        original.setAttackPositionOffset(attackPositionOffset);
    }

    public void setOpCost(float opCost) {
        original.setOpCost(opCost);
    }

    public float getOpCost(MutableShipStatsAPI shipStats) {
        return original.getOpCost(shipStats);
    }

    public void resetAutofitPriorityCategories() {
        original.resetAutofitPriorityCategories();
    }

}
