package ru.BouH_.entity.zombie;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
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
import net.minecraft.item.*;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.entity.ai.*;
import ru.BouH_.gameplay.WorldManager;
import ru.BouH_.init.ItemsZp;
import ru.BouH_.utils.EntityUtils;

import java.util.Iterator;
import java.util.List;

public class EntityZombieMiner extends AZombieBase {
    @SuppressWarnings("unchecked")
    public EntityZombieMiner(World p_i1745_1_) {
        super(p_i1745_1_);
        this.tasks.addTask(0, new AISwimming(this, 1.0f + Main.rand.nextFloat() * 0.4f, false));
        this.tasks.addTask(1, new AIAttack(this, EntityPlayer.class, this.getAttackRange(1.5f), 3.0f, true));
        this.tasks.addTask(1, new AIAttack(this, EntityVillager.class, this.getAttackRange(1.5f), 3.0f, true));
        this.tasks.addTask(1, new AIAttack(this, EntityAnimal.class, this.getAttackRange(1.5f), 3.0f, true));
        this.tasks.addTask(1, new AIAttack(this, EntityGolem.class, this.getAttackRange(1.5f), 3.0f, true));
        this.tasks.addTask(2, new AIMining(this, 12.5f, 0.925f));
        this.tasks.addTask(3, new AIAngry(this));
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
        return 4;
    }

