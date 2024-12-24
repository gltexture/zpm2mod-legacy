package ru.BouH_.items.melee;

import cpw.mods.fml.relauncher.FMLLaunchHandler;
import net.minecraft.item.ItemSword;
import net.minecraftforge.client.MinecraftForgeClient;
import ru.BouH_.items.melee.render.SpearRender;

public class ItemSpear extends ItemSword {
    public ItemSpear(ToolMaterial p_i45356_1_) {
        super(p_i45356_1_);
        if (FMLLaunchHandler.side().isClient()) {
            MinecraftForgeClient.registerItemRenderer(this, SpearRender.instance);
        }
    }
}
