package ru.BouH_.items.progs;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.tiles.TileLootCase;

import java.util.List;

public class ItemChance extends Item {
    public ItemChance(String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!stack.hasTagCompound()) {
            stack.setTagInfo(Main.MODID, new NBTTagCompound());
            stack.getTagCompound().getCompoundTag(Main.MODID).setFloat("chance", 1.0f);
        }
    }

    public boolean onItemUseFirst(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
        if (!p_77648_2_.worldObj.isRemote) {
            if (p_77648_1_.hasTagCompound()) {
                TileEntity tileEntity = p_77648_2_.worldObj.getTileEntity(p_77648_4_, p_77648_5_, p_77648_6_);
                if (tileEntity != null) {
                    if (tileEntity instanceof TileLootCase) {
                        TileLootCase tileLootCase = (TileLootCase) tileEntity;
                        tileLootCase.setChance(p_77648_1_.getTagCompound().getCompoundTag(Main.MODID).getFloat("chance"));
                        ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("tools.chance.success");
                        chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.AQUA);
                        p_77648_2_.addChatComponentMessage(chatComponentTranslation);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
        tooltip.add(EnumChatFormatting.GRAY + "/setChance <float>");
        tooltip.add(EnumChatFormatting.AQUA + I18n.format("tools.chance"));
        if (stack.hasTagCompound()) {
            tooltip.add(EnumChatFormatting.GREEN + "Chance: " + stack.getTagCompound().getCompoundTag(Main.MODID).getFloat("chance"));
        }
    }
}
