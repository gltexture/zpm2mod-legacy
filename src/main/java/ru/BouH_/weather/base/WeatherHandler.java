package ru.BouH_.weather.base;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.world.WorldEvent;
import ru.BouH_.weather.managers.IWeatherInfo;
import ru.BouH_.weather.managers.WeatherFogManager;
import ru.BouH_.weather.managers.WeatherRainManager;
import ru.BouH_.weather.render.RenderRain;
import ru.BouH_.weather.render.RenderSandStorm;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class WeatherHandler {
    public static WeatherHandler instance = new WeatherHandler();
    public Map<Integer, WeatherFogManager> fogManagerMap = new HashMap<>();
    public Map<Integer, WeatherRainManager> rainManagerMap = new HashMap<>();
    protected WeatherRain weatherRain = new WeatherRain();
    protected WeatherFog weatherFog = new WeatherFog();

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent ev) {
        World world = Minecraft.getMinecraft().theWorld;
        if (ev.phase == TickEvent.Phase.START) {
            if (world != null) {
                if (!world.provider.hasNoSky) {
                    WeatherRain weatherRain = WeatherHandler.instance.getWeatherRain();
                    WeatherFog weatherFog = WeatherHandler.instance.getWeatherFog();
                    weatherRain.prevCloudStrength = weatherRain.currentCloudStrength;
                    RenderRain.instance.addRainParticles(Minecraft.getMinecraft().entityRenderer);
                    RenderSandStorm.instance.addStormParticles(Minecraft.getMinecraft().entityRenderer);
                    float f1 = Math.max(weatherRain.cloudStrength, Math.min(weatherFog.currentFogDepth, 0.75f));
                    if (weatherRain.currentCloudStrength <= f1) {
                        weatherRain.currentCloudStrength = Math.min(weatherRain.currentCloudStrength + 0.002f, f1);
                    } else {
                        weatherRain.currentCloudStrength = Math.max(weatherRain.currentCloudStrength - 0.002f, 0);
                    }
                    if (weatherFog.currentFogDepth <= weatherFog.fogDepth) {
                        weatherFog.currentFogDepth = Math.min(weatherFog.currentFogDepth + 0.02f, weatherFog.fogDepth);
                    } else {
                        weatherFog.currentFogDepth = Math.max(weatherFog.currentFogDepth - 0.02f, 0);
                    }
                    if (weatherRain.currentRainStrength <= weatherRain.rainStrength) {
                        weatherRain.currentRainStrength = Math.min(weatherRain.currentRainStrength + 0.002f, weatherRain.rainStrength);
                    } else {
                        weatherRain.currentRainStrength = Math.max(weatherRain.currentRainStrength - 0.002f, 0);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent ev) {
        if (ev.phase == TickEvent.Phase.START) {
            if (DimensionManager.getWorld(0).getGameRules().getGameRuleBooleanValue("doWeatherCycle")) {
                this.fogManagerMap.forEach((i, e) -> this.weatherFog.tick(e));
                this.rainManagerMap.forEach((i, e) -> this.weatherRain.tick(e));
            }
        }
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load ev) {
        if (!ev.world.provider.hasNoSky) {
            WorldSaveRain.getStorage(ev.world);
            WorldSaveFog.getStorage(ev.world);
        }
    }

    @SubscribeEvent
    public void onWorldSave(WorldEvent.Save ev) {
        if (!ev.world.provider.hasNoSky) {
            Objects.requireNonNull(WorldSaveRain.getStorage(ev.world)).markDirty();
            Objects.requireNonNull(WorldSaveFog.getStorage(ev.world)).markDirty();
        }
    }

    public IWeatherInfo getWorldRainInfo(int dimension) {
        return this.rainManagerMap.get(dimension);
    }

    public IWeatherInfo getWorldFogInfo(int dimension) {
        return this.fogManagerMap.get(dimension);
    }

    public WeatherRain getWeatherRain() {
        return this.weatherRain;
    }

    public WeatherFog getWeatherFog() {
        return this.weatherFog;
    }
}
