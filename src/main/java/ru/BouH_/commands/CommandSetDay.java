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
import ru.BouH_.gameplay.WorldManager;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.packets.misc.PacketDay;

import java.util.Objects;

public class CommandSetDay extends CommandBase {

    @Override
    public String getCommandName() {
        return "setDay";
    }

    @Override
    public String getCommandUsage(ICommandSender commandSender) {
        return "/setDay <int>";
    }

    @Override
    public void processCommand(ICommandSender commandSender, String[] args) {
        if (commandSender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) commandSender;
            if (args.length != 1) {
                throw new WrongUsageException(this.getCommandUsage(commandSender));
            }
            WorldManager.WorldSaveDay saveDay = Objects.requireNonNull(WorldManager.WorldSaveDay.getStorage(commandSender.getEntityWorld()));
            saveDay.day = Math.max(Integer.parseInt(args[0]), 0);
            NetworkHandler.NETWORK.sendToDimension(new PacketDay(WorldManager.is7NightEnabled(), saveDay.day), commandSender.getEntityWorld().provider.dimensionId);
            CommandBase.notifyOperators(commandSender, this, "Success");
        }
    }
}

