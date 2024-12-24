package ru.BouH_.items.gun.modules.base;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.BouH_.Main;
import ru.BouH_.items.gun.ammo.AAmmo;
import ru.BouH_.items.gun.base.AGunBase;
import ru.BouH_.items.gun.modules.ItemForegrip;
import ru.BouH_.utils.SoundUtils;

public abstract class ALauncherModuleBase extends ItemForegrip {
    private final String shootSound;
    private final String reloadSound;
    private final int maxAmmo;
    private final int shootCd;
    private final int reloadCd;
    private final AAmmo aAmmo;

    public ALauncherModuleBase(String name, String shootSound, String reloadSound, int shootCd, int reloadCd, AAmmo aAmmo, int maxAmmo) {
        this(name, shootSound, reloadSound, 0.05f, 0.05f, -0.05f, shootCd, reloadCd, aAmmo, maxAmmo);
    }

    public ALauncherModuleBase(String name, String shootSound, String reloadSound, float recoilHorizontalModifier, float recoilVerticalModifier, float stabilityModifier, int shootCd, int reloadCd, AAmmo aAmmo, int maxAmmo) {
        super(name);
        this.setRecoilHorizontalModifier(recoilHorizontalModifier);
        this.setRecoilVerticalModifier(recoilVerticalModifier);
        this.setStabilityModifier(stabilityModifier);
        this.shootSound = shootSound;
        this.reloadSound = reloadSound;
        this.maxAmmo = maxAmmo;
        this.shootCd = shootCd;
        this.reloadCd = reloadCd;
        this.aAmmo = aAmmo;
    }

    public abstract void shoot(AGunBase aGunBase, ItemStack stack, EntityPlayer player, float rotationPitch, float rotationYaw, boolean scoping);

    public abstract boolean reload(AGunBase aGunBase, ItemStack stack, EntityPlayer player, boolean unReloading);

    public void playShootSound(EntityPlayer player) {
        SoundUtils.playSound(player, Main.MODID + ":" + this.getShootSound(), this.aAmmo.getVolume(), this.getScaledPitch(player));
        if (!player.worldObj.isRemote) {
            player.worldObj.playSoundToNearExcept(player, (Main.MODID + ":far_s"), this.aAmmo.getVolume() * 3.0f, this.getScaledPitch(player));
        }
    }

    public void playReloadSound(EntityPlayer player) {
        SoundUtils.playSound(player, (Main.MODID + ":" + this.getReloadSound()), 2.5f, this.getScaledPitch(player));
    }

    public float getScaledPitch(EntityPlayer player) {
        return player.isInsideOfMaterial(Material.water) || player.isInsideOfMaterial(Material.lava) ? 0 : Main.rand.nextFloat() * 0.2f + 1;
    }

    public String getShootSound() {
        return this.shootSound;
    }

    public String getReloadSound() {
        return this.reloadSound;
    }

    public int getMaxAmmo() {
        return this.maxAmmo;
    }

    public int getShootCd() {
        return this.shootCd;
    }

    public int getReloadCd() {
        return this.reloadCd;
    }

    public AAmmo getAAmmo() {
        return this.aAmmo;
    }

    public Item getAmmo() {
        return this.aAmmo.getItem();
    }
}
