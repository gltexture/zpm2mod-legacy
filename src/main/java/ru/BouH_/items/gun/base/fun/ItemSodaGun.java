package ru.BouH_.items.gun.base.fun;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;
import ru.BouH_.items.gun.ItemShotgunNomag;
import ru.BouH_.items.gun.ammo.AAmmo;

public class ItemSodaGun extends ItemShotgunNomag {
    public ItemSodaGun(String name, String shootSound, String reloadSound, int ammo, int cdReload, int damage, int cdShoot, int maxDistance, int effectiveDistance, float recoilVertical, float recoilHorizontal, float inaccuracy, float inaccuracyInAim, float addInaccuracyStep, float maxAddInaccuracy, float jamChance, boolean shouldDropShell, boolean isAutomatic, AAmmo ammoItem) {
        super(name, shootSound, reloadSound, ammo, cdReload, damage, cdShoot, maxDistance, effectiveDistance, recoilVertical, recoilHorizontal, inaccuracy, inaccuracyInAim, addInaccuracyStep, maxAddInaccuracy, jamChance, shouldDropShell, isAutomatic, ammoItem);
    }

    @SideOnly(Side.CLIENT)
    public boolean addTranslateMuzzle1st(EntityPlayer player, ItemStack stack) {
        GL11.glColor4f(0.8f, 0.0f, 1.0f, 1.0f);
        return super.addTranslateMuzzle1st(player, stack);
    }

    @SideOnly(Side.CLIENT)
    public boolean addTranslateMuzzle3d(EntityPlayer player, ItemStack stack) {
        GL11.glColor4f(0.8f, 0.0f, 1.0f, 1.0f);
        return super.addTranslateMuzzle3d(player, stack);
    }
}
