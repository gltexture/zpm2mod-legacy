package ru.BouH_.utils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import ru.BouH_.misc.MovingSoundEntity;
import ru.BouH_.misc.MovingSoundRepeatingEntity;

public class SoundUtils {
    @SideOnly(Side.CLIENT)
    public static void playMonoSound(String name) {
        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.createPositionedSoundRecord(new ResourceLocation(name)));
    }

    @SideOnly(Side.CLIENT)
    public static void playClientSound(double x, double y, double z, String resourceLocation, float volume, float pitch) {
        Minecraft.getMinecraft().theWorld.playSound(x, y - (double) Minecraft.getMinecraft().thePlayer.yOffset, z, resourceLocation, volume, pitch, false);
    }

    @SideOnly(Side.CLIENT)
    public static void playClientSound(Entity entity, String resourceLocation, float volume, float pitch) {
        Minecraft.getMinecraft().theWorld.playSound(entity.posX, entity.posY - (double) entity.yOffset, entity.posZ, resourceLocation, volume, pitch, false);
    }

    @SideOnly(Side.CLIENT)
    public static void playClientMovingSound(Entity entity, String resourceLocation, float pitch, float volume) {
        SoundHandler soundHandler = Minecraft.getMinecraft().getSoundHandler();
        MovingSoundEntity sound = new MovingSoundEntity(entity, new ResourceLocation(resourceLocation), pitch, volume);
        soundHandler.playSound(sound);
    }

    @SideOnly(Side.CLIENT)
    public static void playClientMovingRepeatingSound(Entity entity, String resourceLocation, float volume, float pitch) {
        SoundHandler soundHandler = Minecraft.getMinecraft().getSoundHandler();
        MovingSoundRepeatingEntity sound = new MovingSoundRepeatingEntity(entity, new ResourceLocation(resourceLocation), volume, pitch);
        soundHandler.playSound(sound);
    }

    public static void playSound(EntityPlayer player, String resourceLocation, float volume, float pitch) {
        if (!player.worldObj.isRemote) {
            player.worldObj.playSoundToNearExcept(player, resourceLocation, volume, pitch);
        } else if (player instanceof EntityPlayerSP) {
            playClientSound(player, resourceLocation, volume, pitch);
        }
    }
}
