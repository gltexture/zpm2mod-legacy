package ru.BouH_.items.gun.events;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Mouse;
import ru.BouH_.gameplay.client.ItemChecker;
import ru.BouH_.items.gun.base.AGunBase;
import ru.BouH_.items.gun.base.EnumFireModes;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.packets.gun.PacketReloadInterrupt;
import ru.BouH_.network.packets.misc.PacketPickUp;
import ru.BouH_.proxy.ClientProxy;

public class ClientGunEvent {
    public static ClientGunEvent instance = new ClientGunEvent();
    public boolean isDown;
    public int currentItem;

    @SubscribeEvent
    public void onPress(InputEvent.KeyInputEvent ev) {
        KeyBinding keyReload = ClientProxy.keyReload;
        KeyBinding keyUnReload = ClientProxy.keyUnReload;
        KeyBinding keyF = ClientProxy.keyPick;
        ItemStack stack = Minecraft.getMinecraft().thePlayer.getHeldItem();
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (stack != null && stack.hasTagCompound() && stack.getItem() instanceof AGunBase) {
            boolean keyPressed1 = keyUnReload.isPressed();
            boolean keyPressed2 = keyReload.isPressed();
            if (keyPressed2 || keyPressed1) {
                AGunBase aGunBase = (AGunBase) stack.getItem();
                if (this.checkGunToInterrupt(player, stack)) {
                    aGunBase.reloadPacket(player.getHeldItem(), player, keyPressed1);
                }
            }
        }
        EntityItem entityItem = ItemChecker.selectedItem;
        if (keyF.isPressed() && entityItem != null) {
            NetworkHandler.NETWORK.sendToServer(new PacketPickUp(entityItem.getEntityId()));
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent ev) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.thePlayer;
        if (ev.phase == TickEvent.Phase.START) {
            if (player != null) {
                ItemStack stack = player.getHeldItem();
                if (player.getEntityData().getInteger("cdShoot") > 0) {
                    player.getEntityData().setInteger("cdShoot", player.getEntityData().getInteger("cdShoot") - 1);
                }
                if (this.currentItem != player.inventory.currentItem) {
                    this.currentItem = player.inventory.currentItem;
                    this.isDown = true;
                }
                if (mc.currentScreen == null) {
                    if (stack != null && stack.hasTagCompound() && stack.getItem() instanceof AGunBase) {
                        AGunBase aGunBase = ((AGunBase) stack.getItem());
                        if (Mouse.isButtonDown(0) && !this.isDown) {
                            if (aGunBase.getCurrentFireMode(stack) != EnumFireModes.SAFE) {
                                aGunBase.shootPacket(stack, player);
                            }
                            if (aGunBase.getCurrentFireMode(stack) != EnumFireModes.AUTO) {
                                this.isDown = true;
                            }
                        }
                    }
                    if (!Mouse.isButtonDown(0) && this.isDown) {
                        this.isDown = false;
                    }
                } else {
                    this.isDown = true;
                }
            }
        }
    }

    public boolean checkGunToInterrupt(EntityPlayer player, ItemStack stack) {
        AGunBase aGunBase = ((AGunBase) stack.getItem());
        if (aGunBase.canInterruptReloading(player, stack)) {
            NetworkHandler.NETWORK.sendToServer(new PacketReloadInterrupt());
            player.getEntityData().setBoolean("interruptReloading", true);
            return false;
        }
        return true;
    }
}
