package ru.BouH_.inventory.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerPlayer;
import net.minecraft.inventory.IInventory;
import ru.BouH_.recipe_master.RecipeMaster;

public class ContainerPlayerNew extends ContainerPlayer {
    private final EntityPlayer thePlayer;

    public ContainerPlayerNew(final InventoryPlayer p_i1819_1_, boolean p_i1819_2_, EntityPlayer p_i1819_3_) {
        super(p_i1819_1_, p_i1819_2_, p_i1819_3_);
        this.thePlayer = p_i1819_3_;
    }

    public void onCraftMatrixChanged(IInventory p_75130_1_) {
        if (this.thePlayer != null) {
            this.craftResult.setInventorySlotContents(0, RecipeMaster.instance.findMatchingRecipe(this.craftMatrix, this.thePlayer, this.thePlayer.worldObj));
        }
    }
}