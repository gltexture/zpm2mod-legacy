package ru.BouH_.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import ru.BouH_.Main;

import java.util.Random;

public class BlockConcreteF extends Block {
    @SideOnly(Side.CLIENT)
    private IIcon field_149984_b;
    @SideOnly(Side.CLIENT)
    private IIcon field_149986_M;

    public BlockConcreteF() {
        super(Material.rock);
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return Item.getItemFromBlock(Blocks.cobblestone);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        return p_149691_1_ == 1 ? this.field_149984_b : p_149691_1_ == 2 ? this.field_149986_M : p_149691_1_ == 3 ? this.field_149986_M : p_149691_1_ == 4 ? this.field_149986_M : p_149691_1_ == 5 ? this.field_149986_M : this.field_149984_b;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister p_149651_1_) {
        this.field_149986_M = p_149651_1_.registerIcon(Main.MODID + ":concrete_f");
        this.field_149984_b = p_149651_1_.registerIcon(Main.MODID + ":concrete");
    }

    public int getMobilityFlag() {
        return 2;
    }
}
