package ru.BouH_.gameplay.client;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import ru.BouH_.Main;
import ru.BouH_.utils.TraceUtils;

public class ItemChecker {
    public static int selectionTicks;
    public static EntityItem selectedItem;

    @SideOnly(Side.CLIENT)
    public static void updateItemPickingUp() {
        if (Main.settingsZp.pickUp_F.isFlag()) {
            Entity entity = TraceUtils.getMouseOver(EntityItem.class, 1.0f, 2.0f, 2.5f);
            if (entity != null) {
                EntityItem entityItem = (EntityItem) entity;
                if (entityItem.ticksExisted > 20) {
                    ItemChecker.selectedItem = entityItem;
                    ItemChecker.selectionTicks = 20;
                }
            } else {
                if (ItemChecker.selectionTicks > 0) {
                    ItemChecker.selectionTicks--;
                } else {
                    ItemChecker.selectedItem = null;
                }
            }
        } else {
            ItemChecker.selectedItem = null;
            ItemChecker.selectionTicks = 0;
        }
    }
}
