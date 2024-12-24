package ru.BouH_.achievements;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.event.HoverEvent;
import net.minecraft.init.Items;
import net.minecraft.util.*;
import ru.BouH_.ConfigZp;
import ru.BouH_.init.ItemsZp;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.packets.nbt.PacketTriggerAchievement;
import ru.BouH_.notifications.NotificationManager;

import java.util.ArrayList;
import java.util.List;

public class AchievementManager {
    public static AchievementManager instance = new AchievementManager();
    public final AchievementZp study;
    public final AchievementZp enterWorld;
    public final AchievementZp firstShot;
    public final AchievementZp firstBlood;
    public final AchievementZp firstZombie;
    public final AchievementZp badCan;
    public final AchievementZp grenade;
    public final AchievementZp radiation;
    public final AchievementZp goodChemist;
    public final AchievementZp doctor;
    public final AchievementZp artillery;
    public final AchievementZp airdrop;
    public final AchievementZp antiZombie;
    public final AchievementZp armored;
    public final AchievementZp city;
    public final AchievementZp workbench;
    public final AchievementZp iron;
    public final AchievementZp diamond;
    public final AchievementZp steel;
    public final AchievementZp leg;
    public final AchievementZp alcohol;
    public final AchievementZp pvz;
    public final AchievementZp secretZombie;
    public final AchievementZp secretLoc;
    public final AchievementZp secretMaster;
    public final AchievementZp uranium;
    public final AchievementZp skillMaster;
    public final AchievementZp lockpick;
    public final AchievementZp strongZm;
    public final AchievementZp dope;
    public final AchievementZp furnace;
    private final List<AchievementZp> achievementZpList = new ArrayList<>();

    public AchievementManager() {
        this.enterWorld = this.createAchievement(TierEnum.LVL_ONE, "achievement.zp.enterWorld", "achievement.zp.enterWorld_desc");
        this.firstShot = this.createAchievement(TierEnum.LVL_ONE, "achievement.zp.firstShot", "achievement.zp.firstShot_desc");
        this.firstBlood = this.createAchievement(TierEnum.LVL_ONE, "achievement.zp.firstBlood", "achievement.zp.firstBlood_desc");
        this.firstZombie = this.createAchievement(TierEnum.LVL_ONE, "achievement.zp.firstZombie", "achievement.zp.firstZombie_desc");
        this.doctor = this.createAchievement(TierEnum.LVL_ONE, "achievement.zp.doctor", "achievement.zp.doctor_desc");
        this.armored = this.createAchievement(TierEnum.LVL_ONE, "achievement.zp.armored", "achievement.zp.armored_desc");
        this.workbench = this.createAchievement(TierEnum.LVL_ONE, "achievement.zp.workbench", "achievement.zp.workbench_desc");
        this.furnace = this.createAchievement(TierEnum.LVL_ONE, "achievement.zp.furnace", "achievement.zp.furnace_desc");
        this.iron = this.createAchievement(TierEnum.LVL_ONE, "achievement.zp.iron", "achievement.zp.iron_desc");
        this.steel = this.createAchievement(TierEnum.LVL_ONE, "achievement.zp.steel", "achievement.zp.steel_desc");
        this.diamond = this.createAchievement(TierEnum.LVL_ONE, "achievement.zp.diamond", "achievement.zp.diamond_desc");
        this.alcohol = this.createAchievement(TierEnum.LVL_ONE, "achievement.zp.alcohol", "achievement.zp.alcohol_desc");
        this.dope = this.createAchievement(TierEnum.LVL_ONE, "achievement.zp.dope", "achievement.zp.dope_desc");
        this.study = this.createAchievement(TierEnum.LVL_ONE, "achievement.zp.study", "achievement.zp.study_desc");
        this.leg = this.createAchievement(TierEnum.LVL_TWO, "achievement.zp.leg", "achievement.zp.leg_desc");
        this.strongZm = this.createAchievement(TierEnum.LVL_TWO, "achievement.zp.strongZm", "achievement.zp.strongZm_desc");
        this.city = this.createAchievement(TierEnum.LVL_TWO, "achievement.zp.city", "achievement.zp.city_desc");
        this.badCan = this.createAchievement(TierEnum.LVL_TWO, "achievement.zp.badCan", "achievement.zp.badCan_desc");
        this.radiation = this.createAchievement(TierEnum.LVL_TWO, "achievement.zp.radiation", "achievement.zp.radiation_desc");
        this.airdrop = this.createAchievement(TierEnum.LVL_TWO, "achievement.zp.airdrop", "achievement.zp.airdrop_desc");
        this.grenade = this.createAchievement(TierEnum.LVL_THREE, "achievement.zp.grenade", "achievement.zp.grenade_desc");
        this.lockpick = this.createAchievement(TierEnum.LVL_THREE, "achievement.zp.lockpick", "achievement.zp.lockpick_desc");
        this.antiZombie = this.createAchievement(TierEnum.LVL_THREE, "achievement.zp.antiZombie", "achievement.zp.antiZombie_desc");
        this.artillery = this.createAchievement(TierEnum.LVL_THREE, "achievement.zp.artillery", "achievement.zp.artillery_desc");
        this.uranium = this.createAchievement(TierEnum.LVL_THREE, "achievement.zp.uranium", "achievement.zp.uranium_desc");
        this.goodChemist = this.createAchievement(TierEnum.LVL_THREE, "achievement.zp.goodChemist", "achievement.zp.goodChemist_desc");
        this.pvz = this.createAchievement(TierEnum.LVL_THREE, "achievement.zp.pvz", "achievement.zp.pvz_desc");
        this.skillMaster = this.createAchievement(TierEnum.LVL_SECRET, "achievement.zp.skillMaster", "achievement.zp.skillMaster_desc");
        this.secretLoc = this.createAchievement(TierEnum.LVL_SECRET, "achievement.zp.secretLoc", "achievement.zp.secretLoc_desc");
        this.secretMaster = this.createAchievement(TierEnum.LVL_SECRET, "achievement.zp.secretMaster", "achievement.zp.secretMaster_desc");
        this.secretZombie = this.createAchievement(TierEnum.LVL_SECRET, "achievement.zp.secretZombie", "achievement.zp.secretZombie_desc");
    }

