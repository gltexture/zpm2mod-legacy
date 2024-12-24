package ru.BouH_.items.gun.render.slot;

import net.minecraft.item.ItemStack;
import ru.BouH_.items.gun.base.AGunBase;
import ru.BouH_.items.gun.modules.base.EnumModule;
import ru.BouH_.items.gun.modules.base.ItemModule;
import ru.BouH_.items.gun.modules.base.ModuleInfo;

import java.util.ArrayList;
import java.util.List;

public class ModuleSection {
    private final EnumModule enumModifier;
    private final SlotInfo mainSlot = new SlotInfo(true);
    private List<SlotInfo> listToRender;
    private boolean isAlwaysEmpty;

    public ModuleSection(EnumModule enumModifier) {
        this.enumModifier = enumModifier;
        this.mainSlot.setEnumModule(enumModifier);
    }

    public void updateRenderList(ItemStack stack) {
        AGunBase aGunBase = (AGunBase) stack.getItem();
        EnumModule enumModifier = this.getEnumModifier();
        ModuleInfo mod = aGunBase.getCurrentModule(stack, this.getEnumModifier());
        this.listToRender = this.updateListToRender(aGunBase.getListToRender(enumModifier));
        this.isAlwaysEmpty = !aGunBase.getSupportedModifiers().contains(this.enumModifier);
        if (mod != null) {
            this.mainSlot.setItemModule(mod.getMod());
        } else {
            this.mainSlot.setItemModule(null);
        }
    }

    public void setPos(int x, int y) {
        this.mainSlot.setPosX(x);
        this.mainSlot.setPosY(y);
    }

    public SlotInfo getMainSlot() {
        return this.mainSlot;
    }

    public EnumModule getEnumModifier() {
        return this.enumModifier;
    }

    public boolean isAlwaysEmpty() {
        return this.isAlwaysEmpty;
    }

    public List<SlotInfo> getListToRender() {
        return this.listToRender;
    }

    public int getMaxX() {
        List<SlotInfo> list = this.getListToRender();
        return list.isEmpty() ? this.getMainSlot().getPosX() : list.get(list.size() - 1).getPosX();
    }

    private List<SlotInfo> updateListToRender(List<ItemModule> rawList) {
        List<SlotInfo> slotInfoList = new ArrayList<>();
        if (this.mainSlot.getItemModule() == null) {
            for (int i = 0; i < rawList.size(); i++) {
                ItemModule itemModule = rawList.get(i);
                SlotInfo slotInfo = new SlotInfo(false, itemModule.getEnumModule(), itemModule, this.mainSlot.getPosX() + (i + 1) * 18, this.mainSlot.getPosY());
                slotInfoList.add(slotInfo);
            }
        }
        return slotInfoList;
    }
}
