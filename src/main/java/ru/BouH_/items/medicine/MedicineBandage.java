package ru.BouH_.items.medicine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.achievements.AchievementManager;
import ru.BouH_.proxy.CommonProxy;

public class MedicineBandage extends MedicineZp {
    public MedicineBandage(String unlocalizedName, boolean isWolfFood, boolean createPoisonRecipe) {
        super(0, 0, isWolfFood, createPoisonRecipe);
        this.setUnlocalizedName(unlocalizedName);
    }

    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player) {
        if (stack.getItem().isDamageable()) {
            if (!world.isRemote) {
                stack.damageItem(1, player);
            }
        } else {
            --stack.stackSize;
        }
        player.worldObj.playSoundAtEntity(player, Main.MODID + ":cloth", 1.0F, 0.9f + Main.rand.nextFloat() * 0.1f);
        if (!world.isRemote && player.isPotionActive(28)) {
            if (player.getActivePotionEffect(CommonProxy.bleeding).getAmplifier() == 0) {
                player.removePotionEffect(28);
                AchievementManager.instance.triggerAchievement(AchievementManager.instance.doctor, player);
            } else {
                int amplifier = player.getActivePotionEffect(CommonProxy.bleeding).getAmplifier();
                player.removePotionEffect(28);
                player.addPotionEffect(new PotionEffect(28, 1200, amplifier - 1));
            }
        }

        return stack;
    }

    public int getMaxItemUseDuration(ItemStack stack) {
        return 42;
    }

    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.block;
    }
}

