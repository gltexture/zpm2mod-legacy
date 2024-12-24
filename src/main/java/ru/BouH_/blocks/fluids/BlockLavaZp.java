package ru.BouH_.blocks.fluids;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import ru.BouH_.Main;
import ru.BouH_.tiles.TileLava;

import java.util.Random;

public class BlockLavaZp extends BlockFluidClassic implements ITileEntityProvider {

    @SideOnly(Side.CLIENT)
    protected IIcon stillIcon;
    @SideOnly(Side.CLIENT)
    protected IIcon flowingIcon;

    public BlockLavaZp(Fluid fluid) {
        super(fluid, Material.lava);
        this.setQuantaPerBlock(4);
        this.setTickRandomly(true);
    }

    public void onEntityCollidedWithBlock(World worldIn, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity entityIn) {
        if (!worldIn.isRemote) {
            entityIn.setFire(5);
        }
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return (side == 0 || side == 1) ? stillIcon : flowingIcon;
    }

    @Override
    public int getLightValue(IBlockAccess world, int x, int y, int z) {
        return 15;
    }

    @Override
    public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
        if (world.getBlock(x, y, z).getMaterial().isLiquid()) {
            return false;
        }
        return super.canDisplace(world, x, y, z);
    }

    @Override
    public boolean displaceIfPossible(World world, int x, int y, int z) {
        if (world.getBlock(x, y, z).getMaterial().isLiquid()) {
            return false;
        }
        return super.displaceIfPossible(world, x, y, z);
    }

    public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_) {
        super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
        this.func_149805_n(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
    }

    public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_) {
        super.onNeighborBlockChange(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_5_);
        this.func_149805_n(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_);
    }

    public void updateTick(World worldIn, int x, int y, int z, Random random) {
        super.updateTick(worldIn, x, y, z, random);
        if (this.blockMaterial == Material.lava) {
            int l = random.nextInt(4);
            int i1;

            for (i1 = 0; i1 < l; ++i1) {
                x += random.nextInt(4) - 1;
                ++y;
                z += random.nextInt(4) - 1;
                Block block = worldIn.getBlock(x, y, z);

                if (block.getMaterial() == Material.air) {
                    if (this.isFlammable(worldIn, x - 1, y, z) || this.isFlammable(worldIn, x + 1, y, z) || this.isFlammable(worldIn, x, y, z - 1) || this.isFlammable(worldIn, x, y, z + 1) || this.isFlammable(worldIn, x, y - 1, z) || this.isFlammable(worldIn, x, y + 1, z)) {
                        worldIn.setBlock(x, y, z, Blocks.fire);
                        return;
                    }
                } else if (block.getMaterial().blocksMovement()) {
                    return;
                }
            }

            if (l == 0) {
                i1 = x;
                int k1 = z;

                for (int j1 = 0; j1 < 4; ++j1) {
                    x = i1 + random.nextInt(4) - 1;
                    z = k1 + random.nextInt(4) - 1;

                    if (worldIn.isAirBlock(x, y + 1, z) && this.isFlammable(worldIn, x, y, z)) {
                        worldIn.setBlock(x, y + 1, z, Blocks.fire);
                    }
                }
            }
        }
    }

    private boolean isFlammable(World p_149817_1_, int p_149817_2_, int p_149817_3_, int p_149817_4_) {
        return p_149817_1_.getBlock(p_149817_2_, p_149817_3_, p_149817_4_).getMaterial().getCanBurn();
    }

    private void func_149805_n(World p_149805_1_, int x, int y, int z) {
        if (p_149805_1_.getBlock(x, y, z) == this) {
            if (p_149805_1_.getBlock(x - 1, y, z).getMaterial() == Material.water || p_149805_1_.getBlock(x + 1, y, z).getMaterial() == Material.water || p_149805_1_.getBlock(x, y, z - 1).getMaterial() == Material.water || p_149805_1_.getBlock(x, y, z + 1).getMaterial() == Material.water) {
                p_149805_1_.setBlock(x, y, z, Blocks.cobblestone);
                this.func_149799_m(p_149805_1_, x, y, z);
            } else if (p_149805_1_.getBlock(x, y - 1, z).getMaterial() == Material.water) {
                p_149805_1_.setBlock(x, y - 1, z, Blocks.cobblestone);
                this.func_149799_m(p_149805_1_, x, y - 1, z);
            } else if (p_149805_1_.getBlock(x, y + 1, z).getMaterial() == Material.water) {
                int l = p_149805_1_.getBlockMetadata(x, y, z);
                if (l == 0) {
                    if (Main.rand.nextFloat() <= 0.3) {
                        p_149805_1_.setBlock(x, y, z, Blocks.obsidian);
                    } else {
                        p_149805_1_.setBlock(x, y, z, Blocks.stone);
                    }
                } else {
                    p_149805_1_.setBlockToAir(x, y + 1, z);
                    p_149805_1_.setBlockToAir(x, y, z);
                }
                this.func_149799_m(p_149805_1_, x, y, z);
            }
        }
    }

    protected void func_149799_m(World p_149799_1_, int p_149799_2_, int p_149799_3_, int p_149799_4_) {
        p_149799_1_.playSoundEffect((float) p_149799_2_ + 0.5F, (float) p_149799_3_ + 0.5F, (float) p_149799_4_ + 0.5F, "random.fizz", 0.5F, 2.6F + (p_149799_1_.rand.nextFloat() - p_149799_1_.rand.nextFloat()) * 0.8F);

        for (int l = 0; l < 8; ++l) {
            p_149799_1_.spawnParticle("largesmoke", (double) p_149799_2_ + Math.random(), (double) p_149799_3_ + 1.2D, (double) p_149799_4_ + Math.random(), 0.0D, 0.0D, 0.0D);
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister register) {
        stillIcon = register.registerIcon("lava_still");
        flowingIcon = register.registerIcon("lava_flow");
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileLava();
    }
}
