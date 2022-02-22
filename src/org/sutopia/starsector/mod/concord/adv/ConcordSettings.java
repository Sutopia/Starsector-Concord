package org.sutopia.starsector.mod.concord.adv;

import java.util.HashMap;
import java.util.HashSet;

import org.sutopia.starsector.mod.concord.dynamic.MutableHullModSpec;
import org.sutopia.starsector.mod.concord.dynamic.MutableSettingsAPI;

import sun.reflect.Reflection;

import com.fs.starfarer.api.SettingsAPI;
import com.fs.starfarer.api.loading.HullModSpecAPI;

public class ConcordSettings extends MutableSettingsAPI {
    
    private static HashMap<String, HullModSpecAPI> cachedMock = new HashMap<>();
    
    public static HashSet<String> needMock = new HashSet<>();

    public ConcordSettings(SettingsAPI original) {
        super(original);
    }

    @Override
    public HullModSpecAPI getHullModSpec(String modId) {
        final HullModSpecAPI real = super.getHullModSpec(modId);
        if (!needMock.contains(modId)) {
            return real;
        }
        if (real.isHidden()) {
            StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
            for (StackTraceElement trace : stackTraceElements) {
                String callerMethod = trace.getMethodName();
                if (callerMethod == "getCurrSpecialMods" || callerMethod == "getCurrSpecialModsList") {
                    if (cachedMock.containsKey(real.getId())) {
                        return cachedMock.get(real.getId());
                    }
                    HullModSpecAPI mock = new MutableHullModSpec(real) {
                        @Override
                        public boolean isHidden() {
                            return false;
                        }
                    };
                    cachedMock.put(real.getId(), mock);
                    return mock;
                }
            }
        }
        
        return real;
    }
}
