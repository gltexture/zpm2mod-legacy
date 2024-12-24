package ru.BouH_.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import ru.BouH_.init.ItemsZp;

import java.util.Random;

public class BlockDebrisand extends Block {
    public BlockDebrisand() {
        super(Material.ground);
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return p_149650_2_.nextFloat() <= 0.025f ? ItemsZp.scrap_material : null;
    }
}
