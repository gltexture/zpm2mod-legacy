package ru.BouH_.items.gun.crossbow;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import ru.BouH_.Main;
import ru.BouH_.items.gun.ammo.AAmmo;
import ru.BouH_.items.gun.base.AGunBase;
import ru.BouH_.utils.RenderUtils;

import java.util.ArrayList;
import java.util.List;

public class ItemCrossbow extends AGunBase {
    @SideOnly(Side.CLIENT)
    private IIcon icon;
    @SideOnly(Side.CLIENT)
    private IIcon icon2;

    public ItemCrossbow(String name, String shootSound, String reloadSound, int cdReload, int cdShoot, float inaccuracy, float inaccuracyInAim, AAmmo ammoItem) {
        super(name, shootSound, reloadSound, GunType.CROSSBOW, 1, cdReload, -1, cdShoot, -1, -1, 0.1f, 0.1f, inaccuracy, inaccuracyInAim, 0, 0, 0, false, false, ammoItem);
    }

    protected void addRepair() {
    }

    protected void triggerAchievement(EntityPlayer entityPlayer) {
    }

    @Override
    public void makeShot(EntityPlayer player, float rotationPitch, float rotationYaw, boolean scoping) {
        EntityArrow arrow = new EntityArrow(player.worldObj, player, 2.6f);
        ItemStack stack = player.getHeldItem();
        arrow.setIsCritical(true);
        arrow.getEntityData().setBoolean("stronger", true);

        int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);

        if (k > 0) {
            arrow.setDamage(arrow.getDamage() + (double) k * 0.5D + 0.5D);
        }

        int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);

        if (l > 0) {
            arrow.setKnockbackStrength(l);
        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) > 0) {
            arrow.setFire(100);
        }

        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0) {
            arrow.canBePickedUp = 2;
        }
        player.worldObj.spawnEntityInWorld(arrow);
    }

    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return true;
    }

    public int getItemEnchantability() {
        return 1;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int p_77617_1_) {
        return this.icon;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        this.icon = register.registerIcon(Main.MODID + ":crossbow_0");
        this.icon2 = register.registerIcon(Main.MODID + ":crossbow_1");
    }

    @SideOnly(Side.CLIENT)
    public IIcon getActualIcon(ItemStack stack) {
        return stack.hasTagCompound() && stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("ammo") == 1 ? this.icon2 : this.icon;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
        return this.getActualIcon(stack);
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
                while (stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("ammo") < this.getMaxAmmo() && (EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0 || player.capabilities.isCreativeMode || player.inventory.consumeInventoryItem(this.getAmmo()))) {
                    stack.getTagCompound().getCompoundTag(Main.MODID).setInteger("ammo", stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("ammo") + 1);
                }
            }
        }
        return true;
    }

    @SideOnly(Side.CLIENT)
    public void gunFX(ItemStack stack, EntityPlayer player, boolean dropShell) {
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
