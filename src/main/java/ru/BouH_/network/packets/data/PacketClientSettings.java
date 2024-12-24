package ru.BouH_.network.packets.data;

import net.minecraft.entity.player.EntityPlayerMP;
import ru.BouH_.entity.ieep.PlayerMiscData;
import ru.BouH_.network.SimplePacket;

public class PacketClientSettings extends SimplePacket {

    public PacketClientSettings() {
    }

    public PacketClientSettings(boolean pickUpOnF) {
        buf().writeBoolean(pickUpOnF);
    }

    @Override
    public void server(EntityPlayerMP player) {
        PlayerMiscData.getPlayerData(player).setPickUpOnF(buf().readBoolean());
    }
}
