package ru.BouH_.gameplay;

import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.particle.EntityBubbleFX;
import net.minecraft.client.particle.EntityExplodeFX;
import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.play.server.S1FPacketSetExperience;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.minecart.MinecartCollisionEvent;
import ru.BouH_.ConfigZp;
import ru.BouH_.GoodPeople;
import ru.BouH_.Main;
import ru.BouH_.achievements.AchievementManager;
import ru.BouH_.entity.ieep.Hunger;
import ru.BouH_.entity.ieep.PlayerMiscData;
import ru.BouH_.entity.ieep.Thirst;
import ru.BouH_.entity.particle.EntityParticleColoredCloud;
import ru.BouH_.entity.particle.EntityParticleNausea;
import ru.BouH_.entity.projectile.EntityGrenadeGas;
import ru.BouH_.entity.zombie.*;
import ru.BouH_.gameplay.client.ClientHandler;
import ru.BouH_.gameplay.client.GameHud;
import ru.BouH_.gameplay.client.PainUpdater;
import ru.BouH_.init.BlocksZp;
import ru.BouH_.init.FluidsZp;
import ru.BouH_.init.ItemsZp;
import ru.BouH_.inventory.containers.ContainerPlayerNew;
import ru.BouH_.items.gun.base.AGunBase;
import ru.BouH_.items.gun.crossbow.ItemCrossbow;
import ru.BouH_.items.melee.ItemBloodsucker;
import ru.BouH_.items.melee.ItemSpear;
import ru.BouH_.misc.DamageSourceZp;
import ru.BouH_.misc.ZpTeleport;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.packets.misc.PacketDay;
import ru.BouH_.network.packets.misc.PacketNausea;
import ru.BouH_.network.packets.misc.PacketPain;
import ru.BouH_.network.packets.particles.ParticleBlood2;
import ru.BouH_.network.packets.particles.ParticleCloud;
import ru.BouH_.network.packets.particles.ParticleCowReloading;
import ru.BouH_.proxy.CommonProxy;
import ru.BouH_.skills.SkillManager;
import ru.BouH_.utils.*;
import ru.BouH_.weather.base.WeatherHandler;
import ru.BouH_.weather.managers.WeatherFogManager;
import ru.BouH_.weather.managers.WeatherRainManager;

import java.util.List;
import java.util.Objects;


public class EntityManager {
    private int clientMaskCd;

