package ru.BouH_.notifications;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import ru.BouH_.gameplay.client.NotificationHud;
import ru.BouH_.skills.SkillManager;
import ru.BouH_.skills.SkillZp;
import ru.BouH_.utils.RenderUtils;

@SideOnly(Side.CLIENT)
public class NotificationSkill implements INotification {
    private final SkillZp skillZp;
    private final int i1;

    public NotificationSkill(SkillZp skillZp, int lvl) {
        this.skillZp = skillZp;
        this.i1 = lvl;
    }

    public SkillZp getSkillZp() {
        return this.skillZp;
    }

    @Override
    public Item getLogo() {
        return this.getSkillZp().getLogo();
    }

    @Override
    public void drawWindow(int scaledWidth, int scaledHeight) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;
        mc.fontRendererObj.drawString(this.getSkillZp().getName(), 34, 8, this.i1 == SkillManager.instance.max ? 0xaa65f7 : 0xb1ff99);
        mc.ingameGUI.drawString(mc.fontRendererObj, this.i1 + "/" + SkillManager.instance.max, 36, 18, 0xffffff);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        float f1 = this.i1 / (float) SkillManager.instance.max;
        mc.getTextureManager().bindTexture(NotificationHud.component);
        mc.ingameGUI.drawTexturedModalRect(36, 26, 0, 0, 100, 3);
        mc.ingameGUI.drawTexturedModalRect(36, 26, 0, 3, (int) (100 * f1), 3);
        mc.getTextureManager().bindTexture(NotificationHud.component);
        mc.ingameGUI.drawTexturedModalRect(7, 5, 26, 202, 26, 26);
        RenderUtils.renderIcon(12, 10, new ItemStack(this.getLogo()));
    }
}
