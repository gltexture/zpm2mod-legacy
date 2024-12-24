package ru.BouH_.network.packets.gun;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import ru.BouH_.items.gun.base.AGunBase;
import ru.BouH_.network.SimplePacket;

public class PacketReloadingC extends SimplePacket {
    public PacketReloadingC() {

    }

    public PacketReloadingC(boolean unReloading, int id, ItemStack stack) {
        buf().writeBoolean(unReloading);
        buf().writeInt(id);
        ByteBufUtils.writeItemStack(buf(), stack);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void client(EntityPlayer player) {
        boolean unReloading = buf().readBoolean();
        int id = buf().readInt();
        if (player.worldObj.getEntityByID(id) != null) {
            EntityPlayer pl = (EntityPlayer) player.worldObj.getEntityByID(id);
            ItemStack stack = ByteBufUtils.readItemStack(buf());
            if (pl != player) {
                if (stack != null && stack.hasTagCompound()) {
                    if (stack.getItem() instanceof AGunBase) {
                        ((AGunBase) stack.getItem()).reloadStart(stack, pl, unReloading);
                    }
                }
            }
        }
    }
}
