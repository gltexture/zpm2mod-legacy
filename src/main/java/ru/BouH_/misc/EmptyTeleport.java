package ru.BouH_.misc;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

public class EmptyTeleport extends Teleporter {

    public EmptyTeleport(WorldServer worldIn) {
        super(worldIn);
    }

    public void placeInPortal(Entity entityIn, float rotationYaw) {
    }

    public boolean placeInExistingPortal(Entity entityIn, float rotationYaw) {
        return false;
    }

    public boolean makePortal(Entity entityIn) {
        int dimension = entityIn.dimension;
        EntityPlayer player = (EntityPlayer) entityIn;
        World world = DimensionManager.getWorld(dimension);
        ChunkCoordinates chunkCoordinates = world.provider.getRandomizedSpawnPoint();
        if (player.getBedLocation(dimension) != null) {
            chunkCoordinates = player.getBedLocation(dimension);
        }
        entityIn.setLocationAndAngles(chunkCoordinates.posX, chunkCoordinates.posY, chunkCoordinates.posZ, 0, 0);
        return false;
    }
}
