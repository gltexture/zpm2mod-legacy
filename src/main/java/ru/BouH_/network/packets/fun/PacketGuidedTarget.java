package ru.BouH_.network.packets.fun;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import ru.BouH_.fun.rockets.EntityKalibrGuidedRocket;
import ru.BouH_.network.SimplePacket;

public class PacketGuidedTarget extends SimplePacket {
    public PacketGuidedTarget() {
    }

    public PacketGuidedTarget(int id, int x, int y, int z) {
        buf().writeInt(id);
        buf().writeInt(x);
        buf().writeInt(y);
        buf().writeInt(z);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void client(EntityPlayer player) {
        int id = buf().readInt();
        int x = buf().readInt();
        int y = buf().readInt();
        int z = buf().readInt();
        if (player.worldObj.getEntityByID(id) != null) {
            EntityKalibrGuidedRocket entityKalibrGuidedRocket = (EntityKalibrGuidedRocket) player.worldObj.getEntityByID(id);
            entityKalibrGuidedRocket.setTarget(x, y, z);
        }
    }
}
