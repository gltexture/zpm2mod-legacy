package ru.BouH_.fun.rockets;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import ru.BouH_.entity.particle.EntityParticleColoredCloud;
import ru.BouH_.entity.particle.EntityParticleFlame;
import ru.BouH_.fun.rockets.base.EntityDetectableRocketZp;
import ru.BouH_.utils.ExplosionUtils;

public class EntityJavelinRocket extends EntityDetectableRocketZp {
    private Entity entityHit;
    private boolean startAttack;
    private boolean readyToDestroy;

    public EntityJavelinRocket(World p_i1776_1_) {
        super(p_i1776_1_);
    }

    public EntityJavelinRocket(World p_i1778_1_, double p_i1778_2_, double p_i1778_4_, double p_i1778_6_) {
        super(p_i1778_1_, p_i1778_2_, p_i1778_4_, p_i1778_6_);
    }

    public EntityJavelinRocket(World p_i1756_1_, EntityLivingBase p_i1756_2_, float p_i1756_3_) {
        super(p_i1756_1_, p_i1756_2_, 2.2f, p_i1756_3_);
    }

    public void explode() {
        ExplosionUtils.makeExplosion(this.worldObj, null, this, this.posX, this.posY + (double) (this.height / 16.0F), this.posZ, 6.0f, true, true, false);
        this.setDead();
    }

    public void setInfo(Entity entityHit) {
        this.entityHit = entityHit;
    }

    @SideOnly(Side.CLIENT)
    public void spawnParticles() {
        int flameCount = 12;
        float flameScale = 3.0f;
        for (int i = 0; i < 2; i++) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleColoredCloud(this.worldObj, this.posX - this.motionX * 2.0f, this.posY - this.motionY * 2.0f, this.posZ - this.motionZ * 2.0f, -this.motionX * 0.2f, -this.motionY * 0.2f, -this.motionZ * 0.2f, new float[]{0.85f, 0.85f, 0.85f}, 1.0f));
        }
        for (int i = 0; i < flameCount; i++) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleFlame(this.worldObj, this.posX - this.motionX, this.posY - this.motionY, this.posZ - this.motionZ, -this.motionX * 0.4f, -this.motionY * 0.4f, -this.motionZ * 0.4f, flameScale));
        }
    }

    public void onUpdate() {
        super.onUpdate();
        if (!this.isDead) {
            if (this.entityHit != null && this.entityHit.isEntityAlive() && this.ticksExisted <= this.chargingTicks()) {

                double x = this.entityHit.posX;
                double y = this.entityHit.posY;
                double z = this.entityHit.posZ;

                if (!this.readyToDestroy) {
                    if (this.getDistance(x, this.posY, z) <= 8) {
                        this.readyToDestroy = true;
                    } else if (this.getDistance(x, this.posY, z) <= 64) {
                        this.startAttack = true;
                    }
                }

                double plX = this.posX;
                double plY = this.posY;
                double plZ = this.posZ;

                double d0 = x - plX;
                double d1 = y - plY;
                double d2 = z - plZ;

                double d3 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);
                float f = (float) (Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
                float f1 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
                this.rotationPitch = f1;
                this.rotationYaw = f;

                this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
                this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);

                if (this.readyToDestroy) {
                    this.motionY = -MathHelper.sin((this.rotationPitch + this.func_70183_g()) / 180.0F * (float) Math.PI);
                } else {
                    this.motionY = this.startAttack ? 0.4f : 0.2f;
                }

                if (this.getDistance(x, y, z) <= 0.5f) {
                    this.onImpact(null);
                }

                this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, 2.2f, 0.0f);
            } else {
                this.onImpact(null);
            }
            if (this.worldObj.isRemote) {
                this.spawnParticles();
                this.playSound();
            }
        }
    }

    private float updateRotation(float p_75652_1_, float p_75652_2_, float p_75652_3_) {
        float f3 = MathHelper.wrapAngleTo180_float(p_75652_2_ - p_75652_1_);

        if (f3 > p_75652_3_) {
            f3 = p_75652_3_;
        }

        if (f3 < -p_75652_3_) {
            f3 = -p_75652_3_;
        }

        return p_75652_1_ + f3;
    }

    public int chargingTicks() {
        return 260;
    }

    protected float getGravityVelocity() {
        return 0.04f;
    }
}
