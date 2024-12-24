package ru.BouH_.weather.render;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.particle.EntityRainFX;
import net.minecraft.client.particle.EntitySmokeFX;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.IRenderHandler;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import org.lwjgl.opengl.GL11;
import ru.BouH_.Main;
import ru.BouH_.weather.base.WeatherHandler;

public class RenderRain {
    public static final ResourceLocation locationRainPng = new ResourceLocation(Main.MODID + ":textures/misc/rain.png");
    public static final ResourceLocation locationSnowPng = new ResourceLocation(Main.MODID + ":textures/misc/rain.png");
    public static final ResourceLocation locationWeakRainPng = new ResourceLocation(Main.MODID + ":textures/misc/weak_rain.png");
    public static final ResourceLocation locationWeakSnowPng = new ResourceLocation(Main.MODID + ":textures/misc/weak_snow.png");
    public static final ResourceLocation locationStrongRainPng = new ResourceLocation(Main.MODID + ":textures/misc/strong_rain.png");
    public static final ResourceLocation locationStrongSnowPng = new ResourceLocation(Main.MODID + ":textures/misc/strong_snow.png");

    public static RenderRain instance = new RenderRain();

    @SubscribeEvent
    public void render(RenderWorldLastEvent event) {
        this.renderSandStorm(Minecraft.getMinecraft().entityRenderer, event.partialTicks);
    }

