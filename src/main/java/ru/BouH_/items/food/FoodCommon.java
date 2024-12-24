package ru.BouH_.items.food;

public class FoodCommon extends FoodZp {
    public FoodCommon(String unlocalizedName, int amount, float saturation, boolean isWolfFood, boolean createPoisonRecipe) {
        super(amount, saturation, isWolfFood, FoodType.FOOD, createPoisonRecipe);
        this.setUnlocalizedName(unlocalizedName);
    }
}

