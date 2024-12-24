package ru.BouH_.blocks.lootCases;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ru.BouH_.init.ItemsZp;
import ru.BouH_.tiles.TileLootCase;
import ru.BouH_.tiles.loot_spawn.ItemToSpawn;
import ru.BouH_.tiles.loot_spawn.LootSpawnManager;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class LootTier1Food extends BlockLootCase {
    public static final Set<LootSpawnManager> lootManagerSet = new HashSet<>();
    private static final Set<ItemToSpawn> foodList = new HashSet<>();
    private static final Set<ItemToSpawn> food2List = new HashSet<>();
    private static final Set<ItemToSpawn> drinkList = new HashSet<>();
    private static final Set<ItemToSpawn> drink2List = new HashSet<>();

    static {
        lootManagerSet.add(new LootSpawnManager(LootTier1Food.foodList, 1, 4, 0.5f, 45));
        lootManagerSet.add(new LootSpawnManager(LootTier1Food.food2List, 1, 5));
        lootManagerSet.add(new LootSpawnManager(LootTier1Food.drinkList, 1, 4, 0.5f, 45));
        lootManagerSet.add(new LootSpawnManager(LootTier1Food.drink2List, 1, 5));
    }

    static {

        // Food

        foodList.add(new ItemToSpawn(Items.apple, 1, 5));
        foodList.add(new ItemToSpawn(ItemsZp.hotdog, 1, 5));
        foodList.add(new ItemToSpawn(Items.bread, 1, 5));
        foodList.add(new ItemToSpawn(Items.porkchop, 1, 5));
        foodList.add(new ItemToSpawn(ItemsZp.jam, 1, 5));
        foodList.add(new ItemToSpawn(ItemsZp.burger, 1, 1, 5));
        foodList.add(new ItemToSpawn(Items.melon, 1, 5));
        foodList.add(new ItemToSpawn(Items.beef, 1, 5));
        foodList.add(new ItemToSpawn(Items.chicken, 1, 5));
        foodList.add(new ItemToSpawn(Items.carrot, 1, 5));
        foodList.add(new ItemToSpawn(Items.potato, 1, 5));
        foodList.add(new ItemToSpawn(ItemsZp.donut, 1, 5));
        foodList.add(new ItemToSpawn(Items.pumpkin_pie, 1, 5));
        foodList.add(new ItemToSpawn(ItemsZp.orange, 1, 5));
        foodList.add(new ItemToSpawn(ItemsZp.banana, 1, 5));
        foodList.add(new ItemToSpawn(ItemsZp.fish_canned, 1, 5));
        foodList.add(new ItemToSpawn(Items.fish, new ItemToSpawn.IntRange(0, 2), 1, 5));
        foodList.add(new ItemToSpawn(ItemsZp.fish_zp, new ItemToSpawn.IntRange(0, 4), 1, 5));
        foodList.add(new ItemToSpawn(ItemsZp.ananas, 1, 5));
        foodList.add(new ItemToSpawn(ItemsZp.pea, 1, 5));

        // Food2

        food2List.add(new ItemToSpawn(ItemsZp.soup, 1, 20));
        food2List.add(new ItemToSpawn(Items.cooked_beef, 1, 15));
        food2List.add(new ItemToSpawn(Items.cooked_porkchop, 1, 15));
        food2List.add(new ItemToSpawn(Items.cooked_chicken, 1, 15));
        food2List.add(new ItemToSpawn(Items.cooked_porkchop, 1, 15));
        food2List.add(new ItemToSpawn(Items.cooked_fish, new ItemToSpawn.IntRange(0, 2), 1, 10));
        food2List.add(new ItemToSpawn(ItemsZp.fish_zp_cooked, new ItemToSpawn.IntRange(0, 2), 1, 10));

        // Drink

        drinkList.add(new ItemToSpawn(ItemsZp.water, 1, 30));
        drinkList.add(new ItemToSpawn(ItemsZp.cola, 1, 35));
        drinkList.add(new ItemToSpawn(ItemsZp.pepsi, 1, 35));

        // Drink2

        drink2List.add(new ItemToSpawn(ItemsZp.nuka_cola, 1, 1));
        drink2List.add(new ItemToSpawn(ItemsZp.vodka, 1, 24));
        drink2List.add(new ItemToSpawn(ItemsZp.beer, 1, 35));
        drink2List.add(new ItemToSpawn(ItemsZp.burn, 1, 40));
    }

    public LootTier1Food(int p_i45397_1_) {
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
        return new TileLootCase(EnumLootGroups.Tier1Food, 0.8f, true, this.chestType);
    }
}
