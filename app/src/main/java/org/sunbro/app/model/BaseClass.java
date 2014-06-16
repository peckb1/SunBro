package org.sunbro.app.model;

import android.util.Log;
import com.google.common.collect.Maps;

import java.util.Map;

import static org.sunbro.app.model.Stat.*;

public enum BaseClass {
    WARRIOR(7, 6, 6, 5, 15, 11, 5, 5, 5),
    KNIGHT(12, 6, 7, 9, 11, 8, 3, 6, 4),
    SWORDSMAN(4, 8, 4, 6, 9, 16, 7, 5, 6),
    BANDIT(9, 7, 11, 3, 9, 14, 1, 8, 2),
    CLERIC(10, 3, 8, 4, 11, 5, 4, 12 ,10),
    SORCERER(5, 6, 5, 8, 3, 7, 14, 4, 12),
    EXPLORER(7, 6, 9, 12, 6, 6, 5, 5, 7),
    DEPRIVED(6, 6, 6, 6, 6, 6, 6, 6, 6);

    private static final int BASE_SOUL_LEVEL = -53;
    private static final String TAG = BaseClass.class.getSimpleName();

    private final Map<Stat, Integer> baseStats = Maps.newEnumMap(Stat.class);

    BaseClass(int vigor,        int endurance, int vitality,
              int adaptability, int strength,  int dexterity,
              int intelligence, int faith,     int attunement) {

        baseStats.put(VIGOR,        vigor);
        baseStats.put(ENDURANCE,    endurance);
        baseStats.put(VITALITY,     vitality);
        baseStats.put(ADAPTABILITY, adaptability);
        baseStats.put(STRENGTH,     strength);
        baseStats.put(DEXTERITY,    dexterity);
        baseStats.put(INTELLIGENCE, intelligence);
        baseStats.put(FAITH,        faith);
        baseStats.put(ATTUNEMENT,   attunement);
    }

    public int getSoulLevel() {
        int soulLevel = BASE_SOUL_LEVEL;
        for(int value: baseStats.values()) {
            soulLevel = soulLevel + value;
        }
        return soulLevel;
    }

    public int getStatValue(Stat stat) {
        return baseStats.get(stat);
    }

    @Override
    public String toString() {
        String word = super.toString();
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }
}
