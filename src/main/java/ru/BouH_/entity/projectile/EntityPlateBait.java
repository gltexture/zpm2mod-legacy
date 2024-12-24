package ru.BouH_.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityPlateBait extends EntityPlate {
    public EntityPlateBait(World p_i1776_1_) {
        super(p_i1776_1_);
    }

    public EntityPlateBait(World p_i1778_1_, double p_i1778_2_, double p_i1778_4_, double p_i1778_6_) {
        super(p_i1778_1_, p_i1778_2_, p_i1778_4_, p_i1778_6_);
    }

    public EntityPlateBait(World p_i1756_1_, EntityLivingBase p_i1756_2_, float p_i1756_3_) {
        this(p_i1756_1_, p_i1756_2_, p_i1756_3_, 10.0f);
    }

    public EntityPlateBait(World p_i1756_1_, EntityLivingBase p_i1756_2_, float speed, float inac) {
        super(p_i1756_1_, p_i1756_2_, speed, inac);
    }

    @Override
    protected void onImpact(MovingObjectPosition movingobjectposition) {
        super.onImpact(movingobjectposition);
        if (!this.worldObj.isRemote) {
            EntityItem entityitem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(Items.rotten_flesh));
            entityitem.delayBeforeCanPickup = 10;
            this.worldObj.spawnEntityInWorld(entityitem);
        }
    }
}
