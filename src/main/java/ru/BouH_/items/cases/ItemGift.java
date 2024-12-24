package ru.BouH_.items.cases;

import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.blocks.lootCases.EnumLootGroups;
import ru.BouH_.entity.zombie.EntityZombieWolf;
import ru.BouH_.init.ItemsZp;
import ru.BouH_.tiles.loot_spawn.LootSpawnManager;

import java.util.ArrayList;
import java.util.List;

public class ItemGift extends Item {
    public ItemGift(String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);

    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        return stack;
    }

    public int getMaxItemUseDuration(ItemStack stack) {
        return 10;
    }

    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.block;
    }

    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            player.getEntityData().setInteger("cdThrow", 20);
            player.worldObj.playSoundAtEntity(player, Main.MODID + ":cloth", 1.0F, 0.9f + Main.rand.nextFloat() * 0.1f);
            if (Main.rand.nextFloat() <= 0.1f) {
                ItemStack itemStack = this.getRandomNewYearItem();
                if (!player.inventory.addItemStackToInventory(itemStack)) {
                    player.entityDropItem(itemStack, 1.0f);
                }
            } else if (Main.rand.nextFloat() <= 0.25f) {
                this.startBadNewYearEvent(world, stack, player);
            } else if (Main.rand.nextFloat() <= 0.5f) {
                this.startRandomNewYearEvent(world, stack, player);
            } else {
                List<LootSpawnManager> list = new ArrayList<>(EnumLootGroups.getLootGroupById(Main.rand.nextInt(EnumLootGroups.values().length)).getLSP());
                ItemStack itemStack = list.get(Main.rand.nextInt(list.size())).getRandomItemStack();
                if (itemStack != null) {
                    if (!player.inventory.addItemStackToInventory(itemStack)) {
                        player.entityDropItem(itemStack, 1.0f);
                    }
                }
            }
        }
        return null;
    }

    protected ItemStack getRandomNewYearItem() {
        if (Main.rand.nextFloat() <= 0.03f) {
            return new ItemStack(ItemsZp.caramel_gun);
        } else if (Main.rand.nextFloat() <= 0.15f) {
            return new ItemStack(ItemsZp.caramel_sword);
        } else {
            return new ItemStack(ItemsZp._caramel, 32);
        }
    }

    protected void startRandomNewYearEvent(World world, ItemStack stack, EntityPlayer player) {
        if (Main.rand.nextFloat() <= 0.5f) {
            EntityFireworkRocket entityFireworkRocket1 = new EntityFireworkRocket(world, player.posX + 1, player.posY, player.posZ, null);
            world.spawnEntityInWorld(entityFireworkRocket1);
            EntityFireworkRocket entityFireworkRocket2 = new EntityFireworkRocket(world, player.posX - 1, player.posY, player.posZ, null);
            world.spawnEntityInWorld(entityFireworkRocket2);
            EntityFireworkRocket entityFireworkRocket3 = new EntityFireworkRocket(world, player.posX, player.posY, player.posZ + 1, null);
            world.spawnEntityInWorld(entityFireworkRocket3);
            EntityFireworkRocket entityFireworkRocket4 = new EntityFireworkRocket(world, player.posX, player.posY, player.posZ - 1, null);
            world.spawnEntityInWorld(entityFireworkRocket4);
        } else if (Main.rand.nextFloat() <= 0.75f) {
            player.addPotionEffect(new PotionEffect(1, 1200));
            player.addPotionEffect(new PotionEffect(16, 1200));
            player.addPotionEffect(new PotionEffect(5, 1200));
        } else {
            for (int i = 0; i < 8; i++) {
                EntitySnowman entitySnowman = new EntitySnowman(world);
                entitySnowman.setPosition(player.posX, player.posY, player.posZ);
                world.spawnEntityInWorld(entitySnowman);
            }
        }
    }

    protected void startBadNewYearEvent(World world, ItemStack stack, EntityPlayer player) {
        if (Main.rand.nextFloat() <= 0.5f) {
            player.setFire(10);
        } else if (Main.rand.nextFloat() <= 0.51f) {
            for (int i = 0; i < 2; i++) {
                EntityZombieWolf entityZombieWolf = new EntityZombieWolf(world);
                entityZombieWolf.setPosition(player.posX, player.posY, player.posZ);
                world.spawnEntityInWorld(entityZombieWolf);
            }
        } else if (Main.rand.nextFloat() <= 0.7f) {
            player.addPotionEffect(new PotionEffect(17, 1200));
        } else if (Main.rand.nextFloat() <= 0.8f) {
            player.addPotionEffect(new PotionEffect(15, 1200));
        } else if (Main.rand.nextFloat() <= 0.9f) {
            player.addPotionEffect(new PotionEffect(19, 1200));
        } else {
            player.addPotionEffect(new PotionEffect(9, 1200));
        }
    }
}
