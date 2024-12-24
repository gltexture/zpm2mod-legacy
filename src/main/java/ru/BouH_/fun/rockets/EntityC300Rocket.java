package ru.BouH_.fun.rockets;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import ru.BouH_.entity.particle.EntityParticleColoredCloud;
import ru.BouH_.entity.particle.EntityParticleFlame;
import ru.BouH_.entity.projectile.EntityRocketZPBase;
import ru.BouH_.fun.rockets.base.EntityDefenceRocketZp;
import ru.BouH_.fun.rockets.base.EntityDetectableRocketZp;
import ru.BouH_.utils.ExplosionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EntityC300Rocket extends EntityDefenceRocketZp {
    public static final Set<Class> rockets = new HashSet<>();

    static {
        rockets.add(EntityX101Rocket.class);
        rockets.add(EntityKalibrRocket.class);
        rockets.add(EntityKalibrGuidedRocket.class);
        rockets.add(EntityIskanderRocket.class);
        rockets.add(EntityOvodRocket.class);
        rockets.add(EntityBastionRocket.class);
        rockets.add(EntityGeran2.class);
    }

    public EntityC300Rocket(World p_i1776_1_) {
        super(p_i1776_1_);
    }

    public EntityC300Rocket(World p_i1778_1_, double p_i1778_2_, double p_i1778_4_, double p_i1778_6_) {
        super(p_i1778_1_, p_i1778_2_, p_i1778_4_, p_i1778_6_);
    }

    public void explode() {
        ExplosionUtils.makeExplosion(this.worldObj, null, this, this.posX, this.posY + (double) (this.height / 16.0F), this.posZ, 4.0f, false, false, false);
        this.setDead();
    }

    @SideOnly(Side.CLIENT)
    public void spawnParticles() {
        int flameCount = 14;
        float flameScale = 3.0f;
        for (int i = 0; i < 2; i++) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleColoredCloud(this.worldObj, this.posX - this.motionX * 2.0f, this.posY - this.motionY * 2.0f, this.posZ - this.motionZ * 2.0f, -this.motionX * 0.2f, -this.motionY * 0.2f, -this.motionZ * 0.2f, new float[]{0.85f, 0.85f, 0.85f}, 1.5f));
        }
        for (int i = 0; i < flameCount; i++) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleFlame(this.worldObj, this.posX - this.motionX, this.posY - this.motionY, this.posZ - this.motionZ, -this.motionX * 0.4f, -this.motionY * 0.4f, -this.motionZ * 0.4f, flameScale));
        }
    }

    public void onUpdate() {
        super.onUpdate();

        if (this.rocketToDestroy != null) {
            if (this.seekPunishment == 0) {
                if (!this.rocketToDestroy.isDead && this.isCharging) {
                    if (this.getDistanceToEntity(this.rocketToDestroy) <= 2.0f) {
                        this.rocketToDestroy.onImpact(null);
                        this.onImpact(null);
                    }
                    double plX = this.posX;
                    double plY = this.posY;
                    double plZ = this.posZ;

                    double atX = this.rocketToDestroy.posX;
                    double atY = this.rocketToDestroy.posY;
                    double atZ = this.rocketToDestroy.posZ;

                    double d0 = atX - plX;
                    double d1 = atY - plY;
                    double d2 = atZ - plZ;

                    double d3 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);
                    float f3 = (float) (Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
                    float f4 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));

                    this.rotationPitch = f4;
                    this.rotationYaw = f3;

                    this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
                    this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
                    this.motionY = -MathHelper.sin((this.rotationPitch + this.func_70183_g()) / 180.0F * (float) Math.PI);

                    this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, 3.9f, 0.0f);
                } else {
                    if (this.ticksExisted >= 40) {
                        this.onImpact(null);
                    }
                }
            } else {
                if (this.seekPunishment-- == 1) {
                    this.onImpact(null);
                }
            }
        } else {
            this.rocketToDestroy = this.seekForEnemy();
        }
        if (this.worldObj.isRemote) {
            this.spawnParticles();
            this.playSound();
        }
    }

    @SuppressWarnings("unchecked")
    private EntityRocketZPBase seekForEnemy() {
        List<EntityDetectableRocketZp> list = this.worldObj.getEntitiesWithinAABB(EntityDetectableRocketZp.class, this.boundingBox.expand(86, 256, 86));
        if (!list.isEmpty()) {
            for (EntityDetectableRocketZp rocketDetectable : list) {
                if (!this.equals(rocketDetectable.getOwner())) {
                    if (rocketDetectable.canBeDetected()) {
                        rocketDetectable.setDetected(this);
                        return rocketDetectable;
                    }
                }
            }
        }
        return null;
    }

    protected float func_205060_a(float p_205060_1_, float p_205060_2_, float p_205060_3_) {
        float f = (p_205060_2_ - p_205060_1_) % ((float) Math.PI * 2F);
        if (f < -(float) Math.PI) {
            f += ((float) Math.PI * 2F);
        }

        if (f >= (float) Math.PI) {
            f -= ((float) Math.PI * 2F);
        }

        return p_205060_1_ + p_205060_3_ * f;
    }

    public int getFinalPunishment() {
        return 61;
    }

    public int chargingTicks() {
        return 120;
    }

    protected float getGravityVelocity() {
        return 0.05f;
    }
}
