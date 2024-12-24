package ru.BouH_.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import ru.BouH_.Main;
import ru.BouH_.init.ItemsZp;

import java.util.List;
import java.util.Random;

public class BlockShelf extends Block {
    public static final String[] field_150096_a = new String[]{"empty_bookshelf", "empty_bookshelf2", "empty_bookshelf3"};
    @SideOnly(Side.CLIENT)
    private IIcon field_149984_b;
    @SideOnly(Side.CLIENT)
    private IIcon[] field_150095_b;

    public BlockShelf() {
        super(Material.wood);
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_) {
        if (p_149691_2_ >= 0 && p_149691_2_ < this.field_150095_b.length) {
            return p_149691_1_ == 1 ? this.field_149984_b : p_149691_1_ == 0 ? this.field_149984_b : field_150095_b[p_149691_2_];
        }
        return super.getIcon(p_149691_1_, p_149691_2_);
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return Main.rand.nextFloat() <= 0.05f ? ItemsZp.table : Items.paper;
    }

    protected boolean canSilkHarvest() {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public int damageDropped(int p_149692_1_) {
        return p_149692_1_;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void getSubBlocks(Item p_149666_1_, CreativeTabs p_149666_2_, List p_149666_3_) {
        p_149666_3_.add(new ItemStack(this, 1, 0));
        p_149666_3_.add(new ItemStack(this, 1, 1));
        p_149666_3_.add(new ItemStack(this, 1, 2));
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister p_149651_1_) {
        this.field_150095_b = new IIcon[field_150096_a.length];

        for (int i = 0; i < this.field_150095_b.length; ++i) {
            this.field_150095_b[i] = p_149651_1_.registerIcon(Main.MODID + ":" + field_150096_a[i]);
        }
        this.field_149984_b = p_149651_1_.registerIcon("planks_oak");
    }
}
