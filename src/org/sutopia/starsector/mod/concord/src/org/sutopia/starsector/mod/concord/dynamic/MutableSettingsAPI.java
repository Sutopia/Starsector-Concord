package org.sutopia.starsector.mod.concord.dynamic;

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
public abstract class MutableSettingsAPI implements SettingsAPI {
    
    private final SettingsAPI original;

    public MutableSettingsAPI(SettingsAPI source) {
        original = source;
    }

    public int getBattleSize() {
        return original.getBattleSize();
    }

    public PersonAPI createPerson() {
        return original.createPerson();
    }

    public LabelAPI createLabel(String text, String font) {
        return original.createLabel(text, font);
    }

    public float getBonusXP(String key) {
        return original.getBonusXP(key);
    }

    public float getFloat(String key) {
        return original.getFloat(key);
    }

    public boolean getBoolean(String key) {
        return original.getBoolean(key);
    }

    public ClassLoader getScriptClassLoader() {
        return original.getScriptClassLoader();
    }

    public boolean isDevMode() {
        return original.isDevMode();
    }

    public void setDevMode(boolean devMode) {
        original.setDevMode(devMode);
    }

    public Color getColor(String id) {
        return original.getColor(id);
    }

    public Object getInstanceOfScript(String className) {
        return original.getInstanceOfScript(className);
    }

    public String getString(String category, String id) {
        return original.getString(category, id);
    }

    public SpriteAPI getSprite(String filename) {
        return original.getSprite(filename);
    }

    public SpriteAPI getSprite(String category, String key) {
        return original.getSprite(category, key);
    }

    public SpriteAPI getSprite(SpriteId id) {
        return original.getSprite(id);
    }

    public String getSpriteName(String category, String id) {
        return original.getSpriteName(category, id);
    }

    public InputStream openStream(String filename) throws IOException {
        return original.openStream(filename);
    }

    public String loadText(String filename) throws IOException {
        return original.loadText(filename);
    }

    public JSONObject loadJSON(String filename) throws IOException, JSONException {
        return original.loadJSON(filename);
    }

    public JSONArray loadCSV(String filename) throws IOException, JSONException {
        return original.loadCSV(filename);
    }

    public JSONArray getMergedSpreadsheetDataForMod(String idColumn, String path, String masterMod) throws IOException,
            JSONException {
        return original.getMergedSpreadsheetDataForMod(idColumn, path, masterMod);
    }

    public JSONObject getMergedJSONForMod(String path, String masterMod) throws IOException, JSONException {
        return original.getMergedJSONForMod(path, masterMod);
    }

    public float getScreenWidth() {
        return original.getScreenWidth();
    }

    public float getScreenHeight() {
        return original.getScreenHeight();
    }

    public float getScreenWidthPixels() {
        return original.getScreenWidthPixels();
    }

    public float getScreenHeightPixels() {
        return original.getScreenHeightPixels();
    }

    public Description getDescription(String id, Type type) {
        return original.getDescription(id, type);
    }

    public CombatReadinessPlugin getCRPlugin() {
        return original.getCRPlugin();
    }

    public int getCodeFor(String key) {
        return original.getCodeFor(key);
    }

    public WeaponSpecAPI getWeaponSpec(String weaponId) {
        return original.getWeaponSpec(weaponId);
    }

    public void loadTexture(String filename) throws IOException {
        original.loadTexture(filename);
    }

    public float getTargetingRadius(Vector2f from, CombatEntityAPI target, boolean considerShield) {
        return original.getTargetingRadius(from, target, considerShield);
    }

    public ShipVariantAPI getVariant(String variantId) {
        return original.getVariant(variantId);
    }

    public Object getPlugin(String id) {
        return original.getPlugin(id);
    }

    public List<String> getSortedSkillIds() {
        return original.getSortedSkillIds();
    }

    public SkillSpecAPI getSkillSpec(String skillId) {
        return original.getSkillSpec(skillId);
    }

    public String getString(String key) {
        return original.getString(key);
    }

    public AbilitySpecAPI getAbilitySpec(String abilityId) {
        return original.getAbilitySpec(abilityId);
    }

    public List<String> getSortedAbilityIds() {
        return original.getSortedAbilityIds();
    }

    public float getBaseTravelSpeed() {
        return original.getBaseTravelSpeed();
    }

    public float getSpeedPerBurnLevel() {
        return original.getSpeedPerBurnLevel();
    }

