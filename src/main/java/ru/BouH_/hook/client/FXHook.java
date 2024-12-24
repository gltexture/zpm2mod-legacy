package ru.BouH_.hook.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.achievement.GuiAchievement;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.stats.Achievement;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldProvider;
import org.lwjgl.opengl.GL11;
import ru.BouH_.Main;
import ru.BouH_.entity.particle.EntityParticleColoredCloud;
import ru.BouH_.gameplay.WorldManager;
import ru.BouH_.gameplay.client.ClientHandler;
import ru.BouH_.gameplay.client.CrosshairManager;
import ru.BouH_.proxy.CommonProxy;
import ru.BouH_.utils.ClientUtils;
import ru.BouH_.utils.RenderUtils;
import ru.hook.asm.Hook;
import ru.hook.asm.ReturnCondition;

public class FXHook {

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void displayAchievement(GuiAchievement guiAchievements, Achievement p_146256_1_) {
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void displayUnformattedAchievement(GuiAchievement guiAchievements, Achievement p_146255_1_) {
    }

    @Hook(injectOnExit = true)
    public static void renderParticle(EntityFireworkStarterFX fx, Tessellator p_70539_1_, float p_70539_2_, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_) {
        if (!Main.settingsZp.lowParticles.isFlag()) {
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(true);
        }
    }

    @Hook(returnCondition = ReturnCondition.ON_TRUE)
    public static boolean addEffect(EffectRenderer effectRenderer, EntityFX entityFX) {
        if (!Main.settingsZp.lowParticles.isFlag() || (entityFX instanceof EntityParticleColoredCloud)) {
            return false;
        }
        return Minecraft.getMinecraft().thePlayer.getDistance(entityFX.posX, entityFX.posY, entityFX.posZ) >= (double) (Minecraft.getMinecraft().gameSettings.renderDistanceChunks * Minecraft.getMinecraft().gameSettings.renderDistanceChunks) / 4;
    }

    @Hook(injectOnExit = true)
    public static void renderParticle(EntityDiggingFX fx, Tessellator p_70539_1_, float p_70539_2_, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_) {
        if (!Main.settingsZp.lowParticles.isFlag()) {
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(true);
        }
    }

    @Hook(injectOnExit = true)
    public static void renderParticle(EntityFX fx, Tessellator p_70539_1_, float p_70539_2_, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_) {
        if (!Main.settingsZp.lowParticles.isFlag()) {
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(true);
        }
    }

    @Hook(injectOnExit = true)
    public static void renderParticle(EntityBreakingFX fx, Tessellator p_70539_1_, float p_70539_2_, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_) {
        if (!Main.settingsZp.lowParticles.isFlag()) {
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(true);
        }
    }

    @Hook(injectOnExit = true)
    public static void renderParticle(EntitySpellParticleFX fx, Tessellator p_70539_1_, float p_70539_2_, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_) {
        if (!Main.settingsZp.lowParticles.isFlag()) {
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(true);
        }
    }

    @Hook(injectOnExit = true)
    public static void renderParticle(EntityFireworkOverlayFX fx, Tessellator p_70539_1_, float p_70539_2_, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_) {
        if (!Main.settingsZp.lowParticles.isFlag()) {
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(true);
        }
    }

    @Hook(injectOnExit = true)
    public static void renderParticle(EntityFireworkSparkFX fx, Tessellator p_70539_1_, float p_70539_2_, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_) {
        if (!Main.settingsZp.lowParticles.isFlag()) {
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(true);
        }
    }

    @Hook(injectOnExit = true)
    public static void renderEffect(RenderItem it, TextureManager manager, int x, int y) {
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
    }

    @Hook(injectOnExit = true)
    public static void setupViewBobbing(EntityRenderer entityRenderer, float p_78475_1_) {
        if (Main.settingsZp.strafes.isFlag()) {
            float f4 = CrosshairManager.instance.bobbingPrev + (CrosshairManager.instance.bobbing - CrosshairManager.instance.bobbingPrev) * p_78475_1_;
            GL11.glRotatef(f4, 1.0F, 0.0F, 0.0F);
        }
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void updateLightmap(EntityRenderer renderer, float p_78472_1_) {
        WorldClient worldclient = Minecraft.getMinecraft().theWorld;
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;

        if (worldclient != null) {
            for (int i = 0; i < 256; ++i) {
                float f1 = worldclient.getSunBrightness(1.0F) * 0.95F + 0.05F;
                float f2 = worldclient.provider.lightBrightnessTable[i / 16] * f1;
                float f3 = worldclient.provider.lightBrightnessTable[i % 16] * (renderer.torchFlickerX * 0.1F + 1.5F);

                if (worldclient.lastLightningBolt > 0) {
                    f2 = worldclient.provider.lightBrightnessTable[i / 16];
                }

                float f4 = f2 * (worldclient.getSunBrightness(1.0F) * 0.65F + 0.35F);
                float f5 = f2 * (worldclient.getSunBrightness(1.0F) * 0.65F + 0.35F);
                float f6 = f3 * ((f3 * 0.6F + 0.4F) * 0.6F + 0.4F);
                float f7 = f3 * (f3 * f3 * 0.6F + 0.4F);
                float f8 = f4 + f3;
                float f9 = f5 + f6;
                float f10 = f2 + f7;
                f8 = f8 * 0.96F + 0.03F;
                f9 = f9 * 0.96F + 0.03F;
                f10 = f10 * 0.96F + 0.03F;
                float f11;

                if (worldclient.provider.dimensionId == 1) {
                    f8 = 0.22F + f3 * 0.75F;
                    f9 = 0.28F + f6 * 0.75F;
                    f10 = 0.25F + f7 * 0.75F;
                }
                boolean flag = false;
                float f12;
                float fB = RenderUtils.isBright() ? 1.0f : 0.0f;
                if (ClientUtils.isClientInNVG() || ClientUtils.isClientInNightVisionScope()) {
                    flag = true;
                    f11 = FXHook.getNVGBrightness(player, p_78472_1_) + fB;
                    f12 = 1.0F / f8;

                    if (f12 > 1.0F / f9) {
                        f12 = 1.0F / f9;
                    }

                    if (f12 > 1.0F / f10) {
                        f12 = 1.0F / f10;
                    }

                    f8 = 0;
                    f9 = FXHook.getNVGBrightness(player, p_78472_1_);
                    f10 = FXHook.getNVGBrightness(player, p_78472_1_) * 0.6f;
                } else {
                    if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.nightVision)) {
                        f11 = FXHook.getNightVisionBrightness(player, p_78472_1_) + fB;
                        f12 = 1.0F / f8;

                        if (f12 > 1.0F / f9) {
                            f12 = 1.0F / f9;
                        }

                        if (f12 > 1.0F / f10) {
                            f12 = 1.0F / f10;
                        }

                        f8 = f8 * (1.0F - f11) + f8 * f12 * f11;
                        f9 = f9 * (1.0F - f11) + f9 * f12 * f11;
                        f10 = f10 * (1.0F - f11) + f10 * f12 * f11;
                    } else if (Minecraft.getMinecraft().thePlayer.isPotionActive(CommonProxy.betterVision)) {
                        f11 = FXHook.getBetterVisionBrightness(player, p_78472_1_) + fB;
                        f12 = 1.0F / f8;

                        if (f12 > 1.0F / f9) {
                            f12 = 1.0F / f9;
                        }

                        if (f12 > 1.0F / f10) {
                            f12 = 1.0F / f10;
                        }

                        f8 = f8 * (1.0F - f11) + f8 * f12 * f11;
                        f9 = f9 * (1.0F - f11) + f9 * f12 * f11;
                        f10 = f10 * (1.0F - f11) + f10 * f12 * f11;
                    } else if (Minecraft.getMinecraft().thePlayer.isPotionActive(CommonProxy.adrenaline)) {
                        f11 = FXHook.getAdrenalineVisionBrightness(player, p_78472_1_) + fB;
                        f12 = 1.0F / f8;

                        if (f12 > 1.0F / f9) {
                            f12 = 1.0F / f9;
                        }

                        if (f12 > 1.0F / f10) {
                            f12 = 1.0F / f10;
                        }

                        f8 = f8 * (1.0F - f11) + f8 * f12 * f11;
                        f9 = f9 * (0.9F - f11) + f9 * f12 * f11;
                        f10 = f10 * (1.0F - f11) + f10 * f12 * f11;
                    }
                    int day1 = (int) (Minecraft.getMinecraft().theWorld.getTotalWorldTime() / 24000);
                    if (WorldManager.is7Night(Minecraft.getMinecraft().theWorld) || Minecraft.getMinecraft().theWorld.getWorldInfo().getTerrainType().getWorldTypeName().equals(CommonProxy.worldTypeHardCoreZp.getWorldTypeName())) {
                        float plagueMultiplier = FXHook.get7NightBrightness(player);
                        f9 *= plagueMultiplier;
                        f10 *= plagueMultiplier;
                    }
                }
                if (!flag && Minecraft.getMinecraft().thePlayer.isPotionActive(CommonProxy.zpm)) {
                    float plagueMultiplier = FXHook.getPlagueMultiplier(player);
                    f9 *= plagueMultiplier;
                    f10 *= plagueMultiplier;
                }

                if (f8 > 1.0F) {
                    f8 = 1.0F;
                }

                if (f9 > 1.0F) {
                    f9 = 1.0F;
                }

                if (f10 > 1.0F) {
                    f10 = 1.0F;
                }
                f11 = !Minecraft.getMinecraft().gameSettings.hideGUI && player.capabilities.isCreativeMode ? Minecraft.getMinecraft().gameSettings.gammaSetting * 3.0f : -0.55f;
                f8 += ClientHandler.instance.nightBrightConstant * 0.02f;
                f10 += ClientHandler.instance.nightBrightConstant * 0.18f;
                f11 += ClientHandler.instance.nightBrightConstant * 0.012f;

                f11 += fB;
                f12 = 1.0F - f8;
                float f13 = 1.0F - f9;
                float f14 = 1.0F - f10;
                f12 = 1.0F - f12 * f12 * f12 * f12;
                f13 = 1.0F - f13 * f13 * f13 * f13;
                f14 = 1.0F - f14 * f14 * f14 * f14;
                f8 = f8 * (1.0F - f11) + f12 * f11;
                f9 = f9 * (1.0F - f11) + f13 * f11;
                f10 = f10 * (1.0F - f11) + f14 * f11;
                f8 = f8 * 0.96F + 0.03F;
                f9 = f9 * 0.96F + 0.03F;
                f10 = f10 * 0.96F + 0.03F;

                if (f8 > 1.0F) {
                    f8 = 1.0F;
                }

                if (f9 > 1.0F) {
                    f9 = 1.0F;
                }

                if (f10 > 1.0F) {
                    f10 = 1.0F;
                }

                if (f8 < 0.0F) {
                    f8 = 0.0F;
                }

                if (f9 < 0.0F) {
                    f9 = 0.0F;
                }

                if (f10 < 0.0F) {
                    f10 = 0.0F;
                }

                short short1 = 255;
                int j = (int) (f8 * 255.0F);
                int k = (int) (f9 * 255.0F);
                int l = (int) (f10 * 255.0F);
                renderer.lightmapColors[i] = short1 << 24 | j << 16 | k << 8 | l;
            }

            renderer.lightmapTexture.updateDynamicTexture();
            renderer.lightmapUpdateNeeded = false;
        }
    }

