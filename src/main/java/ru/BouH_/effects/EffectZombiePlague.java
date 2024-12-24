package ru.BouH_.effects;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityCreature;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.event.entity.living.LivingEvent;
import ru.BouH_.Main;
import ru.BouH_.gameplay.client.RenderManager;
import ru.BouH_.misc.DamageSourceZp;

public class EffectZombiePlague extends Potion {
    public EffectZombiePlague(int Id, boolean isBad, int color) {
        super(Id, isBad, color);
        this.setPotionName("effect.zombieplague");
        this.setIconIndex(2, 0);
    }

    @Override
    public boolean isReady(int time, int mod) {
        return true;
    }

    @SubscribeEvent
    public void onEntityUpdate(LivingEvent.LivingUpdateEvent ev) {
        if (ev.entityLiving.isEntityAlive() && ev.entityLiving.getActivePotionEffect(this) != null) {
            if (!ev.entityLiving.worldObj.isRemote) {
                if (ev.entityLiving.getActivePotionEffect(this).getDuration() <= 16000 && ev.entityLiving.getActivePotionEffect(this).getDuration() % 60 == 0 && Main.rand.nextBoolean()) {
                    ev.entityLiving.worldObj.playSoundAtEntity(ev.entityLiving, "mob.zombie.say", 1.0F, 0.9f + Main.rand.nextFloat() * 0.1f);
                }
                if (ev.entityLiving.getActivePotionEffect(this).getDuration() <= 16000) {
                    ev.entityLiving.addPotionEffect(new PotionEffect(4, 600));
                }
                if (ev.entityLiving.getActivePotionEffect(this).getDuration() <= 12000) {
                    ev.entityLiving.addPotionEffect(new PotionEffect(2, 600));
                }
                if (ev.entityLiving.getActivePotionEffect(this).getDuration() <= 6000) {
                    ev.entityLiving.addPotionEffect(new PotionEffect(9, 600));
                }
                if (ev.entityLiving.getActivePotionEffect(this).getDuration() == 1) {
                    ev.entityLiving.attackEntityFrom(DamageSourceZp.virus, 100);
                } else if (ev.entityLiving instanceof EntityCreature) {
                    if (ev.entityLiving.getActivePotionEffect(this).getDuration() == 1) {
                        ev.entityLiving.onKillEntity(null);
                    }
                }
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
