package ru.BouH_.world.generator.cities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenTrees;
import ru.BouH_.Main;
import ru.BouH_.blocks.BlockLayer;
import ru.BouH_.init.BlocksZp;
import ru.BouH_.world.automata.Automata;
import ru.BouH_.world.automata.MilitaryGenAutomata;
import ru.BouH_.world.automata.Structures;
import ru.BouH_.world.biome.BiomeMilitary;
import ru.BouH_.world.generator.AGenerator;
import ru.BouH_.world.structures.base.AStructure;
import ru.BouH_.world.structures.base.StructureHolder;
import ru.BouH_.world.structures.building.CityDecoStructure;
import ru.BouH_.world.structures.building.CityStructure;
import ru.BouH_.world.structures.building.CommonStructure;
import ru.BouH_.world.type.WorldTypeCrazyZp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MilitaryGenerator implements ICityGen {
    public final List<AStructure> mainBuildings = new ArrayList<>();
    public final List<AStructure> hangarsList = new ArrayList<>();
    public final List<AStructure> decoList = new ArrayList<>();
    public final List<AStructure> militaryList = new ArrayList<>();
    public final List<AStructure> vehiclesList = new ArrayList<>();
    private final Block footing;
    private final Block footing2;
    private final AGenerator aGenerator;
    private int matrixSize;

    public MilitaryGenerator(AGenerator aGenerator) {
        this.footing = BlocksZp.frozen_dirt;
        this.footing2 = BlocksZp.frozen_dirt;
        this.aGenerator = aGenerator;
    }

    public CityType getCityType() {
        return CityType.MILITARY;
    }

    public void loadStructures() {
        StructureHolder mil_hangar1 = StructureHolder.create("mil/mil_hangar1");
        StructureHolder mil_hangar2 = StructureHolder.create("mil/mil_hangar2");
        StructureHolder mil_hangar3 = StructureHolder.create("mil/mil_hangar3");
        StructureHolder mil_bunker1 = StructureHolder.create("mil/mil_bunker1");

        StructureHolder mil_house1 = StructureHolder.create("mil/mil_house1");
        StructureHolder mil_house2 = StructureHolder.create("mil/mil_house2");
        StructureHolder mil_house3 = StructureHolder.create("mil/mil_house3");
        StructureHolder mil_house4 = StructureHolder.create("mil/mil_house4");

        StructureHolder mil_main1 = StructureHolder.create("mil/mil_main1");
        StructureHolder mil_main2 = StructureHolder.create("mil/mil_main2");
        StructureHolder mil_main3 = StructureHolder.create("mil/mil_main3");

        StructureHolder mil_tent1 = StructureHolder.create("mil/mil_tent1");
        StructureHolder mil_tents1 = StructureHolder.create("mil/mil_tents1");
        StructureHolder mil_tents2 = StructureHolder.create("mil/mil_tents2");

        StructureHolder city_car_mil1 = StructureHolder.create("city/city_car_mil1");
        StructureHolder city_car_tank1 = StructureHolder.create("city/city_car_tank1");

        StructureHolder mil_transform1 = StructureHolder.create("mil/mil_transform1");

        StructureHolder mil_tower1 = StructureHolder.create("mil/mil_tower1");

        //StructureHolder.reconvert("mil/mil_house3", mil_house3);
        //StructureHolder.reconvert("mil/mil_house4", mil_house4);
        //StructureHolder.reconvert("mil/mil_bunker1", mil_bunker1);

        this.vehiclesList.add(new CommonStructure(city_car_mil1, null, null, 10));
        this.vehiclesList.add(new CommonStructure(city_car_tank1, null, null, 1));

        this.hangarsList.add(new CityStructure(mil_hangar1, this.footing, this.footing2, 1));
        this.hangarsList.add(new CityStructure(mil_hangar2, this.footing, this.footing2, 1));
        this.hangarsList.add(new CityStructure(mil_hangar3, this.footing, this.footing2, 1));
        this.hangarsList.add(new CityStructure(mil_bunker1, this.footing, this.footing2, 1));

        this.militaryList.add(new CityStructure(mil_tent1, this.footing, this.footing2, 6));
        this.militaryList.add(new CityStructure(mil_tents1, this.footing, this.footing2, 6));
        this.militaryList.add(new CityStructure(mil_tents2, this.footing, this.footing2, 6));
        this.militaryList.add(new CityStructure(mil_transform1, this.footing, this.footing2, 1));
        this.militaryList.add(new CityStructure(mil_house1, this.footing, this.footing2, 2));
        this.militaryList.add(new CityStructure(mil_house2, this.footing, this.footing2, 2));
        this.militaryList.add(new CityStructure(mil_house3, this.footing, this.footing2, 2));
        this.militaryList.add(new CityStructure(mil_house4, this.footing, this.footing2, 2));

        this.mainBuildings.add(new CityStructure(mil_main1, this.footing, this.footing2, 1));
        this.mainBuildings.add(new CityStructure(mil_main2, this.footing, this.footing2, 1));
        this.mainBuildings.add(new CityStructure(mil_main3, this.footing, this.footing2, 1));

        this.decoList.add(new CityDecoStructure(mil_tower1, this.footing, this.footing2, 1));
    }

    public boolean checkRegion(World world, int x, int z) {
        if (SpecialGenerator.getTerType(world) instanceof WorldTypeCrazyZp) {
            return true;
        }
        return world.getBiomeGenForCoords(x, z) instanceof BiomeMilitary || (world.getBiomeGenForCoords(x, z) == BiomeGenBase.river || world.getBiomeGenForCoords(x, z) == BiomeGenBase.beach);
    }

    public CityCheckResult tryGenCity(World world, int x, int startY, int z) {
        int threshold = 1;
        int steps = this.getMatrixSize(world) * this.getScale(world) / 16;
        for (int i = threshold; i < steps - threshold; i++) {
            for (int j = threshold; j < steps - threshold; j++) {
                if (!this.checkRegion(world, x + i * 16, z + j * 16)) {
                    return null;
                }
            }
        }

        return new CityCheckResult(this.getCityType(), x, 63, z);
    }

    @Override
    public int getScale(World world) {
        return 32;
    }

    @Override
    public int defaultMatrixSize() {
        return 6;
    }

    @Override
    public void setMatrixSize(int size) {
        this.matrixSize = size;
    }

    @Override
    public int getMatrixSize(World world) {
        return this.matrixSize;
    }

    private void clearRegion(Random random, World world, int x, int minY, int z) {
        int maxX = x + this.getScale(world) * (this.getMatrixSize(world) - 1);
        int maxZ = z + this.getScale(world) * (this.getMatrixSize(world) - 1);
        int wider = this.getScale(world) / 2 + 2;
        for (int i = x - wider; i <= maxX + wider; i++) {
            for (int o = z - wider; o <= maxZ + wider; o++) {
                if (!((i == x - wider || i == maxX + wider || o == z - wider || o == maxZ + wider) && random.nextBoolean())) {
                    for (int startY = this.findY(world, i, o); startY >= minY; startY--) {
                        if (world.getBlock(i, minY + 1, o) instanceof BlockLayer || world.getBlock(i, startY + 1, o) instanceof BlockBush) {
                            world.setBlockToAir(i, startY + 1, o);
                        }
                        world.setBlockToAir(i, startY, o);
                    }
                    world.setBlock(i, minY, o, this.footing);
                    int startY2 = minY - 1;
                    while (!world.getBlock(i, startY2, o).isOpaqueCube()) {
                        world.setBlock(i, startY2, o, this.footing2);
                        startY2 -= 1;
                    }
                }
            }
        }
        for (int i = x - wider + 1; i <= maxX + wider - 1; i++) {
            for (int j = 1; j < 7; j++) {
                if (j > 2 && random.nextFloat() <= 0.05f) {
                    break;
                }
                Block block = (j <= 3 ? BlocksZp.concrete_f : j < 6 ? Blocks.iron_bars : (random.nextFloat() <= 0.7f ? BlocksZp.wire_stage2 : BlocksZp.wire));
                world.setBlock(i, minY + j, z - wider + 1, block);
                world.setBlock(i, minY + j, maxZ + wider - 1, block);
            }
        }
        for (int i = z - wider + 1; i <= maxZ + wider - 1; i++) {
            for (int j = 1; j < 7; j++) {
                if (j > 3 && random.nextFloat() <= 0.05f) {
                    break;
                }
                Block block = (j <= 3 ? BlocksZp.concrete_f : j < 6 ? Blocks.iron_bars : (random.nextFloat() <= 0.7f ? BlocksZp.wire_stage2 : BlocksZp.wire));
                world.setBlock(x - wider + 1, minY + j, i, block);
                world.setBlock(maxX + wider - 1, minY + j, i, block);
            }
        }
    }

    private void finalDecorate(Random random, World world, int x, int minY, int z) {
        int maxX = x + this.getScale(world) * (this.getMatrixSize(world) - 1);
        int maxZ = z + this.getScale(world) * (this.getMatrixSize(world) - 1);
        int wider = this.getScale(world) / 2;
        for (int i = x; i <= maxX; i++) {
            for (int o = z; o <= maxZ; o++) {
                if (random.nextFloat() <= 1.0e-3f) {
                    this.aGenerator.buildStructure(random, this.decoList, world, i, minY, o, random.nextInt(4));
                }
            }
        }
        for (int i = x - wider; i <= maxX + wider; i++) {
            for (int o = z - wider; o <= maxZ + wider; o++) {
                if (world.getBlock(i, minY, o) == this.footing) {
                    if (random.nextFloat() <= 0.03f) {
                        if (world.getBlock(i, minY + 1, o) instanceof BlockLayer || world.getBlock(i, minY + 1, o) instanceof BlockBush) {
                            world.setBlockToAir(i, minY + 1, o);
                        }
                        if (random.nextBoolean()) {
                            world.setBlock(i, minY, o, Blocks.sand, 2, 2);
                        } else {
                            world.setBlock(i, minY, o, Blocks.gravel);
                        }
                    }
                    if (world.isAirBlock(i, minY + 1, o)) {
                        if (random.nextFloat() <= 0.01f) {
                            world.setBlock(i, minY + 1, o, Blocks.leaves);
                        } else if (random.nextFloat() <= 0.0025f) {
                            WorldGenAbstractTree worldgenabstracttree = new WorldGenTrees(false);
                            worldgenabstracttree.setScale(1.0D, 1.0D, 1.0D);
                            worldgenabstracttree.generate(world, random, i, minY + 1, o);
                        } else if (random.nextFloat() <= 0.2f) {
                            if (world.getBlock(i, minY, o) == this.footing) {
                                if (world.isAirBlock(i, minY + 1, o)) {
                                    if (random.nextFloat() <= 5.0e-2f) {
                                        world.setBlock(i, minY + 1, o, BlocksZp.mine, 0, 2);
                                    } else if (random.nextFloat() <= 0.00125f) {
                                        world.setBlock(i, minY + 1, o, BlocksZp.sandbag);
                                    } else if (random.nextFloat() <= 0.25f) {
                                        world.setBlock(i, minY + 1, o, BlocksZp.gravel_layer, 0, 2);
                                    } else {
                                        world.setBlock(i, minY + 1, o, Blocks.deadbush, 0, 2);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private int findY(World world, int x, int z) {
        return world.getPrecipitationHeight(x, z) - 1;
    }

    public void genCity(Random random, World world, int x, int y, int z) {
        this.clearRegion(random, world, x, y, z);
        for (Object o : world.playerEntities) {
            EntityPlayer entityPlayer = (EntityPlayer) o;
            if (entityPlayer.posX >= x && entityPlayer.posZ >= z && entityPlayer.posX <= x + (this.getScale(world) * this.getMatrixSize(world)) && entityPlayer.posZ <= z + (this.getScale(world) * this.getMatrixSize(world))) {
                entityPlayer.setPosition(x, this.findY(world, x, z), z);
            }
        }
        MilitaryGenAutomata militaryGenAutomata = new MilitaryGenAutomata(this.getMatrixSize(world));
        militaryGenAutomata.generate();
        this.genRoads(random, militaryGenAutomata, world, x, y, z);
        this.genBuildings(random, militaryGenAutomata, world, x, y, z);
        this.finalDecorate(random, world, x, y, z);
    }

    private void genRoads(Random random, Automata automata, World world, int x, int y, int z) {
        for (int i = 0; i < automata.getSize(); i++) {
            for (int j = 0; j < automata.getSize(); j++) {
                int newX = (i * this.getScale(world)) + x;
                int newZ = (j * this.getScale(world)) + z;
                if (automata.getStructure(i, j) == Structures.ROAD_VISITED) {
                    if (automata.checkStructure(i + 1, j, Structures.ROAD_VISITED)) {
                        for (int o = -5; o < this.getScale(world) + 6; o += 1) {
                            this.gen11x1Road(random, world, newX + o, y, newZ);
                            if (o < this.getScale(world) + 4 && o > -4 && o % 4 == 0) {
                                if (random.nextFloat() <= 0.8f) {
                                    world.setBlock(newX + o, y, newZ, BlocksZp.road2);
                                }
                            }
                        }
                    }
                    if (automata.checkStructure(i, j + 1, Structures.ROAD_VISITED)) {
                        for (int o = -5; o < this.getScale(world) + 6; o += 1) {
                            this.gen1x11Road(random, world, newX, y, newZ + o);
                            if (o < this.getScale(world) + 4 && o > -4 && o % 4 == 0) {
                                if (random.nextFloat() <= 0.8f) {
                                    world.setBlock(newX, y, newZ + o, BlocksZp.road2);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void genBuildings(Random random, Automata automata, World world, int x, int y, int z) {
        for (int i = 0; i < automata.getSize(); i++) {
            for (int j = 0; j < automata.getSize(); j++) {
                if (automata.checkNeighborWeight(Structures.ROAD_VISITED, i, j) == 0) {
                    continue;
                }
                int newX = (i * this.getScale(world)) + x;
                int newZ = (j * this.getScale(world)) + z;
                int rotation = 0;
                if (automata.checkStructure(i + 1, j, Structures.ROAD_VISITED)) {
                    rotation = 1;
                } else if (automata.checkStructure(i, j + 1, Structures.ROAD_VISITED)) {
                    rotation = 2;
                } else if (automata.checkStructure(i - 1, j, Structures.ROAD_VISITED)) {
                    rotation = 3;
                }
                switch (automata.getStructure(i, j)) {
                    case WHITE: {
                        this.aGenerator.buildStructure(random, this.militaryList, world, newX, y, newZ, rotation);
                        break;
                    }
                    case GREEN: {
                        this.aGenerator.buildStructure(random, this.hangarsList, world, newX, y, newZ, rotation);
                        break;
                    }
                    case GRAY: {
                        this.aGenerator.buildStructure(random, this.mainBuildings, world, newX, y, newZ, rotation);
                        break;
                    }
                }
            }
        }
    }

    private void gen1x11Road(Random random, World world, int x, int y, int z) {
        for (int i = -5; i < 6; i++) {
            if (i != 0) {
                if (random.nextFloat() <= 1.0e-3f) {
                    this.aGenerator.buildStructure(random, this.vehiclesList, world, x + i, y + 1, z, i < 0 ? 2 : 0);
                }
            }
            if (world.getBlock(x + i, y, z) != BlocksZp.road2) {
                if (random.nextFloat() <= 0.8f) {
                    if (random.nextFloat() <= 5.0e-3f) {
                        if (world.isAirBlock(x + i, y + 1, z)) {
                            world.setBlock(x + i, y + 1, z, BlocksZp.cone);
                        }
                    }
                    world.setBlock(x + i, y, z, BlocksZp.road);
                } else {
                    if (random.nextFloat() <= 0.5f) {
                        world.setBlock(x + i, y, z, this.footing);
                    } else if (random.nextFloat() <= 0.9f) {
                        world.setBlock(x + i, y, z, Blocks.gravel);
                    } else {
                        world.setBlock(x + i, y, z, Blocks.cobblestone);
                    }
                }
            }
        }
    }

    private void gen11x1Road(Random random, World world, int x, int y, int z) {
        for (int j = -5; j < 6; j++) {
            if (j != 0) {
                if (random.nextFloat() <= 1.0e-3f) {
                    this.aGenerator.buildStructure(random, this.vehiclesList, world, x, y + 1, z + j, j < 0 ? 3 : 1);
                }
            }
            if (world.getBlock(x, y, z + j) != BlocksZp.road2) {
                if (random.nextFloat() <= 0.8f) {
                    if (random.nextFloat() <= 5.0e-3f) {
                        if (world.isAirBlock(x, y + 1, z + j)) {
                            world.setBlock(x, y + 1, z + j, BlocksZp.cone);
                        }
                    }
                    world.setBlock(x, y, z + j, BlocksZp.road);
                } else {
                    if (random.nextFloat() <= 0.5f) {
                        world.setBlock(x, y, z + j, this.footing);
                    } else if (random.nextFloat() <= 0.9f) {
                        world.setBlock(x, y, z + j, Blocks.gravel);
                    } else {
                        world.setBlock(x, y, z + j, Blocks.cobblestone);
                    }
                }
            }
        }
    }
}
