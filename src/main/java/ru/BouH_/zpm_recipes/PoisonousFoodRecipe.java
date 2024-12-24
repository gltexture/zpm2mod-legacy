package ru.BouH_.zpm_recipes;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.init.ItemsZp;

import java.util.ArrayList;
import java.util.List;

public class PoisonousFoodRecipe {

    @SuppressWarnings("unchecked")
    public static void addPoisonRecipe(CraftingManager craft, ItemStack p_77596_1_) {
        ArrayList<ItemStack> arraylist = new ArrayList<>();

        arraylist.add(p_77596_1_);
        arraylist.add(new ItemStack(ItemsZp.poison));

        craft.getRecipeList().add(new PoisonousFoodShapelessRecipe(arraylist));
    }

    public static class PoisonousFoodShapelessRecipe implements IRecipe {
        public final List<ItemStack> recipeItems;
        private ItemStack recipeOutput;

        public PoisonousFoodShapelessRecipe(List<ItemStack> list) {
            this.recipeItems = list;
        }

        public ItemStack getRecipeOutput() {
            return this.recipeOutput;
        }

        public boolean matches(InventoryCrafting p_77569_1_, World p_77569_2_) {
            ArrayList<ItemStack> arraylist = new ArrayList<>(this.recipeItems);
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
                        if (itemstack.getItem() != ItemsZp.poison) {
                            this.recipeOutput = itemstack;
                        }
                    }
                }
            }
            return arraylist.isEmpty();
        }

        public ItemStack getCraftingResult(InventoryCrafting p_77572_1_) {
            ItemStack newStack = new ItemStack(this.recipeOutput.getItem(), 1, this.recipeOutput.getMetadata());
            if (this.recipeOutput.hasTagCompound()) {
                newStack.setTagCompound((NBTTagCompound) this.recipeOutput.getTagCompound().copy());
            } else {
                newStack.setTagInfo(Main.MODID, new NBTTagCompound());
            }
            newStack.getTagCompound().getCompoundTag(Main.MODID).setByte("poisonous", (byte) 1);
            return newStack;
        }

        public int getRecipeSize() {
            return this.recipeItems.size();
        }
    }
}
