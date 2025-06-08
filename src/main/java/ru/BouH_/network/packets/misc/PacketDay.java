package ru.BouH_.network.packets.misc;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import ru.BouH_.gameplay.client.ClientHandler;
import ru.BouH_.network.SimplePacket;
import ru.BouH_.weather.base.WeatherFog;
import ru.BouH_.weather.base.WeatherHandler;

public class PacketDay extends SimplePacket {
    public PacketDay() {
    }

    public PacketDay(boolean server7NightEnabled, int day) {
        buf().writeBoolean(server7NightEnabled);
        buf().writeInt(day);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void client(EntityPlayer player) {
        boolean server7NightEnabled = buf().readBoolean();
        int day = buf().readInt();
        ClientHandler.day = day;
        ClientHandler.is7nightEnabled = server7NightEnabled;
    }
}
