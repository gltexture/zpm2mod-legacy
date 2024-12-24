package ru.BouH_.tiles;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import ru.BouH_.Main;
import ru.BouH_.blocks.BlockTorch1;
import ru.BouH_.blocks.BlockTorch2;
import ru.BouH_.blocks.BlockTorch3;
import ru.BouH_.blocks.BlockTorch4;
import ru.BouH_.init.BlocksZp;

public class TileTorch extends TileEntity {
    private long timer;

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setLong("timer", this.timer);
    }


    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.timer = nbt.getLong("timer");
    }

    public void setTimer(long timer) {
        this.timer = timer;
    }

    @Override
    public void updateEntity() {
        if (!this.worldObj.isRemote) {
            if (this.timer == 0) {
                this.timer = this.worldObj.getTotalWorldTime() + Main.rand.nextInt(501);
            }
            if ((this.worldObj.isRainingAt(this.xCoord, this.yCoord, this.zCoord) && this.worldObj.getTotalWorldTime() % 20 == 0 && Main.rand.nextBoolean()) || this.timer <= this.worldObj.getTotalWorldTime() - 12000) {
                Block block = null;
                if (this.getBlockType() instanceof BlockTorch1) {
                    block = BlocksZp.torch2;
                } else if (this.getBlockType() instanceof BlockTorch2) {
                    block = BlocksZp.torch3;
                } else if (this.getBlockType() instanceof BlockTorch3) {
                    block = BlocksZp.torch4;
                } else if (this.getBlockType() instanceof BlockTorch4) {
                    block = BlocksZp.torch5;
                }
                this.worldObj.setBlock(this.xCoord, this.yCoord, this.zCoord, block, this.getBlockMetadata(), 2);
                this.worldObj.updateAllLightTypes(this.xCoord, this.yCoord, this.zCoord);
                if (this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != null) {
                    TileTorch tileTorch = (TileTorch) this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord);
                    tileTorch.setTimer(this.timer + 8200 + Main.rand.nextInt(501));
                }
                this.worldObj.playSoundEffect(this.xCoord, this.yCoord, this.zCoord, "random.fizz", 1.0F, 2.6F + (Main.rand.nextFloat() - Main.rand.nextFloat()) * 0.8F);
            }
        }
    }
}

