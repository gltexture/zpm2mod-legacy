package ru.BouH_.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;

import java.util.Random;

public class BlockTorch1 extends BlockTorchBase {

    public BlockTorch1() {
        super();
    }


    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister p_149651_1_) {
        this.blockIcon = p_149651_1_.registerIcon("torch_on");
    }


    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return Item.getItemFromBlock(Blocks.torch);
    }
}
