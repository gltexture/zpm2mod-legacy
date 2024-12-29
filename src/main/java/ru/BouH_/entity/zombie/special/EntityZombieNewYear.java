package ru.BouH_.entity.zombie.special;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityExplodeFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.entity.ai.*;
import ru.BouH_.entity.zombie.AZombieBase;
import ru.BouH_.init.ItemsZp;

public class EntityZombieNewYear extends AZombieBase {
    @SuppressWarnings("unchecked")
    public EntityZombieNewYear(World p_i1745_1_) {
        super(p_i1745_1_);
        this.tasks.addTask(0, new AISwimming(this, 1.2f + Main.rand.nextFloat() * 0.4f, false));
        this.tasks.addTask(1, new AIAttack(this, EntityPlayer.class, this.getAttackRange(1.6f), 3.0f, true));
        this.tasks.addTask(1, new AIAttack(this, EntityVillager.class, this.getAttackRange(1.6f), 3.0f, true));
        this.tasks.addTask(1, new AIAttack(this, EntityAnimal.class, this.getAttackRange(1.6f), 3.0f, true));
        this.tasks.addTask(1, new AIAttack(this, EntityGolem.class, this.getAttackRange(1.6f), 3.0f, true));
        this.tasks.addTask(2, new AIMining(this, 5.0f, 0.9f));
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
        return 0;
    }

    public void applyEntityAttributes() {
        super.applyEntityAttributes();
        float randomSpeed = 0.22f + Main.rand.nextFloat() * 0.03f;
        int i1 = this.worldObj.difficultySetting == EnumDifficulty.EASY || this.worldObj.difficultySetting == EnumDifficulty.NORMAL ? 50 : 70;
        int randomHealth = i1 + Main.rand.nextInt(11);
        float randomDamageSalt = (Main.rand.nextFloat() - Main.rand.nextFloat()) * 0.5f;
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(48);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(randomSpeed);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0f + randomDamageSalt);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(randomHealth);
    }

    public boolean getCanSpawnHere() {
        return super.getCanSpawnHere() && Main.isNewYear();
    }

    protected void dropFewItems(boolean p_70628_1_, int p_70628_2_) {
        this.dropItem(ItemsZp.gift, 1);
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.worldObj.isRemote) {
            if (this.ticksExisted % 2 == 0) {
                this.spawnParticles();
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private void spawnParticles() {
        Minecraft.getMinecraft().effectRenderer.addEffect(new EntityExplodeFX(this.worldObj, this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width, this.posY + this.rand.nextDouble() * (double) this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width, 0.0D, 0.05D, 0.0D));
    }

    public boolean attackEntityAsMob(Entity p_70652_1_) {
        if (super.attackEntityAsMob(p_70652_1_)) {
            if (p_70652_1_ instanceof EntityLivingBase) {
                EntityLivingBase entityLivingBase = (EntityLivingBase) p_70652_1_;
                entityLivingBase.addPotionEffect(new PotionEffect(2, 300));
            }
            return true;
        }
        return false;
    }

    protected void addRandomArmor() {
        if (Main.rand.nextFloat() <= 0.1f) {
            this.equipmentDropChances[0] = 0.0F;
            this.setCurrentItemOrArmor(0, new ItemStack(ItemsZp.caramel_sword));
        }
    }

    public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_110161_1_) {
        IEntityLivingData p_110161_1_1 = super.onSpawnWithEgg(p_110161_1_);
        this.addRandomArmor();
        return p_110161_1_1;
    }
}