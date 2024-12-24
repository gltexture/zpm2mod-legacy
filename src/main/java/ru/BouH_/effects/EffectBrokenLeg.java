package ru.BouH_.effects;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.Potion;
import ru.BouH_.gameplay.client.RenderManager;

public class EffectBrokenLeg extends Potion {
    public EffectBrokenLeg(int Id, boolean isBad, int color) {
        super(Id, isBad, color);
        this.setPotionName("effect.brokenleg");
        this.setIconIndex(3, 0);
        this.registerPotionAttributeModifier(SharedMonsterAttributes.movementSpeed, "7107DE5E-7CE8-4030-940E-514C1F160890", -0.07000000596046448D, 2);
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
