package ru.BouH_.network.packets.particles;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import ru.BouH_.Main;
import ru.BouH_.entity.particle.EntityParticleColoredCloud;
import ru.BouH_.entity.particle.EntityParticleFlame;
import ru.BouH_.network.SimplePacket;
import ru.BouH_.utils.SoundUtils;

public final class ParticleExplosion extends SimplePacket {
    public ParticleExplosion() {
    }

    public ParticleExplosion(double x, double y, double z, double explosionSize) {
        buf().writeDouble(x);
        buf().writeDouble(y);
        buf().writeDouble(z);
        buf().writeDouble(explosionSize);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void client(EntityPlayer player) {
        double x = buf().readDouble(), y = buf().readDouble(), z = buf().readDouble(), explosionSize = buf().readDouble();
        float volume = (float) Math.min(explosionSize * 3.0f, 14.0f);
        SoundUtils.playClientSound(x, y, z, Main.MODID + ":explosion", volume, 0.85F);
        SoundUtils.playClientSound(x, y, z, Main.MODID + ":explosion_far", Math.min(volume * 2.0f, 24.0f), 0.85F);
        for (int i = 0; i < 10 * explosionSize; i++) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleColoredCloud(player.worldObj, x + (i * 0.1f) * (Main.rand.nextGaussian() - Main.rand.nextGaussian()), y + (i * 0.1f) * (Main.rand.nextGaussian() - Main.rand.nextGaussian()) + 0.5f, z + (i * 0.1f) * (Main.rand.nextGaussian() - Main.rand.nextGaussian()), 0, 0, 0, new float[]{0.9f, 0.85f, 0.85f}, Math.max((float) explosionSize * (0.25f + i * 0.002f), 2.0f)));
        }
        if (Minecraft.getMinecraft().gameSettings.fancyGraphics) {
            if (!player.worldObj.getBlock((int) x, (int) y, (int) z).getMaterial().isLiquid()) {
                for (int i = 0; i < 15 * explosionSize; i++) {
                    Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleFlame(player.worldObj, x + ((i * 0.15f) * (Main.rand.nextGaussian() - Main.rand.nextGaussian())) * 0.1f, y + ((i * 0.15f) * (Main.rand.nextGaussian() - Main.rand.nextGaussian())) * 0.1f + 0.5f, z + ((i * 0.15f) * (Main.rand.nextGaussian() - Main.rand.nextGaussian())) * 0.1f, (Main.rand.nextGaussian() - Main.rand.nextGaussian()) * 0.1f, (Main.rand.nextGaussian() - Main.rand.nextGaussian()) * 0.1f, (Main.rand.nextGaussian() - Main.rand.nextGaussian()) * 0.1f, Math.max((float) explosionSize * (1.0f + i * 0.005f), 8.0f)));
                }
            }
        }
    }
}