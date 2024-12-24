package ru.BouH_.blocks.lootCases;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.achievements.AchievementManager;
import ru.BouH_.init.BlocksZp;
import ru.BouH_.init.ItemsZp;
import ru.BouH_.tiles.TileLootSafe;
import ru.BouH_.tiles.loot_spawn.ItemToSpawn;
import ru.BouH_.tiles.loot_spawn.LootSpawnManager;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class LootTierXSafe extends BlockLootCase {
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
    private static final Set<ItemToSpawn> blockList = new HashSet<>();

    static {
        lootManagerSet.add(new LootSpawnManager(LootTierXSafe.gunList, 1, 10));
        lootManagerSet.add(new LootSpawnManager(LootTierXSafe.gun2List, 1, 8));
        lootManagerSet.add(new LootSpawnManager(LootTierXSafe.ammoList, 4, 10, 0.8f, 15));
        lootManagerSet.add(new LootSpawnManager(LootTierXSafe.medList, 1, 2, 0.3f, 10));
        lootManagerSet.add(new LootSpawnManager(LootTierXSafe.specialList, 1, 8));
        lootManagerSet.add(new LootSpawnManager(LootTierXSafe.toolList, 1, 2, 0.9f, 10));
        lootManagerSet.add(new LootSpawnManager(LootTierXSafe.armorList, 1, 2, 0.3f, 8));
        lootManagerSet.add(new LootSpawnManager(LootTierXSafe.foodList, 2, 4, 0.5f, 12));
        lootManagerSet.add(new LootSpawnManager(LootTierXSafe.miscList, 1, 2, 0.3f, 12));
        lootManagerSet.add(new LootSpawnManager(LootTierXSafe.modList, 1, 2));
        lootManagerSet.add(new LootSpawnManager(LootTierXSafe.blockList, 2, 4, 0.9f, 5));
    }

    static {

        // Weapons

        gunList.add(new ItemToSpawn(ItemsZp.fal, 0.3f, 1.0f, 0.92f, 8));
        gunList.add(new ItemToSpawn(ItemsZp.sg550, 0.3f, 1.0f, 0.92f, 8));
        gunList.add(new ItemToSpawn(ItemsZp.deagle, 0.3f, 1.0f, 0.92f, 6));
        gunList.add(new ItemToSpawn(ItemsZp.akm, 0.3f, 1.0f, 0.92f, 8));
        gunList.add(new ItemToSpawn(ItemsZp.aksu, 0.3f, 1.0f, 0.92f, 12));
        gunList.add(new ItemToSpawn(ItemsZp.m16a1, 0.3f, 1.0f, 0.92f, 8));
        gunList.add(new ItemToSpawn(ItemsZp.g36k, 0.3f, 1.0f, 0.92f, 8));
        gunList.add(new ItemToSpawn(ItemsZp.aug, 0.3f, 1.0f, 0.92f, 6));
        gunList.add(new ItemToSpawn(ItemsZp.mosin, 0.3f, 1.0f, 0.92f, 6));
        gunList.add(new ItemToSpawn(ItemsZp.fiveseven, 0.3f, 1.0f, 0.92f, 12));
        gunList.add(new ItemToSpawn(ItemsZp.m4a1, 0.3f, 1.0f, 0.92f, 8));
        gunList.add(new ItemToSpawn(ItemsZp.p90, 0.3f, 1.0f, 0.92f, 10));

        // Weapons 2

        gun2List.add(new ItemToSpawn(ItemsZp.scar, 0.3f, 1.0f, 0.92f, 16));
        gun2List.add(new ItemToSpawn(ItemsZp.svd, 0.3f, 1.0f, 0.92f, 16));
        gun2List.add(new ItemToSpawn(ItemsZp.pkm, 0.3f, 1.0f, 0.92f, 12));
        gun2List.add(new ItemToSpawn(ItemsZp.m249, 0.3f, 1.0f, 0.92f, 12));
        gun2List.add(new ItemToSpawn(ItemsZp.rpk, 0.3f, 1.0f, 0.92f, 12));
        gun2List.add(new ItemToSpawn(ItemsZp.sks, 0.3f, 1.0f, 0.92f, 15));
        gun2List.add(new ItemToSpawn(ItemsZp.m79, 0.3f, 1.0f, 0.85f, 2));
        gun2List.add(new ItemToSpawn(ItemsZp.spas12, 0.3f, 1.0f, 0.92f, 15));

        // Ammo

        ammoList.add(new ItemToSpawn(ItemsZp._9mm, 32, 0.92f, 12));
        ammoList.add(new ItemToSpawn(ItemsZp._12, 12, 0.92f, 10));
        ammoList.add(new ItemToSpawn(ItemsZp._7_62x39, 32, 0.92f, 6));
        ammoList.add(new ItemToSpawn(ItemsZp._5_56x45, 32, 0.92f, 6));
        ammoList.add(new ItemToSpawn(ItemsZp._45acp, 32, 0.92f, 12));
        ammoList.add(new ItemToSpawn(ItemsZp._7_62x25, 32, 0.92f, 12));
        ammoList.add(new ItemToSpawn(ItemsZp._5_45x39, 32, 0.92f, 6));
        ammoList.add(new ItemToSpawn(ItemsZp._7_62x54R, 12, 0.92f, 6));
        ammoList.add(new ItemToSpawn(ItemsZp._5_7x28, 32, 0.92f, 12));
        ammoList.add(new ItemToSpawn(ItemsZp._9x39, 16, 0.92f, 6));
        ammoList.add(new ItemToSpawn(ItemsZp._357m, 32, 0.92f, 12));

        // Armor

        armorList.add(new ItemToSpawn(Items.diamond_helmet, 0.5f, 1.0f, 0.9f, 10));
        armorList.add(new ItemToSpawn(Items.diamond_chestplate, 0.5f, 1.0f, 0.9f, 10));
        armorList.add(new ItemToSpawn(Items.diamond_leggings, 0.5f, 1.0f, 0.9f, 10));
        armorList.add(new ItemToSpawn(Items.diamond_boots, 0.5f, 1.0f, 0.9f, 10));

        armorList.add(new ItemToSpawn(ItemsZp.steel_helmet, 0.5f, 1.0f, 0.9f, 8));
        armorList.add(new ItemToSpawn(ItemsZp.steel_chestplate, 0.5f, 1.0f, 0.9f, 8));
        armorList.add(new ItemToSpawn(ItemsZp.steel_leggings, 0.5f, 1.0f, 0.9f, 8));
        armorList.add(new ItemToSpawn(ItemsZp.steel_boots, 0.5f, 1.0f, 0.9f, 8));

        armorList.add(new ItemToSpawn(ItemsZp.rad_helmet, 0.5f, 1.0f, 0.9f, 7));
        armorList.add(new ItemToSpawn(ItemsZp.rad_chestplate, 0.5f, 1.0f, 0.9f, 7));
        armorList.add(new ItemToSpawn(ItemsZp.rad_leggings, 0.5f, 1.0f, 0.9f, 7));
        armorList.add(new ItemToSpawn(ItemsZp.rad_boots, 0.5f, 1.0f, 0.9f, 7));

        // Tools

        toolList.add(new ItemToSpawn(ItemsZp.lockpick, 1, 4));
        toolList.add(new ItemToSpawn(ItemsZp.m_scissors, 0.3f, 1.0f, 0.9f, 4));
        toolList.add(new ItemToSpawn(ItemsZp.wrench, 0.3f, 1.0f, 0.8f, 4));
        toolList.add(new ItemToSpawn(ItemsZp.sledgehammer, 0.3f, 1.0f, 0.9f, 6));
        toolList.add(new ItemToSpawn(ItemsZp.hatchet, 0.3f, 1.0f, 0.9f, 6));
        toolList.add(new ItemToSpawn(Items.diamond_axe, 0.3f, 1.0f, 0.9f, 10));
        toolList.add(new ItemToSpawn(Items.diamond_sword, 0.3f, 1.0f, 0.9f, 9));
        toolList.add(new ItemToSpawn(Items.diamond_pickaxe, 0.3f, 1.0f, 0.9f, 10));
        toolList.add(new ItemToSpawn(ItemsZp.machete, 0.3f, 1.0f, 0.9f, 10));
        toolList.add(new ItemToSpawn(ItemsZp.titan_axe, 0.3f, 1.0f, 0.9f, 5));
        toolList.add(new ItemToSpawn(ItemsZp.titan_sword, 0.3f, 1.0f, 0.9f, 4));
        toolList.add(new ItemToSpawn(ItemsZp.titan_pickaxe, 0.3f, 1.0f, 0.9f, 5));
        toolList.add(new ItemToSpawn(ItemsZp.steel_axe, 0.3f, 1.0f, 0.9f, 8));
        toolList.add(new ItemToSpawn(ItemsZp.steel_sword, 0.3f, 1.0f, 0.9f, 7));
        toolList.add(new ItemToSpawn(ItemsZp.steel_pickaxe, 0.3f, 1.0f, 0.9f, 8));

        // Food

        foodList.add(new ItemToSpawn(ItemsZp.water, 1, 50));
        foodList.add(new ItemToSpawn(ItemsZp.stewed_meat, 1, 10));
        foodList.add(new ItemToSpawn(ItemsZp.pea, 1, 10));
        foodList.add(new ItemToSpawn(ItemsZp.ananas, 1, 10));
        foodList.add(new ItemToSpawn(Items.cooked_beef, 1, 5));
        foodList.add(new ItemToSpawn(Items.cooked_porkchop, 1, 5));
        foodList.add(new ItemToSpawn(ItemsZp.soup, 1, 10));

        // Misc

        miscList.add(new ItemToSpawn(ItemsZp.steel_ingot, 1, 8));
        miscList.add(new ItemToSpawn(ItemsZp.titan_ingot, 1, 1));
        miscList.add(new ItemToSpawn(ItemsZp.raw_titan, 1, 1));
        miscList.add(new ItemToSpawn(ItemsZp.CBS, 0.3f, 1.0f, 0.8f, 5));
        miscList.add(new ItemToSpawn(ItemsZp.dosimeter, 0.3f, 1.0f, 0.8f, 5));
        miscList.add(new ItemToSpawn(ItemsZp.tanning, 1, 2));
        miscList.add(new ItemToSpawn(ItemsZp.cement, 1, 14));
        miscList.add(new ItemToSpawn(Items.redstone, 16, 0.7f, 4));
        miscList.add(new ItemToSpawn(ItemsZp.solid_fuel, 2, 0.7f, 4));
        miscList.add(new ItemToSpawn(Items.iron_ingot, 6, 0.9f, 5));
        miscList.add(new ItemToSpawn(Items.gold_ingot, 4, 0.9f, 5));
        miscList.add(new ItemToSpawn(ItemsZp.raw_iron, 6, 0.9f, 5));
        miscList.add(new ItemToSpawn(ItemsZp.raw_gold, 4, 0.9f, 5));
        miscList.add(new ItemToSpawn(ItemsZp.brass_material, 6, 0.92f, 2));
        miscList.add(new ItemToSpawn(Items.diamond, 2, 0.9f, 5));
        miscList.add(new ItemToSpawn(Items.emerald, 1, 5));
        miscList.add(new ItemToSpawn(ItemsZp.electronic, 1, 8));
        miscList.add(new ItemToSpawn(ItemsZp.battery, 1, 8));
        miscList.add(new ItemToSpawn(ItemsZp.coils, 1, 8));

        // Med

        medList.add(new ItemToSpawn(ItemsZp.bandage, 1, 15));
        medList.add(new ItemToSpawn(ItemsZp.aid_kit, 1, 5));
        medList.add(new ItemToSpawn(ItemsZp.night_vision, 1, 4));
        medList.add(new ItemToSpawn(ItemsZp.blood_bag, 1, 2));
        medList.add(new ItemToSpawn(ItemsZp.antihunger, 1, 10));
        medList.add(new ItemToSpawn(ItemsZp.antipoison, 1, 10));
        medList.add(new ItemToSpawn(ItemsZp.antiheadache, 1, 10));
        medList.add(new ItemToSpawn(ItemsZp.poison, 0.0f, 1.0f, 0.9f, 5));
        medList.add(new ItemToSpawn(ItemsZp.morphine, 1, 8));
        medList.add(new ItemToSpawn(ItemsZp.antitoxin, 1, 5));
        medList.add(new ItemToSpawn(ItemsZp.vodka, 1, 14));
        medList.add(new ItemToSpawn(ItemsZp.heal, 1, 8));
        medList.add(new ItemToSpawn(ItemsZp.stimulator, 1, 2));
        medList.add(new ItemToSpawn(ItemsZp.blood_material, 1, 2));

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
        specialList.add(new ItemToSpawn(ItemsZp.repair, 0.1f, 1.0f, 0.9f, 15));
        specialList.add(new ItemToSpawn(ItemsZp.lubricant, 1, 15));
        specialList.add(new ItemToSpawn(Item.getItemFromBlock(BlocksZp.sandbag), 12, 0.8f, 5));
        specialList.add(new ItemToSpawn(Items.gunpowder, 5, 0.7f, 15));
        specialList.add(new ItemToSpawn(ItemsZp._customRocket, 5, 0.7f, 15));
        specialList.add(new ItemToSpawn(ItemsZp.pnv, 0.1f, 1.0f, 0.9f, 10));
        specialList.add(new ItemToSpawn(Items.glowstone_dust, 6, 0.3f, 15));
        specialList.add(new ItemToSpawn(ItemsZp.electrician_kit, 1.0f, 0.9f, 5));

        // Blocks

        blockList.add(new ItemToSpawn(Item.getItemFromBlock(BlocksZp.concrete_green), 3, 0.7f, 4));
        blockList.add(new ItemToSpawn(Item.getItemFromBlock(BlocksZp.concrete), 4, 0.6f, 4));
        blockList.add(new ItemToSpawn(Item.getItemFromBlock(BlocksZp.uranium), 1, 4));
        blockList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.brick_block), 6, 0.82f, 5));
        blockList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.glass), 4, 0.82f, 14));
        blockList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.iron_bars), 4, 0.82f, 16));
        blockList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.coal_block), 2, 0.1f, 16));
        blockList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.enchanting_table), 1, 1));
        blockList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.torch), 32, 0.9f, 22));
        blockList.add(new ItemToSpawn(Item.getItemFromBlock(BlocksZp.lamp), 8, 0.82f, 10));
        blockList.add(new ItemToSpawn(Item.getItemFromBlock(BlocksZp.wire), 2, 0.5f, 2));
        blockList.add(new ItemToSpawn(Item.getItemFromBlock(BlocksZp.copper_block), 3, 0.5f, 2));
    }

    public LootTierXSafe(int p_i45397_1_) {
        super(p_i45397_1_);
        this.setStepSound(new SoundType("wood", 1.0F, 1.0F));
    }

    public boolean canPlaceBlockAt(World worldIn, int x, int y, int z) {
        return worldIn.getBlock(x - 1, y, z) != this && worldIn.getBlock(x + 1, y, z) != this && worldIn.getBlock(x, y, z - 1) != this && worldIn.getBlock(x, y, z + 1) != this;
    }

    public boolean onBlockActivated(World worldIn, int x, int y, int z, EntityPlayer player, int side, float subX, float subY, float subZ) {
        if (!worldIn.isRemote) {
            IInventory iinventory = this.getInventory(worldIn, x, y, z);
            TileLootSafe tileLootSafe = (TileLootSafe) worldIn.getTileEntity(x, y, z);
            if (tileLootSafe == null) {
                return false;
            }
            if (iinventory != null) {
                if (tileLootSafe.isClosed()) {
                    if (player.capabilities.isCreativeMode || (player.getHeldItem() != null && player.getHeldItem().getItem() == ItemsZp.lockpick)) {
                        worldIn.playSoundAtEntity(player, Main.MODID + ":lock_open", 1.0f, Main.rand.nextFloat() * 0.2f + 1);
                        if (!player.capabilities.isCreativeMode) {
                            player.getHeldItem().stackSize--;
                        }
                        AchievementManager.instance.triggerAchievement(AchievementManager.instance.lockpick, player);
                        tileLootSafe.reloadChest(2);
                        TileLootSafe.lootSpawnManager.spawnRandomItem(tileLootSafe);
                        tileLootSafe.setClosed(false);
                    } else {
                        worldIn.playSoundAtEntity(player, Main.MODID + ":lock_fail", 1.0f, Main.rand.nextFloat() * 0.2f + 1);
                    }
                    return false;
                }
                player.displayGUIChest(iinventory);
            }
        }
        return true;
    }

    public void onBlockPlacedBy(World p_149689_1_, int p_149689_2_, int p_149689_3_, int p_149689_4_, EntityLivingBase p_149689_5_, ItemStack p_149689_6_) {
        super.onBlockPlacedBy(p_149689_1_, p_149689_2_, p_149689_3_, p_149689_4_, p_149689_5_, p_149689_6_);
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return null;
    }

    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileLootSafe(EnumLootGroups.TierXSafe, 1.0f, true, this.chestType);
    }
}