    public float getUnitsPerLightYear() {
        return original.getUnitsPerLightYear();
    }

    public int getMaxShipsInFleet() {
        return original.getMaxShipsInFleet();
    }

    public TerrainSpecAPI getTerrainSpec(String terrainId) {
        return original.getTerrainSpec(terrainId);
    }

    public EventSpecAPI getEventSpec(String eventId) {
        return original.getEventSpec(eventId);
    }

    public CustomEntitySpecAPI getCustomEntitySpec(String id) {
        return original.getCustomEntitySpec(id);
    }

    public GameState getCurrentState() {
        return original.getCurrentState();
    }

    public int getMaxSensorRange() {
        return original.getMaxSensorRange();
    }

    public int getMaxSensorRangeHyper() {
        return original.getMaxSensorRangeHyper();
    }

    public int getMaxSensorRange(LocationAPI loc) {
        return original.getMaxSensorRange(loc);
    }

    public List<String> getAllVariantIds() {
        return original.getAllVariantIds();
    }

    public List<String> getAptitudeIds() {
        return original.getAptitudeIds();
    }

    public List<String> getSkillIds() {
        return original.getSkillIds();
    }

    public LevelupPlugin getLevelupPlugin() {
        return original.getLevelupPlugin();
    }

    public String getVersionString() {
        return original.getVersionString();
    }

    public JSONObject loadJSON(String filename, String modId) throws IOException, JSONException {
        return original.loadJSON(filename, modId);
    }

    public JSONArray loadCSV(String filename, String modId) throws IOException, JSONException {
        return original.loadCSV(filename, modId);
    }

    public String loadText(String filename, String modId) throws IOException, JSONException {
        return original.loadText(filename, modId);
    }

    public ModManagerAPI getModManager() {
        return original.getModManager();
    }

    public float getBaseFleetSelectionRadius() {
        return original.getBaseFleetSelectionRadius();
    }

    public float getFleetSelectionRadiusPerUnitSize() {
        return original.getFleetSelectionRadiusPerUnitSize();
    }

    public float getMaxFleetSelectionRadius() {
        return original.getMaxFleetSelectionRadius();
    }

    public List<RoleEntryAPI> getEntriesForRole(String factionId, String role) {
        return original.getEntriesForRole(factionId, role);
    }

    public void addEntryForRole(String factionId, String role, String variantId, float weight) {
        original.addEntryForRole(factionId, role, variantId, weight);
    }

    public void removeEntryForRole(String factionId, String role, String variantId) {
        original.removeEntryForRole(factionId, role, variantId);
    }

    public List<RoleEntryAPI> getDefaultEntriesForRole(String role) {
        return original.getDefaultEntriesForRole(role);
    }

    public void addDefaultEntryForRole(String role, String variantId, float weight) {
        original.addDefaultEntryForRole(role, variantId, weight);
    }

    public void removeDefaultEntryForRole(String role, String variantId) {
        original.removeDefaultEntryForRole(role, variantId);
    }

    public void profilerBegin(String id) {
        original.profilerBegin(id);
    }

    public void profilerEnd() {
        original.profilerEnd();
    }

    public void profilerPrintResultsTree() {
        original.profilerPrintResultsTree();
    }

    public List<PlanetSpecAPI> getAllPlanetSpecs() {
        return original.getAllPlanetSpecs();
    }

    public Object getSpec(Class c, String id, boolean nullOnNotFound) {
        return original.getSpec(c, id, nullOnNotFound);
    }

    public void putSpec(Class c, String id, Object spec) {
        original.putSpec(c, id, spec);
    }

    public Collection<Object> getAllSpecs(Class c) {
        return original.getAllSpecs(c);
    }

    public String getRoman(int n) {
        return original.getRoman(n);
    }

    public void greekLetterReset() {
        original.greekLetterReset();
    }

    public String getNextCoolGreekLetter(Object context) {
        return original.getNextCoolGreekLetter(context);
    }

    public String getNextGreekLetter(Object context) {
        return original.getNextGreekLetter(context);
    }

    public MarketConditionSpecAPI getMarketConditionSpec(String conditionId) {
        return original.getMarketConditionSpec(conditionId);
    }

    public ShipAIPlugin createDefaultShipAI(ShipAPI ship, ShipAIConfig config) {
        return original.createDefaultShipAI(ship, config);
    }

    public HullModSpecAPI getHullModSpec(String modId) {
        return original.getHullModSpec(modId);
    }

