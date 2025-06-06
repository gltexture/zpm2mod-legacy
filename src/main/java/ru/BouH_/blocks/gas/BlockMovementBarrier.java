package ru.BouH_.blocks.gas;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

public class BlockMovementBarrier extends BlockGasBase {
    public BlockMovementBarrier() {
        super();
    }

    public void addCollisionBoxesToList(World worldIn, int x, int y, int z, AxisAlignedBB mask, List list, Entity collider)
    {
        if (collider instanceof EntityPlayer) {
            if (((EntityPlayer) collider).capabilities.isCreativeMode) {
                return;
            }
        }
        super.addCollisionBoxesToList(worldIn, x, y, z, mask, list, collider);
    }

    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int x, int y, int z) {
        return AxisAlignedBB.getBoundingBox(x, y, z, (double) x + 1.0d, (double) y + 1.0F, (double) z + 1.0d);
    }
}
