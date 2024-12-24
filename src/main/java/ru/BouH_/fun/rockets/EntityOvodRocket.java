package ru.BouH_.fun.rockets;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import ru.BouH_.entity.particle.EntityParticleColoredCloud;
import ru.BouH_.entity.particle.EntityParticleFlame;
import ru.BouH_.fun.rockets.base.EntityDetectableRocketZp;
import ru.BouH_.fun.tiles.TileTacticalBlock;
import ru.BouH_.utils.ExplosionUtils;

import java.util.List;

public class EntityOvodRocket extends EntityDetectableRocketZp {
    public TileTacticalBlock defenceToDestroy;
    private int seekPunishment;

    public EntityOvodRocket(World p_i1776_1_) {
        super(p_i1776_1_);
    }

    public EntityOvodRocket(World p_i1778_1_, double p_i1778_2_, double p_i1778_4_, double p_i1778_6_) {
        super(p_i1778_1_, p_i1778_2_, p_i1778_4_, p_i1778_6_);
    }

    public void explode() {
        ExplosionUtils.makeExplosion(this.worldObj, null, this, this.posX, this.posY + (double) (this.height / 16.0F), this.posZ, 3.0f, false, true, false);
        this.setDead();
    }

    @SideOnly(Side.CLIENT)
    public void spawnParticles() {
        float multiplier = Math.min((float) ((this.chargingTicks() + 15) - this.ticksExisted) / this.chargingTicks(), 1.0f);
        int flameCount = (int) (8 * multiplier) + 1;
        float flameScale = (2.5f * multiplier) + 0.25f;
        for (int i = 0; i < 2; i++) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleColoredCloud(this.worldObj, this.posX - this.motionX * 2.0f, this.posY - this.motionY * 2.0f, this.posZ - this.motionZ * 2.0f, -this.motionX * 0.2f, -this.motionY * 0.2f, -this.motionZ * 0.2f, new float[]{0.85f, 0.85f, 0.85f}, 1.0f));
        }
        for (int i = 0; i < flameCount; i++) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleFlame(this.worldObj, this.posX - this.motionX, this.posY - this.motionY, this.posZ - this.motionZ, -this.motionX * 0.4f, -this.motionY * 0.4f, -this.motionZ * 0.4f, flameScale));
        }
    }

    public void onUpdate() {
        super.onUpdate();

        if (this.ticksExisted >= 20) {
            if (this.canBlockAD() && this.homingRocket != null) {
                if (this.seekPunishment == 0) {
                    if (this.motionX != 0 && this.motionZ != 0) {
                        if (this.getDistanceToEntity(this.homingRocket) <= 10.0f) {
                            this.motionY = 10.0f;
                            this.punishSeek();
                            this.homingRocket.punishSeek();
                        }
                    }
                }
            }
            if (this.defenceToDestroy != null) {
                if (this.seekPunishment == 0) {
                    if (!this.worldObj.isAirBlock(this.defenceToDestroy.xCoord, this.defenceToDestroy.yCoord, this.defenceToDestroy.zCoord)) {
                        if (this.getDistance(this.defenceToDestroy.xCoord, this.defenceToDestroy.yCoord, this.defenceToDestroy.zCoord) > 5) {
                            double plX = this.posX;
                            double plY = this.posY;
                            double plZ = this.posZ;

                            double atX = this.defenceToDestroy.xCoord;
                            double atY = this.defenceToDestroy.yCoord;
                            double atZ = this.defenceToDestroy.zCoord;

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

                            this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, 3.0f, 0.0f);
                        } else {
                            this.punishSeek();
                        }
                    }
                } else {
                    this.seekPunishment--;
                }
                if (this.worldObj.isAirBlock(this.defenceToDestroy.xCoord, this.defenceToDestroy.yCoord, this.defenceToDestroy.zCoord)) {
                    this.defenceToDestroy = null;
                }
            } else {
                this.defenceToDestroy = this.seekForBlock();
                this.motionY *= 0.9f;
            }
        }

        if (this.worldObj.isRemote) {
            if (this.ticksExisted < this.chargingTicks() + 15) {
                this.spawnParticles();
            }
            this.playSound();
        }
    }

    private boolean canBlockAD() {
        return this.getEntityId() % 10 != 5;
    }

    @SuppressWarnings("unchecked")
    private TileTacticalBlock seekForBlock() {
        List<TileEntity> list = this.worldObj.loadedTileEntityList;
        TileTacticalBlock currentSelectedTarget = null;
        if (!list.isEmpty()) {
            for (TileEntity tileEntity : list) {
                if (tileEntity instanceof TileTacticalBlock) {
                    TileTacticalBlock tileAD = (TileTacticalBlock) tileEntity;
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
        return 0.05f;
    }
}
