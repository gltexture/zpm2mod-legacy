package ru.BouH_.zpm_recipes;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.items.gun.base.AGunBase;

import java.util.ArrayList;

public class WeaponRepairRecipe {

    @SuppressWarnings("unchecked")
    public static void addRepairRecipe(CraftingManager craft, ItemStack toRepair, ItemStack repairTool) {
        craft.getRecipeList().add(new RepairShapelessRecipe(toRepair, repairTool));
    }

    public static class RepairShapelessRecipe implements IRecipe {
        public final ItemStack toRepairItem;
        public final ItemStack repairToolItem;
        private ItemStack recipeOutput;
        private float toRepair;

        public RepairShapelessRecipe(ItemStack toRepair, ItemStack repairTool) {
            this.toRepairItem = toRepair;
            this.repairToolItem = repairTool;
            this.toRepair = 1.0f;
        }

        public ItemStack getRecipeOutput() {
            return this.recipeOutput;
        }

        public boolean matches(InventoryCrafting p_77569_1_, World p_77569_2_) {
            ArrayList<ItemStack> arraylist = new ArrayList<>();
            arraylist.add(this.toRepairItem);
            arraylist.add(this.repairToolItem);
            this.toRepair = 1.0f;
            for (int i = 0; i < 3; ++i) {
                for (int j = 0; j < 3; ++j) {
                    ItemStack itemstack = p_77569_1_.getStackInRowAndColumn(j, i);
                    if (itemstack != null) {
                        boolean flag = false;
                        for (ItemStack o : arraylist) {
                            if (itemstack.getItem() == o.getItem()) {
                                flag = true;
                                if (this.repairToolItem.getMaxDurability() > 0) {
                                    if (itemstack.getItem() == this.repairToolItem.getItem()) {
                                        this.toRepair = 1.0f - (float) itemstack.getCurrentDurability() / itemstack.getMaxDurability();
                                    }
                                }
                                arraylist.remove(o);
                                break;
                            }
                        }
                        if (!flag) {
                            return false;
                        }
                        if (itemstack.getItem() instanceof AGunBase) {
                            this.recipeOutput = itemstack;
                        }
                    }
                }
            }
            return arraylist.isEmpty();
        }

        public ItemStack getCraftingResult(InventoryCrafting p_77572_1_) {
            ItemStack newStack = new ItemStack(this.recipeOutput.getItem(), this.recipeOutput.stackSize, this.recipeOutput.getMetadata());
            if (this.recipeOutput.hasTagCompound()) {
                newStack.setTagCompound((NBTTagCompound) this.recipeOutput.getTagCompound().copy());
            } else {
                newStack.setTagInfo(Main.MODID, new NBTTagCompound());
            }
            newStack.setMetadata(Math.max((int) (this.recipeOutput.getCurrentDurability() - (this.recipeOutput.getMaxDurability() * 0.7f) * this.toRepair), 0));
            newStack.getTagCompound().getCompoundTag(Main.MODID).setBoolean("isJammed", false);
            return newStack;
        }

        public int getRecipeSize() {
            return 2;
        }
    }
}
