package ru.BouH_.items.misc;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;

public class ItemFluidBucket extends ItemBucket {
    public ItemFluidBucket(Block p_i45331_1_, String name) {
        super(p_i45331_1_);
        this.setUnlocalizedName(name);
        this.setContainerItem(Items.bucket);
    }
}
