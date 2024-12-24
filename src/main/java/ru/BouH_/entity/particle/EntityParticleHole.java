package ru.BouH_.entity.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import ru.BouH_.gameplay.client.RenderManager;

@SideOnly(Side.CLIENT)
public class EntityParticleHole extends EntityFX {
    private final int side;
    private final int blockX;
    private final int blockY;
    private final int blockZ;

    public EntityParticleHole(World world, double x, double y, double z, int blockX, int blockY, int blockZ, int sideHit) {
        super(world, x, y, z);
        this.setParticleIcon(RenderManager.hole);
        this.motionX = 0.0f;
        this.motionY = 0.0f;
        this.motionZ = 0.0f;
        this.side = sideHit;
        this.particleMaxAge = 1800;
        this.blockX = blockX;
        this.blockY = blockY;
        this.blockZ = blockZ;
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender(float p_70070_1_) {
        int i = MathHelper.floor_double(this.posX);
        int o = MathHelper.floor_double(this.posY);
        int j = MathHelper.floor_double(this.posZ);
        switch (side) {
            case 0:
                o -= 1;
                break;
            case 2:
                j -= 1;
                break;
            case 4:
                i -= 1;
                break;
            default:
                break;
        }
        if (this.worldObj.blockExists(i, 0, j)) {
            double d0 = (this.boundingBox.maxY - this.boundingBox.minY) * 0.66D;
            int k = MathHelper.floor_double(o - (double) this.yOffset + d0);
            return this.worldObj.getLightBrightnessForSkyBlocks(i, k, j, 0);
        } else {
            return 0;
        }
    }

    @Override
    public void renderParticle(Tessellator tessellator, float p_70539_2_, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_) {
        float f6 = ((float) this.particleAge + p_70539_2_) / (float) this.particleMaxAge;
        f6 *= f6;
        float f7 = Math.min(2.0F - f6 * 2.0F, 1.0f);
        Entity player = Minecraft.getMinecraft().thePlayer;
        interpPosX = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) p_70539_2_;
        interpPosY = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) p_70539_2_;
        interpPosZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) p_70539_2_;

        float f1 = this.particleIcon.getMinU();
        float f2 = this.particleIcon.getMaxU();
        float f3 = this.particleIcon.getMinV();
        float f4 = this.particleIcon.getMaxV();

        float f8 = 0.15F;
        float f9 = (float) (this.posX - interpPosX);
        float f10 = (float) (this.posY - interpPosY);
        float f11 = (float) (this.posZ - interpPosZ);
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        tessellator.setColorRGBA_F(1.0f, 1.0f, 1.0f, f7);
        Block block = this.worldObj.getBlock(this.blockX, this.blockY, this.blockZ);
        double d1;
        double d2;
        double d3;
        double mix = (this.getEntityId() % 1000.0d) * 1.0e-5d + 5.0e-5d;
        switch (side) {
            case 0:
                d1 = Math.min((double) blockX + block.getBlockBoundsMinX() - this.posX, 1);
                d3 = Math.min((double) blockZ + block.getBlockBoundsMinZ() - this.posZ, 1);
                if (d1 < -0.93f) {
                    f9 -= 0.03f - (1.0f + d1);
                } else if (d1 > -0.03f) {
                    f9 += 0.03f + d1;
                }
                if (d3 < -0.93f) {
                    f11 -= 0.03f - (1.0f + d3);
                } else if (d3 > -0.03f) {
                    f11 += 0.03f + d3;
                }
                tessellator.addVertexWithUV(f9 - f8, (double) f10 - mix, f11 - f8, f1, f4);
                tessellator.addVertexWithUV(f9 + f8, (double) f10 - mix, f11 - f8, f1, f3);
                tessellator.addVertexWithUV(f9 + f8, (double) f10 - mix, f11 + f8, f2, f3);
                tessellator.addVertexWithUV(f9 - f8, (double) f10 - mix, f11 + f8, f2, f4);
                break;
            case 1:
                d1 = Math.min((double) blockX + block.getBlockBoundsMinX() - this.posX, 1);
                d3 = Math.min((double) blockZ + block.getBlockBoundsMinZ() - this.posZ, 1);
                if (d1 < -0.93f) {
                    f9 -= 0.03f - (1.0f + d1);
                } else if (d1 > -0.03f) {
                    f9 += 0.03f + d1;
                }
                if (d3 < -0.93f) {
                    f11 -= 0.03f - (1.0f + d3);
                } else if (d3 > -0.03f) {
                    f11 += 0.03f + d3;
                }
                tessellator.addVertexWithUV(f9 - f8, (double) f10 + mix, f11 + f8, f1, f4);
                tessellator.addVertexWithUV(f9 + f8, (double) f10 + mix, f11 + f8, f1, f3);
                tessellator.addVertexWithUV(f9 + f8, (double) f10 + mix, f11 - f8, f2, f3);
                tessellator.addVertexWithUV(f9 - f8, (double) f10 + mix, f11 - f8, f2, f4);
                break;
            case 2:
                d1 = Math.min((double) blockX + block.getBlockBoundsMinX() - this.posX, 1);
                d2 = Math.min((double) blockY + block.getBlockBoundsMinY() - this.posY, 1);
                if (d1 < -0.93f) {
                    f9 -= 0.03f - (1.0f + d1);
                } else if (d1 > -0.03f) {
                    f9 += 0.03f + d1;
                }
                if (d2 < -0.93f) {
                    f10 -= 0.03f - (1.0f + d2);
                } else if (d2 > -0.03f) {
                    f10 += 0.03f + d2;
                }
                tessellator.addVertexWithUV(f9 - f8, f10 + f8, f11 - mix, f1, f4);
                tessellator.addVertexWithUV(f9 + f8, f10 + f8, f11 - mix, f1, f3);
                tessellator.addVertexWithUV(f9 + f8, f10 - f8, f11 - mix, f2, f3);
                tessellator.addVertexWithUV(f9 - f8, f10 - f8, f11 - mix, f2, f4);
                break;
            case 3:
                d1 = Math.min((double) blockX + block.getBlockBoundsMinX() - this.posX, 1);
                d2 = Math.min((double) blockY + block.getBlockBoundsMinY() - this.posY, 1);
                if (d1 < -0.93f) {
                    f9 -= 0.03f - (1.0f + d1);
                } else if (d1 > -0.03f) {
                    f9 += 0.03f + d1;
                }
                if (d2 < -0.93f) {
                    f10 -= 0.03f - (1.0f + d2);
                } else if (d2 > -0.03f) {
                    f10 += 0.03f + d2;
                }
                tessellator.addVertexWithUV(f9 - f8, f10 - f8, f11 + mix, f1, f4);
                tessellator.addVertexWithUV(f9 + f8, f10 - f8, f11 + mix, f1, f3);
                tessellator.addVertexWithUV(f9 + f8, f10 + f8, f11 + mix, f2, f3);
                tessellator.addVertexWithUV(f9 - f8, f10 + f8, f11 + mix, f2, f4);
                break;
            case 4:
                d2 = Math.min((double) blockY + block.getBlockBoundsMinY() - this.posY, 1);
                d3 = Math.min((double) blockZ + block.getBlockBoundsMinZ() - this.posZ, 1);
                if (d3 < -0.93f) {
                    f11 -= 0.03f - (1.0f + d3);
                } else if (d3 > -0.03f) {
                    f11 += 0.03f + d3;
                }
                if (d2 < -0.93f) {
                    f10 -= 0.03f - (1.0f + d2);
                } else if (d2 > -0.03f) {
                    f10 += 0.03f + d2;
                }
                tessellator.addVertexWithUV(f9 - mix, f10 + f8, f11 + f8, f1, f4);
                tessellator.addVertexWithUV(f9 - mix, f10 + f8, f11 - f8, f1, f3);
                tessellator.addVertexWithUV(f9 - mix, f10 - f8, f11 - f8, f2, f3);
                tessellator.addVertexWithUV(f9 - mix, f10 - f8, f11 + f8, f2, f4);
                break;
            case 5:
                d2 = Math.min((double) blockY + block.getBlockBoundsMinY() - this.posY, 1);
                d3 = Math.min((double) blockZ + block.getBlockBoundsMinZ() - this.posZ, 1);
                if (d3 < -0.93f) {
                    f11 -= 0.03f - (1.0f + d3);
                } else if (d3 > -0.03f) {
                    f11 += 0.03f + d3;
                }
                if (d2 < -0.93f) {
                    f10 -= 0.03f - (1.0f + d2);
                } else if (d2 > -0.03f) {
                    f10 += 0.03f + d2;
                }
                tessellator.addVertexWithUV(f9 + mix, f10 - f8, f11 + f8, f1, f4);
                tessellator.addVertexWithUV(f9 + mix, f10 - f8, f11 - f8, f1, f3);
                tessellator.addVertexWithUV(f9 + mix, f10 + f8, f11 - f8, f2, f3);
                tessellator.addVertexWithUV(f9 + mix, f10 + f8, f11 + f8, f2, f4);
                break;
            default:
                break;
        }
        GL11.glPopMatrix();
    }

    @Override
    public void onUpdate() {
        if (this.particleAge++ >= this.particleMaxAge || this.worldObj.isAirBlock(this.blockX, this.blockY, this.blockZ)) {
            this.setDead();
        }
    }

    @Override
    public int getFXLayer() {
        return 1;
    }
}
