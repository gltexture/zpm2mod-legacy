package ru.BouH_.network.packets.gun;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import ru.BouH_.items.gun.base.AGunBase;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.SimplePacket;

public class PacketReloading extends SimplePacket {
    public PacketReloading() {

    }

    public PacketReloading(boolean unReload) {
        buf().writeBoolean(unReload);
    }

    @Override
    public void server(EntityPlayerMP player) {
        boolean unReload = buf().readBoolean();
        ItemStack stack = player.getHeldItem();
        if (stack != null && stack.hasTagCompound()) {
            if (stack.getItem() instanceof AGunBase) {
                AGunBase iGunBase = ((AGunBase) stack.getItem());
                if (iGunBase.canStartReloading(player, stack, unReload)) {
                    NetworkHandler.NETWORK.sendToAllAround(new PacketReloadingC(unReload, player.getEntityId(), stack), new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 256));
                    iGunBase.reloadStart(stack, player, unReload);
                }
            }
        }
    }
}
