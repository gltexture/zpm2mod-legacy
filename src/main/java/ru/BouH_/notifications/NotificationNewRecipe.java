package ru.BouH_.notifications;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import ru.BouH_.gameplay.client.NotificationHud;
import ru.BouH_.skills.SkillZp;
import ru.BouH_.utils.RenderUtils;

@SideOnly(Side.CLIENT)
public class NotificationNewRecipe implements INotification {
    private final SkillZp skillZp;
    private final boolean many;

    public NotificationNewRecipe(SkillZp skillZp, boolean many) {
        this.skillZp = skillZp;
        this.many = many;
    }

    public SkillZp getSkillZp() {
        return this.skillZp;
    }

    @Override
    public Item getLogo() {
        return this.skillZp.getLogo();
    }

    @Override
    public void drawWindow(int scaledWidth, int scaledHeight) {
        Minecraft mc = Minecraft.getMinecraft();
        mc.fontRendererObj.drawString(this.many ? I18n.format("recipes.zp.new_s") : I18n.format("recipes.zp.new"), 34, 14, 0x87e4ff);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        mc.getTextureManager().bindTexture(NotificationHud.component);
        mc.ingameGUI.drawTexturedModalRect(7, 5, 26, 202, 26, 26);
        RenderUtils.renderIcon(12, 10, new ItemStack(this.getLogo()));
    }
}
