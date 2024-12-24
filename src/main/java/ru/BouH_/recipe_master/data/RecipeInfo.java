package ru.BouH_.recipe_master.data;

import ru.BouH_.recipe_master.data.container.ARecipeContainer;

import java.util.ArrayList;
import java.util.List;

public class RecipeInfo {
    private final List<ARecipeContainer> aRecipeContainerList = new ArrayList<>();

    public RecipeInfo(ARecipeContainer aRecipeContainer) {
        this.aRecipeContainerList.add(aRecipeContainer);
    }

    public List<ARecipeContainer> getiRecipeContainerList() {
        return this.aRecipeContainerList;
    }

    public int getPages() {
        return this.getiRecipeContainerList().size();
    }
}
