package ru.BouH_.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;
import ru.BouH_.achievements.AchievementManager;
import ru.BouH_.audio.AmbientSounds;
import ru.BouH_.gameplay.client.*;
import ru.BouH_.init.EntitiesZp;
import ru.BouH_.items.gun.events.ClientGunEvent;
import ru.BouH_.items.gun.render.BinocularsItemRender;
import ru.BouH_.items.gun.render.GunItemRender;
import ru.BouH_.items.gun.render.tracer.RenderTracer;
import ru.BouH_.skills.SkillManager;
import ru.BouH_.weather.render.RenderFog;
import ru.BouH_.weather.render.RenderRain;
import ru.BouH_.weather.render.RenderSandStorm;

@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    public static KeyBinding keyReload, keyUnReload, keySeeGun;
    public static KeyBinding keyRight, keyLeft, keyUp, keyDown, keyConfirm, keySwitch, keyPick;

    @Override
    public void registerEvents() {
        super.registerEvents();
        FMLCommonHandler.instance().bus().register(new RenderTracer());
        FMLCommonHandler.instance().bus().register(PingManager.instance);
        FMLCommonHandler.instance().bus().register(GunItemRender.instance);
        FMLCommonHandler.instance().bus().register(CrosshairManager.instance);
        FMLCommonHandler.instance().bus().register(ClientHandler.instance);
        FMLCommonHandler.instance().bus().register(ClientGunEvent.instance);
        FMLCommonHandler.instance().bus().register(PainUpdater.instance);
        FMLCommonHandler.instance().bus().register(BinocularsItemRender.instance);
        FMLCommonHandler.instance().bus().register(GameHud.instance);
        FMLCommonHandler.instance().bus().register(AmbientSounds.instance);
        FMLCommonHandler.instance().bus().register(NotificationHud.instance);

        MinecraftForge.EVENT_BUS.register(ClientHandler.instance);
        MinecraftForge.EVENT_BUS.register(BinocularsItemRender.instance);
        MinecraftForge.EVENT_BUS.register(RenderSandStorm.instance);
        MinecraftForge.EVENT_BUS.register(RenderRain.instance);
        MinecraftForge.EVENT_BUS.register(RenderFog.instance);
        MinecraftForge.EVENT_BUS.register(new RenderManager());
        MinecraftForge.EVENT_BUS.register(new RenderTracer());
        MinecraftForge.EVENT_BUS.register(PainUpdater.instance);
        MinecraftForge.EVENT_BUS.register(CrosshairManager.instance);
        MinecraftForge.EVENT_BUS.register(GunItemRender.instance);
        MinecraftForge.EVENT_BUS.register(GameHud.instance);
        MinecraftForge.EVENT_BUS.register(NotificationHud.instance);
    }

    @Override
    public void registerDispenses() {
        super.registerDispenses();
    }

    @Override
    public void registerEffects() {
        super.registerEffects();
    }

    @Override
    public void registerTileEntities() {
        super.registerTileEntities();
    }

    @Override
    public void registerCrafts() {
        super.registerCrafts();
    }

    @Override
    public void registerBiomes() {
        super.registerBiomes();
    }

    @Override
    public void registerStackData() {
        super.registerStackData();
    }

    @Override
    public void registerBaseZp() throws IllegalAccessException {
        super.registerBaseZp();
    }

    @Override
    public void registerGen() {
        super.registerGen();
    }

    @Override
    public void registerRenders() {
        super.registerRenders();
        EntitiesZp.addEntityRender();
        SkillManager.instance.initIcons();
        AchievementManager.instance.initIcons();
    }

    @Override
    public void registerKeyBindings() {
        super.registerKeyBindings();
        keyReload = new KeyBinding(I18n.format("button.reload"), Keyboard.KEY_R, I18n.format("button.category.gunCategory"));
        keyUnReload = new KeyBinding(I18n.format("button.unReload"), Keyboard.KEY_Y, I18n.format("button.category.gunCategory"));
        keySeeGun = new KeyBinding(I18n.format("button.seeGun"), Keyboard.KEY_C, I18n.format("button.category.gunCategory"));
        keyRight = new KeyBinding(I18n.format("button.right"), Keyboard.KEY_RIGHT, I18n.format("button.category.gunCategory"));
        keyLeft = new KeyBinding(I18n.format("button.left"), Keyboard.KEY_LEFT, I18n.format("button.category.gunCategory"));
        keyUp = new KeyBinding(I18n.format("button.up"), Keyboard.KEY_UP, I18n.format("button.category.gunCategory"));
        keyDown = new KeyBinding(I18n.format("button.down"), Keyboard.KEY_DOWN, I18n.format("button.category.gunCategory"));
        keyConfirm = new KeyBinding(I18n.format("button.confirm"), Keyboard.KEY_X, I18n.format("button.category.gunCategory"));
        keySwitch = new KeyBinding(I18n.format("button.switch"), Keyboard.KEY_V, I18n.format("button.category.gunCategory"));

        keyPick = new KeyBinding(I18n.format("button.pickUp"), Keyboard.KEY_F, "ZPM");

        ClientRegistry.registerKeyBinding(keyReload);
        ClientRegistry.registerKeyBinding(keyUnReload);
        ClientRegistry.registerKeyBinding(keySeeGun);

        ClientRegistry.registerKeyBinding(keyRight);
        ClientRegistry.registerKeyBinding(keyLeft);
        ClientRegistry.registerKeyBinding(keyUp);
        ClientRegistry.registerKeyBinding(keyDown);
        ClientRegistry.registerKeyBinding(keyConfirm);
        ClientRegistry.registerKeyBinding(keyPick);
        ClientRegistry.registerKeyBinding(keySwitch);
    }
}
