package ru.BouH_.items.gun;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import ru.BouH_.Main;
import ru.BouH_.entity.ieep.PlayerMiscData;
import ru.BouH_.entity.projectile.EntityGrenade40;
import ru.BouH_.items.gun.ammo.AAmmo;
import ru.BouH_.items.gun.base.AGunBase;
import ru.BouH_.utils.RenderUtils;

import java.util.ArrayList;
import java.util.List;

public class ItemM79 extends AGunBase {
    public ItemM79(String name, String shootSound, String reloadSound, int ammo, int cdReload, int cdShoot, float recoilVertical, float recoilHorizontal, float inaccuracy, float inaccuracyInAim, float jamChance, boolean shouldDropShell, boolean isAutomatic, AAmmo ammoItem) {
        super(name, shootSound, reloadSound, GunType.RIFLE, ammo, cdReload, -1, cdShoot, -1, -1, recoilVertical, recoilHorizontal, inaccuracy, inaccuracyInAim, 0, 0, jamChance, shouldDropShell, isAutomatic, ammoItem);
    }

    @Override
    public void makeShot(EntityPlayer player, float rotationPitch, float rotationYaw, boolean scoping) {
        float inaccuracy = scoping ? this.getInaccuracyInAim() : this.getInaccuracy();
        EntityGrenade40 grenade = new EntityGrenade40(player.worldObj, player, 3.0f, inaccuracy);
        player.worldObj.spawnEntityInWorld(grenade);
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
        return 14;
    }

    @Override
    public List<String> weaponDescriptionText(ItemStack stack) {
        List<String> list = new ArrayList<>();
        list.add(String.format("%s: %s", I18n.format("weapon.gui.desc.inaccuracy"), this.getInaccuracy() != this.getInaccuracyInAim() ? RenderUtils.getModifiedValueBiggerWorse(this.getInaccuracy(), this.collectModuleInaccuracy(stack)) + " / " + RenderUtils.getModifiedValueBiggerWorse(this.getInaccuracyInAim(), this.collectModuleInaccuracy(stack)) + " " + I18n.format("weapon.gui.desc.inZoom") : RenderUtils.getModifiedValueBiggerWorse(this.getInaccuracy(), this.collectModuleInaccuracy(stack))));
        list.add(String.format("%s: %s", I18n.format("weapon.gui.desc.recoilHorizontal"), RenderUtils.getModifiedValueBiggerWorse(this.getRecoilHorizontal(), this.collectModuleHorizontalRecoil(stack))));
        list.add(String.format("%s: %s", I18n.format("weapon.gui.desc.recoilVertical"), RenderUtils.getModifiedValueBiggerWorse(this.getRecoilVertical(), this.collectModuleVerticalRecoil(stack))));
        list.add(String.format("%s: %s %s", I18n.format("weapon.gui.desc.fireRate") + EnumChatFormatting.GRAY, 1200 / this.getFireRate(), I18n.format("weapon.gui.desc.rate")));
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
