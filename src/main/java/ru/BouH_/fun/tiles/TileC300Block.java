package ru.BouH_.fun.tiles;

import net.minecraft.util.AxisAlignedBB;
import ru.BouH_.Main;
import ru.BouH_.fun.rockets.EntityC300Rocket;
import ru.BouH_.fun.rockets.base.EntityDetectableRocketZp;

import java.util.List;

public class TileC300Block extends TileTacticalBlock {
    private int ticks;

    @Override
    @SuppressWarnings("unchecked")
    public void updateEntity() {
        if (!this.worldObj.isRemote) {
            if (this.ticks++ >= 5) {
                List<EntityDetectableRocketZp> list = this.worldObj.getEntitiesWithinAABB(EntityDetectableRocketZp.class, AxisAlignedBB.getBoundingBox(this.xCoord - 100, this.yCoord - 256, this.zCoord - 100, this.xCoord + 100, this.yCoord + 256, this.zCoord + 100));
                if (!list.isEmpty()) {
                    for (EntityDetectableRocketZp rocketDetectable : list) {
                        if (EntityC300Rocket.rockets.contains(rocketDetectable.getClass())) {
                            if (!rocketDetectable.getOwner().equals(this.owner)) {
                                if (rocketDetectable.canBeDetected() && !rocketDetectable.isDead) {
                                    EntityC300Rocket entityC300Rocket = new EntityC300Rocket(this.worldObj, this.xCoord + 0.5f, this.yCoord + 3, this.zCoord + 0.5f);
                                    rocketDetectable.setDetected(entityC300Rocket);
                                    entityC300Rocket.rocketToDestroy = rocketDetectable;
                                    this.worldObj.playSoundAtEntity(entityC300Rocket, Main.MODID + ":rpg_s", 5.0f, Main.rand.nextFloat() * 0.2f + 1);
                                    entityC300Rocket.setThrowableHeading(0, this.yCoord + 2, 0, 2.5f, 1.0f);
                                    this.worldObj.spawnEntityInWorld(entityC300Rocket);
                                    entityC300Rocket.setOwner(this.owner);
                                    this.usages += 1;
                                }
                            }
                        }
                    }
                }
                if (this.usages >= 4) {
                    this.worldObj.breakBlock(this.xCoord, this.yCoord, this.zCoord, false);
                }
                this.ticks = 0;
            }
        }
    }
}

