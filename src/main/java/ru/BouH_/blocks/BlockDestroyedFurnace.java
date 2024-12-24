package ru.BouH_.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import ru.BouH_.Main;

import java.util.Random;

public class BlockDestroyedFurnace extends Block {
    @SideOnly(Side.CLIENT)
    private IIcon iconTop;
    @SideOnly(Side.CLIENT)
    private IIcon iconFront;

    public BlockDestroyedFurnace() {
        super(Material.rock);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return side == 1 ? this.iconTop : (side == 0 ? this.iconTop : (side != meta ? this.blockIcon : this.iconFront));
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        this.blockIcon = reg.registerIcon("furnace_side");
        this.iconFront = reg.registerIcon(Main.MODID + ":furnace_front_destroyed");
        this.iconTop = reg.registerIcon("furnace_top");
    }

    public void onBlockPlacedBy(World worldIn, int x, int y, int z, EntityLivingBase placer, ItemStack itemIn) {
        int l = MathHelper.floor_double((double) (placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        if (l == 0) {
            worldIn.setBlockMetadataWithNotify(x, y, z, 2, 2);
        }

        if (l == 1) {
            worldIn.setBlockMetadataWithNotify(x, y, z, 5, 2);
        }

        if (l == 2) {
            worldIn.setBlockMetadataWithNotify(x, y, z, 3, 2);
        }

        if (l == 3) {
            worldIn.setBlockMetadataWithNotify(x, y, z, 4, 2);
        }
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return null;
    }

    protected boolean canSilkHarvest() {
        return false;
    }
}
