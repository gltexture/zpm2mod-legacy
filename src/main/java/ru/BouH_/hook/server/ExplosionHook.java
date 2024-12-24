package ru.BouH_.hook.server;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import ru.BouH_.misc.ExplosionZp;
import ru.hook.asm.Hook;
import ru.hook.asm.ReturnCondition;

public class ExplosionHook {
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static Explosion newExplosion(World world, Entity p_72885_1_, double p_72885_2_, double p_72885_4_, double p_72885_6_, float p_72885_8_, boolean p_72885_9_, boolean p_72885_10_) {
        ExplosionZp explosion = new ExplosionZp(world, p_72885_1_, p_72885_2_, p_72885_4_, p_72885_6_, p_72885_8_);
        explosion.isFlaming = p_72885_9_;
        explosion.isSmoking = p_72885_10_;
        if (net.minecraftforge.event.ForgeEventFactory.onExplosionStart(world, explosion)) {
            return explosion;
        }
        explosion.doExplosionA();
        return explosion;
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static Explosion newExplosion(WorldServer world, Entity p_72885_1_, double p_72885_2_, double p_72885_4_, double p_72885_6_, float p_72885_8_, boolean p_72885_9_, boolean p_72885_10_) {
        ExplosionZp explosion = new ExplosionZp(world, p_72885_1_, p_72885_2_, p_72885_4_, p_72885_6_, p_72885_8_);
        explosion.isFlaming = p_72885_9_;
        explosion.isSmoking = p_72885_10_;
        if (net.minecraftforge.event.ForgeEventFactory.onExplosionStart(world, explosion)) {
            return explosion;
        }
        explosion.doExplosionA();

        if (!p_72885_10_) {
            explosion.affectedBlockPositions.clear();
        }

        for (Object o : world.playerEntities) {
            EntityPlayer entityplayer = (EntityPlayer) o;

            if (entityplayer.getDistance(p_72885_2_, p_72885_4_, p_72885_6_) < 1024.0f) {
                ((EntityPlayerMP) entityplayer).playerNetServerHandler.sendPacket(new S27PacketExplosion(p_72885_2_, p_72885_4_, p_72885_6_, p_72885_8_, explosion.affectedBlockPositions, (Vec3) explosion.func_77277_b().get(entityplayer)));
            }
        }

        return explosion;
    }
}
