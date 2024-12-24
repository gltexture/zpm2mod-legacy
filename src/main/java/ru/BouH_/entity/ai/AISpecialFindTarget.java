package ru.BouH_.entity.ai;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import ru.BouH_.Main;
import ru.BouH_.entity.ieep.PlayerMiscData;
import ru.BouH_.entity.zombie.AZombieBase;
import ru.BouH_.gameplay.WorldManager;
import ru.BouH_.init.ItemsZp;
import ru.BouH_.items.armor.CamoType;
import ru.BouH_.items.armor.ItemArmorCamo;
import ru.BouH_.proxy.CommonProxy;
import ru.BouH_.utils.EntityUtils;

import java.util.HashSet;
import java.util.Set;

public class AISpecialFindTarget extends AITargetZp {
    private static final Set<BiomeGenBase> forest = new HashSet<>();
    private static final Set<BiomeGenBase> winter = new HashSet<>();
    private static final Set<BiomeGenBase> sand = new HashSet<>();

    static {
        forest.add(BiomeGenBase.jungle);
        forest.add(BiomeGenBase.forest);
        forest.add(BiomeGenBase.jungleEdge);
        forest.add(BiomeGenBase.birchForest);
        forest.add(BiomeGenBase.birchForestHills);
        forest.add(BiomeGenBase.forestHills);
        forest.add(BiomeGenBase.roofedForest);
        forest.add(BiomeGenBase.jungleHills);
        forest.add(CommonProxy.biome_city);

        winter.add(CommonProxy.biome_military);
        winter.add(BiomeGenBase.coldBeach);
        winter.add(BiomeGenBase.coldTaiga);
        winter.add(BiomeGenBase.coldTaigaHills);
        winter.add(BiomeGenBase.frozenOcean);
        winter.add(BiomeGenBase.frozenRiver);
        winter.add(BiomeGenBase.iceMountains);
        winter.add(BiomeGenBase.icePlains);

        sand.add(CommonProxy.biome_rad1);
        sand.add(CommonProxy.biome_rad2);
        sand.add(CommonProxy.biome_industry);
        sand.add(BiomeGenBase.desert);
        sand.add(BiomeGenBase.desertHills);
        sand.add(BiomeGenBase.beach);
    }

    private final Class<? extends EntityLivingBase>[] targetClass;
    private final int normalFindDistance;
    private final int xrayFindDistance;
    private final IEntitySelector commonSelector;
    private final IEntitySelector xRaySelector;
    private final IEntitySelector helpWantingSelector;
    private EntityLivingBase targetEntity;

    public AISpecialFindTarget(AZombieBase p_i1665_1_, Class<? extends EntityLivingBase>[] p_i1665_2_, int normalFindDistance, int xrayFindDistance) {
        super(p_i1665_1_, false, false);
        this.targetClass = p_i1665_2_;
        int f1 = Math.max(normalFindDistance, xrayFindDistance);
        int f2 = xrayFindDistance;
        int i1 = this.taskOwner.worldObj.difficultySetting == EnumDifficulty.HARD ? 6 : 12;
        if (normalFindDistance > 12.0f) {
            f1 += (3 - Main.rand.nextInt(i1));
        }
        if (xrayFindDistance > 6.0f) {
            f2 += (3 - Main.rand.nextInt(i1));
        }
        this.normalFindDistance = f1;
        this.xrayFindDistance = f2;
        this.setMutexBits(1);
        this.commonSelector = new IEntitySelector() {
            public boolean isEntityApplicable(Entity entity) {
                return entity instanceof EntityLivingBase && AISpecialFindTarget.this.isSuitableTarget((EntityLivingBase) entity, true);
            }
        };
        this.xRaySelector = new IEntitySelector() {
            public boolean isEntityApplicable(Entity entity) {
                return entity.worldObj.getGameRules().getGameRuleBooleanValue("zombiesSeekXRay") && entity instanceof EntityLivingBase && AISpecialFindTarget.this.isSuitableTarget((EntityLivingBase) entity, false);
            }
        };
        this.helpWantingSelector = new IEntitySelector() {
            public boolean isEntityApplicable(Entity entity) {
                return entity instanceof AZombieBase && ((AZombieBase) entity).getAttackTarget() == null;
            }
        };
    }

