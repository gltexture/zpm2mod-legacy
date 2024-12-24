package ru.BouH_.hook.client;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.IIcon;
import ru.BouH_.Main;
import ru.BouH_.blocks.BlockLayer;
import ru.BouH_.gameplay.client.RenderManager;
import ru.hook.asm.Hook;
import ru.hook.asm.ReturnCondition;

public class RenderBlockHook {
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static IIcon getIconSafe(RenderBlocks ren, IIcon p_147758_1_) {
        if (p_147758_1_ == null) {
            p_147758_1_ = ((TextureMap) Minecraft.getMinecraft().getTextureManager().getTexture(TextureMap.locationBlocksTexture)).getAtlasSprite("missingno");
        }
        return p_147758_1_;
    }

    @Hook
    public static boolean renderStandardBlockWithAmbientOcclusion(RenderBlocks ren, Block block, int x, int y, int z, float a, float b, float c) {
        double mixedValue = z * Math.sqrt(z & 453) + y * Math.sqrt(y & 453) + x * Math.sqrt(x & 453);
        Block block2 = Minecraft.getMinecraft().theWorld.getBlock(x, y + 1, z);
        if (Main.settingsZp.fancyGrass.isFlag()) {
            if (block instanceof BlockGrass) {
                if (block2 instanceof BlockSnow) {
                    int id = Math.floorMod((int) mixedValue, RenderManager.grass_snow.length);
                    RenderBlockHook.renderGrassSnowy(ren, RenderManager.grass_snow[id], x, y + 1, z);
                } else if (!(block2 instanceof BlockCarpet || block2 instanceof BlockLayer || block2.getMaterial() == Material.lava) && block2.isPassable(Minecraft.getMinecraft().theWorld, x, y + 1, z) || block2.getMaterial() == Material.portal) {
                    int id = Math.floorMod((int) mixedValue, RenderManager.grass.length);
                    RenderBlockHook.renderGrass(ren, RenderManager.grass[id], x, y + 1, z);
                }
            }
        }
        if (Main.settingsZp.fancyLeaf.isFlag()) {
            if (block instanceof BlockLeaves) {
                if (block2 instanceof BlockSnow) {
                    int id = Math.floorMod((int) mixedValue, RenderManager.leaf_snow.length);
                    RenderBlockHook.renderLeafSnowy(ren, RenderManager.leaf_snow[id], x, y, z);
                } else {
                    int id = Math.floorMod((int) mixedValue, RenderManager.leaf.length);
                    RenderBlockHook.renderLeaf(ren, RenderManager.leaf[id], x, y, z);
                }
            }
        }
        return true;
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS, createMethod = true)
    public static double getMaxRenderDistanceSquared(TileEntityChest entityChest) {
        return Main.settingsZp.chestDistance.getValue() * Main.settingsZp.chestDistance.getValue();
    }

    private static void renderGrass(RenderBlocks ren, IIcon icon, float p_147746_2_, float p_147746_3_, float p_147746_4_) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(Blocks.tallgrass.getMixedBrightnessForBlock(ren.blockAccess, (int) p_147746_2_, (int) p_147746_3_, (int) p_147746_4_));
        tessellator.setColorOpaque_I(Minecraft.getMinecraft().theWorld.getBiomeGenForCoords((int) p_147746_2_, (int) p_147746_4_).getBiomeGrassColor((int) p_147746_2_, (int) p_147746_3_ + 1, (int) p_147746_4_));
        double d1 = p_147746_2_;
        double d2 = (double) p_147746_3_ - (Minecraft.getMinecraft().theWorld.getBlock((int) p_147746_2_, (int) p_147746_3_, (int) p_147746_4_) instanceof BlockSnow ? -0.12f : 0.02f);
        double d0 = p_147746_4_;

        if (p_147746_2_ % 2 == 0) {
            d0 += 0.125f;
        } else {
            d0 -= 0.125f;
        }

        if (p_147746_4_ % 2 == 0) {
            d1 += 0.0625f;
        } else {
            d1 -= 0.0625f;
        }

        if (p_147746_3_ % 2 == 0) {
            d1 += 0.0626f;
        } else {
            d1 -= 0.0626f;
        }

