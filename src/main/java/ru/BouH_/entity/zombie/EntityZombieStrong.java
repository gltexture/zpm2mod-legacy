package ru.BouH_.entity.zombie;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.entity.ai.*;
import ru.BouH_.gameplay.WorldManager;
import ru.BouH_.init.ItemsZp;
import ru.BouH_.world.biome.BiomeCity;

public class EntityZombieStrong extends AZombieBase {

    @SuppressWarnings("unchecked")
    public EntityZombieStrong(World p_i1745_1_) {
        super(p_i1745_1_);
        this.setSize(1.0F, 2.25F);
        this.canPickUp = false;
        this.tasks.addTask(0, new AISwimming(this, 0.8f + Main.rand.nextFloat() * 0.2f, false));
        this.tasks.addTask(1, new AIAttack(this, EntityPlayer.class, this.getAttackRange(1.5f), 3.5f, true));
        this.tasks.addTask(1, new AIAttack(this, EntityVillager.class, this.getAttackRange(1.5f), 3.5f, true));
        this.tasks.addTask(1, new AIAttack(this, EntityGolem.class, this.getAttackRange(1.5f), 3.5f, true));
        this.tasks.addTask(2, new AIMining(this, 7.0f, 0.8f));
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
        return 0;
    }

    public void applyEntityAttributes() {
        super.applyEntityAttributes();
        float randomSpeed = 0.2f + Main.rand.nextFloat() * 0.03f;
        int i1 = this.worldObj.difficultySetting == EnumDifficulty.EASY ? 80 : this.worldObj.difficultySetting == EnumDifficulty.NORMAL ? 100 : 120;
        int randomHealth = i1 + Main.rand.nextInt(11);
        float randomDamageSalt = Main.rand.nextFloat() * 1.75f;
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(48);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(randomSpeed);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0f + randomDamageSalt);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(randomHealth);
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(1.25f);
    }

    protected int minYSpawn() {
        return 42;
    }

    public boolean getCanSpawnHere() {
        if (WorldManager.is7Night(this.worldObj)) {
            return super.getCanSpawnHere();
        }
        if (!(this.worldObj.getBiomeGenForCoords((int) this.posX, (int) this.posZ) instanceof BiomeCity)) {
            return false;
        }
        return super.getCanSpawnHere();
    }

    public void onStruckByLightning(EntityLightningBolt lightningBolt) {
    }

    public int getTotalArmorValue() {
        return 16;
    }

    protected Item getDropItem() {
        return Items.rotten_flesh;
    }

    public boolean attackEntityAsMob(Entity p_70652_1_) {
        if (super.attackEntityAsMob(p_70652_1_)) {
            p_70652_1_.addVelocity(0, rand.nextFloat() * 0.6f + 0.4f, 0);
            return true;
        }
        return false;
    }

    public int getExperiencePoints(EntityPlayer p_70693_1_) {
        return 8;
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
}