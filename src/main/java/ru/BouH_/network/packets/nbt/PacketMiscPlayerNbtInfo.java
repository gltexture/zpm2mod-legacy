package ru.BouH_.network.packets.nbt;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import ru.BouH_.network.SimplePacket;

public class PacketMiscPlayerNbtInfo extends SimplePacket {

    public PacketMiscPlayerNbtInfo() {
    }

    public PacketMiscPlayerNbtInfo(int entId, NBTTagCompound nbtTagCompound) {
        buf().writeInt(entId);
        ByteBufUtils.writeTag(buf(), nbtTagCompound);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void client(EntityPlayer player) {
        int id = buf().readInt();
        NBTTagCompound nbtTagCompound = ByteBufUtils.readTag(buf());
        if (player.worldObj.getEntityByID(id) != null) {
            EntityPlayer pl = (EntityPlayer) player.worldObj.getEntityByID(id);
            pl.getEntityData().setInteger("acid", nbtTagCompound.getInteger("acid"));
            pl.getEntityData().setInteger("holy", nbtTagCompound.getInteger("holy"));
            pl.getEntityData().setInteger("cdReload", nbtTagCompound.getInteger("cdReload"));
            pl.getEntityData().setBoolean("isReloading", nbtTagCompound.getBoolean("isReloading"));
            pl.getEntityData().setBoolean("isUnReloading", nbtTagCompound.getBoolean("isUnReloading"));
        }
    }
}