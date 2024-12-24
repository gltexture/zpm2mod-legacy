package ru.BouH_.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.MathHelper;
import ru.BouH_.Main;
import ru.BouH_.entity.projectile.EntityRot;
import ru.BouH_.entity.zombie.EntityZombieSwamp;
import ru.BouH_.utils.EntityUtils;

public class AISwampAttack extends EntityAIBase {
    private final EntityZombieSwamp attacker;
    private final Class<? extends EntityLivingBase> classTarget;
    private final float seekCdMultiplier;
    private final float maxDistanceToAttack;
    private final float maxTicksToAttack;
    private PathEntity entityPathEntity;
    private int seekCd;
    private int chargingTicks;

    public AISwampAttack(EntityZombieSwamp p_i1636_1_, Class<? extends EntityLivingBase> p_i1635_2_, float seekCdMultiplier) {
        this.attacker = p_i1636_1_;
        this.setMutexBits(3);
        this.classTarget = p_i1635_2_;
        this.seekCdMultiplier = seekCdMultiplier;
        this.maxDistanceToAttack = Main.rand.nextInt(11) + 20;
        this.maxTicksToAttack = Main.rand.nextInt(21) + 60;
    }

    public boolean shouldExecute() {
        EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
        if (entitylivingbase == null) {
            return false;
        } else if (!entitylivingbase.isEntityAlive()) {
            return false;
        } else if (this.classTarget != null && !this.classTarget.isAssignableFrom(entitylivingbase.getClass())) {
            return false;
        } else {
            if (this.seekCd <= 0) {
                this.entityPathEntity = this.attacker.getNavigator().getPathToEntityLiving(entitylivingbase);
                this.seekCd = 3;
                return this.entityPathEntity != null;
            } else {
                this.seekCd--;
                return true;
            }
        }
    }

    public boolean continueExecuting() {
        EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
        return entitylivingbase != null && entitylivingbase.isEntityAlive();
    }

    public void startExecuting() {
        this.attacker.getNavigator().setPath(this.entityPathEntity, 1.0f);
        this.chargingTicks = 0;
        this.seekCd = 0;
    }

    public void resetTask() {
        this.attacker.getNavigator().clearPathEntity();
    }

    public void updateTask() {
        EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
        double distanceToEntity = this.attacker.getDistanceToEntity(entitylivingbase);
        if (this.checkEntity(entitylivingbase, this.maxDistanceToAttack * 0.5f)) {
            this.attacker.canMine = false;
            this.attacker.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);
            this.attacker.getNavigator().clearPathEntity();
        } else {
            this.attacker.canMine = true;
            if (this.seekCd <= 0) {
                this.seekCd = (int) (distanceToEntity * this.seekCdMultiplier);
                this.attacker.getNavigator().tryMoveToEntityLiving(entitylivingbase, 1.0f);
            } else {
                this.seekCd--;
            }
        }
        if (this.checkEntity(entitylivingbase, this.maxDistanceToAttack)) {
            if (this.chargingTicks++ >= this.maxTicksToAttack) {
                this.attacker.swingItem();
                EntityRot entityRot = new EntityRot(this.attacker.worldObj, this.attacker, 0.0f);
                this.attacker.worldObj.playSoundAtEntity(this.attacker, Main.MODID + ":impactmeat", 1F, 0.6F);
                double d0 = entitylivingbase.posY + entitylivingbase.getEyeHeight() - 1.1f;
                double d1 = entitylivingbase.posX - this.attacker.posX;
                double d2 = d0 - entityRot.posY;
                double d3 = entitylivingbase.posZ - this.attacker.posZ;
                float f = MathHelper.sqrt_double(d1 * d1 + d3 * d3) * 0.2F;
                entityRot.setThrowableHeading(d1, d2 + (double) f, d3, (float) ((distanceToEntity * 0.05f) + 1.0f), 8.0f);
                this.attacker.worldObj.spawnEntityInWorld(entityRot);
                this.chargingTicks = 0;
            }
        }
    }

    private boolean checkEntity(EntityLivingBase entityLivingBase, float distance) {
        return this.attacker.getDistanceToEntity(entityLivingBase) <= distance && this.attacker.getDistanceToEntity(entityLivingBase) <= 24 && EntityUtils.canEntitySeeEntity(this.attacker, entityLivingBase, false);
    }
}
