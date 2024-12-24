package ru.BouH_.render.entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.*;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import ru.BouH_.Main;
import ru.BouH_.gameplay.client.ItemChecker;
import ru.BouH_.items.gun.base.AGunBase;
import ru.BouH_.items.gun.crossbow.ItemCrossbow;
import ru.BouH_.items.gun.modules.ItemLaser;
import ru.BouH_.items.gun.modules.base.EnumModule;
import ru.BouH_.items.gun.modules.base.ModuleInfo;
import ru.BouH_.items.misc.ItemRocket;

import java.util.Random;

@SideOnly(Side.CLIENT)
public class RenderItemZp extends RenderItem {
    private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    private final RenderBlocks renderBlocksRi = new RenderBlocks();
    private final Random random = new Random();
    public boolean renderWithColor = true;

    public RenderItemZp() {
        this.shadowSize = 0.15F;
        this.shadowOpaque = 0.75F;
    }

    protected void passSpecialRender(EntityItem p_77033_1_, String text, double p_77033_2_, double p_77033_4_, double p_77033_6_) {
        FontRenderer fontrenderer = RenderManager.instance.getFontRenderer();
        if (p_77033_1_.getEntityItem().stackSize > 1) {
            text += " x" + p_77033_1_.getEntityItem().stackSize;
        }
        float f1 = 0.02f;
        float y = p_77033_1_.getEntityItem().getItem() instanceof ItemBlock ? 0.8f : 0.35f;
        GL11.glPushMatrix();
        GL11.glTranslatef((float) p_77033_2_ - 0.1F, (float) p_77033_4_ + y, (float) p_77033_6_);
        GL11.glNormal3f(0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
        GL11.glScalef(-f1, -f1, f1);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glTranslatef(0.0F, 0.05F / f1, 0.0F);
        GL11.glDepthMask(false);
        GL11.glEnable(GL11.GL_BLEND);
        int j = p_77033_1_.worldObj.getLightBrightnessForSkyBlocks(MathHelper.floor_double(p_77033_1_.posX), MathHelper.floor_double(p_77033_1_.posY), MathHelper.floor_double(p_77033_1_.posZ), 0);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j % 65536, (float) j / 65536);
        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
        Tessellator tessellator = Tessellator.instance;
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        tessellator.startDrawingQuads();
        int i = fontrenderer.getStringWidth(text) / 2;
        tessellator.setColorRGBA_F(0.0F, 0.0F, 0.0F, 0.25F);
        tessellator.addVertex((-i - 1), -1.0D, 0.0D);
        tessellator.addVertex((-i - 1), 8.0D, 0.0D);
        tessellator.addVertex((i + 1), 8.0D, 0.0D);
        tessellator.addVertex((i + 1), -1.0D, 0.0D);
        tessellator.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDepthMask(true);
        fontrenderer.drawString(text, -fontrenderer.getStringWidth(text) / 2, 0, 0x00FF00);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glPopMatrix();
    }

