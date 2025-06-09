package ru.BouH_.weather.managers;

import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.weather.utils.WeatherRainMixer;
import ru.BouH_.weather.utils.WeatherRainScript;

import java.util.LinkedList;

public class WeatherRainManager implements IWeatherInfo {
    private static final long serialVersionUID = 2281337L;
    public final String name;
    private final int dimension;
    private boolean isStarted;
    private int timeTicks;
    private int timeUntilStart;
    private float rainStrength;
    private float cloudyStrength;
    private WeatherRainScript currentWeatherRain;
    private LinkedList<WeatherRainScript> weatherRainMixer;

    public WeatherRainManager(String name, int dimension) {
        this.name = name;
        this.dimension = dimension;
        this.resetTimer();
    }

    public void start(World world) {
        this.weatherRainMixer = WeatherRainMixer.buildRainScript();
        this.switchScript(world);
        this.isStarted = true;
    }

    public void switchScript(World world) {
        WeatherRainScript weatherRainScript = this.weatherRainMixer.pollFirst();
        if (weatherRainScript == null) {
            this.stop(world);
        } else {
            this.currentWeatherRain = weatherRainScript;
            this.changeWeather(world);
            this.setTimeTicks(this.currentWeatherRain.getTime());
        }
    }

    public int getDimension() {
        return this.dimension;
    }

    public LinkedList<WeatherRainScript> getWeatherRainMixer() {
        return this.weatherRainMixer;
    }

    public void setWeatherRainMixer(LinkedList<WeatherRainScript> weatherRainMixer) {
        this.weatherRainMixer = weatherRainMixer;
    }

    public WeatherRainScript getCurrentWeatherRain() {
        return this.currentWeatherRain;
    }

    public void setCurrentWeatherRain(WeatherRainScript currentWeatherRain) {
        this.currentWeatherRain = currentWeatherRain;
    }

    public float getRainStrength() {
        return this.rainStrength;
    }

    public void setRainStrength(float rainStrength) {
        this.rainStrength = rainStrength;
    }

    public float getCloudyStrength() {
        return this.cloudyStrength;
    }

    public void setCloudyStrength(float cloudyStrength) {
        this.cloudyStrength = cloudyStrength;
    }

    public void changeWeather(World world) {
        world.getWorldInfo().setThundering(this.currentWeatherRain.isThundering());
        this.setRainStrength(this.currentWeatherRain.getRainStrength());
        this.setCloudyStrength(this.currentWeatherRain.getCloudyStrength());
    }

    public void stop(World world) {
        this.isStarted = false;
        this.currentWeatherRain = null;
        this.weatherRainMixer = null;
        world.getWorldInfo().setThunderTime(0);
        world.getWorldInfo().setThundering(false);
    }

    public boolean isStarted() {
        return this.isStarted;
    }

    public void setStarted(boolean started) {
        this.isStarted = started;
    }

    public void resetTimer() {
        this.timeUntilStart = 20000 + Main.rand.nextInt(40001);
    }

    public final int getTimeUntilStart() {
        return this.timeUntilStart;
    }

    public void setTimeUntilStart(int timeUntilStart) {
        this.timeUntilStart = timeUntilStart;
    }

    public int getTimeTicks() {
        return this.timeTicks;
    }

    public void setTimeTicks(int timeTicks) {
        this.timeTicks = timeTicks;
    }
}
