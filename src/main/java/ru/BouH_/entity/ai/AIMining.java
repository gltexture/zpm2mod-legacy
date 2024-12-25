package ru.BouH_.entity.ai;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.block.*;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTool;
import net.minecraft.util.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.entity.zombie.AZombieBase;
import ru.BouH_.init.BlocksZp;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.packets.particles.ParticleZombieBlockCrack;
import ru.BouH_.utils.PluginUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AIMining extends EntityAIBase {
    public static Set<Block> globalBlocksBlackList = new HashSet<>();
    public static Set<Class<? extends Block>> extraClassesGlobalDigList = new HashSet<>();

    static {
        globalBlocksBlackList.add(BlocksZp.lab);
        globalBlocksBlackList.add(BlocksZp.sand_layer);
        globalBlocksBlackList.add(BlocksZp.gravel_layer);
        globalBlocksBlackList.add(Blocks.snow_layer);
        extraClassesGlobalDigList.add(BlockTorch.class);
        extraClassesGlobalDigList.add(BlockCrops.class);
        extraClassesGlobalDigList.add(BlockReed.class);
        extraClassesGlobalDigList.add(BlockRedstoneWire.class);
        extraClassesGlobalDigList.add(BlockRedstoneLight.class);
    }

    protected float mobStrength;
    protected int mineProgress;
    protected int doNotMiningCd;
    protected int ticksBeforeCanMine;
    protected AZombieBase taskOwner;
    protected float nominalStrength;

    public AIMining(AZombieBase creature, float nominalStrength, float f2) {
        super();
        this.taskOwner = creature;
        this.mobStrength = this.getStrength(nominalStrength, f2);
        this.setMutexBits(0);
        this.nominalStrength = nominalStrength;
        if (Main.rand.nextFloat() <= 0.7f) {
            int i1 = this.taskOwner.worldObj.difficultySetting == EnumDifficulty.EASY ? 6000 : this.taskOwner.worldObj.difficultySetting == EnumDifficulty.NORMAL ? 4800 : 3600;
            this.ticksBeforeCanMine = Main.rand.nextInt(i1);
        }
    }

    protected boolean checkBlock(World world, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);
        for (Class<? extends Block> clazz : AIMining.extraClassesGlobalDigList) {
            if (clazz.isAssignableFrom(block.getClass())) {
                return true;
            }
        }
        if (block == Blocks.ladder && this.taskOwner.isOnLadder()) {
            return false;
        }
        return block.getCollisionBoundingBoxFromPool(world, x, y, z) != null;
    }

    protected boolean canDig(World world, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);
        return block.getBlockHardness(world, x, y, z) >= 0 && !AIMining.globalBlocksBlackList.contains(block) && block.getBlockHardness(world, x, y, z) <= this.getMobStrength(block);
    }

    protected BlockPos getDigPos(World world, int x, int y, int z, int plX, int plY, int plZ, int atY, Way way) {
        if (this.taskOwner.isOnLadder()) {
            if (this.checkBlock(world, plX, plY + 2, plZ) && this.canDig(world, plX, plY + 2, plZ)) {
                return new BlockPos(plX, plY + 2, plZ);
            }
        }
        if ((this.checkBlock(world, plX, y, plZ) && this.canDig(world, plX, y, plZ)) || (this.checkBlock(world, plX, y + 1, plZ) && this.canDig(world, plX, y + 1, plZ))) {
            x = plX;
            z = plZ;
        }
        if (way == Way.STRAIGHT) {
            for (int i = 1; i >= 0; i--) {
                if (this.checkBlock(world, x, y + i, z) && this.canDig(world, x, y + i, z)) {
                    y += i;
                    break;
                }
            }
        }
        if (way == Way.UP) {
            for (int i = 3; i >= 0; i--) {
                if (this.checkBlock(world, x, y + i, z) && this.canDig(world, x, y + i, z)) {
                    if (i == 3 && (((this.checkBlock(world, plX, y + 2, plZ) || this.checkBlock(world, plX, y + 3, plZ)) && this.checkBlock(world, x, y + 2, z)) || (this.checkBlock(world, plX, y + 2, plZ) && this.checkBlock(world, x, y + 1, z)))) {
                        if (this.checkBlock(world, plX, y + 2, plZ) && this.checkBlock(world, x, y + 1, z)) {
                            y += i - 2;
                        } else {
                            if (this.canDig(world, x, y + 2, z)) {
                                y += i - 1;
                            } else {
                                way = Way.STRAIGHT_UP_DOWN;
                                break;
                            }
                        }
                    } else if (i == 2 && this.checkBlock(world, plX, y + 2, plZ) && this.checkBlock(world, x, y + 1, z)) {
                        if (this.canDig(world, x, y + 1, z)) {
                            y += i - 1;
                        } else {
                            way = Way.STRAIGHT_UP_DOWN;
                            break;
                        }
                    } else if (i == 0) {
                        way = Way.STRAIGHT_UP_DOWN;
                        break;
                    } else {
                        y += i;
                    }
                    break;
                }
            }
        }
        if (way == Way.DOWN) {
            for (int i = -1; i < 3; i++) {
                if (this.checkBlock(world, x, y + i, z)) {
                    if (this.canDig(world, x, y + i, z)) {
                        if (i == -1 && this.checkBlock(world, x, y, z)) {
                            if (!this.canDig(world, x, y, z)) {
                                way = Way.STRAIGHT_UP_DOWN;
                                break;
                            }
                        } else {
                            y += i;
                            break;
                        }
                    } else {
                        way = Way.STRAIGHT_UP_DOWN;
                        break;
                    }
                }
            }
        }
        if (way == Way.STRAIGHT_UP_DOWN) {
            x = plX;
            z = plZ;
            if (atY > plY) {
                if (this.checkBlock(world, x, y + 3, z) && this.canDig(world, x, y + 3, z) && !this.checkBlock(world, x, y + 2, z)) {
                    y += 3;
                } else {
                    if (this.checkBlock(world, x, y + 2, z) && this.canDig(world, x, y + 2, z)) {
                        y += 2;
                    } else {
                        return this.getDigPos(world, x, y, z, plX, plY, plZ, atY, Way.STRAIGHT);
                    }
                }
            } else if (atY < plY) {
                if (this.checkBlock(world, x, y - 1, z) && this.canDig(world, x, y - 1, z)) {
                    y -= 1;
                } else {
                    return this.getDigPos(world, x, y, z, plX, plY, plZ, atY, Way.STRAIGHT);
                }
            }
        }
        return this.checkBlock(world, x, y, z) && this.canDig(world, x, y, z) ? new BlockPos(x, y, z) : null;
    }

    protected float getStrength(float f1, float f2) {
        if (f1 <= 5.0f && Main.rand.nextFloat() <= 0.1f){
            this.taskOwner.canMine = false;
            return 0.0f;
        }
        if (f2 >= 1.0f) {
            return f1;
        }
        float f0 = this.taskOwner.worldObj.difficultySetting == EnumDifficulty.HARD ? 0.2f : 0.1f;
        float f3 = Math.min(0.5f, f1);
        float f4 = f1 * f0;
        float chance = Main.rand.nextFloat();
        while (chance <= f2 && f3 < f1) {
            f3 = Math.min(f3 + f4, f1);
            f2 *= f2;
        }
        return f3;
    }

    public float optimalStrength() {
        return this.nominalStrength;
    }

    protected float getMobToolStrength(Block block) {
        if (this.taskOwner.getHeldItem() != null) {
            Item heldItem = this.taskOwner.getHeldItem().getItem();
            if (heldItem instanceof ItemTool) {
                ItemTool itemTool = (ItemTool) heldItem;
                return itemTool.getStrVsBlock(this.taskOwner.getHeldItem(), block);
            }
        }
        return 0.0f;
    }

    protected float getMobStrength(Block block) {
        return Math.max(this.mobStrength, this.mobStrength * this.getMobToolStrength(block) * 12.0f);
    }

    protected int getBlockHardness(World world, int x, int y, int z) {
        Block block = world.getBlock(x, y, z);
        if (block == Blocks.wooden_door) {
            return 100;
        }
        float f1 = block.getBlockHardness(world, x, y, z);
        if (f1 <= 0.0f) {
            return 0;
        }
        float hardness = Math.min(f1, 500);
        double salt = Math.sqrt(hardness * 0.4f);
        if (hardness >= 1.0f) {
            salt = Math.max(Math.sqrt(hardness * 0.2f), 1.0f);
        }
        int init = this.taskOwner.worldObj.difficultySetting == EnumDifficulty.HARD ? 160 : 180;
        return (int) ((init - this.getMobToolStrength(block) * 3.0f) * salt - this.mobStrength * 2.0f);
    }

    @SuppressWarnings("unchecked")
    public boolean shouldExecute() {
        EntityLivingBase entitylivingbase = this.taskOwner.getAttackTarget();
        if (!this.taskOwner.worldObj.getGameRules().getGameRuleBooleanValue("zombiesCanMine") || !this.taskOwner.isCanMine()) {
            return false;
        }
        if (this.doNotMiningCd <= 0) {
            if (this.mineProgress > 0) {
                this.mineProgress--;
            }
        } else {
            this.doNotMiningCd -= 1;
        }
        if (this.ticksBeforeCanMine > 0) {
            List<AZombieBase> aZombieBaseList = this.taskOwner.worldObj.getEntitiesWithinAABB(AZombieBase.class, this.taskOwner.boundingBox.expand(1.0f, 1.0f, 1.0f));
            this.ticksBeforeCanMine -= (1 + Math.max(aZombieBaseList.size() - 1, 0) / 2);
            if (this.taskOwner.getHealth() <= this.taskOwner.getMaxHealth() / 2) {
                this.ticksBeforeCanMine = 0;
            }
            return false;
        }
        return entitylivingbase != null && entitylivingbase.isEntityAlive();
    }

    @SuppressWarnings("unchecked")
    public boolean continueExecuting() {
        EntityLivingBase entitylivingbase = this.taskOwner.getAttackTarget();
        if (entitylivingbase != null && entitylivingbase.isEntityAlive()) {
            if (this.mobStrength < this.optimalStrength()) {
                if (this.taskOwner.ticksExisted > 0 && this.taskOwner.ticksExisted % 600 == 0) {
                    List<AZombieBase> aZombieBaseList = this.taskOwner.worldObj.getEntitiesWithinAABB(AZombieBase.class, this.taskOwner.boundingBox.expand(1.0f, 1.0f, 1.0f));
                    if (!aZombieBaseList.isEmpty()) {
                        this.mobStrength = Math.min(this.mobStrength + (aZombieBaseList.size() * (Main.rand.nextFloat() * 0.75f + 0.25f)), this.optimalStrength());
                    }
                }
            }
            return true;
        }
        return false;
    }

    public void updateTask() {
        EntityCreature ent = this.taskOwner;
        World world = ent.worldObj;
        int rotation = MathHelper.floor_double((double) (ent.rotationYawHead * 4.0F / 360.0F) + 0.5D) & 3;

        int plX = MathHelper.floor_double(ent.posX);
        int plY = (int) Math.round(ent.posY);
        int plZ = MathHelper.floor_double(ent.posZ);

        if (this.taskOwner.isOnLadder()) {
            Block block = world.getBlock(plX, plY + 2, plZ);
            if (block == Blocks.ladder) {
                return;
            }
        }

        int oldX = plX;
        int oldY = (int) (plY + 0.5f);
        int oldZ = plZ;

        switch (rotation) {
            case 0: {
                oldZ += 1;
                break;
            }
            case 1: {
                oldX -= 1;
                break;
            }
            case 2: {
                oldZ -= 1;
                break;
            }
            case 3: {
                oldX += 1;
                break;
            }
            default: {
                break;
            }
        }

        int atX = MathHelper.floor_double(ent.getAttackTarget().posX);
        int atY = (int) ent.getAttackTarget().posY;
        int atZ = MathHelper.floor_double(ent.getAttackTarget().posZ);

        BlockPos newPos = this.getDigPos(world, oldX, oldY, oldZ, plX, plY, plZ, atY, Way.DOWN);
        if (plX == atX && plZ == atZ) {
            newPos = this.getDigPos(world, oldX, oldY, oldZ, plX, plY, plZ, atY, Way.STRAIGHT_UP_DOWN);
        } else if (plY == atY) {
            newPos = this.getDigPos(world, oldX, oldY, oldZ, plX, plY, plZ, atY, Way.STRAIGHT);
        } else if (atY > plY) {
            newPos = this.getDigPos(world, oldX, oldY, oldZ, plX, plY, plZ, atY, Way.UP);
        }

        if (newPos != null) {
            int x = newPos.x;
            int y = newPos.y;
            int z = newPos.z;
            Block block = world.getBlock(x, y, z);
            if (!PluginUtils.isInPrivate2(this.taskOwner.worldObj, x, y, z)) {
                if (this.mineProgress++ >= 0) {
                    if (this.mineProgress >= 10) {
                        if (ent.ticksExisted % 5 == 0) {
                            ent.swingItem();
                            if (block == Blocks.trapdoor || block == Blocks.wooden_door || block == Blocks.fence_gate) {
                                world.playSoundAtEntity(ent, "mob.zombie.wood", 0.5F, 0.8F + Main.rand.nextFloat() * 0.2f);
                            } else {
                                world.playSoundAtEntity(ent, block.stepSound.getStepSound(), 0.5F, 0.5F);
                            }
                            NetworkHandler.NETWORK.sendToAllAround(new ParticleZombieBlockCrack(x, y, z), new NetworkRegistry.TargetPoint(this.taskOwner.dimension, x, y, z, 86));
                        }
                    }
                    if (this.mineProgress >= this.getBlockHardness(world, x, y, z)) {
                        if (block == Blocks.trapdoor || block == Blocks.wooden_door || block == Blocks.fence_gate) {
                            world.playSoundAtEntity(ent, "mob.zombie.woodbreak", 0.5F, 0.8F + Main.rand.nextFloat() * 0.2f);
                        }
                        world.breakBlock(x, y, z, false);
                        ent.swingItem();
                        this.mineProgress = block.getBlockHardness(world, x, y, z) > 0 ? (int) Math.max(this.mineProgress * -0.15f, -50) : 0;
                    }
                    this.doNotMiningCd = 100;
                }
            }
        }
    }

    public void startExecuting() {
        super.startExecuting();
    }

    protected enum Way {
        STRAIGHT_UP_DOWN,
        UP,
        DOWN,
        STRAIGHT
    }

    protected static class BlockPos {
        private final int x;
        private final int y;
        private final int z;

        public BlockPos(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}