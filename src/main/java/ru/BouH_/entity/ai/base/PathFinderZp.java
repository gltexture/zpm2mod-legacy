package ru.BouH_.entity.ai.base;

import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import ru.BouH_.blocks.BlockLayer;

public class PathFinderZp extends PathFinder {
    private final boolean canPassOpenWoodenDoors;
    private final boolean canPassClosedWoodenDoor;
    private final boolean avoidsWater;

    public PathFinderZp(IBlockAccess p_i2137_1_, boolean p_i2137_2_, boolean p_i2137_3_, boolean p_i2137_4_, boolean p_i2137_5_) {
        super(p_i2137_1_, p_i2137_2_, p_i2137_3_, p_i2137_4_, p_i2137_5_);
        this.canPassOpenWoodenDoors = p_i2137_2_;
        this.canPassClosedWoodenDoor = p_i2137_3_;
        this.avoidsWater = p_i2137_4_;
    }

    public static int canEntityStandAt(Entity p_82565_0_, int p_82565_1_, int p_82565_2_, int p_82565_3_, PathPoint p_82565_4_, boolean p_82565_5_, boolean p_82565_6_, boolean p_82565_7_) {
        boolean flag3 = false;

        for (int l = p_82565_1_; l < p_82565_1_ + p_82565_4_.xCoord; ++l) {
            for (int i1 = p_82565_2_; i1 < p_82565_2_ + p_82565_4_.yCoord; ++i1) {
                for (int j1 = p_82565_3_; j1 < p_82565_3_ + p_82565_4_.zCoord; ++j1) {
                    Block block = p_82565_0_.worldObj.getBlock(l, i1, j1);

                    if (block.getMaterial() != Material.air) {
                        if (block instanceof BlockRailBase || block instanceof BlockLayer) {
                            return 1;
                        }
                        if (block == Blocks.snow_layer) {
                            int n1 = p_82565_0_.worldObj.getBlockMetadata(l, i1, j1);
                            if (n1 >= 2) {
                                return 5;
                            }
                        }
                        if (!p_82565_0_.handleLavaMovement() && block.getMaterial() == Material.lava) {
                            return -4;
                        }
                        if (block == Blocks.trapdoor) {
                            flag3 = true;
                        } else if (block != Blocks.flowing_water && block != Blocks.water) {
                            if (!p_82565_7_ && block == Blocks.wooden_door) {
                                return 0;
                            }
                        } else {
                            if (p_82565_5_) {
                                return -1;
                            }

                            flag3 = true;
                        }

                        int k1 = block.getRenderType();

                        if (p_82565_0_.worldObj.getBlock(l, i1, j1).getRenderType() == 9) {
                            int j2 = MathHelper.floor_double(p_82565_0_.posX);
                            int l1 = MathHelper.floor_double(p_82565_0_.posY);
                            int i2 = MathHelper.floor_double(p_82565_0_.posZ);

                            if (p_82565_0_.worldObj.getBlock(j2, l1, i2).getRenderType() != 9 && p_82565_0_.worldObj.getBlock(j2, l1 - 1, i2).getRenderType() != 9) {
                                return -3;
                            }
                        } else if (!block.isPassable(p_82565_0_.worldObj, l, i1, j1) && (!p_82565_6_ || !(block instanceof BlockDoor) && !(block instanceof BlockFence || block instanceof BlockFenceGate))) {
                            if (k1 == 11 || block == Blocks.fence_gate || k1 == 32) {
                                return -3;
                            }

                            if (block == Blocks.trapdoor) {
                                return 1;
                            }

                            Material material = block.getMaterial();

                            if (material != Material.lava) {
                                return 0;
                            }

                            if (!p_82565_0_.handleLavaMovement()) {
                                return -2;
                            }
                        }
                    }
                }
            }
        }
        return flag3 ? 2 : 1;
    }

    public int canEntityStandAt(Entity p_75855_1_, int p_75855_2_, int p_75855_3_, int p_75855_4_, PathPoint p_75855_5_) {
        return PathFinderZp.canEntityStandAt(p_75855_1_, p_75855_2_, p_75855_3_, p_75855_4_, p_75855_5_, this.avoidsWater, this.canPassClosedWoodenDoor, this.canPassOpenWoodenDoors);
    }

    public PathPoint getSafePoint(Entity p_75858_1_, int p_75858_2_, int p_75858_3_, int p_75858_4_, PathPoint p_75858_5_, int p_75858_6_) {
        PathPoint pathpoint1 = null;
        int i1 = this.canEntityStandAt(p_75858_1_, p_75858_2_, p_75858_3_, p_75858_4_, p_75858_5_);
        if (i1 == 5 || i1 == 2) {
            return this.openPoint(p_75858_2_, p_75858_3_, p_75858_4_);
        } else {
            if (i1 == 1) {
                pathpoint1 = this.openPoint(p_75858_2_, p_75858_3_, p_75858_4_);
            }

            if (pathpoint1 == null && p_75858_6_ > 0 && i1 != -3 && i1 != -4 && this.canEntityStandAt(p_75858_1_, p_75858_2_, p_75858_3_ + p_75858_6_, p_75858_4_, p_75858_5_) == 1) {
                pathpoint1 = this.openPoint(p_75858_2_, p_75858_3_ + p_75858_6_, p_75858_4_);
                p_75858_3_ += p_75858_6_;
            }

            if (pathpoint1 != null) {
                int j1 = 0;
                int k1 = 0;

                while (p_75858_3_ > 0) {
                    k1 = this.canEntityStandAt(p_75858_1_, p_75858_2_, p_75858_3_ - 1, p_75858_4_, p_75858_5_);

                    if (this.avoidsWater && k1 == -1) {
                        return null;
                    }

                    if (k1 != 1) {
                        break;
                    }

                    if (j1++ >= p_75858_1_.getMaxFallHeight()) {
                        return null;
                    }

                    --p_75858_3_;

                    if (p_75858_3_ > 0) {
                        pathpoint1 = this.openPoint(p_75858_2_, p_75858_3_, p_75858_4_);
                    }
                }

                if (k1 == -2 || k1 == -4) {
                    return null;
                }
            }
            return pathpoint1;
        }
    }
}