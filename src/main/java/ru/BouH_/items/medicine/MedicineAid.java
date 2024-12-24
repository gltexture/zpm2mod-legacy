package ru.BouH_.items.medicine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import ru.BouH_.Main;

public class MedicineAid extends MedicineZp {
    public MedicineAid(String unlocalizedName, boolean isWolfFood, boolean createPoisonRecipe) {
        super(0, 0, isWolfFood, createPoisonRecipe);
        this.setUnlocalizedName(unlocalizedName);
    }

    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player) {
        --stack.stackSize;
        player.worldObj.playSoundAtEntity(player, Main.MODID + ":cloth", 1.0F, 0.9f + Main.rand.nextFloat() * 0.1f);
        if (!world.isRemote) {
            player.removePotionEffect(28);
            player.heal(50);
            player.addPotionEffect(new PotionEffect(10, 200));
            player.getEntityData().setInteger("nausea", 0);
            player.getEntityData().setInteger("toxin", 0);
        }
        return stack;
    }

    public int getMaxItemUseDuration(ItemStack stack) {
        return 86;
    }

    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.block;
    }
}

