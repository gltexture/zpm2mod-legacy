package ru.BouH_.hook.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityBodyHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import ru.BouH_.Main;
import ru.hook.asm.Hook;
import ru.hook.asm.ReturnCondition;

import java.util.List;

public class ModelHook {
    @Hook(returnCondition = ReturnCondition.ALWAYS, createMethod = true)
    public static int getBrightnessForRender(EntityPlayer entityPlayer, float p_70070_1_) {
        int i = MathHelper.floor_double(entityPlayer.posX);
        int j = MathHelper.floor_double(entityPlayer.posZ);
        if (entityPlayer.worldObj.blockExists(i, 0, j)) {
            int k = MathHelper.floor_double(entityPlayer.boundingBox.maxY - 0.2f);
            int k2 = MathHelper.floor_double(entityPlayer.boundingBox.minY + 0.2f);
            return Math.max(entityPlayer.worldObj.getLightBrightnessForSkyBlocks(i, k, j, 0), entityPlayer.worldObj.getLightBrightnessForSkyBlocks(i, k2, j, 0));
        } else {
            return 0;
        }
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    @SuppressWarnings("rawtypes")
    public static void setPositionAndRotation2(Entity en, double p_70056_1_, double p_70056_3_, double p_70056_5_, float p_70056_7_, float p_70056_8_, int p_70056_9_) {
        en.rotationYaw = p_70056_7_ % 360.0F;
        en.rotationPitch = p_70056_8_ % 360.0F;
        if (en.posY != p_70056_3_ && en.posX != p_70056_1_ && en.posZ != p_70056_5_) {
            if (Math.abs(p_70056_3_ - en.posY) >= 0.3f) {
                en.setPosition(p_70056_1_, p_70056_3_, p_70056_5_);
            } else {
                en.setPosition(p_70056_1_, en.posY, p_70056_5_);
            }
        }
        List list = en.worldObj.getCollidingBoundingBoxes(en, en.boundingBox.contract(0.03125D, 0.0D, 0.03125D));

        if (!list.isEmpty()) {
            double d3 = 0.0D;

            for (Object o : list) {
                AxisAlignedBB axisalignedbb = (AxisAlignedBB) o;

                if (axisalignedbb.maxY > d3) {
                    d3 = axisalignedbb.maxY;
                }
            }

            p_70056_3_ += d3 - en.boundingBox.minY;
            en.setPosition(p_70056_1_, p_70056_3_, p_70056_5_);
        }
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void updateRenderAngles(EntityBodyHelper ent) {
        double d0 = ent.theLiving.posX - ent.theLiving.prevPosX;
        double d1 = ent.theLiving.posZ - ent.theLiving.prevPosZ;
        if (d0 * d0 + d1 * d1 > 2.500000277905201E-7D) {
            ent.theLiving.renderYawOffset = func_75665_a(ent.theLiving.prevRotationYaw, ent.theLiving.rotationYaw, 45);
            ent.theLiving.rotationYawHead = func_75665_a(ent.theLiving.renderYawOffset, ent.theLiving.rotationYawHead, 90);
            ent.prevRenderYawHead = ent.theLiving.rotationYawHead;
            ent.rotationTickCounter = 0;
        } else {
            float f = 15.0F;
            if (Math.abs(ent.theLiving.rotationYawHead - ent.prevRenderYawHead) > 15.0F) {
                ent.rotationTickCounter = 0;
                ent.prevRenderYawHead = ent.theLiving.rotationYawHead;
            } else {
                ++ent.rotationTickCounter;

                if (ent.rotationTickCounter > 10) {
                    f = Math.max(1.0F - (float) (ent.rotationTickCounter - 10) / 10.0F, 0.0F) * 75.0F;
                }
            }
            ent.theLiving.renderYawOffset = func_75665_a(ent.theLiving.rotationYawHead, ent.theLiving.renderYawOffset, f);
        }
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void setRotationYawHead(EntityLivingBase en, float rotation) {
        en.rotationYawHead = func_75665_a(en.renderYawOffset, rotation, 90);
    }

    private static float func_75665_a(float p_75665_1_, float p_75665_2_, float p_75665_3_) {
        float f3 = MathHelper.wrapAngleTo180_float(p_75665_1_ - p_75665_2_);

        if (f3 < -p_75665_3_) {
            f3 = -p_75665_3_;
        }

        if (f3 >= p_75665_3_) {
            f3 = p_75665_3_;
        }

        return p_75665_1_ - f3;
    }

    @Hook
    public static void renderFireInFirstPerson(ItemRenderer it, float p_78442_1_) {
        GL11.glTranslatef(0.0f, -0.2f, 0.0f);
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static boolean isInRangeToRenderDist(Entity e, double d) {
        if (Minecraft.getMinecraft().thePlayer == null || e == null) {
            return false;
        }
        if (e instanceof EntityItem) {
            return Minecraft.getMinecraft().thePlayer.getDistanceToEntity(e) <= Main.settingsZp.itemDistance.getValue();
        }
        return Minecraft.getMinecraft().thePlayer.getDistanceToEntity(e) <= Main.settingsZp.entDistance.getValue();
    }
}
