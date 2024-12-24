package ru.BouH_.fun.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.packets.fun.PacketTacticBlockOwner;

public class TileTacticalBlock extends TileEntity {
    public String owner = "";
    protected int usages;
    private int ticks;

    public void setOwner(String text) {
        this.owner = text;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity() {
        if (!this.worldObj.isRemote && this.ticks++ >= 20) {
            if (!this.owner.isEmpty()) {
                EntityPlayer entityPlayer = this.worldObj.getPlayerEntityByName(this.owner);
                if (entityPlayer != null) {
                    EntityPlayerMP entityPlayerMP = (EntityPlayerMP) entityPlayer;
                    NetworkHandler.NETWORK.sendTo(new PacketTacticBlockOwner(this.xCoord, this.yCoord, this.zCoord), entityPlayerMP);
                }
            }
            this.ticks = 0;
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("usages", this.usages);
        if (!this.owner.isEmpty()) {
            nbt.setString("owner", this.owner);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.usages = nbt.getInteger("usages");
        if (nbt.hasKey("owner")) {
            this.setOwner(nbt.getString("owner"));
        }
    }
}

