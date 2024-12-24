package ru.BouH_.items.melee;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import ru.BouH_.init.BlocksZp;

public class ItemTorch extends ItemSword {
    public ItemTorch(ToolMaterial p_i45327_1_) {
        super(p_i45327_1_);
    }

    public float getStrVsBlock(ItemStack p_150893_1_, Block p_150893_2_) {
        Material material = p_150893_2_.getMaterial();
        return p_150893_2_ == Blocks.web ? 15 : p_150893_2_ == BlocksZp.scrap ? 3 : material != Material.plants && material != Material.vine && material != Material.coral && material != Material.leaves && material != Material.gourd ? 1.0F : 1.5F;
    }

    public boolean canItemHarvestBlock(Block p_150897_1_) {
        return p_150897_1_ == Blocks.web || p_150897_1_ == BlocksZp.scrap;
    }

    public boolean hitEntity(ItemStack stack, EntityLivingBase p_77644_2_, EntityLivingBase p_77644_3_) {
        p_77644_2_.setFire(3);
        return super.hitEntity(stack, p_77644_2_, p_77644_3_);
    }
}
