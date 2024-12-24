package ru.BouH_.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.init.ItemsZp;

import java.util.ArrayList;

public class BlockScrap extends Block {

    public BlockScrap() {
        super(Material.iron);
    }

    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
        ArrayList<ItemStack> ret = new ArrayList<>();
        if (Main.rand.nextFloat() <= 0.5f) {
            ret.add(new ItemStack(ItemsZp.armature, 1, Main.rand.nextInt(ItemsZp.armature.getMaxDurability())));
        }
        ret.add(new ItemStack(ItemsZp.scrap_material, Main.rand.nextInt(4)));
        return ret;
    }

    public boolean canHarvestBlock(EntityPlayer player, int meta) {
        return player.getHeldItem() != null && (player.getHeldItem().getItem() == ItemsZp.crowbar || player.getHeldItem().getItem() == ItemsZp.wrench);
    }

    protected boolean canSilkHarvest() {
        return false;
    }
}