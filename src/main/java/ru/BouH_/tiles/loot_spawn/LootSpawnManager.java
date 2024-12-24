package ru.BouH_.tiles.loot_spawn;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityChest;
import ru.BouH_.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class LootSpawnManager {
    private final Random rand = Main.rand;
    private final Set<ItemToSpawn> lootSet;
    private final int maxItemSpawnTimes;
    private final int minItemSpawnTimes;
    private final float eachTimeSpawnStep;
    private final int spawnChance;

    public LootSpawnManager(Set<ItemToSpawn> lootSet, int minItemSpawnTimes, int maxItemSpawnTimes, float eachTimeSpawnStep, int spawnChance) {
        this.lootSet = lootSet;
        this.maxItemSpawnTimes = maxItemSpawnTimes;
        this.minItemSpawnTimes = minItemSpawnTimes;
        this.eachTimeSpawnStep = eachTimeSpawnStep;
        this.spawnChance = spawnChance;
    }

    public LootSpawnManager(Set<ItemToSpawn> lootSet, int minItemSpawnTimes, int maxItemSpawnTimes, float eachTimeSpawnStep) {
        this(lootSet, minItemSpawnTimes, maxItemSpawnTimes, eachTimeSpawnStep, 100);
    }

    public LootSpawnManager(Set<ItemToSpawn> lootSet, int minItemSpawnTimes, int spawnChance) {
        this(lootSet, minItemSpawnTimes, 1, 0, spawnChance);
    }

    public LootSpawnManager(Set<ItemToSpawn> lootSet, int spawnChance) {
        this(lootSet, 1, 1, 0, spawnChance);
    }

    public LootSpawnManager(Set<ItemToSpawn> lootSet) {
        this(lootSet, 1, 1, 0, 0);
    }

    public ItemStack getRandomItemStack() {
        //TODO
        List<ItemToSpawn> list = new ArrayList<>();
        for (ItemToSpawn lsp : this.lootSet) {
            for (int i = 0; i < lsp.getSpawnChance(); i++) {
                list.add(lsp);
            }
        }
        if (list.size() == 100) {
            ItemToSpawn il = list.get(rand.nextInt(100));
            ItemStack stack = il.getItemStack(il.getItem());
            if (stack == null) {
                FMLLog.info("NULL STACK ");
            }
            return stack;
        } else {
            FMLLog.info("Wrong list size, " + list.size() + "/100 " + list.get(0).getItem().getUnlocalizedName());
        }
        return null;
    }

    public void spawnRandomItem(TileEntityChest tile) {
        for (int i = 0; i < this.getSpawnTimes(); i++) {
            ItemStack stack = this.getRandomItemStack();
            if (stack != null) {
                tile.setInventorySlotContents(this.getRandomFreeSlot(tile), stack);
            }
        }
    }

    private int getRandomFreeSlot(TileEntityChest tile) {
        int attemps = 128;
        int i = this.rand.nextInt(tile.getSizeInventory());
        while (tile.getStackInSlot(i) != null) {
            i = this.rand.nextInt(tile.getSizeInventory());
            if (attemps-- <= 0) {
                break;
            }
        }
        return i;
    }

    public int getSpawnTimes() {
        int i = this.minItemSpawnTimes;
        if (this.maxItemSpawnTimes > 1) {
            float randomFloat = this.rand.nextFloat();
            float f1 = this.eachTimeSpawnStep;
            while (randomFloat <= f1 && i < this.maxItemSpawnTimes) {
                i++;
                f1 *= 0.75f * this.eachTimeSpawnStep;
            }
        }
        return i;
    }

    public int getSpawnChance() {
        return this.spawnChance;
    }

    public Set<ItemToSpawn> getLootSet() {
        return this.lootSet;
    }
}
