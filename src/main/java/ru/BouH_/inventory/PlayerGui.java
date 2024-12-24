package ru.BouH_.inventory;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import ru.BouH_.Main;
import ru.BouH_.recipe_master.gui.GuiContainerRecipes;
import ru.BouH_.utils.RenderUtils;
import ru.BouH_.utils.SoundUtils;

public class PlayerGui extends GuiInventory {
    public static ResourceLocation t1 = new ResourceLocation(Main.MODID + ":textures/gui/c_sel_1.png");
    public static ResourceLocation t2 = new ResourceLocation(Main.MODID + ":textures/gui/c_sel_2.png");
    private boolean selected = false;

    public PlayerGui(EntityPlayer p_i1094_1_) {
        super(p_i1094_1_);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == 0) {
            if (this.selected) {
                Minecraft.getMinecraft().displayGuiScreen(new GuiContainerRecipes(this, false));
                SoundUtils.playMonoSound(Main.MODID + ":select");
            }
        }
    }

    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int k = this.guiLeft;
        int l = this.guiTop;
        final int x = k + 85;
        final int y = l + 64;
        String s = I18n.format("recipes.zp.menuTitle");
        this.selected = mouseX >= x && mouseX <= x + 16 + this.fontRendererObj.getStringWidth(s) && mouseY >= y && mouseY <= y + 16;
        GL11.glPushMatrix();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(this.selected ? PlayerGui.t2 : PlayerGui.t1);
        RenderUtils.drawTexturedModalRectCustom(x, y, 16, 16, 100);
        GL11.glPopMatrix();
        this.fontRendererObj.drawString(s, x + 18, y + 4, 4210752);
        WorldClient worldClient = Minecraft.getMinecraft().theWorld;
        if (worldClient != null) {
            long wTime = worldClient.getWorldTime() + 6000L;
            int hours = (int) ((wTime / 1000L) % 24);
            int minutes = (int) ((wTime % 1000L) * 60L / 1000L);
            int day_i = (int) (worldClient.getTotalWorldTime() / 24000.0f);
            String day = "Day: " + day_i;
            String time = "Time: " + String.format("%02d", hours) + ":" + String.format("%02d", minutes);
            this.fontRendererObj.drawString(day, 6, 6, day_i > 0 && day_i % 7 == 0 ? 0xff0000 : 0xffffff);
            this.fontRendererObj.drawString(time, 6, 16, 0xffffff);
        }
    }
}
