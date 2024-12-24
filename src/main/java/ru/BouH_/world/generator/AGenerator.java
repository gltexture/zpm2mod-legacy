package ru.BouH_.world.generator;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import ru.BouH_.world.structures.base.AStructure;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class AGenerator implements IWorldGenerator {
    private final int genId;

    protected AGenerator(int id) {
        this.genId = id;
    }

    public abstract void loadStructures();

    public int getGenId() {
        return this.genId;
    }

    private AStructure chooseStructureSorted(Random random, List<AStructure> structureList, BiomeGenBase biomeGenBase) {
        List<AStructure> allowedList = new ArrayList<>(structureList);
        allowedList.removeIf(e -> (e.allowedBiomes() != null && !e.allowedBiomes().contains(biomeGenBase)));
        List<AStructure> listToSort = new ArrayList<>();
        for (AStructure aStructure : allowedList) {
            for (int i = 0; i < aStructure.getChance(); i++) {
                listToSort.add(aStructure);
            }
        }
        if (listToSort.isEmpty()) {
            return null;
        }
        return listToSort.get(random.nextInt(listToSort.size()));
    }

    private AStructure chooseStructure(Random random, List<AStructure> structureList) {
        if (structureList.isEmpty()) {
            return null;
        }
        return structureList.get(random.nextInt(structureList.size()));
    }

    public boolean buildStructure(Random random, List<AStructure> structureList, BiomeGenBase biomeGenBase, World world, int x, int y, int z, int rotation) {
        AStructure aStructure = this.chooseStructureSorted(random, structureList, biomeGenBase);
        if (aStructure != null) {
            return aStructure.runGenerator(world, x, y, z, rotation);
        }
        return false;
    }

    public boolean buildStructure(Random random, List<AStructure> structureList, World world, int x, int y, int z, int rotation) {
        AStructure aStructure = this.chooseStructure(random, structureList);
        if (aStructure != null) {
            return aStructure.runGenerator(world, x, y, z, rotation);
        }
        return false;
    }

    public boolean buildStructure(AStructure aStructure, World world, int x, int y, int z, int rotation) {
        if (aStructure != null) {
            return aStructure.runGenerator(world, x, y, z, rotation);
        }
        return false;
    }
}
