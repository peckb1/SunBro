package org.sunbro.app.model;

public enum Stat {
    VIGOR,
    ENDURANCE,
    VITALITY,
    ADAPTABILITY,
    STRENGTH,
    DEXTERITY,
    INTELLIGENCE,
    FAITH,
    ATTUNEMENT;

    @Override
    public String toString() {
        String word = super.toString();
        return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
    }
}
