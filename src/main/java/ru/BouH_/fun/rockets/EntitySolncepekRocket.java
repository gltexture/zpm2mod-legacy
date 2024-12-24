package ru.BouH_.fun.rockets;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import ru.BouH_.entity.particle.EntityParticleColoredCloud;
import ru.BouH_.entity.particle.EntityParticleFlame;
import ru.BouH_.fun.rockets.base.EntityDetectableRocketZp;
import ru.BouH_.utils.ExplosionUtils;

public class EntitySolncepekRocket extends EntityDetectableRocketZp {

    public EntitySolncepekRocket(World p_i1776_1_) {
        super(p_i1776_1_);
    }

    public EntitySolncepekRocket(World p_i1778_1_, double p_i1778_2_, double p_i1778_4_, double p_i1778_6_) {
        super(p_i1778_1_, p_i1778_2_, p_i1778_4_, p_i1778_6_);
    }

    public void explode() {
        ExplosionUtils.makeExplosionThermobaric(this.worldObj, null, this, this.posX, this.posY + (double) (this.height / 16.0F), this.posZ, 8.0f, false);
        this.setDead();
    }

    @SideOnly(Side.CLIENT)
    public void spawnParticles() {
        float multiplier = Math.min((float) ((this.chargingTicks() + 15) - this.ticksExisted) / this.chargingTicks(), 1.0f);
        int flameCount = (int) (8 * multiplier) + 1;
        float flameScale = (2.5f * multiplier) + 0.5f;
        for (int i = 0; i < 2; i++) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleColoredCloud(this.worldObj, this.posX - this.motionX * 2.0f, this.posY - this.motionY * 2.0f, this.posZ - this.motionZ * 2.0f, -this.motionX * 0.2f, -this.motionY * 0.2f, -this.motionZ * 0.2f, new float[]{0.85f, 0.85f, 0.85f}, 0.8f));
        }
        for (int i = 0; i < flameCount; i++) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleFlame(this.worldObj, this.posX - this.motionX, this.posY - this.motionY, this.posZ - this.motionZ, -this.motionX * 0.3f, -this.motionY * 0.3f, -this.motionZ * 0.3f, flameScale));
        }
    }

    public void onUpdate() {
        super.onUpdate();
        if (this.worldObj.isRemote) {
            if (this.ticksExisted < this.chargingTicks() + 15) {
                this.spawnParticles();
            }
            this.playSound();
        }
    }

    public int chargingTicks() {
        return 18;
    }

    protected float getGravityVelocity() {
        return 0.0235f;
    }
}
