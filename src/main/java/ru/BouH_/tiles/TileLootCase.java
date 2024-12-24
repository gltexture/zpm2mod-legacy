package ru.BouH_.tiles;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.world.EnumDifficulty;
import ru.BouH_.ConfigZp;
import ru.BouH_.Main;
import ru.BouH_.blocks.lootCases.EnumLootGroups;
import ru.BouH_.init.BlocksZp;
import ru.BouH_.init.ItemsZp;
import ru.BouH_.tiles.loot_spawn.ItemToSpawn;
import ru.BouH_.tiles.loot_spawn.LootSpawnManager;
import ru.BouH_.world.type.WorldTypeHardcoreZp;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TileLootCase extends TileEntityChest {
    public static final Set<ItemToSpawn> trashCommonSpawnList = new HashSet<>();
    public static final Set<ItemToSpawn> trashDecorSpawnList = new HashSet<>();
    public static final Set<ItemToSpawn> trashBonusSpawnList = new HashSet<>();
    public static final Set<ItemToSpawn> trashToolsSpawnList = new HashSet<>();
    public static final Set<ItemToSpawn> trashItemsSpawnList = new HashSet<>();
    public static final Set<ItemToSpawn> trashSpecialSpawnList = new HashSet<>();
    public static final LootSpawnManager trashLootManager1 = new LootSpawnManager(TileLootCase.trashCommonSpawnList, 2, 12, 0.92f, 34);
    public static final LootSpawnManager trashLootManager2 = new LootSpawnManager(TileLootCase.trashDecorSpawnList, 1, 4, 0.15f, 30);
    public static final LootSpawnManager trashLootManager3 = new LootSpawnManager(TileLootCase.trashBonusSpawnList, 1, 4, 0.15f, 8);
    public static final LootSpawnManager trashLootManager4 = new LootSpawnManager(TileLootCase.trashToolsSpawnList, 1, 4, 0.1f, 7);
    public static final LootSpawnManager trashLootManager5 = new LootSpawnManager(TileLootCase.trashItemsSpawnList, 1, 4, 0.15f, 20);
    public static final LootSpawnManager trashLootManager6 = new LootSpawnManager(TileLootCase.trashSpecialSpawnList, 1);
    private final static Set<LootSpawnManager> trashSet = new HashSet<>();

    static {
        trashCommonSpawnList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.web), 1, 2));
        trashCommonSpawnList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.deadbush), 1, 34));
        trashCommonSpawnList.add(new ItemToSpawn(Item.getItemFromBlock(BlocksZp.torch5), 1, 24));
        trashCommonSpawnList.add(new ItemToSpawn(Item.getItemFromBlock(BlocksZp.torch4), 1, 12));
        trashCommonSpawnList.add(new ItemToSpawn(Item.getItemFromBlock(BlocksZp.torch3), 1, 6));
        trashCommonSpawnList.add(new ItemToSpawn(Items.rotten_flesh, 1, 10));
        trashCommonSpawnList.add(new ItemToSpawn(Items.glass_bottle, 2, 0.1f, 4));
        trashCommonSpawnList.add(new ItemToSpawn(Items.bowl, 2, 0.1f, 6));
        trashCommonSpawnList.add(new ItemToSpawn(Item.getItemFromBlock(BlocksZp.lamp_off), 1, 1));
        trashCommonSpawnList.add(new ItemToSpawn(Item.getItemFromBlock(BlocksZp.block_lamp_off), 1, 1));

        trashSpecialSpawnList.add(new ItemToSpawn(ItemsZp._custom, 6, 0.15f, 30));
        trashSpecialSpawnList.add(new ItemToSpawn(ItemsZp._custom2, 3, 0.06f, 20));
        trashSpecialSpawnList.add(new ItemToSpawn(ItemsZp.custom_pistol, 0.3f, 0.5f, 12));
        trashSpecialSpawnList.add(new ItemToSpawn(ItemsZp.custom_revolver, 0.3f, 0.5f, 10));
        trashSpecialSpawnList.add(new ItemToSpawn(ItemsZp.custom_rifle, 0.3f, 0.5f, 12));
        trashSpecialSpawnList.add(new ItemToSpawn(ItemsZp.custom_sniper, 0.3f, 0.5f, 10));
        trashSpecialSpawnList.add(new ItemToSpawn(ItemsZp.custom_shotgun, 0.3f, 0.5f, 6));

        trashDecorSpawnList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.red_flower), new ItemToSpawn.IntRange(0, 16), 1, 5));
        trashDecorSpawnList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.yellow_flower), 1, 5));
        trashDecorSpawnList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.ladder), 1, 5));
        trashDecorSpawnList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.fence_gate), 1, 5));
        trashDecorSpawnList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.fence), 1, 5));
        trashDecorSpawnList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.nether_brick_fence), 1, 5));
        trashDecorSpawnList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.tripwire_hook), 1, 5));
        trashDecorSpawnList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.waterlily), 1, 5));
        trashDecorSpawnList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.vine), 1, 5));
        trashDecorSpawnList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.lever), 1, 5));
        trashDecorSpawnList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.sand), 1, 5));
        trashDecorSpawnList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.dirt), 1, 5));
        trashDecorSpawnList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.gravel), 1, 5));
        trashDecorSpawnList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.sapling), new ItemToSpawn.IntRange(0, 6), 1, 5));
        trashDecorSpawnList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.wool), new ItemToSpawn.IntRange(0, 16), 1, 5));
        trashDecorSpawnList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.cobblestone), 1, 5));
        trashDecorSpawnList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.planks), 1, 5));
        trashDecorSpawnList.add(new ItemToSpawn(Item.getItemFromBlock(BlocksZp.empty_bookshelf), 3, 1, 5));
        trashDecorSpawnList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.sandstone), 1, 5));
        trashDecorSpawnList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.stained_hardened_clay), new ItemToSpawn.IntRange(0, 16), 1, 5));

        trashItemsSpawnList.add(new ItemToSpawn(Items.item_frame, 1, 14));
        trashItemsSpawnList.add(new ItemToSpawn(Items.brick, 1, 14));
        trashItemsSpawnList.add(new ItemToSpawn(Items.wooden_door, 1, 14));
        trashItemsSpawnList.add(new ItemToSpawn(Items.dye, new ItemToSpawn.IntRange(0, 15), 1, 14));
        trashItemsSpawnList.add(new ItemToSpawn(Items.flower_pot, 1, 14));
        trashItemsSpawnList.add(new ItemToSpawn(Items.painting, 1, 12));
        trashItemsSpawnList.add(new ItemToSpawn(Items.poisonous_potato, 1, 2));
        trashItemsSpawnList.add(new ItemToSpawn(Items.paper, 1, 8));
        trashItemsSpawnList.add(new ItemToSpawn(Items.nether_wart, 4, 2));
        trashItemsSpawnList.add(new ItemToSpawn(Items.bone, 1, 3));
        trashItemsSpawnList.add(new ItemToSpawn(ItemsZp.fish_bones, 1, 3));

        trashBonusSpawnList.add(new ItemToSpawn(Items.experience_bottle, 1, 1));
        trashBonusSpawnList.add(new ItemToSpawn(ItemsZp.old_backpack, 1, 2));
        trashBonusSpawnList.add(new ItemToSpawn(ItemsZp.old_backpack2, 1, 1));
        trashBonusSpawnList.add(new ItemToSpawn(Items.flint, 1, 2));
        trashBonusSpawnList.add(new ItemToSpawn(Items.arrow, 1, 1));
        trashBonusSpawnList.add(new ItemToSpawn(ItemsZp.plate, 1, 4));
        trashBonusSpawnList.add(new ItemToSpawn(Items.gold_nugget, 1, 1));
        trashBonusSpawnList.add(new ItemToSpawn(ItemsZp.rotten_apple, 1, 5));
        trashBonusSpawnList.add(new ItemToSpawn(ItemsZp.cactus_water, 1, 2));
        trashBonusSpawnList.add(new ItemToSpawn(Item.getItemFromBlock(BlocksZp.wooden_stakes_stage2), 1, 1));
        trashBonusSpawnList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.cactus), 1, 5));
        trashBonusSpawnList.add(new ItemToSpawn(Items.netherbrick, 1, 5));
        trashBonusSpawnList.add(new ItemToSpawn(Items.potionitem, 1, 4));
        trashBonusSpawnList.add(new ItemToSpawn(Items.leather, 1, 4));
        trashBonusSpawnList.add(new ItemToSpawn(Items.stick, 1, 4));
        trashBonusSpawnList.add(new ItemToSpawn(Items.egg, 1, 4));
        trashBonusSpawnList.add(new ItemToSpawn(Items.feather, 1, 5));
        trashBonusSpawnList.add(new ItemToSpawn(Items.string, 1, 4));
        trashBonusSpawnList.add(new ItemToSpawn(Items.wheat, 1, 5));
        trashBonusSpawnList.add(new ItemToSpawn(Items.melon_seeds, 1, 4));
        trashBonusSpawnList.add(new ItemToSpawn(Items.wheat_seeds, 1, 4));
        trashBonusSpawnList.add(new ItemToSpawn(Items.carrot, 1, 1));
        trashBonusSpawnList.add(new ItemToSpawn(Items.potato, 1, 1));
        trashBonusSpawnList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.brick_block), 1, 5));
        trashBonusSpawnList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.rail), 1, 5));
        trashBonusSpawnList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.stained_glass_pane), new ItemToSpawn.IntRange(0, 16), 1, 5));
        trashBonusSpawnList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.stained_glass), new ItemToSpawn.IntRange(0, 16), 1, 5));
        trashBonusSpawnList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.glass_pane), 1, 5));
        trashBonusSpawnList.add(new ItemToSpawn(Item.getItemFromBlock(Blocks.glass), 1, 5));

        trashToolsSpawnList.add(new ItemToSpawn(Items.wooden_axe, 0.05f, 0.5f, 7));
        trashToolsSpawnList.add(new ItemToSpawn(Items.wooden_hoe, 0.05f, 0.5f, 7));
        trashToolsSpawnList.add(new ItemToSpawn(Items.wooden_shovel, 0.05f, 0.5f, 7));
        trashToolsSpawnList.add(new ItemToSpawn(Items.wooden_sword, 0.05f, 0.5f, 7));
        trashToolsSpawnList.add(new ItemToSpawn(Items.wooden_pickaxe, 0.05f, 0.5f, 7));
        trashToolsSpawnList.add(new ItemToSpawn(ItemsZp.bone_knife, 1.0f, 0.5f, 5));
        trashToolsSpawnList.add(new ItemToSpawn(Items.stone_axe, 0.05f, 0.5f, 5));
        trashToolsSpawnList.add(new ItemToSpawn(Items.stone_hoe, 0.05f, 0.5f, 5));
        trashToolsSpawnList.add(new ItemToSpawn(Items.stone_shovel, 0.05f, 0.5f, 5));
        trashToolsSpawnList.add(new ItemToSpawn(Items.stone_sword, 0.05f, 0.5f, 5));
        trashToolsSpawnList.add(new ItemToSpawn(Items.stone_pickaxe, 0.05f, 0.5f, 5));
        trashToolsSpawnList.add(new ItemToSpawn(Items.leather_chestplate, 0.05f, 0.5f, 5));
        trashToolsSpawnList.add(new ItemToSpawn(Items.leather_helmet, 0.05f, 0.5f, 5));
        trashToolsSpawnList.add(new ItemToSpawn(Items.leather_boots, 0.05f, 0.5f, 5));
        trashToolsSpawnList.add(new ItemToSpawn(Items.leather_leggings, 0.05f, 0.5f, 5));
        trashToolsSpawnList.add(new ItemToSpawn(Items.bow, 0.05f, 0.5f, 5));
        trashToolsSpawnList.add(new ItemToSpawn(Items.shears, 0.05f, 0.5f, 5));
        trashToolsSpawnList.add(new ItemToSpawn(Items.fishing_rod, 0.05f, 0.5f, 5));

        trashSet.add(trashLootManager1);
        trashSet.add(trashLootManager2);
        trashSet.add(trashLootManager3);
        trashSet.add(trashLootManager4);
        trashSet.add(trashLootManager5);
        trashSet.add(trashLootManager6);
    }

    public EnumLootGroups enumLootGroups;
    protected float spawnLootChance;
    protected boolean addTrash;
    protected boolean isFreeze;
    protected long timer;
    protected int type;

    public TileLootCase() {
    }

    public TileLootCase(EnumLootGroups enumLootGroups, boolean addTrash, int type) {
        this(enumLootGroups, 1.0f, addTrash, type);
    }

    public TileLootCase(EnumLootGroups enumLootGroups, float spawnLootChance, boolean addTrash, int type) {
        this.enumLootGroups = enumLootGroups;
        this.spawnLootChance = spawnLootChance * ConfigZp.lootMultiplier;
        this.addTrash = addTrash;
        this.type = type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void freezeChest() {
        this.isFreeze = true;
    }

    public void disableTrash() {
        this.addTrash = false;
    }

    public void setChance(float f1) {
        this.spawnLootChance = f1;
    }

    public String getInventoryName() {
        return "container.chest";
    }

    @Override
    public void openChest() {
        super.openChest();
        this.onOpen();
    }

    protected void onOpen() {
        if (!this.worldObj.isRemote) {
            int i = 36000;
            if (ConfigZp.longDays) {
                i -= 12000;
            }
            if (ConfigZp.longNights) {
                i -= 12000;
            }
            if (this.isFreeze) {
                this.timer = this.worldObj.getTotalWorldTime() + Main.rand.nextInt(4001) - 2000;
                this.isFreeze = false;
            } else if (this.timer == 0 || this.timer <= this.worldObj.getTotalWorldTime() - i) {
                this.reloadChest(1);
            }
        }
    }

    public void reloadChest(int spawnTimes) {
        if (this.enumLootGroups != null) {
            if (this.worldObj.getWorldInfo().getTerrainType() instanceof WorldTypeHardcoreZp) {
                spawnTimes *= 8;
            }
            this.clear();
            float difAf = this.worldObj.difficultySetting == EnumDifficulty.EASY ? 0.1f : this.worldObj.difficultySetting == EnumDifficulty.HARD ? -0.1f : 0.0f;
            for (int i = 0; i < spawnTimes; i++) {
                if (Main.rand.nextFloat() <= (this.spawnLootChance + difAf)) {
                    //TODO
                    LootSpawnManager lootSpawnManager = this.findManager(this.enumLootGroups.getLSP());
                    if (lootSpawnManager != null) {
                        lootSpawnManager.spawnRandomItem(this);
                    }
                }
            }
            if (this.addTrash) {
                TileLootCase.trashLootManager1.spawnRandomItem(this);
                for (int i = 0; i < Main.rand.nextInt(4); i++) {
                    LootSpawnManager lootSpawnManager = this.findManager(trashSet);
                    if (lootSpawnManager != null) {
                        lootSpawnManager.spawnRandomItem(this);
                    }
                }
            }
            this.timer = this.worldObj.getTotalWorldTime() + Main.rand.nextInt(2001) - 1000;
        } else {
            FMLLog.info("WRONG LOOTCASE");
        }
    }

    protected LootSpawnManager findManager(Set<LootSpawnManager> lootManagerSet) {
        List<LootSpawnManager> list = new ArrayList<>();
        for (LootSpawnManager lsp : lootManagerSet) {
            for (int i = 0; i < lsp.getSpawnChance(); i++) {
                list.add(lsp);
            }
        }
        if (list.size() == 100) {
            LootSpawnManager lsp = list.get(Main.rand.nextInt(100));
            if (lsp != null) {
                return lsp;
            } else {
                FMLLog.info("Wrong list: NULL " + this.enumLootGroups);
            }
        } else {
            FMLLog.info("Wrong list size, " + list.size() + "/100 " + this.enumLootGroups.name());
        }
        return null;
    }

    protected void clear() {
        for (int i = 0; i < this.getSizeInventory(); i++) {
            this.setInventorySlotContents(i, null);
        }
    }

    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        this.timer = tag.getLong("timer");
        this.addTrash = tag.getBoolean("addTrash");
        this.isFreeze = tag.getBoolean("isFreeze");
        this.spawnLootChance = tag.getFloat("spawnLootChance");
        this.enumLootGroups = EnumLootGroups.getLootGroupById(tag.getInteger("lootGroupId"));
    }

    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setLong("timer", this.timer);
        tag.setBoolean("addTrash", this.addTrash);
        tag.setBoolean("isFreeze", this.isFreeze);
        tag.setFloat("spawnLootChance", this.spawnLootChance);
        tag.setInteger("lootGroupId", this.enumLootGroups.getId());
    }
}
