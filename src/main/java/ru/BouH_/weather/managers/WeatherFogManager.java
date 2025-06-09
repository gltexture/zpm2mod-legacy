package ru.BouH_.weather.managers;

import ru.BouH_.Main;

public class WeatherFogManager implements IWeatherInfo {
    private static final long serialVersionUID = 1337L;
    public final String name;
    private final int dimension;
    private boolean isStarted;
    private int timeTicks;
    private int timeUntilStart;
    private int depth;

    public WeatherFogManager(String name, int dimension) {
        this.name = name;
        this.dimension = dimension;
        this.resetTimer();
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

    public int getTimeUntilStart() {
        return this.timeUntilStart;
    }

    public void setTimeUntilStart(int timeUntilStart) {
        this.timeUntilStart = timeUntilStart;
    }

    public int getDimension() {
        return this.dimension;
    }

    public int getTimeTicks() {
        return this.timeTicks;
    }

    public void setTimeTicks(int timeTicks) {
        this.timeTicks = timeTicks;
    }

    public int getDepth() {
        return this.depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}
