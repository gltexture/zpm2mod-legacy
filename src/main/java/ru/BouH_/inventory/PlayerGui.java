package ru.BouH_.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import ru.BouH_.Main;
import ru.BouH_.gameplay.WorldManager;
import ru.BouH_.gameplay.client.ClientHandler;
import ru.BouH_.recipe_master.gui.GuiContainerRecipes;
import ru.BouH_.skills.gui.GuiSkillsZp;
import ru.BouH_.utils.RenderUtils;
import ru.BouH_.utils.SoundUtils;

public class PlayerGui extends GuiInventory {
    public static ResourceLocation t1 = new ResourceLocation(Main.MODID + ":textures/gui/c_sel_1.png");
    public static ResourceLocation t2 = new ResourceLocation(Main.MODID + ":textures/gui/c_sel_2.png");
    public static ResourceLocation t3 = new ResourceLocation(Main.MODID + ":textures/gui/c_sel_3.png");
    public static ResourceLocation t4 = new ResourceLocation(Main.MODID + ":textures/gui/c_sel_4.png");

    private boolean selectedRecipes = false;
    private boolean selectedSkills = false;

    public PlayerGui(EntityPlayer p_i1094_1_) {
        super(p_i1094_1_);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == 0) {
            if (this.selectedRecipes) {
                Minecraft.getMinecraft().displayGuiScreen(new GuiContainerRecipes(this, false));
                SoundUtils.playMonoSound(Main.MODID + ":select");
            }
            if (this.selectedSkills) {
                Minecraft.getMinecraft().displayGuiScreen(new GuiSkillsZp(this));
                SoundUtils.playMonoSound(Main.MODID + ":select");
            }
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);
        int k = this.guiLeft;
        int l = this.guiTop;
        final int x = k + 85;
        final int y = l + 64;
        final int x2 = x + 24;
        this.selectedRecipes = mouseX >= x && mouseX <= x + 16 && mouseY >= y && mouseY <= y + 16;
        this.selectedSkills = mouseX >= x2 && mouseX <= x2 + 16 && mouseY >= y && mouseY <= y + 16;
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(this.selectedRecipes ? PlayerGui.t2 : PlayerGui.t1);
        RenderUtils.drawTexturedModalRectCustom(x, y, 16, 16, 100);
        this.mc.getTextureManager().bindTexture(this.selectedSkills ? PlayerGui.t4 : PlayerGui.t3);
        RenderUtils.drawTexturedModalRectCustom(x2, y, 16, 16, 100);

        GL11.glDisable(GL11.GL_DEPTH_TEST);
        if (this.selectedRecipes) {
            String s = I18n.format("recipes.zp.menuTitle");
            this.fontRendererObj.drawStringWithShadow(s, mouseX, mouseY + 14, 0x00ff00);
        }

        if (this.selectedSkills) {
            String s = I18n.format("skill.zp.menuTitle");
            this.fontRendererObj.drawStringWithShadow(s, mouseX, mouseY + 14, 0xff0000);
        }
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        WorldClient worldClient = Minecraft.getMinecraft().theWorld;
        if (worldClient != null) {
            String day = "";
            String time = "";
            if (worldClient.provider.dimensionId == 0 || worldClient.provider.dimensionId == 2) {
                long wTime = worldClient.getWorldTime() + 6000L;
                int hours = (int) ((wTime / 1000L) % 24);
                int minutes = (int) ((wTime % 1000L) * 60L / 1000L);
                int day_i = ClientHandler.day;
                day = "Day: " + day_i;
                time = "Time: " + String.format("%02d", hours) + ":" + String.format("%02d", minutes);
            } else {
                day = "???";
                time = "???";
            }
            this.fontRendererObj.drawString(day, 6, 6, WorldManager.is7Night(worldClient) ? 0xff0000 : 0xffffff);
            this.fontRendererObj.drawString(time, 6, 16, 0xffffff);
        }
    }
}
