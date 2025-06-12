package ru.BouH_;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class ConfigZp {
    public static boolean forceZombieTrackingUpdate;
    public static boolean forcePlayerTrackingUpdate;
    public static boolean enableKitStart;
    public static int plagueZoneType;
    public static float lootMultiplier;
    public static boolean explosionDestruction;
    public static boolean longDays;
    public static boolean longNights;
    public static boolean isAirdropEnabled;
    public static boolean clientHigherBrightness;
    public static boolean achievementsSystem;

    public static boolean skillsSystemCrafts;
    public static boolean skillsSystemProgression;

    public static boolean dynamicEvents;
    public static boolean zombieDifficultyProgression;
    public static float zombieSpawnScale;
    public static boolean negativeLevel;
    public static boolean night7;

    public static boolean newStackSizes;
    public static boolean increasedBlockHardness;
    public static int cityBiomeSpawnWeights;
    public static boolean acidWorksInPrivates;
    public static boolean disabledWaterInfSources;

    public static boolean disableMilitaryCities;
    public static boolean disableIndustrialCities;
    public static boolean disableCommonCities;
    public static boolean openEnderChestsViaEye;

    public static float bagsFromZombiesChanceMultiplier;
    public static float bleedingChanceMultiplier;

    public static boolean Dim2Dim0Sync;

    public static float hungerExhaustionMultiplier;
    public static float thirstExhaustionMultiplier;

    public static float rainAndFogChanceMultiplier;
    public static int lootRespawnLootcaseTime;

    public void register() {
        Configuration config = new Configuration(new File("config", "zpm2.cfg"));
        config.defaultEncoding = "UTF-8";
        config.load();

        night7 = config.getBoolean("night7", "default", true, "7 Days To Die");
        forceZombieTrackingUpdate = config.getBoolean("forceZombieTrackingUpdate", "default", false, "Zombie tracking update. Bugfix");
        forcePlayerTrackingUpdate = config.getBoolean("forcePlayerTrackingUpdate", "default", false, "Player tracking update. Bugfix");
        enableKitStart = config.getBoolean("enableKitStart", "default", true, "Kit start");
        explosionDestruction = config.getBoolean("explosionDestruction", "default", true, "Explosion destruction");
        plagueZoneType = config.getInt("plagueZoneType", "default", 2, 0, 3, "Type of Plague Zone generation[0 = FLAT, 1 = DEFAULT, 2 = ZPW, 3 = ZPF]");
        lootMultiplier = config.getFloat("lootMultiplier", "default", 1.0f, 0.0f, 100.0f, "Loot-luckiness chance multiplier");
        longDays = config.getBoolean("longDays", "default", true, "Longer Days");
        longNights = config.getBoolean("longNights", "default", true, "Longer Nights");
        isAirdropEnabled = config.getBoolean("isAirdropEnabled", "default", true, "Airdrops");
        clientHigherBrightness = config.getBoolean("clientHigherBrightness", "default", false, "Increases the brightness of the players. ATTENTION! Setting can spoil the impression of the game. If the value is \"false\" on the server, the setting will not work on the client");
        achievementsSystem = config.getBoolean("achievementsSystem", "default", true, "Advancements");

        skillsSystemCrafts = config.getBoolean("skillsSystemProgression", "default", true, "Skills Crafts");
        skillsSystemProgression = config.getBoolean("skillsSystemCrafts", "default", true, "Skills Progression");

        dynamicEvents = config.getBoolean("dynamicEvents", "default", true, "Dynamic Events");
        zombieDifficultyProgression = config.getBoolean("zombieDifficultyProgression", "default", true, "Zombie Progression");
        zombieSpawnScale = config.getFloat("zombieSpawnScale", "default", 1.0f, 0.0f, 100.0f, "Zombie Spawn Scale");
        negativeLevel = config.getBoolean("negativeLevel", "default", true, "Negative Level");
        increasedBlockHardness = config.getBoolean("increasedBlockHardness", "default", true, "Increased Block Hardness");
        newStackSizes = config.getBoolean("newStackSizes", "default", true, "New Stack Sizes");
        cityBiomeSpawnWeights = config.getInt("cityBiomeSpawnWeights", "default", 30, 1, 300, "Chance to spawn city in ZPW");
        acidWorksInPrivates = config.getBoolean("acidWorksInPrivates", "default", true, "Acid bottles disabled/enabled in private zones(WG)");
        disabledWaterInfSources = config.getBoolean("disabledWaterInfSources", "default", true, "Disable infinite water sources");

        disableMilitaryCities = config.getBoolean("disableMilitaryCities", "default", false, "Disable Military Cities");
        disableIndustrialCities = config.getBoolean("disableIndustrialCities", "default", false, "Disable Industrial Cities");
        disableCommonCities = config.getBoolean("disableCommonCities", "default", false, "Disable Common Cities");
        openEnderChestsViaEye = config.getBoolean("openEnderChestsViaEye", "default", true, "Open Ender Chests with ender-eye");

        bagsFromZombiesChanceMultiplier = config.getFloat("bagsFromZombiesChanceMultiplier", "default", 1.0f, 0.0f, 100.0f, "Bag drop from zombies chance multiplier");
        bleedingChanceMultiplier = config.getFloat("bleedingChanceMultiplier", "default", 1.0f, 0.0f, 100.0f, "Bleeding Chance Multiplier");
        Dim2Dim0Sync = config.getBoolean("Dim2Dim0Sync", "default", false, "Enable, if you have issues with Dim-0 and Dim-2 time-sync.");

        hungerExhaustionMultiplier = config.getFloat("hungerExhaustionMultiplier", "default", 1.0f, 0.0f, 100.0f, "Hunger Exhaustion Multiplier");
        thirstExhaustionMultiplier = config.getFloat("thirstExhaustionMultiplier", "default", 1.0f, 0.0f, 100.0f, "Thirst Exhaustion Multiplier");

        rainAndFogChanceMultiplier = config.getFloat("rainAndFogChanceMultiplier", "default", 1.0f, 0.0f, 100.0f, "Rain And Fog Chance Multiplier");
        lootRespawnLootcaseTime = config.getInt("lootRespawnLootcaseTime", "default", 36000, 1, 3600000, "Loot Respawn Lootcase Time(ticks). (20 ticks = 1 sec)");
        config.save();
    }
}