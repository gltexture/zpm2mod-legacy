package ru.BouH_.network.packets.fun;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import ru.BouH_.fun.rockets.base.EntityRocketOwnable;
import ru.BouH_.network.SimplePacket;

public class PacketRocketOwner extends SimplePacket {
    public PacketRocketOwner() {
    }

    public PacketRocketOwner(int id) {
        buf().writeInt(id);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void client(EntityPlayer player) {
        int id = buf().readInt();
        if (player.worldObj.getEntityByID(id) != null) {
            EntityRocketOwnable entityRocketOwnable = (EntityRocketOwnable) player.worldObj.getEntityByID(id);
            entityRocketOwnable.setOwner(player.getDisplayName());
        }
    }
}
