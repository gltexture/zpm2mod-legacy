package ru.BouH_.items.cases;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.blocks.lootCases.EnumLootGroups;
import ru.BouH_.tiles.loot_spawn.LootSpawnManager;

import java.util.ArrayList;
import java.util.List;

public class ItemBagRandom extends Item {
    public ItemBagRandom(String unlocalizedName) {
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
        ItemStack startStack = null;
        if (!world.isRemote) {
            player.getEntityData().setInteger("cdThrow", 20);
            player.worldObj.playSoundAtEntity(player, Main.MODID + ":cloth", 1.0F, 0.9f + Main.rand.nextFloat() * 0.1f);
            for (int i = 0; i < 36; i++) {
                List<LootSpawnManager> list = new ArrayList<>(EnumLootGroups.getLootGroupById(Main.rand.nextInt(EnumLootGroups.values().length)).getLSP());
                ItemStack itemStack = list.get(Main.rand.nextInt(list.size())).getRandomItemStack();
                if (player.inventory.currentItem == i) {
                    startStack = itemStack;
                } else {
                    player.inventory.setInventorySlotContents(i, itemStack);
                }
            }
        }
        return startStack;
    }
}
