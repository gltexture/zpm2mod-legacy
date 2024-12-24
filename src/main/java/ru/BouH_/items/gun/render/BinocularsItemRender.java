package ru.BouH_.items.gun.render;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.event.FOVUpdateEvent;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import ru.BouH_.utils.RenderUtils;

public class BinocularsItemRender implements IItemRenderer {
    public static BinocularsItemRender instance = new BinocularsItemRender();
    public boolean isScoping;
    public float scope;
    public float scopePrev;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent ev) {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        if (ev.phase == TickEvent.Phase.START) {
            if (player != null) {
                this.scopePrev = this.scope;
            }
        }
    }

    @SubscribeEvent
    public void onFov(FOVUpdateEvent e) {
        if (this.isInScope()) {
            e.newfov -= 0.8f;
        }
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type == ItemRenderType.EQUIPPED_FIRST_PERSON || type == ItemRenderType.EQUIPPED;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack itemRendering, Object... data) {
        Tessellator tessellator = Tessellator.instance;
        EntityLivingBase entity = (EntityLivingBase) data[1];
        IIcon icon = entity.getItemIcon(itemRendering, 0);
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            if (player instanceof EntityPlayerSP) {
                if (this.isScoping) {
                    this.scope = Math.min(this.scopePrev + 4.0f, 25.0f);
                } else {
                    this.scope = Math.max(this.scopePrev - 8.0f, 0);
                }
                if (Mouse.isButtonDown(1) && player.getHeldItem() == itemRendering && this.canStartScoping(player)) {
                    this.startScopeAnimation();
                } else {
                    this.stopScopeAnimation();
                }
            }
            if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
                if (icon != null) {
                    float scopeConst = this.scopePrev + (this.scope - this.scopePrev) * RenderUtils.partialTicks;
                    if (!this.isInScope()) {
                        GL11.glRotatef(-scopeConst / 5, 0, 1, 0);
                        GL11.glRotatef(scopeConst / 5, 1, 1, 0);
                        GL11.glTranslatef(-scopeConst / 65, -scopeConst / 200, -scopeConst / 82);

                        GL11.glRotatef(80, 1, 0, 0);
                        GL11.glRotatef(45, 0, 1, 0);
                        GL11.glRotatef(-115, 0, 0, 1);
                        GL11.glTranslatef(-0.7f, 0.4f, 0.0f);
                        this.renderItem(tessellator, icon);
                    }
                }
            }
        }
        if (type == ItemRenderType.EQUIPPED) {
            GL11.glPushMatrix();
            GL11.glScalef(1.2f, 1.2f, 1.2f);
            GL11.glTranslatef(1.5f, 0.225f, -0.2f);
            GL11.glRotatef(215, 0, 1, 1);
            GL11.glRotatef(25, 0, 1, 0);
            GL11.glRotatef(110, 1, 0, 0);
            GL11.glRotatef(240, 1, 1, 0);
            this.renderItem(tessellator, icon);
            GL11.glPopMatrix();
        }
    }

    private boolean canStartScoping(EntityPlayer player) {
        return Minecraft.getMinecraft().entityRenderer.itemRenderer.equippedProgress == 1.0f && !player.isSprinting() && Minecraft.getMinecraft().currentScreen == null && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0;
    }

    public void startScopeAnimation() {
        this.isScoping = true;
    }

    public void stopScopeAnimation() {
        this.isScoping = false;
    }

    public boolean isInScope() {
        return this.scope >= 25;
    }

    private void renderItem(Tessellator tessellator, IIcon icon) {
        ItemRenderer.renderItemIn2D(tessellator, icon.getMaxU(), icon.getMinV(), icon.getMinU(), icon.getMaxV(), icon.getIconWidth(), icon.getIconHeight(), 0.12f);
    }
}
