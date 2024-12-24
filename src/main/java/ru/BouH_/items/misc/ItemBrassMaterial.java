package ru.BouH_.items.misc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.init.ItemsZp;

public class ItemBrassMaterial extends Item {
    public ItemBrassMaterial(String unlocalizedName) {
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
            player.worldObj.playSoundAtEntity(player, Main.MODID + ":cloth", 1.0F, 0.9f + Main.rand.nextFloat() * 0.1f);
            --stack.stackSize;
            ItemStack stack1 = new ItemStack(Items.iron_ingot);
            ItemStack stack2 = new ItemStack(ItemsZp.copper_ingot);
            if (!player.inventory.addItemStackToInventory(stack1)) {
                player.entityDropItem(stack1, 1.0f);
            }
            if (!player.inventory.addItemStackToInventory(stack2)) {
                player.entityDropItem(stack2, 1.0f);
            }
        }
        return stack;
    }
}
