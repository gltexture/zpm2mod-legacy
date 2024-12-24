package ru.BouH_.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import ru.BouH_.blocks.lootCases.EnumLootGroups;
import ru.BouH_.tiles.loot_spawn.ItemToSpawn;
import ru.BouH_.tiles.loot_spawn.LootSpawnManager;

public class CommandCheckLoot extends CommandBase {

    @Override
    public String getCommandName() {
        return "checkAllLootGroups";
    }

    @Override
    public String getCommandUsage(ICommandSender commandSender) {
        return "/checkAllLootGroups";
    }

    @Override
    public void processCommand(ICommandSender commandSender, String[] args) {
        if (commandSender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) commandSender;
            if (args.length > 0) {
                throw new WrongUsageException(this.getCommandUsage(commandSender));
            }
            for (EnumLootGroups enumLootGroups : EnumLootGroups.values()) {
                int i1 = 0;
                for (LootSpawnManager set : enumLootGroups.getLSP()) {
                    i1 += set.getSpawnChance();
                    int i2 = 0;
                    String s1 = "";
                    for (ItemToSpawn itemToSpawn : set.getLootSet()) {
                        i2 += itemToSpawn.getSpawnChance();
                        s1 = itemToSpawn.getItem().getUnlocalizedName();
                    }
                    if (i2 != 100) {
                        System.out.println("PROBLEM-ITS-ID-N: " + enumLootGroups.getId() + " : " + s1 + "(" + i2 + ")");
                    }
                }
                if (i1 != 100) {
                    System.out.println("PROBLEM-LSP-ID: " + enumLootGroups.getId() + "(" + i1 + ")");
                }
            }
        }
    }
}

