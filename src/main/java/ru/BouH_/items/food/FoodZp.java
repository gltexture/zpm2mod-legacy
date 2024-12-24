package ru.BouH_.items.food;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.entity.ieep.Hunger;
import ru.BouH_.entity.ieep.Thirst;
import ru.BouH_.zpm_recipes.PoisonousFoodRecipe;

public abstract class FoodZp extends ItemFood {
    private final FoodType foodType;

    public FoodZp(int p_i45339_1_, float p_i45339_2_, boolean p_i45339_3_, FoodType foodType, boolean createPoisonRecipe) {
        super(p_i45339_1_, p_i45339_2_, p_i45339_3_);
        if (createPoisonRecipe) {
            PoisonousFoodRecipe.addPoisonRecipe(CraftingManager.getInstance(), new ItemStack(this));
        }
        this.foodType = foodType;
    }

    public FoodType getFoodType() {
        return this.foodType;
    }

    public EnumAction getItemUseAction(ItemStack stack) {
        return this.getFoodType() == FoodType.DRINK ? EnumAction.drink : EnumAction.eat;
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            --stack.stackSize;
            world.playSoundAtEntity(player, "random.burp", 0.5F, Main.rand.nextFloat() * 0.1F + 0.9F);
            if (this.getItemUseAction(stack) == EnumAction.drink) {
                Thirst.getThirst(player).addThirst(getHealAmount(stack), getSaturationModifier(stack));
            } else {
                Hunger.getHunger(player).addHunger(this.getHealAmount(stack), this.getSaturationModifier(stack));
            }
        }
        return stack;
    }

    public enum FoodType {
        DRINK,
        FOOD
    }
}
