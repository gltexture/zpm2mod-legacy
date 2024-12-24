package ru.BouH_.items.gun;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Vec3;
import ru.BouH_.Main;
import ru.BouH_.entity.ieep.PlayerMiscData;
import ru.BouH_.items.gun.ammo.AAmmo;
import ru.BouH_.items.gun.base.AGunBase;
import ru.BouH_.items.gun.tracer.RegisterHit;
import ru.BouH_.items.gun.tracer.Tracer;
import ru.BouH_.items.gun.tracer.TracerHit;
import ru.BouH_.utils.RenderUtils;

import java.util.ArrayList;
import java.util.List;

public class ItemShotgun extends AGunBase {
    public ItemShotgun(String name, String shootSound, String reloadSound, int ammo, int cdReload, int damage, int cdShoot, int maxDistance, int effectiveDistance, float recoilVertical, float recoilHorizontal, float inaccuracy, float inaccuracyInAim, float addInaccuracyStep, float maxAddInaccuracy, float jamChance, boolean shouldDropShell, boolean isAutomatic, AAmmo ammoItem) {
        super(name, shootSound, reloadSound, GunType.RIFLE, ammo, cdReload, damage, cdShoot, maxDistance, effectiveDistance, recoilVertical, recoilHorizontal, inaccuracy, inaccuracyInAim, addInaccuracyStep, maxAddInaccuracy, jamChance, shouldDropShell, isAutomatic, ammoItem);
    }

    @Override
    public void makeShot(EntityPlayer player, float rotationPitch, float rotationYaw, boolean scoping) {
        float inaccuracy = scoping ? this.getInaccuracyInAim() : this.getInaccuracy();
        int damage = (int) (this.getDamage() * this.damageModifier(player, player.getHeldItem()));
        int distance = (int) (this.getMaxDistance() * this.distanceModifier(player, player.getHeldItem()));

        for (int i = 0; i < 10; i++) {
            TracerHit trace = Tracer.trace(player.worldObj, Vec3.createVectorHelper(player.posX, player.posY + player.getEyeHeight(), player.posZ), rotationPitch, rotationYaw, inaccuracy, distance, player, PlayerMiscData.getPlayerData(player).getPing());
            RegisterHit.registerHit(player, this.getMaxDistance(), this.getEffectiveDistance(), trace.blockX, trace.blockY, trace.blockZ, trace.hitVec.xCoord, trace.hitVec.yCoord, trace.hitVec.zCoord, trace.sideHit, trace.entityHit, damage);
        }
    }

    @Override
    public boolean makeReload(ItemStack stack, EntityPlayer player, boolean unReloading) {
        if (!stack.getTagCompound().getCompoundTag(Main.MODID).getBoolean("isJammed")) {
            if (unReloading) {
                while (stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("ammo") > 0) {
                    if (!player.inventory.addItemStackToInventory(new ItemStack(this.getAmmo()))) {
                        if (!player.worldObj.isRemote) {
                            player.dropItemWithOffset(this.getAmmo(), 1, 1.0f);
                        }
                    }
                    stack.getTagCompound().getCompoundTag(Main.MODID).setInteger("ammo", stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("ammo") - 1);
                }
            } else {
                while (stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("ammo") < this.getMaxAmmo() && (player.capabilities.isCreativeMode || player.inventory.consumeInventoryItem(this.getAmmo()))) {
                    stack.getTagCompound().getCompoundTag(Main.MODID).setInteger("ammo", stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("ammo") + 1);
                }
            }
        }
        return true;
    }

    @SideOnly(Side.CLIENT)
    public int particleSmokeCount() {
        return super.particleSmokeCount() * 10;
    }

    @Override
    public List<String> weaponDescriptionText(ItemStack stack) {
        List<String> list = new ArrayList<>();
        list.add(String.format("%s: %s x 10 %s", I18n.format("weapon.gui.desc.damage"), RenderUtils.getModifiedValueBiggerWorse(this.getDamage(), this.collectModuleDamage(stack)), I18n.format("weapon.gui.desc.hp")));
        list.add(String.format("%s: %s x 10 %s", I18n.format("weapon.gui.desc.damagePerSec"), RenderUtils.getModifiedValueBiggerWorse(20.0f * this.getDamage() / this.getFireRate(), this.collectModuleDamage(stack)), I18n.format("weapon.gui.desc.hp")));
        list.add(String.format("%s: %s", I18n.format("weapon.gui.desc.inaccuracy"), this.getInaccuracy() != this.getInaccuracyInAim() ? RenderUtils.getModifiedValueBiggerWorse(this.getInaccuracy(), this.collectModuleInaccuracy(stack)) + " / " + RenderUtils.getModifiedValueBiggerWorse(this.getInaccuracyInAim(), this.collectModuleInaccuracy(stack)) + " " + I18n.format("weapon.gui.desc.inZoom") : RenderUtils.getModifiedValueBiggerWorse(this.getInaccuracy(), this.collectModuleInaccuracy(stack))));
        list.add(String.format("%s: %s", I18n.format("weapon.gui.desc.recoilHorizontal"), RenderUtils.getModifiedValueBiggerWorse(this.getRecoilHorizontal(), this.collectModuleHorizontalRecoil(stack))));
        list.add(String.format("%s: %s", I18n.format("weapon.gui.desc.recoilVertical"), RenderUtils.getModifiedValueBiggerWorse(this.getRecoilVertical(), this.collectModuleVerticalRecoil(stack))));
        list.add(String.format("%s: %s %s", I18n.format("weapon.gui.desc.fireRate") + EnumChatFormatting.GRAY, 1200 / this.getFireRate(), I18n.format("weapon.gui.desc.rate")));
        list.add(String.format("%s: %s (%s max) %s", I18n.format("weapon.gui.desc.shootDistance"), RenderUtils.getModifiedValueBiggerBetter(this.getEffectiveDistance(), this.collectModuleDistance(stack)), RenderUtils.getModifiedValueBiggerBetter(this.getMaxDistance(), this.collectModuleDistance(stack)), I18n.format("weapon.gui.desc.blocks")));
        list.add(String.format("%s: %s %s", I18n.format("weapon.gui.desc.reloadTime") + EnumChatFormatting.GRAY, this.getCdReload() / 20.0f, I18n.format("weapon.gui.desc.sec")));
        list.add("");
        return list;
    }

    @Override
    public void onShootPost(ItemStack stack, EntityPlayer player) {
        if (!player.worldObj.isRemote && !this.isSilenced(stack)) {
            PlayerMiscData.getPlayerData(player).addPlayerLoudness((int) this.getShootVolume(stack));
        }
    }
}
