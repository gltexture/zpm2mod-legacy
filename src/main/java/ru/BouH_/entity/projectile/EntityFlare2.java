package ru.BouH_.entity.projectile;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.blocks.lootCases.EnumLootGroups;
import ru.BouH_.entity.particle.EntityParticleColoredCloud;
import ru.BouH_.entity.particle.EntityParticleFlareFlame2;
import ru.BouH_.tiles.loot_spawn.LootSpawnManager;
import ru.BouH_.utils.SoundUtils;

import java.util.ArrayList;
import java.util.List;

public class EntityFlare2 extends Entity {
    private int ticksOnGround;

    public EntityFlare2(World p_i1776_1_) {
        super(p_i1776_1_);
    }

    public EntityFlare2(World p_i1778_1_, double p_i1778_2_, double p_i1778_4_, double p_i1778_6_) {
        super(p_i1778_1_);
        this.setSize(0.25F, 0.25F);
        this.setPosition(p_i1778_2_, p_i1778_4_, p_i1778_6_);
        this.yOffset = 0.0F;
    }

    @Override
    protected void entityInit() {

    }

    @SideOnly(Side.CLIENT)
    public void spawnParticles() {
        for (int i = 0; i < 2; i++) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleFlareFlame2(this.worldObj, this.posX, this.posY, this.posZ));
        }
    }

    @SideOnly(Side.CLIENT)
    public void spawnParticles2() {
        for (int i = 0; i < 4; i++) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleColoredCloud(this.worldObj, this.posX, this.posY, this.posZ, Main.rand.nextGaussian() * 0.03D, Main.rand.nextGaussian() * 0.03D, Main.rand.nextGaussian() * 0.03D, new float[]{0.9f, 0.9f, 0.9f}, 1.2f - rand.nextFloat() * 0.4f));
        }
    }

    public void onUpdate() {
        super.onUpdate();
        int x = MathHelper.floor_double(this.posX);
        int z = MathHelper.floor_double(this.posZ);
        int y = (int) this.posY;

        if (this.inWater) {
            this.onGround = true;
            if (this.ticksOnGround == 0) {
                this.spawnCase(x, y + 1, z);
                this.worldObj.setBlock(x, y, z, Blocks.hardened_clay);
                this.setPosition(this.posX, y + 2, this.posZ);
                this.ticksOnGround += 1;
            }
        }
        if (this.onGround) {
            if (this.ticksOnGround == 0) {
                this.spawnCase(x, y, z);
                this.setPosition(this.posX, y + 1, this.posZ);
            }
            if (this.ticksOnGround++ >= 600) {
                this.setDead();
            }
        }

        if (this.worldObj.isRemote) {
            if (this.worldObj.getBlock(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY + 0.5f), MathHelper.floor_double(this.posZ)).getMaterial() == Material.water) {
                if (this.ticksExisted == 1 || this.ticksExisted % 4 == 0) {
                    SoundUtils.playClientMovingSound(this, "random.fizz", 0.6f, 1.0f);
                }
                this.spawnParticles2();
            }
            this.spawnParticles();
            this.playSound();
        }

        this.motionY = -this.getGravityVelocity();
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        if (this.onGround) {
            this.motionX *= 0.7D;
            this.motionZ *= 0.7D;
        }
    }

    private void spawnCase(int x, int y, int z) {
        this.worldObj.setBlock(x, y, z, Blocks.chest);
        TileEntityChest tileEntityChest = (TileEntityChest) this.worldObj.getTileEntity(x, y, z);
        for (int i = 0; i < 10; i++) {
            List<LootSpawnManager> list = new ArrayList<>(EnumLootGroups.getLootGroupById(Main.rand.nextInt(EnumLootGroups.values().length)).getLSP());
            ItemStack itemStack = list.get(Main.rand.nextInt(list.size())).getRandomItemStack();
            if (itemStack != null) {
                int j = Main.rand.nextInt(tileEntityChest.getSizeInventory());
                while (tileEntityChest.getStackInSlot(j) == null) {
                    tileEntityChest.setInventorySlotContents(j, itemStack);
                }
            }
        }
        tileEntityChest.markDirty();
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound tagCompund) {
        this.ticksOnGround = tagCompund.getInteger("ticksOnGround");
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound tagCompound) {
        tagCompound.setInteger("ticksOnGround", this.ticksOnGround);
    }

    protected float getGravityVelocity() {
        return 2.5e-1f;
    }

    @SideOnly(Side.CLIENT)
    public void playSound() {
        if (this.ticksExisted == 1 || this.ticksExisted % 25 == 0) {
            SoundUtils.playClientMovingSound(this, Main.MODID + ":flare", 1.5f, 4.0f);
        }
    }
}
