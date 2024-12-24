package ru.BouH_.items.tools;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.BlockWorkbench;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import ru.BouH_.init.BlocksZp;

public class ItemWrench extends Item {
    public ItemWrench(String string) {
        this.setUnlocalizedName(string);
    }

    public boolean onBlockDestroyed(ItemStack p_150894_1_, World p_150894_2_, Block block, int p_150894_4_, int p_150894_5_, int p_150894_6_, EntityLivingBase p_150894_7_) {
        if (!this.canItemHarvestBlock(block)) {
            return super.onBlockDestroyed(p_150894_1_, p_150894_2_, block, p_150894_4_, p_150894_5_, p_150894_6_, p_150894_7_);
        } else {
            p_150894_1_.damageItem(1, p_150894_7_);
            return true;
        }
    }

    public boolean canItemHarvestBlock(Block block) {
        return (block == BlocksZp.scrap || block instanceof BlockWorkbench || block instanceof BlockFurnace);
    }

    public float getStrVsBlock(ItemStack p_150893_1_, Block block) {
        return block == BlocksZp.scrap ? 6.0f : (block instanceof BlockWorkbench || block instanceof BlockFurnace) ? 2.0f : 1.0f;
    }
}
