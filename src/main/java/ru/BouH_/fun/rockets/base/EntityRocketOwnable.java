package ru.BouH_.fun.rockets.base;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import ru.BouH_.entity.projectile.EntityRocketZPBase;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.packets.fun.PacketRocketOwner;

public abstract class EntityRocketOwnable extends EntityRocketZPBase implements IOwner {
    private String owner = "";

    public EntityRocketOwnable(World p_i1776_1_) {
        super(p_i1776_1_);
    }

    public EntityRocketOwnable(World p_i1777_1_, EntityLivingBase p_i1777_2_, float speed, float inac) {
        super(p_i1777_1_, p_i1777_2_, speed, inac);
    }

    public EntityRocketOwnable(World p_i1778_1_, double p_i1778_2_, double p_i1778_4_, double p_i1778_6_) {
        super(p_i1778_1_, p_i1778_2_, p_i1778_4_, p_i1778_6_);
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        if (!this.worldObj.isRemote) {
            EntityPlayerMP entityPlayer = (EntityPlayerMP) this.worldObj.getPlayerEntityByName(owner);
            if (entityPlayer != null) {
                NetworkHandler.NETWORK.sendTo(new PacketRocketOwner(this.getEntityId()), entityPlayer);
            }
        }
        this.owner = owner;
    }

    public boolean equals(String owner) {
        return !owner.isEmpty() && !this.getOwner().isEmpty() && this.getOwner().equals(owner);
    }
}
