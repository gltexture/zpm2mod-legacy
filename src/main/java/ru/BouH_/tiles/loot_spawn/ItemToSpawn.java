package ru.BouH_.tiles.loot_spawn;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import org.jetbrains.annotations.NotNull;
import ru.BouH_.Main;
import ru.BouH_.items.gun.base.AGunBase;
import ru.BouH_.items.gun.modules.base.EnumModule;

import java.util.Random;

public class ItemToSpawn {
    private final Random rand = Main.rand;
    private final Item item;
    private final float maxDurability;
    private final float minDurability;
    private final int spawnChance;
    private final float eachStackSizeChanceSpawnStep;
    private final float eachDurabilityStep;
    private final int maxStackSize;
    private final IntRange metaRange;

    public ItemToSpawn(Item item, IntRange metaRange, float minDurability, float maxDurability, float eachDurabilityStep, int spawnRange) {
        this(item, metaRange, minDurability, maxDurability, eachDurabilityStep, 1, 0.0f, spawnRange);
    }

    public ItemToSpawn(Item item, IntRange metaRange, int maxStackSize, float eachStackSizeChanceSpawnStep, int spawnRange) {
        this(item, metaRange, 0, 0, 0, maxStackSize, eachStackSizeChanceSpawnStep, spawnRange);
    }

    public ItemToSpawn(Item item, int maxStackSize, float eachStackSizeChanceSpawnStep, int spawnChance) {
        this(item, null, 0, 0, 0, maxStackSize, eachStackSizeChanceSpawnStep, spawnChance);
    }

    public ItemToSpawn(Item item, int maxStackSize, int spawnChance) {
        this(item, null, 0, 0, 0, maxStackSize, 0, spawnChance);
    }

    public ItemToSpawn(Item item, IntRange metaRange, int maxStackSize, int spawnChance) {
        this(item, metaRange, 0, 0, 0, maxStackSize, 0, spawnChance);
    }

    public ItemToSpawn(Item item, float maxDurability, float eachDurabilityStep, int spawnChance) {
        this(item, null, 0, maxDurability, eachDurabilityStep, 1, 0.0f, spawnChance);
    }

    public ItemToSpawn(Item item, float minDurability, float maxDurability, float eachDurabilityStep, int spawnChance) {
        this(item, null, minDurability, maxDurability, eachDurabilityStep, 1, 0.0f, spawnChance);
    }

    public ItemToSpawn(Item item, float minDurability, float maxDurability, float eachDurabilityStep, int maxStackSize, float eachStackSizeChanceSpawnStep, int spawnChance) {
        this(item, null, minDurability, maxDurability, eachDurabilityStep, maxStackSize, eachStackSizeChanceSpawnStep, spawnChance);
    }

    public ItemToSpawn(@NotNull Item item, IntRange metaRange, float minDurability, float maxDurability, float eachDurabilityStep, int maxStackSize, float eachStackSizeChanceSpawnStep, int spawnChance) {
        this.item = item;
        this.metaRange = metaRange;
        this.minDurability = minDurability;
        this.maxDurability = maxDurability;
        this.maxStackSize = maxStackSize;
        this.spawnChance = spawnChance;
        this.eachStackSizeChanceSpawnStep = eachStackSizeChanceSpawnStep;
        this.eachDurabilityStep = eachDurabilityStep;
    }

    public ItemStack getItemStack(@NotNull Item item) {
        ItemStack stack = new ItemStack(item, this.getCountToSpawn());
        if (this.getMetaRange() != null && stack.getHasSubtypes()) {
            int i1 = this.getMetaRange().getStart();
            int i2 = this.getMetaRange().getEnd();
            stack.setMetadata(i1 + Main.rand.nextInt(i2 - i1 + 1));
        } else {
            if (stack.isItemStackDamageable()) {
                stack.setMetadata(this.getDurabilityToSpawn(stack));
            }
        }
        if (stack.getItem() instanceof AGunBase) {
            this.addAmmoInGun(stack);
        }
        return stack;
    }

    private void addAmmoInGun(ItemStack stack) {
        AGunBase aGunBase = (AGunBase) stack.getItem();
        int ammo = 0;
        float chance = Main.rand.nextFloat();
        float f1 = Math.min(aGunBase.getMaxAmmo() / 30.0f, 0.99f);
        int maxInt = aGunBase.getMaxAmmo();
        while (chance <= f1 && ammo < maxInt) {
            ammo = Math.min(ammo + 1, maxInt);
            f1 *= Math.max(f1, 0.9f);
        }
        if (!stack.hasTagCompound()) {
            stack.setTagInfo(Main.MODID, new NBTTagCompound());
            stack.getTagCompound().getCompoundTag(Main.MODID).setInteger(EnumModule.SCOPE.toString(), -1);
            stack.getTagCompound().getCompoundTag(Main.MODID).setInteger(EnumModule.BARREL.toString(), -1);
            stack.getTagCompound().getCompoundTag(Main.MODID).setInteger(EnumModule.UNDERBARREL.toString(), -1);
        }
        stack.getTagCompound().getCompoundTag(Main.MODID).setInteger("fireMode", aGunBase.getStandardFireMode().getId());
        stack.getTagCompound().getCompoundTag(Main.MODID).setInteger("ammo", ammo);
    }

    public IntRange getMetaRange() {
        return this.metaRange;
    }

    private int getCountToSpawn() {
        int count = 1;
        if (this.getMaxStackSize() > 1) {
            float randomFloat = this.rand.nextFloat();
            float f1 = this.getEachStackSizeChanceSpawnStep();
            while (randomFloat <= f1 && count < this.getMaxStackSize()) {
                count++;
                f1 *= this.getEachStackSizeChanceSpawnStep();
            }
        }
        return count;
    }

    private int getDurabilityToSpawn(@NotNull ItemStack stack) {
        int durability = stack.getMaxDurability();
        float randomFloat = this.rand.nextFloat();
        float f1 = this.getEachDurabilityStep();
        float f2 = this.getMinDurability();
        while (randomFloat <= f1 && f2 < this.getMaxDurability()) {
            durability -= stack.getMaxDurability() / stack.getMaxDurability();
            f2 = Math.min(f2 + 0.1f + Main.rand.nextFloat() * 0.01f, this.getMaxDurability());
            f1 *= this.getEachDurabilityStep();
        }
        return (int) (durability * (1.0f - f2));
    }

    public Item getItem() {
        return this.item;
    }

    public float getEachDurabilityStep() {
        return Math.min(this.eachDurabilityStep, 1.0f);
    }

    public float getEachStackSizeChanceSpawnStep() {
        return Math.min(this.eachStackSizeChanceSpawnStep, 1.0f);
    }

    public float getMaxDurability() {
        return this.maxDurability;
    }

    public float getMinDurability() {
        return this.minDurability;
    }

    public int getMaxStackSize() {
        return this.maxStackSize;
    }

    public int getSpawnChance() {
        return this.spawnChance;
    }

    public static class IntRange {
        private final int start;
        private final int end;

        public IntRange(int start, int end) {
            this.start = start;
            this.end = end;
        }

        public IntRange(int meta) {
            this.start = meta;
            this.end = meta;
        }

        public int getStart() {
            return this.start;
        }

        public int getEnd() {
            return this.end;
        }
    }
}
