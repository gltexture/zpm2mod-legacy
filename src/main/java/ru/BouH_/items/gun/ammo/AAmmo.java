package ru.BouH_.items.gun.ammo;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.BouH_.items.gun.base.AGunBase;
import ru.BouH_.items.gun.render.Shell;

public abstract class AAmmo {
    private final float volume;
    private final Shell shell;
    private final Item item;

    public AAmmo(Item item, Shell shell, float volume) {
        this.item = item;
        this.volume = volume;
        this.shell = shell;
    }

    public abstract void generateSmoke(EntityPlayer player, ItemStack stack, AGunBase aGunBase, int count);

    public float getVolume() {
        return Math.min(this.volume, 16);
    }

    public Shell getShell() {
        return this.shell;
    }

    public Item getItem() {
        return this.item;
    }
}
