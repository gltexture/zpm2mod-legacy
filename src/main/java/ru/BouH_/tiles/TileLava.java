package ru.BouH_.tiles;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import ru.BouH_.Main;

public class TileLava extends TileEntity {

    private long timer;

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        timer = nbt.getLong("timer");
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        nbt.setLong("timer", this.timer);
    }

    @Override
    public void updateEntity() {
        if (!this.worldObj.isRemote && this.getBlockMetadata() == 0) {
            if (this.timer == 0) {
                this.timer = this.worldObj.getTotalWorldTime() + Main.rand.nextInt(501);
            }
            if (this.timer <= this.worldObj.getTotalWorldTime() - 6000) {
                this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "random.fizz", 1.0F, 2.6F + (Main.rand.nextFloat() - Main.rand.nextFloat()) * 0.8F);
                this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, Blocks.cobblestone, 0, 3);
            }
        }
    }
}

