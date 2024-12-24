package ru.BouH_.skills;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.util.*;
import ru.BouH_.ConfigZp;
import ru.BouH_.achievements.AchievementManager;
import ru.BouH_.init.ItemsZp;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.packets.nbt.PacketAddSkillPoints;
import ru.BouH_.notifications.NotificationManager;
import ru.BouH_.recipe_master.RecipeMaster;

import java.util.ArrayList;
import java.util.List;

public class SkillManager {
    public static SkillManager instance = new SkillManager();
    public final SkillZp hunter;
    public final SkillZp fisher;
    public final SkillZp gunSmith;
    public final SkillZp miner;
    public final SkillZp survivor;
    public final SkillZp farmer;
    public final int min;
    public final int max;
    private final List<SkillZp> skillZpList = new ArrayList<>();

    public SkillManager() {
        this.hunter = this.createSkill("skill.zp.hunter");
        this.fisher = this.createSkill("skill.zp.fisher");
        this.gunSmith = this.createSkill("skill.zp.gunSmith");
        this.miner = this.createSkill("skill.zp.miner");
        this.survivor = this.createSkill("skill.zp.survivor");
        this.farmer = this.createSkill("skill.zp.farmer");
        this.min = 0;
        this.max = 10;
    }

    public void initIcons() {
        this.setSkillIcons();
    }

    @SideOnly(Side.CLIENT)
    public void setSkillIcons() {
        this.hunter.setLogo(Items.bow);
        this.fisher.setLogo(Items.fishing_rod);
        this.gunSmith.setLogo(ItemsZp.pm);
        this.miner.setLogo(Items.iron_pickaxe);
        this.survivor.setLogo(Items.iron_sword);
        this.farmer.setLogo(Items.carrot);
    }

    @SideOnly(Side.CLIENT)
    public int getSkillPercentage(EntityPlayer entityPlayer, SkillZp skillZp) {
        int i1 = 0;
        if (skillZp == this.hunter) {
            i1 = 5;
        } else if (skillZp == this.miner) {
            i1 = 5;
        } else if (skillZp == this.fisher) {
            i1 = 4;
        } else if (skillZp == this.survivor) {
            i1 = 2;
        } else if (skillZp == this.gunSmith) {
            i1 = 3;
        } else if (skillZp == this.farmer) {
            i1 = 4;
        }
        return this.getSkillPoints(skillZp, entityPlayer) * i1;
    }

    private SkillZp createSkill(String unlocalizedName) {
        SkillZp skillZp = new SkillZp(unlocalizedName);
        this.skillZpList.add(skillZp);
        return skillZp;
    }

    public void createSkillBase(EntityPlayer entityPlayer) {
        for (SkillZp skillZp : this.getSkillZpList()) {
            if (!entityPlayer.getEntityData().hasKey(skillZp.getNBT())) {
                entityPlayer.getEntityData().setInteger(skillZp.getNBT(), 0);
            }
        }
    }

    public boolean isMaxSkill(SkillZp skillZp, EntityPlayer entityPlayer) {
        return this.getSkillPoints(skillZp, entityPlayer) == this.max;
    }

    public SkillZp findSkillById(int id) {
        return this.getSkillZpList().get(id);
    }

    public int getSkillPoints(SkillZp skillZp, EntityPlayer entityPlayer) {
        if (!ConfigZp.skillsSystem) {
            return 0;
        }
        if (entityPlayer.capabilities.isCreativeMode) {
            return this.max;
        }
        if (!entityPlayer.getEntityData().hasKey(skillZp.getNBT())) {
            return this.min;
        }
        return entityPlayer.getEntityData().getInteger(skillZp.getNBT());
    }

    public float getSkillBonus(SkillZp skillZp, EntityPlayer entityPlayer, float bonus) {
        if (ConfigZp.skillsSystem) {
            return this.getSkillPoints(skillZp, entityPlayer) * bonus;
        }
        return 0;
    }

    public boolean checkSkill(EntityPlayer entityPlayer, SkillZp skillZp, int lvl) {
        return this.getSkillPoints(skillZp, entityPlayer) >= lvl;
    }

    public void addSkillPoints(SkillZp skillZp, EntityPlayer entityPlayer, int points) {
        int i1 = MathHelper.clamp_int(entityPlayer.getEntityData().getInteger(skillZp.getNBT()) + points, this.min, this.max);
        if (this.getSkillPoints(skillZp, entityPlayer) != i1) {
            if (!entityPlayer.worldObj.isRemote) {
                if (entityPlayer instanceof EntityPlayerMP) {
                    EntityPlayerMP entityPlayerMP = (EntityPlayerMP) entityPlayer;
                    NetworkHandler.NETWORK.sendTo(new PacketAddSkillPoints(skillZp.getId(), points), entityPlayerMP);
                    AchievementManager.instance.triggerAchievement(AchievementManager.instance.study, entityPlayer);
                }
            } else {
                IChatComponent iChatComponent = (new ChatComponentText("[")).appendSibling(new ChatComponentText(skillZp.getName())).appendText("]");
                iChatComponent.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.LIGHT_PURPLE));
                NotificationManager.instance.setNotificationMessage(entityPlayer, new ChatComponentTranslation("skill.zp.chat_message", iChatComponent, new ChatComponentText(String.valueOf(i1)), new ChatComponentText(String.valueOf(this.max))));
                NotificationManager.instance.triggerNotification(skillZp, i1);
                int i2 = RecipeMaster.instance.checkNewRecipes(skillZp, this.getSkillPoints(skillZp, entityPlayer) + 1);
                if (i2 > 0) {
                    boolean many = i2 != 1;
                    iChatComponent = new ChatComponentTranslation(many ? I18n.format("recipes.zp.new_s") : I18n.format("recipes.zp.new"));
                    iChatComponent.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.LIGHT_PURPLE));
                    NotificationManager.instance.setNotificationMessage(entityPlayer, iChatComponent);
                    NotificationManager.instance.triggerNotification(skillZp, many);
                }
            }
            entityPlayer.getEntityData().setInteger(skillZp.getNBT(), i1);
        }
        boolean flag = true;
        for (SkillZp skillZp1 : this.getSkillZpList()) {
            if (!this.isMaxSkill(skillZp1, entityPlayer)) {
                flag = false;
                break;
            }
        }
        if (flag) {
            AchievementManager.instance.triggerAchievement(AchievementManager.instance.skillMaster, entityPlayer);
        }
    }

    public List<SkillZp> getSkillZpList() {
        return this.skillZpList;
    }
}
