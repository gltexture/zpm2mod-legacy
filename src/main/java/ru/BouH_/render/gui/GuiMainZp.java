package ru.BouH_.render.gui;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import ru.BouH_.Main;
import ru.BouH_.options.gui.GuiMenuOptionsZp;
import ru.BouH_.utils.RenderUtils;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@SideOnly(Side.CLIENT)
public class GuiMainZp extends GuiScreen implements GuiYesNoCallback {
    private final int backgroundCount = 42;
    private final LinkButton buttonDiscord;
    private final LinkButton buttonModrinth;
    private final ResourceLocation[] resourceLocations = new ResourceLocation[backgroundCount + 1];
    private final ResourceLocation background = new ResourceLocation(Main.MODID + ":textures/misc/zpm.png");
    private int currentBackground = Main.rand.nextInt(backgroundCount + 1);
    private int backgroundTimer;
    private boolean flag;

    public GuiMainZp() {
        this.buttonDiscord = new LinkButton(new ResourceLocation(Main.MODID + ":textures/misc/discord.png"), "https://discord.gg/SrxSSsFv5F", 2, 2);
        this.buttonModrinth = new LinkButton(new ResourceLocation(Main.MODID + ":textures/misc/modrinth.png"), "https://modrinth.com/mod/zombie-plague-2-hardcore-zombie-apocalypse", 2, 28);

        for (int i = 0; i < this.backgroundCount + 1; i++) {
            this.resourceLocations[i] = new ResourceLocation(Main.MODID + ":textures/misc/background/background" + i + ".png");
        }
    }

    public void updateScreen() {
        if (this.backgroundTimer++ >= 160) {
            this.currentBackground++;
            this.backgroundTimer = 0;
            if (this.currentBackground > this.backgroundCount) {
                this.currentBackground = 0;
            }
            if (this.flag || Main.rand.nextFloat() <= 1.5e-3f) {
                this.flag = !this.flag;
            }
        }
    }

    public void initGui() {
        this.addSingleplayerMultiplayerButtons();
    }

    @SuppressWarnings("unchecked")
    private void addSingleplayerMultiplayerButtons() {
        this.buttonList.add(new GuiButton(1, this.width / 2 - 102, 50, 100, 20, I18n.format("menu.singleplayer")));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 102, 72, 100, 20, I18n.format("menu.multiplayer")));

        this.buttonList.add(new GuiButton(0, this.width / 2 + 2, 50, 100, 20, I18n.format("menu.options")));
        this.buttonList.add(new GuiButton(4, this.width / 2 + 2, 72, 100, 20, I18n.format("menu.quit")));
    }

    protected void actionPerformed(GuiButton p_146284_1_) {
        if (p_146284_1_.id == 0) {
            this.mc.displayGuiScreen(new GuiMenuOptionsZp(this));
        }
        if (p_146284_1_.id == 1) {
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        }
        if (p_146284_1_.id == 2) {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }
        if (p_146284_1_.id == 4) {
            this.mc.shutdown();
        }
    }

    public void drawLinkButtons(int mX, int mY) {
        this.buttonDiscord.update(mX, mY);
        this.buttonModrinth.update(mX, mY);
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == 0) {
            this.buttonDiscord.onClick();
            this.buttonModrinth.onClick();
        }
    }

    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
        Tessellator tessellator = Tessellator.instance;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.0F);

        tessellator.startDrawingQuads();
        this.mc.getTextureManager().bindTexture(this.resourceLocations[this.currentBackground != this.backgroundCount ? (this.currentBackground + 1) : 0]);
        tessellator.addVertexWithUV(0.0f, this.height, -2, 0, 1);
        tessellator.addVertexWithUV(this.width, this.height, -2, 1, 1);
        tessellator.addVertexWithUV(this.width, 0.0f, -2, 1, 0);
        tessellator.addVertexWithUV(0.0f, 0.0f, -2, 0, 0);
        tessellator.draw();

        GL11.glPushMatrix();
        float f6 = ((float) this.backgroundTimer + p_73863_3_) / (float) 100;
        f6 *= f6;
        float f7 = Math.min(2.0F - f6 * 2.0F, 1.0f);

        tessellator.startDrawingQuads();
        this.mc.getTextureManager().bindTexture(this.resourceLocations[this.currentBackground]);
        tessellator.setColorRGBA_F(1, 1, 1, f7);
        tessellator.addVertexWithUV(0.0f, this.height, -2, 0, 1);
        tessellator.addVertexWithUV(this.width, this.height, -2, 1, 1);
        tessellator.addVertexWithUV(this.width, 0.0f, -2, 1, 0);
        tessellator.addVertexWithUV(0.0f, 0.0f, -2, 0, 0);
        tessellator.draw();
        GL11.glPopMatrix();

        short short1 = 189;
        int k = this.width / 2 - short1 / 2;

        this.mc.getTextureManager().bindTexture(this.background);
        RenderUtils.drawTexturedModalRectCustom(k, -10, 189, 74);

        this.drawLinkButtons(p_73863_1_, p_73863_2_);

        if (Main.modStatus.isDisplayUnstableWarning()) {
            String s = I18n.format("misc.warn.uns");
            RenderUtils.drawStringInSquare(s, this.width / 2 - this.fontRendererObj.getStringWidth(s) / 2, 100, 0xffffff);
        }
        if (FMLClientHandler.instance().hasOptifine()) {
            String s = I18n.format("misc.warn.optifine");
            RenderUtils.drawStringInSquare(s, this.width / 2 - this.fontRendererObj.getStringWidth(s) / 2, 120, 0xff0000);
        } else {
            if (this.flag) {
                String s = "SGVyb2JyaW5lLi4u";
                RenderUtils.drawStringInSquare(s, this.width / 2 - this.fontRendererObj.getStringWidth(s) / 2, 120, 0xff0000);
            }
        }

        String s = "Minecraft";
        String s1 = "Copyright Mojang AB";
        RenderUtils.drawStringInSquare(s, 2, this.height - 30, -1);
        RenderUtils.drawStringInSquare(Main.MODNAME, 2, this.height - 20, 0xff0000);
        RenderUtils.drawStringInSquare(Main.VERSION + Main.modStatus.getVer(), 2, this.height - 10, 0xff0000);
        RenderUtils.drawStringInSquare(s1, this.width - this.fontRendererObj.getStringWidth(s1) - 2, this.height - 10, -1);
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }

    public class LinkButton {
        private final ResourceLocation image;
        private final String link;
        private boolean isSelected;
        private final int x;
        private final int y;

        public LinkButton(ResourceLocation image, String link, int x, int y) {
            this.image = image;
            this.link = link;
            this.x = x;
            this.y = y;
        }

        public void update(int mX, int mY) {
            int WH = 22;
            this.isSelected = mX >= this.x && mX <= this.x + WH && mY >= this.y && mY <= this.y + WH;
            GL11.glPushMatrix();
            if (this.isSelected) {
                GL11.glColor4f(0.7F, 0.7F, 0.7F, 1.0F);
            } else {
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            }
            Minecraft.getMinecraft().getTextureManager().bindTexture(this.image);
            RenderUtils.drawTexturedModalRectCustom(x, y, WH, WH, 100);
            GL11.glPopMatrix();
        }

        public void onClick() {
            if (this.isSelected) {
                try {
                    Desktop.getDesktop().browse(new URI(this.link));
                } catch (IOException | URISyntaxException ignored) {
                }
            }
        }
    }
}
