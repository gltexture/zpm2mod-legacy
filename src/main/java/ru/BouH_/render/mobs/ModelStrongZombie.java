package ru.BouH_.render.mobs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.entity.Entity;

@SideOnly(Side.CLIENT)
public class ModelStrongZombie extends ModelZombie {
    protected ModelStrongZombie(float p_i1167_1_, float p_i1167_2_, int p_i1167_3_, int p_i1167_4_) {
        super(p_i1167_1_, p_i1167_2_, p_i1167_3_, p_i1167_4_);
        this.textureWidth = 128;
        this.textureHeight = 128;
        short short1 = 128;
        short short2 = 128;

        this.bipedHead = (new ModelRenderer(this)).setTextureSize(short1, short2);
        this.bipedHead.setRotationPoint(0.0F, 0.0F + p_i1167_2_, -2.0F);
        this.bipedHead.setTextureOffset(0, 0).addBox(-4.0F, -12.0F, -2.5F, 8, 8, 8, p_i1167_1_);

        this.bipedHeadwear.showModel = false;

        this.bipedRightArm = (new ModelRenderer(this)).setTextureSize(short1, short2);
        this.bipedRightArm.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.bipedRightArm.setTextureOffset(60, 21).addBox(-5.725F, 0.0F, 4.0F, 4, 24, 6, p_i1167_1_);

        this.bipedLeftArm = (new ModelRenderer(this)).setTextureSize(short1, short2);
        this.bipedLeftArm.setRotationPoint(0.0F, -7.0F, 0.0F);
        this.bipedLeftArm.setTextureOffset(60, 58).addBox(2.0F, 0.0F, 4.0F, 4, 24, 6, p_i1167_1_);

        this.bipedBody = (new ModelRenderer(this)).setTextureSize(short1, short2);
        this.bipedBody.setRotationPoint(0.0F, 0.0F + p_i1167_2_, 0.0F);
        this.bipedBody.setTextureOffset(0, 40).addBox(-9.0F, -4.0F, -6.0F, 18, 12, 11, p_i1167_1_);
        this.bipedBody.setTextureOffset(0, 70).addBox(-4.5F, 8.0F, -3.0F, 9, 5, 6, p_i1167_1_ + 0.5F);

        this.bipedLeftLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(short1, short2);
        this.bipedLeftLeg.setRotationPoint(-4.0F, 18.0F + p_i1167_2_, 0.0F);
        this.bipedLeftLeg.setTextureOffset(37, 0).addBox(-3.5F, -1.0F, -3.0F, 6, 16, 5, p_i1167_1_);

        this.bipedRightLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(short1, short2);
        this.bipedRightLeg.mirror = true;
        this.bipedRightLeg.setTextureOffset(60, 0).setRotationPoint(5.0F, 18.0F + p_i1167_2_, 0.0F);
        this.bipedRightLeg.addBox(-3.5F, -1.0F, -3.0F, 6, 16, 5, p_i1167_1_);
    }

    public void render(Entity p_78088_1_, float p_78088_2_, float p_78088_3_, float p_78088_4_, float p_78088_5_, float p_78088_6_, float p_78088_7_) {

        this.setRotationAngles(p_78088_2_, p_78088_3_, p_78088_4_, p_78088_5_, p_78088_6_, p_78088_7_, p_78088_1_);
        this.bipedHead.render(p_78088_7_);
        this.bipedBody.render(p_78088_7_);
        this.bipedRightArm.render(p_78088_7_);
        this.bipedLeftArm.render(p_78088_7_);
        this.bipedRightLeg.render(p_78088_7_);
        this.bipedLeftLeg.render(p_78088_7_);
        this.bipedHeadwear.render(p_78088_7_);
    }
}