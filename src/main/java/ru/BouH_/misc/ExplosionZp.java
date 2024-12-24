package ru.BouH_.misc;

import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import ru.BouH_.ConfigZp;
import ru.BouH_.Main;
import ru.BouH_.blocks.gas.BlockGasBase;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.packets.particles.ParticleExplosion;
import ru.BouH_.utils.EntityUtils;
import ru.BouH_.utils.PluginUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class ExplosionZp extends Explosion {
    public World worldObj;
    public List affectedBlockPositions = new ArrayList<>();
    public EntityPlayer player;

    public ExplosionZp(World p_i1948_1_, Entity p_i1948_2_, double p_i1948_3_, double p_i1948_5_, double p_i1948_7_, float p_i1948_9_) {
        this(p_i1948_1_, null, p_i1948_2_, p_i1948_3_, p_i1948_5_, p_i1948_7_, p_i1948_9_);
    }

    public ExplosionZp(World p_i1948_1_, EntityPlayer player, Entity p_i1948_2_, double p_i1948_3_, double p_i1948_5_, double p_i1948_7_, float p_i1948_9_) {
        super(p_i1948_1_, p_i1948_2_, p_i1948_3_, p_i1948_5_, p_i1948_7_, p_i1948_9_);
        this.worldObj = p_i1948_1_;
        this.player = player;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public void doExplosionA() {
        if (!this.worldObj.isRemote) {
            float f = this.explosionSize;
            HashSet<ChunkPosition> hashset = new HashSet<>();
            int i;
            int j;
            int k;
            double d5;
            double d6;
            double d7;
            if (ConfigZp.explosionDestruction) {
                int field_77289_h = (int) Math.min(this.explosionSize * 2.0f, 64);
                for (i = 0; i < field_77289_h; ++i) {
                    for (j = 0; j < field_77289_h; ++j) {
                        for (k = 0; k < field_77289_h; ++k) {
                            if (i == 0 || i == field_77289_h - 1 || j == 0 || j == field_77289_h - 1 || k == 0 || k == field_77289_h - 1) {
                                double d0 = (float) i / ((float) field_77289_h - 1.0F) * 2.0F - 1.0F;
                                double d1 = (float) j / ((float) field_77289_h - 1.0F) * 2.0F - 1.0F;
                                double d2 = (float) k / ((float) field_77289_h - 1.0F) * 2.0F - 1.0F;
                                double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                                d0 /= d3;
                                d1 /= d3;
                                d2 /= d3;
                                float f1 = this.explosionSize * (0.7F + this.worldObj.rand.nextFloat() * 0.6F);
                                d5 = this.explosionX;
                                d6 = this.explosionY;
                                d7 = this.explosionZ;

                                for (float f2 = 0.3F; f1 > 0.0F; f1 -= f2 * 0.75F) {
                                    int j1 = MathHelper.floor_double(d5);
                                    int k1 = MathHelper.floor_double(d6);
                                    int l1 = MathHelper.floor_double(d7);
                                    Block block = this.worldObj.getBlock(j1, k1, l1);

                                    if (block.getMaterial() != Material.air) {
                                        float f3 = this.exploder != null ? this.exploder.getExplosionResistance(this, this.worldObj, j1, k1, l1, block) : block.getExplosionResistance(null, worldObj, j1, k1, l1, explosionX, explosionY, explosionZ);
                                        f1 -= (f3 + 0.3F) * f2;
                                    }

                                    if (f1 > 0.0F && (this.exploder == null || this.exploder.func_145774_a(this, this.worldObj, j1, k1, l1, block, f1))) {
                                        hashset.add(new ChunkPosition(j1, k1, l1));
                                    }

                                    d5 += d0 * (double) f2;
                                    d6 += d1 * (double) f2;
                                    d7 += d2 * (double) f2;
                                }
                            }
                        }
                    }
                }
                this.affectedBlockPositions.addAll(hashset);
            }
            this.postExplosion();
            this.explosionSize *= 2.0F;
            i = MathHelper.floor_double(this.explosionX - (double) this.explosionSize - 1.0D);
            j = MathHelper.floor_double(this.explosionX + (double) this.explosionSize + 1.0D);
            k = MathHelper.floor_double(this.explosionY - (double) this.explosionSize - 1.0D);
            int i2 = MathHelper.floor_double(this.explosionY + (double) this.explosionSize + 1.0D);
            int l = MathHelper.floor_double(this.explosionZ - (double) this.explosionSize - 1.0D);
            int j2 = MathHelper.floor_double(this.explosionZ + (double) this.explosionSize + 1.0D);
            List list = this.worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(i, k, l, j, i2, j2));
            net.minecraftforge.event.ForgeEventFactory.onExplosionDetonate(this.worldObj, this, list, this.explosionSize);
            Vec3 vec3 = Vec3.createVectorHelper(this.explosionX, this.explosionY, this.explosionZ);

            for (Object value : list) {
                Entity entity = (Entity) value;
                if (entity != null) {
                    double d4 = entity.getDistance(this.explosionX, this.explosionY, this.explosionZ) / (double) this.explosionSize;

                    if (d4 <= 1.5D) {
                        d5 = entity.posX - this.explosionX;
                        d6 = entity.posY + (double) entity.getEyeHeight() - this.explosionY;
                        d7 = entity.posZ - this.explosionZ;
                        double d9 = MathHelper.sqrt_double(d5 * d5 + d6 * d6 + d7 * d7);

                        if (d9 != 0.0D) {
                            d5 /= d9;
                            d6 /= d9;
                            d7 /= d9;
                            double d10 = entity.worldObj.getBlockDensity(vec3, entity.boundingBox);
                            double d11 = (1.5D - d4) * d10;
                            float dmg = (float) ((int) ((d11 * d11 + d11) * 4.8D * (double) this.explosionSize + 1.0D));
                            float dmgAfterCalculations = EntityUtils.armorCalculations(dmg, entity);
                            float velocity = 0.1f;
                            boolean flag = false;
                            if (this.exploder instanceof EntityTNTPrimed) {
                                EntityLivingBase entityLivingBase = ((EntityTNTPrimed) exploder).getTntPlacedBy();
                                if (entityLivingBase instanceof EntityPlayer) {
                                    this.player = (EntityPlayer) entityLivingBase;
                                }
                            }
                            if (entity instanceof EntityPlayer) {
                                if (this.player != null) {
                                    flag = !PluginUtils.canDamage(this.player, entity) || !PluginUtils.canDamage(entity, this.player);
                                }
                            }

                            DamageSource damageSource = DamageSourceZp.explosionNew;
                            if (this.player != null) {
                                damageSource = DamageSourceZp.causePlayerExplosionDamage(player);
                            }

                            if (!flag) {
                                if (entity instanceof EntityPlayer) {
                                    ((EntityPlayer) entity).inventory.damageArmor(dmg * 1.8f + 4.0f);
                                }
                                this.func_77277_b().put(entity, Vec3.createVectorHelper(d5 * d11 * velocity, d6 * d11 * velocity, d7 * d11 * velocity));
                                entity.attackEntityFrom(damageSource, (float) EnchantmentProtection.func_92092_a(entity, dmgAfterCalculations));
                            }

                            entity.motionX += d5 * velocity;
                            entity.motionY += d6 * velocity;
                            entity.motionZ += d7 * velocity;
                        }
                    }
                }
            }
            this.explosionSize = f;
        }
    }

    public void doExplosionB(boolean p_77279_1_) {
    }

    protected void explosionEffect() {
        if (!this.worldObj.isRemote) {
            NetworkHandler.NETWORK.sendToAllAround(new ParticleExplosion(this.explosionX, this.explosionY, this.explosionZ, this.explosionSize), new NetworkRegistry.TargetPoint(this.worldObj.provider.dimensionId, this.explosionX, this.explosionY, this.explosionZ, 256));
        }
    }

    @SuppressWarnings("unchecked")
    protected void postExplosion() {
        Iterator<ChunkPosition> iterator;
        ChunkPosition chunkposition;
        int i;
        int j;
        int k;
        Block block;
        this.explosionEffect();
        if (this.isSmoking) {
            iterator = this.affectedBlockPositions.iterator();
            while (iterator.hasNext()) {
                chunkposition = iterator.next();
                i = chunkposition.chunkPosX;
                j = chunkposition.chunkPosY;
                k = chunkposition.chunkPosZ;
                block = this.worldObj.getBlock(i, j, k);
                if (!(block instanceof BlockGasBase) && block.getMaterial() != Material.air) {
                    block.onBlockExploded(this.worldObj, i, j, k, this);
                }
            }
        }

        if (this.isFlaming) {
            iterator = this.affectedBlockPositions.iterator();

            while (iterator.hasNext()) {
                chunkposition = iterator.next();
                i = chunkposition.chunkPosX;
                j = chunkposition.chunkPosY;
                k = chunkposition.chunkPosZ;
                block = this.worldObj.getBlock(i, j, k);
                Block block1 = this.worldObj.getBlock(i, j - 1, k);

                if (block.getMaterial() == Material.air && block1.isFullBlock() && Main.rand.nextInt(3) == 0) {
                    this.worldObj.setBlock(i, j, k, Blocks.fire);
                }
            }
        }
    }
}
