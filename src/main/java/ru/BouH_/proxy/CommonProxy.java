package ru.BouH_.proxy;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.BlockDispenser;
import net.minecraft.block.material.Material;
import net.minecraft.dispenser.BehaviorDefaultDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import ru.BouH_.ConfigZp;
import ru.BouH_.Main;
import ru.BouH_.effects.*;
import ru.BouH_.fun.tiles.TileC300Block;
import ru.BouH_.fun.tiles.TileKalibrBlock;
import ru.BouH_.fun.tiles.TilePancirBlock;
import ru.BouH_.gameplay.*;
import ru.BouH_.init.*;
import ru.BouH_.inventory.GuiHandler;
import ru.BouH_.items.gun.events.GunLivingEvent;
import ru.BouH_.moving.LivingMovingEvents;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.recipe_master.RecipeMaster;
import ru.BouH_.skills.SkillManager;
import ru.BouH_.tiles.*;
import ru.BouH_.weather.base.WeatherHandler;
import ru.BouH_.world.Generators;
import ru.BouH_.world.WorldZp;
import ru.BouH_.world.biome.*;
import ru.BouH_.world.generator.BunkerGenerator;
import ru.BouH_.world.generator.DynamicEventsGenerator;
import ru.BouH_.world.generator.RadiationGenerator;
import ru.BouH_.world.generator.StructureGenerator;
import ru.BouH_.world.generator.cities.SpecialGenerator;
import ru.BouH_.world.type.WorldTypeCrazyZp;
import ru.BouH_.world.type.WorldTypeHardcoreZp;
import ru.BouH_.world.type.WorldTypeZp;
import ru.BouH_.zpm_recipes.LubricantGunRecipe;
import ru.BouH_.zpm_recipes.PoisonousFoodRecipe;
import ru.BouH_.zpm_recipes.RepairToolRecipe;
import ru.BouH_.zpm_recipes.WeaponRepairRecipe;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static net.minecraftforge.oredict.RecipeSorter.Category.SHAPED;
import static net.minecraftforge.oredict.RecipeSorter.Category.SHAPELESS;


public class CommonProxy {
    public static boolean structuresLoaded = false;
    public static final Object MONITOR = new Object();

    private static final Set<Item> deleteCraft = new HashSet<>();
    public static boolean initRecipes = false;
    public static BiomeGenBase biome_rad1;
    public static BiomeGenBase biome_rad2;
    public static BiomeGenBase biome_gas;
    public static BiomeGenBase biome_acid;
    public static BiomeGenBase biome_antiZm;

    public static BiomeGenBase biome_city;
    public static BiomeGenBase biome_industry;
    public static BiomeGenBase biome_military;

    public static WorldType worldTypeZp = new WorldTypeZp("world_zp");
    public static WorldType worldTypeCrazyZp = new WorldTypeCrazyZp("world_crazy_zp");
    public static WorldType worldTypeHardCoreZp = new WorldTypeHardcoreZp("world_hard_zp");

    public static Potion betterVision;
    public static Potion zpm;
    public static Potion brokenLeg;
    public static Potion bleeding;
    public static Potion adrenaline;
    public static Potion legImmune;
    public static Potion dehydration;
    public static Potion tire;
    public static Potion antiradiation;
    public static Generators generators = new Generators();
    public static Thread loadStructures = null;

    static {
        deleteCraft.add(Item.getItemFromBlock(Blocks.crafting_table));
        deleteCraft.add(Items.brewing_stand);
        deleteCraft.add(Item.getItemFromBlock(Blocks.hopper));
        deleteCraft.add(Items.blaze_rod);
        deleteCraft.add(Items.blaze_powder);
        deleteCraft.add(Items.glass_bottle);
        deleteCraft.add(Item.getItemFromBlock(Blocks.anvil));
        deleteCraft.add(Items.hopper_minecart);
        deleteCraft.add(Items.book);
        deleteCraft.add(Items.compass);
        deleteCraft.add(Item.getItemFromBlock(Blocks.ender_chest));
        deleteCraft.add(Items.pumpkin_seeds);
        deleteCraft.add(Item.getItemFromBlock(Blocks.beacon));
        deleteCraft.add(Items.golden_apple);
        deleteCraft.add(Item.getItemFromBlock(Blocks.glowstone));
        deleteCraft.add(Items.golden_carrot);
        deleteCraft.add(Items.chainmail_helmet);
        deleteCraft.add(Items.chainmail_chestplate);
        deleteCraft.add(Items.chainmail_leggings);
        deleteCraft.add(Items.chainmail_boots);
        deleteCraft.add(Item.getItemFromBlock(Blocks.iron_bars));
        deleteCraft.add(Item.getItemFromBlock(Blocks.furnace));
        deleteCraft.add(Item.getItemFromBlock(Blocks.tnt));
        deleteCraft.add(Items.bed);
    }

    public void registerEvents() {
        MinecraftForge.EVENT_BUS.register(new EntityManager());
        MinecraftForge.EVENT_BUS.register(SpecialGenerator.instance);
        MinecraftForge.EVENT_BUS.register(WeatherHandler.instance);
        MinecraftForge.EVENT_BUS.register(LivingMovingEvents.instance);
        FMLCommonHandler.instance().bus().register(WeatherHandler.instance);
        FMLCommonHandler.instance().bus().register(WorldManager.instance);
        FMLCommonHandler.instance().bus().register(DynamicEventsGenerator.instance);
        MinecraftForge.EVENT_BUS.register(DynamicEventsGenerator.instance);
        //FMLCommonHandler.instance().bus().register(new NavThread());
        MinecraftForge.EVENT_BUS.register(WorldManager.instance);
        MinecraftForge.ORE_GEN_BUS.register(new WorldGenManager());
        MinecraftForge.TERRAIN_GEN_BUS.register(new WorldGenManager());
        MinecraftForge.EVENT_BUS.register(PlayerManager.instance);
        FMLCommonHandler.instance().bus().register(PlayerManager.instance);
        FMLCommonHandler.instance().bus().register(new GunLivingEvent());

        MinecraftForge.EVENT_BUS.register(BucketFilling.instance);
    }

    public void registerBaseZp() throws IllegalAccessException {
        NetworkHandler.registerPackets();
        NetworkHandler.registerPacketsClient();
        ItemsZp.init();
        FluidsZp.init();
        FluidsZp.register();
        BlocksZp.init();
        BlocksZp.register();
        this.registerBiomes();
        ItemsZp.register();
        EntitiesZp.registerEntityDisplacer();
        EntitiesZp.registerEntitySpawn();
        EntitiesZp.registerEntity();
        LootCasesZp.init();
        LootCasesZp.register();
    }

    public void registerRenders() {
        NetworkRegistry.INSTANCE.registerGuiHandler(Main.instance, new GuiHandler());
    }

    public void registerKeyBindings() {

    }

    public void registerGen() {
        CommonProxy.generators.init(SpecialGenerator.instance);
        CommonProxy.generators.init(DynamicEventsGenerator.instance);
        CommonProxy.generators.init(RadiationGenerator.instance);
        CommonProxy.generators.init(StructureGenerator.instance);
        CommonProxy.generators.init(BunkerGenerator.instance);
        CommonProxy.loadStructures = new Thread(() -> {
            CommonProxy.generators.loadFiles();
            synchronized (CommonProxy.MONITOR) {
                CommonProxy.MONITOR.notifyAll();
                CommonProxy.structuresLoaded = true;
            }
        });
        CommonProxy.loadStructures.start();
    }

    public void registerBiomes() {
        DimensionManager.registerProviderType(2, WorldZp.class, true);
        DimensionManager.registerDimension(2, 2);

        CommonProxy.biome_rad1 = new BiomeRad(40).setBiomeName("biome_rad1");
        CommonProxy.biome_rad2 = new BiomeRad(41).setBiomeName("biome_rad2");
        CommonProxy.biome_gas = new BiomeGas(42).setBiomeName("biome_gas");
        CommonProxy.biome_acid = new BiomeGas(43).setBiomeName("biome_acid");
        CommonProxy.biome_antiZm = new BiomeAntiZm(44).setBiomeName("biome_antiZm");

        CommonProxy.biome_city = new BiomeCity(45).setBiomeName("biome_city");
        CommonProxy.biome_industry = new BiomeIndustry(46).setBiomeName("biome_industry");
        CommonProxy.biome_military = new BiomeMilitary(47).setBiomeName("biome_military");

        CommonProxy.biome_rad1.createMutation();
        CommonProxy.biome_rad2.createMutation();
        CommonProxy.biome_gas.createMutation();
        CommonProxy.biome_acid.createMutation();
        CommonProxy.biome_antiZm.createMutation();
        CommonProxy.biome_city.createMutation();
        CommonProxy.biome_industry.createMutation();
        CommonProxy.biome_military.createMutation();
    }

