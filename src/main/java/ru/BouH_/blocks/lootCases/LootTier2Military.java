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

public class LootTier2Military extends BlockLootCase {
    public static final Set<LootSpawnManager> lootManagerSet = new HashSet<>();
    private static final Set<ItemToSpawn> gunList = new HashSet<>();
    private static final Set<ItemToSpawn> gun2List = new HashSet<>();
    private static final Set<ItemToSpawn> ammoList = new HashSet<>();
    private static final Set<ItemToSpawn> ammo2List = new HashSet<>();
    private static final Set<ItemToSpawn> medList = new HashSet<>();
    private static final Set<ItemToSpawn> armorList = new HashSet<>();
    private static final Set<ItemToSpawn> specialList = new HashSet<>();
    private static final Set<ItemToSpawn> toolList = new HashSet<>();
    private static final Set<ItemToSpawn> foodList = new HashSet<>();
    private static final Set<ItemToSpawn> miscList = new HashSet<>();
    private static final Set<ItemToSpawn> modList = new HashSet<>();
    private static final Set<ItemToSpawn> mod2List = new HashSet<>();
    private static final Set<ItemToSpawn> camo = new HashSet<>();

    static {
        lootManagerSet.add(new LootSpawnManager(LootTier2Military.gunList, 1, 10));
        lootManagerSet.add(new LootSpawnManager(LootTier2Military.gun2List, 1, 6));
        lootManagerSet.add(new LootSpawnManager(LootTier2Military.ammoList, 2, 4, 0.5f, 8));
        lootManagerSet.add(new LootSpawnManager(LootTier2Military.ammo2List, 1, 2, 0.35f, 6));
        lootManagerSet.add(new LootSpawnManager(LootTier2Military.medList, 1, 8));
        lootManagerSet.add(new LootSpawnManager(LootTier2Military.specialList, 1, 6));
        lootManagerSet.add(new LootSpawnManager(LootTier2Military.toolList, 1, 8));
        lootManagerSet.add(new LootSpawnManager(LootTier2Military.armorList, 1, 2, 0.3f, 8));
        lootManagerSet.add(new LootSpawnManager(LootTier2Military.camo, 1, 12));
        lootManagerSet.add(new LootSpawnManager(LootTier2Military.foodList, 1, 2, 0.08f, 8));
        lootManagerSet.add(new LootSpawnManager(LootTier2Military.miscList, 1, 10));
        lootManagerSet.add(new LootSpawnManager(LootTier2Military.modList, 1, 6));
        lootManagerSet.add(new LootSpawnManager(LootTier2Military.mod2List, 1, 4));
    }

