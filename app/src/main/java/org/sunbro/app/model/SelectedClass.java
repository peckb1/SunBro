package org.sunbro.app.model;


import com.google.common.collect.Maps;

import java.util.EnumSet;
import java.util.Map;

public class SelectedClass {

    private final BaseClass baseClass;
    private final Map<Stat, Integer> raisedStats;

    public SelectedClass(BaseClass baseClass) {
        this.baseClass = baseClass;
        raisedStats = Maps.newEnumMap(Stat.class);

        initMap();
    }

    public int getSoulLevel() {
        int soulLevel = baseClass.getSoulLevel();
        for(int value: raisedStats.values()) {
            soulLevel = soulLevel + value;
        }
        return soulLevel;
    }

    public BaseClass getBaseClass() {
        return this.baseClass;
    }

    public int getRaisedStatValue(Stat stat) {
        return (baseClass.getStatValue(stat) + raisedStats.get(stat));
    }

    private void initMap() {
        for(Stat stat : EnumSet.allOf(Stat.class)) {
            raisedStats.put(stat, 0);
        }
    }

    public void increase(Stat stat) {
        raisedStats.put(stat, raisedStats.get(stat) + 1);
    }

    public void decrease(Stat stat) {
        raisedStats.put(stat, raisedStats.get(stat) - 1);
    }
}

