package ru.BouH_.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import ru.BouH_.Main;
import ru.BouH_.tiles.TileBrewingStand;

@SideOnly(Side.CLIENT)
public class BrewGui extends GuiContainer {
    private static final ResourceLocation brewingStandGuiTextures = new ResourceLocation(Main.MODID + ":textures/gui/brewing_stand.png");
    private final TileBrewingStand tileBrewingStand;

    public BrewGui(InventoryPlayer p_i1081_1_, TileBrewingStand p_i1081_2_) {
        super(new BrewContainer(p_i1081_1_, p_i1081_2_));
        this.tileBrewingStand = p_i1081_2_;
    }

    protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_) {
        String s = this.tileBrewingStand.isCustomInventoryName() ? this.tileBrewingStand.getInventoryName() : I18n.format(this.tileBrewingStand.getInventoryName());
        this.fontRendererObj.drawString(s, this.xSize / 2 - this.fontRendererObj.getStringWidth(s) / 2 + 38, this.ySize - 96 + 2, 4210752);
        this.fontRendererObj.drawString(I18n.format("container.inventory"), 8, this.ySize - 96 + 2, 4210752);
    }

    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(brewingStandGuiTextures);
        int k = (this.width - this.xSize) / 2;
        int l = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
        int i1 = this.tileBrewingStand.getBrewTime() - 1;

        if (i1 > 0) {
            int j1 = (int) (28.0F * (1.0F - (float) i1 / 3600.0F));

            if (j1 > 0) {
                this.drawTexturedModalRect(k + 97, l + 16, 176, 0, 9, j1);
            }

            int k1 = i1 / 2 % 10;

            switch (k1) {
                case 0:
                    j1 = 29;
                    break;
                case 1:
                    j1 = 26;
                    break;
                case 2:
                    j1 = 23;
                    break;
                case 3:
                    j1 = 20;
                    break;
                case 4:
                    j1 = 17;
                    break;
                case 5:
                    j1 = 14;
                    break;
                case 6:
                    j1 = 11;
                    break;
                case 7:
                    j1 = 8;
                    break;
                case 8:
                    j1 = 5;
                    break;
                case 9:
                    j1 = 0;
            }
            if (j1 > 0) {
                this.drawTexturedModalRect(k + 65, l + 14 + 29 - j1, 185, 29 - j1, 12, j1);
            }
        }
    }
}