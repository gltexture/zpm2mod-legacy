package ru.BouH_.render.mobs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import ru.BouH_.entity.zombie.AZombieBase;
import ru.BouH_.entity.zombie.EntityZombieWolf;
import ru.BouH_.utils.RenderUtils;

@SideOnly(Side.CLIENT)
public class RenderZombieWolfZp extends RenderLiving {
    private final ModelWolfZombie model;
    protected ModelBase greenModel;

    public RenderZombieWolfZp(ModelWolfZombie model) {
        super(model, 0.5F);
        this.setRenderPassModel(model);
        this.model = model;
        this.greenModel = new ModelWolfZombie(0.65f);
    }

    protected float handleRotationFloat(EntityZombieWolf p_77044_1_, float p_77044_2_) {
        return (float) Math.PI / 5F;
    }

    protected ResourceLocation getEntityTexture(EntityZombieWolf p_110775_1_) {
        return p_110775_1_.getResourceLocation();
    }

    protected int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_) {
        return -1;
    }


    protected void renderEquippedItems(EntityLivingBase p_77029_1_, float p_77029_2_) {
        GL11.glColor3f(1.0F, 1.0F, 1.0F);
        super.renderEquippedItems(p_77029_1_, p_77029_2_);
        ItemStack itemstack = p_77029_1_.getHeldItem();
        float f1;

        if (itemstack != null && itemstack.getItem() != null) {
            GL11.glPushMatrix();

            this.model.wolfHeadMain.postRender(0.0625F);
            GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);
            f1 = 0.375F;
            GL11.glTranslatef(0.2F, -0.25F, -0.1875F);
            GL11.glScalef(f1, f1, f1);
            GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);


            float f2;
            int i;
            float f5;

            if (itemstack.getItem().requiresMultipleRenderPasses()) {
                for (i = 0; i < itemstack.getItem().getRenderPasses(itemstack.getMetadata()); ++i) {
                    int j = itemstack.getItem().getColorFromItemStack(itemstack, i);
                    f5 = (float) (j >> 16 & 255) / 255.0F;
                    f2 = (float) (j >> 8 & 255) / 255.0F;
                    float f3 = (float) (j & 255) / 255.0F;
                    GL11.glColor4f(f5, f2, f3, 1.0F);
                    this.renderManager.itemRenderer.renderItem(p_77029_1_, itemstack, i);
                }
            } else {
                i = itemstack.getItem().getColorFromItemStack(itemstack, 0);
                float f4 = (float) (i >> 16 & 255) / 255.0F;
                f5 = (float) (i >> 8 & 255) / 255.0F;
                f2 = (float) (i & 255) / 255.0F;
                GL11.glColor4f(f4, f5, f2, 1.0F);
                this.renderManager.itemRenderer.renderItem(p_77029_1_, itemstack, 0);
            }

            GL11.glPopMatrix();
        }
    }

    protected void renderModel(EntityLivingBase p_77036_1_, float p_77036_2_, float p_77036_3_, float p_77036_4_, float p_77036_5_, float p_77036_6_, float p_77036_7_) {
        super.renderModel(p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
        if (p_77036_1_ instanceof AZombieBase) {
            AZombieBase aZombieBase = (AZombieBase) p_77036_1_;
            if (aZombieBase.radiationModified()) {
                float partial = RenderUtils.partialTicks;
                float f7 = p_77036_1_.limbSwing - p_77036_1_.limbSwingAmount * (1.0F - partial);
                float f6 = p_77036_1_.prevLimbSwingAmount + (p_77036_1_.limbSwingAmount - p_77036_1_.prevLimbSwingAmount) * partial;
                if (f6 > 1.0F) {
                    f6 = 1.0F;
                }
                if (!p_77036_1_.isInvisible()) {
                    float f8 = (float) p_77036_1_.ticksExisted + RenderUtils.partialTicks;
                    this.bindTexture(RenderZombieZp.RES_ITEM_GLINT);
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glEnable(GL11.GL_ALPHA_TEST);
                    GL11.glEnable(GL11.GL_DEPTH_TEST);
                    GL11.glDepthFunc(GL11.GL_LESS);
                    GL11.glDepthMask(false);
                    for (int k = 0; k < 2; k++) {
                        GL11.glDisable(GL11.GL_LIGHTING);
                        if (aZombieBase.getModifierId() == 1) {
                            GL11.glColor4f(0.3f, 0.37f, 0.15f, 1.0F);
                        } else {
                            GL11.glColor4f(0.32f, 0.5f, 0.18f, 1.0F);
                        }
                        GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
                        GL11.glMatrixMode(GL11.GL_TEXTURE);
                        GL11.glLoadIdentity();
                        float f11 = f8 * (0.001F + (float) k * 1.0e-5f) * 10.0F;
                        float f12 = 1.5F;
                        GL11.glScalef(f12, f12, f12);
                        GL11.glRotatef(30.0F - (float) k * 60.0F, 0.0F, 0.0F, 1.0F);
                        GL11.glTranslatef(0.0F, f11, 0.0F);
                        GL11.glMatrixMode(GL11.GL_MODELVIEW);
                        this.greenModel.isChild = false;
                        this.greenModel.swingProgress = this.getSwingProgress(p_77036_1_, partial);
                        this.greenModel.setLivingAnimations(p_77036_1_, f7, f6, 0);
                        this.greenModel.render(p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
                    }
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    GL11.glMatrixMode(GL11.GL_TEXTURE);
                    GL11.glDepthMask(true);
                    GL11.glLoadIdentity();
                    GL11.glMatrixMode(GL11.GL_MODELVIEW);
                    GL11.glEnable(GL11.GL_LIGHTING);
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glDepthFunc(GL11.GL_LEQUAL);
                }
            }
        }
    }

    protected float handleRotationFloat(EntityLivingBase p_77044_1_, float p_77044_2_) {
        return this.handleRotationFloat((EntityZombieWolf) p_77044_1_, p_77044_2_);
    }

    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return this.getEntityTexture((EntityZombieWolf) p_110775_1_);
    }
}