package ru.BouH_.network.packets.particles;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import ru.BouH_.entity.particle.EntityParticleColoredCloud;
import ru.BouH_.network.SimplePacket;

public final class ParticleCloud extends SimplePacket {
    public ParticleCloud() {
    }

    public ParticleCloud(double x, double y, double z, double ox, double oy, double oz, float cl, float cl2, float cl3, float scale, int count) {
        buf().writeDouble(x);
        buf().writeDouble(y);
        buf().writeDouble(z);
        buf().writeDouble(ox);
        buf().writeDouble(oy);
        buf().writeDouble(oz);
        buf().writeFloat(cl);
        buf().writeFloat(cl2);
        buf().writeFloat(cl3);
        buf().writeFloat(scale);
        buf().writeInt(count);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void client(EntityPlayer player) {
        double x = buf().readDouble(), y = buf().readDouble(), z = buf().readDouble(), ox = buf().readDouble(), oy = buf().readDouble(), oz = buf().readDouble();
        float cl = buf().readFloat(), cl2 = buf().readFloat(), cl3 = buf().readFloat(), scale = buf().readFloat();
        int count = buf().readInt();
        for (int i = 0; i < count; i++) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleColoredCloud(player.worldObj, x, y, z, ox, oy, oz, new float[]{cl, cl2, cl3}, scale));
        }
    }
}