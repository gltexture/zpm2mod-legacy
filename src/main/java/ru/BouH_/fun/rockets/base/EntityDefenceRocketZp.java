package ru.BouH_.fun.rockets.base;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import ru.BouH_.entity.projectile.EntityRocketZPBase;

public abstract class EntityDefenceRocketZp extends EntityRocketOwnable {
    public EntityRocketZPBase rocketToDestroy;
    public int seekPunishment;

    public EntityDefenceRocketZp(World p_i1776_1_) {
        super(p_i1776_1_);
    }

    public EntityDefenceRocketZp(World p_i1778_1_, double p_i1778_2_, double p_i1778_4_, double p_i1778_6_) {
        super(p_i1778_1_, p_i1778_2_, p_i1778_4_, p_i1778_6_);
    }

    public EntityDefenceRocketZp(World p_i1756_1_, EntityLivingBase p_i1756_2_, float speed, float p_i1756_3_) {
        super(p_i1756_1_, p_i1756_2_, speed, p_i1756_3_);
    }

    public abstract int getFinalPunishment();

    public void punishSeek() {
        this.seekPunishment = this.getFinalPunishment();
    }
}
