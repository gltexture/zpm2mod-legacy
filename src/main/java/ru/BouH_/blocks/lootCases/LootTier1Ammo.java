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

public class LootTier1Ammo extends BlockLootCase {
    public static final Set<LootSpawnManager> lootManagerSet = new HashSet<>();
    private static final Set<ItemToSpawn> ammoList = new HashSet<>();
    private static final Set<ItemToSpawn> ammo2List = new HashSet<>();

    static {
        lootManagerSet.add(new LootSpawnManager(LootTier1Ammo.ammoList, 2, 5, 0.7f, 90));
        lootManagerSet.add(new LootSpawnManager(LootTier1Ammo.ammo2List, 1, 3, 0.3f, 10));
    }

    static {

        // Ammo

        ammoList.add(new ItemToSpawn(ItemsZp._357m, 16, 0.9f, 12));
        ammoList.add(new ItemToSpawn(ItemsZp._45acp, 26, 0.9f, 20));
        ammoList.add(new ItemToSpawn(ItemsZp._9mm, 26, 0.9f, 12));
        ammoList.add(new ItemToSpawn(ItemsZp._7_62x25, 26, 0.9f, 12));
        ammoList.add(new ItemToSpawn(ItemsZp._5_7x28, 16, 0.9f, 10));
        ammoList.add(new ItemToSpawn(ItemsZp._12, 6, 0.9f, 10));
        ammoList.add(new ItemToSpawn(ItemsZp._22lr, 26, 0.9f, 16));
        ammoList.add(new ItemToSpawn(Items.arrow, 26, 0.9f, 8));

        // Ammo 2

        ammo2List.add(new ItemToSpawn(ItemsZp._5_45x39, 16, 0.9f, 25));
        ammo2List.add(new ItemToSpawn(ItemsZp._5_56x45, 16, 0.9f, 25));
        ammo2List.add(new ItemToSpawn(ItemsZp._7_62x39, 16, 0.9f, 25));
        ammo2List.add(new ItemToSpawn(ItemsZp._7_62x54R, 8, 0.9f, 15));
        ammo2List.add(new ItemToSpawn(ItemsZp._9x39, 16, 0.9f, 10));
    }

    public LootTier1Ammo(int p_i45397_1_) {
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
        return new TileLootCase(EnumLootGroups.Tier1Ammo, 1.0f, true, this.chestType);
    }
}
