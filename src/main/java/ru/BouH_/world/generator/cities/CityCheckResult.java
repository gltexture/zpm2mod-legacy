package ru.BouH_.world.generator.cities;

public class CityCheckResult {
    private final CityType cityType;
    private final int x;
    private final int y;
    private final int z;

    public CityCheckResult(CityType cityType, int x, int y, int z) {
        this.cityType = cityType;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public CityType getCityType() {
        return this.cityType;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }
}
