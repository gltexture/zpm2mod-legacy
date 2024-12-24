package ru.BouH_.hook.server;

import cpw.mods.fml.common.registry.VillagerRegistry;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.ai.EntityAIVillagerMate;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Tuple;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import ru.BouH_.Main;
import ru.hook.asm.Hook;
import ru.hook.asm.ReturnCondition;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class VillagerHook {
    public static final Map<Item, Tuple> villagersSellingList = new HashMap<>();
    public static final Map<Item, Tuple> blacksmithSellingList = new HashMap<>();

    static {
        villagersSellingList.put(Items.coal, new Tuple(16, 24));
        villagersSellingList.put(Items.iron_ingot, new Tuple(8, 10));
        villagersSellingList.put(Items.gold_ingot, new Tuple(8, 10));
        villagersSellingList.put(Items.diamond, new Tuple(4, 6));
        villagersSellingList.put(Items.paper, new Tuple(24, 36));
        villagersSellingList.put(Items.book, new Tuple(11, 13));
        villagersSellingList.put(Items.written_book, new Tuple(1, 1));
        villagersSellingList.put(Items.ender_pearl, new Tuple(3, 4));
        villagersSellingList.put(Items.ender_eye, new Tuple(2, 3));
        villagersSellingList.put(Items.porkchop, new Tuple(14, 18));
        villagersSellingList.put(Items.beef, new Tuple(14, 18));
        villagersSellingList.put(Items.chicken, new Tuple(14, 18));
        villagersSellingList.put(Items.cooked_fish, new Tuple(9, 13));
        villagersSellingList.put(Items.wheat_seeds, new Tuple(34, 48));
        villagersSellingList.put(Items.melon_seeds, new Tuple(30, 38));
        villagersSellingList.put(Items.pumpkin_seeds, new Tuple(30, 38));
        villagersSellingList.put(Items.wheat, new Tuple(18, 22));
        villagersSellingList.put(Item.getItemFromBlock(Blocks.wool), new Tuple(22, 32));
        villagersSellingList.put(Items.rotten_flesh, new Tuple(36, 64));
        blacksmithSellingList.put(Items.flint_and_steel, new Tuple(3, 4));
        blacksmithSellingList.put(Items.shears, new Tuple(3, 4));
        blacksmithSellingList.put(Items.iron_sword, new Tuple(7, 11));
        blacksmithSellingList.put(Items.diamond_sword, new Tuple(12, 14));
        blacksmithSellingList.put(Items.iron_axe, new Tuple(6, 8));
        blacksmithSellingList.put(Items.diamond_axe, new Tuple(9, 12));
        blacksmithSellingList.put(Items.iron_pickaxe, new Tuple(7, 9));
        blacksmithSellingList.put(Items.diamond_pickaxe, new Tuple(10, 12));
        blacksmithSellingList.put(Items.iron_shovel, new Tuple(4, 6));
        blacksmithSellingList.put(Items.diamond_shovel, new Tuple(7, 8));
        blacksmithSellingList.put(Items.iron_hoe, new Tuple(4, 6));
        blacksmithSellingList.put(Items.diamond_hoe, new Tuple(7, 8));
        blacksmithSellingList.put(Items.iron_boots, new Tuple(4, 6));
        blacksmithSellingList.put(Items.diamond_boots, new Tuple(7, 8));
        blacksmithSellingList.put(Items.iron_helmet, new Tuple(4, 6));
        blacksmithSellingList.put(Items.diamond_helmet, new Tuple(7, 8));
        blacksmithSellingList.put(Items.iron_chestplate, new Tuple(10, 14));
        blacksmithSellingList.put(Items.diamond_chestplate, new Tuple(16, 19));
        blacksmithSellingList.put(Items.iron_leggings, new Tuple(8, 10));
        blacksmithSellingList.put(Items.diamond_leggings, new Tuple(11, 14));
        blacksmithSellingList.put(Items.chainmail_boots, new Tuple(5, 7));
        blacksmithSellingList.put(Items.chainmail_helmet, new Tuple(5, 7));
        blacksmithSellingList.put(Items.chainmail_chestplate, new Tuple(11, 15));
        blacksmithSellingList.put(Items.chainmail_leggings, new Tuple(9, 11));
        blacksmithSellingList.put(Items.bread, new Tuple(-4, -2));
        blacksmithSellingList.put(Items.melon, new Tuple(-8, -4));
        blacksmithSellingList.put(Items.apple, new Tuple(-8, -4));
        blacksmithSellingList.put(Items.cookie, new Tuple(-10, -2));
        blacksmithSellingList.put(Item.getItemFromBlock(Blocks.glass), new Tuple(-5, -3));
        blacksmithSellingList.put(Item.getItemFromBlock(Blocks.bookshelf), new Tuple(3, 4));
        blacksmithSellingList.put(Items.leather_chestplate, new Tuple(4, 5));
        blacksmithSellingList.put(Items.leather_boots, new Tuple(2, 4));
        blacksmithSellingList.put(Items.leather_helmet, new Tuple(2, 4));
        blacksmithSellingList.put(Items.leather_leggings, new Tuple(2, 4));
        blacksmithSellingList.put(Items.saddle, new Tuple(6, 8));
        blacksmithSellingList.put(Items.experience_bottle, new Tuple(-2, -1));
        blacksmithSellingList.put(Items.redstone, new Tuple(-4, -1));
        blacksmithSellingList.put(Items.compass, new Tuple(10, 12));
        blacksmithSellingList.put(Items.clock, new Tuple(10, 12));
        blacksmithSellingList.put(Item.getItemFromBlock(Blocks.glowstone), new Tuple(-3, -1));
        blacksmithSellingList.put(Items.cooked_porkchop, new Tuple(-7, -5));
        blacksmithSellingList.put(Items.cooked_beef, new Tuple(-7, -5));
        blacksmithSellingList.put(Items.cooked_chicken, new Tuple(-8, -6));
        blacksmithSellingList.put(Items.ender_eye, new Tuple(7, 11));
        blacksmithSellingList.put(Items.arrow, new Tuple(-12, -8));
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static int func_146090_c(EntityVillager vil, Item p_146090_0_, Random p_146090_1_) {
        Tuple tuple = VillagerHook.blacksmithSellingList.get(p_146090_0_);
        return tuple == null ? 1 : ((Integer) tuple.getFirst() >= (Integer) tuple.getSecond() ? (Integer) tuple.getFirst() : (Integer) tuple.getFirst() + p_146090_1_.nextInt((Integer) tuple.getSecond() - (Integer) tuple.getFirst()));
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static int func_146092_b(EntityVillager vil, Item p_146092_0_, Random p_146092_1_) {
        Tuple tuple = VillagerHook.villagersSellingList.get(p_146092_0_);
        return tuple == null ? 1 : ((Integer) tuple.getFirst() >= (Integer) tuple.getSecond() ? (Integer) tuple.getFirst() : (Integer) tuple.getFirst() + p_146092_1_.nextInt((Integer) tuple.getSecond() - (Integer) tuple.getFirst()));
    }

    @SuppressWarnings("unchecked")
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static void addDefaultEquipmentAndRecipies(EntityVillager vil, int p_70950_1_) {
        float f2 = 0;
        if (vil.buyingList != null) {
            f2 = MathHelper.sqrt_float((float) vil.buyingList.size()) * 0.2F;
        }

        MerchantRecipeList merchantrecipelist = new MerchantRecipeList();
        VillagerRegistry.manageVillagerTrades(merchantrecipelist, vil, vil.getProfession(), Main.rand);
        int k;
        label50:

        switch (vil.getProfession()) {
            case 0:
                EntityVillager.func_146091_a(merchantrecipelist, Items.wheat, Main.rand, VillagerHook.adjustProbability(vil, 0.9F, f2));
                EntityVillager.func_146091_a(merchantrecipelist, Item.getItemFromBlock(Blocks.wool), Main.rand, VillagerHook.adjustProbability(vil, 0.5F, f2));
                EntityVillager.func_146091_a(merchantrecipelist, Items.chicken, Main.rand, VillagerHook.adjustProbability(vil, 0.5F, f2));
                EntityVillager.func_146091_a(merchantrecipelist, Items.cooked_fish, Main.rand, VillagerHook.adjustProbability(vil, 0.4F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.bread, Main.rand, VillagerHook.adjustProbability(vil, 0.9F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.melon, Main.rand, VillagerHook.adjustProbability(vil, 0.3F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.apple, Main.rand, VillagerHook.adjustProbability(vil, 0.3F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.cookie, Main.rand, VillagerHook.adjustProbability(vil, 0.3F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.shears, Main.rand, VillagerHook.adjustProbability(vil, 0.3F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.flint_and_steel, Main.rand, VillagerHook.adjustProbability(vil, 0.3F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.cooked_chicken, Main.rand, VillagerHook.adjustProbability(vil, 0.3F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.arrow, Main.rand, VillagerHook.adjustProbability(vil, 0.5F, f2));
                if (Main.rand.nextFloat() < VillagerHook.adjustProbability(vil, 0.5F, f2)) {
                    merchantrecipelist.add(new MerchantRecipe(new ItemStack(Blocks.gravel, 10), new ItemStack(Items.emerald), new ItemStack(Items.flint, 4 + Main.rand.nextInt(2), 0)));
                }
                break;
            case 1:
                EntityVillager.func_146091_a(merchantrecipelist, Items.paper, Main.rand, VillagerHook.adjustProbability(vil, 0.8F, f2));
                EntityVillager.func_146091_a(merchantrecipelist, Items.book, Main.rand, VillagerHook.adjustProbability(vil, 0.8F, f2));
                EntityVillager.func_146091_a(merchantrecipelist, Items.written_book, Main.rand, VillagerHook.adjustProbability(vil, 0.3F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Item.getItemFromBlock(Blocks.bookshelf), Main.rand, VillagerHook.adjustProbability(vil, 0.8F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Item.getItemFromBlock(Blocks.glass), Main.rand, VillagerHook.adjustProbability(vil, 0.2F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.compass, Main.rand, VillagerHook.adjustProbability(vil, 0.2F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.clock, Main.rand, VillagerHook.adjustProbability(vil, 0.2F, f2));

                if (Main.rand.nextFloat() < VillagerHook.adjustProbability(vil, 0.07F, f2)) {
                    Enchantment enchantment = Enchantment.enchantmentsBookList[Main.rand.nextInt(Enchantment.enchantmentsBookList.length)];
                    int i1 = MathHelper.getRandomIntegerInRange(Main.rand, enchantment.getMinLevel(), enchantment.getMaxLevel());
                    ItemStack itemstack = Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(enchantment, i1));
                    k = 5 + Main.rand.nextInt(5 + i1 * 10) + 3 * i1;
                    merchantrecipelist.add(new MerchantRecipe(new ItemStack(Items.book), new ItemStack(Items.emerald, k), itemstack));
                }
                break;
            case 2:
                EntityVillager.func_146089_b(merchantrecipelist, Items.ender_eye, Main.rand, VillagerHook.adjustProbability(vil, 0.3F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.experience_bottle, Main.rand, VillagerHook.adjustProbability(vil, 0.2F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.redstone, Main.rand, VillagerHook.adjustProbability(vil, 0.4F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Item.getItemFromBlock(Blocks.glowstone), Main.rand, VillagerHook.adjustProbability(vil, 0.3F, f2));
                Item[] aitem = new Item[]{Items.iron_sword, Items.diamond_sword, Items.iron_chestplate, Items.diamond_chestplate, Items.iron_axe, Items.diamond_axe, Items.iron_pickaxe, Items.diamond_pickaxe};
                int j = aitem.length;
                k = 0;

                while (true) {
                    if (k >= j) {
                        break label50;
                    }

                    Item item = aitem[k];

                    if (Main.rand.nextFloat() < VillagerHook.adjustProbability(vil, 0.05F, f2)) {
                        merchantrecipelist.add(new MerchantRecipe(new ItemStack(item, 1, 0), new ItemStack(Items.emerald, 4 + Main.rand.nextInt(3), 0), EnchantmentHelper.addRandomEnchantment(Main.rand, new ItemStack(item, 1, 0), 1 + Main.rand.nextInt(8))));
                    }

                    ++k;
                }
            case 3:
                EntityVillager.func_146091_a(merchantrecipelist, Items.coal, Main.rand, VillagerHook.adjustProbability(vil, 0.7F, f2));
                EntityVillager.func_146091_a(merchantrecipelist, Items.iron_ingot, Main.rand, VillagerHook.adjustProbability(vil, 0.5F, f2));
                EntityVillager.func_146091_a(merchantrecipelist, Items.gold_ingot, Main.rand, VillagerHook.adjustProbability(vil, 0.5F, f2));
                EntityVillager.func_146091_a(merchantrecipelist, Items.diamond, Main.rand, VillagerHook.adjustProbability(vil, 0.5F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.iron_sword, Main.rand, VillagerHook.adjustProbability(vil, 0.5F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.diamond_sword, Main.rand, VillagerHook.adjustProbability(vil, 0.5F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.iron_axe, Main.rand, VillagerHook.adjustProbability(vil, 0.3F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.diamond_axe, Main.rand, VillagerHook.adjustProbability(vil, 0.3F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.iron_pickaxe, Main.rand, VillagerHook.adjustProbability(vil, 0.5F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.diamond_pickaxe, Main.rand, VillagerHook.adjustProbability(vil, 0.5F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.iron_shovel, Main.rand, VillagerHook.adjustProbability(vil, 0.2F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.diamond_shovel, Main.rand, VillagerHook.adjustProbability(vil, 0.2F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.iron_hoe, Main.rand, VillagerHook.adjustProbability(vil, 0.2F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.diamond_hoe, Main.rand, VillagerHook.adjustProbability(vil, 0.2F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.iron_boots, Main.rand, VillagerHook.adjustProbability(vil, 0.2F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.diamond_boots, Main.rand, VillagerHook.adjustProbability(vil, 0.2F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.iron_helmet, Main.rand, VillagerHook.adjustProbability(vil, 0.2F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.diamond_helmet, Main.rand, VillagerHook.adjustProbability(vil, 0.2F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.iron_chestplate, Main.rand, VillagerHook.adjustProbability(vil, 0.2F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.diamond_chestplate, Main.rand, VillagerHook.adjustProbability(vil, 0.2F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.iron_leggings, Main.rand, VillagerHook.adjustProbability(vil, 0.2F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.diamond_leggings, Main.rand, VillagerHook.adjustProbability(vil, 0.2F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.chainmail_boots, Main.rand, VillagerHook.adjustProbability(vil, 0.1F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.chainmail_helmet, Main.rand, VillagerHook.adjustProbability(vil, 0.1F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.chainmail_chestplate, Main.rand, VillagerHook.adjustProbability(vil, 0.1F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.chainmail_leggings, Main.rand, VillagerHook.adjustProbability(vil, 0.1F, f2));
                break;
            case 4:
                EntityVillager.func_146091_a(merchantrecipelist, Items.coal, Main.rand, VillagerHook.adjustProbability(vil, 0.7F, f2));
                EntityVillager.func_146091_a(merchantrecipelist, Items.porkchop, Main.rand, VillagerHook.adjustProbability(vil, 0.5F, f2));
                EntityVillager.func_146091_a(merchantrecipelist, Items.beef, Main.rand, VillagerHook.adjustProbability(vil, 0.5F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.saddle, Main.rand, VillagerHook.adjustProbability(vil, 0.1F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.leather_chestplate, Main.rand, VillagerHook.adjustProbability(vil, 0.3F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.leather_boots, Main.rand, VillagerHook.adjustProbability(vil, 0.3F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.leather_helmet, Main.rand, VillagerHook.adjustProbability(vil, 0.3F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.leather_leggings, Main.rand, VillagerHook.adjustProbability(vil, 0.3F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.cooked_porkchop, Main.rand, VillagerHook.adjustProbability(vil, 0.3F, f2));
                EntityVillager.func_146089_b(merchantrecipelist, Items.cooked_beef, Main.rand, VillagerHook.adjustProbability(vil, 0.3F, f2));
        }

        if (merchantrecipelist.isEmpty()) {
            EntityVillager.func_146091_a(merchantrecipelist, Items.gold_ingot, Main.rand, 1.0F);
        }

        Collections.shuffle(merchantrecipelist);

        if (vil.buyingList == null) {
            vil.buyingList = new MerchantRecipeList();
        }

        for (int l = 0; l < p_70950_1_ && l < merchantrecipelist.size(); ++l) {
            vil.buyingList.addToListWithCheck((MerchantRecipe) merchantrecipelist.get(l));
        }
    }

    public static float adjustProbability(EntityVillager vil, float p_82188_1_, float f2) {
        float f1 = p_82188_1_ + f2;
        return f1 > 0.9F ? 0.9F - (f1 - 0.9F) : f1;
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static boolean shouldExecute(EntityAIVillagerMate vil) {
        return false;
    }
}
