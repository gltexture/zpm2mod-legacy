package ru.BouH_.render.tile;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.model.ModelLargeChest;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import ru.BouH_.Main;
import ru.BouH_.init.LootCasesZp;
import ru.BouH_.tiles.TileLootCase;

@SideOnly(Side.CLIENT)
public class TileChestRenderer extends TileEntitySpecialRenderer {
    private static final ResourceLocation textureTrappedDouble = new ResourceLocation("textures/entity/chest/trapped_double.png");
    private static final ResourceLocation textureChristmasDouble = new ResourceLocation("textures/entity/chest/christmas_double.png");
    private static final ResourceLocation textureNormalDouble = new ResourceLocation("textures/entity/chest/normal_double.png");
    private static final ResourceLocation textureTrapped = new ResourceLocation("textures/entity/chest/trapped.png");
    private static final ResourceLocation textureChristmas = new ResourceLocation("textures/entity/chest/christmas.png");
    private static final ResourceLocation textureNormal = new ResourceLocation("textures/entity/chest/normal.png");
    private static final ResourceLocation textureHalloween = new ResourceLocation(Main.MODID + ":textures/blocks/tile/halloween.png");
    private static final ResourceLocation textureHalloweenDouble = new ResourceLocation(Main.MODID + ":textures/blocks/tile/halloween_double.png");
    private static final ResourceLocation[] textureTierDouble = new ResourceLocation[LootCasesZp.id - 2];
    private static final ResourceLocation[] textureTier = new ResourceLocation[LootCasesZp.id - 2];

    private final ModelChest simpleChest = new ModelChest();
    private final ModelChest largeChest = new ModelLargeChest();

    private boolean isChristmas;
    private boolean isHalloween;

    public TileChestRenderer() {
        if (Main.isNewYear()) {
            this.isChristmas = true;
        } else if (Main.isHalloween()) {
            this.isHalloween = true;
        }

        for (int i = 1; i < LootCasesZp.id - 2; i++) {
            textureTier[i] = new ResourceLocation(Main.MODID + ":textures/blocks/case/tier" + i + ".png");
            textureTierDouble[i] = new ResourceLocation(Main.MODID + ":textures/blocks/case/tier" + i + "_double.png");
        }
    }

    public void renderTileEntityAt(TileEntityChest p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_) {
        int i;
        if (!p_147500_1_.hasWorldObj()) {
            i = 0;
        } else {
            Block block = p_147500_1_.getBlockType();
            i = p_147500_1_.getBlockMetadata();

            if (block instanceof BlockChest && i == 0) {
                try {
                    ((BlockChest) block).initMetadata(p_147500_1_.getWorld(), p_147500_1_.xCoord, p_147500_1_.yCoord, p_147500_1_.zCoord);
                } catch (ClassCastException e) {
                    FMLLog.severe("Attempted to render a chest at %d,  %d, %d that was not a chest", p_147500_1_.xCoord, p_147500_1_.yCoord, p_147500_1_.zCoord);
                }
                i = p_147500_1_.getBlockMetadata();
            }

            p_147500_1_.checkForAdjacentChests();
        }

        if (p_147500_1_.adjacentChestZNeg == null && p_147500_1_.adjacentChestXNeg == null) {
            ModelChest modelchest;

            if (p_147500_1_.adjacentChestXPos == null && p_147500_1_.adjacentChestZPos == null) {
                modelchest = this.simpleChest;
                if (p_147500_1_ instanceof TileFakeChest) {
                    TileFakeChest tileFakeChest = (TileFakeChest) p_147500_1_;
                    this.bindTexture(textureTier[Math.max(tileFakeChest.getType() - 2, 0)]);
                } else if (p_147500_1_ instanceof TileLootCase) {
                    this.bindTexture(textureTier[Math.max(p_147500_1_.getChestType() - 2, 0)]);
                } else {
                    if (p_147500_1_.getChestType() == 1) {
                        this.bindTexture(textureTrapped);
                    } else if (p_147500_1_.getChestType() == 0) {
                        if (this.isChristmas) {
                            this.bindTexture(textureChristmas);
                        } else if (this.isHalloween) {
                            this.bindTexture(textureHalloween);
                        } else {
                            this.bindTexture(textureNormal);
                        }
                    }
                }
            } else {
                modelchest = this.largeChest;
                if (p_147500_1_.getChestType() == 1) {
                    this.bindTexture(textureTrappedDouble);
                } else if (p_147500_1_.getChestType() == 0) {
                    if (this.isChristmas) {
                        this.bindTexture(textureChristmasDouble);
                    } else if (this.isHalloween) {
                        this.bindTexture(textureHalloweenDouble);
                    } else {
                        this.bindTexture(textureNormalDouble);
                    }
                } else {
                    if (p_147500_1_ instanceof TileLootCase) {
                        this.bindTexture(textureTierDouble[Math.max(p_147500_1_.getChestType() - 2, 0)]);
                    }
                }
            }

            GL11.glPushMatrix();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glTranslatef((float) p_147500_2_, (float) p_147500_4_ + 1.0F, (float) p_147500_6_ + 1.0F);
            GL11.glScalef(1.0F, -1.0F, -1.0F);
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
            short short1 = 0;

            if (i == 2) {
                short1 = 180;
            }

            if (i == 3) {
                short1 = 0;
            }

            if (i == 4) {
                short1 = 90;
            }

            if (i == 5) {
                short1 = -90;
            }

            if (i == 2 && p_147500_1_.adjacentChestXPos != null) {
                GL11.glTranslatef(1.0F, 0.0F, 0.0F);
            }

            if (i == 5 && p_147500_1_.adjacentChestZPos != null) {
                GL11.glTranslatef(0.0F, 0.0F, -1.0F);
            }

            GL11.glRotatef(short1, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            float f1 = p_147500_1_.prevLidAngle + (p_147500_1_.lidAngle - p_147500_1_.prevLidAngle) * p_147500_8_;
            float f2;

            if (p_147500_1_.adjacentChestZNeg != null) {
                f2 = p_147500_1_.adjacentChestZNeg.prevLidAngle + (p_147500_1_.adjacentChestZNeg.lidAngle - p_147500_1_.adjacentChestZNeg.prevLidAngle) * p_147500_8_;

                if (f2 > f1) {
                    f1 = f2;
                }
            }

            if (p_147500_1_.adjacentChestXNeg != null) {
                f2 = p_147500_1_.adjacentChestXNeg.prevLidAngle + (p_147500_1_.adjacentChestXNeg.lidAngle - p_147500_1_.adjacentChestXNeg.prevLidAngle) * p_147500_8_;

                if (f2 > f1) {
                    f1 = f2;
                }
            }

            f1 = 1.0F - f1;
            f1 = 1.0F - f1 * f1 * f1;
            modelchest.chestLid.rotateAngleX = -(f1 * (float) Math.PI / 2.0F);
            modelchest.renderAll();
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        }
    }

    public void renderTileEntityAt(TileEntity p_147500_1_, double p_147500_2_, double p_147500_4_, double p_147500_6_, float p_147500_8_) {
        this.renderTileEntityAt((TileEntityChest) p_147500_1_, p_147500_2_, p_147500_4_, p_147500_6_, p_147500_8_);
    }
}