    public void doRender(EntityItem p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
        ItemStack itemstack = p_76986_1_.getEntityItem();

        if (itemstack.getItem() != null) {
            this.bindEntityTexture(p_76986_1_);
            TextureUtil.func_152777_a(false, false, 1.0F);
            this.random.setSeed(187L);
            GL11.glPushMatrix();
            float f2 = (!Main.settingsZp.fancyDrop.isFlag() && shouldBob()) ? MathHelper.sin(((float) p_76986_1_.age + p_76986_9_) / 10.0F + p_76986_1_.hoverStart) * 0.1F + 0.1F : 0;
            float f3 = (((float) p_76986_1_.age + p_76986_9_) / 20.0F + p_76986_1_.hoverStart) * (180F / (float) Math.PI);
            byte b0 = 1;

            if (p_76986_1_.getEntityItem().stackSize > 1) {
                b0 = 2;
            }

            if (p_76986_1_.getEntityItem().stackSize > 5) {
                b0 = 3;
            }

            if (p_76986_1_.getEntityItem().stackSize > 20) {
                b0 = 4;
            }

            if (p_76986_1_.getEntityItem().stackSize > 40) {
                b0 = 5;
            }

            b0 = getMiniBlockCount(itemstack, b0);
            if (f2 == 0) {
                this.shadowSize = 0.0f;
            } else {
                this.shadowSize = 0.15f;
            }
            if (renderInFrame && f2 == 0) {
                GL11.glTranslatef((float) p_76986_2_, (float) p_76986_4_ + 0.1f, (float) p_76986_6_);
            } else {
                GL11.glTranslatef((float) p_76986_2_, (float) p_76986_4_ + f2 + (p_76986_1_.getEntityId() % 1000) * 1.0e-5f, (float) p_76986_6_);
            }

            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            float f6;
            float f7;
            int k;

            boolean flag = false;
            if (!Minecraft.getMinecraft().gameSettings.hideGUI) {
                if (Main.settingsZp.fancyDrop.isFlag()) {
                    EntityItem entityItem = ItemChecker.selectedItem;
                    if (entityItem != null && entityItem.getEntityItem() == itemstack) {
                        ItemStack itemStack = entityItem.getEntityItem();
                        if (itemStack.stackSize > 0) {
                            flag = true;
                        }
                    }
                }
            }

            if (!ForgeHooksClient.renderEntityItem(p_76986_1_, itemstack, f2, f3, random, renderManager.renderEngine, field_147909_c, b0)) {
                if (itemstack.getItemSpriteNumber() == 0 && itemstack.getItem() instanceof ItemBlock && RenderBlocks.renderItemIn3d(Block.getBlockFromItem(itemstack.getItem()).getRenderType())) {
                    Block block = Block.getBlockFromItem(itemstack.getItem());

                    if (renderInFrame) {
                        GL11.glScalef(1.5F, 1.5F, 1.5F);
                        GL11.glTranslatef(0.0F, 0.05F, 0.0F);
                        GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
                    } else {
                        if (Main.settingsZp.fancyDrop.isFlag()) {
                            if (this.renderManager.options.fancyGraphics) {
                                if (!p_76986_1_.onGround) {
                                    GL11.glRotatef(((p_76986_1_.age + p_76986_9_) / 8 + p_76986_1_.hoverStart) * (180F / (float) Math.PI), 1.0F, 1.0F, 1.0F);
                                } else {
                                    GL11.glRotatef((p_76986_1_.hoverStart) * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
                                }
                                GL11.glTranslatef(0.0F, 0.15F, 0.0F);
                                GL11.glScalef(2, 2, 2);
                            } else {
                                GL11.glTranslatef(0.0F, 0.05F, 0.0F);
                            }
                        } else {
                            GL11.glRotatef(f3, 0.0F, 1.0F, 0.0F);
                        }
                    }

                    float f9 = 0.25F;
                    k = block.getRenderType();

                    if (k == 1 || k == 19 || k == 12 || k == 2) {
                        f9 = 0.5F;
                    }

                    if (block.getRenderBlockPass() > 0) {
                        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
                        GL11.glEnable(GL11.GL_BLEND);
                        OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
                    }

                    GL11.glScalef(f9, f9, f9);

                    for (int l = 0; l < b0; ++l) {
                        GL11.glPushMatrix();

                        if (l > 0) {
                            f6 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F / f9;
                            f7 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F / f9;
                            float f8 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.2F / f9;
                            GL11.glTranslatef(f6, f7, f8);
                        }

                        this.renderBlocksRi.renderBlockAsItem(block, itemstack.getMetadata(), 1.0f);
                        GL11.glPopMatrix();
                    }

                    if (block.getRenderBlockPass() > 0) {
                        GL11.glDisable(GL11.GL_BLEND);
                    }
                } else {
                    float f5;

                    if (itemstack.getItem().requiresMultipleRenderPasses()) {
                        if (renderInFrame) {
                            GL11.glScalef(0.5128205F, 0.5128205F, 0.5128205F);
                            GL11.glTranslatef(0.0F, -0.05F, 0.0F);
                        } else {
                            GL11.glScalef(0.5F, 0.5F, 0.5F);
                        }

                        for (int j = 0; j < itemstack.getItem().getRenderPasses(itemstack.getMetadata()); ++j) {
                            this.random.setSeed(187L);
                            IIcon iicon1 = itemstack.getItem().getIcon(itemstack, j);

                            if (this.renderWithColor) {
                                k = itemstack.getItem().getColorFromItemStack(itemstack, j);
                                f5 = (float) (k >> 16 & 255) / 255.0F;
                                f6 = (float) (k >> 8 & 255) / 255.0F;
                                f7 = (float) (k & 255) / 255.0F;
                                this.renderDroppedItem(p_76986_1_, iicon1, b0, p_76986_9_, f5, f6, f7, j);
                            } else {
                                this.renderDroppedItem(p_76986_1_, iicon1, b0, p_76986_9_, 1.0F, 1.0F, 1.0F, j);
                            }
                        }
                    } else {
                        if (itemstack.getItem() instanceof ItemCloth) {
                            GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
                            GL11.glEnable(GL11.GL_BLEND);
                            OpenGlHelper.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, 1, 0);
                        }

                        if (renderInFrame) {
                            GL11.glScalef(0.5128205F, 0.5128205F, 0.5128205F);
                            GL11.glTranslatef(0.0F, -0.05F, 0.0F);
                        } else {
                            GL11.glScalef(0.5F, 0.5F, 0.5F);
                        }

                        IIcon iicon = itemstack.getIconIndex();

                        if (this.renderWithColor) {
                            int i = itemstack.getItem().getColorFromItemStack(itemstack, 0);
                            float f4 = (float) (i >> 16 & 255) / 255.0F;
                            f5 = (float) (i >> 8 & 255) / 255.0F;
                            f6 = (float) (i & 255) / 255.0F;
                            this.renderDroppedItem(p_76986_1_, iicon, b0, p_76986_9_, f4, f5, f6);
                        } else {
                            this.renderDroppedItem(p_76986_1_, iicon, b0, p_76986_9_, 1.0F, 1.0F, 1.0F);
                        }

                        if (itemstack.getItem() instanceof ItemCloth) {
                            GL11.glDisable(GL11.GL_BLEND);
                        }
                    }
                }

                GL11.glDisable(GL12.GL_RESCALE_NORMAL);
                GL11.glPopMatrix();
                this.bindEntityTexture(p_76986_1_);
                TextureUtil.func_147945_b();
                if (flag) {
                    this.passSpecialRender(p_76986_1_, "[F] " + itemstack.getDisplayName(), p_76986_2_, p_76986_4_, p_76986_6_);
                }
            }
        }
    }

