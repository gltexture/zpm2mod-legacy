package ru.BouH_.recipe_master.data.container;

import net.minecraft.item.ItemStack;
import ru.BouH_.recipe_master.data.RecipeType;

import java.util.ArrayList;

public class ShapelessRecipeContainer extends ARecipeContainer {
    public ShapelessRecipeContainer(ArrayList<ItemStack> arrayList, int stackSize) {
        super(arrayList, stackSize);
    }

    public RecipeType getType() {
        return RecipeType.SHAPELESS;
    }
}
