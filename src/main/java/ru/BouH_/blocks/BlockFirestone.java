package ru.BouH_.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class BlockFirestone extends Block {

    public BlockFirestone() {
        super(Material.rock);
    }

    protected boolean canSilkHarvest() {
        return false;
    }

    public boolean isFireSource(World world, int x, int y, int z, ForgeDirection side) {
        return true;
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return Item.getItemFromBlock(Blocks.cobblestone);
    }
}
