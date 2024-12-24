package ru.BouH_.render.tile;

import net.minecraft.tileentity.TileEntityChest;

public class TileFakeChest extends TileEntityChest {
    private int type;

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
