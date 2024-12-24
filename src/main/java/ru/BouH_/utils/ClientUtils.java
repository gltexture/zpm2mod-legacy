package ru.BouH_.utils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import ru.BouH_.init.ItemsZp;
import ru.BouH_.items.gun.base.AGunBase;
import ru.BouH_.items.gun.modules.ItemNVScope;
import ru.BouH_.items.gun.modules.ItemScope;
import ru.BouH_.items.gun.modules.base.EnumModule;
import ru.BouH_.items.gun.modules.base.ModuleInfo;
import ru.BouH_.items.gun.render.GunItemRender;

public class ClientUtils {
    @SideOnly(Side.CLIENT)
    public static boolean isClientInNVG() {
        Minecraft mc = Minecraft.getMinecraft();
        return mc.gameSettings.thirdPersonView == 0 && EntityUtils.isInArmor(mc.thePlayer, ItemsZp.pnv, null, null, null);
    }

    @SideOnly(Side.CLIENT)
    public static boolean isClientInNightVisionScope() {
        Minecraft mc = Minecraft.getMinecraft();
        ItemStack stack = mc.thePlayer.getHeldItem();
        if (stack != null && stack.getItem() instanceof AGunBase) {
            AGunBase aGunBase = (AGunBase) stack.getItem();
            if (GunItemRender.instance.getTicksAfterScoping() == 3 && aGunBase.isPlayerInOpticScope(stack)) {
                ModuleInfo modScope = aGunBase.getCurrentModule(stack, EnumModule.SCOPE);
                ItemScope itemScope = ((ItemScope) modScope.getMod());
                return Minecraft.getMinecraft().entityRenderer.itemRenderer.equippedProgress == 1 && itemScope instanceof ItemNVScope;
            }
        }
        return false;
    }
}
