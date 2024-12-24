package ru.BouH_.options;

import net.minecraft.client.resources.I18n;

public abstract class SettingObject {
    private final String name;
    private final Object[] defaultValue;

    public SettingObject(String name, Object... defaultValue) {
        this.name = name;
        this.defaultValue = defaultValue;
    }

    public String getName() {
        return this.name;
    }

    public String getTranslation() {
        return I18n.format("zpm.options." + this.name);
    }

    public Object[] getDefaultValue() {
        return this.defaultValue;
    }

    public abstract void performSetting(Object... objects);
}
