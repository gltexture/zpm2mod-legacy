package ru.BouH_.fun.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockStone;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.fun.tiles.TileC300Block;
import ru.BouH_.fun.tiles.TileTacticalBlock;

import java.util.Random;

public class BlockC300 extends BlockStone implements ITileEntityProvider {
    @SideOnly(Side.CLIENT)
    private IIcon field_149984_b;

    @SideOnly(Side.CLIENT)
    private IIcon field_149986_M;

    public BlockC300() {
        super();
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return null;
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return p_149691_1_ == 1 ? this.field_149986_M : this.field_149984_b;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister p_149651_1_) {
        this.field_149986_M = p_149651_1_.registerIcon(Main.MODID + ":c300_0");
        this.field_149984_b = p_149651_1_.registerIcon(Main.MODID + ":c300_1");
    }

    public void onBlockPlacedBy(World worldIn, int x, int y, int z, EntityLivingBase placer, ItemStack itemIn) {
        if (!worldIn.isRemote && placer != null) {
            if (placer instanceof EntityPlayer) {
                EntityPlayer entityPlayer = (EntityPlayer) placer;
                ((TileTacticalBlock) worldIn.getTileEntity(x, y, z)).setOwner(entityPlayer.getDisplayName());
            }
        }
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileC300Block();
    }
}