    public void initIcons() {
        this.setAchievementsIcons();
    }

    @SideOnly(Side.CLIENT)
    public void setAchievementsIcons() {
        this.enterWorld.setLogo(Items.wooden_sword);
        this.firstShot.setLogo(ItemsZp._9mm);
        this.firstBlood.setLogo(ItemsZp.aid_kit);
        this.firstZombie.setLogo(ItemsZp.bat);
        this.secretLoc.setLogo(ItemsZp.burn);
        this.badCan.setLogo(ItemsZp.soup);
        this.doctor.setLogo(ItemsZp.bandage);
        this.radiation.setLogo(ItemsZp.antiradiation);
        this.grenade.setLogo(ItemsZp.frag_grenade);
        this.artillery.setLogo(ItemsZp.rpg28);
        this.goodChemist.setLogo(ItemsZp.chemicals2);
        this.armored.setLogo(ItemsZp.steel_chestplate);
        this.antiZombie.setLogo(ItemsZp.lucille);
        this.secretMaster.setLogo(Items.diamond);
        this.airdrop.setLogo(ItemsZp.flare);
        this.leg.setLogo(ItemsZp.morphine);
        this.secretZombie.setLogo(Items.rotten_flesh);
        this.city.setLogo(ItemsZp.old_backpack);
        this.workbench.setLogo(ItemsZp.wrench);
        this.alcohol.setLogo(ItemsZp.vodka);
        this.iron.setLogo(Items.iron_ingot);
        this.diamond.setLogo(Items.diamond);
        this.steel.setLogo(ItemsZp.steel_ingot);
        this.pvz.setLogo(Items.potato);
        this.uranium.setLogo(ItemsZp.uran_material);
        this.skillMaster.setLogo(Items.potionitem);
        this.lockpick.setLogo(ItemsZp.lockpick);
        this.strongZm.setLogo(Items.diamond_sword);
        this.dope.setLogo(ItemsZp.meth);
        this.furnace.setLogo(ItemsZp.bellows);
        this.study.setLogo(Items.book);
    }

    private AchievementZp createAchievement(TierEnum tierEnum, String unlocalizedName, String unlocalizedDescription) {
        AchievementZp achievementZp = new AchievementZp(tierEnum, unlocalizedName, unlocalizedDescription);
        this.achievementZpList.add(achievementZp);
        return achievementZp;
    }

