package ru.BouH_.blocks;

import net.minecraft.item.Item;
import ru.BouH_.init.BlocksZp;

import java.util.Random;

public class BlockTorch2 extends BlockTorchBase {
    public BlockTorch2() {
        super();
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return Item.getItemFromBlock(BlocksZp.torch2);
    }
}
