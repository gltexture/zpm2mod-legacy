package ru.BouH_.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockConcreteStairs extends BlockStairs {
    public BlockConcreteStairs(Block block, int i) {
        super(block, i);
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return Item.getItemFromBlock(Blocks.cobblestone);
    }

    public int getMobilityFlag() {
        return 2;
    }
}
