package ru.BouH_.options.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import ru.BouH_.options.SettingsZp;
import ru.BouH_.options.manage.GuiButtonZp;
import ru.BouH_.options.manage.GuiSliderZp;

@SideOnly(Side.CLIENT)
public class GuiOptimizationOptionsZp extends GuiScreen {
    private final GuiScreen guiScreen;
    private final SettingsZp settingsZp;
    private String menuTitle;

    public GuiOptimizationOptionsZp(GuiScreen guiScreen, SettingsZp settingsZp) {
        this.guiScreen = guiScreen;
        this.settingsZp = settingsZp;
    }

    @SuppressWarnings("unchecked")
    public void initGui() {
        this.menuTitle = I18n.format("zpm.options.optimization.title");
        this.buttonList.add(new GuiButtonZp("gui.yes", "gui.no", 601, this.width / 2 - 155, this.height / 6 - 8, 150, 20, this.settingsZp.fancyGrass));
        this.buttonList.add(new GuiButtonZp("gui.yes", "gui.no", 602, this.width / 2 + 5, this.height / 6 - 8, 150, 20, this.settingsZp.fancyLeaf));
        this.buttonList.add(new GuiButtonZp("gui.yes", "gui.no", 603, this.width / 2 - 155, this.height / 6 + 16, 150, 20, this.settingsZp.lowParticles));
        this.buttonList.add(new GuiSliderZp(604, this.width / 2 - 128, this.height / 6 + 40, 256, this.settingsZp.chestDistance));
        this.buttonList.add(new GuiSliderZp(605, this.width / 2 - 128, this.height / 6 + 64, 256, this.settingsZp.entDistance));
        this.buttonList.add(new GuiSliderZp(606, this.width / 2 - 128, this.height / 6 + 88, 256, this.settingsZp.itemDistance));

        this.buttonList.add(new GuiButton(201, this.width / 2 - 100, this.height / 6 + 168, I18n.format("zpm.options.title")));
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 190, I18n.format("gui.done")));
    }

    public void updateScreen() {
    }

    private void reloadChunks() {
        if (Minecraft.getMinecraft().thePlayer != null) {
            EntityPlayerSP playerSP = Minecraft.getMinecraft().thePlayer;
            int distance = Minecraft.getMinecraft().gameSettings.renderDistanceChunks * 16;
            Minecraft.getMinecraft().renderGlobal.markBlockRangeForRenderUpdate((int) playerSP.posX - distance, 0, (int) playerSP.posZ - distance, (int) playerSP.posX + distance, 256, (int) playerSP.posZ + distance);
        }
    }

    protected void actionPerformed(GuiButton button) {
        if (button.enabled) {
            if (button instanceof GuiButtonZp) {
                GuiButtonZp guiButtonZp = (GuiButtonZp) button;
                guiButtonZp.perform();
                this.reloadChunks();
            } else if (button.id == 200) {
                this.mc.displayGuiScreen(new GuiMenuOptionsZp(this.guiScreen));
            } else if (button.id == 201) {
                this.mc.displayGuiScreen(new GuiOptionsZp(this.guiScreen, this.settingsZp));
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