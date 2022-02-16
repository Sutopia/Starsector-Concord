package org.sutopia.starsector.mod.concord.adv;

import org.sutopia.starsector.mod.concord.Codex;

import com.fs.starfarer.api.EveryFrameScript;
import com.fs.starfarer.api.GameState;
import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.campaign.FactionAPI;
import com.fs.starfarer.api.loading.HullModSpecAPI;

public class ConcordCommander implements EveryFrameScript {
    @Override
    public boolean isDone() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean runWhilePaused() {
        // TODO Auto-generated method stub
        return true;
    }
    
    private float timer = 0f;
    
    @Override
    public void advance(float amount) {
        boolean skipUnhide = false;
        if (Global.getCombatEngine() != null
                && Global.getCurrentState().equals(GameState.CAMPAIGN)
                && Global.getSector().isPaused()) {
            skipUnhide = true;
        } else if (!Global.getCurrentState().equals(GameState.COMBAT)) {
            timer -= amount;
            if (timer <= 0) {
                timer = 10f;
            } else {
                return;
            }
        } else {
            return;
        }
        FactionAPI player = Global.getSector().getPlayerFaction();
        for (HullModSpecAPI spec : Global.getSettings().getAllHullModSpecs()) {
            if (spec.hasTag(Codex.TAG_CONCORD_CUSTODY) && !spec.hasTag(Codex.TAG_CONCORD_HIDDEN)) {
                if (!skipUnhide) {
                    spec.setHidden(false);
                }
                String doppelganger = Codex.ID_PREFIX_CONCORD_DOPPELGANGER + spec.getId();
                if (player.knowsHullMod(spec.getId()) 
                        && !player.knowsHullMod(doppelganger)) {
                    player.addKnownHullMod(doppelganger);
                }
            }
        }
    }
}
