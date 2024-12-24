package ru.BouH_.blocks.gas;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import ru.BouH_.Main;

import java.util.Random;

public abstract class BlockGasBase extends Block {
    public BlockGasBase() {
        super(Material.portal);
    }

    public boolean isOpaqueCube() {
        return false;
    }

    public int getMobilityFlag() {
        return 2;
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_) {
        return null;
    }

    public int getRenderType() {
        return -1;
    }

    public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
        return false;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return null;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        this.blockIcon = reg.registerIcon(Main.MODID + ":admin");
    }

    protected boolean canSilkHarvest() {
        return false;
    }

    public boolean canStopRayTrace(int p_149678_1_, boolean p_149678_2_) {
        return false;
    }
}
