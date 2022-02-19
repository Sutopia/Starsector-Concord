package org.sutopia.starsector.mod.concord.dynamic;

import java.util.Set;

import org.sutopia.starsector.mod.concord.MutableSpecAPI;

import com.fs.starfarer.api.combat.HullModEffect;
import com.fs.starfarer.api.combat.HullModFleetEffect;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.loading.HullModSpecAPI;

public abstract class MutableHullModSpec extends MutableSpecAPI<HullModSpecAPI> implements HullModSpecAPI {

    public MutableHullModSpec(HullModSpecAPI source) {
        super(source);
    }
    
    public String getOriginalId() {
        return original.getId();
    }

    public HullModEffect getEffect() {
        return original.getEffect();
    }

    public HullModFleetEffect getFleetEffect() {
        return original.getFleetEffect();
    }

    public boolean isAlwaysUnlocked() {
        return original.isAlwaysUnlocked();
    }

    public boolean isHidden() {
        return original.isHidden();
    }

    public boolean isHiddenEverywhere() {
        return original.isHiddenEverywhere();
    }

    public void setHidden(boolean isHidden) {
        original.setHidden(isHidden);
    }

    public void setHiddenEverywhere(boolean isHiddenEverywhere) {
        original.setHiddenEverywhere(isHiddenEverywhere);
    }

    public void setAlwaysUnlocked(boolean isStarting) {
        original.setAlwaysUnlocked(isStarting);
    }

    public String getEffectClass() {
        return original.getEffectClass();
    }

    public void setEffectClass(String effectClass) {
        original.setEffectClass(effectClass);
    }

    public String getDisplayName() {
        return original.getDisplayName();
    }

    public void setDisplayName(String displayName) {
        original.setDisplayName(displayName);
    }

    public String getId() {
        return original.getId();
    }

    public void setId(String id) {
        original.setId(id);
    }

    public String getDescriptionFormat() {
        return original.getDescriptionFormat();
    }

    public void setDescriptionFormat(String descriptionFormat) {
        original.setDescriptionFormat(descriptionFormat);
    }

    public int getFrigateCost() {
        return original.getFrigateCost();
    }

    public void setFrigateCost(int frigateCost) {
        original.setFrigateCost(frigateCost);
    }

    public int getDestroyerCost() {
        return original.getDestroyerCost();
    }

    public void setDestroyerCost(int destroyerCost) {
        original.setDestroyerCost(destroyerCost);
    }

    public int getCruiserCost() {
        return original.getCruiserCost();
    }

    public void setCruiserCost(int cruiserCost) {
        original.setCruiserCost(cruiserCost);
    }

    public int getCapitalCost() {
        return original.getCapitalCost();
    }

    public void setCapitalCost(int capitalCost) {
        original.setCapitalCost(capitalCost);
    }

    public int getTier() {
        return original.getTier();
    }

    public void setTier(int tier) {
        original.setTier(tier);
    }

    public String getSpriteName() {
        return original.getSpriteName();
    }

    public void setSpriteName(String spriteName) {
        original.setSpriteName(spriteName);
    }

    public int getCostFor(HullSize size) {
        return original.getCostFor(size);
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

    public float getBaseValue() {
        return original.getBaseValue();
    }

    public void setBaseValue(float baseValue) {
        original.setBaseValue(baseValue);
    }

    public float getRarity() {
        return original.getRarity();
    }

    public void setRarity(float rarity) {
        original.setRarity(rarity);
    }

    public String getDescription(HullSize size) {
        return original.getDescription(size);
    }

    public String getManufacturer() {
        return original.getManufacturer();
    }

    public Set<String> getUITags() {
        return original.getUITags();
    }

    public void addUITag(String tag) {
        original.addUITag(tag);
    }

    public boolean hasUITag(String tag) {
        return original.hasUITag(tag);
    }

    public void setManufacturer(String manufacturer) {
        original.setManufacturer(manufacturer);
    }
}
