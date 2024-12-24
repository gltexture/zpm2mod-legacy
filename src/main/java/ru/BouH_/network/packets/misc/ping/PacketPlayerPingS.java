package ru.BouH_.network.packets.misc.ping;

import net.minecraft.entity.player.EntityPlayerMP;
import ru.BouH_.entity.ieep.PlayerMiscData;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.SimplePacket;

public final class PacketPlayerPingS extends SimplePacket {
    public PacketPlayerPingS() {

    }

    public PacketPlayerPingS(int ping) {
        buf().writeInt(ping);
    }

    @Override
    public void server(EntityPlayerMP player) {
        int ping = buf().readInt();
        NetworkHandler.NETWORK.sendTo(new PacketPlayerPingC(), player);
        PlayerMiscData.getPlayerData(player).setPing(Math.max(ping, 0));
    }
}