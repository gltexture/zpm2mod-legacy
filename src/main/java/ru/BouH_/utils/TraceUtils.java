package ru.BouH_.utils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import java.util.List;

public class TraceUtils {
    public static MovingObjectPosition getMovingObjectPositionFromPlayer(World worldIn, EntityPlayer player, double distance, boolean useLiquids) {
        float f = 1.0F;
        float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
        float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
        double d0 = player.prevPosX + (player.posX - player.prevPosX) * (double) f;
        double d1 = player.prevPosY + (player.posY - player.prevPosY) * (double) f + (double) (worldIn.isRemote ? player.getEyeHeight() - player.getDefaultEyeHeight() : player.getEyeHeight()); // isRemote check to revert changes to ray trace position due to adding the eye height clientside and player yOffset differences
        double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * (double) f;
        Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
        float f3 = MathHelper.cos((float) (-f2 * (Math.PI / 180.0f) - (float) Math.PI));
        float f4 = MathHelper.sin((float) (-f2 * (Math.PI / 180.0f) - (float) Math.PI));
        float f5 = -MathHelper.cos((float) (-f1 * (Math.PI / 180.0f)));
        float f6 = MathHelper.sin((float) (-f1 * (Math.PI / 180.0f)));
        float f7 = f4 * f5;
        float f8 = f3 * f5;
        double d3 = 5.0D;
        if (player instanceof EntityPlayerMP) {
            d3 = distance;
        }
        Vec3 vec31 = vec3.addVector((double) f7 * d3, (double) f6 * d3, (double) f8 * d3);
        return worldIn.rayTraceBlocks(vec3, vec31, useLiquids, !useLiquids, false);
    }

    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public static Entity getMouseOver(Class<? extends Entity> classToFind, float look, double distance, float expand) { //TODO: REMAKE
        Minecraft mc = Minecraft.getMinecraft();
        Entity pointedEntity = null;
        MovingObjectPosition rayTrace;
        if (mc.renderViewEntity != null) {
            if (mc.theWorld != null) {
                rayTrace = rayTrace(mc.renderViewEntity, distance);
                Vec3 positionVec = mc.renderViewEntity.getPosition(look);
                double distanceToVec3 = distance;
                if (rayTrace != null) {
                    distanceToVec3 = rayTrace.hitVec.distanceTo(positionVec);
                }
                Vec3 lookVec = mc.renderViewEntity.getLook(look);
                Vec3 posDistVec = positionVec.addVector(lookVec.xCoord * distance, lookVec.yCoord * distance, lookVec.zCoord * distance);
                List<Entity> entities = mc.theWorld.getLoadedEntityList(); //BUGFIX
                double vecInsideDist = distanceToVec3;
                for (Entity entity : entities) {
                    if (entity != Minecraft.getMinecraft().thePlayer && classToFind.isAssignableFrom(entity.getClass())) {
                        double borderSize = entity.getCollisionBorderSize() * expand;
                        AxisAlignedBB expEntityBox = entity.boundingBox.expand(borderSize, borderSize, borderSize);
                        MovingObjectPosition calculateInterceptPos = expEntityBox.calculateIntercept(positionVec, posDistVec);
                        if (expEntityBox.isVecInside(positionVec)) {
                            if (0.0D < vecInsideDist || vecInsideDist == 0.0D) {
                                pointedEntity = entity;
                                vecInsideDist = 0.0D;
                            }
                        } else if (calculateInterceptPos != null) {
                            double calcInterceptPosDist = positionVec.distanceTo(calculateInterceptPos.hitVec);
                            if (calcInterceptPosDist < vecInsideDist || vecInsideDist == 0.0D) {
                                if (entity == mc.renderViewEntity.ridingEntity && !entity.canRiderInteract()) {
                                    if (vecInsideDist == 0.0D) {
                                        pointedEntity = entity;
                                    }
                                } else {
                                    pointedEntity = entity;
                                    vecInsideDist = calcInterceptPosDist;
                                }
                            }
                        }
                    }
                }
                if (pointedEntity != null && (vecInsideDist < distanceToVec3 || rayTrace == null)) {
                    return pointedEntity;
                }
            }
        }
        return null;
    }

    public static MovingObjectPosition rayTrace(EntityLivingBase entityLivingBase, double p_70614_1_) {
        Vec3 vec3 = Vec3.createVectorHelper(entityLivingBase.posX, entityLivingBase.posY + entityLivingBase.getEyeHeight(), entityLivingBase.posZ);
        Vec3 vec31 = entityLivingBase.getLookVec();
        Vec3 vec32 = vec3.addVector(vec31.xCoord * p_70614_1_, vec31.yCoord * p_70614_1_, vec31.zCoord * p_70614_1_);
        return rayTraceBlocks(entityLivingBase.worldObj, vec3, vec32, false, true, false);
    }

