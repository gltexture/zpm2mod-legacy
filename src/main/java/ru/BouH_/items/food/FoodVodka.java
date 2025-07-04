package ru.BouH_.items.food;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import ru.BouH_.achievements.AchievementManager;
import ru.BouH_.entity.ieep.Thirst;

public class FoodVodka extends FoodZp {

    public FoodVodka(String unlocalizedName, int amount, float saturation, boolean isWolfFood, boolean createPoisonRecipe) {
        super(amount, saturation, isWolfFood, FoodType.DRINK, createPoisonRecipe);
        this.setUnlocalizedName(unlocalizedName);

    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player) {
        --stack.stackSize;
        if (!world.isRemote) {
            AchievementManager.instance.triggerAchievement(AchievementManager.instance.alcohol, player);
            Thirst.getThirst(player).addThirst(getHealAmount(stack), getSaturationModifier(stack));
            player.addPotionEffect(new PotionEffect(5, 300));
            player.addPotionEffect(new PotionEffect(9, 1800));
            player.addPotionEffect(new PotionEffect(33, 900));
            player.addPotionEffect(new PotionEffect(29, 900));
            player.getEntityData().setInteger("radiation", Math.max(player.getEntityData().getInteger("radiation") - 70, 0));
            player.getEntityData().setInteger("nausea", (player.getEntityData().getInteger("nausea") + 40));
        }
        return stack;
    }
}

