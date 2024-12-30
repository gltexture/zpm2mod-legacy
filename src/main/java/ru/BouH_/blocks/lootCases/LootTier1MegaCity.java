package ru.BouH_.blocks.lootCases;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ru.BouH_.init.BlocksZp;
import ru.BouH_.init.ItemsZp;
import ru.BouH_.tiles.TileLootCase;
import ru.BouH_.tiles.loot_spawn.ItemToSpawn;
import ru.BouH_.tiles.loot_spawn.LootSpawnManager;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class LootTier1MegaCity extends BlockLootCase {
    public static final Set<LootSpawnManager> lootManagerSet = new HashSet<>();
    private static final Set<ItemToSpawn> gunList = new HashSet<>();
    private static final Set<ItemToSpawn> ammoList = new HashSet<>();
    private static final Set<ItemToSpawn> medList = new HashSet<>();
    private static final Set<ItemToSpawn> armorList = new HashSet<>();
    private static final Set<ItemToSpawn> specialList = new HashSet<>();
    private static final Set<ItemToSpawn> toolList = new HashSet<>();
    private static final Set<ItemToSpawn> foodList = new HashSet<>();
    private static final Set<ItemToSpawn> miscList = new HashSet<>();
    private static final Set<ItemToSpawn> modList = new HashSet<>();
    private static final Set<ItemToSpawn> blockList = new HashSet<>();

    static {
        lootManagerSet.add(new LootSpawnManager(LootTier1MegaCity.gunList, 1, 3));
        lootManagerSet.add(new LootSpawnManager(LootTier1MegaCity.ammoList, 1, 2, 0.1f, 3));
        lootManagerSet.add(new LootSpawnManager(LootTier1MegaCity.medList, 1, 3));
        lootManagerSet.add(new LootSpawnManager(LootTier1MegaCity.specialList, 1, 6));
        lootManagerSet.add(new LootSpawnManager(LootTier1MegaCity.toolList, 1, 3, 0.03f, 16));
        lootManagerSet.add(new LootSpawnManager(LootTier1MegaCity.armorList, 1, 2, 0.3f, 6));
        lootManagerSet.add(new LootSpawnManager(LootTier1MegaCity.foodList, 1, 3, 0.05f, 8));
        lootManagerSet.add(new LootSpawnManager(LootTier1MegaCity.miscList, 1, 3, 0.08f, 36));
        lootManagerSet.add(new LootSpawnManager(LootTier1MegaCity.blockList, 1, 3, 0.08f, 18));
        lootManagerSet.add(new LootSpawnManager(LootTier1MegaCity.modList, 1, 1));
    }

    static {

        // Weapons

        gunList.add(new ItemToSpawn(ItemsZp.crossbow, 0.3f, 1.0f, 0.88f, 6));
        gunList.add(new ItemToSpawn(ItemsZp.pm, 1.0f, 0.82f, 10));
        gunList.add(new ItemToSpawn(ItemsZp.mac10, 1.0f, 0.82f, 6));
        gunList.add(new ItemToSpawn(ItemsZp.mini_uzi, 1.0f, 0.82f, 6));
        gunList.add(new ItemToSpawn(ItemsZp.tt, 1.0f, 0.82f, 8));
        gunList.add(new ItemToSpawn(ItemsZp.m1911, 1.0f, 0.82f, 8));
        gunList.add(new ItemToSpawn(ItemsZp.toz66, 1.0f, 0.82f, 8));
        gunList.add(new ItemToSpawn(ItemsZp.toz66_short, 1.0f, 0.82f, 5));
        gunList.add(new ItemToSpawn(ItemsZp.sporter, 1.0f, 0.82f, 10));
        gunList.add(new ItemToSpawn(ItemsZp.walther, 1.0f, 0.82f, 10));
        gunList.add(new ItemToSpawn(ItemsZp.m1895, 1.0f, 0.82f, 5));
        gunList.add(new ItemToSpawn(ItemsZp.flare, 1.0f, 0.82f, 2));
        gunList.add(new ItemToSpawn(ItemsZp.casull290, 1.0f, 0.7f, 8));
        gunList.add(new ItemToSpawn(ItemsZp.mp40, 1.0f, 0.875f, 4));
        gunList.add(new ItemToSpawn(ItemsZp.ppsh, 1.0f, 0.875f, 4));

        // Ammo

        ammoList.add(new ItemToSpawn(ItemsZp._9mm, 12, 0.84f, 12));
        ammoList.add(new ItemToSpawn(ItemsZp._7_62x25, 12, 0.84f, 12));
        ammoList.add(new ItemToSpawn(ItemsZp._12, 3, 0.62f, 8));
        ammoList.add(new ItemToSpawn(ItemsZp._7_62x39, 8, 0.84f, 12));
        ammoList.add(new ItemToSpawn(ItemsZp._22lr, 24, 0.84f, 16));
        ammoList.add(new ItemToSpawn(ItemsZp._357m, 8, 0.84f, 8));
        ammoList.add(new ItemToSpawn(ItemsZp._45acp, 12, 0.84f, 10));
        ammoList.add(new ItemToSpawn(ItemsZp._7_62x54R, 6, 0.84f, 3));
        ammoList.add(new ItemToSpawn(Items.arrow, 16, 0.86f, 8));
        ammoList.add(new ItemToSpawn(ItemsZp._flare, 1, 1));
        ammoList.add(new ItemToSpawn(ItemsZp._custom, 8, 0.9f, 6));
        ammoList.add(new ItemToSpawn(ItemsZp._custom2, 2, 0.6f, 4));

        // Armor

        armorList.add(new ItemToSpawn(Items.leather_helmet, 1.0f, 0.8f, 9));
        armorList.add(new ItemToSpawn(Items.leather_chestplate, 1.0f, 0.8f, 9));
        armorList.add(new ItemToSpawn(Items.leather_leggings, 1.0f, 0.8f, 9));
        armorList.add(new ItemToSpawn(Items.leather_boots, 1.0f, 0.8f, 9));

        armorList.add(new ItemToSpawn(Items.chainmail_helmet, 1.0f, 0.8f, 8));
        armorList.add(new ItemToSpawn(Items.chainmail_chestplate, 1.0f, 0.8f, 8));
        armorList.add(new ItemToSpawn(Items.chainmail_leggings, 1.0f, 0.8f, 8));
        armorList.add(new ItemToSpawn(Items.chainmail_boots, 1.0f, 0.8f, 8));

        armorList.add(new ItemToSpawn(Items.golden_helmet, 1.0f, 0.8f, 8));
        armorList.add(new ItemToSpawn(Items.golden_chestplate, 1.0f, 0.8f, 8));
        armorList.add(new ItemToSpawn(Items.golden_leggings, 1.0f, 0.8f, 8));
        armorList.add(new ItemToSpawn(Items.golden_boots, 1.0f, 0.8f, 8));

        // Tools

        toolList.add(new ItemToSpawn(ItemsZp.m_scissors, 1.0f, 0.75f, 1));
        toolList.add(new ItemToSpawn(ItemsZp.wrench, 1.0f, 0.75f, 2));
        toolList.add(new ItemToSpawn(ItemsZp.armature, 1.0f, 0.82f, 10));
        toolList.add(new ItemToSpawn(ItemsZp.crowbar, 1.0f, 0.82f, 9));
        toolList.add(new ItemToSpawn(ItemsZp.golf_club, 1.0f, 0.82f, 11));
        toolList.add(new ItemToSpawn(ItemsZp.cleaver, 1.0f, 0.82f, 9));
        toolList.add(new ItemToSpawn(ItemsZp.bat, 1.0f, 0.82f, 11));
        toolList.add(new ItemToSpawn(ItemsZp.hammer, 1.0f, 0.82f, 9));
        toolList.add(new ItemToSpawn(ItemsZp.screwdriver, 1.0f, 0.82f, 9));
        toolList.add(new ItemToSpawn(ItemsZp.pipe, 1.0f, 0.82f, 12));
        toolList.add(new ItemToSpawn(ItemsZp.sledgehammer, 1.0f, 0.82f, 4));
        toolList.add(new ItemToSpawn(ItemsZp.hatchet, 1.0f, 0.82f, 5));
        toolList.add(new ItemToSpawn(Items.iron_axe, 1.0f, 0.82f, 4));
        toolList.add(new ItemToSpawn(Items.iron_sword, 1.0f, 0.82f, 4));

        // Food

        foodList.add(new ItemToSpawn(ItemsZp.water, 1, 10));
        foodList.add(new ItemToSpawn(ItemsZp.cola, 1, 15));
        foodList.add(new ItemToSpawn(ItemsZp.pepsi, 1, 15));
        foodList.add(new ItemToSpawn(ItemsZp.pea, 1, 5));
        foodList.add(new ItemToSpawn(ItemsZp.ananas, 1, 5));
        foodList.add(new ItemToSpawn(ItemsZp.banana, 1, 6));
        foodList.add(new ItemToSpawn(ItemsZp.orange, 1, 6));
        foodList.add(new ItemToSpawn(ItemsZp.soup, 1, 5));
        foodList.add(new ItemToSpawn(ItemsZp.hotdog, 1, 5));
        foodList.add(new ItemToSpawn(ItemsZp.burger, 1, 5));
        foodList.add(new ItemToSpawn(ItemsZp.fish_canned, 1, 5));
        foodList.add(new ItemToSpawn(ItemsZp.donut, 1, 5));
        foodList.add(new ItemToSpawn(ItemsZp.jam, 1, 5));
        foodList.add(new ItemToSpawn(ItemsZp.burn, 1, 3));
        foodList.add(new ItemToSpawn(ItemsZp.beer, 1, 4));
        foodList.add(new ItemToSpawn(ItemsZp.vodka, 1, 1));

        // Mod

        modList.add(new ItemToSpawn(ItemsZp.scope_pu, 1, 99));
        modList.add(new ItemToSpawn(ItemsZp.hunting_scope, 1, 1));

        // Misc

        miscList.add(new ItemToSpawn(ItemsZp.balaclava, 1.0f, 0.8f, 2));
        miscList.add(new ItemToSpawn(ItemsZp.plate, 3, 0.82f, 10));
        miscList.add(new ItemToSpawn(Items.paper, 3, 0.82f, 5));
        miscList.add(new ItemToSpawn(ItemsZp.custom_gunpowder, 4, 0.7f, 4));
        miscList.add(new ItemToSpawn(Items.gunpowder, 1, 4));
        miscList.add(new ItemToSpawn(ItemsZp.box_paper, 1, 8));
        miscList.add(new ItemToSpawn(ItemsZp.tanning, 1, 1));
        miscList.add(new ItemToSpawn(Items.string, 1, 4));
        miscList.add(new ItemToSpawn(ItemsZp.matches, 1, 0.9f, 8));
        miscList.add(new ItemToSpawn(ItemsZp.scrap_material, 3, 0.7f, 14));
        miscList.add(new ItemToSpawn(ItemsZp.manyscrap, 1, 6));
        miscList.add(new ItemToSpawn(Items.redstone, 8, 0.6f, 5));
        miscList.add(new ItemToSpawn(Items.coal, 4, 0.5f, 6));
        miscList.add(new ItemToSpawn(Items.iron_ingot, 1, 2));
        miscList.add(new ItemToSpawn(Items.gold_ingot, 1, 2));
        miscList.add(new ItemToSpawn(ItemsZp.raw_iron, 1, 2));
        miscList.add(new ItemToSpawn(ItemsZp.raw_gold, 1, 2));
        miscList.add(new ItemToSpawn(ItemsZp.raw_copper, 1, 2));
        miscList.add(new ItemToSpawn(ItemsZp.copper_ingot, 1, 2));
        miscList.add(new ItemToSpawn(ItemsZp.brass_material, 3, 0.5f, 2));
        miscList.add(new ItemToSpawn(ItemsZp.bellows, 1, 1));
        miscList.add(new ItemToSpawn(ItemsZp.chisel, 1, 2));
        miscList.add(new ItemToSpawn(ItemsZp.table, 1, 2));
        miscList.add(new ItemToSpawn(ItemsZp.shelves, 1, 1));
        miscList.add(new ItemToSpawn(ItemsZp.electronic, 1, 1));
        miscList.add(new ItemToSpawn(ItemsZp.battery, 1, 1));
        miscList.add(new ItemToSpawn(ItemsZp.coils, 1, 1));

        // Med

        medList.add(new ItemToSpawn(ItemsZp.bandage, 1, 25));
        medList.add(new ItemToSpawn(ItemsZp.antiheadache, 1, 20));
        medList.add(new ItemToSpawn(ItemsZp.antipoison, 1, 20));
        medList.add(new ItemToSpawn(ItemsZp.antihunger, 1, 20));
        medList.add(new ItemToSpawn(ItemsZp.tire, 1, 6));
        medList.add(new ItemToSpawn(ItemsZp.meth, 1, 1));
        medList.add(new ItemToSpawn(ItemsZp.coke, 1, 1));
        medList.add(new ItemToSpawn(ItemsZp.heroin, 1, 1));
        medList.add(new ItemToSpawn(ItemsZp.good_vision, 1, 4));
        medList.add(new ItemToSpawn(ItemsZp.night_vision, 1, 2));

        // Specials

        specialList.add(new ItemToSpawn(ItemsZp.cement, 1, 26));
        specialList.add(new ItemToSpawn(ItemsZp.electrician_kit, 1.0f, 0.8f, 4));
        specialList.add(new ItemToSpawn(Item.getItemFromBlock(BlocksZp.lamp), 1, 18));
        specialList.add(new ItemToSpawn(Items.diamond, 1, 4));
        specialList.add(new ItemToSpawn(ItemsZp.dosimeter, 1.0F, 0.5F, 18));
        specialList.add(new ItemToSpawn(ItemsZp.oxygen, 1.0f, 0.5f, 16));
        specialList.add(new ItemToSpawn(ItemsZp.lockpick, 1, 2));
        specialList.add(new ItemToSpawn(Items.glowstone_dust, 2, 0.2f, 12));

        // Block

        blockList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.chest), 2, 0.3f, 4));
        blockList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.furnace), 2, 0.3f, 4));
        blockList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.crafting_table), 1, 4));
        blockList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.fence), 1, 5));
        blockList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.glass), 4, 0.82f, 14));
        blockList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.stone), 16, 0.82f, 16));
        blockList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.cobblestone), 16, 0.82f, 17));
        blockList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.torch), 4, 0.82f, 22));
        blockList.add(new ItemToSpawn(Item.getItemFromBlock(BlocksZp.torch2), 8, 0.82f, 10));
        blockList.add(new ItemToSpawn(Item.getItemFromBlock(BlocksZp.fortified_cobble), 2, 0.5f, 2));
        blockList.add(new ItemToSpawn(Item.getItemFromBlock(BlocksZp.wooden_stakes), 3, 0.5f, 2));
    }

    public LootTier1MegaCity(int p_i45397_1_) {
        super(p_i45397_1_);
        this.setStepSound(new SoundType("wood", 1.0F, 1.0F));
    }

    public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_) {
        super.onBlockPlacedBy(p_149689_1_, p_149689_2_, p_149689_3_, p_149689_4_, p_149689_5_, p_149689_6_);
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return null;
    }

    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileLootCase(EnumLootGroups.Tier1MegaCity, 0.6f, true, this.chestType);
    }
}
