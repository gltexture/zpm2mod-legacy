package ru.BouH_.hook.server;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandTime;
import net.minecraft.command.CommandWeather;
import net.minecraft.command.ICommandSender;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.EntityTrackerEntry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.WorldInfo;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerFlyableFallEvent;
import net.minecraftforge.oredict.OreDictionary;
import ru.BouH_.commands.CommandWth;
import ru.BouH_.inventory.containers.ContainerWorkbenchNew;
import ru.BouH_.items.gun.crossbow.ItemCrossbow;
import ru.BouH_.proxy.CommonProxy;
import ru.BouH_.weather.base.WeatherHandler;
import ru.BouH_.weather.managers.WeatherRainManager;
import ru.BouH_.world.biome.BiomeGas;
import ru.BouH_.world.biome.BiomeRad;
import ru.hook.asm.Hook;
import ru.hook.asm.ReturnCondition;

import java.util.List;

public class MiscHook {
    @Hook(returnCondition = ReturnCondition.ALWAYS, createMethod = true)
    public static void updateFallState(EntityPlayer entity, double distanceFallenThisTick, boolean isOnGround) {
        if (entity.isInWater()) {
            if (Math.abs(distanceFallenThisTick) <= 0.25f) {
                entity.fallDistance = 0.0F;
            }
        }
        if (isOnGround) {
            if (entity.fallDistance > 0.0F) {
                MiscHook.fall(entity, entity.fallDistance);
                entity.fallDistance = 0.0F;
            }
        } else if (distanceFallenThisTick < 0.0D) {
            entity.fallDistance = (float) ((double) entity.fallDistance - distanceFallenThisTick);
        }
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static boolean isDaytime(World world) {
        return world.getWorldTime() % 24000 < 13500 || world.getWorldTime() % 24000 >= 23500;
    }

    @Hook(returnCondition = ReturnCondition.ON_TRUE)
    public static boolean initVanillaEntries(OreDictionary oreDictionary) {
        return !CommonProxy.initRecipes;
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void displayGUIWorkbench(EntityPlayerMP entityPlayerMP, int p_71058_1_, int p_71058_2_, int p_71058_3_) {
        entityPlayerMP.getNextWindowId();
        entityPlayerMP.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(entityPlayerMP.currentWindowId, 1, "Crafting", 9, true));
        entityPlayerMP.openContainer = new ContainerWorkbenchNew(entityPlayerMP.inventory, entityPlayerMP.worldObj, p_71058_1_, p_71058_2_, p_71058_3_);
        entityPlayerMP.openContainer.windowId = entityPlayerMP.currentWindowId;
        entityPlayerMP.openContainer.onCraftGuiOpened(entityPlayerMP);
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void triggerAchievement(EntityPlayer entityPlayer, StatBase p_71029_1_) {
    }

    @Hook(returnCondition = ReturnCondition.ON_TRUE, booleanReturnConstant = true)
    public static boolean canEnchantItem(EnumEnchantmentType enumEnchantmentType, Item p_77557_1_) {
        return p_77557_1_ instanceof ItemCrossbow && enumEnchantmentType == EnumEnchantmentType.bow;
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static boolean canCoordinateBeSpawn(WorldProvider worldProvider, int p_76566_1_, int p_76566_2_) {
        if (MiscHook.checkBiome(worldProvider.worldObj, p_76566_1_, p_76566_2_) || MiscHook.checkBiome(worldProvider.worldObj, p_76566_1_ - 16, p_76566_2_) || MiscHook.checkBiome(worldProvider.worldObj, p_76566_1_ + 16, p_76566_2_) || MiscHook.checkBiome(worldProvider.worldObj, p_76566_1_, p_76566_2_ - 16) || MiscHook.checkBiome(worldProvider.worldObj, p_76566_1_, p_76566_2_ + 16)) {
            return false;
        }
        return worldProvider.worldObj.getTopBlock(p_76566_1_, p_76566_2_) == Blocks.grass;
    }

    private static boolean checkBiome(World world, int x, int z) {
        return world.getBiomeGenForCoords(x, z) instanceof BiomeRad || world.getBiomeGenForCoords(x, z) instanceof BiomeGas;
    }

    @Hook(returnCondition = ReturnCondition.ON_TRUE)
    public static boolean func_147467_a(World world, int p_147467_1_, int p_147467_2_, Chunk p_147467_3_) {
        return p_147467_3_ == null;
    }

    public static void fall(EntityPlayer entity, float distance) {
        if (!entity.capabilities.allowFlying) {
            if (distance >= 2.0F) {
                entity.addStat(StatList.distanceFallenStat, (int) Math.round((double) distance * 100.0D));
            }
            distance = ForgeHooks.onLivingFall((EntityLivingBase) entity.riddenByEntity, distance);
            if (distance <= 0) {
                return;
            }
            if (entity.riddenByEntity != null) {
                MiscHook.fall((EntityPlayer) entity.riddenByEntity, distance);
            }
            PotionEffect potioneffect = entity.getActivePotionEffect(Potion.jump);
            float f1 = potioneffect != null ? (float) (potioneffect.getAmplifier() + 1) : 0.0F;
            int i = MathHelper.ceiling_float_int(distance - 3.0F - f1);

            if (i > 0) {
                entity.playSound(i > 4 ? "game.neutral.hurt.fall.big" : "game.neutral.hurt.fall.small", 1.0F, 1.0F);
                entity.attackEntityFrom(DamageSource.fall, (float) i);
                int j = MathHelper.floor_double(entity.posX);
                int k = MathHelper.floor_double(entity.posY - 0.2D - (double) entity.yOffset);
                int l = MathHelper.floor_double(entity.posZ);
                Block block = entity.worldObj.getBlock(j, k, l);

                if (block.getMaterial() != Material.air) {
                    Block.SoundType soundtype = block.stepSound;
                    entity.playSound(soundtype.getStepSound(), soundtype.getVolume() * 0.5F, soundtype.getFrequency() * 0.75F);
                }
            }
        } else {
            MinecraftForge.EVENT_BUS.post(new PlayerFlyableFallEvent(entity, distance));
        }
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void playStepSound(Entity entity, int x, int y, int z, Block blockIn) {
    }

    @Hook(returnCondition = ReturnCondition.ON_TRUE)
    public static boolean isBlockTickScheduledThisTick(WorldServer worldServer, int p_147477_1_, int p_147477_2_, int p_147477_3_, Block p_147477_4_) {
        return worldServer.pendingTickListEntriesThisTick == null;
    }

    @Hook(returnCondition = ReturnCondition.ON_TRUE)
    public static boolean addBlockEvent(WorldServer worldServer, int x, int y, int z, Block blockIn, int eventId, int eventParameter) {
        return worldServer.field_147490_S == null;
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS, createMethod = true)
    public static boolean handleWaterMovement(EntityPlayer entity) {
        if (entity.worldObj.handleMaterialAcceleration(entity.boundingBox.expand(0.0D, -0.4000000059604645D, 0.0D).contract(0.001D, 0.001D, 0.001D), Material.water, entity)) {
            if (!entity.inWater && !entity.firstUpdate) {
                float f = MathHelper.sqrt_double(entity.motionX * entity.motionX * 0.20000000298023224D + entity.motionY * entity.motionY + entity.motionZ * entity.motionZ * 0.20000000298023224D) * 0.2F;
                if (f > 1.0F) {
                    f = 1.0F;
                }
                entity.playSound("game.player.swim.splash", f, 1.0F + (entity.rand.nextFloat() - entity.rand.nextFloat()) * 0.4F);
                float f1 = (float) MathHelper.floor_double(entity.boundingBox.minY);
                int i;
                float f2;
                float f3;

                for (i = 0; (float) i < 1.0F + entity.width * 20.0F; ++i) {
                    f2 = (entity.rand.nextFloat() * 2.0F - 1.0F) * entity.width;
                    f3 = (entity.rand.nextFloat() * 2.0F - 1.0F) * entity.width;
                    entity.worldObj.spawnParticle("bubble", entity.posX + (double) f2, f1 + 1.0F, entity.posZ + (double) f3, entity.motionX, entity.motionY - (double) (entity.rand.nextFloat() * 0.2F), entity.motionZ);
                }

                for (i = 0; (float) i < 1.0F + entity.width * 20.0F; ++i) {
                    f2 = (entity.rand.nextFloat() * 2.0F - 1.0F) * entity.width;
                    f3 = (entity.rand.nextFloat() * 2.0F - 1.0F) * entity.width;
                    entity.worldObj.spawnParticle("splash", entity.posX + (double) f2, f1 + 1.0F, entity.posZ + (double) f3, entity.motionX, entity.motionY, entity.motionZ);
                }
            }
            entity.inWater = true;
            entity.fire = 0;
        } else {
            entity.inWater = false;
        }
        return entity.inWater;
    }

    @Hook(createMethod = true)
    public static boolean canSnowAt(WorldServer worldServer, int x, int y, int z, boolean checkLight) {
        return false;
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static boolean isRaining(World world) {
        if (WeatherHandler.instance.rainManagerMap.containsKey(world.getWorldInfo().getDimension())) {
            WeatherRainManager weatherRainManager = (WeatherRainManager) WeatherHandler.instance.getWorldRainInfo(world.getWorldInfo().getDimension());
            return weatherRainManager.isStarted() && weatherRainManager.getRainStrength() > 0.3f;
        }
        return false;
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static float getRainStrength(World world, float p_72867_1_) {
        return WeatherHandler.instance.getWeatherRain().prevCloudStrength + (WeatherHandler.instance.getWeatherRain().currentCloudStrength - WeatherHandler.instance.getWeatherRain().prevCloudStrength) * p_72867_1_;
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static boolean isRaining(WorldInfo worldInfo) {
        if (WeatherHandler.instance.rainManagerMap.containsKey(worldInfo.getDimension())) {
            WeatherRainManager weatherRainManager = (WeatherRainManager) WeatherHandler.instance.getWorldRainInfo(worldInfo.getDimension());
            return weatherRainManager.isStarted() && weatherRainManager.getRainStrength() > 0.3f;
        }
        return false;
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void updateWeatherBody(World world) {
    }

    @SuppressWarnings("rawtypes")
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static List addTabCompletionOptions(CommandWeather weather, ICommandSender sender, String[] args) {
        return args.length == 1 ? CommandBase.getListOfStringsMatchingLastWord(args, "clear", "rain", "fog") : null;
    }

    @Hook(injectOnExit = true)
    public static void setTime(CommandTime commandTime, ICommandSender p_71552_1_, int p_71552_2_) {
        p_71552_1_.getEntityWorld().getWorldInfo().incrementTotalWorldTime((((p_71552_1_.getEntityWorld().getWorldInfo().getWorldTotalTime() / 24000)) * 24000) + p_71552_2_);
    }

    @Hook(injectOnExit = true)
    public static void addTime(CommandTime commandTime, ICommandSender p_71553_1_, int p_71553_2_) {
        p_71553_1_.getEntityWorld().getWorldInfo().incrementTotalWorldTime(p_71553_1_.getEntityWorld().getWorldInfo().getWorldTotalTime() + p_71553_2_);
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void processCommand(CommandWeather weather, ICommandSender sender, String[] args) {
        CommandWth.instance.processCommand(sender, args);
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static float getBlockDensity(World world, Vec3 p_72842_1_, AxisAlignedBB p_72842_2_) {
        double d0 = 1.0D / ((p_72842_2_.maxX - p_72842_2_.minX) * 2.0D + 1.0D);
        double d1 = 1.0D / ((p_72842_2_.maxY - p_72842_2_.minY) * 2.0D + 1.0D);
        double d2 = 1.0D / ((p_72842_2_.maxZ - p_72842_2_.minZ) * 2.0D + 1.0D);

        if (d0 >= 0.0D && d1 >= 0.0D && d2 >= 0.0D) {
            int i = 0;
            int j = 0;

            for (float f = 0.0F; f <= 1.0F; f = (float) ((double) f + d0)) {
                for (float f1 = 0.0F; f1 <= 1.0F; f1 = (float) ((double) f1 + d1)) {
                    for (float f2 = 0.0F; f2 <= 1.0F; f2 = (float) ((double) f2 + d2)) {
                        double d3 = p_72842_2_.minX + (p_72842_2_.maxX - p_72842_2_.minX) * (double) f;
                        double d4 = p_72842_2_.minY + (p_72842_2_.maxY - p_72842_2_.minY) * (double) f1;
                        double d5 = p_72842_2_.minZ + (p_72842_2_.maxZ - p_72842_2_.minZ) * (double) f2;

                        if (world.rayTraceBlocks(Vec3.createVectorHelper(d3, d4, d5), p_72842_1_, false, true, true) == null) {
                            ++i;
                        }

                        ++j;
                    }
                }
            }

            return (float) i / (float) j;
        } else {
            return 0.0F;
        }
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    @SuppressWarnings("unchecked")
    public static void addEntityToTracker(EntityTracker e, Entity p_72785_1_, int p_72785_2_, final int p_72785_3_, boolean p_72785_4_) {
        p_72785_2_ = 256;

        try {
            if (e.trackedEntityHashTable.containsItem(p_72785_1_.getEntityId())) {
                throw new IllegalStateException("Entity is already tracked!");
            }

            EntityTrackerEntry entitytrackerentry = new EntityTrackerEntry(p_72785_1_, p_72785_2_, p_72785_3_, p_72785_4_);
            e.trackedEntities.add(entitytrackerentry);
            e.trackedEntityHashTable.addKey(p_72785_1_.getEntityId(), entitytrackerentry);
            entitytrackerentry.updatePlayerEntities(e.theWorld.playerEntities);
        } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Adding entity to track");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity To Track");
            crashreportcategory.addCrashSection("Tracking range", p_72785_2_ + " blocks");
            crashreportcategory.addCrashSectionCallable("Update interval", () -> {
                String s = "Once per " + p_72785_3_ + " ticks";

                if (p_72785_3_ == Integer.MAX_VALUE) {
                    s = "Maximum (" + s + ")";
                }

                return s;
            });
            p_72785_1_.addEntityCrashInfo(crashreportcategory);
            CrashReportCategory crashreportcategory1 = crashreport.makeCategory("Entity That Is Already Tracked");
            ((EntityTrackerEntry) e.trackedEntityHashTable.lookup(p_72785_1_.getEntityId())).trackedEntity.addEntityCrashInfo(crashreportcategory1);

            try {
                throw new ReportedException(crashreport);
            } catch (ReportedException reportedexception) {
                //logger.error("\"Silently\" catching entity tracking error.", reportedexception);
            }
        }
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static int xpBarCap(EntityPlayer pl) {
        return (int) ((pl.experienceLevel >= 30 ? 62 + (pl.experienceLevel - 30) * 7 : (pl.experienceLevel >= 15 ? 17 + (pl.experienceLevel - 15) * 3 : 17)) * (pl.experienceLevel < 0 ? 1.25f : 2.5f));
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void addExperienceLevel(EntityPlayer pl, int p_82242_1_) {
        pl.experienceLevel += p_82242_1_;
        if (p_82242_1_ > 0 && pl.experienceLevel % 5 == 0) {
            float f = pl.experienceLevel > 30 ? 1.0F : (float) pl.experienceLevel / 30.0F;
            pl.worldObj.playSoundAtEntity(pl, "random.levelup", f * 0.75F, 1.0F);
        }
    }
}
