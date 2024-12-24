package ru.BouH_.weather.utils;

import ru.BouH_.Main;

import java.util.LinkedList;

public class WeatherRainMixer {
    public static LinkedList<WeatherRainScript> buildRainScript() {
        LinkedList<WeatherRainScript> rainScript = new LinkedList<>();
        if (Main.rand.nextFloat() <= 0.05f) {
            for (int i = 0; i < 2 + Main.rand.nextInt(2); i++) {
                rainScript.add(new WeatherRainScript(5400 + Main.rand.nextInt(5401), false, Main.rand.nextFloat() <= 0.8f ? 0.2f : 0.0f, 0.3f));
            }
        } else {
            rainScript.add(new WeatherRainScript(1200, false, Main.rand.nextFloat() <= 0.1f ? 0.2f : 0.0f, 0.5f));
            if (Main.rand.nextFloat() <= 0.8f) {
                rainScript.add(new WeatherRainScript(3600 + Main.rand.nextInt(5401), false, 0.0f, 0.7f));
            }
            boolean b1 = Main.rand.nextFloat() <= 0.1f;
            rainScript.add(new WeatherRainScript(12000 + Main.rand.nextInt(2401), b1, Main.rand.nextBoolean() ? 0.3f : 0.0f, 1.0f));
            if (Main.rand.nextFloat() <= 0.2f) {
                rainScript.add(new WeatherRainScript(18000 + Main.rand.nextInt(7201), b1, Main.rand.nextFloat() <= 0.7f ? 0.3f : 0.0f, 1.0f));
            } else {
                boolean thunder = Main.rand.nextFloat() <= 0.3f;
                for (int i = 0; i < 2 + Main.rand.nextInt(4); i++) {
                    float strength = Main.rand.nextFloat() <= 0.3f ? 0.5f : Main.rand.nextFloat() <= 0.8f ? 1.0f : 0.3f;
                    if (Main.rand.nextFloat() <= 0.1f) {
                        rainScript.add(new WeatherRainScript(5400 + Main.rand.nextInt(6401), false, Main.rand.nextFloat() <= 0.7f ? 0.2f : 0.0f, Main.rand.nextFloat() <= 0.3f ? 0.5f : 0.7f));
                    }
                    if (strength == 1.0f) {
                        rainScript.add(new WeatherRainScript(2400, thunder, Main.rand.nextBoolean() ? 1.0f : 0.5f, 0.7f));
                    }
                    rainScript.add(new WeatherRainScript(7200 + Main.rand.nextInt(7201), thunder, Main.rand.nextFloat() <= 0.7f ? thunder ? 1.0f : strength : 0.0f, 1.0f));
                }
            }
            rainScript.add(new WeatherRainScript(7200 + Main.rand.nextInt(2401), Main.rand.nextFloat() <= 0.05f, Main.rand.nextBoolean() ? 0.3f : 0.0f, 1.0f));
            if (Main.rand.nextFloat() <= 0.8f) {
                rainScript.add(new WeatherRainScript(3600 + Main.rand.nextInt(5401), false, 0.0f, 0.7f));
            }
            rainScript.add(new WeatherRainScript(1200, false, Main.rand.nextFloat() <= 0.1f ? 0.2f : 0.0f, 0.5f));
        }
        return rainScript;
    }
}
