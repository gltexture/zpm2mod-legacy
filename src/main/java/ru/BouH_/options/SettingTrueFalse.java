package ru.BouH_.options;

import ru.BouH_.Main;

public class SettingTrueFalse extends SettingObject {
    private boolean flag;

    public SettingTrueFalse(String name, Object... value) {
        super(name, value);
        this.flag = (boolean) value[0];
    }

    public boolean isFlag() {
        return this.flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void performSetting(Object... objects) {
        if (objects.length == 0) {
            this.setFlag(!this.isFlag());
        } else {
            this.setFlag((boolean) objects[0]);
        }
        Main.settingsZp.saveOptions();
    }
}
