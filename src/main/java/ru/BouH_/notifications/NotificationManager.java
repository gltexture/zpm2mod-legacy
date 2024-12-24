package ru.BouH_.notifications;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IChatComponent;
import ru.BouH_.Main;
import ru.BouH_.achievements.AchievementZp;
import ru.BouH_.skills.SkillZp;

import java.util.ArrayDeque;
import java.util.Deque;

@SideOnly(Side.CLIENT)
public class NotificationManager {
    public static NotificationManager instance = new NotificationManager();
    private final Deque<INotification> notificationAchievementDeque = new ArrayDeque<>();

    public NotificationManager() {

    }

    public void setNotificationMessage(EntityPlayer entityPlayer, IChatComponent chatComponentText) {
        if (Main.settingsZp.notificationChat.isFlag()) {
            entityPlayer.addChatMessage(chatComponentText);
        }
    }

    public void triggerNotification(AchievementZp achievementZp) {
        this.triggerNotification(new NotificationAchievement(achievementZp));
    }

    public void triggerNotification(SkillZp skillZp, int lvl) {
        this.triggerNotification(new NotificationSkill(skillZp, lvl));
    }

    public void triggerNotification(SkillZp skillZp, boolean many) {
        this.triggerNotification(new NotificationNewRecipe(skillZp, many));
    }

    public void triggerNotification(INotification iNotification) {
        if (Main.settingsZp.notificationHud.isFlag()) {
            this.notificationAchievementDeque.add(iNotification);
        }
    }

    public INotification popNotification() {
        return this.getNotificationDeque().pop();
    }

    public boolean canPop() {
        return !this.getNotificationDeque().isEmpty();
    }

    public Deque<INotification> getNotificationDeque() {
        return this.notificationAchievementDeque;
    }
}
