package ru.BouH_.items.tools;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import ru.BouH_.misc.EmptyTeleport;

import java.util.List;

public class ItemTransmitter extends Item {
    public ItemTransmitter(String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        int dimension = world.getWorldInfo().getDimension();
        if (dimension == 0 || dimension == 2) {
            player.setItemInUse(stack, player.capabilities.isCreativeMode ? 10 : this.getMaxItemUseDuration(stack));
        }
        return stack;
    }

    public void onUsingTick(ItemStack stack, EntityPlayer player, int count) {
        if (player.hurtTime > 0) {
            player.getEntityData().setInteger("itemUsed", 20);
            player.clearItemInUse();
        }
    }

    public int getMaxItemUseDuration(ItemStack stack) {
        return 200;
    }

    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.block;
    }

    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player) {
        int dimension = -99;
        if (player.dimension == 0) {
            dimension = 2;
        } else if (player.dimension == 2) {
            dimension = 0;
        }
        if (dimension != -99) {
            if (!world.isRemote) {
                int x = MathHelper.floor_double(player.posX);
                int y = (int) player.posY;
                int z = MathHelper.floor_double(player.posZ);
                if (!world.canBlockSeeTheSky(x, y, z)) {
                    ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("misc.travel.error.sky");
                    chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
                    player.addChatComponentMessage(chatComponentTranslation);
                } else {
                    player.getEntityData().setLong("transmitterTime", world.getTotalWorldTime());
                    MinecraftServer.getServer().getConfigurationManager().transferPlayerToDimension((EntityPlayerMP) player, dimension, new EmptyTeleport(DimensionManager.getWorld(dimension)));
                    stack.damageItem(1, player);
                    if (stack.stackSize == 0) {
                        return null;
                    }
                }
            }
        }
        return stack;
    }

    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List tooltip, boolean advanced) {
        String dimension = "<error>";
        switch (playerIn.dimension) {
            case 0: {
                dimension = I18n.format("dimension.pz");
                break;
            }
            case 2: {
                dimension = I18n.format("dimension.nw");
                break;
            }
            default: {
                break;
            }
        }
        tooltip.add(EnumChatFormatting.GRAY + dimension);
    }
}
