package ru.BouH_.init;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.FMLLaunchHandler;
import net.minecraft.block.Block;
import ru.BouH_.Main;
import ru.BouH_.blocks.lootCases.*;
import ru.BouH_.items.tab.CustomTab;

public class LootCasesZp {
    public static Block tier1_chest;
    public static Block tier2_chest;
    public static Block tier3_chest;
    public static Block tier4_chest;
    public static Block tier5_chest;
    public static Block tier6_chest;
    public static Block tier7_chest;
    public static Block tier8_chest;
    public static Block tier9_chest;
    public static Block tier10_chest;
    public static Block tier11_chest;
    public static Block tier12_chest;
    public static Block tier13_chest;
    public static Block tier14_chest;
    public static Block tier15_chest;
    public static Block tier16_chest;
    public static Block tier17_chest;
    public static Block tier18_chest;
    public static Block tier_x_safe;

    public static CustomTab lootCases;
    public static int id = 3;

    public static void init() {
        lootCases = new CustomTab("item.category.lootCases");

        tier1_chest = new LootTier1Village(id++);
        tier1_chest.setCreativeTab(lootCases);
        tier1_chest.setBlockUnbreakable();
        tier1_chest.setResistance(9999);
        tier1_chest.setUnlocalizedName("tier1_chest");
        tier1_chest.setStepSound(Block.soundTypeWood);

        tier2_chest = new LootTier2Village(id++);
        tier2_chest.setCreativeTab(lootCases);
        tier2_chest.setBlockUnbreakable();
        tier2_chest.setResistance(9999);
        tier2_chest.setUnlocalizedName("tier2_chest");
        tier2_chest.setStepSound(Block.soundTypeWood);

        tier3_chest = new LootTier1City(id++);
        tier3_chest.setCreativeTab(lootCases);
        tier3_chest.setBlockUnbreakable();
        tier3_chest.setResistance(9999);
        tier3_chest.setUnlocalizedName("tier3_chest");
        tier3_chest.setStepSound(Block.soundTypeWood);

        tier4_chest = new LootTier1Police(id++);
        tier4_chest.setCreativeTab(lootCases);
        tier4_chest.setBlockUnbreakable();
        tier4_chest.setResistance(9999);
        tier4_chest.setUnlocalizedName("tier4_chest");
        tier4_chest.setStepSound(Block.soundTypeWood);

        tier5_chest = new LootTier1Industry(id++);
        tier5_chest.setCreativeTab(lootCases);
        tier5_chest.setBlockUnbreakable();
        tier5_chest.setResistance(9999);
        tier5_chest.setUnlocalizedName("tier5_chest");
        tier5_chest.setStepSound(Block.soundTypeWood);

        tier6_chest = new LootTier2Industry(id++);
        tier6_chest.setCreativeTab(lootCases);
        tier6_chest.setBlockUnbreakable();
        tier6_chest.setResistance(9999);
        tier6_chest.setUnlocalizedName("tier6_chest");
        tier6_chest.setStepSound(Block.soundTypeWood);

        tier7_chest = new LootTier1Military(id++);
        tier7_chest.setCreativeTab(lootCases);
        tier7_chest.setBlockUnbreakable();
        tier7_chest.setResistance(9999);
        tier7_chest.setUnlocalizedName("tier7_chest");
        tier7_chest.setStepSound(Block.soundTypeWood);

        tier8_chest = new LootTier2Military(id++);
        tier8_chest.setCreativeTab(lootCases);
        tier8_chest.setBlockUnbreakable();
        tier8_chest.setResistance(9999);
        tier8_chest.setUnlocalizedName("tier8_chest");
        tier8_chest.setStepSound(Block.soundTypeWood);

        tier9_chest = new LootTier3Military(id++);
        tier9_chest.setCreativeTab(lootCases);
        tier9_chest.setBlockUnbreakable();
        tier9_chest.setResistance(9999);
        tier9_chest.setUnlocalizedName("tier9_chest");
        tier9_chest.setStepSound(Block.soundTypeWood);

        tier10_chest = new LootTier1Medicine(id++);
        tier10_chest.setCreativeTab(lootCases);
        tier10_chest.setBlockUnbreakable();
        tier10_chest.setResistance(9999);
        tier10_chest.setUnlocalizedName("tier10_chest");
        tier10_chest.setStepSound(Block.soundTypeWood);

        tier11_chest = new LootTier2Medicine(id++);
        tier11_chest.setCreativeTab(lootCases);
        tier11_chest.setBlockUnbreakable();
        tier11_chest.setResistance(9999);
        tier11_chest.setUnlocalizedName("tier11_chest");
        tier11_chest.setStepSound(Block.soundTypeWood);

        tier12_chest = new LootTier1Food(id++);
        tier12_chest.setCreativeTab(lootCases);
        tier12_chest.setBlockUnbreakable();
        tier12_chest.setResistance(9999);
        tier12_chest.setUnlocalizedName("tier12_chest");
        tier12_chest.setStepSound(Block.soundTypeWood);

        tier13_chest = new LootTier1Ammo(id++);
        tier13_chest.setCreativeTab(lootCases);
        tier13_chest.setBlockUnbreakable();
        tier13_chest.setResistance(9999);
        tier13_chest.setUnlocalizedName("tier13_chest");
        tier13_chest.setStepSound(Block.soundTypeWood);

        tier14_chest = new LootTier2Ammo(id++);
        tier14_chest.setCreativeTab(lootCases);
        tier14_chest.setBlockUnbreakable();
        tier14_chest.setResistance(9999);
        tier14_chest.setUnlocalizedName("tier14_chest");
        tier14_chest.setStepSound(Block.soundTypeWood);

        tier15_chest = new LootTier1Hunting(id++);
        tier15_chest.setCreativeTab(lootCases);
        tier15_chest.setBlockUnbreakable();
        tier15_chest.setResistance(9999);
        tier15_chest.setUnlocalizedName("tier15_chest");
        tier15_chest.setStepSound(Block.soundTypeWood);

        tier16_chest = new LootTier2Hunting(id++);
        tier16_chest.setCreativeTab(lootCases);
        tier16_chest.setBlockUnbreakable();
        tier16_chest.setResistance(9999);
        tier16_chest.setUnlocalizedName("tier16_chest");
        tier16_chest.setStepSound(Block.soundTypeWood);

        tier17_chest = new LootTier1MegaCity(id++);
        tier17_chest.setCreativeTab(lootCases);
        tier17_chest.setBlockUnbreakable();
        tier17_chest.setResistance(9999);
        tier17_chest.setUnlocalizedName("tier17_chest");
        tier17_chest.setStepSound(Block.soundTypeWood);

        tier18_chest = new LootTier1Labs(id++);
        tier18_chest.setCreativeTab(lootCases);
        tier18_chest.setBlockUnbreakable();
        tier18_chest.setResistance(9999);
        tier18_chest.setUnlocalizedName("tier18_chest");
        tier18_chest.setStepSound(Block.soundTypeWood);

        tier_x_safe = new LootTierXSafe(id++);
        tier_x_safe.setCreativeTab(lootCases);
        tier_x_safe.setBlockUnbreakable();
        tier_x_safe.setResistance(9999);
        tier_x_safe.setUnlocalizedName("tier_x_safe");
        tier_x_safe.setStepSound(Block.soundTypeWood);
    }

    public static void register() {
        registerBlock(tier1_chest);
        registerBlock(tier2_chest);
        registerBlock(tier3_chest);
        registerBlock(tier4_chest);
        registerBlock(tier5_chest);
        registerBlock(tier6_chest);
        registerBlock(tier7_chest);
        registerBlock(tier8_chest);
        registerBlock(tier9_chest);
        registerBlock(tier10_chest);
        registerBlock(tier11_chest);
        registerBlock(tier12_chest);
        registerBlock(tier13_chest);
        registerBlock(tier14_chest);
        registerBlock(tier15_chest);
        registerBlock(tier16_chest);
        registerBlock(tier17_chest);
        registerBlock(tier18_chest);
        registerBlock(tier_x_safe);

        if (FMLLaunchHandler.side().isClient()) {
            lootCases.loadTable();
        }
    }

    public static void registerBlock(Block block) {
        GameRegistry.registerBlock(block, block.getUnlocalizedName().substring(5));
        block.setTextureName(Main.MODID + ":" + block.getUnlocalizedName().substring(5));
    }
}
