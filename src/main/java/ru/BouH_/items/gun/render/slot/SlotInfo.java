package ru.BouH_.items.gun.render.slot;

import ru.BouH_.items.gun.modules.base.EnumModule;
import ru.BouH_.items.gun.modules.base.ItemModule;

public class SlotInfo {
    private final boolean isMainSlot;
    private ItemModule itemModule;
    private EnumModule enumModule;
    private int posX;
    private int posY;

    public SlotInfo(boolean isMainSlot, EnumModule enumModule, ItemModule itemModule, int posX, int posY) {
        this.isMainSlot = isMainSlot;
        this.enumModule = enumModule;
        this.itemModule = itemModule;
        this.posX = posX;
        this.posY = posY;
    }

    public SlotInfo(boolean isMainSlot) {
        this(isMainSlot, null, null, 0, 0);
    }

    public boolean isMainSlot() {
        return this.isMainSlot;
    }

    public EnumModule getEnumModule() {
        return this.enumModule;
    }

    public void setEnumModule(EnumModule enumModule) {
        this.enumModule = enumModule;
    }

    public ItemModule getItemModule() {
        return this.itemModule;
    }

    public void setItemModule(ItemModule itemModule) {
        this.itemModule = itemModule;
    }

    public int getPosX() {
        return this.posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return this.posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
}
