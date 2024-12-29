package ru.BouH_.commands;

import net.minecraft.block.Block;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import ru.BouH_.gameplay.PlayerManager;
import ru.BouH_.tiles.TileLootCase;

import java.util.ArrayList;

public class CommandChangeChestsToLootCases extends CommandBase {

    @Override
    public String getCommandName() {
        return "changeChestsToLootCases";
    }

    @Override
    public String getCommandUsage(ICommandSender commandSender) {
        return "/changeChestsToLootCases";
    }

    @SuppressWarnings("all")
    @Override
    public void processCommand(ICommandSender commandSender, String[] args) {
        if (commandSender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) commandSender;
            if (args.length > 0) {
                throw new WrongUsageException(this.getCommandUsage(commandSender));
            }
            for (Object tileEntity : new ArrayList<>(player.worldObj.loadedTileEntityList)) {
                TileEntity tile = (TileEntity) tileEntity;
                if (tile instanceof TileEntityChest) {
                    Block block = PlayerManager.chooseRandomLootCase();
                    int m = tile.getWorld().getBlockMetadata(tile.xCoord, tile.yCoord, tile.zCoord);
                    tile.getWorld().setBlock(tile.xCoord, tile.yCoord, tile.zCoord, block);
                    tile.getWorld().setBlockMetadataWithNotify(tile.xCoord, tile.yCoord, tile.zCoord, m, 0);
                    notifyOperators(commandSender, this, "Done - [" + tile.xCoord + ", " + tile.yCoord + ", " + tile.zCoord + "]");
                }
            }
        }
    }
}

