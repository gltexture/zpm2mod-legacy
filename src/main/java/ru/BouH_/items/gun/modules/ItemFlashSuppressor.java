package ru.BouH_.items.gun.modules;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import ru.BouH_.items.gun.modules.base.EnumModule;
import ru.BouH_.items.gun.modules.base.ItemModule;

import java.util.List;

public class ItemFlashSuppressor extends ItemModule {
    public ItemFlashSuppressor(String name) {
        super(name, EnumModule.BARREL);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
        super.addInformation(stack, playerIn, tooltip, advanced);
        tooltip.add(EnumChatFormatting.GREEN + I18n.format("modifiers.description.disMuzFlash"));
        tooltip.add(EnumChatFormatting.GREEN + I18n.format("modifiers.description.smoke"));
    }
}
