package ru.BouH_.items.misc;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import ru.BouH_.Main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemCoin extends Item {

    public ItemCoin() {
        this.setHasSubtypes(true);
        this.setUnlocalizedName("coin");
    }

    @SuppressWarnings("all")
    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (CoinType type : CoinType.values()) {
            list.add(new ItemStack(this, 1, type.ordinal()));
        }
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister p_94581_1_) {
        for (CoinType cnType : CoinType.values()) {
            cnType.getIcon(p_94581_1_);
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int p_77617_1_) {
        return CoinType.byMetadata(p_77617_1_).icon();
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return false;
    }

    public String getUnlocalizedName(ItemStack stack) {
        return "item.cash_coin" + CoinType.byMetadata(this.getDamage(stack)).unlocalizedName;
    }

    public enum CoinType {
        COIN005(0, "_005"),
        COIN025(1, "_025"),
        COIN050(2, "_050");

        private static final Map<Integer, CoinType> META_LOOKUP = new HashMap<>();

        static {
            for (CoinType coin : values()) {
                META_LOOKUP.put(coin.meta, coin);
            }
        }

        private final int meta;
        private final String unlocalizedName;
        @SideOnly(Side.CLIENT)
        private IIcon icon;

        CoinType(int meta, String unlocalizedName) {
            this.meta = meta;
            this.unlocalizedName = unlocalizedName;
        }

        public static CoinType byMetadata(int meta) {
            CoinType coin = META_LOOKUP.get(meta);
            return coin == null ? COIN005 : coin;
        }

        @SideOnly(Side.CLIENT)
        public void getIcon(IIconRegister p_150968_1_) {
            this.icon = p_150968_1_.registerIcon(Main.MODID + ":cash_coin" + this.unlocalizedName);
        }

        @SideOnly(Side.CLIENT)
        public IIcon icon() {
            return this.icon;
        }
    }
}
