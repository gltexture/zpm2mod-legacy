package ru.BouH_.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockFrozenDirt extends Block {
    public BlockFrozenDirt() {
        super(Material.ground);
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return p_149650_2_.nextFloat() <= 0.25f ? Items.snowball : null;
    }
}
