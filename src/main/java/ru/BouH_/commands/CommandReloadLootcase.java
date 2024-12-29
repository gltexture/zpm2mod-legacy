package ru.BouH_.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import ru.BouH_.tiles.TileLootCase;

public class CommandReloadLootcase extends CommandBase {

    @Override
    public String getCommandName() {
        return "reloadLoot";
    }

    @Override
    public String getCommandUsage(ICommandSender commandSender) {
        return "/reloadLoot";
    }

    @Override
    public void processCommand(ICommandSender commandSender, String[] args) {
        if (commandSender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) commandSender;
            if (args.length > 0) {
                throw new WrongUsageException(this.getCommandUsage(commandSender));
            }
            for (Object tileEntity : player.worldObj.loadedTileEntityList) {
                TileEntity tile = (TileEntity) tileEntity;
                if (tile instanceof TileLootCase) {
                    TileLootCase tileLootCase = (TileLootCase) tile;
                    tileLootCase.reloadChest(1);
                    notifyOperators(commandSender, this, "Reloaded LootCase [" + tileLootCase.xCoord + ", " + tileLootCase.yCoord + ", " + tileLootCase.zCoord + "]");
                }
            }
        }
    }
}

