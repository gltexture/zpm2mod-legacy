package ru.BouH_.network.packets.gun.modifiers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.BouH_.items.gun.base.AGunBase;
import ru.BouH_.items.gun.modules.base.EnumModule;
import ru.BouH_.items.gun.modules.base.ItemModule;
import ru.BouH_.network.SimplePacket;

public class PacketSetModule extends SimplePacket {
    public PacketSetModule() {

    }


    public PacketSetModule(int modId) {
        buf().writeInt(modId);
    }

    @Override
    public void server(EntityPlayerMP player) {
        int modId = buf().readInt();
        ItemStack stack = player.getHeldItem();
        if (stack != null && stack.hasTagCompound() && stack.getItem() instanceof AGunBase) {
            AGunBase aGunBase = ((AGunBase) stack.getItem());
            if (this.consumeInventoryModifier(player, ItemModule.getModById(modId))) {
                EnumModule enumModifier = ItemModule.getModById(modId).getEnumModule();
                if (enumModifier == EnumModule.SCOPE) {
                    aGunBase.setScope(stack, modId);
                } else if (enumModifier == EnumModule.BARREL) {
                    aGunBase.setBarrel(stack, modId);
                } else if (enumModifier == EnumModule.UNDERBARREL) {
                    aGunBase.setUnderBarrel(stack, modId);
                }
            }
        }
    }

    private boolean consumeInventoryModifier(EntityPlayer player, Item item) {
        int i = this.getInventorySlotContainItem(player.inventory, item);
        if (i < 0) {
            return false;
        } else {
            ItemStack stack1 = player.inventory.mainInventory[i];
            AGunBase aGunBase = (AGunBase) player.getHeldItem().getItem();
            if (--stack1.stackSize <= 0) {
                player.inventory.setInventorySlotContents(i, null);
            }
            aGunBase.onModuleAdded(player, player.getHeldItem(), stack1);
            return true;
        }
    }

    private int getInventorySlotContainItem(InventoryPlayer inventoryPlayer, Item itemIn) {
        for (int i = 0; i < inventoryPlayer.mainInventory.length; ++i) {
            if (inventoryPlayer.mainInventory[i] != null && inventoryPlayer.mainInventory[i].getItem() == itemIn) {
                return i;
            }
        }
        return -1;
    }
}
