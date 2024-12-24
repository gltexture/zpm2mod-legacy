package ru.BouH_.fun.rockets;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import ru.BouH_.entity.particle.EntityParticleColoredCloud;
import ru.BouH_.fun.rockets.base.EntityDetectableRocketZp;
import ru.BouH_.utils.ExplosionUtils;

import java.util.List;

public class EntityGeran2 extends EntityDetectableRocketZp {
    public TileEntityDispenser dispencerToDestroy;
    private int seekPunishment;

    public EntityGeran2(World p_i1776_1_) {
        super(p_i1776_1_);
    }

    public EntityGeran2(World p_i1778_1_, double p_i1778_2_, double p_i1778_4_, double p_i1778_6_) {
        super(p_i1778_1_, p_i1778_2_, p_i1778_4_, p_i1778_6_);
    }

    public void explode() {
        ExplosionUtils.makeExplosion(this.worldObj, null, this, this.posX, this.posY + (double) (this.height / 16.0F), this.posZ, 5.0f, false, true, false);
        this.setDead();
    }

    @SideOnly(Side.CLIENT)
    public void spawnParticles() {
        for (int i = 0; i < 2; i++) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleColoredCloud(this.worldObj, this.posX - this.motionX * 2.0f, this.posY - this.motionY * 2.0f, this.posZ - this.motionZ * 2.0f, -this.motionX * 0.2f, -this.motionY * 0.2f, -this.motionZ * 0.2f, new float[]{0.85f, 0.85f, 0.85f}, 0.6f));
        }
    }

    public void onUpdate() {
        super.onUpdate();

        if (this.ticksExisted >= 30) {
            if (this.dispencerToDestroy != null) {
                if (this.seekPunishment == 0) {
                    if (!this.worldObj.isAirBlock(this.dispencerToDestroy.xCoord, this.dispencerToDestroy.yCoord, this.dispencerToDestroy.zCoord)) {
                        if (this.getDistance(this.dispencerToDestroy.xCoord, this.dispencerToDestroy.yCoord, this.dispencerToDestroy.zCoord) > 5) {

                            double plX = this.posX;
                            double plY = this.posY;
                            double plZ = this.posZ;

                            double atX = this.dispencerToDestroy.xCoord;
                            double atY = this.dispencerToDestroy.yCoord;
                            double atZ = this.dispencerToDestroy.zCoord;

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

                            this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, 2.6f, 0.0f);
                        } else {
                            this.punishSeek();
                        }
                    }
                } else {
                    this.seekPunishment--;
                }
                if (this.worldObj.isAirBlock(this.dispencerToDestroy.xCoord, this.dispencerToDestroy.yCoord, this.dispencerToDestroy.zCoord)) {
                    this.dispencerToDestroy = null;
                }
            } else {
                this.dispencerToDestroy = this.seekForBlock();
                this.motionY *= 0.92f;
            }
        }

        if (this.worldObj.isRemote) {
            this.spawnParticles();
            this.playSound();
        }
    }

    @SuppressWarnings("unchecked")
    private TileEntityDispenser seekForBlock() {
        List<TileEntity> list = this.worldObj.loadedTileEntityList;
        TileEntityDispenser currentSelectedTarget = null;
        if (!list.isEmpty()) {
            for (TileEntity tileEntity : list) {
                if (tileEntity instanceof TileEntityDispenser) {
                    TileEntityDispenser tileAD = (TileEntityDispenser) tileEntity;
                    //if (!this.equals(tileAD.owner)) {
                    if (this.getDistance(tileAD.xCoord, this.posY, tileAD.zCoord) <= 42) {
                        if (currentSelectedTarget == null || this.getDistance(tileAD.xCoord, tileAD.yCoord, tileAD.zCoord) < this.getDistance(currentSelectedTarget.xCoord, currentSelectedTarget.yCoord, currentSelectedTarget.zCoord)) {
                            currentSelectedTarget = tileAD;
                        }
                    }
                    //}
                }
            }
        }
        return currentSelectedTarget;
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
        this.seekPunishment = 5;
    }

    public int chargingTicks() {
        return 60;
    }

    protected float getGravityVelocity() {
        return 0.15f;
    }
}
