package ru.BouH_.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import ru.BouH_.gameplay.WorldManager;

import java.util.Objects;

public class ResetTime extends CommandBase {

    @Override
    public String getCommandName() {
        return "resetTime";
    }

    @Override
    public String getCommandUsage(ICommandSender commandSender) {
        return "/resetTime";
    }

    @Override
    public void processCommand(ICommandSender commandSender, String[] args) {
        commandSender.getEntityWorld().setWorldTime(0);
        WorldManager.WorldSaveDay saveDay = Objects.requireNonNull(WorldManager.WorldSaveDay.getStorage(commandSender.getEntityWorld()));
        saveDay.day = 0;
    }
}

