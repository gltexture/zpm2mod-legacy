package ru.BouH_.achievements;

public enum TierEnum {
    LVL_ONE(0xffffff),
    LVL_TWO(0xa4cdf7),
    LVL_THREE(0xff9494),
    LVL_SECRET(0xf600ff);

    private final int colorCode;

    TierEnum(int c) {
        this.colorCode = c;
    }

    public int getColorCode() {
        return this.colorCode;
    }
}
