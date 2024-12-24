package ru.BouH_.items.gun.render;

import cpw.mods.fml.common.eventhandler.EventPriority;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.event.FOVUpdateEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import ru.BouH_.Main;
import ru.BouH_.entity.ieep.PlayerMiscData;
import ru.BouH_.gameplay.client.GameHud;
import ru.BouH_.gameplay.client.RenderManager;
import ru.BouH_.items.gun.base.AGunBase;
import ru.BouH_.items.gun.base.EnumFireModes;
import ru.BouH_.items.gun.modules.ItemBipod;
import ru.BouH_.items.gun.modules.ItemLaser;
import ru.BouH_.items.gun.modules.ItemScope;
import ru.BouH_.items.gun.modules.base.ALauncherModuleBase;
import ru.BouH_.items.gun.modules.base.EnumModule;
import ru.BouH_.items.gun.modules.base.ItemModule;
import ru.BouH_.items.gun.modules.base.ModuleInfo;
import ru.BouH_.items.gun.render.modules.gui.GuiModules;
import ru.BouH_.items.gun.render.modules.selector.Selection;
import ru.BouH_.items.gun.render.slot.ModuleSection;
import ru.BouH_.items.gun.render.slot.SlotInfo;
import ru.BouH_.items.gun.tracer.Tracer;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.packets.gun.PacketSwitchFireMode;
import ru.BouH_.proxy.ClientProxy;
import ru.BouH_.utils.EntityUtils;
import ru.BouH_.utils.RenderUtils;
import ru.BouH_.utils.SoundUtils;

import java.util.List;

public final class GunItemRender implements IItemRenderer {
    private static final ResourceLocation flash = new ResourceLocation(Main.MODID + ":textures/misc/flash.png");
    private static final ResourceLocation[] muzzleFlash = new ResourceLocation[]{new ResourceLocation(Main.MODID + ":textures/misc/muzzleflash1.png"), new ResourceLocation(Main.MODID + ":textures/misc/muzzleflash2.png"), new ResourceLocation(Main.MODID + ":textures/misc/muzzleflash3.png"), new ResourceLocation(Main.MODID + ":textures/misc/muzzleflash4.png"), new ResourceLocation(Main.MODID + ":textures/misc/muzzleflash5.png"), new ResourceLocation(Main.MODID + ":textures/misc/muzzleflash6.png")};
    private static final ResourceLocation[] muzzleflashEnt1 = new ResourceLocation[]{new ResourceLocation(Main.MODID + ":textures/misc/muzzleflashEnt1_0.png"), new ResourceLocation(Main.MODID + ":textures/misc/muzzleflashEnt1_1.png"), new ResourceLocation(Main.MODID + ":textures/misc/muzzleflashEnt1_2.png")};
    private static final ResourceLocation[] muzzleflashEnt2 = new ResourceLocation[]{new ResourceLocation(Main.MODID + ":textures/misc/muzzleflashEnt2_0.png"), new ResourceLocation(Main.MODID + ":textures/misc/muzzleflashEnt2_1.png"), new ResourceLocation(Main.MODID + ":textures/misc/muzzleflashEnt2_2.png")};
    public static GunItemRender instance = new GunItemRender();
    public final Selection selection;
    public int muzzleFlashTimer;
    public int muzzleFlashId;
    private int shootTimer;
    private float screenShake;
    private boolean isReloading;
    private boolean isWatchingGun;
    private boolean isRunning;
    private boolean isPlayingShutterAnim;
    private boolean isScoping;
    private float shutter;
    private float shutterPrev;
    private float scope;
    private float scopePrev;
    private float recoil;
    private float recoilPrev;
    private float run;
    private float runPrev;
    private float reload;
    private float reloadPrev;
    private int shakeStage = 1;
    private int ticksAfterScoping;
    private float shakeMultiplier = 3.0f;

    private GunItemRender() {
        this.selection = new Selection();
    }

    @SubscribeEvent
    public void onPressKey(InputEvent.KeyInputEvent ev) {
        KeyBinding keySeeGun = ClientProxy.keySeeGun;
        KeyBinding keyR = ClientProxy.keyRight;
        KeyBinding keyL = ClientProxy.keyLeft;
        KeyBinding keyU = ClientProxy.keyUp;
        KeyBinding keyD = ClientProxy.keyDown;
        KeyBinding keyX = ClientProxy.keyConfirm;
        KeyBinding keySwitch = ClientProxy.keySwitch;
        ItemStack stack = Minecraft.getMinecraft().thePlayer.getHeldItem();

        if (keySwitch.isPressed()) {
            this.trySwitchFireMode();
        }

        if (keySeeGun.isPressed() && !this.isScoping && !this.isReloading && this.isGun(stack)) {
            if (this.isWatchingGun) {
                this.stopGunWatching();
            } else {
                this.selection.startSelecting(stack);
                this.startWatchAnimation();
            }
        } else if (this.selection.isKeyboard()) {
            boolean flag = this.isWatchingGun && this.isGun(stack);
            if (keyD.isPressed()) {
                if (flag) {
                    this.selection.getKeyboardSelector().changeSelection(0, -1);
                }
            } else if (keyU.isPressed()) {
                if (flag) {
                    this.selection.getKeyboardSelector().changeSelection(0, 1);
                }
            }
            if (keyR.isPressed()) {
                if (flag) {
                    this.selection.getKeyboardSelector().changeSelection(1, 0);
                }
            } else if (keyL.isPressed()) {
                if (flag) {
                    this.selection.getKeyboardSelector().changeSelection(-1, 0);
                }
            }
            if (keyX.isPressed() && this.isGun(stack)) {
                this.performSlotAction(stack, this.selection.currentSlotSelected);
                this.selection.getKeyboardSelector().setCurrentX(0);
            }
        }
    }

    public void stopGunWatching() {
        this.stopWatchAnimation();
        this.shootTimer = 20;
    }

    public void performSlotAction(ItemStack itemStack, SlotInfo slotInfo) {
        if (slotInfo != null) {
            if (slotInfo.getItemModule() != null) {
                SoundUtils.playMonoSound(Main.MODID + ":select");
                if (slotInfo.isMainSlot()) {
                    this.selection.clearModifier(itemStack, this.selection.getSelectedModule().getEnumModule());
                } else {
                    this.selection.setModifier(itemStack, this.selection.getSelectedModule().getId());
                }
            }
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent ev) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;
        if (ev.phase == TickEvent.Phase.START) {
            if (!mc.isGamePaused() && player != null) {
                if (mc.currentScreen != null && !(mc.currentScreen instanceof GuiModules)) {
                    this.stopGunWatching();
                }
                PlayerMiscData playerMiscData = PlayerMiscData.getPlayerData(player);
                if (playerMiscData.getGunInaccuracyTimer() == 0) {
                    this.shakeMultiplier = 3.0f;
                }
                this.scopePrev = this.scope;
                this.runPrev = this.run;
                this.recoilPrev = this.recoil;
                this.reloadPrev = this.reload;
                this.shutterPrev = this.shutter;
                if (!this.isScoping && this.ticksAfterScoping > 0) {
                    this.ticksAfterScoping--;
                }
                if (this.muzzleFlashTimer > 0) {
                    this.muzzleFlashTimer--;
                }
                if (this.shootTimer > 0) {
                    this.shootTimer--;
                }
            }
        }
        this.recoil = (float) ((double) this.recoil - this.recoil * 0.25f);
        this.screenShake = (float) ((double) this.screenShake - this.screenShake * 0.5f);
    }

