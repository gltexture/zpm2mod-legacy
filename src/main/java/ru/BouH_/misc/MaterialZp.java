package ru.BouH_.misc;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

public class MaterialZp extends Material {
    public static final Material wire = (new MaterialZp(MapColor.ironColor)).setRequiresTool();
    public static final Material stakes = (new MaterialZp(MapColor.ironColor)).setRequiresTool();

    public MaterialZp(MapColor p_i2116_1_) {
        super(p_i2116_1_);
    }
}