    public FighterWingSpecAPI getFighterWingSpec(String wingId) {
        return original.getFighterWingSpec(wingId);
    }

    public List<HullModSpecAPI> getAllHullModSpecs() {
        return original.getAllHullModSpecs();
    }

    public List<FighterWingSpecAPI> getAllFighterWingSpecs() {
        return original.getAllFighterWingSpecs();
    }

    public List<WeaponSpecAPI> getAllWeaponSpecs() {
        return original.getAllWeaponSpecs();
    }

    public boolean isSoundEnabled() {
        return original.isSoundEnabled();
    }

    public boolean isInCampaignState() {
        return original.isInCampaignState();
    }

    public boolean isGeneratingNewGame() {
        return original.isGeneratingNewGame();
    }

    public float getAngleInDegreesFast(Vector2f v) {
        return original.getAngleInDegreesFast(v);
    }

    public float getAngleInDegreesFast(Vector2f from, Vector2f to) {
        return original.getAngleInDegreesFast(from, to);
    }

    public CommoditySpecAPI getCommoditySpec(String commodityId) {
        return original.getCommoditySpec(commodityId);
    }

    public ShipHullSpecAPI getHullSpec(String hullId) {
        return original.getHullSpec(hullId);
    }

    public int computeNumFighterBays(ShipVariantAPI variant) {
        return original.computeNumFighterBays(variant);
    }

    public boolean isInGame() {
        return original.isInGame();
    }

    public Object getNewPluginInstance(String id) {
        return original.getNewPluginInstance(id);
    }

    public String getControlStringForAbilitySlot(int index) {
        return original.getControlStringForAbilitySlot(index);
    }

    public String getControlStringForEnumName(String name) {
        return original.getControlStringForEnumName(name);
    }

    public boolean isNewPlayer() {
        return original.isNewPlayer();
    }

    public IndustrySpecAPI getIndustrySpec(String industryId) {
        return original.getIndustrySpec(industryId);
    }

    public List<CommoditySpecAPI> getAllCommoditySpecs() {
        return original.getAllCommoditySpecs();
    }

    public int getInt(String key) {
        return original.getInt(key);
    }

    public List<IndustrySpecAPI> getAllIndustrySpecs() {
        return original.getAllIndustrySpecs();
    }

    public SpecialItemSpecAPI getSpecialItemSpec(String itemId) {
        return original.getSpecialItemSpec(itemId);
    }

    public List<SpecialItemSpecAPI> getAllSpecialItemSpecs() {
        return original.getAllSpecialItemSpecs();
    }

    public List<ShipHullSpecAPI> getAllShipHullSpecs() {
        return original.getAllShipHullSpecs();
    }

    public SpriteAPI getSprite(String category, String id, boolean emptySpriteOnNotFound) {
        return original.getSprite(category, id, emptySpriteOnNotFound);
    }

    public ShipVariantAPI createEmptyVariant(String hullVariantId, ShipHullSpecAPI hullSpec) {
        return original.createEmptyVariant(hullVariantId, hullSpec);
    }

    public ListMap<String> getHullIdToVariantListMap() {
        return original.getHullIdToVariantListMap();
    }

    public String readTextFileFromCommon(String filename) throws IOException {
        return original.readTextFileFromCommon(filename);
    }

    public void writeTextFileToCommon(String filename, String data) throws IOException {
        original.writeTextFileToCommon(filename, data);
    }

    public boolean fileExistsInCommon(String filename) {
        return original.fileExistsInCommon(filename);
    }

    public void deleteTextFileFromCommon(String filename) {
        original.deleteTextFileFromCommon(filename);
    }

    public Color getBasePlayerColor() {
        return original.getBasePlayerColor();
    }

    public Color getDesignTypeColor(String designType) {
        return original.getDesignTypeColor(designType);
    }

    public boolean doesVariantExist(String variantId) {
        return original.doesVariantExist(variantId);
    }

    public void addCommodityInfoToTooltip(TooltipMakerAPI tooltip, float initPad, CommoditySpecAPI spec, int max,
            boolean withText, boolean withSell, boolean withBuy) {
        original.addCommodityInfoToTooltip(tooltip, initPad, spec, max, withText, withSell, withBuy);
    }

    public JSONObject getJSONObject(String key) throws JSONException {
        return original.getJSONObject(key);
    }

    public JSONArray getJSONArray(String key) throws JSONException {
        return original.getJSONArray(key);
    }

