package ru.BouH_.world.generator.cities;

import net.minecraft.world.World;

import java.util.Random;

public interface ICityGen {
    CityCheckResult tryGenCity(World world, int x, int y, int z);

    void genCity(Random random, World world, int x, int y, int z);

    int getScale(World world);

    int defaultMatrixSize();

    void setMatrixSize(int size);

    int getMatrixSize(World world);
}
