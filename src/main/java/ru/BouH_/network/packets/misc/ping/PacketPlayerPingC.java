package ru.BouH_.network.packets.misc.ping;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import ru.BouH_.gameplay.client.PingManager;
import ru.BouH_.network.SimplePacket;

public final class PacketPlayerPingC extends SimplePacket {
    public PacketPlayerPingC() {
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void client(EntityPlayer player) {
        PingManager.instance.setPing();
    }
}