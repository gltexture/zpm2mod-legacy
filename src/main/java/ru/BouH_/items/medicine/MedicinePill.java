package ru.BouH_.items.medicine;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import ru.BouH_.Main;

public class MedicinePill extends MedicineZp {
    private final PotionEffect[] effectData;

    public MedicinePill(String unlocalizedName, PotionEffect[] effectData, boolean isWolfFood, boolean createPoisonRecipe) {
        super(0, 0, isWolfFood, createPoisonRecipe);
        this.setUnlocalizedName(unlocalizedName);
        this.effectData = effectData;
    }

    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.eat;
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player) {
        --stack.stackSize;
        world.playSoundAtEntity(player, "random.burp", 0.5F, Main.rand.nextFloat() * 0.1F + 0.9F);
        if (!world.isRemote) {
            for (PotionEffect effectData : this.effectData) {
                if (effectData.getDuration() == -1) {
                    player.removePotionEffect(effectData.getPotionID());
                } else {
                    player.addPotionEffect(new PotionEffect(effectData.getPotionID(), effectData.getDuration(), effectData.getAmplifier()));
                }
            }
        }
        return stack;
    }
}

