package ru.BouH_.weather.base;

import net.minecraft.entity.player.EntityPlayerMP;
import ru.BouH_.ConfigZp;
import ru.BouH_.Main;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.packets.misc.PacketFogCheck;
import ru.BouH_.weather.managers.IWeatherInfo;
import ru.BouH_.weather.managers.WeatherFogManager;

public class WeatherFog implements IWeather {
    public float currentFogDepth = 0;
    public float fogDepth = 0;

    public void startWeatherFog(float depth) {
        this.fogDepth = depth;
    }

    public void stopWeatherFog() {
        this.fogDepth = 0;
    }

    public void setWeatherFog(float depth) {
        this.currentFogDepth = depth;
        this.fogDepth = depth;
    }

    public void clearWeatherFog() {
        this.currentFogDepth = 0;
        this.fogDepth = 0;
    }

    public void sendFogPacketCheck(EntityPlayerMP player, WeatherFogManager weatherFogInfo) {
        if (player != null && weatherFogInfo != null) {
            NetworkHandler.NETWORK.sendTo(new PacketFogCheck(weatherFogInfo.isStarted(), true, weatherFogInfo.getDepth()), player);
        }
    }

    public void tick(IWeatherInfo iWeatherInfo) {
        if (!iWeatherInfo.isStarted()) {
            if (iWeatherInfo.getTimeUntilStart() > 0) {
                iWeatherInfo.setTimeUntilStart(iWeatherInfo.getTimeUntilStart() - 1);
            } else {
                if (Main.rand.nextFloat() <= 0.2f) {
                    this.startWeatherFogPacket((WeatherFogManager) iWeatherInfo);
                } else {
                    iWeatherInfo.resetTimer();
                }
            }
        } else if (iWeatherInfo.getTimeTicks() > 0) {
            iWeatherInfo.setTimeTicks(iWeatherInfo.getTimeTicks() - 1);
        } else {
            this.stopWeatherFogPacket((WeatherFogManager) iWeatherInfo);
        }
    }

    public void startWeatherFogPacket7Night(WeatherFogManager weatherFogInfo) {
        int timeTicks = ConfigZp.longNights ? 31500 : 10500;
        int depth = 16;
        weatherFogInfo.setDepth(depth);
        weatherFogInfo.setStarted(true);
        weatherFogInfo.setTimeTicks(timeTicks);
        NetworkHandler.NETWORK.sendToDimension(new PacketFogCheck(true, false, depth), weatherFogInfo.getDimension());
    }

    public void startWeatherFogPacket(WeatherFogManager weatherFogInfo) {
        int timeTicks = 6400 + Main.rand.nextInt(18001);
        int depth = 12 + Main.rand.nextInt(7);
        weatherFogInfo.setDepth(depth);
        weatherFogInfo.setStarted(true);
        weatherFogInfo.setTimeTicks(timeTicks);
        NetworkHandler.NETWORK.sendToDimension(new PacketFogCheck(true, false, depth), weatherFogInfo.getDimension());
    }

    public void stopWeatherFogPacket(WeatherFogManager weatherFogInfo) {
        weatherFogInfo.resetTimer();
        weatherFogInfo.setStarted(false);
        NetworkHandler.NETWORK.sendToDimension(new PacketFogCheck(false, false, 0), weatherFogInfo.getDimension());
    }
}