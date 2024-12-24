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

public class LootTier1Military extends BlockLootCase {
    public static final Set<LootSpawnManager> lootManagerSet = new HashSet<>();
    private static final Set<ItemToSpawn> gunList = new HashSet<>();
    private static final Set<ItemToSpawn> gun2List = new HashSet<>();
    private static final Set<ItemToSpawn> ammoList = new HashSet<>();
    private static final Set<ItemToSpawn> medList = new HashSet<>();
    private static final Set<ItemToSpawn> armorList = new HashSet<>();
    private static final Set<ItemToSpawn> specialList = new HashSet<>();
    private static final Set<ItemToSpawn> toolList = new HashSet<>();
    private static final Set<ItemToSpawn> foodList = new HashSet<>();
    private static final Set<ItemToSpawn> miscList = new HashSet<>();
    private static final Set<ItemToSpawn> modList = new HashSet<>();
    private static final Set<ItemToSpawn> camo = new HashSet<>();

    static {
        lootManagerSet.add(new LootSpawnManager(LootTier1Military.gunList, 1, 8));
        lootManagerSet.add(new LootSpawnManager(LootTier1Military.gun2List, 1, 4));
        lootManagerSet.add(new LootSpawnManager(LootTier1Military.ammoList, 1, 2, 0.5f, 14));
        lootManagerSet.add(new LootSpawnManager(LootTier1Military.medList, 1, 10));
        lootManagerSet.add(new LootSpawnManager(LootTier1Military.specialList, 1, 8));
        lootManagerSet.add(new LootSpawnManager(LootTier1Military.toolList, 1, 10));
        lootManagerSet.add(new LootSpawnManager(LootTier1Military.armorList, 1, 2, 0.3f, 8));
        lootManagerSet.add(new LootSpawnManager(LootTier1Military.foodList, 1, 2, 0.08f, 10));
        lootManagerSet.add(new LootSpawnManager(LootTier1Military.miscList, 1, 12));
        lootManagerSet.add(new LootSpawnManager(LootTier1Military.modList, 1, 2));
        lootManagerSet.add(new LootSpawnManager(LootTier1Military.camo, 1, 14));
    }

