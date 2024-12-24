package ru.BouH_.network.packets.gun;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import ru.BouH_.items.gun.base.AGunBase;
import ru.BouH_.network.SimplePacket;

public class PacketSwitchFireMode extends SimplePacket {
    public PacketSwitchFireMode() {
    }

    @Override
    public void server(EntityPlayerMP player) {
        ItemStack stack = player.getHeldItem();
        if (stack != null && stack.hasTagCompound()) {
            if (stack.getItem() instanceof AGunBase) {
                ((AGunBase) stack.getItem()).switchFireMode(stack);
            }
        }
    }
}
