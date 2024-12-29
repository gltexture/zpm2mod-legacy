package ru.BouH_.entity.zombie;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.entity.ai.*;
import ru.BouH_.entity.particle.EntityParticleSpark;
import ru.BouH_.init.ItemsZp;
import ru.BouH_.utils.ExplosionUtils;
import ru.BouH_.utils.SoundUtils;

public class EntityZombieMilitary extends AZombieBase {
    private final Item baseItem = ItemsZp.tnt;
    private int explodeTicks;

    @SuppressWarnings("unchecked")
    public EntityZombieMilitary(World p_i1745_1_) {
        super(p_i1745_1_);
        this.tasks.addTask(0, new AISwimming(this, 1.2f + Main.rand.nextFloat() * 0.4f, false));
        this.tasks.addTask(1, new AIAttack(this, EntityPlayer.class, this.getAttackRange(1.6f), 3.0f, true));
        this.tasks.addTask(1, new AIAttack(this, EntityVillager.class, this.getAttackRange(1.6f), 3.0f, true));
        this.tasks.addTask(1, new AIAttack(this, EntityAnimal.class, this.getAttackRange(1.6f), 3.0f, true));
        this.tasks.addTask(1, new AIAttack(this, EntityGolem.class, this.getAttackRange(1.6f), 3.0f, true));
        this.tasks.addTask(2, new AIMining(this, 6.0f, 0.85f));
        this.tasks.addTask(3, new AIAngry(this));
        this.tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(5, new EntityAIMoveThroughVillage(this, 1.0D, false));
        this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));

        this.targetTasks.addTask(1, new AISpecialFindTarget(this, new Class[]{EntityPlayerMP.class, EntityVillager.class, EntityGolem.class}, 28, 12));
        this.targetTasks.addTask(2, new AISpecialFindTarget(this, new Class[]{EntityAnimal.class}, 8, 4));
        this.standardArmor = 8;
    }

    @Override
    protected int minDayToSpawn() {
        return 5;
    }

    protected void entityInit() {
        super.entityInit();
        this.getDataWatcher().addObject(14, (byte) 0);
    }

    public void applyEntityAttributes() {
        super.applyEntityAttributes();
        float randomSpeed = 0.2f + Main.rand.nextFloat() * 0.05f;
        int i1 = this.worldObj.difficultySetting == EnumDifficulty.EASY || this.worldObj.difficultySetting == EnumDifficulty.NORMAL ? 50 : 80;
        int randomHealth = i1 + Main.rand.nextInt(11);
        float randomDamageSalt = Main.rand.nextFloat() * 2.0f;
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(48);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(randomSpeed);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0f + randomDamageSalt);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(randomHealth);
    }

    protected Item getDropItem() {
        return Items.rotten_flesh;
    }

    protected boolean canPickUpTools() {
        return false;
    }

    public void onLivingUpdate() {
        boolean flag = this.getDataWatcher().getWatchableObjectByte(14) == (byte) 1;
        if (this.worldObj.isRemote) {
            if (flag) {
                if (this.isEntityAlive()) {
                    this.spawnParticles2();
                }
            }
        } else {
            World world = this.worldObj;
            ItemStack stack = this.getHeldItem();
            if (stack != null && stack.getItem() == this.baseItem) {
                if (!flag) {
                    if (this.isEntityAlive()) {
                        if (this.ticksExisted % 20 == 0 && this.getAttackTarget() != null && this.getAttackTarget().isEntityAlive() && this.getAttackTarget() instanceof EntityPlayer) {
                            if (!this.isEating() && this.getHealth() < this.getMaxHealth() / 2.0f) {
                                if (!this.getNavigator().noPath() && this.getDistanceToEntity(this.getAttackTarget()) <= 16) {
                                    this.getDataWatcher().updateObject(14, (byte) 1);
                                    this.explodeTicks = 100;
                                    this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.335f);
                                    this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0f);
                                }
                            }
                        }
                    }
                } else {
                    this.canPickUp = false;
                    if (this.getHealth() <= 0.0f || this.explodeTicks <= 0) {
                        this.setCurrentItemOrArmor(0, null);
                        ExplosionUtils.makeExplosion(world, null, this, this.posX, this.posY + (double) (this.height / 16.0F), this.posZ, 3.0f, false, true, false);
                        this.dropEquipment(this.recentlyHit > 0, 0);
                        this.setDead();
                    } else {
                        this.explodeTicks -= 1;
                    }
                }
            }
        }
        super.onLivingUpdate();
    }

    protected ItemStack onEaten(ItemStack stack) {
        ItemStack stack1 = super.onEaten(stack);
        if (stack1 == null) {
            this.equipmentDropChances[0] = 0.0F;
            return new ItemStack(this.baseItem);
        }
        return stack1;
    }

    @SideOnly(Side.CLIENT)
    public void spawnParticles2() {
        double const1 = (-MathHelper.sin(this.rotationYawHead / 180.0F * (float) Math.PI)) * 0.6f;
        double const2 = (MathHelper.cos(this.rotationYawHead / 180.0F * (float) Math.PI)) * 0.6f;
        for (int i = 0; i < 2; i++) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleSpark(this.worldObj, this.posX + const1, this.posY + this.getEyeHeight(), this.posZ + const2, 0.0f, 0.4f, 0.0f));
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntitySmokeFX(this.worldObj, this.posX + const1, this.posY + this.getEyeHeight(), this.posZ + const2, 0.0D, 0.0D, 0.0D));
        }
        this.playSound();
    }

    @SideOnly(Side.CLIENT)
    public void playSound() {
        if (this.ticksExisted == 1 || this.ticksExisted % 20 == 0) {
            SoundUtils.playClientMovingSound(this, "game.tnt.primed", 3.0f, 1.0f);
        }
    }

    public void dropRareDrop(int p_70600_1_) {
        super.dropRareDrop(p_70600_1_);
        switch (Main.rand.nextInt(2)) {
            case 0: {
                this.dropItem(Items.gunpowder, 1);
                break;
            }
            case 1: {
                this.dropItem(Items.glowstone_dust, 2);
                break;
            }
        }
    }

    public void writeEntityToNBT(NBTTagCompound p_70014_1_) {
        super.writeEntityToNBT(p_70014_1_);
        p_70014_1_.setInteger("explodeTicks", this.explodeTicks);
        p_70014_1_.setByte("isReadyToExplode", this.getDataWatcher().getWatchableObjectByte(14));
    }

    public void readEntityFromNBT(NBTTagCompound p_70037_1_) {
        super.readEntityFromNBT(p_70037_1_);
        this.explodeTicks = p_70037_1_.getInteger("explodeTicks");
        this.getDataWatcher().updateObject(14, p_70037_1_.getByte("isReadyToExplode"));
    }

    public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_110161_1_) {
        this.setCurrentItemOrArmor(0, new ItemStack(this.baseItem));
        this.equipmentDropChances[0] = 0.0F;
        return super.onSpawnWithEgg(p_110161_1_);
    }
}