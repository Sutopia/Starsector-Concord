package org.sutopia.starsector.mod.concord.adv;

import org.sutopia.starsector.mod.concord.Codex;

import com.fs.starfarer.api.Global;
import com.fs.starfarer.api.combat.BaseHullMod;
import com.fs.starfarer.api.combat.ShipAPI;
import com.fs.starfarer.api.loading.HullModSpecAPI;

public class ConcordCaptain extends BaseHullMod {
    @Override
    public void applyEffectsAfterShipCreation(ShipAPI ship, String id) {
        for (HullModSpecAPI spec : Global.getSettings().getAllHullModSpecs()) {
            if (spec.hasTag(Codex.TAG_CONCORD_CUSTODY) && !spec.hasTag(Codex.TAG_CONCORD_HIDDEN)) {
                HullModSpecAPI doppelganger = Global.getSettings()
                        .getHullModSpec(Codex.ID_PREFIX_CONCORD_DOPPELGANGER + spec.getId());
                
                if(ship.getVariant().hasHullMod(spec.getId())) {
                    doppelganger.setHidden(true);
                    spec.setHidden(false);
                } else {
                    doppelganger.setHidden(false);
                    spec.setHidden(true);
                }
            }
        }
    }
}
