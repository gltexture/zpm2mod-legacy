package ru.BouH_.render.player;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import ru.BouH_.entity.ieep.PlayerMiscData;
import ru.BouH_.gameplay.client.RenderManager;
import ru.BouH_.moving.MovingUtils;
import ru.BouH_.utils.RenderUtils;

@SideOnly(Side.CLIENT)
public class RenderPlayerZp extends RenderPlayer {
    public RenderPlayerZp() {
        this.mainModel = new ModelPlayer(0.0F);
        this.modelBipedMain = (ModelPlayer) this.mainModel;
        this.modelArmorChestplate = new ModelPlayer(1.0F);
        this.modelArmor = new ModelPlayer(0.5F);
    }

    public void renderFirstPersonArm(EntityPlayer p_82441_1_) {
        if (!RenderManager.hideHud) {
            GL11.glRotatef(MathHelper.cos(p_82441_1_.ticksExisted * 0.09f) * 0.5F, 0, 0, 1);
            GL11.glTranslatef(0, 0, MathHelper.cos(p_82441_1_.ticksExisted * 0.025f) * 0.025F);
            RenderUtils.renderArm(true);
        }
    }

    protected int shouldRenderPass(AbstractClientPlayer p_77032_1_, int p_77032_2_, float p_77032_3_) {
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        return super.shouldRenderPass(p_77032_1_, p_77032_2_, p_77032_3_);
    }

    protected void rotateCorpse(AbstractClientPlayer p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_) {
        if (!p_77043_1_.isPlayerSleeping() && MovingUtils.isSwimming(p_77043_1_)) {
            float f2 = (p_77043_1_ instanceof EntityPlayerSP) ? this.interpolateRotation(p_77043_1_.prevRotationYawHead, p_77043_1_.rotationYawHead, p_77043_4_) : p_77043_1_.rotationYaw;
            float f3 = (p_77043_1_ instanceof EntityPlayerSP) ? this.interpolateRotation(p_77043_1_.prevRotationPitch, p_77043_1_.rotationPitch, p_77043_4_) : p_77043_1_.rotationPitch;
            double f5 = MathHelper.cos(-p_77043_1_.rotationPitch * (float) Math.PI / 180.0F);
            float d1 = (float) (MathHelper.cos(f2 / 180.0F * (float) Math.PI) * (1.5f * f5));
            float d2 = (float) (MathHelper.sin(f2 / 180.0F * (float) Math.PI) * (1.5f * f5));
            GL11.glTranslatef(d2, 1.2f + MathHelper.sin(p_77043_1_.rotationPitch / 90) * 1.5f, -d1);
            GL11.glRotatef(270.0f - f2, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-this.getDeathMaxRotation(p_77043_1_), 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-f3, 1.0F, 0.0F, 0.0F);
        } else if (!p_77043_1_.isPlayerSleeping() && PlayerMiscData.getPlayerData(p_77043_1_).isLying()) {
            float f2 = (p_77043_1_ instanceof EntityPlayerSP) ? this.interpolateRotation(p_77043_1_.prevRotationYawHead, p_77043_1_.rotationYawHead, p_77043_4_) : p_77043_1_.rotationYaw;
            float d1 = MathHelper.cos(f2 / 180.0F * (float) Math.PI) * 1.5f;
            float d2 = MathHelper.sin(f2 / 180.0F * (float) Math.PI) * 1.5f;
            GL11.glTranslatef(d2, 1.2f, -d1);
            GL11.glRotatef(270.0f - f2, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-this.getDeathMaxRotation(p_77043_1_), 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(270.0F, 0.0F, 1.0F, 0.0F);
        } else {
            super.rotateCorpse(p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
        }
    }

    private float interpolateRotation(float p_77034_1_, float p_77034_2_, float p_77034_3_) {
        float f3;

        for (f3 = p_77034_2_ - p_77034_1_; f3 < -180.0F; f3 += 360.0F) ;

        while (f3 >= 180.0F) {
            f3 -= 360.0F;
        }

        return p_77034_1_ + p_77034_3_ * f3;
    }

    public void doRender(AbstractClientPlayer p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
        GL11.glDisable(GL11.GL_BLEND);
        float offset = 0.0f;
        if (p_76986_1_ instanceof EntityOtherPlayerMP) {
            if (!p_76986_1_.isPlayerSleeping()) {
                if (PlayerMiscData.getPlayerData(p_76986_1_).isLying() || MovingUtils.isSwimming(p_76986_1_)) {
                    if (p_76986_1_.isSneaking()) {
                        offset = 0.875f;
                    } else {
                        offset = 1.0f;
                    }
                } else if (p_76986_1_.isSneaking()) {
                    offset = 0.2f;
                }
            }
        }
        if (!(p_76986_1_ instanceof EntityPlayerSP && p_76986_1_.isPlayerSleeping())) {
            super.doRender(p_76986_1_, p_76986_2_, p_76986_4_ - offset, p_76986_6_, p_76986_8_, p_76986_9_);
        }
    }
}
