package ru.BouH_.options.manage;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import ru.BouH_.options.SettingTrueFalse;

public class GuiButtonZp extends GuiButton {
    private final SettingTrueFalse settingObject;
    private final String text1;
    private final String text2;

    public GuiButtonZp(String text1, String text2, int stateName, int id, int p_i1021_3_, int p_i1021_4_, int p_i1021_5_, SettingTrueFalse settingObject) {
        super(stateName, id, p_i1021_3_, p_i1021_4_, p_i1021_5_, "");
        this.settingObject = settingObject;
        this.text1 = text1;
        this.text2 = text2;
        this.displayString = this.getText(this.text1, this.text2);
    }

    public void perform() {
        this.settingObject.performSetting();
    }

    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
        super.mouseDragged(mc, mouseX, mouseY);
        this.displayString = this.getText(this.text1, this.text2);
    }

    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        this.displayString = this.getText(this.text1, this.text2);
        return super.mousePressed(mc, mouseX, mouseY);
    }

    private String getText(String text1, String text2) {
        return this.settingObject.getTranslation() + ": " + (this.settingObject.isFlag() ? I18n.format(text1) : I18n.format(text2));
    }
}