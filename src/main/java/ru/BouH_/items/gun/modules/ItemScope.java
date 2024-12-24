package ru.BouH_.items.gun.modules;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import ru.BouH_.Main;
import ru.BouH_.items.gun.modules.base.EnumModule;
import ru.BouH_.items.gun.modules.base.ItemModule;

public class ItemScope extends ItemModule {
    private final float fov;
    private final ResourceLocation resourceLocation;

    public ItemScope(String name, String textureName, float fov) {
        super(name, EnumModule.SCOPE);
        this.fov = fov;
        this.resourceLocation = new ResourceLocation(Main.MODID + ":textures/gui/" + textureName + ".png");
    }

    public ResourceLocation getScopeGuiLocation() {
        return this.resourceLocation;
    }

    public float getFov() {
        return this.fov;
    }

    public float getModifiedInaccuracy(EntityPlayer player, boolean inZoom) {
        return inZoom ? this.inaccuracyModifier : 0.0f;
    }
}
