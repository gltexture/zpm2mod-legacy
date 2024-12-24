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

public class LootTier3Military extends BlockLootCase {
    public static final Set<LootSpawnManager> lootManagerSet = new HashSet<>();
    private static final Set<ItemToSpawn> gunList = new HashSet<>();
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
        lootManagerSet.add(new LootSpawnManager(LootTier3Military.gunList, 1, 14));
        lootManagerSet.add(new LootSpawnManager(LootTier3Military.ammoList, 2, 6, 0.55f, 8));
        lootManagerSet.add(new LootSpawnManager(LootTier3Military.ammo2List, 2, 6, 0.4f, 12));
        lootManagerSet.add(new LootSpawnManager(LootTier3Military.medList, 1, 8));
        lootManagerSet.add(new LootSpawnManager(LootTier3Military.specialList, 1, 10));
        lootManagerSet.add(new LootSpawnManager(LootTier3Military.toolList, 1, 6));
        lootManagerSet.add(new LootSpawnManager(LootTier3Military.armorList, 1, 2, 0.3f, 8));
        lootManagerSet.add(new LootSpawnManager(LootTier3Military.camo, 1, 8));
        lootManagerSet.add(new LootSpawnManager(LootTier3Military.foodList, 1, 3, 0.1f, 4));
        lootManagerSet.add(new LootSpawnManager(LootTier3Military.miscList, 1, 8));
        lootManagerSet.add(new LootSpawnManager(LootTier3Military.modList, 1, 8));
        lootManagerSet.add(new LootSpawnManager(LootTier3Military.mod2List, 1, 6));
    }

    static {

        // Weapons

        gunList.add(new ItemToSpawn(ItemsZp.rpg28, 0.3f, 1.0f, 0.92f, 2));
        gunList.add(new ItemToSpawn(ItemsZp.m79, 0.3f, 1.0f, 0.88f, 2));
        gunList.add(new ItemToSpawn(ItemsZp.m24, 0.3f, 1.0f, 0.92f, 4));
        gunList.add(new ItemToSpawn(ItemsZp.ak12, 0.3f, 1.0f, 0.92f, 18));
        gunList.add(new ItemToSpawn(ItemsZp.oc14, 0.3f, 1.0f, 0.92f, 14));
        gunList.add(new ItemToSpawn(ItemsZp.vss, 0.3f, 1.0f, 0.92f, 14));
        gunList.add(new ItemToSpawn(ItemsZp.fal, 0.3f, 1.0f, 0.92f, 18));
        gunList.add(new ItemToSpawn(ItemsZp.sg550, 0.3f, 1.0f, 0.92f, 14));
        gunList.add(new ItemToSpawn(ItemsZp.barrett_m82a1, 0.3f, 1.0f, 0.88f, 2));
        gunList.add(new ItemToSpawn(ItemsZp.m24, 1.0f, 0.92f, 12));

        // Ammo

        ammoList.add(new ItemToSpawn(ItemsZp._357m, 16, 0.88f, 14));
        ammoList.add(new ItemToSpawn(ItemsZp._45acp, 32, 0.88f, 20));
        ammoList.add(new ItemToSpawn(ItemsZp._9mm, 32, 0.88f, 14));
        ammoList.add(new ItemToSpawn(ItemsZp._7_62x25, 32, 0.88f, 14));
        ammoList.add(new ItemToSpawn(ItemsZp._5_7x28, 16, 0.88f, 10));
        ammoList.add(new ItemToSpawn(ItemsZp._12, 8, 0.88f, 10));
        ammoList.add(new ItemToSpawn(ItemsZp._22lr, 32, 0.88f, 18));

        // Ammo2

        ammo2List.add(new ItemToSpawn(ItemsZp._5_45x39, 32, 0.88f, 14));
        ammo2List.add(new ItemToSpawn(ItemsZp._5_56x45, 32, 0.88f, 14));
        ammo2List.add(new ItemToSpawn(ItemsZp._7_62x39, 32, 0.88f, 14));
        ammo2List.add(new ItemToSpawn(ItemsZp._7_62x54R, 24, 0.86f, 12));
        ammo2List.add(new ItemToSpawn(ItemsZp._9x39, 24, 0.86f, 12));
        ammo2List.add(new ItemToSpawn(ItemsZp._308win, 12, 0.86f, 12));
        ammo2List.add(new ItemToSpawn(ItemsZp._50bmg, 6, 0.84f, 10));
        ammo2List.add(new ItemToSpawn(ItemsZp._rocket, 1, 4));
        ammo2List.add(new ItemToSpawn(ItemsZp._grenade40mm, 1, 4));
        ammo2List.add(new ItemToSpawn(ItemsZp._wog25, 1, 4));

        // Armor

        armorList.add(new ItemToSpawn(ItemsZp.steel_helmet, 1.0f, 0.8f, 14));
        armorList.add(new ItemToSpawn(ItemsZp.steel_chestplate, 1.0f, 0.8f, 14));
        armorList.add(new ItemToSpawn(ItemsZp.steel_leggings, 1.0f, 0.8f, 14));
        armorList.add(new ItemToSpawn(ItemsZp.steel_boots, 1.0f, 0.8f, 14));

        armorList.add(new ItemToSpawn(Items.diamond_helmet, 1.0f, 0.8f, 11));
        armorList.add(new ItemToSpawn(Items.diamond_chestplate, 1.0f, 0.8f, 11));
        armorList.add(new ItemToSpawn(Items.diamond_leggings, 1.0f, 0.8f, 11));
        armorList.add(new ItemToSpawn(Items.diamond_boots, 1.0f, 0.8f, 11));

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

        toolList.add(new ItemToSpawn(ItemsZp.machete, 1.0f, 0.9f, 15));
        toolList.add(new ItemToSpawn(ItemsZp.steel_sword, 1.0f, 0.9f, 25));
        toolList.add(new ItemToSpawn(ItemsZp.titan_sword, 1.0f, 0.9f, 5));
        toolList.add(new ItemToSpawn(Items.diamond_sword, 1.0f, 0.9f, 30));
        toolList.add(new ItemToSpawn(ItemsZp.katana, 1.0f, 0.9f, 10));
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

        miscList.add(new ItemToSpawn(ItemsZp.steel_ingot, 1, 10));
        miscList.add(new ItemToSpawn(ItemsZp.armor_material, 1, 5));
        miscList.add(new ItemToSpawn(ItemsZp.titan_ingot, 2, 0.1f, 2));
        miscList.add(new ItemToSpawn(ItemsZp.raw_titan, 2, 0.1f, 3));
        miscList.add(new ItemToSpawn(Items.diamond, 2, 0.2f, 10));
        miscList.add(new ItemToSpawn(ItemsZp.gps, 0.05f, 1.0f, 0.8f, 8));
        miscList.add(new ItemToSpawn(Items.iron_ingot, 1, 16));
        miscList.add(new ItemToSpawn(ItemsZp.repair, 0.8f, 1.0f, 0.9f, 8));
        miscList.add(new ItemToSpawn(ItemsZp.lubricant, 1, 8));
        miscList.add(new ItemToSpawn(ItemsZp.cement, 1, 20));
        miscList.add(new ItemToSpawn(Items.gunpowder, 5, 0.8f, 8));
        miscList.add(new ItemToSpawn(ItemsZp.solid_fuel, 1, 2));

        // Med

        medList.add(new ItemToSpawn(ItemsZp.military_bandage, 1.0f, 0.98f, 16));
        medList.add(new ItemToSpawn(ItemsZp.antidote_syringe, 1, 18));
        medList.add(new ItemToSpawn(ItemsZp.aid_kit, 1, 14));
        medList.add(new ItemToSpawn(ItemsZp.ai2_kit, 1, 4));
        medList.add(new ItemToSpawn(ItemsZp.blood_bag, 1, 10));
        medList.add(new ItemToSpawn(ItemsZp.steroid, 1, 5));
        medList.add(new ItemToSpawn(ItemsZp.good_vision, 1, 8));
        medList.add(new ItemToSpawn(ItemsZp.night_vision, 1, 4));
        medList.add(new ItemToSpawn(ItemsZp.morphine, 1, 20));
        medList.add(new ItemToSpawn(ItemsZp.antiradiation, 1, 1));

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
        mod2List.add(new ItemToSpawn(ItemsZp.scope6x, 1, 10));
        mod2List.add(new ItemToSpawn(ItemsZp.pso6x, 1, 10));
        mod2List.add(new ItemToSpawn(ItemsZp.angle_foregrip, 1, 12));
        mod2List.add(new ItemToSpawn(ItemsZp.foregrip, 1, 12));
        mod2List.add(new ItemToSpawn(ItemsZp.bipod, 1, 12));
        mod2List.add(new ItemToSpawn(ItemsZp.m203, 1, 4));
        mod2List.add(new ItemToSpawn(ItemsZp.gp25, 1, 4));
        mod2List.add(new ItemToSpawn(ItemsZp.anPvs4, 1, 2));
        mod2List.add(new ItemToSpawn(ItemsZp.nspu, 1, 2));

        // Specials

        specialList.add(new ItemToSpawn(ItemsZp._customRocket, 1, 2));
        specialList.add(new ItemToSpawn(ItemsZp.ammo_press, 0.1f, 1.0f, 0.9f, 6));
        specialList.add(new ItemToSpawn(ItemsZp.kevlar, 1, 8));
        specialList.add(new ItemToSpawn(ItemsZp.smoke_grenade, 1, 8));
        specialList.add(new ItemToSpawn(ItemsZp.gas_grenade, 1, 8));
        specialList.add(new ItemToSpawn(ItemsZp.frag_grenade, 1, 12));
        specialList.add(new ItemToSpawn(ItemsZp.tnt, 1, 8));
        specialList.add(new ItemToSpawn(Item.getItemFromBlock(BlocksZp.sandbag), 2, 0.25f, 8));
        specialList.add(new ItemToSpawn(Item.getItemFromBlock(BlocksZp.mine), 1, 1));
        specialList.add(new ItemToSpawn(Item.getItemFromBlock(BlocksZp.wire_reinforced), 1, 1));
        specialList.add(new ItemToSpawn(Item.getItemFromBlock(BlocksZp.wire), 1, 5));
        specialList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.tnt), 1, 2));
        specialList.add(new ItemToSpawn(ItemsZp.pnv, 0.3f, 1.0f, 0.8f, 15));
        specialList.add(new ItemToSpawn(ItemsZp.gasmask, 0.3f, 1.0f, 0.8f, 12));
        specialList.add(new ItemToSpawn(ItemsZp.binoculars, 1.0f, 0.8f, 4));
    }

    public LootTier3Military(int p_i45397_1_) {
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
        return new TileLootCase(EnumLootGroups.Tier3Military, 0.75f, true, this.chestType);
    }
}
