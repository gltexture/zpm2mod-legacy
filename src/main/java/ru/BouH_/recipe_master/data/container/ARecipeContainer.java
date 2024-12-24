package ru.BouH_.recipe_master.data.container;

import net.minecraft.item.ItemStack;
import ru.BouH_.recipe_master.data.RecipeType;

import java.util.ArrayList;

public abstract class ARecipeContainer {
    private final ArrayList<ItemStack> arrayList;
    private final int stackSize;

    public ARecipeContainer(ArrayList<ItemStack> arrayList, int stackSize) {
        this.arrayList = arrayList;
        this.stackSize = stackSize;
    }

    public int getStackSize() {
        return this.stackSize;
    }

    public abstract RecipeType getType();

    public ArrayList<ItemStack> getArrayList() {
        return this.arrayList;
    }
}
