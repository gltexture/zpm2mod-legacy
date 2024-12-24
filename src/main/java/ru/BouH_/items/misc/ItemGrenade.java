package ru.BouH_.items.misc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ru.BouH_.achievements.AchievementManager;
import ru.BouH_.entity.projectile.EntityThrowableZPBase;
import ru.BouH_.init.ItemsZp;

import java.lang.reflect.InvocationTargetException;

public class ItemGrenade extends Item {
    private final Class<? extends Entity> throwable;
    private final float speed;
    private final float inaccuracy;

    public ItemGrenade(String unlocalizedName, Class<? extends Entity> throwable, float speed, float inaccuracy) {
        this.setUnlocalizedName(unlocalizedName);
        this.throwable = throwable;
        this.speed = speed;
        this.inaccuracy = inaccuracy;
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        return stack;
    }

    public int getMaxItemUseDuration(ItemStack stack) {
        return 20;
    }

    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.block;
    }

    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
            --stack.stackSize;
            if (this == ItemsZp.frag_grenade) {
                AchievementManager.instance.triggerAchievement(AchievementManager.instance.grenade, player);
            }
            world.spawnEntityInWorld(this.getEntity(world, player));
        }
        return stack;
    }

    @SuppressWarnings("unckecked")
    protected EntityThrowableZPBase getEntity(World world, EntityPlayer player) {
        try {
            return (EntityThrowableZPBase) this.throwable.getConstructor(World.class, EntityLivingBase.class, float.class, float.class).newInstance(world, player, this.speed, this.inaccuracy);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
