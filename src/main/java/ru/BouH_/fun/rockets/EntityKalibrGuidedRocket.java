package ru.BouH_.fun.rockets;


import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import ru.BouH_.entity.particle.EntityParticleColoredCloud;
import ru.BouH_.entity.particle.EntityParticleFlame;
import ru.BouH_.fun.rockets.base.EntityDetectableRocketZp;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.packets.fun.PacketGuidedTarget;
import ru.BouH_.utils.ExplosionUtils;

public class EntityKalibrGuidedRocket extends EntityDetectableRocketZp {
    private double targetX;
    private double targetY;
    private double targetZ;
    private boolean isValidTarget;

    public EntityKalibrGuidedRocket(World p_i1776_1_) {
        super(p_i1776_1_);
    }

    public EntityKalibrGuidedRocket(World p_i1778_1_, double p_i1778_2_, double p_i1778_4_, double p_i1778_6_) {
        super(p_i1778_1_, p_i1778_2_, p_i1778_4_, p_i1778_6_);
    }

    public void explode() {
        ExplosionUtils.makeExplosion(this.worldObj, null, this, this.posX, this.posY + (double) (this.height / 16.0F), this.posZ, 13.5f, true, true, false);
        this.setDead();
    }

    @SideOnly(Side.CLIENT)
    public void spawnParticles() {
        int flameCount = 12;
        float flameScale = 5.0f;
        for (int i = 0; i < 2; i++) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleColoredCloud(this.worldObj, this.posX - this.motionX * 2.0f, this.posY - this.motionY * 2.0f, this.posZ - this.motionZ * 2.0f, -this.motionX * 0.2f, -this.motionY * 0.2f, -this.motionZ * 0.2f, new float[]{0.85f, 0.85f, 0.85f}, 1.5f));
        }
        for (int i = 0; i < flameCount; i++) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleFlame(this.worldObj, this.posX - this.motionX, this.posY - this.motionY, this.posZ - this.motionZ, -this.motionX * 0.4f, -this.motionY * 0.4f, -this.motionZ * 0.4f, flameScale));
        }
    }

    public void onUpdate() {
        super.onUpdate();
        if (this.ticksExisted > 10 && this.posY >= 180) {
            if (this.isValidTarget) {
                double plX = this.posX;
                double plY = this.posY;
                double plZ = this.posZ;

                double atX = this.targetX;
                double atY = this.targetY;
                double atZ = this.targetZ;

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

                if ((this.canSeeBlock(atX, atY, atZ) && this.getDistance(atX, this.posY, atZ) <= 186) || this.getDistance(atX, this.posY, atZ) < 12) {
                    this.motionY = -MathHelper.sin((this.rotationPitch + this.func_70183_g()) / 180.0F * (float) Math.PI);
                } else {
                    this.motionY *= 0.25f;
                }

                this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, 3.0f, 0.0f);

                if (this.getDistance(atX, atY, atZ) < 3) {
                    this.isValidTarget = false;
                }
            }
        }
        if (this.worldObj.isRemote) {
            this.spawnParticles();
            this.playSound();
        }
    }

    private boolean canSeeBlock(double x, double y, double z) {
        Vec3 vec1 = Vec3.createVectorHelper(this.posX, this.posY + (this.boundingBox.maxY - this.boundingBox.minY) * 0.25f, this.posZ);
        Vec3 vec2 = Vec3.createVectorHelper(x, y, z);
        return this.worldObj.rayTraceBlocks(vec1, vec2, false, true, false) == null;
    }

    public void setTarget(double x, double y, double z) {
        this.targetX = x;
        this.targetY = y;
        this.targetZ = z;
        this.isValidTarget = true;
        if (!this.worldObj.isRemote) {
            NetworkHandler.NETWORK.sendToAllAround(new PacketGuidedTarget(this.getEntityId(), (int) x, (int) y, (int) z), new NetworkRegistry.TargetPoint(this.dimension, this.posX, this.posY, this.posZ, 256));
        }
    }

    public int chargingTicks() {
        return 300;
    }

    protected float getGravityVelocity() {
        return 0.15f;
    }
}
