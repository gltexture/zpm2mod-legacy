package ru.BouH_.entity.zombie;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.entity.ai.*;
import ru.BouH_.init.ItemsZp;
import ru.BouH_.utils.EntityUtils;

public class EntityZombieDrowned extends AZombieBase {

    @SuppressWarnings("unchecked")
    public EntityZombieDrowned(World p_i1745_1_) {
        super(p_i1745_1_);
        this.tasks.addTask(0, new AISwimming(this, 1.4f + Main.rand.nextFloat() * 0.4f, false));
        this.tasks.addTask(1, new AIAttack(this, EntityPlayer.class, this.getAttackRange(1.6f), 3.0f, true));
        this.tasks.addTask(1, new AIAttack(this, EntityVillager.class, this.getAttackRange(1.6f), 3.0f, true));
        this.tasks.addTask(1, new AIAttack(this, EntityAnimal.class, this.getAttackRange(1.6f), 3.0f, true));
        this.tasks.addTask(1, new AIAttack(this, EntityGolem.class, this.getAttackRange(1.6f), 3.0f, true));
        this.tasks.addTask(2, new AIMining(this, 5.0f, 0.8f));
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
        float randomSpeed = 0.18f + Main.rand.nextFloat() * 0.03f;
        int i1 = this.worldObj.difficultySetting == EnumDifficulty.EASY || this.worldObj.difficultySetting == EnumDifficulty.NORMAL ? 20 : 30;
        int randomHealth = i1 + Main.rand.nextInt(11);
        float randomDamageSalt = Main.rand.nextFloat() * 3.0f;
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(48);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(randomSpeed);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0f + randomDamageSalt);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(randomHealth);
    }

    public boolean getCanSpawnHere() {
        return Main.rand.nextFloat() <= 0.3f && this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && EntityUtils.isInBlock(this, Blocks.water);
    }

    protected Item getDropItem() {
        return Main.rand.nextFloat() <= 0.1f ? ItemsZp.brass_material : ItemsZp.rot_mass;
    }

    public void onLivingUpdate() {
        super.onLivingUpdate();
    }

    public void dropRareDrop(int p_70600_1_) {
        super.dropRareDrop(p_70600_1_);
        switch (this.rand.nextInt(10)) {
            case 0: {
                this.dropItem(Items.gold_ingot, 1);
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
        switch (rand.nextInt(10)) {
            case 0: {
                ItemStack stack = new ItemStack(Items.golden_sword);
                stack.setMetadata(rand.nextInt(stack.getMaxDurability()));
                this.setCurrentItemOrArmor(0, stack);
                break;
            }
            case 1: {
                ItemStack stack = new ItemStack(Items.golden_shovel);
                stack.setMetadata(rand.nextInt(stack.getMaxDurability()));
                this.setCurrentItemOrArmor(0, stack);
                break;
            }
            case 2: {
                ItemStack stack = new ItemStack(Items.golden_axe);
                stack.setMetadata(rand.nextInt(stack.getMaxDurability()));
                this.setCurrentItemOrArmor(0, stack);
                break;
            }
            case 3: {
                ItemStack stack = new ItemStack(ItemsZp.copper_pickaxe);
                stack.setMetadata(rand.nextInt(stack.getMaxDurability()));
                this.setCurrentItemOrArmor(0, stack);
                break;
            }
        }
    }

    protected boolean canEatItem(ItemStack stack) {
        return super.canEatItem(stack) || (stack != null && stack.getItem() == ItemsZp.toxicwater_bucket);
    }

    public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_110161_1_) {
        IEntityLivingData p_110161_1_1 = super.onSpawnWithEgg(p_110161_1_);
        this.addRandomArmor();
        return p_110161_1_1;
    }
}