package ru.BouH_.network.packets.misc.ping;

import net.minecraft.entity.player.EntityPlayerMP;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.SimplePacket;

public final class PacketPingTab extends SimplePacket {
    public PacketPingTab() {
    }

    public PacketPingTab(int ping) {
        buf().writeInt(ping);
    }

    @Override
    public void server(EntityPlayerMP player) {
        int ping = buf().readInt();
        NetworkHandler.NETWORK.sendToAll(new PacketPingClient(player.getDisplayName(), ping));
    }
}