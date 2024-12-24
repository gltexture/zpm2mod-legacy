package ru.BouH_.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import ru.BouH_.Main;

public class CommandLootChance extends CommandBase {

    @Override
    public String getCommandName() {
        return "setChance";
    }

    @Override
    public String getCommandUsage(ICommandSender commandSender) {
        return "/setChance <float>";
    }

    @Override
    public void processCommand(ICommandSender commandSender, String[] args) {
        if (commandSender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) commandSender;
            if (args.length != 1) {
                throw new WrongUsageException(this.getCommandUsage(commandSender));
            }
            ItemStack stack = player.getHeldItem();
            if (stack != null && stack.hasTagCompound()) {
                ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("set: " + MathHelper.clamp_float(Float.parseFloat(args[0]), 0, 1));
                chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.AQUA);
                player.addChatComponentMessage(chatComponentTranslation);
                stack.getTagCompound().getCompoundTag(Main.MODID).setFloat("chance", Float.parseFloat(args[0]));
            }
        }
    }
}

