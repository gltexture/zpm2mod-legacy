package ru.BouH_.items.medicine;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.entity.zombie.AZombieBase;

public class MedicineDeathSyringe extends MedicineZp {

    public MedicineDeathSyringe(String unlocalizedName, boolean isWolfFood, boolean createPoisonRecipe) {
        super(0, 0, isWolfFood, createPoisonRecipe);
        this.setUnlocalizedName(unlocalizedName);
    }

    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player) {
        --stack.stackSize;
        player.worldObj.playSoundAtEntity(player, Main.MODID + ":syringe", 1.0F, 0.9f + Main.rand.nextFloat() * 0.1f);
        if (!world.isRemote) {
            player.addPotionEffect(new PotionEffect(19, 900, 1));
            player.addPotionEffect(new PotionEffect(20, 400));
            player.addPotionEffect(new PotionEffect(9, 2400));
        }
        return stack;
    }

    public int getMaxItemUseDuration(ItemStack stack) {
        return 32;
    }

    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.block;
    }

    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        if (target.isEntityAlive()) {
            --stack.stackSize;
            target.worldObj.playSoundAtEntity(target, Main.MODID + ":syringe", 1.0F, 0.9f + Main.rand.nextFloat() * 0.1f);
            if (!target.worldObj.isRemote) {
                if (target instanceof AZombieBase) {
                    target.addPotionEffect(new PotionEffect(20, 1200, 2));
                } else {
                    target.addPotionEffect(new PotionEffect(19, 900, 2));
                    target.addPotionEffect(new PotionEffect(20, 700));
                    target.addPotionEffect(new PotionEffect(9, 2400));
                }
            }
        }
        return false;
    }
}

