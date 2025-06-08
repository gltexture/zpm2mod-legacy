package ru.BouH_.items.cases;

import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.passive.EntityBat;
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

public class ItemHalloweenBag extends Item {
    public ItemHalloweenBag(String unlocalizedName) {
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
                ItemStack itemStack = this.getRandomHalloweenItem();
                if (!player.inventory.addItemStackToInventory(itemStack)) {
                    player.entityDropItem(itemStack, 1.0f);
                }
            } else if (Main.rand.nextFloat() <= 0.25f) {
                this.startBadHalloweenEvent(world, stack, player);
            } else if (Main.rand.nextFloat() <= 0.5f) {
                this.startRandomHalloweenEvent(world, stack, player);
            } else {
                for (int i = 0; i < 3; i++) {
                    List<LootSpawnManager> list = new ArrayList<>(EnumLootGroups.getLootGroupById(Main.rand.nextInt(EnumLootGroups.values().length)).getLSP());
                    ItemStack itemStack = list.get(Main.rand.nextInt(list.size())).getRandomItemStack();
                    if (itemStack != null) {
                        if (!player.inventory.addItemStackToInventory(itemStack)) {
                            player.entityDropItem(itemStack, 1.0f);
                        }
                    }
                }
            }
        }
        return null;
    }

    protected ItemStack getRandomHalloweenItem() {
        if (Main.rand.nextFloat() <= 0.03f) {
            return new ItemStack(ItemsZp.scare_gun);
        } else if (Main.rand.nextFloat() <= 0.15f) {
            return new ItemStack(ItemsZp.scare_sword);
        } else {
            return new ItemStack(ItemsZp._scare, 32);
        }
    }

    protected void startRandomHalloweenEvent(World world, ItemStack stack, EntityPlayer player) {
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
            player.addPotionEffect(new PotionEffect(3, 1200));
            player.addPotionEffect(new PotionEffect(10, 1200));
            player.addPotionEffect(new PotionEffect(5, 1200));
        } else {
            for (int i = 0; i < 8; i++) {
                EntityBat entityBat = new EntityBat(world);
                entityBat.setPosition(player.posX, player.posY, player.posZ);
                world.spawnEntityInWorld(entityBat);
            }
        }
    }

    protected void startBadHalloweenEvent(World world, ItemStack stack, EntityPlayer player) {
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
            player.addPotionEffect(new PotionEffect(17, 1200));
        } else if (Main.rand.nextFloat() <= 0.9f) {
            player.addPotionEffect(new PotionEffect(18, 1200));
        } else {
            player.addPotionEffect(new PotionEffect(2, 1200));
        }
    }
}
