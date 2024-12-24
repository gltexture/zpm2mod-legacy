package ru.BouH_.network.packets.gun;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import ru.BouH_.items.gun.base.AGunBase;
import ru.BouH_.network.SimplePacket;

public class PacketResetGun extends SimplePacket {
    public PacketResetGun() {
    }

    public PacketResetGun(int plId) {
        buf().writeInt(plId);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void client(EntityPlayer player) {
        int plId = buf().readInt();
        if (player.worldObj.getEntityByID(plId) != null) {
            EntityPlayer entityPlayer = (EntityPlayer) player.worldObj.getEntityByID(plId);
            if (entityPlayer != player) {
                AGunBase.resetPartiallyGun(entityPlayer);
            }
        }
    }
}
