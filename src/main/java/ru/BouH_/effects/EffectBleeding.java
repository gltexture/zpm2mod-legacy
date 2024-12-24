package ru.BouH_.effects;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;
import ru.BouH_.Main;
import ru.BouH_.entity.particle.EntityParticleBlood;
import ru.BouH_.gameplay.client.PainUpdater;
import ru.BouH_.gameplay.client.RenderManager;
import ru.BouH_.misc.DamageSourceZp;

public class EffectBleeding extends Potion {
    public EffectBleeding(int Id, boolean isBad, int color) {
        super(Id, isBad, color);
        this.setPotionName("effect.bleeding");
        this.setIconIndex(4, 0);
    }

    @Override
    public boolean isReady(int time, int mod) {
        return true;
    }

    @SubscribeEvent
    public void onEntityUpdate(LivingEvent.LivingUpdateEvent ev) {
        if (ev.entityLiving instanceof EntityPlayer && ev.entityLiving.getActivePotionEffect(this) != null) {
            if (!ev.entityLiving.worldObj.isRemote) {
                if (ev.entityLiving.ticksExisted % (40 / (ev.entityLiving.getActivePotionEffect(this).getAmplifier() + 1)) == 0) {
                    ev.entityLiving.attackEntityFrom(DamageSourceZp.blood, ev.entityLiving.getActivePotionEffect(this).getAmplifier() + 1);
                }
            } else {
                if (ev.entityLiving.getActivePotionEffect(this).getDuration() > 0 && ev.entityLiving.ticksExisted % 20 == 0) {
                    this.spawnParticles(ev.entityLiving);
                    if (ev.entityLiving instanceof EntityPlayerSP) {
                        PainUpdater.instance.addPainUpdater((ev.entityLiving.getActivePotionEffect(this).getAmplifier() + 1) * 0.15f);
                    }
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void spawnParticles(EntityLivingBase ent) {
        if (ent.getActivePotionEffect(this).getDuration() % 20 == 0) {
            for (int is = 0; is < 6 + Main.rand.nextInt(4); is++) {
                Vec3 vec3 = Vec3.createVectorHelper(((double) Main.rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
                vec3.rotateAroundX(-ent.rotationPitch * (float) Math.PI / 180.0F);
                vec3.rotateAroundY(-ent.getRotationYawHead() * (float) Math.PI / 180.0F);
                Vec3 vec31 = Vec3.createVectorHelper(((double) Main.rand.nextFloat() - 0.5D) * 0.3D, (double) (-Main.rand.nextFloat()) * 0.6D - 0.3D, 0.6D);
                vec31.rotateAroundX(-ent.rotationPitch * (float) Math.PI / 180.0F);
                vec31.rotateAroundY(-ent.getRotationYawHead() * (float) Math.PI / 180.0F);
                vec31 = vec31.addVector(ent.posX, ent.posY + (double) ent.getEyeHeight(), ent.posZ);
                Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleBlood(ent.worldObj, vec31.xCoord, vec31.yCoord, vec31.zCoord, vec3.xCoord, vec3.yCoord + 0.05D, vec3.zCoord));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getStatusIconIndex() {
        Minecraft.getMinecraft().renderEngine.bindTexture(RenderManager.effectIcons);
        return super.getStatusIconIndex();
    }
}
