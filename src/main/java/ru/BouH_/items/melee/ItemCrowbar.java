package ru.BouH_.items.melee;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.world.World;
import ru.BouH_.init.BlocksZp;

public class ItemCrowbar extends ItemSword {
    public ItemCrowbar(Item.ToolMaterial p_i45327_1_) {
        super(p_i45327_1_);
    }

    public float getStrVsBlock(ItemStack p_150893_1_, Block p_150893_2_) {
        Material material = p_150893_2_.getMaterial();
        return p_150893_2_ == BlocksZp.scrap ? 5.25f : material != Material.plants && material != Material.vine && material != Material.coral && material != Material.leaves && material != Material.gourd ? 1.0F : 1.5F;
    }

    public boolean onBlockDestroyed(ItemStack stack, World worldIn, Block blockIn, int p_150894_4_, int p_150894_5_, int p_150894_6_, EntityLivingBase p_150894_7_) {
        if (blockIn == BlocksZp.scrap) {
            stack.damageItem(5, p_150894_7_);
        } else if ((double) blockIn.getBlockHardness(worldIn, p_150894_4_, p_150894_5_, p_150894_6_) != 0.0D) {
            stack.damageItem(2, p_150894_7_);
        }
        return true;
    }

    public boolean canItemHarvestBlock(Block p_150897_1_) {
        return p_150897_1_ == Blocks.web || p_150897_1_ == BlocksZp.scrap;
    }
}
