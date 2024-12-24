package ru.BouH_.skills.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSlot;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import ru.BouH_.entity.ieep.PlayerMiscData;
import ru.BouH_.gameplay.client.NotificationHud;
import ru.BouH_.recipe_master.gui.GuiContainerRecipes;
import ru.BouH_.render.gui.GuiInGameMenuZp;
import ru.BouH_.skills.SkillManager;
import ru.BouH_.skills.SkillZp;
import ru.BouH_.utils.RenderUtils;

import java.util.ArrayList;

@SideOnly(Side.CLIENT)
public class GuiSkillsZp extends GuiScreen {
    protected GuiScreen guiScreen;
    private GuiSkillsZp.List list;

    public GuiSkillsZp(GuiScreen p_i1043_1_) {
        this.guiScreen = p_i1043_1_;
    }

    public void initGui() {
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height - 28, I18n.format("gui.done")));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height - 50, I18n.format("recipes.zp.menuTitle.special")));
        this.list = new GuiSkillsZp.List();
        this.list.registerScrollButtons(7, 8);
    }

    protected void actionPerformed(GuiButton button) {
        if (button.id == 1) {
            this.mc.displayGuiScreen(new GuiContainerRecipes(this.guiScreen, true));
        } else if (button.id == 200) {
            this.mc.displayGuiScreen(new GuiInGameMenuZp());
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.list.drawScreen(mouseX, mouseY, partialTicks);
        this.drawCenteredString(this.fontRendererObj, I18n.format("skill.zp.menuTitle"), this.width / 2, 16, 0xffffff);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public class List extends GuiSlot {
        private final ArrayList<SkillZp> skillZpList = new ArrayList<>();

        public List() {
            super(GuiSkillsZp.this.mc, GuiSkillsZp.this.width, GuiSkillsZp.this.height, 32, GuiSkillsZp.this.height - 52, 48);
            this.skillZpList.addAll(SkillManager.instance.getSkillZpList());
        }

        protected int getSize() {
            return this.skillZpList.size();
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
            GuiSkillsZp.this.drawDefaultBackground();
        }

        protected void drawSlot(int p_148126_1_, int p_148126_2_, int p_148126_3_, int p_148126_4_, Tessellator p_148126_5_, int p_148126_6_, int p_148126_7_) {
            SkillZp skillZp = this.skillZpList.get(p_148126_1_);
            if (p_148126_1_ != this.skillZpList.size() - 1) {
                this.drawBorders(this.width / 2 + 98, p_148126_3_ + 41);
            }
            EntityPlayerSP entityPlayerSp = Minecraft.getMinecraft().thePlayer;
            int i1 = SkillManager.instance.getSkillPoints(skillZp, entityPlayerSp);
            boolean b1 = SkillManager.instance.isMaxSkill(skillZp, entityPlayerSp);
            GuiSkillsZp.this.drawString(GuiSkillsZp.this.fontRendererObj, skillZp.getName(), this.width / 2 - 116, p_148126_3_, b1 ? 0xaa65f7 : 0xb1ff99);
            GuiSkillsZp.this.drawString(GuiSkillsZp.this.fontRendererObj, i1 + "/" + SkillManager.instance.max, this.width / 2 - 86, p_148126_3_ + 12, 0xffffff);
            int i2 = SkillManager.instance.getSkillPercentage(entityPlayerSp, skillZp);
            if (i2 != 0) {
                GuiSkillsZp.this.drawString(GuiSkillsZp.this.fontRendererObj, skillZp.getDescription() + " +" + i2 + "%", this.width / 2 - 86, p_148126_3_ + 28, 0x00ff00);
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glEnable(GL11.GL_BLEND);
            float f1 = i1 / (float) SkillManager.instance.max;
            float f0 = PlayerMiscData.getPlayerData(entityPlayerSp).getSkillProgressProfiler().getProgress(skillZp);
            float f2 = b1 ? 1.0f : f0;
            mc.getTextureManager().bindTexture(NotificationHud.component);
            GuiSkillsZp.this.drawTexturedModalRect(this.width / 2 - 86, p_148126_3_ + 22, 0, 0, 100, 3);
            GuiSkillsZp.this.drawTexturedModalRect(this.width / 2 - 86, p_148126_3_ + 22, 0, 3, (int) (100 * f1), 3);
            GuiSkillsZp.this.drawTexturedModalRect(this.width / 2 - 86, p_148126_3_ + 25, 0, 6, 100, 1);
            if (f2 < 0) {
                GuiSkillsZp.this.drawTexturedModalRect(this.width / 2 - 86, p_148126_3_ + 25, 0, 8, (int) (100 * Math.abs(f2)), 1);
            } else {
                GuiSkillsZp.this.drawTexturedModalRect(this.width / 2 - 86, p_148126_3_ + 25, 0, 7, (int) (100 * f2), 1);
            }
            GuiSkillsZp.this.drawTexturedModalRect(this.width / 2 - 118, p_148126_3_ + 10, 26, 202, 26, 26);
            RenderUtils.renderIcon((double) this.width / 2 - 113, p_148126_3_ + 15, new ItemStack(skillZp.getLogo()));
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