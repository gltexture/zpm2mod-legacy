package ru.BouH_.init;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import net.minecraft.potion.PotionEffect;
import ru.BouH_.Main;
import ru.BouH_.blocks.*;
import ru.BouH_.blocks.gas.*;
import ru.BouH_.fun.blocks.BlockC300;
import ru.BouH_.fun.blocks.BlockKalibr;
import ru.BouH_.fun.blocks.BlockPancir;
import ru.BouH_.items.block.ItemBlockLayer;
import ru.BouH_.items.block.ItemShelf;

public class BlocksZp {
    public static Block scrap;
    public static BlockOreZp titan_ore;
    public static BlockOreZp copper_ore;
    public static Block workbench_destroyed;
    public static Block furnace_destroyed;
    public static Block cone;
    public static Block copper_block;
    public static Block road;
    public static Block roadSlab;
    public static Block road2;
    public static Block road2Slab;
    public static Block sandbag;
    public static Block armored_glass;
    public static BlockPressurePlate mine;
    public static Block pumpkinNew;
    public static Block sand_layer;
    public static Block gravel_layer;
    public static Block torch1;
    public static Block torch2;
    public static Block torch3;
    public static Block torch4;
    public static Block torch5;
    public static Block empty_bookshelf;
    public static Block concrete_f;
    public static Block concrete_f2;
    public static Block concrete;
    public static Block concrete2;
    public static Block concrete3;
    public static BlockStairs concreteStairs;
    public static Block concrete_green;
    public static Block fortified_cobble;

    public static Block temp_stone;

    public static Block debrisand;
    public static Block frozen_dirt;

    public static Block titan;
    public static Block lab;
    public static Block armor;
    public static Block armor2;
    public static Block uranium;
    public static Block lamp;
    public static Block lamp_off;
    public static Block block_lamp;
    public static Block block_lamp_off;
    public static Block wooden_stakes;
    public static Block wooden_stakes_stage2;
    public static Block wire;
    public static Block wire_reinforced;
    public static Block wire_stage2;
    public static Block wire3;
    public static Block radiation1;
    public static Block radiation2;
    public static Block gas;
    public static Block acidCloud;
    public static Block virus;
    public static Block hunger;
    public static Block barrier;
    public static Block living_barrier;
    public static Block movement_barrier;
    public static Block fireStone;
    //FUN
    public static Block c300Block;
    public static Block pancirBlock;
    public static Block kalibrBlock;

