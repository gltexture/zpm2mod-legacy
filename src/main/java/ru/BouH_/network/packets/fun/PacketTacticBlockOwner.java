package ru.BouH_.network.packets.fun;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import ru.BouH_.fun.tiles.TileTacticalBlock;
import ru.BouH_.network.SimplePacket;

public class PacketTacticBlockOwner extends SimplePacket {
    public PacketTacticBlockOwner() {
    }

    public PacketTacticBlockOwner(int x, int y, int z) {
        buf().writeInt(x).writeInt(y).writeInt(z);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void client(EntityPlayer player) {
        int x = buf().readInt();
        int y = buf().readInt();
        int z = buf().readInt();
        TileEntity tileEntity = player.worldObj.getTileEntity(x, y, z);
        if (tileEntity != null) {
            if (tileEntity instanceof TileTacticalBlock) {
                TileTacticalBlock tileTacticalBlock = (TileTacticalBlock) tileEntity;
                tileTacticalBlock.setOwner(player.getDisplayName());
            }
        }
    }
}
