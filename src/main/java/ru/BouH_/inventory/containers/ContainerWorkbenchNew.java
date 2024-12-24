package ru.BouH_.inventory.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ContainerWorkbench;
import net.minecraft.inventory.IInventory;
import net.minecraft.world.World;
import ru.BouH_.recipe_master.RecipeMaster;

public class ContainerWorkbenchNew extends ContainerWorkbench {
    public final EntityPlayer entityPlayer;

    public ContainerWorkbenchNew(InventoryPlayer p_i1808_1_, World p_i1808_2_, int p_i1808_3_, int p_i1808_4_, int p_i1808_5_) {
        super(p_i1808_1_, p_i1808_2_, p_i1808_3_, p_i1808_4_, p_i1808_5_);
        this.entityPlayer = p_i1808_1_.player;
    }

    public void onCraftMatrixChanged(IInventory p_75130_1_) {
        this.craftResult.setInventorySlotContents(0, RecipeMaster.instance.findMatchingRecipe(this.craftMatrix, this.entityPlayer, this.worldObj));
    }
}