package ru.hook.helper;

import cpw.mods.fml.relauncher.FMLLaunchHandler;
import ru.hook.minecraft.HookLoader;
import ru.hook.minecraft.PrimaryClassTransformer;

public class ModHookLoader extends HookLoader {
    public static boolean GRAVITY_FIX_DISABLE_FALL_HOOK = false;

    @Override
    public String[] getASMTransformerClass() {
        return new String[]{PrimaryClassTransformer.class.getName()};
    }

    @Override
    public void registerHooks() {
        if (FMLLaunchHandler.side().isClient()) {
            registerHookContainer("ru.BouH_.hook.client.BlockHook");
            registerHookContainer("ru.BouH_.hook.client.MiscHook");
            registerHookContainer("ru.BouH_.hook.client.ItemHook");
            registerHookContainer("ru.BouH_.hook.client.FXHook");
            registerHookContainer("ru.BouH_.hook.client.ModelHook");
            registerHookContainer("ru.BouH_.hook.client.RenderBlockHook");
        }
        registerHookContainer("ru.BouH_.hook.server.MiscHook");
        registerHookContainer("ru.BouH_.hook.server.ItemHook");
        registerHookContainer("ru.BouH_.hook.server.EntityHook");
        registerHookContainer("ru.BouH_.hook.server.VillagerHook");
        registerHookContainer("ru.BouH_.hook.server.BlockHook");
        try {
            Class.forName("com.bloodnbonesgaming.blockphysics.asm.modules.ModuleWorldClass");
        } catch (Exception e) {
            registerHookContainer("ru.BouH_.hook.server.ExplosionHook");
        }
        try {
            Class.forName("com.hbm.dim.CelestialBody");
            ModHookLoader.GRAVITY_FIX_DISABLE_FALL_HOOK = true;
        } catch (Exception e) {
            ModHookLoader.GRAVITY_FIX_DISABLE_FALL_HOOK = false;
        }
        registerHookContainer("ru.BouH_.hook.both.MathHook");
    }
}
