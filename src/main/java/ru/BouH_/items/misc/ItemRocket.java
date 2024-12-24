package ru.BouH_.items.misc;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import ru.BouH_.Main;

import java.util.List;

public class ItemRocket extends Item {
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
        if (stack.hasTagCompound()) {
            tooltip.add("Owner: " + stack.getTagCompound().getCompoundTag(Main.MODID).getString("owner"));
        }
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
        if (entityIn instanceof EntityPlayer) {
            EntityPlayer entityPlayer = (EntityPlayer) entityIn;
            if (!stack.hasTagCompound()) {
                stack.setTagInfo(Main.MODID, new NBTTagCompound());
                stack.getTagCompound().getCompoundTag(Main.MODID).setString("owner", entityPlayer.getDisplayName());
            }
        }
    }
}