    public void registerStackData() {
        if (ConfigZp.increasedBlockHardness) {
            Blocks.obsidian.setHardness(6.0f);
            Blocks.obsidian.setResistance(0.5f);
            Blocks.brick_block.setHardness(6.0f);
            Blocks.brick_stairs.setHardness(6.0f);
            Blocks.stone_slab.setHardness(5.0f);
            Blocks.stone_brick_stairs.setHardness(6.0f);
            Blocks.cobblestone_wall.setHardness(4.0f);
            Blocks.iron_bars.setHardness(10.0f);
            Blocks.stonebrick.setHardness(6.0f);
            Blocks.iron_door.setHardness(10.0f);
            Blocks.iron_block.setHardness(15.0f);
            Blocks.gold_block.setHardness(15.0f);
            Blocks.emerald_block.setHardness(35.0f);
            Blocks.diamond_block.setHardness(20.0f);
            Blocks.redstone_block.setHardness(7.5f);
            Blocks.coal_block.setHardness(9.5f);
            Blocks.lapis_block.setHardness(12.5f);
            Blocks.ladder.setHardness(2.0f);
        }

        Items.diamond_helmet.setMaxDurability(282);
        Items.diamond_chestplate.setMaxDurability(392);
        Items.diamond_leggings.setMaxDurability(370);
        Items.diamond_boots.setMaxDurability(326);

        Items.diamond_sword.setMaxDurability(286);
        Items.diamond_shovel.setMaxDurability(356);
        Items.diamond_pickaxe.setMaxDurability(304);
        Items.diamond_axe.setMaxDurability(316);
        Items.diamond_hoe.setMaxDurability(272);

        Items.shears.setMaxDurability(64);
        Items.bow.setMaxDurability(128);

        if (ConfigZp.newStackSizes) {
            Items.potato.setMaxStackSize(16);
            Items.baked_potato.setMaxStackSize(16);
            Items.golden_carrot.setMaxStackSize(16);
            Items.pumpkin_pie.setMaxStackSize(16);
            Items.poisonous_potato.setMaxStackSize(16);
            Items.emerald.setMaxStackSize(16);
            Items.spider_eye.setMaxStackSize(16);
            Items.rotten_flesh.setMaxStackSize(16);
            Items.cooked_chicken.setMaxStackSize(16);
            Items.chicken.setMaxStackSize(16);
            Items.cooked_beef.setMaxStackSize(16);
            Items.beef.setMaxStackSize(16);
            Items.melon.setMaxStackSize(16);
            Items.cookie.setMaxStackSize(16);
            Items.cooked_fish.setMaxStackSize(16);
            Items.fish.setMaxStackSize(16);
            Items.leather.setMaxStackSize(16);
            Items.golden_apple.setMaxStackSize(1);
            Items.cooked_porkchop.setMaxStackSize(16);
            Items.porkchop.setMaxStackSize(16);
            Items.fermented_spider_eye.setMaxStackSize(16);
            Items.blaze_powder.setMaxStackSize(16);
            Items.slime_ball.setMaxStackSize(16);
            Items.book.setMaxStackSize(16);
            Items.magma_cream.setMaxStackSize(16);
            Items.speckled_melon.setMaxStackSize(16);
            Items.milk_bucket.setMaxStackSize(1);
            Items.bucket.setMaxStackSize(1);
            Items.nether_star.setMaxStackSize(1);
            Items.skull.setMaxStackSize(1);
            Items.bread.setMaxStackSize(16);
            Items.diamond.setMaxStackSize(16);
            Items.iron_ingot.setMaxStackSize(16);
            Items.gold_ingot.setMaxStackSize(16);
            Items.apple.setMaxStackSize(16);
            Items.carrot.setMaxStackSize(16);
            Items.glass_bottle.setMaxStackSize(16);
            Items.blaze_rod.setMaxStackSize(1);
            Items.ender_eye.setMaxStackSize(1);
            Items.ender_pearl.setMaxStackSize(1);
            Items.brewing_stand.setMaxStackSize(1);

            Items.experience_bottle.setMaxStackSize(16);

            Item.getItemFromBlock(Blocks.tnt).setMaxStackSize(3);
            Item.getItemFromBlock(Blocks.diamond_block).setMaxStackSize(4);
            Item.getItemFromBlock(Blocks.iron_block).setMaxStackSize(6);
            Item.getItemFromBlock(Blocks.gold_block).setMaxStackSize(4);
            Item.getItemFromBlock(Blocks.emerald_block).setMaxStackSize(4);
            Item.getItemFromBlock(Blocks.lapis_block).setMaxStackSize(4);

            Item.getItemFromBlock(Blocks.coal_ore).setMaxStackSize(16);
            Item.getItemFromBlock(Blocks.diamond_ore).setMaxStackSize(16);
            Item.getItemFromBlock(Blocks.emerald_ore).setMaxStackSize(16);
            Item.getItemFromBlock(Blocks.lapis_ore).setMaxStackSize(16);
            Item.getItemFromBlock(Blocks.quartz_ore).setMaxStackSize(16);
            Item.getItemFromBlock(Blocks.iron_ore).setMaxStackSize(16);
            Item.getItemFromBlock(Blocks.redstone_ore).setMaxStackSize(16);
            Item.getItemFromBlock(Blocks.gold_ore).setMaxStackSize(16);
            Item.getItemFromBlock(Blocks.bookshelf).setMaxStackSize(16);
            Items.gunpowder.setMaxStackSize(16);
        }
    }

    public void registerEffects() {
        this.extendPotions();
        betterVision = new EffectBetterVision(25, false, 0x00FA9A);
        zpm = new EffectZombiePlague(26, true, 0x808000);
        brokenLeg = new EffectBrokenLeg(27, true, 0xFFE4B5);
        bleeding = new EffectBleeding(28, true, 0xff0000);
        adrenaline = new EffectAdrenaline(29, false, 0xFF6347);
        legImmune = new EffectBrokenLegImmune(30, false, 0x98FB98);
        dehydration = new EffectDehydration(31, false, 0x8FBC8F);
        tire = new EffectTire(32, false, 0xB5B8B1);
        antiradiation = new EffectAntiradiation(33, false, 0xe0c417);

        MinecraftForge.EVENT_BUS.register(bleeding);
        MinecraftForge.EVENT_BUS.register(adrenaline);
        MinecraftForge.EVENT_BUS.register(zpm);
    }

    public void registerTileEntities() {
        GameRegistry.registerTileEntity(TileTorch.class, "torch");
        GameRegistry.registerTileEntity(TilePumpkin.class, "pumpkin");
        GameRegistry.registerTileEntity(TileLava.class, "lava");
        GameRegistry.registerTileEntity(TileBrewingStand.class, "brew");
        GameRegistry.registerTileEntity(TileLootCase.class, "lootCase");
        GameRegistry.registerTileEntity(TileLootSafe.class, "lootSafe");
        GameRegistry.registerTileEntity(TileBlockWithDamage.class, "wire");

        GameRegistry.registerTileEntity(TileC300Block.class, "c300");
        GameRegistry.registerTileEntity(TileKalibrBlock.class, "kalibr");
        GameRegistry.registerTileEntity(TilePancirBlock.class, "pancir");
    }

