package ru.BouH_.network.packets.data;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import ru.BouH_.entity.ieep.PlayerMiscData;
import ru.BouH_.network.SimplePacket;

public class PacketMiscInfo extends SimplePacket {

    public PacketMiscInfo() {
    }

    public PacketMiscInfo(int playersKilled, int zombiesKilled, int playerDeaths) {
        buf().writeInt(playersKilled);
        buf().writeInt(zombiesKilled);
        buf().writeInt(playerDeaths);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void client(EntityPlayer player) {
        PlayerMiscData.getPlayerData(player).setPlayersKilled(buf().readInt());
        PlayerMiscData.getPlayerData(player).setZombiesKilled(buf().readInt());
        PlayerMiscData.getPlayerData(player).setPlayerDeaths(buf().readInt());
    }
}