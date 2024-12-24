package ru.BouH_.options.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;
import ru.BouH_.Main;
import ru.BouH_.options.SettingsZp;
import ru.BouH_.options.manage.GuiButtonZp;
import ru.BouH_.options.manage.GuiSliderColorZp;
import ru.BouH_.options.manage.GuiSliderZp;

@SideOnly(Side.CLIENT)
public class GuiCrosshairOptionsZp extends GuiScreen {
    private final GuiScreen guiScreen;
    private final SettingsZp settingsZp;
    private String menuTitle;
    private int backgroundTimer;

    public GuiCrosshairOptionsZp(GuiScreen guiScreen, SettingsZp settingsZp) {
        this.guiScreen = guiScreen;
        this.settingsZp = settingsZp;
    }

    @SuppressWarnings("unchecked")
    public void initGui() {
        this.menuTitle = I18n.format("zpm.options.title");
        this.buttonList.add(new GuiButtonZp("gui.yes", "gui.no", 601, this.width / 2 - 232, this.height / 6 + 16, 150, 20, this.settingsZp.dot));
        this.buttonList.add(new GuiSliderZp(602, this.width / 2 - 232, this.height / 6 + 40, this.settingsZp.length));
        this.buttonList.add(new GuiSliderZp(603, this.width / 2 - 232, this.height / 6 + 64, this.settingsZp.distance));
        this.buttonList.add(new GuiSliderZp(604, this.width / 2 - 232, this.height / 6 + 88, this.settingsZp.dynamicStr));

        this.buttonList.add(new GuiSliderColorZp(606, this.width / 2 + 83, this.height / 6 + 16, this.settingsZp.crossRed, 255, 0, 0));
        this.buttonList.add(new GuiSliderColorZp(607, this.width / 2 + 83, this.height / 6 + 40, this.settingsZp.crossGreen, 0, 255, 0));
        this.buttonList.add(new GuiSliderColorZp(608, this.width / 2 + 83, this.height / 6 + 64, this.settingsZp.crossBlue, 0, 0, 255));
        this.buttonList.add(new GuiButtonZp("gui.yes", "gui.no", 609, this.width / 2 + 83, this.height / 6 + 88, 150, 20, this.settingsZp.crossEffect));

        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 190, I18n.format("gui.done")));
    }

    public void updateScreen() {
        this.backgroundTimer++;
    }

    protected void actionPerformed(GuiButton button) {
        if (button.enabled) {
            if (button instanceof GuiButtonZp) {
                GuiButtonZp guiButtonZp = (GuiButtonZp) button;
                guiButtonZp.perform();
            } else if (button.id == 200) {
                this.mc.displayGuiScreen(new GuiMenuOptionsZp(this.guiScreen));
            }
        }
    }


    public void drawTesting() {
        double inaccuracyConst = Math.max(MathHelper.sin(this.backgroundTimer * 0.15f) * 10.0f, 0.0f) * this.settingsZp.dynamicStr.getValue();
        GL11.glPushMatrix();
        if (Main.settingsZp.crossEffect.isFlag()) {
            OpenGlHelper.glBlendFunc(GL11.GL_ONE_MINUS_DST_COLOR, GL11.GL_ONE_MINUS_SRC_COLOR, 1, 0);
        }
        GL11.glColor4d(this.settingsZp.crossRed.getValue(), this.settingsZp.crossGreen.getValue(), this.settingsZp.crossBlue.getValue(), 1);
        double scaledWidth = (double) this.width / 2 + 85;
        double scaledHeight = (double) this.height / 6 + 74;
        double l = Main.settingsZp.length.getValue();
        double d = Main.settingsZp.distance.getValue();
        double x;
        double y;
        if (Main.settingsZp.dot.isFlag()) {
            GL11.glPushMatrix();
            x = scaledWidth - 85;
            y = scaledHeight;
            this.drawCross(x, y, x + 1, y + 1);
            GL11.glPopMatrix();
        }
        GL11.glPushMatrix();
        GL11.glTranslated(-inaccuracyConst, 0, 0);
        x = scaledWidth - l - d - 85;
        y = scaledHeight;
        this.drawCross(x, y, x + 3 + (l - 3), y + 1);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glTranslated(inaccuracyConst, 0, 0);
        x = scaledWidth + 1 + d - 85;
        y = scaledHeight;
        this.drawCross(x, y, x + l, y + 1);
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glTranslated(0, -inaccuracyConst, 0);
        x = scaledWidth - 85;
        y = scaledHeight - l - d;
        this.drawCross(x, y, x + 1, y + 3 + (l - 3));
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glTranslated(0, inaccuracyConst, 0);
        x = scaledWidth - 85;
        y = scaledHeight + 1 + d;
        this.drawCross(x, y, x + 1, y + l);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

    private void drawCross(double x, double y, double w, double h) {
        double j1;
        if (x < w) {
            j1 = x;
            x = w;
            w = j1;
        }
        if (y < h) {
            j1 = y;
            y = h;
            h = j1;
        }
        Tessellator tessellator = Tessellator.instance;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        tessellator.startDrawingQuads();
        tessellator.addVertex(x, h, 0.0D);
        tessellator.addVertex(w, h, 0.0D);
        tessellator.addVertex(w, y, 0.0D);
        tessellator.addVertex(x, y, 0.0D);
        tessellator.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        int x = this.width / 2 - 80;
        int y = this.height / 6 - 6;
        int w = x + 161;
        int h = y + 160;
        Gui.drawRect(x, y, w, h, 0xff000000);
        Gui.drawRect(x + 1, y + 1, w - 1, h - 1, 0xff2d2d2d);
        this.drawTesting();
        this.drawCenteredString(this.fontRendererObj, this.menuTitle, this.width / 2, 15, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}