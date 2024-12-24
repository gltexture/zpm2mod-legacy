package ru.BouH_.inventory;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import ru.BouH_.tiles.TileBrewingStand;

public class GuiHandler implements IGuiHandler {
    public static int[] GuiId = new int[]{31};

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (!world.isRemote) {
            if (ID == GuiId[0]) {
                return new ru.BouH_.inventory.BrewContainer(player.inventory, (TileBrewingStand) world.getTileEntity(x, y, z));
            }
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == GuiId[0]) {
            return new BrewGui(player.inventory, (TileBrewingStand) world.getTileEntity(x, y, z));
        }
        return null;
    }
}
