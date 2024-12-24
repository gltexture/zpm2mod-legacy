package ru.BouH_.network.packets.particles;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityExplodeFX;
import net.minecraft.entity.player.EntityPlayer;
import ru.BouH_.Main;
import ru.BouH_.network.SimplePacket;

public final class ParticleBookExplode extends SimplePacket {
    public ParticleBookExplode() {
    }

    public ParticleBookExplode(double x, double y, double z) {
        buf().writeDouble(x);
        buf().writeDouble(y);
        buf().writeDouble(z);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void client(EntityPlayer player) {
        double x = buf().readDouble(), y = buf().readDouble(), z = buf().readDouble();
        for (int i = 0; i < 10; i++) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityExplodeFX(Minecraft.getMinecraft().thePlayer.worldObj, x + Main.rand.nextFloat(), y + Main.rand.nextFloat(), z + Main.rand.nextFloat(), (Main.rand.nextFloat() - Main.rand.nextFloat()) * 0.2f, 0.2, (Main.rand.nextFloat() - Main.rand.nextFloat()) * 0.2f));
        }
    }
}