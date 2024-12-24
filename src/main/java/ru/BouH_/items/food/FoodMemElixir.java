package ru.BouH_.items.food;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import ru.BouH_.achievements.AchievementManager;
import ru.BouH_.entity.ieep.PlayerMiscData;
import ru.BouH_.entity.ieep.Thirst;

public class FoodMemElixir extends FoodZp {

    public FoodMemElixir(String unlocalizedName, int amount, float saturation, boolean isWolfFood, boolean createPoisonRecipe) {
        super(amount, saturation, isWolfFood, FoodType.DRINK, createPoisonRecipe);
        this.setUnlocalizedName(unlocalizedName);

    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player) {
        --stack.stackSize;
        if (!world.isRemote) {
            player.addPotionEffect(new PotionEffect(9, 6000));
            Thirst.getThirst(player).addThirst(getHealAmount(stack), getSaturationModifier(stack));
            PlayerMiscData.getPlayerData(player).setPlayerDeaths(0);
            PlayerMiscData.getPlayerData(player).setPlayersKilled(0);
            PlayerMiscData.getPlayerData(player).setZombiesKilled(0);
            PlayerMiscData.getPlayerData(player).packet();
            player.experience = 0f;
            player.experienceLevel = 0;
            player.experienceTotal = 0;
        }
        return stack;
    }
}

