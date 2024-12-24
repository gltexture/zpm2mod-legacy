package ru.BouH_.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;
import ru.BouH_.weather.base.WeatherHandler;
import ru.BouH_.weather.managers.WeatherFogManager;
import ru.BouH_.weather.managers.WeatherRainManager;

import java.util.List;

public class CommandWth extends CommandBase {
    public static CommandWth instance = new CommandWth();

    @Override
    public String getCommandName() {
        return "wth";
    }

    @Override
    public String getCommandUsage(ICommandSender commandSender) {
        return "/wth rain/fog/clear";
    }

    @Override
    public void processCommand(ICommandSender commandSender, String[] args) {
        if (commandSender instanceof EntityPlayer) {
            if (args.length >= 1) {
                WorldServer worldserver = MinecraftServer.getServer().worldServers[0];
                if ("clear".equalsIgnoreCase(args[0])) {
                    WeatherHandler.instance.getWeatherFog().stopWeatherFogPacket((WeatherFogManager) WeatherHandler.instance.getWorldFogInfo(((EntityPlayer) commandSender).dimension));
                    WeatherHandler.instance.getWeatherRain().stopWeatherRainPacket((WeatherRainManager) WeatherHandler.instance.getWorldRainInfo(((EntityPlayer) commandSender).dimension));
                    CommandBase.notifyOperators(commandSender, this, "commands.weather.clear");
                } else if ("fog".equalsIgnoreCase(args[0])) {
                    WeatherHandler.instance.getWeatherFog().startWeatherFogPacket((WeatherFogManager) WeatherHandler.instance.getWorldFogInfo(worldserver.getWorldInfo().getDimension()));
                    CommandBase.notifyOperators(commandSender, this, "Started Fog!");
                } else if ("rain".equalsIgnoreCase(args[0])) {
                    WeatherHandler.instance.getWeatherRain().startWeatherRainPacket((WeatherRainManager) WeatherHandler.instance.getWorldRainInfo(worldserver.getWorldInfo().getDimension()));
                    CommandBase.notifyOperators(commandSender, this, "commands.weather.rain");
                } else {
                    throw new WrongUsageException("commands.weather.usage");
                }
            } else {
                throw new WrongUsageException("commands.weather.usage");
            }
        }
    }

    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        return args.length == 1 ? CommandBase.getListOfStringsMatchingLastWord(args, "clear", "rain", "fog") : null;
    }
}

