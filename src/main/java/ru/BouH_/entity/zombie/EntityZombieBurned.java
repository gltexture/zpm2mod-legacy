package ru.BouH_.entity.zombie;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.entity.Entity;
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
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.entity.ai.*;
import ru.BouH_.init.ItemsZp;

public class EntityZombieBurned extends AZombieBase {
    @SuppressWarnings("unchecked")
    public EntityZombieBurned(World p_i1745_1_) {
        super(p_i1745_1_);
        this.tasks.addTask(0, new AISwimming(this, 1.2f + Main.rand.nextFloat() * 0.4f, false));
        this.tasks.addTask(1, new AIAttack(this, EntityPlayer.class, this.getAttackRange(1.5f), 3.0f, true));
        this.tasks.addTask(1, new AIAttack(this, EntityVillager.class, this.getAttackRange(1.5f), 3.0f, true));
        this.tasks.addTask(1, new AIAttack(this, EntityAnimal.class, this.getAttackRange(1.5f), 3.0f, true));
        this.tasks.addTask(1, new AIAttack(this, EntityGolem.class, this.getAttackRange(1.5f), 3.0f, true));
        this.tasks.addTask(2, new AIMining(this, 6.0f, 0.85f));
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
        return 2;
    }

    public void applyEntityAttributes() {
        super.applyEntityAttributes();
        float randomSpeed = 0.2f + Main.rand.nextFloat() * 0.05f;
        int i1 = this.worldObj.difficultySetting == EnumDifficulty.EASY || this.worldObj.difficultySetting == EnumDifficulty.NORMAL ? 20 : 30;
        int randomHealth = i1 + Main.rand.nextInt(11);
        float randomDamageSalt = Main.rand.nextFloat() * 3.0f;
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(48);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(randomSpeed);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0f + randomDamageSalt);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(randomHealth);
    }

    protected Item getDropItem() {
        return Main.rand.nextFloat() <= 0.25f ? Items.coal : Items.rotten_flesh;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.worldObj.isRemote) {
            this.spawnParticles();
        }
    }

    protected boolean canEatItem(ItemStack stack) {
        return super.canEatItem(stack) || (stack != null && stack.getItem() == Items.lava_bucket);
    }

    @SideOnly(Side.CLIENT)
    private void spawnParticles() {
        Minecraft.getMinecraft().effectRenderer.addEffect(new EntitySmokeFX(this.worldObj, this.posX + (this.rand.nextDouble() - 0.5D) * (double) this.width, this.posY + this.rand.nextDouble() * (double) this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * (double) this.width, 0.0D, 0.05D, 0.0D));
    }

    public boolean attackEntityAsMob(Entity p_70652_1_) {
        if (super.attackEntityAsMob(p_70652_1_)) {
            p_70652_1_.setFire(6);
            return true;
        }
        return false;
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

    protected void addRandomArmor() {
        if (rand.nextFloat() <= this.worldObj.difficultySetting.getDifficultyId() * 0.01f) {
            EntityZombieCitizen.addDefaultArmor(this.rand, this);
        }
        if (super.addRandomBackpack()) {
            return;
        }
        switch (rand.nextInt(12)) {
            case 0: {
                ItemStack stack = new ItemStack(Main.rand.nextFloat() <= 0.7f ? Items.stone_sword : Main.rand.nextFloat() <= 0.7f ? ItemsZp.copper_sword : Items.iron_sword);
                stack.setMetadata(rand.nextInt(stack.getMaxDurability()));
                this.setCurrentItemOrArmor(0, stack);
                break;
            }
            case 1: {
                ItemStack stack = new ItemStack(Main.rand.nextFloat() <= 0.7f ? Items.stone_axe : Main.rand.nextFloat() <= 0.7f ? ItemsZp.copper_axe : Items.iron_axe);
                stack.setMetadata(rand.nextInt(stack.getMaxDurability()));
                this.setCurrentItemOrArmor(0, stack);
                break;
            }
            case 2: {
                ItemStack stack = new ItemStack(Main.rand.nextFloat() <= 0.7f ? Items.stone_shovel : Main.rand.nextFloat() <= 0.7f ? ItemsZp.copper_shovel : Items.iron_shovel);
                stack.setMetadata(rand.nextInt(stack.getMaxDurability()));
                this.setCurrentItemOrArmor(0, stack);
                break;
            }
            case 3: {
                ItemStack stack = new ItemStack(Main.rand.nextFloat() <= 0.7f ? Items.stone_pickaxe : Main.rand.nextFloat() <= 0.7f ? ItemsZp.copper_pickaxe : Items.iron_pickaxe);
                stack.setMetadata(rand.nextInt(stack.getMaxDurability()));
                this.setCurrentItemOrArmor(0, stack);
                break;
            }
        }
    }

    public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_110161_1_) {
        IEntityLivingData p_110161_1_1 = super.onSpawnWithEgg(p_110161_1_);
        this.addRandomArmor();
        return p_110161_1_1;
    }
}