    //FUN
    public static void init() {
        cone = new BlockRoadCone();
        cone.setCreativeTab(TabsZP.decorations);
        cone.setUnlocalizedName("cone");
        cone.setHardness(0.1f);

        workbench_destroyed = new BlockDestroyedWorkBench();
        workbench_destroyed.setCreativeTab(TabsZP.decorations);
        workbench_destroyed.setUnlocalizedName("workbench_destroyed");
        workbench_destroyed.setStepSound(Block.soundTypeWood);
        workbench_destroyed.setHardness(2.5F);

        furnace_destroyed = new BlockDestroyedFurnace();
        furnace_destroyed.setCreativeTab(TabsZP.decorations);
        furnace_destroyed.setUnlocalizedName("furnace_destroyed");
        furnace_destroyed.setStepSound(Block.soundTypeStone);
        furnace_destroyed.setHardness(3.5F);

        empty_bookshelf = new BlockShelf();
        empty_bookshelf.setCreativeTab(TabsZP.decorations);
        empty_bookshelf.setUnlocalizedName("empty_bookshelf");
        empty_bookshelf.setStepSound(Block.soundTypeWood);
        empty_bookshelf.setHardness(1.5F);

        debrisand = new BlockDebrisand();
        debrisand.setCreativeTab(TabsZP.blocks);
        debrisand.setUnlocalizedName("debrisand");
        debrisand.setStepSound(Block.soundTypeGravel);
        debrisand.setHardness(2.5f);

        frozen_dirt = new BlockFrozenDirt();
        frozen_dirt.setCreativeTab(TabsZP.blocks);
        frozen_dirt.setUnlocalizedName("frozen_dirt");
        frozen_dirt.setStepSound(Block.soundTypeGravel);
        frozen_dirt.setHardness(2.5f);

        titan_ore = new BlockOreZp(ItemsZp.raw_titan);
        titan_ore.setCreativeTab(TabsZP.blocks);
        titan_ore.setUnlocalizedName("titan_ore");
        titan_ore.setHardness(3.0f);

        copper_ore = new BlockOreZp(ItemsZp.raw_copper);
        copper_ore.setCreativeTab(TabsZP.blocks);
        copper_ore.setUnlocalizedName("copper_ore");
        copper_ore.setHardness(3.0f);

        mine = new BlockMine("mine", Material.iron, BlockPressurePlate.Sensitivity.mobs);
        mine.setCreativeTab(TabsZP.blocks);
        mine.setUnlocalizedName("mine");
        mine.setStepSound(Block.soundTypeStone);
        mine.setHardness(1.5f);

        radiation1 = new BlockRadiation();
        radiation1.setCreativeTab(TabsZP.admin_blocks);
        radiation1.setUnlocalizedName("radiation1");

        radiation2 = new BlockRadiation();
        radiation2.setCreativeTab(TabsZP.admin_blocks);
        radiation2.setUnlocalizedName("radiation2");

        armored_glass = new BlockArmorGlass();
        armored_glass.setCreativeTab(TabsZP.blocks);
        armored_glass.setUnlocalizedName("armored_glass");
        armored_glass.setHardness(8.5f);
        armored_glass.setStepSound(Block.soundTypeGlass);
        armored_glass.setResistance(1.0f);

        sand_layer = new BlockLayer();
        sand_layer.setHardness(0.1f);
        sand_layer.setCreativeTab(TabsZP.decorations);
        sand_layer.setStepSound(Block.soundTypeSand);
        sand_layer.setUnlocalizedName("sand_layer");

        gravel_layer = new BlockLayer();
        gravel_layer.setHardness(0.1f);
        gravel_layer.setCreativeTab(TabsZP.decorations);
        gravel_layer.setStepSound(Block.soundTypeGravel);
        gravel_layer.setUnlocalizedName("gravel_layer");

        temp_stone = new BlockTempStone();
        temp_stone.setHardness(0.1f);
        temp_stone.setStepSound(Block.soundTypeStone);
        temp_stone.setUnlocalizedName("temp_stone");

        gas = new BlockGas();
        gas.setCreativeTab(TabsZP.admin_blocks);
        gas.setUnlocalizedName("gas");

        acidCloud = new BlockAcidCloud();
        acidCloud.setCreativeTab(TabsZP.admin_blocks);
        acidCloud.setUnlocalizedName("acidCloud");

        virus = new BlockInstantEffect(false, new PotionEffect(26, 21600));
        virus.setCreativeTab(TabsZP.admin_blocks);
        virus.setUnlocalizedName("virus");

        hunger = new BlockInstantEffect(true, new PotionEffect(17, 1200), new PotionEffect(31, 1200));
        hunger.setCreativeTab(TabsZP.admin_blocks);
        hunger.setUnlocalizedName("hunger");

        barrier = new BlockZombieBarrier();
        barrier.setCreativeTab(TabsZP.admin_blocks);
        barrier.setUnlocalizedName("barrier");

        living_barrier = new BlockLivingBarrier();
        living_barrier.setCreativeTab(TabsZP.admin_blocks);
        living_barrier.setUnlocalizedName("living_barrier");

        movement_barrier = new BlockMovementBarrier();
        movement_barrier.setCreativeTab(TabsZP.admin_blocks);
        movement_barrier.setUnlocalizedName("movement_barrier");

        scrap = new BlockScrap();
        scrap.setCreativeTab(TabsZP.blocks);
        scrap.setUnlocalizedName("scrap");
        scrap.setStepSound(Block.soundTypeMetal);
        scrap.setHardness(15.0f);
        scrap.setResistance(10.0F);

        fireStone = new BlockFirestone();
        fireStone.setCreativeTab(TabsZP.blocks);
        fireStone.setUnlocalizedName("fireStone");
        fireStone.setHardness(2.0f);

        wooden_stakes = new BlockStakes(false);
        wooden_stakes.setCreativeTab(TabsZP.blocks);
        wooden_stakes.setUnlocalizedName("wooden_stakes");
        wooden_stakes.setStepSound(Block.soundTypeWood);
        wooden_stakes.setHardness(1.25f);
        wooden_stakes.setResistance(0.5f);

        wooden_stakes_stage2 = new BlockStakes(true);
        wooden_stakes_stage2.setCreativeTab(TabsZP.blocks);
        wooden_stakes_stage2.setUnlocalizedName("wooden_stakes_stage2");
        wooden_stakes_stage2.setStepSound(Block.soundTypeWood);
        wooden_stakes_stage2.setHardness(1.25f);
        wooden_stakes_stage2.setResistance(0.5f);

        wire = new BlockWire(false);
        wire.setCreativeTab(TabsZP.blocks);
        wire.setUnlocalizedName("wire");
        wire.setStepSound(Block.soundTypeMetal);
        wire.setHardness(8.0f);
        wire.setResistance(2.0f);

        wire_stage2 = new BlockWire(true);
        wire_stage2.setCreativeTab(TabsZP.blocks);
        wire_stage2.setUnlocalizedName("wire_stage2");
        wire_stage2.setStepSound(Block.soundTypeMetal);
        wire_stage2.setHardness(4.0f);
        wire_stage2.setResistance(1.0f);

        wire_reinforced = new BlockWire(false);
        wire_reinforced.setCreativeTab(TabsZP.blocks);
        wire_reinforced.setUnlocalizedName("wire_reinforced");
        wire_reinforced.setStepSound(Block.soundTypeMetal);
        wire_reinforced.setHardness(24.0f);
        wire_reinforced.setResistance(2.0f);

        wire3 = new BlockWire(false);
        wire3.setCreativeTab(TabsZP.blocks);
        wire3.setUnlocalizedName("wire3");
        wire3.setStepSound(Block.soundTypeMetal);
        wire3.setResistance(1500.0f);
        wire3.setBlockUnbreakable();

        uranium = new BlockUran();
        uranium.setCreativeTab(TabsZP.blocks);
        uranium.setUnlocalizedName("uranium");
        uranium.setStepSound(Block.soundTypeMetal);
        uranium.setHardness(12.5f);
        uranium.setLightLevel(0.7f);

        armor = new BlockArmor();
        armor.setCreativeTab(TabsZP.blocks);
        armor.setUnlocalizedName("armor");
        armor.setStepSound(Block.soundTypeMetal);
        armor.setHardness(100.0f);
        armor.setResistance(50.0f);

        armor2 = new BlockArmor();
        armor2.setCreativeTab(TabsZP.blocks);
        armor2.setUnlocalizedName("armor2");
        armor2.setStepSound(Block.soundTypeMetal);
        armor2.setHardness(100.0f);
        armor2.setResistance(50.0f);

        concrete = new BlockConcrete();
        concrete.setCreativeTab(TabsZP.blocks);
        concrete.setUnlocalizedName("concrete");
        concrete.setHardness(150.0f);
        concrete.setResistance(17.0f);

        concrete2 = new BlockConcrete();
        concrete2.setCreativeTab(TabsZP.blocks);
        concrete2.setUnlocalizedName("concrete2");
        concrete2.setHardness(300.0f);
        concrete2.setResistance(20.0f);

        concreteStairs = new BlockConcreteStairs(concrete, 0);
        concreteStairs.setCreativeTab(TabsZP.blocks);
        concreteStairs.setUnlocalizedName("concreteStairs");
        concreteStairs.setHardness(200.0f);
        concreteStairs.setResistance(19.0f);
        concreteStairs.setLightOpacity(1);

        concrete3 = new BlockConcrete();
        concrete3.setCreativeTab(TabsZP.blocks);
        concrete3.setUnlocalizedName("concrete3");
        concrete3.setBlockUnbreakable();
        concrete3.setResistance(1500.0f);

        concrete_green = new BlockConcrete();
        concrete_green.setCreativeTab(TabsZP.blocks);
        concrete_green.setUnlocalizedName("concrete_green");
        concrete_green.setHardness(200.0f);
        concrete_green.setResistance(18.5f);

        sandbag = new BlockFalling();
        sandbag.setCreativeTab(TabsZP.blocks);
        sandbag.setUnlocalizedName("sandbag");
        sandbag.setStepSound(Block.soundTypeSand);
        sandbag.setHardness(5.0f);

        concrete_f = new BlockConcreteF();
        concrete_f.setCreativeTab(TabsZP.blocks);
        concrete_f.setUnlocalizedName("concrete_f");
        concrete_f.setHardness(100.0f);
        concrete_f.setResistance(10.0f);

        concrete_f2 = new BlockConcreteF();
        concrete_f2.setCreativeTab(TabsZP.blocks);
        concrete_f2.setUnlocalizedName("concrete_f2");
        concrete_f2.setBlockUnbreakable();
        concrete_f2.setResistance(1500.0f);

        copper_block = new BlockDecorate(Material.iron, 0);
        copper_block.setCreativeTab(TabsZP.blocks);
        copper_block.setUnlocalizedName("copper_block");
        copper_block.setHardness(10.0f);

        titan = new BlockDecorate(Material.iron, 0);
        titan.setCreativeTab(TabsZP.blocks);
        titan.setUnlocalizedName("titan");
        titan.setStepSound(Block.soundTypeMetal);
        titan.setHardness(1500.0f);

        lab = new BlockDecorate(Material.iron, 0);
        lab.setCreativeTab(TabsZP.blocks);
        lab.setUnlocalizedName("lab");
        lab.setStepSound(Block.soundTypeMetal);
        lab.setHardness(1500.0f);
        lab.setResistance(1.5f);

        fortified_cobble = new BlockStone();
        fortified_cobble.setCreativeTab(TabsZP.blocks);
        fortified_cobble.setUnlocalizedName("fortified_cobble");
        fortified_cobble.setResistance(5.0f);
        fortified_cobble.setHardness(8.0f);

        road = new BlockStone();
        road.setCreativeTab(TabsZP.blocks);
        road.setUnlocalizedName("road");
        road.setHardness(2.0f);

        roadSlab = new BlockRoadSlab();
        roadSlab.setCreativeTab(TabsZP.blocks);
        roadSlab.setUnlocalizedName("roadSlab");
        roadSlab.setHardness(2.0f);
        roadSlab.setLightOpacity(1);

        road2 = new BlockStone();
        road2.setCreativeTab(TabsZP.blocks);
        road2.setUnlocalizedName("road2");
        road2.setHardness(2.0f);

        road2Slab = new BlockRoadSlab();
        road2Slab.setCreativeTab(TabsZP.blocks);
        road2Slab.setUnlocalizedName("road2Slab");
        road2Slab.setHardness(2.0f);
        road2Slab.setLightOpacity(1);

        torch1 = new BlockTorch1();
        torch1.setCreativeTab(null);
        torch1.setUnlocalizedName("torch1");
        torch1.setStepSound(Block.soundTypeWood);
        torch1.setLightLevel(1.0f);

        torch2 = new BlockTorch2();
        torch2.setCreativeTab(TabsZP.decorations);
        torch2.setUnlocalizedName("torch2");
        torch2.setStepSound(Block.soundTypeWood);
        torch2.setLightLevel(0.9f);

        torch3 = new BlockTorch3();
        torch3.setCreativeTab(TabsZP.decorations);
        torch3.setUnlocalizedName("torch3");
        torch3.setStepSound(Block.soundTypeWood);
        torch3.setLightLevel(0.8f);

        torch4 = new BlockTorch4();
        torch4.setCreativeTab(TabsZP.decorations);
        torch4.setUnlocalizedName("torch4");
        torch4.setStepSound(Block.soundTypeWood);
        torch4.setLightLevel(0.7f);

        torch5 = new BlockTorch5();
        torch5.setCreativeTab(TabsZP.decorations);
        torch5.setUnlocalizedName("torch5");
        torch5.setStepSound(Block.soundTypeWood);
        torch5.setLightLevel(0.0f);

        lamp = new BlockTorchLamp();
        lamp.setCreativeTab(TabsZP.decorations);
        lamp.setUnlocalizedName("lamp");
        lamp.setLightLevel(1);
        lamp.setStepSound(Block.soundTypeGlass);
        lamp.setHardness(0.3f);

        lamp_off = new BlockTorchLamp();
        lamp_off.setCreativeTab(TabsZP.decorations);
        lamp_off.setUnlocalizedName("lamp_off");
        lamp_off.setStepSound(Block.soundTypeGlass);
        lamp_off.setHardness(0.3f);

        block_lamp = new BlockLamp(true);
        block_lamp.setCreativeTab(TabsZP.decorations);
        block_lamp.setUnlocalizedName("block_lamp");
        block_lamp.setLightLevel(1);
        block_lamp.setStepSound(Block.soundTypeGlass);
        block_lamp.setHardness(0.3f);

        block_lamp_off = new BlockLamp(true);
        block_lamp_off.setCreativeTab(TabsZP.decorations);
        block_lamp_off.setUnlocalizedName("block_lamp_off");
        block_lamp_off.setStepSound(Block.soundTypeGlass);
        block_lamp_off.setHardness(0.3f);

        pumpkinNew = new BlockPumpkinNew();
        pumpkinNew.setCreativeTab(null);
        pumpkinNew.setUnlocalizedName("pumpkinNew");
        pumpkinNew.setStepSound(Block.soundTypeWood);
        pumpkinNew.setLightLevel(1);
        pumpkinNew.setHardness(0.5f);

        //FUN
        c300Block = new BlockC300();
        c300Block.setCreativeTab(TabsZP.fun);
        c300Block.setUnlocalizedName("c300Block");
        c300Block.setStepSound(Block.soundTypeMetal);
        c300Block.setHardness(5.0f);

        pancirBlock = new BlockPancir();
        pancirBlock.setCreativeTab(TabsZP.fun);
        pancirBlock.setUnlocalizedName("pancirBlock");
        pancirBlock.setStepSound(Block.soundTypeMetal);
        pancirBlock.setHardness(5.0f);

        kalibrBlock = new BlockKalibr();
        kalibrBlock.setCreativeTab(TabsZP.fun);
        kalibrBlock.setUnlocalizedName("kalibrBlock");
        kalibrBlock.setStepSound(Block.soundTypeMetal);
        kalibrBlock.setHardness(5.0f);
    }


