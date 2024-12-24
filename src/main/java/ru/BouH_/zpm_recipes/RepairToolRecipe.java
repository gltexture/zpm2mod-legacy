package ru.BouH_.zpm_recipes;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class RepairToolRecipe {
    @SuppressWarnings("unchecked")
    public static void addRecipe(CraftingManager craft, ItemStack[] stacks, ItemStack output) {
        craft.getRecipeList().add(new RepairToolShapedRecipe(stacks, output));
    }

    public static class RepairToolShapedRecipe implements IRecipe {
        public final ItemStack[] recipeItems;
        public final int recipeWidth;
        public final int recipeHeight;
        private final ItemStack recipeOutput;
        private float percentage;

        public RepairToolShapedRecipe(ItemStack[] stacks, ItemStack output) {
            this.recipeItems = stacks;
            this.recipeOutput = output;
            this.recipeWidth = 2;
            this.recipeHeight = 2;
        }

        public ItemStack getRecipeOutput() {
            return this.recipeOutput;
        }

        public boolean matches(InventoryCrafting p_77569_1_, World p_77569_2_) {
            for (int i = 0; i <= 3 - this.recipeWidth; ++i) {
                for (int j = 0; j <= 3 - this.recipeHeight; ++j) {
                    if (this.checkMatch(p_77569_1_, i, j, true)) {
                        return true;
                    }
                    if (this.checkMatch(p_77569_1_, i, j, false)) {
                        return true;
                    }
                }
            }
            return false;
        }

        private boolean checkMatch(InventoryCrafting p_77573_1_, int p_77573_2_, int p_77573_3_, boolean p_77573_4_) {
            this.percentage = 0;
            int i2 = 0;
            float f1 = 0;
            for (int k = 0; k < 3; ++k) {
                for (int l = 0; l < 3; ++l) {
                    int i1 = k - p_77573_2_;
                    int j1 = l - p_77573_3_;
                    ItemStack itemstack = null;
                    if (i1 >= 0 && j1 >= 0 && i1 < this.recipeWidth && j1 < this.recipeHeight) {
                        if (p_77573_4_) {
                            itemstack = this.recipeItems[this.recipeWidth - i1 - 1 + j1 * this.recipeWidth];
                        } else {
                            itemstack = this.recipeItems[i1 + j1 * this.recipeWidth];
                        }
                    }
                    ItemStack itemStack1 = p_77573_1_.getStackInRowAndColumn(k, l);
                    if (itemStack1 != null || itemstack != null) {
                        if (itemStack1 == null || itemstack == null) {
                            return false;
                        }
                        if (itemstack.getItem() != itemStack1.getItem()) {
                            return false;
                        }
                    }
                    if (itemStack1 != null) {
                        if (itemStack1.getItem().getMaxDurability() > 0) {
                            f1 += (float) itemStack1.getCurrentDurability() / itemStack1.getMaxDurability();
                            i2 += 1;
                        }
                    }
                }
            }
            if (i2 > 0) {
                this.percentage = f1 / i2;
            }
            return true;
        }

        public ItemStack getCraftingResult(InventoryCrafting p_77572_1_) {
            ItemStack newStack = new ItemStack(this.recipeOutput.getItem(), 1, this.recipeOutput.getMetadata());
            newStack.setMetadata((int) (newStack.getMaxDurability() * this.percentage));
            return newStack;
        }

        public int getRecipeSize() {
            return 4;
        }
    }
}
