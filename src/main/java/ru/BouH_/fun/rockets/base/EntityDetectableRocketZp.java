package ru.BouH_.fun.rockets.base;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public abstract class EntityDetectableRocketZp extends EntityRocketOwnable {
    protected EntityDefenceRocketZp homingRocket;
    private int seekPunishment;

    public EntityDetectableRocketZp(World p_i1776_1_) {
        super(p_i1776_1_);
    }

    public EntityDetectableRocketZp(World p_i1778_1_, double p_i1778_2_, double p_i1778_4_, double p_i1778_6_) {
        super(p_i1778_1_, p_i1778_2_, p_i1778_4_, p_i1778_6_);
    }

    public EntityDetectableRocketZp(World p_i1756_1_, EntityLivingBase p_i1756_2_, float speed, float p_i1756_3_) {
        super(p_i1756_1_, p_i1756_2_, speed, p_i1756_3_);
    }

    public boolean equals(String owner) {
        return false;
    }

    public boolean canBeDetected() {
        return this.homingRocket == null || this.homingRocket.isDead;
    }

    public boolean isDetected() {
        return this.homingRocket != null;
    }

    public void setDetected(EntityDefenceRocketZp entityDefenceRocketZp) {
        entityDefenceRocketZp.rocketToDestroy = this;
        this.homingRocket = entityDefenceRocketZp;
    }
}
