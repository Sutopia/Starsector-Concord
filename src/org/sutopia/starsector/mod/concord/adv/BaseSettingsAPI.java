package org.sutopia.starsector.mod.concord.adv;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.lwjgl.util.vector.Vector2f;

import com.fs.starfarer.api.GameState;
import com.fs.starfarer.api.ModManagerAPI;
import com.fs.starfarer.api.SettingsAPI;
import com.fs.starfarer.api.SpriteId;
import com.fs.starfarer.api.campaign.CustomEntitySpecAPI;
import com.fs.starfarer.api.campaign.CustomUIPanelPlugin;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.campaign.LocationAPI;
import com.fs.starfarer.api.campaign.PlanetSpecAPI;
import com.fs.starfarer.api.campaign.SpecialItemSpecAPI;
import com.fs.starfarer.api.campaign.econ.CommoditySpecAPI;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.campaign.econ.SubmarketSpecAPI;
import com.fs.starfarer.api.characters.MarketConditionSpecAPI;
import com.fs.starfarer.api.characters.PersonAPI;
import com.fs.starfarer.api.characters.SkillSpecAPI;
import com.fs.starfarer.api.combat.CombatEntityAPI;
import com.fs.starfarer.api.combat.CombatReadinessPlugin;
import com.fs.starfarer.api.combat.ShipAIConfig;
import com.fs.starfarer.api.combat.ShipAIPlugin;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.combat.ShipHullSpecAPI;
import com.fs.starfarer.api.combat.ShipSystemSpecAPI;
import com.fs.starfarer.api.combat.ShipVariantAPI;
import com.fs.starfarer.api.fleet.FleetMemberAPI;
import com.fs.starfarer.api.graphics.SpriteAPI;
import com.fs.starfarer.api.loading.AbilitySpecAPI;
import com.fs.starfarer.api.loading.BarEventSpec;
import com.fs.starfarer.api.loading.Description;
import com.fs.starfarer.api.loading.Description.Type;
import com.fs.starfarer.api.loading.EventSpecAPI;
import com.fs.starfarer.api.loading.FighterWingSpecAPI;
import com.fs.starfarer.api.loading.HullModSpecAPI;
import com.fs.starfarer.api.loading.IndustrySpecAPI;
import com.fs.starfarer.api.loading.PersonMissionSpec;
import com.fs.starfarer.api.loading.RoleEntryAPI;
import com.fs.starfarer.api.loading.TerrainSpecAPI;
import com.fs.starfarer.api.loading.WeaponSpecAPI;
import com.fs.starfarer.api.plugins.LevelupPlugin;
import com.fs.starfarer.api.ui.ButtonAPI;
import com.fs.starfarer.api.ui.ButtonAPI.UICheckboxSize;
import com.fs.starfarer.api.ui.CustomPanelAPI;
import com.fs.starfarer.api.ui.LabelAPI;
import com.fs.starfarer.api.ui.TextFieldAPI;
import com.fs.starfarer.api.ui.TooltipMakerAPI;
import com.fs.starfarer.api.util.ListMap;

@SuppressWarnings("rawtypes")
public class BaseSettingsAPI implements SettingsAPI {
    private final SettingsAPI vanilla;

    public BaseSettingsAPI(SettingsAPI original) {
        vanilla = original;
    }

    @Override
    public int getBattleSize() {
        return vanilla.getBattleSize();
    }

    @Override
    public PersonAPI createPerson() {
        return vanilla.createPerson();
    }

    @Override
    public LabelAPI createLabel(String text, String font) {
        return vanilla.createLabel(text, font);
    }

    @Override
    public float getBonusXP(String key) {
        return vanilla.getBonusXP(key);
    }

    @Override
    public float getFloat(String key) {
        return vanilla.getFloat(key);
    }

    @Override
    public boolean getBoolean(String key) {
        return vanilla.getBoolean(key);
    }

    @Override
    public ClassLoader getScriptClassLoader() {
        return vanilla.getScriptClassLoader();
    }

