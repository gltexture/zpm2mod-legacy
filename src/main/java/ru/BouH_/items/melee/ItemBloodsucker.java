package ru.BouH_.items.melee;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import ru.BouH_.Main;

public class ItemBloodsucker extends ItemSword {
    public ItemBloodsucker(ToolMaterial p_i45327_1_) {
        super(p_i45327_1_);
    }

    public boolean hitEntity(ItemStack stack, EntityLivingBase p_77644_2_, EntityLivingBase p_77644_3_) {
        p_77644_2_.worldObj.playSoundAtEntity(p_77644_2_, Main.MODID + ":saw", 1.0F, 0.9f + Main.rand.nextFloat() * 0.1f);
        return super.hitEntity(stack, p_77644_2_, p_77644_3_);
    }
}
