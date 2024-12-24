package ru.BouH_.entity.ai;

import net.minecraft.block.BlockTrapDoor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import ru.BouH_.Main;
import ru.BouH_.entity.zombie.AZombieBase;
import ru.BouH_.utils.EntityUtils;

import java.util.List;

public class AIAttack extends EntityAIBase {
    protected final AZombieBase attacker;
    protected final double attackRange;
    protected final Class<? extends EntityLivingBase> classTarget;
    protected final float seekCdMultiplier;
    protected final boolean canOpenTrapDoors;
    protected int attackTick;
    protected PathEntity entityPathEntity;
    protected int seekCd;

    public AIAttack(AZombieBase p_i1636_1_, Class<? extends EntityLivingBase> p_i1635_2_, double attackRange, float seekCdMultiplier, boolean canOpenTrapDoors) {
        this.attacker = p_i1636_1_;
        this.attackRange = attackRange;
        this.setMutexBits(3);
        this.classTarget = p_i1635_2_;
        this.seekCdMultiplier = seekCdMultiplier;
        this.canOpenTrapDoors = canOpenTrapDoors;
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
        if (entitylivingbase != null && entitylivingbase.isEntityAlive()) {
            this.attacker.getLookHelper().setLookPositionWithEntity(entitylivingbase, 30.0F, 30.0F);
            return true;
        }
        return false;
    }

    public void startExecuting() {
        this.attacker.getNavigator().setPath(this.entityPathEntity, 1.0f);
        this.seekCd = 0;
    }

    public void resetTask() {
        this.attacker.getNavigator().clearPathEntity();
    }

    @SuppressWarnings("unchecked")
    public void updateTask() {
        EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
        EntityLivingBase entityToFollow = this.attacker.getAttackTarget();

        double d1 = this.attacker.getDistance(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ);
        if (this.seekCd <= 0) {
            this.seekCd = (int) (d1 * this.seekCdMultiplier);
            this.attacker.getNavigator().tryMoveToEntityLiving(entityToFollow, 1.0f);
        } else {
            this.seekCd--;
        }

        if (this.attackTick <= 0) {
            if (entitylivingbase.isEntityAlive()) {
                if (this.canOpenTrapDoors && this.attacker.getNavigator().noPath()) {
                    this.tryOpenTrapDoor();
                }
                float f1 = 0.3f;
                List<Entity> entities = this.attacker.worldObj.getEntitiesWithinAABB(AZombieBase.class, this.attacker.boundingBox.expand(1.5f, 1.5f, 1.5f));
                float f2 = Math.min((entities.size() - 1) * 0.05f, 0.5f);
                f1 -= f2 * 0.25f;
                double range = this.attackRange - f2;
                double const1 = EntityUtils.canEntitySeeEntity(this.attacker, entitylivingbase, false) ? Main.rand.nextFloat() * f1 + (range + this.attacker.width * 0.5f) : 1.0f;
                if (d1 <= const1) {
                    if (this.attacker.attackEntityAsMob(entitylivingbase)) {
                        this.attacker.swingItem();
                        if (Main.rand.nextFloat() <= (entitylivingbase.worldObj.difficultySetting == EnumDifficulty.EASY ? 0.01f : entitylivingbase.worldObj.difficultySetting == EnumDifficulty.NORMAL ? 0.015f : 0.025f)) {
                            switch (Main.rand.nextInt(4)) {
                                case 0: {
                                    entitylivingbase.addPotionEffect(new PotionEffect(19, 400));
                                    break;
                                }
                                case 1: {
                                    entitylivingbase.addPotionEffect(new PotionEffect(17, 12000));
                                    break;
                                }
                                case 2: {
                                    entitylivingbase.addPotionEffect(new PotionEffect(9, 12000));
                                    break;
                                }
                                case 3: {
                                    entitylivingbase.addPotionEffect(new PotionEffect(31, 12000));
                                    break;
                                }
                            }
                        }
                        if (entitylivingbase instanceof EntityPlayer && Main.rand.nextFloat() <= 0.01f) {
                            ((EntityPlayer) entitylivingbase).dropOneItem(true);
                        }

                        if (!entitylivingbase.isPotionActive(26) && Main.rand.nextFloat() <= 0.003f) {
                            entitylivingbase.addPotionEffect(new PotionEffect(26, 21600));
                        }
                        this.attackTick = 20;
                    }
                }
            }
        } else {
            this.attackTick--;
        }
    }

    private void tryOpenTrapDoor() {
        if (attacker.ticksExisted % 200 == 0) {
            int rotation = MathHelper.floor_double((double) (this.attacker.rotationYawHead * 4.0F / 360.0F) + 0.5D) & 3;
            int plX = MathHelper.floor_double(this.attacker.posX);
            int plY = (int) this.attacker.posY;
            int plZ = MathHelper.floor_double(this.attacker.posZ);
            int oldX = plX;
            int oldZ = plZ;
            switch (rotation) {
                case 0: {
                    oldZ += 1;
                    break;
                }
                case 1: {
                    oldX -= 1;
                    break;
                }
                case 2: {
                    oldZ -= 1;
                    break;
                }
                case 3: {
                    oldX += 1;
                    break;
                }
                default: {
                    break;
                }
            }
            if (this.attacker.worldObj.getBlock(oldX, plY - 1, oldZ) instanceof BlockTrapDoor) {
                if (BlockTrapDoor.isTrapdoorOpen(this.attacker.worldObj.getBlockMetadata(oldX, plY - 1, oldZ))) {
                    BlockTrapDoor blockTrapDoor = (BlockTrapDoor) this.attacker.worldObj.getBlock(oldX, plY - 1, oldZ);
                    blockTrapDoor.func_150120_a(attacker.worldObj, oldX, plY - 1, oldZ, false);
                    this.attacker.swingItem();
                }
            } else if (this.attacker.worldObj.getBlock(oldX, plY, oldZ) instanceof BlockTrapDoor) {
                if (BlockTrapDoor.isTrapdoorOpen(this.attacker.worldObj.getBlockMetadata(oldX, plY, oldZ))) {
                    BlockTrapDoor blockTrapDoor = (BlockTrapDoor) this.attacker.worldObj.getBlock(oldX, plY, oldZ);
                    blockTrapDoor.func_150120_a(attacker.worldObj, oldX, plY, oldZ, false);
                    this.attacker.swingItem();
                }
            }
        }
    }
}
