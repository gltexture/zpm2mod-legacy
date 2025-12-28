package ru.BouH_;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.*;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.FMLLaunchHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;
import ru.BouH_.commands.*;
import ru.BouH_.options.SettingsZp;
import ru.BouH_.options.zm.SettingsZombieMiningZp;
import ru.BouH_.proxy.CommonProxy;
import ru.BouH_.weather.base.WeatherHandler;
import ru.BouH_.weather.managers.WeatherFogManager;
import ru.BouH_.weather.managers.WeatherRainManager;
import ru.BouH_.world.generator.DynamicEventsGenerator;
import ru.BouH_.world.generator.cities.SpecialGenerator;
import ru.BouH_.world.ore.CopperGen;
import ru.BouH_.world.ore.DiamondGen;
import ru.BouH_.world.ore.TitanGen;
import ru.BouH_.world.ore.UranGen;

import java.io.File;
import java.util.Calendar;
import java.util.Random;

@Mod(modid = Main.MODID, name = Main.MODNAME, version = Main.VERSION)
public class Main implements Thread.UncaughtExceptionHandler {
    public static final String MODNAME = "Zombie Plague Mod 2";
    public static final String MODID = "zombieplague2";
    public static final String VERSION = "1.6.31";
    public static SettingsZp settingsZp;
    public static SettingsZombieMiningZp settingsZombieMiningZp;
    public static ConfigZp configZp = new ConfigZp();
    @SidedProxy(clientSide = "ru.BouH_.proxy.ClientProxy", serverSide = "ru.BouH_.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance("zombieplague2")
    public static Main instance;
    public static Random rand = new Random();
    static String[] nothingToSeeHere = new String[7];
    public static ModStatus modStatus = ModStatus.EXP;

    static {
        nothingToSeeHere[0] = "****************************************";
        nothingToSeeHere[1] = "* Zombie Plague Mod 2                  *";
        nothingToSeeHere[2] = "* Made by: gltexture                   *";
        nothingToSeeHere[3] = "* Ds:    https://discord.gg/bb6AaU6Taw *";
        nothingToSeeHere[4] = "* Contact me, if you found a bug!      *";
        nothingToSeeHere[5] = "* Thanks for your attention:)          *";
        nothingToSeeHere[6] = "****************************************";
    }

    public static boolean isNewYear() {
        Calendar calendar = Calendar.getInstance();
        return (calendar.get(Calendar.MONTH) + 1 == 12 && calendar.get(Calendar.DATE) >= 14) || (calendar.get(Calendar.MONTH) + 1 == 1 && calendar.get(Calendar.DATE) <= 3);
    }

    public static boolean isHalloween() {
        Calendar calendar = Calendar.getInstance();
        return (calendar.get(Calendar.MONTH) + 1 == 10 && calendar.get(Calendar.DATE) >= 14) || (calendar.get(Calendar.MONTH) + 1 == 11 && calendar.get(Calendar.DATE) <= 3);
    }

    public static boolean isApril1() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.MONTH) + 1 == 4 && calendar.get(Calendar.DATE) <= 3;
    }
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) throws IllegalAccessException {
        if (FMLLaunchHandler.side() == Side.CLIENT && Minecraft.getMinecraft().gameSettings.renderDistanceChunks > 16) {
            Minecraft.getMinecraft().gameSettings.renderDistanceChunks = 16;
        }
        WeatherHandler.instance.rainManagerMap.put(0, new WeatherRainManager("0_rain", 0));
        WeatherHandler.instance.fogManagerMap.put(0, new WeatherFogManager("0_fog", 0));
        WeatherHandler.instance.rainManagerMap.put(2, new WeatherRainManager("2_rain", 2));
        WeatherHandler.instance.fogManagerMap.put(2, new WeatherFogManager("2_fog", 2));
        configZp.register();
        proxy.registerStackData();
        proxy.registerBaseZp();
        proxy.registerGen();
        proxy.registerRenders();
        proxy.registerDispenses();
        proxy.registerEffects();
        proxy.registerEvents();
        proxy.registerCrafts();
        proxy.registerKeyBindings();
        proxy.registerTileEntities();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) throws Exception {
        GameRegistry.registerWorldGenerator(new TitanGen(), 10);
        GameRegistry.registerWorldGenerator(new DiamondGen(), 11);
        GameRegistry.registerWorldGenerator(new UranGen(), 12);
        GameRegistry.registerWorldGenerator(new CopperGen(), 13);
    }

    @SuppressWarnings("all")
    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        try {
            Class.forName("com.gamerforea.eventhelper.util.EventUtils");
        } catch (ClassNotFoundException e) {
            FMLLog.bigWarning("EventUtils not found");
        }
        event.registerServerCommand(new CommandChangeChestsToLootCases());
        event.registerServerCommand(new CommandReloadLootcase());
        event.registerServerCommand(new ResetTime());
        if (true) {
            event.registerServerCommand(new CommandSaveStructure());
            event.registerServerCommand(new CommandCheckLoot());
        }
        event.registerServerCommand(new CommandLootChance());
        event.registerServerCommand(new CommandSetDay());
        event.registerServerCommand(CommandWth.instance);
    }

    @Mod.EventHandler
    public void serverStopping(FMLServerStoppedEvent event) {
        WeatherHandler.instance.fogManagerMap.forEach((i, e) -> e.setStarted(false));
        WeatherHandler.instance.rainManagerMap.forEach((i, e) -> e.setStarted(false));
        SpecialGenerator.instance.cities.clear();
        DynamicEventsGenerator.instance.targets.clear();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) throws InterruptedException {
        synchronized (CommonProxy.MONITOR) {
            if (!CommonProxy.structuresLoaded) {
                CommonProxy.MONITOR.wait();
            }
        }
        if (FMLLaunchHandler.side().isClient()) {
            Main.settingsZp = new SettingsZp(Minecraft.getMinecraft().mcDataDir);
        }
        Main.settingsZombieMiningZp = new SettingsZombieMiningZp(FMLLaunchHandler.side().isClient() ? Minecraft.getMinecraft().mcDataDir : new File("config"));
        Main.settingsZombieMiningZp.loadOptions();
        for (String text : nothingToSeeHere) {
            FMLLog.info(text);
        }
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.err.println(t + " has unhandled exception:" + e);
    }

    public enum ModStatus {
        ALPHA(true, "a"),
        BETA(true, "b"),
        DEV(true, "dev"),
        DEMO(true, "dem"),
        UNSTABLE(true, "uns"),
        STABLE(false, "stb"),
        EXP(true, "exp");

        private final String ver;
        private final boolean displayUnstableWarning;

        ModStatus(boolean displayUnstableWarning, String ver) {
            this.displayUnstableWarning = displayUnstableWarning;
            this.ver = ver;
        }

        public boolean isDisplayUnstableWarning() {
            return this.displayUnstableWarning;
        }

        public String getVer() {
            return this.ver;
        }
    }
}