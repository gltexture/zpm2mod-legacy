package ru.BouH_.render.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

@SideOnly(Side.CLIENT)
public class RenderThrowable extends RenderSnowball {
    private final int field_94150_f;
    private IIcon iIcon;
    private Item field_94151_a;

    public RenderThrowable(Item p_i1259_1_, int p_i1259_2_) {
        super(p_i1259_1_, p_i1259_2_);
        this.field_94151_a = p_i1259_1_;
        this.field_94150_f = p_i1259_2_;
    }

    public RenderThrowable(IIcon iIcon, int p_i1259_2_) {
        super(null, p_i1259_2_);
        this.iIcon = iIcon;
        this.field_94150_f = p_i1259_2_;
    }

    public RenderThrowable(IIcon iIcon) {
        this(iIcon, 0);
    }

    public RenderThrowable(Item p_i1260_1_) {
        this(p_i1260_1_, 0);
    }

    public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
        GL11.glPushMatrix();
        this.bindEntityTexture(p_76986_1_);
        GL11.glTranslatef((float) p_76986_2_, (float) p_76986_4_, (float) p_76986_6_);
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glScalef(0.7f, 0.7f, 0.7f);
        IIcon iicon;
        if (this.field_94151_a == null) {
            iicon = this.iIcon;
        } else {
            iicon = this.field_94151_a.getIconFromDamage(this.field_94150_f);
        }
        Tessellator tessellator = Tessellator.instance;
        float f3 = iicon.getMinU();
        float f4 = iicon.getMaxU();
        float f5 = iicon.getMinV();
        float f6 = iicon.getMaxV();
        float f7 = 1.0F;
        float f8 = 0.5F;
        float f9 = 0.25F;
        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        tessellator.addVertexWithUV(0.0F - f8, 0.0F - f9, 0.0D, f3, f6);
        tessellator.addVertexWithUV(f7 - f8, 0.0F - f9, 0.0D, f4, f6);
        tessellator.addVertexWithUV(f7 - f8, 1.0F - f9, 0.0D, f4, f5);
        tessellator.addVertexWithUV(0.0F - f8, 1.0F - f9, 0.0D, f3, f5);
        tessellator.draw();
        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }
}
