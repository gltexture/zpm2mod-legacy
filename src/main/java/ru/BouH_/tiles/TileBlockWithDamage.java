package ru.BouH_.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TileBlockWithDamage extends TileEntity {
    private int damage;

    @Override
    public void writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setInteger("damage", this.damage);
    }


    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.damage = nbt.getInteger("damage");
    }

    public int getDamage() {
        return this.damage;
    }

    public void addDamage() {
        this.damage++;
    }
}

