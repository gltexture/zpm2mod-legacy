package ru.BouH_.network.packets.moving;

import net.minecraft.entity.player.EntityPlayerMP;
import ru.BouH_.entity.ieep.PlayerMiscData;
import ru.BouH_.network.SimplePacket;

public class PacketLying extends SimplePacket {
    public PacketLying() {
    }

    public PacketLying(boolean isLying) {
        buf().writeBoolean(isLying);
    }

    @Override
    public void server(EntityPlayerMP player) {
        boolean isLying = buf().readBoolean();
        PlayerMiscData.getPlayerData(player).setLying(isLying);
    }
}
