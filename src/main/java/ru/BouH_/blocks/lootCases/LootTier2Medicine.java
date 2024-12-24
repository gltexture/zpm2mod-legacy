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

public class LootTier2Medicine extends BlockLootCase {
    public static final Set<LootSpawnManager> lootManagerSet = new HashSet<>();
    private static final Set<ItemToSpawn> medList = new HashSet<>();
    private static final Set<ItemToSpawn> med2List = new HashSet<>();

    static {
        lootManagerSet.add(new LootSpawnManager(LootTier2Medicine.medList, 1, 2, 0.02f, 80));
        lootManagerSet.add(new LootSpawnManager(LootTier2Medicine.med2List, 1, 20));
    }

    static {

        // Med

        medList.add(new ItemToSpawn(ItemsZp.bandage, 1, 15));
        medList.add(new ItemToSpawn(ItemsZp.aid_kit, 1, 8));
        medList.add(new ItemToSpawn(ItemsZp.good_vision, 1, 4));
        medList.add(new ItemToSpawn(ItemsZp.blood_bag, 1, 2));
        medList.add(new ItemToSpawn(ItemsZp.antihunger, 1, 10));
        medList.add(new ItemToSpawn(ItemsZp.antipoison, 1, 10));
        medList.add(new ItemToSpawn(ItemsZp.antiheadache, 1, 10));
        medList.add(new ItemToSpawn(ItemsZp.poison, 0.0f, 1.0f, 0.9f, 4));
        medList.add(new ItemToSpawn(ItemsZp.morphine, 1, 8));
        medList.add(new ItemToSpawn(ItemsZp.antitoxin, 1, 5));
        medList.add(new ItemToSpawn(ItemsZp.vodka, 1, 12));
        medList.add(new ItemToSpawn(ItemsZp.heal, 1, 8));
        medList.add(new ItemToSpawn(ItemsZp.stimulator, 1, 2));
        medList.add(new ItemToSpawn(ItemsZp.blood_material, 1, 2));

        // Med2

        med2List.add(new ItemToSpawn(ItemsZp.adrenaline, 1, 14));
        med2List.add(new ItemToSpawn(ItemsZp.ai2_kit, 1, 20));
        med2List.add(new ItemToSpawn(ItemsZp.night_vision, 1, 10));
        med2List.add(new ItemToSpawn(ItemsZp.steroid, 1, 5));
        med2List.add(new ItemToSpawn(ItemsZp.blind_syringe, 1, 5));
        med2List.add(new ItemToSpawn(ItemsZp.antidote_syringe, 1, 10));
        med2List.add(new ItemToSpawn(ItemsZp.heroin, 1, 9));
        med2List.add(new ItemToSpawn(ItemsZp.virus_syringe, 1, 5));
        med2List.add(new ItemToSpawn(ItemsZp.coke, 1, 9));
        med2List.add(new ItemToSpawn(ItemsZp.meth, 1, 9));
        med2List.add(new ItemToSpawn(ItemsZp.antiradiation, 1, 4));
    }

    public LootTier2Medicine(int p_i45397_1_) {
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
        return new TileLootCase(EnumLootGroups.Tier2Medicine, 0.65f, true, this.chestType);
    }
}
