package org.sutopia.starsector.mod.concord.adv;

import com.fs.starfarer.api.campaign.BaseCampaignEventListener;
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
}
