package ru.BouH_.render.mobs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import ru.BouH_.Main;
import ru.BouH_.entity.zombie.EntityModZombie;
import ru.BouH_.gameplay.client.ClientHandler;

@SideOnly(Side.CLIENT)
public class RenderZombieZ extends RenderBiped {
    public static final ResourceLocation vav = new ResourceLocation(Main.MODID + ":structures/misc/rbush1.struct");
    private final ModelBiped field_82434_o;
    protected ModelBiped field_82437_k;
    protected ModelBiped field_82435_l;

    public RenderZombieZ() {
        super(new ModelBiped(), 0.5F, 1.0F);
        this.field_82434_o = this.modelBipedMain;
    }

    protected void func_82421_b() {
        this.modelArmourChestplate = new ModelBiped(1.0F);
        this.field_82425_h = new ModelBiped(0.5F);
        this.field_82437_k = this.modelArmourChestplate;
        this.field_82435_l = this.field_82425_h;
    }

    protected int shouldRenderPass(EntityModZombie p_77032_1_, int p_77032_2_, float p_77032_3_) {
        this.func_82427_a(p_77032_1_);
        return super.shouldRenderPass(p_77032_1_, p_77032_2_, p_77032_3_);
    }

    public void doRender(EntityModZombie p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
        Minecraft mc = Minecraft.getMinecraft();
        if (p_76986_1_ != ClientHandler.instance.zombieTestSys) {
            return;
        }
        if (!mc.isGamePaused()) {
            this.func_82427_a(p_76986_1_);
            super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
        }
    }

    protected ResourceLocation getEntityTexture(EntityModZombie zombie) {
        return vav;
    }

    protected void renderEquippedItems(EntityModZombie p_77029_1_, float p_77029_2_) {
        this.func_82427_a(p_77029_1_);
        super.renderEquippedItems(p_77029_1_, p_77029_2_);
    }

    private void func_82427_a(EntityModZombie p_82427_1_) {
        this.mainModel = this.field_82434_o;
        this.modelArmourChestplate = this.field_82437_k;
        this.field_82425_h = this.field_82435_l;
        this.modelBipedMain = (ModelBiped) this.mainModel;
    }

    protected void rotateCorpse(EntityModZombie p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_) {
        super.rotateCorpse(p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
    }

    protected void renderEquippedItems(EntityLiving p_77029_1_, float p_77029_2_) {
        this.renderEquippedItems((EntityModZombie) p_77029_1_, p_77029_2_);
    }

    protected ResourceLocation getEntityTexture(EntityLiving p_110775_1_) {
        return this.getEntityTexture((EntityModZombie) p_110775_1_);
    }

    public void doRender(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
        this.doRender((EntityModZombie) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }

    protected int shouldRenderPass(EntityLiving p_77032_1_, int p_77032_2_, float p_77032_3_) {
        return this.shouldRenderPass((EntityModZombie) p_77032_1_, p_77032_2_, p_77032_3_);
    }

    protected int shouldRenderPass(EntityLivingBase p_77032_1_, int p_77032_2_, float p_77032_3_) {
        return this.shouldRenderPass((EntityModZombie) p_77032_1_, p_77032_2_, p_77032_3_);
    }

    protected void renderEquippedItems(EntityLivingBase p_77029_1_, float p_77029_2_) {
        this.renderEquippedItems((EntityModZombie) p_77029_1_, p_77029_2_);
    }

    protected void rotateCorpse(EntityLivingBase p_77043_1_, float p_77043_2_, float p_77043_3_, float p_77043_4_) {
        this.rotateCorpse((EntityModZombie) p_77043_1_, p_77043_2_, p_77043_3_, p_77043_4_);
    }

    public void doRender(EntityLivingBase p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
        this.doRender((EntityModZombie) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }

    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return this.getEntityTexture((EntityModZombie) p_110775_1_);
    }

    public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
        this.doRender((EntityModZombie) p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}