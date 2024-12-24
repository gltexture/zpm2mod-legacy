package ru.BouH_.achievements.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;
import ru.BouH_.achievements.AchievementManager;
import ru.BouH_.achievements.AchievementZp;
import ru.BouH_.achievements.TierEnum;
import ru.BouH_.gameplay.client.NotificationHud;
import ru.BouH_.render.gui.GuiInGameMenuZp;
import ru.BouH_.utils.RenderUtils;

import java.util.ArrayList;

@SideOnly(Side.CLIENT)
public class GuiAchievementsZp extends GuiScreen {
    protected GuiScreen guiScreen;
    private GuiAchievementsZp.List list;

    public GuiAchievementsZp(GuiScreen p_i1043_1_) {
        this.guiScreen = p_i1043_1_;
    }

    public void initGui() {
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height - 38, I18n.format("gui.done")));
        this.list = new GuiAchievementsZp.List();
        this.list.registerScrollButtons(7, 8);
    }

    protected void actionPerformed(GuiButton button) {
        if (button.id == 200) {
            this.mc.displayGuiScreen(new GuiInGameMenuZp());
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.list.drawScreen(mouseX, mouseY, partialTicks);
        EntityPlayerSP entityPlayerSp = Minecraft.getMinecraft().thePlayer;
        this.drawCenteredString(this.fontRendererObj, I18n.format("achievement.zp.menuTitle"), this.width / 2, 16, 0xffffff);
        String s1 = String.format("%s %s/%s", I18n.format("achievement.zp.percent"), AchievementManager.instance.calcAchievements(entityPlayerSp), AchievementManager.instance.getAchievementZpList().size());
        GuiAchievementsZp.this.drawString(GuiAchievementsZp.this.fontRendererObj, s1, this.width / 2 - GuiAchievementsZp.this.fontRendererObj.getStringWidth(s1) / 2, this.height - 56, 0xdefac3);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public class List extends GuiSlot {
        private final ArrayList<AchievementZp> achievementZpList = new ArrayList<>();

        public List() {
            super(GuiAchievementsZp.this.mc, GuiAchievementsZp.this.width, GuiAchievementsZp.this.height, 32, GuiAchievementsZp.this.height - 64, 48);
            this.achievementZpList.addAll(AchievementManager.instance.getAchievementZpList());
        }

        protected int getSize() {
            return this.achievementZpList.size();
        }

        protected void elementClicked(int p_148144_1_, boolean p_148144_2_, int p_148144_3_, int p_148144_4_) {

        }

        protected boolean isSelected(int p_148131_1_) {
            return false;
        }

        protected int getContentHeight() {
            return this.getSize() * 48;
        }

        protected void drawBackground() {
            GuiAchievementsZp.this.drawDefaultBackground();
        }

        protected void drawSlot(int p_148126_1_, int p_148126_2_, int p_148126_3_, int p_148126_4_, Tessellator p_148126_5_, int p_148126_6_, int p_148126_7_) {
            AchievementZp achievementZp = this.achievementZpList.get(p_148126_1_);
            if (p_148126_1_ != this.achievementZpList.size() - 1) {
                this.drawBorders(this.width / 2 + 98, p_148126_3_ + 41);
            }
            EntityPlayerSP entityPlayerSp = Minecraft.getMinecraft().thePlayer;
            boolean flag = AchievementManager.instance.isAchievementActivated(achievementZp, entityPlayerSp);
            boolean flag2 = !flag && achievementZp.getLvlEnum() == TierEnum.LVL_SECRET;
            StringBuilder name = new StringBuilder(flag2 ? (EnumChatFormatting.GRAY + "***") : achievementZp.getName());
            if (flag) {
                name.append(EnumChatFormatting.GREEN).append(" [");
                name.append(I18n.format("achievement.zp.completed"));
                name.append("]");
            }
            GuiAchievementsZp.this.drawString(GuiAchievementsZp.this.fontRendererObj, name.toString(), this.width / 2 - 116, p_148126_3_, achievementZp.getLvlEnum().getColorCode());
            GuiAchievementsZp.this.drawString(GuiAchievementsZp.this.fontRendererObj, flag2 ? I18n.format("achievement.zp.secret") : achievementZp.getDescription(), this.width / 2 - 90, p_148126_3_ + 18, flag2 ? 0x595959 : !flag ? 0x999999 : 0xffffff);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            mc.getTextureManager().bindTexture(NotificationHud.component);
            GuiAchievementsZp.this.drawTexturedModalRect(this.width / 2 - 118, p_148126_3_ + 10, 26, 202, 26, 26);
            ItemStack stack = new ItemStack(flag2 ? Items.emerald : achievementZp.getLogo());
            if (!flag) {
                RenderUtils.renderIconBlack((double) this.width / 2 - 113, p_148126_3_ + 15, stack);
            } else {
                RenderUtils.renderIcon((double) this.width / 2 - 113, p_148126_3_ + 15, stack);
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }

        private void drawBorders(int x, int y) {
            Tessellator tessellator = Tessellator.instance;
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glColor4f(0.9f, 0.9f, 0.9f, 1.0f);
            tessellator.startDrawingQuads();
            tessellator.addVertex(x, y - 1, 0.0D);
            tessellator.addVertex(x - 196, y - 1, 0.0D);
            tessellator.addVertex(x - 196, y, 0.0D);
            tessellator.addVertex(x, y, 0.0D);
            tessellator.draw();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
        }
    }
}