package ru.BouH_.effects;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import ru.BouH_.gameplay.client.RenderManager;

public class EffectDehydration extends Potion {
    public EffectDehydration(int Id, boolean isBad, int color) {
        super(Id, isBad, color);
        this.setPotionName("effect.dehydration");
        this.setIconIndex(1, 1);
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
