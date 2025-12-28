package ru.BouH_.items.gun.tracer;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import ru.BouH_.Main;
import ru.BouH_.entity.ieep.PlayerMiscData;
import ru.BouH_.entity.zombie.AZombieBase;
import ru.BouH_.init.BlocksZp;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.packets.misc.sound.PacketSound;
import ru.BouH_.utils.PluginUtils;

import java.util.*;

public class Tracer {
    public static final Set<Object> ToBrake = new HashSet<>();
    public static final Set<Object> ToIgnore = new HashSet<>();
    public static final Set<Object> nullCollisionToBreak = new HashSet<>();
    private static int materialsAreBroken;

    static {
        nullCollisionToBreak.add(BlocksZp.lamp);

        ToBrake.add(Material.ice);
        ToBrake.add(Material.glass);
        ToBrake.add(Material.sponge);
        ToBrake.add(Material.cactus);
        ToBrake.add(Material.gourd);
        ToBrake.add(Material.redstoneLight);
        ToBrake.add(Material.cake);
        ToBrake.add(Blocks.skull);
        ToBrake.add(Blocks.flower_pot);
        ToBrake.add(BlocksZp.lamp);

        ToIgnore.add(Material.leaves);
        ToIgnore.add(Blocks.waterlily);
        ToIgnore.add(Blocks.trapdoor);
        ToIgnore.add(Blocks.iron_bars);
        ToIgnore.add(Blocks.bed);
        ToIgnore.add(Blocks.wooden_door);
        ToIgnore.add(Material.snow);
        ToIgnore.add(Material.craftedSnow);
        ToIgnore.add(BlocksZp.sand_layer);
        ToIgnore.add(BlocksZp.gravel_layer);
        ToIgnore.add(Blocks.carpet);
        ToIgnore.add(Blocks.fence);
        ToIgnore.add(Blocks.fence_gate);
        ToIgnore.add(Blocks.nether_brick_fence);
    }

    public static TracerHit trace(World w, Vec3 fromVec, float yaw, float pitch, float spread, float maxLength, EntityPlayer entityToExclude) {
        Vec3 lookVec = Tracer.createLookVec(yaw, pitch, Math.max(spread, 0), maxLength);
        Vec3 toVec = Vec3.createVectorHelper(fromVec.xCoord + lookVec.xCoord, fromVec.yCoord + lookVec.yCoord, fromVec.zCoord + lookVec.zCoord);
        return Tracer.trace(w, fromVec, toVec, entityToExclude, -1);
    }

    public static TracerHit trace(World w, Vec3 fromVec, float yaw, float pitch, float spread, float maxLength, EntityPlayer entityToExclude, int ping) {
        Vec3 lookVec = Tracer.createLookVec(yaw, pitch, Math.max(spread, 0), maxLength);
        Vec3 toVec = Vec3.createVectorHelper(fromVec.xCoord + lookVec.xCoord, fromVec.yCoord + lookVec.yCoord, fromVec.zCoord + lookVec.zCoord);
        return Tracer.trace(w, fromVec, toVec, entityToExclude, ping);
    }

    public static TracerHit trace(World w, Vec3 fromVec, Vec3 toVec, EntityPlayer entityToExclude, int ping) {
        Vec3 fromVecCopy = Vec3.createVectorHelper(fromVec.xCoord, fromVec.yCoord, fromVec.zCoord);
        Vec3 toVecCopy = Vec3.createVectorHelper(toVec.xCoord, toVec.yCoord, toVec.zCoord);
        MovingObjectPosition collidingBlockMop = newTrace(entityToExclude, w, fromVecCopy, toVecCopy, false, true, true);
        TracerHit collidingBlock;
        collidingBlock = collidingBlockMop != null ? new TracerHit(collidingBlockMop.blockX, collidingBlockMop.blockY, collidingBlockMop.blockZ, collidingBlockMop.sideHit, collidingBlockMop.hitVec) : new TracerHit((int) toVec.xCoord, (int) toVec.yCoord, (int) toVec.zCoord, -1, toVec);
        Vec3 newToVec = collidingBlock.hitVec;
        HashMap<Entity, Vec3> collidingEntities = Tracer.traceEntitiesSimple(w, fromVec, newToVec, entityToExclude, ping);
        Tracer.materialsAreBroken = 0;
        return !collidingEntities.isEmpty() ? Tracer.getClosestEntity(collidingEntities, fromVec, toVec, collidingBlock) : collidingBlock;
    }

