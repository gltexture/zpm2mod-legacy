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

public class LootTier1Labs extends BlockLootCase {
    public static final Set<LootSpawnManager> lootManagerSet = new HashSet<>();
    private static final Set<ItemToSpawn> armorList = new HashSet<>();
    private static final Set<ItemToSpawn> specialList = new HashSet<>();
    private static final Set<ItemToSpawn> toolList = new HashSet<>();
    private static final Set<ItemToSpawn> miscList = new HashSet<>();
    private static final Set<ItemToSpawn> labMaterialList = new HashSet<>();

    static {
        lootManagerSet.add(new LootSpawnManager(LootTier1Labs.specialList, 1, 44));
        lootManagerSet.add(new LootSpawnManager(LootTier1Labs.toolList, 1, 14));
        lootManagerSet.add(new LootSpawnManager(LootTier1Labs.armorList, 1, 2));
        lootManagerSet.add(new LootSpawnManager(LootTier1Labs.miscList, 1, 20));
        lootManagerSet.add(new LootSpawnManager(LootTier1Labs.labMaterialList, 1, 20));
    }

    static {

        // Armor

        armorList.add(new ItemToSpawn(ItemsZp.rad_helmet, 1.0f, 0.9f, 15));
        armorList.add(new ItemToSpawn(ItemsZp.rad_chestplate, 1.0f, 0.9f, 15));
        armorList.add(new ItemToSpawn(ItemsZp.rad_leggings, 1.0f, 0.9f, 15));
        armorList.add(new ItemToSpawn(ItemsZp.rad_boots, 1.0f, 0.9f, 15));

        armorList.add(new ItemToSpawn(ItemsZp.indcostume_helmet, 1.0f, 0.9f, 10));
        armorList.add(new ItemToSpawn(ItemsZp.indcostume_chestplate, 1.0f, 0.9f, 10));
        armorList.add(new ItemToSpawn(ItemsZp.indcostume_leggings, 1.0f, 0.9f, 10));
        armorList.add(new ItemToSpawn(ItemsZp.indcostume_boots, 1.0f, 0.9f, 10));

        // Tools

        toolList.add(new ItemToSpawn(ItemsZp.screwdriver, 1.0f, 0.95f, 12));
        toolList.add(new ItemToSpawn(ItemsZp.hammer, 1.0f, 0.95f, 12));
        toolList.add(new ItemToSpawn(ItemsZp.hatchet, 1.0f, 0.95f, 5));
        toolList.add(new ItemToSpawn(ItemsZp.sledgehammer, 1.0f, 0.95f, 5));
        toolList.add(new ItemToSpawn(ItemsZp.m_scissors, 1.0f, 0.95f, 10));
        toolList.add(new ItemToSpawn(ItemsZp.wrench, 1.0f, 0.95f, 10));
        toolList.add(new ItemToSpawn(ItemsZp.steel_pickaxe, 1.0f, 0.9f, 14));
        toolList.add(new ItemToSpawn(ItemsZp.steel_axe, 1.0f, 0.9f, 14));
        toolList.add(new ItemToSpawn(ItemsZp.titan_axe, 1.0f, 0.9f, 10));
        toolList.add(new ItemToSpawn(ItemsZp.titan_pickaxe, 1.0f, 0.9f, 8));

        // Misc

        miscList.add(new ItemToSpawn(ItemsZp.steel_ingot, 1, 6));
        miscList.add(new ItemToSpawn(ItemsZp.titan_ingot, 1, 1));
        miscList.add(new ItemToSpawn(ItemsZp.raw_titan, 1, 1));
        miscList.add(new ItemToSpawn(ItemsZp.CBS, 1.0f, 0.8f, 5));
        miscList.add(new ItemToSpawn(ItemsZp.dosimeter, 1.0f, 0.8f, 5));
        miscList.add(new ItemToSpawn(ItemsZp.tanning, 1, 2));
        miscList.add(new ItemToSpawn(ItemsZp.cement, 1, 14));
        miscList.add(new ItemToSpawn(Items.lava_bucket, 1, 4));
        miscList.add(new ItemToSpawn(ItemsZp.solid_fuel, 1, 6));
        miscList.add(new ItemToSpawn(Items.iron_ingot, 2, 0.8f, 5));
        miscList.add(new ItemToSpawn(Items.gold_ingot, 1, 5));
        miscList.add(new ItemToSpawn(ItemsZp.raw_iron, 2, 0.8f, 5));
        miscList.add(new ItemToSpawn(ItemsZp.raw_gold, 1, 5));
        miscList.add(new ItemToSpawn(ItemsZp.brass_material, 9, 0.82f, 2));
        miscList.add(new ItemToSpawn(Items.diamond, 1, 5));
        miscList.add(new ItemToSpawn(Items.emerald, 1, 5));
        miscList.add(new ItemToSpawn(ItemsZp.electronic, 1, 8));
        miscList.add(new ItemToSpawn(ItemsZp.battery, 1, 8));
        miscList.add(new ItemToSpawn(ItemsZp.coils, 1, 8));

        // Lab

        labMaterialList.add(new ItemToSpawn(Items.redstone, 32, 0.7f, 8));
        labMaterialList.add(new ItemToSpawn(Items.glowstone_dust, 4, 0.7f, 8));
        labMaterialList.add(new ItemToSpawn(Item.getItemFromBlock(BlocksZp.lamp), 1, 6));
        labMaterialList.add(new ItemToSpawn(Item.getItemFromBlock(BlocksZp.block_lamp), 1, 6));
        labMaterialList.add(new ItemToSpawn(Items.blaze_powder, 1, 6));
        labMaterialList.add(new ItemToSpawn(Items.fermented_spider_eye, 1, 6));
        labMaterialList.add(new ItemToSpawn(Items.speckled_melon, 1, 6));
        labMaterialList.add(new ItemToSpawn(Items.experience_bottle, 1, 6));
        labMaterialList.add(new ItemToSpawn(Items.ghast_tear, 1, 6));
        labMaterialList.add(new ItemToSpawn(Items.magma_cream, 1, 6));
        labMaterialList.add(new ItemToSpawn(Items.nether_wart, 1, 6));
        labMaterialList.add(new ItemToSpawn(Items.sugar, 1, 6));
        labMaterialList.add(new ItemToSpawn(Items.cauldron, 1, 6));
        labMaterialList.add(new ItemToSpawn(Items.slime_ball, 1, 6));
        labMaterialList.add(new ItemToSpawn(Items.golden_carrot, 1, 6));
        labMaterialList.add(new ItemToSpawn(Items.spider_eye, 1, 6));

        // Specials

        specialList.add(new ItemToSpawn(Item.getItemFromBlock(BlocksZp.uranium), 2, 0.1f, 4));
        specialList.add(new ItemToSpawn(Items.gunpowder, 12, 0.7f, 6));
        specialList.add(new ItemToSpawn(ItemsZp.toxicwater_bucket, 1, 6));
        specialList.add(new ItemToSpawn(Items.ender_pearl, 1, 4));
        specialList.add(new ItemToSpawn(ItemsZp.acid_bucket, 1, 4));
        specialList.add(new ItemToSpawn(ItemsZp.chemicals2, 1.0f, 1.0f, 1.0f, 4));
        specialList.add(new ItemToSpawn(ItemsZp.chemicals1_a, 1, 8));
        specialList.add(new ItemToSpawn(ItemsZp.chemicals1, 0.0f, 1.0f, 0.99f, 6));
        specialList.add(new ItemToSpawn(ItemsZp.chemicals1_b, 1, 8));
        specialList.add(new ItemToSpawn(ItemsZp.chemicals1_c, 0.0f, 1.0f, 0.99f, 6));
        specialList.add(new ItemToSpawn(ItemsZp.blood_material, 1, 8));
        specialList.add(new ItemToSpawn(ItemsZp.acid, 1, 10));
        specialList.add(new ItemToSpawn(ItemsZp.holywater, 1, 10));
        specialList.add(new ItemToSpawn(ItemsZp.chemres, 1, 10));
        specialList.add(new ItemToSpawn(ItemsZp.antiradiation, 1, 6));
    }

    public LootTier1Labs(int p_i45397_1_) {
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
        return new TileLootCase(EnumLootGroups.Tier1Labs, 0.9f, true, this.chestType);
    }
}
