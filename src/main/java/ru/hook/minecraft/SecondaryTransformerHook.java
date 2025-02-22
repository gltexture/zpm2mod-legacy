package ru.hook.minecraft;

import cpw.mods.fml.common.Loader;
import net.minecraft.launchwrapper.LaunchClassLoader;
import ru.clientfixer.asm.ASMTransformer;
import ru.hook.asm.Hook;

public class SecondaryTransformerHook {

    /**
     * Регистрирует хук-трансформер последним.
     */
    @Hook
    public static void injectData(Loader loader, Object... data) {
        ClassLoader classLoader = SecondaryTransformerHook.class.getClassLoader();
        if (classLoader instanceof LaunchClassLoader) {
            ((LaunchClassLoader) classLoader).registerTransformer(ASMTransformer.class.getName());
            ((LaunchClassLoader) classLoader).registerTransformer(MinecraftClassTransformer.class.getName());
        } else {
            System.out.println("HookLib was not loaded by LaunchClassLoader. Hooks will not be injected.");
        }
    }
}
