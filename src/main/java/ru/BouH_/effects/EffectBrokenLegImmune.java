package ru.BouH_.effects;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import ru.BouH_.gameplay.client.RenderManager;

public class EffectBrokenLegImmune extends Potion {
    public EffectBrokenLegImmune(int Id, boolean isBad, int color) {
        super(Id, isBad, color);
        this.setPotionName("effect.brokenlegimmune");
        this.setIconIndex(6, 0);
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
}
