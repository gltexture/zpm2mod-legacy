package ru.BouH_.items.food;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.entity.ieep.Hunger;

public class FoodFish extends FoodZp {

    public FoodFish(String unlocalizedName, int amount, float saturation, boolean isWolfFood, boolean createPoisonRecipe) {
        super(amount, saturation, isWolfFood, FoodType.FOOD, createPoisonRecipe);
        this.setUnlocalizedName(unlocalizedName);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player) {
        --stack.stackSize;
        world.playSoundAtEntity(player, "random.burp", 0.5F, Main.rand.nextFloat() * 0.1F + 0.9F);
        if (!world.isRemote) {
            Hunger.getHunger(player).addHunger(this.getHealAmount(stack), this.getSaturationModifier(stack));
            if (Main.rand.nextInt(10) == 0) {
                player.addPotionEffect(new PotionEffect(19, 200));
            }
        }
        return stack;
    }
}

