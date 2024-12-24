package ru.BouH_.items.gun.render.modules.selector;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import ru.BouH_.items.gun.render.GunItemRender;
import ru.BouH_.items.gun.render.modules.gui.GuiModules;
import ru.BouH_.items.gun.render.slot.ModuleSection;
import ru.BouH_.items.gun.render.slot.SlotInfo;

import java.util.List;

public class SelectorMouse implements ISelector {
    private final List<ModuleSection> moduleSectionList;
    private final GuiModules guiModules;
    public SlotInfo currentSlot;
    private ModuleSection currentSelectedSection = null;

    public SelectorMouse(List<ModuleSection> moduleSectionList) {
        this.moduleSectionList = moduleSectionList;
        this.guiModules = new GuiModules(this);
    }

    public void update() {
        float mouseX = this.guiModules.mouseX();
        float mouseY = this.guiModules.mouseY();
        this.currentSlot = this.getMouseSlot(mouseX, mouseY - 16);
        for (ModuleSection moduleSection : this.moduleSectionList) {
            if (this.currentSlot == moduleSection.getMainSlot() || moduleSection.getListToRender().contains(this.currentSlot)) {
                this.currentSelectedSection = moduleSection;
                break;
            }
        }
        if (GunItemRender.instance.isWatchingGun()) {
            if (Minecraft.getMinecraft().currentScreen != this.guiModules) {
                Minecraft.getMinecraft().displayGuiScreen(this.guiModules);
            }
        } else if (Minecraft.getMinecraft().currentScreen == this.guiModules) {
            this.stopSelecting(null);
            Minecraft.getMinecraft().displayGuiScreen(null);
        }
    }

    private boolean checkSlot(SlotInfo slotInfo, float x, float y) {
        return x > slotInfo.getPosX() && y > slotInfo.getPosY() && x < slotInfo.getPosX() + 16 && y < slotInfo.getPosY() + 16;
    }

    private SlotInfo getMouseSlot(float x, float y) {
        for (ModuleSection moduleSection : this.moduleSectionList) {
            SlotInfo slotInfo = null;
            if (this.checkSlot(moduleSection.getMainSlot(), x, y)) {
                slotInfo = moduleSection.getMainSlot();
            } else {
                for (SlotInfo slotInfo1 : moduleSection.getListToRender()) {
                    if (this.checkSlot(slotInfo1, x, y)) {
                        slotInfo = slotInfo1;
                    }
                }
            }
            if (slotInfo != null) {
                this.currentSelectedSection = moduleSection;
                return slotInfo;
            }
        }
        return null;
    }

    @Override
    public boolean isCurrentSlotIsEmpty() {
        if (this.currentSelectedSection != null && this.currentSlot == this.currentSelectedSection.getMainSlot()) {
            return this.currentSelectedSection.isAlwaysEmpty();
        }
        return false;
    }

    @Override
    public ModuleSection getCurrentModuleSection() {
        return this.currentSelectedSection;
    }

    @Override
    public SlotInfo getCurrentSlot() {
        return this.currentSlot;
    }

    public void startSelecting(ItemStack stack) {

    }

    public void stopSelecting(ItemStack stack) {
        this.currentSlot = null;
    }
}
