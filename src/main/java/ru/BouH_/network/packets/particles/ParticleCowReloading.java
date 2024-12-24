package ru.BouH_.network.packets.particles;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import ru.BouH_.Main;
import ru.BouH_.network.SimplePacket;

public final class ParticleCowReloading extends SimplePacket {
    public ParticleCowReloading() {
    }

    public ParticleCowReloading(double x, double y, double z) {
        buf().writeDouble(x);
        buf().writeDouble(y);
        buf().writeDouble(z);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void client(EntityPlayer player) {
        double x = buf().readDouble(), y = buf().readDouble(), z = buf().readDouble();
        for (int i = 0; i < 10; i++) {
            double db0 = Main.rand.nextGaussian() * 0.03D;
            double db1 = Main.rand.nextGaussian() * 0.03D;
            double db2 = Main.rand.nextGaussian() * 0.03D;
            player.worldObj.spawnParticle("happyVillager", x + (double) Main.rand.nextFloat(), y + (double) Main.rand.nextFloat(), z + (double) Main.rand.nextFloat(), db0, db1, db2);
        }
    }
}