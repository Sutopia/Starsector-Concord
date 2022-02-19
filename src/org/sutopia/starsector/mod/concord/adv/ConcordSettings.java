package org.sutopia.starsector.mod.concord.adv;

import org.sutopia.starsector.mod.concord.dynamic.MutableHullModSpec;
import org.sutopia.starsector.mod.concord.dynamic.MutableSettingsAPI;

import com.fs.starfarer.api.SettingsAPI;
import com.fs.starfarer.api.loading.HullModSpecAPI;

public class ConcordSettings extends MutableSettingsAPI {

    public ConcordSettings(SettingsAPI original) {
        super(original);
    }

    @Override
    public HullModSpecAPI getHullModSpec(String modId) {
        final HullModSpecAPI real = super.getHullModSpec(modId);

        StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (StackTraceElement trace : stackTraceElements) {
            String callerMethod = trace.getMethodName();
            if (callerMethod == "getCurrSpecialMods" || callerMethod == "getCurrSpecialModsList") {
                return new MutableHullModSpec(real) {
                    @Override
                    public boolean isHiddenEverywhere() {
                        return false;
                    }

                    @Override
                    public boolean isHidden() {
                        return false;
                    }
                };
            }
        }
        
        return real;
    }
}
