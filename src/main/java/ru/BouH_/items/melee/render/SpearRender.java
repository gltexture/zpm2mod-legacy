package ru.BouH_.items.melee.render;

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public class SpearRender implements IItemRenderer {
    public static SpearRender instance = new SpearRender();
    public int recoil;

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack itemRendering, Object... data) {
        Tessellator tessellator = Tessellator.instance;
        EntityLivingBase entity = (EntityLivingBase) data[1];
        if (itemRendering != null) {
            IIcon icon = entity.getItemIcon(itemRendering, 0);
            if (icon != null) {
                if (entity instanceof EntityPlayer) {
                    if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
                        GL11.glPushMatrix();
                        GL11.glRotatef(-90.0f, 0.0f, 0.0f, 1.0f);
                        GL11.glTranslatef(-1.0f, -0.1f, -0.1f);
                        this.renderWeapon(tessellator, icon);
                        GL11.glPopMatrix();
                    } else {
                        this.renderWeapon(tessellator, icon);
                    }
                }
            }
        }
    }

    private void renderWeapon(Tessellator tessellator, IIcon gunIcon) {
        ItemRenderer.renderItemIn2D(tessellator, gunIcon.getMaxU(), gunIcon.getMinV(), gunIcon.getMinU(), gunIcon.getMaxV(), gunIcon.getIconWidth(), gunIcon.getIconHeight(), 0.08f);
    }
}
