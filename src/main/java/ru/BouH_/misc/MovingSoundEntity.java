package ru.BouH_.misc;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@SideOnly(Side.CLIENT)
public class MovingSoundEntity extends MovingSound {
    private final Entity entity;
    public float pitch;

    public MovingSoundEntity(Entity p_i45105_1_, ResourceLocation resourceLocation, float pitch) {
        this(p_i45105_1_, resourceLocation, pitch, 3.0f);
    }

    public MovingSoundEntity(Entity p_i45105_1_, ResourceLocation resourceLocation, float pitch, float volume) {
        super(resourceLocation);
        this.entity = p_i45105_1_;
        this.pitch = pitch;
        this.volume = volume;
        this.attenuationType = AttenuationType.LINEAR;
    }

    public void update() {
        if (this.entity.isDead) {
            this.donePlaying = true;
        } else {
            this.xPosF = (float) this.entity.posX;
            this.yPosF = (float) this.entity.posY;
            this.zPosF = (float) this.entity.posZ;
        }
    }
}