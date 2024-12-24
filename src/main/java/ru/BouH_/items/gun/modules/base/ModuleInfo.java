package ru.BouH_.items.gun.modules.base;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

public class ModuleInfo {
    private final ItemModule mod;
    private final int xOffset;
    private final int yOffset;
    private boolean shouldBeRendered = true;

    public ModuleInfo(@NotNull ItemModule mod, int xOffset, int yOffset) {
        this.mod = mod;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public ItemModule getMod() {
        return this.mod;
    }

    public void disableRendering() {
        this.shouldBeRendered = false;
    }

    public boolean isShouldBeRendered() {
        return this.shouldBeRendered;
    }

    @SideOnly(Side.CLIENT)
    public int getXOffset() {
        return this.xOffset;
    }

    @SideOnly(Side.CLIENT)
    public int getYOffset() {
        return this.yOffset;
    }
}
