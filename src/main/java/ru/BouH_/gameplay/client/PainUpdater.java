package ru.BouH_.gameplay.client;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;
import ru.BouH_.Main;
import ru.BouH_.utils.RenderUtils;

@SideOnly(Side.CLIENT)
public class PainUpdater {
    public static PainUpdater instance = new PainUpdater();
    private final ResourceLocation background = new ResourceLocation(Main.MODID + ":textures/gui/pain_hud.png");
    private float alphaFloat = 1.0f;
    private float strength;
    private int timer;

    public void addPainUpdater(float str) {
        this.strength = Math.min(this.strength + str, 1.5f);
        this.timer = 15;
    }

    public float getPainStr() {
        return this.strength;
    }

    public void setPainStr(float strength) {
        this.strength = strength;
    }

    private float getMinPainStr() {
        float h = Minecraft.getMinecraft().thePlayer.getHealth();
        if (h < 50) {
            return (50 - h) * 0.02f;
        }
        return 0;
    }

    public void reset() {
        this.strength = 0;
        this.timer = 0;
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onOverlayRender(RenderGameOverlayEvent.Pre ev) {
        if (Main.settingsZp.redScreen.isFlag()) {
            int scaledWidth = ev.resolution.getScaledWidth();
            int scaledHeight = ev.resolution.getScaledHeight();
            Minecraft mc = Minecraft.getMinecraft();
            if (ev.type == RenderGameOverlayEvent.ElementType.HEALTH) {
                GL11.glAlphaFunc(GL11.GL_GREATER, 0.0F);
                GL11.glPushMatrix();
                mc.getTextureManager().bindTexture(this.background);
                GL11.glColor4f(1, 1, 1, (Math.max(this.getPainStr(), this.getMinPainStr())) * this.alphaFloat);
                RenderUtils.drawTexturedModalRectCustom(0, 0, scaledWidth, scaledHeight);
                GL11.glColor4f(1, 1, 1, 1);
                GL11.glPopMatrix();
                GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            }
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent ev) {
        if (ev.phase == TickEvent.Phase.START) {
            if (!Minecraft.getMinecraft().isGamePaused()) {
                if (Minecraft.getMinecraft().thePlayer != null) {
                    EntityPlayerSP entityPlayerSP = Minecraft.getMinecraft().thePlayer;
                    int f0 = entityPlayerSP.getBrightnessForRender(0);
                    int j = f0 % 65536;
                    int k = f0 / 65536;
                    k *= (int) entityPlayerSP.worldObj.getSunBrightnessFactor(0);
                    float f1 = Math.max((j | k) / 240.0f, 0.1f);
                    if (this.alphaFloat > f1) {
                        this.alphaFloat = Math.max(this.alphaFloat - 0.02f, f1);
                    } else if (this.alphaFloat < f1) {
                        this.alphaFloat = Math.min(this.alphaFloat + 0.02f, f1);
                    }
                    if (this.timer > 0) {
                        this.timer -= 1;
                    } else if (this.getPainStr() > this.getMinPainStr()) {
                        this.strength = Math.max(this.strength - 0.02f, 0.0f);
                    }
                }
            }
        }
    }
}
