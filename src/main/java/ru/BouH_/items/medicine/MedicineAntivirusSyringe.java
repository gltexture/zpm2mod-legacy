package ru.BouH_.items.medicine;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.entity.zombie.EntityZombieCitizen;

public class MedicineAntivirusSyringe extends MedicineZp {
    public MedicineAntivirusSyringe(String unlocalizedName, boolean isWolfFood, boolean createPoisonRecipe) {
        super(0, 0, isWolfFood, createPoisonRecipe);
        this.setUnlocalizedName(unlocalizedName);
    }

    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player) {
        --stack.stackSize;
        player.worldObj.playSoundAtEntity(player, Main.MODID + ":syringe", 1.0F, 0.9f + Main.rand.nextFloat() * 0.1f);
        if (!world.isRemote) {
            player.removePotionEffect(26);
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
                if (target instanceof EntityZombieCitizen) {
                    EntityZombieCitizen entityZombieCitizen = (EntityZombieCitizen) target;
                    if (entityZombieCitizen.isVillager() && entityZombieCitizen.isPotionActive(18)) {
                        entityZombieCitizen.startConversion(24000);
                    }
                }
                target.removePotionEffect(26);
            }
        }
        return false;
    }
}

