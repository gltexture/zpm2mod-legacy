package ru.BouH_.moving;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraftforge.event.entity.living.LivingEvent;
import ru.BouH_.entity.ieep.PlayerMiscData;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.packets.moving.PacketLyingC;

public class LivingMovingEvents {
    public static LivingMovingEvents instance = new LivingMovingEvents();

    @SubscribeEvent()
    public void onUpdate(LivingEvent.LivingUpdateEvent ev) {
        EntityLivingBase ent = ev.entityLiving;
        if (ent != null && ent.isEntityAlive()) {
            if (ent instanceof EntityPlayer) {
                PlayerMiscData playerMiscData = PlayerMiscData.getPlayerData((EntityPlayer) ent);
                EntityPlayer pl = (EntityPlayer) ent;
                if (playerMiscData.isLying() || MovingUtils.isSwimming(pl)) {
                    this.setSize(pl, 0.6f, 0.8f);
                } else {
                    if (pl.isSneaking()) {
                        this.setSize(pl, 0.6f, 1.5f);
                    } else {
                        this.setSize(pl, 0.6F, 1.8F);
                    }
                }
                if (pl.worldObj.isRemote) {
                    this.setInput();
                    if (MovingUtils.isSwimming(pl)) {
                        if (MovingInput.instance.moveForward > 0) {
                            pl.motionX = -MathHelper.sin(pl.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(pl.rotationPitch / 180.0F * (float) Math.PI) * MovingInput.instance.moveForward * 0.2f;
                            pl.motionZ = MathHelper.cos(pl.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(pl.rotationPitch / 180.0F * (float) Math.PI) * MovingInput.instance.moveForward * 0.2f;
                            pl.motionY = -MathHelper.sin(pl.rotationPitch / 180.0F * (float) Math.PI) * MovingInput.instance.moveForward * 0.2f;
                        } else {
                            pl.motionY -= 1.0E-4D;
                        }
                    }
                } else {
                    NetworkHandler.NETWORK.sendToAllAround(new PacketLyingC(pl.getEntityId(), playerMiscData.isLying()), new NetworkRegistry.TargetPoint(pl.dimension, pl.posX, pl.posY, pl.posZ, 256));
                }
            }
        }
    }

    private void setSize(Entity entity, float width, float height) {
        float f2;

        if (width != entity.width || height != entity.height) {
            f2 = entity.width;
            entity.width = width;
            entity.height = height;
            entity.boundingBox.maxX = entity.boundingBox.minX + (double) entity.width;
            entity.boundingBox.maxZ = entity.boundingBox.minZ + (double) entity.width;
            entity.boundingBox.maxY = entity.boundingBox.minY + (double) entity.height;

            if (entity.width > f2 && !entity.firstUpdate && !entity.worldObj.isRemote) {
                entity.moveEntity(f2 - entity.width, 0.0D, f2 - entity.width);
            }
        }

        f2 = width % 2.0F;

        if ((double) f2 < 0.375D) {
            entity.myEntitySize = Entity.EnumEntitySize.SIZE_1;
        } else if ((double) f2 < 0.75D) {
            entity.myEntitySize = Entity.EnumEntitySize.SIZE_2;
        } else if ((double) f2 < 1.0D) {
            entity.myEntitySize = Entity.EnumEntitySize.SIZE_3;
        } else if ((double) f2 < 1.375D) {
            entity.myEntitySize = Entity.EnumEntitySize.SIZE_4;
        } else if ((double) f2 < 1.75D) {
            entity.myEntitySize = Entity.EnumEntitySize.SIZE_5;
        } else {
            entity.myEntitySize = Entity.EnumEntitySize.SIZE_6;
        }
    }

    @SideOnly(Side.CLIENT)
    private void setInput() {
        if (Minecraft.getMinecraft().thePlayer.movementInput != MovingInput.instance) {
            Minecraft.getMinecraft().thePlayer.movementInput = MovingInput.instance;
        }
    }
}
