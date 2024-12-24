package ru.BouH_.items.misc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ru.BouH_.entity.projectile.EntityThrowableZPBase;

import java.lang.reflect.InvocationTargetException;

public class ItemThrowable extends Item {
    private final Class<? extends Entity> throwable;
    private final float speed;
    private final float inaccuracy;

    public ItemThrowable(String unlocalizedName, Class<? extends Entity> throwable, float speed, float inaccuracy) {
        this.setUnlocalizedName(unlocalizedName);
        this.throwable = throwable;
        this.speed = speed;
        this.inaccuracy = inaccuracy;
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            if (player.getEntityData().getInteger("cdThrow") <= 0) {
                world.playSoundAtEntity(player, "random.bow", 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
                --stack.stackSize;
                player.getEntityData().setInteger("cdThrow", 20);
                world.spawnEntityInWorld(this.getEntity(world, player));
            }
        }
        return stack;
    }

    protected EntityThrowableZPBase getEntity(World world, EntityPlayer player) {
        try {
            return (EntityThrowableZPBase) this.throwable.getConstructor(World.class, EntityLivingBase.class, float.class, float.class).newInstance(world, player, this.speed, this.inaccuracy);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
