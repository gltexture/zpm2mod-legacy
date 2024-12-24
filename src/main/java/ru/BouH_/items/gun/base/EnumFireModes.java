package ru.BouH_.items.gun.base;

import net.minecraft.client.resources.I18n;

import java.util.HashMap;
import java.util.Map;

public enum EnumFireModes {
    SAFE("safe", 0),
    SEMI("semi", 1),
    AUTO("auto", 2),
    LAUNCHER("launcher", 3);
    private static final Map<Integer, EnumFireModes> EFMMap = new HashMap<>();

    static {
        EnumFireModes[] enumModifiers = EnumFireModes.values();
        for (EnumFireModes enumModifier : enumModifiers) {
            EnumFireModes.EFMMap.put(enumModifier.getId(), enumModifier);
        }
    }

    private final String name;
    private final int id;

    EnumFireModes(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public static EnumFireModes getEnumFireMode(int id) {
        return EnumFireModes.EFMMap.get(id);
    }

    public String toString() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public String getTranslation() {
        return I18n.format("weapon.fireMode." + this.name);
    }
}
