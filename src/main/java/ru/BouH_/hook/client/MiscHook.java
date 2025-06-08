package ru.BouH_.hook.client;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.SplashProgress;
import cpw.mods.fml.relauncher.FMLLaunchHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MusicTicker;
import net.minecraft.client.gui.GuiGameOver;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldServer;
import net.minecraftforge.client.ForgeHooksClient;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import paulscode.sound.SoundSystemException;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;
import ru.BouH_.Main;
import ru.BouH_.audio.ALSoundZp;
import ru.BouH_.audio.AmbientSounds;
import ru.BouH_.items.gun.base.AGunBase;
import ru.BouH_.items.tools.ItemBinoculars;
import ru.BouH_.misc.ExplosionZp;
import ru.BouH_.utils.RenderUtils;
import ru.BouH_.world.WorldZp;
import ru.hook.asm.Hook;
import ru.hook.asm.ReturnCondition;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

@SideOnly(Side.CLIENT)
public class MiscHook {
    @Hook(returnCondition = ReturnCondition.ON_TRUE, booleanReturnConstant = false)
    public static boolean getBool(SplashProgress pr, String name, boolean def) {
        return name.equals("enabled");
    }

    @Hook
    public static void createDisplay(ForgeHooksClient cl) throws LWJGLException {
        Display.setTitle("Minecraft | " + Main.MODNAME + " - " + Main.VERSION + Main.modStatus.getVer());
        InputStream inputStream = MiscHook.class.getResourceAsStream("/assets/" + Main.MODID + "/textures/misc/icon/icon2.png");
        InputStream inputStream1 = MiscHook.class.getResourceAsStream("/assets/" + Main.MODID + "/textures/misc/icon/icon1.png");
        if (inputStream != null && inputStream1 != null) {
            Display.setIcon(new ByteBuffer[]{RenderUtils.getByteBufferFromPNG(inputStream), RenderUtils.getByteBufferFromPNG(inputStream1)});
        }
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void displayGuiScreen(Minecraft minecraft, GuiScreen guiScreenIn) {
        if (guiScreenIn == null && minecraft.theWorld == null) {
            guiScreenIn = new GuiMainMenu();
        } else if (guiScreenIn == null && minecraft.thePlayer.getHealth() <= 0.0F) {
            guiScreenIn = new GuiGameOver();
        }

        GuiScreen old = minecraft.currentScreen;
        net.minecraftforge.client.event.GuiOpenEvent event = new net.minecraftforge.client.event.GuiOpenEvent(guiScreenIn);

        if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(event)) return;

        guiScreenIn = event.gui;
        if (old != null && guiScreenIn != old) {
            old.onGuiClosed();
        }

        if (guiScreenIn instanceof GuiMainMenu) {
            minecraft.gameSettings.showDebugInfo = false;
            minecraft.ingameGUI.getChatGUI().clearChatMessages();
        }

        minecraft.currentScreen = guiScreenIn;

        if (guiScreenIn != null) {
            minecraft.setIngameNotInFocus();
            ScaledResolution scaledresolution = new ScaledResolution(minecraft, minecraft.displayWidth, minecraft.displayHeight);
            int i = scaledresolution.getScaledWidth();
            int j = scaledresolution.getScaledHeight();
            guiScreenIn.setWorldAndResolution(minecraft, i, j);
            minecraft.skipRenderWorld = false;
        } else {
            if (minecraft.isGamePaused()) {
                minecraft.getSoundHandler().resumeSounds();
            }
            minecraft.setIngameFocus();
        }
    }

    @Hook(returnCondition = ReturnCondition.ON_TRUE)
    public static boolean update(MusicTicker musicTicker) {
        return Minecraft.getMinecraft().getAmbientMusicType() == MusicTicker.MusicType.MENU;
    }

    @Hook
    public static void cleanup(LibraryLWJGLOpenAL soundSystem) {
        AmbientSounds.instance.init = false;
        ALSoundZp.soundSetZp.forEach(ALSoundZp::clear);
    }

