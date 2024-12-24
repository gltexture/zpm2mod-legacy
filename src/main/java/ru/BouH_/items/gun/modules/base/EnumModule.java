package ru.BouH_.items.gun.modules.base;

import net.minecraft.client.resources.I18n;

import java.util.HashMap;
import java.util.Map;

public enum EnumModule {
    SCOPE("scope"),
    BARREL("barrel"),
    UNDERBARREL("underbarrel");
    private static final Map<String, EnumModule> EMMap = new HashMap<>();

    static {
        EnumModule[] enumModifiers = EnumModule.values();
        for (EnumModule enumModifier : enumModifiers) {
            EnumModule.EMMap.put(enumModifier.toString(), enumModifier);
        }
    }

    private final String NBTName;

    EnumModule(String NBTName) {
        this.NBTName = NBTName;
    }

    public static EnumModule getEnumMod(String name) {
        return EnumModule.EMMap.get(name);
    }

    public String toString() {
        return this.NBTName;
    }

    public String getTranslation() {
        return I18n.format("weapon.mod." + this.NBTName);
    }
}
