package ru.BouH_.fun.tiles;

import net.minecraft.entity.player.EntityPlayerMP;
import ru.BouH_.Main;
import ru.BouH_.fun.rockets.EntityKalibrGuidedRocket;

public class TileKalibrBlock extends TileTacticalBlock {

    public void startRocket(EntityPlayerMP entityPlayerMP, double x, double y, double z) {
        if (!this.worldObj.isRemote) {
            EntityKalibrGuidedRocket entityKalibrGuidedRocket = new EntityKalibrGuidedRocket(this.worldObj, this.xCoord + 0.5f, this.yCoord + 2.0f, this.zCoord + 0.5f);
            this.worldObj.playSoundAtEntity(entityKalibrGuidedRocket, Main.MODID + ":rpg_s", 5.0f, Main.rand.nextFloat() * 0.2f + 1);
            entityKalibrGuidedRocket.setThrowableHeading(0, 3.0f, 0, 3.0f, 1.0f);
            this.worldObj.spawnEntityInWorld(entityKalibrGuidedRocket);
            entityKalibrGuidedRocket.setTarget(x, y, z);
            entityKalibrGuidedRocket.setOwner(entityPlayerMP.getDisplayName());
            this.worldObj.breakBlock(this.xCoord, this.yCoord, this.zCoord, false);
        }
    }
}

