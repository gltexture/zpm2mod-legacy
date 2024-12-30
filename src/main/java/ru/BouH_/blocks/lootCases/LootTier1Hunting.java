package ru.BouH_.blocks.lootCases;

import net.minecraft.entity.EntityLivingBase;
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

public class LootTier1Hunting extends BlockLootCase {
    public static final Set<LootSpawnManager> lootManagerSet = new HashSet<>();
    private static final Set<ItemToSpawn> gunList = new HashSet<>();
    private static final Set<ItemToSpawn> ammoList = new HashSet<>();
    private static final Set<ItemToSpawn> medList = new HashSet<>();
    private static final Set<ItemToSpawn> armorList = new HashSet<>();
    private static final Set<ItemToSpawn> camo = new HashSet<>();
    private static final Set<ItemToSpawn> specialList = new HashSet<>();
    private static final Set<ItemToSpawn> toolList = new HashSet<>();
    private static final Set<ItemToSpawn> foodList = new HashSet<>();
    private static final Set<ItemToSpawn> modList = new HashSet<>();

    static {
        lootManagerSet.add(new LootSpawnManager(LootTier1Hunting.gunList, 1, 12));
        lootManagerSet.add(new LootSpawnManager(LootTier1Hunting.ammoList, 1, 4, 0.3f, 12));
        lootManagerSet.add(new LootSpawnManager(LootTier1Hunting.medList, 1, 10));
        lootManagerSet.add(new LootSpawnManager(LootTier1Hunting.specialList, 1, 8));
        lootManagerSet.add(new LootSpawnManager(LootTier1Hunting.toolList, 1, 14));
        lootManagerSet.add(new LootSpawnManager(LootTier1Hunting.armorList, 1, 2, 0.3f, 10));
        lootManagerSet.add(new LootSpawnManager(LootTier1Hunting.camo, 1, 14));
        lootManagerSet.add(new LootSpawnManager(LootTier1Hunting.foodList, 1, 3, 0.2f, 18));
        lootManagerSet.add(new LootSpawnManager(LootTier1Hunting.modList, 1, 2));
    }

