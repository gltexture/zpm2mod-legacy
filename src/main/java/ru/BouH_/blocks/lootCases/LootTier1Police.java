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

public class LootTier1Police extends BlockLootCase {
    public static final Set<LootSpawnManager> lootManagerSet = new HashSet<>();
    private static final Set<ItemToSpawn> gunList = new HashSet<>();
    private static final Set<ItemToSpawn> ammoList = new HashSet<>();
    private static final Set<ItemToSpawn> medList = new HashSet<>();
    private static final Set<ItemToSpawn> armorList = new HashSet<>();
    private static final Set<ItemToSpawn> specialList = new HashSet<>();
    private static final Set<ItemToSpawn> toolList = new HashSet<>();
    private static final Set<ItemToSpawn> miscList = new HashSet<>();
    private static final Set<ItemToSpawn> modList = new HashSet<>();

    static {
        lootManagerSet.add(new LootSpawnManager(LootTier1Police.gunList, 1, 16));
        lootManagerSet.add(new LootSpawnManager(LootTier1Police.ammoList, 1, 4, 0.2f, 12));
        lootManagerSet.add(new LootSpawnManager(LootTier1Police.medList, 1, 6));
        lootManagerSet.add(new LootSpawnManager(LootTier1Police.specialList, 1, 8));
        lootManagerSet.add(new LootSpawnManager(LootTier1Police.toolList, 1, 20));
        lootManagerSet.add(new LootSpawnManager(LootTier1Police.armorList, 1, 2, 0.3f, 12));
        lootManagerSet.add(new LootSpawnManager(LootTier1Police.miscList, 1, 2, 0.1f, 24));
        lootManagerSet.add(new LootSpawnManager(LootTier1Police.modList, 1, 2));
    }

