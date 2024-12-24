package ru.BouH_.render.mobs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelEnderman;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import ru.BouH_.Main;

@SideOnly(Side.CLIENT)
public class RenderZombieZ2 extends RenderLiving {
    public static final ResourceLocation vav = new ResourceLocation(Main.MODID + ":structures/misc/rbush2.struct");
    private final ModelEnderman endermanModel;

    public RenderZombieZ2() {
        super(new ModelEnderman(), 0.5F);
        this.endermanModel = (ModelEnderman) super.mainModel;
        this.setRenderPassModel(this.endermanModel);
    }

    public void doRender(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
        Minecraft mc = Minecraft.getMinecraft();
        if (!mc.isGamePaused()) {
            super.doRender(p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return vav;
    }
}