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

public class LootTier2Industry extends BlockLootCase {
    public static final Set<LootSpawnManager> lootManagerSet = new HashSet<>();
    private static final Set<ItemToSpawn> medList = new HashSet<>();
    private static final Set<ItemToSpawn> armorList = new HashSet<>();
    private static final Set<ItemToSpawn> specialList = new HashSet<>();
    private static final Set<ItemToSpawn> toolList = new HashSet<>();
    private static final Set<ItemToSpawn> miscList = new HashSet<>();

    static {
        lootManagerSet.add(new LootSpawnManager(LootTier2Industry.medList, 1, 8));
        lootManagerSet.add(new LootSpawnManager(LootTier2Industry.specialList, 1, 24));
        lootManagerSet.add(new LootSpawnManager(LootTier2Industry.toolList, 1, 24));
        lootManagerSet.add(new LootSpawnManager(LootTier2Industry.armorList, 1, 14));
        lootManagerSet.add(new LootSpawnManager(LootTier2Industry.miscList, 1, 30));
    }

    static {

        // Armor

        armorList.add(new ItemToSpawn(ItemsZp.rad_helmet, 1.0f, 0.865f, 8));
        armorList.add(new ItemToSpawn(ItemsZp.rad_chestplate, 1.0f, 0.865f, 8));
        armorList.add(new ItemToSpawn(ItemsZp.rad_leggings, 1.0f, 0.865f, 8));
        armorList.add(new ItemToSpawn(ItemsZp.rad_boots, 1.0f, 0.865f, 8));

        armorList.add(new ItemToSpawn(ItemsZp.aqualung_helmet, 1.0f, 0.865f, 14));
        armorList.add(new ItemToSpawn(ItemsZp.aqualung_chestplate, 1.0f, 0.865f, 14));
        armorList.add(new ItemToSpawn(ItemsZp.aqualung_leggings, 1.0f, 0.865f, 14));
        armorList.add(new ItemToSpawn(ItemsZp.aqualung_boots, 1.0f, 0.865f, 14));

        armorList.add(new ItemToSpawn(ItemsZp.indcostume_helmet, 1.0f, 0.865f, 3));
        armorList.add(new ItemToSpawn(ItemsZp.indcostume_chestplate, 1.0f, 0.865f, 3));
        armorList.add(new ItemToSpawn(ItemsZp.indcostume_leggings, 1.0f, 0.865f, 3));
        armorList.add(new ItemToSpawn(ItemsZp.indcostume_boots, 1.0f, 0.865f, 3));

        // Tools

        toolList.add(new ItemToSpawn(ItemsZp.screwdriver, 1.0f, 0.9f, 12));
        toolList.add(new ItemToSpawn(ItemsZp.hammer, 1.0f, 0.9f, 12));
        toolList.add(new ItemToSpawn(ItemsZp.hatchet, 1.0f, 0.9f, 6));
        toolList.add(new ItemToSpawn(ItemsZp.sledgehammer, 1.0f, 0.9f, 6));
        toolList.add(new ItemToSpawn(ItemsZp.wrench, 1.0f, 0.95f, 8));
        toolList.add(new ItemToSpawn(ItemsZp.m_scissors, 1.0f, 0.9f, 8));
        toolList.add(new ItemToSpawn(ItemsZp.steel_pickaxe, 1.0f, 0.9f, 12));
        toolList.add(new ItemToSpawn(ItemsZp.steel_axe, 1.0f, 0.9f, 12));
        toolList.add(new ItemToSpawn(Items.diamond_axe, 1.0f, 0.9f, 10));
        toolList.add(new ItemToSpawn(Items.diamond_pickaxe, 1.0f, 0.9f, 10));
        toolList.add(new ItemToSpawn(ItemsZp.electrician_kit, 1.0f, 0.8f, 4));

        // Misc

        miscList.add(new ItemToSpawn(ItemsZp.steel_ingot, 1, 4));
        miscList.add(new ItemToSpawn(ItemsZp.CBS, 1.0f, 0.8f, 4));
        miscList.add(new ItemToSpawn(ItemsZp.oxygen, 1.0f, 0.8f, 4));
        miscList.add(new ItemToSpawn(ItemsZp.dosimeter, 1.0f, 0.8f, 5));
        miscList.add(new ItemToSpawn(ItemsZp.upd_leather, 1, 10));
        miscList.add(new ItemToSpawn(ItemsZp.tanning, 1, 2));
        miscList.add(new ItemToSpawn(ItemsZp.cement, 1, 15));
        miscList.add(new ItemToSpawn(Items.lava_bucket, 1, 1));
        miscList.add(new ItemToSpawn(Items.water_bucket, 1, 1));
        miscList.add(new ItemToSpawn(Items.redstone, 16, 0.7f, 2));
        miscList.add(new ItemToSpawn(Items.coal, 16, 0.8f, 4));
        miscList.add(new ItemToSpawn(Items.iron_ingot, 2, 0.65f, 4));
        miscList.add(new ItemToSpawn(Items.gold_ingot, 1, 4));
        miscList.add(new ItemToSpawn(ItemsZp.raw_iron, 2, 0.65f, 4));
        miscList.add(new ItemToSpawn(ItemsZp.raw_gold, 1, 4));
        miscList.add(new ItemToSpawn(ItemsZp.brass_material, 6, 0.82f, 2));
        miscList.add(new ItemToSpawn(Items.diamond, 1, 5));
        miscList.add(new ItemToSpawn(Items.emerald, 1, 5));
        miscList.add(new ItemToSpawn(ItemsZp.matches, 1, 0.95f, 4));
        miscList.add(new ItemToSpawn(ItemsZp.electronic, 1, 8));
        miscList.add(new ItemToSpawn(ItemsZp.battery, 1, 4));
        miscList.add(new ItemToSpawn(ItemsZp.coils, 1, 4));

        // Med

        medList.add(new ItemToSpawn(ItemsZp.bandage, 1, 64));
        medList.add(new ItemToSpawn(ItemsZp.ai2_kit, 1, 10));
        medList.add(new ItemToSpawn(ItemsZp.aid_kit, 1, 10));
        medList.add(new ItemToSpawn(ItemsZp.nuka_cola, 1, 7));
        medList.add(new ItemToSpawn(ItemsZp.poison, 0.0f, 1.0f, 0.9f, 8));
        medList.add(new ItemToSpawn(ItemsZp.antiradiation, 1, 1));

        // Specials

        specialList.add(new ItemToSpawn(ItemsZp.gasmask, 1.0f, 0.85f, 10));
        specialList.add(new ItemToSpawn(Items.glowstone_dust, 6, 0.6f, 10));
        specialList.add(new ItemToSpawn(Item.getItemFromBlock(BlocksZp.uranium), 2, 0.1f, 2));
        specialList.add(new ItemToSpawn(Items.gunpowder, 6, 0.7f, 6));
        specialList.add(new ItemToSpawn(ItemsZp.tnt, 1, 4));
        specialList.add(new ItemToSpawn(ItemsZp.toxicwater_bucket, 1, 8));
        specialList.add(new ItemToSpawn(ItemsZp.acid_bucket, 1, 2));
        specialList.add(new ItemToSpawn(ItemsZp.chemicals2, 1.0f, 1.0f, 1.0f, 1));
        specialList.add(new ItemToSpawn(ItemsZp.chemicals1_a, 1, 4));
        specialList.add(new ItemToSpawn(ItemsZp.chemicals1, 0.0f, 1.0f, 0.98f, 2));
        specialList.add(new ItemToSpawn(ItemsZp.chemicals1_b, 1, 4));
        specialList.add(new ItemToSpawn(ItemsZp.chemicals1_c, 0.0f, 1.0f, 0.98f, 4));
        specialList.add(new ItemToSpawn(ItemsZp.blood_material, 1, 5));
        specialList.add(new ItemToSpawn(ItemsZp.acid, 1, 16));
        specialList.add(new ItemToSpawn(ItemsZp.holywater, 1, 14));
        specialList.add(new ItemToSpawn(ItemsZp.chemres, 1, 8));
    }

    public LootTier2Industry(int p_i45397_1_) {
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
        return new TileLootCase(EnumLootGroups.Tier2Industry, 0.65f, true, this.chestType);
    }
}
