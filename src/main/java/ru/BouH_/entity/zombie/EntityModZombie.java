package ru.BouH_.entity.zombie;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.world.World;

public class EntityModZombie extends EntityMob {

    public EntityModZombie(World p_i1745_1_) {
        super(p_i1745_1_);
    }

    public void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(48);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.0f);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(0);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(999);
    }

    protected void entityInit() {
        super.entityInit();
    }
}