    @Override
    public boolean isDevMode() {
        return vanilla.isDevMode();
    }

    @Override
    public void setDevMode(boolean devMode) {
        vanilla.setDevMode(devMode);
    }

    @Override
    public Color getColor(String id) {
        return vanilla.getColor(id);
    }

    @Override
    public Object getInstanceOfScript(String className) {
        return vanilla.getInstanceOfScript(className);
    }

    @Override
    public String getString(String category, String id) {
        return vanilla.getString(category, id);
    }

    @Override
    public SpriteAPI getSprite(String filename) {
        return vanilla.getSprite(filename);
    }

    @Override
    public SpriteAPI getSprite(String category, String key) {
        return vanilla.getSprite(category, key);
    }

    @Override
    public SpriteAPI getSprite(SpriteId id) {
        return vanilla.getSprite(id);
    }

    @Override
    public String getSpriteName(String category, String id) {
        return vanilla.getSpriteName(category, id);
    }

    @Override
    public InputStream openStream(String filename) throws IOException {
        return vanilla.openStream(filename);
    }

    @Override
    public String loadText(String filename) throws IOException {
        return vanilla.loadText(filename);
    }

    @Override
    public JSONObject loadJSON(String filename) throws IOException, JSONException {
        return vanilla.loadJSON(filename);
    }

    @Override
    public JSONArray loadCSV(String filename) throws IOException, JSONException {
        return vanilla.loadCSV(filename);
    }

    @Override
    public JSONArray getMergedSpreadsheetDataForMod(String idColumn, String path, String masterMod) throws IOException,
            JSONException {
        return vanilla.getMergedSpreadsheetDataForMod(idColumn, path, masterMod);
    }

    @Override
    public JSONObject getMergedJSONForMod(String path, String masterMod) throws IOException, JSONException {
        return vanilla.getMergedJSONForMod(path, masterMod);
    }

    @Override
    public float getScreenWidth() {
        return vanilla.getScreenWidth();
    }

    @Override
    public float getScreenHeight() {
        return vanilla.getScreenHeight();
    }

    @Override
    public float getScreenWidthPixels() {
        return vanilla.getScreenWidthPixels();
    }

    @Override
    public float getScreenHeightPixels() {
        return vanilla.getScreenHeightPixels();
    }

    @Override
    public Description getDescription(String id, Type type) {
        return vanilla.getDescription(id, type);
    }

    @Override
    public CombatReadinessPlugin getCRPlugin() {
        return vanilla.getCRPlugin();
    }

    @Override
    public int getCodeFor(String key) {
        return vanilla.getCodeFor(key);
    }

    @Override
    public WeaponSpecAPI getWeaponSpec(String weaponId) {
        return vanilla.getWeaponSpec(weaponId);
    }

    @Override
    public void loadTexture(String filename) throws IOException {
        vanilla.loadTexture(filename);
    }

    @Override
    public float getTargetingRadius(Vector2f from, CombatEntityAPI target, boolean considerShield) {
        return vanilla.getTargetingRadius(from, target, considerShield);
    }

    @Override
    public ShipVariantAPI getVariant(String variantId) {
        return vanilla.getVariant(variantId);
    }

    @Override
    public Object getPlugin(String id) {
        return vanilla.getPlugin(id);
    }

    @Override
    public List<String> getSortedSkillIds() {
        return vanilla.getSortedSkillIds();
    }

    @Override
    public SkillSpecAPI getSkillSpec(String skillId) {
        return vanilla.getSkillSpec(skillId);
    }

    @Override
    public String getString(String key) {
        return vanilla.getString(key);
    }

    @Override
    public AbilitySpecAPI getAbilitySpec(String abilityId) {
        return vanilla.getAbilitySpec(abilityId);
    }

    @Override
    public List<String> getSortedAbilityIds() {
        return vanilla.getSortedAbilityIds();
    }

    @Override
    public float getBaseTravelSpeed() {
        return vanilla.getBaseTravelSpeed();
    }

