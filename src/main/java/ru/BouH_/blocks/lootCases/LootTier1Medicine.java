package ru.BouH_.blocks.lootCases;

import net.minecraft.entity.EntityLivingBase;
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

public class LootTier1Medicine extends BlockLootCase {
    public static final Set<LootSpawnManager> lootManagerSet = new HashSet<>();
    private static final Set<ItemToSpawn> medList = new HashSet<>();

    static {
        lootManagerSet.add(new LootSpawnManager(LootTier1Medicine.medList, 1, 3, 0.03f, 100));
    }

    static {
        // Med

        medList.add(new ItemToSpawn(ItemsZp.bandage, 1, 16));
        medList.add(new ItemToSpawn(ItemsZp.aid_kit, 1, 4));
        medList.add(new ItemToSpawn(ItemsZp.ai2_kit, 1, 4));
        medList.add(new ItemToSpawn(ItemsZp.good_vision, 1, 5));
        medList.add(new ItemToSpawn(ItemsZp.blood_bag, 1, 2));
        medList.add(new ItemToSpawn(ItemsZp.antihunger, 1, 8));
        medList.add(new ItemToSpawn(ItemsZp.antipoison, 1, 8));
        medList.add(new ItemToSpawn(ItemsZp.antiheadache, 1, 8));
        medList.add(new ItemToSpawn(ItemsZp.poison, 0.0f, 1.0f, 0.7f, 2));
        medList.add(new ItemToSpawn(ItemsZp.morphine, 1, 5));
        medList.add(new ItemToSpawn(ItemsZp.antitoxin, 1, 5));
        medList.add(new ItemToSpawn(ItemsZp.vodka, 1, 12));
        medList.add(new ItemToSpawn(ItemsZp.heal, 1, 8));
        medList.add(new ItemToSpawn(ItemsZp.tire, 1, 10));
        medList.add(new ItemToSpawn(ItemsZp.stimulator, 1, 2));
        medList.add(new ItemToSpawn(ItemsZp.blood_material, 1, 1));
    }

    public LootTier1Medicine(int p_i45397_1_) {
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
        return new TileLootCase(EnumLootGroups.Tier1Medicine, 0.35f, true, this.chestType);
    }
}
