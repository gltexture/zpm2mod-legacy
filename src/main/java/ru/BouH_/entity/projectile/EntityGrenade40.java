package ru.BouH_.entity.projectile;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import ru.BouH_.entity.particle.EntityParticleGunSmoke;
import ru.BouH_.utils.ExplosionUtils;

public class EntityGrenade40 extends EntityThrowableZPBase {
    public final float gravity = 0.03f;

    public EntityGrenade40(World p_i1776_1_) {
        super(p_i1776_1_);
    }

    public EntityGrenade40(World p_i1778_1_, double p_i1778_2_, double p_i1778_4_, double p_i1778_6_) {
        super(p_i1778_1_, p_i1778_2_, p_i1778_4_, p_i1778_6_);
    }

    public EntityGrenade40(World p_i1756_1_, EntityLivingBase p_i1756_2_, float speed, float p_i1756_3_) {
        super(p_i1756_1_, p_i1756_2_, speed, p_i1756_3_);
    }

    @SideOnly(Side.CLIENT)
    public void spawnParticles() {
        for (int i = 0; i < 2; i++) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleGunSmoke(this.worldObj, this.posX - this.motionX * 2, this.posY - this.motionY * 2, this.posZ - this.motionZ * 2, 0, 0, 0));
        }
    }

    public void explode() {
        EntityPlayer player = this.getThrower() instanceof EntityPlayer ? (EntityPlayer) this.getThrower() : null;
        ExplosionUtils.makeExplosion(this.worldObj, player, this, this.posX, this.posY + (double) (this.height / 16.0F), this.posZ, 6.0f, false, true, false);
        this.setDead();
    }

    protected float getGravityVelocity() {
        return this.gravity;
    }

    public void onUpdate() {
        super.onUpdate();
        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
            float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.prevRotationYaw = this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float) (Math.atan2(this.motionY, f) * 180.0D / Math.PI);
        }

        if (this.worldObj.isRemote) {
            this.spawnParticles();
        }

        if (this.isBurning()) {
            this.explode();
        }
    }

    @Override
    protected void onImpact(MovingObjectPosition movingobjectposition) {
        if (!this.worldObj.isRemote) {
            this.explode();
        }
    }
}
