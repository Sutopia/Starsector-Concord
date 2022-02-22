package org.sutopia.starsector.mod.concord.adv;

import com.fs.starfarer.api.EveryFrameScript;

public class ConcordCommander implements EveryFrameScript {
    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public boolean runWhilePaused() {
        return false;
    }
    
    
    @Override
    public void advance(float amount) {
        return;
    }
}
