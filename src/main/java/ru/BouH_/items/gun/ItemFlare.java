package ru.BouH_.items.gun;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import ru.BouH_.entity.projectile.EntityFlare;
import ru.BouH_.items.gun.ammo.AAmmo;
import ru.BouH_.utils.RenderUtils;

import java.util.ArrayList;
import java.util.List;

public class ItemFlare extends ItemPistol {
    public ItemFlare(String name, String shootSound, String reloadSound, int ammo, int cdReload, int cdShoot, float recoilVertical, float recoilHorizontal, float inaccuracy, float inaccuracyInAim, float jamChance, boolean shouldDropShell, boolean isAutomatic, AAmmo ammoItem) {
        super(name, shootSound, reloadSound, ammo, cdReload, -1, cdShoot, -1, -1, recoilVertical, recoilHorizontal, inaccuracy, inaccuracyInAim, 0, 0, jamChance, shouldDropShell, isAutomatic, ammoItem);
    }

    @Override
    public void makeShot(EntityPlayer player, float rotationPitch, float rotationYaw, boolean scoping) {
        float inaccuracy = scoping ? this.getInaccuracyInAim() : this.getInaccuracy();
        EntityFlare flare = new EntityFlare(player.worldObj, player, 2.5f, inaccuracy);
        player.worldObj.spawnEntityInWorld(flare);
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
}
