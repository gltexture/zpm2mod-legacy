package ru.BouH_.entity.projectile;


import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.lwjgl_vector.Vector3fLWJGL;
import ru.BouH_.utils.ExplosionUtils;
import ru.BouH_.utils.SoundUtils;

public class EntityGrenade extends EntityThrowableZPBase {

    public EntityGrenade(World p_i1776_1_) {
        super(p_i1776_1_);
    }

    public EntityGrenade(World p_i1778_1_, double p_i1778_2_, double p_i1778_4_, double p_i1778_6_) {
        super(p_i1778_1_, p_i1778_2_, p_i1778_4_, p_i1778_6_);
    }

    public EntityGrenade(World p_i1756_1_, EntityLivingBase p_i1756_2_, float p_i1756_3_) {
        this(p_i1756_1_, p_i1756_2_, p_i1756_3_, 10.0f);
    }

    public EntityGrenade(World p_i1756_1_, EntityLivingBase p_i1756_2_, float speed, float inac) {
        super(p_i1756_1_, p_i1756_2_, speed, inac);
    }

    public void onUpdate() {
        super.onUpdate();
        if (this.isBurning() || this.ticksExisted >= 46) {
            EntityPlayer player = this.getThrower() instanceof EntityPlayer ? (EntityPlayer) this.getThrower() : null;
            ExplosionUtils.makeExplosion(this.worldObj, player, this, this.posX, this.posY + (double) (this.height / 16.0F), this.posZ, 3.0f, false, false, false);
            this.setDead();
        }
    }

    @Override
    protected void onImpact(MovingObjectPosition movingobjectposition) {
        if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
            if (this.ticksExisted >= 5) {
                if (this.worldObj.isRemote) {
                    SoundUtils.playClientSound((int) this.posX, (int) this.posY, (int) this.posZ, Main.MODID + ":grenade_rico", 0.5F, 0.8F / (this.rand.nextFloat() * 0.2F + 0.4F));
                }
                this.motionX *= -0.1f;
                this.motionY *= 0.1f;
                this.motionZ *= -0.1f;
            }
        } else if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            Vector3fLWJGL posVec = new Vector3fLWJGL((float) movingobjectposition.hitVec.xCoord, (float) movingobjectposition.hitVec.yCoord, (float) movingobjectposition.hitVec.zCoord);
            Vector3fLWJGL motVec = new Vector3fLWJGL((float) motionX, (float) motionY, (float) motionZ);
            Vector3fLWJGL nextPosVec = Vector3fLWJGL.add(posVec, motVec, null);
            MovingObjectPosition hit = worldObj.rayTraceBlocks(Vec3.createVectorHelper(posVec.x, posVec.y, posVec.z), Vec3.createVectorHelper(nextPosVec.x, nextPosVec.y, nextPosVec.z), false, true, false);

            if (hit != null && hit.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                Vector3fLWJGL hitVec = new Vector3fLWJGL((float) hit.hitVec.xCoord, (float) hit.hitVec.yCoord, (float) hit.hitVec.zCoord);
                Vector3fLWJGL preHitMotVec = Vector3fLWJGL.sub(hitVec, posVec, null);
                Vector3fLWJGL postHitMotVec = Vector3fLWJGL.sub(motVec, preHitMotVec, null);

                int sideHit = hit.sideHit;
                switch (sideHit) {
                    case 0:
                    case 1:
                        postHitMotVec.setY(-postHitMotVec.getY());
                        break;
                    case 4:
                    case 5:
                        postHitMotVec.setX(-postHitMotVec.getX());
                        break;
                    case 2:
                    case 3:
                        postHitMotVec.setZ(-postHitMotVec.getZ());
                        break;
                }

                float lambda = Math.abs(motVec.lengthSquared()) < 1.0E-9D ? 1F : postHitMotVec.length() / motVec.length();
                postHitMotVec.scale(motVec.lengthSquared() > 0.1f ? 0.225f : 0);
                if (motVec.lengthSquared() > 0.01D) {
                    this.playSound(Main.MODID + ":grenade_rico", 0.5F, 0.8F / (this.rand.nextFloat() * 0.2F + 0.4F));
                }

                this.motionX = postHitMotVec.x / lambda;
                this.motionY = postHitMotVec.y / lambda;
                this.motionZ = postHitMotVec.z / lambda;
            }
        }
    }
}
