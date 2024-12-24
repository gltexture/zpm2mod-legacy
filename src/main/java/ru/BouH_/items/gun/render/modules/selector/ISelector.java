package ru.BouH_.items.gun.render.modules.selector;

import net.minecraft.item.ItemStack;
import ru.BouH_.items.gun.render.slot.ModuleSection;
import ru.BouH_.items.gun.render.slot.SlotInfo;

public interface ISelector {
    SlotInfo getCurrentSlot();

    void startSelecting(ItemStack stack);

    void stopSelecting(ItemStack stack);

    void update();

    boolean isCurrentSlotIsEmpty();

    ModuleSection getCurrentModuleSection();
}
