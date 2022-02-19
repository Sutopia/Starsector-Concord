package org.sutopia.starsector.mod.concord;

import com.fs.starfarer.api.Global;

public abstract class MutableSpecAPI<T> {

    protected final T original;
    private final Class<?> type;

    public MutableSpecAPI(T source) {
        this.original = source;
        this.type = source.getClass();
    }

    public boolean register(String id) {
        if (id == null) {
            return false;
        }

        if (Global.getSettings().getSpec(type, id, true) != null) {
            return false;
        }

        Global.getSettings().putSpec(type, id, this);
        return true;
    }
    
    public abstract String getOriginalId();
}
