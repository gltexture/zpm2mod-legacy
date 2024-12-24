package ru.BouH_.network.packets.moving;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import ru.BouH_.entity.ieep.PlayerMiscData;
import ru.BouH_.network.SimplePacket;

public class PacketLyingC extends SimplePacket {
    public PacketLyingC() {
    }

    public PacketLyingC(int plId, boolean isLying) {
        buf().writeInt(plId);
        buf().writeBoolean(isLying);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void client(EntityPlayer player) {
        int plId = buf().readInt();
        boolean isLying = buf().readBoolean();
        if (player.worldObj.getEntityByID(plId) != null) {
            EntityPlayer entityPlayer = (EntityPlayer) player.worldObj.getEntityByID(plId);
            if (entityPlayer != player) {
                PlayerMiscData.getPlayerData(entityPlayer).setLying(isLying);
            }
        }
    }
}
