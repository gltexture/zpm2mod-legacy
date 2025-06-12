package ru.BouH_.recipe_master;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.world.World;
import ru.BouH_.ConfigZp;
import ru.BouH_.recipe_master.data.RecipeInfo;
import ru.BouH_.recipe_master.data.container.ARecipeContainer;
import ru.BouH_.recipe_master.data.container.CommonRecipeContainer;
import ru.BouH_.recipe_master.data.container.FurnaceRecipeContainer;
import ru.BouH_.recipe_master.data.container.ShapelessRecipeContainer;
import ru.BouH_.render.gui.GuiInGameMenuZp;
import ru.BouH_.skills.SkillManager;
import ru.BouH_.skills.SkillZp;
import ru.BouH_.zpm_recipes.RepairToolRecipe;

import java.util.*;

public class RecipeMaster {
    public static RecipeMaster instance = new RecipeMaster();
    private final Map<ItemStack, RecipeInfo> listMap;
    private final Map<ItemStack, SpecialRecipePair> specialMap;

    public RecipeMaster() {
        this.listMap = new HashMap<>();
        this.specialMap = new HashMap<>();
    }

    @SuppressWarnings("all")
    public void formRecipeMap(List<IRecipe> list) {
        for (IRecipe iRecipe : list) {
            this.addRecipe(iRecipe);
        }
    }

    @SuppressWarnings("all")
    public void addRecipe(IRecipe iRecipe) {
        ItemStack result = iRecipe.getRecipeOutput();
        ARecipeContainer aRecipeContainer = null;
        if (result != null) {
            int size = result.stackSize;
            if (iRecipe instanceof RepairToolRecipe.RepairToolShapedRecipe) {
                RepairToolRecipe.RepairToolShapedRecipe repairToolRecipe = (RepairToolRecipe.RepairToolShapedRecipe) iRecipe;
                ArrayList<ItemStack> itemStacks = new ArrayList<>(Arrays.asList(repairToolRecipe.recipeItems));
                aRecipeContainer = new CommonRecipeContainer(itemStacks, size, repairToolRecipe.recipeWidth, repairToolRecipe.recipeHeight);
            } else if (iRecipe instanceof ShapedRecipes) {
                ShapedRecipes shapedRecipes = (ShapedRecipes) iRecipe;
                ArrayList<ItemStack> itemStacks = new ArrayList<>(Arrays.asList(shapedRecipes.recipeItems));
                aRecipeContainer = new CommonRecipeContainer(itemStacks, size, shapedRecipes.recipeWidth, shapedRecipes.recipeHeight);
            } else if (iRecipe instanceof ShapelessRecipes) {
                ShapelessRecipes shapelessRecipes = (ShapelessRecipes) iRecipe;
                ArrayList<ItemStack> itemStacks = new ArrayList<>(shapelessRecipes.recipeItems);
                aRecipeContainer = new ShapelessRecipeContainer(itemStacks, size);
            }
            if (aRecipeContainer == null) {
                System.out.println(result.getItem().getUnlocalizedName());
            }
            if (aRecipeContainer != null) {
                RecipeInfo recipeInfo = new RecipeInfo(aRecipeContainer);
                this.addToListMap(result, aRecipeContainer, recipeInfo);
            }
        }
    }

