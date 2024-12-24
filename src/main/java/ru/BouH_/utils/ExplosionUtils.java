package ru.BouH_.utils;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import ru.BouH_.misc.ExplosionThermobaricZp;
import ru.BouH_.misc.ExplosionZp;

public class ExplosionUtils {
    public static void makeExplosion(World world, EntityPlayer player, Entity entity, double posX, double posY, double posZ, float scale, boolean isFlaming, boolean isSmoking, boolean doExplosionB) {
        ExplosionZp explosion = new ExplosionZp(world, player, entity, posX, posY + (double) (entity.height / 16.0F), posZ, scale);
        explosion.isFlaming = isFlaming;
        explosion.isSmoking = isSmoking;
        explosion.doExplosionA();
        explosion.doExplosionB(doExplosionB);
    }

    public static void makeExplosionThermobaric(World world, EntityPlayer player, Entity entity, double posX, double posY, double posZ, float scale, boolean doExplosionB) {
        ExplosionThermobaricZp explosion = new ExplosionThermobaricZp(world, player, entity, posX, posY + (double) (entity.height / 16.0F), posZ, scale);
        explosion.isFlaming = true;
        explosion.isSmoking = true;
        explosion.doExplosionA();
        explosion.doExplosionB(doExplosionB);
    }
}
