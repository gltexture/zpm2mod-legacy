package ru.BouH_.network.packets.misc;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import ru.BouH_.network.SimplePacket;
import ru.BouH_.weather.base.WeatherHandler;
import ru.BouH_.weather.base.WeatherRain;

public class PacketRainCheck extends SimplePacket {
    public PacketRainCheck() {
    }

    public PacketRainCheck(boolean isStartedRain, boolean forceStart, float rainStrength, float cloudStrength) {
        buf().writeBoolean(isStartedRain);
        buf().writeBoolean(forceStart);
        buf().writeFloat(rainStrength);
        buf().writeFloat(cloudStrength);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void client(EntityPlayer player) {
        boolean isStarted = buf().readBoolean();
        boolean forceStart = buf().readBoolean();
        float rainStrength = buf().readFloat();
        float cloudStrength = buf().readFloat();
        WeatherRain weatherRain = WeatherHandler.instance.getWeatherRain();
        if (isStarted) {
            if (forceStart) {
                weatherRain.setWeatherRain(rainStrength, cloudStrength);
            } else {
                weatherRain.startWeatherRain(rainStrength, cloudStrength);
            }
        } else {
            if (forceStart) {
                weatherRain.clearWeatherRain();
            } else {
                weatherRain.stopWeatherRain();
            }
        }
    }
}
