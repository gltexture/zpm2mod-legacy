package ru.BouH_.utils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import ru.BouH_.ConfigZp;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.ByteBuffer;

@SideOnly(Side.CLIENT)
public class RenderUtils {
    public static final RenderItem renderItem = new RenderItem();
    public static float partialTicks;
    public static ModelBiped modelArmArmor = new ModelBiped(0.2F);
    public static ModelBiped modelArm = new ModelBiped(0.0F);
    public static boolean canBeBrightSetting;

    public static boolean isBright() {
        return RenderUtils.canBeBrightSetting && ConfigZp.clientHigherBrightness;
    }

    public static String getModifiedValueBiggerWorse(float value, float mod) {
        EnumChatFormatting enumChatFormatting = EnumChatFormatting.GRAY;
        if (mod > 1.0f) {
            enumChatFormatting = EnumChatFormatting.RED;
        } else if (mod < 1.0f) {
            enumChatFormatting = EnumChatFormatting.GREEN;
        }
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(value * mod));
        String number = bigDecimal.setScale(2, RoundingMode.FLOOR).toString();
        if (number.endsWith("0")) {
            number = number.substring(0, number.length() - 1);
        }
        return enumChatFormatting + number;
    }

    public static void renderIcon(double x, double y, ItemStack item) {
        if (item != null) {
            GL11.glPushMatrix();
            GL11.glTranslated(x - 16, y - 16, 20);
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            RenderHelper.enableGUIStandardItemLighting();
            GL11.glEnable(GL11.GL_CULL_FACE);
            RenderUtils.renderItem.renderItemAndEffectIntoGUI(Minecraft.getMinecraft().fontRendererObj, Minecraft.getMinecraft().getTextureManager(), item, 16, 16);
            GL11.glEnable(GL11.GL_BLEND);
            RenderHelper.disableStandardItemLighting();
            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    public static void renderIconBlack(double x, double y, ItemStack itemStack) {
        IIcon icon = itemStack.getItem().getIconFromDamage(itemStack.getMetadata());
        if (icon != null) {
            GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
            Minecraft.getMinecraft().getTextureManager().bindTexture(itemStack.getItem() instanceof ItemBlock ? TextureMap.locationBlocksTexture : TextureMap.locationItemsTexture);
            Tessellator tessellator = Tessellator.instance;
            tessellator.startDrawingQuads();
            tessellator.addVertexWithUV(x, y + 16, 10, icon.getMinU(), icon.getMaxV());
            tessellator.addVertexWithUV(x + 16, y + 16, 10, icon.getMaxU(), icon.getMaxV());
            tessellator.addVertexWithUV(x + 16, y, 10, icon.getMaxU(), icon.getMinV());
            tessellator.addVertexWithUV(x, y, 10, icon.getMinU(), icon.getMinV());
            tessellator.draw();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
    }

    public static void renderIconBlockBlack(double x, double y, ItemStack itemStack) {
        if (!RenderBlocks.renderItemIn3d(Block.getBlockFromItem(itemStack.getItem()).getRenderType())) {
            RenderUtils.renderIconBlack(x, y, itemStack);
            return;
        }
        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        Block block = Block.getBlockFromItem(itemStack.getItem());
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPushMatrix();
        GL11.glTranslatef((float) (x - 2), (float) (y + 3), 20);
        GL11.glScalef(10.0F, 10.0F, 10.0F);
        GL11.glTranslatef(1.0F, 0.5F, 1.0F);
        GL11.glScalef(1.0F, 1.0F, -1.0F);
        GL11.glRotatef(210.0F, 1.0F, 0.0F, 0.0F);
        GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-90.0F, 0.0F, 1.0F, 0.0F);
        RenderBlocks.getInstance().renderBlockAsItem(block, 0, 0.0F);
        GL11.glPopMatrix();
    }

    public static String getModifiedValueBiggerBetter(float value, float mod) {
        EnumChatFormatting enumChatFormatting = EnumChatFormatting.GRAY;
        if (mod > 1.0f) {
            enumChatFormatting = EnumChatFormatting.GREEN;
        } else if (mod < 1.0f) {
            enumChatFormatting = EnumChatFormatting.RED;
        }
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(value * mod));
        String number = bigDecimal.setScale(2, RoundingMode.FLOOR).toString();
        if (number.endsWith("0")) {
            number = number.substring(0, number.length() - 1);
        }
        return enumChatFormatting + number;
    }

    public static void drawStringInSquare(String text, int x, int y, int clr) {
        Gui.drawRect(x - 1, y - 1, x + Minecraft.getMinecraft().fontRendererObj.getStringWidth(text), y + 9, -2147483648);
        Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(text, x, y, clr);
    }

    public static void renderArm(boolean rightArm) {
        EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        GL11.glPushMatrix();
        int i = player.getBrightnessForRender(1.0f);
        int j = i % 65536;
        int k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
        if (!player.isInvisible()) {
            ItemStack equipment = player.getCurrentArmor(2);
            if (equipment != null && equipment.getItem() instanceof ItemArmor) {
                GL11.glPushMatrix();

                int color = equipment.getItem().getColorFromItemStack(player.getCurrentArmor(2), 0);
                float f5 = (float) (color >> 16 & 255) / 255.0F;
                float f6 = (float) (color >> 8 & 255) / 255.0F;
                float f7 = (float) (color & 255) / 255.0F;
                GL11.glColor4f(f5, f6, f7, 1.0F);

                Minecraft.getMinecraft().getTextureManager().bindTexture(RenderBiped.getArmorResource(Minecraft.getMinecraft().thePlayer, player.getCurrentArmor(2), 1, null));
                modelArmArmor.swingProgress = 0.0F;
                modelArmArmor.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, player);
                if (rightArm) {
                    modelArmArmor.bipedRightArm.render(0.0625F);
                } else {
                    modelArmArmor.bipedLeftArm.render(0.0625F);
                }
                GL11.glColor4f(1, 1, 1, 1);
                GL11.glPopMatrix();
            }

            Minecraft.getMinecraft().getTextureManager().bindTexture(player.getLocationSkin());
            modelArm.swingProgress = 0.0F;
            modelArm.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, player);
            if (rightArm) {
                modelArm.bipedRightArm.render(0.0625F);
            } else {
                modelArm.bipedLeftArm.render(0.0625F);
            }
        }
        GL11.glPopMatrix();
    }

    public static void drawTexturedModalRectCustom(int x, int y, int width, int height) {
        RenderUtils.drawTexturedModalRectCustom(x, y, width, height, -100);
    }

    public static void drawTexturedModalRectCustom(int x, int y, int width, int height, int lvl) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV(x, y + height, lvl, 0, 1);
        tessellator.addVertexWithUV(x + width, y + height, lvl, 1, 1);
        tessellator.addVertexWithUV(x + width, y, lvl, 1, 0);
        tessellator.addVertexWithUV(x, y, lvl, 0, 0);
        tessellator.draw();
    }

    public static int rgb2hex(int r, int g, int b) {
        return ((1 << 24) + (r << 16) + (g << 8) + b);
    }

    public static ByteBuffer getByteBufferFromPNG(InputStream stream) {
        BufferedImage bufferedimage;
        try {
            bufferedimage = ImageIO.read(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        int[] ints = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), null, 0, bufferedimage.getWidth());
        ByteBuffer bytebuffer = ByteBuffer.allocate(4 * ints.length);
        for (int k : ints) {
            bytebuffer.putInt(k << 8 | k >> 24 & 255);
        }
        bytebuffer.flip();
        return bytebuffer;
    }

    //FOR DEBUG
    public static float TRANSFORM_DEBUG_F1() {
        float f3 = Minecraft.getMinecraft().thePlayer.prevRenderArmPitch + (Minecraft.getMinecraft().thePlayer.renderArmPitch - Minecraft.getMinecraft().thePlayer.prevRenderArmPitch) * partialTicks;
        System.out.println("S " + f3);
        return f3;
    }

    //FOR DEBUG
    public static float TRANSFORM_DEBUG_F2() {
        float f4 = Minecraft.getMinecraft().thePlayer.prevRenderArmYaw + (Minecraft.getMinecraft().thePlayer.renderArmYaw - Minecraft.getMinecraft().thePlayer.prevRenderArmYaw) * partialTicks;
        System.out.println("W " + f4);
        return f4;
    }
}
