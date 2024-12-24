package ru.BouH_.items.gun.modules;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import ru.BouH_.Main;
import ru.BouH_.entity.ieep.PlayerMiscData;
import ru.BouH_.items.gun.modules.base.EnumModule;
import ru.BouH_.items.gun.modules.base.ItemModule;

public class ItemBipod extends ItemModule {
    @SideOnly(Side.CLIENT)
    private IIcon getIconBipodActive;

    public ItemBipod(String name) {
        super(name, EnumModule.UNDERBARREL);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister register) {
        super.registerIcons(register);
        this.getIconBipodActive = register.registerIcon(Main.MODID + ":mod/bipod_active");
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconBipodActive() {
        return this.getIconBipodActive;
    }

    public float getModifiedVerticalRecoil(EntityPlayer player, boolean inZoom) {
        return PlayerMiscData.getPlayerData(player).isLying() ? this.recoilVerticalModifier : 0.0f;
    }

    public float getModifiedHorizontalRecoil(EntityPlayer player, boolean inZoom) {
        return PlayerMiscData.getPlayerData(player).isLying() ? this.recoilHorizontalModifier : 0.0f;
    }

    public float getModifiedInaccuracy(EntityPlayer player, boolean inZoom) {
        return PlayerMiscData.getPlayerData(player).isLying() ? this.inaccuracyModifier : 0.0f;
    }

    public float getModifiedStability(EntityPlayer player, boolean inZoom) {
        return PlayerMiscData.getPlayerData(player).isLying() ? this.stabilityModifier : 0.0f;
    }
}
