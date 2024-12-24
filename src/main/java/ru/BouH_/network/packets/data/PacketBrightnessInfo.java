package ru.BouH_.network.packets.data;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import ru.BouH_.network.SimplePacket;
import ru.BouH_.utils.RenderUtils;

public class PacketBrightnessInfo extends SimplePacket {

    public PacketBrightnessInfo() {
    }

    public PacketBrightnessInfo(boolean flag) {
        buf().writeBoolean(flag);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void client(EntityPlayer player) {
        RenderUtils.canBeBrightSetting = buf().readBoolean();
    }
}