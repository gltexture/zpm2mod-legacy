package ru.BouH_.options.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import ru.BouH_.options.SettingsZp;
import ru.BouH_.options.manage.GuiButtonZp;

@SideOnly(Side.CLIENT)
public class GuiOptionsZp extends GuiScreen {
    private final GuiScreen guiScreen;
    private final SettingsZp settingsZp;
    private String menuTitle;

    public GuiOptionsZp(GuiScreen guiScreen, SettingsZp settingsZp) {
        this.guiScreen = guiScreen;
        this.settingsZp = settingsZp;
    }

    @SuppressWarnings("unchecked")
    public void initGui() {
        this.menuTitle = I18n.format("zpm.options.title");
        this.buttonList.add(new GuiButtonZp("gui.yes", "gui.no", 603, this.width / 2 - 155, this.height / 6 - 8, 150, 20, this.settingsZp.pickUp_F));
        this.buttonList.add(new GuiButtonZp("gui.yes", "gui.no", 604, this.width / 2 + 5, this.height / 6 - 8, 150, 20, this.settingsZp.showPing));
        this.buttonList.add(new GuiButtonZp("gui.yes", "gui.no", 605, this.width / 2 - 155, this.height / 6 + 16, 150, 20, this.settingsZp.fastFly));
        this.buttonList.add(new GuiButtonZp("gui.yes", "gui.no", 606, this.width / 2 + 5, this.height / 6 + 16, 150, 20, this.settingsZp.fancyDrop));
        this.buttonList.add(new GuiButtonZp("gui.yes", "gui.no", 607, this.width / 2 - 155, this.height / 6 + 40, 150, 20, this.settingsZp.autoReload));
        this.buttonList.add(new GuiButtonZp("gui.yes", "gui.no", 608, this.width / 2 + 5, this.height / 6 + 40, 150, 20, this.settingsZp.scaleCross));
        this.buttonList.add(new GuiButtonZp("gui.yes", "gui.no", 609, this.width / 2 - 155, this.height / 6 + 64, 150, 20, this.settingsZp.strafes));
        this.buttonList.add(new GuiButtonZp("gui.yes", "gui.no", 611, this.width / 2 + 5, this.height / 6 + 64, 150, 20, this.settingsZp.redScreen));
        this.buttonList.add(new GuiButtonZp("gui.yes", "gui.no", 612, this.width / 2 - 155, this.height / 6 + 88, 150, 20, this.settingsZp.progressBar));
        this.buttonList.add(new GuiButtonZp("zpm.options.selector.mouse", "zpm.options.selector.keyboard", 613, this.width / 2 + 5, this.height / 6 + 88, 150, 20, this.settingsZp.selector));
        this.buttonList.add(new GuiButtonZp("gui.yes", "gui.no", 614, this.width / 2 - 155, this.height / 6 + 112, 150, 20, this.settingsZp.notificationHud));
        this.buttonList.add(new GuiButtonZp("gui.yes", "gui.no", 615, this.width / 2 + 5, this.height / 6 + 112, 150, 20, this.settingsZp.notificationChat));
        this.buttonList.add(new GuiButtonZp("gui.yes", "gui.no", 616, this.width / 2 - 155, this.height / 6 + 136, 150, 20, this.settingsZp.ambientVolume));
        this.buttonList.add(new GuiButtonZp("gui.yes", "gui.no", 617, this.width / 2 + 5, this.height / 6 + 136, 150, 20, this.settingsZp.musicVolume));

        this.buttonList.add(new GuiButton(201, this.width / 2 - 100, this.height / 6 + 168, I18n.format("zpm.options.optimization.title")));
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 190, I18n.format("gui.done")));
    }

    protected void actionPerformed(GuiButton button) {
        if (button.enabled) {
            if (button instanceof GuiButtonZp) {
                GuiButtonZp guiButtonZp = (GuiButtonZp) button;
                guiButtonZp.perform();
            } else if (button.id == 200) {
                this.mc.displayGuiScreen(new GuiMenuOptionsZp(this.guiScreen));
            } else if (button.id == 201) {
                this.mc.displayGuiScreen(new GuiOptimizationOptionsZp(this.guiScreen, this.settingsZp));
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