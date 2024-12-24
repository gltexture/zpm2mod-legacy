package ru.BouH_.hook.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import org.lwjgl.opengl.GL11;
import ru.BouH_.entity.ieep.PlayerMiscData;
import ru.BouH_.utils.RenderUtils;
import ru.hook.asm.Hook;
import ru.hook.asm.ReturnCondition;

public class ItemHook {
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void updateEquippedItem(ItemRenderer it) {
        it.prevEquippedProgress = it.equippedProgress;
        EntityClientPlayerMP entityclientplayermp = it.mc.thePlayer;
        ItemStack itemstack = entityclientplayermp.inventory.getCurrentItem();
        boolean flag = false;
        if (it.itemToRender != null && itemstack != null) {
            if (it.itemToRender != itemstack && (it.equippedItemSlot != entityclientplayermp.inventory.currentItem || it.itemToRender.getItem() != itemstack.getItem())) {
                flag = true;
            } else {
                it.itemToRender = itemstack;
            }
        } else {
            flag = it.itemToRender != null || itemstack != null;
        }

        float f = 0.4F;
        float f1 = flag ? 0.0F : 1.0F;
        float f2 = MathHelper.clamp_float(f1 - it.equippedProgress, -f, f);
        it.equippedProgress += f2;

        if (it.equippedProgress < 0.1F) {
            it.itemToRender = itemstack;
            it.equippedItemSlot = entityclientplayermp.inventory.currentItem;
        }
    }
    @Hook
    public static void renderItem(ItemRenderer it, EntityLivingBase entityIn, ItemStack stackToRender, int p_78443_3_, IItemRenderer.ItemRenderType type) {
        if (entityIn instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityIn;
            if (type == IItemRenderer.ItemRenderType.EQUIPPED_FIRST_PERSON) {
                int i = player.getBrightnessForRender(1.0f);
                int j = i % 65536;
                int k = i / 65536;
                OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
                GL11.glDisable(GL11.GL_CULL_FACE);
                if (!stackToRender.getItem().requiresMultipleRenderPasses()) {
                    float f1 = player.isSneaking() ? 0.5f : PlayerMiscData.getPlayerData(player).isLying() ? 0.1f : 1.0f;
                    GL11.glRotatef(MathHelper.cos(player.ticksExisted * 0.09f) * 0.5F * f1, 1, 0, 0);
                    GL11.glTranslatef(0, 0, MathHelper.cos(player.ticksExisted * 0.025f) * 0.025F * f1);
                    float f4 = Minecraft.getMinecraft().thePlayer.prevRenderArmYaw + (Minecraft.getMinecraft().thePlayer.renderArmYaw - Minecraft.getMinecraft().thePlayer.prevRenderArmYaw) * RenderUtils.partialTicks;
                    float f3 = Minecraft.getMinecraft().thePlayer.prevRenderArmPitch + (Minecraft.getMinecraft().thePlayer.renderArmPitch - Minecraft.getMinecraft().thePlayer.prevRenderArmPitch) * RenderUtils.partialTicks;
                    float r = MathHelper.clamp_float((player.rotationYaw - f4) * 0.175f, -8, 8);
                    float r2 = MathHelper.clamp_float((player.rotationPitch - f3) * 0.175f, -8, 8);
                    GL11.glRotatef(r, 0, 1, 0);
                    GL11.glRotatef(r2, 1, 0, 1);
                    IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(stackToRender, type);
                    if (customRenderer == null) {
                        if (!(player.isUsingItem() && player.getItemInUseCount() > 0)) {
                            if (stackToRender.getItem() instanceof ItemTool || stackToRender.getItem() instanceof ItemFishingRod || stackToRender.getItem() instanceof ItemHoe || stackToRender.getItem() instanceof ItemSword || stackToRender.getItem() instanceof ItemTool || stackToRender.getItem() instanceof ItemBow) {
                                GL11.glRotatef(-4.5f, 1, 0, 0);
                                GL11.glRotatef(-2.5f, 0, 0, 1);
                                GL11.glTranslatef(0.1f, 0.1f, -0.2f);
                            } else {
                                GL11.glRotatef(5, 1, 0, 0);
                                GL11.glRotatef(40, 0, 1, 0);
                                GL11.glTranslatef(0.4f, 0, -0.3f);
                            }
                        }
                    }
                }
            }
        }
    }
}