    public boolean continueExecuting() {
        if (this.taskOwner.getAttackTarget() != null) {
            if (!this.taskOwner.getAttackTarget().isEntityAlive()) {
                this.taskOwner.setAttackTarget(null);
                return false;
            }
        }
        if (this.taskOwner.ticksExisted % 300 == 0) {
            if (Main.rand.nextFloat() <= 0.5f) {
                AZombieBase aZombieBase = (AZombieBase) this.getClosestEntity(this.taskOwner.worldObj, AZombieBase.class, this.helpWantingSelector, this.taskOwner.posX, this.taskOwner.posY, this.taskOwner.posZ, 24);
                if (aZombieBase != null) {
                    aZombieBase.setAttackTarget(this.taskOwner.getAttackTarget());
                }
            }
        }
        if (this.taskOwner.getAITarget() != null && this.taskOwner.getDistanceToEntity(this.taskOwner.getAITarget()) <= 24) {
            this.targetEntity = this.taskOwner.getAITarget();
            this.taskOwner.setRevengeTarget(null);
        } else {
            for (Class<? extends EntityLivingBase> entity : this.targetClass) {
                EntityLivingBase entityLivingBase = this.getClosestEntity(this.taskOwner.worldObj, entity, this.commonSelector, this.taskOwner.posX, this.taskOwner.posY, this.taskOwner.posZ, 6);
                if (entityLivingBase != null) {
                    this.targetEntity = entityLivingBase;
                }
            }
        }
        if (this.targetEntity != null && this.taskOwner.getAttackTarget() != this.targetEntity && (this.taskOwner.ticksExisted % 200 == 0 || this.taskOwner.getDistanceToEntity(this.targetEntity) <= 6)) {
            this.taskOwner.setAttackTarget(this.targetEntity);
            this.taskOwner.getNavigator().tryMoveToEntityLiving(this.targetEntity, 1.0f);
            return true;
        }
        return super.continueExecuting();
    }

