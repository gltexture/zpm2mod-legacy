package ru.BouH_.items.gun.render.modules.selector;

import net.minecraft.item.ItemStack;
import ru.BouH_.items.gun.render.slot.ModuleSection;
import ru.BouH_.items.gun.render.slot.SlotInfo;

import java.util.List;

public class SelectorKeyboard implements ISelector {
    private final List<ModuleSection> moduleSectionList;
    public SlotInfo currentSlot;
    public int currentY;
    public int currentX;
    public int horizontalSize;
    public int verticalSize;
    private ModuleSection currentSelectedSection = null;

    public SelectorKeyboard(List<ModuleSection> moduleSectionList) {
        this.moduleSectionList = moduleSectionList;
        this.verticalSize = this.moduleSectionList.size() - 1;
    }

    public void update() {
        this.currentSelectedSection = this.moduleSectionList.get(this.currentY);
        SlotInfo itemModule = this.currentSelectedSection.getMainSlot();
        List<SlotInfo> renderList = this.currentSelectedSection.getListToRender();
        if (!renderList.isEmpty()) {
            this.horizontalSize = renderList.size();
            SlotInfo slotInfo = this.checkItem(renderList);
            if (slotInfo != null) {
                this.currentSlot = slotInfo;
            } else {
                this.currentSlot = itemModule;
            }
        } else {
            this.horizontalSize = 0;
            this.currentSlot = itemModule;
        }
    }

    @Override
    public boolean isCurrentSlotIsEmpty() {
        return this.currentSelectedSection.isAlwaysEmpty();
    }

    @Override
    public ModuleSection getCurrentModuleSection() {
        return this.currentSelectedSection;
    }

    public int getCurrentX() {
        return this.currentX;
    }

    public void setCurrentX(int currentX) {
        this.currentX = currentX;
    }

    public int getCurrentY() {
        return this.currentY;
    }

    public void setCurrentY(int currentY) {
        this.currentY = currentY;
    }

    public void changeSelection(int x, int y) {
        if (y != 0) {
            this.currentX = 0;
            this.currentY -= y;
        }
        this.currentX += x;

        if (this.currentX < 0) {
            this.currentX = this.horizontalSize;
        } else if (this.currentX > this.horizontalSize) {
            this.currentX = 0;
        }

        if (this.currentY < 0) {
            this.currentY = this.verticalSize;
        } else if (this.currentY > this.verticalSize) {
            this.currentY = 0;
        }
    }

    @Override
    public SlotInfo getCurrentSlot() {
        return this.currentSlot;
    }

    public void startSelecting(ItemStack stack) {
        this.currentX = 0;
        this.firstNormalSlot();
    }

    public void stopSelecting(ItemStack stack) {
        this.currentX = 0;
        this.currentY = 0;
        this.currentSlot = null;
    }

    public SlotInfo checkItem(List<SlotInfo> slotInfo) {
        SlotInfo itemModifier = null;
        int id = this.currentX;
        if (id > 0) {
            if (id <= slotInfo.size()) {
                itemModifier = slotInfo.get(id - 1);
            } else {
                this.stopSelecting(null);
            }
        }
        return itemModifier;
    }

    public void firstNormalSlot() {
        this.currentY = 0;
        for (ModuleSection moduleSection : this.moduleSectionList) {
            if (moduleSection.isAlwaysEmpty()) {
                this.changeSelection(0, -1);
            } else {
                break;
            }
        }
    }
}
