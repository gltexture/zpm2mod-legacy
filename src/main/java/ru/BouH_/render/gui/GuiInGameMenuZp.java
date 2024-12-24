package ru.BouH_.render.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiShareToLan;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.resources.I18n;
import ru.BouH_.achievements.gui.GuiAchievementsZp;
import ru.BouH_.options.gui.GuiMenuOptionsZp;
import ru.BouH_.recipe_master.gui.GuiContainerRecipes;
import ru.BouH_.skills.gui.GuiSkillsZp;

@SideOnly(Side.CLIENT)
public class GuiInGameMenuZp extends GuiIngameMenu {
    @SideOnly(Side.CLIENT)
    public static boolean skillsEnabled = true;
    @SideOnly(Side.CLIENT)
    public static boolean achievementsEnabled = true;

    @SuppressWarnings("unchecked")
    public void initGui() {
        this.buttonList.clear();
        byte b0 = -16;
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96 + b0, I18n.format("menu.returnToMenu")));

        if (!this.mc.isIntegratedServerRunning()) {
            ((GuiButton) this.buttonList.get(0)).displayString = I18n.format("menu.disconnect");
        }

        this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 24 + b0, I18n.format("menu.returnToGame")));
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 72 + b0, 200, 20, I18n.format("menu.options")));
        GuiButton guibutton;
        this.buttonList.add(guibutton = new GuiButton(24, this.width / 2 - 100, this.height / 4 + 48 + b0, 200, 20, I18n.format("menu.shareToLan")));
        GuiButton guiButton1 = new GuiButton(5, 4, 4, 98, 20, I18n.format("achievement.zp.menuTitle"));
        if (!GuiInGameMenuZp.achievementsEnabled) {
            guiButton1.enabled = false;
        } else {
            guiButton1.packedFGColour = 0xccffcc;
        }
        this.buttonList.add(guiButton1);
        GuiButton guiButton2 = new GuiButton(7, 4, 26, 98, 20, I18n.format("skill.zp.menuTitle"));
        if (!GuiInGameMenuZp.skillsEnabled) {
            guiButton2.enabled = false;
        } else {
            guiButton2.packedFGColour = 0xccffcc;
        }
        this.buttonList.add(guiButton2);
        GuiButton guiButton3 = new GuiButton(8, 4, 48, 98, 20, I18n.format("recipes.zp.menuTitle"));
        guiButton3.packedFGColour = 0xccffcc;
        this.buttonList.add(guiButton3);
        this.buttonList.add(new GuiButton(6, 4, 70, 98, 20, I18n.format("gui.stats")));
        ((GuiButton) this.buttonList.get(2)).packedFGColour = 0xe6c7f3;
        guibutton.enabled = this.mc.isSingleplayer() && !this.mc.getIntegratedServer().getPublic();
    }

    protected void actionPerformed(GuiButton button) {
        switch (button.id) {
            case 0:
                this.mc.displayGuiScreen(new GuiMenuOptionsZp(this));
                break;
            case 1:
                button.enabled = false;
                this.mc.theWorld.sendQuittingDisconnectingPacket();
                this.mc.loadWorld(null);
                this.mc.displayGuiScreen(new GuiMainMenu());
            case 2:
            case 3:
            default:
                break;
            case 4:
                this.mc.displayGuiScreen(null);
                this.mc.setIngameFocus();
                break;
            case 5:
                if (this.mc.thePlayer != null) {
                    this.mc.displayGuiScreen(new GuiAchievementsZp(this));
                }
                break;
            case 7:
                if (this.mc.thePlayer != null) {
                    this.mc.displayGuiScreen(new GuiSkillsZp(this));
                }
                break;
            case 8:
                if (this.mc.thePlayer != null) {
                    this.mc.displayGuiScreen(new GuiContainerRecipes(this, false));
                }
                break;
            case 6:
                if (this.mc.thePlayer != null) {
                    this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.getStatFileWriter()));
                }
                break;
            case 24 :
                this.mc.displayGuiScreen(new GuiShareToLan(this));
                break;
        }
    }
}