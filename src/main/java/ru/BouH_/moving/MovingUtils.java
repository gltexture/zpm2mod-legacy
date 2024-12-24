package ru.BouH_.moving;

import net.minecraft.block.Block;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import ru.BouH_.entity.ieep.PlayerMiscData;
import ru.BouH_.utils.EntityUtils;

import java.util.ArrayList;
import java.util.List;

public class MovingUtils {
    private static final List<AxisAlignedBB> collidingBoundingBoxes = new ArrayList<>();

    public static boolean forceSneak(EntityPlayer player) {
        AxisAlignedBB axisAlignedBB = player.boundingBox;
        double y = axisAlignedBB.minY;
        AxisAlignedBB axisAlignedBB1 = AxisAlignedBB.getBoundingBox(axisAlignedBB.minX, y + 1.5f, axisAlignedBB.minZ, axisAlignedBB.maxX, y + 1.8f, axisAlignedBB.maxZ);
        return !player.isRiding() && MovingUtils.blockInAABB(player, axisAlignedBB1);
    }

    public static boolean forceLie(EntityPlayer player) {
        AxisAlignedBB axisAlignedBB = player.boundingBox;
        double y = axisAlignedBB.minY;
        AxisAlignedBB axisAlignedBB2 = AxisAlignedBB.getBoundingBox(axisAlignedBB.minX, y + 0.8f, axisAlignedBB.minZ, axisAlignedBB.maxX, y + 1.5f, axisAlignedBB.maxZ);
        return !player.isRiding() && MovingUtils.blockInAABB(player, axisAlignedBB2);
    }

    public static boolean isSwimming(EntityPlayer player) {
        return MovingUtils.canSwim(player) && PlayerMiscData.getPlayerData(player).isLying();
    }

    public static boolean canSwim(EntityPlayer player) {
        return !player.isPotionActive(27) && player.hurtTime == 0 && EntityUtils.isFullyInMaterial(player, Material.water);
    }

    public static boolean blockInAABB(EntityLivingBase entity, AxisAlignedBB p_147461_1_) {
        MovingUtils.collidingBoundingBoxes.clear();
        int i = MathHelper.floor_double(p_147461_1_.minX);
        int j = MathHelper.floor_double(p_147461_1_.maxX + 0.999D);
        int k = MathHelper.floor_double(p_147461_1_.minY);
        int l = MathHelper.floor_double(p_147461_1_.maxY + 0.999D);
        int i1 = MathHelper.floor_double(p_147461_1_.minZ);
        int j1 = MathHelper.floor_double(p_147461_1_.maxZ + 0.999D);
        for (int k1 = i; k1 < j; ++k1) {
            for (int l1 = i1; l1 < j1; ++l1) {
                if (entity.worldObj.blockExists(k1, 64, l1)) {
                    for (int i2 = k - 1; i2 < l; ++i2) {
                        Block block;
                        if (k1 >= -30000000 && k1 < 30000000 && l1 >= -30000000 && l1 < 30000000) {
                            block = entity.worldObj.getBlock(k1, i2, l1);
                        } else {
                            block = Blocks.bedrock;
                        }
                        if (!(entity.isOnLadder() && block instanceof BlockTrapDoor)) {
                            if (!block.isLadder(entity.worldObj, k1, i2, l1, entity) && block != Blocks.wooden_door && block != Blocks.iron_door && block != Blocks.fence_gate) {
                                block.addCollisionBoxesToList(entity.worldObj, k1, i2, l1, p_147461_1_, MovingUtils.collidingBoundingBoxes, entity);
                            }
                        }
                    }
                }
            }
        }
        return !MovingUtils.collidingBoundingBoxes.isEmpty();
    }
}