    private static HashMap<Entity, Vec3> traceEntitiesSimple(World w, Vec3 fromVec, Vec3 toVec, EntityPlayer entityToExclude, int ping) {
        AxisAlignedBB scannedAABB = AxisAlignedBB.getBoundingBox(Math.min(fromVec.xCoord, toVec.xCoord), Math.min(fromVec.yCoord, toVec.yCoord), Math.min(fromVec.zCoord, toVec.zCoord), Math.max(fromVec.xCoord, toVec.xCoord), Math.max(fromVec.yCoord, toVec.yCoord), Math.max(fromVec.zCoord, toVec.zCoord));
        List<Entity> tracedEntities = Tracer.getEntitiesWithinAABBExcludingEntity(w, entityToExclude, scannedAABB, ping);
        HashMap<Entity, Vec3> collidingEntities = new HashMap<>();
        for (Entity entity : tracedEntities) {
            if (!entity.canBeCollidedWith() || entityToExclude != null && (entity == entityToExclude.ridingEntity || !entity.isEntityAlive())) {
                continue;
            } else {
                if (entity instanceof EntityEnderman) {
                    entity.attackEntityFrom(DamageSource.causeThrownDamage(null, null), 1);
                    continue;
                }
            }
            float borderSize = entity.getCollisionBorderSize();
            AxisAlignedBB entityAABB = Tracer.getNewAABB(entity, ping).expand(borderSize, borderSize, borderSize);
            if (entityAABB.isVecInside(fromVec)) {
                collidingEntities.put(entity, fromVec);
                continue;
            }
            MovingObjectPosition mop = entityAABB.calculateIntercept(fromVec, toVec);
            if (mop == null) {
                if (entity instanceof EntityPlayerMP) {
                    AxisAlignedBB entityAABB2 = Tracer.getNewAABB(entity, ping).expand(0.65f, 0.65f, 0.65f);
                    MovingObjectPosition mop2 = entityAABB2.calculateIntercept(fromVec, toVec);
                    if (mop2 != null) {
                        NetworkHandler.NETWORK.sendTo(new PacketSound(2), (EntityPlayerMP) entity);
                    }
                }
                continue;
            }
            collidingEntities.put(entity, mop.hitVec);
        }
        return collidingEntities;
    }

    private static Vec3 createLookVec(float yaw, float pitch, float spread, float length) {
        float cosYaw = MathHelper.cos((float) ((-yaw) * (Math.PI / 180.0f) - Math.PI));
        float sinYaw = MathHelper.sin((float) ((-yaw) * (Math.PI / 180.0f) - Math.PI));
        float cosPitch = -MathHelper.cos((float) ((-pitch) * (Math.PI / 180.0f)));
        float sinPitch = MathHelper.sin((float) ((-pitch) * (Math.PI / 180.0f)));
        Vec3 lookVec = Vec3.createVectorHelper(sinYaw * cosPitch * length, sinPitch * length, cosYaw * cosPitch * length);
        if (spread > 0.0f) {
            Tracer.addSpreadToVec3(lookVec, spread);
        }
        return lookVec;
    }

    private static void addSpreadToVec3(Vec3 vec, float spread) {
        float randAngle = (float) ((Main.rand.nextFloat() - 0.5f) * spread * 2.0f * (Math.PI / 180.0f));
        Vec3 axis = Vec3.createVectorHelper(Main.rand.nextFloat() - 0.5f, Main.rand.nextFloat() - 0.5f, Main.rand.nextFloat() - 0.5f);
        axis.normalize();
        float s = MathHelper.sin(randAngle);
        float c = MathHelper.cos(randAngle);
        float oc = 1.0f - c;
        double[] m = new double[]{oc * axis.xCoord * axis.xCoord + c, oc * axis.xCoord * axis.yCoord - axis.zCoord * s, oc * axis.zCoord * axis.xCoord + axis.yCoord * s, oc * axis.xCoord * axis.yCoord + axis.zCoord * s, oc * axis.yCoord * axis.yCoord + c, oc * axis.yCoord * axis.zCoord - axis.xCoord * s, oc * axis.zCoord * axis.xCoord - axis.yCoord * s, oc * axis.yCoord * axis.zCoord + axis.xCoord * s, oc * axis.zCoord * axis.zCoord + c};
        vec.xCoord = m[0] * vec.xCoord + m[1] * vec.yCoord + m[2] * vec.zCoord;
        vec.yCoord = m[3] * vec.xCoord + m[4] * vec.yCoord + m[5] * vec.zCoord;
        vec.zCoord = m[6] * vec.xCoord + m[7] * vec.yCoord + m[8] * vec.zCoord;
    }

