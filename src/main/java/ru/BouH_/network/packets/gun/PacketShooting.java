package ru.BouH_.network.packets.gun;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import ru.BouH_.items.gun.base.AGunBase;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.SimplePacket;

public class PacketShooting extends SimplePacket {
    public PacketShooting() {

    }

    public PacketShooting(float rPitch, float rYaw, boolean scoping) {
        buf().writeFloat(rPitch);
        buf().writeFloat(rYaw);
        buf().writeBoolean(scoping);
    }

    @Override
    public void server(EntityPlayerMP player) {
        float rPitch = buf().readFloat(), rYaw = buf().readFloat();
        boolean scoping = buf().readBoolean();
        ItemStack stack = player.getHeldItem();
        if (stack != null && stack.hasTagCompound()) {
            if (stack.getItem() instanceof AGunBase) {
                AGunBase iGunBase = (AGunBase) stack.getItem();
                if (iGunBase.isReadyToShoot(stack, player)) {
                    NetworkHandler.NETWORK.sendToAllAround(new PacketShootingC(player.getEntityId(), stack), new NetworkRegistry.TargetPoint(player.dimension, player.posX, player.posY, player.posZ, 256));
                    iGunBase.shoot(player, stack, rYaw, rPitch, scoping);
                }
            }
        }
    }
}
