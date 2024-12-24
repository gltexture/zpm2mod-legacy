package ru.BouH_.items.tools;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ru.BouH_.blocks.BlockMine;
import ru.BouH_.init.BlocksZp;
import ru.BouH_.misc.MaterialZp;

public class ItemMetalScissors extends Item {

    public boolean onBlockDestroyed(ItemStack p_150894_1_, World p_150894_2_, Block p_150894_3_, int p_150894_4_, int p_150894_5_, int p_150894_6_, EntityLivingBase p_150894_7_) {
        if (p_150894_3_ instanceof BlockMine) {
            p_150894_1_.damageItem(1, p_150894_7_);
            return true;
        }
        if (!this.canItemHarvestBlock(p_150894_3_)) {
            return super.onBlockDestroyed(p_150894_1_, p_150894_2_, p_150894_3_, p_150894_4_, p_150894_5_, p_150894_6_, p_150894_7_);
        } else {
            p_150894_1_.damageItem(p_150894_3_ == BlocksZp.wire_reinforced ? 2 : 1, p_150894_7_);
            return true;
        }
    }

    public boolean canItemHarvestBlock(Block p_150897_1_) {
        return p_150897_1_.getMaterial() == MaterialZp.wire;
    }

    public float getStrVsBlock(ItemStack p_150893_1_, Block p_150893_2_) {
        return p_150893_2_ instanceof BlockMine ? 1.5f : p_150893_2_.getMaterial() == MaterialZp.wire ? 2.0f : 1.0f;
    }
}
