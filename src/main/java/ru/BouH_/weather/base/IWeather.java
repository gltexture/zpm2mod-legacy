package ru.BouH_.weather.base;

import ru.BouH_.weather.managers.IWeatherInfo;

public interface IWeather {
    void tick(IWeatherInfo iWeatherInfo);
}
