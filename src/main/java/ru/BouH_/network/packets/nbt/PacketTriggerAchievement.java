package ru.BouH_.network.packets.nbt;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import ru.BouH_.achievements.AchievementManager;
import ru.BouH_.network.SimplePacket;

public class PacketTriggerAchievement extends SimplePacket {

    public PacketTriggerAchievement() {
    }

    public PacketTriggerAchievement(int id) {
        buf().writeInt(id);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void client(EntityPlayer player) {
        AchievementManager.instance.triggerAchievement(AchievementManager.instance.findAchievementById(buf().readInt()), player);
    }
}