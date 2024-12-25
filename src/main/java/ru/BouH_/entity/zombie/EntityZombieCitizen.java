package ru.BouH_.entity.zombie;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.entity.ai.*;
import ru.BouH_.init.ItemsZp;

import java.util.Random;

public class EntityZombieCitizen extends AZombieBase {
    private int conversionTime;

    @SuppressWarnings("unchecked")
    public EntityZombieCitizen(World p_i1745_1_) {
        super(p_i1745_1_);
        this.tasks.addTask(0, new AISwimming(this, 1.2f + Main.rand.nextFloat() * 0.4f, false));
        this.tasks.addTask(1, new AIAttack(this, EntityPlayer.class, this.getAttackRange(1.5f), 3.0f, true));
        this.tasks.addTask(1, new AIAttack(this, EntityVillager.class, this.getAttackRange(1.5f), 3.0f, true));
        this.tasks.addTask(1, new AIAttack(this, EntityAnimal.class, this.getAttackRange(1.5f), 3.0f, true));
        this.tasks.addTask(1, new AIAttack(this, EntityGolem.class, this.getAttackRange(1.5f), 3.0f, true));
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

    protected int minYSpawn() {
        return Main.rand.nextFloat() <= 0.5f ? 24 : 48;
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

    protected void entityInit() {
        super.entityInit();
        this.getDataWatcher().addObject(14, (byte) 0);
        this.getDataWatcher().addObject(15, (byte) 0);
    }

    public boolean isVillager() {
        return this.getDataWatcher().getWatchableObjectByte(15) == 1;
    }

    public void setVillager(boolean p_82229_1_) {
        this.getDataWatcher().updateObject(15, (byte) (p_82229_1_ ? 1 : 0));
    }

    public void onUpdate() {
        if (!this.worldObj.isRemote && this.isConverting()) {
            if (this.conversionTime-- <= 0) {
                this.convertToVillager();
            }
        }
        super.onUpdate();
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

    public static void addDefaultArmor(Random rand, AZombieBase zombieBase) {
        switch (rand.nextInt(6)) {
            case 0: {
                zombieBase.setCurrentItemOrArmor(4, new ItemStack(Items.iron_helmet, 1, Items.iron_helmet.getMaxDurability() - rand.nextInt((int) (Items.iron_helmet.getMaxDurability() * 0.1f))));
                zombieBase.setCurrentItemOrArmor(3, new ItemStack(Items.iron_chestplate, 1, Items.iron_helmet.getMaxDurability() - rand.nextInt((int) (Items.iron_helmet.getMaxDurability() * 0.1f))));
                zombieBase.setCurrentItemOrArmor(2, new ItemStack(Items.iron_leggings, 1, Items.iron_helmet.getMaxDurability() - rand.nextInt((int) (Items.iron_helmet.getMaxDurability() * 0.1f))));
                zombieBase.setCurrentItemOrArmor(1, new ItemStack(Items.iron_boots, 1, Items.iron_helmet.getMaxDurability() - rand.nextInt((int) (Items.iron_helmet.getMaxDurability() * 0.1f))));
                break;
            }
            case 1: {
                zombieBase.setCurrentItemOrArmor(4, new ItemStack(Items.leather_helmet, 1, Items.iron_helmet.getMaxDurability() - rand.nextInt((int) (Items.iron_helmet.getMaxDurability() * 0.1f))));
                zombieBase.setCurrentItemOrArmor(3, new ItemStack(Items.leather_chestplate, 1, Items.iron_helmet.getMaxDurability() - rand.nextInt((int) (Items.iron_helmet.getMaxDurability() * 0.1f))));
                zombieBase.setCurrentItemOrArmor(2, new ItemStack(Items.leather_leggings, 1, Items.iron_helmet.getMaxDurability() - rand.nextInt((int) (Items.iron_helmet.getMaxDurability() * 0.1f))));
                zombieBase.setCurrentItemOrArmor(1, new ItemStack(Items.leather_boots, 1, Items.iron_helmet.getMaxDurability() - rand.nextInt((int) (Items.iron_helmet.getMaxDurability() * 0.1f))));
                break;
            }
            case 2: {
                zombieBase.setCurrentItemOrArmor(4, new ItemStack(ItemsZp.forest_helmet, 1, Items.iron_helmet.getMaxDurability() - rand.nextInt((int) (Items.iron_helmet.getMaxDurability() * 0.1f))));
                zombieBase.setCurrentItemOrArmor(3, new ItemStack(ItemsZp.forest_chestplate, 1, Items.iron_helmet.getMaxDurability() - rand.nextInt((int) (Items.iron_helmet.getMaxDurability() * 0.1f))));
                zombieBase.setCurrentItemOrArmor(2, new ItemStack(ItemsZp.forest_leggings, 1, Items.iron_helmet.getMaxDurability() - rand.nextInt((int) (Items.iron_helmet.getMaxDurability() * 0.1f))));
                zombieBase.setCurrentItemOrArmor(1, new ItemStack(ItemsZp.forest_boots, 1, Items.iron_helmet.getMaxDurability() - rand.nextInt((int) (Items.iron_helmet.getMaxDurability() * 0.1f))));
                break;
            }
            case 3: {
                zombieBase.setCurrentItemOrArmor(4, new ItemStack(ItemsZp.winter_helmet, 1, Items.iron_helmet.getMaxDurability() - rand.nextInt((int) (Items.iron_helmet.getMaxDurability() * 0.1f))));
                zombieBase.setCurrentItemOrArmor(3, new ItemStack(ItemsZp.winter_chestplate, 1, Items.iron_helmet.getMaxDurability() - rand.nextInt((int) (Items.iron_helmet.getMaxDurability() * 0.1f))));
                zombieBase.setCurrentItemOrArmor(2, new ItemStack(ItemsZp.winter_leggings, 1, Items.iron_helmet.getMaxDurability() - rand.nextInt((int) (Items.iron_helmet.getMaxDurability() * 0.1f))));
                zombieBase.setCurrentItemOrArmor(1, new ItemStack(ItemsZp.winter_boots, 1, Items.iron_helmet.getMaxDurability() - rand.nextInt((int) (Items.iron_helmet.getMaxDurability() * 0.1f))));
                break;
            }
            case 4: {
                zombieBase.setCurrentItemOrArmor(4, new ItemStack(ItemsZp.sand_helmet, 1, Items.iron_helmet.getMaxDurability() - rand.nextInt((int) (Items.iron_helmet.getMaxDurability() * 0.1f))));
                zombieBase.setCurrentItemOrArmor(3, new ItemStack(ItemsZp.sand_chestplate, 1, Items.iron_helmet.getMaxDurability() - rand.nextInt((int) (Items.iron_helmet.getMaxDurability() * 0.1f))));
                zombieBase.setCurrentItemOrArmor(2, new ItemStack(ItemsZp.sand_leggings, 1, Items.iron_helmet.getMaxDurability() - rand.nextInt((int) (Items.iron_helmet.getMaxDurability() * 0.1f))));
                zombieBase.setCurrentItemOrArmor(1, new ItemStack(ItemsZp.sand_boots, 1, Items.iron_helmet.getMaxDurability() - rand.nextInt((int) (Items.iron_helmet.getMaxDurability() * 0.1f))));
                break;
            }
            case 5: {
                zombieBase.setCurrentItemOrArmor(4, new ItemStack(ItemsZp.steel_helmet, 1, Items.iron_helmet.getMaxDurability() - rand.nextInt((int) (Items.iron_helmet.getMaxDurability() * 0.1f))));
                zombieBase.setCurrentItemOrArmor(3, new ItemStack(ItemsZp.steel_chestplate, 1, Items.iron_helmet.getMaxDurability() - rand.nextInt((int) (Items.iron_helmet.getMaxDurability() * 0.1f))));
                zombieBase.setCurrentItemOrArmor(2, new ItemStack(ItemsZp.steel_leggings, 1, Items.iron_helmet.getMaxDurability() - rand.nextInt((int) (Items.iron_helmet.getMaxDurability() * 0.1f))));
                zombieBase.setCurrentItemOrArmor(1, new ItemStack(ItemsZp.steel_boots, 1, Items.iron_helmet.getMaxDurability() - rand.nextInt((int) (Items.iron_helmet.getMaxDurability() * 0.1f))));
                break;
            }
        }
    }

    protected void addRandomArmor() {
        if (rand.nextFloat() <= this.worldObj.difficultySetting.getDifficultyId() * 0.0125f) {
            EntityZombieCitizen.addDefaultArmor(this.rand, this);
        }
        if (super.addRandomBackpack()) {
            return;
        }
        switch (rand.nextInt(14)) {
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

    public void writeEntityToNBT(NBTTagCompound p_70014_1_) {
        super.writeEntityToNBT(p_70014_1_);
        if (this.isVillager()) {
            p_70014_1_.setBoolean("IsVillager", true);
        }
        p_70014_1_.setInteger("ConversionTime", this.isConverting() ? this.conversionTime : -1);
    }

    public void readEntityFromNBT(NBTTagCompound p_70037_1_) {
        super.readEntityFromNBT(p_70037_1_);
        if (p_70037_1_.getBoolean("IsVillager")) {
            this.setVillager(true);
        }
        if (p_70037_1_.hasKey("ConversionTime", 99) && p_70037_1_.getInteger("ConversionTime") > -1) {
            this.startConversion(p_70037_1_.getInteger("ConversionTime"));
        }
    }

    public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_110161_1_) {
        IEntityLivingData p_110161_1_1 = super.onSpawnWithEgg(p_110161_1_);

        if (this.worldObj.rand.nextFloat() <= 0.03F) {
            this.setVillager(true);
        }

        this.addRandomArmor();

        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextDouble() * 0.05000000074505806D, 0));
        double d0 = this.rand.nextDouble() * 1.5D * (double) this.worldObj.getTensionFactorForBlock(this.posX, this.posY, this.posZ);

        if (d0 > 1.0D) {
            this.getEntityAttribute(SharedMonsterAttributes.followRange).applyModifier(new AttributeModifier("Random zombie-spawn bonus", d0, 2));
        }

        return p_110161_1_1;
    }

    public void startConversion(int p_82228_1_) {
        this.conversionTime = p_82228_1_;
        this.getDataWatcher().updateObject(14, (byte) 1);
        this.removePotionEffect(Potion.weakness.id);
        this.addPotionEffect(new PotionEffect(Potion.damageBoost.id, p_82228_1_, 3));
        this.worldObj.setEntityState(this, (byte) 16);
    }

    @SideOnly(Side.CLIENT)
    public void handleHealthUpdate(byte p_70103_1_) {
        super.handleHealthUpdate(p_70103_1_);
    }

    public boolean isConverting() {
        return this.getDataWatcher().getWatchableObjectByte(14) == 1;
    }

    protected void convertToVillager() {
        EntityVillager entityvillager = new EntityVillager(this.worldObj);
        entityvillager.copyLocationAndAnglesFrom(this);
        entityvillager.onSpawnWithEgg(null);
        entityvillager.setLookingForHome();
        this.worldObj.removeEntity(this);
        this.worldObj.spawnEntityInWorld(entityvillager);
        entityvillager.addPotionEffect(new PotionEffect(Potion.confusion.id, 200, 0));
        this.worldObj.playAuxSFXAtEntity(null, 1017, (int) this.posX, (int) this.posY, (int) this.posZ, 0);
    }
}