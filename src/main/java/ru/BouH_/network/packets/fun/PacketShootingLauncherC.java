package ru.BouH_.network.packets.fun;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import ru.BouH_.items.gun.base.fun.ALauncherBase;
import ru.BouH_.network.SimplePacket;

public class PacketShootingLauncherC extends SimplePacket {
    public PacketShootingLauncherC() {

    }

    public PacketShootingLauncherC(int id) {
        buf().writeInt(id);
    }

    @Override
    public void client(EntityPlayer player) {
        int id = buf().readInt();
        if (player.worldObj.getEntityByID(id) != null) {
            EntityPlayer pl = (EntityPlayer) player.worldObj.getEntityByID(id);
            ItemStack stack = pl.getHeldItem();
            if (pl != player) {
                if (stack != null && stack.hasTagCompound()) {
                    if (stack.getItem() instanceof ALauncherBase) {
                        ((ALauncherBase) stack.getItem()).shoot2(pl, stack, null);
                    }
                }
            }
        }
    }
}
