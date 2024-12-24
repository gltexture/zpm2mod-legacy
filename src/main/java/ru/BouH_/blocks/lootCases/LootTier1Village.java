package ru.BouH_.blocks.lootCases;

import net.minecraft.block.Block;
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

public class LootTier1Village extends BlockLootCase {
    public static final Set<LootSpawnManager> lootManagerSet = new HashSet<>();
    private static final Set<ItemToSpawn> toolList = new HashSet<>();
    private static final Set<ItemToSpawn> foodList = new HashSet<>();
    private static final Set<ItemToSpawn> miscList = new HashSet<>();
    private static final Set<ItemToSpawn> blockList = new HashSet<>();
    private static final Set<ItemToSpawn> armorList = new HashSet<>();

    static {
        lootManagerSet.add(new LootSpawnManager(LootTier1Village.armorList, 1, 1, 0.85f, 5));
        lootManagerSet.add(new LootSpawnManager(LootTier1Village.toolList, 1, 2, 0.05f, 15));
        lootManagerSet.add(new LootSpawnManager(LootTier1Village.foodList, 1, 2, 0.05f, 5));
        lootManagerSet.add(new LootSpawnManager(LootTier1Village.miscList, 1, 2, 0.05f, 40));
        lootManagerSet.add(new LootSpawnManager(LootTier1Village.blockList, 1, 2, 0.05f, 35));
    }

    static {

        // Tools
        armorList.add(new ItemToSpawn(Items.leather_helmet, 1.0f, 0.8f, 25));
        armorList.add(new ItemToSpawn(ItemsZp.rotten_chestplate, 1.0f, 0.8f, 25));
        armorList.add(new ItemToSpawn(ItemsZp.rotten_leggings, 1.0f, 0.8f, 25));
        armorList.add(new ItemToSpawn(ItemsZp.rotten_boots, 1.0f, 0.8f, 25));

        toolList.add(new ItemToSpawn(ItemsZp.armature, 1.0f, 0.75f, 9));
        toolList.add(new ItemToSpawn(ItemsZp.pipe, 1.0f, 0.75f, 9));
        toolList.add(new ItemToSpawn(ItemsZp.screwdriver, 1.0f, 0.75f, 9));
        toolList.add(new ItemToSpawn(ItemsZp.hammer, 1.0f, 0.75f, 9));
        toolList.add(new ItemToSpawn(Items.wooden_axe, 1.0f, 0.75f, 6));
        toolList.add(new ItemToSpawn(Items.wooden_hoe, 1.0f, 0.75f, 6));
        toolList.add(new ItemToSpawn(Items.wooden_pickaxe, 1.0f, 0.75f, 6));
        toolList.add(new ItemToSpawn(Items.wooden_sword, 1.0f, 0.75f, 6));
        toolList.add(new ItemToSpawn(Items.wooden_shovel, 1.0f, 0.75f, 6));
        toolList.add(new ItemToSpawn(ItemsZp.copper_axe, 1.0f, 0.75f, 6));
        toolList.add(new ItemToSpawn(ItemsZp.copper_hoe, 1.0f, 0.75f, 6));
        toolList.add(new ItemToSpawn(ItemsZp.copper_pickaxe, 1.0f, 0.75f, 6));
        toolList.add(new ItemToSpawn(ItemsZp.copper_sword, 1.0f, 0.75f, 6));
        toolList.add(new ItemToSpawn(ItemsZp.copper_shovel, 1.0f, 0.75f, 6));
        toolList.add(new ItemToSpawn(ItemsZp.cleaver, 0.5f, 0.85f, 4));

        // Food

        foodList.add(new ItemToSpawn(ItemsZp.water, 1, 20));
        foodList.add(new ItemToSpawn(ItemsZp.cactus_water, 1, 15));
        foodList.add(new ItemToSpawn(ItemsZp.rotten_apple, 1, 15));
        foodList.add(new ItemToSpawn(Items.fish, new ItemToSpawn.IntRange(0, 2), 1, 5));
        foodList.add(new ItemToSpawn(Items.carrot, 1, 5));
        foodList.add(new ItemToSpawn(Items.potato, 2, 0.85f, 5));
        foodList.add(new ItemToSpawn(Items.melon, 2, 0.85f, 5));
        foodList.add(new ItemToSpawn(Items.bread, 1, 5));
        foodList.add(new ItemToSpawn(Items.porkchop, 1, 5));
        foodList.add(new ItemToSpawn(Items.beef, 1, 5));
        foodList.add(new ItemToSpawn(Items.apple, 1, 1, 5));
        foodList.add(new ItemToSpawn(Items.chicken, 1, 5));
        foodList.add(new ItemToSpawn(ItemsZp.fish_canned, 1, 5));

        // Misc

        miscList.add(new ItemToSpawn(ItemsZp.custom_gunpowder, 1, 2));
        miscList.add(new ItemToSpawn(Items.egg, 2, 0.1f, 3));
        miscList.add(new ItemToSpawn(Items.brick, 1, 8));
        miscList.add(new ItemToSpawn(ItemsZp.bellows, 1, 1));
        miscList.add(new ItemToSpawn(ItemsZp.chisel, 1, 1));
        miscList.add(new ItemToSpawn(ItemsZp.table, 1, 2));
        miscList.add(new ItemToSpawn(Items.clay_ball, 1, 10));
        miscList.add(new ItemToSpawn(ItemsZp.plate, 1, 10));
        miscList.add(new ItemToSpawn(Items.stick, 1, 10));
        miscList.add(new ItemToSpawn(Items.string, 1, 8));
        miscList.add(new ItemToSpawn(Items.reeds, 1, 8));
        miscList.add(new ItemToSpawn(Items.melon_seeds, 1, 5));
        miscList.add(new ItemToSpawn(Items.wheat_seeds, 1, 5));
        miscList.add(new ItemToSpawn(Items.pumpkin_seeds, 1, 5));
        miscList.add(new ItemToSpawn(Items.leather, 1, 6));
        miscList.add(new ItemToSpawn(Items.coal, 1, 1, 4));
        miscList.add(new ItemToSpawn(Items.arrow, 6, 0.85f, 5));
        miscList.add(new ItemToSpawn(ItemsZp.scrap_material, 1, 4));
        miscList.add(new ItemToSpawn(ItemsZp.matches, 1, 0.7f, 1));
        miscList.add(new ItemToSpawn(ItemsZp.bandage, 1, 2));

        // Block

        blockList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.stone), 6, 0.85f, 25));
        blockList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.cobblestone), 6, 0.85f, 25));
        blockList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.torch), 1, 25));
        blockList.add(new ItemToSpawn(Item.getItemFromBlock(BlocksZp.torch2), 2, 0.85f, 25));
    }

    public LootTier1Village(int p_i45397_1_) {
        super(p_i45397_1_);
        this.setStepSound(new Block.SoundType("wood", 1.0F, 1.0F));
    }

    public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_) {
        super.onBlockPlacedBy(p_149689_1_, p_149689_2_, p_149689_3_, p_149689_4_, p_149689_5_, p_149689_6_);
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return null;
    }

    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileLootCase(EnumLootGroups.Tier1Village, 0.4f, true, this.chestType);
    }
}
