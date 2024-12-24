package ru.BouH_.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import ru.BouH_.Main;

import java.util.Random;

public class BlockLamp extends BlockGlass {
    public BlockLamp(boolean p_i45408_2_) {
        super(Material.glass, p_i45408_2_);
    }

    public int quantityDropped(Random random) {
        return 1;
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return p_149650_2_.nextFloat() <= 0.1f ? Items.redstone : p_149650_2_.nextFloat() <= 0.05f ? Items.glowstone_dust : null;
    }

    protected boolean canSilkHarvest() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister p_149651_1_) {
        this.blockIcon = p_149651_1_.registerIcon(Main.MODID + ":" + this.getUnlocalizedName().substring(5));
    }
}
