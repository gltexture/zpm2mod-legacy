package ru.BouH_.entity.ai;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.MathHelper;
import ru.BouH_.entity.zombie.AZombieBase;

import java.util.UUID;

public class AISwimming extends EntityAIBase {
    private final AZombieBase theEntity;
    private final UUID swimmingSpeedBoostModifierUUID = UUID.fromString("e841a832-ea56-40d0-b6b9-7a20cb4271e1");
    private final AttributeModifier swimmingSpeedBoostModifier;
    private final boolean shouldBeDrowned;

    public AISwimming(AZombieBase p_i1624_1_, float swimmingBoost, boolean shouldBeDrowned) {
        this.theEntity = p_i1624_1_;
        this.swimmingSpeedBoostModifier = (new AttributeModifier(swimmingSpeedBoostModifierUUID, "Swimming speed boost", swimmingBoost, 2)).setSaved(false);
        this.shouldBeDrowned = shouldBeDrowned;
        this.setMutexBits(0);
        p_i1624_1_.getNavigator().setCanSwim(true);
    }

    public boolean shouldExecute() {
        if (this.theEntity.isInWater() || this.theEntity.handleLavaMovement()) {
            this.updateSpeed(true);
            return true;
        }
        this.updateSpeed(false);
        return false;
    }

    public void updateTask() {
        boolean flag = !this.shouldBeDrowned;
        AZombieBase ent = this.theEntity;
        if (ent.getAttackTarget() != null) {
            int plY = (int) Math.round(ent.posY);
            int atX = MathHelper.floor_double(ent.getAttackTarget().posX);
            int atY = (int) ent.getAttackTarget().posY;
            int atZ = MathHelper.floor_double(ent.getAttackTarget().posZ);
            if (this.shouldBeDrowned) {
                if (atY > plY) {
                    flag = true;
                }
            } else {
                if (atY < plY && this.theEntity.getDistance(atX, plY, atZ) <= 3) {
                    flag = false;
                }
            }
        }
        if (flag) {
            this.theEntity.getJumpHelper().setJumping();
        }
    }

    private void updateSpeed(boolean isInWater) {
        IAttributeInstance iattributeinstance = this.theEntity.getEntityAttribute(SharedMonsterAttributes.movementSpeed);

        if (iattributeinstance.getModifier(this.swimmingSpeedBoostModifierUUID) != null) {
            iattributeinstance.removeModifier(this.swimmingSpeedBoostModifier);
        }

        if (isInWater) {
            iattributeinstance.applyModifier(this.swimmingSpeedBoostModifier);
        }
    }
}