    public static void register() {
        registerBlock(titan);
        registerBlock(lab);
        registerBlock(scrap);
        registerBlock(uranium);
        registerBlock(armor);
        registerBlock(armor2);
        registerBlock(armored_glass);
        registerBlock(mine);

        registerBlock(wooden_stakes);
        registerBlock(wooden_stakes_stage2);
        registerBlock(wire);
        registerBlock(wire_stage2);
        registerBlock(wire_reinforced);
        registerBlock(wire3, Main.MODID + ":wire");

        registerBlock2(pumpkinNew);
        registerBlock(torch1, Main.MODID + ":torch1");
        registerBlock(torch2, Main.MODID + ":torch2");
        registerBlock(torch3, Main.MODID + ":torch3");
        registerBlock(torch4, Main.MODID + ":torch4");
        registerBlock(torch5, Main.MODID + ":torch5");

        registerBlockMetadata(empty_bookshelf, ItemShelf.class);
        registerBlockMetadata2(gravel_layer, ItemBlockLayer.class, "gravel");
        registerBlockMetadata2(sand_layer, ItemBlockLayer.class, "sand");

        registerBlock(sandbag);
        registerBlock(fortified_cobble);
        registerBlock(fireStone, ":cobblestone");
        registerBlock(temp_stone, ":cobblestone");

        registerBlock(lamp, Main.MODID + ":lamp");
        registerBlock(lamp_off, Main.MODID + ":lamp_off");
        registerBlock(block_lamp);
        registerBlock(block_lamp_off);

        registerBlock(debrisand);
        registerBlock(frozen_dirt);

        registerBlock(cone);

        registerBlock(road, Main.MODID + ":road");
        registerBlock(roadSlab, Main.MODID + ":road");

        registerBlock(road2, Main.MODID + ":road2");
        registerBlock(road2Slab, Main.MODID + ":road2");

        registerBlock(workbench_destroyed);
        registerBlock(furnace_destroyed);

        registerBlock(concrete);
        registerBlock(concrete2);
        registerBlock(concrete3, Main.MODID + ":concrete2");
        registerBlock(concreteStairs, Main.MODID + ":concrete2");
        registerBlock(concrete_green);
        registerBlock2(concrete_f);
        registerBlock2(concrete_f2);
        registerBlock(copper_ore);
        registerBlock(titan_ore);
        registerBlock(copper_block);

        registerBlock(radiation1);
        registerBlock(radiation2);
        registerBlock(gas);
        registerBlock(acidCloud);
        registerBlock(virus);
        registerBlock(hunger);
        registerBlock(barrier);
        registerBlock(living_barrier);
        registerBlock(movement_barrier);

        //FUN
        registerBlock(pancirBlock);
        registerBlock(c300Block);
        registerBlock(kalibrBlock);
    }

    public static void registerBlock(Block block) {
        GameRegistry.registerBlock(block, block.getUnlocalizedName().substring(5));
        block.setTextureName(Main.MODID + ":" + block.getUnlocalizedName().substring(5));
    }

    public static void registerBlock(Block block, String name) {
        GameRegistry.registerBlock(block, block.getUnlocalizedName().substring(5));
        block.setTextureName(name);
    }

    public static void registerBlock2(Block block) {
        GameRegistry.registerBlock(block, block.getUnlocalizedName().substring(5));
    }

    public static void registerBlockMetadata(Block block, Class<? extends ItemBlock> metadata) {
        GameRegistry.registerBlock(block, metadata, block.getUnlocalizedName().substring(5));
    }

    public static void registerBlockMetadata2(Block block, Class<? extends ItemBlock> metadata, String name) {
        GameRegistry.registerBlock(block, metadata, block.getUnlocalizedName().substring(5));
        block.setTextureName(name);
    }
}