    public boolean shouldExecute() {
        if (this.taskOwner.getAITarget() != null) {
            this.targetEntity = this.taskOwner.getAITarget();
            return true;
        }
        for (Class<? extends EntityLivingBase> entity : this.targetClass) {
            EntityLivingBase entityLivingBase = this.getClosestEntity(this.taskOwner.worldObj, entity, this.commonSelector, this.taskOwner.posX, this.taskOwner.posY, this.taskOwner.posZ, this.normalFindDistance);
            if (entityLivingBase != null) {
                this.targetEntity = entityLivingBase;
                return true;
            }
            if (this.xrayFindDistance > 0) {
                entityLivingBase = this.getClosestEntity(this.taskOwner.worldObj, entity, this.xRaySelector, this.taskOwner.posX, this.taskOwner.posY, this.taskOwner.posZ, this.xrayFindDistance);
                if (entityLivingBase != null) {
                    this.targetEntity = entityLivingBase;
                    return true;
                }
            }
        }
        return false;
    }

    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.targetEntity);
        super.startExecuting();
    }

    protected EntityLivingBase getClosestEntity(World world, Class<? extends EntityLivingBase> findClass, IEntitySelector selector, double x, double y, double z, double distance) {
        double d4 = -1.0D;
        EntityLivingBase entityLivingBase = null;
        for (int i = 0; i < world.loadedEntityList.size(); i++) {
            Entity entity = (Entity) world.loadedEntityList.get(i);
            if (findClass.isAssignableFrom(entity.getClass())) {
                EntityLivingBase entityLivingBase1 = (EntityLivingBase) entity;
                if (entityLivingBase1 instanceof EntityPlayer) {
                    boolean isRotten = this.isPlayerInRotten((EntityPlayer) entityLivingBase1);
                    if (isRotten) {
                        distance = 0;
                    }
                    distance = (WorldManager.is7Night(world)) ? 48 : MathHelper.clamp_double(distance + this.seekDistanceModifier((EntityPlayer) entityLivingBase1), isRotten ? 1 : 6, 48);
                }
                double d5 = entityLivingBase1.getDistanceSq(x, y, z);
                if ((distance < 0.0D || d5 < distance * distance) && (d4 == -1.0D || d5 < d4)) {
                    if (selector.isEntityApplicable(entityLivingBase1)) {
                        d4 = d5;
                        entityLivingBase = entityLivingBase1;
                    }
                }
            }
        }
        return entityLivingBase;
    }

    private boolean isPlayerInRotten(EntityPlayer player) {
        return !player.isSprinting() && EntityUtils.isInArmor(player, null, ItemsZp.rotten_chestplate, ItemsZp.rotten_leggings, ItemsZp.rotten_boots);
    }

    protected int seekDistanceModifier(EntityPlayer player) {
        int mod = 0;
        int loudness = PlayerMiscData.getPlayerData(player).getPlayerLoudness();
        int x = MathHelper.floor_double(player.posX);
        int z = MathHelper.floor_double(player.posZ);
        BiomeGenBase biomeGenBase = player.worldObj.getBiomeGenForCoords(x, z);
        if (this.isInCamo(player, CamoType.UNIVERSAL) || (AISpecialFindTarget.sand.contains(biomeGenBase) && this.isInCamo(player, CamoType.SAND)) || (AISpecialFindTarget.forest.contains(biomeGenBase) && this.isInCamo(player, CamoType.FOREST)) || (AISpecialFindTarget.winter.contains(biomeGenBase) && this.isInCamo(player, CamoType.WINTER))) {
            mod -= 32;
        } else if (EntityUtils.isInArmor(player, ItemsZp.balaclava, null, null, null)) {
            mod -= 4;
        } else if (EntityUtils.isInArmor(player, ItemsZp.pnv, null, null, null)) {
            mod += 2;
        }

        if (loudness == 0) {
            if (PlayerMiscData.getPlayerData(player).isLying()) {
                mod -= 16;
            } else if (player.isSneaking()) {
                mod -= 8;
            }
        }
        if (player.isPotionActive(26)) {
            if (player.getActivePotionEffect(CommonProxy.zpm).getDuration() <= 16000) {
                mod -= (int) (32.0f - (player.getActivePotionEffect(CommonProxy.zpm).getDuration() / 500.0f));
            }
        }
        if (player.isSprinting()) {
            mod += 6;
        }
        if (Math.abs(player.motionY) > 0.1f) {
            mod += 3;
        }
        if (player.isUsingItem()) {
            if (player.getHeldItem() != null && (player.getHeldItem().getItem() instanceof ItemFood)) {
                mod += 4;
            }
        }
        if (player.isBurning()) {
            mod += 4;
        }
        if (player.isPotionActive(28)) {
            mod += 8;
        }
        if (player.hurtResistantTime > 0) {
            mod += 2;
        }
        return mod + loudness;
    }

    private boolean isInCamo(EntityPlayer entityPlayer, CamoType camoType) {
        for (int i = 1; i < 5; i++) {
            ItemStack equipment = entityPlayer.getEquipmentInSlot(i);
            if (equipment != null && equipment.getItem() instanceof ItemArmorCamo) {
                ItemArmorCamo armor = (ItemArmorCamo) entityPlayer.getEquipmentInSlot(i).getItem();
                if (!(armor.getCamoType() == camoType || armor.getCamoType() == CamoType.UNIVERSAL)) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }
}