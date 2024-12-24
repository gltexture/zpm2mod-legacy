package ru.BouH_.hook.server;

import net.minecraft.block.Block;
import net.minecraft.block.BlockWeb;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.*;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import ru.BouH_.Main;
import ru.BouH_.blocks.BlockTorchBase;
import ru.BouH_.entity.ieep.PlayerMiscData;
import ru.BouH_.gameplay.WorldManager;
import ru.BouH_.init.BlocksZp;
import ru.BouH_.moving.MovingInput;
import ru.BouH_.moving.MovingUtils;
import ru.BouH_.utils.FishingUtils;
import ru.hook.asm.Hook;
import ru.hook.asm.ReturnCondition;

import java.util.Collection;
import java.util.List;

public class EntityHook {
    @Hook(returnCondition = ReturnCondition.ON_TRUE, intReturnConstant = 0)
    public static boolean getExperiencePoints(EntityAnimal en, EntityPlayer p_70693_1_) {
        return en.attackingPlayer == null || en.getEntityData().getBoolean("spawner");
    }

    @Hook(returnCondition = ReturnCondition.ON_TRUE, intReturnConstant = 0)
    public static boolean getExperiencePoints(EntityLiving en, EntityPlayer p_70693_1_) {
        return en.attackingPlayer == null || en.getEntityData().getBoolean("spawner");
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static ItemStack func_146033_f(EntityFishHook entityFishHook) {
        return FishingUtils.getStack(entityFishHook, entityFishHook.angler);
    }

    @Hook(returnCondition = ReturnCondition.ON_TRUE)
    public static boolean jump(EntityPlayer player) {
        if (!player.worldObj.isRemote || player.capabilities.isCreativeMode) {
            return false;
        }
        if (MovingInput.instance.jumpTms < 4 && MovingInput.instance.jumpCd <= 0) {
            MovingInput.instance.jumpCd = 60;
        }
        MovingInput.instance.jumpTms += 1;
        return MovingInput.instance.jumpTms >= 4;
    }

    @Hook
    public static void onImpact(EntitySnowball entitySnowball, MovingObjectPosition movingObjectPosition) {
        if (!entitySnowball.worldObj.isRemote) {
            if (movingObjectPosition != null) {
                if (entitySnowball.worldObj.getBlock(movingObjectPosition.blockX, movingObjectPosition.blockY, movingObjectPosition.blockZ) == Blocks.torch || entitySnowball.worldObj.getBlock(movingObjectPosition.blockX, movingObjectPosition.blockY, movingObjectPosition.blockZ) instanceof BlockTorchBase) {
                    entitySnowball.worldObj.playSoundEffect(movingObjectPosition.blockX, movingObjectPosition.blockY, movingObjectPosition.blockZ, "random.fizz", 1.0F, 2.6F + (Main.rand.nextFloat() - Main.rand.nextFloat()) * 0.8F);
                    entitySnowball.worldObj.setBlock(movingObjectPosition.blockX, movingObjectPosition.blockY, movingObjectPosition.blockZ, BlocksZp.torch5, entitySnowball.worldObj.getBlockMetadata(movingObjectPosition.blockX, movingObjectPosition.blockY, movingObjectPosition.blockZ), 2);
                }
            }
        }
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static boolean isDifficultyScaled(DamageSource difficultyScaled) {
        return false;
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static boolean isDifficultyScaled(EntityDamageSource difficultyScaled) {
        return false;
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static float getEyeHeight(EntityPlayer player) {
        if (player.worldObj.isRemote && player instanceof EntityPlayerSP) {
            return player.eyeHeight;
        }
        if (PlayerMiscData.getPlayerData(player).isLying() || MovingUtils.isSwimming(player)) {
            return 0.62f;
        } else {
            if (player.isSneaking()) {
                return 1.3f;
            } else {
                return player.eyeHeight;
            }
        }
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS, createMethod = true)
    public static void moveEntity(EntityPlayer entity, double x, double y, double z) {
        if (entity.noClip) {
            entity.boundingBox.offset(x, y, z);
            entity.posX = (entity.boundingBox.minX + entity.boundingBox.maxX) / 2.0D;
            entity.posY = entity.boundingBox.minY + (double) entity.yOffset - (double) entity.yOffset2;
            entity.posZ = (entity.boundingBox.minZ + entity.boundingBox.maxZ) / 2.0D;
        } else {
            entity.worldObj.theProfiler.startSection("move");
            entity.yOffset2 *= 0.4F;
            double d3 = entity.posX;
            double d4 = entity.posY;
            double d5 = entity.posZ;

            if (entity.isInWeb) {
                entity.isInWeb = false;
                x *= 0.25D;
                y *= 0.05000000074505806D;
                z *= 0.25D;
                entity.motionX = 0.0D;
                entity.motionY = 0.0D;
                entity.motionZ = 0.0D;
            }

            double d6 = x;
            double d7 = y;
            double d8 = z;
            AxisAlignedBB axisalignedbb = entity.boundingBox.copy();
            boolean flag = entity.onGround && (entity.isSneaking() || PlayerMiscData.getPlayerData(entity).isLying()) && !MovingUtils.isSwimming(entity);

            if (entity.worldObj.isRemote) {
                if (flag && !MovingUtils.forceSneak(entity) && !MovingUtils.forceLie(entity)) {
                    double d9;
                    for (d9 = 0.05D; x != 0.0D && entity.worldObj.getCollidingBoundingBoxes(entity, entity.boundingBox.getOffsetBoundingBox(x, -1.0D, 0.0D)).isEmpty(); d6 = x) {
                        if (x < d9 && x >= -d9) {
                            x = 0.0D;
                        } else if (x > 0.0D) {
                            x -= d9;
                        } else {
                            x += d9;
                        }
                    }

                    for (; z != 0.0D && entity.worldObj.getCollidingBoundingBoxes(entity, entity.boundingBox.getOffsetBoundingBox(0.0D, -1.0D, z)).isEmpty(); d8 = z) {
                        if (z < d9 && z >= -d9) {
                            z = 0.0D;
                        } else if (z > 0.0D) {
                            z -= d9;
                        } else {
                            z += d9;
                        }
                    }

                    while (x != 0.0D && z != 0.0D && entity.worldObj.getCollidingBoundingBoxes(entity, entity.boundingBox.getOffsetBoundingBox(x, -1.0D, z)).isEmpty()) {
                        if (x < d9 && x >= -d9) {
                            x = 0.0D;
                        } else if (x > 0.0D) {
                            x -= d9;
                        } else {
                            x += d9;
                        }

                        if (z < d9 && z >= -d9) {
                            z = 0.0D;
                        } else if (z > 0.0D) {
                            z -= d9;
                        } else {
                            z += d9;
                        }

                        d6 = x;
                        d8 = z;
                    }
                }
            }

            List list = entity.worldObj.getCollidingBoundingBoxes(entity, entity.boundingBox.addCoord(x, y, z));

            for (Object o : list) {
                y = ((AxisAlignedBB) o).calculateYOffset(entity.boundingBox, y);
            }

            entity.boundingBox.offset(0.0D, y, 0.0D);

            if (!entity.field_70135_K && d7 != y) {
                z = 0.0D;
                y = 0.0D;
                x = 0.0D;
            }

            boolean flag1 = entity.onGround || d7 != y && d7 < 0.0D;
            int j;

            for (j = 0; j < list.size(); ++j) {
                x = ((AxisAlignedBB) list.get(j)).calculateXOffset(entity.boundingBox, x);
            }

            entity.boundingBox.offset(x, 0.0D, 0.0D);

            if (!entity.field_70135_K && d6 != x) {
                z = 0.0D;
                y = 0.0D;
                x = 0.0D;
            }

            for (j = 0; j < list.size(); ++j) {
                z = ((AxisAlignedBB) list.get(j)).calculateZOffset(entity.boundingBox, z);
            }

            entity.boundingBox.offset(0.0D, 0.0D, z);

            if (!entity.field_70135_K && d8 != z) {
                z = 0.0D;
                y = 0.0D;
                x = 0.0D;
            }

            double d10;
            double d11;
            int k;
            double d12;

            if (entity.stepHeight > 0.0F && flag1 && (d6 != x || d8 != z)) {
                d12 = x;
                d10 = y;
                d11 = z;
                x = d6;
                y = entity.stepHeight;
                z = d8;
                AxisAlignedBB axisalignedbb1 = entity.boundingBox.copy();
                entity.boundingBox.setBB(axisalignedbb);
                list = entity.worldObj.getCollidingBoundingBoxes(entity, entity.boundingBox.addCoord(d6, y, d8));

                for (k = 0; k < list.size(); ++k) {
                    y = ((AxisAlignedBB) list.get(k)).calculateYOffset(entity.boundingBox, y);
                }

                entity.boundingBox.offset(0.0D, y, 0.0D);

                if (!entity.field_70135_K && d7 != y) {
                    z = 0.0D;
                    y = 0.0D;
                    x = 0.0D;
                }

                for (k = 0; k < list.size(); ++k) {
                    x = ((AxisAlignedBB) list.get(k)).calculateXOffset(entity.boundingBox, x);
                }

                entity.boundingBox.offset(x, 0.0D, 0.0D);

                if (!entity.field_70135_K && d6 != x) {
                    z = 0.0D;
                    y = 0.0D;
                    x = 0.0D;
                }

                for (k = 0; k < list.size(); ++k) {
                    z = ((AxisAlignedBB) list.get(k)).calculateZOffset(entity.boundingBox, z);
                }

                entity.boundingBox.offset(0.0D, 0.0D, z);

                if (!entity.field_70135_K && d8 != z) {
                    z = 0.0D;
                    y = 0.0D;
                    x = 0.0D;
                }

                if (!entity.field_70135_K && d7 != y) {
                    z = 0.0D;
                    y = 0.0D;
                    x = 0.0D;
                } else {
                    y = -entity.stepHeight;

                    for (k = 0; k < list.size(); ++k) {
                        y = ((AxisAlignedBB) list.get(k)).calculateYOffset(entity.boundingBox, y);
                    }

                    entity.boundingBox.offset(0.0D, y, 0.0D);
                }

                if (d12 * d12 + d11 * d11 >= x * x + z * z) {
                    x = d12;
                    y = d10;
                    z = d11;
                    entity.boundingBox.setBB(axisalignedbb1);
                }
            }

            entity.worldObj.theProfiler.endSection();
            entity.worldObj.theProfiler.startSection("rest");
            entity.posX = (entity.boundingBox.minX + entity.boundingBox.maxX) / 2.0D;
            entity.posY = entity.boundingBox.minY + (double) entity.yOffset - (double) entity.yOffset2;
            entity.posZ = (entity.boundingBox.minZ + entity.boundingBox.maxZ) / 2.0D;
            entity.isCollidedHorizontally = d6 != x || d8 != z;
            entity.isCollidedVertically = d7 != y;
            entity.onGround = d7 != y && d7 < 0.0D;
            entity.isCollided = entity.isCollidedHorizontally || entity.isCollidedVertically;

            if (d6 != x) {
                entity.motionX = 0.0D;
            }

            if (d7 != y) {
                entity.motionY = 0.0D;
            }

            if (d8 != z) {
                entity.motionZ = 0.0D;
            }

            d12 = entity.posX - d3;
            d10 = entity.posY - d4;
            d11 = entity.posZ - d5;

            if (!entity.capabilities.isFlying && !flag && entity.ridingEntity == null) {
                int j1 = MathHelper.floor_double(entity.posX);
                k = MathHelper.floor_double(entity.posY - 0.20000000298023224D - (double) entity.yOffset);
                int l = MathHelper.floor_double(entity.posZ);
                Block block = entity.worldObj.getBlock(j1, k, l);
                int i1 = entity.worldObj.getBlock(j1, k - 1, l).getRenderType();

                if (i1 == 11 || i1 == 32 || i1 == 21) {
                    block = entity.worldObj.getBlock(j1, k - 1, l);
                }

                if (block != Blocks.ladder) {
                    d10 = 0.0D;
                }

                entity.distanceWalkedModified = (float) ((double) entity.distanceWalkedModified + (double) MathHelper.sqrt_double(d12 * d12 + d11 * d11) * 0.6D);
                entity.distanceWalkedOnStepModified = (float) ((double) entity.distanceWalkedOnStepModified + (double) MathHelper.sqrt_double(d12 * d12 + d10 * d10 + d11 * d11) * 0.6D);

                if (entity.distanceWalkedOnStepModified > (float) entity.nextStepDistance) {
                    entity.nextStepDistance = (int) entity.distanceWalkedOnStepModified + 1;

                    if (entity.isInWater()) {
                        float f = MathHelper.sqrt_double(entity.motionX * entity.motionX * 0.20000000298023224D + entity.motionY * entity.motionY + entity.motionZ * entity.motionZ * 0.20000000298023224D) * 0.35F;

                        if (f > 1.0F) {
                            f = 1.0F;
                        }

                        entity.playSound("game.player.swim", f, 1.0F + (Main.rand.nextFloat() - Main.rand.nextFloat()) * 0.4F);
                    }

                    if (!MovingUtils.isSwimming(entity)) {
                        Block block1 = entity.worldObj.getBlock(MathHelper.floor_double(entity.boundingBox.minX), k, MathHelper.floor_double(entity.boundingBox.minZ));
                        Block block2 = entity.worldObj.getBlock(MathHelper.floor_double(entity.boundingBox.minX), k, MathHelper.floor_double(entity.boundingBox.maxZ));
                        Block block3 = entity.worldObj.getBlock(MathHelper.floor_double(entity.boundingBox.maxX), k, MathHelper.floor_double(entity.boundingBox.minZ));
                        Block block4 = entity.worldObj.getBlock(MathHelper.floor_double(entity.boundingBox.maxX), k, MathHelper.floor_double(entity.boundingBox.maxZ));
                        if (block.getMaterial() != Material.air) {
                            EntityHook.playStepSound(entity, j1, k, l, block);
                        } else {
                            Block valid = block1.getMaterial() != Material.air ? block1 : block2.getMaterial() != Material.air ? block2 : block3.getMaterial() != Material.air ? block3 : block4.getMaterial() != Material.air ? block4 : null;
                            if (valid != null) {
                                EntityHook.playStepSound(entity, j1, k, l, valid);
                            }
                        }
                    }
                    block.onEntityWalking(entity.worldObj, j1, k, l, entity);
                }
            }

            try {
                entity.doBlockCollisions();
            } catch (Throwable throwable) {
                CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Checking entity block collision");
                CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being checked for collision");
                entity.addEntityCrashInfo(crashreportcategory);
                throw new ReportedException(crashreport);
            }

            boolean flag2 = entity.isWet();

            if (entity.worldObj.func_147470_e(entity.boundingBox.contract(0.001D, 0.001D, 0.001D))) {
                if (!entity.isImmuneToFire()) {
                    entity.attackEntityFrom(DamageSource.inFire, 1);
                }

                if (!flag2) {
                    ++entity.fire;

                    if (entity.fire == 0) {
                        entity.setFire(8);
                    }
                }
            } else if (entity.fire <= 0) {
                entity.fire = -entity.fireResistance;
            }

            if (flag2 && entity.fire > 0) {
                entity.playSound("random.fizz", 0.7F, 1.6F + (Main.rand.nextFloat() - Main.rand.nextFloat()) * 0.4F);
                entity.fire = -entity.fireResistance;
            }

            entity.worldObj.theProfiler.endSection();
        }
    }

    public static void playStepSound(EntityLivingBase entityLivingBase, int x, int y, int z, Block blockIn) {
        Block.SoundType soundtype = blockIn.stepSound;
        if (entityLivingBase.worldObj.getBlock(x, y + 1, z) == Blocks.snow_layer) {
            soundtype = Blocks.snow_layer.stepSound;
            entityLivingBase.playSound(soundtype.getStepSound(), soundtype.getVolume() * 0.15F, soundtype.getFrequency());
        } else if (!blockIn.getMaterial().isLiquid()) {
            entityLivingBase.playSound(soundtype.getStepSound(), soundtype.getVolume() * 0.15F, soundtype.getFrequency());
        }
    }

    public static int getMaxType(EnumCreatureType enumCreatureType) {
        return WorldManager.maxMonsterType;
    }

    @Hook(returnCondition = ReturnCondition.ON_TRUE, returnAnotherMethod = "getMaxType")
    public static boolean getMaxNumberOfCreature(EnumCreatureType enumCreatureType) {
        return enumCreatureType == EnumCreatureType.monster;
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS, createMethod = true)
    public static float getBrightness(EntityPlayer entityPlayer, float p_70013_1_) {
        int i = MathHelper.floor_double(entityPlayer.posX);
        int j = MathHelper.floor_double(entityPlayer.posZ);
        if (entityPlayer.worldObj.blockExists(i, 0, j)) {
            int k = MathHelper.floor_double(entityPlayer.boundingBox.maxY - 0.2f);
            int k2 = MathHelper.floor_double(entityPlayer.boundingBox.minY + 0.2f);
            return Math.max(entityPlayer.worldObj.getLightBrightnessForSkyBlocks(i, k, j, 0), entityPlayer.worldObj.getLightBrightnessForSkyBlocks(i, k2, j, 0));
        } else {
            return 0.0F;
        }
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void knockBack(EntityLivingBase en, Entity p_70653_1_, float p_70653_2_, double p_70653_3_, double p_70653_5_) {
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static boolean onEntityCollidedWithBlock(BlockWeb web, World worldIn, int x, int y, int z, Entity entityIn) {
        return false;
    }

    @Hook(returnCondition = ReturnCondition.ON_TRUE)
    public static boolean setInWeb(Entity entity) {
        return !(entity instanceof EntityLivingBase);
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static boolean getCanSpawnHere(EntitySquid squid) {
        return Main.rand.nextBoolean() && squid.posY > 45.0D && squid.posY < 63.0D && squid.worldObj.checkNoEntityCollision(squid.boundingBox);
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    @SuppressWarnings("rawtypes")
    public static void onDeath(EntityPlayerMP entityPlayerMP, DamageSource p_70645_1_) {
        if (ForgeHooks.onLivingDeath(entityPlayerMP, p_70645_1_)) return;

        if (!entityPlayerMP.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) {
            entityPlayerMP.captureDrops = true;
            entityPlayerMP.capturedDrops.clear();

            entityPlayerMP.inventory.dropAllItems();

            entityPlayerMP.captureDrops = false;
            for (EntityItem item : entityPlayerMP.capturedDrops) {
                entityPlayerMP.joinEntityItemWithWorld(item);
            }
        }

        Collection collection = entityPlayerMP.worldObj.getScoreboard().func_96520_a(IScoreObjectiveCriteria.deathCount);

        for (Object o : collection) {
            ScoreObjective scoreobjective = (ScoreObjective) o;
            Score score = entityPlayerMP.getWorldScoreboard().getValueFromObjective(entityPlayerMP.getCommandSenderName(), scoreobjective);
            score.func_96648_a();
        }

        EntityLivingBase entitylivingbase = entityPlayerMP.func_94060_bK();

        if (entitylivingbase != null) {
            int i = EntityList.getEntityID(entitylivingbase);
            EntityList.EntityEggInfo entityegginfo = (EntityList.EntityEggInfo) EntityList.entityEggs.get(i);

            if (entityegginfo != null) {
                entityPlayerMP.addStat(entityegginfo.field_151513_e, 1);
            }

            entitylivingbase.addToPlayerScore(entityPlayerMP, entityPlayerMP.getScore());
        }

        entityPlayerMP.addStat(StatList.deathsStat, 1);
        entityPlayerMP.getCombatTracker().func_94549_h();
    }
}
