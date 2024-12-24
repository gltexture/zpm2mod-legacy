package ru.BouH_.network.packets.misc.ping;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import ru.BouH_.gameplay.client.PingManager;
import ru.BouH_.network.SimplePacket;

public final class PacketPingClient extends SimplePacket {
    public PacketPingClient() {
    }

    public PacketPingClient(String name, int ping) {
        ByteBufUtils.writeUTF8String(buf(), name);
        buf().writeInt(ping);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void client(EntityPlayer player) {
        String name = ByteBufUtils.readUTF8String(buf());
        int ping = buf().readInt();
        if (ping == -1) {
            PingManager.instance.getPingMap().remove(name);
        }
        if (PingManager.instance.getPingMap().containsKey(name)) {
            PingManager.instance.getPingMap().replace(name, ping);
        } else {
            PingManager.instance.getPingMap().put(name, ping);
        }
    }
}