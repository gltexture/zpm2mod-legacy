package ru.BouH_.items.medicine;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ru.BouH_.Main;

public class MedicineAntidoteSyringe extends MedicineZp {
    public MedicineAntidoteSyringe(String unlocalizedName, boolean isWolfFood, boolean createPoisonRecipe) {
        super(0, 0, isWolfFood, createPoisonRecipe);
        this.setUnlocalizedName(unlocalizedName);
    }

    @SuppressWarnings("unchecked")
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            player.removePotionEffect(2);
            player.removePotionEffect(4);
            player.removePotionEffect(9);
            player.removePotionEffect(17);
            player.removePotionEffect(19);
            player.removePotionEffect(15);
            player.removePotionEffect(18);
            player.removePotionEffect(20);
            player.removePotionEffect(31);
            player.getEntityData().setInteger("nausea", 0);
            player.getEntityData().setInteger("toxin", 0);
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
                target.removePotionEffect(2);
                target.removePotionEffect(4);
                target.removePotionEffect(9);
                target.removePotionEffect(17);
                target.removePotionEffect(19);
                target.removePotionEffect(15);
                target.removePotionEffect(18);
                target.removePotionEffect(20);
                target.removePotionEffect(31);
                target.getEntityData().setInteger("nausea", 0);
                target.getEntityData().setInteger("toxin", 0);
            }
        }
        return false;
    }
}