    @Override
    public float getSpeedPerBurnLevel() {
        return vanilla.getSpeedPerBurnLevel();
    }

    @Override
    public float getUnitsPerLightYear() {
        return vanilla.getUnitsPerLightYear();
    }

    @Override
    public int getMaxShipsInFleet() {
        return vanilla.getMaxShipsInFleet();
    }

    @Override
    public TerrainSpecAPI getTerrainSpec(String terrainId) {
        return vanilla.getTerrainSpec(terrainId);
    }

    @Override
    public EventSpecAPI getEventSpec(String eventId) {
        return vanilla.getEventSpec(eventId);
    }

    @Override
    public CustomEntitySpecAPI getCustomEntitySpec(String id) {
        return vanilla.getCustomEntitySpec(id);
    }

    @Override
    public GameState getCurrentState() {
        return vanilla.getCurrentState();
    }

    @Override
    public int getMaxSensorRange() {
        return vanilla.getMaxSensorRange();
    }

    @Override
    public int getMaxSensorRangeHyper() {
        return vanilla.getMaxSensorRangeHyper();
    }

    @Override
    public int getMaxSensorRange(LocationAPI loc) {
        return vanilla.getMaxSensorRange(loc);
    }

    @Override
    public List<String> getAllVariantIds() {
        return vanilla.getAllVariantIds();
    }

    @Override
    public List<String> getAptitudeIds() {
        return vanilla.getAptitudeIds();
    }

    @Override
    public List<String> getSkillIds() {
        return vanilla.getSkillIds();
    }

    @Override
    public LevelupPlugin getLevelupPlugin() {
        return vanilla.getLevelupPlugin();
    }

    @Override
    public String getVersionString() {
        return vanilla.getVersionString();
    }

    @Override
    public JSONObject loadJSON(String filename, String modId) throws IOException, JSONException {
        return vanilla.loadJSON(filename, modId);
    }

    @Override
    public JSONArray loadCSV(String filename, String modId) throws IOException, JSONException {
        return vanilla.loadCSV(filename, modId);
    }

    @Override
    public String loadText(String filename, String modId) throws IOException, JSONException {
        return vanilla.loadText(filename, modId);
    }

    @Override
    public ModManagerAPI getModManager() {
        return vanilla.getModManager();
    }

    @Override
    public float getBaseFleetSelectionRadius() {
        return vanilla.getBaseFleetSelectionRadius();
    }

    @Override
    public float getFleetSelectionRadiusPerUnitSize() {
        return vanilla.getFleetSelectionRadiusPerUnitSize();
    }

    @Override
    public float getMaxFleetSelectionRadius() {
        return vanilla.getMaxFleetSelectionRadius();
    }

    @Override
    public List<RoleEntryAPI> getEntriesForRole(String factionId, String role) {
        return vanilla.getEntriesForRole(factionId, role);
    }

    @Override
    public void addEntryForRole(String factionId, String role, String variantId, float weight) {
        vanilla.addEntryForRole(factionId, role, variantId, weight);
    }

    @Override
    public void removeEntryForRole(String factionId, String role, String variantId) {
        vanilla.removeEntryForRole(factionId, role, variantId);
    }

    @Override
    public List<RoleEntryAPI> getDefaultEntriesForRole(String role) {
        return vanilla.getDefaultEntriesForRole(role);
    }

    @Override
    public void addDefaultEntryForRole(String role, String variantId, float weight) {
        vanilla.addDefaultEntryForRole(role, variantId, weight);
    }

    @Override
    public void removeDefaultEntryForRole(String role, String variantId) {
        vanilla.removeDefaultEntryForRole(role, variantId);
    }

    @Override
    public void profilerBegin(String id) {
        vanilla.profilerBegin(id);
    }

    @Override
    public void profilerEnd() {
        vanilla.profilerEnd();
    }

    @Override
    public void profilerPrintResultsTree() {
        vanilla.profilerPrintResultsTree();
    }

