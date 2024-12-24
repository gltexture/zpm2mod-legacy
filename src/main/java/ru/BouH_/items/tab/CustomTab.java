package ru.BouH_.items.tab;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.BouH_.Main;

import java.util.ArrayList;
import java.util.List;

public class CustomTab extends CreativeTabs {
    private final List<Item> items = new ArrayList<>();
    private Item currentToDisplay;
    private int currentMeta;
    private long pastSync;

    public CustomTab(String label) {
        super(label);
    }

    @SideOnly(Side.CLIENT)
    public ItemStack getIconItemStack() {
        if (this.items.isEmpty()) {
            return new ItemStack(Items.stick);
        }
        if (this.currentToDisplay == null || (System.currentTimeMillis() - pastSync) >= 2000) {
            Item item = this.items.get(Main.rand.nextInt(this.items.size()));
            this.currentToDisplay = item;
            this.currentMeta = item.getHasSubtypes() ? Main.rand.nextInt(Math.max(item.getMaxDurability(), 1)) : 0;
            this.pastSync = System.currentTimeMillis();
        }
        return new ItemStack(this.currentToDisplay, 1, this.currentMeta);
    }

    @Override
    public Item getTabIconItem() {
        return this.currentToDisplay;
    }

    @SideOnly(Side.CLIENT)
    public String getTranslatedTabLabel() {
        return this.getTabLabel();
    }

    @SideOnly(Side.CLIENT)
    public void loadTable() {
        for (Object o : Item.itemRegistry) {
            Item item = (Item) o;
            if (item != null) {
                for (CreativeTabs tab : item.getCreativeTabs()) {
                    if (tab == this) {
                        this.items.add(item);
                    }
                }
            }
        }
    }
}
