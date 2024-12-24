package ru.BouH_.network.packets.gun;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import ru.BouH_.items.gun.base.AGunBase;
import ru.BouH_.network.SimplePacket;

public class PacketReloadInterrupt extends SimplePacket {
    public PacketReloadInterrupt() {
    }

    @Override
    public void server(EntityPlayerMP player) {
        ItemStack stack = player.getHeldItem();
        if (stack.getItem() instanceof AGunBase) {
            AGunBase aGunBase = ((AGunBase) stack.getItem());
            if (aGunBase.canInterruptReloading(player, stack)) {
                player.getEntityData().setBoolean("interruptReloading", true);
            }
        }
    }
}