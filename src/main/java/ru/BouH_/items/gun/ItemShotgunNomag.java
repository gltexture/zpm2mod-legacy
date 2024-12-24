package ru.BouH_.items.gun;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import ru.BouH_.Main;
import ru.BouH_.items.gun.ammo.AAmmo;
import ru.BouH_.items.gun.events.ClientGunEvent;
import ru.BouH_.utils.RenderUtils;
import ru.BouH_.utils.SoundUtils;

import java.util.ArrayList;
import java.util.List;

public class ItemShotgunNomag extends ItemShotgun {
    protected String reloadSound2;

    public ItemShotgunNomag(String name, String shootSound, String reloadSound, int ammo, int cdReload, int damage, int cdShoot, int maxDistance, int effectiveDistance, float recoilVertical, float recoilHorizontal, float inaccuracy, float inaccuracyInAim, float addInaccuracyStep, float maxAddInaccuracy, float jamChance, boolean shouldDropShell, boolean isAutomatic, AAmmo ammoItem) {
        super(name, shootSound, reloadSound, ammo, cdReload, damage, cdShoot, maxDistance, effectiveDistance, recoilVertical, recoilHorizontal, inaccuracy, inaccuracyInAim, addInaccuracyStep, maxAddInaccuracy, jamChance, shouldDropShell, isAutomatic, ammoItem);
        this.setAdditionalReloadSound("shotgun_r");
    }

    @Override
    public boolean makeReload(ItemStack stack, EntityPlayer player, boolean unReloading) {
        if (!stack.getTagCompound().getCompoundTag(Main.MODID).getBoolean("isJammed")) {
            if (unReloading) {
                if (stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("ammo") > 0) {
                    if (!player.inventory.addItemStackToInventory(new ItemStack(this.getAmmo()))) {
                        player.dropItem(this.getAmmo(), 1);
                    }
                    stack.getTagCompound().getCompoundTag(Main.MODID).setInteger("ammo", stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("ammo") - 1);
                    player.getEntityData().setInteger("cdReload", stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("ammo") == 0 ? 10 : this.getCdReload());
                    this.playReloadSound2(player);
                    return false;
                }
            } else {
                if (stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("ammo") < this.getMaxAmmo()) {
                    if (player.capabilities.isCreativeMode || player.inventory.consumeInventoryItem(this.getAmmo())) {
                        stack.getTagCompound().getCompoundTag(Main.MODID).setInteger("ammo", stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("ammo") + 1);
                        player.getEntityData().setInteger("cdReload", stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("ammo") == this.getMaxAmmo() ? 10 : this.getCdReload());
                        this.playReloadSound2(player);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    protected void setAdditionalReloadSound(String sound) {
        this.reloadSound2 = sound;
    }

    public void playReloadSound2(EntityPlayer player) {
        SoundUtils.playSound(player, Main.MODID + ":" + this.reloadSound2, 2.5f, this.getScaledPitch(player));
    }

    public void onReloadPre(ItemStack stack, EntityPlayer player) {
        if (player.worldObj.isRemote && player instanceof EntityPlayerSP) {
            ClientGunEvent.instance.isDown = true;
        }
    }

    public boolean canInterruptReloading(EntityPlayer player, ItemStack stack) {
        return this.isInReloadingAnim(player) && !stack.getTagCompound().getCompoundTag(Main.MODID).getBoolean("isJammed");
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
        list.add(String.format("%s: %s %s", I18n.format("weapon.gui.desc.reloadTime") + EnumChatFormatting.GRAY, this.getCdReload() / 20.0f + " x " + this.getMaxAmmo(), I18n.format("weapon.gui.desc.sec")));
        list.add("");
        return list;
    }

    public void onReloadPost(ItemStack stack, EntityPlayer player) {
        SoundUtils.playSound(player, (Main.MODID + ":" + "shotgun_r2"), 2.5f, this.getScaledPitch(player));
    }
}
