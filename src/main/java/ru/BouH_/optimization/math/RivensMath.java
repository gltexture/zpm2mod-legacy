package ru.BouH_.optimization.math;

public class RivensMath {
    private static final int BF_SIN_BITS = 12;
    private static final int BF_SIN_MASK;
    private static final int BF_SIN_COUNT;
    private static final float BF_radFull;
    private static final float BF_radToIndex;
    private static final float BF_degFull;
    private static final float BF_degToIndex;
    private static final float[] BF_sin;
    private static final float[] BF_cos;

    static {
        BF_SIN_MASK = ~(-1 << BF_SIN_BITS);
        BF_SIN_COUNT = BF_SIN_MASK + 1;
        BF_radFull = 6.2831855F;
        BF_degFull = 360.0F;
        BF_radToIndex = (float) BF_SIN_COUNT / BF_radFull;
        BF_degToIndex = (float) BF_SIN_COUNT / BF_degFull;
        BF_sin = new float[BF_SIN_COUNT];
        BF_cos = new float[BF_SIN_COUNT];
        int i;
        for (i = 0; i < BF_SIN_COUNT; ++i) {
            BF_sin[i] = (float) Math.sin(((float) i + 0.5F) / (float) BF_SIN_COUNT * BF_radFull);
            BF_cos[i] = (float) Math.cos(((float) i + 0.5F) / (float) BF_SIN_COUNT * BF_radFull);
        }
        for (i = 0; i < 360; i += 90) {
            BF_sin[(int) ((float) i * BF_degToIndex) & BF_SIN_MASK] = (float) Math.sin((double) i * Math.PI / 180.0);
            BF_cos[(int) ((float) i * BF_degToIndex) & BF_SIN_MASK] = (float) Math.cos((double) i * Math.PI / 180.0);
        }
    }

    public static float sin(double rad) {
        return BF_sin[(int) (rad * BF_radToIndex) & BF_SIN_MASK];
    }

    public static float cos(double rad) {
        return BF_cos[(int) (rad * BF_radToIndex) & BF_SIN_MASK];
    }
}