    @SuppressWarnings("all")
    public void formSmeltingMap(Map<ItemStack, ItemStack> map) {
        for (ItemStack itemStack : map.keySet()) {
            ItemStack result = map.get(itemStack);
            ARecipeContainer aRecipeContainer = null;
            if (result != null) {
                int size = result.stackSize;
                ArrayList<ItemStack> arrayList = new ArrayList<>();
                arrayList.add(itemStack);
                aRecipeContainer = new FurnaceRecipeContainer(arrayList, size);
                if (aRecipeContainer != null) {
                    ItemStack itemStack1 = this.checkItemInCollection(result, this.getListMap().keySet());
                    RecipeInfo recipeInfo = new RecipeInfo(aRecipeContainer);
                    this.addToListMap(result, aRecipeContainer, recipeInfo);
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private static boolean isSkillEnabled() {
        return GuiInGameMenuZp.skillsEnabled;
    }

    @SuppressWarnings("all")
    public boolean isRecipeOpened(EntityPlayer entityPlayer, ItemStack result) {
        if (entityPlayer == null || result == null || !this.getSpecialMap().containsKey(result)) {
            return true;
        }
        if (entityPlayer.worldObj.isRemote) {
            if (!RecipeMaster.isSkillEnabled()) {
                return true;
            }
        } else {
            if (!ConfigZp.skillsSystemCrafts) {
                return true;
            }
        }
        SpecialRecipePair specialRecipePair = this.getSpecialMap().get(result);
        return SkillManager.instance.checkSkill(entityPlayer, specialRecipePair.getSkillZp(), specialRecipePair.getLvl());
    }

    public ItemStack findMatchingRecipe(InventoryCrafting p_82787_1_, EntityPlayer entityPlayer, World p_82787_2_) {
        int i = 0;
        ItemStack itemStack = null;
        ItemStack itemStack1 = null;
        int j;

        for (j = 0; j < p_82787_1_.getSizeInventory(); ++j) {
            ItemStack itemStack2 = p_82787_1_.getStackInSlot(j);

            if (itemStack2 != null) {
                if (i == 0) {
                    itemStack = itemStack2;
                }

                if (i == 1) {
                    itemStack1 = itemStack2;
                }

                ++i;
            }
        }

        if (i == 2 && itemStack.getItem() == itemStack1.getItem() && itemStack.stackSize == 1 && itemStack1.stackSize == 1 && itemStack.getItem().isRepairable()) {
            Item item = itemStack.getItem();
            int j1 = item.getMaxDurability() - itemStack.getCurrentDurability();
            int k = item.getMaxDurability() - itemStack1.getCurrentDurability();
            int l = j1 + k + item.getMaxDurability() * 5 / 100;
            int i1 = item.getMaxDurability() - l;

            if (i1 < 0) {
                i1 = 0;
            }

            return new ItemStack(itemStack.getItem(), 1, i1);
        } else {
            for (j = 0; j < CraftingManager.getInstance().getRecipeList().size(); ++j) {
                IRecipe irecipe = (IRecipe) CraftingManager.getInstance().getRecipeList().get(j);

                if (irecipe.matches(p_82787_1_, p_82787_2_)) {
                    return !this.isRecipeOpened(entityPlayer, irecipe.getRecipeOutput()) ? null : irecipe.getCraftingResult(p_82787_1_);
                }
            }
        }
        return null;
    }

    public void addToListMap(ItemStack result, ARecipeContainer aRecipeContainer, RecipeInfo recipeInfo) {
        ItemStack itemStack2 = this.checkItemInCollection(result, this.getListMap().keySet());
        if (itemStack2 == null) {
            this.getListMap().put(result, recipeInfo);
        } else {
            if (!this.getSpecialMap().containsKey(result)) {
                this.getListMap().get(itemStack2).getiRecipeContainerList().add(aRecipeContainer);
            } else {
                ItemStack itemStack3 = this.findMatchSpecialRecipe(result, this.getSpecialMap().get(result));
                if (itemStack3 != null) {
                    this.getListMap().get(itemStack3).getiRecipeContainerList().add(aRecipeContainer);
                } else {
                    this.getListMap().put(result, recipeInfo);
                }
            }
        }
    }

    private ItemStack findMatchSpecialRecipe(ItemStack itemStack, SpecialRecipePair specialRecipePair) {
        for (ItemStack itemStack1 : this.getListMap().keySet()) {
            if (this.checkItems(itemStack, itemStack1) && this.getSpecialMap().get(itemStack1).equalsTo(specialRecipePair)) {
                return itemStack1;
            }
        }
        return null;
    }

    public int checkNewRecipes(SkillZp skillZp, int lvl) {
        int i = 0;
        for (Map.Entry<ItemStack, SpecialRecipePair> e : this.getSpecialMap().entrySet()) {
            SpecialRecipePair specialRecipePair = e.getValue();
            if (skillZp == specialRecipePair.skillZp && specialRecipePair.lvl == lvl) {
                if (++i == 2) {
                    return i;
                }
            }
        }
        return i;
    }

    public void addSpecialRecipe(ItemStack itemStack, SpecialRecipePair specialRecipePair) {
        this.addSpecialInMap(itemStack, specialRecipePair);
    }

    public void addSpecialShapedRecipe(ItemStack itemStack, SpecialRecipePair specialRecipePair, Object... params) {
        GameRegistry.addRecipe(itemStack, params);
        this.addSpecialInMap(itemStack, specialRecipePair);
    }

    public void addSpecialShapelessRecipe(ItemStack itemStack, SpecialRecipePair specialRecipePair, Object... params) {
        GameRegistry.addShapelessRecipe(itemStack, params);
        this.addSpecialInMap(itemStack, specialRecipePair);
    }

    public void addSpecialInMap(ItemStack itemStack, SpecialRecipePair specialRecipePair) {
        this.getSpecialMap().put(itemStack, specialRecipePair);
    }

    public ItemStack checkItemInCollection(ItemStack itemStack, Collection<ItemStack> stacks) {
        for (ItemStack itemStack1 : stacks) {
            if (this.checkItems(itemStack, itemStack1)) {
                return itemStack1;
            }
        }
        return null;
    }

    private boolean checkItems(ItemStack itemStack1, ItemStack itemStack2) {
        if (itemStack1 == null || itemStack2 == null) {
            return false;
        }
        return (itemStack2.getMetadata() == Short.MAX_VALUE || itemStack1.getMetadata() == itemStack2.getMetadata()) && itemStack1.getItem() == itemStack2.getItem();
    }

    public Map<ItemStack, SpecialRecipePair> getSpecialMap() {
        return this.specialMap;
    }

    public Map<ItemStack, RecipeInfo> getListMap() {
        return this.listMap;
    }

    public static class SpecialRecipePair {
        private final SkillZp skillZp;
        private final int lvl;

        public SpecialRecipePair(SkillZp skillZp, int lvl) {
            this.skillZp = skillZp;
            this.lvl = lvl;
        }

        public SkillZp getSkillZp() {
            return this.skillZp;
        }

        public int getLvl() {
            return this.lvl;
        }

        public boolean equalsTo(SpecialRecipePair specialRecipePair) {
            return specialRecipePair.lvl == this.lvl && this.skillZp == specialRecipePair.skillZp;
        }
    }
}
