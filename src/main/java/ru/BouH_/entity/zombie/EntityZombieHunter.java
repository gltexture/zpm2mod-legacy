package ru.BouH_.entity.zombie;

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
import ru.BouH_.gameplay.WorldManager;
import ru.BouH_.init.ItemsZp;
import ru.BouH_.world.biome.BiomeCity;
import ru.BouH_.world.biome.BiomeIndustry;

public class EntityZombieHunter extends AZombieBase {
    @SuppressWarnings("unchecked")
    public EntityZombieHunter(World p_i1745_1_) {
        super(p_i1745_1_);
        this.setSize(0.8F, 2.0F);
        this.tasks.addTask(0, new AISwimming(this, 1.1f + Main.rand.nextFloat() * 0.4f, false));
        this.tasks.addTask(1, new AIAttack(this, EntityPlayer.class, this.getAttackRange(1.6f), 2.25f, true));
        this.tasks.addTask(1, new AIAttack(this, EntityVillager.class, this.getAttackRange(1.6f), 2.25f, true));
        this.tasks.addTask(1, new AIAttack(this, EntityAnimal.class, this.getAttackRange(1.6f), 2.25f, true));
        this.tasks.addTask(1, new AIAttack(this, EntityGolem.class, this.getAttackRange(1.6f), 2.25f, true));
        this.tasks.addTask(2, new AIMining(this, 4.0f, 0.65f));
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

    protected int minYSpawn() {
        return 46;
    }

    public void applyEntityAttributes() {
        super.applyEntityAttributes();
        float randomSpeed = 0.32f + Main.rand.nextFloat() * 0.05f;
        int i1 = this.worldObj.difficultySetting == EnumDifficulty.EASY || this.worldObj.difficultySetting == EnumDifficulty.NORMAL ? 60 : 80;
        int randomHealth = i1 + Main.rand.nextInt(11);
        float randomDamageSalt = Main.rand.nextFloat() * 3.0f;
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(48);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(randomSpeed);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.5f + randomDamageSalt);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(randomHealth);
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).setBaseValue(0.3f);
    }

    public boolean getCanSpawnHere() {
        if (WorldManager.is7Night(this.worldObj)) {
            return super.getCanSpawnHere();
        }
        if (!(this.worldObj.getBiomeGenForCoords((int) this.posX, (int) this.posZ) instanceof BiomeIndustry)) {
            return false;
        }
        return super.getCanSpawnHere();
    }

    public boolean attackEntityAsMob(Entity p_70652_1_) {
        if (super.attackEntityAsMob(p_70652_1_)) {
            p_70652_1_.addVelocity(this.getLookVec().xCoord * 0.5f, 0.3f, this.getLookVec().zCoord * 0.5f);
            return true;
        }
        return false;
    }

    protected Item getDropItem() {
        return Main.rand.nextBoolean() ? (Main.rand.nextBoolean() ? Items.bone : ItemsZp.fish_bones) : Items.rotten_flesh;
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
        switch (rand.nextInt(16)) {
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