    public void applyEntityAttributes() {
        super.applyEntityAttributes();
        float randomSpeed = 0.2f + Main.rand.nextFloat() * 0.05f;
        int i1 = this.worldObj.difficultySetting == EnumDifficulty.EASY || this.worldObj.difficultySetting == EnumDifficulty.NORMAL ? 30 : 40;
        int randomHealth = i1 + Main.rand.nextInt(21);
        float randomDamageSalt = Main.rand.nextFloat() * 3.0f;
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(48);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(randomSpeed);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0f + randomDamageSalt);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(randomHealth);
    }

    protected int maxYSpawn() {
        return (WorldManager.is7Night(this.worldObj) && Main.rand.nextFloat() <= 0.4f) || Main.rand.nextFloat() <= 0.1f ? 128 : 32;
    }

    protected Item getDropItem() {
        return Items.rotten_flesh;
    }

    public void dropRareDrop(int p_70600_1_) {
        super.dropRareDrop(p_70600_1_);
        switch (this.rand.nextInt(10)) {
            case 0: {
                this.dropItem(Items.iron_ingot, 1);
                break;
            }
            case 1: {
                this.dropItem(Items.carrot, 1);
                break;
            }
            case 2: {
                this.dropItem(Items.potato, 1);
                break;
            }
            case 3: {
                this.dropItem(ItemsZp.rock, 1);
                break;
            }
            case 4: {
                this.dropItem(Main.rand.nextBoolean() ? Items.bone : ItemsZp.fish_bones, 1);
                break;
            }
            case 5: {
                this.dropItem(ItemsZp.cola, 1);
                break;
            }
            case 6: {
                this.dropItem(ItemsZp.pepsi, 1);
                break;
            }
            case 7: {
                this.dropItem(ItemsZp.ananas, 1);
                break;
            }
            case 8: {
                this.dropItem(ItemsZp.pea, 1);
                break;
            }
            case 9: {
                this.dropItem(Items.string, 1);
                break;
            }
        }
    }

    protected void addRandomArmor() {
        if (rand.nextFloat() <= this.worldObj.difficultySetting.getDifficultyId() * 0.01f) {
            EntityZombieCitizen.addDefaultArmor(this.rand, this);
        }
        ItemStack stack = new ItemStack(Main.rand.nextFloat() <= 0.8f ? Items.iron_pickaxe : Items.iron_shovel);
        stack.setMetadata(rand.nextInt(stack.getMaxDurability()));
        this.setCurrentItemOrArmor(0, stack);
    }

    @SuppressWarnings("unchecked")
    protected void lootItem() {
        List<EntityItem> list = this.worldObj.getEntitiesWithinAABB(EntityItem.class, this.boundingBox.expand(1.5D, 2.0D, 1.5D));
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
                    int i = EntityLiving.getArmorPosition(itemstack);

                    if (i > -1) {
                        boolean flag = true;
                        ItemStack stackInSlot = this.getEquipmentInSlot(i);

                        if (stackInSlot != null) {
                            if (i == 0) {
                                if (!this.isEating()) {
                                    if (!(this.canEatItem(itemstack))) {
                                        if (stackInSlot.getItem() instanceof ItemTool) {
                                            if (itemstack.getItem() instanceof ItemTool) {
                                                if (stackInSlot.getItem() instanceof ItemTool) {
                                                    ItemTool itemtool = (ItemTool) itemstack.getItem();
                                                    ItemTool itemTool1 = (ItemTool) stackInSlot.getItem();
                                                    Block blockToCheck = this.getBlockToCheck(stackInSlot.getItem());
                                                    if (itemtool.getStrVsBlock(itemstack, blockToCheck) == itemTool1.getStrVsBlock(stackInSlot, blockToCheck)) {
                                                        flag = itemstack.getMetadata() > stackInSlot.getMetadata() || itemstack.hasTagCompound() && !stackInSlot.hasTagCompound();
                                                    } else {
                                                        flag = itemtool.getStrVsBlock(itemstack, blockToCheck) > itemTool1.getStrVsBlock(stackInSlot, blockToCheck);
                                                    }
                                                }
                                            } else {
                                                flag = false;
                                            }
                                        } else if (!(itemstack.getItem() instanceof ItemTool)) {
                                            if (itemstack.getItem() instanceof ItemSword) {
                                                if (stackInSlot.getItem() instanceof ItemSword) {
                                                    ItemSword itemsword = (ItemSword) itemstack.getItem();
                                                    ItemSword itemSword1 = (ItemSword) stackInSlot.getItem();
                                                    if (itemsword.func_150931_i() == itemSword1.func_150931_i()) {
                                                        flag = itemstack.getMetadata() > stackInSlot.getMetadata() || itemstack.hasTagCompound() && !stackInSlot.hasTagCompound();
                                                    } else {
                                                        flag = itemsword.func_150931_i() > itemSword1.func_150931_i();
                                                    }
                                                }
                                            } else {
                                                flag = false;
                                            }
                                        }
                                    }
                                } else if (this.canPickUpFood(itemstack)) {
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
                                    flag = false;
                                } else {
                                    flag = false;
                                }
                            } else if (stackInSlot.getItem() instanceof ItemArmor) {
                                if (itemstack.getItem() instanceof ItemArmor) {
                                    ItemArmor itemarmor = (ItemArmor) itemstack.getItem();
                                    ItemArmor itemArmor1 = (ItemArmor) stackInSlot.getItem();
                                    if (itemarmor.damageReduceAmount == itemArmor1.damageReduceAmount) {
                                        flag = itemstack.getMetadata() > stackInSlot.getMetadata() || itemstack.hasTagCompound() && !stackInSlot.hasTagCompound();
                                    } else {
                                        flag = itemarmor.damageReduceAmount > itemArmor1.damageReduceAmount;
                                    }
                                } else {
                                    flag = false;
                                }
                            }
                        }

                        if (!entityitem.isDead && flag) {
                            if (stackInSlot != null && this.rand.nextFloat() - 0.1F < this.equipmentDropChances[i]) {
                                this.entityDropItem(stackInSlot, 0.0F);
                            }
                            this.setCurrentItemOrArmor(i, itemstack);
                            this.equipmentDropChances[i] = 2.0F;
                            if (itemstack.getItem() instanceof ItemSword || itemstack.getItem() instanceof ItemTool || itemstack.getItem() instanceof ItemArmor) {
                                this.canUnSpawn = true;
                            }
                            this.onItemPickup(entityitem, 1);
                            entityitem.setDead();
                        }
                    }
                }
            }
        }
    }

    public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_110161_1_) {
        IEntityLivingData p_110161_1_1 = super.onSpawnWithEgg(p_110161_1_);
        this.addRandomArmor();
        return p_110161_1_1;
    }
}