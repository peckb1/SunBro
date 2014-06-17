package org.sunbro.app.events;

import org.sunbro.app.model.Stat;

public class StatChangeEvent {

    private final Stat stat;
    private final Integer newValue;

    private StatChangeEvent(Stat stat, Integer newValue) {
        this.stat = stat;
        this.newValue = newValue;
    }

    public static StatChangeEvent create(Stat stat, Integer newValue) {
        return new StatChangeEvent(stat, newValue);
    }
}