    static {

        // Weapons

        gunList.add(new ItemToSpawn(ItemsZp.deagle, 1.0f, 0.9f, 5));
        gunList.add(new ItemToSpawn(ItemsZp.fiveseven, 1.0f, 0.9f, 5));
        gunList.add(new ItemToSpawn(ItemsZp.python, 1.0f, 0.9f, 3));
        gunList.add(new ItemToSpawn(ItemsZp.m1894, 1.0f, 0.9f, 3));
        gunList.add(new ItemToSpawn(ItemsZp.glock18, 1.0f, 0.9f, 5));
        gunList.add(new ItemToSpawn(ItemsZp.fal, 1.0f, 0.9f, 5));
        gunList.add(new ItemToSpawn(ItemsZp.sg550, 1.0f, 0.9f, 5));
        gunList.add(new ItemToSpawn(ItemsZp.akm, 1.0f, 0.9f, 5));
        gunList.add(new ItemToSpawn(ItemsZp.aksu, 1.0f, 0.9f, 10));
        gunList.add(new ItemToSpawn(ItemsZp.m16a1, 1.0f, 0.9f, 5));
        gunList.add(new ItemToSpawn(ItemsZp.g36k, 1.0f, 0.9f, 10));
        gunList.add(new ItemToSpawn(ItemsZp.aug, 1.0f, 0.9f, 10));
        gunList.add(new ItemToSpawn(ItemsZp.mosin, 1.0f, 0.9f, 14));
        gunList.add(new ItemToSpawn(ItemsZp.m4a1, 1.0f, 0.9f, 5));
        gunList.add(new ItemToSpawn(ItemsZp.p90, 1.0f, 0.9f, 10));

        // Weapons 2

        gun2List.add(new ItemToSpawn(ItemsZp.scar, 1.0f, 0.875f, 12));
        gun2List.add(new ItemToSpawn(ItemsZp.svd, 1.0f, 0.875f, 12));
        gun2List.add(new ItemToSpawn(ItemsZp.pkm, 1.0f, 0.875f, 12));
        gun2List.add(new ItemToSpawn(ItemsZp.m249, 1.0f, 0.875f, 12));
        gun2List.add(new ItemToSpawn(ItemsZp.rpk, 1.0f, 0.875f, 12));
        gun2List.add(new ItemToSpawn(ItemsZp.sks, 1.0f, 0.875f, 12));
        gun2List.add(new ItemToSpawn(ItemsZp.rpg28, 0.3f, 1.0f, 0.92f, 2));
        gun2List.add(new ItemToSpawn(ItemsZp.m79, 1.0f, 0.7f, 2));
        gun2List.add(new ItemToSpawn(ItemsZp.spas12, 1.0f, 0.875f, 14));
        gun2List.add(new ItemToSpawn(ItemsZp.m24, 1.0f, 0.875f, 10));

        // Ammo

        ammoList.add(new ItemToSpawn(ItemsZp._357m, 16, 0.88f, 16));
        ammoList.add(new ItemToSpawn(ItemsZp._45acp, 32, 0.88f, 22));
        ammoList.add(new ItemToSpawn(ItemsZp._9mm, 32, 0.88f, 18));
        ammoList.add(new ItemToSpawn(ItemsZp._7_62x25, 32, 0.88f, 18));
        ammoList.add(new ItemToSpawn(ItemsZp._5_7x28, 24, 0.88f, 14));
        ammoList.add(new ItemToSpawn(ItemsZp._12, 6, 0.86f, 12));

        // Ammo2

        ammo2List.add(new ItemToSpawn(ItemsZp._5_45x39, 32, 0.88f, 16));
        ammo2List.add(new ItemToSpawn(ItemsZp._5_56x45, 32, 0.88f, 16));
        ammo2List.add(new ItemToSpawn(ItemsZp._7_62x39, 32, 0.88f, 16));
        ammo2List.add(new ItemToSpawn(ItemsZp._7_62x54R, 8, 0.88f, 14));
        ammo2List.add(new ItemToSpawn(ItemsZp._9x39, 12, 0.88f, 13));
        ammo2List.add(new ItemToSpawn(ItemsZp._308win, 6, 0.88f, 12));
        ammo2List.add(new ItemToSpawn(ItemsZp._50bmg, 4, 0.7f, 4));
        ammo2List.add(new ItemToSpawn(ItemsZp._rocket, 1, 3));
        ammo2List.add(new ItemToSpawn(ItemsZp._grenade40mm, 1, 3));
        ammo2List.add(new ItemToSpawn(ItemsZp._wog25, 1, 3));

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

        toolList.add(new ItemToSpawn(ItemsZp.machete, 1.0f, 0.875f, 15));
        toolList.add(new ItemToSpawn(ItemsZp.steel_sword, 1.0f, 0.875f, 15));
        toolList.add(new ItemToSpawn(Items.iron_sword, 1.0f, 0.9f, 30));
        toolList.add(new ItemToSpawn(Items.diamond_sword, 1.0f, 0.9f, 15));
        toolList.add(new ItemToSpawn(ItemsZp.katana, 1.0f, 0.875f, 10));
        toolList.add(new ItemToSpawn(ItemsZp.iron_club, 1.0f, 0.9f, 15));

        // Food

        foodList.add(new ItemToSpawn(ItemsZp.water, 1, 50));
        foodList.add(new ItemToSpawn(ItemsZp.stewed_meat, 1, 10));
        foodList.add(new ItemToSpawn(ItemsZp.pea, 1, 10));
        foodList.add(new ItemToSpawn(ItemsZp.ananas, 1, 10));
        foodList.add(new ItemToSpawn(Items.cooked_beef, 1, 5));
        foodList.add(new ItemToSpawn(Items.cooked_porkchop, 1, 5));
        foodList.add(new ItemToSpawn(ItemsZp.soup, 1, 10));

        // Misc

        miscList.add(new ItemToSpawn(ItemsZp.steel_ingot, 1, 1));
        miscList.add(new ItemToSpawn(ItemsZp.steel_material, 1, 3));
        miscList.add(new ItemToSpawn(Items.coal, 4, 0.6f, 26));
        miscList.add(new ItemToSpawn(ItemsZp.gps, 0.05f, 1.0f, 0.8f, 4));
        miscList.add(new ItemToSpawn(Items.iron_ingot, 2, 0.5f, 30));
        miscList.add(new ItemToSpawn(ItemsZp.repair, 0.1f, 1.0f, 0.9f, 18));
        miscList.add(new ItemToSpawn(ItemsZp.lubricant, 1, 8));
        miscList.add(new ItemToSpawn(Items.gunpowder, 4, 0.8f, 8));
        miscList.add(new ItemToSpawn(ItemsZp.solid_fuel, 1, 2));

        // Med

        medList.add(new ItemToSpawn(ItemsZp.military_bandage, 1.0f, 0.98f, 28));
        medList.add(new ItemToSpawn(ItemsZp.antidote_syringe, 1, 25));
        medList.add(new ItemToSpawn(ItemsZp.aid_kit, 1, 10));
        medList.add(new ItemToSpawn(ItemsZp.good_vision, 1, 4));
        medList.add(new ItemToSpawn(ItemsZp.night_vision, 1, 8));
        medList.add(new ItemToSpawn(ItemsZp.morphine, 1, 20));
        medList.add(new ItemToSpawn(ItemsZp.blood_bag, 1, 5));

        // Mod

        modList.add(new ItemToSpawn(ItemsZp.acog2x, 1, 8));
        modList.add(new ItemToSpawn(ItemsZp.scope_kashtan, 1, 8));
        modList.add(new ItemToSpawn(ItemsZp.scope_eotech, 1, 10));
        modList.add(new ItemToSpawn(ItemsZp.scope_kobra, 1, 10));
        modList.add(new ItemToSpawn(ItemsZp.muzzlebrake_pistol, 1, 10));
        modList.add(new ItemToSpawn(ItemsZp.muzzlebrake_rifle, 1, 10));
        modList.add(new ItemToSpawn(ItemsZp.silencer_pistol, 1, 8));
        modList.add(new ItemToSpawn(ItemsZp.silencer_rifle, 1, 8));
        modList.add(new ItemToSpawn(ItemsZp.laser, 1, 8));
        modList.add(new ItemToSpawn(ItemsZp.flashSuppressor_pistol, 1, 10));
        modList.add(new ItemToSpawn(ItemsZp.flashSuppressor_rifle, 1, 10));

        // Mod

        mod2List.add(new ItemToSpawn(ItemsZp.scope4x, 1, 16));
        mod2List.add(new ItemToSpawn(ItemsZp.pso4x, 1, 16));
        mod2List.add(new ItemToSpawn(ItemsZp.scope6x, 1, 12));
        mod2List.add(new ItemToSpawn(ItemsZp.pso6x, 1, 12));
        mod2List.add(new ItemToSpawn(ItemsZp.angle_foregrip, 1, 12));
        mod2List.add(new ItemToSpawn(ItemsZp.foregrip, 1, 12));
        mod2List.add(new ItemToSpawn(ItemsZp.bipod, 1, 10));
        mod2List.add(new ItemToSpawn(ItemsZp.m203, 1, 4));
        mod2List.add(new ItemToSpawn(ItemsZp.gp25, 1, 4));
        mod2List.add(new ItemToSpawn(ItemsZp.anPvs4, 1, 1));
        mod2List.add(new ItemToSpawn(ItemsZp.nspu, 1, 1));

        // Specials

        specialList.add(new ItemToSpawn(ItemsZp._customRocket, 1, 1));
        specialList.add(new ItemToSpawn(ItemsZp.ammo_press, 0.1f, 1.0f, 0.9f, 5));
        specialList.add(new ItemToSpawn(ItemsZp.kevlar, 1, 8));
        specialList.add(new ItemToSpawn(ItemsZp.smoke_grenade, 1, 12));
        specialList.add(new ItemToSpawn(ItemsZp.gas_grenade, 1, 8));
        specialList.add(new ItemToSpawn(ItemsZp.frag_grenade, 1, 6));
        specialList.add(new ItemToSpawn(ItemsZp.tnt, 1, 6));
        specialList.add(new ItemToSpawn(Item.getItemFromBlock(BlocksZp.sandbag), 2, 0.25f, 12));
        specialList.add(new ItemToSpawn(Item.getItemFromBlock(BlocksZp.mine), 1, 1));
        specialList.add(new ItemToSpawn(Item.getItemFromBlock(BlocksZp.wire_reinforced), 1, 1));
        specialList.add(new ItemToSpawn(Item.getItemFromBlock(BlocksZp.wire), 1, 6));
        specialList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.tnt), 1, 2));
        specialList.add(new ItemToSpawn(ItemsZp.pnv, 1.0f, 0.8f, 12));
        specialList.add(new ItemToSpawn(ItemsZp.gasmask, 1.0f, 0.8f, 12));
        specialList.add(new ItemToSpawn(ItemsZp.binoculars, 1.0f, 0.8f, 8));

    }

    public LootTier2Military(int p_i45397_1_) {
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
        return new TileLootCase(EnumLootGroups.Tier2Military, 0.65f, true, this.chestType);
    }
}
