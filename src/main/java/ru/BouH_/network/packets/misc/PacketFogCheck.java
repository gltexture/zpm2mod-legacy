package ru.BouH_.network.packets.misc;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import ru.BouH_.network.SimplePacket;
import ru.BouH_.weather.base.WeatherFog;
import ru.BouH_.weather.base.WeatherHandler;

public class PacketFogCheck extends SimplePacket {
    public PacketFogCheck() {
    }

    public PacketFogCheck(boolean isStartedFog, boolean forceStart, float depth) {
        buf().writeBoolean(isStartedFog);
        buf().writeBoolean(forceStart);
        buf().writeFloat(depth);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void client(EntityPlayer player) {
        boolean isStartedFog = buf().readBoolean();
        boolean forceStart = buf().readBoolean();
        float depth = buf().readFloat();
        WeatherFog weatherFog = WeatherHandler.instance.getWeatherFog();
        if (isStartedFog) {
            if (forceStart) {
                weatherFog.setWeatherFog(depth);
            } else {
                weatherFog.startWeatherFog(depth);
            }
        } else {
            if (forceStart) {
                weatherFog.clearWeatherFog();
            } else {
                weatherFog.stopWeatherFog();
            }
        }
    }
}
