package ru.BouH_.world.generator.cities;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenTrees;
import ru.BouH_.init.BlocksZp;
import ru.BouH_.world.automata.Automata;
import ru.BouH_.world.automata.Structures;
import ru.BouH_.world.automata.TownGenAutomata;
import ru.BouH_.world.biome.BiomeCity;
import ru.BouH_.world.biome.BiomeIndustry;
import ru.BouH_.world.generator.AGenerator;
import ru.BouH_.world.structures.base.AStructure;
import ru.BouH_.world.structures.base.StructureHolder;
import ru.BouH_.world.structures.building.CityStructure;
import ru.BouH_.world.structures.building.CommonStructure;
import ru.BouH_.world.type.WorldTypeCrazyZp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CityGenerator implements ICityGen {
    public final List<AStructure> cityCommonList = new ArrayList<>();
    public final List<AStructure> cityVehiclesList = new ArrayList<>();
    public final List<AStructure> cityGreenList = new ArrayList<>();
    public final List<AStructure> cityPoliceList = new ArrayList<>();
    public final List<AStructure> citySkyScrapersList = new ArrayList<>();
    public final List<AStructure> cityMedList = new ArrayList<>();
    private final Block footing;
    private final Block footing2;
    private final AGenerator aGenerator;

    private int matrixSize;

    public CityGenerator(AGenerator aGenerator) {
        this.footing = Blocks.grass;
        this.footing2 = Blocks.stone;
        this.aGenerator = aGenerator;
    }

    public CityType getCityType() {
        return CityType.COMMON;
    }

    public void loadStructures() {
        StructureHolder city_basket1 = StructureHolder.create("city/city_basket1");
        StructureHolder city_park1 = StructureHolder.create("city/city_park1");
        StructureHolder city_park2 = StructureHolder.create("city/city_park2");
        StructureHolder city_park3 = StructureHolder.create("city/city_park3");
        StructureHolder city_foot1 = StructureHolder.create("city/city_foot1");
        StructureHolder city_gym1 = StructureHolder.create("city/city_gym1");
        StructureHolder city_arena1 = StructureHolder.create("city/city_arena1");

        StructureHolder city_med_park1 = StructureHolder.create("city/city_med_park1");
        StructureHolder city_mil_park1 = StructureHolder.create("city/city_mil_park1");

        StructureHolder city_med1 = StructureHolder.create("city/city_med1");

        StructureHolder city_car_bus1 = StructureHolder.create("city/city_car_bus1");
        StructureHolder city_car_bus2 = StructureHolder.create("city/city_car_bus2");
        StructureHolder city_car_bus3 = StructureHolder.create("city/city_car_bus3");
        StructureHolder city_car_bus4 = StructureHolder.create("city/city_car_bus4");
        StructureHolder city_car_bus5 = StructureHolder.create("city/city_car_bus5");
        StructureHolder city_car_bus6 = StructureHolder.create("city/city_car_bus6");
        StructureHolder city_car_bus7 = StructureHolder.create("city/city_car_bus7");
        StructureHolder city_car_bus8 = StructureHolder.create("city/city_car_bus8");
        StructureHolder city_car_bus9 = StructureHolder.create("city/city_car_bus9");

        StructureHolder city_car_pol1 = StructureHolder.create("city/city_car_pol1");

        StructureHolder city_car_mil1 = StructureHolder.create("city/city_car_mil1");
        StructureHolder city_car_tank1 = StructureHolder.create("city/city_car_tank1");
        StructureHolder city_car_fire1 = StructureHolder.create("city/city_car_fire1");
        StructureHolder city_car_med1 = StructureHolder.create("city/city_car_med1");

        StructureHolder city_car1 = StructureHolder.create("city/city_car1");
        StructureHolder city_car2 = StructureHolder.create("city/city_car2");
        StructureHolder city_car3 = StructureHolder.create("city/city_car3");
        StructureHolder city_car4 = StructureHolder.create("city/city_car4");
        StructureHolder city_car5 = StructureHolder.create("city/city_car5");
        StructureHolder city_car6 = StructureHolder.create("city/city_car6");

        StructureHolder city_house1 = StructureHolder.create("city/city_house1");
        StructureHolder city_house2 = StructureHolder.create("city/city_house2");
        StructureHolder city_house3 = StructureHolder.create("city/city_house3");
        StructureHolder city_house4 = StructureHolder.create("city/city_house4");
        StructureHolder city_house5 = StructureHolder.create("city/city_house5");
        StructureHolder city_house6 = StructureHolder.create("city/city_house6");
        StructureHolder city_house7 = StructureHolder.create("city/city_house7");
        StructureHolder city_house8 = StructureHolder.create("city/city_house8");
        StructureHolder city_house9 = StructureHolder.create("city/city_house9");
        StructureHolder city_house10 = StructureHolder.create("city/city_house10");
        StructureHolder city_house11 = StructureHolder.create("city/city_house11");
        StructureHolder city_house12 = StructureHolder.create("city/city_house12");
        StructureHolder city_house13 = StructureHolder.create("city/city_house13");
        StructureHolder city_house14 = StructureHolder.create("city/city_house14");
        StructureHolder city_house15 = StructureHolder.create("city/city_house15");
        StructureHolder city_house16 = StructureHolder.create("city/city_house16");
        StructureHolder city_house17 = StructureHolder.create("city/city_house17");
        StructureHolder city_house18 = StructureHolder.create("city/city_house18");
        StructureHolder city_house19 = StructureHolder.create("city/city_house19");
        StructureHolder city_house20 = StructureHolder.create("city/city_house20");
        StructureHolder city_house21 = StructureHolder.create("city/city_house21");
        StructureHolder city_house22 = StructureHolder.create("city/city_house22");
        StructureHolder city_house23 = StructureHolder.create("city/city_house23");
        StructureHolder city_house24 = StructureHolder.create("city/city_house24");
        StructureHolder city_house25 = StructureHolder.create("city/city_house25");
        StructureHolder city_house26 = StructureHolder.create("city/city_house26");
        StructureHolder city_house27 = StructureHolder.create("city/city_house27");
        StructureHolder city_house28 = StructureHolder.create("city/city_house28");
        StructureHolder city_house29 = StructureHolder.create("city/city_house29");

        StructureHolder city_houses1 = StructureHolder.create("city/city_houses1");
        StructureHolder city_houses2 = StructureHolder.create("city/city_houses2");

        StructureHolder city_house_special1 = StructureHolder.create("city/city_house_special1");
        StructureHolder city_church1 = StructureHolder.create("city/city_church1");

        StructureHolder city_pol1 = StructureHolder.create("city/city_pol1");
        StructureHolder city_pol2 = StructureHolder.create("city/city_pol2");
        StructureHolder city_shop1 = StructureHolder.create("city/city_shop1");
        StructureHolder city_shop2 = StructureHolder.create("city/city_shop2");
        StructureHolder city_shop3 = StructureHolder.create("city/city_shop3");
        StructureHolder city_shop4 = StructureHolder.create("city/city_shop4");
        StructureHolder city_shop5 = StructureHolder.create("city/city_shop5");
        StructureHolder city_shop6 = StructureHolder.create("city/city_shop6");
        StructureHolder city_shop7 = StructureHolder.create("city/city_shop7");
        StructureHolder city_med_clinic1 = StructureHolder.create("city/city_med_clinic1");
        StructureHolder city_megashop1 = StructureHolder.create("city/city_megashop1");
        StructureHolder city_rest1 = StructureHolder.create("city/city_rest1");
        StructureHolder city_rest2 = StructureHolder.create("city/city_rest2");
        StructureHolder city_pool1 = StructureHolder.create("city/city_pool1");
        StructureHolder city_dead1 = StructureHolder.create("city/city_dead1");
        StructureHolder city_warehouse1 = StructureHolder.create("city/city_warehouse1");
        StructureHolder city_gas1 = StructureHolder.create("city/city_gas1");
        StructureHolder city_gas2 = StructureHolder.create("city/city_gas2");
        StructureHolder city_fire1 = StructureHolder.create("city/city_fire1");

        StructureHolder city_sky1 = StructureHolder.create("city/city_sky1");
        StructureHolder city_sky2 = StructureHolder.create("city/city_sky2");
        StructureHolder city_sky3 = StructureHolder.create("city/city_sky3");
        StructureHolder city_sky4 = StructureHolder.create("city/city_sky4");
        StructureHolder city_sky5 = StructureHolder.create("city/city_sky5");
        StructureHolder city_sky6 = StructureHolder.create("city/city_sky6");
        StructureHolder city_sky7 = StructureHolder.create("city/city_sky7");
        StructureHolder city_sky8 = StructureHolder.create("city/city_sky8");
        StructureHolder city_sky9 = StructureHolder.create("city/city_sky9");
        StructureHolder city_sky10 = StructureHolder.create("city/city_sky10");
        StructureHolder city_sky11 = StructureHolder.create("city/city_sky11");

       //StructureHolder.reconvert("city/city_sky8", city_sky8);
       //StructureHolder.reconvert("city/city_sky9", city_sky9);
       //StructureHolder.reconvert("city/city_sky10", city_sky10);
       //StructureHolder.reconvert("city/city_sky11", city_sky11);
       //StructureHolder.reconvert("city/city_house20", city_house20);
       //StructureHolder.reconvert("city/city_house21", city_house21);
       //StructureHolder.reconvert("city/city_house22", city_house22);
       //StructureHolder.reconvert("city/city_house23", city_house23);
       //StructureHolder.reconvert("city/city_house24", city_house24);
       //StructureHolder.reconvert("city/city_house25", city_house25);
       //StructureHolder.reconvert("city/city_house26", city_house26);
       //StructureHolder.reconvert("city/city_house27", city_house27);
       //StructureHolder.reconvert("city/city_house28", city_house28);
       //StructureHolder.reconvert("city/city_house29", city_house29);
       //StructureHolder.reconvert("city/city_shop2", city_shop2);
       //StructureHolder.reconvert("city/city_gas2", city_gas2);


        StructureHolder city_ruins1 = StructureHolder.create("city/city_ruins1");

        this.cityCommonList.add(new CityStructure(city_house1, 4));
        this.cityCommonList.add(new CityStructure(city_house2, 4));
        this.cityCommonList.add(new CityStructure(city_house3, 4));
        this.cityCommonList.add(new CityStructure(city_house4, 4));
        this.cityCommonList.add(new CityStructure(city_house5, 4));
        this.cityCommonList.add(new CityStructure(city_house6, 4));
        this.cityCommonList.add(new CityStructure(city_house7, 4));
        this.cityCommonList.add(new CityStructure(city_house8, 4));
        this.cityCommonList.add(new CityStructure(city_house9, 4));
        this.cityCommonList.add(new CityStructure(city_house10, 4));
        this.cityCommonList.add(new CityStructure(city_house11, 4));
        this.cityCommonList.add(new CityStructure(city_house12, 4));
        this.cityCommonList.add(new CityStructure(city_house13, 4));
        this.cityCommonList.add(new CityStructure(city_house14, 4));
        this.cityCommonList.add(new CityStructure(city_house15, 4));
        this.cityCommonList.add(new CityStructure(city_house16, 4));
        this.cityCommonList.add(new CityStructure(city_house17, 4));
        this.cityCommonList.add(new CityStructure(city_house18, 4));
        this.cityCommonList.add(new CityStructure(city_house19, 4));
        this.cityCommonList.add(new CityStructure(city_house20, 4));
        this.cityCommonList.add(new CityStructure(city_house21, 4));
        this.cityCommonList.add(new CityStructure(city_house22, 4));
        this.cityCommonList.add(new CityStructure(city_house23, 4));
        this.cityCommonList.add(new CityStructure(city_house24, 4));
        this.cityCommonList.add(new CityStructure(city_house25, 4));
        this.cityCommonList.add(new CityStructure(city_house26, 4));
        this.cityCommonList.add(new CityStructure(city_house27, 4));
        this.cityCommonList.add(new CityStructure(city_house28, 4));
        this.cityCommonList.add(new CityStructure(city_house29, 4));
        this.cityCommonList.add(new CityStructure(city_shop1, 2));
        this.cityCommonList.add(new CityStructure(city_shop2, 2));
        this.cityCommonList.add(new CityStructure(city_shop3, 2));
        this.cityCommonList.add(new CityStructure(city_shop4, 2));
        this.cityCommonList.add(new CityStructure(city_shop5, 2));
        this.cityCommonList.add(new CityStructure(city_shop6, 2));
        this.cityCommonList.add(new CityStructure(city_shop7, 2));
        this.cityCommonList.add(new CityStructure(city_megashop1, 2));
        this.cityCommonList.add(new CityStructure(city_warehouse1, 2));
        this.cityCommonList.add(new CityStructure(city_med_clinic1, 1));
        this.cityCommonList.add(new CityStructure(city_gas1, 2));
        this.cityCommonList.add(new CityStructure(city_gas2, 2));
        this.cityCommonList.add(new CityStructure(city_fire1, 2));
        this.cityCommonList.add(new CityStructure(city_rest1, 2));
        this.cityCommonList.add(new CityStructure(city_rest2, 2));
        this.cityCommonList.add(new CityStructure(city_pool1, 2));
        this.cityCommonList.add(new CityStructure(city_church1, 2));
        this.cityCommonList.add(new CityStructure(city_houses1, 2));
        this.cityCommonList.add(new CityStructure(city_houses2, 2));
        this.cityCommonList.add(new CityStructure(city_ruins1, 2));
        this.cityCommonList.add(new CityStructure(city_house_special1, 1));

        this.cityVehiclesList.add(new CommonStructure(city_car1, null, null, 8));
        this.cityVehiclesList.add(new CommonStructure(city_car2, null, null, 8));
        this.cityVehiclesList.add(new CommonStructure(city_car3, null, null, 8));
        this.cityVehiclesList.add(new CommonStructure(city_car4, null, null, 8));
        this.cityVehiclesList.add(new CommonStructure(city_car5, null, null, 8));
        this.cityVehiclesList.add(new CommonStructure(city_car6, null, null, 8));

        this.cityVehiclesList.add(new CommonStructure(city_car_bus1, null, null, 2));
        this.cityVehiclesList.add(new CommonStructure(city_car_bus2, null, null, 2));
        this.cityVehiclesList.add(new CommonStructure(city_car_bus3, null, null, 2));
        this.cityVehiclesList.add(new CommonStructure(city_car_bus4, null, null, 2));
        this.cityVehiclesList.add(new CommonStructure(city_car_bus5, null, null, 2));
        this.cityVehiclesList.add(new CommonStructure(city_car_bus6, null, null, 2));
        this.cityVehiclesList.add(new CommonStructure(city_car_bus7, null, null, 2));
        this.cityVehiclesList.add(new CommonStructure(city_car_bus8, null, null, 2));
        this.cityVehiclesList.add(new CommonStructure(city_car_bus9, null, null, 2));
        
        this.cityVehiclesList.add(new CommonStructure(city_car_pol1, null, null, 2));
        this.cityVehiclesList.add(new CommonStructure(city_car_mil1, null, null, 2));
        this.cityVehiclesList.add(new CommonStructure(city_car_tank1, null, null, 1));
        this.cityVehiclesList.add(new CommonStructure(city_car_fire1, null, null, 2));
        this.cityVehiclesList.add(new CommonStructure(city_car_med1, null, null, 2));

        this.cityMedList.add(new CityStructure(city_med1, 1));

        this.cityPoliceList.add(new CityStructure(city_pol1, 1));
        this.cityPoliceList.add(new CityStructure(city_pol2, 1));

        this.citySkyScrapersList.add(new CityStructure(city_sky1, 1));
        this.citySkyScrapersList.add(new CityStructure(city_sky2, 1));
        this.citySkyScrapersList.add(new CityStructure(city_sky3, 1));
        this.citySkyScrapersList.add(new CityStructure(city_sky4, 1));
        this.citySkyScrapersList.add(new CityStructure(city_sky5, 1));
        this.citySkyScrapersList.add(new CityStructure(city_sky6, 1));
        this.citySkyScrapersList.add(new CityStructure(city_sky7, 1));
        this.citySkyScrapersList.add(new CityStructure(city_sky8, 1));
        this.citySkyScrapersList.add(new CityStructure(city_sky9, 1));
        this.citySkyScrapersList.add(new CityStructure(city_sky10, 1));
        this.citySkyScrapersList.add(new CityStructure(city_sky11, 1));
        
        this.cityGreenList.add(new CityStructure(city_foot1, 3));
        this.cityGreenList.add(new CityStructure(city_basket1, 3));
        this.cityGreenList.add(new CityStructure(city_gym1, 3));
        this.cityGreenList.add(new CityStructure(city_park1, 3));
        this.cityGreenList.add(new CityStructure(city_park2, 3));
        this.cityGreenList.add(new CityStructure(city_park3, 3));
        this.cityGreenList.add(new CityStructure(city_arena1, 2));
        this.cityGreenList.add(new CityStructure(city_dead1, 2));
        this.cityGreenList.add(new CityStructure(city_med_park1, 1));
        this.cityGreenList.add(new CityStructure(city_mil_park1, 1));
    }

    public boolean checkRegion(World world, int x, int z) {
        if (SpecialGenerator.getTerType(world) instanceof WorldTypeCrazyZp) {
            return true;
        }
        return world.getBiomeGenForCoords(x, z) instanceof BiomeCity || (world.getBiomeGenForCoords(x, z) == BiomeGenBase.river || world.getBiomeGenForCoords(x, z) == BiomeGenBase.beach);
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
        return 7;
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
                        if (world.getBlock(i, startY + 1, o) instanceof BlockBush) {
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
            if (random.nextFloat() <= 0.7f) {
                world.setBlock(i, minY + 1, z - wider + 1, Blocks.fence);
                world.setBlock(i, minY + 1, maxZ + wider - 1, Blocks.fence);
            }
        }
        for (int i = z - wider + 1; i <= maxZ + wider - 1; i++) {
            if (random.nextFloat() <= 0.7f) {
                world.setBlock(x - wider + 1, minY + 1, i, Blocks.fence);
                world.setBlock(maxX + wider - 1, minY + 1, i, Blocks.fence);
            }
        }
    }

    private void finalDecorate(Random random, World world, int x, int minY, int z) {
        //-8712810937854479305
        //1350 -2300
        int maxX = x + this.getScale(world) * (this.getMatrixSize(world) - 1);
        int maxZ = z + this.getScale(world) * (this.getMatrixSize(world) - 1);
        int wider = this.getScale(world) / 2;
        for (int i = x - wider; i <= maxX + wider; i++) {
            for (int o = z - wider; o <= maxZ + wider; o++) {
                if (world.getBlock(i, minY, o) == this.footing) {
                    if (random.nextFloat() <= 0.03f) {
                        if (world.getBlock(i, minY + 1, o) instanceof BlockBush) {
                            world.setBlockToAir(i, minY + 1, o);
                        }
                        if (random.nextBoolean()) {
                            world.setBlock(i, minY, o, Blocks.dirt, 2, 2);
                        } else {
                            world.setBlock(i, minY, o, Blocks.gravel);
                        }
                    }
                    if (world.isAirBlock(i, minY + 1, o)) {
                        if (random.nextFloat() <= 0.025f) {
                            world.setBlock(i, minY + 1, o, Blocks.leaves);
                        } else if (random.nextFloat() <= 0.01f) {
                            WorldGenAbstractTree worldgenabstracttree = new WorldGenTrees(false);
                            worldgenabstracttree.setScale(1.0D, 1.0D, 1.0D);
                            worldgenabstracttree.generate(world, random, i, minY + 1, o);
                        } else if (random.nextFloat() <= 0.8f) {
                            if (world.getBlock(i, minY, o) == this.footing) {
                                if (world.isAirBlock(i, minY + 1, o)) {
                                    if (random.nextFloat() <= 0.05f) {
                                        world.setBlock(i, minY + 1, o, random.nextBoolean() ? Blocks.red_flower : Blocks.yellow_flower, 0, 2);
                                    } else {
                                        world.setBlock(i, minY + 1, o, Blocks.tallgrass, random.nextFloat() <= 0.2f ? 2 : 1, 2);
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
        TownGenAutomata townGenAutomata = new TownGenAutomata(this.getMatrixSize(world));
        townGenAutomata.generate();
        this.genRoads(random, townGenAutomata, world, x, y, z);
        this.genBuildings(random, townGenAutomata, world, x, y, z);
        this.finalDecorate(random, world, x, y, z);
    }

    private void genRoads(Random random, Automata automata, World world, int x, int y, int z) {
        for (int i = 0; i < automata.getSize(); i++) {
            for (int j = 0; j < automata.getSize(); j++) {
                int newX = (i * this.getScale(world)) + x;
                int newZ = (j * this.getScale(world)) + z;
                if (automata.getStructure(i, j) == Structures.ROAD_VISITED) {
                    if (automata.checkStructure(i + 1, j, Structures.ROAD_VISITED)) {
                        for (int o = -4; o < this.getScale(world) + 5; o += 1) {
                            this.gen9x1Road(random, world, newX + o, y, newZ);
                            if (o < this.getScale(world) + 4 && o > -4 && o % 4 == 0) {
                                if (random.nextFloat() <= 0.65f) {
                                    world.setBlock(newX + o, y, newZ, BlocksZp.road2);
                                }
                            }
                        }
                    }
                    if (automata.checkStructure(i, j + 1, Structures.ROAD_VISITED)) {
                        for (int o = -4; o < this.getScale(world) + 5; o += 1) {
                            this.gen1x9Road(random, world, newX, y, newZ + o);
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
                    case GRAY: {
                        this.aGenerator.buildStructure(random, this.citySkyScrapersList, world, newX, y, newZ, rotation);
                        break;
                    }
                    case GREEN: {
                        this.aGenerator.buildStructure(random, this.cityGreenList, world, newX, y, newZ, rotation);
                        break;
                    }
                    case WHITE: {
                        this.aGenerator.buildStructure(random, this.cityCommonList, world, newX, y, newZ, rotation);
                        break;
                    }
                    case BLUE: {
                        this.aGenerator.buildStructure(random, this.cityPoliceList, world, newX, y, newZ, rotation);
                        break;
                    }
                    case PINK: {
                        this.aGenerator.buildStructure(random, this.cityMedList, world, newX, y, newZ, rotation);
                        break;
                    }
                }
            }
        }
    }

    private void gen1x9Road(Random random, World world, int x, int y, int z) {
        for (int i = -4; i < 5; i++) {
            if (i != 0) {
                if (random.nextFloat() <= 0.01f) {
                    this.aGenerator.buildStructure(random, this.cityVehiclesList, world, x + i, y + 1, z, i < 0 ? 2 : 0);
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

    private void gen9x1Road(Random random, World world, int x, int y, int z) {
        for (int j = -4; j < 5; j++) {
            if (j != 0) {
                if (random.nextFloat() <= 0.01f) {
                    this.aGenerator.buildStructure(random, this.cityVehiclesList, world, x, y + 1, z + j, j < 0 ? 3 : 1);
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