    static {

        // Weapons

        gunList.add(new ItemToSpawn(ItemsZp.crossbow, 0.3f, 1.0f, 0.88f, 10));
        gunList.add(new ItemToSpawn(ItemsZp.blaser93, 0.3f, 1.0f, 0.88f, 5));
        gunList.add(new ItemToSpawn(ItemsZp.m1895, 0.3f, 1.0f, 0.88f, 20));
        gunList.add(new ItemToSpawn(ItemsZp.mosin, 0.3f, 1.0f, 0.88f, 14));
        gunList.add(new ItemToSpawn(ItemsZp.sporter, 0.3f, 1.0f, 0.88f, 20));
        gunList.add(new ItemToSpawn(ItemsZp.toz66, 0.3f, 1.0f, 0.88f, 14));
        gunList.add(new ItemToSpawn(ItemsZp.python, 0.3f, 1.0f, 0.88f, 6));
        gunList.add(new ItemToSpawn(ItemsZp.m1894, 0.3f, 1.0f, 0.88f, 6));
        gunList.add(new ItemToSpawn(ItemsZp.flare, 0.3f, 1.0f, 0.88f, 5));

        // Ammo

        ammoList.add(new ItemToSpawn(ItemsZp._12, 5, 0.84f, 24));
        ammoList.add(new ItemToSpawn(ItemsZp._7_62x54R, 8, 0.84f, 12));
        ammoList.add(new ItemToSpawn(ItemsZp._357m, 24, 0.88f, 25));
        ammoList.add(new ItemToSpawn(ItemsZp._22lr, 32, 0.88f, 30));
        ammoList.add(new ItemToSpawn(Items.arrow, 16, 0.88f, 8));
        ammoList.add(new ItemToSpawn(ItemsZp._flare, 1, 1));

        // Armor

        armorList.add(new ItemToSpawn(Items.iron_helmet, 1.0f, 0.8f, 25));
        armorList.add(new ItemToSpawn(Items.iron_chestplate, 1.0f, 0.8f, 25));
        armorList.add(new ItemToSpawn(Items.iron_leggings, 1.0f, 0.8f, 25));
        armorList.add(new ItemToSpawn(Items.iron_boots, 1.0f, 0.8f, 25));

        // Camo

        camo.add(new ItemToSpawn(ItemsZp.winter_helmet, 1.0f, 0.8f, 7));
        camo.add(new ItemToSpawn(ItemsZp.winter_chestplate, 1.0f, 0.8f, 7));
        camo.add(new ItemToSpawn(ItemsZp.winter_leggings, 1.0f, 0.8f, 7));
        camo.add(new ItemToSpawn(ItemsZp.winter_boots, 1.0f, 0.8f, 7));

        camo.add(new ItemToSpawn(ItemsZp.forest_helmet, 1.0f, 0.8f, 7));
        camo.add(new ItemToSpawn(ItemsZp.forest_chestplate, 1.0f, 0.8f, 7));
        camo.add(new ItemToSpawn(ItemsZp.forest_leggings, 1.0f, 0.8f, 7));
        camo.add(new ItemToSpawn(ItemsZp.forest_boots, 1.0f, 0.8f, 7));

        camo.add(new ItemToSpawn(ItemsZp.sand_helmet, 1.0f, 0.8f, 7));
        camo.add(new ItemToSpawn(ItemsZp.sand_chestplate, 1.0f, 0.8f, 7));
        camo.add(new ItemToSpawn(ItemsZp.sand_leggings, 1.0f, 0.8f, 7));
        camo.add(new ItemToSpawn(ItemsZp.sand_boots, 1.0f, 0.8f, 7));

        camo.add(new ItemToSpawn(ItemsZp.balaclava, 1.0f, 0.8f, 16));

        // Tools

        toolList.add(new ItemToSpawn(ItemsZp.machete, 1.0f, 0.9f, 5));
        toolList.add(new ItemToSpawn(ItemsZp.copper_sword, 1.0f, 0.82f, 15));
        toolList.add(new ItemToSpawn(ItemsZp.copper_spear, 1.0f, 0.82f, 15));
        toolList.add(new ItemToSpawn(ItemsZp.steel_spear, 1.0f, 0.82f, 5));
        toolList.add(new ItemToSpawn(Items.iron_sword, 1.0f, 0.9f, 60));

        // Food

        foodList.add(new ItemToSpawn(ItemsZp.water, 1, 50));
        foodList.add(new ItemToSpawn(ItemsZp.stewed_meat, 1, 5));
        foodList.add(new ItemToSpawn(ItemsZp.pea, 1, 5));
        foodList.add(new ItemToSpawn(ItemsZp.ananas, 1, 5));
        foodList.add(new ItemToSpawn(Items.cooked_beef, 1, 5));
        foodList.add(new ItemToSpawn(Items.cooked_porkchop, 1, 5));
        foodList.add(new ItemToSpawn(ItemsZp.soup, 1, 10));
        foodList.add(new ItemToSpawn(ItemsZp.jam, 1, 15));

        // Med

        medList.add(new ItemToSpawn(ItemsZp.bandage, 1, 74));
        medList.add(new ItemToSpawn(ItemsZp.night_vision, 1, 4));
        medList.add(new ItemToSpawn(ItemsZp.good_vision, 1, 8));
        medList.add(new ItemToSpawn(ItemsZp.morphine, 1, 14));

        // Mod

        modList.add(new ItemToSpawn(ItemsZp.laser, 1, 5));
        modList.add(new ItemToSpawn(ItemsZp.scope4x, 1, 15));
        modList.add(new ItemToSpawn(ItemsZp.pso4x, 1, 15));
        modList.add(new ItemToSpawn(ItemsZp.hunting_scope, 1, 10));
        modList.add(new ItemToSpawn(ItemsZp.scope_pu, 1, 15));
        modList.add(new ItemToSpawn(ItemsZp.flashSuppressor_rifle, 1, 10));
        modList.add(new ItemToSpawn(ItemsZp.muzzlebrake_rifle, 1, 10));
        modList.add(new ItemToSpawn(ItemsZp.silencer_rifle, 1, 5));
        modList.add(new ItemToSpawn(ItemsZp.bipod, 1, 5));
        modList.add(new ItemToSpawn(ItemsZp.pistol_scope, 1, 10));

        // Specials

        specialList.add(new ItemToSpawn(ItemsZp.binoculars, 1, 25));
        specialList.add(new ItemToSpawn(ItemsZp.ammo_press, 0.1f, 1.0f, 0.9f, 5));
        specialList.add(new ItemToSpawn(ItemsZp.repair, 0.1f, 1.0f, 0.9f, 10));
        specialList.add(new ItemToSpawn(ItemsZp.lubricant, 1, 5));
        specialList.add(new ItemToSpawn(Items.gunpowder, 4, 0.7f, 10));
        specialList.add(new ItemToSpawn(Item.getItemFromBlock(BlocksZp.sandbag), 1, 35));
        specialList.add(new ItemToSpawn(Item.getItemFromBlock(BlocksZp.wire), 1, 5));
        specialList.add(new ItemToSpawn(ItemsZp.matches, 1.0f, 1.0f, 0.9f, 4));
        specialList.add(new ItemToSpawn(ItemsZp.pnv, 0.1f, 1.0f, 0.8f, 1));
    }

    public LootTier1Hunting(int p_i45397_1_) {
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
        return new TileLootCase(EnumLootGroups.Tier1Hunting, 0.6f, true, this.chestType);
    }
}
