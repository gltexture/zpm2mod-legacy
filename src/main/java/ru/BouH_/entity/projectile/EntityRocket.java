package ru.BouH_.entity.projectile;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import ru.BouH_.entity.particle.EntityParticleColoredCloud;
import ru.BouH_.entity.particle.EntityParticleFlame;
import ru.BouH_.utils.ExplosionUtils;

public class EntityRocket extends EntityRocketZPBase {

    public EntityRocket(World p_i1776_1_) {
        super(p_i1776_1_);
    }

    public EntityRocket(World p_i1778_1_, double p_i1778_2_, double p_i1778_4_, double p_i1778_6_) {
        super(p_i1778_1_, p_i1778_2_, p_i1778_4_, p_i1778_6_);
    }

    public EntityRocket(World p_i1756_1_, EntityLivingBase p_i1756_2_, float p_i1756_3_) {
        super(p_i1756_1_, p_i1756_2_, 3.2f, p_i1756_3_);
    }

    @SideOnly(Side.CLIENT)
    public void spawnParticles() {
        float multiplier = Math.min((float) ((this.chargingTicks() + 15) - this.ticksExisted) / this.chargingTicks(), 1.0f);
        int flameCount = (int) (10 * multiplier) + 1;
        float flameScale = (3.0f * multiplier) + 0.25f;
        for (int i = 0; i < 2; i++) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleColoredCloud(this.worldObj, this.posX - this.motionX * 3.0f, this.posY - this.motionY * 3.0f, this.posZ - this.motionZ * 3.0f, -this.motionX * 0.3f, -this.motionY * 0.3f, -this.motionZ * 0.3f, new float[]{0.85f, 0.85f, 0.85f}, 1.2f));
        }
        for (int i = 0; i < flameCount; i++) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleFlame(this.worldObj, this.posX - this.motionX, this.posY - this.motionY, this.posZ - this.motionZ, -this.motionX * 0.2f, -this.motionY * 0.2f, -this.motionZ * 0.2f, flameScale));
        }
    }

    public void explode() {
        EntityPlayer player = this.getThrower() instanceof EntityPlayer ? (EntityPlayer) this.getThrower() : null;
        ExplosionUtils.makeExplosion(this.worldObj, player, this, this.posX, this.posY + (double) (this.height / 16.0F), this.posZ, 6.0f, true, true, false);
        this.setDead();
    }

    protected float getGravityVelocity() {
        return 0.02f;
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
        return 20;
    }
}
