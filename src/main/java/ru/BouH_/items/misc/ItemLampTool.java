package ru.BouH_.items.misc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemLampTool extends Item {
    public ItemLampTool(String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);
        this.setContainerItem(this);
    }

    public ItemStack getContainerItem(ItemStack itemStack) {
        ItemStack stack = new ItemStack(this.getContainerItem());
        stack.setMetadata(itemStack.getMetadata() + 1);
        return stack;
    }

    public ItemStack onItemUseFinish(ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_) {
        ItemStack stack = new ItemStack(this.getContainerItem());
        stack.setMetadata(p_77654_1_.getMetadata() + 1);
        return stack;
    }

    public boolean doesContainerItemLeaveCraftingGrid(ItemStack p_77630_1_) {
        return false;
    }
}
