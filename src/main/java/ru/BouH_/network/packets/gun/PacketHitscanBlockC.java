package ru.BouH_.network.packets.gun;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import ru.BouH_.items.gun.render.tracer.RenderTracer;
import ru.BouH_.items.gun.tracer.RegisterHit;
import ru.BouH_.network.SimplePacket;

public class PacketHitscanBlockC extends SimplePacket {
    public PacketHitscanBlockC() {
    }

    public PacketHitscanBlockC(int plId, int blockX, int blockY, int blockZ, int side, double x, double y, double z, boolean flag) {
        buf().writeInt(plId);
        buf().writeInt(blockX);
        buf().writeInt(blockY);
        buf().writeInt(blockZ);
        buf().writeInt(side);

        buf().writeDouble(x);
        buf().writeDouble(y);
        buf().writeDouble(z);

        buf().writeBoolean(flag);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void client(EntityPlayer player) {
        int plId = buf().readInt(), blockX = buf().readInt(), blockY = buf().readInt(), blockZ = buf().readInt(), side = buf().readInt();
        double x = buf().readDouble(), y = buf().readDouble(), z = buf().readDouble();
        boolean flag = buf().readBoolean();
        if (player.worldObj.getEntityByID(plId) != null) {
            EntityPlayer entityPlayer = (EntityPlayer) player.worldObj.getEntityByID(plId);
            RegisterHit.spawnBulletHitParticles(player.worldObj, player, blockX, blockY, blockZ, x, y, z, side);
            RenderTracer.register(new RenderTracer(entityPlayer, entityPlayer instanceof EntityPlayerSP || flag, x, y, z));
        }
    }
}
