package ru.BouH_.network.packets.particles;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import ru.BouH_.Main;
import ru.BouH_.entity.particle.EntityParticleBlood;
import ru.BouH_.network.SimplePacket;

public final class ParticleBlood2 extends SimplePacket {
    public ParticleBlood2() {
    }

    public ParticleBlood2(int id) {
        buf().writeInt(id);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void client(EntityPlayer player) {
        int id = buf().readInt();
        if (player.worldObj.getEntityByID(id) != null) {
            Entity entity = player.worldObj.getEntityByID(id);
            for (int is = 0; is < 12 + Main.rand.nextInt(4); is++) {
                Vec3 vec3 = Vec3.createVectorHelper(((double) Main.rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
                vec3.rotateAroundX(-entity.rotationPitch * (float) Math.PI / 180.0F);
                vec3.rotateAroundY(-entity.getRotationYawHead() * (float) Math.PI / 180.0F);
                Vec3 vec31 = Vec3.createVectorHelper(((double) Main.rand.nextFloat() - 0.5D) * 0.3D, (double) (-Main.rand.nextFloat()) * 0.6D - 0.3D, 0.6D);
                vec31.rotateAroundX(-entity.rotationPitch * (float) Math.PI / 180.0F);
                vec31.rotateAroundY(-entity.getRotationYawHead() * (float) Math.PI / 180.0F);
                vec31 = vec31.addVector(entity.posX, entity.posY + (double) entity.getEyeHeight(), entity.posZ);
                Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleBlood(entity.worldObj, vec31.xCoord, vec31.yCoord, vec31.zCoord, vec3.xCoord, vec3.yCoord + 0.05D, vec3.zCoord));
            }
        }
    }
}