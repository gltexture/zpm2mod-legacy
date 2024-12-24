package ru.BouH_.entity.zombie;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
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
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.entity.ai.*;
import ru.BouH_.entity.particle.EntityParticleSwampCloud;
import ru.BouH_.init.ItemsZp;
import ru.BouH_.utils.EntityUtils;

import java.util.Iterator;
import java.util.List;

public class EntityZombieSwamp extends AZombieBase {

    @SuppressWarnings("unchecked")
    public EntityZombieSwamp(World p_i1745_1_) {
        super(p_i1745_1_);
        this.setSize(0.6F, 1.5F);
        this.tasks.addTask(0, new AISwimming(this, 1.2f + Main.rand.nextFloat() * 0.4f, false));
        this.tasks.addTask(1, new AISwampAttack(this, EntityPlayer.class, 3.5f));
        this.tasks.addTask(1, new AISwampAttack(this, EntityVillager.class, 3.5f));
        this.tasks.addTask(1, new AIAttack(this, EntityAnimal.class, this.getAttackRange(1.5f), 3.5f, true));
        this.tasks.addTask(1, new AISwampAttack(this, EntityGolem.class, 3.5f));
        this.tasks.addTask(2, new AIMining(this, 3.0f, 0.95f));
        this.tasks.addTask(3, new AIAngry(this));
        this.tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 1.0D));
        this.tasks.addTask(5, new EntityAIMoveThroughVillage(this, 1.0D, false));
        this.tasks.addTask(6, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));

        this.targetTasks.addTask(1, new AISpecialFindTarget(this, new Class[]{EntityPlayerMP.class, EntityVillager.class, EntityGolem.class}, 28, 12));
    }

    @Override
    protected int minDayToSpawn() {
        return 3;
    }

    public void applyEntityAttributes() {
        super.applyEntityAttributes();
        float randomSpeed = 0.27f + Main.rand.nextFloat() * 0.03f;
        int i1 = this.worldObj.difficultySetting == EnumDifficulty.EASY || this.worldObj.difficultySetting == EnumDifficulty.NORMAL ? 20 : 30;
        int randomHealth = i1 + Main.rand.nextInt(11);
        float randomDamageSalt = Main.rand.nextFloat() * 3.0f;
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(48);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(randomSpeed);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0f + randomDamageSalt);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(randomHealth);
    }

    protected Item getDropItem() {
        return Main.rand.nextBoolean() ? Items.rotten_flesh : ItemsZp.rot_mass;
    }

    protected int minYSpawn() {
        return 42;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
        World world = this.worldObj;
        if (world.isRemote) {
            if (this.ticksExisted % 5 == 0) {
                this.spawnParticles();
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private void spawnParticles() {
        float const1, const2;
        const1 = (-MathHelper.sin(this.rotationYawHead / 180.0F * (float) Math.PI)) * 0.35f;
        const2 = (MathHelper.cos(this.rotationYawHead / 180.0F * (float) Math.PI)) * 0.35f;
        Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleSwampCloud(this.worldObj, this.posX + const1, this.posY + this.getEyeHeight() - 0.12f, this.posZ + const2));
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
        if (super.addRandomBackpack()) {
            return;
        }
        switch (rand.nextInt(12)) {
            case 0: {
                ItemStack stack = new ItemStack(Main.rand.nextFloat() <= 0.7f ? Items.stone_sword : Items.iron_sword);
                stack.setMetadata(rand.nextInt(stack.getMaxDurability()));
                this.setCurrentItemOrArmor(0, stack);
                break;
            }
            case 1: {
                ItemStack stack = new ItemStack(Main.rand.nextFloat() <= 0.7f ? Items.stone_axe : Items.iron_axe);
                stack.setMetadata(rand.nextInt(stack.getMaxDurability()));
                this.setCurrentItemOrArmor(0, stack);
                break;
            }
            case 2: {
                ItemStack stack = new ItemStack(Main.rand.nextFloat() <= 0.7f ? Items.stone_shovel : Items.iron_shovel);
                stack.setMetadata(rand.nextInt(stack.getMaxDurability()));
                this.setCurrentItemOrArmor(0, stack);
                break;
            }
            case 3: {
                ItemStack stack = new ItemStack(Main.rand.nextFloat() <= 0.7f ? Items.wooden_pickaxe : Items.stone_pickaxe);
                stack.setMetadata(rand.nextInt(stack.getMaxDurability()));
                this.setCurrentItemOrArmor(0, stack);
                break;
            }
        }
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

    public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_110161_1_) {
        IEntityLivingData p_110161_1_1 = super.onSpawnWithEgg(p_110161_1_);
        this.addRandomArmor();
        return p_110161_1_1;
    }
}