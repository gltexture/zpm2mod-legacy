package ru.BouH_.recipe_master.data.container;

import net.minecraft.item.ItemStack;
import ru.BouH_.recipe_master.data.RecipeType;

import java.util.ArrayList;

public class CommonRecipeContainer extends ARecipeContainer {
    private final int width;
    private final int height;

    public CommonRecipeContainer(ArrayList<ItemStack> arrayList, int stackSize, int width, int height) {
        super(arrayList, stackSize);
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public RecipeType getType() {
        return RecipeType.SHAPED;
    }
}