        drawCrossedSquares(icon, d1, d2, d0, 1.0f);
    }

    private static void renderGrassSnowy(RenderBlocks ren, IIcon icon, float p_147746_2_, float p_147746_3_, float p_147746_4_) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(Blocks.leaves.getMixedBrightnessForBlock(ren.blockAccess, (int) p_147746_2_, (int) p_147746_3_, (int) p_147746_4_));
        tessellator.setColorOpaque(255, 255, 255);
        double d1 = p_147746_2_;
        double d2 = (double) p_147746_3_ - (Minecraft.getMinecraft().theWorld.getBlock((int) p_147746_2_, (int) p_147746_3_, (int) p_147746_4_) instanceof BlockSnow ? -0.12f : 0.02f);
        double d0 = p_147746_4_;

        if (p_147746_2_ % 2 == 0) {
            d0 += 0.125f;
        } else {
            d0 -= 0.125f;
        }

        if (p_147746_4_ % 2 == 0) {
            d1 += 0.0625f;
        } else {
            d1 -= 0.0625f;
        }

        if (p_147746_3_ % 2 == 0) {
            d1 += 0.0626f;
        } else {
            d1 -= 0.0626f;
        }

        drawCrossedSquares(icon, d1, d2, d0, 1.0f);
    }

    private static void renderLeaf(RenderBlocks ren, IIcon icon, float p_147746_2_, float p_147746_3_, float p_147746_4_) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(Blocks.leaves.getMixedBrightnessForBlock(ren.blockAccess, (int) p_147746_2_, (int) p_147746_3_, (int) p_147746_4_));
        tessellator.setColorOpaque_I(Minecraft.getMinecraft().theWorld.getBiomeGenForCoords((int) p_147746_2_, (int) p_147746_4_).getBiomeFoliageColor((int) p_147746_2_, (int) p_147746_3_ + 1, (int) p_147746_4_));
        double d1 = p_147746_2_;
        double d0 = p_147746_4_;

        if (p_147746_2_ % 2 == 0) {
            d0 += 0.125f;
        } else {
            d0 -= 0.125f;
        }

        if (p_147746_4_ % 2 == 0) {
            d1 += 0.0625f;
        } else {
            d1 -= 0.0625f;
        }

        if (p_147746_3_ % 2 == 0) {
            d1 += 0.0626f;
        } else {
            d1 -= 0.0626f;
        }

        drawCrossedSquares(icon, d1, p_147746_3_ - 0.25f, d0, 1.75f);
    }

    private static void renderLeafSnowy(RenderBlocks ren, IIcon icon, float p_147746_2_, float p_147746_3_, float p_147746_4_) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(Blocks.leaves.getMixedBrightnessForBlock(ren.blockAccess, (int) p_147746_2_, (int) p_147746_3_, (int) p_147746_4_));
        tessellator.setColorOpaque(255, 255, 255);
        double d1 = p_147746_2_;
        double d0 = p_147746_4_;

        if (p_147746_2_ % 2 == 0) {
            d0 += 0.125f;
        } else {
            d0 -= 0.125f;
        }

        if (p_147746_4_ % 2 == 0) {
            d1 += 0.0625f;
        } else {
            d1 -= 0.0625f;
        }

        if (p_147746_3_ % 2 == 0) {
            d1 += 0.0626f;
        } else {
            d1 -= 0.0626f;
        }

        drawCrossedSquares(icon, d1, p_147746_3_ - 0.25f, d0, 1.75f);
    }

    private static void drawCrossedSquares(IIcon p_147765_1_, double p_147765_2_, double p_147765_4_, double p_147765_6_, float scale) {
        Tessellator tessellator = Tessellator.instance;
        double d3 = p_147765_1_.getMinU();
        double d4 = p_147765_1_.getMinV();
        double d5 = p_147765_1_.getMaxU();
        double d6 = p_147765_1_.getMaxV();
        double d8 = p_147765_2_ + 0.5D - 0.45f * scale;
        double d9 = p_147765_2_ + 0.5D + 0.45f * scale;
        double d10 = p_147765_6_ + 0.5D - 0.45f * scale;
        double d11 = p_147765_6_ + 0.5D + 0.45f * scale;
        tessellator.addVertexWithUV(d8, p_147765_4_ + scale, d10, d3, d4);
        tessellator.addVertexWithUV(d8, p_147765_4_ + 0.0f, d10, d3, d6);
        tessellator.addVertexWithUV(d9, p_147765_4_ + 0.0f, d11, d5, d6);
        tessellator.addVertexWithUV(d9, p_147765_4_ + scale, d11, d5, d4);
        tessellator.addVertexWithUV(d9, p_147765_4_ + scale, d11, d3, d4);
        tessellator.addVertexWithUV(d9, p_147765_4_ + 0.0f, d11, d3, d6);
        tessellator.addVertexWithUV(d8, p_147765_4_ + 0.0f, d10, d5, d6);
        tessellator.addVertexWithUV(d8, p_147765_4_ + scale, d10, d5, d4);
        tessellator.addVertexWithUV(d8, p_147765_4_ + scale, d11, d3, d4);
        tessellator.addVertexWithUV(d8, p_147765_4_ + 0.0f, d11, d3, d6);
        tessellator.addVertexWithUV(d9, p_147765_4_ + 0.0f, d10, d5, d6);
        tessellator.addVertexWithUV(d9, p_147765_4_ + scale, d10, d5, d4);
        tessellator.addVertexWithUV(d9, p_147765_4_ + scale, d10, d3, d4);
        tessellator.addVertexWithUV(d9, p_147765_4_ + 0.0f, d10, d3, d6);
        tessellator.addVertexWithUV(d8, p_147765_4_ + 0.0f, d11, d5, d6);
        tessellator.addVertexWithUV(d8, p_147765_4_ + scale, d11, d5, d4);
    }
}
