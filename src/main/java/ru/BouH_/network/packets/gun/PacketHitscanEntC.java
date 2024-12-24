package ru.BouH_.network.packets.gun;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import ru.BouH_.items.gun.render.tracer.RenderTracer;
import ru.BouH_.items.gun.tracer.RegisterHit;
import ru.BouH_.network.SimplePacket;

public class PacketHitscanEntC extends SimplePacket {
    public PacketHitscanEntC() {
    }

    public PacketHitscanEntC(int plId, int ent, boolean isHead, double x, double y, double z, boolean flag) {
        buf().writeInt(plId);
        buf().writeInt(ent);
        buf().writeBoolean(isHead);

        buf().writeDouble(x);
        buf().writeDouble(y);
        buf().writeDouble(z);

        buf().writeBoolean(flag);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void client(EntityPlayer player) {
        int plId = buf().readInt(), ent = buf().readInt();
        boolean isHead = buf().readBoolean();
        double x = buf().readDouble(), y = buf().readDouble(), z = buf().readDouble();
        boolean flag = buf().readBoolean();
        if (player.worldObj.getEntityByID(plId) != null) {
            EntityPlayer entityPlayer = (EntityPlayer) player.worldObj.getEntityByID(plId);
            if (player.worldObj.getEntityByID(ent) != null) {
                Entity entity = player.worldObj.getEntityByID(ent);
                EntityLivingBase entityLivingBase = (EntityLivingBase) entity;
                RegisterHit.spawnBulletBlood(player.worldObj, player, entityLivingBase, RegisterHit.checkHeadShot(entityLivingBase, y), x, y, z);
            }
            RenderTracer.register(new RenderTracer(entityPlayer, entityPlayer instanceof EntityPlayerSP || flag, x, y, z));
        }
    }
}
