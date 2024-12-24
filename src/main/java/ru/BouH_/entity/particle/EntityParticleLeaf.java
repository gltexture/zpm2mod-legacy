package ru.BouH_.entity.particle;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;
import ru.BouH_.Main;
import ru.BouH_.gameplay.client.RenderManager;


@SideOnly(Side.CLIENT)
public class EntityParticleLeaf extends EntityFX {
    public EntityParticleLeaf(World p_i1234_1_, double p_i1234_2_, double p_i1234_4_, double p_i1234_6_, double p_i1234_8_, double p_i1234_10_, double p_i1234_12_) {
        super(p_i1234_1_, p_i1234_2_, p_i1234_4_, p_i1234_6_, p_i1234_8_, p_i1234_10_, p_i1234_12_);
        this.setParticleIcon(RenderManager.leaf_particle[Main.rand.nextInt(2)]);
        this.particleGravity = 0.03f;
        int colorMultiplier = Minecraft.getMinecraft().theWorld.getBiomeGenForCoords((int) p_i1234_2_, (int) p_i1234_6_).getBiomeFoliageColor((int) p_i1234_2_, (int) p_i1234_4_ + 1, (int) p_i1234_6_);
        float r = (float) (colorMultiplier >> 16 & 255) / 255.0f;
        float g = (float) (colorMultiplier >> 8 & 255) / 255.0f;
        float b = (float) (colorMultiplier & 255) / 255.0f;
        this.particleRed = r;
        this.particleGreen = g;
        this.particleBlue = b;
        this.particleScale = 0.6f + Main.rand.nextFloat() * 0.4f;
        this.particleMaxAge = 20;
    }

    public void onUpdate() {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionY = -this.particleGravity;

        if (this.onGround || this.isEntityInsideOpaqueBlock()) {
            this.motionX *= 0.85f;
            this.motionZ *= 0.85f;
            if (this.particleAge++ >= this.particleMaxAge) {
                this.setDead();
            }
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
            f6 = this.particleIcon.getMinU();
            f7 = this.particleIcon.getMaxU();
            f8 = this.particleIcon.getMinV();
            f9 = this.particleIcon.getMaxV();
        }

        float f11 = (float) (this.prevPosX + (this.posX - this.prevPosX) * (double) p_70539_2_ - interpPosX);
        float f12 = (float) (this.prevPosY + (this.posY - this.prevPosY) * (double) p_70539_2_ - interpPosY) + 0.05f;
        float f13 = (float) (this.prevPosZ + (this.posZ - this.prevPosZ) * (double) p_70539_2_ - interpPosZ);

        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(true);
        p_70539_1_.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue, f2);
        p_70539_1_.addVertexWithUV(f11 - p_70539_3_ * f10 - p_70539_6_ * f10, f12 - p_70539_4_ * f10, f13 - p_70539_5_ * f10 - p_70539_7_ * f10, f6, f9);
        p_70539_1_.addVertexWithUV(f11 - p_70539_3_ * f10 + p_70539_6_ * f10, f12 + p_70539_4_ * f10, f13 - p_70539_5_ * f10 + p_70539_7_ * f10, f6, f8);
        p_70539_1_.addVertexWithUV(f11 + p_70539_3_ * f10 + p_70539_6_ * f10, f12 + p_70539_4_ * f10, f13 + p_70539_5_ * f10 + p_70539_7_ * f10, f7, f8);
        p_70539_1_.addVertexWithUV(f11 + p_70539_3_ * f10 - p_70539_6_ * f10, f12 - p_70539_4_ * f10, f13 + p_70539_5_ * f10 - p_70539_7_ * f10, f7, f9);
    }
}