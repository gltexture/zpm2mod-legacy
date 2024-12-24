package ru.BouH_.entity.projectile.dispencer;

import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.world.World;
import ru.BouH_.entity.projectile.EntityThrowableZPBase;

import java.lang.reflect.InvocationTargetException;

public class ProjectileThrowable extends BehaviorProjectileDispense {
    private final Class<? extends Entity> throwable;

    public ProjectileThrowable(Class<? extends Entity> throwable) {
        this.throwable = throwable;
    }

    protected IProjectile getProjectileEntity(World par1World, IPosition par2IPosition) {
        return this.getEntity(par1World, par2IPosition);
    }

    @SuppressWarnings("unckecked")
    protected EntityThrowableZPBase getEntity(World world, IPosition par2IPosition) {
        try {
            return (EntityThrowableZPBase) this.throwable.getConstructor(World.class, double.class, double.class, double.class).newInstance(world, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    protected float func_82500_b() {
        return 2.0F;
    }
}
