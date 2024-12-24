package ru.BouH_.items.medicine;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import ru.BouH_.Main;

public class MedicineSyringe extends MedicineZp {
    private final PotionEffect[] effectData;

    public MedicineSyringe(String unlocalizedName, PotionEffect[] effectData, boolean isWolfFood, boolean createPoisonRecipe) {
        super(0, 0, isWolfFood, createPoisonRecipe);
        this.setUnlocalizedName(unlocalizedName);
        this.effectData = effectData;
    }

    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            for (PotionEffect effectData : this.effectData) {
                if (effectData.getDuration() == -1) {
                    player.removePotionEffect(effectData.getPotionID());
                } else {
                    player.addPotionEffect(new PotionEffect(effectData.getPotionID(), effectData.getDuration(), effectData.getAmplifier()));
                }
            }
        }
        --stack.stackSize;
        player.worldObj.playSoundAtEntity(player, Main.MODID + ":syringe", 1.0F, 0.9f + Main.rand.nextFloat() * 0.1f);
        return stack;
    }

    public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
        return false;
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
                for (PotionEffect effectData : this.effectData) {
                    if (effectData.getDuration() == -1) {
                        target.removePotionEffect(effectData.getPotionID());
                    } else {
                        target.addPotionEffect(new PotionEffect(effectData.getPotionID(), effectData.getDuration(), effectData.getAmplifier()));
                    }
                }
            }
        }
        return false;
    }
}