    static {

        // Weapons

        gunList.add(new ItemToSpawn(ItemsZp.pm, 1.0f, 0.86f, 12));
        gunList.add(new ItemToSpawn(ItemsZp.mini_uzi, 1.0f, 0.86f, 6));
        gunList.add(new ItemToSpawn(ItemsZp.mac10, 1.0f, 0.86f, 6));
        gunList.add(new ItemToSpawn(ItemsZp.python, 1.0f, 0.86f, 3));
        gunList.add(new ItemToSpawn(ItemsZp.m1894, 1.0f, 0.86f, 3));
        gunList.add(new ItemToSpawn(ItemsZp.tt, 1.0f, 0.86f, 14));
        gunList.add(new ItemToSpawn(ItemsZp.rem870, 1.0f, 0.86f, 4));
        gunList.add(new ItemToSpawn(ItemsZp.glock18, 1.0f, 0.86f, 10));
        gunList.add(new ItemToSpawn(ItemsZp.mini14, 1.0f, 0.86f, 4));
        gunList.add(new ItemToSpawn(ItemsZp.scout, 1.0f, 0.86f, 4));
        gunList.add(new ItemToSpawn(ItemsZp.mp5, 1.0f, 0.86f, 4));
        gunList.add(new ItemToSpawn(ItemsZp.ump45, 1.0f, 0.86f, 5));
        gunList.add(new ItemToSpawn(ItemsZp.bizon, 1.0f, 0.86f, 4));
        gunList.add(new ItemToSpawn(ItemsZp.aksu, 1.0f, 0.86f, 2));
        gunList.add(new ItemToSpawn(ItemsZp.p90, 1.0f, 0.86f, 4));
        gunList.add(new ItemToSpawn(ItemsZp.m1911, 1.0f, 0.86f, 14));
        gunList.add(new ItemToSpawn(ItemsZp.m24, 1.0f, 0.86f, 1));

        // Ammo

        ammoList.add(new ItemToSpawn(ItemsZp._5_45x39, 16, 0.88f, 7));
        ammoList.add(new ItemToSpawn(ItemsZp._357m, 16, 0.88f, 15));
        ammoList.add(new ItemToSpawn(ItemsZp._7_62x25, 26, 0.88f, 18));
        ammoList.add(new ItemToSpawn(ItemsZp._45acp, 26, 0.88f, 18));
        ammoList.add(new ItemToSpawn(ItemsZp._9mm, 26, 0.88f, 18));
        ammoList.add(new ItemToSpawn(ItemsZp._5_56x45, 12, 0.88f, 7));
        ammoList.add(new ItemToSpawn(ItemsZp._7_62x39, 12, 0.88f, 7));
        ammoList.add(new ItemToSpawn(ItemsZp._12, 4, 0.86f, 10));

        // Armor

        armorList.add(new ItemToSpawn(ItemsZp.kevlar_helmet, 1.0f, 0.85f, 8));
        armorList.add(new ItemToSpawn(ItemsZp.kevlar_vest, 1.0f, 0.85f, 8));
        armorList.add(new ItemToSpawn(Items.iron_helmet, 1.0f, 0.85f, 21));
        armorList.add(new ItemToSpawn(Items.iron_chestplate, 1.0f, 0.85f, 21));
        armorList.add(new ItemToSpawn(Items.iron_leggings, 1.0f, 0.85f, 21));
        armorList.add(new ItemToSpawn(Items.iron_boots, 1.0f, 0.85f, 21));

        // Tools

        toolList.add(new ItemToSpawn(ItemsZp.police_club, 1.0f, 0.9f, 40));
        toolList.add(new ItemToSpawn(ItemsZp.iron_club, 1.0f, 0.9f, 35));
        toolList.add(new ItemToSpawn(Items.iron_sword, 1.0f, 0.9f, 25));

        // Misc

        miscList.add(new ItemToSpawn(ItemsZp.steel_material, 1, 0.2f, 2));
        miscList.add(new ItemToSpawn(Items.coal, 4, 0.6f, 32));
        miscList.add(new ItemToSpawn(Items.iron_ingot, 1, 30));
        miscList.add(new ItemToSpawn(Items.arrow, 32, 0.8f, 36));

        // Med

        medList.add(new ItemToSpawn(ItemsZp.bandage, 1, 50));
        medList.add(new ItemToSpawn(ItemsZp.coke, 1, 15));
        medList.add(new ItemToSpawn(ItemsZp.blind_syringe, 1, 25));
        medList.add(new ItemToSpawn(ItemsZp.aid_kit, 1, 10));

        // Mod

        modList.add(new ItemToSpawn(ItemsZp.scope_eotech, 1, 16));
        modList.add(new ItemToSpawn(ItemsZp.scope_kobra, 1, 16));
        modList.add(new ItemToSpawn(ItemsZp.laser, 1, 2));
        modList.add(new ItemToSpawn(ItemsZp.flashSuppressor_rifle, 1, 16));
        modList.add(new ItemToSpawn(ItemsZp.muzzlebrake_pistol, 1, 16));
        modList.add(new ItemToSpawn(ItemsZp.silencer_pistol, 1, 14));
        modList.add(new ItemToSpawn(ItemsZp.scope_pu, 1, 10));
        modList.add(new ItemToSpawn(ItemsZp.pistol_scope, 1, 10));

        // Specials

        specialList.add(new ItemToSpawn(ItemsZp.kevlar, 1, 12));
        specialList.add(new ItemToSpawn(ItemsZp.smoke_grenade, 1, 26));
        specialList.add(new ItemToSpawn(ItemsZp.gas_grenade, 1, 24));
        specialList.add(new ItemToSpawn(ItemsZp.frag_grenade, 1, 6));
        specialList.add(new ItemToSpawn(ItemsZp.repair, 0.1f, 1.0f, 0.9f, 10));
        specialList.add(new ItemToSpawn(ItemsZp.lubricant, 1, 8));
        specialList.add(new ItemToSpawn(ItemsZp.pnv, 0.1f, 1.0f, 0.8f, 8));
        specialList.add(new ItemToSpawn(ItemsZp.lockpick, 1, 6));
    }

    public LootTier1Police(int p_i45397_1_) {
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
        return new TileLootCase(EnumLootGroups.Tier1Police, 0.6f, true, this.chestType);
    }
}
