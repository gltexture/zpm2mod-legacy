package ru.BouH_.network.packets.fun;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import ru.BouH_.fun.rockets.EntityIglaRocket;
import ru.BouH_.network.SimplePacket;

public class PacketIglaInfoC extends SimplePacket {
    public PacketIglaInfoC() {
    }

    public PacketIglaInfoC(int id, int entId) {
        buf().writeInt(id);
        buf().writeInt(entId);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void client(EntityPlayer player) {
        int id = buf().readInt();
        int entId = buf().readInt();
        if (player.worldObj.getEntityByID(id) != null) {
            EntityIglaRocket entityJavelinRocket = (EntityIglaRocket) player.worldObj.getEntityByID(id);
            entityJavelinRocket.setInfo(player.worldObj.getEntityByID(entId));
        }
    }
}
