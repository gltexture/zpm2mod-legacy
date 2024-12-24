package ru.BouH_.items.food;

import net.minecraft.item.ItemStack;

public class FoodEgg extends FoodZp {
    public FoodEgg(String unlocalizedName, int amount, float saturation, boolean isWolfFood, boolean createPoisonRecipe) {
        super(amount, saturation, isWolfFood, FoodType.FOOD, createPoisonRecipe);
        this.setUnlocalizedName(unlocalizedName);
    }

    public int getMaxItemUseDuration(ItemStack p_77626_1_) {
        return 16;
    }
}

