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

public class LootTier2Village extends BlockLootCase {
    public static final Set<LootSpawnManager> lootManagerSet = new HashSet<>();
    private static final Set<ItemToSpawn> gunList = new HashSet<>();
    private static final Set<ItemToSpawn> ammoList = new HashSet<>();
    private static final Set<ItemToSpawn> toolList = new HashSet<>();
    private static final Set<ItemToSpawn> medList = new HashSet<>();
    private static final Set<ItemToSpawn> foodList = new HashSet<>();
    private static final Set<ItemToSpawn> miscList = new HashSet<>();
    private static final Set<ItemToSpawn> blockList = new HashSet<>();

    static {
        lootManagerSet.add(new LootSpawnManager(LootTier2Village.gunList, 1, 2));
        lootManagerSet.add(new LootSpawnManager(LootTier2Village.ammoList, 1, 1));
        lootManagerSet.add(new LootSpawnManager(LootTier2Village.medList, 1, 2));
        lootManagerSet.add(new LootSpawnManager(LootTier2Village.toolList, 1, 2, 0.05f, 12));
        lootManagerSet.add(new LootSpawnManager(LootTier2Village.blockList, 1, 2, 0.05f, 24));
        lootManagerSet.add(new LootSpawnManager(LootTier2Village.miscList, 1, 2, 0.05f, 54));
        lootManagerSet.add(new LootSpawnManager(LootTier2Village.foodList, 1, 3, 0.05f, 5));
    }
    static {

        // Weapons

        gunList.add(new ItemToSpawn(ItemsZp.pm, 0.4f, 0.65f, 20));
        gunList.add(new ItemToSpawn(ItemsZp.toz66, 0.4f, 0.65f, 10));
        gunList.add(new ItemToSpawn(ItemsZp.toz66_short, 0.4f, 0.65f, 10));
        gunList.add(new ItemToSpawn(ItemsZp.walther, 1.0f, 0.65f, 20));
        gunList.add(new ItemToSpawn(ItemsZp.custom_pistol, 1.0f, 0.75f, 8));
        gunList.add(new ItemToSpawn(ItemsZp.custom_revolver, 1.0f, 0.75f, 8));
        gunList.add(new ItemToSpawn(ItemsZp.custom_rifle, 1.0f, 0.75f, 8));
        gunList.add(new ItemToSpawn(ItemsZp.custom_shotgun, 1.0f, 0.75f, 8));
        gunList.add(new ItemToSpawn(ItemsZp.custom_sniper, 1.0f, 0.75f, 8));

        // Ammo

        ammoList.add(new ItemToSpawn(ItemsZp._22lr, 16, 0.84f, 20));
        ammoList.add(new ItemToSpawn(ItemsZp._12, 1, 25));
        ammoList.add(new ItemToSpawn(ItemsZp._9mm, 6, 0.84f, 15));
        ammoList.add(new ItemToSpawn(ItemsZp._custom, 4, 0.3f, 25));
        ammoList.add(new ItemToSpawn(ItemsZp._custom2, 1, 10));
        ammoList.add(new ItemToSpawn(Items.arrow, 6, 0.9f, 5));

        // Tools

        toolList.add(new ItemToSpawn(ItemsZp.armature, 1.0f, 0.85f, 7));
        toolList.add(new ItemToSpawn(ItemsZp.pipe, 1.0f, 0.85f, 7));
        toolList.add(new ItemToSpawn(ItemsZp.screwdriver, 1.0f, 0.85f, 7));
        toolList.add(new ItemToSpawn(ItemsZp.hammer, 1.0f, 0.85f, 7));
        toolList.add(new ItemToSpawn(Items.stone_axe, 1.0f, 0.85f, 3));
        toolList.add(new ItemToSpawn(Items.stone_hoe, 1.0f, 0.85f, 3));
        toolList.add(new ItemToSpawn(Items.stone_pickaxe, 1.0f, 0.85f, 3));
        toolList.add(new ItemToSpawn(Items.stone_sword, 1.0f, 0.85f, 3));
        toolList.add(new ItemToSpawn(Items.stone_shovel, 1.0f, 0.85f, 3));
        toolList.add(new ItemToSpawn(ItemsZp.copper_axe, 1.0f, 0.85f, 3));
        toolList.add(new ItemToSpawn(ItemsZp.copper_hoe, 1.0f, 0.85f, 3));
        toolList.add(new ItemToSpawn(ItemsZp.copper_pickaxe, 1.0f, 0.85f, 3));
        toolList.add(new ItemToSpawn(ItemsZp.copper_sword, 1.0f, 0.85f, 3));
        toolList.add(new ItemToSpawn(ItemsZp.copper_shovel, 1.0f, 0.85f, 3));
        toolList.add(new ItemToSpawn(ItemsZp.cleaver, 1.0f, 0.8f, 5));
        toolList.add(new ItemToSpawn(Items.bow, 1.0f, 0.66f, 5));
        toolList.add(new ItemToSpawn(Items.fishing_rod, 1.0f, 0.66f, 11));
        toolList.add(new ItemToSpawn(Items.shears, 1.0f, 0.66f, 11));
        toolList.add(new ItemToSpawn(Items.bucket, 1, 10));

        // Food

        foodList.add(new ItemToSpawn(ItemsZp.beer, 1, 1));
        foodList.add(new ItemToSpawn(ItemsZp.water, 1, 29));
        foodList.add(new ItemToSpawn(ItemsZp.cactus_water, 1, 10));
        foodList.add(new ItemToSpawn(ItemsZp.rotten_apple, 1, 10));
        foodList.add(new ItemToSpawn(Items.fish, new ItemToSpawn.IntRange(0, 2), 1, 4));
        foodList.add(new ItemToSpawn(Items.carrot, 1, 4));
        foodList.add(new ItemToSpawn(Items.potato, 2, 0.9f, 4));
        foodList.add(new ItemToSpawn(Items.cooked_porkchop, 1, 2));
        foodList.add(new ItemToSpawn(Items.cooked_beef, 1, 4));
        foodList.add(new ItemToSpawn(Items.cooked_chicken, 1, 3));
        foodList.add(new ItemToSpawn(Items.cooked_fish, new ItemToSpawn.IntRange(0, 2), 1, 1));
        foodList.add(new ItemToSpawn(ItemsZp.fish_zp_cooked, new ItemToSpawn.IntRange(0, 2), 1, 1));
        foodList.add(new ItemToSpawn(Items.mushroom_stew, 1, 3));
        foodList.add(new ItemToSpawn(Items.bread, 1, 4));
        foodList.add(new ItemToSpawn(Items.porkchop, 1, 4));
        foodList.add(new ItemToSpawn(Items.beef, 1, 4));
        foodList.add(new ItemToSpawn(Items.apple, 1, 4));
        foodList.add(new ItemToSpawn(Items.chicken, 1, 4));
        foodList.add(new ItemToSpawn(ItemsZp.fish_canned, 1, 4));

        // Med

        medList.add(new ItemToSpawn(ItemsZp.bandage, 1, 100));

        // Misc

        miscList.add(new ItemToSpawn(ItemsZp.custom_gunpowder, 1, 2));
        miscList.add(new ItemToSpawn(Items.egg, 2, 0.4f, 6));
        miscList.add(new ItemToSpawn(ItemsZp.plate, 2, 0.9f, 10));
        miscList.add(new ItemToSpawn(Items.stick, 3, 0.9f, 6));
        miscList.add(new ItemToSpawn(Items.string, 2, 0.9f, 6));
        miscList.add(new ItemToSpawn(Items.leather, 2, 0.9f, 6));
        miscList.add(new ItemToSpawn(ItemsZp.matches, 1, 0.9f, 4));
        miscList.add(new ItemToSpawn(Items.coal, 1, 3, 5));
        miscList.add(new ItemToSpawn(ItemsZp.custom_repair, 0.1f, 0.1f, 0.7f, 5));
        miscList.add(new ItemToSpawn(ItemsZp.scrap_material, 3, 4));
        miscList.add(new ItemToSpawn(Items.reeds, 1, 8));
        miscList.add(new ItemToSpawn(Items.melon_seeds, 1, 10));
        miscList.add(new ItemToSpawn(Items.wheat_seeds, 1, 12));
        miscList.add(new ItemToSpawn(Items.pumpkin_seeds, 1, 12));
        miscList.add(new ItemToSpawn(ItemsZp.bellows, 1, 1));
        miscList.add(new ItemToSpawn(ItemsZp.chisel, 1, 1));
        miscList.add(new ItemToSpawn(ItemsZp.table, 1, 1));
        miscList.add(new ItemToSpawn(ItemsZp.shelves, 1, 1));

        // Block

        blockList.add(new ItemToSpawn(Item.getItemFromBlock(BlocksZp.block_lamp), 1, 1));
        blockList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.chest), 1, 4));
        blockList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.furnace), 1, 2));
        blockList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.crafting_table), 1, 1));
        blockList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.fence), 1, 6));
        blockList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.glass), 1, 15));
        blockList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.stone), 8, 0.9f, 18));
        blockList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.cobblestone), 8, 0.9f, 18));
        blockList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.torch), 2, 0.9f, 17));
        blockList.add(new ItemToSpawn(Item.getItemFromBlock(BlocksZp.torch2), 4, 0.9f, 18));
    }

    public LootTier2Village(int p_i45397_1_) {
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
        return new TileLootCase(EnumLootGroups.Tier2Village, 0.6f, true, this.chestType);
    }
}
