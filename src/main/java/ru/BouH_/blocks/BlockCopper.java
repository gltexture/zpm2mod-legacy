package ru.BouH_.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import ru.BouH_.init.BlocksZp;

import java.util.Random;

public class BlockCopper extends Block
{
    public BlockCopper()
    {
        super(Material.iron);
    }

    public Item getItemDropped(int meta, Random random, int fortune)
    {
        return Item.getItemFromBlock(BlocksZp.copper_block);
    }
}