package ru.BouH_.items.misc;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTNT;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemMatches extends ItemFlintAndSteel {

    public ItemMatches(String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);
    }

    public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
        Block block = p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_);
        if (block == Blocks.tnt) {
            if (!p_77648_3_.isRemote) {
                BlockTNT blockTNT = (BlockTNT) block;
                p_77648_3_.setBlockToAir(p_77648_4_, p_77648_5_, p_77648_6_);
                blockTNT.func_150114_a(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, 1, p_77648_2_);
            }
            return true;
        } else {
            return super.onItemUse(p_77648_1_, p_77648_2_, p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_);
        }
    }
}
