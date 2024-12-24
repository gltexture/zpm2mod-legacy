package ru.BouH_.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockOreZp extends Block {
    private final Item droppingItem;

    public BlockOreZp(Item droppingItem) {
        super(Material.rock);
        this.droppingItem = droppingItem;
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return this.droppingItem;
    }
}