    public void renderSandStorm(EntityRenderer entityRenderer, float p_78474_1_) {
        IRenderHandler renderer;
        if ((renderer = entityRenderer.mc.theWorld.provider.getWeatherRenderer()) != null) {
            renderer.render(p_78474_1_, entityRenderer.mc.theWorld, entityRenderer.mc);
            return;
        }

        float f1 = WeatherHandler.instance.getWeatherRain().currentRainStrength;
        if (f1 > 0.0F) {
            entityRenderer.enableLightmap(p_78474_1_);

            if (entityRenderer.rainXCoords == null) {
                entityRenderer.rainXCoords = new float[1024];
                entityRenderer.rainYCoords = new float[1024];

                for (int i = 0; i < 32; ++i) {
                    for (int j = 0; j < 32; ++j) {
                        float f2 = (float) (j - 16);
                        float f3 = (float) (i - 16);
                        float f4 = MathHelper.sqrt_float(f2 * f2 + f3 * f3);
                        entityRenderer.rainXCoords[i << 5 | j] = -f3 / f4;
                        entityRenderer.rainYCoords[i << 5 | j] = f2 / f4;
                    }
                }
            }

            EntityLivingBase entitylivingbase = entityRenderer.mc.renderViewEntity;
            WorldClient worldclient = entityRenderer.mc.theWorld;
            int k2 = MathHelper.floor_double(entitylivingbase.posX);
            int l2 = MathHelper.floor_double(entitylivingbase.posY);
            int i3 = MathHelper.floor_double(entitylivingbase.posZ);
            Tessellator tessellator = Tessellator.instance;
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            double d0 = entitylivingbase.lastTickPosX + (entitylivingbase.posX - entitylivingbase.lastTickPosX) * (double) p_78474_1_;
            double d1 = entitylivingbase.lastTickPosY + (entitylivingbase.posY - entitylivingbase.lastTickPosY) * (double) p_78474_1_;
            double d2 = entitylivingbase.lastTickPosZ + (entitylivingbase.posZ - entitylivingbase.lastTickPosZ) * (double) p_78474_1_;
            int k = MathHelper.floor_double(d1);
            byte b0 = 5;

            if (entityRenderer.mc.gameSettings.fancyGraphics) {
                b0 = 10;
            }

            byte b1 = -1;
            float f5 = (float) entityRenderer.rendererUpdateCount + p_78474_1_;

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

            for (int l = i3 - b0; l <= i3 + b0; ++l) {
                for (int i1 = k2 - b0; i1 <= k2 + b0; ++i1) {
                    int j1 = (l - i3 + 16) * 32 + i1 - k2 + 16;
                    float f6 = entityRenderer.rainXCoords[j1] * 0.5F;
                    float f7 = entityRenderer.rainYCoords[j1] * 0.5F;
                    BiomeGenBase biomegenbase = worldclient.getBiomeGenForCoords(i1, l);

                    if (biomegenbase.canSpawnLightningBolt() || biomegenbase.getEnableSnow()) {
                        int k1 = worldclient.getPrecipitationHeight(i1, l);
                        int l1 = l2 - b0;
                        int i2 = l2 + b0;

                        if (l1 < k1) {
                            l1 = k1;
                        }

                        if (i2 < k1) {
                            i2 = k1;
                        }

                        float f8 = 1.0F;

                        int j2 = Math.max(k1, k);

                        if (l1 != i2) {
                            entityRenderer.random.setSeed((long) i1 * i1 * 3121 + i1 * 45238971L ^ (long) l * l * 418711 + l * 13761L);
                            float f9 = biomegenbase.getFloatTemperature(i1, l1, l);
                            float f10;
                            double d4;

                            if (worldclient.getWorldChunkManager().getTemperatureAtHeight(f9, k1) >= 0.15F) {
                                if (b1 != 0) {
                                    if (b1 >= 0) {
                                        tessellator.draw();
                                    }

                                    b1 = 0;
                                    if (f1 <= 0.3f) {
                                        entityRenderer.mc.getTextureManager().bindTexture(RenderRain.locationWeakRainPng);
                                    } else if (f1 <= 0.5f) {
                                        entityRenderer.mc.getTextureManager().bindTexture(RenderRain.locationRainPng);
                                    } else {
                                        entityRenderer.mc.getTextureManager().bindTexture(RenderRain.locationStrongRainPng);
                                    }
                                    tessellator.startDrawingQuads();
                                }

                                f10 = ((float) (entityRenderer.rendererUpdateCount + i1 * i1 * 3121 + i1 * 45238971 + l * l * 418711 + l * 13761 & 31) + p_78474_1_) / 32.0F * (3.0F + entityRenderer.random.nextFloat());
                                double d3 = (double) ((float) i1 + 0.5F) - entitylivingbase.posX;
                                d4 = (double) ((float) l + 0.5F) - entitylivingbase.posZ;
                                float f12 = MathHelper.sqrt_double(d3 * d3 + d4 * d4) / (float) b0;
                                float f13 = 1.0F;
                                tessellator.setBrightness(worldclient.getLightBrightnessForSkyBlocks(i1, j2, l, 0));
                                tessellator.setColorRGBA_F(f13, f13, f13, ((1.0F - f12 * f12) * 0.5F + 0.5F) * f1);
                                tessellator.setTranslation(-d0, -d1, -d2);
                                tessellator.addVertexWithUV((double) ((float) i1 - f6) + 0.5D, l1, (double) ((float) l - f7) + 0.5D, 0.0F * f8, (float) l1 * f8 / 4.0F + f10 * f8);
                                tessellator.addVertexWithUV((double) ((float) i1 + f6) + 0.5D, l1, (double) ((float) l + f7) + 0.5D, f8, (float) l1 * f8 / 4.0F + f10 * f8);
                                tessellator.addVertexWithUV((double) ((float) i1 + f6) + 0.5D, i2, (double) ((float) l + f7) + 0.5D, f8, (float) i2 * f8 / 4.0F + f10 * f8);
                                tessellator.addVertexWithUV((double) ((float) i1 - f6) + 0.5D, i2, (double) ((float) l - f7) + 0.5D, 0.0F * f8, (float) i2 * f8 / 4.0F + f10 * f8);
                                tessellator.setTranslation(0.0D, 0.0D, 0.0D);
                            } else {
                                if (b1 != 1) {
                                    if (b1 >= 0) {
                                        tessellator.draw();
                                    }

                                    b1 = 1;
                                    if (f1 <= 0.3f) {
                                        entityRenderer.mc.getTextureManager().bindTexture(RenderRain.locationWeakSnowPng);
                                    } else if (f1 <= 0.5f) {
                                        entityRenderer.mc.getTextureManager().bindTexture(RenderRain.locationSnowPng);
                                    } else {
                                        entityRenderer.mc.getTextureManager().bindTexture(RenderRain.locationStrongSnowPng);
                                    }
                                    tessellator.startDrawingQuads();
                                }

                                f10 = ((float) (entityRenderer.rendererUpdateCount & 511) + p_78474_1_) / 512.0F;
                                float f16 = entityRenderer.random.nextFloat() + f5 * 0.01F * (float) entityRenderer.random.nextGaussian();
                                float f11 = entityRenderer.random.nextFloat() + f5 * (float) entityRenderer.random.nextGaussian() * 0.001F;
                                d4 = (double) ((float) i1 + 0.5F) - entitylivingbase.posX;
                                double d5 = (double) ((float) l + 0.5F) - entitylivingbase.posZ;
                                float f14 = MathHelper.sqrt_double(d4 * d4 + d5 * d5) / (float) b0;
                                float f15 = 1.0F;
                                tessellator.setBrightness((worldclient.getLightBrightnessForSkyBlocks(i1, j2, l, 0) * 3 + 15728880) / 4);
                                tessellator.setColorRGBA_F(f15, f15, f15, ((1.0F - f14 * f14) * 0.3F + 0.5F) * f1);
                                tessellator.setTranslation(-d0, -d1, -d2);
                                tessellator.addVertexWithUV((double) ((float) i1 - f6) + 0.5D, l1, (double) ((float) l - f7) + 0.5D, 0.0F * f8 + f16, (float) l1 * f8 / 4.0F + f10 * f8 + f11);
                                tessellator.addVertexWithUV((double) ((float) i1 + f6) + 0.5D, l1, (double) ((float) l + f7) + 0.5D, f8 + f16, (float) l1 * f8 / 4.0F + f10 * f8 + f11);
                                tessellator.addVertexWithUV((double) ((float) i1 + f6) + 0.5D, i2, (double) ((float) l + f7) + 0.5D, f8 + f16, (float) i2 * f8 / 4.0F + f10 * f8 + f11);
                                tessellator.addVertexWithUV((double) ((float) i1 - f6) + 0.5D, i2, (double) ((float) l - f7) + 0.5D, 0.0F * f8 + f16, (float) i2 * f8 / 4.0F + f10 * f8 + f11);
                                tessellator.setTranslation(0.0D, 0.0D, 0.0D);
                            }
                        }
                    }
                }
            }

            if (b1 >= 0) {
                tessellator.draw();
            }

            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
            entityRenderer.disableLightmap(p_78474_1_);
        }
    }

