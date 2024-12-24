package ru.BouH_.items.gun.render;

public class Shell {
    private final float red;
    private final float green;
    private final float blue;
    private final float scale;

    public Shell(float red, float green, float blue, float scale) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.scale = scale;
    }

    public float getBlue() {
        return this.blue;
    }

    public float getGreen() {
        return this.green;
    }

    public float getRed() {
        return this.red;
    }

    public float getScale() {
        return this.scale;
    }
}
