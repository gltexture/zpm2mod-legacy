package ru.BouH_.gameplay.client;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.GuiCrafting;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import ru.BouH_.GoodPeople;
import ru.BouH_.Main;
import ru.BouH_.entity.ieep.PlayerMiscData;
import ru.BouH_.fun.tiles.TileKalibrBlock;
import ru.BouH_.hook.client.FXHook;
import ru.BouH_.init.ItemsZp;
import ru.BouH_.inventory.PlayerGui;
import ru.BouH_.inventory.containers.gui.GuiCraftingNew;
import ru.BouH_.items.gun.base.AGunBase;
import ru.BouH_.items.gun.base.EnumFireModes;
import ru.BouH_.items.gun.base.fun.ALauncherBase;
import ru.BouH_.items.gun.modules.ItemScope;
import ru.BouH_.items.gun.modules.base.ALauncherModuleBase;
import ru.BouH_.items.gun.modules.base.EnumModule;
import ru.BouH_.items.gun.modules.base.ModuleInfo;
import ru.BouH_.items.gun.render.BinocularsItemRender;
import ru.BouH_.items.gun.render.GunItemRender;
import ru.BouH_.items.tools.ItemTransmitterTactic;
import ru.BouH_.proxy.ClientProxy;
import ru.BouH_.proxy.CommonProxy;
import ru.BouH_.render.gui.GuiInGameMenuZp;
import ru.BouH_.render.gui.GuiMainZp;
import ru.BouH_.utils.ClientUtils;
import ru.BouH_.utils.EntityUtils;
import ru.BouH_.utils.RenderUtils;
import ru.BouH_.world.structures.base.StructureSaver;
import ru.BouH_.world.type.WorldTypeZp;

import java.util.Iterator;
import java.util.List;

@SideOnly(Side.CLIENT)
public class RenderManager {
    public static final ResourceLocation effectIcons = new ResourceLocation(Main.MODID + ":textures/gui/inventory.png");
    public static boolean hideHud;
    public static ResourceLocation scopeOverlay = new ResourceLocation(Main.MODID + ":textures/gui/scopeOverlay.png");
    public static ResourceLocation blur = new ResourceLocation(Main.MODID, "textures/gui/blur.png");
    public static ResourceLocation binocularsOverlay = new ResourceLocation(Main.MODID + ":textures/gui/binoculars.png");

