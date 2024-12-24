package ru.BouH_.items.melee;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import ru.BouH_.Main;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.packets.particles.ParticleSpark;

public class ItemMjolnir extends ItemPickaxe {
    public ItemMjolnir(ToolMaterial p_i45327_1_) {
        super(p_i45327_1_);
    }

    public boolean hitEntity(ItemStack stack, EntityLivingBase p_77644_2_, EntityLivingBase p_77644_3_) {
        p_77644_2_.addPotionEffect(new PotionEffect(2, 100));
        p_77644_2_.worldObj.playSoundAtEntity(p_77644_2_, Main.MODID + ":electro", 1.0F, 0.9f + Main.rand.nextFloat() * 0.1f);
        NetworkHandler.NETWORK.sendToAllAround(new ParticleSpark(p_77644_2_.getEntityId()), new NetworkRegistry.TargetPoint(p_77644_2_.dimension, p_77644_2_.posX, p_77644_2_.posY, p_77644_2_.posZ, 256));
        return super.hitEntity(stack, p_77644_2_, p_77644_3_);
    }
}
