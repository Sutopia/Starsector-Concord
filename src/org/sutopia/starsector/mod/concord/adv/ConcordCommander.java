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
        return false;
    }

    @Override
    public boolean runWhilePaused() {
        return false;
    }
    
    private float timer = 0f;
    
    @Override
    public void advance(float amount) {
        if (!Global.getCurrentState().equals(GameState.COMBAT)) {
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
        
        for (HullModSpecAPI spec : ConcordCaptain.specs) {
            String doppelganger = Codex.ID_PREFIX_CONCORD_DOPPELGANGER + spec.getId();
            if (player.knowsHullMod(spec.getId()) 
                    && !player.knowsHullMod(doppelganger)) {
                player.addKnownHullMod(doppelganger);
            }
        }
    }
}
