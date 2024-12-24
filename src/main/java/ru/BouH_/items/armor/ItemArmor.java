package ru.BouH_.items.armor;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import ru.BouH_.Main;

public class ItemArmor extends net.minecraft.item.ItemArmor {
    private final int renderId;

    public ItemArmor(String name, ArmorMaterial mat, int render, int id) {
        super(mat, render, id);
        this.renderId = render;
        this.setUnlocalizedName(name);
    }

    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        String s = this.getUnlocalizedName();
        int t = s.indexOf("_");
        if (t < 0) {
            t = s.length();
        }
        return Main.MODID + ":textures/armor/" + s.substring(0, t).substring(5) + "_" + renderId + ".png";
    }
}
