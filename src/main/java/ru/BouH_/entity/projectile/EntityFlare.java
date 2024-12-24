package ru.BouH_.entity.projectile;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import ru.BouH_.ConfigZp;
import ru.BouH_.Main;
import ru.BouH_.achievements.AchievementManager;
import ru.BouH_.entity.particle.EntityParticleColoredCloud;
import ru.BouH_.entity.particle.EntityParticleFlareFlame;
import ru.BouH_.lwjgl_vector.Vector3fLWJGL;
import ru.BouH_.utils.PluginUtils;
import ru.BouH_.utils.SoundUtils;
import ru.BouH_.weather.base.WeatherHandler;

public class EntityFlare extends EntityThrowableZPBase {
    public boolean canStartAirdrop;
    private double startY;

    public EntityFlare(World p_i1776_1_) {
        super(p_i1776_1_);
    }

    public EntityFlare(World p_i1756_1_, EntityLivingBase p_i1756_2_, float speed, float inac) {
        super(p_i1756_1_, p_i1756_2_, speed, inac);
        this.startY = this.posY;
    }

    @SideOnly(Side.CLIENT)
    public void spawnParticles() {
        for (int i = 0; i < 2; i++) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleFlareFlame(this.worldObj, this.posX, this.posY, this.posZ, 7.0f));
        }
    }

    @SideOnly(Side.CLIENT)
    public void spawnParticles2() {
        for (int i = 0; i < 4; i++) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleColoredCloud(this.worldObj, this.posX, this.posY, this.posZ, Main.rand.nextGaussian() * 0.03D, Main.rand.nextGaussian() * 0.03D, Main.rand.nextGaussian() * 0.03D, new float[]{0.9f, 0.9f, 0.9f}, 1.2f - rand.nextFloat() * 0.4f));
        }
    }

    public void onUpdate() {
        super.onUpdate();
        if (!this.worldObj.isRemote) {
            if (!this.canStartAirdrop) {
                if (this.dimension == 0 || this.dimension == 2) {
                    if (this.thrower != null && this.worldObj.canBlockSeeTheSky(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) && this.posY - this.startY > 32) {
                        this.canStartAirdrop = true;
                    }
                }
            }
        }

        if (this.ticksExisted >= 600) {
            if (!this.worldObj.isRemote) {
                if (ConfigZp.isAirdropEnabled) {
                    if (WeatherHandler.instance.getWeatherFog().currentFogDepth <= 0) {
                        if (this.canStartAirdrop) {
                            int x = (int) (this.posX - 64 + Main.rand.nextInt(129));
                            int z = (int) (this.posZ - 64 + Main.rand.nextInt(129));
                            int y = this.worldObj.getPrecipitationHeight(x, z);
                            this.worldObj.playSoundEffect(x, y + 32, z, Main.MODID + ":airdrop", 16.0f, 1.0f);
                            if (this.getThrower() instanceof EntityPlayer) {
                                AchievementManager.instance.triggerAchievement(AchievementManager.instance.airdrop, (EntityPlayer) this.getThrower());
                            }
                            if (!PluginUtils.isInPrivate2(this.worldObj, x, y, z)) {
                                EntityFlare2 flare2 = new EntityFlare2(this.worldObj, x + 0.5f, 256, z + 0.5f);
                                this.worldObj.spawnEntityInWorld(flare2);
                            }
                        }
                    }
                }
            }
            this.setDead();
        }

        if (this.worldObj.isRemote) {
            if (this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY + 0.5f), MathHelper.floor_double(this.posZ)).getMaterial() == Material.water) {
                if (this.ticksExisted == 1 || this.ticksExisted % 4 == 0) {
                    SoundUtils.playClientMovingSound(this, "random.fizz", 0.6f, 1.0f);
                }
                this.spawnParticles2();
            }
            this.spawnParticles();
            this.playSound();
        }
    }

    protected float getGravityVelocity() {
        return 0.01F;
    }

    @SideOnly(Side.CLIENT)
    public void playSound() {
        if (this.ticksExisted == 1 || this.ticksExisted % 25 == 0) {
            SoundUtils.playClientMovingSound(this, Main.MODID + ":flare", 5.5f, 4.0f);
        }
    }

    @Override
    protected void onImpact(MovingObjectPosition movingobjectposition) {
        if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
            if (movingobjectposition.entityHit instanceof EntityLivingBase) {
                EntityLivingBase entity = (EntityLivingBase) movingobjectposition.entityHit;
                if (!this.worldObj.isRemote) {
                    entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower()), 10);
                    entity.setFire(10);
                }
            }
            if (this.ticksExisted >= 5) {
                if (this.worldObj.isRemote) {
                    SoundUtils.playClientSound((int) this.posX, (int) this.posY, (int) this.posZ, Main.MODID + ":grenade_rico", 0.5F, 0.8F / (this.rand.nextFloat() * 0.2F + 0.4F));
                }
                this.motionX *= -0.1f;
                this.motionY *= 0.1f;
                this.motionZ *= -0.1f;
            }
        } else if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            Vector3fLWJGL posVec = new Vector3fLWJGL((float) posX, (float) posY, (float) posZ);
            Vector3fLWJGL motVec = new Vector3fLWJGL((float) motionX, (float) motionY, (float) motionZ);
            Vector3fLWJGL nextPosVec = Vector3fLWJGL.add(posVec, motVec, null);
            MovingObjectPosition hit = worldObj.rayTraceBlocks(Vec3.createVectorHelper(posVec.x, posVec.y, posVec.z), Vec3.createVectorHelper(nextPosVec.x, nextPosVec.y, nextPosVec.z));

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

    public void writeEntityToNBT(NBTTagCompound p_70014_1_) {
        p_70014_1_.setDouble("startY", this.startY);
        p_70014_1_.setBoolean("canStartAirdrop", this.canStartAirdrop);
    }

    public void readEntityFromNBT(NBTTagCompound p_70037_1_) {
        this.startY = p_70037_1_.getDouble("startY");
        this.canStartAirdrop = p_70037_1_.getBoolean("canStartAirdrop");
    }
}
