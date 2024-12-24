package ru.BouH_.items.gun.tracer;

import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class TracerHit extends MovingObjectPosition {
    public float distance;

    public TracerHit(int x, int y, int z, int sideHit, Vec3 hitVec) {
        super(x, y, z, sideHit, hitVec);
    }

    public TracerHit(Entity entity, Vec3 hitVec) {
        super(entity);
        this.hitVec = hitVec;
    }
}
