package ru.BouH_.items.gun.render.modules.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;
import ru.BouH_.items.gun.render.GunItemRender;
import ru.BouH_.items.gun.render.modules.selector.SelectorMouse;
import ru.BouH_.proxy.ClientProxy;

public class GuiModules extends GuiScreen {
    private final SelectorMouse selectorMouse;
    private float mouseX;
    private float mouseY;

    public GuiModules(SelectorMouse selectorMouse) {
        this.selectorMouse = selectorMouse;
    }

    public void initGui() {
        super.initGui();
    }

    public boolean doesGuiPauseGame() {
        return false;
    }

    public float mouseX() {
        return this.mouseX;
    }

    public float mouseY() {
        return this.mouseY;
    }

    protected void keyTyped(char typedChar, int keyCode) {
        if (keyCode == Keyboard.KEY_ESCAPE || keyCode == ClientProxy.keySeeGun.getKeyCode()) {
            GunItemRender.instance.stopGunWatching();
            this.mc.setIngameFocus();
        } else if (keyCode == ClientProxy.keySwitch.getKeyCode()) {
            GunItemRender.instance.trySwitchFireMode();
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton == 0) {
            ItemStack stack = Minecraft.getMinecraft().thePlayer.getHeldItem();
            if (stack != null) {
                GunItemRender.instance.performSlotAction(stack, this.selectorMouse.currentSlot);
            }
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.mouseX = (float) mouseX;
        this.mouseY = (float) mouseY;
    }
}
