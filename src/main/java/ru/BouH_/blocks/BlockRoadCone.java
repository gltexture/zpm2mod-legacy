package ru.BouH_.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRoadCone extends Block {
    public BlockRoadCone() {
        super(Material.circuits);
        this.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.85F, 0.75F);
    }

    @Override
    public int getRenderType() {
        return 1;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
        return null;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isPassable(IBlockAccess worldIn, int x, int y, int z) {
        return true;
    }

    public boolean canPlaceBlockAt(World worldIn, int x, int y, int z) {
        Block block = worldIn.getBlock(x, y - 1, z);
        return block.isOpaqueCube() && block.getMaterial().blocksMovement();
    }

    public void onNeighborBlockChange(World worldIn, int x, int y, int z, Block neighbor) {
        this.func_150155_m(worldIn, x, y, z);
    }

    private void func_150155_m(World p_150155_1_, int p_150155_2_, int p_150155_3_, int p_150155_4_) {
        if (!this.canPlaceBlockAt(p_150155_1_, p_150155_2_, p_150155_3_, p_150155_4_)) {
            p_150155_1_.setBlockToAir(p_150155_2_, p_150155_3_, p_150155_4_);
        }
    }
}
