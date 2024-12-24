package ru.BouH_.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import ru.BouH_.Main;

import java.util.Random;

public class BlockArmorGlass extends BlockBreakable {
    public BlockArmorGlass() {
        super(Main.MODID + ":armored_glass", Material.glass, false);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    public int quantityDropped(Random random) {
        return 0;
    }

    @SideOnly(Side.CLIENT)
    public int getRenderBlockPass() {
        return 1;
    }

    public boolean renderAsNormalBlock() {
        return false;
    }

    protected boolean canSilkHarvest() {
        return false;
    }
}
