package ru.BouH_.weather.render;

import com.sun.javafx.util.Utils;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.ForgeHooks;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.opengl.NVFogDistance;
import ru.BouH_.gameplay.WorldManager;
import ru.BouH_.hook.client.FXHook;
import ru.BouH_.proxy.CommonProxy;
import ru.BouH_.utils.RenderUtils;
import ru.BouH_.weather.base.WeatherFog;
import ru.BouH_.weather.base.WeatherHandler;

import java.awt.*;

public class RenderFog {
    public static RenderFog instance = new RenderFog();

    @SubscribeEvent
    public void onFog(EntityViewRenderEvent.RenderFogEvent ev) {
        WeatherFog weatherFog = WeatherHandler.instance.getWeatherFog();
        if (weatherFog.currentFogDepth > 0) {
            float f1 = 20 + (Minecraft.getMinecraft().entityRenderer.farPlaneDistance) * (1.0F - weatherFog.currentFogDepth / 20.0F);
            GL11.glFogi(GL11.GL_FOG_MODE, GL11.GL_LINEAR);
            GL11.glFogf(GL11.GL_FOG_START, f1 * (Math.min(1.0f - weatherFog.currentFogDepth / 20.0f, 0.75F)));
            GL11.glFogf(GL11.GL_FOG_END, f1);
            if (GLContext.getCapabilities().GL_NV_fog_distance) {
                GL11.glFogi(NVFogDistance.GL_FOG_DISTANCE_MODE_NV, NVFogDistance.GL_EYE_RADIAL_NV);
            }
        } else {
            float f1 = Minecraft.getMinecraft().entityRenderer.farPlaneDistance + 20;
            GL11.glFogf(GL11.GL_FOG_START, f1 * 0.75F);
            GL11.glFogf(GL11.GL_FOG_END, f1);
        }
    }

    @SubscribeEvent
    public void onFog(EntityViewRenderEvent.FogColors ev) {
        Minecraft.getMinecraft().entityRenderer.fogColor2 = 1.0f;
        Minecraft.getMinecraft().entityRenderer.fogColor1 = 1.0f;
        Vec3 rgb = Minecraft.getMinecraft().theWorld.getSkyColor(Minecraft.getMinecraft().thePlayer, (float) ev.renderPartialTicks);
        float del = 1.0f - Minecraft.getMinecraft().theWorld.getRainStrength((float) ev.renderPartialTicks) * 0.5f;
        if (Minecraft.getMinecraft().thePlayer.isInsideOfMaterial(Material.lava)) {
            return;
        }
        ev.red = (float) (rgb.xCoord * del);
        ev.green = (float) (rgb.yCoord * del);
        ev.blue = (float) (rgb.zCoord * del);
        if (WorldManager.is7Night(Minecraft.getMinecraft().theWorld) || Minecraft.getMinecraft().theWorld.getWorldInfo().getTerrainType().getWorldTypeName().equals(CommonProxy.worldTypeHardCoreZp.getWorldTypeName())) {
            float plagueMultiplier = FXHook.get7NightBrightness(Minecraft.getMinecraft().thePlayer);
            ev.blue *= plagueMultiplier;
            ev.red *= 1.0f;
            ev.green *= plagueMultiplier;
        }
    }
}