    @SuppressWarnings("unchecked")
    public void registerCrafts() {
        Iterator<IRecipe> removerRecipes = CraftingManager.getInstance().getRecipeList().iterator();
        while (removerRecipes.hasNext()) {
            ItemStack itemStack = removerRecipes.next().getRecipeOutput();
            if (itemStack != null) {
                if (deleteCraft.contains(itemStack.getItem())) {
                    removerRecipes.remove();
                } else if (itemStack.getItem() == Items.dye) {
                    if (itemStack.getMetadata() == 15) {
                        removerRecipes.remove();
                    }
                }
            }
        }

        RecipeSorter.register("minecraft:poisonousfood", PoisonousFoodRecipe.PoisonousFoodShapelessRecipe.class, SHAPELESS, "after:minecraft:shapeless");
        RecipeSorter.register("minecraft:weaponrepair", WeaponRepairRecipe.RepairShapelessRecipe.class, SHAPELESS, "after:minecraft:shapeless");
        RecipeSorter.register("minecraft:weaponlubricant", LubricantGunRecipe.LubricantShapelessRecipe.class, SHAPELESS, "after:minecraft:shapeless");
        RecipeSorter.register("minecraft:weaponreptool", RepairToolRecipe.RepairToolShapedRecipe.class, SHAPED, "after:minecraft:shaped");

        PoisonousFoodRecipe.addPoisonRecipe(CraftingManager.getInstance(), new ItemStack(Items.apple));
        PoisonousFoodRecipe.addPoisonRecipe(CraftingManager.getInstance(), new ItemStack(Items.mushroom_stew));
        PoisonousFoodRecipe.addPoisonRecipe(CraftingManager.getInstance(), new ItemStack(Items.bread));
        PoisonousFoodRecipe.addPoisonRecipe(CraftingManager.getInstance(), new ItemStack(Items.porkchop));
        PoisonousFoodRecipe.addPoisonRecipe(CraftingManager.getInstance(), new ItemStack(Items.cooked_porkchop));
        PoisonousFoodRecipe.addPoisonRecipe(CraftingManager.getInstance(), new ItemStack(Items.golden_apple, 1, 0));
        PoisonousFoodRecipe.addPoisonRecipe(CraftingManager.getInstance(), new ItemStack(Items.golden_apple, 1, 1));
        PoisonousFoodRecipe.addPoisonRecipe(CraftingManager.getInstance(), new ItemStack(Items.fish, 1, 0));
        PoisonousFoodRecipe.addPoisonRecipe(CraftingManager.getInstance(), new ItemStack(Items.fish, 1, 1));
        PoisonousFoodRecipe.addPoisonRecipe(CraftingManager.getInstance(), new ItemStack(Items.fish, 1, 2));
        PoisonousFoodRecipe.addPoisonRecipe(CraftingManager.getInstance(), new ItemStack(Items.fish, 1, 3));
        PoisonousFoodRecipe.addPoisonRecipe(CraftingManager.getInstance(), new ItemStack(Items.cooked_fish, 1, 0));
        PoisonousFoodRecipe.addPoisonRecipe(CraftingManager.getInstance(), new ItemStack(Items.cooked_fish, 1, 1));
        PoisonousFoodRecipe.addPoisonRecipe(CraftingManager.getInstance(), new ItemStack(Items.cookie));
        PoisonousFoodRecipe.addPoisonRecipe(CraftingManager.getInstance(), new ItemStack(Items.melon));
        PoisonousFoodRecipe.addPoisonRecipe(CraftingManager.getInstance(), new ItemStack(Items.beef));
        PoisonousFoodRecipe.addPoisonRecipe(CraftingManager.getInstance(), new ItemStack(Items.cooked_beef));
        PoisonousFoodRecipe.addPoisonRecipe(CraftingManager.getInstance(), new ItemStack(Items.chicken));
        PoisonousFoodRecipe.addPoisonRecipe(CraftingManager.getInstance(), new ItemStack(Items.cooked_chicken));
        PoisonousFoodRecipe.addPoisonRecipe(CraftingManager.getInstance(), new ItemStack(Items.carrot));
        PoisonousFoodRecipe.addPoisonRecipe(CraftingManager.getInstance(), new ItemStack(Items.potato));
        PoisonousFoodRecipe.addPoisonRecipe(CraftingManager.getInstance(), new ItemStack(Items.baked_potato));
        PoisonousFoodRecipe.addPoisonRecipe(CraftingManager.getInstance(), new ItemStack(Items.golden_carrot));
        PoisonousFoodRecipe.addPoisonRecipe(CraftingManager.getInstance(), new ItemStack(Items.pumpkin_pie));

        GameRegistry.addRecipe(new ItemStack(ItemsZp.table), "II", "FF", 'F', Blocks.planks, 'I', Items.stick);
        GameRegistry.addRecipe(new ItemStack(BlocksZp.wooden_stakes, 4), "FFF", "FFF", "III", 'F', Items.stick, 'I', Blocks.planks);

        GameRegistry.addRecipe(new ItemStack(Blocks.crafting_table, 1), "IR", "SS", 'I', ItemsZp.chisel, 'R', ItemsZp.shelves, 'S', new ItemStack(Blocks.log, 1, OreDictionary.WILDCARD_VALUE));

        GameRegistry.addRecipe(new ItemStack(Blocks.crafting_table, 1), "IR", "SS", 'I', ItemsZp.chisel, 'R', ItemsZp.shelves, 'S', new ItemStack(Blocks.log2, 1, OreDictionary.WILDCARD_VALUE));

        GameRegistry.addShapelessRecipe(new ItemStack(ItemsZp.pnv), new ItemStack(ItemsZp.pnv, 1, OreDictionary.WILDCARD_VALUE), ItemsZp.battery);
        GameRegistry.addShapelessRecipe(new ItemStack(ItemsZp.gps), new ItemStack(ItemsZp.gps, 1, OreDictionary.WILDCARD_VALUE), ItemsZp.battery);

        GameRegistry.addShapelessRecipe(new ItemStack(Items.pumpkin_seeds), Blocks.pumpkin);

        GameRegistry.addShapelessRecipe(new ItemStack(Items.potionitem), Items.glass_bottle, Items.snowball);
        GameRegistry.addShapelessRecipe(new ItemStack(ItemsZp.cactus_water), Items.glass_bottle, Blocks.cactus);
        GameRegistry.addShapelessRecipe(new ItemStack(ItemsZp.cactus_food), Items.bowl, Blocks.cactus);

        GameRegistry.addRecipe(new ItemStack(ItemsZp.wrench), "F F", " F ", " F ", 'F', Items.iron_ingot);

        GameRegistry.addRecipe(new ItemStack(Blocks.iron_bars, 8), "FFF", "FFF", 'F', Items.iron_ingot);

        GameRegistry.addRecipe(new ItemStack(ItemsZp.bellows), "FSF", 'F', Items.leather, 'S', Items.string);

        GameRegistry.addRecipe(new ItemStack(Blocks.furnace), "FFF", "SES", "FFF", 'F', Blocks.cobblestone, 'S', Items.flint, 'E', ItemsZp.bellows);

        GameRegistry.addRecipe(new ItemStack(ItemsZp.tire), "FS ", "SFS", " SF", 'F', ItemsZp.bandage, 'S', Items.stick);

        GameRegistry.addRecipe(new ItemStack(BlocksZp.copper_block), "FFF", "FFF", "FFF", 'F', ItemsZp.copper_ingot);
        GameRegistry.addShapelessRecipe(new ItemStack(ItemsZp.copper_ingot, 9), BlocksZp.copper_block);

        GameRegistry.addRecipe(new ItemStack(Blocks.anvil), "FFF", " F ", "III", 'I', ItemsZp.steel_ingot, 'F', Blocks.iron_block);

        GameRegistry.addRecipe(new ItemStack(ItemsZp.toxicwater_bucket), "I", "F", 'I', ItemsZp.rot_mass, 'F', Items.water_bucket);

        GameRegistry.addRecipe(new ItemStack(ItemsZp.trap_grenade), "F", "I", 'I', Items.rotten_flesh, 'F', ItemsZp.frag_grenade);

        GameRegistry.addRecipe(new ItemStack(ItemsZp.plate_meat), "I", "F", 'I', Items.rotten_flesh, 'F', ItemsZp.plate);

        GameRegistry.addRecipe(new ItemStack(ItemsZp.chisel), "F", "I", 'I', Items.flint, 'F', Items.stick);

        GameRegistry.addRecipe(new ItemStack(ItemsZp.shelves), "FF", "FF", 'F', ItemsZp.table);

        GameRegistry.addRecipe(new ItemStack(ItemsZp.scare_sword), "F", "F", "I", 'I', Items.stick, 'F', ItemsZp.scare_pumpkin);

        GameRegistry.addRecipe(new ItemStack(ItemsZp.caramel_sword), "F", "F", "I", 'I', Items.stick, 'F', ItemsZp.caramel);

        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 15), Items.bone);
        GameRegistry.addShapelessRecipe(new ItemStack(Items.dye, 1, 15), ItemsZp.fish_bones);

        GameRegistry.addRecipe(new ItemStack(ItemsZp.tanning, 1), "FIF", "SWS", 'I', ItemsZp.chemres, 'F', new ItemStack(Items.dye, 1, 3), 'S', Items.sugar, 'W', ItemsZp.vodka, 'D', Items.glass_bottle);

        GameRegistry.addRecipe(new ItemStack(ItemsZp.box_paper, 1), "FFF", 'F', Items.paper);

        GameRegistry.addRecipe(new ItemStack(ItemsZp.matches, 1), "FW", "SI", 'I', Items.paper, 'W', ItemsZp.box_paper, 'F', Items.coal, 'S', Items.stick);

        GameRegistry.addRecipe(new ItemStack(Items.book, 3), "FS", "IF", 'I', ItemsZp.upd_leather, 'F', ItemsZp.box_paper, 'S', Items.paper);

        GameRegistry.addRecipe(new ItemStack(ItemsZp.upd_leather, 3), "FF", "SF", 'S', ItemsZp.tanning, 'F', Items.leather);

        GameRegistry.addRecipe(new ItemStack(Items.glass_bottle, 3), " F ", "I I", " I ", 'I', Blocks.glass, 'F', Blocks.wooden_button);

        GameRegistry.addRecipe(new ItemStack(ItemsZp.chemicals1, 1), "IF", 'I', ItemsZp.chemicals1_a, 'F', ItemsZp.chemicals1_b);

        GameRegistry.addRecipe(new ItemStack(ItemsZp.chemres, 1), "YSI", "DWD", "ISY", 'I', new ItemStack(Items.dye, 1, 9), 'S', Items.sugar, 'Y', new ItemStack(Items.dye, 1, 0), 'W', Items.poisonous_potato, 'D', new ItemStack(Items.dye, 1, 15));

        GameRegistry.addRecipe(new ItemStack(ItemsZp.chemicals1_a, 1), "ILI", "SPS", "ILI", 'I', new ItemStack(Items.dye, 1, 14), 'L', ItemsZp.chemres, 'S', Items.glowstone_dust, 'P', Items.potionitem);

        GameRegistry.addRecipe(new ItemStack(ItemsZp.chemicals1_b, 1), "ILI", "SPS", "ILI", 'I', new ItemStack(Items.dye, 1, 9), 'L', ItemsZp.chemres, 'S', Items.redstone, 'P', Items.potionitem);

        GameRegistry.addRecipe(new ItemStack(ItemsZp.steel_helmet, 1), "WWW", "W W", 'W', ItemsZp.steel_ingot);
        GameRegistry.addRecipe(new ItemStack(ItemsZp.steel_chestplate, 1), "W W", "WWW", "WWW", 'W', ItemsZp.steel_ingot);
        GameRegistry.addRecipe(new ItemStack(ItemsZp.steel_leggings, 1), "WWW", "W W", "W W", 'W', ItemsZp.steel_ingot);
        GameRegistry.addRecipe(new ItemStack(ItemsZp.steel_boots, 1), "W W", "W W", 'W', ItemsZp.steel_ingot);

        GameRegistry.addRecipe(new ItemStack(ItemsZp.frame_helmet, 1), "WSW", "W W", 'W', ItemsZp.titan_ingot, 'S', ItemsZp.steel_ingot);
        GameRegistry.addRecipe(new ItemStack(ItemsZp.frame_chestplate, 1), "W W", "SWS", "WWW", 'W', ItemsZp.titan_ingot, 'S', ItemsZp.steel_ingot);
        GameRegistry.addRecipe(new ItemStack(ItemsZp.frame_leggings, 1), "SWS", "W W", "W W", 'W', ItemsZp.titan_ingot, 'S', ItemsZp.steel_ingot);
        GameRegistry.addRecipe(new ItemStack(ItemsZp.frame_boots, 1), "W W", "W W", 'W', ItemsZp.titan_ingot, 'S', ItemsZp.steel_ingot);

        GameRegistry.addRecipe(new ItemStack(ItemsZp.juggernaut_helmet, 1), "TWT", "WCW", "TST", 'W', ItemsZp.armor_material, 'S', ItemsZp.frame_helmet, 'T', ItemsZp.kevlar, 'C', Items.diamond_helmet);
        GameRegistry.addRecipe(new ItemStack(ItemsZp.juggernaut_chestplate, 1), "TWT", "WCW", "TST", 'W', ItemsZp.armor_material, 'S', ItemsZp.frame_chestplate, 'T', ItemsZp.kevlar, 'C', Items.diamond_chestplate);
        GameRegistry.addRecipe(new ItemStack(ItemsZp.juggernaut_leggings, 1), "TWT", "WCW", "TST", 'W', ItemsZp.armor_material, 'S', ItemsZp.frame_leggings, 'T', ItemsZp.kevlar, 'C', Items.diamond_leggings);
        GameRegistry.addRecipe(new ItemStack(ItemsZp.juggernaut_boots, 1), "TWT", "WCW", "TST", 'W', ItemsZp.armor_material, 'S', ItemsZp.frame_boots, 'T', ItemsZp.kevlar, 'C', Items.diamond_boots);

        GameRegistry.addRecipe(new ItemStack(ItemsZp.dynamike, 1), "T T", "SWS", "STS", 'W', Blocks.tnt, 'S', Items.string, 'T', ItemsZp.upd_leather);

        GameRegistry.addRecipe(new ItemStack(ItemsZp.m_scissors, 1), "I ", " I", 'I', ItemsZp.steel_ingot);
        GameRegistry.addRecipe(new ItemStack(ItemsZp.m_scissors, 1), " I", "I ", 'I', ItemsZp.steel_ingot);

        GameRegistry.addRecipe(new ItemStack(BlocksZp.scrap, 1), "II", "II", 'I', ItemsZp.manyscrap);

        GameRegistry.addRecipe(new ItemStack(ItemsZp.manyscrap, 1), "LLL", "LLL", "LLL", 'L', ItemsZp.scrap_material);

        GameRegistry.addRecipe(new ItemStack(Blocks.beacon, 1), "FFF", "FSF", "WIW", 'I', BlocksZp.lamp, 'F', BlocksZp.armored_glass, 'S', Items.nether_star, 'W', Blocks.obsidian);

        GameRegistry.addRecipe(new ItemStack(Items.brewing_stand, 1), "GIG", "ISI", "YTY", 'G', ItemsZp.chemicals1, 'I', ItemsZp.chemicals2, 'S', Items.blaze_rod, 'T', ItemsZp.chemicals1_c, 'Y', ItemsZp.titan_ingot);

        GameRegistry.addRecipe(new ItemStack(Items.blaze_rod, 1), "ITI", "WSW", "ITI", 'I', Items.blaze_powder, 'W', ItemsZp.acid, 'T', Items.lava_bucket, 'S', ItemsZp.uran_material);

        GameRegistry.addRecipe(new ItemStack(ItemsZp.antivirus_syringe, 2), "RI ", "ISI", " IT", 'R', Items.golden_apple, 'I', Blocks.glass, 'T', ItemsZp.blood_material, 'S', ItemsZp.antidote_syringe);

        GameRegistry.addRecipe(new ItemStack(ItemsZp.virus_syringe, 1), " I ", "ISI", " IT", 'I', Blocks.glass, 'T', Items.rotten_flesh, 'S', ItemsZp.antivirus_syringe);

        GameRegistry.addRecipe(new ItemStack(ItemsZp.virus_syringe, 1), " I ", "ISI", " IT", 'I', Blocks.glass, 'T', ItemsZp.rot_mass, 'S', ItemsZp.antivirus_syringe);

        GameRegistry.addRecipe(new ItemStack(ItemsZp.death_syringe, 1), " I ", "ISI", " IT", 'I', Blocks.glass, 'T', ItemsZp.poison, 'S', Items.spider_eye);

        GameRegistry.addShapelessRecipe(new ItemStack(Items.blaze_powder, 32), Items.blaze_rod);

        GameRegistry.addRecipe(new ItemStack(ItemsZp.steel_material, 1), "WQW", "QWQ", 'Q', Items.coal, 'W', Items.iron_ingot);
        GameRegistry.addRecipe(new ItemStack(ItemsZp.steel_material, 1), "QWQ", "WQW", 'Q', Items.coal, 'W', Items.iron_ingot);
        GameRegistry.addRecipe(new ItemStack(ItemsZp.brass_material, 1), "QW", 'Q', Items.iron_ingot, 'W', ItemsZp.copper_ingot);
        GameRegistry.addRecipe(new ItemStack(BlocksZp.fortified_cobble, 4), "WQW", "QWQ", "WQW", 'Q', Blocks.planks, 'W', Blocks.cobblestone);

        GameRegistry.addSmelting(new ItemStack(Items.egg), new ItemStack(ItemsZp.boiled_egg), 0.1f);

        GameRegistry.addSmelting(new ItemStack(Items.potionitem, 1, 0), new ItemStack(ItemsZp.boiled_water), 0.1f);

        GameRegistry.addSmelting(new ItemStack(Blocks.quartz_block), new ItemStack(BlocksZp.armored_glass), 2.0f);

        GameRegistry.addSmelting(new ItemStack(ItemsZp.manyscrap), new ItemStack(Items.iron_ingot), 2.0f);

        GameRegistry.addSmelting(new ItemStack(ItemsZp.steel_material), new ItemStack(ItemsZp.steel_ingot), 1.0f);
        GameRegistry.addSmelting(new ItemStack(ItemsZp.brass_material), new ItemStack(ItemsZp.brass_nugget, 9), 1.0f);
        GameRegistry.addSmelting(new ItemStack(Item.getItemFromBlock(BlocksZp.copper_ore)), new ItemStack(ItemsZp.copper_ingot), 0.5f);
        GameRegistry.addSmelting(new ItemStack(Item.getItemFromBlock(BlocksZp.titan_ore)), new ItemStack(ItemsZp.titan_ingot), 1.25f);

        GameRegistry.addSmelting(new ItemStack(Items.rotten_flesh), new ItemStack(Items.leather), 0.25f);
        GameRegistry.addSmelting(new ItemStack(ItemsZp.raw_copper), new ItemStack(ItemsZp.copper_ingot), 0.5f);
        GameRegistry.addSmelting(new ItemStack(ItemsZp.raw_iron), new ItemStack(Items.iron_ingot), 0.7f);
        GameRegistry.addSmelting(new ItemStack(ItemsZp.raw_gold), new ItemStack(Items.gold_ingot), 1.0f);
        GameRegistry.addSmelting(new ItemStack(ItemsZp.raw_titan), new ItemStack(ItemsZp.titan_ingot), 1.25f);
        GameRegistry.addSmelting(new ItemStack(ItemsZp.scrap_material), new ItemStack(Items.iron_ingot), 0.7f);
        GameRegistry.addSmelting(new ItemStack(BlocksZp.uranium), new ItemStack(ItemsZp.uran_material), 50.0f);
        GameRegistry.addSmelting(new ItemStack(BlocksZp.scrap), new ItemStack(Items.iron_ingot, 4), 5.0f);

        for (int i = 0; i < 3; i++) {
            GameRegistry.addSmelting(new ItemStack(ItemsZp.fish_zp, 1, i), new ItemStack(ItemsZp.fish_zp_cooked, 1, i), 0.35f);
        }

        GameRegistry.addSmelting(new ItemStack(ItemsZp.mutton), new ItemStack(ItemsZp.cooked_mutton), 0.35f);
        GameRegistry.addSmelting(new ItemStack(Items.fish, 1, 2), new ItemStack(Items.cooked_fish), 0.35f);
        GameRegistry.addRecipe(new ItemStack(Blocks.glowstone, 1), "III", "III", "III", 'I', Items.glowstone_dust);
        GameRegistry.addRecipe(new ItemStack(ItemsZp.steel_sword), "F", "F", "I", 'I', Items.stick, 'F', ItemsZp.steel_ingot);
        GameRegistry.addRecipe(new ItemStack(ItemsZp.steel_axe), "FF", "IF", "I ", 'I', Items.stick, 'F', ItemsZp.steel_ingot);
        GameRegistry.addRecipe(new ItemStack(ItemsZp.steel_pickaxe), "FFF", " I ", " I ", 'I', Items.stick, 'F', ItemsZp.steel_ingot);
        GameRegistry.addRecipe(new ItemStack(ItemsZp.steel_shovel), "F", "I", "I", 'I', Items.stick, 'F', ItemsZp.steel_ingot);
        GameRegistry.addRecipe(new ItemStack(ItemsZp.steel_hoe), "FF", "I ", "I ", 'I', Items.stick, 'F', ItemsZp.steel_ingot);
        GameRegistry.addRecipe(new ItemStack(ItemsZp.titan_sword), "F", "F", "I", 'I', Items.stick, 'F', ItemsZp.titan_ingot);
        GameRegistry.addRecipe(new ItemStack(ItemsZp.titan_axe), "FF", "IF", "I ", 'I', Items.stick, 'F', ItemsZp.titan_ingot);
        GameRegistry.addRecipe(new ItemStack(ItemsZp.titan_pickaxe), "FFF", " I ", " I ", 'I', Items.stick, 'F', ItemsZp.titan_ingot);
        GameRegistry.addRecipe(new ItemStack(ItemsZp.titan_shovel), "F", "I", "I", 'I', Items.stick, 'F', ItemsZp.titan_ingot);
        GameRegistry.addRecipe(new ItemStack(ItemsZp.titan_hoe), "FF", "I ", "I ", 'I', Items.stick, 'F', ItemsZp.titan_ingot);
        GameRegistry.addRecipe(new ItemStack(ItemsZp.copper_sword), "F", "F", "I", 'I', Items.stick, 'F', ItemsZp.copper_ingot);
        GameRegistry.addRecipe(new ItemStack(ItemsZp.copper_axe), "FF", "IF", "I ", 'I', Items.stick, 'F', ItemsZp.copper_ingot);
        GameRegistry.addRecipe(new ItemStack(ItemsZp.copper_pickaxe), "FFF", " I ", " I ", 'I', Items.stick, 'F', ItemsZp.copper_ingot);
        GameRegistry.addRecipe(new ItemStack(ItemsZp.copper_shovel), "F", "I", "I", 'I', Items.stick, 'F', ItemsZp.copper_ingot);
        GameRegistry.addRecipe(new ItemStack(ItemsZp.copper_hoe), "FF", "I ", "I ", 'I', Items.stick, 'F', ItemsZp.copper_ingot);

        GameRegistry.addRecipe(new ItemStack(Items.bed), "WRR", "ITI", 'I', new ItemStack(Blocks.planks, 1, Short.MAX_VALUE), 'T', ItemsZp.shelves, 'W', new ItemStack(Blocks.wool), 'R', new ItemStack(Blocks.wool, 1, 14));

        GameRegistry.addRecipe(new ItemStack(ItemsZp.bone_knife), "F", "Q", 'F', Items.bone, 'Q', Items.flint);

        GameRegistry.addRecipe(new ItemStack(ItemsZp.bat), "SSS", "SFS", "SSS", 'F', new ItemStack(ItemsZp.bat, 1, Short.MAX_VALUE), 'S', Blocks.planks);

        GameRegistry.addRecipe(new ItemStack(ItemsZp.custom_gunpowder, 1), "FF", "IY", 'F', Items.sugar, 'I', new ItemStack(Items.dye, 1, 15), 'Y', Items.coal);
        GameRegistry.addRecipe(new ItemStack(ItemsZp.custom_gunpowder, 1), "FF", "IY", 'F', Items.sugar, 'I', new ItemStack(Items.dye, 1, 15), 'Y', new ItemStack(Items.coal, 1, 1));

        GameRegistry.addRecipe(new ItemStack(ItemsZp._custom, 32), "F", "I", "T", 'I', ItemsZp.custom_gunpowder, 'T', ItemsZp.manyscrap, 'F', Items.flint);
        GameRegistry.addRecipe(new ItemStack(ItemsZp.custom_pistol, 1), "IFC", "DDR", "  D", 'I', ItemsZp.armature, 'F', Items.iron_ingot, 'D', Blocks.planks, 'C', Blocks.lever, 'R', ItemsZp.scrap_material);

        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.copper_spear, 1), new RecipeMaster.SpecialRecipePair(SkillManager.instance.hunter, 1), "F", "F", "I", 'F', Items.stick, 'I', ItemsZp.copper_ingot);
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.steel_spear, 1), new RecipeMaster.SpecialRecipePair(SkillManager.instance.hunter, 4), "F", "F", "I", 'F', Items.stick, 'I', ItemsZp.steel_ingot);

        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp._gauss, 6), new RecipeMaster.SpecialRecipePair(SkillManager.instance.gunSmith, 1), "F", "F", 'F', ItemsZp.steel_ingot);
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp._custom2, 16), new RecipeMaster.SpecialRecipePair(SkillManager.instance.gunSmith, 1), " F ", " F ", "TIT", 'I', ItemsZp.custom_gunpowder, 'T', ItemsZp.manyscrap, 'F', Items.flint);
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.custom_revolver, 1), new RecipeMaster.SpecialRecipePair(SkillManager.instance.gunSmith, 1), "IFC", "DDF", " RD", 'I', ItemsZp.armature, 'F', Items.iron_ingot, 'D', Blocks.planks, 'C', Blocks.lever, 'R', ItemsZp.scrap_material);
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.custom_rifle, 1), new RecipeMaster.SpecialRecipePair(SkillManager.instance.gunSmith, 2), "ISC", "DDS", " WD", 'W', Blocks.lever, 'I', ItemsZp.armature, 'S', Items.iron_ingot, 'D', Blocks.planks, 'C', BlocksZp.wire);
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.custom_sniper, 1), new RecipeMaster.SpecialRecipePair(SkillManager.instance.gunSmith, 3), "IIC", " SF", " WD", 'W', Blocks.lever, 'I', ItemsZp.armature, 'S', Items.iron_ingot, 'F', ItemsZp.steel_ingot, 'D', Blocks.planks, 'C', BlocksZp.wire);
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.custom_shotgun, 1), new RecipeMaster.SpecialRecipePair(SkillManager.instance.gunSmith, 4), "IIW", " DC", "  F", 'W', Blocks.lever, 'I', ItemsZp.armature, 'F', ItemsZp.steel_ingot, 'D', Blocks.planks, 'C', ItemsZp.manyscrap);

        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(BlocksZp.lamp), new RecipeMaster.SpecialRecipePair(SkillManager.instance.survivor, 1), "FSY", 'F', Items.glowstone_dust, 'S', BlocksZp.lamp_off, 'Y', new ItemStack(ItemsZp.electrician_kit, 1, Short.MAX_VALUE));
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(BlocksZp.block_lamp), new RecipeMaster.SpecialRecipePair(SkillManager.instance.survivor, 1), "FSY", 'F', Items.glowstone_dust, 'S', BlocksZp.block_lamp_off, 'Y', new ItemStack(ItemsZp.electrician_kit, 1, Short.MAX_VALUE));
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.electrician_kit), new RecipeMaster.SpecialRecipePair(SkillManager.instance.survivor, 3), "FS", "RT", 'S', ItemsZp.electronic, 'T', Items.redstone, 'R', ItemsZp.battery, 'F', Items.shears);
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.balaclava), new RecipeMaster.SpecialRecipePair(SkillManager.instance.survivor, 1), " F ", "FIF", 'I', Items.leather_helmet, 'F', new ItemStack(Blocks.wool, 1, 13));
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(BlocksZp.concrete, 24), new RecipeMaster.SpecialRecipePair(SkillManager.instance.survivor, 2), "FIS", "SSS", "SSS", 'I', Items.water_bucket, 'F', ItemsZp.cement, 'S', Blocks.sand);
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(BlocksZp.concrete_green, 20), new RecipeMaster.SpecialRecipePair(SkillManager.instance.survivor, 2), "FIS", "SSS", "WWW", 'I', Items.water_bucket, 'F', ItemsZp.cement, 'S', Blocks.sand, 'W', new ItemStack(Items.dye, 1, 2));
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.forest_helmet, 1), new RecipeMaster.SpecialRecipePair(SkillManager.instance.survivor, 3), "IWI", "WSW", 'S', ItemsZp.upd_leather, 'W', Items.string, 'I', new ItemStack(Blocks.wool, 1, 13));
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.forest_chestplate, 1), new RecipeMaster.SpecialRecipePair(SkillManager.instance.survivor, 3), "W W", "ISI", "IWI", 'S', ItemsZp.upd_leather, 'W', Items.string, 'I', new ItemStack(Blocks.wool, 1, 13));
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.forest_leggings, 1), new RecipeMaster.SpecialRecipePair(SkillManager.instance.survivor, 3), "WIW", "WSW", "I I", 'S', ItemsZp.upd_leather, 'W', Items.string, 'I', new ItemStack(Blocks.wool, 1, 13));
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.forest_boots, 1), new RecipeMaster.SpecialRecipePair(SkillManager.instance.survivor, 3), "ISI", "W W", 'S', ItemsZp.upd_leather, 'W', Items.string, 'I', new ItemStack(Blocks.wool, 1, 13));
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.sand_helmet, 1), new RecipeMaster.SpecialRecipePair(SkillManager.instance.survivor, 3), "IWI", "WSW", 'S', ItemsZp.upd_leather, 'W', Items.string, 'I', new ItemStack(Blocks.wool, 1, 4));
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.sand_chestplate, 1), new RecipeMaster.SpecialRecipePair(SkillManager.instance.survivor, 3), "W W", "ISI", "IWI", 'S', ItemsZp.upd_leather, 'W', Items.string, 'I', new ItemStack(Blocks.wool, 1, 4));
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.sand_leggings, 1), new RecipeMaster.SpecialRecipePair(SkillManager.instance.survivor, 3), "WIW", "WSW", "I I", 'S', ItemsZp.upd_leather, 'W', Items.string, 'I', new ItemStack(Blocks.wool, 1, 4));
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.sand_boots, 1), new RecipeMaster.SpecialRecipePair(SkillManager.instance.survivor, 3), "ISI", "W W", 'S', ItemsZp.upd_leather, 'W', Items.string, 'I', new ItemStack(Blocks.wool, 1, 4));
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.winter_helmet, 1), new RecipeMaster.SpecialRecipePair(SkillManager.instance.survivor, 3), "IWI", "WSW", 'S', ItemsZp.upd_leather, 'W', Items.string, 'I', new ItemStack(Blocks.wool, 1, 0));
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.winter_chestplate, 1), new RecipeMaster.SpecialRecipePair(SkillManager.instance.survivor, 3), "W W", "ISI", "IWI", 'S', ItemsZp.upd_leather, 'W', Items.string, 'I', new ItemStack(Blocks.wool, 1, 0));
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.winter_leggings, 1), new RecipeMaster.SpecialRecipePair(SkillManager.instance.survivor, 3), "WIW", "WSW", "I I", 'S', ItemsZp.upd_leather, 'W', Items.string, 'I', new ItemStack(Blocks.wool, 1, 0));
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.winter_boots, 1), new RecipeMaster.SpecialRecipePair(SkillManager.instance.survivor, 3), "ISI", "W W", 'S', ItemsZp.upd_leather, 'W', Items.string, 'I', new ItemStack(Blocks.wool, 1, 0));
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(BlocksZp.concreteStairs, 12), new RecipeMaster.SpecialRecipePair(SkillManager.instance.survivor, 4), "S  ", "TS ", "FIS", 'I', Items.water_bucket, 'F', ItemsZp.cement, 'S', Blocks.sand, 'T', ItemsZp.armature);
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(BlocksZp.concrete2, 16), new RecipeMaster.SpecialRecipePair(SkillManager.instance.survivor, 4), "FIW", "SSS", 'I', Items.water_bucket, 'W', ItemsZp.armature, 'F', ItemsZp.cement, 'S', Blocks.sand);
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(Items.compass, 1), new RecipeMaster.SpecialRecipePair(SkillManager.instance.survivor, 3), "LLL", "LIL", "LLL", 'L', Items.iron_ingot, 'I', Blocks.redstone_torch);
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.lockpick, 1), new RecipeMaster.SpecialRecipePair(SkillManager.instance.survivor, 6), "IS", 'S', ItemsZp.steel_ingot, 'I', ItemsZp.screwdriver);

        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.lubricant), new RecipeMaster.SpecialRecipePair(SkillManager.instance.gunSmith, 1), "SFS", "BIB", 'S', Items.reeds, 'I', ItemsZp.chemres, 'F', ItemsZp.boiled_water, 'B', new ItemStack(Items.dye, 1, 0));
        ItemStack stack0 = new ItemStack(ItemsZp.custom_repair, 1);
        RepairToolRecipe.addRecipe(CraftingManager.getInstance(), new ItemStack[]{new ItemStack(ItemsZp.armature), new ItemStack(ItemsZp.armature), new ItemStack(ItemsZp.manyscrap), new ItemStack(ItemsZp.chisel)}, stack0);
        ItemStack stack = new ItemStack(ItemsZp.repair, 1);
        RepairToolRecipe.addRecipe(CraftingManager.getInstance(), new ItemStack[]{new ItemStack(ItemsZp.screwdriver), new ItemStack(ItemsZp.hammer), new ItemStack(ItemsZp.steel_ingot), new ItemStack(Items.iron_ingot)}, stack);
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.kevlar_vest), new RecipeMaster.SpecialRecipePair(SkillManager.instance.gunSmith, 2), "S S", "III", "SSS", 'I', ItemsZp.kevlar, 'S', Items.leather);
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.kevlar_helmet), new RecipeMaster.SpecialRecipePair(SkillManager.instance.gunSmith, 2), "III", "S S", 'I', ItemsZp.kevlar, 'S', Items.leather);
        RecipeMaster.instance.addSpecialRecipe(stack0, new RecipeMaster.SpecialRecipePair(SkillManager.instance.gunSmith, 1));
        RecipeMaster.instance.addSpecialRecipe(stack, new RecipeMaster.SpecialRecipePair(SkillManager.instance.gunSmith, 4));
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.ammo_press, 1), new RecipeMaster.SpecialRecipePair(SkillManager.instance.gunSmith, 4), " I ", " I ", " I ", 'I', ItemsZp.steel_ingot);
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp._22lr, 18), new RecipeMaster.SpecialRecipePair(SkillManager.instance.gunSmith, 4), " I ", "SDF", "XXX", 'X', ItemsZp.brass_nugget, 'I', ItemsZp._22lr, 'S', Items.iron_ingot, 'F', Items.gunpowder, 'D', new ItemStack(ItemsZp.ammo_press, 1, OreDictionary.WILDCARD_VALUE));
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp._9mm, 16), new RecipeMaster.SpecialRecipePair(SkillManager.instance.gunSmith, 5), " I ", "SDF", "XXX", 'X', ItemsZp.brass_nugget, 'I', ItemsZp._9mm, 'S', Items.iron_ingot, 'F', Items.gunpowder, 'D', new ItemStack(ItemsZp.ammo_press, 1, OreDictionary.WILDCARD_VALUE));
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp._7_62x25, 14), new RecipeMaster.SpecialRecipePair(SkillManager.instance.gunSmith, 5), " I ", "SDF", "XXX", 'X', ItemsZp.brass_nugget, 'I', ItemsZp._7_62x25, 'S', Items.iron_ingot, 'F', Items.gunpowder, 'D', new ItemStack(ItemsZp.ammo_press, 1, OreDictionary.WILDCARD_VALUE));
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp._45acp, 14), new RecipeMaster.SpecialRecipePair(SkillManager.instance.gunSmith, 5), " I ", "SDF", "XXX", 'X', ItemsZp.brass_nugget, 'I', ItemsZp._45acp, 'S', Items.iron_ingot, 'F', Items.gunpowder, 'D', new ItemStack(ItemsZp.ammo_press, 1, OreDictionary.WILDCARD_VALUE));
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp._12, 8), new RecipeMaster.SpecialRecipePair(SkillManager.instance.gunSmith, 6), " I ", "SDF", "XXX", 'X', ItemsZp.brass_nugget, 'I', ItemsZp._12, 'S', Items.iron_ingot, 'F', Items.gunpowder, 'D', new ItemStack(ItemsZp.ammo_press, 1, OreDictionary.WILDCARD_VALUE));
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.solid_fuel, 2), new RecipeMaster.SpecialRecipePair(SkillManager.instance.gunSmith, 7), "FF", "FI", 'I', ItemsZp.chemres, 'F', ItemsZp.custom_gunpowder);
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp._customRocket, 1), new RecipeMaster.SpecialRecipePair(SkillManager.instance.gunSmith, 7), "IF ", "FIF", " FY", 'I', Items.gunpowder, 'F', Items.iron_ingot, 'Y', ItemsZp.solid_fuel);
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(BlocksZp.wire, 4), new RecipeMaster.SpecialRecipePair(SkillManager.instance.hunter, 3), " I ", "I I", " I ", 'I', Items.iron_ingot);
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.lucille), new RecipeMaster.SpecialRecipePair(SkillManager.instance.hunter, 1), "FS", 'F', ItemsZp.bat, 'S', BlocksZp.wire);
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.crossbow, 1), new RecipeMaster.SpecialRecipePair(SkillManager.instance.hunter, 2), " I ", "IFC", " CD", 'I', Blocks.planks, 'F', Items.bow, 'D', Blocks.tripwire_hook, 'C', Blocks.lever);
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.mjolnir), new RecipeMaster.SpecialRecipePair(SkillManager.instance.hunter, 3), "FS ", "FWT", "FS ", 'F', Items.redstone, 'S', ItemsZp.electronic, 'W', ItemsZp.sledgehammer, 'T', ItemsZp.battery);
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.inferno), new RecipeMaster.SpecialRecipePair(SkillManager.instance.hunter, 5), "FFF", "TWS", " Y ", 'Y', ItemsZp.electronic, 'F', Blocks.redstone_torch, 'S', ItemsZp.coils, 'W', ItemsZp.crowbar, 'T', ItemsZp.battery);
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(BlocksZp.mine, 1), new RecipeMaster.SpecialRecipePair(SkillManager.instance.hunter, 5), "IFI", "SSS", 'I', ItemsZp.manyscrap, 'F', Item.getItemFromBlock(BlocksZp.wire), 'S', ItemsZp.custom_gunpowder);
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.ripper), new RecipeMaster.SpecialRecipePair(SkillManager.instance.hunter, 6), " FS", "TWY", " FS", 'S', Items.redstone, 'F', ItemsZp.battery, 'W', ItemsZp.iron_club, 'T', Items.iron_ingot, 'Y', ItemsZp.electronic);
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(BlocksZp.wire_reinforced, 1), new RecipeMaster.SpecialRecipePair(SkillManager.instance.hunter, 4), "FI", 'I', BlocksZp.wire, 'F', ItemsZp.armature);

        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.tnt), new RecipeMaster.SpecialRecipePair(SkillManager.instance.miner, 2), "XUX", "III", "XXX", 'I', Items.gunpowder, 'X', Blocks.sand, 'U', Items.string);
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(Blocks.tnt), new RecipeMaster.SpecialRecipePair(SkillManager.instance.miner, 4), "UUU", "III", 'I', ItemsZp.tnt, 'U', Items.string);
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.stimulator), new RecipeMaster.SpecialRecipePair(SkillManager.instance.miner, 5), " I ", "XYX", "SDF", 'Y', Items.glowstone_dust, 'X', Items.redstone, 'I', Blocks.brown_mushroom, 'S', new ItemStack(ItemsZp.fish_zp, 1, 3), 'F', new ItemStack(ItemsZp.fish_zp, 1, 4), 'D', Items.potionitem);

        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(Items.golden_carrot), new RecipeMaster.SpecialRecipePair(SkillManager.instance.farmer, 1)," I ", "ISI", " I ", 'I', Items.gold_ingot, 'S', Items.carrot);
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(Items.golden_apple), new RecipeMaster.SpecialRecipePair(SkillManager.instance.farmer, 3)," I ", "ISI", " I ", 'I', Items.gold_ingot, 'S', Items.apple);
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.mem_elixir), new RecipeMaster.SpecialRecipePair(SkillManager.instance.farmer, 8), "XYZ", "WDF", "BNM", 'X', ItemsZp.morphine, 'Y', Items.rotten_flesh, 'Z', ItemsZp.cactus_water, 'W', ItemsZp.custom_gunpowder, 'D', ItemsZp.vodka, 'F', ItemsZp.antiheadache, 'B', Items.poisonous_potato, 'N', ItemsZp.chemicals1, 'M', ItemsZp.boiled_egg);
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.poison), new RecipeMaster.SpecialRecipePair(SkillManager.instance.farmer, 5), "III", "IXI", "III", 'I', Items.poisonous_potato, 'X', ItemsZp.antiheadache);
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.steroid), new RecipeMaster.SpecialRecipePair(SkillManager.instance.farmer, 10), "ITI", "TXT", "IYI", 'I', Items.golden_carrot, 'X', ItemsZp.antiheadache, 'T', Items.baked_potato, 'Y', ItemsZp.stimulator);

        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.rotten_chestplate, 1), new RecipeMaster.SpecialRecipePair(SkillManager.instance.survivor, 1), "I I", "IWI", "III", 'W', Items.string, 'I', Items.rotten_flesh);
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.rotten_leggings, 1), new RecipeMaster.SpecialRecipePair(SkillManager.instance.farmer, 1), "IWI", "I I", "I I", 'W', Items.string, 'I', Items.rotten_flesh);
        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.rotten_boots, 1), new RecipeMaster.SpecialRecipePair(SkillManager.instance.gunSmith, 1), "I I", "I I", 'I', Items.rotten_flesh);

        RecipeMaster.instance.addSpecialShapedRecipe(new ItemStack(ItemsZp.gauss, 1), new RecipeMaster.SpecialRecipePair(SkillManager.instance.hunter, 5), "FFF", "TTT", "IIU", 'I', ItemsZp.steel_ingot, 'T', ItemsZp.electronic, 'F', ItemsZp.coils, 'U', ItemsZp.battery);

        RecipeMaster.instance.formRecipeMap(CraftingManager.getInstance().getRecipeList());
        RecipeMaster.instance.formSmeltingMap(FurnaceRecipes.instance().getSmeltingList());
        initRecipes = true;
        OreDictionary.initVanillaEntries();
    }

    public void registerDispenses() {
        EntitiesZp.registerEntityDisplacer();
        BlockDispenser.dispenseBehaviorRegistry.putObject(ItemsZp.matches, new BehaviorDefaultDispenseItem() {
            protected ItemStack dispenseStack(IBlockSource p_82487_1_, ItemStack p_82487_2_) {
                EnumFacing enumfacing = BlockDispenser.getFacingDirection(p_82487_1_.getBlockMetadata());
                World world = p_82487_1_.getWorld();
                int i = p_82487_1_.getXInt() + enumfacing.getFrontOffsetX();
                int j = p_82487_1_.getYInt() + enumfacing.getFrontOffsetY();
                int k = p_82487_1_.getZInt() + enumfacing.getFrontOffsetZ();

                if (world.isAirBlock(i, j, k)) {
                    world.setBlock(i, j, k, Blocks.fire);

                    if (p_82487_2_.attemptDamageItem(1, world.rand)) {
                        p_82487_2_.stackSize = 0;
                    }
                } else if (world.getBlock(i, j, k) == Blocks.tnt) {
                    Blocks.tnt.onBlockDestroyedByPlayer(world, i, j, k, 1);
                    world.setBlockToAir(i, j, k);
                }

                return p_82487_2_;
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(ItemsZp.toxicwater_bucket, new BehaviorDefaultDispenseItem() {
            private final BehaviorDefaultDispenseItem field_150841_b = new BehaviorDefaultDispenseItem();

            public ItemStack dispenseStack(IBlockSource p_82487_1_, ItemStack p_82487_2_) {
                ItemBucket itembucket = (ItemBucket) p_82487_2_.getItem();
                int i = p_82487_1_.getXInt();
                int j = p_82487_1_.getYInt();
                int k = p_82487_1_.getZInt();
                EnumFacing enumfacing = BlockDispenser.getFacingDirection(p_82487_1_.getBlockMetadata());

                if (itembucket.tryPlaceContainedLiquid(p_82487_1_.getWorld(), i + enumfacing.getFrontOffsetX(), j + enumfacing.getFrontOffsetY(), k + enumfacing.getFrontOffsetZ())) {
                    p_82487_2_.setItem(Items.bucket);
                    p_82487_2_.stackSize = 1;
                    return p_82487_2_;
                } else {
                    return this.field_150841_b.dispense(p_82487_1_, p_82487_2_);
                }
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(ItemsZp.acid_bucket, new BehaviorDefaultDispenseItem() {
            private final BehaviorDefaultDispenseItem field_150841_b = new BehaviorDefaultDispenseItem();

            public ItemStack dispenseStack(IBlockSource p_82487_1_, ItemStack p_82487_2_) {
                ItemBucket itembucket = (ItemBucket) p_82487_2_.getItem();
                int i = p_82487_1_.getXInt();
                int j = p_82487_1_.getYInt();
                int k = p_82487_1_.getZInt();
                EnumFacing enumfacing = BlockDispenser.getFacingDirection(p_82487_1_.getBlockMetadata());

                if (itembucket.tryPlaceContainedLiquid(p_82487_1_.getWorld(), i + enumfacing.getFrontOffsetX(), j + enumfacing.getFrontOffsetY(), k + enumfacing.getFrontOffsetZ())) {
                    p_82487_2_.setItem(Items.bucket);
                    p_82487_2_.stackSize = 1;
                    return p_82487_2_;
                } else {
                    return this.field_150841_b.dispense(p_82487_1_, p_82487_2_);
                }
            }
        });
        BlockDispenser.dispenseBehaviorRegistry.putObject(Items.bucket, new BehaviorDefaultDispenseItem() {
            private final BehaviorDefaultDispenseItem field_150840_b = new BehaviorDefaultDispenseItem();

            public ItemStack dispenseStack(IBlockSource p_82487_1_, ItemStack p_82487_2_) {
                EnumFacing enumfacing = BlockDispenser.getFacingDirection(p_82487_1_.getBlockMetadata());
                World world = p_82487_1_.getWorld();
                int i = p_82487_1_.getXInt() + enumfacing.getFrontOffsetX();
                int j = p_82487_1_.getYInt() + enumfacing.getFrontOffsetY();
                int k = p_82487_1_.getZInt() + enumfacing.getFrontOffsetZ();
                Material material = world.getBlock(i, j, k).getMaterial();
                int l = world.getBlockMetadata(i, j, k);
                Item item;

                if (world.getBlock(i, j, k) == FluidsZp.toxicwater_block && l == 0) {
                    item = ItemsZp.toxicwater_bucket;
                } else if (world.getBlock(i, j, k) == FluidsZp.acidblock) {
                    if (l != 0 || Main.rand.nextFloat() >= 0.03f) {
                        return super.dispenseStack(p_82487_1_, p_82487_2_);
                    }
                    item = ItemsZp.acid_bucket;
                } else if (Material.water.equals(material) && l == 0) {
                    item = Items.water_bucket;
                } else {
                    if (!Material.lava.equals(material) || l != 0 || Main.rand.nextFloat() >= 0.25f) {
                        return super.dispenseStack(p_82487_1_, p_82487_2_);
                    }

                    item = Items.lava_bucket;
                }

                world.setBlockToAir(i, j, k);

                if (--p_82487_2_.stackSize == 0) {
                    p_82487_2_.setItem(item);
                    p_82487_2_.stackSize = 1;
                } else if (((TileEntityDispenser) p_82487_1_.getBlockTileEntity()).func_146019_a(new ItemStack(item)) < 0) {
                    this.field_150840_b.dispense(p_82487_1_, new ItemStack(item));
                }

                return p_82487_2_;
            }
        });
    }

    private void extendPotions() {
        for (Field f : Potion.class.getDeclaredFields()) {
            f.setAccessible(true);
            try {
                if (f.getName().equals("potionTypes") || f.getName().equals("field_76425_a")) {
                    Field modfield = Field.class.getDeclaredField("modifiers");
                    modfield.setAccessible(true);
                    modfield.setInt(f, f.getModifiers() & ~Modifier.FINAL);
                    Potion[] potionTypes = (Potion[]) f.get(null);
                    final Potion[] newPotionTypes = new Potion[256];
                    System.arraycopy(potionTypes, 0, newPotionTypes, 0, potionTypes.length);
                    f.set(null, newPotionTypes);
                    return;
                }
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }
}
