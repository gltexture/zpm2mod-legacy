package ru.BouH_.network.packets.data;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import ru.BouH_.entity.ieep.Hunger;
import ru.BouH_.network.SimplePacket;

public class PacketHunger extends SimplePacket {

    public PacketHunger() {
    }

    public PacketHunger(int hunger) {
        buf().writeInt(hunger);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void client(EntityPlayer player) {
        Hunger.getHunger(player).setHunger(buf().readInt());
    }
}
