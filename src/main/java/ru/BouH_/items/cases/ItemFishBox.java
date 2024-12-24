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

public class ItemFishBox extends Item {
    public ItemFishBox(String unlocalizedName) {
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
            List<LootSpawnManager> list = new ArrayList<>(Main.rand.nextBoolean() ? EnumLootGroups.Tier2Village.getLSP() : EnumLootGroups.Tier1City.getLSP());
            ItemStack itemStack = list.get(Main.rand.nextInt(list.size())).getRandomItemStack();
            if (!player.inventory.addItemStackToInventory(itemStack)) {
                player.entityDropItem(itemStack, 1.0f);
            }
        }
        return null;
    }
}