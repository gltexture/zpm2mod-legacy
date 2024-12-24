package ru.BouH_.zpm_recipes;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.init.ItemsZp;
import ru.BouH_.items.gun.base.AGunBase;

import java.util.ArrayList;

public class LubricantGunRecipe {

    @SuppressWarnings("unchecked")
    public static void addRepairRecipe(CraftingManager craft, ItemStack toRepair) {
        craft.getRecipeList().add(new LubricantShapelessRecipe(toRepair, new ItemStack(ItemsZp.lubricant)));
    }

    public static class LubricantShapelessRecipe implements IRecipe {
        public final ItemStack toRepairItem;
        public final ItemStack repairToolItem;
        private ItemStack recipeOutput;

        public LubricantShapelessRecipe(ItemStack toRepair, ItemStack repairTool) {
            this.toRepairItem = toRepair;
            this.repairToolItem = repairTool;
        }

        public ItemStack getRecipeOutput() {
            return this.recipeOutput;
        }

        public boolean matches(InventoryCrafting p_77569_1_, World p_77569_2_) {
            ArrayList<ItemStack> arraylist = new ArrayList<>();
            arraylist.add(this.toRepairItem);
            arraylist.add(this.repairToolItem);
            ItemStack stack = null;
            for (int i = 0; i < 3; ++i) {
                for (int j = 0; j < 3; ++j) {
                    ItemStack itemstack = p_77569_1_.getStackInRowAndColumn(j, i);
                    if (itemstack != null) {
                        boolean flag = false;
                        for (ItemStack o : arraylist) {
                            if (itemstack.getItem() == o.getItem()) {
                                flag = true;
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
            AGunBase aGunBase = (AGunBase) newStack.getItem();
            if (this.recipeOutput.hasTagCompound()) {
                newStack.setTagCompound((NBTTagCompound) this.recipeOutput.getTagCompound().copy());
            } else {
                newStack.setTagInfo(Main.MODID, new NBTTagCompound());
            }
            newStack.getTagCompound().getCompoundTag(Main.MODID).setInteger("reserveShots", (int) Math.min(aGunBase.getMaxAmmo() * 3.0f, 100));
            return newStack;
        }

        public int getRecipeSize() {
            return 2;
        }
    }
}
