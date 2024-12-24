package ru.BouH_.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockDecorate extends Block {
    private final int mobilityFlag;

    public BlockDecorate(Material p_i45408_1_, int mobilityFlag) {
        super(p_i45408_1_);
        this.mobilityFlag = mobilityFlag;
    }

    public int getMobilityFlag() {
        return this.mobilityFlag;
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return null;
    }

    protected boolean canSilkHarvest() {
        return false;
    }
}
