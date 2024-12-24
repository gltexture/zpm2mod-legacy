package ru.BouH_.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class BlockRoadSlab extends BlockSlab {
    public BlockRoadSlab() {
        super(false, Material.rock);
    }

    @SideOnly(Side.CLIENT)
    public Item getItem(World worldIn, int x, int y, int z) {
        return Item.getItemFromBlock(this);
    }

    @Override
    public String getFullSlabName(int p_150002_1_) {
        return this.getUnlocalizedName();
    }
}