    public void createAchievementBase(EntityPlayer entityPlayer) {
        for (AchievementZp achievementZp : this.getAchievementZpList()) {
            if (!entityPlayer.getEntityData().hasKey(achievementZp.getNBT())) {
                entityPlayer.getEntityData().setByte(achievementZp.getNBT(), (byte) 0);
            }
        }
    }

    public AchievementZp findAchievementById(int id) {
        return this.getAchievementZpList().get(id);
    }

    public void triggerAchievement(AchievementZp achievementZp, EntityPlayer entityPlayer) {
        if (!entityPlayer.worldObj.isRemote) {
            if (ConfigZp.achievementsSystem) {
                if (!this.isAchievementActivated(achievementZp, entityPlayer) && !entityPlayer.capabilities.isCreativeMode) {
                    if (entityPlayer instanceof EntityPlayerMP) {
                        EntityPlayerMP entityPlayerMP = (EntityPlayerMP) entityPlayer;
                        NetworkHandler.NETWORK.sendTo(new PacketTriggerAchievement(achievementZp.getId()), entityPlayerMP);
                    }
                    if (entityPlayer.worldObj.getGameRules().getGameRuleBooleanValue("achievementMessagesForAll")) {
                        for (Object o : entityPlayer.worldObj.playerEntities) {
                            EntityPlayer entityPlayer1 = (EntityPlayer) o;
                            if (entityPlayer1 != null && entityPlayer1 != entityPlayer) {
                                IChatComponent iChatComponent0 = new ChatComponentText(entityPlayer.getDisplayName());
                                iChatComponent0.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.YELLOW));
                                IChatComponent iChatComponent = (new ChatComponentText("[")).appendSibling(new ChatComponentTranslation(achievementZp.getUnlocalizedName())).appendText("]");
                                iChatComponent.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.LIGHT_PURPLE));
                                iChatComponent.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentTranslation(achievementZp.getUnlocalizedDescription()).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GREEN))));
                                ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("achievement.zp.chat_message_all", iChatComponent0, iChatComponent);
                                entityPlayer1.addChatMessage(chatComponentTranslation);
                            }
                        }
                    }
                    switch (achievementZp.getLvlEnum()) {
                        case LVL_ONE: {
                            entityPlayer.addExperience(1);
                            break;
                        }
                        case LVL_TWO: {
                            entityPlayer.addExperience(30);
                            break;
                        }
                        case LVL_THREE: {
                            entityPlayer.addExperienceLevel(1);
                            break;
                        }
                        case LVL_SECRET: {
                            entityPlayer.addExperienceLevel(5);
                            break;
                        }
                    }
                    achievementZp.trigger(entityPlayer);
                }
                if (this.checkMaster(entityPlayer)) {
                    AchievementManager.instance.triggerAchievement(AchievementManager.instance.secretMaster, entityPlayer);
                }
            }
        } else {
            IChatComponent iChatComponent = (new ChatComponentText("[")).appendSibling(new ChatComponentText(achievementZp.getName())).appendText("]");
            iChatComponent.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.LIGHT_PURPLE));
            iChatComponent.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText(achievementZp.getDescription()).setChatStyle(new ChatStyle().setColor(EnumChatFormatting.GREEN))));
            NotificationManager.instance.setNotificationMessage(entityPlayer, new ChatComponentTranslation("achievement.zp.chat_message", iChatComponent));
            NotificationManager.instance.triggerNotification(achievementZp);
            achievementZp.trigger(entityPlayer);
        }
    }

    public int calcAchievements(EntityPlayer entityPlayer) {
        int i1 = 0;
        for (AchievementZp achievementZp : this.getAchievementZpList()) {
            if (this.isAchievementActivated(achievementZp, entityPlayer)) {
                i1 += 1;
            }
        }
        return i1;
    }

    private boolean checkMaster(EntityPlayer entityPlayer) {
        for (AchievementZp achievementZp : this.getAchievementZpList()) {
            if (!this.isAchievementActivated(achievementZp, entityPlayer)) {
                return false;
            }
        }
        return true;
    }

    //-2842831239605558169
    public boolean isAchievementActivated(AchievementZp achievementZp, EntityPlayer entityPlayer) {
        return entityPlayer.getEntityData().hasKey(achievementZp.getNBT()) && entityPlayer.getEntityData().getByte(achievementZp.getNBT()) == (byte) 1;
    }

    public List<AchievementZp> getAchievementZpList() {
        return this.achievementZpList;
    }
}