    private void renderDroppedItem(EntityItem p_77020_1_, IIcon p_77020_2_, int p_77020_3_, float p_77020_4_, float p_77020_5_, float p_77020_6_, float p_77020_7_) {
        ItemStack stack = p_77020_1_.getEntityItem();
        if (stack != null && stack.getItem() instanceof ItemCrossbow) {
            ItemCrossbow itemCrossbow = (ItemCrossbow) stack.getItem();
            p_77020_2_ = itemCrossbow.getActualIcon(stack);
        }
        this.renderDroppedItem(p_77020_1_, p_77020_2_, p_77020_3_, p_77020_4_, p_77020_5_, p_77020_6_, p_77020_7_, 0);
    }

    private void renderDroppedItem(EntityItem p_77020_1_, IIcon p_77020_2_, int p_77020_3_, float p_77020_4_, float p_77020_5_, float p_77020_6_, float p_77020_7_, int pass) {
        Tessellator tessellator = Tessellator.instance;
        if (p_77020_2_ == null) {
            TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
            ResourceLocation resourcelocation = texturemanager.getResourceLocation(p_77020_1_.getEntityItem().getItemSpriteNumber());
            p_77020_2_ = ((TextureMap) texturemanager.getTexture(resourcelocation)).getAtlasSprite("missingno");
        }

        float f14 = p_77020_2_.getMinU();
        float f15 = p_77020_2_.getMaxU();
        float f4 = p_77020_2_.getMinV();
        float f5 = p_77020_2_.getMaxV();
        float f6 = 1.0F;
        float f7 = 0.5F;
        float f8 = 0.25F;
        float f10;
        ItemStack itemstack = p_77020_1_.getEntityItem();
        if (this.renderManager.options.fancyGraphics) {
            GL11.glPushMatrix();
            GL11.glDisable(GL11.GL_CULL_FACE);
            if (renderInFrame) {
                GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            } else {
                if (Main.settingsZp.fancyDrop.isFlag()) {
                    float scale = 1.2f;
                    if (itemstack.getItem() instanceof ItemRocket || itemstack.getItem() instanceof ItemFishingRod || itemstack.getItem() instanceof ItemSword || itemstack.getItem() instanceof ItemBow || itemstack.getItem() instanceof ItemTool || itemstack.getItem() instanceof ItemHoe) {
                        scale = 1.7f;
                    } else if (itemstack.getItem() instanceof AGunBase) {
                        AGunBase aGunBase = (AGunBase) itemstack.getItem();
                        if (aGunBase.getRenderType() == AGunBase.GunType.CROSSBOW || aGunBase.getRenderType() == AGunBase.GunType.RIFLE2 || aGunBase.getRenderType() == AGunBase.GunType.RIFLE || aGunBase.getRenderType() == AGunBase.GunType.RPG) {
                            scale = 2.0f;
                        } else if (aGunBase.getRenderType() == AGunBase.GunType.PISTOL2) {
                            scale = 1.75f;
                        } else {
                            scale = 1.5f;
                        }
                    }

                    GL11.glScalef(scale, scale, scale);

                    if (!p_77020_1_.onGround) {
                        GL11.glRotatef(((p_77020_1_.age + p_77020_4_) / 8 + p_77020_1_.hoverStart) * (180F / (float) Math.PI), 1.0F, 1.0F, 1.0F);
                    }

                    GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glRotatef((p_77020_1_.hoverStart) * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
                } else {
                    GL11.glRotatef((((float) p_77020_1_.age + p_77020_4_) / 20.0F + p_77020_1_.hoverStart) * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
                }
            }

            float f9 = 0.0625F;
            f10 = 0.021875F;
            int j = itemstack.stackSize;
            byte b0;

            if (j < 2) {
                b0 = 1;
            } else if (j < 16) {
                b0 = 2;
            } else if (j < 32) {
                b0 = 3;
            } else {
                b0 = 4;
            }

            b0 = getMiniItemCount(itemstack, b0);

            GL11.glTranslatef(-f7, -f8, -((f9 + f10) * (float) b0 / 2.0F));

            for (int k = 0; k < b0; ++k) {
                if (k > 0 && shouldSpreadItems()) {
                    float x = (random.nextFloat() * 2.0F - 1.0F) * 0.3F / 0.5F;
                    float y = (random.nextFloat() * 2.0F - 1.0F) * 0.3F / 0.5F;
                    GL11.glTranslatef(x, y, f9 + f10);
                } else {
                    GL11.glTranslatef(0f, 0f, f9 + f10);
                }

                if (itemstack.getItemSpriteNumber() == 0) {
                    this.bindTexture(TextureMap.locationBlocksTexture);
                } else {
                    this.bindTexture(TextureMap.locationItemsTexture);
                }

                GL11.glColor4f(p_77020_5_, p_77020_6_, p_77020_7_, 1.0F);

                ItemRenderer.renderItemIn2D(tessellator, f15, f4, f14, f5, p_77020_2_.getIconWidth(), p_77020_2_.getIconHeight(), f9);
                if (itemstack.getItem() instanceof AGunBase) {
                    this.renderWeaponMod(tessellator, itemstack);
                }
                if (itemstack.hasEffect(pass)) {
                    GL11.glDepthFunc(GL11.GL_EQUAL);
                    GL11.glDisable(GL11.GL_LIGHTING);
                    this.renderManager.renderEngine.bindTexture(RES_ITEM_GLINT);
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
                    float f11 = 0.76F;
                    GL11.glColor4f(0.5F * f11, 0.25F * f11, 0.8F * f11, 1.0F);
                    GL11.glMatrixMode(GL11.GL_TEXTURE);
                    GL11.glPushMatrix();
                    float f12 = 0.125F;
                    GL11.glScalef(f12, f12, f12);
                    float f13 = (float) (Minecraft.getSystemTime() % 3000L) / 3000.0F * 8.0F;
                    GL11.glTranslatef(f13, 0.0F, 0.0F);
                    GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
                    ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 255, 255, f9);
                    GL11.glPopMatrix();
                    GL11.glPushMatrix();
                    GL11.glScalef(f12, f12, f12);
                    f13 = (float) (Minecraft.getSystemTime() % 4873L) / 4873.0F * 8.0F;
                    GL11.glTranslatef(-f13, 0.0F, 0.0F);
                    GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
                    ItemRenderer.renderItemIn2D(tessellator, 0.0F, 0.0F, 1.0F, 1.0F, 255, 255, f9);
                    GL11.glPopMatrix();
                    GL11.glMatrixMode(GL11.GL_MODELVIEW);
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glEnable(GL11.GL_LIGHTING);
                    GL11.glDepthFunc(GL11.GL_LEQUAL);
                }
            }
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glPopMatrix();
        } else {
            for (int l = 0; l < p_77020_3_; ++l) {
                GL11.glPushMatrix();

                if (l > 0) {
                    f10 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.3F;
                    float f16 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.3F;
                    float f17 = (this.random.nextFloat() * 2.0F - 1.0F) * 0.3F;
                    GL11.glTranslatef(f10, f16, f17);
                }

                if (!renderInFrame) {
                    if (p_77020_1_.onGround && Main.settingsZp.fancyDrop.isFlag()) {
                        GL11.glTranslatef(0, 0.01f, 0);
                        GL11.glRotatef(-90, 1.0F, 0.0F, 0.0F);
                        GL11.glRotatef((p_77020_1_.hoverStart) * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
                    } else {
                        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                    }
                }

                GL11.glColor4f(p_77020_5_, p_77020_6_, p_77020_7_, 1.0F);
                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, 1.0F, 0.0F);
                tessellator.addVertexWithUV(0.0F - f7, 0.0F - f8, 0.0D, f14, f5);
                tessellator.addVertexWithUV(f6 - f7, 0.0F - f8, 0.0D, f15, f5);
                tessellator.addVertexWithUV(f6 - f7, 1.0F - f8, 0.0D, f15, f4);
                tessellator.addVertexWithUV(0.0F - f7, 1.0F - f8, 0.0D, f14, f4);
                tessellator.draw();
                GL11.glPopMatrix();
            }
        }
    }

    private void renderWeaponMod(Tessellator tessellator, ItemStack stack) {
        AGunBase rStack = ((AGunBase) stack.getItem());
        ModuleInfo modScope = null;
        ModuleInfo modBarrel = null;
        ModuleInfo modUnderBarrel = null;
        if (stack.hasTagCompound()) {
            modScope = rStack.getCurrentModule(stack, EnumModule.SCOPE);
            modBarrel = rStack.getCurrentModule(stack, EnumModule.BARREL);
            modUnderBarrel = rStack.getCurrentModule(stack, EnumModule.UNDERBARREL);
        }
        GL11.glPushMatrix();
        GL11.glPolygonOffset(-0.1F, -0.1F);
        GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
        if (modScope != null && modScope.isShouldBeRendered()) {
            IIcon iconScope = modScope.getMod().getIconToRender();
            if (iconScope != null) {
                float xOffset = modScope.getXOffset() * 0.0625f;
                float yOffset = modScope.getYOffset() * 0.0625f;
                GL11.glPushMatrix();
                GL11.glTranslatef(xOffset, yOffset, 0.02f);
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
                GL11.glTranslatef(xOffset, yOffset, 0.02f);
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
                    GL11.glTranslatef(xOffset, yOffset, -0.06f);
                    ItemRenderer.renderItemIn2D(tessellator, iconUnderBarrel.getMaxU(), iconUnderBarrel.getMinV(), iconUnderBarrel.getMinU(), iconUnderBarrel.getMaxV(), iconUnderBarrel.getIconWidth(), iconUnderBarrel.getIconHeight(), 0.03f);
                    GL11.glPopMatrix();
                } else {
                    GL11.glTranslatef(xOffset, yOffset, 0.02f);
                    ItemRenderer.renderItemIn2D(tessellator, iconUnderBarrel.getMaxU(), iconUnderBarrel.getMinV(), iconUnderBarrel.getMinU(), iconUnderBarrel.getMaxV(), iconUnderBarrel.getIconWidth(), iconUnderBarrel.getIconHeight(), 0.1f);
                }
                GL11.glPopMatrix();
            }
        }
        GL11.glPolygonOffset(0F, 0F);
        GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
        GL11.glPopMatrix();
    }
}