    public static IIcon[] grass = new IIcon[7];
    public static IIcon[] grass_snow = new IIcon[2];
    public static IIcon[] leaf = new IIcon[5];
    public static IIcon[] leaf_snow = new IIcon[2];
    public static IIcon[] leaf_particle = new IIcon[4];
    public static IIcon blood;
    public static IIcon spark;
    public static IIcon shell;
    public static IIcon hole;

    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent.Pre ev) {
        if (ev.map.getTextureType() == 0) {
            RenderManager.blood = ev.map.registerIcon(Main.MODID + ":sprite/blood");
            RenderManager.spark = ev.map.registerIcon(Main.MODID + ":sprite/spark");
            RenderManager.shell = ev.map.registerIcon(Main.MODID + ":sprite/shell");
            RenderManager.hole = ev.map.registerIcon(Main.MODID + ":sprite/hole");

            for (int i = 0; i < grass.length; i++) {
                RenderManager.grass[i] = ev.map.registerIcon(Main.MODID + ":ambient/grass" + i);
            }
            for (int i = 0; i < leaf.length; i++) {
                RenderManager.leaf[i] = ev.map.registerIcon(Main.MODID + ":ambient/leaf" + i);
            }
            for (int i = 0; i < leaf_particle.length; i++) {
                RenderManager.leaf_particle[i] = ev.map.registerIcon(Main.MODID + ":ambient/leaf_particle" + i);
            }
            for (int i = 0; i < grass_snow.length; i++) {
                RenderManager.grass_snow[i] = ev.map.registerIcon(Main.MODID + ":ambient/grass_snow" + i);
            }
            for (int i = 0; i < leaf_snow.length; i++) {
                RenderManager.leaf_snow[i] = ev.map.registerIcon(Main.MODID + ":ambient/leaf_snow" + i);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onTooltip(ItemTooltipEvent ev) {
        if (!(ev.itemStack.getAttributeModifiers().isEmpty())) {
            Iterator<String> iterator = ev.toolTip.listIterator();
            while (iterator.hasNext()) {
                String next = iterator.next();
                if (next.isEmpty()) {
                    iterator.remove();
                    break;
                }
            }
        }
    }

    @SubscribeEvent
    public void onFog(EntityViewRenderEvent.FogColors ev) {
        if (ClientUtils.isClientInNVG() || ClientUtils.isClientInNightVisionScope()) {
            ev.red = 0.0f;
            ev.green = 1.0f;
            ev.blue = 0.6f;
        } else if (Minecraft.getMinecraft().thePlayer.isPotionActive(CommonProxy.zpm)) {
            EntityPlayerSP entityPlayerSP = Minecraft.getMinecraft().thePlayer;
            float f1 = FXHook.getPlagueMultiplier(entityPlayerSP);
            ev.green *= f1;
            ev.blue *= f1;
        }
    }

    @SubscribeEvent
    public void onGui(GuiOpenEvent ev) {
        EntityPlayerSP entityPlayerSP = Minecraft.getMinecraft().thePlayer;
        if (ev.gui instanceof GuiMainMenu) {
            ev.gui = new GuiMainZp();
        } else if (ev.gui instanceof GuiIngameMenu) {
            ev.gui = new GuiInGameMenuZp();
        } else if (ev.gui instanceof GuiCrafting) {
            ev.gui = new GuiCraftingNew(entityPlayerSP.inventory, entityPlayerSP.worldObj, MathHelper.floor_double(entityPlayerSP.posX), MathHelper.floor_double(entityPlayerSP.posY), MathHelper.floor_double(entityPlayerSP.posZ));
        } else if (ev.gui instanceof GuiInventory) {
            ev.gui = new PlayerGui(entityPlayerSP);
        }
    }

    @SubscribeEvent
    public void onRenderName(RenderLivingEvent.Specials.Pre ev) {
        if (!RenderManager.hideHud) {
            RendererLivingEntity.NAME_TAG_RANGE_SNEAK = 0;
            RendererLivingEntity.NAME_TAG_RANGE = 0;
            if ((ev.entity instanceof EntityPlayer && !(ev.entity.isPlayerSleeping() || PlayerMiscData.getPlayerData((EntityPlayer) ev.entity).isLying() || ev.entity.isSneaking() || Minecraft.getMinecraft().thePlayer == ev.entity || !Minecraft.getMinecraft().thePlayer.isEntityAlive() || !Minecraft.isGuiEnabled() || ev.entity.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer))) || (ev.entity instanceof EntityCreature && ((EntityCreature) ev.entity).hasCustomNameTag())) {
                float f1 = 0.0288f;
                double d3 = ev.entity.getDistanceToEntity(net.minecraft.client.renderer.entity.RenderManager.instance.livingPlayer);
                if ((!EntityUtils.isInArmor(ev.entity, ItemsZp.balaclava, null, null, null) && d3 <= 6)) {
                    String s = ev.entity.getFormattedCommandSenderName().getFormattedText();
                    FontRenderer fontrenderer = ev.renderer.getFontRendererFromRenderManager();
                    GL11.glPushMatrix();
                    GL11.glTranslatef((float) ev.x + 0.0F, (float) ev.y + ev.entity.height + 0.5F, (float) ev.z);
                    GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(-net.minecraft.client.renderer.entity.RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(net.minecraft.client.renderer.entity.RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
                    GL11.glScalef(-f1, -f1, f1);
                    GL11.glDisable(GL11.GL_LIGHTING);
                    GL11.glTranslatef(0.0F, 0.05F / f1, 0.0F);
                    GL11.glDepthMask(false);
                    GL11.glEnable(GL11.GL_BLEND);
                    int br = ev.entity.getBrightnessForRender(1.0f);
                    int j = br % 65536;
                    int k = br / 65536;
                    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
                    OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
                    Tessellator tessellator = Tessellator.instance;
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    tessellator.startDrawingQuads();
                    int i = fontrenderer.getStringWidth(s) / 2;
                    tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
                    tessellator.addVertex((-i - 1), -1.0D, 0.0D);
                    tessellator.addVertex((-i - 1), 8.0D, 0.0D);
                    tessellator.addVertex((i + 1), 8.0D, 0.0D);
                    tessellator.addVertex((i + 1), -1.0D, 0.0D);
                    tessellator.draw();
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                    GL11.glDepthMask(true);
                    fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, 0, ev.entity.getCommandSenderName().equals(GoodPeople.dungeonMaster) ? 0xff0092 : GoodPeople.coolMans.contains(ev.entity.getCommandSenderName()) ? 0x6ab6d6 : 0x00FF00);
                    GL11.glEnable(GL11.GL_LIGHTING);
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    GL11.glPopMatrix();
                }
            }
        }
    }

    @SubscribeEvent
    public void render(RenderWorldLastEvent event) {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        double x = player.prevPosX + (player.posX - player.prevPosX) * event.partialTicks;
        double y = player.prevPosY + (player.posY - player.prevPosY) * event.partialTicks;
        double z = player.prevPosZ + (player.posZ - player.prevPosZ) * event.partialTicks;
        if (player.worldObj.getWorldInfo().getTerrainType() instanceof WorldTypeZp) {
            float f1 = 0.0288f;
            String s = GoodPeople.dungeonMaster;
            FontRenderer fontrenderer = Minecraft.getMinecraft().fontRendererObj;
            GL11.glPushMatrix();
            GL11.glTranslated(-x + 0.5d, -y + 6.5d, -z + 0.5d);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GL11.glRotatef(-net.minecraft.client.renderer.entity.RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(net.minecraft.client.renderer.entity.RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
            GL11.glScalef(-f1, -f1, f1);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDepthMask(false);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) 255, (float) 255);
            OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
            Tessellator tessellator = Tessellator.instance;
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            tessellator.startDrawingQuads();
            int i = fontrenderer.getStringWidth(s) / 2;
            tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
            tessellator.addVertex((-i - 1), -1.0D, 0.0D);
            tessellator.addVertex((-i - 1), 8.0D, 0.0D);
            tessellator.addVertex((i + 1), 8.0D, 0.0D);
            tessellator.addVertex((i + 1), -1.0D, 0.0D);
            tessellator.draw();
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glDepthMask(true);
            fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, 0, 0xDC143C);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glPopMatrix();
        }
        if (net.minecraft.client.renderer.entity.RenderManager.debugBoundingBox) {
            for (int o = -4; o < 4; o++) {
                for (int j = -4; j < 4; j++) {
                    GL11.glPushMatrix();
                    GL11.glTranslated(-x, -y, -z);
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    GL11.glColor4f(1, 1, 0, 1);
                    GL11.glBegin(GL11.GL_LINES);
                    GL11.glVertex3d(player.chunkCoordX * 16 + 16 * o, 0, player.chunkCoordZ * 16 + 16 * j);
                    GL11.glVertex3d(player.chunkCoordX * 16 + 16 * o, 256, player.chunkCoordZ * 16 + 16 * j);

                    GL11.glVertex3d(player.chunkCoordX * 16 + 16 + 16 * o, 0, player.chunkCoordZ * 16 + 16 * j);
                    GL11.glVertex3d(player.chunkCoordX * 16 + 16 + 16 * o, 256, player.chunkCoordZ * 16 + 16 * j);

                    GL11.glVertex3d(player.chunkCoordX * 16 + 16 * o, 0, player.chunkCoordZ * 16 + 16 + 16 * j);
                    GL11.glVertex3d(player.chunkCoordX * 16 + 16 * o, 256, player.chunkCoordZ * 16 + 16 + 16 * j);

                    GL11.glVertex3d(player.chunkCoordX * 16 + 16 + 16 * o, 0, player.chunkCoordZ * 16 + 16 + 16 * j);
                    GL11.glVertex3d(player.chunkCoordX * 16 + 16 + 16 * o, 256, player.chunkCoordZ * 16 + 16 + 16 * j);
                    GL11.glEnd();
                    GL11.glLineWidth(3);
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glPopMatrix();
                }
            }
        }
        StructureSaver.BlockPos pos1 = StructureSaver.instance.pos1.get(player);
        StructureSaver.BlockPos pos2 = StructureSaver.instance.pos2.get(player);
        if (pos1 != null && pos2 != null) {
            GL11.glPushMatrix();
            GL11.glTranslated(-x, -y, -z);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glDisable(GL11.GL_TEXTURE_2D);

            int minX = Math.min(pos1.getX(), pos2.getX());
            int maxX = Math.max(pos1.getX(), pos2.getX()) + 1;

            int minY = Math.min(pos1.getY(), pos2.getY());
            int maxY = Math.max(pos1.getY(), pos2.getY()) + 1;

            int minZ = Math.min(pos1.getZ(), pos2.getZ());
            int maxZ = Math.max(pos1.getZ(), pos2.getZ()) + 1;

            GL11.glColor4f(1, 0, 0, 1);
            GL11.glBegin(GL11.GL_LINES);
            GL11.glVertex3d(minX, minY, minZ);
            GL11.glVertex3d(minX, maxY, minZ);
            GL11.glVertex3d(minX, minY, minZ);
            GL11.glVertex3d(minX, minY, maxZ);
            GL11.glVertex3d(minX, minY, minZ);
            GL11.glVertex3d(maxX, minY, minZ);
            GL11.glVertex3d(maxX, maxY, maxZ);
            GL11.glVertex3d(minX, maxY, maxZ);
            GL11.glVertex3d(maxX, maxY, maxZ);
            GL11.glVertex3d(maxX, minY, maxZ);
            GL11.glVertex3d(maxX, maxY, maxZ);
            GL11.glVertex3d(maxX, maxY, minZ);
            GL11.glVertex3d(maxX, maxY, minZ);
            GL11.glVertex3d(minX, maxY, minZ);
            GL11.glVertex3d(maxX, maxY, minZ);
            GL11.glVertex3d(maxX, minY, minZ);
            GL11.glVertex3d(maxX, minY, minZ);
            GL11.glVertex3d(maxX, minY, maxZ);
            GL11.glVertex3d(minX, maxY, minZ);
            GL11.glVertex3d(minX, maxY, maxZ);
            GL11.glVertex3d(minX, minY, maxZ);
            GL11.glVertex3d(minX, maxY, maxZ);
            GL11.glVertex3d(minX, minY, maxZ);
            GL11.glVertex3d(maxX, minY, maxZ);
            GL11.glEnd();
            minY += StructureSaver.instance.deltaY + 1;
            GL11.glColor4f(0, 0, 1, 1);
            GL11.glBegin(GL11.GL_LINES);
            GL11.glVertex3d(minX, minY, minZ);
            GL11.glVertex3d(minX, minY, maxZ);
            GL11.glVertex3d(minX, minY, minZ);
            GL11.glVertex3d(maxX, minY, minZ);
            GL11.glVertex3d(maxX, minY, maxZ);
            GL11.glVertex3d(minX, minY, maxZ);
            GL11.glVertex3d(maxX, minY, maxZ);
            GL11.glVertex3d(maxX, minY, minZ);
            GL11.glEnd();
            GL11.glLineWidth(3);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glPopMatrix();
        }

        if (player.getHeldItem() != null) {
            if (player.getHeldItem().getItem() instanceof ALauncherBase && player.getHeldItem().getTagCompound().getCompoundTag(Main.MODID).getInteger("ammo") > 0) {
                Entity entity = ((ALauncherBase) player.getHeldItem().getItem()).chooseTarget();
                if (entity != null) {
                    GL11.glPushMatrix();
                    GL11.glTranslated(-x, -y, -z);

                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    GL11.glColor4f(0, 1, 0, 1);

                    GL11.glBegin(GL11.GL_LINES);
                    GL11.glVertex3d(entity.posX, entity.posY - 32, entity.posZ);
                    GL11.glVertex3d(entity.posX, entity.posY + 32, entity.posZ);
                    GL11.glVertex3d(entity.posX - 32, entity.posY + entity.getEyeHeight(), entity.posZ);
                    GL11.glVertex3d(entity.posX + 32, entity.posY + entity.getEyeHeight(), entity.posZ);
                    GL11.glVertex3d(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ - 32);
                    GL11.glVertex3d(entity.posX, entity.posY + entity.getEyeHeight(), entity.posZ + 32);
                    GL11.glEnd();
                    GL11.glLineWidth(3);
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glPopMatrix();
                }
            }
            if (player.getHeldItem().getItem() instanceof ItemTransmitterTactic) {
                MovingObjectPosition movingObjectPosition = ((ItemTransmitterTactic) player.getHeldItem().getItem()).chooseMOV(player);
                if (movingObjectPosition != null && movingObjectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                    GL11.glPushMatrix();
                    GL11.glTranslated(-player.posX, -player.posY, -player.posZ);

                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glDisable(GL11.GL_TEXTURE_2D);
                    GL11.glColor4f(0, 0, 1, 1);

                    GL11.glBegin(GL11.GL_LINES);
                    GL11.glVertex3d(movingObjectPosition.hitVec.xCoord, movingObjectPosition.hitVec.yCoord - 31, movingObjectPosition.hitVec.zCoord);
                    GL11.glVertex3d(movingObjectPosition.hitVec.xCoord, movingObjectPosition.hitVec.yCoord + 33, movingObjectPosition.hitVec.zCoord);
                    GL11.glVertex3d(movingObjectPosition.hitVec.xCoord - 32, movingObjectPosition.hitVec.yCoord + 0.25f, movingObjectPosition.hitVec.zCoord);
                    GL11.glVertex3d(movingObjectPosition.hitVec.xCoord + 32, movingObjectPosition.hitVec.yCoord + 0.25f, movingObjectPosition.hitVec.zCoord);
                    GL11.glVertex3d(movingObjectPosition.hitVec.xCoord, movingObjectPosition.hitVec.yCoord + 0.25f, movingObjectPosition.hitVec.zCoord - 32);
                    GL11.glVertex3d(movingObjectPosition.hitVec.xCoord, movingObjectPosition.hitVec.yCoord + 0.25f, movingObjectPosition.hitVec.zCoord + 32);
                    GL11.glEnd();
                    GL11.glLineWidth(3);
                    GL11.glEnable(GL11.GL_TEXTURE_2D);
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glPopMatrix();
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    @SubscribeEvent
    public void onOverlayRender(RenderGameOverlayEvent.Pre ev) {
        int scaledWidth = ev.resolution.getScaledWidth();
        int scaledHeight = ev.resolution.getScaledHeight();
        RenderItem render = RenderItem.getInstance();
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;
        if (!player.capabilities.isCreativeMode) {
            net.minecraft.client.renderer.entity.RenderManager.debugBoundingBox = false;
        }
        if (mc.gameSettings.fovSetting < 70) {
            mc.gameSettings.fovSetting = 70;
        }
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glColor4f(1, 1, 1, 1);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        ItemStack stack = player.getHeldItem();
        if (ev.type == RenderGameOverlayEvent.ElementType.HOTBAR) {
            CrosshairManager.instance.updateStrafe(ev.partialTicks);
            if (!RenderManager.hideHud) {
                if (!GunItemRender.instance.isWatchingGun() && !Minecraft.getMinecraft().gameSettings.showDebugInfo) {
                    if (Main.modStatus == Main.ModStatus.DEMO) {
                        mc.fontRendererObj.drawStringWithShadow("Demo " + Main.VERSION, 2, 2, 0xffffff);
                    }
                    if (Main.modStatus == Main.ModStatus.DEV) {
                        mc.fontRendererObj.drawStringWithShadow("Dev preview " + Main.VERSION, 2, 2, 0xffffff);
                    }
                }
                if (player.inventory.hasItem(ItemsZp.dosimeter)) {
                    int x = MathHelper.floor_double(player.posX);
                    int z = MathHelper.floor_double(player.posZ);
                    if (EntityUtils.isEntityInHighRadiation(player)) {
                        String info = I18n.format("misc.rad.info") + " " + I18n.format("misc.rad.rad2");
                        mc.fontRendererObj.drawStringWithShadow(info, scaledWidth / 2 - mc.fontRendererObj.getStringWidth(info) / 2, 12, 0xdd3b3b);
                    } else if (EntityUtils.isEntityInLowRadiation(player)) {
                        String info = I18n.format("misc.rad.info") + " " + I18n.format("misc.rad.rad1");
                        mc.fontRendererObj.drawStringWithShadow(info, scaledWidth / 2 - mc.fontRendererObj.getStringWidth(info) / 2, 12, 0xcf9999);
                    } else {
                        String info = I18n.format("misc.rad.info") + " " + I18n.format("misc.rad.no");
                        mc.fontRendererObj.drawStringWithShadow(info, scaledWidth / 2 - mc.fontRendererObj.getStringWidth(info) / 2, 12, 0xd0d0d0);
                    }
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                }
                int j = 8 - player.inventory.currentItem;
                GL11.glPushMatrix();
                GL11.glDisable(GL11.GL_DEPTH_TEST);
                mc.getTextureManager().bindTexture(GameHud.components);
                mc.ingameGUI.drawTexturedModalRect((int) (scaledWidth / 2.0f) - j * 18 + 63, scaledHeight - 21, 0, 33, 18, 18);
                GL11.glEnable(GL11.GL_DEPTH_TEST);
                for (int i = 0; i < 9; i++) {
                    GL11.glDisable(GL11.GL_LIGHTING);
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glDisable(GL11.GL_DEPTH_TEST);
                    mc.getTextureManager().bindTexture(GameHud.components);
                    if (player.inventory.currentItem == i) {
                        mc.ingameGUI.drawTexturedModalRect((int) (scaledWidth / 2.0f) - (8 - i) * 18 + 63, scaledHeight - 21, 18, 33, 17, 17);
                    } else {
                        mc.ingameGUI.drawTexturedModalRect((int) (scaledWidth / 2.0f) - (8 - i) * 18 + 65, scaledHeight - 19, 0, 0, 14, 14);
                    }
                    GL11.glEnable(GL11.GL_DEPTH_TEST);
                    if (player.inventory.getStackInSlot(i) != null) {
                        ItemStack stack1 = player.inventory.getStackInSlot(i);
                        GL11.glPushMatrix();
                        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
                        RenderHelper.enableGUIStandardItemLighting();
                        GL11.glDisable(GL11.GL_BLEND);
                        GL11.glTranslatef((int) (scaledWidth / 2.0f) + i * 18 - 96, scaledHeight - 36, 500);
                        render.renderItemAndEffectIntoGUI(mc.fontRendererObj, mc.getTextureManager(), stack1, 16, 16);
                        render.renderItemOverlayIntoGUI(mc.fontRendererObj, mc.getTextureManager(), stack1, 16, 16);
                        RenderHelper.enableGUIStandardItemLighting();
                        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                        GL11.glPopMatrix();
                    }
                }
                GL11.glDisable(GL11.GL_LIGHTING);
                GL11.glPopMatrix();
                if (Main.settingsZp.progressBar.isFlag()) {
                    if (stack != null) {
                        int i1 = 0;
                        if (player.isUsingItem() && stack.getMaxItemUseDuration() > 0) {
                            i1 = player.getItemInUseDuration() * 100 / stack.getMaxItemUseDuration();
                        } else if (stack.hasTagCompound() && stack.getItem() instanceof AGunBase) {
                            AGunBase aGunBase = (AGunBase) stack.getItem();
                            if ((aGunBase.isInUnReloading(player) && stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("ammo") > 0) || (aGunBase.isInReloading(player) && stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("ammo") != aGunBase.getMaxAmmo())) {
                                int iR = aGunBase.getCdReload();
                                if (aGunBase.getCurrentFireMode(stack) == EnumFireModes.LAUNCHER) {
                                    ALauncherModuleBase aLauncherModuleBase = (ALauncherModuleBase) aGunBase.getCurrentModule(stack, EnumModule.UNDERBARREL).getMod();
                                    iR = aLauncherModuleBase.getReloadCd();
                                }
                                i1 = 100 - (player.getEntityData().getInteger("cdReload") * 100 / iR);
                            }
                        }
                        if (i1 > 0) {
                            GL11.glPushMatrix();
                            GL11.glEnable(GL11.GL_BLEND);
                            mc.getTextureManager().bindTexture(GameHud.components);
                            mc.ingameGUI.drawTexturedModalRect(scaledWidth / 2 - 75, scaledHeight / 2 + 44, 106, 0, 150, 4);
                            GL11.glColor3f(1.0f - i1 * 0.01f, 1.0f, 1.0f - i1 * 0.01f);
                            mc.ingameGUI.drawTexturedModalRect(scaledWidth / 2 - 74, scaledHeight / 2 + 45, 107, 5, (int) (148 * (i1 * 0.01f)), 2);
                            GL11.glPopMatrix();
                        }
                    }
                }
                if (Main.settingsZp.showPing.isFlag()) {
                    if (!(Minecraft.getMinecraft().gameSettings.guiScale == 0 && GunItemRender.instance.isWatchingGun())) {
                        int ping = PingManager.instance.localPing;
                        int pingColor = ping >= 130 && ping < 200 ? 0xffff00 : ping > 200 ? 0xff0000 : 0x90EE90;
                        String str = ping + "ms";
                        if (ping == -1 || ping > 9999) {
                            str = "...";
                            pingColor = 0xff0000;
                        }
                        RenderUtils.drawStringInSquare(str, 2, ev.resolution.getScaledHeight() - 11, pingColor);
                    }
                }
            }
        }
        if (ev.type == RenderGameOverlayEvent.ElementType.BOSSHEALTH) {
            ev.setCanceled(true);
            if (!RenderManager.hideHud) {
                if (BossStatus.healthScale > 0 && BossStatus.bossName != null) {
                    String s = BossStatus.bossName;
                    String s2 = (int) (BossStatus.healthScale * 100) + "%";
                    mc.fontRendererObj.drawStringWithShadow(s, scaledWidth / 2 - mc.fontRendererObj.getStringWidth(s) / 2, 10, 0xff0000);
                    mc.fontRendererObj.drawStringWithShadow(s2, scaledWidth / 2 - mc.fontRendererObj.getStringWidth(s2) / 2, 20, 0xffffff);
                }
            }
        }
        if (ev.type == RenderGameOverlayEvent.ElementType.CROSSHAIRS) {
            if (Minecraft.getMinecraft().gameSettings.thirdPersonView > 0) {
                ev.setCanceled(true);
            }
            if (PlayerMiscData.getPlayerData(player).isLying() || GunItemRender.instance.isInScope() || ClientUtils.isClientInNVG()) {
                Minecraft.getMinecraft().ingameGUI.prevVignetteBrightness = (float) ((double) Minecraft.getMinecraft().ingameGUI.prevVignetteBrightness + (double) (2.0f - Minecraft.getMinecraft().ingameGUI.prevVignetteBrightness) * 0.01D);
            }
            if (BinocularsItemRender.instance.isInScope()) {
                ev.setCanceled(true);
                GL11.glAlphaFunc(GL11.GL_GREATER, 0.0F);
                mc.getTextureManager().bindTexture(RenderManager.binocularsOverlay);
                RenderUtils.drawTexturedModalRectCustom(0, 0, scaledWidth, scaledHeight);
            }
            if (stack != null) {
                if (!RenderManager.hideHud) {
                    if (stack.getItem() == Items.compass || stack.getItem() == ItemsZp.gps) {
                        String text2 = null;
                        int rotation = MathHelper.floor_double((double) (player.rotationYawHead * 4.0F / 360.0F) + 0.5D) & 3;
                        switch (rotation) {
                            case 0: {
                                text2 = "[ +x ]( +Z )[ -x ]";
                                break;
                            }
                            case 1: {
                                text2 = "[ +z ]( -X )[ -z ]";
                                break;
                            }
                            case 2: {
                                text2 = "[ -x ]( -Z )[ +x ]";
                                break;
                            }
                            case 3: {
                                text2 = "[ -z ]( +X )[ +z ]";
                                break;
                            }
                        }
                        if (stack.getItem() == ItemsZp.gps) {
                            int x = MathHelper.floor_double(player.posX);
                            int z = MathHelper.floor_double(player.posZ);
                            String s = "[" + x + " | " + z + "]";
                            RenderUtils.drawStringInSquare(s, scaledWidth / 2 - mc.fontRendererObj.getStringWidth(s) / 2, 6, 0xffffff);
                        }
                        RenderUtils.drawStringInSquare(text2, scaledWidth / 2 - mc.fontRendererObj.getStringWidth(text2) / 2, 16, 0x00ff00);
                    }
                    if (stack.getItem() instanceof ALauncherBase) {
                        Entity entity = ((ALauncherBase) stack.getItem()).chooseTarget();
                        if (entity != null && stack.getTagCompound().getCompoundTag(Main.MODID).getInteger("ammo") > 0) {
                            mc.fontRendererObj.drawStringWithShadow(entity.getCommandSenderName(), scaledWidth / 2 - mc.fontRendererObj.getStringWidth(entity.getCommandSenderName()) / 2, scaledHeight / 2 - 64, 0x00FF00);
                        } else {
                            mc.fontRendererObj.drawStringWithShadow("...", scaledWidth / 2 - mc.fontRendererObj.getStringWidth("...") / 2, scaledHeight / 2 - 64, 0xFF0000);
                        }
                    }
                    if (stack.getItem() instanceof ItemTransmitterTactic) {
                        ItemTransmitterTactic transmitterTactic = (ItemTransmitterTactic) stack.getItem();
                        TileKalibrBlock tileKalibrBlock = transmitterTactic.getClosestBlock(player, player.worldObj);
                        if (tileKalibrBlock != null) {
                            mc.fontRendererObj.drawStringWithShadow(String.valueOf((int) transmitterTactic.getDistanceTo()), scaledWidth / 2, scaledHeight / 2 - 64, 0x00FF00);
                        } else {
                            mc.fontRendererObj.drawStringWithShadow("-", scaledWidth / 2, scaledHeight / 2 - 64, 0xFF0000);
                        }
                    }
                }
                if (stack.hasTagCompound() && stack.getItem() instanceof AGunBase) {
                    AGunBase aGunBase = (AGunBase) stack.getItem();
                    if (aGunBase.isPlayerInOpticScope(stack)) {
                        ModuleInfo modScope = ((AGunBase) stack.getItem()).getCurrentModule(stack, EnumModule.SCOPE);
                        ev.setCanceled(true);
                        GL11.glMatrixMode(GL11.GL_PROJECTION);
                        GL11.glPushMatrix();
                        GL11.glLoadIdentity();
                        GL11.glOrtho(0.0f, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight, 0.0f, 1.0f, 3000.0f);
                        GL11.glMatrixMode(GL11.GL_MODELVIEW);
                        int w = 2048;
                        int h = 512;
                        double k = (double) Minecraft.getMinecraft().displayHeight / h;
                        int scaledW = (int) Math.round(w * k);
                        int scaledH = (int) Math.round(h * k);
                        mc.renderEngine.bindTexture(((ItemScope) modScope.getMod()).getScopeGuiLocation());
                        int whCross = Main.settingsZp.scaleCross.isFlag() ? 4096 : 2048;
                        RenderUtils.drawTexturedModalRectCustom(Minecraft.getMinecraft().displayWidth / 2 - whCross / 2, Minecraft.getMinecraft().displayHeight / 2 - whCross / 2, whCross, whCross);
                        mc.renderEngine.bindTexture(RenderManager.scopeOverlay);
                        GL11.glAlphaFunc(GL11.GL_GREATER, 0.0F);
                        RenderUtils.drawTexturedModalRectCustom(Minecraft.getMinecraft().displayWidth / 2 - scaledW / 2, 0, scaledW, scaledH);
                        GL11.glMatrixMode(GL11.GL_PROJECTION);
                        GL11.glPopMatrix();
                        GL11.glMatrixMode(GL11.GL_MODELVIEW);
                        GL11.glPushMatrix();
                        GL11.glAlphaFunc(GL11.GL_GREATER, 0.0F);
                        GL11.glPopMatrix();
                    }
                }
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            EntityItem entityItem = ItemChecker.selectedItem;
            if (player.getCurrentArmor(3) != null) {
                if (mc.gameSettings.thirdPersonView == 0 && (EntityUtils.isInArmor(player, ItemsZp.gasmask, null, null, null) || EntityUtils.isInArmor(player, ItemsZp.rad_helmet, null, null, null) || EntityUtils.isInArmor(player, ItemsZp.aqualung_helmet, null, null, null) || EntityUtils.isInArmor(player, ItemsZp.indcostume_helmet, null, null, null))) {
                    mc.getTextureManager().bindTexture(RenderManager.blur);
                    RenderUtils.drawTexturedModalRectCustom(0, 0, scaledWidth, scaledHeight);
                }
            }
            if (!RenderManager.hideHud) {
                if (!Main.settingsZp.fancyDrop.isFlag()) {
                    if (entityItem != null) {
                        ItemStack itemStack = entityItem.getEntityItem();
                        if (itemStack.stackSize > 0) {
                            String pickUpFText = "[" + Keyboard.getKeyName(ClientProxy.keyPick.getKeyCode()) + "]";
                            String pickUpText = I18n.format("misc.pickUp");
                            String itemName = itemStack.getDisplayName();
                            GL11.glDisable(GL11.GL_BLEND);
                            mc.fontRendererObj.drawStringWithShadow(pickUpFText, scaledWidth / 2 - mc.fontRendererObj.getStringWidth(pickUpFText) / 2, scaledHeight / 2 + 15, 16777215);
                            mc.fontRendererObj.drawStringWithShadow(pickUpText, scaledWidth / 2 - mc.fontRendererObj.getStringWidth(pickUpText) / 2, scaledHeight / 2 + 25, 16777215);
                            mc.fontRendererObj.drawStringWithShadow(itemName, scaledWidth / 2 - mc.fontRendererObj.getStringWidth(itemName) / 2, scaledHeight / 2 + 35, 0x90EE90);
                            GL11.glPushMatrix();
                            float scale = 1.0f;
                            GL11.glTranslatef(scaledWidth / 2.0f - 24 * scale, scaledHeight / 2.0f + 30, 0);
                            GL11.glScalef(scale, scale, scale);
                            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
                            RenderHelper.enableGUIStandardItemLighting();
                            render.renderItemAndEffectIntoGUI(mc.fontRendererObj, mc.getTextureManager(), itemStack, 16, 16);
                            render.renderItemOverlayIntoGUI(mc.fontRendererObj, mc.getTextureManager(), itemStack, 16, 16);
                            RenderHelper.enableGUIStandardItemLighting();
                            GL11.glColor4f(1, 1, 1, 1);
                            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
                            GL11.glEnable(GL11.GL_BLEND);
                            GL11.glDisable(GL11.GL_LIGHTING);
                            GL11.glPopMatrix();
                        }
                    }
                }
            }
        }
        if (ev.type == RenderGameOverlayEvent.ElementType.DEBUG) {
            ev.setCanceled(true);
            String s4;
            GL11.glPushMatrix();
            RenderUtils.drawStringInSquare("Minecraft Forge", 2, 2, 16777215);
            RenderUtils.drawStringInSquare(mc.getWorldProviderName(), 2, 22, 16777215);
            long i5 = Runtime.getRuntime().maxMemory();
            long j5 = Runtime.getRuntime().totalMemory();
            long k5 = Runtime.getRuntime().freeMemory();
            long l5 = j5 - k5;
            s4 = "Used memory: " + l5 * 100L / i5 + "% (" + l5 / 1024L / 1024L + "MB) of " + i5 / 1024L / 1024L + "MB";
            RenderUtils.drawStringInSquare(s4, 2, 36, 14737632);
            s4 = "Allocated memory: " + j5 * 100L / i5 + "% (" + j5 / 1024L / 1024L + "MB)";
            RenderUtils.drawStringInSquare(s4, 2, 46, 14737632);

            String textName = Main.MODNAME;
            RenderUtils.drawStringInSquare(textName, 2, 12, 0xffffff);

            int x = MathHelper.floor_double(player.posX);
            int y = MathHelper.floor_double(player.posY - 1.0f);
            int z = MathHelper.floor_double(player.posZ);

            if (player.capabilities.isCreativeMode) {
                RenderUtils.drawStringInSquare(player.getDisplayName() + String.format(": [%s, %s, %s]", x, y, z), 2, 60, 0xffffff);
            } else {
                RenderUtils.drawStringInSquare(player.getDisplayName(), 2, 60, 0xffffff);
            }

            String[] fps = mc.debug.split(" ");

            String textFps = "FPS: " + fps[0];

            int clr = 0x90EE90;
            if (Integer.parseInt(fps[0]) >= 30 && Integer.parseInt(fps[0]) <= 50) {
                clr = 0xffff00;
            } else if (Integer.parseInt(fps[0]) < 30) {
                clr = 0xff0000;
            }

            String s = I18n.format("misc.warn.uns");
            if (Main.modStatus.isDisplayUnstableWarning()) {
                RenderUtils.drawStringInSquare(s, 2, 80, 0xff0000);
            }
            RenderUtils.drawStringInSquare(textFps, 2, 70, clr);
            RenderUtils.drawStringInSquare(I18n.format("message.plKilled") + " " + PlayerMiscData.getPlayerData(player).getPlayersKilled(), 2, 100, 0xFFFFFF);
            RenderUtils.drawStringInSquare(I18n.format("message.zmKilled") + " " + PlayerMiscData.getPlayerData(player).getZombiesKilled(), 2, 110, 0xFFFFFF);
            RenderUtils.drawStringInSquare(I18n.format("message.plDeaths") + " " + PlayerMiscData.getPlayerData(player).getPlayerDeaths(), 2, 120, 0xFFFFFF);

            GL11.glPopMatrix();
        }

        if (ev.type == RenderGameOverlayEvent.ElementType.PLAYER_LIST) {
            ev.setCanceled(true);

            ScoreObjective scoreobjective = mc.theWorld.getScoreboard().getObjectiveInDisplaySlot(0);
            NetHandlerPlayClient handler = mc.thePlayer.sendQueue;
            ScaledResolution res = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);
            int width = res.getScaledWidth();

            if ((!mc.isIntegratedServerRunning() || handler.playerInfoList.size() > 1 || scoreobjective != null)) {
                mc.mcProfiler.startSection("playerList");
                List<GuiPlayerInfo> players = handler.playerInfoList;
                int maxPlayers = handler.currentServerMaxPlayers;
                int rows = maxPlayers;
                int columns;

                for (columns = 1; rows > 20; rows = (maxPlayers + columns - 1) / columns) {
                    columns++;
                }

                int columnWidth = 300 / columns;

                if (columnWidth > 150) {
                    columnWidth = 150;
                }

                int left = (width - columns * columnWidth) / 2;
                byte border = 10;
                Gui.drawRect(left - 1, border - 1, left + columnWidth * columns, border + 9 * rows, Integer.MIN_VALUE);

                for (int i = 0; i < maxPlayers; i++) {
                    int xPos = left + i % columns * columnWidth;
                    int yPos = border + i / columns * 9;
                    Gui.drawRect(xPos, yPos, xPos + columnWidth - 1, yPos + 8, 553648107);
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    GL11.glEnable(GL11.GL_ALPHA_TEST);

                    if (i < players.size()) {
                        GuiPlayerInfo playergui = players.get(i);
                        ScorePlayerTeam team = mc.theWorld.getScoreboard().getPlayersTeam(playergui.name);
                        String displayName = ScorePlayerTeam.formatPlayerName(team, playergui.name);
                        mc.fontRendererObj.drawStringWithShadow(displayName, xPos + 2, yPos, 0x00FF00);
                        int ping = displayName.equals(player.getDisplayName()) ? PingManager.instance.localPing : -1;
                        if (PingManager.instance.getPingMap().containsKey(displayName)) {
                            ping = PingManager.instance.getPingMap().get(displayName);
                        }
                        String str = ping + "ms";
                        int pingColor = ping >= 130 && ping < 200 ? 0xffff00 : ping > 200 ? 0xff0000 : 0x90EE90;
                        if (ping == -1) {
                            str = "...";
                            pingColor = 0xff0000;
                        }
                        mc.fontRendererObj.drawStringWithShadow(str, xPos + columnWidth - mc.fontRendererObj.getStringWidth(str) - 2, yPos, pingColor);

                        if (scoreobjective != null) {
                            int endX = xPos + mc.fontRendererObj.getStringWidth(displayName) + 5;
                            int maxX = xPos + columnWidth - 10 - 5;

                            if (maxX - endX > 5) {
                                Score score = scoreobjective.getScoreboard().getValueFromObjective(playergui.name, scoreobjective);
                                String scoreDisplay = EnumChatFormatting.YELLOW + String.valueOf(score.getScorePoints());
                                mc.fontRendererObj.drawStringWithShadow(scoreDisplay, maxX - mc.fontRendererObj.getStringWidth(scoreDisplay), yPos, 16777215);
                            }
                        }
                    }
                }
            }
        }
    }
}
