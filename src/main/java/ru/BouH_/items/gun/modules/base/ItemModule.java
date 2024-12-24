package ru.BouH_.items.gun.modules.base;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import ru.BouH_.Main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemModule extends Item {
    private static final Map<Integer, ItemModule> modMap = new HashMap<>();
    public static int globalModId;
    protected final int id;
    protected final EnumModule enumModule;
    public float recoilVerticalModifier;
    public float recoilHorizontalModifier;
    public float inaccuracyModifier;
    public float damageModifier;
    public float distanceModifier;
    public float stabilityModifier;
    @SideOnly(Side.CLIENT)
    protected IIcon getIconNameToRender;

    protected ItemModule(String name, int id, EnumModule enumModule) {
        this.setUnlocalizedName(name);
        this.setMaxStackSize(1);
        this.id = id;
        this.enumModule = enumModule;
        ItemModule.modMap.put(this.id, this);
    }

    protected ItemModule(String name, EnumModule enumModule) {
        this.setUnlocalizedName(name);
        this.setMaxStackSize(1);
        this.id = ItemModule.globalModId++;
        this.enumModule = enumModule;
        ItemModule.modMap.put(this.id, this);
    }

    public static ItemModule getModById(int id) {
        return ItemModule.modMap.get(id);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconToRender() {
        return this.getIconNameToRender;
    }

    public int getId() {
        return this.id;
    }

    public EnumModule getEnumModule() {
        return this.enumModule;
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
        if (this.distanceModifier != 0) {
            tooltip.add(EnumChatFormatting.GRAY + this.getPercentage(this.distanceModifier) + I18n.format("modifiers.description.distance"));
        }
        if (this.damageModifier != 0) {
            tooltip.add(EnumChatFormatting.GRAY + this.getPercentage(this.distanceModifier) + I18n.format("modifiers.description.damage"));
        }
        if (this.recoilVerticalModifier != 0) {
            tooltip.add(EnumChatFormatting.GRAY + this.getPercentage(this.recoilVerticalModifier) + I18n.format("modifiers.description.recoilVertical"));
        }
        if (this.recoilHorizontalModifier != 0) {
            tooltip.add(EnumChatFormatting.GRAY + this.getPercentage(this.recoilHorizontalModifier) + I18n.format("modifiers.description.recoilHorizontal"));
        }
        if (this.stabilityModifier != 0) {
            tooltip.add(EnumChatFormatting.GRAY + this.getPercentage(this.stabilityModifier) + I18n.format("modifiers.description.stability"));
        }
        if (this.inaccuracyModifier != 0) {
            tooltip.add(EnumChatFormatting.GRAY + this.getPercentage(this.inaccuracyModifier) + I18n.format("modifiers.description.inaccuracy"));
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        this.itemIcon = register.registerIcon(this.getIconString());
        this.getIconNameToRender = register.registerIcon(Main.MODID + ":mod/" + this.getUnlocalizedName().substring(5));
    }

    protected String getPercentage(float f1) {
        return (f1 < 0 ? "" : "+") + (int) (f1 * 100.0f) + "% ";
    }

    public void setDamageModifier(float damageModifier) {
        this.damageModifier = damageModifier;
    }

    public void setDistanceModifier(float distanceModifier) {
        this.distanceModifier = distanceModifier;
    }

    public void setInaccuracyModifier(float inaccuracyModifier) {
        this.inaccuracyModifier = inaccuracyModifier;
    }

    public void setStabilityModifier(float stabilityModifier) {
        this.stabilityModifier = stabilityModifier;
    }

    public void setRecoilVerticalModifier(float recoilVerticalModifier) {
        this.recoilVerticalModifier = recoilVerticalModifier;
    }

    public void setRecoilHorizontalModifier(float recoilHorizontalModifier) {
        this.recoilHorizontalModifier = recoilHorizontalModifier;
    }

    public float getModifiedVerticalRecoil(EntityPlayer player, boolean inZoom) {
        return this.recoilVerticalModifier;
    }

    public float getModifiedHorizontalRecoil(EntityPlayer player, boolean inZoom) {
        return this.recoilHorizontalModifier;
    }

    public float getModifiedInaccuracy(EntityPlayer player, boolean inZoom) {
        return this.inaccuracyModifier;
    }

    public float getModifiedDamage(EntityPlayer player, boolean inZoom) {
        return this.damageModifier;
    }

    public float getModifiedDistance(EntityPlayer player, boolean inZoom) {
        return this.distanceModifier;
    }

    public float getModifiedStability(EntityPlayer player, boolean inZoom) {
        return this.stabilityModifier;
    }
}