    public static float getBetterVisionBrightness(EntityPlayer p_82830_1_, float p_82830_2_) {
        int i = p_82830_1_.getActivePotionEffect(CommonProxy.betterVision).getDuration();
        return i > 200 ? 0.375F : 0.2F + MathHelper.sin(((float) i - p_82830_2_) * (float) Math.PI * 0.2F) * 0.3F;
    }

    public static float getAdrenalineVisionBrightness(EntityPlayer p_82830_1_, float p_82830_2_) {
        int i = p_82830_1_.getActivePotionEffect(CommonProxy.adrenaline).getDuration();
        return i > 200 ? 0.1F : 0.02F + MathHelper.sin(((float) i - p_82830_2_) * (float) Math.PI * 0.2F) * 0.4F;
    }

    public static float get7NightBrightness(EntityPlayer p_82830_1_) {
        int i = (int) (p_82830_1_.worldObj.getWorldTime() % 24000) - 9000;
        if (i < 0) {
            i = 0;
        }
        return ((float) Math.pow((float) i / 7500.0f - 1.0f, 2));
    }

    public static float getNightVisionBrightness(EntityPlayer p_82830_1_, float p_82830_2_) {
        int i = p_82830_1_.getActivePotionEffect(Potion.nightVision).getDuration();
        return i > 200 ? 1.0F : 0.7F + MathHelper.sin(((float) i - p_82830_2_) * (float) Math.PI * 0.2F) * 0.3F;
    }

