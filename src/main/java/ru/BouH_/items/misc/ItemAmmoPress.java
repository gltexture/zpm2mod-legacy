package ru.BouH_.items.misc;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemAmmoPress extends Item {
    public ItemAmmoPress(String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);
        this.setContainerItem(this);
    }

    public ItemStack getContainerItem(ItemStack itemStack) {
        ItemStack stack = new ItemStack(this.getContainerItem());
        stack.setMetadata(itemStack.getMetadata() + 1);
        return stack;
    }

    public boolean doesContainerItemLeaveCraftingGrid(ItemStack p_77630_1_) {
        return false;
    }
}
