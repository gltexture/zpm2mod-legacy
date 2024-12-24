package ru.BouH_.notifications;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import ru.BouH_.achievements.AchievementZp;
import ru.BouH_.gameplay.client.NotificationHud;
import ru.BouH_.utils.RenderUtils;

@SideOnly(Side.CLIENT)
public class NotificationAchievement implements INotification {
    private final AchievementZp achievementZp;

    public NotificationAchievement(AchievementZp achievementZp) {
        this.achievementZp = achievementZp;
    }

    public AchievementZp getAchievementZp() {
        return this.achievementZp;
    }

    @Override
    public Item getLogo() {
        return this.getAchievementZp().getLogo();
    }

    @Override
    public void drawWindow(int scaledWidth, int scaledHeight) {
        Minecraft mc = Minecraft.getMinecraft();
        mc.fontRendererObj.drawString(I18n.format("achievement.zp.completed"), 34, 8, 0xb1ff99);
        mc.fontRendererObj.drawString(this.getAchievementZp().getName(), 34, 20, this.getAchievementZp().getLvlEnum().getColorCode());
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        mc.getTextureManager().bindTexture(NotificationHud.component);
        mc.ingameGUI.drawTexturedModalRect(7, 5, 26, 202, 26, 26);
        RenderUtils.renderIcon(12, 10, new ItemStack(this.getLogo()));
    }
}
