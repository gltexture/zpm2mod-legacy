package ru.BouH_.recipe_master.data.container;

import net.minecraft.item.ItemStack;
import ru.BouH_.recipe_master.data.RecipeType;

import java.util.ArrayList;

public class FurnaceRecipeContainer extends ARecipeContainer {
    public FurnaceRecipeContainer(ArrayList<ItemStack> arrayList, int stackSize) {
        super(arrayList, stackSize);
    }

    public RecipeType getType() {
        return RecipeType.FURNACE;
    }
}
