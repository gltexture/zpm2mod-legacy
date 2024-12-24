package ru.BouH_.network.packets.nbt;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import ru.BouH_.entity.ieep.PlayerMiscData;
import ru.BouH_.network.SimplePacket;
import ru.BouH_.skills.SkillManager;

public class PacketSkillProgressData extends SimplePacket {

    public PacketSkillProgressData() {
    }

    public PacketSkillProgressData(int entId, int skillId, float f1) {
        buf().writeInt(entId);
        buf().writeInt(skillId);
        buf().writeFloat(f1);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void client(EntityPlayer player) {
        int id = buf().readInt();
        int i1 = buf().readInt();
        float f1 = buf().readFloat();
        if (player.worldObj.getEntityByID(id) != null) {
            EntityPlayer pl = (EntityPlayer) player.worldObj.getEntityByID(id);
            PlayerMiscData.getPlayerData(pl).getSkillProgressProfiler().setProgress(SkillManager.instance.findSkillById(i1), pl, f1);
        }
    }
}