    public static float getNVGBrightness(EntityPlayer p_82830_1_, float p_82830_2_) {
        int i = p_82830_1_.ticksExisted;
        return 1.0F + MathHelper.sin(((float) i - p_82830_2_) * (float) Math.PI * 0.15F) * 0.1F;
    }

    public static float getPlagueMultiplier(EntityPlayer p_82830_1_) {
        int i = p_82830_1_.getActivePotionEffect(CommonProxy.zpm).getDuration();
        return i < 12000 ? (float) i / 12000 : 1.0f;
    }

    @Hook(returnCondition = ReturnCondition.ON_TRUE, returnNull = true)
    public static boolean renderSky(RenderGlobal renderGlobal, float p_72714_1_) {
        return ClientUtils.isClientInNVG() || ClientUtils.isClientInNightVisionScope();
    }

    @Hook(returnCondition = ReturnCondition.ON_TRUE, returnNull = true)
    public static boolean renderClouds(RenderGlobal renderGlobal, float p_72718_1_) {
        return ClientUtils.isClientInNVG() || ClientUtils.isClientInNightVisionScope();
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void renderRainSnow(EntityRenderer entityRenderer, float p_78474_1_) {
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void addRainParticles(EntityRenderer entityRenderer) {
    }

    @Hook(injectOnExit = true)
    public static void func_147051_a(GuiContainerCreative guiContainerCreative, CreativeTabs p_147051_1_) {
        GL11.glEnable(GL11.GL_BLEND);
    }

    @Hook
    public static void drawEntityOnScreen(GuiInventory guiInventory, int p_147046_0_, int p_147046_1_, int p_147046_2_, float p_147046_3_, float p_147046_4_, EntityLivingBase p_147046_5_) {
        GL11.glEnable(GL11.GL_BLEND);
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static float[] calcSunriseSunsetColors(WorldProvider provider, float p_76560_1_, float p_76560_2_) {
        if (ClientUtils.isClientInNVG() || ClientUtils.isClientInNightVisionScope()) {
            return null;
        } else {
            float f2 = 0.5F;
            float f3 = MathHelper.cos(p_76560_1_ * (float) Math.PI * 2.0F);
            float f4 = -0.0F;
            float[] colorsSunriseSunset = new float[4];
            if (f3 >= f4 - f2 && f3 <= f4 + f2) {
                float f5 = (f3 - f4) / f2 * 0.5F + 0.5F;
                float f6 = 1.0F - (1.0F - MathHelper.sin(f5 * (float) Math.PI)) * 0.99F;
                f6 *= f6;
                if (p_76560_1_ < 0.5f) {
                    colorsSunriseSunset[0] = f5 * 0.3F + 0.8F;
                    colorsSunriseSunset[1] = f5 * f5 * 0.7F + 0.1F;
                    colorsSunriseSunset[2] = f5 * f5 * 0.1F + 0.4F;
                } else {
                    colorsSunriseSunset[0] = f5 * 0.3F + 0.7F;
                    colorsSunriseSunset[1] = f5 * f5 * 0.7F + 0.2F;
                    colorsSunriseSunset[2] = f5 * f5 * 0.0F + 0.2F;
                }
                colorsSunriseSunset[3] = f6;
                return colorsSunriseSunset;
            } else {
                return null;
            }
        }
    }


    @Hook(returnCondition = ReturnCondition.ON_TRUE, returnAnotherMethod = "getSkyNew")
    public static boolean getSkyColor(WorldProvider provider, Entity cameraEntity, float partialTicks) {
        return ClientUtils.isClientInNVG() || ClientUtils.isClientInNightVisionScope() || Minecraft.getMinecraft().thePlayer.isPotionActive(CommonProxy.zpm);
    }

    public static Vec3 getSkyNew(WorldProvider provider, Entity cameraEntity, float partialTicks) {
        if (ClientUtils.isClientInNVG() || ClientUtils.isClientInNightVisionScope()) {
            return Vec3.createVectorHelper(0.0f, 1.0f, 0.0f);
        } else {
            Vec3 vec3 = provider.worldObj.getSkyColorBody(cameraEntity, partialTicks);
            EntityPlayerSP entityPlayerSP = Minecraft.getMinecraft().thePlayer;
            float f1 = FXHook.getPlagueMultiplier(entityPlayerSP);
            vec3.yCoord *= f1;
            vec3.zCoord *= f1;
            return vec3;
        }
    }

    @Hook
    public static void drawActivePotionEffects(InventoryEffectRenderer ev) {
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_BLEND);
    }
}
