package ru.BouH_.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.init.BlocksZp;
import ru.BouH_.misc.DamageSourceZp;
import ru.BouH_.misc.MaterialZp;
import ru.BouH_.tiles.TileBlockWithDamage;

public class BlockWire extends Block implements ITileEntityProvider {
    private final boolean isDamaged;

    public BlockWire(boolean isDamaged) {
        super(MaterialZp.wire);
        this.isDamaged = isDamaged;
    }

    public void onEntityCollidedWithBlock(World worldIn, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity entityIn) {
        if (!worldIn.isRemote) {
            if (entityIn instanceof EntityLivingBase) {
                if (this.blockHardness >= 0) {
                    TileBlockWithDamage tileBlockWithDamage = ((TileBlockWithDamage) worldIn.getTileEntity(p_149670_2_, p_149670_3_, p_149670_4_));
                    if (tileBlockWithDamage.getDamage() >= this.blockHardness * 70.0f) {
                        if (this.isDamaged) {
                            worldIn.breakBlock(p_149670_2_, p_149670_3_, p_149670_4_, false);
                        } else {
                            worldIn.setBlock(p_149670_2_, p_149670_3_, p_149670_4_, BlocksZp.wire_stage2);
                        }
                        entityIn.worldObj.playSoundAtEntity(entityIn, "random.break", 1.0f, 1.0f + Main.rand.nextFloat() * 0.2f);
                    } else {
                        tileBlockWithDamage.addDamage();
                    }
                }
                entityIn.attackEntityFrom(DamageSourceZp.wire, Main.rand.nextBoolean() ? 1 : 2);
            } else if (entityIn instanceof EntityFallingBlock) {
                entityIn.entityDropItem(new ItemStack(((EntityFallingBlock) entityIn).getBlock()), 0);
                entityIn.setDead();
            }
        }
        if (entityIn.motionY >= -0.25f) {
            float f1 = entityIn.fallDistance;
            entityIn.setInWeb();
            entityIn.fallDistance = f1;
        }
    }

    public boolean isPassable(IBlockAccess worldIn, int x, int y, int z) {
        return true;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getMobilityFlag() {
        return 2;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
        return null;
    }

    @Override
    public int getRenderType() {
        return 1;
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
    protected boolean canSilkHarvest() {
        return false;
    }

    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileBlockWithDamage();
    }
}

