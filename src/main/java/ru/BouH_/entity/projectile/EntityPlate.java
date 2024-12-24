package ru.BouH_.entity.projectile;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityBreakingFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.entity.zombie.AZombieBase;
import ru.BouH_.init.ItemsZp;
import ru.BouH_.utils.PluginUtils;

public class EntityPlate extends EntityThrowableZPBase {
    public EntityPlate(World p_i1776_1_) {
        super(p_i1776_1_);
    }

    public EntityPlate(World p_i1778_1_, double p_i1778_2_, double p_i1778_4_, double p_i1778_6_) {
        super(p_i1778_1_, p_i1778_2_, p_i1778_4_, p_i1778_6_);
    }

    public EntityPlate(World p_i1756_1_, EntityLivingBase p_i1756_2_, float p_i1756_3_) {
        this(p_i1756_1_, p_i1756_2_, p_i1756_3_, 10.0f);
    }

    public EntityPlate(World p_i1756_1_, EntityLivingBase p_i1756_2_, float speed, float inac) {
        super(p_i1756_1_, p_i1756_2_, speed, inac);
    }

    @SideOnly(Side.CLIENT)
    public void spawnParticlesImpact(double x, double y, double z) {
        for (int i = 0; i < 16; ++i) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityBreakingFX(this.worldObj, x, y, z, (Main.rand.nextGaussian() - this.motionX) * 0.05D, (Main.rand.nextGaussian() - this.motionY) * 0.05D + 0.1f, (Main.rand.nextGaussian() - this.motionZ) * 0.05D, ItemsZp.plate, 1));
        }
    }

    @Override
    protected void onImpact(MovingObjectPosition movingobjectposition) {
        if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.ENTITY) {
            Entity entity = movingobjectposition.entityHit;
            if (entity != null && entity.isEntityAlive()) {
                if (!(this.getThrower() instanceof AZombieBase && entity instanceof AZombieBase)) {
                    if (!this.worldObj.isRemote) {
                        entity.attackEntityFrom(DamageSource.causeThrownDamage(this, this.getThrower() == null ? this : this.getThrower()), 1);
                        this.worldObj.playSoundAtEntity(this, "dig.glass", 1F, 0.8F);
                        if (!(entity instanceof EntityEnderman)) {
                            this.setDead();
                        }
                    }
                }
            }
        } else if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            Block inTile = this.worldObj.getBlock(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ);
            int hardness = (int) inTile.getBlockHardness(this.worldObj, movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ);
            if (!this.worldObj.isRemote) {
                this.worldObj.playSoundAtEntity(this, "dig.glass", 1F, 0.8F);
                if (this.worldObj.getGameRules().getGameRuleBooleanValue("zombiesCanMine") && this.getThrower() instanceof AZombieBase && hardness >= 0 && hardness <= 10 && rand.nextInt(hardness + 6) == 0) {
                    if (!PluginUtils.isInPrivate2(worldObj, movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ)) {
                        worldObj.breakBlock(movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ, false);
                    }
                    inTile.onEntityCollidedWithBlock(this.worldObj, movingobjectposition.blockX, movingobjectposition.blockY, movingobjectposition.blockZ, this);
                }
            } else {
                this.spawnParticlesImpact(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
            }
            this.setDead();
        }
    }
}