    public static MovingObjectPosition rayTraceBlocks(World world, Vec3 p_147447_1_, Vec3 p_147447_2_, boolean p_147447_3_, boolean p_147447_4_, boolean p_147447_5_) {
        if (!Double.isNaN(p_147447_1_.xCoord) && !Double.isNaN(p_147447_1_.yCoord) && !Double.isNaN(p_147447_1_.zCoord)) {
            if (!Double.isNaN(p_147447_2_.xCoord) && !Double.isNaN(p_147447_2_.yCoord) && !Double.isNaN(p_147447_2_.zCoord)) {
                int i = MathHelper.floor_double(p_147447_2_.xCoord);
                int j = MathHelper.floor_double(p_147447_2_.yCoord);
                int k = MathHelper.floor_double(p_147447_2_.zCoord);
                int l = MathHelper.floor_double(p_147447_1_.xCoord);
                int i1 = MathHelper.floor_double(p_147447_1_.yCoord);
                int j1 = MathHelper.floor_double(p_147447_1_.zCoord);
                Block block = world.getBlock(l, i1, j1);
                int k1 = world.getBlockMetadata(l, i1, j1);

                if ((!p_147447_4_ || block.getCollisionBoundingBoxFromPool(world, l, i1, j1) != null) && block.canStopRayTrace(k1, p_147447_3_)) {
                    MovingObjectPosition movingobjectposition = block.collisionRayTrace(world, l, i1, j1, p_147447_1_, p_147447_2_);

                    if (movingobjectposition != null) {
                        return movingobjectposition;
                    }
                }

                MovingObjectPosition movingobjectposition2 = null;
                k1 = 512;

                while (k1-- >= 0) {
                    if (Double.isNaN(p_147447_1_.xCoord) || Double.isNaN(p_147447_1_.yCoord) || Double.isNaN(p_147447_1_.zCoord)) {
                        return null;
                    }

                    if (l == i && i1 == j && j1 == k) {
                        return p_147447_5_ ? movingobjectposition2 : null;
                    }

                    boolean flag6 = true;
                    boolean flag3 = true;
                    boolean flag4 = true;
                    double d0 = 999.0D;
                    double d1 = 999.0D;
                    double d2 = 999.0D;

                    if (i > l) {
                        d0 = (double) l + 1.0D;
                    } else if (i < l) {
                        d0 = (double) l + 0.0D;
                    } else {
                        flag6 = false;
                    }

                    if (j > i1) {
                        d1 = (double) i1 + 1.0D;
                    } else if (j < i1) {
                        d1 = (double) i1 + 0.0D;
                    } else {
                        flag3 = false;
                    }

                    if (k > j1) {
                        d2 = (double) j1 + 1.0D;
                    } else if (k < j1) {
                        d2 = (double) j1 + 0.0D;
                    } else {
                        flag4 = false;
                    }

                    double d3 = 999.0D;
                    double d4 = 999.0D;
                    double d5 = 999.0D;
                    double d6 = p_147447_2_.xCoord - p_147447_1_.xCoord;
                    double d7 = p_147447_2_.yCoord - p_147447_1_.yCoord;
                    double d8 = p_147447_2_.zCoord - p_147447_1_.zCoord;

                    if (flag6) {
                        d3 = (d0 - p_147447_1_.xCoord) / d6;
                    }

                    if (flag3) {
                        d4 = (d1 - p_147447_1_.yCoord) / d7;
                    }

                    if (flag4) {
                        d5 = (d2 - p_147447_1_.zCoord) / d8;
                    }

                    boolean flag5 = false;
                    byte b0;

                    if (d3 < d4 && d3 < d5) {
                        if (i > l) {
                            b0 = 4;
                        } else {
                            b0 = 5;
                        }

                        p_147447_1_.xCoord = d0;
                        p_147447_1_.yCoord += d7 * d3;
                        p_147447_1_.zCoord += d8 * d3;
                    } else if (d4 < d5) {
                        if (j > i1) {
                            b0 = 0;
                        } else {
                            b0 = 1;
                        }

                        p_147447_1_.xCoord += d6 * d4;
                        p_147447_1_.yCoord = d1;
                        p_147447_1_.zCoord += d8 * d4;
                    } else {
                        if (k > j1) {
                            b0 = 2;
                        } else {
                            b0 = 3;
                        }

                        p_147447_1_.xCoord += d6 * d5;
                        p_147447_1_.yCoord += d7 * d5;
                        p_147447_1_.zCoord = d2;
                    }

                    Vec3 vec32 = Vec3.createVectorHelper(p_147447_1_.xCoord, p_147447_1_.yCoord, p_147447_1_.zCoord);
                    l = (int) (vec32.xCoord = MathHelper.floor_double(p_147447_1_.xCoord));

                    if (b0 == 5) {
                        --l;
                        ++vec32.xCoord;
                    }

                    i1 = (int) (vec32.yCoord = MathHelper.floor_double(p_147447_1_.yCoord));

                    if (b0 == 1) {
                        --i1;
                        ++vec32.yCoord;
                    }

                    j1 = (int) (vec32.zCoord = MathHelper.floor_double(p_147447_1_.zCoord));

                    if (b0 == 3) {
                        --j1;
                        ++vec32.zCoord;
                    }

                    Block block1 = world.getBlock(l, i1, j1);
                    int l1 = world.getBlockMetadata(l, i1, j1);

                    if (!p_147447_4_ || block1.getCollisionBoundingBoxFromPool(world, l, i1, j1) != null) {
                        if (block1.canStopRayTrace(l1, p_147447_3_)) {
                            MovingObjectPosition movingobjectposition1 = block1.collisionRayTrace(world, l, i1, j1, p_147447_1_, p_147447_2_);

                            if (movingobjectposition1 != null) {
                                return movingobjectposition1;
                            }
                        } else {
                            movingobjectposition2 = new MovingObjectPosition(l, i1, j1, b0, p_147447_1_, false);
                        }
                    }
                }

                return p_147447_5_ ? movingobjectposition2 : null;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }
}
