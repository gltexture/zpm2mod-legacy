package ru.BouH_.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import ru.BouH_.init.ItemsZp;

import java.util.Random;

public class BlockArmor extends Block {
    public BlockArmor() {
        super(Material.iron);
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return p_149650_2_.nextFloat() <= 0.225f ? ItemsZp.armor_material : null;
    }
}
