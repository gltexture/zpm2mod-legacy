package ru.BouH_.tiles;

import net.minecraft.nbt.NBTTagCompound;
import ru.BouH_.blocks.lootCases.EnumLootGroups;
import ru.BouH_.init.ItemsZp;
import ru.BouH_.tiles.loot_spawn.ItemToSpawn;
import ru.BouH_.tiles.loot_spawn.LootSpawnManager;

import java.util.HashSet;
import java.util.Set;

public class TileLootSafe extends TileLootCase {
    private static final Set<ItemToSpawn> lootList = new HashSet<>();
    public static LootSpawnManager lootSpawnManager = new LootSpawnManager(lootList);

    static {
        lootList.add(new ItemToSpawn(ItemsZp.book_miner, 1, 16));
        lootList.add(new ItemToSpawn(ItemsZp.book_gunsmith, 1, 16));
        lootList.add(new ItemToSpawn(ItemsZp.book_hunter, 1, 16));
        lootList.add(new ItemToSpawn(ItemsZp.book_farmer, 1, 16));
        lootList.add(new ItemToSpawn(ItemsZp.book_fisher, 1, 16));
        lootList.add(new ItemToSpawn(ItemsZp.book_survivor, 1, 16));

        lootList.add(new ItemToSpawn(ItemsZp.chemicals2, 1.0f, 1.0f, 1.0f, 5));
    }

    private boolean isClosed;

    public TileLootSafe() {
        this.isClosed = true;
    }

    public TileLootSafe(EnumLootGroups enumLootGroups, boolean addTrash, int type) {
        this(enumLootGroups, 1.0f, addTrash, type);
    }

    public TileLootSafe(EnumLootGroups enumLootGroups, float spawnLootChance, boolean addTrash, int type) {
        super(enumLootGroups, spawnLootChance, addTrash, type);
        this.isClosed = true;
    }

    public void checkForAdjacentChests() {
    }

    @Override
    protected void onOpen() {
    }

    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.isClosed = tag.getBoolean("isClosed");
    }

    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setBoolean("isClosed", this.isClosed);
    }

    public boolean isClosed() {
        return this.isClosed;
    }

    public void setClosed(boolean closed) {
        this.isClosed = closed;
    }
}
