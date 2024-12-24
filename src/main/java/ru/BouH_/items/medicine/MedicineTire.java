package ru.BouH_.items.medicine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.proxy.CommonProxy;

public class MedicineTire extends MedicineZp {

    public MedicineTire(String unlocalizedName, boolean isWolfFood, boolean createPoisonRecipe) {
        super(0, 0, isWolfFood, createPoisonRecipe);
        this.setUnlocalizedName(unlocalizedName);
    }

    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player) {
        --stack.stackSize;
        player.worldObj.playSoundAtEntity(player, Main.MODID + ":cloth", 1.0F, 0.9f + Main.rand.nextFloat() * 0.1f);
        if (!world.isRemote) {
            if (!player.isPotionActive(32) && player.isPotionActive(27)) {
                if (player.getActivePotionEffect(CommonProxy.brokenLeg).getAmplifier() == 0) {
                    player.addPotionEffect(new PotionEffect(32, (int) (player.getActivePotionEffect(CommonProxy.brokenLeg).getDuration() / 2.0f)));
                    player.removePotionEffect(27);
                }
            }
        }
        return stack;
    }

    public int getMaxItemUseDuration(ItemStack stack) {
        return 128;
    }

    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.block;
    }
}

