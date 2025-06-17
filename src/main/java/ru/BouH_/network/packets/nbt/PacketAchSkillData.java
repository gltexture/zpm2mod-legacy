package ru.BouH_.network.packets.nbt;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import ru.BouH_.achievements.AchievementManager;
import ru.BouH_.achievements.AchievementZp;
import ru.BouH_.network.SimplePacket;
import ru.BouH_.render.gui.GuiInGameMenuZp;
import ru.BouH_.skills.SkillManager;
import ru.BouH_.skills.SkillZp;

public class PacketAchSkillData extends SimplePacket {

    public PacketAchSkillData() {
    }

    public PacketAchSkillData(int entId, NBTTagCompound nbtTagCompound, boolean b0, boolean b1, boolean b2) {
        buf().writeInt(entId);
        ByteBufUtils.writeTag(buf(), nbtTagCompound);
        buf().writeBoolean(b0);
        buf().writeBoolean(b1);
        buf().writeBoolean(b2);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void client(EntityPlayer player) {
        int id = buf().readInt();
        NBTTagCompound nbtTagCompound = ByteBufUtils.readTag(buf());
        boolean b0 = buf().readBoolean();
        boolean b1 = buf().readBoolean();
        boolean b2 = buf().readBoolean();
        GuiInGameMenuZp.skillsCrafts = b0;
        GuiInGameMenuZp.skillsProgression = b1;
        GuiInGameMenuZp.achievementsEnabled = b2;
        if (player.worldObj.getEntityByID(id) != null) {
            EntityPlayer pl = (EntityPlayer) player.worldObj.getEntityByID(id);
            for (AchievementZp achievementZp : AchievementManager.instance.getAchievementZpList()) {
                if (nbtTagCompound.hasKey(achievementZp.getNBT())) {
                    pl.getEntityData().setByte(achievementZp.getNBT(), nbtTagCompound.getByte(achievementZp.getNBT()));
                }
            }
            for (SkillZp skillZp : SkillManager.instance.getSkillZpList()) {
                if (nbtTagCompound.hasKey(skillZp.getNBT())) {
                    pl.getEntityData().setInteger(skillZp.getNBT(), nbtTagCompound.getInteger(skillZp.getNBT()));
                }
            }
        }
    }
}