package ru.BouH_.items.block;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBlockLayer extends ItemBlockWithMetadata {
    private final Block block;

    public ItemBlockLayer(Block p_i45354_1_) {
        super(p_i45354_1_, p_i45354_1_);
        this.block = p_i45354_1_;
    }

    public boolean onItemUse(ItemStack p_77648_1_, EntityPlayer p_77648_2_, World p_77648_3_, int p_77648_4_, int p_77648_5_, int p_77648_6_, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
        if (p_77648_1_.stackSize == 0) {
            return false;
        } else if (!p_77648_2_.canPlayerEdit(p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_1_)) {
            return false;
        } else {
            Block block = p_77648_3_.getBlock(p_77648_4_, p_77648_5_, p_77648_6_);

            if (block == this.block) {
                int i1 = p_77648_3_.getBlockMetadata(p_77648_4_, p_77648_5_, p_77648_6_);
                int j1 = i1 & 7;

                if (j1 <= 6 && p_77648_3_.checkNoEntityCollision(this.blockInstance.getCollisionBoundingBoxFromPool(p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_)) && p_77648_3_.setBlockMetadataWithNotify(p_77648_4_, p_77648_5_, p_77648_6_, j1 + 1 | i1 & -8, 2)) {
                    p_77648_3_.playSoundEffect((float) p_77648_4_ + 0.5F, (float) p_77648_5_ + 0.5F, (float) p_77648_6_ + 0.5F, this.blockInstance.stepSound.getPlaceSound(), (this.blockInstance.stepSound.getVolume() + 1.0F) / 2.0F, this.blockInstance.stepSound.getFrequency() * 0.8F);
                    --p_77648_1_.stackSize;
                    return true;
                }
            }

            return super.onItemUse(p_77648_1_, p_77648_2_, p_77648_3_, p_77648_4_, p_77648_5_, p_77648_6_, p_77648_7_, p_77648_8_, p_77648_9_, p_77648_10_);
        }
    }
}