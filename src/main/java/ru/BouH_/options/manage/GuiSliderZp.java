package ru.BouH_.options.manage;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;
import ru.BouH_.options.SettingFloatBar;

@SideOnly(Side.CLIENT)
public class GuiSliderZp extends GuiButton {
    private final SettingFloatBar settingFloatBar;
    public boolean dragging;
    private double sliderValue;
    private float cast = 1.0f;
    private boolean flag;

    public GuiSliderZp(int p_i45017_1_, int p_i45017_2_, int p_i45017_3_, SettingFloatBar settingFloatBar) {
        super(p_i45017_1_, p_i45017_2_, p_i45017_3_, 150, 20, "");
        this.sliderValue = 1.0F;
        this.settingFloatBar = settingFloatBar;
        this.sliderValue = settingFloatBar.normalizeValue(settingFloatBar.getValue());
        this.displayString = this.getText();
    }

    public GuiSliderZp(int p_i45017_1_, int p_i45017_2_, int p_i45017_3_, int width, SettingFloatBar settingFloatBar) {
        super(p_i45017_1_, p_i45017_2_, p_i45017_3_, width, 20, "");
        this.sliderValue = 1.0F;
        this.settingFloatBar = settingFloatBar;
        this.sliderValue = settingFloatBar.normalizeValue(settingFloatBar.getValue());
        this.displayString = this.getText();
    }

    public GuiSliderZp(int p_i45017_1_, int p_i45017_2_, int p_i45017_3_, SettingFloatBar settingFloatBar, float cast) {
        super(p_i45017_1_, p_i45017_2_, p_i45017_3_, 150, 20, "");
        this.sliderValue = 1.0F;
        this.settingFloatBar = settingFloatBar;
        this.sliderValue = settingFloatBar.normalizeValue(settingFloatBar.getValue());
        this.displayString = this.getText();
        this.cast = cast;
    }

    public GuiSliderZp setPercentValue() {
        this.flag = true;
        this.displayString = this.getText();
        return this;
    }

    public float getCast() {
        return this.cast;
    }

    public int getHoverState(boolean mouseOver) {
        return 0;
    }

    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
        if (this.visible) {
            if (this.dragging) {
                this.sliderValue = (double) (mouseX - (this.xPosition + 4)) / (this.width - 8);

                if (this.sliderValue < 0.0F) {
                    this.sliderValue = 0.0F;
                }

                if (this.sliderValue > 1.0F) {
                    this.sliderValue = 1.0F;
                }

                double f = this.settingFloatBar.denormalizeValue(this.sliderValue);
                this.settingFloatBar.performSetting(f);
                this.sliderValue = this.settingFloatBar.normalizeValue(f);
                this.displayString = this.getText();
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.drawTexturedModalRect(this.xPosition + (int) (this.sliderValue * (float) (this.width - 8)), this.yPosition, 0, 66, 4, 20);
            this.drawTexturedModalRect(this.xPosition + (int) (this.sliderValue * (float) (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
        }
    }

    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if (super.mousePressed(mc, mouseX, mouseY)) {
            this.sliderValue = (double) (mouseX - (this.xPosition + 4)) / (this.width - 8);

            if (this.sliderValue < 0.0F) {
                this.sliderValue = 0.0F;
            }

            if (this.sliderValue > 1.0F) {
                this.sliderValue = 1.0F;
            }

            this.settingFloatBar.performSetting(this.settingFloatBar.denormalizeValue(this.sliderValue));
            this.displayString = this.getText();
            this.dragging = true;
            return true;
        } else {
            return false;
        }
    }

    private String getText() {
        String s1 = this.settingFloatBar.getTranslation() + ": " + String.format("%.1f", this.settingFloatBar.getValue() * this.getCast());
        if (this.flag) {
            s1 = s1.substring(0, s1.length() - 2);
            s1 += "%";
        }
        return s1;
    }

    public void mouseReleased(int mouseX, int mouseY) {
        this.dragging = false;
    }
}