    @Override
    public List<PlanetSpecAPI> getAllPlanetSpecs() {
        return vanilla.getAllPlanetSpecs();
    }

    @Override
    public Object getSpec(Class c, String id, boolean nullOnNotFound) {
        return vanilla.getSpec(c, id, nullOnNotFound);
    }

    @Override
    public void putSpec(Class c, String id, Object spec) {
        vanilla.putSpec(c, id, spec);
    }

    @Override
    public Collection<Object> getAllSpecs(Class c) {
        return vanilla.getAllSpecs(c);
    }

    @Override
    public String getRoman(int n) {
        return vanilla.getRoman(n);
    }

    @Override
    public void greekLetterReset() {
        vanilla.greekLetterReset();
    }

    @Override
    public String getNextCoolGreekLetter(Object context) {
        return vanilla.getNextCoolGreekLetter(context);
    }

    @Override
    public String getNextGreekLetter(Object context) {
        return vanilla.getNextGreekLetter(context);
    }

    @Override
    public MarketConditionSpecAPI getMarketConditionSpec(String conditionId) {
        return vanilla.getMarketConditionSpec(conditionId);
    }

    @Override
    public ShipAIPlugin createDefaultShipAI(ShipAPI ship, ShipAIConfig config) {
        return vanilla.createDefaultShipAI(ship, config);
    }

    @Override
    public HullModSpecAPI getHullModSpec(String modId) {
        return vanilla.getHullModSpec(modId);
    }

    @Override
    public FighterWingSpecAPI getFighterWingSpec(String wingId) {
        return vanilla.getFighterWingSpec(wingId);
    }

    @Override
    public List<HullModSpecAPI> getAllHullModSpecs() {
        return vanilla.getAllHullModSpecs();
    }

    @Override
    public List<FighterWingSpecAPI> getAllFighterWingSpecs() {
        return vanilla.getAllFighterWingSpecs();
    }

    @Override
    public List<WeaponSpecAPI> getAllWeaponSpecs() {
        return vanilla.getAllWeaponSpecs();
    }

    @Override
    public boolean isSoundEnabled() {
        return vanilla.isSoundEnabled();
    }

    @Override
    public boolean isInCampaignState() {
        return vanilla.isInCampaignState();
    }

    @Override
    public boolean isGeneratingNewGame() {
        return vanilla.isGeneratingNewGame();
    }

    @Override
    public float getAngleInDegreesFast(Vector2f v) {
        return vanilla.getAngleInDegreesFast(v);
    }

    @Override
    public float getAngleInDegreesFast(Vector2f from, Vector2f to) {
        return vanilla.getAngleInDegreesFast(from, to);
    }

    @Override
    public CommoditySpecAPI getCommoditySpec(String commodityId) {
        return vanilla.getCommoditySpec(commodityId);
    }

    @Override
    public ShipHullSpecAPI getHullSpec(String hullId) {
        return vanilla.getHullSpec(hullId);
    }

    @Override
    public int computeNumFighterBays(ShipVariantAPI variant) {
        return vanilla.computeNumFighterBays(variant);
    }

    @Override
    public boolean isInGame() {
        return vanilla.isInGame();
    }

    @Override
    public Object getNewPluginInstance(String id) {
        return vanilla.getNewPluginInstance(id);
    }

    @Override
    public String getControlStringForAbilitySlot(int index) {
        return vanilla.getControlStringForAbilitySlot(index);
    }

    @Override
    public String getControlStringForEnumName(String name) {
        return vanilla.getControlStringForEnumName(name);
    }

    @Override
    public boolean isNewPlayer() {
        return vanilla.isNewPlayer();
    }

    @Override
    public IndustrySpecAPI getIndustrySpec(String industryId) {
        return vanilla.getIndustrySpec(industryId);
    }

    @Override
    public List<CommoditySpecAPI> getAllCommoditySpecs() {
        return vanilla.getAllCommoditySpecs();
    }

