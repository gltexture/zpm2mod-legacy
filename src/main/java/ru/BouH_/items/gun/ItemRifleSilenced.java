package ru.BouH_.items.gun;

import net.minecraft.item.ItemStack;
import ru.BouH_.items.gun.ammo.AAmmo;

public class ItemRifleSilenced extends ItemRifle {
    public ItemRifleSilenced(String name, String shootSound, String reloadSound, int ammo, int cdReload, int damage, int cdShoot, int maxDistance, int effectiveDistance, float recoilVertical, float recoilHorizontal, float inaccuracy, float inaccuracyInAim, float addInaccuracyStep, float maxAddInaccuracy, float jamChance, boolean shouldDropShell, boolean isAutomatic, AAmmo ammoItem) {
        super(name, shootSound, reloadSound, ammo, cdReload, damage, cdShoot, maxDistance, effectiveDistance, recoilVertical, recoilHorizontal, inaccuracy, inaccuracyInAim, addInaccuracyStep, maxAddInaccuracy, jamChance, shouldDropShell, isAutomatic, ammoItem);
    }

    public boolean isSilenced(ItemStack stack) {
        return true;
    }
}