    @SuppressWarnings("unchecked")
    @SubscribeEvent()
    public void onUpdate(LivingUpdateEvent ev) {
        EntityLivingBase ent = ev.entityLiving;
        if (ent != null && ent.isEntityAlive()) {
            int x = MathHelper.floor_double(ent.posX);
            int z = MathHelper.floor_double(ent.posZ);
            if (!ev.entity.worldObj.isRemote) {
                if (ev.entity instanceof EntityPig) {
                    EntityPig entityPig = (EntityPig) ev.entityLiving;
                    if (entityPig.hasCustomNameTag() && entityPig.getCustomNameTag().equals(GoodPeople.dungeonMaster)) {
                        ExplosionUtils.makeExplosion(entityPig.worldObj, null, entityPig, entityPig.posX, entityPig.posY, entityPig.posZ, 0.1f, false, false, true);
                        entityPig.setDead();
                    }
                }
                if (ev.entity instanceof EntityChicken) {
                    if (ev.entity.getEntityData().getBoolean("canChickenReload")) {
                        EntityChicken chicken = (EntityChicken) ev.entity;
                        if (!chicken.isChild()) {
                            if (--chicken.timeUntilNextEgg <= 0) {
                                if (Main.rand.nextFloat() <= 0.7f) {
                                    chicken.playSound("mob.chicken.plop", 1.0F, (Main.rand.nextFloat() - Main.rand.nextFloat()) * 0.2F + 1.0F);
                                    chicken.dropItem(Items.egg, 1);
                                    if (Main.rand.nextBoolean()) {
                                        chicken.getEntityData().setBoolean("canChickenReload", false);
                                    }
                                }
                                chicken.timeUntilNextEgg = Main.rand.nextInt(12000) + 4001;
                            }
                        }
                    }
                } else if (ev.entity instanceof EntityCow) {
                    if (ev.entity.getEntityData().getBoolean("canCowReload")) {
                        int i1 = ev.entity.getEntityData().getInteger("cowReloading");
                        if (i1 < 1200) {
                            if (i1 == 1199) {
                                NetworkHandler.NETWORK.sendToAllAround(new ParticleCowReloading(MathHelper.floor_double(ev.entity.posX), MathHelper.floor_double(ev.entity.posY), MathHelper.floor_double(ev.entity.posZ)), new NetworkRegistry.TargetPoint(ent.worldObj.getWorldInfo().getDimension(), ent.posX, ent.posY, ent.posZ, 256));
                            }
                            ev.entity.getEntityData().setInteger("cowReloading", ev.entity.getEntityData().getInteger("cowReloading") + 1);
                        }
                    }
                }
            }
            if (ent instanceof EntityPlayer || ent instanceof EntityAnimal || ent instanceof EntityVillager) {
                if (this.isInBoilingWater(ent)) {
                    if (!ent.worldObj.isRemote) {
                        ent.attackEntityFrom(DamageSourceZp.boil, 1);
                    } else {
                        this.spawnParticlesBoiling(ent);
                    }
                }
            }

            if (ent instanceof EntityPlayer) {
                EntityPlayer pl = (EntityPlayer) ent;
                pl.getFoodStats().foodLevel = 10;
                if (pl.worldObj.isRemote) {
                    if (Main.settingsZp.fastFly.isFlag() && pl.isSprinting()) {
                        pl.capabilities.setFlySpeed(0.16f);
                    } else {
                        pl.capabilities.setFlySpeed(0.05f);
                    }
                    if (pl.ticksExisted % 2 == 0 && pl.isInsideOfMaterial(Material.water)) {
                        this.spawnParticlesBubbles(pl);
                    }
                    if (!pl.capabilities.isCreativeMode) {
                        if (pl.getHealth() <= 45 || pl.isPotionActive(28)) {
                            this.heartSound(pl);
                        }
                    }
                    this.nauseaEffects(pl);
                    this.maskSoundPlayers(pl);
                } else {
                    PlayerMiscData playerMiscData = PlayerMiscData.getPlayerData(pl);
                    playerMiscData.updateAABBHistory();
                    if (pl instanceof EntityPlayerMP) {
                        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) pl;
                        if (ConfigZp.forceZombieTrackingUpdate || ConfigZp.forcePlayerTrackingUpdate) {
                            if (entityPlayerMP.ticksExisted % 60 == 0) {
                                List<Entity> entities = entityPlayerMP.worldObj.getEntitiesWithinAABBExcludingEntity(entityPlayerMP, pl.boundingBox.expand(16, 16, 16));
                                for (Entity entity : entities) {
                                    if ((ConfigZp.forceZombieTrackingUpdate && entity instanceof AZombieBase) || (ConfigZp.forcePlayerTrackingUpdate && entity instanceof EntityPlayer)) {
                                        entityPlayerMP.getServerForPlayer().getEntityTracker().trackEntity(entity);
                                    }
                                }
                            }
                        }

                        if (EntityUtils.isInBlock(pl, FluidsZp.portalZp)) {
                            if (!entityPlayerMP.getEntityData().getBoolean("wasInPortal")) {
                                int dim = entityPlayerMP.dimension == 0 ? 2 : 0;
                                NetworkHandler.NETWORK.sendToAllAround(new ParticleCloud(entityPlayerMP.posX, entityPlayerMP.posY + 0.25f, entityPlayerMP.posZ, 0, 0.1f, 0, 0.85f, 0.85f, 1.0f, 0.5f, 18), new NetworkRegistry.TargetPoint(entityPlayerMP.dimension, entityPlayerMP.posX, entityPlayerMP.posY + 1.0f, entityPlayerMP.posZ, 32));
                                MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension(entityPlayerMP, dim, new ZpTeleport(DimensionManager.getWorld(dim)));
                                NetworkHandler.NETWORK.sendToAllAround(new ParticleCloud(entityPlayerMP.posX, entityPlayerMP.posY + 0.25f, entityPlayerMP.posZ, 0, 0.1f, 0, 0.85f, 0.85f, 1.0f, 0.5f, 18), new NetworkRegistry.TargetPoint(entityPlayerMP.dimension, entityPlayerMP.posX, entityPlayerMP.posY + 1.0f, entityPlayerMP.posZ, 32));
                                entityPlayerMP.getEntityData().setBoolean("wasInPortal", true);
                            }
                        } else {
                            entityPlayerMP.getEntityData().setBoolean("wasInPortal", false);
                        }
                    }
                    if (!pl.capabilities.isCreativeMode) {
                        boolean flag = true;
                        if (pl.getHeldItem() != null && pl.getHeldItem().getItem() == ItemsZp.gps) {
                            if (pl.ticksExisted % 20 == 0) {
                                pl.getHeldItem().damageItem(1, pl);
                                if (pl.getHeldItem().stackSize <= 0) {
                                    pl.setCurrentItemOrArmor(0, null);
                                }
                            }
                        }
                        if (pl.isInsideOfMaterial(Material.water)) {
                            if ((EntityUtils.isInArmor(pl, ItemsZp.aqualung_helmet, ItemsZp.aqualung_chestplate, ItemsZp.aqualung_leggings, ItemsZp.aqualung_boots) || EntityUtils.isInArmor(pl, ItemsZp.indcostume_helmet, ItemsZp.indcostume_chestplate, ItemsZp.indcostume_leggings, ItemsZp.indcostume_boots)) && pl.inventory.hasItem(ItemsZp.oxygen)) {
                                if (pl.ticksExisted % 60 == 0) {
                                    pl.setAir(300);
                                    this.damageItem(ItemsZp.oxygen, pl.inventory, pl);
                                }
                                if (pl.ticksExisted % 300 == 0) {
                                    pl.inventory.damageArmor(4);
                                }
                            } else {
                                if (pl.ticksExisted % 10 == 0) {
                                    int i1 = pl.getEntityData().getInteger("nausea");
                                    pl.getEntityData().setInteger("nausea", i1 + 1);
                                    if (i1 >= 80) {
                                        pl.addPotionEffect(new PotionEffect(9, 100));
                                    }
                                }
                                flag = false;
                            }
                        }
                        if (EntityUtils.isInBlock(pl, FluidsZp.toxicwater_block)) {
                            if (pl.ticksExisted % 2 == 0) {
                                pl.getEntityData().setInteger("nausea", pl.getEntityData().getInteger("nausea") + 1);
                            }
                            flag = false;
                        }
                        if (pl.getEntityData().getInteger("radiation") >= 20) {
                            if (pl.ticksExisted % Math.max(300 - ent.getEntityData().getInteger("radiation") * 0.5f, 10) == 0) {
                                pl.getEntityData().setInteger("nausea", ent.getEntityData().getInteger("nausea") + 1);
                            }
                            flag = false;
                        }
                        if (flag && pl.ticksExisted % 10 == 0) {
                            if (pl.getEntityData().getInteger("nausea") > 0) {
                                pl.getEntityData().setInteger("nausea", pl.getEntityData().getInteger("nausea") - 1);
                            }
                        }
                        if (pl.getEntityData().getInteger("nausea") >= 100) {
                            NetworkHandler.NETWORK.sendToAllAround(new PacketNausea(pl.getEntityId()), new NetworkRegistry.TargetPoint(pl.dimension, pl.posX, pl.posY, pl.posZ, 256));
                            pl.worldObj.playSoundAtEntity(pl, Main.MODID + ":nausea", 3.0F, 0.8F + Main.rand.nextFloat() * 0.2f);
                            Thirst thirst = Thirst.getThirst(pl);
                            Hunger hunger = Hunger.getHunger(pl);
                            thirst.removeThirst(75);
                            hunger.removeHunger(50);
                            pl.addPotionEffect(new PotionEffect(15, 200));
                            pl.addPotionEffect(new PotionEffect(17, 300));
                            pl.addPotionEffect(new PotionEffect(31, 300));
                            pl.getEntityData().setInteger("nausea", 0);
                        } else if (pl.getEntityData().getInteger("nausea") >= 50) {
                            if (!pl.isPotionActive(9) || pl.getActivePotionEffect(Potion.confusion).getDuration() <= 120) {
                                pl.addPotionEffect(new PotionEffect(9, 120));
                            }
                        }
                    } else {
                        pl.getEntityData().setInteger("nausea", 0);
                    }
                    if (pl.getEntityData().getInteger("cdThrow") > 0) {
                        pl.getEntityData().setInteger("cdThrow", pl.getEntityData().getInteger("cdThrow") - 1);
                    }
                    if (!pl.isPotionActive(28)) {
                        PlayerMiscData.getPlayerData(pl).clearBloodInitializedId();
                    }
                }

                if (pl.getEntityData().getInteger("itemUsed") > 0) {
                    pl.getEntityData().setInteger("itemUsed", pl.getEntityData().getInteger("itemUsed") - 1);
                }

                if (EntityUtils.isInArmor(pl, ItemsZp.pnv, null, null, null)) {
                    if (pl.ticksExisted % 100 == 0) {
                        pl.getCurrentArmor(3).damageItem(1, pl);
                        if (pl.getCurrentArmor(3).stackSize <= 0) {
                            pl.inventory.armorInventory[3] = null;
                        }
                    }
                }
                boolean flag = false;
                if (EntityUtils.isEntityInHighRadiation(pl)) {
                    if (pl.inventory.hasItem(ItemsZp.dosimeter)) {
                        if (pl.ticksExisted % 100 == 0) {
                            this.damageItem(ItemsZp.dosimeter, pl.inventory, pl);
                        }
                    }
                    if ((EntityUtils.isInArmor(pl, ItemsZp.rad_helmet, ItemsZp.rad_chestplate, ItemsZp.rad_leggings, ItemsZp.rad_boots) || EntityUtils.isInArmor(pl, ItemsZp.indcostume_helmet, ItemsZp.indcostume_chestplate, ItemsZp.indcostume_leggings, ItemsZp.indcostume_boots)) && pl.inventory.hasItem(ItemsZp.CBS)) {
                        if (pl.ticksExisted % 100 == 0) {
                            pl.inventory.damageArmor(4);
                            this.damageItem(ItemsZp.CBS, pl.inventory, pl);
                        }
                    } else {
                        if (ent.getEntityData().getInteger("radiation") < 480) {
                            if (pl.ticksExisted % (pl.isPotionActive(33) ? 8 : 3) == 0) {
                                if (!pl.capabilities.isCreativeMode) {
                                    pl.getEntityData().setInteger("radiation", pl.getEntityData().getInteger("radiation") + 1);
                                }
                            }
                        }
                    }
                } else if (EntityUtils.isEntityInLowRadiation(pl)) {
                    if (pl.inventory.hasItem(ItemsZp.dosimeter)) {
                        if (pl.ticksExisted % 180 == 0) {
                            this.damageItem(ItemsZp.dosimeter, pl.inventory, pl);
                        }
                    }
                    if ((EntityUtils.isInArmor(pl, ItemsZp.rad_helmet, ItemsZp.rad_chestplate, ItemsZp.rad_leggings, ItemsZp.rad_boots) || EntityUtils.isInArmor(pl, ItemsZp.indcostume_helmet, ItemsZp.indcostume_chestplate, ItemsZp.indcostume_leggings, ItemsZp.indcostume_boots)) && pl.inventory.hasItem(ItemsZp.CBS)) {
                        if (pl.ticksExisted % 200 == 0) {
                            pl.inventory.damageArmor(4);
                            this.damageItem(ItemsZp.CBS, pl.inventory, pl);
                        }
                    } else {
                        if (pl.getEntityData().getInteger("radiation") < 240) {
                            if (pl.ticksExisted % 6 == 0) {
                                if (!pl.capabilities.isCreativeMode) {
                                    if (!pl.isPotionActive(33)) {
                                        pl.getEntityData().setInteger("radiation", pl.getEntityData().getInteger("radiation") + 1);
                                    }
                                }
                            }
                        }
                    }
                } else {
                    flag = true;
                }

                if (flag || pl.isPotionActive(33)) {
                    if (pl.ticksExisted % 20 == 0 && pl.getEntityData().getInteger("radiation") > 0) {
                        pl.getEntityData().setInteger("radiation", pl.getEntityData().getInteger("radiation") - 1);
                    }
                }

                if (pl.inventory.hasItem(ItemsZp.dosimeter) && pl.getEntityData().getInteger("radiation") > 0) {
                    if (pl.ticksExisted % 200 == 0) {
                        this.damageItem(ItemsZp.dosimeter, pl.inventory, pl);
                    }
                }
            } else {
                if (ent.getEntityData().getInteger("radiation") < 480) {
                    if (ent.ticksExisted % 4 == 0) {
                        if (EntityUtils.isEntityInHighRadiation(ent)) {
                            ent.getEntityData().setInteger("radiation", ent.getEntityData().getInteger("radiation") + 2);
                        } else if (EntityUtils.isEntityInLowRadiation(ent)) {
                            if (ent.getEntityData().getInteger("radiation") < 240) {
                                ent.getEntityData().setInteger("radiation", ent.getEntityData().getInteger("radiation") + 1);
                            }
                        }
                    }
                }
            }
            if (!(ent instanceof EntityPlayer)) {
                boolean zm = (ent instanceof AZombieBase) && ent.worldObj.getBiomeGenForCoords(x, z) == CommonProxy.biome_antiZm || EntityUtils.isInBlock(ent, BlocksZp.barrier);
                boolean oth = (ent instanceof EntityLiving) && EntityUtils.isInBlock(ent, BlocksZp.living_barrier);
                if (zm || oth) {
                    ent.worldObj.playSoundAtEntity(ent, "random.fizz", 1F, 0.8F);
                    NetworkHandler.NETWORK.sendToAllAround(new ParticleCloud(ent.posX, ent.posY + 0.5f, ent.posZ, Main.rand.nextGaussian() * 0.05D, Main.rand.nextGaussian() * 0.05D + 0.1f, Main.rand.nextGaussian() * 0.05D, 0.8f, 1.0f, 1.0f, 1.2f - Main.rand.nextFloat() * 0.4f, 24), new NetworkRegistry.TargetPoint(ent.worldObj.getWorldInfo().getDimension(), ent.posX, ent.posY, ent.posZ, 256));
                    ent.setDead();
                }
            }
            if (ent instanceof AZombieBase) {
                if (!ent.worldObj.isRemote) {
                    AZombieBase aZombieBase = (AZombieBase) ent;
                    if (ent.getEntityData().getInteger("radiation") >= 100 && ent.getEntityData().getInteger("radiation") <= 240) {
                        aZombieBase.setModifierId(1);
                    } else if (ent.getEntityData().getInteger("radiation") > 360) {
                        aZombieBase.setModifierId(2);
                    }
                }
            } else {
                boolean flag = true;
                if (EntityUtils.isInBlock(ent, BlocksZp.gas) || ent.worldObj.getBiomeGenForCoords(x, z) == CommonProxy.biome_gas || this.isEntityInGasGrenadeRange(ent)) {
                    boolean flag1 = EntityUtils.isInArmor(ent, ItemsZp.gasmask, null, null, null);
                    boolean flag2 = EntityUtils.isInArmor(ent, ItemsZp.rad_helmet, null, null, null) || EntityUtils.isInArmor(ent, ItemsZp.indcostume_helmet, null, null, null);
                    if (ent instanceof EntityPlayer && (flag1 || flag2)) {
                        EntityPlayer entityPlayer = (EntityPlayer) ent;
                        if (entityPlayer.ticksExisted % (flag1 ? 80 : 20) == 0) {
                            entityPlayer.getCurrentArmor(3).damageItem(1, entityPlayer);
                            if (entityPlayer.getCurrentArmor(3).stackSize <= 0) {
                                entityPlayer.inventory.armorInventory[3] = null;
                            }
                        }
                    } else {
                        if (!ent.worldObj.isRemote) {
                            ent.getEntityData().setInteger("toxin", Math.min(ent.getEntityData().getInteger("toxin") + 1, 300));
                            ent.addPotionEffect(new PotionEffect(19, 300));
                            ent.addPotionEffect(new PotionEffect(9, 300));
                            flag = false;
                        }
                    }
                }
                if (EntityUtils.isInBlock(ent, FluidsZp.acidblock)) {
                    if (!(ent instanceof EntityPlayer && EntityUtils.isInArmor(ent, ItemsZp.indcostume_helmet, ItemsZp.indcostume_chestplate, ItemsZp.indcostume_leggings, ItemsZp.indcostume_boots))) {
                        ent.getEntityData().setInteger("acid", ent.getEntityData().getInteger("acid") + 1);
                        ent.attackEntityFrom(DamageSourceZp.acid, 1);
                        if (!ent.worldObj.isRemote) {
                            ent.getEntityData().setInteger("toxin", Math.min(ent.getEntityData().getInteger("toxin") + 1, 300));
                            flag = false;
                        }
                    } else if (ent.ticksExisted % 40 == 0) {
                        ((EntityPlayer) ent).inventory.damageArmor(4);
                    }
                }
                if (EntityUtils.isInBlock(ent, FluidsZp.toxicwater_block)) {
                    if (!(ent instanceof EntityPlayer && (EntityUtils.isInArmor(ent, ItemsZp.indcostume_helmet, ItemsZp.indcostume_chestplate, ItemsZp.indcostume_leggings, ItemsZp.indcostume_boots) || EntityUtils.isInArmor(ent, ItemsZp.aqualung_helmet, ItemsZp.aqualung_chestplate, ItemsZp.aqualung_leggings, ItemsZp.aqualung_boots) || EntityUtils.isInArmor(ent, ItemsZp.rad_helmet, ItemsZp.rad_chestplate, ItemsZp.rad_leggings, ItemsZp.rad_boots)))) {
                        if (!ent.worldObj.isRemote) {
                            ent.getEntityData().setInteger("toxin", Math.min(ent.getEntityData().getInteger("toxin") + 1, 300));
                            flag = false;
                        }
                    } else if (ent.ticksExisted % 60 == 0) {
                        ((EntityPlayer) ent).inventory.damageArmor(4);
                    }
                }
                if (ent.worldObj.getBiomeGenForCoords(x, z) == CommonProxy.biome_acid || EntityUtils.isInBlock(ent, BlocksZp.acidCloud)) {
                    if (!(ent instanceof EntityPlayer && EntityUtils.isInArmor(ent, ItemsZp.indcostume_helmet, ItemsZp.indcostume_chestplate, ItemsZp.indcostume_leggings, ItemsZp.indcostume_boots))) {
                        if (ent.ticksExisted % 8 == 0) {
                            ent.getEntityData().setInteger("acid", ent.getEntityData().getInteger("acid") + 1);
                        }
                        if (!ent.worldObj.isRemote) {
                            if (ent.ticksExisted % 2 == 0) {
                                ent.getEntityData().setInteger("toxin", Math.min(ent.getEntityData().getInteger("toxin") + 1, 300));
                            }
                            flag = false;
                        }
                    } else if (ent.ticksExisted % 40 == 0) {
                        ((EntityPlayer) ent).inventory.damageArmor(4);
                    }
                }

                if (!ent.worldObj.isRemote) {
                    if (ent.getEntityData().getInteger("toxin") >= 150) {
                        ent.addPotionEffect(new PotionEffect(17, 600));
                        ent.addPotionEffect(new PotionEffect(31, 600));
                        ent.addPotionEffect(new PotionEffect(19, 300));
                    }
                    if (flag) {
                        if (ent.getEntityData().getInteger("toxin") > 0) {
                            ent.getEntityData().setInteger("toxin", ent.getEntityData().getInteger("toxin") - 1);
                        }
                    }
                    if (ent.getEntityData().getInteger("radiation") >= 100 && ent.getEntityData().getInteger("radiation") < 180) {
                        if (ent instanceof EntityPlayer) {
                            AchievementManager.instance.triggerAchievement(AchievementManager.instance.radiation, (EntityPlayer) ent);
                        }
                        ent.addPotionEffect(new PotionEffect(17, 2400));
                        ent.addPotionEffect(new PotionEffect(31, 2400));
                    } else if (ent.getEntityData().getInteger("radiation") >= 180 && ent.getEntityData().getInteger("radiation") < 240) {
                        ent.addPotionEffect(new PotionEffect(17, 6000));
                        ent.addPotionEffect(new PotionEffect(31, 6000));
                        ent.addPotionEffect(new PotionEffect(9, 6000));
                    } else if (ent.getEntityData().getInteger("radiation") >= 240 && ent.getEntityData().getInteger("radiation") < 360) {
                        ent.addPotionEffect(new PotionEffect(19, 2400));
                        ent.addPotionEffect(new PotionEffect(17, 6000));
                        ent.addPotionEffect(new PotionEffect(31, 6000));
                        ent.addPotionEffect(new PotionEffect(9, 6000));
                        ent.addPotionEffect(new PotionEffect(15, 3600));
                    } else if (ent.getEntityData().getInteger("radiation") >= 360) {
                        ent.addPotionEffect(new PotionEffect(28, 2400, 2));
                        ent.addPotionEffect(new PotionEffect(17, 6000));
                        ent.addPotionEffect(new PotionEffect(31, 6000));
                        ent.addPotionEffect(new PotionEffect(9, 6000));
                        ent.addPotionEffect(new PotionEffect(15, 6000));
                    }
                    if (ent.getEntityData().getInteger("radiation") >= 50) {
                        if (ent.ticksExisted % 100 == 0) {
                            if (Main.rand.nextFloat() <= ent.getEntityData().getInteger("radiation") * 1.0E-5D) {
                                if (!ent.isPotionActive(26)) {
                                    ent.addPotionEffect(new PotionEffect(26, 21600));
                                }
                            }
                        }
                    }
                }
            }
            if (ent.getEntityData().getInteger("holy") > 0) {
                if (ent.worldObj.isRemote) {
                    this.spawnParticlesHoly(ent);
                }
                if (ent instanceof AZombieBase) {
                    ent.attackEntityFrom(DamageSource.generic, 15);
                } else {
                    ent.heal(1.0f);
                }
                ent.getEntityData().setInteger("holy", ent.getEntityData().getInteger("holy") - 1);
            }
            if (ent.getEntityData().getInteger("acid") > 0) {
                int acidCd = (EntityUtils.isInBlock(ent, Blocks.water) || EntityUtils.isInBlock(ent, FluidsZp.toxicwater_block)) ? 1 : 2;
                if (ent.ticksExisted % acidCd == 0) {
                    ent.getEntityData().setInteger("acid", ent.getEntityData().getInteger("acid") - 1);
                }
                if (ent instanceof EntityPlayer) {
                    EntityPlayer pl = (EntityPlayer) ent;
                    if (EntityUtils.isInArmor(pl, ItemsZp.indcostume_helmet, ItemsZp.indcostume_chestplate, ItemsZp.indcostume_leggings, ItemsZp.indcostume_boots)) {
                        if (pl.getEntityData().getInteger("acid") % 6 == 0) {
                            pl.inventory.damageArmor(4);
                        }
                    } else if (pl.ticksExisted % 2 == 0) {
                        this.damageAllItems(pl.inventory, pl);
                    }
                } else if (ent instanceof EntityIronGolem) {
                    ent.attackEntityFrom(DamageSource.generic, 10);
                }
                if (ent.worldObj.isRemote) {
                    this.spawnParticlesAcid(ent);
                }
            }
        }
    }

    private void damageItem(Item item, InventoryPlayer inv, EntityPlayer player) {
        for (int i = 0; i < inv.mainInventory.length; ++i) {
            if (inv.mainInventory[i] != null && inv.mainInventory[i].getItem() == item) {
                inv.mainInventory[i].damageItem(1, player);
                if (inv.mainInventory[i].stackSize <= 0) {
                    inv.mainInventory[i] = null;
                }
                break;
            }
        }
    }

    private void damageAllItems(InventoryPlayer inv, EntityPlayer player) {
        for (int i = 0; i < inv.mainInventory.length; ++i) {
            if (inv.mainInventory[i] != null) {
                Item item = inv.mainInventory[i].getItem();
                if (item != ItemsZp.poison && item != ItemsZp.military_bandage) {
                    inv.mainInventory[i].damageItem(1, player);
                    if (inv.mainInventory[i].stackSize <= 0) {
                        if (inv.mainInventory[i].getItem() instanceof AGunBase) {
                            ((AGunBase) inv.mainInventory[i].getItem()).clearAllModifiers(player, inv.mainInventory[i]);
                        }
                        inv.mainInventory[i] = null;
                    }
                }
            }
        }
        player.inventory.damageArmor(4);
    }

    private boolean isInBoilingWater(EntityLivingBase entityLivingBase) {
        World world = entityLivingBase.worldObj;
        AxisAlignedBB axisAlignedBB = entityLivingBase.boundingBox;
        int i = MathHelper.floor_double(axisAlignedBB.minX);
        int j = MathHelper.floor_double(axisAlignedBB.maxX + 0.999D);
        int k = MathHelper.floor_double(axisAlignedBB.minY);
        int l = MathHelper.floor_double(axisAlignedBB.maxY + 0.999D);
        int i1 = MathHelper.floor_double(axisAlignedBB.minZ);
        int j1 = MathHelper.floor_double(axisAlignedBB.maxZ + 0.999D);
        for (int k1 = i; k1 < j; ++k1) {
            for (int l1 = k; l1 < l; ++l1) {
                for (int i2 = i1; i2 < j1; ++i2) {
                    if (world.getBlock(k1, l1, i2).getMaterial() == Material.water) {
                        return EntityUtils.isBoilingWater(world, k1, l1, i2) || EntityUtils.isBoilingWater(world, k1, l1 - 1, i2);
                    }
                }
            }
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    private boolean isEntityInGasGrenadeRange(EntityLivingBase entityLivingBase) {
        List<EntityGrenadeGas> list = entityLivingBase.worldObj.selectEntitiesWithinAABB(EntityGrenadeGas.class, entityLivingBase.boundingBox.expand(4, 4, 4), new IEntitySelector() {
            @Override
            public boolean isEntityApplicable(Entity p_82704_1_) {
                return ((EntityGrenadeGas) p_82704_1_).isStarted();
            }
        });
        return !list.isEmpty();
    }

    @SideOnly(Side.CLIENT)
    private void nauseaEffects(EntityPlayer pl) {
        if (pl instanceof EntityPlayerSP) {
            if (!Minecraft.getMinecraft().thePlayer.isPotionActive(9)) {
                if (pl.getEntityData().getInteger("nausea") > 0 || Thirst.getThirst(Minecraft.getMinecraft().thePlayer).getThirst() <= 10 || Hunger.getHunger(Minecraft.getMinecraft().thePlayer).getHunger() <= 10) {
                    Minecraft.getMinecraft().thePlayer.timeInPortal = Math.min(Minecraft.getMinecraft().thePlayer.timeInPortal + 0.1F, 0.5f);
                }
            }
        }
        if (pl.getEntityData().getInteger("nausea") > 0) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleNausea(pl.worldObj, pl.posX + pl.getLookVec().xCoord * 0.35f, (PlayerMiscData.getPlayerData(pl).isLying() ? pl.boundingBox.minY + 0.2f : pl.boundingBox.maxY - 0.3f) + pl.getLookVec().yCoord * 0.3f, pl.posZ + pl.getLookVec().zCoord * 0.35f, pl.getLookVec().xCoord * 0.1f, pl.getLookVec().yCoord * 0.3f, pl.getLookVec().zCoord * 0.1f));
            pl.getEntityData().setInteger("nausea", pl.getEntityData().getInteger("nausea") - 1);
        }
    }

    @SideOnly(Side.CLIENT)
    private void maskSoundPlayers(EntityPlayer pl) {
        boolean flag1 = EntityUtils.isInArmor(pl, ItemsZp.pnv, null, null, null);
        boolean flag2 = EntityUtils.isInArmor(pl, ItemsZp.aqualung_helmet, null, null, null) || EntityUtils.isInArmor(pl, ItemsZp.rad_helmet, null, null, null) || EntityUtils.isInArmor(pl, ItemsZp.indcostume_helmet, null, null, null) || EntityUtils.isInArmor(pl, ItemsZp.gasmask, null, null, null);
        if (pl instanceof EntityPlayerSP) {
            int i2 = 5;
            if (!flag1 && ClientUtils.isClientInNightVisionScope()) {
                flag1 = true;
                i2 = 1;
            }
            if (flag1 || flag2) {
                this.clientMaskCd += 1;
            } else {
                this.clientMaskCd = 0;
            }
            if (this.clientMaskCd > 0) {
                int i1;
                if (flag1) {
                    i1 = 20 + i2;
                    if (this.clientMaskCd == i2) {
                        SoundUtils.playMonoSound(Main.MODID + ":pnv");
                    }
                } else {
                    i1 = 60 + i2;
                    if (this.clientMaskCd == i2) {
                        if ((EntityUtils.isInArmor(pl, ItemsZp.aqualung_helmet, ItemsZp.aqualung_chestplate, ItemsZp.aqualung_leggings, ItemsZp.aqualung_boots) || EntityUtils.isInArmor(pl, ItemsZp.indcostume_helmet, ItemsZp.indcostume_chestplate, ItemsZp.indcostume_leggings, ItemsZp.indcostume_boots)) && pl.isInsideOfMaterial(Material.water) && pl.inventory.hasItem(ItemsZp.oxygen)) {
                            SoundUtils.playMonoSound(Main.MODID + ":breath2");
                        } else {
                            SoundUtils.playMonoSound(Main.MODID + ":breath");
                        }
                    }
                }
                if (this.clientMaskCd > i1) {
                    this.clientMaskCd = i2 - 1;
                }
            }
        } else {
            if (flag1) {
                if (pl.ticksExisted % 20 == 0) {
                    SoundUtils.playClientMovingSound(pl, Main.MODID + ":pnv", 3.0f, 1.0f);
                }
            } else if (flag2) {
                if (pl.ticksExisted % 60 == 0) {
                    SoundUtils.playClientMovingSound(pl, Main.MODID + ":breath", 3.0f, 1.0f);
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private void heartSound(EntityPlayer pl) {
        if (pl instanceof EntityPlayerSP) {
            int i2 = (int) Math.max(pl.getHealth(), 8);
            if (pl.getActivePotionEffect(CommonProxy.bleeding) != null) {
                i2 = Math.min(i2, Math.max(40 - pl.getActivePotionEffect(CommonProxy.bleeding).getAmplifier() * 10, 10));
            }
            if (pl.ticksExisted % i2 == 0) {
                SoundUtils.playMonoSound(Main.MODID + ":heart");
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private void spawnParticlesBubbles(EntityLivingBase entity) {
        double y = entity.posY - entity.yOffset;
        if (entity instanceof EntityPlayerSP) {
            y += 1.62f - (entity.boundingBox.maxY - entity.boundingBox.minY);
        }
        for (int i = 0; i < 4; ++i) {
            float f = Main.rand.nextFloat() - Main.rand.nextFloat();
            float f1 = Main.rand.nextFloat() - Main.rand.nextFloat();
            float f2 = Main.rand.nextFloat() - Main.rand.nextFloat();
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityBubbleFX(entity.worldObj, entity.posX + (double) f, y + (entity.boundingBox.maxY - entity.boundingBox.minY) * 0.5f + (double) f1, entity.posZ + (double) f2, 0, 0, 0));
        }
    }

    @SideOnly(Side.CLIENT)
    private void spawnParticlesBoiling(EntityLivingBase entity) {
        if (entity.ticksExisted % 3 == 0) {
            double y = entity.posY - entity.yOffset;
            float f = Main.rand.nextFloat() - Main.rand.nextFloat();
            float f1 = Main.rand.nextFloat() - Main.rand.nextFloat();
            float f2 = Main.rand.nextFloat() - Main.rand.nextFloat();
            if (entity instanceof EntityPlayerSP) {
                y += 1.62f - (entity.boundingBox.maxY - entity.boundingBox.minY);
            }
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityExplodeFX(entity.worldObj, entity.posX + (double) f, y + (entity.boundingBox.maxY - entity.boundingBox.minY) * 0.5f + (double) f1, entity.posZ + (double) f2, entity.motionX, 0.0f, entity.motionZ));
            SoundUtils.playClientSound(entity.posX, entity.posY, entity.posZ, "random.fizz", 0.5f, Main.rand.nextFloat() * 0.1F + 0.65F);
        }
    }

    @SideOnly(Side.CLIENT)
    private void spawnParticlesAcid(EntityLivingBase entity) {
        double y = entity.posY - entity.yOffset;
        double x = entity.posX;
        double z = entity.posZ;
        if (entity.ticksExisted % 3 == 0) {
            SoundUtils.playClientSound(x, y, z, "random.fizz", 1.2F, 0.9f + Main.rand.nextFloat() * 0.3f);
        }
        if (entity instanceof EntityPlayerSP) {
            y += 1.62f - (entity.boundingBox.maxY - entity.boundingBox.minY);
        }
        for (int i = 0; i < 2; i++) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleColoredCloud(entity.worldObj, x, y + (entity.boundingBox.maxY - entity.boundingBox.minY) * 0.5f, z, Main.rand.nextGaussian() * 0.05D, Main.rand.nextDouble() * 0.05D + 0.03f, Main.rand.nextGaussian() * 0.05D, new float[]{0.85f, 1.0f, 0.85f}, 1.2f - Main.rand.nextFloat() * 0.4f));
        }
    }

    @SideOnly(Side.CLIENT)
    private void spawnParticlesHoly(EntityLivingBase entity) {
        double y = entity.posY - entity.yOffset;
        double x = entity.posX;
        double z = entity.posZ;
        if (entity.ticksExisted % 3 == 0) {
            SoundUtils.playClientSound(x, y, z, "random.fizz", 1.2F, 1.2f + Main.rand.nextFloat() * 0.4f);
        }
        if (entity instanceof EntityPlayerSP) {
            y += 1.62f - (entity.boundingBox.maxY - entity.boundingBox.minY);
        }
        for (int i = 0; i < 2; i++) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleColoredCloud(entity.worldObj, x, y + (entity.boundingBox.maxY - entity.boundingBox.minY) * 0.5f, z, Main.rand.nextGaussian() * 0.05D, Main.rand.nextDouble() * 0.05D + 0.03f, Main.rand.nextGaussian() * 0.05D, new float[]{0.8f, 1.0f, 1.0f}, 1.2f - Main.rand.nextFloat() * 0.4f));
        }
    }

    @SubscribeEvent
    public void onDrop(LivingDropsEvent ev) {
        Entity entity = ev.entity;
        if (entity instanceof EntitySkeleton) {
            if (Main.rand.nextFloat() <= 0.2f) {
                entity.dropItem(Items.bone, 1);
            } else if (Main.rand.nextFloat() <= 0.15f) {
                entity.dropItem(Items.arrow, 1);
            }
            ev.setCanceled(true);
        }
        if (entity instanceof EntitySpider) {
            if (Main.rand.nextFloat() <= 0.2f) {
                entity.dropItem(Items.spider_eye, 1);
            }
            ev.setCanceled(true);
        }
        if (entity instanceof EntitySquid) {
            if (Main.rand.nextFloat() <= 0.15f) {
                entity.entityDropItem(new ItemStack(Items.dye, 1, 0), 0.0f);
            }
            ev.setCanceled(true);
        }
        if (entity instanceof EntityPigZombie) {
            if (Main.rand.nextFloat() <= 0.1f) {
                entity.dropItem(Items.gold_nugget, 1);
            }
            ev.setCanceled(true);
        } else if (entity instanceof EntityBlaze) {
            if (Main.rand.nextFloat() <= 0.05f) {
                entity.dropItem(Items.blaze_powder, 1);
            }
            ev.setCanceled(true);
        } else if (entity instanceof EntityEnderman) {
            if (Main.rand.nextFloat() <= 0.05f) {
                entity.dropItem(Items.ender_pearl, 1);
            }
            ev.setCanceled(true);
        } else if (entity instanceof EntityAnimal) {
            EntityAnimal entityAnimal = (EntityAnimal) entity;
            if (!entityAnimal.isChild()) {
                float chance = ev.source.getEntity() instanceof AZombieBase ? 0.3f : 0.0f;
                float f1 = 0.0f;
                if (ev.source.getEntity() instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) ev.source.getEntity();
                    PlayerMiscData playerMiscData = PlayerMiscData.getPlayerData(player);
                    if (player.getHeldItem() != null) {
                        Item item = player.getHeldItem().getItem();
                        if (item instanceof ItemSword || item instanceof ItemAxe) {
                            chance = ((item == ItemsZp.cleaver || item instanceof ItemSpear) ? 0.5f : 0.3f) * (ev.lootingLevel + 1);
                        }
                        if (item instanceof ItemBow || item instanceof ItemCrossbow) {
                            chance = 0.5f;
                        }
                    }
                    f1 = SkillManager.instance.getSkillBonus(SkillManager.instance.hunter, player, 0.05f);
                }
                if (DamageSourceZp.isBulletDamage(ev.source) || DamageSourceZp.isBulletHeadShotDamage(ev.source)) {
                    chance += 0.25f;
                }
                chance += f1;
                if (entity instanceof EntitySheep) {
                    if (Main.rand.nextFloat() <= chance) {
                        entity.dropItem((entity.isBurning() || this.isInBoilingWater((EntityLivingBase) entity)) ? ItemsZp.cooked_mutton : ItemsZp.mutton, 1);
                    }
                } else if (entity instanceof EntityPig) {
                    if (Main.rand.nextFloat() <= chance) {
                        entity.dropItem((entity.isBurning() || this.isInBoilingWater((EntityLivingBase) entity)) ? Items.cooked_porkchop : Items.porkchop, 1);
                    }
                } else if (entity instanceof EntityChicken) {
                    if (Main.rand.nextFloat() <= chance) {
                        entity.dropItem((entity.isBurning() || this.isInBoilingWater((EntityLivingBase) entity)) ? Items.cooked_chicken : Items.chicken, 1);
                    }
                    if (Main.rand.nextFloat() <= 0.2f) {
                        entity.dropItem(Items.feather, 1);
                    }
                } else if (entity instanceof EntityCow) {
                    if (Main.rand.nextFloat() <= chance) {
                        entity.dropItem((entity.isBurning() || this.isInBoilingWater((EntityLivingBase) entity)) ? Items.cooked_beef : Items.beef, 1);
                    }
                    if (Main.rand.nextFloat() <= 0.2f) {
                        entity.dropItem(Items.leather, 1);
                    }
                }
            }
            ev.setCanceled(true);
        }
    }

    @SubscribeEvent()
    public void onSpawn(EntityJoinWorldEvent ev) {
        if (ev.entity instanceof EntityModZombie) {
            if (ev.entity != ClientHandler.instance.horror) {
                ev.setCanceled(true);
            }
        }
        if (!ev.world.isRemote) {
            if (ev.entity instanceof EntityZombie) {
                if (!(ev.entity instanceof EntityPigZombie)) {
                    ev.setCanceled(true);
                    EntityZombieCitizen citizen = new EntityZombieCitizen(ev.entity.worldObj);
                    citizen.copyLocationAndAnglesFrom(ev.entity);
                    ev.world.spawnEntityInWorld(citizen);
                }
            }
            if (ev.entity instanceof EntityChicken) {
                EntityChicken chicken = (EntityChicken) ev.entity;
                if (!chicken.isChild()) {
                    chicken.func_152117_i(true);
                }
            }
            if (ev.entity instanceof EntityVillager) {
                EntityVillager villager = (EntityVillager) ev.entity;
                villager.tasks.addTask(1, new EntityAIAvoidEntity(villager, AZombieBase.class, 8.0F, 0.6D, 0.6D));
            }
            if (ev.entity instanceof EntityDragon) {
                EntityDragon drago = (EntityDragon) ev.entity;
                if (drago.getMaxHealth() != 1000) {
                    drago.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(1000.0D);
                    drago.setHealth(1000.0f);
                }
            }
            if (ev.entity instanceof EntityWither) {
                EntityWither drago = (EntityWither) ev.entity;
                if (drago.getMaxHealth() != 500) {
                    drago.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(500.0D);
                    drago.setHealth(500.0f);
                }
            }
        }
        if (ev.entity instanceof EntityPlayer) {
            EntityPlayer pl = (EntityPlayer) ev.entity;
            if (!(pl.inventoryContainer instanceof ContainerPlayerNew)) {
                pl.inventoryContainer = new ContainerPlayerNew(pl.inventory, !pl.worldObj.isRemote, pl);
                pl.openContainer = pl.inventoryContainer;
            }
            if (pl.isEntityAlive()) {
                if (pl.worldObj.isRemote) {
                    if (pl instanceof EntityPlayerSP) {
                        GameHud.instance.healthCurr = 0;
                        GameHud.instance.hungerCurr = 0;
                        GameHud.instance.thirstCurr = 0;
                        GameHud.instance.overlayHealthCurr = 100;
                        GameHud.instance.overlayHungerCurr = 100;
                        GameHud.instance.overlayThirstCurr = 100;
                        GameHud.instance.healthCd = 0;
                        GameHud.instance.hungerCd = 0;
                        GameHud.instance.thirstCd = 0;
                        PainUpdater.instance.reset();
                        Main.settingsZp.sendSettingsToServer();
                        pl.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100);
                        pl.setHealth(100);
                    }
                } else {
                    this.sendStatsData(pl);
                    if (pl instanceof EntityPlayerMP) {
                        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) pl;
                        int dayZ = -1;
                        if (entityPlayerMP.dimension == 0 || entityPlayerMP.dimension == 2) {
                            if (MinecraftServer.getServer().worldServers.length >= 3) {
                                WorldManager.WorldSaveDay saveDay = WorldManager.WorldSaveDay.getStorage(MinecraftServer.getServer().worldServers[entityPlayerMP.dimension]);
                                if (saveDay != null) {
                                    dayZ = saveDay.day;
                                }
                            }
                        }
                        NetworkHandler.NETWORK.sendTo(new PacketDay(WorldManager.is7NightEnabled(), dayZ), entityPlayerMP);
                        entityPlayerMP.playerNetServerHandler.sendPacket(new S1FPacketSetExperience(entityPlayerMP.experience, entityPlayerMP.experienceTotal, entityPlayerMP.experienceLevel));
                        entityPlayerMP.playerNetServerHandler.sendPacket(new S03PacketTimeUpdate(entityPlayerMP.worldObj.getTotalWorldTime(), entityPlayerMP.worldObj.getWorldTime(), entityPlayerMP.worldObj.getGameRules().getGameRuleBooleanValue("doDaylightCycle")));
                        PlayerManager.instance.sendAchievementData(pl);
                        if (!DimensionManager.getProvider(entityPlayerMP.dimension).hasNoSky) {
                            WeatherHandler.instance.getWeatherFog().sendFogPacketCheck(entityPlayerMP, (WeatherFogManager) WeatherHandler.instance.getWorldFogInfo(entityPlayerMP.dimension));
                            WeatherHandler.instance.getWeatherRain().sendRainPacketCheck(entityPlayerMP, (WeatherRainManager) WeatherHandler.instance.getWorldRainInfo(entityPlayerMP.dimension));
                        }
                    }
                    if (pl.getMaxHealth() != 100) {
                        if (!pl.worldObj.provider.isDaytime() && pl.getBedLocation(0) == null) {
                            PotionEffect potionEffect = new PotionEffect(25, 1200);
                            pl.addPotionEffect(potionEffect);
                        }
                        pl.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(100);
                        pl.setHealth(100);
                    }
                }
            }
        }
    }

    private void sendStatsData(EntityPlayer player) {
        if (player instanceof EntityPlayerMP) {
            Thirst.getThirst(player).packet();
            Hunger.getHunger(player).packet();
            PlayerMiscData.getPlayerData(player).packet();
        }
    }

    @SubscribeEvent
    public void onCart(MinecartCollisionEvent ev) {
        if (!ev.entity.worldObj.isRemote && ev.collider instanceof EntityMinecart) {
            if (ev.entity.boundingBox.contract(0.5f, 0.5f, 0.5f).intersectsWith(ev.collider.boundingBox)) {
                ev.entity.worldObj.playSoundAtEntity(ev.entity, "Utils.random.break", 0.6F, 1.2F);
                ev.entity.entityDropItem(ev.minecart.getCartItem(), 0.1f);
                ev.entity.setDead();
            }
        }
    }

    @SubscribeEvent
    public void onDeath(LivingDeathEvent ev) {
        if (!ev.entityLiving.worldObj.isRemote) {
            if (ev.entityLiving.worldObj.getGameRules().getGameRuleBooleanValue("showDeathMessages")) {
                DamageSource src = ev.source;
                Entity enSrc = src.getEntity();
                if (ev.entityLiving instanceof EntityPlayer) {
                    EntityPlayer pl = (EntityPlayer) ev.entityLiving;
                    FMLLog.info(pl.getDisplayName() + " died at: " + pl.posX + " " + pl.posY + " " + pl.posZ);
                    if (GoodPeople.coolMans.contains(pl.getCommandSenderName())) {
                        pl.dropItemWithOffset(Item.getItemFromBlock(Blocks.yellow_flower), 1, 1.0f);
                    }
                    if (GoodPeople.dungeonMaster.equals(pl.getCommandSenderName())) {
                        pl.dropItemWithOffset(Items.diamond, 1, 1.0f);
                    }
                    AchievementManager.instance.triggerAchievement(AchievementManager.instance.firstBlood, pl);
                    PlayerMiscData.getPlayerData(pl).addDeath();
                    String text;
                    String s;
                    boolean flag = src == DamageSourceZp.virus;
                    if (flag) {
                        AchievementManager.instance.triggerAchievement(AchievementManager.instance.secretZombie, pl);
                    }
                    if (src == DamageSourceZp.acid) {
                        text = "acid";
                    } else if (src == DamageSourceZp.drown) {
                        text = "drown";
                    } else if (src == DamageSourceZp.blood) {
                        text = "blood";
                        if (PlayerMiscData.getPlayerData(pl).getBloodInitializedId() >= 0) {
                            EntityPlayer player = (EntityPlayer) ev.entityLiving.worldObj.getEntityByID(PlayerMiscData.getPlayerData(pl).getBloodInitializedId());
                            if (player != null) {
                                enSrc = player;
                            }
                        }
                    } else if (src == DamageSource.cactus) {
                        text = "cactus";
                    } else if (src == DamageSource.fall) {
                        text = "fall";
                    } else if (src == DamageSource.fallingBlock) {
                        text = "fallingBlock";
                    } else if (src == DamageSourceZp.wire || src == DamageSourceZp.stakes) {
                        text = "blood";
                    } else if (src == DamageSource.starve) {
                        text = "starve";
                    } else if (src == DamageSource.inWall) {
                        text = "inWall";
                    } else if (src == DamageSourceZp.boil) {
                        text = "boil";
                    } else if (src.getDamageType().equals("explosionPlayer")) {
                        text = "explosionPlayer";
                    } else if (src == DamageSourceZp.explosionNew) {
                        text = "explosion";
                    } else if (DamageSourceZp.isBulletDamage(src) || src.getSourceOfDamage() instanceof EntityArrow) {
                        text = "shot";
                    } else if (DamageSourceZp.isBulletHeadShotDamage(src)) {
                        text = "shot_head";
                    } else if (src.isFireDamage()) {
                        text = "burned";
                    } else if (enSrc instanceof AZombieBase) {
                        if (src.isProjectile()) {
                            text = "zombie_proj";
                        } else {
                            ((AZombieBase) enSrc).heal(12.0f);
                            text = "zombie";
                        }
                    } else if (enSrc instanceof EntityPlayer) {
                        text = "player";
                    } else if (enSrc instanceof EntityMob) {
                        text = "mob";
                    } else if (flag || pl.getEntityData().getInteger("radiation") >= 100) {
                        text = "zombie";
                    } else {
                        text = "generic";
                    }
                    if (enSrc == pl) {
                        text = "suicide";
                    } else {
                        if (enSrc instanceof EntityPlayer) {
                            PlayerMiscData.getPlayerData((EntityPlayer) enSrc).addPlayer();
                        }
                    }
                    s = "player.died." + text;
                    ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation(s, pl.getDisplayName());
                    chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
                    MinecraftServer.getServer().getConfigurationManager().sendPacketToAllPlayers(new S02PacketChat(chatComponentTranslation));
                    PlayerMiscData.getPlayerData(pl).clearBloodInitializedId();
                }
                if (enSrc instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) enSrc;
                    PlayerMiscData playerMiscData = PlayerMiscData.getPlayerData(player);
                    float f1 = 0.0f;
                    float f2 = 0.0f;
                    float f3 = 0.0f;
                    boolean b1 = player.getHeldItem() != null && player.getHeldItem().getItem() instanceof AGunBase;
                    if (b1) {
                        f1 = ev.entityLiving.getMaxHealth() * 5.0e-4f;
                        f2 = 5.0e-3f;
                        f3 = Main.rand.nextFloat() * 0.03f;
                    }
                    if (ev.entityLiving instanceof AZombieBase) {
                        if (ev.entityLiving instanceof EntityZombieStrong) {
                            AchievementManager.instance.triggerAchievement(AchievementManager.instance.strongZm, player);
                        }
                        if (player.getHeldItem() != null && player.getHeldItem().getItem() == Items.potato) {
                            AchievementManager.instance.triggerAchievement(AchievementManager.instance.pvz, player);
                        }
                        playerMiscData.addZombie(ev.entityLiving);
                        AchievementManager.instance.triggerAchievement(AchievementManager.instance.firstZombie, player);
                        if (b1) {
                            f1 += Main.rand.nextFloat() * 1.0e-2f;
                        }
                        f2 = 0.005f;
                        f3 = Main.rand.nextFloat() * 0.02f + ev.entityLiving.getMaxHealth() * 5.0e-4f;
                    }
                    if (ev.entityLiving instanceof EntityAnimal) {
                        if (b1) {
                            f1 += Main.rand.nextFloat() * 5.0e-3f;
                        }
                        f2 = Main.rand.nextFloat() * 0.02f + ev.entityLiving.getMaxHealth() * 1.0e-3f;
                        f3 = 0.01f;
                    }
                    if (!(ev.entityLiving instanceof EntityPlayer)) {
                        playerMiscData.getSkillProgressProfiler().addProgress(SkillManager.instance.gunSmith, player, f1);
                        playerMiscData.getSkillProgressProfiler().addProgress(SkillManager.instance.hunter, player, f2);
                        playerMiscData.getSkillProgressProfiler().addProgress(SkillManager.instance.survivor, player, f3);
                    }
                }
                if ((enSrc instanceof AZombieBase && !src.isProjectile()) || src == DamageSourceZp.virus || ev.entityLiving.getEntityData().getInteger("radiation") >= 100) {
                    EntityZombieCitizen citizen = new EntityZombieCitizen(ev.entityLiving.worldObj);
                    EntityZombieWolf wolf = new EntityZombieWolf(ev.entityLiving.worldObj);
                    if (src == DamageSourceZp.virus) {
                        citizen.setVillager(true);
                    }
                    AZombieBase monster = (ev.entityLiving instanceof EntityPlayer || ev.entityLiving instanceof EntityVillager) ? citizen : ev.entityLiving instanceof EntityWolf ? wolf : null;
                    if (monster != null) {
                        monster.copyLocationAndAnglesFrom(ev.entityLiving);
                        monster.onSpawnWithEgg(null);
                        ev.entityLiving.worldObj.spawnEntityInWorld(monster);
                    }
                }
            }
        }
    }

    private float applyArmorCalculations(DamageSource p_70655_1_, float p_70655_2_, EntityPlayer player) {
        if (!p_70655_1_.isUnblockable()) {
            int i = 25 - player.getTotalArmorValue();
            float f1 = p_70655_2_ * (float) i;
            if (p_70655_1_.getEntity() == null || PluginUtils.canDamage(p_70655_1_.getEntity(), player)) {
                player.inventory.damageArmor(4);
            }
            p_70655_2_ = f1 / 25;
        }
        return p_70655_2_;
    }

    @SubscribeEvent
    public void onHurt(LivingAttackEvent ev) {
        if (ev.entityLiving != null) {
            if (!ev.entityLiving.worldObj.isRemote) {
                if (ev.source.getEntity() instanceof EntityPlayer && ev.source.getEntity() != ev.entityLiving) {
                    PlayerMiscData.getPlayerData((EntityPlayer) ev.source.getEntity()).addPlayerLoudness(1);
                }
                if (ev.entityLiving instanceof EntityPlayer) {
                    PlayerMiscData.getPlayerData((EntityPlayer) ev.entityLiving).addPlayerLoudness(1);
                }
            }
            if (!ev.source.isProjectile()) {
                if (EntityUtils.isInArmor(ev.entityLiving, null, ItemsZp.dynamike, null, null)) {
                    if (ev.source.isFireDamage() || DamageSourceZp.isExplosionDamage(ev.source) || DamageSourceZp.isBulletDamage(ev.source)) {
                        ev.entityLiving.setCurrentItemOrArmor(3, null);
                        ExplosionUtils.makeExplosion(ev.entityLiving.worldObj, ev.entityLiving instanceof EntityPlayer ? (EntityPlayer) ev.entityLiving : null, ev.entityLiving, ev.entityLiving.posX, ev.entityLiving.posY + 1.0f, ev.entityLiving.posZ, 6.0f, true, true, false);
                        if (ev.entityLiving.isEntityAlive()) {
                            ev.entityLiving.attackEntityFrom(DamageSourceZp.explosionNew, 100);
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onHurt(LivingHurtEvent ev) {
        if (ev.entityLiving != null) {
            if (!DamageSourceZp.isBulletDamage(ev.source) && !DamageSourceZp.isBulletHeadShotDamage(ev.source)) {
                if (ev.entityLiving instanceof AZombieBase) {
                    if (ev.source.getSourceOfDamage() instanceof EntityArrow) {
                        EntityArrow entityArrow = (EntityArrow) ev.source.getSourceOfDamage();
                        if (entityArrow.getEntityData().hasKey("stronger")) {
                            ev.ammount *= 1.5f;
                        }
                    }
                    if (ev.source.getEntity() instanceof EntityPlayer) {
                        EntityPlayer entityPlayer = (EntityPlayer) ev.source.getEntity();
                        float f1 = 1.0f + SkillManager.instance.getSkillBonus(SkillManager.instance.survivor, entityPlayer, 0.02f);
                        ItemStack stack = entityPlayer.getHeldItem();
                        if (stack != null) {
                            if (PlayerManager.meleeSpeedBonus.containsKey(stack.getItem())) {
                                ev.entityLiving.hurtResistantTime = PlayerManager.meleeSpeedBonus.get(stack.getItem());
                            }
                            if (stack.getItem() == ItemsZp.ripper) {
                                f1 += 0.25f;
                            }
                        }
                        ev.ammount *= f1;
                    }
                }
            }

            if (ev.entityLiving instanceof EntityWither) {
                if (ev.source.isExplosion() || ev.source.isFireDamage()) {
                    ev.setCanceled(true);
                }
            }

            if (ev.ammount > 0) {
                if (ev.source.isProjectile() || ev.source.getEntity() == null || PluginUtils.canDamage(ev.source.getEntity(), ev.entityLiving)) {
                    if (ev.source != DamageSource.drown && ev.source != DamageSource.starve) {
                        NetworkHandler.NETWORK.sendToAllAround(new ParticleBlood2(ev.entityLiving.getEntityId()), new NetworkRegistry.TargetPoint(ev.entityLiving.dimension, ev.entityLiving.posX, ev.entityLiving.posY, ev.entityLiving.posZ, 256));
                    }
                }
            }

            if (ev.entityLiving instanceof EntityPlayer) {
                EntityPlayer pl = (EntityPlayer) ev.entityLiving;

                if (ev.source.getSourceOfDamage() != null) {
                    if (pl.getHeldItem() != null) {
                        if (pl.isUsingItem()) {
                            if (pl.getHeldItem().getItem() instanceof ItemFood) {
                                pl.stopUsingItem();
                                pl.getEntityData().setInteger("itemUsed", 20);
                            }
                        }
                    }
                }

                if (ev.source.isFireDamage()) {
                    ev.ammount *= 2.0f;
                } else if (ev.source.isMagicDamage()) {
                    if (ev.ammount <= 1.0f) {
                        ev.ammount = pl.getHealth() <= 2.0f ? 1.0f : 2.0f;
                    } else {
                        ev.ammount *= 3.0f;
                    }
                } else if (!(DamageSourceZp.isBulletDamage(ev.source) || DamageSourceZp.isBulletHeadShotDamage(ev.source)) && !DamageSourceZp.isExplosionDamage(ev.source) && ev.source != DamageSourceZp.blood && ev.source != DamageSource.cactus) {
                    ev.ammount = this.applyArmorCalculations(ev.source, ev.ammount * (4.25f + Main.rand.nextFloat() * 0.5f), pl);
                }

                if (ev.source.getEntity() == null || PluginUtils.canDamage(ev.source.getEntity(), pl)) {
                    NetworkHandler.NETWORK.sendTo(new PacketPain(ev.ammount * 0.01f, ev.source.getSourceOfDamage() != null), (EntityPlayerMP) pl);
                }

                if (!PluginUtils.isInPrivate(pl) && !pl.isPotionActive(30)) {
                    if (!pl.isPotionActive(27) && pl.isPotionActive(32)) {
                        if (ev.ammount >= 36 || ev.source == DamageSource.fall) {
                            pl.addPotionEffect(new PotionEffect(27, (int) (6000 + pl.fallDistance * 600), 1));
                            pl.removePotionEffect(32);
                            pl.worldObj.playSoundAtEntity(pl, Main.MODID + ":bone", 3.0F, 0.8F);
                        }
                    }
                    if (ev.source == DamageSource.fall) {
                        boolean flag = false;
                        if (pl.getCurrentArmor(0) != null) {
                            pl.getCurrentArmor(0).damageItem((int) Math.max(pl.fallDistance / 4.0f, 1), pl);
                        }
                        if (pl.fallDistance <= 12 && pl.fallDistance >= 6) {
                            if (Main.rand.nextFloat() <= pl.fallDistance * 0.1f - 0.3f) {
                                pl.addPotionEffect(new PotionEffect(27, (int) (7200 + pl.fallDistance * 500)));
                                flag = true;
                                pl.worldObj.playSoundAtEntity(pl, Main.MODID + ":bone", 2.0F, 0.8F);
                            }
                        } else if (ev.entity.fallDistance > 12 && ev.entity.fallDistance <= 18) {
                            pl.addPotionEffect(new PotionEffect(9, 4800));
                            pl.addPotionEffect(new PotionEffect(27, (int) (24000 + pl.fallDistance * 300), 1));
                            flag = true;
                            pl.worldObj.playSoundAtEntity(pl, Main.MODID + ":bone", 2.0F, 0.8F);
                        } else if (ev.entity.fallDistance > 18 && ev.entity.fallDistance <= 24) {
                            if (Main.rand.nextFloat() <= 0.6f) {
                                pl.addPotionEffect(new PotionEffect(28, 2400));
                                pl.worldObj.playSoundAtEntity(pl, Main.MODID + ":bone", 2.0F, 0.8F);
                            }
                            pl.addPotionEffect(new PotionEffect(9, 7200));
                            pl.addPotionEffect(new PotionEffect(27, (int) (36000 + pl.fallDistance * 300), 1));
                            flag = true;
                            pl.worldObj.playSoundAtEntity(pl, Main.MODID + ":bone", 2.0F, 0.8F);
                        } else if (ev.entity.fallDistance > 24) {
                            pl.addPotionEffect(new PotionEffect(28, Main.rand.nextInt(601) + 2400));
                            pl.addPotionEffect(new PotionEffect(15, 1200));
                            pl.addPotionEffect(new PotionEffect(9, 12000));
                            pl.addPotionEffect(new PotionEffect(27, (int) (48000 + pl.fallDistance * 300), 1));
                            flag = true;
                            pl.worldObj.playSoundAtEntity(pl, Main.MODID + ":bone", 2.0F, 0.8F);
                        }
                        if (flag) {
                            AchievementManager.instance.triggerAchievement(AchievementManager.instance.leg, pl);
                        }
                    }
                }
                if (DamageSourceZp.isBulletHeadShotDamage(ev.source)) {
                    if (ev.ammount >= 50) {
                        pl.addPotionEffect(new PotionEffect(9, 1200, ev.ammount >= 75 ? 1 : 0));
                    }
                }
                if (ev.source.getEntity() == null || PluginUtils.canDamage(ev.source.getEntity(), ev.entityLiving)) {
                    double bleedingChance = Math.max(0.01f * ev.ammount - (0.01f * pl.getTotalArmorValue()), 0.01f);
                    if (pl.isPotionActive(28)) {
                        bleedingChance += (pl.getActivePotionEffect(CommonProxy.bleeding).getAmplifier() + 1) * 0.1f;
                    }
                    bleedingChance *= ConfigZp.bleedingChanceMultiplier;

                    if (ev.source != DamageSource.fall && ev.source != DamageSource.generic && !ev.source.isMagicDamage() && ev.source != DamageSource.starve && ev.source != DamageSource.drown && ev.ammount >= 5.0f && (Main.rand.nextFloat() <= bleedingChance || bleedingChance >= 0.5f)) {
                        if (ev.source.getEntity() instanceof EntityPlayer) {
                            EntityPlayer entityPlayer = (EntityPlayer) ev.source.getEntity();
                            if (DamageSourceZp.isBulletHeadShotDamage(ev.source) || (entityPlayer.getHeldItem() != null && entityPlayer.getHeldItem().getItem() instanceof ItemBloodsucker)) {
                                bleedingChance += 0.2f;
                            }
                        }
                        if (!pl.isPotionActive(30)) {
                            if (bleedingChance >= 0.5f && ((ev.ammount > 30 && ev.source.isExplosion()) || Main.rand.nextFloat() <= 0.5f + ev.ammount * 0.005f)) {
                                pl.addPotionEffect(new PotionEffect(27, (int) (5000 + ev.ammount * 10)));
                            }
                        }
                        if (!pl.isPotionActive(28)) {
                            pl.addPotionEffect(new PotionEffect(28, (int) (ev.ammount * (20 + Main.rand.nextInt(16)) + 100), ev.ammount >= 75 ? 2 : ev.ammount >= 50 ? 1 : 0));
                        } else if (pl.getActivePotionEffect(CommonProxy.bleeding).getAmplifier() < 3) {
                            pl.addPotionEffect(new PotionEffect(28, (int) (ev.ammount * (20 + Main.rand.nextInt(16)) + 100) + (pl.getActivePotionEffect(CommonProxy.bleeding).getDuration() / (pl.getActivePotionEffect(CommonProxy.bleeding).getAmplifier() + 1)), pl.getActivePotionEffect(CommonProxy.bleeding).getAmplifier() + 1));
                        }
                        if (ev.source.getEntity() instanceof EntityPlayer) {
                            PlayerMiscData.getPlayerData(pl).setBloodInitializedId(ev.source.getEntity().getEntityId());
                        }
                    }
                }
            } else if (ev.entityLiving instanceof EntityHorse) {
                EntityHorse horse = (EntityHorse) ev.entityLiving;
                if (horse.riddenByEntity instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) horse.riddenByEntity;
                    player.mountEntity(null);
                }
            }

            if (ev.source.getEntity() != null) {
                double d1 = ev.source.getEntity().posX - ev.entityLiving.posX;
                double d0;
                for (d0 = ev.source.getEntity().posZ - ev.entityLiving.posZ; d1 * d1 + d0 * d0 < 1.0E-4D; d0 = (Math.random() - Math.random()) * 0.01D) {
                    d1 = (Math.random() - Math.random()) * 0.01D;
                }
                this.knockBack(ev.source.getEntity(), ev.entityLiving, ev.ammount, d1, d0, ev.source);
            }
        }
    }

    private void knockBack(Entity attacker, EntityLivingBase en, float p_70653_2_, double p_70653_3_, double p_70653_5_, DamageSource source) {
        if (DamageSourceZp.isGlassBottleDamage(source) || DamageSourceZp.isBulletDamage(source) || DamageSourceZp.isBulletHeadShotDamage(source)) {
            float f1 = MathHelper.sqrt_double(p_70653_3_ * p_70653_3_ + p_70653_5_ * p_70653_5_) / (p_70653_2_ * 0.5f);
            float f2 = (float) (0.035F * (1.0f - en.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue()));
            en.motionX /= 2.0D;
            en.motionY /= 2.0D;
            en.motionZ /= 2.0D;
            en.addVelocity(-(p_70653_3_ / (double) f1 * (double) f2), f2, -(p_70653_5_ / (double) f1 * (double) f2));
            if (en.motionY > 0.045) {
                en.motionY = 0.045;
            }
        } else {
            float f0 = 1.0f;
            if (attacker instanceof EntityPlayer) {
                EntityPlayer entityPlayer = (EntityPlayer) attacker;
                ItemStack itemStack = entityPlayer.getHeldItem();
                if (itemStack != null && PlayerManager.meleeSpeedBonus.containsKey(itemStack.getItem())) {
                    f0 = PlayerManager.meleeSpeedBonus.get(itemStack.getItem()) / 20.0f;
                }
            }
            float f1 = MathHelper.sqrt_double(p_70653_3_ * p_70653_3_ + p_70653_5_ * p_70653_5_) / f0;
            float f2 = (float) ((0.35F * f0) * (1.0f - en.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).getAttributeValue()));
            en.motionX /= 2.0D;
            en.motionY /= 2.0D;
            en.motionZ /= 2.0D;
            en.addVelocity(-(p_70653_3_ / (double) f1 * (double) f2), f2, -(p_70653_5_ / (double) f1 * (double) f2));
            if (en.motionY > 0.45) {
                en.motionY = 0.45;
            }
        }
    }
}
