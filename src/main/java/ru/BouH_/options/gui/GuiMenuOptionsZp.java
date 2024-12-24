package ru.BouH_.options.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import ru.BouH_.Main;

@SideOnly(Side.CLIENT)
public class GuiMenuOptionsZp extends GuiScreen {
    private final GuiScreen guiScreen;
    private String menuTitle;

    public GuiMenuOptionsZp(GuiScreen guiScreen) {
        this.guiScreen = guiScreen;
    }

    @SuppressWarnings("unchecked")
    public void initGui() {
        this.menuTitle = I18n.format("options.title");
        this.buttonList.add(new GuiButton(201, this.width / 2 - 75, this.height / 6 + 16, 150, 20, I18n.format("options.title")));
        this.buttonList.add(new GuiButton(202, this.width / 2 - 75, this.height / 6 + 40, 150, 20, I18n.format("zpm.options.title")));
        this.buttonList.add(new GuiButton(203, this.width / 2 - 75, this.height / 6 + 64, 150, 20, I18n.format("zpm.options.crossSettings")));
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 160, I18n.format("gui.done")));

        ((GuiButton) this.buttonList.get(1)).packedFGColour = 0xbcfce6;
    }

    protected void actionPerformed(GuiButton button) {
        if (button.enabled) {
            if (button.id == 201) {
                this.mc.displayGuiScreen(new GuiOptionsNew(this.guiScreen, Minecraft.getMinecraft().gameSettings));
            } else if (button.id == 202) {
                this.mc.displayGuiScreen(new GuiOptionsZp(this.guiScreen, Main.settingsZp));
            } else if (button.id == 203) {
                this.mc.displayGuiScreen(new GuiCrosshairOptionsZp(this.guiScreen, Main.settingsZp));
            } else if (button.id == 200) {
                this.mc.displayGuiScreen(Minecraft.getMinecraft().thePlayer != null ? new GuiIngameMenu() : null);
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.menuTitle, this.width / 2, 15, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}