    @Override
    public int getInt(String key) {
        return vanilla.getInt(key);
    }

    @Override
    public List<IndustrySpecAPI> getAllIndustrySpecs() {
        return vanilla.getAllIndustrySpecs();
    }

    @Override
    public SpecialItemSpecAPI getSpecialItemSpec(String itemId) {
        return vanilla.getSpecialItemSpec(itemId);
    }

    @Override
    public List<SpecialItemSpecAPI> getAllSpecialItemSpecs() {
        return vanilla.getAllSpecialItemSpecs();
    }

    @Override
    public List<ShipHullSpecAPI> getAllShipHullSpecs() {
        return vanilla.getAllShipHullSpecs();
    }

    @Override
    public SpriteAPI getSprite(String category, String id, boolean emptySpriteOnNotFound) {
        return vanilla.getSprite(category, id, emptySpriteOnNotFound);
    }

    @Override
    public ShipVariantAPI createEmptyVariant(String hullVariantId, ShipHullSpecAPI hullSpec) {
        return vanilla.createEmptyVariant(hullVariantId, hullSpec);
    }

    @Override
    public ListMap<String> getHullIdToVariantListMap() {
        return vanilla.getHullIdToVariantListMap();
    }

    @Override
    public String readTextFileFromCommon(String filename) throws IOException {
        return vanilla.readTextFileFromCommon(filename);
    }

    @Override
    public void writeTextFileToCommon(String filename, String data) throws IOException {
        vanilla.writeTextFileToCommon(filename, data);
    }

    @Override
    public boolean fileExistsInCommon(String filename) {
        return vanilla.fileExistsInCommon(filename);
    }

    @Override
    public void deleteTextFileFromCommon(String filename) {
        vanilla.deleteTextFileFromCommon(filename);
    }

    @Override
    public Color getBasePlayerColor() {
        return vanilla.getBasePlayerColor();
    }

    @Override
    public Color getDesignTypeColor(String designType) {
        return vanilla.getDesignTypeColor(designType);
    }

    @Override
    public boolean doesVariantExist(String variantId) {
        return vanilla.doesVariantExist(variantId);
    }

    @Override
    public void addCommodityInfoToTooltip(TooltipMakerAPI tooltip, float initPad, CommoditySpecAPI spec, int max,
            boolean withText, boolean withSell, boolean withBuy) {
        vanilla.addCommodityInfoToTooltip(tooltip, initPad, spec, max, withText, withSell, withBuy);
    }

    @Override
    public JSONObject getJSONObject(String key) throws JSONException {
        return vanilla.getJSONObject(key);
    }

    @Override
    public JSONArray getJSONArray(String key) throws JSONException {
        return vanilla.getJSONArray(key);
    }

    @Override
    public FactionAPI createBaseFaction(String factionId) {
        return vanilla.createBaseFaction(factionId);
    }

    @Override
    public List<MarketConditionSpecAPI> getAllMarketConditionSpecs() {
        return vanilla.getAllMarketConditionSpecs();
    }

    @Override
    public List<SubmarketSpecAPI> getAllSubmarketSpecs() {
        return vanilla.getAllSubmarketSpecs();
    }

    @Override
    public float getMinArmorFraction() {
        return vanilla.getMinArmorFraction();
    }

    @Override
    public float getMaxArmorDamageReduction() {
        return vanilla.getMaxArmorDamageReduction();
    }

    @Override
    public ShipSystemSpecAPI getShipSystemSpec(String id) {
        return vanilla.getShipSystemSpec(id);
    }

    @Override
    public List<ShipSystemSpecAPI> getAllShipSystemSpecs() {
        return vanilla.getAllShipSystemSpecs();
    }

    @Override
    public float getScreenScaleMult() {
        return vanilla.getScreenScaleMult();
    }

    @Override
    public int getAASamples() {
        return vanilla.getAASamples();
    }

    @Override
    public int getMouseX() {
        return vanilla.getMouseX();
    }

    @Override
    public int getMouseY() {
        return vanilla.getMouseY();
    }

