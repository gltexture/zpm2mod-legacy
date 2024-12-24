package ru.BouH_.items.medicine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import ru.BouH_.Main;

public class MedicineCoke extends MedicineZp {
    public MedicineCoke(String unlocalizedName, boolean isWolfFood, boolean createPoisonRecipe) {
        super(0, 0, isWolfFood, createPoisonRecipe);
        this.setUnlocalizedName(unlocalizedName);
    }

    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player) {
        --stack.stackSize;
        player.worldObj.playSoundAtEntity(player, Main.MODID + ":cloth", 1.0F, 0.9f + Main.rand.nextFloat() * 0.1f);
        if (!world.isRemote) {
            player.addPotionEffect(new PotionEffect(9, 7200));
            player.addPotionEffect(new PotionEffect(1, 2400));
            player.addPotionEffect(new PotionEffect(5, 3600, 1));
            player.addPotionEffect(new PotionEffect(17, 600));
            player.addPotionEffect(new PotionEffect(31, 600));
        }
        return stack;
    }

    public int getMaxItemUseDuration(ItemStack stack) {
        return 32;
    }

    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.block;
    }
}

