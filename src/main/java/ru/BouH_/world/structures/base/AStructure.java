package ru.BouH_.world.structures.base;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class AStructure {
    public final BiomeGenBase[] biomes;
    private final int chance;
    private final StructureHolder structureHolder;

    protected AStructure(StructureHolder structureHolder, int chance, BiomeGenBase... biomes) {
        this.structureHolder = structureHolder;
        this.chance = chance;
        this.biomes = biomes;
    }

    public abstract boolean runGenerator(World world, int x, int y, int z, int rotation);

    public StructureHolder getStructureHolder() {
        return this.structureHolder;
    }

    public Set<BiomeGenBase> allowedBiomes() {
        return new HashSet<>(Arrays.asList(this.biomes));
    }

    public int getChance() {
        return this.chance;
    }
}
