package ru.BouH_.blocks.lootCases;

import ru.BouH_.tiles.loot_spawn.LootSpawnManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public enum EnumLootGroups {
    Tier1Village(0, LootTier1Village.lootManagerSet),
    Tier2Village(1, LootTier2Village.lootManagerSet),
    Tier1City(2, LootTier1City.lootManagerSet),
    Tier1Police(3, LootTier1Police.lootManagerSet),
    Tier1Food(4, LootTier1Food.lootManagerSet),
    Tier1Medicine(5, LootTier1Medicine.lootManagerSet),
    Tier2Medicine(6, LootTier2Medicine.lootManagerSet),
    Tier1Ammo(7, LootTier1Ammo.lootManagerSet),
    Tier2Ammo(8, LootTier2Ammo.lootManagerSet),
    Tier1Industry(9, LootTier1Industry.lootManagerSet),
    Tier2Industry(10, LootTier2Industry.lootManagerSet),
    Tier1Military(11, LootTier1Military.lootManagerSet),
    Tier2Military(12, LootTier2Military.lootManagerSet),
    Tier3Military(13, LootTier3Military.lootManagerSet),
    Tier1Hunting(14, LootTier1Hunting.lootManagerSet),
    Tier2Hunting(15, LootTier1Hunting.lootManagerSet),
    Tier1MegaCity(16, LootTier1MegaCity.lootManagerSet),
    Tier1Labs(17, LootTier1Labs.lootManagerSet),
    TierXSafe(18, LootTierXSafe.lootManagerSet);

    private static final Map<Integer, EnumLootGroups> ELGMap = new HashMap<>();

    static {
        EnumLootGroups[] enumLootGroups = EnumLootGroups.values();
        for (EnumLootGroups enumLootGroups1 : enumLootGroups) {
            EnumLootGroups.ELGMap.put(enumLootGroups1.getId(), enumLootGroups1);
        }
    }

    private final Set<LootSpawnManager> lsp;
    private final int id;

    EnumLootGroups(int id, Set<LootSpawnManager> lsp) {
        this.lsp = lsp;
        this.id = id;
    }

    public static EnumLootGroups getLootGroupById(int id) {
        return EnumLootGroups.ELGMap.get(id);
    }

    public int getId() {
        return this.id;
    }

    public Set<LootSpawnManager> getLSP() {
        return this.lsp;
    }
}
