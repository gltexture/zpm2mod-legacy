package ru.BouH_.network.packets.particles;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import ru.BouH_.Main;
import ru.BouH_.entity.particle.EntityParticleBlockCrack;
import ru.BouH_.network.SimplePacket;

public class ParticleZombieBlockCrack extends SimplePacket {
    public ParticleZombieBlockCrack() {
    }

    public ParticleZombieBlockCrack(int x, int y, int z) {
        buf().writeInt(x);
        buf().writeInt(y);
        buf().writeInt(z);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void client(EntityPlayer player) {
        int x = buf().readInt(), y = buf().readInt(), z = buf().readInt();

        double newX = x + Main.rand.nextFloat();
        double newY = y + Main.rand.nextFloat();
        double newZ = z + Main.rand.nextFloat();
        for (int i = 0; i < 5 + Main.rand.nextInt(4); ++i) {
            if (player.worldObj.getBlock(x, y, z) != null) {
                Block block = player.worldObj.getBlock(x, y, z);
                int meta = player.worldObj.getBlockMetadata(x, y, z);
                if (!player.worldObj.getBlock(x, y + 1, z).getMaterial().isSolid()) {
                    Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleBlockCrack(player.worldObj, newX, y + 1.1f, newZ, 0, 0, 0, block, meta));
                }
                if (!player.worldObj.getBlock(x, y - 1, z).getMaterial().isSolid()) {
                    Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleBlockCrack(player.worldObj, newX, y - 0.1f, newZ, 0, 0, 0, block, meta));
                }
                if (!player.worldObj.getBlock(x, y, z - 1).getMaterial().isSolid()) {
                    Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleBlockCrack(player.worldObj, newX, newY, z - 0.1f, 0, 0, 0, block, meta));
                }
                if (!player.worldObj.getBlock(x, y, z + 1).getMaterial().isSolid()) {
                    Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleBlockCrack(player.worldObj, newX, newY, z + 1.1f, 0, 0, 0, block, meta));
                }
                if (!player.worldObj.getBlock(x + 1, y, z).getMaterial().isSolid()) {
                    Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleBlockCrack(player.worldObj, x + 1.1f, newY, newZ, 0, 0, 0, block, meta));
                }
                if (!player.worldObj.getBlock(x - 1, y, z).getMaterial().isSolid()) {
                    Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleBlockCrack(player.worldObj, x - 0.1f, newY, newZ, 0, 0, 0, block, meta));
                }
            }
        }

    }
}
