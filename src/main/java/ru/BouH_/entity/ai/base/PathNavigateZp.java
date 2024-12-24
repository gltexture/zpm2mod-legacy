package ru.BouH_.entity.ai.base;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.util.MathHelper;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.World;

public class PathNavigateZp extends PathNavigate {
    private final EntityLiving theEntity;
    private final World worldObj;

    public PathNavigateZp(EntityLiving p_i1671_1_, World p_i1671_2_) {
        super(p_i1671_1_, p_i1671_2_);
        this.theEntity = p_i1671_1_;
        this.worldObj = p_i1671_2_;
    }

    public PathEntity getPathToXYZ(double p_75488_1_, double p_75488_3_, double p_75488_5_) {
        return !this.canNavigate() ? null : this.getEntityPathToXYZ(this.worldObj, this.theEntity, MathHelper.floor_double(p_75488_1_), (int) p_75488_3_, MathHelper.floor_double(p_75488_5_), this.getPathSearchRange(), true, true, false, true);
    }

    public PathEntity getPathToEntityLiving(Entity p_75494_1_) {
        return !this.canNavigate() ? null : this.getPathEntityToEntity(this.worldObj, this.theEntity, p_75494_1_, this.getPathSearchRange(), true, true, false, true);
    }

    public int getPathableYPos() {
        if (this.theEntity.isInWater() && this.canSwim) {
            for (int k = 0; k < 32; k++) {
                Block block = this.worldObj.getBlock(MathHelper.floor_double(this.theEntity.posX), (int) this.theEntity.boundingBox.minY + k, MathHelper.floor_double(this.theEntity.posZ));
                if (block != Blocks.flowing_water && block != Blocks.water) {
                    return (int) this.theEntity.boundingBox.minY + k;
                }
            }
            return (int) this.theEntity.boundingBox.minY;
        } else {
            return (int) (this.theEntity.boundingBox.minY + 0.5D);
        }
    }

    public boolean canNavigate() {
        return this.theEntity.onGround || this.theEntity.isInWater() || this.theEntity.handleLavaMovement();
    }

    public PathEntity getPathEntityToEntity(World world, Entity p_72865_1_, Entity p_72865_2_, float p_72865_3_, boolean p_72865_4_, boolean p_72865_5_, boolean p_72865_6_, boolean p_72865_7_) {
        world.theProfiler.startSection("pathfind");
        int i = MathHelper.floor_double(p_72865_1_.posX);
        int j = MathHelper.floor_double(p_72865_1_.posY + 1.0D);
        int k = MathHelper.floor_double(p_72865_1_.posZ);
        int l = (int) (p_72865_3_ + 16.0F);
        int i1 = i - l;
        int j1 = j - l;
        int k1 = k - l;
        int l1 = i + l;
        int i2 = j + l;
        int j2 = k + l;
        ChunkCache chunkcache = new ChunkCache(world, i1, j1, k1, l1, i2, j2, 0);
        PathEntity pathentity = (new PathFinderZp(chunkcache, p_72865_4_, p_72865_5_, p_72865_6_, p_72865_7_)).createEntityPathTo(p_72865_1_, p_72865_2_, p_72865_3_);
        world.theProfiler.endSection();
        return pathentity;
    }

    public PathEntity getEntityPathToXYZ(World world, Entity p_72844_1_, int p_72844_2_, int p_72844_3_, int p_72844_4_, float p_72844_5_, boolean p_72844_6_, boolean p_72844_7_, boolean p_72844_8_, boolean p_72844_9_) {
        world.theProfiler.startSection("pathfind");
        int l = MathHelper.floor_double(p_72844_1_.posX);
        int i1 = MathHelper.floor_double(p_72844_1_.posY);
        int j1 = MathHelper.floor_double(p_72844_1_.posZ);
        int k1 = (int) (p_72844_5_ + 8.0F);
        int l1 = l - k1;
        int i2 = i1 - k1;
        int j2 = j1 - k1;
        int k2 = l + k1;
        int l2 = i1 + k1;
        int i3 = j1 + k1;
        ChunkCache chunkcache = new ChunkCache(world, l1, i2, j2, k2, l2, i3, 0);
        PathEntity pathentity = (new PathFinderZp(chunkcache, p_72844_6_, p_72844_7_, p_72844_8_, p_72844_9_)).createEntityPathTo(p_72844_1_, p_72844_2_, p_72844_3_, p_72844_4_, p_72844_5_);
        world.theProfiler.endSection();
        return pathentity;
    }
}