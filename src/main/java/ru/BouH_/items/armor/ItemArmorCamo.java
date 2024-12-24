package ru.BouH_.items.armor;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import ru.BouH_.Main;

public class ItemArmorCamo extends net.minecraft.item.ItemArmor {
    private final int renderId;
    private final CamoType camoType;

    public ItemArmorCamo(String name, ArmorMaterial mat, int render, CamoType camoType, int id) {
        super(mat, render, id);
        this.renderId = render;
        this.camoType = camoType;
        this.setUnlocalizedName(name);
    }

    public CamoType getCamoType() {
        return this.camoType;
    }

    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        String s = this.getUnlocalizedName();
        int i = s.indexOf("_");
        if (i == -1) {
            return Main.MODID + ":textures/armor/" + s.substring(5) + "_" + renderId + ".png";
        }
        return Main.MODID + ":textures/armor/" + s.substring(5, i) + "_" + renderId + ".png";
    }
}