    public FactionAPI createBaseFaction(String factionId) {
        return original.createBaseFaction(factionId);
    }

    public List<MarketConditionSpecAPI> getAllMarketConditionSpecs() {
        return original.getAllMarketConditionSpecs();
    }

    public List<SubmarketSpecAPI> getAllSubmarketSpecs() {
        return original.getAllSubmarketSpecs();
    }

    public float getMinArmorFraction() {
        return original.getMinArmorFraction();
    }

    public float getMaxArmorDamageReduction() {
        return original.getMaxArmorDamageReduction();
    }

    public ShipSystemSpecAPI getShipSystemSpec(String id) {
        return original.getShipSystemSpec(id);
    }

    public List<ShipSystemSpecAPI> getAllShipSystemSpecs() {
        return original.getAllShipSystemSpecs();
    }

    public float getScreenScaleMult() {
        return original.getScreenScaleMult();
    }

    public int getAASamples() {
        return original.getAASamples();
    }

    public int getMouseX() {
        return original.getMouseX();
    }

    public int getMouseY() {
        return original.getMouseY();
    }

    public int getShippingCapacity(MarketAPI market, boolean inFaction) {
        return original.getShippingCapacity(market, inFaction);
    }

    public JSONObject getSettingsJSON() {
        return original.getSettingsJSON();
    }

    public void resetCached() {
        original.resetCached();
    }

    public void setFloat(String key, Float value) {
        original.setFloat(key, value);
    }

    public void setBoolean(String key, Boolean value) {
        original.setBoolean(key, value);
    }

    public List<PersonMissionSpec> getAllMissionSpecs() {
        return original.getAllMissionSpecs();
    }

    public PersonMissionSpec getMissionSpec(String id) {
        return original.getMissionSpec(id);
    }

    public List<BarEventSpec> getAllBarEventSpecs() {
        return original.getAllBarEventSpecs();
    }

    public BarEventSpec getBarEventSpec(String id) {
        return original.getBarEventSpec(id);
    }

    public void setAutoTurnMode(boolean autoTurnMode) {
        original.setAutoTurnMode(autoTurnMode);
    }

    public boolean isAutoTurnMode() {
        return original.isAutoTurnMode();
    }

    public boolean isShowDamageFloaties() {
        return original.isShowDamageFloaties();
    }

    public float getFloatFromArray(String key, int index) {
        return original.getFloatFromArray(key, index);
    }

    public int getIntFromArray(String key, int index) {
        return original.getIntFromArray(key, index);
    }

    public void loadTextureConvertBlackToAlpha(String filename) throws IOException {
        original.loadTextureConvertBlackToAlpha(filename);
    }

    public String getControlDescriptionForEnumName(String name) {
        return original.getControlDescriptionForEnumName(name);
    }

    public ShipAIPlugin pickShipAIPlugin(FleetMemberAPI member, ShipAPI ship) {
        return original.pickShipAIPlugin(member, ship);
    }

    public void unloadTexture(String filename) {
        original.unloadTexture(filename);
    }

    public void profilerSetEnabled(boolean enabled) {
        original.profilerSetEnabled(enabled);
    }

    public void profilerReset() {
        original.profilerReset();
    }

    public void profilerRestore() {
        original.profilerRestore();
    }

    public Color getBrightPlayerColor() {
        return original.getBrightPlayerColor();
    }

    public Color getDarkPlayerColor() {
        return original.getDarkPlayerColor();
    }

    public void forceMipmapsFor(String filename, boolean forceMipmaps) throws IOException {
        original.forceMipmapsFor(filename, forceMipmaps);
    }

    public String getGameVersion() {
        return original.getGameVersion();
    }

    public float computeStringWidth(String in, String font) {
        return original.computeStringWidth(in, font);
    }

    public TextFieldAPI createTextField(String text, String font) {
        return original.createTextField(text, font);
    }

    public ButtonAPI createCheckbox(String text, UICheckboxSize size) {
        return original.createCheckbox(text, size);
    }

    public ButtonAPI createCheckbox(String text, String font, Color checkColor, UICheckboxSize size) {
        return original.createCheckbox(text, font, checkColor, size);
    }

    public CustomPanelAPI createCustom(float width, float height, CustomUIPanelPlugin plugin) {
        return original.createCustom(width, height, plugin);
    }

    public int getMissionScore(String id) {
        return original.getMissionScore(id);
    }

}