package ru.BouH_.utils;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import ru.BouH_.Main;
import ru.BouH_.entity.ieep.PlayerMiscData;
import ru.BouH_.init.ItemsZp;
import ru.BouH_.skills.SkillManager;
import ru.BouH_.tiles.TileLootCase;
import ru.BouH_.tiles.loot_spawn.ItemToSpawn;
import ru.BouH_.tiles.loot_spawn.LootSpawnManager;
import ru.BouH_.world.biome.BiomeRad;

import java.util.HashSet;
import java.util.Set;

public class FishingUtils {
    public static final Set<ItemToSpawn> fishSpawnList = new HashSet<>();
    public static final LootSpawnManager fishLootManager = new LootSpawnManager(FishingUtils.fishSpawnList);

    static {
        fishSpawnList.add(new ItemToSpawn(Items.fish, new ItemToSpawn.IntRange(0), 1, 30));
        fishSpawnList.add(new ItemToSpawn(Items.fish, new ItemToSpawn.IntRange(1), 1, 25));
        fishSpawnList.add(new ItemToSpawn(Items.fish, new ItemToSpawn.IntRange(2), 1, 2));
        fishSpawnList.add(new ItemToSpawn(Items.fish, new ItemToSpawn.IntRange(3), 1, 13));
        fishSpawnList.add(new ItemToSpawn(ItemsZp.fish_zp, new ItemToSpawn.IntRange(0, 2), 1, 30));
    }

    public static ItemStack getStack(EntityFishHook entityFishHook, EntityPlayer entityPlayer) {
        int x = MathHelper.floor_double(entityFishHook.posX);
        int y = MathHelper.floor_double(entityFishHook.posY);
        int z = MathHelper.floor_double(entityFishHook.posZ);
        World world = entityFishHook.worldObj;
        boolean flag = world.getBiomeGenForCoords(x, z) == BiomeGenBase.swampland || world.getBiomeGenForCoords(x, z) == BiomeGenBase.river || world.getBiomeGenForCoords(x, z) == BiomeGenBase.frozenRiver || world.getBiomeGenForCoords(x, z) == BiomeGenBase.ocean || world.getBiomeGenForCoords(x, z) == BiomeGenBase.deepOcean || world.getBiomeGenForCoords(x, z) == BiomeGenBase.frozenOcean;
        boolean flag2 = world.getBlock(x - 1, y, z) != Blocks.water || world.getBlock(x, y, z - 1) != Blocks.water || world.getBlock(x + 1, y, z) != Blocks.water || world.getBlock(x, y, z + 1) != Blocks.water || world.getBlock(x, y - 1, z) != Blocks.water || world.getBlock(x, y - 2, z) != Blocks.water;

        if (flag && flag2) {
            return new ItemStack(Item.getItemFromBlock(Blocks.deadbush));
        }

        int i = EnchantmentHelper.func_151386_g(entityPlayer);
        int i1 = SkillManager.instance.getSkillPoints(SkillManager.instance.fisher, entityPlayer);

        float f1 = i * 0.1f;
        float f2 = f1 * 0.5f;
        float modifier = SkillManager.instance.getSkillBonus(SkillManager.instance.fisher, entityPlayer, 0.03f);

        float chance = Main.rand.nextFloat();
        float trashChance = 0.585f - f2 - modifier;
        float fishChance = (trashChance + 0.375f) - f2 - modifier;
        BiomeGenBase base = entityPlayer.worldObj.getBiomeGenForCoords(MathHelper.floor_double(entityFishHook.posX), MathHelper.floor_double(entityFishHook.posZ));
        PlayerMiscData playerMiscData = PlayerMiscData.getPlayerData(entityPlayer);

        if (chance <= trashChance) {
            LootSpawnManager trash = null;
            switch (Main.rand.nextInt(5)) {
                case 0: {
                    trash = TileLootCase.trashLootManager1;
                    break;
                }
                case 1: {
                    trash = TileLootCase.trashLootManager2;
                    break;
                }
                case 2: {
                    trash = TileLootCase.trashLootManager3;
                    break;
                }
                case 3: {
                    trash = TileLootCase.trashLootManager4;
                    break;
                }
                case 4: {
                    trash = TileLootCase.trashLootManager5;
                    break;
                }
            }
            playerMiscData.getSkillProgressProfiler().addProgress(SkillManager.instance.fisher, entityPlayer, 0.05f);
            return trash.getRandomItemStack();
        } else if (chance <= fishChance) {
            if (base instanceof BiomeRad) {
                return new ItemStack(Main.rand.nextFloat() <= 0.7f ? Item.getItemFromBlock(Blocks.deadbush) : ItemsZp.fish_bones);
            }
            playerMiscData.getSkillProgressProfiler().addProgress(SkillManager.instance.fisher, entityPlayer, 0.125f);
            if (base == BiomeGenBase.swampland) {
                if (Main.rand.nextFloat() <= 0.3f) {
                    return new ItemStack(ItemsZp.fish_zp, 1, 4);
                }
            } else if (base == BiomeGenBase.ocean || base == BiomeGenBase.deepOcean || base == BiomeGenBase.frozenOcean) {
                if (Main.rand.nextFloat() <= 0.3f) {
                    return new ItemStack(ItemsZp.fish_zp, 1, 3);
                }
            }
            return FishingUtils.fishLootManager.getRandomItemStack();
        } else {
            playerMiscData.getSkillProgressProfiler().addProgress(SkillManager.instance.fisher, entityPlayer, 0.25f);
            if (i1 >= 3) {
                if (Main.rand.nextFloat() <= 0.1f) {
                    return new ItemStack(ItemsZp.lockpick);
                }
            }
            if (i1 <= 3) {
                return new ItemStack(ItemsZp.fish_box);
            } else if (i1 <= 7) {
                return Main.rand.nextFloat() <= 0.3f ? new ItemStack(ItemsZp.fish_crate) : new ItemStack(ItemsZp.fish_box);
            } else {
                return Main.rand.nextFloat() <= 0.3f ? new ItemStack(ItemsZp.fish_iron_crate) : new ItemStack(ItemsZp.fish_crate);
            }
        }
    }
}
