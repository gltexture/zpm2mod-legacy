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
import ru.BouH_.init.FluidsZp;
import ru.BouH_.world.automata.Automata;
import ru.BouH_.world.automata.IndustryGenAutomata;
import ru.BouH_.world.automata.Structures;
import ru.BouH_.world.biome.BiomeIndustry;
import ru.BouH_.world.biome.ICityBiome;
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

public class IndustryGenerator implements ICityGen {
    public final List<AStructure> atomPlantsList = new ArrayList<>();
    public final List<AStructure> plantsList = new ArrayList<>();
    public final List<AStructure> decoList = new ArrayList<>();
    public final List<AStructure> plantTubesList = new ArrayList<>();
    public final List<AStructure> vehiclesList = new ArrayList<>();
    public final List<AStructure> wiresList = new ArrayList<>();
    private final Block footing;
    private final Block footing2;
    private final AGenerator aGenerator;

    private int matrixSize;

    public IndustryGenerator(AGenerator aGenerator) {
        this.footing = BlocksZp.debrisand;
        this.footing2 = BlocksZp.debrisand;
        this.aGenerator = aGenerator;
    }

    public CityType getCityType() {
        return CityType.INDUSTRY;
    }

    public void loadStructures() {
        StructureHolder ind_aplant1 = StructureHolder.create("ind/ind_aplant1");
        StructureHolder ind_plant1 = StructureHolder.create("ind/ind_plant1");
        StructureHolder ind_tube1 = StructureHolder.create("ind/ind_tube1");

        StructureHolder ind_deco1 = StructureHolder.create("ind/ind_deco1");
        StructureHolder ind_deco2 = StructureHolder.create("ind/ind_deco2");
        StructureHolder ind_deco3 = StructureHolder.create("ind/ind_deco3");

        StructureHolder city_car_bus1 = StructureHolder.create("city/city_car_bus1");
        StructureHolder city_car_bus2 = StructureHolder.create("city/city_car_bus2");
        StructureHolder city_car_bus3 = StructureHolder.create("city/city_car_bus3");
        StructureHolder city_car_bus4 = StructureHolder.create("city/city_car_bus4");
        StructureHolder city_car_bus5 = StructureHolder.create("city/city_car_bus5");
        StructureHolder city_car_bus6 = StructureHolder.create("city/city_car_bus6");
        StructureHolder city_car_bus7 = StructureHolder.create("city/city_car_bus7");
        StructureHolder city_car_bus8 = StructureHolder.create("city/city_car_bus8");
        StructureHolder city_car_bus9 = StructureHolder.create("city/city_car_bus9");
        
        StructureHolder ind_wires1 = StructureHolder.create("ind/ind_wires1");
        StructureHolder ind_wires_destr1 = StructureHolder.create("ind/ind_wires_destr1");

        this.atomPlantsList.add(new CityStructure(ind_aplant1, this.footing, this.footing2, 1));

        this.plantTubesList.add(new CityStructure(ind_tube1, this.footing, this.footing2, 1));

        this.plantsList.add(new CityStructure(ind_plant1, this.footing, this.footing2, 1));

        this.wiresList.add(new CommonStructure(ind_wires1, this.footing, this.footing2, 1));
        this.wiresList.add(new CommonStructure(ind_wires_destr1, this.footing, this.footing2, 3));

        this.decoList.add(new CityDecoStructure(ind_deco1, this.footing, this.footing2, 1));
        this.decoList.add(new CityDecoStructure(ind_deco2, this.footing, this.footing2, 1));
        this.decoList.add(new CityDecoStructure(ind_deco3, this.footing, this.footing2, 1));

        this.vehiclesList.add(new CommonStructure(city_car_bus1, null, null, 1));
        this.vehiclesList.add(new CommonStructure(city_car_bus2, null, null, 1));
        this.vehiclesList.add(new CommonStructure(city_car_bus3, null, null, 1));
        this.vehiclesList.add(new CommonStructure(city_car_bus4, null, null, 1));
        this.vehiclesList.add(new CommonStructure(city_car_bus5, null, null, 1));
        this.vehiclesList.add(new CommonStructure(city_car_bus6, null, null, 1));
        this.vehiclesList.add(new CommonStructure(city_car_bus7, null, null, 1));
        this.vehiclesList.add(new CommonStructure(city_car_bus8, null, null, 1));
        this.vehiclesList.add(new CommonStructure(city_car_bus9, null, null, 1));
    }

    public boolean checkRegion(World world, int x, int z) {
        if (world.getWorldInfo().getTerrainType() instanceof WorldTypeCrazyZp) {
            return true;
        }
        return world.getBiomeGenForCoords(x, z) instanceof BiomeIndustry || (world.getBiomeGenForCoords(x, z) == BiomeGenBase.river || world.getBiomeGenForCoords(x, z) == BiomeGenBase.beach);
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
        return 64;
    }

