package ru.BouH_.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockPumpkin;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import ru.BouH_.tiles.TilePumpkin;

import java.util.Random;

public class BlockPumpkinNew extends BlockPumpkin implements ITileEntityProvider {
    @SideOnly(Side.CLIENT)
    private IIcon field_149984_b;
    @SideOnly(Side.CLIENT)
    private IIcon field_149986_M;

    public BlockPumpkinNew() {
        super(true);
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return Item.getItemFromBlock(Blocks.lit_pumpkin);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return side == 1 ? this.field_149984_b : (side == 0 ? this.field_149984_b : (meta == 2 && side == 2 ? this.field_149986_M : (meta == 3 && side == 5 ? this.field_149986_M : (meta == 0 && side == 3 ? this.field_149986_M : (meta == 1 && side == 4 ? this.field_149986_M : this.blockIcon)))));
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        this.field_149986_M = reg.registerIcon("pumpkin_face_on");
        this.field_149984_b = reg.registerIcon("pumpkin_top");
        this.blockIcon = reg.registerIcon("pumpkin_side");
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TilePumpkin();
    }
}
