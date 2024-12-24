package ru.BouH_.hook.both;

import net.minecraft.util.MathHelper;
import ru.BouH_.optimization.math.RivensMath;
import ru.hook.asm.Hook;
import ru.hook.asm.ReturnCondition;

public class MathHook {
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static double sin(Math math, double a) {
        return RivensMath.sin(a);
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static double cos(Math math, double a) {
        return RivensMath.cos(a);
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static float sin(MathHelper mathHelper, float a) {
        return RivensMath.sin(a);
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static float cos(MathHelper mathHelper, float a) {
        return RivensMath.cos(a);
    }
}
