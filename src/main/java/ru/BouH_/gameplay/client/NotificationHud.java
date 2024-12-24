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
import ru.BouH_.notifications.INotification;
import ru.BouH_.notifications.NotificationManager;

@SideOnly(Side.CLIENT)
public class NotificationHud {
    public static NotificationHud instance = new NotificationHud();
    public static ResourceLocation component = new ResourceLocation(Main.MODID, "textures/gui/gui_special.png");
    private INotification iNotification;
    private int notificationTicks;
    private int prevY;
    private int currentY;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent ev) {
        if (ev.phase == TickEvent.Phase.START) {
            Minecraft mc = Minecraft.getMinecraft();
            EntityPlayerSP player = mc.thePlayer;
            if (player != null) {
                if (!mc.isGamePaused()) {
                    this.prevY = this.currentY;
                    if (this.notificationTicks > 0) {
                        if (this.currentY > 0) {
                            this.currentY -= 2;
                        }
                        this.notificationTicks -= 1;
                    } else if (this.currentY < 32) {
                        this.currentY += 2;
                    } else if (NotificationManager.instance.canPop()) {
                        this.notificationTicks = 100;
                        this.iNotification = NotificationManager.instance.popNotification();
                    } else {
                        this.iNotification = null;
                    }
                }
            } else {
                this.currentY = -32;
                this.iNotification = null;
                NotificationManager.instance.getNotificationDeque().clear();
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void onOverlayRender(RenderGameOverlayEvent.Pre ev) {
        int scaledWidth = ev.resolution.getScaledWidth();
        int scaledHeight = ev.resolution.getScaledHeight();
        if (ev.type == RenderGameOverlayEvent.ElementType.HOTBAR) {
            ev.setCanceled(true);
            if (this.iNotification != null && !RenderManager.hideHud) {
                GL11.glPushMatrix();
                Minecraft mc = Minecraft.getMinecraft();
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
                mc.getTextureManager().bindTexture(NotificationHud.component);
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glTranslated(0, -(this.prevY + (this.currentY - this.prevY) * ev.partialTicks), 0);
                mc.ingameGUI.drawTexturedModalRect(2, 2, 96, 202, 160, 32);
                this.iNotification.drawWindow(scaledWidth, scaledHeight);
                GL11.glPopMatrix();
            }
        }
    }
}
