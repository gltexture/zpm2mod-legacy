package ru.BouH_.network.packets.fun;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import ru.BouH_.fun.rockets.EntityJavelinRocket;
import ru.BouH_.network.SimplePacket;

public class PacketJavelinInfoC extends SimplePacket {
    public PacketJavelinInfoC() {
    }

    public PacketJavelinInfoC(int id, int entId) {
        buf().writeInt(id);
        buf().writeInt(entId);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void client(EntityPlayer player) {
        int id = buf().readInt();
        int entId = buf().readInt();
        if (player.worldObj.getEntityByID(id) != null) {
            EntityJavelinRocket entityJavelinRocket = (EntityJavelinRocket) player.worldObj.getEntityByID(id);
            entityJavelinRocket.setInfo(player.worldObj.getEntityByID(entId));
        }
    }
}
