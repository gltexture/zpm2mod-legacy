package ru.BouH_.items.gun.modules;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

public class ItemNVScope extends ItemScope {
    public ItemNVScope(String name, String textureName, float fov) {
        super(name, textureName, fov);
    }

    @SuppressWarnings("unchecked")
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);
        tooltip.add(EnumChatFormatting.GREEN + I18n.format("modifiers.description.nv"));
    }

    public float getModifiedInaccuracy(EntityPlayer player, boolean inZoom) {
        return inZoom ? this.inaccuracyModifier : 0.0f;
    }
}
