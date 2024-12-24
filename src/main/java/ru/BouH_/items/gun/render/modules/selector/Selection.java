package ru.BouH_.items.gun.render.modules.selector;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import ru.BouH_.items.gun.base.AGunBase;
import ru.BouH_.items.gun.modules.base.EnumModule;
import ru.BouH_.items.gun.modules.base.ItemModule;
import ru.BouH_.items.gun.render.slot.ModuleSection;
import ru.BouH_.items.gun.render.slot.SlotInfo;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.packets.gun.modifiers.PacketClearModule;
import ru.BouH_.network.packets.gun.modifiers.PacketSetModule;

import java.util.ArrayList;
import java.util.List;

public class Selection {
    private final SelectorKeyboard keyboardSelector;
    private final SelectorMouse mouseSelector;
    public List<ModuleSection> moduleSectionList = new ArrayList<>();
    public SlotInfo currentSlotSelected;
    private boolean isOnEmptySlot;
    private ISelector currentSelector;

    public Selection() {
        ModuleSection scopeSlot = new ModuleSection(EnumModule.SCOPE);
        ModuleSection barrelSlot = new ModuleSection(EnumModule.BARREL);
        ModuleSection underBarrelSlot = new ModuleSection(EnumModule.UNDERBARREL);
        this.moduleSectionList.add(scopeSlot);
        this.moduleSectionList.add(barrelSlot);
        this.moduleSectionList.add(underBarrelSlot);
        this.keyboardSelector = new SelectorKeyboard(this.moduleSectionList);
        this.mouseSelector = new SelectorMouse(this.moduleSectionList);
        this.currentSelector = this.mouseSelector;
    }

    public void setRelativePosition(int x, int y) {
        for (int i = 0; i < this.moduleSectionList.size(); i++) {
            this.moduleSectionList.get(i).setPos(6, y + i * 40);
        }
    }

    private void updateSlots(ItemStack stack) {
        for (ModuleSection moduleSection : moduleSectionList) {
            moduleSection.updateRenderList(stack);
        }
    }

    public void updateSelecting(ItemStack stack) {
        this.updateSlots(stack);
        this.getCurrentSelector().update();
        this.currentSlotSelected = this.currentSelector.getCurrentSlot();
        this.isOnEmptySlot = this.currentSelector.isCurrentSlotIsEmpty();
    }

    public ItemModule getSelectedModule() {
        SlotInfo slotInfo = this.currentSelector.getCurrentSlot();
        if (slotInfo == null) {
            return null;
        }
        return slotInfo.getItemModule();
    }

    public void startSelecting(ItemStack stack) {
        this.updateSelecting(stack);
        this.currentSelector.startSelecting(stack);
    }

    public void stopSelecting(ItemStack stack) {
        this.currentSelector.stopSelecting(stack);
    }

    public EnumModule getCurrentSlotType() {
        return this.currentSlotSelected.getEnumModule();
    }

    public void setSelector(boolean isKeyboard) {
        if (isKeyboard) {
            this.currentSelector = this.getKeyboardSelector();
        } else {
            this.currentSelector = this.getMouseSelector();
        }
    }

    public boolean isKeyboard() {
        return this.getCurrentSelector() == this.getKeyboardSelector();
    }

    public boolean isMouse() {
        return this.getCurrentSelector() == this.getMouseSelector();
    }

    public void clearModifier(ItemStack stack, EnumModule enumModifier) {
        NetworkHandler.NETWORK.sendToServer(new PacketClearModule(enumModifier));
        ((AGunBase) stack.getItem()).clearModifier(Minecraft.getMinecraft().thePlayer, stack, enumModifier);
    }

    public void setModifier(ItemStack stack, int id) {
        NetworkHandler.NETWORK.sendToServer(new PacketSetModule(id));
        ((AGunBase) stack.getItem()).setModifier(stack, id);
    }

    public ISelector getCurrentSelector() {
        return this.currentSelector;
    }

    public SelectorKeyboard getKeyboardSelector() {
        return this.keyboardSelector;
    }

    public SelectorMouse getMouseSelector() {
        return this.mouseSelector;
    }

    public boolean isOnEmptySlot() {
        return this.isOnEmptySlot;
    }
}
