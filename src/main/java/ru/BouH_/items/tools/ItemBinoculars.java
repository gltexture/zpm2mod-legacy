package ru.BouH_.items.tools;

import cpw.mods.fml.relauncher.FMLLaunchHandler;
import net.minecraft.item.Item;
import net.minecraftforge.client.MinecraftForgeClient;
import ru.BouH_.items.gun.render.BinocularsItemRender;

public class ItemBinoculars extends Item {
    public ItemBinoculars(String unlocalizedName) {
        if (FMLLaunchHandler.side().isClient()) {
            MinecraftForgeClient.registerItemRenderer(this, BinocularsItemRender.instance);
        }
        this.setUnlocalizedName(unlocalizedName);
    }
}
