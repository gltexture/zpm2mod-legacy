package ru.BouH_.network.packets.fun;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import ru.BouH_.items.gun.base.fun.ALauncherBase;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.SimplePacket;

public class PacketShootingLauncher extends SimplePacket {
    public PacketShootingLauncher() {

    }

    public PacketShootingLauncher(int entId) {
        buf().writeInt(entId);
    }

    @Override
    public void server(EntityPlayerMP player) {
        int entId = buf().readInt();
        ItemStack stack = player.getHeldItem();
        if (stack != null && stack.hasTagCompound()) {
            if (stack.getItem() instanceof ALauncherBase) {
                ALauncherBase iGunBase = (ALauncherBase) stack.getItem();
                if (iGunBase.isReadyToShoot(stack, player)) {
                    NetworkHandler.NETWORK.sendToAllAround(new PacketShootingLauncherC(player.getEntityId()), new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 256));
                    iGunBase.shoot2(player, stack, player.worldObj.getEntityByID(entId));
                }
            }
        }
    }
}
