package ru.BouH_.items.food;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import ru.BouH_.entity.ieep.Thirst;

public class FoodDrinkStimulator extends FoodZp {

    public FoodDrinkStimulator(String unlocalizedName, int amount, float saturation, boolean isWolfFood, boolean createPoisonRecipe) {
        super(amount, saturation, isWolfFood, FoodType.DRINK, createPoisonRecipe);
        this.setUnlocalizedName(unlocalizedName);

    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            Thirst.getThirst(player).addThirst(getHealAmount(stack), getSaturationModifier(stack));
            player.addPotionEffect(new PotionEffect(1, 7200));
            player.addPotionEffect(new PotionEffect(3, 7200));
            player.addPotionEffect(new PotionEffect(5, 1200));
        }
        return new ItemStack(Items.glass_bottle);
    }
}

