package ru.BouH_.entity.zombie;

import cpw.mods.fml.relauncher.FMLLaunchHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityBreakingFX;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.*;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import ru.BouH_.ConfigZp;
import ru.BouH_.Main;
import ru.BouH_.entity.ai.base.PathNavigateZp;
import ru.BouH_.entity.projectile.*;
import ru.BouH_.gameplay.WorldManager;
import ru.BouH_.init.EntitiesZp;
import ru.BouH_.init.ItemsZp;
import ru.BouH_.items.food.FoodCactusBowl;
import ru.BouH_.items.food.FoodDrinkCactus;
import ru.BouH_.items.food.FoodDrinkGlass;
import ru.BouH_.items.food.FoodDrinkStimulator;
import ru.BouH_.proxy.CommonProxy;
import ru.BouH_.utils.EntityUtils;
import ru.BouH_.utils.ExplosionUtils;
import ru.BouH_.utils.SoundUtils;
import ru.BouH_.world.biome.BiomeRad;
import ru.BouH_.world.generator.cities.CityType;
import ru.BouH_.world.generator.cities.SpecialGenerator;
import ru.BouH_.world.type.WorldTypeCrazyZp;
import ru.BouH_.world.type.WorldTypeZp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public abstract class AZombieBase extends EntityMob {
    private final PathNavigateZp navigator;
    private final LinkedList<AxisAlignedBB> AABBHistory = new LinkedList<>();
    public boolean canUnSpawn;
    public boolean canThrowTrash;
    public boolean canPickUp;
    public boolean canReceiveHeadshot;
    public boolean canMine;
    public int eatProgress;
    public int lockUnloadingTicks;
    protected int standardArmor;
    protected int experience;
    protected int modifierId;

    public AZombieBase(World p_i1738_1_) {
        super(p_i1738_1_);
        this.setSize(0.6F, 1.8F);
        this.navigator = new PathNavigateZp(this, p_i1738_1_);
        this.canMine = true;
        this.canReceiveHeadshot = true;
        this.canPickUp = true;
        this.canThrowTrash = true;
        this.experience = this.worldObj.difficultySetting == EnumDifficulty.EASY ? 2 : this.worldObj.difficultySetting == EnumDifficulty.NORMAL ? 3 : 4;
        this.standardArmor = 2 + Main.rand.nextInt(5);
    }

    protected abstract int minDayToSpawn();

    public boolean getCanSpawnHere() {
        if (this.worldObj.difficultySetting == EnumDifficulty.PEACEFUL) {
            return false;
        }
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.boundingBox.minY);
        int k = MathHelper.floor_double(this.posZ);

        int day = 7;
        WorldManager.WorldSaveDay saveDay = WorldManager.WorldSaveDay.getStorage(this.worldObj);
        if (saveDay != null) {
            day = saveDay.day;
        }
        float f1 = ConfigZp.zombieDifficultyProgression ? Math.min(day * 0.15f, 1.0f) : 1.0f;
        boolean isInInd = this.checkCity(CityType.INDUSTRY);
        boolean isInMil = this.checkCity(CityType.MILITARY);
        if ((j < this.minYSpawn() && Main.rand.nextFloat() > this.spawnChanceUnderY()) || (j > this.maxYSpawn() && Main.rand.nextFloat() > this.spawnChanceAboveY())) {
            return false;
        }
        if (!isInInd && !isInMil) {
            if (saveDay != null && day < this.minDayToSpawn()) {
                return false;
            }
            if (Main.rand.nextFloat() > f1) {
                return false;
            }
            if (this.worldObj.difficultySetting != EnumDifficulty.HARD) {
                if (this.worldObj.provider.isDaytime()) {
                    f1 *= 0.5f;
                }
            }
        }
        if (this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty()) {
            int l = this.worldObj.getSavedLightValue(EnumSkyBlock.Block, i, j, k);
            float miscFloat = (this.worldObj.getBiomeGenForCoords(i, k) instanceof BiomeRad || isInInd) ? 8.0e-3f : isInMil ? 8.0e-2f : 8.0e-4f;
            if (SpecialGenerator.getTerType(this.worldObj) instanceof WorldTypeCrazyZp) {
                miscFloat *= 0.5f;
            }
            return (Main.rand.nextFloat() <= miscFloat && l <= 8) || (this.getBlockPathWeight(i, j, k) >= 0.0F && this.isValidLightLevel());
        }
        return false;
    }

    protected float spawnChanceUnderY() {
        return 0.0f;
    }

    protected float spawnChanceAboveY() {
        return 0.0f;
    }

    protected float getAttackRange(float f1) {
        return (this.worldObj.difficultySetting == EnumDifficulty.EASY ? f1 - 0.15f : this.worldObj.difficultySetting == EnumDifficulty.NORMAL ? f1 : f1 + 0.15f) + Main.rand.nextFloat() * 0.5f;
    }

    protected int minYSpawn() {
        return 0;
    }

    protected int maxYSpawn() {
        return 256;
    }

    private boolean checkCity(CityType cityType) {
        return SpecialGenerator.instance.isPointInsideCityRange(this.worldObj, cityType, MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posZ), 286);
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public ResourceLocation getResourceLocation() {
        int i1 = this.getSkin();
        ArrayList<ResourceLocation> resourceLocations = (ArrayList<ResourceLocation>) EntitiesZp.resourceMap.get(this.getClass());
        if (i1 > resourceLocations.size()) {
            return null;
        }
        return resourceLocations.get(this.getSkin());
    }

    protected void entityInit() {
        super.entityInit();
        this.getDataWatcher().addObject(12, 0);
        this.getDataWatcher().addObject(13, -1);
        this.checkSkin();
    }

    public boolean isCanMine() {
        return this.canMine;
    }

    public int getTotalArmorValue() {
        return Math.min(super.getTotalArmorValue() + this.standardArmor, 20);
    }

    public synchronized PathNavigate getNavigator() {
        return this.navigator;
    }

    protected void updateAITasks() {
        ++this.entityAge;
        this.worldObj.theProfiler.startSection("checkDespawn");
        this.despawnEntity();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("sensing");
        this.getEntitySenses().clearSensingCache();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("targetSelector");
        this.targetTasks.onUpdateTasks();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("goalSelector");
        this.tasks.onUpdateTasks();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("navigation");
        this.navigator.onUpdateNavigation();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("mob tick");
        this.updateAITick();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection("controls");
        this.worldObj.theProfiler.startSection("move");
        this.getMoveHelper().onUpdateMoveHelper();
        this.worldObj.theProfiler.endStartSection("look");
        this.getLookHelper().onUpdateLook();
        this.worldObj.theProfiler.endStartSection("jump");
        this.getJumpHelper().doJump();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.endSection();
    }

    public int getAir() {
        return 300;
    }

    protected boolean isAIEnabled() {
        return true;
    }

    public int getSkin() {
        return this.getDataWatcher().getWatchableObjectInt(13);
    }

    public void setSkin(int i) {
        this.getDataWatcher().updateObject(13, i);
    }

    public int getModifierId() {
        return this.getDataWatcher().getWatchableObjectInt(12);
    }

    public void setModifierId(int modifierId) {
        this.getDataWatcher().updateObject(12, modifierId);
    }

    public boolean radiationModified() {
        return this.getModifierId() == 1 || this.getModifierId() == 2;
    }

    protected void checkSkin() {
        if (this.getSkin() == -1) {
            this.setSkin(rand.nextInt(this.getSkinCount()));
        }
    }

    @SuppressWarnings("unchecked")
    private int getSkinCount() {
        return FMLLaunchHandler.side().isClient() ? ((ArrayList<ResourceLocation>) EntitiesZp.resourceMap.get(this.getClass())).size() : (Integer) EntitiesZp.resourceMap.get(this.getClass());
    }

    public IEntityLivingData onSpawnWithEgg(IEntityLivingData p_110161_1_) {
        if (!this.worldObj.isRemote) {
            BiomeGenBase base = this.worldObj.getBiomeGenForCoords(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posZ));
            if (base == CommonProxy.biome_rad1) {
                this.setModifierId(1);
            } else if (base == CommonProxy.biome_rad2) {
                this.setModifierId(2);
            } else {
                if (this.worldObj.difficultySetting != EnumDifficulty.EASY && (this.worldObj.getWorldInfo().getWorldTotalTime() / 24000.0f) >= 7) {
                    if (Main.rand.nextFloat() <= (this.worldObj.difficultySetting == EnumDifficulty.NORMAL ? 0.01f : 0.02f)) {
                        if (Main.rand.nextFloat() <= 0.1f) {
                            this.setModifierId(2);
                            this.getEntityData().setInteger("radiation", 480);
                        } else {
                            this.setModifierId(1);
                            this.getEntityData().setInteger("radiation", 240);
                        }
                    }
                }
            }
        }
        return p_110161_1_;
    }

    public boolean attackEntityAsMob(Entity p_70652_1_) {
        this.setLastAttacker(p_70652_1_);
        int f = (int) Math.min(this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getBaseValue() + this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue() * 0.3f, this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getBaseValue() * 2);
        int i = 0;

        if (p_70652_1_ instanceof EntityLivingBase) {
            if (EnchantmentHelper.getEnchantmentModifierLiving(this, (EntityLivingBase) p_70652_1_) > 0) {
                f += 1;
            }
            i += EnchantmentHelper.getKnockbackModifier(this, (EntityLivingBase) p_70652_1_);
            if (this.radiationModified()) {
                int i1 = this.getModifierId() == 2 ? 40 : 20;
                p_70652_1_.getEntityData().setInteger("radiation", p_70652_1_.getEntityData().getInteger("radiation") + i1);
            }
        }
        boolean flag = p_70652_1_.attackEntityFrom(DamageSource.causeMobDamage(this), f);

        if (flag) {
            if (i > 0) {
                p_70652_1_.addVelocity((-MathHelper.sin(this.rotationYaw * (float) Math.PI / 180.0F) * (float) i * 0.5F), 0.1D, MathHelper.cos(this.rotationYaw * (float) Math.PI / 180.0F) * (float) i * 0.5F);
                this.motionX *= 0.6D;
                this.motionZ *= 0.6D;
            }

            int j = EnchantmentHelper.getFireAspectModifier(this) * 4 + (this.isBurning() ? 4 : 0);

            if (j > 0) {
                p_70652_1_.setFire(j);
            }

            if (p_70652_1_ instanceof EntityLivingBase) {
                EnchantmentHelper.func_151384_a((EntityLivingBase) p_70652_1_, this);
            }

            EnchantmentHelper.func_151385_b(this, p_70652_1_);
        }

        return flag;
    }

    public boolean isOnLadder() {
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.posY);
        int k = MathHelper.floor_double(this.posZ);
        return (this.worldObj.getBlock(i, j, k) == Blocks.ladder || this.worldObj.getBlock(i, j + 1, k) == Blocks.ladder);
    }

    @SuppressWarnings("unchecked")
    public void onLivingUpdate() {
        super.onLivingUpdate();
        World world = this.worldObj;
        ItemStack stack = this.getHeldItem();
        if (this.isEntityAlive()) {
            if (this.worldObj.getGameRules().getGameRuleBooleanValue("zombiesCanEatFood")) {
                if (this.isEating() && !this.isPotionActive(20)) {
                    if (!world.isRemote) {
                        if (this.eatProgress >= 160) {
                            this.heal(2.5f);
                            this.setCurrentItemOrArmor(0, this.onEaten(stack));
                            this.eatProgress = 0;
                        }
                    } else {
                        if (this.ticksExisted % 2 == 0) {
                            this.spawnParticlesEating();
                        }
                        if (this.ticksExisted % 5 == 0) {
                            SoundUtils.playClientSound(this.posX, this.posY, this.posZ, (stack.getItemUseAction() == EnumAction.drink || stack.getItem() instanceof ItemBucket) ? "random.drink" : "random.eat", 1.0f, rand.nextFloat() * 0.1F + 0.9F);
                        }
                    }
                    this.eatProgress++;
                } else {
                    this.eatProgress = 0;
                }
            }

            if (!world.isRemote) {
                this.updateAABBHistory();
                if (this.getAttackTarget() != null) {
                    if (this.lockUnloadingTicks < 1200) {
                        this.lockUnloadingTicks += 1;
                    }
                    if (this.isOnLadder() && this.getAttackTarget().posY > this.posY) {
                        this.motionY = 0.075d;
                        this.motionX = this.getLookVec().xCoord * 0.05d;
                        this.motionZ = this.getLookVec().zCoord * 0.05d;
                        if (this.ticksExisted % 6 == 0) {
                            world.playSoundAtEntity(this, Block.soundTypeLadder.getStepSound(), 1.0F, 0.5F);
                        }
                    }
                    if (this.getAttackTarget().posY - 3 >= this.posY) {
                        if (this.canThrowTrash && this.ticksExisted % 20 == 0) {
                            List<Entity> entities = this.worldObj.getEntitiesWithinAABB(AZombieBase.class, this.boundingBox.expand(2, 2, 2));
                            if (WorldManager.is7Night(this.worldObj) || entities.size() > 3 || Main.rand.nextFloat() <= 5.0e-3f) {
                                if (Main.rand.nextFloat() <= Math.min((WorldManager.is7Night(this.worldObj) ? 2.5e-2D : 5.0e-3D) + (entities.size() * 1.0e-3D), 0.15f)) {
                                    world.playSoundAtEntity(this, "random.bow", 1.0F, 0.4F / (rand.nextFloat() * 0.4F + 0.8F));
                                    this.swingItem();
                                    EntityThrowableZPBase entityThrowableZPBase = null;
                                    if (Main.rand.nextFloat() <= (WorldManager.is7Night(this.worldObj) ? 0.2f : 0.1f)) {
                                        switch (Main.rand.nextInt(2)) {
                                            case 0: {
                                                entityThrowableZPBase = new EntityRock(world, this, 0.0f);
                                                break;
                                            }
                                            case 1: {
                                                entityThrowableZPBase = new EntityAcid(world, this, 0.0f);
                                                break;
                                            }
                                            default: {
                                                break;
                                            }
                                        }
                                    } else {
                                        switch (rand.nextInt(5)) {
                                            case 0: {
                                                entityThrowableZPBase = new EntityGlassBottle(world, this, 0.0f);
                                                break;
                                            }
                                            case 1: {
                                                entityThrowableZPBase = new EntityTrashBrick(world, this, 0.0f);
                                                break;
                                            }
                                            case 2: {
                                                entityThrowableZPBase = new EntityTrashRottenFlesh(world, this, 0.0f);
                                                break;
                                            }
                                            case 3: {
                                                entityThrowableZPBase = new EntityTrashBone(world, this, 0.0f);
                                                break;
                                            }
                                            case 4: {
                                                entityThrowableZPBase = new EntityPlate(world, this, 0.0f);
                                                break;
                                            }
                                            default: {
                                                break;
                                            }
                                        }
                                    }
                                    double d0 = this.getAttackTarget().posY + this.getAttackTarget().getEyeHeight() - 1.1f;
                                    double d1 = this.getAttackTarget().posX - this.posX;
                                    double d2 = d0 - entityThrowableZPBase.posY;
                                    double d3 = this.getAttackTarget().posZ - this.posZ;
                                    float f = MathHelper.sqrt_double(d1 * d1 + d3 * d3) * 0.2F;
                                    entityThrowableZPBase.setThrowableHeading(d1, d2 + (double) f, d3, 0.5f + rand.nextFloat() * 1.8f, 6.0F + (entities.size() * 0.5f));
                                    world.spawnEntityInWorld(entityThrowableZPBase);
                                }
                            }
                        }
                    }
                } else {
                    if (this.lockUnloadingTicks > 0) {
                        this.lockUnloadingTicks -= 1;
                    }
                }
                if (this.worldObj.getGameRules().getGameRuleBooleanValue("zombiesCanPickItems")) {
                    if (this.canPickUp && this.isEntityAlive()) {
                        this.lootItem();
                    }
                }
            }
        }
    }

    protected ItemStack onEaten(ItemStack stack) {
        this.worldObj.playSoundAtEntity(this, "random.burp", 1.0F, rand.nextFloat() * 0.1F + 0.9F);
        if (stack.getItem() == Items.potionitem) {
            ItemPotion itemPotion = (ItemPotion) stack.getItem();
            if (itemPotion.getEffects(stack) != null) {
                for (Object o : itemPotion.getEffects(stack)) {
                    this.addPotionEffect((PotionEffect) o);
                }
            }
            return new ItemStack(Items.glass_bottle);
        }
        if (stack.getItem() instanceof FoodDrinkStimulator || stack.getItem() instanceof FoodDrinkGlass || stack.getItem() instanceof FoodDrinkCactus) {
            return new ItemStack(Items.glass_bottle);
        }
        if (stack.getItem() instanceof FoodCactusBowl || stack.getItem() instanceof ItemSoup) {
            return new ItemStack(Items.bowl);
        }
        if (stack.getItem() == Items.lava_bucket || stack.getItem() == Items.water_bucket || stack.getItem() == Items.milk_bucket) {
            return new ItemStack(Items.bucket);
        }
        if (stack.getItem() == ItemsZp.trap_grenade) {
            ExplosionUtils.makeExplosion(this.worldObj, null, this, this.posX, this.posY + 1, this.posZ, 3.0f, false, false, false);
            this.setDead();
        } else if (stack.hasTagCompound() && stack.getTagCompound().getCompoundTag(Main.MODID).getByte("poisonous") == 1) {
            this.addPotionEffect(new PotionEffect(20, 1200, 2));
        }
        if (stack.isItemStackDamageable()) {
            stack.damageItem(1, this);
        } else {
            stack.stackSize--;
        }
        return stack.stackSize <= 0 ? null : stack;
    }

    public void updateAABBHistory() {
        this.getAABBHistory().addFirst(this.boundingBox.copy());
        if (this.getAABBHistory().size() > 20) {
            this.getAABBHistory().remove(20);
        }
    }

    protected void dropFewItems(boolean p_70628_1_, int p_70628_2_) {
        if (WorldManager.is7Night(this.worldObj) && Main.rand.nextFloat() <= 0.075f) {
            if (Main.rand.nextFloat() <= 0.5f) {
                this.dropItem(ItemsZp.fish_box, 1);
            } else if (Main.rand.nextFloat() <= 0.8f) {
                this.dropItem(ItemsZp.fish_crate, 1);
            } else {
                this.dropItem(ItemsZp.fish_iron_crate, 1);
            }
        } else {
            super.dropFewItems(p_70628_1_, p_70628_2_);
        }
    }

    public LinkedList<AxisAlignedBB> getAABBHistory() {
        return this.AABBHistory;
    }

    protected boolean addRandomBackpack() {
        if (this.getHeldItem() == null) {
            if ((WorldManager.is7Night(this.worldObj))) {
                if (Main.rand.nextFloat() <= 0.03f) {
                    this.equipmentDropChances[0] = 2.0F;
                    this.setCurrentItemOrArmor(0, new ItemStack(ItemsZp.old_backpack2));
                    return true;
                }
            }
            if (Main.rand.nextFloat() <= (2.25e-2f * ConfigZp.bagsFromZombiesChanceMultiplier)) {
                this.equipmentDropChances[0] = 2.0F;
                this.setCurrentItemOrArmor(0, this.getBackpack());
                return true;
            }
        }
        return false;
    }

    protected ItemStack getBackpack() {
        return new ItemStack((Main.rand.nextFloat() <= 0.85f) ? ItemsZp.old_backpack : ItemsZp.old_backpack2);
    }

    @SideOnly(Side.CLIENT)
    protected void spawnParticlesEating() {
        float const1, const2;
        const1 = (-MathHelper.sin(this.rotationYawHead / 180.0F * (float) Math.PI)) * 0.6f;
        const2 = (MathHelper.cos(this.rotationYawHead / 180.0F * (float) Math.PI)) * 0.6f;
        for (int i = 0; i < 2; i++) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityBreakingFX(this.worldObj, this.posX + const1, this.posY + this.getEyeHeight(), this.posZ + const2, Main.rand.nextGaussian() * 0.06D, Main.rand.nextDouble() * 0.3D, Main.rand.nextGaussian() * 0.06D, this.getHeldItem().getItem(), this.getHeldItem().getCurrentDurability()));
        }
    }

    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }

    protected String getLivingSound() {
        return "mob.zombie.say";
    }

    protected String getHurtSound() {
        return "mob.zombie.hurt";
    }

    protected String getDeathSound() {
        return "mob.zombie.death";
    }

    protected void playStepSound(int p_145780_1_, int p_145780_2_, int p_145780_3_, Block p_145780_4_) {
        this.playSound("mob.zombie.step", 0.15F, 1.0F);
    }

    public int getExperiencePoints(EntityPlayer p_70693_1_) {
        int i = 1 + Main.rand.nextInt(this.experience + 1);
        i += (int) (this.getMaxHealth() / 20);
        return i;
    }

    public void onKillEntity(EntityLivingBase p_70074_1_) {
        super.onKillEntity(p_70074_1_);
    }

    public boolean attackEntityFrom(DamageSource p_70097_1_, float p_70097_2_) {
        return super.attackEntityFrom(p_70097_1_, p_70097_2_);
    }

    public void onStruckByLightning(EntityLightningBolt lightningBolt) {
        if (!this.worldObj.isRemote) {
            EntityZombieStrong entityZombieStrong = new EntityZombieStrong(this.worldObj);
            entityZombieStrong.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.worldObj.spawnEntityInWorld(entityZombieStrong);
            this.setDead();
        }
    }

    protected boolean canEatItem(ItemStack stack) {
        return stack != null && (stack.getItem() instanceof ItemFood || stack.getItem() == ItemsZp.trap_grenade || stack.getItem() == Items.milk_bucket || stack.getItem() == Items.water_bucket || stack.getItem() == Items.potionitem);
    }

    public boolean isEating() {
        return this.canEatItem(this.getHeldItem());
    }

    public boolean canPickUpFood(ItemStack stack) {
        return !this.isEating() || (this.isEating() && this.getHeldItem().getItem() == stack.getItem() && this.getHeldItem().stackSize < this.getHeldItem().getMaxStackSize());
    }

    protected Block getBlockToCheck(Item item) {
        return item instanceof ItemSpade ? Blocks.dirt : item instanceof ItemAxe ? Blocks.planks : Blocks.stone;
    }

    protected boolean canPickUpTools() {
        return true;
    }

    @SuppressWarnings("unchecked")
    protected void lootItem() {
        List<EntityItem> list = this.worldObj.getEntitiesWithinAABB(EntityItem.class, this.boundingBox.expand(1.5D, 2.0D, 1.5D));
        Iterator<EntityItem> iterator = list.iterator();

        if (this.ticksExisted % 20 == 0 && Main.rand.nextBoolean()) {
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
                                        if (this.canPickUpTools()) {
                                            if (stackInSlot.getItem() instanceof ItemSword) {
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
                                            } else if (!(itemstack.getItem() instanceof ItemSword)) {
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
                                            }
                                        } else {
                                            flag = false;
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

    protected void dropEquipment(boolean p_82160_1_, int p_82160_2_) {
        if (Main.rand.nextFloat() <= 0.0025f * this.getExperiencePoints(null)) {
            this.entityDropItem(new ItemStack(this.getBackpack().getItem()), 0.0f);
        }
        for (int j = 0; j < this.getInventory().length; ++j) {
            ItemStack itemstack = this.getEquipmentInSlot(j);
            boolean flag1 = this.equipmentDropChances[j] > 1.0F;
            if (itemstack != null && (p_82160_1_ || flag1) && this.rand.nextFloat() - (float) p_82160_2_ * 0.01F < this.equipmentDropChances[j]) {
                if (!flag1 && itemstack.isItemStackDamageable()) {
                    int k = Math.max(itemstack.getMaxDurability() - 25, 1);
                    int l = itemstack.getMaxDurability() - this.rand.nextInt(this.rand.nextInt(k) + 1);
                    if (l > k) {
                        l = k;
                    }
                    if (l < 1) {
                        l = 1;
                    }
                    itemstack.setMetadata(l);
                }
                this.setCurrentItemOrArmor(j, null);
                this.entityDropItem(itemstack, 0.0F);
            }
        }
    }

    public void writeEntityToNBT(NBTTagCompound p_70014_1_) {
        super.writeEntityToNBT(p_70014_1_);
        p_70014_1_.setBoolean("canUnSpawn", this.canUnSpawn);
        p_70014_1_.setInteger("modifierId", this.getDataWatcher().getWatchableObjectInt(12));
        p_70014_1_.setInteger("skinId", this.getDataWatcher().getWatchableObjectInt(13));
    }

    public void readEntityFromNBT(NBTTagCompound p_70037_1_) {
        super.readEntityFromNBT(p_70037_1_);
        this.canUnSpawn = p_70037_1_.getBoolean("canUnSpawn");
        this.setModifierId(p_70037_1_.getInteger("modifierId"));
        this.setSkin(p_70037_1_.getInteger("skinId"));
    }

    public boolean canDespawn() {
        return !this.canUnSpawn && this.lockUnloadingTicks <= 0;
    }
}