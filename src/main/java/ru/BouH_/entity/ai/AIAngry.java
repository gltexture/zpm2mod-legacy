package ru.BouH_.entity.ai;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import ru.BouH_.Main;
import ru.BouH_.entity.zombie.AZombieBase;
import ru.BouH_.gameplay.WorldManager;
import ru.BouH_.utils.EntityUtils;

public class AIAngry extends EntityAIBase {
    protected AZombieBase attacker;
    private int angryCd, angryTimer;

    public AIAngry(AZombieBase creature) {
        super();
        this.attacker = creature;
        this.setMutexBits(0);
    }

    public boolean shouldExecute() {
        return this.attacker.getAttackTarget() != null && !this.attacker.getNavigator().noPath();
    }

    public void resetTask() {
        this.angryCd = 0;
        this.angryTimer = 0;
        this.attacker.setSprinting(false);
    }

    public void updateTask() {
        EntityLivingBase entitylivingbase = this.attacker.getAttackTarget();
        if (WorldManager.is7Night(this.attacker.worldObj)) {
            this.attacker.setSprinting(true);
            return;
        }
        if (this.angryTimer <= 0) {
            this.attacker.setSprinting(false);
        } else {
            this.angryTimer--;
        }
        if (this.angryCd-- <= 0) {
            if (!this.attacker.isSprinting() && Main.rand.nextFloat() <= 1.0E-3D) {
                if (entitylivingbase.getDistanceSq(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ) <= 8 && EntityUtils.canEntitySeeEntity(this.attacker, entitylivingbase, false)) {
                    this.attacker.setSprinting(true);
                    this.attacker.worldObj.playSoundAtEntity(this.attacker, "mob.wolf.growl", 1.5F, 0.8f + Main.rand.nextFloat() * 0.2f);
                    this.angryTimer = Main.rand.nextInt(61) + 80;
                }
            }
            this.angryCd = 400;
        }
    }

    public void startExecuting() {
        super.startExecuting();
    }
}