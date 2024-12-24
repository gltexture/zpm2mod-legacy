package ru.BouH_.misc;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class MovingSoundRepeatingEntity extends MovingSoundEntity {
    public MovingSoundRepeatingEntity(Entity p_i45105_1_, ResourceLocation resourceLocation, float pitch, float volume) {
        super(p_i45105_1_, resourceLocation, pitch, volume);
    }

    public boolean canRepeat() {
        return true;
    }
}