    @Hook(injectOnExit = true)
    public static void init(LibraryLWJGLOpenAL libraryLWJGLOpenAL) throws SoundSystemException {
        AmbientSounds.instance.loadSounds();
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void loadScreen(Minecraft mc) throws LWJGLException {
        ScaledResolution scaledresolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
        int i = scaledresolution.getScaleFactor();
        Framebuffer framebuffer = new Framebuffer(scaledresolution.getScaledWidth() * i, scaledresolution.getScaledHeight() * i, true);
        framebuffer.bindFramebuffer(false);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0.0D, scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight(), 0.0D, 1000.0D, 3000.0D);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glLoadIdentity();
        GL11.glTranslatef(0.0F, 0.0F, -2000.0F);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_FOG);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
        GL11.glEnable(GL11.GL_BLEND);
        mc.renderEngine.bindTexture(new ResourceLocation(Main.MODID + ":textures/misc/splash_back.png"));
        RenderUtils.drawTexturedModalRectCustom(0, 0, scaledresolution.getScaledWidth(), scaledresolution.getScaledHeight());
        mc.renderEngine.bindTexture(new ResourceLocation(Main.MODID + ":textures/misc/splash.png"));
        mc.scaledTessellator((scaledresolution.getScaledWidth() - 256) / 2, (scaledresolution.getScaledHeight() - 256) / 2, 0, 0, 256, 256);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_FOG);
        framebuffer.unbindFramebuffer();
        framebuffer.framebufferRender(scaledresolution.getScaledWidth() * i, scaledresolution.getScaledHeight() * i);
        GL11.glFlush();
        mc.resetSize();
    }

    @SuppressWarnings("rawtypes")
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void getMouseOver(EntityRenderer renderer, float ticks) {
        Minecraft mc = Minecraft.getMinecraft();
        if (mc.renderViewEntity != null) {
            if (mc.theWorld != null) {
                mc.pointedEntity = null;
                double d0 = mc.playerController.getBlockReachDistance();
                MovingObjectPosition movingObjectPosition = mc.renderViewEntity.rayTrace(d0, ticks);
                ItemStack stack = Minecraft.getMinecraft().thePlayer.getHeldItem();

                if (movingObjectPosition != null) {
                    if (stack != null && (stack.getItem() instanceof AGunBase || stack.getItem() instanceof ItemBinoculars)) {
                        movingObjectPosition.typeOfHit = MovingObjectPosition.MovingObjectType.MISS;
                    }
                }

                mc.objectMouseOver = movingObjectPosition;
                double d1 = d0;
                Vec3 vec3 = mc.renderViewEntity.getPosition(ticks);

                if (mc.playerController.extendedReach()) {
                    d0 = 6.0D;
                    d1 = 6.0D;
                } else {
                    if (d0 > 3.0D) {
                        d1 = 3.0D;
                    }

                    d0 = d1;
                }

                if (mc.objectMouseOver != null) {
                    d1 = mc.objectMouseOver.hitVec.distanceTo(vec3);
                }

                Vec3 vec31 = mc.renderViewEntity.getLook(ticks);
                Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
                Entity pointedEntity = null;
                Vec3 vec33 = null;
                List list = mc.theWorld.loadedEntityList;

                double d2 = d1;

                for (Object o : list) {
                    Entity entity = (Entity) o;
                    if (entity == mc.renderViewEntity) {
                        continue;
                    }

                    if (entity.canBeCollidedWith()) {
                        float f2 = entity.getCollisionBorderSize();
                        AxisAlignedBB axisalignedbb = entity.boundingBox.expand(f2, f2, f2);
                        MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);

                        if (axisalignedbb.isVecInside(vec3)) {
                            if (0.0D < d2 || d2 == 0.0D) {
                                pointedEntity = entity;
                                vec33 = movingobjectposition == null ? vec3 : movingobjectposition.hitVec;
                                d2 = 0.0D;
                            }
                        } else if (movingobjectposition != null) {
                            double d3 = vec3.distanceTo(movingobjectposition.hitVec);

                            if (d3 < d2 || d2 == 0.0D) {
                                if (entity == mc.renderViewEntity.ridingEntity && !entity.canRiderInteract()) {
                                    if (d2 == 0.0D) {
                                        pointedEntity = entity;
                                        vec33 = movingobjectposition.hitVec;
                                    }
                                } else {
                                    pointedEntity = entity;
                                    vec33 = movingobjectposition.hitVec;
                                    d2 = d3;
                                }
                            }
                        }
                    }
                }

                if (pointedEntity != null && (d2 < d1 || mc.objectMouseOver == null)) {
                    mc.objectMouseOver = new MovingObjectPosition(pointedEntity, vec33);

                    if (pointedEntity instanceof EntityLivingBase || pointedEntity instanceof EntityItemFrame) {
                        mc.pointedEntity = pointedEntity;
                    }
                }
            }
        }
    }

    public static boolean isSp() {
        return (MinecraftServer.getServer() != null && MinecraftServer.getServer().worldServers != null && MinecraftServer.getServer().worldServers.length > 0);
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void handleTimeUpdate(NetHandlerPlayClient netHandlerPlayClient, S03PacketTimeUpdate packetIn)
    {
        Minecraft.getMinecraft().theWorld.func_82738_a(packetIn.func_149366_c());
        if (MiscHook.isSp() && Minecraft.getMinecraft().theWorld.provider.dimensionId == 2) {
            Minecraft.getMinecraft().theWorld.setWorldTime(packetIn.func_149365_d() + 12000);
        } else {
            Minecraft.getMinecraft().theWorld.setWorldTime(packetIn.func_149365_d());
        }
    }

   // @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void tick(WorldClient worldClient)
    {
        worldClient.updateWeather();
        worldClient.func_82738_a(worldClient.getTotalWorldTime() + 1L);

        if (worldClient.getGameRules().getGameRuleBooleanValue("doDaylightCycle"))
        {
            {
                worldClient.setWorldTime(worldClient.getWorldTime() + 1L);
            }
        }

        worldClient.theProfiler.startSection("reEntryProcessing");

        for (int i = 0; i < 10 && !worldClient.entitySpawnQueue.isEmpty(); ++i)
        {
            Entity entity = (Entity)worldClient.entitySpawnQueue.iterator().next();
            worldClient.entitySpawnQueue.remove(entity);

            if (!worldClient.loadedEntityList.contains(entity))
            {
                worldClient.spawnEntityInWorld(entity);
            }
        }

        worldClient.theProfiler.endStartSection("connection");
        worldClient.sendQueue.onNetworkTick();
        worldClient.theProfiler.endStartSection("chunkCache");
        worldClient.clientChunkProvider.unloadQueuedChunks();
        worldClient.theProfiler.endStartSection("blocks");
        worldClient.func_147456_g();
        worldClient.theProfiler.endSection();
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void handleExplosion(NetHandlerPlayClient pack, S27PacketExplosion packetIn) {
        ExplosionZp explosion = new ExplosionZp(Minecraft.getMinecraft().theWorld, null, packetIn.func_149148_f(), packetIn.func_149143_g(), packetIn.func_149145_h(), packetIn.func_149146_i());
        explosion.affectedBlockPositions = packetIn.func_149150_j();
        Minecraft.getMinecraft().thePlayer.motionX += packetIn.func_149149_c();
        Minecraft.getMinecraft().thePlayer.motionY += packetIn.func_149144_d();
        Minecraft.getMinecraft().thePlayer.motionZ += packetIn.func_149147_e();
    }
}