    static {

        // Weapons

        gunList.add(new ItemToSpawn(ItemsZp.deagle, 1.0f, 0.875f, 5));
        gunList.add(new ItemToSpawn(ItemsZp.python, 1.0f, 0.875f, 8));
        gunList.add(new ItemToSpawn(ItemsZp.m1894, 1.0f, 0.875f, 8));
        gunList.add(new ItemToSpawn(ItemsZp.fiveseven, 1.0f, 0.875f, 13));
        gunList.add(new ItemToSpawn(ItemsZp.p90, 1.0f, 0.875f, 5));
        gunList.add(new ItemToSpawn(ItemsZp.m1895, 1.0f, 0.875f, 10));
        gunList.add(new ItemToSpawn(ItemsZp.mp40, 1.0f, 0.875f, 10));
        gunList.add(new ItemToSpawn(ItemsZp.ppsh, 1.0f, 0.875f, 10));
        gunList.add(new ItemToSpawn(ItemsZp.mp5, 1.0f, 0.875f, 6));
        gunList.add(new ItemToSpawn(ItemsZp.bizon, 1.0f, 0.875f, 6));
        gunList.add(new ItemToSpawn(ItemsZp.ump45, 1.0f, 0.875f, 6));
        gunList.add(new ItemToSpawn(ItemsZp.glock18, 1.0f, 0.875f, 13));

        // Weapons 2

        gun2List.add(new ItemToSpawn(ItemsZp.casull290, 1.0f, 0.875f, 15));
        gun2List.add(new ItemToSpawn(ItemsZp.blaser93, 1.0f, 0.875f, 15));
        gun2List.add(new ItemToSpawn(ItemsZp.mosin, 1.0f, 0.875f, 15));
        gun2List.add(new ItemToSpawn(ItemsZp.saiga12, 1.0f, 0.875f, 5));
        gun2List.add(new ItemToSpawn(ItemsZp.rem870, 1.0f, 0.875f, 15));
        gun2List.add(new ItemToSpawn(ItemsZp.ar15, 1.0f, 0.875f, 15));
        gun2List.add(new ItemToSpawn(ItemsZp.aksu, 1.0f, 0.875f, 15));
        gun2List.add(new ItemToSpawn(ItemsZp.m24, 1.0f, 0.875f, 5));

        // Ammo

        ammoList.add(new ItemToSpawn(ItemsZp._9mm, 24, 0.88f, 12));
        ammoList.add(new ItemToSpawn(ItemsZp._12, 5, 0.82f, 10));
        ammoList.add(new ItemToSpawn(ItemsZp._7_62x39, 24, 0.86f, 6));
        ammoList.add(new ItemToSpawn(ItemsZp._5_56x45, 24, 0.86f, 6));
        ammoList.add(new ItemToSpawn(ItemsZp._45acp, 24, 0.86f, 12));
        ammoList.add(new ItemToSpawn(ItemsZp._7_62x25, 24, 0.86f, 12));
        ammoList.add(new ItemToSpawn(ItemsZp._5_45x39, 24, 0.86f, 6));
        ammoList.add(new ItemToSpawn(ItemsZp._7_62x54R, 8, 0.84f, 6));
        ammoList.add(new ItemToSpawn(ItemsZp._5_7x28, 24, 0.82f, 12));
        ammoList.add(new ItemToSpawn(ItemsZp._9x39, 12, 0.82f, 6));
        ammoList.add(new ItemToSpawn(ItemsZp._357m, 16, 0.86f, 12));

        // Armor

        armorList.add(new ItemToSpawn(ItemsZp.kevlar_helmet, 1.0f, 0.85f, 8));
        armorList.add(new ItemToSpawn(ItemsZp.kevlar_vest, 1.0f, 0.85f, 8));
        armorList.add(new ItemToSpawn(Items.iron_helmet, 1.0f, 0.85f, 21));
        armorList.add(new ItemToSpawn(Items.iron_chestplate, 1.0f, 0.85f, 21));
        armorList.add(new ItemToSpawn(Items.iron_leggings, 1.0f, 0.85f, 21));
        armorList.add(new ItemToSpawn(Items.iron_boots, 1.0f, 0.85f, 21));

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

        camo.add(new ItemToSpawn(ItemsZp.balaclava, 1.0f, 0.9f, 16));

        // Tools

        toolList.add(new ItemToSpawn(ItemsZp.machete, 1.0f, 0.85f, 10));
        toolList.add(new ItemToSpawn(ItemsZp.steel_sword, 1.0f, 0.85f, 15));
        toolList.add(new ItemToSpawn(Items.iron_sword, 1.0f, 0.9f, 35));
        toolList.add(new ItemToSpawn(Items.diamond_sword, 1.0f, 0.9f, 10));
        toolList.add(new ItemToSpawn(ItemsZp.iron_club, 1.0f, 0.9f, 20));
        toolList.add(new ItemToSpawn(ItemsZp.katana, 1.0f, 0.875f, 2));
        toolList.add(new ItemToSpawn(ItemsZp.sledgehammer, 1.0f, 0.9f, 4));
        toolList.add(new ItemToSpawn(ItemsZp.hatchet, 1.0f, 0.9f, 4));

        // Food

        foodList.add(new ItemToSpawn(ItemsZp.water, 1, 50));
        foodList.add(new ItemToSpawn(ItemsZp.stewed_meat, 1, 10));
        foodList.add(new ItemToSpawn(ItemsZp.pea, 1, 10));
        foodList.add(new ItemToSpawn(ItemsZp.ananas, 1, 10));
        foodList.add(new ItemToSpawn(Items.cooked_beef, 1, 5));
        foodList.add(new ItemToSpawn(Items.cooked_porkchop, 1, 5));
        foodList.add(new ItemToSpawn(ItemsZp.soup, 1, 10));

        // Misc

        miscList.add(new ItemToSpawn(ItemsZp.steel_material, 1, 5));
        miscList.add(new ItemToSpawn(Items.coal, 4, 0.6f, 32));
        miscList.add(new ItemToSpawn(ItemsZp.gps, 0.05f, 1.0f, 0.8f, 2));
        miscList.add(new ItemToSpawn(Items.iron_ingot, 2, 0.5f, 30));
        miscList.add(new ItemToSpawn(ItemsZp.repair, 0.1f, 1.0f, 0.9f, 15));
        miscList.add(new ItemToSpawn(ItemsZp.lubricant, 1, 5));
        miscList.add(new ItemToSpawn(Items.gunpowder, 3, 0.8f, 10));
        miscList.add(new ItemToSpawn(ItemsZp.solid_fuel, 1, 1));

        // Med

        medList.add(new ItemToSpawn(ItemsZp.military_bandage, 1.0f, 0.98f, 45));
        medList.add(new ItemToSpawn(ItemsZp.good_vision, 1, 10));
        medList.add(new ItemToSpawn(ItemsZp.morphine, 1, 10));
        medList.add(new ItemToSpawn(ItemsZp.tire, 1, 25));
        medList.add(new ItemToSpawn(ItemsZp.aid_kit, 1, 5));
        medList.add(new ItemToSpawn(ItemsZp.blood_bag, 1, 5));

        // Mod

        modList.add(new ItemToSpawn(ItemsZp.acog2x, 1, 10));
        modList.add(new ItemToSpawn(ItemsZp.scope_kashtan, 1, 10));
        modList.add(new ItemToSpawn(ItemsZp.scope_pu, 1, 16));
        modList.add(new ItemToSpawn(ItemsZp.muzzlebrake_pistol, 1, 10));
        modList.add(new ItemToSpawn(ItemsZp.silencer_pistol, 1, 8));
        modList.add(new ItemToSpawn(ItemsZp.angle_foregrip, 1, 5));
        modList.add(new ItemToSpawn(ItemsZp.foregrip, 1, 5));

        modList.add(new ItemToSpawn(ItemsZp.laser, 1, 6));
        modList.add(new ItemToSpawn(ItemsZp.flashSuppressor_pistol, 1, 10));
        modList.add(new ItemToSpawn(ItemsZp.scope_kobra, 1, 10));
        modList.add(new ItemToSpawn(ItemsZp.scope_eotech, 1, 10));

        // Specials

        specialList.add(new ItemToSpawn(ItemsZp.ammo_press, 0.1f, 1.0f, 0.9f, 5));
        specialList.add(new ItemToSpawn(ItemsZp.kevlar, 1, 8));
        specialList.add(new ItemToSpawn(ItemsZp.smoke_grenade, 1, 10));
        specialList.add(new ItemToSpawn(ItemsZp.frag_grenade, 1, 4));
        specialList.add(new ItemToSpawn(ItemsZp.tnt, 1, 5));
        specialList.add(new ItemToSpawn(Item.getItemFromBlock(BlocksZp.sandbag), 2, 0.25f, 28));
        specialList.add(new ItemToSpawn(Item.getItemFromBlock(BlocksZp.wire), 1, 5));
        specialList.add(new ItemToSpawn(ItemsZp.pnv, 1.0f, 0.8f, 15));
        specialList.add(new ItemToSpawn(ItemsZp.gasmask, 1.0f, 0.8f, 14));
        specialList.add(new ItemToSpawn(ItemsZp.binoculars, 1.0f, 0.8f, 6));
    }

    public LootTier1Military(int p_i45397_1_) {
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
        return new TileLootCase(EnumLootGroups.Tier1Military, 0.5f, true, this.chestType);
    }
}
