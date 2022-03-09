package org.sutopia.starsector.mod.concord.adv;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.BaseCampaignEventListener;
import com.fs.starfarer.api.campaign.CampaignFleetAPI;
import com.fs.starfarer.api.campaign.CargoAPI;
import com.fs.starfarer.api.campaign.PlayerMarketTransaction;
import com.fs.starfarer.api.campaign.CargoAPI.CargoItemQuantity;
import com.fs.starfarer.api.campaign.CargoAPI.CargoItemType;
import com.fs.starfarer.api.campaign.econ.MarketAPI;
import com.fs.starfarer.api.combat.EngagementResultAPI;

public class ConcordCommander extends BaseCampaignEventListener {
    public ConcordCommander() {
        super(false);
    }

    @Override
    public void reportPlayerClosedMarket(MarketAPI market) {
        ConcordCaptain.clearMemCache();
    }
    
    @Override
    public void reportPlayerEngagement(EngagementResultAPI result) {
        ConcordCaptain.clearMemCache();
    }
    
    @Override
    public void reportPlayerOpenedMarketAndCargoUpdated(MarketAPI market) {
        revertFighters();
    }
    
    @Override
    public void reportPlayerMarketTransaction(PlayerMarketTransaction transaction) {
        revertFighters();
    }
    
    private void revertFighters() {
        CampaignFleetAPI fleet = Global.getSector().getPlayerFleet();
        if (fleet != null) {
            HashMap<String, Integer> addBack = new HashMap<>();
            CargoAPI cargo = fleet.getCargo();
            List<CargoItemQuantity<String>> allFighters = cargo.getFighters();
            Iterator<CargoItemQuantity<String>> iter = allFighters.iterator();
            while (iter.hasNext()) {
                CargoItemQuantity<String> wing = iter.next();
                if (ConcordDynamicInstanceAssembly.INJECTED_FIGHTER_TO_VANILLA.containsKey(wing.getItem())) {
                    addBack.put(wing.getItem(), wing.getCount());
                }
            }
            for (String wingId : addBack.keySet()) {
                cargo.removeItems(CargoItemType.FIGHTER_CHIP, wingId, addBack.get(wingId));
                cargo.addFighters(ConcordDynamicInstanceAssembly.INJECTED_FIGHTER_TO_VANILLA.get(wingId), addBack.get(wingId));
            }
        }
    }
}
