package ru.BouH_.items.gun;

import cpw.mods.fml.relauncher.FMLLaunchHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import ru.BouH_.Main;
import ru.BouH_.entity.ieep.PlayerMiscData;
import ru.BouH_.items.gun.ammo.AAmmo;
import ru.BouH_.items.gun.tracer.RegisterHit;
import ru.BouH_.items.gun.tracer.Tracer;
import ru.BouH_.items.gun.tracer.TracerHit;

public class ItemGauss extends ItemRifle {
    public ItemGauss(String name, String shootSound, String reloadSound, int ammo, int cdReload, int damage, int cdShoot, int maxDistance, int effectiveDistance, float recoilVertical, float recoilHorizontal, float inaccuracy, float inaccuracyInAim, float addInaccuracyStep, float maxAddInaccuracy, float jamChance, boolean shouldDropShell, boolean isAutomatic, AAmmo ammoItem) {
        super(name, shootSound, reloadSound, ammo, cdReload, damage, cdShoot, maxDistance, effectiveDistance, recoilVertical, recoilHorizontal, inaccuracy, inaccuracyInAim, addInaccuracyStep, maxAddInaccuracy, jamChance, shouldDropShell, isAutomatic, ammoItem);
        if (FMLLaunchHandler.side().isClient()) {
            this.disableMuzzleFlash();
        }
    }

    @SideOnly(Side.CLIENT)
    public void sparksFX(EntityPlayer player, ItemStack stack) {
    }

    @Override
    public void makeShot(EntityPlayer player, float rotationPitch, float rotationYaw, boolean scoping) {
        float inaccuracy = (scoping ? this.getInaccuracyInAim() : this.getInaccuracy()) * this.inaccuracyModifier(player, player.getHeldItem(), scoping);
        int damage = (int) (this.getDamage() * this.damageModifier(player, player.getHeldItem()));
        int distance = (int) (this.getMaxDistance() * this.distanceModifier(player, player.getHeldItem()));
        if (player.isInsideOfMaterial(Material.water) || player.worldObj.isRainingAt(MathHelper.floor_double(player.posX), MathHelper.floor_double(player.posY + (double) player.height), MathHelper.floor_double(player.posZ))) {
            player.attackEntityFrom(DamageSource.generic, 5);
            player.worldObj.playSoundAtEntity(player, Main.MODID + ":electro", 1.0F, 0.9f + Main.rand.nextFloat() * 0.1f);
        }
        TracerHit trace = Tracer.trace(player.worldObj, Vec3.createVectorHelper(player.posX, player.posY + player.getEyeHeight(), player.posZ), rotationPitch, rotationYaw, inaccuracy, distance, player, PlayerMiscData.getPlayerData(player).getPing());
        RegisterHit.registerHit(player, this.getMaxDistance(), this.getEffectiveDistance(), trace.blockX, trace.blockY, trace.blockZ, trace.hitVec.xCoord, trace.hitVec.yCoord, trace.hitVec.zCoord, trace.sideHit, trace.entityHit, damage);
    }
}
