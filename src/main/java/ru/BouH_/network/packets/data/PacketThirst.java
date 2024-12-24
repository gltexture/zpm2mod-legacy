package ru.BouH_.network.packets.data;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import ru.BouH_.entity.ieep.Thirst;
import ru.BouH_.network.SimplePacket;

public class PacketThirst extends SimplePacket {

    public PacketThirst() {
    }

    public PacketThirst(int thirst) {
        buf().writeInt(thirst);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void client(EntityPlayer player) {
        Thirst.getThirst(player).setThirst(buf().readInt());
    }
}
