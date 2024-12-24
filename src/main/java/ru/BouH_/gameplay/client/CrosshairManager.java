package ru.BouH_.gameplay.client;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;
import ru.BouH_.Main;
import ru.BouH_.items.gun.base.AGunBase;
import ru.BouH_.items.gun.base.EnumFireModes;
import ru.BouH_.items.gun.render.GunItemRender;
import ru.BouH_.moving.MovingInput;

@SideOnly(Side.CLIENT)
public class CrosshairManager {
    public static CrosshairManager instance = new CrosshairManager();
    public float strafe;
    public float strafePrev;
    public float bobbing;
    public float bobbingPrev;
    private float inaccuracyPrev;
    private float inaccuracy;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent ev) {
        if (ev.phase == TickEvent.Phase.START) {
            this.inaccuracyPrev = this.inaccuracy;
            this.strafePrev = this.strafe;
            this.bobbingPrev = this.bobbing;
        }
    }

    public float strafeConst(float partial) {
        if (!Main.settingsZp.strafes.isFlag() || Minecraft.getMinecraft().gameSettings.hideGUI) {
            return 0;
        }
        return (this.strafePrev + (this.strafe - this.strafePrev) * partial) * -1.6f;
    }

    public void updateStrafe(float ticks) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP entityPlayerSP = mc.thePlayer;
        float f = entityPlayerSP.prevRenderArmYaw + (entityPlayerSP.renderArmYaw - entityPlayerSP.prevRenderArmYaw) * ticks;
        float f1 = MathHelper.clamp_float(entityPlayerSP.moveStrafing * 1.5f + (entityPlayerSP.rotationYaw - f) * 0.1f, -2.5f, 2.5f);
        float f2 = Math.max(f1 * 0.3f, 0.5f);
        if (entityPlayerSP.onGround) {
            float i = MathHelper.sqrt_float((float) (entityPlayerSP.motionX * entityPlayerSP.motionX + entityPlayerSP.motionZ * entityPlayerSP.motionZ));
            f1 += entityPlayerSP.isSprinting() ? MathHelper.cos(entityPlayerSP.ticksExisted * 0.5f) * i * 6.0f : MathHelper.cos(entityPlayerSP.ticksExisted * 0.4f) * i * 4.0f;
        }
        if (this.strafe < f1) {
            this.strafe = Math.min(this.strafePrev + f2, f1);
        } else if (this.strafe > f1) {
            this.strafe = Math.max(this.strafePrev - f2, f1);
        }
        if (entityPlayerSP.onGround || entityPlayerSP.getHealth() <= 0.0f) {
            if (this.bobbing != 0) {
                if (Main.settingsZp.strafes.isFlag()) {
                    entityPlayerSP.cameraPitch -= (-1.0f + this.bobbing);
                }
                if (MovingInput.instance.jumpTms >= 3) {
                    if (this.bobbing < 0.0f) {
                        MovingInput.instance.speed_rec = this.bobbing / -2.0f;
                    }
                }
            }
            this.bobbing = 0;
        } else {
            this.bobbing = MathHelper.clamp_float((float) entityPlayerSP.motionY * 5.0f, -2.0f, 2.0f);
        }
        Minecraft.getMinecraft().entityRenderer.camRoll = CrosshairManager.instance.strafeConst(ticks) + GunItemRender.instance.getScreenShake();
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onOverlayRender(RenderGameOverlayEvent.Pre ev) {
        int scaledWidth = ev.resolution.getScaledWidth();
        int scaledHeight = ev.resolution.getScaledHeight();
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP entityPlayerSP = mc.thePlayer;
        if (ev.type == RenderGameOverlayEvent.ElementType.CHAT) {
            if (RenderManager.hideHud) {
                ev.setCanceled(true);
            }
        }
        if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 0) {
            if (ev.type == RenderGameOverlayEvent.ElementType.CROSSHAIRS) {
                ev.setCanceled(true);
                if (!RenderManager.hideHud) {
                    float inaccuracyConst;
                    float inaccuracy = 0.0f;
                    if (entityPlayerSP.getHeldItem() != null && entityPlayerSP.getHeldItem().hasTagCompound() && entityPlayerSP.getHeldItem().getItem() instanceof AGunBase) {
                        ItemStack stack = mc.thePlayer.getHeldItem();
                        AGunBase aGunBase = (AGunBase) mc.thePlayer.getHeldItem().getItem();
                        double inaccuracyStatic = (GunItemRender.instance.isInScope() ? aGunBase.getInaccuracyInAim() : aGunBase.getInaccuracy()) * Main.settingsZp.dynamicStr.getValue() + 1.0f;
                        if (aGunBase.getCurrentFireMode(stack) == EnumFireModes.LAUNCHER) {
                            inaccuracyStatic = Main.settingsZp.dynamicStr.getValue() + 1.0f;
                        }
                        inaccuracy = (float) Math.max(inaccuracyStatic * aGunBase.inaccuracyModifier(entityPlayerSP, stack, GunItemRender.instance.isInScope()), 0);
                        if (entityPlayerSP.getEntityData().getInteger("cdShoot") > 0) {
                            inaccuracy += Math.max(aGunBase.getRecoilVertical(), aGunBase.getRecoilHorizontal()) * Main.settingsZp.dynamicStr.getValue();
                        }
                        if (!aGunBase.canActivateCross(stack, entityPlayerSP)) {
                            inaccuracy = 0;
                        }
                    }
                    if (this.inaccuracy < inaccuracy) {
                        this.inaccuracy = Math.min(this.inaccuracyPrev + 5.0f, inaccuracy);
                    } else if (this.inaccuracy > inaccuracy) {
                        this.inaccuracy = Math.max(this.inaccuracyPrev - 5.0f, inaccuracy);
                    }
                    inaccuracyConst = (float) (Math.min(this.inaccuracyPrev + (this.inaccuracy - this.inaccuracyPrev) * ev.partialTicks, 125.0f) * Main.settingsZp.dynamicStr.getValue());

                    if (mc.currentScreen == null) {
                        GL11.glPushMatrix();
                        if (Main.settingsZp.crossEffect.isFlag()) {
                            OpenGlHelper.glBlendFunc(GL11.GL_ONE_MINUS_DST_COLOR, GL11.GL_ONE_MINUS_SRC_COLOR, 1, 0);
                        }
                        GL11.glColor4d(Main.settingsZp.crossRed.getValue(), Main.settingsZp.crossGreen.getValue(), Main.settingsZp.crossBlue.getValue(), 1);
                        double l = Main.settingsZp.length.getValue();
                        double d = Main.settingsZp.distance.getValue();
                        double x;
                        double y;
                        if (Main.settingsZp.dot.isFlag()) {
                            GL11.glPushMatrix();
                            x = (float) (scaledWidth / 2);
                            y = (float) (scaledHeight / 2);
                            this.drawCross(x, y, x + 1, y + 1);
                            GL11.glPopMatrix();
                        }
                        GL11.glPushMatrix();
                        GL11.glTranslatef(-inaccuracyConst, 0, 0);
                        x = (float) (scaledWidth / 2 - l) - d;
                        y = (float) (scaledHeight / 2);
                        this.drawCross(x, y, x + 3 + (l - 3), y + 1);
                        GL11.glPopMatrix();

                        GL11.glPushMatrix();
                        GL11.glTranslatef(inaccuracyConst, 0, 0);
                        x = (float) (scaledWidth / 2 + 1) + d;
                        y = (float) (scaledHeight / 2);
                        this.drawCross(x, y, x + l, y + 1);
                        GL11.glPopMatrix();

                        GL11.glPushMatrix();
                        GL11.glTranslatef(0, -inaccuracyConst, 0);
                        x = (float) (scaledWidth / 2);
                        y = (float) (scaledHeight / 2 - l) - d;
                        this.drawCross(x, y, x + 1, y + 3 + (l - 3));
                        GL11.glPopMatrix();

                        GL11.glPushMatrix();
                        GL11.glTranslatef(0, inaccuracyConst, 0);
                        x = (float) (scaledWidth / 2);
                        y = (float) (scaledHeight / 2 + 1) + d;
                        this.drawCross(x, y, x + 1, y + l);
                        GL11.glPopMatrix();
                        GL11.glPopMatrix();
                    }
                }
            }
        } else {
            this.inaccuracy = 0;
            this.inaccuracyPrev = 0;
        }
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
}