    private static TracerHit getClosestEntity(HashMap<Entity, Vec3> entityToHitVecMap, Vec3 fromVec, Vec3 toVec, TracerHit collidingBlock) {
        Entity closestEntity = null;
        Vec3 closestHitVec = null;

        double distanceSq = Double.MAX_VALUE;
        for (Map.Entry<Entity, Vec3> entry : entityToHitVecMap.entrySet()) {
            double entryDistanceSq = fromVec.squareDistanceTo(entry.getValue());
            if (entryDistanceSq >= distanceSq) continue;
            closestEntity = entry.getKey();
            closestHitVec = entry.getValue();
            distanceSq = entryDistanceSq;
        }
        if (closestEntity != null) {
            return new TracerHit(closestEntity, closestHitVec);
        }
        return collidingBlock;
    }

    //TODO! REMAKE
    public static MovingObjectPosition newTrace(EntityPlayer player, World world, Vec3 p_147447_1_, Vec3 p_147447_2_, boolean p_147447_3_, boolean p_147447_4_, boolean p_147447_5_) {
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
                boolean b1 = Main.rand.nextBoolean();
                if (nullCollisionToBreak.contains(block) || ((!p_147447_4_ || block.getCollisionBoundingBoxFromPool(world, l, i1, j1) != null) && block.canStopRayTrace(k1, p_147447_3_))) {
                    MovingObjectPosition movingobjectposition = block.collisionRayTrace(world, l, i1, j1, p_147447_1_, p_147447_2_);
                    if (movingobjectposition != null) {
                        if (!world.isRemote && (block == Blocks.iron_bars && b1)) {
                            return movingobjectposition;
                        }
                        if (!world.isRemote && Tracer.materialsAreBroken < 3 && (Tracer.ToBrake.contains(block) || Tracer.ToBrake.contains(block.getMaterial())) && (block.getBlockHardness(world, l, i1, j1) <= 3.0f && block.getBlockHardness(world, l, i1, j1) > 0)) {
                            if (PluginUtils.canBreak(player, l, i1, j1)) {
                                world.breakBlock(l, i1, j1, false);
                                Tracer.materialsAreBroken += 1;
                            }
                        } else {
                            if (!ToIgnore.contains(block) && !ToIgnore.contains(block.getMaterial())) {
                                return movingobjectposition;
                            }
                        }
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
                        d0 = l + 1.0D;
                    } else if (i < l) {
                        d0 = l + 0.0D;
                    } else {
                        flag6 = false;
                    }

                    if (j > i1) {
                        d1 = i1 + 1.0D;
                    } else if (j < i1) {
                        d1 = i1 + 0.0D;
                    } else {
                        flag3 = false;
                    }

                    if (k > j1) {
                        d2 = j1 + 1.0D;
                    } else if (k < j1) {
                        d2 = j1 + 0.0D;
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

                    MovingObjectPosition rayMov = block1.collisionRayTrace(world, l, i1, j1, p_147447_1_, p_147447_2_);
                    MovingObjectPosition mov2 = new MovingObjectPosition(l, i1, j1, b0, p_147447_1_, false);
                    if (nullCollisionToBreak.contains(block1) || (!p_147447_4_ || block1.getCollisionBoundingBoxFromPool(world, l, i1, j1) != null)) {
                        if (!world.isRemote && (block1 == Blocks.iron_bars && b1)) {
                            if (rayMov != null) {
                                return mov2;
                            }
                        }
                        if (!world.isRemote && Tracer.materialsAreBroken < 3 && (Tracer.ToBrake.contains(block1) || Tracer.ToBrake.contains(block1.getMaterial())) && (block1.getBlockHardness(world, l, i1, j1) <= 3.0f && block1.getBlockHardness(world, l, i1, j1) > 0)) {
                            if (PluginUtils.canBreak(player, l, i1, j1)) {
                                world.breakBlock(l, i1, j1, false);
                                Tracer.materialsAreBroken += 1;
                            }
                        } else {
                            if (!ToIgnore.contains(block1) && !ToIgnore.contains(block1.getMaterial())) {
                                if (block1.canStopRayTrace(l1, p_147447_3_)) {
                                    if (rayMov != null) {
                                        return rayMov;
                                    }
                                } else {
                                    movingobjectposition2 = mov2;
                                }
                            }
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

    public static AxisAlignedBB getNewAABB(Entity entity, int ping) {
        AxisAlignedBB tempAABB = entity.boundingBox;
        if (!entity.worldObj.isRemote) {
            if (entity instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer) entity;
                PlayerMiscData playerMiscData = PlayerMiscData.getPlayerData(player);
                if (playerMiscData.getAABBHistory().size() == 20) {
                    AxisAlignedBB axisAlignedBB = playerMiscData.getAABBHistory().get(Math.min((int) (ping / 20.0f), 19));
                    if (axisAlignedBB != null) {
                        tempAABB = axisAlignedBB;
                    }
                }
            } else if (entity instanceof AZombieBase) {
                AZombieBase monster = (AZombieBase) entity;
                if (monster.getAABBHistory().size() == 20) {
                    AxisAlignedBB axisAlignedBB = monster.getAABBHistory().get(Math.min((int) (ping / 20.0f), 19));
                    if (axisAlignedBB != null) {
                        tempAABB = axisAlignedBB;
                    }
                }
            }
        }
        return tempAABB;
    }

    public static List<Entity> getEntitiesWithinAABBExcludingEntity(World world, Entity p_94576_1_, AxisAlignedBB p_94576_2_, int ping) {
        ArrayList<Entity> arraylist = new ArrayList<>();
        int i = MathHelper.floor_double((p_94576_2_.minX - World.MAX_ENTITY_RADIUS) / 16.0D);
        int j = MathHelper.floor_double((p_94576_2_.maxX + World.MAX_ENTITY_RADIUS) / 16.0D);
        int k = MathHelper.floor_double((p_94576_2_.minZ - World.MAX_ENTITY_RADIUS) / 16.0D);
        int l = MathHelper.floor_double((p_94576_2_.maxZ + World.MAX_ENTITY_RADIUS) / 16.0D);
        for (int i1 = i; i1 <= j; ++i1) {
            for (int j1 = k; j1 <= l; ++j1) {
                if (world.getChunkProvider().chunkExists(i1, j1)) {
                    Tracer.getEntitiesWithinAABBForEntity(world, world.getChunkFromChunkCoords(i1, j1), p_94576_1_, p_94576_2_, arraylist, ping);
                }
            }
        }
        return arraylist;
    }

    @SuppressWarnings("unchecked")
    public static void getEntitiesWithinAABBForEntity(World world, Chunk chunk, Entity p_76588_1_, AxisAlignedBB p_76588_2_, List<Entity> p_76588_3_, int ping) {
        int i = MathHelper.floor_double((p_76588_2_.minY - World.MAX_ENTITY_RADIUS) / 16.0D);
        int j = MathHelper.floor_double((p_76588_2_.maxY + World.MAX_ENTITY_RADIUS) / 16.0D);
        i = MathHelper.clamp_int(i, 0, chunk.entityLists.length - 1);
        j = MathHelper.clamp_int(j, 0, chunk.entityLists.length - 1);

        for (int k = i; k <= j; ++k) {
            List<Entity> list1 = chunk.entityLists[k];

            for (Entity entity1 : list1) {
                if (entity1 != p_76588_1_ && Tracer.getNewAABB(entity1, ping).intersectsWith(p_76588_2_)) {
                    p_76588_3_.add(entity1);
                    Entity[] entity = entity1.getParts();
                    if (entity != null) {
                        for (Entity entity2 : entity) {
                            if (entity2 != p_76588_1_ && entity2.boundingBox.intersectsWith(p_76588_2_)) {
                                p_76588_3_.add(entity2);
                            }
                        }
                    }
                }
            }
        }
    }
}