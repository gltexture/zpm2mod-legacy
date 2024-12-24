package ru.BouH_.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

public class BlockLayer extends Block {
    public BlockLayer() {
        super(Material.snow);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
        this.func_150154_b(0);
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World worldIn, int x, int y, int z) {
        int l = worldIn.getBlockMetadata(x, y, z) & 7;
        float f = 0.125F;
        return AxisAlignedBB.getBoundingBox((double) x + this.minX, (double) y + this.minY, (double) z + this.minZ, (double) x + this.maxX, (float) y + (float) l * f, (double) z + this.maxZ);
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public void setBlockBoundsForItemRender() {
        this.func_150154_b(0);
    }

    public void setBlockBoundsBasedOnState(IBlockAccess worldIn, int x, int y, int z) {
        this.func_150154_b(worldIn.getBlockMetadata(x, y, z));
    }

    protected void func_150154_b(int p_150154_1_) {
        int j = p_150154_1_ & 7;
        float f = (float) (2 * (1 + j)) / 16.0F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
    }

    public boolean canBlockStay(World worldIn, int x, int y, int z) {
        return this.canPlaceBlockAt(worldIn, x, y, z);
    }

    public boolean canPlaceBlockAt(World worldIn, int x, int y, int z) {
        Block block = worldIn.getBlock(x, y - 1, z);
        if (block == Blocks.air) {
            return false;
        }
        return block.isLeaves(worldIn, x, y - 1, z) || (block == this && (worldIn.getBlockMetadata(x, y - 1, z) & 7) == 7 || block.isOpaqueCube() && block.getMaterial().blocksMovement());
    }

    public void onNeighborBlockChange(World worldIn, int x, int y, int z, Block neighbor) {
        this.func_150155_m(worldIn, x, y, z);
    }

    private void func_150155_m(World p_150155_1_, int p_150155_2_, int p_150155_3_, int p_150155_4_) {
        if (!this.canPlaceBlockAt(p_150155_1_, p_150155_2_, p_150155_3_, p_150155_4_)) {
            p_150155_1_.setBlockToAir(p_150155_2_, p_150155_3_, p_150155_4_);
        }
    }

    public void harvestBlock(World worldIn, EntityPlayer player, int x, int y, int z, int meta) {
        super.harvestBlock(worldIn, player, x, y, z, meta);
        worldIn.setBlockToAir(x, y, z);
    }

    public Item getItemDropped(int meta, Random random, int fortune) {
        return null;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess worldIn, int x, int y, int z, int side) {
        return side == 1 || super.shouldSideBeRendered(worldIn, x, y, z, side);
    }

    public int quantityDropped(int meta, int fortune, Random random) {
        return (meta & 7) + 1;
    }

    public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side) {
        int meta = world.getBlockMetadata(x, y, z);
        return (meta & 7) == 7;
    }

    public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
        int meta = world.getBlockMetadata(x, y, z);
        return meta < 7;
    }
}