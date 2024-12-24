package ru.BouH_.entity.zombie;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityBreakingFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.entity.ai.AIAttack;
import ru.BouH_.entity.ai.AISpecialFindTarget;
import ru.BouH_.entity.ai.AISwimming;
import ru.BouH_.init.ItemsZp;
import ru.BouH_.utils.EntityUtils;

import java.util.Iterator;
import java.util.List;

public class EntityZombieWolf extends AZombieBase {
    private int stunTicks;
    private boolean canBeStunned;

    @SuppressWarnings("unchecked")
    public EntityZombieWolf(World p_i1745_1_) {
        super(p_i1745_1_);
        this.setSize(0.6F, 0.8F);
        this.canThrowTrash = false;
        this.canReceiveHeadshot = false;
        this.canBeStunned = true;
        this.tasks.addTask(0, new AISwimming(this, 0.3f + Main.rand.nextFloat() * 0.4f, false));
        this.tasks.addTask(1, new AIAttack(this, EntityPlayer.class, this.getAttackRange(1.0f), 1.25f, true));
        this.tasks.addTask(1, new AIAttack(this, EntityVillager.class, this.getAttackRange(1.0f), 1.25f, true));
        this.tasks.addTask(1, new AIAttack(this, EntityAnimal.class, this.getAttackRange(1.0f), 1.25f, true));
        this.tasks.addTask(1, new AIAttack(this, EntityGolem.class, this.getAttackRange(1.0f), 1.25f, true));
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.65F));
        this.tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(5, new EntityAIMoveThroughVillage(this, 1.0D, false));
        this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));

        this.targetTasks.addTask(1, new AISpecialFindTarget(this, new Class[]{EntityPlayerMP.class, EntityVillager.class, EntityGolem.class}, 28, 12));
        this.targetTasks.addTask(2, new AISpecialFindTarget(this, new Class[]{EntityAnimal.class}, 8, 4));
    }

    @Override
    protected int minDayToSpawn() {
        return 3;
    }

    public void applyEntityAttributes() {
        super.applyEntityAttributes();
        int i1 = this.worldObj.difficultySetting == EnumDifficulty.EASY ? 12 : this.worldObj.difficultySetting == EnumDifficulty.NORMAL ? 16 : 20;
        int randomHealth = i1 + Main.rand.nextInt(7);
        float randomDamageSalt = Main.rand.nextFloat();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(48);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0f + randomDamageSalt);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(randomHealth);
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.stunTicks > 0) {
            this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23f);
            this.stunTicks--;
        } else {
            this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.36f);
        }
    }

    protected int minYSpawn() {
        return 42;
    }

    protected Item getDropItem() {
        return Items.rotten_flesh;
    }

    public void dropRareDrop(int p_70600_1_) {
        super.dropRareDrop(p_70600_1_);
    }

    protected void addRandomArmor() {
        if (Main.rand.nextFloat() <= 0.02f) {
            this.setCurrentItemOrArmor(0, new ItemStack(Main.rand.nextBoolean() ? Items.bone : ItemsZp.fish_bones));
        }
    }

    public boolean attackEntityAsMob(Entity p_70652_1_) {
        this.heal(4.0f);
        this.canBeStunned = false;
        return super.attackEntityAsMob(p_70652_1_);
    }

    public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_) {
        if (this.canBeStunned) {
            this.stunTicks = 120;
        } else if (Main.rand.nextFloat() <= 0.2f) {
            this.stunTicks = 50;
        }
        return super.attackEntityFrom(p_70097_1_, p_70097_2_);
    }

    protected String getLivingSound() {
        return "mob.wolf.growl";
    }

    protected String getHurtSound() {
        return "mob.wolf.hurt";
    }

    protected String getDeathSound() {
        return "mob.wolf.death";
    }

    @SuppressWarnings("unchecked")
    protected void lootItem() {
        List<EntityItem> list = this.worldObj.getEntitiesWithinAABB(EntityItem.class, this.boundingBox.expand(1.5D, 0.0D, 1.5D));
        Iterator<EntityItem> iterator = list.iterator();

        if (this.ticksExisted % 20 == 0) {
            List<EntityItem> list2 = this.worldObj.getEntitiesWithinAABB(EntityItem.class, this.boundingBox.expand(4.5D, 2.0D, 4.5D));
            for (EntityItem entityitem : list2) {
                if (entityitem.delayBeforeCanPickup <= 0 && this.canEatItem(entityitem.getEntityItem())) {
                    if (this.canPickUpFood(entityitem.getEntityItem()) && this.getAttackTarget() == null && EntityUtils.canEntitySeeEntity(this, entityitem, false)) {
                        this.getNavigator().tryMoveToEntityLiving(entityitem, 1);
                    }
                }
            }
        }

        while (iterator.hasNext()) {
            EntityItem entityitem = iterator.next();
            if (entityitem.getEntityItem() != null) {
                Vec3 vecItem = Vec3.createVectorHelper(entityitem.posX, entityitem.posY + entityitem.yOffset, entityitem.posZ);
                Vec3 vecMob = Vec3.createVectorHelper(this.posX, this.posY + this.getEyeHeight(), this.posZ);
                if (this.worldObj.rayTraceBlocks(vecItem, vecMob, false, true, false) == null) {
                    ItemStack itemstack = entityitem.getEntityItem();
                    ItemStack stackInSlot = this.getHeldItem();
                    boolean flag = false;
                    if (itemstack != null) {
                        if (this.isEating()) {
                            if (this.canPickUpFood(itemstack)) {
                                ItemStack itemStack = this.getHeldItem();
                                ItemStack itemStack1 = entityitem.getEntityItem();
                                int i1 = itemStack.stackSize + itemStack1.stackSize;
                                if (i1 <= itemStack.getMaxStackSize()) {
                                    itemStack.stackSize += itemStack1.stackSize;
                                    this.onItemPickup(entityitem, 1);
                                    entityitem.setDead();
                                } else {
                                    itemStack.stackSize = itemStack.getMaxStackSize();
                                    itemStack1.stackSize = i1 - itemStack.getMaxStackSize();
                                }
                            }
                        } else if (this.canEatItem(itemstack)) {
                            flag = true;
                        }

                        if (!entityitem.isDead && flag) {
                            if (stackInSlot != null) {
                                this.entityDropItem(stackInSlot, 0.0F);
                            }
                            this.equipmentDropChances[0] = 2.0F;
                            this.setCurrentItemOrArmor(0, itemstack);
                            this.onItemPickup(entityitem, 1);
                            entityitem.setDead();
                        }
                    }
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    protected void spawnParticlesEating() {
        float const1, const2;
        const1 = (-MathHelper.sin(this.rotationYawHead / 180.0F * (float) Math.PI)) * 0.85f;
        const2 = (MathHelper.cos(this.rotationYawHead / 180.0F * (float) Math.PI)) * 0.85f;
        for (int i = 0; i < 2; i++) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityBreakingFX(this.worldObj, this.posX + const1, this.posY + this.getEyeHeight(), this.posZ + const2, Main.rand.nextGaussian() * 0.06D, Main.rand.nextDouble() * 0.3D, Main.rand.nextGaussian() * 0.06D, this.getHeldItem().getItem(), this.getHeldItem().getCurrentDurability()));
        }
    }

    public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_110161_1_) {
        IEntityLivingData p_110161_1_1 = super.onSpawnWithEgg(p_110161_1_);
        this.addRandomArmor();
        return p_110161_1_1;
    }
}