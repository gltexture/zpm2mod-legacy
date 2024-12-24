package ru.BouH_.fun.tiles;

import net.minecraft.util.AxisAlignedBB;
import ru.BouH_.Main;
import ru.BouH_.fun.rockets.EntityPancirRocket;
import ru.BouH_.fun.rockets.base.EntityDetectableRocketZp;

import java.util.List;

public class TilePancirBlock extends TileTacticalBlock {
    private int ticks;

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity() {
        if (!this.worldObj.isRemote) {
            if (this.ticks++ >= 14) {
                List<EntityDetectableRocketZp> list = this.worldObj.getEntitiesWithinAABB(EntityDetectableRocketZp.class, AxisAlignedBB.getBoundingBox(this.xCoord - 100, this.yCoord - 256, this.zCoord - 100, this.xCoord + 100, this.yCoord + 256, this.zCoord + 100));
                if (!list.isEmpty()) {
                    for (EntityDetectableRocketZp rocketDetectable : list) {
                        if (EntityPancirRocket.rockets.contains(rocketDetectable.getClass())) {
                            if (!rocketDetectable.getOwner().equals(this.owner)) {
                                if (rocketDetectable.canBeDetected() && !rocketDetectable.isDead) {
                                    EntityPancirRocket entityPancirRocket = new EntityPancirRocket(this.worldObj, this.xCoord + 0.5f, this.yCoord + 3, this.zCoord + 0.5f);
                                    rocketDetectable.setDetected(entityPancirRocket);
                                    entityPancirRocket.rocketToDestroy = rocketDetectable;
                                    this.worldObj.playSoundAtEntity(entityPancirRocket, Main.MODID + ":rpg_s", 5.0f, Main.rand.nextFloat() * 0.2f + 1);
                                    entityPancirRocket.setThrowableHeading(0, this.yCoord + 2, 0, 2.5f, 1.0f);
                                    this.worldObj.spawnEntityInWorld(entityPancirRocket);
                                    entityPancirRocket.setOwner(this.owner);
                                    this.usages += 1;
                                }
                            }
                        }
                    }
                }
                if (this.usages >= 14) {
                    this.worldObj.breakBlock(this.xCoord, this.yCoord, this.zCoord, false);
                }
                this.ticks = 0;
            }
        }
    }
}

