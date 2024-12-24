package ru.BouH_.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockTorch;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import java.util.Random;

public class BlockTorch5 extends BlockTorch {
    public BlockTorch5() {
        super();
    }

    protected boolean canSilkHarvest() {
        return false;
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return null;
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_) {
    }

    public void onEntityCollidedWithBlock(World worldIn, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity entityIn) {
    }
}
