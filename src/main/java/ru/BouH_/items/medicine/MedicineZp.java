package ru.BouH_.items.medicine;

import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import ru.BouH_.zpm_recipes.PoisonousFoodRecipe;

public abstract class MedicineZp extends ItemFood {
    public MedicineZp(int p_i45339_1_, float p_i45339_2_, boolean p_i45339_3_, boolean createPoisonRecipe) {
        super(p_i45339_1_, p_i45339_2_, p_i45339_3_);
        if (createPoisonRecipe) {
            PoisonousFoodRecipe.addPoisonRecipe(CraftingManager.getInstance(), new ItemStack(this));
        }
    }
}
