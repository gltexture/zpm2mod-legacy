package ru.BouH_.items.gun.tracer;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTNT;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.entity.particle.*;
import ru.BouH_.entity.zombie.AZombieBase;
import ru.BouH_.misc.DamageSourceZp;
import ru.BouH_.moving.MovingUtils;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.packets.gun.PacketHitscanBlockC;
import ru.BouH_.network.packets.gun.PacketHitscanEntC;
import ru.BouH_.utils.EntityUtils;
import ru.BouH_.utils.PluginUtils;
import ru.BouH_.utils.SoundUtils;

public class RegisterHit {
    public static void registerHit(EntityPlayer player, int maxDistance, int effectiveDistance, int blockX, int blockY, int blockZ, double x, double y, double z, int side, Entity ent, int damage) {
        World world = player.worldObj;
        Block inTile = world.getBlock(blockX, blockY, blockZ);
        if (!world.isRemote) {
            if (ent == null) {
                NetworkHandler.NETWORK.sendToAllAround(new PacketHitscanBlockC(player.getEntityId(), blockX, blockY, blockZ, side, x, y, z, player.worldObj.getGameRules().getGameRuleBooleanValue("drawTracersForAll")), new NetworkRegistry.TargetPoint(player.dimension, blockX, blockY, blockZ, 400));
                if (!PluginUtils.isInPrivate2(world, blockX, blockY, blockZ)) {
                    if (inTile == Blocks.tnt) {
                        BlockTNT tnt = (BlockTNT) inTile;
                        world.setBlockToAir(blockX, blockY, blockZ);
                        tnt.func_150114_a(world, blockX, blockY, blockZ, 1, player);
                    }
                }
                if (side >= 0 && !inTile.getMaterial().isLiquid() && inTile.getMaterial() != Material.air) {
                    if (inTile.getMaterial() == Material.anvil || inTile.getMaterial() == Material.iron) {
                        world.playSoundEffect(blockX, blockY, blockZ, Main.MODID + ":metal1", 1.0F, 1.2F / (Main.rand.nextFloat() * 0.2F + 0.9F));
                    } else if (inTile.getMaterial() == Material.rock) {
                        world.playSoundEffect(blockX, blockY, blockZ, Main.MODID + ":concrete1", 1.0F, 1.2F / (Main.rand.nextFloat() * 0.2F + 0.9F));
                    } else if (inTile.getMaterial() == Material.wood) {
                        world.playSoundEffect(blockX, blockY, blockZ, Main.MODID + ":wood1", 1.0F, 1.2F / (Main.rand.nextFloat() * 0.2F + 0.9F));
                    } else if (inTile.getMaterial() == Material.grass || inTile.getMaterial() == Material.ground || inTile.getMaterial() == Material.sand || inTile.getMaterial() == Material.sponge) {
                        world.playSoundEffect(blockX, blockY, blockZ, Main.MODID + ":dirt1", 1.0F, 1.2F / (Main.rand.nextFloat() * 0.2F + 0.9F));
                    } else {
                        world.playSoundEffect(blockX, blockY, blockZ, Main.MODID + ":impact1", 1.0F, 1.2F / (Main.rand.nextFloat() * 0.2F + 0.9F));
                    }
                }
            } else if (ent.isEntityAlive()) {
                if (ent instanceof EntityHanging) {
                    if (!PluginUtils.isInPrivate(ent)) {
                        ((EntityHanging) ent).onBroken(null);
                        ent.setDead();
                    }
                }
                if (ent instanceof EntityBoat || ent instanceof EntityMinecart) {
                    if (!PluginUtils.isInPrivate(ent)) {
                        ent.attackEntityFrom(DamageSource.generic, Main.rand.nextInt(3) + 1);
                    }
                }
                if (ent instanceof EntityLivingBase) {
                    double distance = player.getDistanceToEntity(ent);
                    float distanceMultiplier = MathHelper.clamp_float((float) ((maxDistance - distance) / (float) (maxDistance - effectiveDistance)), 0.1f, 1.0f);
                    float mobMultiplier = ent instanceof EntityPlayer ? 1.0f : 0.5f;
                    RegisterHit.damageEntity(player, (EntityLivingBase) ent, (float) Math.max(EntityUtils.armorCalculations(damage * mobMultiplier * distanceMultiplier, ent) - calcEnchant(player), 1.0f), x, y, z);
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public static void spawnBulletHitParticles(World world, EntityPlayer player, int blockX, int blockY, int blockZ, double x, double y, double z, int side) {
        Block inTile = world.getBlock(blockX, blockY, blockZ);
        int inData = world.getBlockMetadata(blockX, blockY, blockZ);
        if (side >= 0 && !inTile.getMaterial().isLiquid() && inTile.getMaterial() != Material.air) {
            int flashCount = inTile.getMaterial() == Material.rock ? 3 + Main.rand.nextInt(4) : (inTile.getMaterial() == Material.anvil || inTile.getMaterial() == Material.iron) ? 16 + Main.rand.nextInt(4) : 0;
            if (flashCount > 0) {
                for (int i = 0; i < flashCount; i++) {
                    Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleSpark(world, x, y, z, -player.getLookVec().xCoord * 0.25f, -player.getLookVec().yCoord * 0.25f, -player.getLookVec().zCoord * 0.25f));
                }
            }
            for (int i = 0; i < 3; i++) {
                Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleBulletSmoke(world, x, y, z, -player.getLookVec().xCoord * 0.02f, -player.getLookVec().yCoord * 0.02f, -player.getLookVec().zCoord * 0.02f));
            }
            for (int i = 0; i < 12; i++) {
                Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleBulletCrack(world, x, y, z, -player.getLookVec().xCoord * 0.05f, -player.getLookVec().yCoord * 0.1f, -player.getLookVec().zCoord * 0.05f, inTile, inData));
            }
            if (!Tracer.ToBrake.contains(inTile) && !Tracer.ToBrake.contains(inTile.getMaterial())) {
                if (inTile.renderAsNormalBlock()) {
                    Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleHole(world, x, y, z, blockX, blockY, blockZ, side));
                }
            }
        }
    }