    @Override
    public int getShippingCapacity(MarketAPI market, boolean inFaction) {
        return vanilla.getShippingCapacity(market, inFaction);
    }

    @Override
    public JSONObject getSettingsJSON() {
        return vanilla.getSettingsJSON();
    }

    @Override
    public void resetCached() {
        vanilla.resetCached();
    }

    @Override
    public void setFloat(String key, Float value) {
        vanilla.setFloat(key, value);
    }

    @Override
    public void setBoolean(String key, Boolean value) {
        vanilla.setBoolean(key, value);
    }

    @Override
    public List<PersonMissionSpec> getAllMissionSpecs() {
        return vanilla.getAllMissionSpecs();
    }

    @Override
    public PersonMissionSpec getMissionSpec(String id) {
        return vanilla.getMissionSpec(id);
    }

    @Override
    public List<BarEventSpec> getAllBarEventSpecs() {
        return vanilla.getAllBarEventSpecs();
    }

    @Override
    public BarEventSpec getBarEventSpec(String id) {
        return vanilla.getBarEventSpec(id);
    }

    @Override
    public void setAutoTurnMode(boolean autoTurnMode) {
        vanilla.setAutoTurnMode(autoTurnMode);
    }

    @Override
    public boolean isAutoTurnMode() {
        return vanilla.isAutoTurnMode();
    }

    @Override
    public boolean isShowDamageFloaties() {
        return vanilla.isShowDamageFloaties();
    }

    @Override
    public float getFloatFromArray(String key, int index) {
        return vanilla.getFloatFromArray(key, index);
    }

    @Override
    public int getIntFromArray(String key, int index) {
        return vanilla.getIntFromArray(key, index);
    }

    @Override
    public void loadTextureConvertBlackToAlpha(String filename) throws IOException {
        vanilla.loadTextureConvertBlackToAlpha(filename);
    }

    @Override
    public String getControlDescriptionForEnumName(String name) {
        return vanilla.getControlDescriptionForEnumName(name);
    }

    @Override
    public ShipAIPlugin pickShipAIPlugin(FleetMemberAPI member, ShipAPI ship) {
        return vanilla.pickShipAIPlugin(member, ship);
    }

    @Override
    public void unloadTexture(String filename) {
        vanilla.unloadTexture(filename);
    }

    @Override
    public void profilerSetEnabled(boolean enabled) {
        vanilla.profilerSetEnabled(enabled);
    }

    @Override
    public void profilerReset() {
        vanilla.profilerReset();
    }

    @Override
    public void profilerRestore() {
        vanilla.profilerRestore();
    }

    @Override
    public Color getBrightPlayerColor() {
        return vanilla.getBrightPlayerColor();
    }

    @Override
    public Color getDarkPlayerColor() {
        return vanilla.getDarkPlayerColor();
    }

    @Override
    public void forceMipmapsFor(String filename, boolean forceMipmaps) throws IOException {
        vanilla.forceMipmapsFor(filename, forceMipmaps);
    }

    @Override
    public String getGameVersion() {
        return vanilla.getGameVersion();
    }

    @Override
    public float computeStringWidth(String in, String font) {
        return vanilla.computeStringWidth(in, font);
    }

    @Override
    public TextFieldAPI createTextField(String text, String font) {
        return vanilla.createTextField(text, font);
    }

    @Override
    public ButtonAPI createCheckbox(String text, UICheckboxSize size) {
        return vanilla.createCheckbox(text, size);
    }

    @Override
    public ButtonAPI createCheckbox(String text, String font, Color checkColor, UICheckboxSize size) {
        return vanilla.createCheckbox(text, font, checkColor, size);
    }

    @Override
    public CustomPanelAPI createCustom(float width, float height, CustomUIPanelPlugin plugin) {
        return vanilla.createCustom(width, height, plugin);
    }

    @Override
    public int getMissionScore(String id) {
        return vanilla.getMissionScore(id);
    }
}