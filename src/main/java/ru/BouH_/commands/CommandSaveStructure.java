package ru.BouH_.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import ru.BouH_.world.structures.base.AStructure;
import ru.BouH_.world.structures.base.StructureHolder;
import ru.BouH_.world.structures.base.StructureSaver;
import ru.BouH_.world.structures.building.CityStructure;

public class CommandSaveStructure extends CommandBase {

    @Override
    public String getCommandName() {
        return "pos";
    }

    @Override
    public String getCommandUsage(ICommandSender commandSender) {
        return "/pos";
    }

    @Override
    public void processCommand(ICommandSender commandSender, String[] args) {
        if (commandSender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) commandSender;
            if (args.length < 1) {
                throw new WrongUsageException(this.getCommandUsage(commandSender));
            }
            int i = MathHelper.floor_double(player.posX);
            int j = MathHelper.floor_double(player.posY);
            int k = MathHelper.floor_double(player.posZ);
            if (args[0].equals("1")) {
                if (StructureSaver.instance.pos1.containsKey(player)) {
                    StructureSaver.instance.pos1.replace(player, new StructureSaver.BlockPos(i, j, k));
                } else {
                    StructureSaver.instance.pos1.putIfAbsent(player, new StructureSaver.BlockPos(i, j, k));
                }
                notifyOperators(commandSender, this, EnumChatFormatting.LIGHT_PURPLE + "Position 1 " + i + " " + j + " " + k);
            }
            if (args[0].equals("2")) {
                if (StructureSaver.instance.pos2.containsKey(player)) {
                    StructureSaver.instance.pos2.replace(player, new StructureSaver.BlockPos(i, j, k));
                } else {
                    StructureSaver.instance.pos2.putIfAbsent(player, new StructureSaver.BlockPos(i, j, k));
                }
                notifyOperators(commandSender, this, EnumChatFormatting.LIGHT_PURPLE + "Position 2 " + i + " " + j + " " + k);
            }
            if (args[0].equals("test")) {
                if (args.length == 2) {
                    String path = args[1];
                    StructureHolder structureHolder = StructureHolder.create(path);
                    if (!structureHolder.getBlockStates().isEmpty()) {
                        AStructure aStructure = new CityStructure(structureHolder, 1);
                        aStructure.runGenerator(player.worldObj, i, player.worldObj.getPrecipitationHeight(i, k) - 1, k, 0);
                    }
                } else {
                    throw new WrongUsageException("NO");
                }
            }
            if (args[0].equals("scan")) {
                if (StructureSaver.instance.pos1.get(player) == null || StructureSaver.instance.pos2.get(player) == null) {
                    throw new WrongUsageException("NO");
                }
                if (args.length == 2) {
                    StructureSaver.instance.saveFile(player.worldObj, player, args[1]);
                    notifyOperators(commandSender, this, EnumChatFormatting.LIGHT_PURPLE + "Scanned: " + args[1]);
                } else {
                    throw new WrongUsageException("NO");
                }
            }
            if (args[0].equals("del")) {
                StructureSaver.instance.deltaY = Integer.parseInt(args[1]);
                notifyOperators(commandSender, this, EnumChatFormatting.LIGHT_PURPLE + "Delta Y " + args[1]);
            } else {
                StructureSaver.instance.deltaY = 0;
            }
        }
    }
}