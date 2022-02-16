package org.sutopia.starsector.mod.concord.adv;

import java.util.Set;

import com.fs.starfarer.api.combat.HullModEffect;
import com.fs.starfarer.api.combat.HullModFleetEffect;
import com.fs.starfarer.api.combat.ShipAPI.HullSize;
import com.fs.starfarer.api.loading.HullModSpecAPI;

@Deprecated
public final class SpecUtil {
    public static HullModSpecAPI cloneSpec(final HullModSpecAPI spec) {
        return new HullModSpecAPI() {

            @Override
            public HullModEffect getEffect() {
                return spec.getEffect();
            }

            @Override
            public HullModFleetEffect getFleetEffect() {
                return spec.getFleetEffect();
            }

            @Override
            public boolean isAlwaysUnlocked() {
                return spec.isAlwaysUnlocked();
            }

            @Override
            public boolean isHidden() {
                return spec.isHidden();
            }

            @Override
            public boolean isHiddenEverywhere() {
                return spec.isHiddenEverywhere();
            }

            @Override
            public void setHidden(boolean isHidden) {
                spec.setHidden(isHidden);
                
            }

            @Override
            public void setHiddenEverywhere(boolean isHiddenEverywhere) {
                spec.setHiddenEverywhere(isHiddenEverywhere);
            }

            @Override
            public void setAlwaysUnlocked(boolean isStarting) {
                spec.setAlwaysUnlocked(isStarting);
            }

            @Override
            public String getEffectClass() {
                return spec.getEffectClass();
            }

            @Override
            public void setEffectClass(String effectClass) {
                spec.setEffectClass(effectClass);
                
            }

            @Override
            public String getDisplayName() {
                return spec.getDisplayName();
            }

            @Override
            public void setDisplayName(String displayName) {
                spec.setDisplayName(displayName);
            }

            @Override
            public String getId() {
                return spec.getId();
            }

            @Override
            public void setId(String id) {
                spec.setId(id);
            }

            @Override
            public String getDescriptionFormat() {
                return spec.getDescriptionFormat();
            }

            @Override
            public void setDescriptionFormat(String descriptionFormat) {
                spec.setDescriptionFormat(descriptionFormat);
            }

            @Override
            public int getFrigateCost() {
                return spec.getFrigateCost();
            }

            @Override
            public void setFrigateCost(int frigateCost) {
                spec.setFrigateCost(frigateCost);
            }

            @Override
            public int getDestroyerCost() {
                return spec.getDestroyerCost();
            }

            @Override
            public void setDestroyerCost(int destroyerCost) {
                spec.setDestroyerCost(destroyerCost);
            }

            @Override
            public int getCruiserCost() {
                return spec.getCruiserCost();
            }

            @Override
            public void setCruiserCost(int cruiserCost) {
                spec.setCruiserCost(cruiserCost);
            }

            @Override
            public int getCapitalCost() {
                return spec.getCapitalCost();
            }

            @Override
            public void setCapitalCost(int capitalCost) {
                spec.setCapitalCost(capitalCost);
            }

            @Override
            public int getTier() {
                return spec.getTier();
            }

            @Override
            public void setTier(int tier) {
                spec.setTier(tier);
            }

            @Override
            public String getSpriteName() {
                return spec.getSpriteName();
            }

            @Override
            public void setSpriteName(String spriteName) {
                spec.setSpriteName(spriteName);
            }

            @Override
            public int getCostFor(HullSize size) {
                return spec.getCostFor(size);
            }

            @Override
            public Set<String> getTags() {
                return spec.getTags();
            }

            @Override
            public void addTag(String tag) {
                spec.addTag(tag);
            }

            @Override
            public boolean hasTag(String tag) {
                return spec.hasTag(tag);
            }

            @Override
            public float getBaseValue() {
                return spec.getBaseValue();
            }

            @Override
            public void setBaseValue(float baseValue) {
                spec.setBaseValue(baseValue);
            }

            @Override
            public float getRarity() {
                return spec.getRarity();
            }

            @Override
            public void setRarity(float rarity) {
                spec.setRarity(rarity);
            }

            @Override
            public String getDescription(HullSize size) {
                return spec.getDescription(size);
            }

            @Override
            public String getManufacturer() {
                return spec.getManufacturer();
            }

            @Override
            public Set<String> getUITags() {
                return spec.getUITags();
            }

            @Override
            public void addUITag(String tag) {
                spec.addUITag(tag);
            }

            @Override
            public boolean hasUITag(String tag) {
                return spec.hasUITag(tag);
            }

            @Override
            public void setManufacturer(String manufacturer) {
                spec.setManufacturer(manufacturer);
            }
        };
    }
}
