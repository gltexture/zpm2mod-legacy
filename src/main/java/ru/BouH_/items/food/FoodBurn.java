package ru.BouH_.items.food;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import ru.BouH_.entity.ieep.Thirst;

public class FoodBurn extends FoodZp {

    public FoodBurn(String unlocalizedName, int amount, float saturation, boolean isWolfFood, boolean createPoisonRecipe) {
        super(amount, saturation, isWolfFood, FoodType.DRINK, createPoisonRecipe);
        this.setUnlocalizedName(unlocalizedName);

    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player) {
        --stack.stackSize;
        if (!world.isRemote) {
            Thirst.getThirst(player).addThirst(getHealAmount(stack), getSaturationModifier(stack));
            player.addPotionEffect(new PotionEffect(1, 240));
            player.addPotionEffect(new PotionEffect(31, 100));
        }
        return stack;
    }
}

