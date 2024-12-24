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

public class BlockDestroyedWorkBench extends Block {
    @SideOnly(Side.CLIENT)
    private IIcon field_150035_a;
    @SideOnly(Side.CLIENT)
    private IIcon field_150034_b;

    public BlockDestroyedWorkBench() {
        super(Material.wood);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta) {
        return side == 1 ? this.field_150035_a : (side == 0 ? Blocks.planks.getBlockTextureFromSide(side) : (side != 2 && side != 4 ? this.blockIcon : this.field_150034_b));
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister reg) {
        this.blockIcon = reg.registerIcon(Main.MODID + ":ct_side_destroyed");
        this.field_150035_a = reg.registerIcon(Main.MODID + ":ct_top_destroyed");
        this.field_150034_b = reg.registerIcon(Main.MODID + ":ct_front_destroyed");
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return null;
    }

    protected boolean canSilkHarvest() {
        return false;
    }
}
