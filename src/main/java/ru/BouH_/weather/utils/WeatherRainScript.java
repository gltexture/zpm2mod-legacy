package ru.BouH_.weather.utils;

import java.io.Serializable;

public class WeatherRainScript implements Serializable {
    private final int time;
    private final boolean thundering;
    private final float rainStrength;
    private final float cloudyStrength;

    public WeatherRainScript(int time, boolean thundering, float rainStrength, float cloudyStrength) {
        this.time = time;
        this.thundering = thundering;
        this.rainStrength = rainStrength;
        this.cloudyStrength = cloudyStrength;
    }

    public boolean isThundering() {
        return this.thundering;
    }

    public int getTime() {
        return this.time;
    }

    public float getRainStrength() {
        return this.rainStrength;
    }

    public float getCloudyStrength() {
        return this.cloudyStrength;
    }
}
