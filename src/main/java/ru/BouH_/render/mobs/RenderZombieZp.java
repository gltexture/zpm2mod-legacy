package ru.BouH_.render.mobs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import ru.BouH_.Main;
import ru.BouH_.entity.zombie.AZombieBase;
import ru.BouH_.entity.zombie.EntityZombieCitizen;
import ru.BouH_.utils.RenderUtils;

@SideOnly(Side.CLIENT)
public class RenderZombieZp extends RenderBiped {
    public static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    public static final ResourceLocation villagerTexture = new ResourceLocation("textures/entity/zombie/zombie_villager.png");
    public static final ResourceLocation april = new ResourceLocation(Main.MODID + ":textures/entity/april/zombie0.png");
    private final ModelBiped field_82434_o;
    protected ModelBiped field_82437_k;
    protected ModelBiped field_82435_l;
    protected ModelBiped field_82436_m;
    protected ModelBiped field_82433_n;
    protected ModelBiped greenComModel;
    protected ModelBiped greenVilModel;
    private RenderModelVillagerZombie zombieVillagerModel;
    private int field_82431_q = 1;

    public RenderZombieZp() {
        super(new RenderModelZombie(), 0.5F, 1.0F);
        this.field_82434_o = this.modelBipedMain;
        this.zombieVillagerModel = new RenderModelVillagerZombie();
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
                    this.bindTexture(RES_ITEM_GLINT);
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
                        ModelBiped modelToShow = this.greenComModel;
                        if (p_77036_1_ instanceof EntityZombieCitizen && ((EntityZombieCitizen) p_77036_1_).isVillager()) {
                            modelToShow = this.greenVilModel;
                        }
                        modelToShow.isChild = false;
                        modelToShow.swingProgress = this.getSwingProgress(p_77036_1_, partial);
                        modelToShow.setLivingAnimations(p_77036_1_, f7, f6, 0);
                        modelToShow.render(p_77036_1_, p_77036_2_, p_77036_3_, p_77036_4_, p_77036_5_, p_77036_6_, p_77036_7_);
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

    protected void func_82421_b() {
        this.modelArmourChestplate = new RenderModelZombie(1.0F, true);
        this.field_82425_h = new RenderModelZombie(0.5F, true);
        this.field_82437_k = this.modelArmourChestplate;
        this.field_82435_l = this.field_82425_h;
        this.greenVilModel = new RenderModelVillagerZombie(0.15F, 0.0f, true);
        this.greenComModel = new RenderModelZombie(0.15F, true);
        this.field_82436_m = new RenderModelVillagerZombie(1.0F, 0.0F, true);
        this.field_82433_n = new RenderModelVillagerZombie(0.5F, 0.0F, true);
    }

    protected int shouldRenderPass(AZombieBase p_77032_1_, int p_77032_2_, float p_77032_3_) {
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        this.func_82427_a(p_77032_1_);
        return super.shouldRenderPass(p_77032_1_, p_77032_2_, p_77032_3_);
    }

    public void doRender(AZombieBase p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
        GL11.glDisable(GL11.GL_BLEND);
        this.func_82427_a(p_76986_1_);
        super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }

    protected ResourceLocation getEntityTexture(AZombieBase zombie) {
        if (zombie instanceof EntityZombieCitizen && ((EntityZombieCitizen) zombie).isVillager()) {
            return RenderZombieZp.villagerTexture;
        }
        return Main.isApril1() ? april : zombie.getResourceLocation();
    }

    protected void renderEquippedItems(AZombieBase p_77029_1_, float p_77029_2_) {
        this.func_82427_a(p_77029_1_);
        super.renderEquippedItems(p_77029_1_, p_77029_2_);
    }

    private void func_82427_a(AZombieBase p_82427_1_) {
        if (p_82427_1_ instanceof EntityZombieCitizen && ((EntityZombieCitizen) p_82427_1_).isVillager()) {
            if (this.field_82431_q != this.zombieVillagerModel.func_82897_a()) {
                this.zombieVillagerModel = new RenderModelVillagerZombie();
                this.field_82431_q = this.zombieVillagerModel.func_82897_a();
                this.field_82436_m = new RenderModelVillagerZombie(1.0F, 0.0F, true);
                this.field_82433_n = new RenderModelVillagerZombie(0.5F, 0.0F, true);
            }
            this.mainModel = this.zombieVillagerModel;
            this.modelArmourChestplate = this.field_82436_m;
            this.field_82425_h = this.field_82433_n;
        } else {
            this.mainModel = this.field_82434_o;
            this.modelArmourChestplate = this.field_82437_k;
            this.field_82425_h = this.field_82435_l;
        }
        this.modelBipedMain = (ModelBiped) this.mainModel;
    }

    protected void rotateCorpse(AZombieBase p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_) {
        if (p_77043_1_ instanceof EntityZombieCitizen && ((EntityZombieCitizen) p_77043_1_).isConverting()) {
            p_77043_3_ += (float) (Math.cos((double) p_77043_1_.ticksExisted * 3.25D) * Math.PI * 0.25D);
        }
        super.rotateCorpse(p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
    }

    protected void renderEquippedItems(EntityLiving p_77029_1_, float p_77029_2_) {
        this.renderEquippedItems((AZombieBase) p_77029_1_, p_77029_2_);
    }

    protected ResourceLocation getEntityTexture(EntityLiving p_110775_1_) {
        return this.getEntityTexture((AZombieBase) p_110775_1_);
    }

    public void doRender(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
        this.doRender((AZombieBase) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }

    protected int shouldRenderPass(EntityLiving p_77032_1_, int p_77032_2_, float p_77032_3_) {
        return this.shouldRenderPass((AZombieBase) p_77032_1_, p_77032_2_, p_77032_3_);
    }

    protected int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_) {
        return this.shouldRenderPass((AZombieBase) p_77032_1_, p_77032_2_, p_77032_3_);
    }

    protected void renderEquippedItems(EntityLivingBase p_77029_1_, float p_77029_2_) {
        this.renderEquippedItems((AZombieBase) p_77029_1_, p_77029_2_);
    }

    protected void rotateCorpse(EntityLivingBase p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_) {
        this.rotateCorpse((AZombieBase) p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
    }

    public void doRender(EntityLivingBase p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
        this.doRender((AZombieBase) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }

    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return this.getEntityTexture((AZombieBase) p_110775_1_);
    }

    public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
        this.doRender((AZombieBase) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}