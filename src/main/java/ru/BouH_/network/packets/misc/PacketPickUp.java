package ru.BouH_.network.packets.misc;

import cpw.mods.fml.common.FMLCommonHandler;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import ru.BouH_.Main;
import ru.BouH_.network.SimplePacket;

import java.util.List;

public class PacketPickUp extends SimplePacket {
    public PacketPickUp() {
    }

    public PacketPickUp(int idItem) {
        buf().writeInt(idItem);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void server(EntityPlayerMP player) {
        int idItem = buf().readInt();
        EntityItem entityItem = (EntityItem) player.worldObj.getEntityByID(idItem);
        if (entityItem != null && !entityItem.isDead && player.isEntityAlive()) {
            AxisAlignedBB axisalignedbb;
            if (player.ridingEntity != null && !player.ridingEntity.isDead) {
                axisalignedbb = player.boundingBox.union(player.ridingEntity.boundingBox).expand(2.0D, 2.0D, 2.0D).offset(0, player.getEyeHeight() - 1.62f, 0);
            } else {
                axisalignedbb = player.boundingBox.expand(2.0D, 0.5D, 2.0D).offset(0, player.getEyeHeight() - 1.62f, 0);
            }
            List<EntityItem> list = player.worldObj.getEntitiesWithinAABB(EntityItem.class, axisalignedbb);
            if (list != null) {
                if (list.contains(entityItem)) {
                    this.pickUpItem(entityItem, player);
                }
            }
        }
    }

    private void pickUpItem(EntityItem entityItem, EntityPlayer player) {
        if (entityItem.delayBeforeCanPickup > 0) {
            return;
        }

        ItemStack itemstack = entityItem.getEntityItem();
        int i = itemstack.stackSize;

        if (entityItem.delayBeforeCanPickup <= 0 && player.inventory.addItemStackToInventory(itemstack)) {
            FMLCommonHandler.instance().firePlayerItemPickupEvent(player, entityItem);
            player.worldObj.playSoundAtEntity(player, "random.pop", 0.2F, ((Main.rand.nextFloat() - Main.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            player.onItemPickup(entityItem, i);
            if (itemstack.stackSize <= 0) {
                entityItem.setDead();
            }
        }
    }
}
