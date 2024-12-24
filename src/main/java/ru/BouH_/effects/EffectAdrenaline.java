package ru.BouH_.effects;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import ru.BouH_.gameplay.client.RenderManager;

public class EffectAdrenaline extends Potion {
    public EffectAdrenaline(int Id, boolean isBad, int color) {
        super(Id, isBad, color);
        this.setPotionName("effect.adrenaline");
        this.setIconIndex(5, 0);
    }

    @Override
    public boolean isReady(int time, int mod) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public int getStatusIconIndex() {
        Minecraft.getMinecraft().renderEngine.bindTexture(RenderManager.effectIcons);
        return super.getStatusIconIndex();
    }

    @SubscribeEvent
    public void onHurt(LivingHurtEvent ev) {
        if (ev.source.getEntity() instanceof EntityPlayer) {
            EntityPlayer pl = (EntityPlayer) ev.source.getEntity();
            if (pl.getActivePotionEffect(this) != null) {
                ev.entityLiving.hurtResistantTime = 18;
            }
        }
    }
}
