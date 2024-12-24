package ru.BouH_.network.packets.misc;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import ru.BouH_.network.SimplePacket;

public class PacketHoly extends SimplePacket {
    public PacketHoly() {
    }

    public PacketHoly(int id) {
        buf().writeInt(id);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void client(EntityPlayer player) {
        int id = buf().readInt();
        if (player.worldObj.getEntityByID(id) != null) {
            EntityLivingBase entityLivingBase = (EntityLivingBase) player.worldObj.getEntityByID(id);
            entityLivingBase.getEntityData().setInteger("holy", entityLivingBase.getEntityData().getInteger("holy") + 100);
        }
    }
}