    public static void damageEntity(EntityPlayer player, EntityLivingBase ent, float damage, double x, double y, double z) {
        if (ent instanceof EntityPlayer) {
            EntityPlayer entityPlayer = (EntityPlayer) ent;
            if (!PluginUtils.canDamage(ent, player) || !PluginUtils.canDamage(player, ent)) {
                return;
            }
            entityPlayer.inventory.damageArmor(4);
        }
        boolean flag = RegisterHit.checkHeadShot(ent, y);
        NetworkHandler.NETWORK.sendToAllAround(new PacketHitscanEntC(player.getEntityId(), ent.getEntityId(), flag, x, y, z, player.worldObj.getGameRules().getGameRuleBooleanValue("drawTracersForAll")), new NetworkRegistry.TargetPoint(player.dimension, ent.posX, ent.posY, ent.posZ, 400));
        if (flag) {
            DamageSource damagesource = DamageSourceZp.causePlayerBulletHeadShotDamage(player);
            ent.attackEntityFrom(damagesource, damage + EntityUtils.armorCalculationsId(10, ent, 1));
        } else {
            DamageSource damagesource = DamageSourceZp.causePlayerBulletDamage(player);
            ent.attackEntityFrom(damagesource, damage);
        }
        ent.hurtResistantTime = 0;
    }

    public static double calcEnchant(EntityLivingBase entityLivingBase) {
        int i1 = EnchantmentHelper.getMaxEnchantmentLevel(Enchantment.projectileProtection.effectId, new ItemStack[]{entityLivingBase.getEquipmentInSlot(1)});
        int i2 = EnchantmentHelper.getMaxEnchantmentLevel(Enchantment.projectileProtection.effectId, new ItemStack[]{entityLivingBase.getEquipmentInSlot(2)});
        int i3 = EnchantmentHelper.getMaxEnchantmentLevel(Enchantment.projectileProtection.effectId, new ItemStack[]{entityLivingBase.getEquipmentInSlot(3)});
        int i4 = EnchantmentHelper.getMaxEnchantmentLevel(Enchantment.projectileProtection.effectId, new ItemStack[]{entityLivingBase.getEquipmentInSlot(4)});
        return MathHelper.floor_float((i1 + i2 + i3 + i4) * 0.5f);
    }

    @SideOnly(Side.CLIENT)
    public static void spawnBulletBlood(World world, EntityPlayer player, Entity ent, boolean headShot, double x, double y, double z) {
        if (ent.boundingBox != null) {
            double getX = MathHelper.clamp_double(x, ent.boundingBox.minX, ent.boundingBox.maxX);
            double getZ = MathHelper.clamp_double(z, ent.boundingBox.minZ, ent.boundingBox.maxZ);
            if (headShot) {
                SoundUtils.playClientSound(getX, ent.posY + ent.getEyeHeight() + 0.225f, getZ, Main.MODID + ":" + (Main.rand.nextBoolean() ? "headshot2" : "headshot3"), 2.0f, 1.0f);
                for (int i = 0; i < 6; i++) {
                    Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleBlood(world, getX, ent.posY + ent.getEyeHeight() + 0.225f, getZ, 0, 1.3f, 0));
                }
                for (int i = 0; i < 5; i++) {
                    Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleBloodHeadshot(world, getX + calcRanFl(0.05f), ent.posY + ent.getEyeHeight() + 0.25f, getZ + calcRanFl(0.05f), calcRanFl(0.15f), calcRanFl(0.15f), calcRanFl(0.15f)));
                }
            } else {
                for (int i = 0; i < 10; i++) {
                    Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleBlood(world, getX, y, getZ, -player.getLookVec().xCoord, -player.getLookVec().yCoord, -player.getLookVec().zCoord));
                    Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleBlood(world, getX + player.getLookVec().xCoord * 0.5f, y + player.getLookVec().yCoord * 0.5f, getZ + player.getLookVec().zCoord * 0.5f, player.getLookVec().xCoord, player.getLookVec().yCoord, player.getLookVec().zCoord));
                }
            }
        }
    }

    public static float calcRanFl(float mul) {
        return (Main.rand.nextFloat() - Main.rand.nextFloat()) * mul;
    }

    public static boolean checkHeadShot(EntityLivingBase entityLivingBase, double y) {
        if (entityLivingBase instanceof EntityPlayer || (entityLivingBase instanceof AZombieBase && ((AZombieBase) entityLivingBase).canReceiveHeadshot) || entityLivingBase instanceof EntityVillager) {
            if (!(entityLivingBase instanceof EntityPlayer && MovingUtils.isSwimming((EntityPlayer) entityLivingBase))) {
                return y > entityLivingBase.posY + entityLivingBase.height - 0.45f;
            }
        }
        return false;
    }
}
