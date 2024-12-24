package ru.BouH_.items.food;

public class FoodDrink extends FoodZp {
    public FoodDrink(String unlocalizedName, int amount, float saturation, boolean isWolfFood, boolean createPoisonRecipe) {
        super(amount, saturation, isWolfFood, FoodType.DRINK, createPoisonRecipe);
        this.setUnlocalizedName(unlocalizedName);
    }
}

