package ru.BouH_.fun.rockets;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import ru.BouH_.entity.particle.EntityParticleColoredCloud;
import ru.BouH_.entity.particle.EntityParticleFlame;
import ru.BouH_.fun.rockets.base.EntityDetectableRocketZp;
import ru.BouH_.utils.ExplosionUtils;

import java.util.List;

public class EntityR27Rocket extends EntityDetectableRocketZp {
    public EntityLivingBase entityToDestroy;
    private int seekPunishment;

    public EntityR27Rocket(World p_i1776_1_) {
        super(p_i1776_1_);
    }

    public EntityR27Rocket(World p_i1778_1_, double p_i1778_2_, double p_i1778_4_, double p_i1778_6_) {
        super(p_i1778_1_, p_i1778_2_, p_i1778_4_, p_i1778_6_);
    }

    public void explode() {
        ExplosionUtils.makeExplosion(this.worldObj, null, this, this.posX, this.posY + (double) (this.height / 16.0F), this.posZ, 3.0f, false, true, false);
        this.setDead();
    }

    @SideOnly(Side.CLIENT)
    public void spawnParticles() {
        int flameCount = 12;
        float flameScale = 2.0f;
        for (int i = 0; i < 2; i++) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleColoredCloud(this.worldObj, this.posX - this.motionX * 2.0f, this.posY - this.motionY * 2.0f, this.posZ - this.motionZ * 2.0f, -this.motionX * 0.2f, -this.motionY * 0.2f, -this.motionZ * 0.2f, new float[]{0.85f, 0.85f, 0.85f}, 1.0f));
        }
        for (int i = 0; i < flameCount; i++) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleFlame(this.worldObj, this.posX - this.motionX, this.posY - this.motionY, this.posZ - this.motionZ, -this.motionX * 0.4f, -this.motionY * 0.4f, -this.motionZ * 0.4f, flameScale));
        }
    }

    public void onUpdate() {
        super.onUpdate();

        if (this.ticksExisted >= 40) {
            if (this.ticksExisted > this.chargingTicks()) {
                this.explode();
            }
            if (this.entityToDestroy != null && !this.entityToDestroy.isDead) {
                if (this.seekPunishment == 0) {
                    if (this.getDistanceToEntity(this.entityToDestroy) > 3) {

                        double plX = this.posX;
                        double plY = this.posY;
                        double plZ = this.posZ;

                        double atX = this.entityToDestroy.posX;
                        double atY = this.entityToDestroy.posY;
                        double atZ = this.entityToDestroy.posZ;

                        double d0 = atX - plX;
                        double d1 = atY - plY;
                        double d2 = atZ - plZ;

                        double d3 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);
                        float f = (float) (Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
                        float f1 = (float) (-(Math.atan2(d1, d3) * 180.0D / Math.PI));
                        this.rotationPitch = f1;
                        this.rotationYaw = f;

                        this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
                        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float) Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float) Math.PI);
                        this.motionY = -MathHelper.sin((this.rotationPitch + this.func_70183_g()) / 180.0F * (float) Math.PI);

                        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, 2.5f, 0.0f);
                    } else {
                        this.punishSeek();
                    }
                } else {
                    this.seekPunishment--;
                }
            } else {
                this.entityToDestroy = this.seekForEnemy();
                this.motionY *= 0.9f;
            }
        }
        if (this.worldObj.isRemote) {
            this.spawnParticles();
            this.playSound();
        }
    }

    @SuppressWarnings("unchecked")
    private EntityLivingBase seekForEnemy() {
        List<EntityPlayer> list = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class, this.boundingBox.expand(32, 256, 32));
        if (!list.isEmpty()) {
            for (EntityPlayer player : list) {
                if (!this.equals(player.getDisplayName())) {
                    return player;
                }
            }
        }
        return null;
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

    public void punishSeek() {
        this.seekPunishment = 40;
    }

    public int chargingTicks() {
        return 150;
    }

    protected float getGravityVelocity() {
        return 0.04f;
    }
}
