package ru.BouH_.items.food;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import ru.BouH_.Main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FoodWaterFish extends FoodZp {
    public final boolean isCooked;

    public FoodWaterFish(boolean isCooked) {
        super(0, 0, false, FoodType.FOOD, true);
        this.setHasSubtypes(true);
        this.setUnlocalizedName(isCooked ? "fish_zp_fried" : "fish_zp");
        this.isCooked = isCooked;
    }

    @SuppressWarnings("all")
    @Override
    public void getSubItems(Item item, CreativeTabs tab, List list) {
        for (FishType type : FishType.values()) {
            if (this.isCooked && !type.isCanBeFried()) {
                continue;
            }
            list.add(new ItemStack(this, 1, type.ordinal()));
        }
    }

    public int getHealAmount(ItemStack itemStackIn) {
        return this.isCooked ? 24 : !FishType.byMetadata(this.getDamage(itemStackIn)).isCanBeFried() ? 16 : 12;
    }

    public float getSaturationModifier(ItemStack itemStackIn) {
        return this.isCooked ? 1.0f : !FishType.byMetadata(this.getDamage(itemStackIn)).isCanBeFried() ? 0.75f : 0.25f;
    }

    public boolean cantPoisonPlayer(ItemStack stack) {
        return this.isCooked || !FishType.byMetadata(this.getDamage(stack)).isCanBeFried();
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            if (this.getDamage(stack) == 3 || this.getDamage(stack) == 4) {
                if (Main.rand.nextFloat() <= 0.3f) {
                    player.addPotionEffect(new PotionEffect(19, 60));
                }
            }
        }
        return super.onItemUseFinish(stack, world, player);
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister p_94581_1_) {
        for (FishType fishType : FishType.values()) {
            fishType.getIcon(p_94581_1_);
        }
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int p_77617_1_) {
        if (this.isCooked) {
            return FishType.byMetadata(p_77617_1_).iconFried();
        }
        return FishType.byMetadata(p_77617_1_).icon();
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return false;
    }

    public String getUnlocalizedName(ItemStack stack) {
        FishType fishType = FishType.byMetadata(this.getDamage(stack));
        String s1 = fishType.unlocalizedName;
        if (this.isCooked) {
            s1 += "_fried";
        }
        return "item." + s1;
    }

    public enum FishType {
        CARP(0, true, "carp"),
        CATFISH(1, true, "catfish"),
        TUNA(2, true, "tuna"),
        JELLYFISH(3, false, "jellyfish"),
        LEECH(4, false, "leech");

        private static final Map<Integer, FishType> META_LOOKUP = new HashMap<>();

        static {
            for (FishType fish : values()) {
                META_LOOKUP.put(fish.meta, fish);
            }
        }

        private final boolean canBeFried;
        private final int meta;
        private final String unlocalizedName;
        @SideOnly(Side.CLIENT)
        private IIcon icon;
        @SideOnly(Side.CLIENT)
        private IIcon iconFried;

        FishType(int meta, boolean canBeFried, String unlocalizedName) {
            this.meta = meta;
            this.unlocalizedName = unlocalizedName;
            this.canBeFried = canBeFried;
        }

        public static FishType byMetadata(int meta) {
            FishType coin = META_LOOKUP.get(meta);
            return coin == null ? CARP : coin;
        }

        public boolean isCanBeFried() {
            return this.canBeFried;
        }

        @SideOnly(Side.CLIENT)
        public void getIcon(IIconRegister p_150968_1_) {
            this.icon = p_150968_1_.registerIcon(Main.MODID + ":" + this.unlocalizedName);
            if (this.isCanBeFried()) {
                this.iconFried = p_150968_1_.registerIcon(Main.MODID + ":" + this.unlocalizedName + "_fried");
            }
        }

        @SideOnly(Side.CLIENT)
        public IIcon icon() {
            return this.icon;
        }

        @SideOnly(Side.CLIENT)
        public IIcon iconFried() {
            return this.iconFried;
        }
    }
}
