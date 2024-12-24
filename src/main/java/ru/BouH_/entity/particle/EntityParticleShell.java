package ru.BouH_.entity.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import ru.BouH_.Main;
import ru.BouH_.blocks.BlockLayer;
import ru.BouH_.gameplay.client.RenderManager;
import ru.BouH_.items.gun.render.Shell;
import ru.BouH_.utils.SoundUtils;


@SideOnly(Side.CLIENT)
public class EntityParticleShell extends EntityFX {
    private boolean collidedWithSurface = false;

    public EntityParticleShell(World p_i1234_1_, Shell shell, double p_i1234_2_, double p_i1234_4_, double p_i1234_6_, double p_i1234_8_, double p_i1234_10_, double p_i1234_12_) {
        super(p_i1234_1_, p_i1234_2_, p_i1234_4_, p_i1234_6_, p_i1234_8_, p_i1234_10_, p_i1234_12_);
        this.setParticleIcon(RenderManager.shell);
        this.particleGravity = Blocks.stone.blockParticleGravity;
        this.particleRed = shell.getRed();
        this.particleGreen = shell.getGreen();
        this.particleBlue = shell.getBlue();
        this.particleScale = shell.getScale();
        this.particleMaxAge = 1800;
    }

    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge) {
            this.setDead();
        }

        this.motionY -= 0.03D * (double) this.particleGravity;
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.98f;
        this.motionZ *= 0.98f;

        if (this.onGround || this.isEntityInsideOpaqueBlock()) {
            if (!this.collidedWithSurface && this.motionY == 0) {
                Block block0 = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
                Block block = this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY - 0.5f), MathHelper.floor_double(this.posZ));
                if (block0.getMaterial() == Material.snow || block0.getMaterial() == Material.craftedSnow || block0.getMaterial() == Material.carpet) {
                    SoundUtils.playClientSound(this.posX, this.posY, this.posZ, block0.stepSound.getStepSound(), 0.5f, 1.5f);
                } else {
                    if (block.getMaterial() == Material.rock || block.getMaterial() == Material.iron || block.getMaterial() == Material.glass || block.getMaterial() == Material.redstoneLight || block.getMaterial() == Material.anvil || block.getMaterial() == Material.wood || block.getMaterial() == Material.coral || block.getMaterial() == Material.ice || block.getMaterial() == Material.piston || block.getMaterial() == Material.packedIce) {
                        float f = Main.rand.nextFloat();
                        this.motionY += 0.1f + (1.0f - f) * 0.12f;
                        SoundUtils.playClientSound(this.posX, this.posY, this.posZ, Main.MODID + ":shell_drop", 0.5f, 0.5f + f);
                    } else {
                        if (block.getMaterial() != Material.air) {
                            SoundUtils.playClientSound(this.posX, this.posY, this.posZ, block.stepSound.getStepSound(), 0.5f, 1.25f);
                        }
                    }
                }
                this.collidedWithSurface = true;
            }
            this.motionX *= 0.75f;
            this.motionZ *= 0.75f;
        }
    }

    public int getFXLayer() {
        return 1;
    }

    public void renderParticle(Tessellator p_70539_1_, float p_70539_2_, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_) {
        float f1 = ((float) this.particleAge + p_70539_2_) / (float) this.particleMaxAge;
        f1 *= f1;
        float f2 = Math.min(2.0F - f1 * 2.0F, 1.0f);

        float f6 = ((float) this.particleTextureIndexX + this.particleTextureJitterX / 4.0F) / 16.0F;
        float f7 = f6 + 0.015609375F;
        float f8 = ((float) this.particleTextureIndexY + this.particleTextureJitterY / 4.0F) / 16.0F;
        float f9 = f8 + 0.015609375F;
        float f10 = 0.1F * this.particleScale;

        if (this.particleIcon != null) {
            f6 = this.particleIcon.getInterpolatedU(this.particleTextureJitterX / 4.0F * 16.0F);
            f7 = this.particleIcon.getInterpolatedU((this.particleTextureJitterX + 1.0F) / 4.0F * 16.0F);
            f8 = this.particleIcon.getInterpolatedV(this.particleTextureJitterY / 4.0F * 16.0F);
            f9 = this.particleIcon.getInterpolatedV((this.particleTextureJitterY + 1.0F) / 4.0F * 16.0F);
        }

        float f11 = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) p_70539_2_ - interpPosX);
        float f12 = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) p_70539_2_ - interpPosY);
        float f13 = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) p_70539_2_ - interpPosZ);

        int x = MathHelper.floor_double(this.posX);
        int y = MathHelper.floor_double(this.posY);
        int z = MathHelper.floor_double(this.posZ);

        if (this.worldObj.getBlock(x, y, z) instanceof BlockLayer || this.worldObj.getBlock(x, y, z) instanceof BlockSnow) {
            f12 += 0.1f;
        }

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        p_70539_1_.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, f2);
        p_70539_1_.addVertexWithUV(f11 - p_70539_3_ * f10 - p_70539_6_ * f10, f12 - p_70539_4_ * f10, f13 - p_70539_5_ * f10 - p_70539_7_ * f10, f6, f9);
        p_70539_1_.addVertexWithUV(f11 - p_70539_3_ * f10 + p_70539_6_ * f10, f12 + p_70539_4_ * f10, f13 - p_70539_5_ * f10 + p_70539_7_ * f10, f6, f8);
        p_70539_1_.addVertexWithUV(f11 + p_70539_3_ * f10 + p_70539_6_ * f10, f12 + p_70539_4_ * f10, f13 + p_70539_5_ * f10 + p_70539_7_ * f10, f7, f8);
        p_70539_1_.addVertexWithUV(f11 + p_70539_3_ * f10 - p_70539_6_ * f10, f12 - p_70539_4_ * f10, f13 + p_70539_5_ * f10 - p_70539_7_ * f10, f7, f9);
    }
}