package ru.BouH_.network.packets.nbt;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import ru.BouH_.network.SimplePacket;
import ru.BouH_.skills.SkillManager;

public class PacketAddSkillPoints extends SimplePacket {

    public PacketAddSkillPoints() {
    }

    public PacketAddSkillPoints(int id, int p) {
        buf().writeInt(id);
        buf().writeInt(p);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void client(EntityPlayer player) {
        SkillManager.instance.addSkillPoints(SkillManager.instance.findSkillById(buf().readInt()), player, buf().readInt());
    }
}