    public void addRainParticles(EntityRenderer entityRenderer) {
        float f = WeatherHandler.instance.getWeatherRain().currentRainStrength;
        if (!entityRenderer.mc.gameSettings.fancyGraphics) {
            f /= 2.0F;
        }

        if (f != 0.0F) {
            entityRenderer.random.setSeed((long) entityRenderer.rendererUpdateCount * 312987231L);
            EntityLivingBase entitylivingbase = entityRenderer.mc.renderViewEntity;
            WorldClient worldclient = entityRenderer.mc.theWorld;
            int i = MathHelper.floor_double(entitylivingbase.posX);
            int j = MathHelper.floor_double(entitylivingbase.posY);
            int k = MathHelper.floor_double(entitylivingbase.posZ);
            byte b0 = 20;
            double d0 = 0.0D;
            double d1 = 0.0D;
            double d2 = 0.0D;
            int l = 0;
            int i1 = (int) (120.0F * f * f);

            if (entityRenderer.mc.gameSettings.particleSetting == 1) {
                i1 >>= 1;
            } else if (entityRenderer.mc.gameSettings.particleSetting == 2) {
                i1 = 0;
            }

            for (int j1 = 0; j1 < i1; ++j1) {
                int k1 = i + entityRenderer.random.nextInt(b0) - entityRenderer.random.nextInt(b0);
                int l1 = k + entityRenderer.random.nextInt(b0) - entityRenderer.random.nextInt(b0);
                int i2 = worldclient.getPrecipitationHeight(k1, l1);
                Block block = worldclient.getBlock(k1, i2 - 1, l1);
                BiomeGenBase biomegenbase = worldclient.getBiomeGenForCoords(k1, l1);

                if (i2 <= j + b0 && i2 >= j - b0 && biomegenbase.canSpawnLightningBolt() && biomegenbase.getFloatTemperature(k1, i2, l1) >= 0.15F) {
                    float f1 = entityRenderer.random.nextFloat();
                    float f2 = entityRenderer.random.nextFloat();

                    if (block.getMaterial() == Material.lava) {
                        entityRenderer.mc.effectRenderer.addEffect(new EntitySmokeFX(worldclient, (float) k1 + f1, (double) ((float) i2 + 0.1F) - block.getBlockBoundsMinY(), (float) l1 + f2, 0.0D, 0.0D, 0.0D));
                    } else if (block.getMaterial() != Material.air) {
                        ++l;

                        if (entityRenderer.random.nextInt(l) == 0) {
                            d0 = (float) k1 + f1;
                            d1 = (double) ((float) i2 + 0.1F) - block.getBlockBoundsMinY();
                            d2 = (float) l1 + f2;
                        }

                        entityRenderer.mc.effectRenderer.addEffect(new EntityRainFX(worldclient, (float) k1 + f1, (double) ((float) i2 + 0.1F) - block.getBlockBoundsMinY(), (float) l1 + f2));
                    }
                }
            }
            if (!Main.settingsZp.ambientVolume.isFlag()) {
                if (l > 0 && entityRenderer.random.nextInt(3) < entityRenderer.rainSoundCounter++) {
                    entityRenderer.rainSoundCounter = 0;
                    float f1 = Math.max(f + 0.3f, 0.75f);
                    float f2 = f * 2.0f;
                    if (d1 > entitylivingbase.posY + 1.0D && worldclient.getPrecipitationHeight(MathHelper.floor_double(entitylivingbase.posX), MathHelper.floor_double(entitylivingbase.posZ)) > MathHelper.floor_double(entitylivingbase.posY)) {
                        entityRenderer.mc.theWorld.playSound(d0, d1, d2, "ambient.weather.rain", 0.15F * f2, f1, false);
                    } else {
                        entityRenderer.mc.theWorld.playSound(d0, d1, d2, "ambient.weather.rain", 0.25F * f2, f1, false);
                    }
                }
            }
        }
    }
}
