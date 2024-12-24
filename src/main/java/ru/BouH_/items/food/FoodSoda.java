package ru.BouH_.items.food;

import net.minecraft.item.ItemStack;

public class FoodSoda extends FoodZp {
    public FoodSoda(String unlocalizedName, int amount, float saturation, boolean isWolfFood, boolean createPoisonRecipe) {
        super(amount, saturation, isWolfFood, FoodType.DRINK, createPoisonRecipe);
        this.setUnlocalizedName(unlocalizedName);
    }

    public int getMaxItemUseDuration(ItemStack p_77626_1_) {
        return 25;
    }
}

