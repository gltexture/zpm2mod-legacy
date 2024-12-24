package ru.BouH_.render.mobs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

@SideOnly(Side.CLIENT)
public class RenderModelStrongZombie extends ModelStrongZombie {
    public RenderModelStrongZombie() {
        this(0.0F, false);
    }

    public RenderModelStrongZombie(float p_i1168_1_, boolean p_i1168_2_) {
        super(p_i1168_1_, 0.0F, 64, p_i1168_2_ ? 32 : 64);
    }

    public void setRotationAngles(float p_78087_1_, float p_78087_2_, float p_78087_3_, float p_78087_4_, float p_78087_5_, float p_78087_6_, Entity p_78087_7_) {
        super.setRotationAngles(p_78087_1_, p_78087_2_, p_78087_3_, p_78087_4_, p_78087_5_, p_78087_6_, p_78087_7_);

        float f6 = MathHelper.sin(this.swingProgress * (float) Math.PI);
        float f7 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * (float) Math.PI);
        this.bipedRightArm.rotateAngleZ = 0.2F;
        this.bipedLeftArm.rotateAngleZ = -0.2F;
        this.bipedRightArm.rotateAngleY = -(0.12F - f6 * 0.6F);
        this.bipedLeftArm.rotateAngleY = 0.12F - f6 * 0.6F;
        this.bipedRightArm.rotateAngleX = -((float) Math.PI / 2.35F);
        this.bipedLeftArm.rotateAngleX = -((float) Math.PI / 2.35F);
        this.bipedRightArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
        this.bipedLeftArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
        this.bipedRightArm.rotateAngleZ += MathHelper.cos(p_78087_3_ * 0.09F) * 0.09F + 0.09F;
        this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(p_78087_3_ * 0.09F) * 0.09F + 0.09F;
        this.bipedRightArm.rotateAngleX += MathHelper.sin(p_78087_3_ * 0.067F) * 0.09F;
        this.bipedLeftArm.rotateAngleX -= MathHelper.sin(p_78087_3_ * 0.067F) * 0.09F;

        this.bipedBody.rotateAngleX = 0.5F;
        this.bipedRightArm.rotateAngleX += 0.4F;
        this.bipedLeftArm.rotateAngleX += 0.4F;
        this.bipedRightLeg.rotationPointZ = 4.0F;
        this.bipedLeftLeg.rotationPointZ = 4.0F;
        this.bipedRightLeg.rotationPointY = 9.0F;
        this.bipedLeftLeg.rotationPointY = 9.0F;
        this.bipedHead.rotationPointY = 1.0F;
        this.bipedHeadwear.rotationPointY = 1.0F;
    }
}
