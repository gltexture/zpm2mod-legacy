package ru.BouH_.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityOwnable;
import net.minecraft.entity.ai.EntityAITarget;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.Team;
import ru.BouH_.utils.EntityUtils;

public abstract class AITargetZp extends EntityAITarget {

    public AITargetZp(EntityCreature p_i1669_1_, boolean p_i1669_2_) {
        super(p_i1669_1_, p_i1669_2_);
    }

    public AITargetZp(EntityCreature p_i1670_1_, boolean p_i1670_2_, boolean p_i1670_3_) {
        super(p_i1670_1_, p_i1670_2_, p_i1670_3_);
    }

    protected boolean isSuitableTarget(EntityLivingBase target, boolean checkSight) {
        if (target == null) {
            return false;
        } else if (target == this.taskOwner) {
            return false;
        } else if (!target.isEntityAlive()) {
            return false;
        } else if (!this.taskOwner.canAttackClass(target.getClass())) {
            return false;
        } else {
            Team team = this.taskOwner.getTeam();
            Team team1 = target.getTeam();
            if (team != null && team1 == team) {
                return false;
            } else {
                if (this.taskOwner instanceof IEntityOwnable && !((IEntityOwnable) this.taskOwner).func_152113_b().isEmpty()) {
                    if (target instanceof IEntityOwnable && ((IEntityOwnable) this.taskOwner).func_152113_b().equals(((IEntityOwnable) target).func_152113_b())) {
                        return false;
                    }
                    if (target == ((IEntityOwnable) this.taskOwner).getOwner()) {
                        return false;
                    }
                } else if (target instanceof EntityPlayer && ((EntityPlayer) target).capabilities.isCreativeMode) {
                    return false;
                }
                return !(target.isInvisible() && this.taskOwner.getDistanceToEntity(target) > 6) && (!checkSight || EntityUtils.canEntitySeeEntity(this.taskOwner, target, false));
            }
        }
    }
}
