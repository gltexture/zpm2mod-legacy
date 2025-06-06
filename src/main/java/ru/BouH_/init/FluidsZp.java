package ru.BouH_.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import ru.BouH_.Main;
import ru.BouH_.blocks.fluids.BlockAcid;
import ru.BouH_.blocks.fluids.BlockLavaZp;
import ru.BouH_.blocks.fluids.BlockToxicwater;
import ru.BouH_.blocks.fluids.BlockZPPortal;
import ru.BouH_.misc.MaterialZp;

public class FluidsZp {
    public static Block lava_new;
    public static Block acidblock;
    public static Block toxicwater_block;
    public static Block portalZp;

    public static Fluid newLava = new Fluid("newLava").setLuminosity(15).setDensity(3000).setViscosity(6000).setTemperature(1300);
    public static Fluid acid = new Fluid("acid").setViscosity(1000).setDensity(1000);
    public static Fluid toxicwater = new Fluid("toxicWater").setViscosity(1000).setDensity(1000).setTemperature(600);
    public static Fluid portal = new Fluid("portal").setLuminosity(8).setViscosity(1000).setDensity(1000);

    public static void init() {
        FluidRegistry.registerFluid(newLava);
        FluidRegistry.registerFluid(acid);
        FluidRegistry.registerFluid(toxicwater);
        FluidRegistry.registerFluid(portal);

        lava_new = new BlockLavaZp(newLava);
        lava_new.setCreativeTab(null);
        lava_new.setHardness(100.0f);
        lava_new.setUnlocalizedName("lava_new");

        acidblock = new BlockAcid(acid, Material.water);
        acidblock.setCreativeTab(null);
        acidblock.setHardness(100.0f);
        acidblock.setUnlocalizedName("acid_block");

        toxicwater_block = new BlockToxicwater(toxicwater, Material.water);
        toxicwater_block.setCreativeTab(null);
        toxicwater_block.setHardness(100.0f);
        toxicwater_block.setUnlocalizedName("toxicWater_block");

        portalZp = new BlockZPPortal(portal, MaterialZp.stakes);
        portalZp.setCreativeTab(ItemsZp.admin_blocks);
        portalZp.setHardness(1000.0f);
        portalZp.setUnlocalizedName("portalZp");
    }

    public static void register() {
        registerBlock(lava_new);
        registerBlock(acidblock);
        registerBlock(toxicwater_block);
        registerBlock(portalZp);
    }

    public static void registerBlock(Block block) {
        GameRegistry.registerBlock(block, block.getUnlocalizedName().substring(5));
        block.setTextureName(Main.MODID + ":" + block.getUnlocalizedName().substring(5));
    }
}
