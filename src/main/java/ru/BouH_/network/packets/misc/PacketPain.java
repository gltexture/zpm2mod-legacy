package ru.BouH_.network.packets.misc;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import ru.BouH_.gameplay.client.PainUpdater;
import ru.BouH_.network.SimplePacket;

public class PacketPain extends SimplePacket {
    public PacketPain() {
    }

    public PacketPain(float str, boolean shouldStopUsing) {
        buf().writeFloat(str);
        buf().writeBoolean(shouldStopUsing);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void client(EntityPlayer player) {
        PainUpdater.instance.addPainUpdater(buf().readFloat());
        EntityPlayerSP entityPlayerSP = Minecraft.getMinecraft().thePlayer;
        if (buf().readBoolean()) {
            if (entityPlayerSP.getHeldItem() != null) {
                if (entityPlayerSP.isUsingItem()) {
                    if (entityPlayerSP.getHeldItem().getItem() instanceof ItemFood) {
                        entityPlayerSP.stopUsingItem();
                        entityPlayerSP.getEntityData().setInteger("itemUsed", 20);
                    }
                }
            }
        }
    }
}
