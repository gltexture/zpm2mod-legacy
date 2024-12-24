package ru.BouH_.weather.managers;

import java.io.Serializable;

public interface IWeatherInfo extends Serializable {
    boolean isStarted();

    void setStarted(boolean started);

    int getDimension();

    int getTimeTicks();

    void setTimeTicks(int timeTicks);

    int getTimeUntilStart();

    void setTimeUntilStart(int timeUntilStart);

    void resetTimer();
}
