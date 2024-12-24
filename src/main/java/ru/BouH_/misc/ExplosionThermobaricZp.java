package ru.BouH_.misc;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.packets.particles.ParticleExplosionThermobaric;

public class ExplosionThermobaricZp extends ExplosionZp {

    public ExplosionThermobaricZp(World p_i1948_1_, Entity p_i1948_2_, double p_i1948_3_, double p_i1948_5_, double p_i1948_7_, float p_i1948_9_) {
        this(p_i1948_1_, null, p_i1948_2_, p_i1948_3_, p_i1948_5_, p_i1948_7_, p_i1948_9_);
    }

    public ExplosionThermobaricZp(World p_i1948_1_, EntityPlayer player, Entity p_i1948_2_, double p_i1948_3_, double p_i1948_5_, double p_i1948_7_, float p_i1948_9_) {
        super(p_i1948_1_, p_i1948_2_, p_i1948_3_, p_i1948_5_, p_i1948_7_, p_i1948_9_);
        this.worldObj = p_i1948_1_;
        this.player = player;
    }

    protected void explosionEffect() {
        if (!this.worldObj.isRemote) {
            NetworkHandler.NETWORK.sendToAllAround(new ParticleExplosionThermobaric(this.explosionX, this.explosionY, this.explosionZ, this.explosionSize), new NetworkRegistry.TargetPoint(this.worldObj.getWorldInfo().getDimension(), this.explosionX, this.explosionY, this.explosionZ, 256));
        }
    }

    @SuppressWarnings("unchecked")
    protected void postExplosion() {
        super.postExplosion();
        this.func_77277_b().keySet().forEach(e -> {
            if (e instanceof EntityLivingBase) {
                ((Entity) e).setFire(10);
            }
        });
    }
}