    @SubscribeEvent
    public void onFov(FOVUpdateEvent e) {
        ItemStack stack = Minecraft.getMinecraft().thePlayer.getHeldItem();
        if (this.isScoping) {
            float float1 = this.scope * 0.008f;
            if (this.isGun(stack)) {
                AGunBase gun = (AGunBase) stack.getItem();
                if (this.ticksAfterScoping == 3 && gun.isPlayerInOpticScope(stack)) {
                    ModuleInfo modScope = gun.getCurrentModule(stack, EnumModule.SCOPE);
                    ItemScope itemScope = ((ItemScope) modScope.getMod());
                    float1 = itemScope.getFov();
                }
            }
            e.newfov -= float1;
        }
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON || type == ItemRenderType.INVENTORY;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return false;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack itemRendering, Object... data) {
        Tessellator tessellator = Tessellator.instance;
        if (type == ItemRenderType.INVENTORY) {
            IIcon icon = Minecraft.getMinecraft().thePlayer.getItemIcon(itemRendering, 0);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
            if (icon != null) {
                this.renderWeaponInGui(itemRendering, icon);
                if (itemRendering.hasEffect(0)) {
                    GL11.glDepthFunc(GL11.GL_EQUAL);
                    GL11.glDisable(GL11.GL_LIGHTING);
                    Minecraft.getMinecraft().getTextureManager().bindTexture(ItemRenderer.RES_ITEM_GLINT);
                    GL11.glEnable(GL11.GL_BLEND);
                    OpenGlHelper.glBlendFunc(768, 1, 1, 0);
                    float f7 = 1.0F;
                    GL11.glColor4f(0.5F * f7, 0.25F * f7, 0.8F * f7, 1.0F);
                    GL11.glMatrixMode(GL11.GL_TEXTURE);
                    GL11.glPushMatrix();
                    float f8 = 5.0F;
                    GL11.glScalef(f8, f8, f8);
                    float f9 = (Minecraft.getSystemTime() % 18000.0F) / 18000.0F;
                    GL11.glTranslatef(f9, 0.0F, 0.0F);
                    GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
                    this.renderWeaponInGui(itemRendering, icon);
                    GL11.glPopMatrix();
                    GL11.glPushMatrix();
                    GL11.glScalef(f8, f8, f8);
                    f9 = (Minecraft.getSystemTime() % 16000.0F) / 16000.0F;
                    GL11.glTranslatef(-f9, 0.0F, 0.0F);
                    GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
                    this.renderWeaponInGui(itemRendering, icon);
                    GL11.glPopMatrix();
                    GL11.glMatrixMode(GL11.GL_MODELVIEW);
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glEnable(GL11.GL_LIGHTING);
                    GL11.glDepthFunc(GL11.GL_LEQUAL);
                    OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                }
            }
        } else {
            EntityLivingBase entity = (EntityLivingBase) data[1];
            AGunBase stack = (AGunBase) itemRendering.getItem();
            if (stack != null && itemRendering.hasTagCompound()) {
                AGunBase.GunType renderType = ((AGunBase) itemRendering.getItem()).getRenderType();
                IIcon icon = entity.getItemIcon(itemRendering, 0);
                if (entity instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) entity;

                    if (player instanceof EntityPlayerSP) {
                        if (this.isRunning) {
                            this.run = Math.min(this.runPrev + 7.0f, 25.0f);
                        } else {
                            this.run = Math.max(this.runPrev - 12.0f, 0);
                        }

                        if (!this.isWatchingGun) {
                            if (!this.isRunning && !this.isReloading && stack.getCurrentFireMode(itemRendering) == EnumFireModes.LAUNCHER) {
                                this.shutter = Math.min(this.shutterPrev + 5.0f, 18.0f);
                            } else {
                                if (this.isPlayingShutterAnim) {
                                    if (this.shutter < 20) {
                                        this.shutter = this.shutterPrev + 6.0f;
                                    } else {
                                        this.stopShutterAnimation();
                                    }
                                } else {
                                    this.shutter = Math.max(this.shutterPrev - 6.0f, 0);
                                }
                            }
                        }

                        if (this.isReloading) {
                            this.reload = Math.min(this.reloadPrev + 5.0f, 25.0f);
                        } else {
                            this.reload = Math.max(this.reloadPrev - 5.0f, 0);
                        }

                        if (this.isScoping) {
                            this.scope = Math.min(this.scopePrev + 8.0f, 25.0f);
                        } else {
                            if (this.ticksAfterScoping == 0) {
                                this.scope = Math.max(this.scopePrev - 8.0f, 0);
                            }
                        }

                        if (stack.isInReloadingAnim(player)) {
                            this.startReloadAnimation();
                        } else {
                            this.stopReloadAnimation();
                        }

                        if (this.shootTimer == 0) {
                            if (!this.isReloading) {
                                if ((player.isSprinting() || stack.isOnLadder(player) || stack.getCurrentFireMode(itemRendering) == EnumFireModes.SAFE) && !this.isWatchingGun && !this.isScoping) {
                                    this.startRunAnimation();
                                } else {
                                    this.stopRunAnimation();
                                }
                            } else {
                                this.stopRunAnimation();
                                this.stopWatchAnimation();
                            }
                        } else {
                            this.run = 0;
                            this.runPrev = 0;
                            this.stopRunAnimation();
                            this.stopWatchAnimation();
                        }
                        if (Minecraft.getMinecraft().entityRenderer.itemRenderer.equippedProgress < 1) {
                            this.scope = 0;
                            this.scopePrev = 0;
                        } else {
                            if (Mouse.isButtonDown(1) && this.canStartScoping(player) && !stack.isOnLadder(player) && stack.getCurrentFireMode(itemRendering) != EnumFireModes.SAFE) {
                                this.ticksAfterScoping = 3;
                                this.startScopeAnimation();
                            } else {
                                this.stopScopeAnimation();
                            }
                        }
                    }
                    if (type == ItemRenderType.EQUIPPED_FIRST_PERSON) {
                        if (icon != null) {
                            float reloadConst = this.reloadPrev + (this.reload - this.reloadPrev) * RenderUtils.partialTicks;
                            float runConst = this.runPrev + (this.run - this.runPrev) * RenderUtils.partialTicks;
                            float recoilConst = this.recoilPrev + (this.recoil - this.recoilPrev) * RenderUtils.partialTicks;
                            float shutterConst = this.shutterPrev + (this.shutter - this.shutterPrev) * RenderUtils.partialTicks;
                            float scopeConst = this.scopePrev + (this.scope - this.scopePrev) * RenderUtils.partialTicks;

                            if (this.isWatchingGun) {
                                GL11.glRotatef(70, 1, 1, 0);
                                GL11.glRotatef(-60, 1, 0, 0);
                                GL11.glRotatef(18, 0, 1, 0);
                                GL11.glTranslatef(-0.1f, 0.1f, 1.0f);
                                reloadConst = 0.0f;
                                runConst = 0.0f;
                                recoilConst = 0.0f;
                                shutterConst = 0.0f;
                                scopeConst = 0.0f;
                            }

                            if (stack.isPlayerInOpticScope(itemRendering)) {
                                if (this.canRenderMuzzleFlash1st(player)) {
                                    GL11.glPushMatrix();
                                    if (stack.addTranslateMuzzle1st(player, itemRendering)) {
                                        GL11.glScalef(1.25f, 1.25f, 1.25f);
                                        GL11.glRotatef(40, 0, 0, 1);
                                        GL11.glRotatef(15, 1, 0, 0);
                                        GL11.glRotatef(-30, 0, 1, 0);
                                        GL11.glTranslatef(-0.5f, -1.65f, -0.565f);
                                        GL11.glPushMatrix();
                                        GL11.glTranslatef(0.1f, 0, 0);
                                        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, 1, 0, 0);
                                        this.drawMuzzleFlashFirstPerson(GunItemRender.flash);
                                        GL11.glPopMatrix();
                                        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, 1, 0, 0);
                                        this.drawMuzzleFlashFirstPerson(GunItemRender.muzzleFlash[this.muzzleFlashId]);
                                    }
                                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                                    GL11.glPopMatrix();
                                }
                            } else {
                                if (renderType == AGunBase.GunType.RPG) {
                                    GL11.glRotatef(-scopeConst / 5, 0, 1, 0);
                                    GL11.glRotatef(scopeConst / 5, 1, 1, 0);
                                    GL11.glTranslatef(-scopeConst / 65, -scopeConst / 200, -scopeConst / 82);

                                    GL11.glRotatef(recoilConst * 5.0f, 0, 0, 1);
                                    GL11.glTranslatef(-recoilConst * 0.05f, -recoilConst * 0.08f, 0);

                                    GL11.glTranslatef(shutterConst * -0.007f, shutterConst * -0.014f, 0);
                                    GL11.glRotatef(shutterConst * 0.5f, 0, 0, 1);

                                    GL11.glRotatef(runConst * -0.4f, 1, 0, 0);
                                    GL11.glTranslatef(0.0f, runConst * 0.015f, 0.0f);
                                    GL11.glRotatef(runConst * -0.8f, 0, 0, 1);

                                    GL11.glRotatef(reloadConst, 0, 1, 0);
                                    GL11.glRotatef(-reloadConst * 1.5f, 0, 1, 1);
                                    GL11.glRotatef(reloadConst * 1.8f, 1, 1, 0);

                                    GL11.glPushMatrix();
                                    GL11.glScalef(1.5f, 1.5f, 1.5f);
                                    GL11.glTranslatef(-0.05f, -0.12f, 0.02f);
                                    GL11.glRotatef(10, 0, 1, 0);
                                    GL11.glRotatef(-6, 1, 1, 0);
                                    GL11.glRotatef(-2, 1, 0, 1);
                                    if (this.isWatchingGun) {
                                        GL11.glTranslatef(0.2f, 0, -0.1f);
                                    }
                                    this.renderWeapon(entity, tessellator, itemRendering, icon);
                                    GL11.glPopMatrix();

                                    GL11.glPushMatrix();
                                    GL11.glTranslatef(0.2f, -0.35f, -0.5f);
                                    GL11.glRotatef(90, 1, 1, 0);
                                    GL11.glRotatef(-6, 1, 0, 0);
                                    GL11.glRotatef(305, 0, 0, 1);
                                    if (!this.isWatchingGun) {
                                        RenderUtils.renderArm(true);
                                    }
                                    GL11.glPopMatrix();

                                    GL11.glPushMatrix();
                                    GL11.glScalef(1.3f, 1.3f, 1.3f);
                                    GL11.glRotatef(-reloadConst * 2, 1, 0, 1);
                                    GL11.glTranslatef(0.35f, -0.125f, -0.325f);
                                    GL11.glRotatef(55, 1, 1, 0);
                                    GL11.glRotatef(-8, 0, 0, 1);
                                    if (!this.isWatchingGun) {
                                        RenderUtils.renderArm(false);
                                    }
                                    GL11.glPopMatrix();

                                    if (this.canRenderMuzzleFlash1st(player)) {
                                        GL11.glPushMatrix();
                                        if (stack.addTranslateMuzzle1st(player, itemRendering)) {
                                            GL11.glScalef(1.25f, 1.25f, 1.25f);
                                            GL11.glRotatef(45, 0, 0, 1);
                                            GL11.glRotatef(15, 1, 0, 0);
                                            GL11.glRotatef(-25, 0, 1, 0);
                                            GL11.glTranslatef(0.35f, -1.4f, -0.425f);
                                            GL11.glPushMatrix();
                                            GL11.glTranslatef(0, 0, -0.12f);
                                            OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, 1, 0, 0);
                                            this.drawMuzzleFlashFirstPerson(GunItemRender.flash);
                                            GL11.glPopMatrix();
                                            OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, 1, 0, 0);
                                            this.drawMuzzleFlashFirstPerson(GunItemRender.muzzleFlash[this.muzzleFlashId]);
                                        }
                                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                                        GL11.glPopMatrix();
                                    }
                                } else if (renderType == AGunBase.GunType.CROSSBOW) {
                                    GL11.glRotatef(-scopeConst / 5, 0, 1, 0);
                                    GL11.glRotatef(scopeConst / 5, 1, 1, 0);
                                    GL11.glTranslatef(-scopeConst / 65, -scopeConst / 200, -scopeConst / 82);

                                    GL11.glRotatef(recoilConst * 5.0f, 0, 0, 1);
                                    GL11.glTranslatef(-recoilConst * 0.05f, -recoilConst * 0.125f, 0);

                                    GL11.glTranslatef(shutterConst * -0.007f, shutterConst * -0.014f, 0);
                                    GL11.glRotatef(shutterConst * 0.5f, 0, 0, 1);

                                    GL11.glRotatef(runConst * -0.4f, 1, 0, 0);
                                    GL11.glTranslatef(0.0f, runConst * 0.015f, 0.0f);
                                    GL11.glRotatef(runConst * -0.8f, 0, 0, 1);

                                    GL11.glRotatef(reloadConst, 0, 1, 0);
                                    GL11.glRotatef(-reloadConst * 1.2f, 0, 1, 1);
                                    GL11.glRotatef(-reloadConst * 1.2f, 1, 1, 0);

                                    GL11.glPushMatrix();
                                    GL11.glScalef(1.5f, 1.5f, 1.5f);
                                    if (this.isWatchingGun) {
                                        GL11.glTranslatef(0.1375f, -0.1225f, 0.00975f);
                                        GL11.glRotatef(12, 0, 1, 0);
                                        GL11.glRotatef(-6, 1, 1, 0);
                                        GL11.glRotatef(-2, 1, 0, 1);
                                    } else {
                                        GL11.glRotatef(90, 1, 0, 0);
                                        GL11.glTranslatef(0.16f, -0.15f, 0.08f);
                                        GL11.glRotatef(40, 0, 1, 0);
                                        GL11.glRotatef(-50, 0, 0, 1);
                                    }
                                    this.renderWeapon(entity, tessellator, itemRendering, icon);
                                    GL11.glPopMatrix();

                                    GL11.glPushMatrix();
                                    GL11.glTranslatef(0.45f, -0.4f, -0.46f);
                                    GL11.glRotatef(90, 1, 1, 0);
                                    GL11.glRotatef(-6, 1, 0, 0);
                                    GL11.glRotatef(305, 0, 0, 1);
                                    if (!this.isWatchingGun) {
                                        RenderUtils.renderArm(true);
                                    }
                                    GL11.glPopMatrix();

                                    GL11.glPushMatrix();
                                    GL11.glScalef(1.3f, 1.3f, 1.3f);
                                    GL11.glRotatef(-reloadConst * 2, 1, 0, 1);
                                    GL11.glTranslatef(0.475f, -0.135f, -0.4f);
                                    GL11.glRotatef(55, 1, 1, 0);
                                    GL11.glRotatef(-8, 0, 0, 1);
                                    if (!this.isWatchingGun) {
                                        RenderUtils.renderArm(false);
                                    }
                                    GL11.glPopMatrix();
                                } else if (renderType == AGunBase.GunType.RIFLE) {
                                    GL11.glRotatef(-scopeConst / 5, 0, 1, 0);
                                    GL11.glRotatef(scopeConst / 5, 1, 1, 0);
                                    GL11.glTranslatef(-scopeConst / 65, -scopeConst / 200, -scopeConst / 82);

                                    GL11.glRotatef(recoilConst * 5.0f, 0, 0, 1);
                                    GL11.glTranslatef(-recoilConst * 0.05f, -recoilConst * 0.125f, 0);

                                    GL11.glTranslatef(shutterConst * -0.007f, shutterConst * -0.014f, 0);
                                    GL11.glRotatef(shutterConst * 0.5f, 0, 0, 1);

                                    GL11.glRotatef(runConst * -0.4f, 1, 0, 0);
                                    GL11.glTranslatef(0.0f, runConst * 0.015f, 0.0f);
                                    GL11.glRotatef(runConst * -0.8f, 0, 0, 1);

                                    GL11.glRotatef(reloadConst, 0, 1, 0);
                                    GL11.glRotatef(-reloadConst * 1.5f, 0, 1, 1);
                                    GL11.glRotatef(reloadConst * 1.8f, 1, 1, 0);

                                    GL11.glPushMatrix();
                                    GL11.glScalef(1.5f, 1.5f, 1.5f);
                                    GL11.glTranslatef(0.1375f, -0.1225f, 0.00975f);
                                    GL11.glRotatef(12, 0, 1, 0);
                                    GL11.glRotatef(-6, 1, 1, 0);
                                    GL11.glRotatef(-2, 1, 0, 1);
                                    this.renderWeapon(entity, tessellator, itemRendering, icon);
                                    GL11.glPopMatrix();

                                    GL11.glPushMatrix();
                                    GL11.glTranslatef(0.45f, -0.4f, -0.46f);
                                    GL11.glRotatef(90, 1, 1, 0);
                                    GL11.glRotatef(-6, 1, 0, 0);
                                    GL11.glRotatef(305, 0, 0, 1);
                                    if (!this.isWatchingGun) {
                                        RenderUtils.renderArm(true);
                                    }
                                    GL11.glPopMatrix();

                                    GL11.glPushMatrix();
                                    GL11.glScalef(1.3f, 1.3f, 1.3f);
                                    GL11.glRotatef(-reloadConst * 2, 1, 0, 1);
                                    GL11.glTranslatef(0.475f, -0.12f, -0.4f);
                                    GL11.glRotatef(55, 1, 1, 0);
                                    GL11.glRotatef(-8, 0, 0, 1);
                                    if (!this.isWatchingGun) {
                                        RenderUtils.renderArm(false);
                                    }
                                    GL11.glPopMatrix();

                                    if (this.canRenderMuzzleFlash1st(player)) {
                                        GL11.glPushMatrix();
                                        if (stack.addTranslateMuzzle1st(player, itemRendering)) {
                                            GL11.glScalef(1.25f, 1.25f, 1.25f);
                                            GL11.glRotatef(45, 0, 0, 1);
                                            GL11.glRotatef(15, 1, 0, 0);
                                            GL11.glRotatef(-25, 0, 1, 0);
                                            GL11.glTranslatef(0.5f, -1.58f, -0.525f);
                                            GL11.glPushMatrix();
                                            GL11.glTranslatef(0, 0, -0.12f);
                                            OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, 1, 0, 0);
                                            this.drawMuzzleFlashFirstPerson(GunItemRender.flash);
                                            GL11.glPopMatrix();
                                            OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, 1, 0, 0);
                                            this.drawMuzzleFlashFirstPerson(GunItemRender.muzzleFlash[this.muzzleFlashId]);
                                        }
                                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                                        GL11.glPopMatrix();
                                    }
                                } else if (renderType == AGunBase.GunType.RIFLE2) {
                                    GL11.glRotatef(-scopeConst / 5, 0, 1, 0);
                                    GL11.glRotatef(scopeConst / 5, 1, 1, 0);
                                    GL11.glTranslatef(-scopeConst / 65, -scopeConst / 200, -scopeConst / 82);

                                    GL11.glRotatef(recoilConst * 5.0f, 0, 0, 1);
                                    GL11.glTranslatef(-recoilConst * 0.05f, -recoilConst * 0.125f, 0);

                                    GL11.glTranslatef(shutterConst * -0.007f, shutterConst * -0.014f, 0);
                                    GL11.glRotatef(shutterConst * 0.5f, 0, 0, 1);

                                    GL11.glRotatef(runConst * -0.4f, 1, 0, 0);
                                    GL11.glTranslatef(0.0f, runConst * 0.015f, 0.0f);
                                    GL11.glRotatef(runConst * -0.8f, 0, 0, 1);

                                    GL11.glRotatef(reloadConst, 0, 1, 0);
                                    GL11.glRotatef(-reloadConst * 1.5f, 0, 1, 1);
                                    GL11.glRotatef(reloadConst * 1.8f, 1, 1, 0);

                                    GL11.glPushMatrix();
                                    GL11.glScalef(1.5f, 1.5f, 1.5f);
                                    GL11.glTranslatef(0.1375f, -0.1225f, 0.00975f);
                                    GL11.glRotatef(12, 0, 1, 0);
                                    GL11.glRotatef(-6, 1, 1, 0);
                                    GL11.glRotatef(-2, 1, 0, 1);
                                    this.renderWeapon(entity, tessellator, itemRendering, icon);
                                    GL11.glPopMatrix();

                                    GL11.glPushMatrix();
                                    GL11.glTranslatef(0.675f, -0.315f, -0.5f);
                                    GL11.glRotatef(95, 1, 1, 0);
                                    GL11.glRotatef(-12, 1, 0, 0);
                                    GL11.glRotatef(305, 0, 0, 1);
                                    if (!this.isWatchingGun) {
                                        RenderUtils.renderArm(true);
                                    }
                                    GL11.glPopMatrix();

                                    GL11.glPushMatrix();
                                    GL11.glScalef(1.3f, 1.3f, 1.3f);
                                    GL11.glRotatef(-reloadConst * 2, 1, 0, 1);
                                    GL11.glTranslatef(0.475f, -0.12f, -0.4f);
                                    GL11.glRotatef(55, 1, 1, 0);
                                    GL11.glRotatef(-8, 0, 0, 1);
                                    if (!this.isWatchingGun) {
                                        RenderUtils.renderArm(false);
                                    }
                                    GL11.glPopMatrix();

                                    if (this.canRenderMuzzleFlash1st(player)) {
                                        GL11.glPushMatrix();
                                        if (stack.addTranslateMuzzle1st(player, itemRendering)) {
                                            GL11.glScalef(1.25f, 1.25f, 1.25f);
                                            GL11.glRotatef(45, 0, 0, 1);
                                            GL11.glRotatef(15, 1, 0, 0);
                                            GL11.glRotatef(-25, 0, 1, 0);
                                            GL11.glTranslatef(0.5f, -1.58f, -0.525f);
                                            GL11.glPushMatrix();
                                            GL11.glTranslatef(0, 0, -0.12f);
                                            OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, 1, 0, 0);
                                            this.drawMuzzleFlashFirstPerson(GunItemRender.flash);
                                            GL11.glPopMatrix();
                                            OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, 1, 0, 0);
                                            this.drawMuzzleFlashFirstPerson(GunItemRender.muzzleFlash[this.muzzleFlashId]);
                                        }
                                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                                        GL11.glPopMatrix();
                                    }
                                } else if (renderType == AGunBase.GunType.PISTOL2) {
                                    GL11.glRotatef(-scopeConst / 5, 0, 1, 0);
                                    GL11.glRotatef(scopeConst / 5, 1, 1, 0);
                                    GL11.glTranslatef(-scopeConst / 65, -scopeConst / 200, -scopeConst / 82);

                                    GL11.glRotatef(recoilConst * 5.0f, 0, 0, 1);
                                    GL11.glTranslatef(-recoilConst * 0.05f, -recoilConst * 0.03f, 0);

                                    GL11.glRotatef(runConst, 0, 0, 1);
                                    GL11.glTranslatef(0, -runConst * 0.01f, 0);

                                    GL11.glRotatef(reloadConst, 0, 1, 0);
                                    GL11.glRotatef(-reloadConst * 1.8f, 0, 1, 1);
                                    GL11.glRotatef(reloadConst * 1.5f, 1, 1, 0);

                                    GL11.glPushMatrix();
                                    GL11.glScalef(1.2f, 1.2f, 1.2f);
                                    GL11.glTranslatef(0.3f, -0.05f, -0.15f);
                                    GL11.glRotatef(12, 0, 1, 0);
                                    GL11.glRotatef(-6, 1, 1, 0);
                                    GL11.glRotatef(-2, 1, 0, 1);
                                    if (this.isWatchingGun) {
                                        GL11.glTranslatef(0.05f, 0, 0.1f);
                                    }
                                    this.renderWeapon(entity, tessellator, itemRendering, icon);
                                    GL11.glPopMatrix();

                                    GL11.glPushMatrix();
                                    GL11.glTranslatef(0.4f, -0.2f, -0.7f);
                                    GL11.glRotatef(90, 1, 1, 0);
                                    GL11.glRotatef(-10, 1, 0, 0);
                                    GL11.glRotatef(310, 0, 0, 1);
                                    if (!this.isWatchingGun) {
                                        RenderUtils.renderArm(true);
                                    }
                                    GL11.glPopMatrix();
                                    if (this.canRenderMuzzleFlash1st(player)) {
                                        GL11.glPushMatrix();
                                        if (stack.addTranslateMuzzle1st(player, itemRendering)) {
                                            GL11.glScalef(0.85f, 0.85f, 0.85f);
                                            GL11.glRotatef(45, 0, 0, 1);
                                            GL11.glRotatef(15, 1, 0, 0);
                                            GL11.glRotatef(-25, 0, 1, 0);
                                            GL11.glTranslatef(0.62f, -1.5f, -0.92f);
                                            GL11.glPushMatrix();
                                            GL11.glTranslatef(0, 0, -0.12f);
                                            OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, 1, 0, 0);
                                            this.drawMuzzleFlashFirstPerson(GunItemRender.flash);
                                            GL11.glPopMatrix();
                                            OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, 1, 0, 0);
                                            this.drawMuzzleFlashFirstPerson(GunItemRender.muzzleFlash[this.muzzleFlashId]);
                                        }
                                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                                        GL11.glPopMatrix();
                                    }
                                } else if (renderType == AGunBase.GunType.PISTOL) {
                                    GL11.glRotatef(-scopeConst / 5, 0, 1, 0);
                                    GL11.glRotatef(scopeConst / 5, 1, 1, 0);
                                    GL11.glTranslatef(-scopeConst / 65, -scopeConst / 200, -scopeConst / 82);

                                    GL11.glRotatef(recoilConst * 5.0f, 0, 0, 1);
                                    GL11.glTranslatef(-recoilConst * 0.05f, -recoilConst * 0.03f, 0);

                                    GL11.glRotatef(runConst, 0, 0, 1);
                                    GL11.glTranslatef(0, -runConst * 0.01f, 0);

                                    GL11.glRotatef(reloadConst, 0, 1, 0);
                                    GL11.glRotatef(-reloadConst * 1.8f, 0, 1, 1);
                                    GL11.glRotatef(reloadConst * 1.5f, 1, 1, 0);

                                    GL11.glPushMatrix();
                                    GL11.glTranslatef(0.4f, 0.1f, -0.2f);
                                    GL11.glRotatef(12, 0, 1, 0);
                                    GL11.glRotatef(-6, 1, 1, 0);
                                    GL11.glRotatef(-2, 1, 0, 1);
                                    this.renderWeapon(entity, tessellator, itemRendering, icon);
                                    GL11.glPopMatrix();

                                    GL11.glPushMatrix();
                                    GL11.glTranslatef(0.4f, -0.2f, -0.7f);
                                    GL11.glRotatef(90, 1, 1, 0);
                                    GL11.glRotatef(-10, 1, 0, 0);
                                    GL11.glRotatef(310, 0, 0, 1);
                                    if (!this.isWatchingGun) {
                                        RenderUtils.renderArm(true);
                                    }
                                    GL11.glPopMatrix();
                                    if (this.canRenderMuzzleFlash1st(player)) {
                                        GL11.glPushMatrix();
                                        if (stack.addTranslateMuzzle1st(player, itemRendering)) {
                                            GL11.glScalef(0.85f, 0.85f, 0.85f);
                                            GL11.glRotatef(45, 0, 0, 1);
                                            GL11.glRotatef(15, 1, 0, 0);
                                            GL11.glRotatef(-25, 0, 1, 0);
                                            GL11.glTranslatef(0.565f, -1.45f, -0.8f);
                                            GL11.glPushMatrix();
                                            GL11.glTranslatef(0, 0, -0.12f);
                                            OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, 1, 0, 0);
                                            this.drawMuzzleFlashFirstPerson(GunItemRender.flash);
                                            GL11.glPopMatrix();
                                            OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, 1, 0, 0);
                                            this.drawMuzzleFlashFirstPerson(GunItemRender.muzzleFlash[this.muzzleFlashId]);
                                        }
                                        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                                        GL11.glPopMatrix();
                                    }
                                }
                            }
                        }
                    } else {
                        if (icon != null) {
                            if (renderType == AGunBase.GunType.RIFLE2 || renderType == AGunBase.GunType.RIFLE || renderType == AGunBase.GunType.RPG) {
                                GL11.glPushMatrix();
                                GL11.glScalef(1.65f, 1.65f, 1.65f);
                                GL11.glTranslatef(0.15f, 0.25f, -0.425f);
                                GL11.glRotatef(320, 0, 1, 1);
                                GL11.glRotatef(90, 1, 0, 0);
                                GL11.glRotatef(300, 1, 1, 0);
                                this.renderWeapon(entity, tessellator, itemRendering, icon);
                                GL11.glPopMatrix();
                                if (this.canRenderMuzzleFlash3d(player)) {
                                    GL11.glPushMatrix();
                                    GL11.glScalef(0.75f, 0.75f, 0.75f);
                                    GL11.glTranslatef(1.13f, -0.16f, 0.35f);
                                    GL11.glRotatef(-45.0f, 0, 1, 0);
                                    GL11.glRotatef(-13.5f, 1, 0, 0);
                                    if (stack.addTranslateMuzzle3d(player, itemRendering)) {
                                        GL11.glPushMatrix();
                                        GL11.glAlphaFunc(GL11.GL_GREATER, 0.99f);
                                        this.drawMuzzleFlashThirdPerson();
                                        GL11.glDisable(GL11.GL_ALPHA_TEST);
                                        GL11.glDepthMask(false);
                                        this.drawMuzzleFlashThirdPerson();
                                        GL11.glDepthMask(true);
                                        GL11.glEnable(GL11.GL_ALPHA_TEST);
                                        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1f);
                                        GL11.glPopMatrix();
                                    }
                                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                                    GL11.glPopMatrix();
                                }
                            } else if (renderType == AGunBase.GunType.CROSSBOW) {
                                GL11.glPushMatrix();
                                GL11.glScalef(1.65f, 1.65f, 1.65f);
                                GL11.glTranslatef(0.15f, 0.25f, -0.425f);
                                GL11.glRotatef(320, 0, 1, 1);
                                GL11.glRotatef(90, 1, 0, 0);
                                GL11.glRotatef(210, 1, 1, 0);
                                this.renderWeapon(entity, tessellator, itemRendering, icon);
                                GL11.glPopMatrix();
                            } else if (renderType == AGunBase.GunType.PISTOL2) {
                                GL11.glPushMatrix();
                                GL11.glScalef(1.35f, 1.35f, 1.35f);
                                GL11.glTranslatef(0.225f, 0.15f, -0.5f);
                                GL11.glRotatef(320, 0, 1, 1);
                                GL11.glRotatef(90, 1, 0, 0);
                                GL11.glRotatef(300, 1, 1, 0);
                                this.renderWeapon(entity, tessellator, itemRendering, icon);
                                GL11.glPopMatrix();
                                if (this.canRenderMuzzleFlash3d(player)) {
                                    GL11.glPushMatrix();
                                    GL11.glScalef(0.65f, 0.65f, 0.65f);
                                    GL11.glTranslatef(1.125f, 0.1f, -0.168f);
                                    GL11.glRotatef(-45.0f, 0, 1, 0);
                                    GL11.glRotatef(-13.5f, 1, 0, 0);
                                    if (stack.addTranslateMuzzle3d(player, itemRendering)) {
                                        GL11.glPushMatrix();
                                        GL11.glAlphaFunc(GL11.GL_GREATER, 0.99f);
                                        this.drawMuzzleFlashThirdPerson();
                                        GL11.glDisable(GL11.GL_ALPHA_TEST);
                                        GL11.glDepthMask(false);
                                        this.drawMuzzleFlashThirdPerson();
                                        GL11.glDepthMask(true);
                                        GL11.glEnable(GL11.GL_ALPHA_TEST);
                                        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1f);
                                        GL11.glPopMatrix();
                                    }
                                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                                    GL11.glPopMatrix();
                                }
                            } else if (renderType == AGunBase.GunType.PISTOL) {
                                GL11.glPushMatrix();
                                GL11.glScalef(1.2f, 1.2f, 1.2f);
                                GL11.glTranslatef(0.3f, 0.2f, -0.5f);
                                GL11.glRotatef(320, 0, 1, 1);
                                GL11.glRotatef(90, 1, 0, 0);
                                GL11.glRotatef(300, 1, 1, 0);
                                this.renderWeapon(entity, tessellator, itemRendering, icon);
                                GL11.glPopMatrix();
                                if (this.canRenderMuzzleFlash3d(player)) {
                                    GL11.glPushMatrix();
                                    GL11.glScalef(0.65f, 0.65f, 0.65f);
                                    GL11.glTranslatef(1.195f, 0.175f, -0.35f);
                                    GL11.glRotatef(-45.0f, 0, 1, 0);
                                    GL11.glRotatef(-13.5f, 1, 0, 0);
                                    if (stack.addTranslateMuzzle3d(player, itemRendering)) {
                                        GL11.glPushMatrix();
                                        GL11.glAlphaFunc(GL11.GL_GREATER, 0.99f);
                                        this.drawMuzzleFlashThirdPerson();
                                        GL11.glDisable(GL11.GL_ALPHA_TEST);
                                        GL11.glDepthMask(false);
                                        this.drawMuzzleFlashThirdPerson();
                                        GL11.glDepthMask(true);
                                        GL11.glEnable(GL11.GL_ALPHA_TEST);
                                        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1f);
                                        GL11.glPopMatrix();
                                    }
                                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                                    GL11.glPopMatrix();
                                }
                            }
                        }
                    }
                } else {
                    if (icon != null) {
                        if (renderType == AGunBase.GunType.RIFLE2 || renderType == AGunBase.GunType.RIFLE || renderType == AGunBase.GunType.RPG) {
                            GL11.glPushMatrix();
                            GL11.glScalef(1.65f, 1.65f, 1.65f);
                            GL11.glTranslatef(0.15f, 0.25f, -0.425f);
                            GL11.glRotatef(320, 0, 1, 1);
                            GL11.glRotatef(90, 1, 0, 0);
                            GL11.glRotatef(300, 1, 1, 0);
                            this.renderWeapon(entity, tessellator, itemRendering, icon);
                            GL11.glPopMatrix();
                        } else if (renderType == AGunBase.GunType.CROSSBOW) {
                            GL11.glPushMatrix();
                            GL11.glScalef(1.65f, 1.65f, 1.65f);
                            GL11.glTranslatef(0.15f, 0.25f, -0.425f);
                            GL11.glRotatef(320, 0, 1, 1);
                            GL11.glRotatef(90, 1, 0, 0);
                            GL11.glRotatef(210, 1, 1, 0);
                            this.renderWeapon(entity, tessellator, itemRendering, icon);
                            GL11.glPopMatrix();
                        } else {
                            GL11.glPushMatrix();
                            GL11.glScalef(1.2f, 1.2f, 1.2f);
                            GL11.glTranslatef(0.3f, 0.2f, -0.5f);
                            GL11.glRotatef(320, 0, 1, 1);
                            GL11.glRotatef(90, 1, 0, 0);
                            GL11.glRotatef(300, 1, 1, 0);
                            this.renderWeapon(entity, tessellator, itemRendering, icon);
                            GL11.glPopMatrix();
                        }
                    }
                }
            }
        }
    }

    public float getScreenShake() {
        return this.screenShake;
    }

    @SuppressWarnings("unchecked")
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onOverlayRender(RenderGameOverlayEvent.Pre ev) {
        int scaledWidth = ev.resolution.getScaledWidth();
        int scaledHeight = ev.resolution.getScaledHeight();
        Minecraft mc = Minecraft.getMinecraft();
        RenderItem render = RenderItem.getInstance();
        EntityPlayerSP player = mc.thePlayer;
        ItemStack ep = player.getHeldItem();
        if (this.isGun(ep)) {
            if (!RenderManager.hideHud) {
                AGunBase stack = ((AGunBase) ep.getItem());
                Minecraft.getMinecraft().rightClickDelayTimer = 5;
                String textAmmo = ep.getTagCompound().getCompoundTag(Main.MODID).getInteger("ammo") + " / " + this.reserveAmmo(ep, player, stack.getAmmo());
                String textFireMode = "[" + Keyboard.getKeyName(ClientProxy.keySwitch.getKeyCode()) + "] " + I18n.format(stack.getCurrentFireMode(ep).getTranslation());
                if (ev.type == RenderGameOverlayEvent.ElementType.HOTBAR) {
                    if (stack.getCurrentModule(ep, EnumModule.UNDERBARREL) != null) {
                        ItemModule modifier = stack.getCurrentModule(ep, EnumModule.UNDERBARREL).getMod();
                        if (modifier instanceof ItemLaser) {
                            float f = 1.0F;
                            float f1 = player.prevRotationPitch + (player.rotationPitch - player.prevRotationPitch) * f;
                            float f2 = player.prevRotationYaw + (player.rotationYaw - player.prevRotationYaw) * f;
                            double d0 = player.prevPosX + (player.posX - player.prevPosX) * (double) f;
                            double d1 = player.prevPosY + (player.posY - player.prevPosY) * (double) f + (double) player.getEyeHeight() - player.getDefaultEyeHeight();
                            double d2 = player.prevPosZ + (player.posZ - player.prevPosZ) * (double) f;
                            Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
                            float f3 = MathHelper.cos((float) (-f2 * (Math.PI / 180.0f) - (float) Math.PI));
                            float f4 = MathHelper.sin((float) (-f2 * (Math.PI / 180.0f) - (float) Math.PI));
                            float f5 = -MathHelper.cos((float) (-f1 * (Math.PI / 180.0f)));
                            float f6 = MathHelper.sin((float) (-f1 * (Math.PI / 180.0f)));
                            float f7 = f4 * f5;
                            float f8 = f3 * f5;
                            double d3 = 512.0D;
                            Vec3 vec31 = vec3.addVector((double) f7 * d3, (double) f6 * d3, (double) f8 * d3);
                            MovingObjectPosition movingObjectPosition = Tracer.trace(player.worldObj, vec3, vec31, player, -1);
                            String text = "...";
                            if (!this.isWatchingGun && !this.isRunning && !this.isReloading && movingObjectPosition != null) {
                                int distance = (int) player.getDistance(movingObjectPosition.hitVec.xCoord, movingObjectPosition.hitVec.yCoord, movingObjectPosition.hitVec.zCoord);
                                if (distance <= 256) {
                                    text = String.valueOf((int) player.getDistance(movingObjectPosition.hitVec.xCoord, movingObjectPosition.hitVec.yCoord, movingObjectPosition.hitVec.zCoord));
                                } else {
                                    text = "256+";
                                }
                            }
                            RenderUtils.drawStringInSquare(text, scaledWidth / 2 + 84, scaledHeight - 16, 0x94d034);
                        }
                    }
                    if (GunItemRender.instance.isWatchingGun && mc.gameSettings.thirdPersonView == 0) {
                        mc.gameSettings.showDebugInfo = false;
                        this.selection.setSelector(!Main.settingsZp.selector.isFlag());
                        this.selection.updateSelecting(ep);
                        GL11.glPushMatrix();
                        GL11.glScalef(1.5f, 1.5f, 1.5f);
                        String s = ep.getDisplayName();
                        if (ep.hasDisplayName()) {
                            s = EnumChatFormatting.ITALIC + s + EnumChatFormatting.RESET;
                        }
                        mc.fontRendererObj.drawStringWithShadow(s, 2, 2, ep.hasDisplayName() ? 0xD8BFD8 : 0xffffff);
                        GL11.glPopMatrix();

                        int i1 = 22;
                        for (String description : stack.weaponDescriptionText(ep)) {
                            if (!description.isEmpty()) {
                                RenderUtils.drawStringInSquare(description, 6, i1, 0xffffff);
                            }
                            i1 += 10;
                        }

                        GL11.glPushMatrix();
                        GL11.glEnable(GL11.GL_BLEND);

                        this.selection.setRelativePosition(6, i1 + 6);

                        for (ModuleSection moduleSection : this.selection.moduleSectionList) {
                            ItemModule modifier = moduleSection.getMainSlot().getItemModule();
                            if (modifier != null) {
                                mc.fontRendererObj.drawStringWithShadow(modifier.getItemStackDisplayName(new ItemStack(modifier)), moduleSection.getMainSlot().getPosX(), moduleSection.getMainSlot().getPosY(), 0x00ff00);
                                GL11.glPushMatrix();
                                GL11.glTranslatef(-10, moduleSection.getMainSlot().getPosY(), 0);
                                render.renderItemIntoGUI(mc.fontRendererObj, mc.getTextureManager(), new ItemStack(modifier), 16, 16);
                                GL11.glDisable(GL11.GL_LIGHTING);
                                GL11.glPopMatrix();
                            } else {
                                mc.fontRendererObj.drawStringWithShadow(I18n.format(moduleSection.getEnumModifier().getTranslation()), moduleSection.getMainSlot().getPosX(), moduleSection.getMainSlot().getPosY(), moduleSection.isAlwaysEmpty() ? 0xff0000 : 0xffffff);
                                GL11.glColor3f(1.0f, 1.0f, 1.0f);
                                List<SlotInfo> listToRender = moduleSection.getListToRender();
                                if (!listToRender.isEmpty()) {
                                    for (SlotInfo slotInfo : listToRender) {
                                        GL11.glEnable(GL11.GL_BLEND);
                                        mc.getTextureManager().bindTexture(GameHud.components);
                                        mc.ingameGUI.drawTexturedModalRect(slotInfo.getPosX(), moduleSection.getMainSlot().getPosY() + 16, 0, 0, 16, 16);
                                        GL11.glPushMatrix();
                                        GL11.glTranslatef(slotInfo.getPosX() - 16, moduleSection.getMainSlot().getPosY(), 0);
                                        render.renderItemIntoGUI(mc.fontRendererObj, mc.getTextureManager(), new ItemStack(slotInfo.getItemModule()), 16, 16);
                                        GL11.glDisable(GL11.GL_BLEND);
                                        GL11.glDisable(GL11.GL_LIGHTING);
                                        GL11.glPopMatrix();
                                    }
                                }
                            }
                            GL11.glPushMatrix();
                            GL11.glEnable(GL11.GL_BLEND);
                            mc.getTextureManager().bindTexture(GameHud.components);
                            mc.ingameGUI.drawTexturedModalRect(6, moduleSection.getMainSlot().getPosY() + 16, moduleSection.isAlwaysEmpty() ? 0 : 32, moduleSection.isAlwaysEmpty() ? 16 : 0, 16, 16);
                            GL11.glDisable(GL11.GL_BLEND);
                            GL11.glPopMatrix();
                        }

                        GL11.glPopMatrix();
                        if (this.selection.currentSlotSelected != null) {
                            GL11.glPushMatrix();
                            GL11.glEnable(GL11.GL_BLEND);
                            mc.ingameGUI.drawTexturedModalRect(this.selection.currentSlotSelected.getPosX(), this.selection.currentSlotSelected.getPosY() + 16, 16, selection.isOnEmptySlot() ? 16 : 0, 16, 16);
                            GL11.glDisable(GL11.GL_BLEND);
                            GL11.glPopMatrix();
                            if (this.selection.getSelectedModule() != null) {
                                ItemStack stack1 = new ItemStack(this.selection.getSelectedModule());
                                List<String> text = stack1.getTooltip(player, false);
                                for (int i = 0; i < text.size(); i++) {
                                    String descr = text.get(i);
                                    RenderUtils.drawStringInSquare(descr, scaledWidth - mc.fontRendererObj.getStringWidth(descr) - 6, 2 + i * 10, 0xFFFFFF);
                                }
                                if (this.selection.isKeyboard()) {
                                    RenderUtils.drawStringInSquare("[" + Keyboard.getKeyName(ClientProxy.keyConfirm.getKeyCode()) + "] - " + ClientProxy.keyConfirm.getKeyDescription(), this.selection.getCurrentSelector().getCurrentModuleSection().getMaxX() + 18, this.selection.currentSlotSelected.getPosY() + 20, 0x87b1f0);
                                }
                            } else {
                                String text12 = I18n.format("weapon.gui.desc.supportableMods");
                                String text13 = I18n.format("weapon.gui.desc.nullListMods");
                                RenderUtils.drawStringInSquare(text12, scaledWidth - mc.fontRendererObj.getStringWidth(text12) - 6, 2, 0xffffff);
                                EnumModule selectedSlotType = this.selection.getCurrentSlotType();

                                int i2 = 12;
                                if (stack.getSupportedModsList(selectedSlotType).isEmpty()) {
                                    RenderUtils.drawStringInSquare(text13, scaledWidth - mc.fontRendererObj.getStringWidth(text13) - 6, i2, 0xFF6347);
                                } else {
                                    for (ModuleInfo modifier : stack.getSupportedModsList(selectedSlotType)) {
                                        String info = modifier.getMod().getItemStackDisplayName(new ItemStack(modifier.getMod()));
                                        RenderUtils.drawStringInSquare(info, scaledWidth - mc.fontRendererObj.getStringWidth(info) - 6, i2, 0xAFEEEE);
                                        i2 += 10;
                                    }
                                }
                            }
                        }
                        int i0 = scaledHeight - 32;
                        RenderUtils.drawStringInSquare(textAmmo, scaledWidth - mc.fontRendererObj.getStringWidth(textAmmo) - 6, i0, 0xAFEEEE);
                        if (stack.getCurrentModule(ep, EnumModule.UNDERBARREL) != null && stack.getCurrentModule(ep, EnumModule.UNDERBARREL).getMod() instanceof ALauncherModuleBase) {
                            ALauncherModuleBase aLauncherModuleBase = (ALauncherModuleBase) stack.getCurrentModule(ep, EnumModule.UNDERBARREL).getMod();
                            String textMetaAmmo = ep.getTagCompound().getCompoundTag(Main.MODID).getInteger("metaAmmo") + " / " + this.reserveAmmo(ep, player, aLauncherModuleBase.getAmmo());
                            RenderUtils.drawStringInSquare(textMetaAmmo, scaledWidth - mc.fontRendererObj.getStringWidth(textMetaAmmo) - 6, i0 + 10, 0xAFEEEE);
                            i0 += 10;
                        }
                        RenderUtils.drawStringInSquare(textFireMode, scaledWidth - mc.fontRendererObj.getStringWidth(textFireMode) - 6, i0 + 10, 0xAFEEEE);
                    } else {
                        this.selection.currentSlotSelected = null;
                        String text = "[" + Keyboard.getKeyName(ClientProxy.keySeeGun.getKeyCode()) + "] " + ClientProxy.keySeeGun.getKeyDescription();

                        int color = (stack.getCurrentFireMode(ep) != EnumFireModes.SAFE && player.getEntityData().getInteger("cdShoot") == 0) ? 0xFFFFFF : 0xC0C0C0;
                        if (ep.getTagCompound().getCompoundTag(Main.MODID).getBoolean("isJammed")) {
                            color = 0xFF0000;
                            textAmmo = I18n.format("weapon.jam") + " / " + this.reserveAmmo(ep, player, stack.getAmmo());
                        }
                        if (stack.isInReloadingAnim(player)) {
                            color = stack.isInUnReloading(player) ? 0xFF7F50 : 0x00FF00;
                        }

                        int i = 16;
                        RenderUtils.drawStringInSquare(textAmmo, scaledWidth - mc.fontRendererObj.getStringWidth(textAmmo) - 25, i, color);
                        if (stack.getCurrentModule(ep, EnumModule.UNDERBARREL) != null && stack.getCurrentModule(ep, EnumModule.UNDERBARREL).getMod() instanceof ALauncherModuleBase) {
                            i += 10;
                            ALauncherModuleBase aLauncherModuleBase = (ALauncherModuleBase) stack.getCurrentModule(ep, EnumModule.UNDERBARREL).getMod();
                            String textMetaAmmo = ep.getTagCompound().getCompoundTag(Main.MODID).getInteger("metaAmmo") + " / " + this.reserveAmmo(ep, player, aLauncherModuleBase.getAmmo());
                            RenderUtils.drawStringInSquare(textMetaAmmo, scaledWidth - mc.fontRendererObj.getStringWidth(textMetaAmmo) - 25, i, color);
                        }
                        RenderUtils.drawStringInSquare(textFireMode, scaledWidth - mc.fontRendererObj.getStringWidth(textFireMode) - 25, i + 10, color);
                        RenderUtils.drawStringInSquare(text, scaledWidth - mc.fontRendererObj.getStringWidth(text) - 25, i + 20, color);
                    }
                    GL11.glDisable(GL11.GL_LIGHTING);
                }
            }
        } else {
            this.stopWatchAnimation();
        }
    }

    private int reserveAmmo(ItemStack stack, EntityPlayer pl, Item item) {
        int i = 0;
        if (pl.inventory.inventoryChanged) {
            for (ItemStack stacks : pl.inventory.mainInventory) {
                if (stacks != null && stack.getItem() instanceof AGunBase && stacks.getItem() == item) {
                    i += stacks.stackSize;
                }
            }
        }
        return i;
    }

    private boolean isGun(ItemStack stack) {
        return stack != null && stack.hasTagCompound() && stack.getItem() instanceof AGunBase;
    }

    public int getTicksAfterScoping() {
        return this.ticksAfterScoping;
    }

    public boolean isWatchingGun() {
        return this.isWatchingGun;
    }

    public float scopingProgress() {
        return this.scope;
    }

    public void startReloadAnimation() {
        this.isReloading = true;
    }

    public void stopReloadAnimation() {
        this.isReloading = false;
    }

    public void startWatchAnimation() {
        this.isWatchingGun = true;
    }

    public void stopWatchAnimation() {
        this.selection.stopSelecting(null);
        this.isWatchingGun = false;
    }

    public void startRunAnimation() {
        this.isRunning = true;
    }

    public void stopRunAnimation() {
        this.isRunning = false;
    }

    public void startShutterAnimation() {
        this.isPlayingShutterAnim = true;
    }

    public void stopShutterAnimation() {
        this.isPlayingShutterAnim = false;
    }

    public void startScopeAnimation() {
        this.isScoping = true;
    }

    public void stopScopeAnimation() {
        this.isScoping = false;
    }

    public boolean isInScope() {
        return this.scope >= 25;
    }

    public void addShutterAnimation() {
        this.startShutterAnimation();
    }

    public void addRecoilAnimation(float strength) {
        this.shootTimer = 40;
        this.recoil = strength;
    }

    private boolean canStartScoping(EntityPlayer player) {
        return Minecraft.getMinecraft().entityRenderer.itemRenderer.equippedProgress == 1.0f && !player.isSprinting() && this.reloadPrev == 0 && this.reload == 0 && !this.isWatchingGun && Minecraft.getMinecraft().currentScreen == null && Minecraft.getMinecraft().gameSettings.thirdPersonView == 0;
    }

    public void addShake(float strength) {
        strength *= this.shakeMultiplier;
        this.shakeMultiplier = Math.max(this.shakeMultiplier * 0.8f, 1.0f);
        this.screenShake = this.shakeStage * (Math.min(strength, 8.0f) + 0.5f) * 1.5f + (Main.rand.nextFloat() - Main.rand.nextFloat()) * 0.2f;
        this.shakeStage *= -1f;
    }

    public void trySwitchFireMode() {
        ItemStack stack = Minecraft.getMinecraft().thePlayer.getHeldItem();
        if (this.shootTimer == 0 && !this.isReloading && this.isGun(stack)) {
            SoundUtils.playMonoSound(Main.MODID + ":select");
            this.switchFireMode(stack);
        }
    }

    public void switchFireMode(ItemStack stack) {
        NetworkHandler.NETWORK.sendToServer(new PacketSwitchFireMode());
        ((AGunBase) stack.getItem()).switchFireMode(stack);
    }

    private void renderWeaponInGui(ItemStack itemRendering, IIcon gunIcon) {
        RenderItem render = RenderItem.getInstance();
        AGunBase rStack = ((AGunBase) itemRendering.getItem());
        ModuleInfo modScope = null;
        ModuleInfo modBarrel = null;
        ModuleInfo modUnderBarrel = null;
        if (itemRendering.hasTagCompound()) {
            modScope = rStack.getCurrentModule(itemRendering, EnumModule.SCOPE);
            modBarrel = rStack.getCurrentModule(itemRendering, EnumModule.BARREL);
            modUnderBarrel = rStack.getCurrentModule(itemRendering, EnumModule.UNDERBARREL);
        }
        render.renderIcon(0, 0, gunIcon, 16, 16);
        if (modScope != null && modScope.isShouldBeRendered()) {
            IIcon iconScope = modScope.getMod().getIconToRender();
            if (iconScope != null) {
                int xOffset = modScope.getXOffset();
                int yOffset = modScope.getYOffset();
                render.renderIcon(-xOffset, -yOffset, iconScope, 16, 16);
            }
        }
        if (modBarrel != null && modBarrel.isShouldBeRendered()) {
            IIcon iconSilencer = modBarrel.getMod().getIconToRender();
            if (iconSilencer != null) {
                int xOffset = modBarrel.getXOffset();
                int yOffset = modBarrel.getYOffset();
                render.renderIcon(-xOffset, -yOffset, iconSilencer, 16, 16);
            }
        }
        if (modUnderBarrel != null && modUnderBarrel.isShouldBeRendered()) {
            IIcon iconUnderBarrel = modUnderBarrel.getMod().getIconToRender();
            if (iconUnderBarrel != null) {
                int xOffset = modUnderBarrel.getXOffset();
                int yOffset = modUnderBarrel.getYOffset();
                render.renderIcon(-xOffset, -yOffset, iconUnderBarrel, 16, 16);
            }
        }
    }

    private void renderWeapon(EntityLivingBase entityLivingBase, Tessellator tessellator, ItemStack itemRendering, IIcon gunIcon) {
        this.renderWeaponIcons(entityLivingBase, tessellator, itemRendering, gunIcon);
        if (itemRendering.hasEffect(0)) {
            GL11.glDepthFunc(GL11.GL_EQUAL);
            GL11.glDisable(GL11.GL_LIGHTING);
            Minecraft.getMinecraft().getTextureManager().bindTexture(ItemRenderer.RES_ITEM_GLINT);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(768, 1, 1, 0);
            float f7 = 1.0F;
            GL11.glColor4f(0.5F * f7, 0.25F * f7, 0.8F * f7, 1.0F);
            GL11.glMatrixMode(GL11.GL_TEXTURE);
            GL11.glPushMatrix();
            float f8 = 5.0F;
            GL11.glScalef(f8, f8, f8);
            float f9 = (Minecraft.getSystemTime() % 18000.0F) / 18000.0F;
            GL11.glTranslatef(f9, 0.0F, 0.0F);
            GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
            this.renderWeaponIcons(entityLivingBase, tessellator, itemRendering, gunIcon);
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            GL11.glScalef(f8, f8, f8);
            f9 = (Minecraft.getSystemTime() % 16000.0F) / 16000.0F;
            GL11.glTranslatef(-f9, 0.0F, 0.0F);
            GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
            this.renderWeaponIcons(entityLivingBase, tessellator, itemRendering, gunIcon);
            GL11.glPopMatrix();
            GL11.glMatrixMode(GL11.GL_MODELVIEW);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glEnable(GL11.GL_LIGHTING);
            GL11.glDepthFunc(GL11.GL_LEQUAL);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    private void renderWeaponIcons(EntityLivingBase entityLivingBase, Tessellator tessellator, ItemStack itemRendering, IIcon gunIcon) {
        AGunBase rStack = ((AGunBase) itemRendering.getItem());
        ModuleInfo modScope = null;
        ModuleInfo modBarrel = null;
        ModuleInfo modUnderBarrel = null;
        if (itemRendering.hasTagCompound()) {
            modScope = rStack.getCurrentModule(itemRendering, EnumModule.SCOPE);
            modBarrel = rStack.getCurrentModule(itemRendering, EnumModule.BARREL);
            modUnderBarrel = rStack.getCurrentModule(itemRendering, EnumModule.UNDERBARREL);
        }
        ItemRenderer.renderItemIn2D(tessellator, gunIcon.getMaxU(), gunIcon.getMinV(), gunIcon.getMinU(), gunIcon.getMaxV(), gunIcon.getIconWidth(), gunIcon.getIconHeight(), 0.08f);
        GL11.glPushMatrix();
        GL11.glPolygonOffset(-0.05F, -0.05F);
        GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
        if (modScope != null && modScope.isShouldBeRendered()) {
            IIcon iconScope = modScope.getMod().getIconToRender();
            if (iconScope != null) {
                float xOffset = modScope.getXOffset() * 0.0625f;
                float yOffset = modScope.getYOffset() * 0.0625f;
                GL11.glPushMatrix();
                GL11.glTranslatef(xOffset, yOffset, 0.01f);
                ItemRenderer.renderItemIn2D(tessellator, iconScope.getMaxU(), iconScope.getMinV(), iconScope.getMinU(), iconScope.getMaxV(), iconScope.getIconWidth(), iconScope.getIconHeight(), 0.1f);
                GL11.glPopMatrix();
            }
        }
        if (modBarrel != null && modBarrel.isShouldBeRendered()) {
            IIcon iconSilencer = modBarrel.getMod().getIconToRender();
            if (iconSilencer != null) {
                float xOffset = modBarrel.getXOffset() * 0.0625f;
                float yOffset = modBarrel.getYOffset() * 0.0625f;
                GL11.glPushMatrix();
                GL11.glTranslatef(xOffset, yOffset, 0.01f);
                ItemRenderer.renderItemIn2D(tessellator, iconSilencer.getMaxU(), iconSilencer.getMinV(), iconSilencer.getMinU(), iconSilencer.getMaxV(), iconSilencer.getIconWidth(), iconSilencer.getIconHeight(), 0.1f);
                GL11.glPopMatrix();
            }
        }
        if (modUnderBarrel != null && modUnderBarrel.isShouldBeRendered()) {
            IIcon iconUnderBarrel = modUnderBarrel.getMod().getIconToRender();
            if (iconUnderBarrel != null) {
                float xOffset = modUnderBarrel.getXOffset() * 0.0625f;
                float yOffset = modUnderBarrel.getYOffset() * 0.0625f;
                GL11.glPushMatrix();
                if (modUnderBarrel.getMod() instanceof ItemLaser) {
                    GL11.glPushMatrix();
                    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
                    GL11.glTranslatef(xOffset, yOffset, -0.07f);
                    ItemRenderer.renderItemIn2D(tessellator, iconUnderBarrel.getMaxU(), iconUnderBarrel.getMinV(), iconUnderBarrel.getMinU(), iconUnderBarrel.getMaxV(), iconUnderBarrel.getIconWidth(), iconUnderBarrel.getIconHeight(), 0.03f);
                    GL11.glPopMatrix();
                } else {
                    if (entityLivingBase instanceof EntityPlayer && modUnderBarrel.getMod() instanceof ItemBipod) {
                        if (!(this.isWatchingGun && entityLivingBase instanceof EntityPlayerSP) && (PlayerMiscData.getPlayerData((EntityPlayer) entityLivingBase).isLying() && !EntityUtils.isFullyInMaterial(entityLivingBase, Material.water))) {
                            iconUnderBarrel = ((ItemBipod) modUnderBarrel.getMod()).getIconBipodActive();
                            GL11.glTranslatef(xOffset + 0.90625f, yOffset + 0.2046875f, 0.42890625f);
                            GL11.glRotatef(90, 0, 1, 0);
                            GL11.glRotatef(-45, 1, 0, 0);
                            ItemRenderer.renderItemIn2D(tessellator, iconUnderBarrel.getMaxU(), iconUnderBarrel.getMinV(), iconUnderBarrel.getMinU(), iconUnderBarrel.getMaxV(), iconUnderBarrel.getIconWidth(), iconUnderBarrel.getIconHeight(), 0.05f);
                        } else {
                            GL11.glTranslatef(xOffset, yOffset, 0.01f);
                            ItemRenderer.renderItemIn2D(tessellator, iconUnderBarrel.getMaxU(), iconUnderBarrel.getMinV(), iconUnderBarrel.getMinU(), iconUnderBarrel.getMaxV(), iconUnderBarrel.getIconWidth(), iconUnderBarrel.getIconHeight(), 0.1f);
                        }
                    } else {
                        GL11.glTranslatef(xOffset, yOffset, 0.01f);
                        ItemRenderer.renderItemIn2D(tessellator, iconUnderBarrel.getMaxU(), iconUnderBarrel.getMinV(), iconUnderBarrel.getMinU(), iconUnderBarrel.getMaxV(), iconUnderBarrel.getIconWidth(), iconUnderBarrel.getIconHeight(), 0.1f);
                    }
                }
                GL11.glPopMatrix();
            }
        }
        GL11.glPolygonOffset(0.0F, 0.0F);
        GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
        GL11.glPopMatrix();
    }

    private void drawMuzzleFlashThirdPerson() {
        Tessellator tessellator2 = Tessellator.instance;
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_LIGHTING);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, 1, 0, 0);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);

        GL11.glRotatef(-6, 1, 0, 1);
        GL11.glTranslatef(1.9f, -1.8f, 0.45f);
        GL11.glRotatef(-19, 0, 1, 0);
        GL11.glRotatef(-20, 0, 0, 1);
        GL11.glRotatef(-8, 0, 1, 0);

        float f6 = 0.8f;
        float f7 = -1;
        float f8 = 0.5f;
        float f9 = 2;

        Minecraft.getMinecraft().getTextureManager().bindTexture(GunItemRender.muzzleflashEnt2[Math.floorMod(Minecraft.getMinecraft().thePlayer.ticksExisted, 3)]);
        GL11.glPushMatrix();
        GL11.glRotatef(90, 0, 1, 0);
        GL11.glTranslatef(-0.88f, 0.0f, -1.81f);
        tessellator2.startDrawingQuads();
        tessellator2.addVertexWithUV(f6, f8, 1, 1, 1);
        tessellator2.addVertexWithUV(f7, f8, 1, 0, 1);
        tessellator2.addVertexWithUV(f7, f9, 1, 0, 0);
        tessellator2.addVertexWithUV(f6, f9, 1, 1, 0);
        tessellator2.draw();
        GL11.glPopMatrix();

        Minecraft.getMinecraft().getTextureManager().bindTexture(GunItemRender.muzzleflashEnt1[Math.floorMod(Minecraft.getMinecraft().thePlayer.ticksExisted, 3)]);
        tessellator2.startDrawingQuads();
        tessellator2.addVertexWithUV(f6, f8, 1, 1, 1);
        tessellator2.addVertexWithUV(f7, f8, 1, 0, 1);
        tessellator2.addVertexWithUV(f7, f9, 1, 0, 0);
        tessellator2.addVertexWithUV(f6, f9, 1, 1, 0);
        tessellator2.draw();

        GL11.glPushMatrix();
        GL11.glRotatef(90, 1, 0, 0);
        GL11.glTranslatef(0.0f, -0.25f, -2.25f);
        tessellator2.startDrawingQuads();
        tessellator2.addVertexWithUV(f6, f8, 1, 1, 1);
        tessellator2.addVertexWithUV(f7, f8, 1, 0, 1);
        tessellator2.addVertexWithUV(f7, f9, 1, 0, 0);
        tessellator2.addVertexWithUV(f6, f9, 1, 1, 0);
        tessellator2.draw();
        GL11.glPopMatrix();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    private void drawMuzzleFlashFirstPerson(ResourceLocation location) {
        Tessellator tessellator2 = Tessellator.instance;
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
        float f6 = 0.8f;
        float f7 = -1;
        float f8 = 0.5f;
        float f9 = 2;
        Minecraft.getMinecraft().getTextureManager().bindTexture(location);
        GL11.glPushMatrix();
        GL11.glRotatef(120, 0, 1, 0);
        tessellator2.startDrawingQuads();
        tessellator2.addVertexWithUV(f6, f8, 1, 1, 1);
        tessellator2.addVertexWithUV(f7, f8, 1, 0, 1);
        tessellator2.addVertexWithUV(f7, f9, 1, 0, 0);
        tessellator2.addVertexWithUV(f6, f9, 1, 1, 0);
        tessellator2.draw();
        GL11.glPopMatrix();
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    private boolean canRenderMuzzleFlash1st(EntityPlayer pl) {
        return !pl.isInsideOfMaterial(Material.water) && !pl.isInsideOfMaterial(Material.lava) && this.muzzleFlashTimer > 0;
    }

    private boolean canRenderMuzzleFlash3d(EntityPlayer pl) {
        return !pl.isInsideOfMaterial(Material.water) && !pl.isInsideOfMaterial(Material.lava) && pl.getEntityData().getInteger("cdFlash") < 2 && pl.getEntityData().getInteger("cdFlash") > 0;
    }
}
