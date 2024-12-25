package ru.BouH_.gameplay.client;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.BiomeGenOcean;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.opengl.GL11;
import ru.BouH_.Main;
import ru.BouH_.entity.ieep.Hunger;
import ru.BouH_.entity.ieep.Thirst;
import ru.BouH_.gameplay.WorldManager;
import ru.BouH_.init.ItemsZp;
import ru.BouH_.proxy.CommonProxy;
import ru.BouH_.utils.EntityUtils;
import ru.BouH_.world.biome.BiomeCity;
import ru.BouH_.world.biome.BiomeIndustry;
import ru.BouH_.world.biome.BiomeMilitary;
import ru.BouH_.world.biome.BiomeRad;

@SideOnly(Side.CLIENT)
public class GameHud {
    public static GameHud instance = new GameHud();
    public static ResourceLocation components = new ResourceLocation(Main.MODID, "textures/gui/components.png");
    public static ResourceLocation icons = new ResourceLocation("textures/gui/icons.png");
    public float healthCurr;
    public float hungerCurr;
    public float thirstCurr;
    public float overlayHealthCurr;
    public float overlayHungerCurr;
    public float overlayThirstCurr;
    public float healthCd;
    public float hungerCd;
    public float thirstCd;

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent ev) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;
        if (!mc.isGamePaused() && player != null) {
            if (player.capabilities.isCreativeMode) {
                this.healthCurr = 0;
                this.hungerCurr = 0;
                this.thirstCurr = 0;
                this.overlayHealthCurr = 100;
                this.overlayHungerCurr = 100;
                this.overlayThirstCurr = 100;
                this.healthCd = 0;
                this.hungerCd = 0;
                this.thirstCd = 0;
            } else {
                this.updateHealthHud(player);
                this.updateFoodHud(player);
                this.updateThirstHud(player);
            }
        }
    }

    private void updateHealthHud(EntityPlayerSP player) {
        int f1 = (int) player.getHealth();
        int f2 = (int) player.prevHealth;
        if (this.healthCurr > f1) {
            this.healthCurr = f1;
            this.healthCd = 25;
        } else if (this.healthCurr < f1) {
            if (this.healthCd == 0) {
                this.healthCurr += 1;
            } else if (f1 > f2) {
                this.healthCd = 25;
            }
            if (this.overlayHealthCurr <= f1) {
                this.overlayHealthCurr = f1;
            }
        }
        if (this.healthCd <= 0) {
            if (this.overlayHealthCurr > f1) {
                this.overlayHealthCurr -= 1;
            }
        } else {
            this.healthCd -= 1;
        }
        if (this.overlayHealthCurr <= this.healthCurr) {
            this.healthCd = 25;
        }
        player.prevHealth = f1;
    }

    private void updateFoodHud(EntityPlayerSP player) {
        int f1 = Hunger.getHunger(player).getHunger();
        int f2 = Hunger.getHunger(player).getPrevHunger();
        if (this.hungerCurr > f1) {
            this.hungerCurr = f1;
            this.hungerCd = 25;
        } else if (this.hungerCurr < f1) {
            if (this.hungerCd == 0) {
                this.hungerCurr += 1;
            } else if (f1 > f2) {
                this.hungerCd = 25;
            }
            if (this.overlayHungerCurr <= f1) {
                this.overlayHungerCurr = f1;
            }
        }
        if (this.hungerCd <= 0) {
            if (this.overlayHungerCurr > f1) {
                this.overlayHungerCurr -= 1;
            }
        } else {
            this.hungerCd -= 1;
        }
        if (this.overlayHungerCurr <= this.hungerCurr) {
            this.hungerCd = 25;
        }
        Hunger.getHunger(player).setPrevHunger(f1);
    }

    private void updateThirstHud(EntityPlayerSP player) {
        int f1 = Thirst.getThirst(player).getThirst();
        int f2 = Thirst.getThirst(player).getPrevThirst();
        if (this.thirstCurr > f1) {
            this.thirstCurr = f1;
            this.thirstCd = 25;
        } else if (this.thirstCurr < f1) {
            if (this.thirstCd == 0) {
                this.thirstCurr += 1;
            } else if (f1 > f2) {
                this.thirstCd = 25;
            }
            if (this.overlayThirstCurr <= f1) {
                this.overlayThirstCurr = f1;
            }
        }
        if (this.thirstCd <= 0) {
            if (this.overlayThirstCurr > f1) {
                this.overlayThirstCurr -= 1;
            }
        } else {
            this.thirstCd -= 1;
        }
        if (this.overlayThirstCurr <= this.thirstCurr) {
            this.thirstCd = 25;
        }
        Thirst.getThirst(player).setPrevThirst(f1);
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public void onOverlayRender(RenderGameOverlayEvent.Pre ev) {
        int scaledWidth = ev.resolution.getScaledWidth();
        int scaledHeight = ev.resolution.getScaledHeight();
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;

        if (ev.type == RenderGameOverlayEvent.ElementType.AIR || ev.type == RenderGameOverlayEvent.ElementType.ARMOR || ev.type == RenderGameOverlayEvent.ElementType.FOOD || ev.type == RenderGameOverlayEvent.ElementType.HEALTH || ev.type == RenderGameOverlayEvent.ElementType.EXPERIENCE) {
            ev.setCanceled(true);
            if (!RenderManager.hideHud) {
                if (!player.capabilities.isCreativeMode) {
                    float f1 = Math.max(player.getHealth(), 1);
                    float f2 = Hunger.getHunger(player).getHunger();
                    float f3 = Thirst.getThirst(player).getThirst();

                    String armorText = player.getTotalArmorValue() + "/" + 20;
                    mc.fontRendererObj.drawStringWithShadow(armorText, scaledWidth / 2 - 68, scaledHeight - 59, 0xcfcfcf);

                    String xpText = String.valueOf(player.experienceLevel);
                    mc.fontRendererObj.drawStringWithShadow(xpText, scaledWidth / 2 - 51 + mc.fontRendererObj.getStringWidth(armorText), scaledHeight - 59, 0xeef27c);

                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    mc.getTextureManager().bindTexture(GameHud.components);

                    BiomeGenBase base = player.worldObj.getBiomeGenForCoords((int) player.posX, (int) player.posZ);
                    int danger = 0;
                    if (WorldManager.is7Night(player.worldObj)) {
                        danger = 4;
                    } else {
                        if (base instanceof BiomeMilitary) {
                            danger = 3;
                        } else if (base instanceof BiomeCity || base instanceof BiomeGenOcean) {
                            danger = 1;
                        } else if (base instanceof BiomeIndustry) {
                            danger = 2;
                        } else if (base instanceof BiomeRad) {
                            danger = 4;
                        }
                    }

                    for (int i = 0; i < danger; i++) {
                        mc.fontRendererObj.drawStringWithShadow(I18n.format("misc.hazard"), scaledWidth / 2 + 37, scaledHeight - 48, 0xff1c1c);
                        mc.getTextureManager().bindTexture(GameHud.components);
                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                        mc.ingameGUI.drawTexturedModalRect(scaledWidth / 2 + 37 + i * 11, scaledHeight - 38, 11, 194, 10, 10);
                    }

                    boolean flag1 = player.isPotionActive(19) || player.isPotionActive(28);
                    mc.ingameGUI.drawTexturedModalRect(scaledWidth / 2 - 79, scaledHeight - 34, 0, 211, 102, 7);
                    mc.ingameGUI.drawTexturedModalRect(scaledWidth / 2 - 78, scaledHeight - 34, 1, 187, (int) this.overlayHealthCurr, 7);
                    if (flag1) {
                        GL11.glColor4f(0.75f, 1.0f, 0.7f, 1.0f);
                    }
                    mc.ingameGUI.drawTexturedModalRect(scaledWidth / 2 - 78, scaledHeight - 34, 1, 204, (int) this.healthCurr, 7);
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    mc.ingameGUI.drawTexturedModalRect(scaledWidth / 2 + 24, scaledHeight - 34, flag1 ? 111 : 103, 211, 7, 7);

                    boolean flag2 = player.isPotionActive(17);
                    mc.ingameGUI.drawTexturedModalRect(scaledWidth / 2 - 79, scaledHeight - 42, 0, 225, 102, 7);
                    mc.ingameGUI.drawTexturedModalRect(scaledWidth / 2 - 78, scaledHeight - 42, 1, 187, (int) this.overlayHungerCurr, 7);
                    if (flag2) {
                        GL11.glColor4f(1.0f, 0.7f, 0.85f, 1.0f);
                    }
                    mc.ingameGUI.drawTexturedModalRect(scaledWidth / 2 - 78, scaledHeight - 42, 1, 232, (int) this.hungerCurr, 7);
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    mc.ingameGUI.drawTexturedModalRect(scaledWidth / 2 + 23, scaledHeight - 42, flag2 ? 110 : 102, 239, 8, 7);

                    boolean flag3 = player.isPotionActive(31);
                    mc.ingameGUI.drawTexturedModalRect(scaledWidth / 2 - 79, scaledHeight - 50, 0, 239, 102, 7);
                    mc.ingameGUI.drawTexturedModalRect(scaledWidth / 2 - 78, scaledHeight - 50, 1, 187, (int) this.overlayThirstCurr, 7);
                    if (flag3) {
                        GL11.glColor4f(0.75f, 1.0f, 0.6f, 1.0f);
                    }
                    mc.ingameGUI.drawTexturedModalRect(scaledWidth / 2 - 78, scaledHeight - 50, 1, 218, (int) this.thirstCurr, 7);
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

                    mc.ingameGUI.drawTexturedModalRect(scaledWidth / 2 + 23, scaledHeight - 50, flag3 ? 108 : 102, 225, 7, 7);
                    mc.ingameGUI.drawTexturedModalRect(scaledWidth / 2 - 79, scaledHeight - 26, 0, 253, (int) (player.experience * 158), 2);
                    mc.ingameGUI.drawTexturedModalRect(scaledWidth / 2 - 79, scaledHeight - 24, 0, 255, 158, 1);

                    mc.ingameGUI.drawTexturedModalRect(scaledWidth / 2 - 64 + mc.fontRendererObj.getStringWidth(armorText), scaledHeight - 61, 0, 194, 10, 10);

                    mc.getTextureManager().bindTexture(GameHud.icons);
                    mc.ingameGUI.drawTexturedModalRect(scaledWidth / 2 - 79, scaledHeight - 60, 34, 9, 9, 9);
                    if (!((EntityUtils.isInArmor(player, ItemsZp.aqualung_helmet, ItemsZp.aqualung_chestplate, ItemsZp.aqualung_leggings, ItemsZp.aqualung_boots) || EntityUtils.isInArmor(player, ItemsZp.indcostume_helmet, ItemsZp.indcostume_chestplate, ItemsZp.indcostume_leggings, ItemsZp.indcostume_boots)) && player.isInsideOfMaterial(Material.water) && player.inventory.hasItem(ItemsZp.oxygen))) {
                        if (mc.thePlayer.isInsideOfMaterial(Material.water)) {
                            int l3 = mc.thePlayer.getAir();
                            int k5 = MathHelper.ceiling_double_int((double) (l3 - 2) * 10.0D / 300.0D);
                            int i4 = MathHelper.ceiling_double_int((double) l3 * 10.0D / 300.0D) - k5;
                            int l1 = scaledWidth / 2 + 50;
                            int l2 = scaledHeight / 2 + 16;
                            for (int j4 = 0; j4 < k5 + i4; ++j4) {
                                if (j4 < k5) {
                                    mc.ingameGUI.drawTexturedModalRect(l1 - j4 * 10 - 9, l2, 16, 18, 9, 9);
                                } else {
                                    mc.ingameGUI.drawTexturedModalRect(l1 - j4 * 10 - 9, l2, 25, 18, 9, 9);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