    @Override
    public int defaultMatrixSize() {
        return 4;
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
                        if (world.getBlock(i, startY + 1, o) instanceof BlockLayer || world.getBlock(i, startY + 1, o) instanceof BlockBush) {
                            world.setBlockToAir(i, startY + 1, o);
                        }
                        world.setBlockToAir(i, startY, o);
                    }
                    world.setBlock(i, minY, o, Main.rand.nextFloat() <= 0.01f ? FluidsZp.toxicwater_block : this.footing);
                    int startY2 = minY - 1;
                    while (!world.getBlock(i, startY2, o).isOpaqueCube()) {
                        world.setBlock(i, startY2, o, this.footing2);
                        startY2 -= 1;
                    }
                }
            }
        }
        for (int i = x - wider + 1; i <= maxX + wider - 1; i++) {
            for (int j = 1; j < 5; j++) {
                if (random.nextFloat() <= 0.05f) {
                    break;
                }
                world.setBlock(i, minY + j, z - wider + 1, BlocksZp.concrete_f);
                world.setBlock(i, minY + j, maxZ + wider - 1, BlocksZp.concrete_f);
            }
        }
        for (int i = z - wider + 1; i <= maxZ + wider - 1; i++) {
            for (int j = 1; j < 5; j++) {
                if (random.nextFloat() <= 0.05f) {
                    break;
                }
                world.setBlock(x - wider + 1, minY + j, i, BlocksZp.concrete_f);
                world.setBlock(maxX + wider - 1, minY + j, i, BlocksZp.concrete_f);
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
                        if (random.nextFloat() <= 0.003f) {
                            world.setBlock(i, minY + 1, o, Main.rand.nextBoolean() ? BlocksZp.furnace_destroyed : BlocksZp.workbench_destroyed, Main.rand.nextInt(6) + 1, 2);
                        } else if (random.nextFloat() <= 0.003f) {
                            world.setBlock(i, minY + 1, o, BlocksZp.scrap);
                        } else if (random.nextFloat() <= 0.2f) {
                            if (world.getBlock(i, minY, o) == this.footing) {
                                if (world.isAirBlock(i, minY + 1, o)) {
                                    if (random.nextFloat() <= 0.5f) {
                                        world.setBlock(i, minY + 1, o, random.nextFloat() <= 0.7f ? BlocksZp.gravel_layer : BlocksZp.sand_layer, 0, 2);
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
        IndustryGenAutomata industryGenAutomata = new IndustryGenAutomata(this.getMatrixSize(world));
        industryGenAutomata.generate();
        this.genRoads(random, industryGenAutomata, world, x, y, z);
        this.genBuildings(random, industryGenAutomata, world, x, y, z);
        this.finalDecorate(random, world, x, y, z);
    }

    private void genRoads(Random random, Automata automata, World world, int x, int y, int z) {
        for (int i = 0; i < automata.getSize(); i++) {
            for (int j = 0; j < automata.getSize(); j++) {
                int newX = (i * this.getScale(world)) + x;
                int newZ = (j * this.getScale(world)) + z;
                if (automata.getStructure(i, j) == Structures.ROAD_VISITED) {
                    if (automata.checkStructure(i + 1, j, Structures.ROAD_VISITED) || automata.checkStructure(i - 1, j, Structures.ROAD_VISITED)) {
                        if (!automata.checkStructure(i, j + 1, Structures.ROAD_VISITED)) {
                            this.aGenerator.buildStructure(random, this.wiresList, world, newX, y, newZ + 16, 3);
                        }
                        if (!automata.checkStructure(i, j - 1, Structures.ROAD_VISITED)) {
                            this.aGenerator.buildStructure(random, this.wiresList, world, newX, y, newZ - 16, 3);
                        }
                    } else if (automata.checkStructure(i, j + 1, Structures.ROAD_VISITED) || automata.checkStructure(i, j - 1, Structures.ROAD_VISITED)) {
                        if (!automata.checkStructure(i + 1, j, Structures.ROAD_VISITED)) {
                            this.aGenerator.buildStructure(random, this.wiresList, world, newX + 16, y, newZ, 0);
                        }
                        if (!automata.checkStructure(i - 1, j, Structures.ROAD_VISITED)) {
                            this.aGenerator.buildStructure(random, this.wiresList, world, newX - 16, y, newZ, 0);
                        }
                    }

                    if (automata.checkStructure(i + 1, j, Structures.ROAD_VISITED)) {
                        for (int o = -6; o < this.getScale(world) + 7; o += 1) {
                            this.gen13x1Road(random, world, newX + o, y, newZ);
                            if (o < this.getScale(world) + 4 && o > -4 && o % 4 == 0) {
                                if (random.nextFloat() <= 0.65f) {
                                    world.setBlock(newX + o, y, newZ, BlocksZp.road2);
                                }
                            }
                        }
                    }
                    if (automata.checkStructure(i, j + 1, Structures.ROAD_VISITED)) {
                        for (int o = -6; o < this.getScale(world) + 7; o += 1) {
                            this.gen1x13Road(random, world, newX, y, newZ + o);
                            if (o < this.getScale(world) + 4 && o > -4 && o % 4 == 0) {
                                if (random.nextFloat() <= 0.65f) {
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
                        this.aGenerator.buildStructure(random, this.plantsList, world, newX, y, newZ, rotation);
                        break;
                    }
                    case GREEN: {
                        this.aGenerator.buildStructure(random, this.plantTubesList, world, newX, y, newZ, rotation);
                        break;
                    }
                    case BLUE: {
                        this.aGenerator.buildStructure(random, this.atomPlantsList, world, newX, y, newZ, rotation);
                        break;
                    }
                }
            }
        }
    }

    private void gen1x13Road(Random random, World world, int x, int y, int z) {
        for (int i = -6; i < 7; i++) {
            if (i != 0) {
                if (random.nextFloat() <= 0.005f) {
                    this.aGenerator.buildStructure(random, this.vehiclesList, world, x + i, y + 1, z, i < 0 ? 2 : 0);
                }
            }
            if (world.getBlock(x + i, y, z) != BlocksZp.road2) {
                if (random.nextFloat() <= 0.6f) {
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

    private void gen13x1Road(Random random, World world, int x, int y, int z) {
        for (int j = -6; j < 7; j++) {
            if (j != 0) {
                if (random.nextFloat() <= 0.005f) {
                    this.aGenerator.buildStructure(random, this.vehiclesList, world, x, y + 1, z + j, j < 0 ? 3 : 1);
                }
            }
            if (world.getBlock(x, y, z + j) != BlocksZp.road2) {
                if (random.nextFloat() <= 0.6f) {
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
