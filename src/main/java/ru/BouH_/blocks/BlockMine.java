package ru.BouH_.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockPressurePlate;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.init.ItemsZp;

import java.util.List;
import java.util.Random;

public class BlockMine extends BlockPressurePlate {
    private int entityId = -1;
    @SideOnly(Side.CLIENT)
    private IIcon field_149984_b;
    @SideOnly(Side.CLIENT)
    private IIcon field_149986_M;

    public BlockMine(String p_i45418_1_, Material p_i45418_2_, Sensitivity p_i45418_3_) {
        super(p_i45418_1_, p_i45418_2_, p_i45418_3_);
    }

    @Override
    public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_) {
        if (!p_149670_1_.isRemote) {
            int l = this.getPowerFromMeta(p_149670_1_.getBlockMetadata(p_149670_2_, p_149670_3_, p_149670_4_));
            if (l == 0) {
                this.setStateIfMobInteractsWithPlate(p_149670_1_, p_149670_2_, p_149670_3_, p_149670_4_, l, p_149670_5_.getEntityId());
            }
        }
    }

    @Override
    public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_) {
        if (!p_149690_1_.isRemote && !p_149690_1_.restoringBlockSnapshots) {
            p_149690_1_.newExplosion(null, p_149690_2_, p_149690_3_, p_149690_4_, 3.0f, true, false);
        }
    }

    public void dropBlockAsItem(World worldIn, int x, int y, int z) {
        super.dropBlockAsItem(worldIn, x, y, z, new ItemStack(ItemsZp.scrap_material, Main.rand.nextBoolean() ? 5 : 6));
    }

    @Override
    public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_) {
        if (!p_149674_1_.isRemote) {
            int l = this.getPowerFromMeta(p_149674_1_.getBlockMetadata(p_149674_2_, p_149674_3_, p_149674_4_));
            if (l > 0) {
                if (p_149674_1_.getEntityByID(this.entityId) == null) {
                    this.entityId = -1;
                } else if (!p_149674_1_.getEntityByID(this.entityId).isEntityAlive()) {
                    l = 1;
                }
                this.setStateIfMobInteractsWithPlate(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_, l, this.entityId);
            }
        }
    }

    public boolean canPlaceBlockAt(World worldIn, int x, int y, int z) {
        return World.doesBlockHaveSolidTopSurface(worldIn, x, y - 1, z);
    }

    @Override
    protected boolean canSilkHarvest() {
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected int getPlateState(World p_150065_1_, int p_150065_2_, int p_150065_3_, int p_150065_4_) {
        List<EntityLivingBase> list = p_150065_1_.getEntitiesWithinAABB(EntityLivingBase.class, this.getSensitiveAABB(p_150065_2_, p_150065_3_, p_150065_4_));
        if (list != null && !list.isEmpty()) {
            for (Entity o : list) {
                if (!o.doesEntityNotTriggerPressurePlate() && o.isEntityAlive()) {
                    return 1;
                }
            }
        }
        return 0;
    }

    @Override
    public int tickRate(World p_149738_1_) {
        return 5;
    }

    protected void setStateIfMobInteractsWithPlate(World p_150062_1_, int p_150062_2_, int p_150062_3_, int p_150062_4_, int p_150062_5_, int id) {
        int i1 = this.getPlateState(p_150062_1_, p_150062_2_, p_150062_3_, p_150062_4_);
        boolean flag = p_150062_5_ > 0;
        this.entityId = id;
        boolean flag1 = i1 > 0;
        if (p_150062_5_ != i1) {
            p_150062_1_.setBlockMetadataWithNotify(p_150062_2_, p_150062_3_, p_150062_4_, this.getMetaFromPower(i1), 2);
            this.updateNeighbors(p_150062_1_, p_150062_2_, p_150062_3_, p_150062_4_);
            p_150062_1_.markBlockRangeForRenderUpdate(p_150062_2_, p_150062_3_, p_150062_4_, p_150062_2_, p_150062_3_, p_150062_4_);
        }

        if (!flag1 && flag) {
            p_150062_1_.playSoundEffect((double) p_150062_2_ + 0.5D, (double) p_150062_3_ + 0.1D, (double) p_150062_4_ + 0.5D, "random.click", 0.3F, 0.5F);
            if (!p_150062_1_.isRemote && this.entityId != -1) {
                p_150062_1_.newExplosion(null, p_150062_2_, p_150062_3_, p_150062_4_, 3.0f, true, false);
                p_150062_1_.setBlockToAir(p_150062_2_, p_150062_3_, p_150062_4_);
            } else {
                this.entityId = -1;
            }
        } else if (flag1 && !flag) {
            p_150062_1_.playSoundEffect((double) p_150062_2_ + 0.5D, (double) p_150062_3_ + 0.1D, (double) p_150062_4_ + 0.5D, "random.click", 0.3F, 0.6F);
        }

        if (flag1) {
            p_150062_1_.scheduleBlockUpdate(p_150062_2_, p_150062_3_, p_150062_4_, this, this.tickRate(p_150062_1_));
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister p_149651_1_) {
        this.field_149986_M = p_149651_1_.registerIcon(Main.MODID + ":scrap");
        this.field_149984_b = p_149651_1_.registerIcon(Main.MODID + ":mine");
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return p_149691_1_ == 1 ? this.field_149984_b : p_149691_1_ == 2 ? this.field_149986_M : p_149691_1_ == 3 ? this.field_149986_M : p_149691_1_ == 4 ? this.field_149986_M : p_149691_1_ == 5 ? this.field_149986_M : this.field_149984_b;
    }
}
