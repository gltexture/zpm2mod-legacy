package ru.BouH_.world.generator;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import ru.BouH_.world.structures.base.AStructure;
import ru.BouH_.world.structures.base.StructureHolder;
import ru.BouH_.world.structures.building.BunkerStructure;
import ru.BouH_.world.type.WorldTypeZp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BunkerGenerator extends AGenerator { //TODO: Make new structure format
    public static BunkerGenerator instance = new BunkerGenerator();
    public final List<AStructure> bunkers = new ArrayList<>();

    protected BunkerGenerator() {
        super(2);
    }

    public void loadStructures() {
        StructureHolder bunker1 = StructureHolder.create("bunker/bunker1");
        StructureHolder bunker2 = StructureHolder.create("bunker/bunker2");
        StructureHolder bunker3 = StructureHolder.create("bunker/bunker3");

        this.bunkers.add(new BunkerStructure(bunker1, 1));
        this.bunkers.add(new BunkerStructure(bunker2, 1));
        this.bunkers.add(new BunkerStructure(bunker3, 1));
    }

    private int findY(World world, int x, int z) {
        return world.getPrecipitationHeight(x, z) - 1;
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        if (world.getWorldInfo().getTerrainType() instanceof WorldTypeZp) {
            int x = chunkX * 16 + 8;
            int z = chunkZ * 16 + 8;
            int y1 = this.findY(world, x, z);
            int y = y1 - (random.nextInt(17) + 32);
            if (y <= 1) {
                y = 24;
            }
            if (world.getBlock(x, y1, z) == Blocks.grass && world.getBlock(x, y, z).isOpaqueCube()) {
                if (random.nextFloat() <= 0.3f) {
                    if (chunkX % 6 == 0 && chunkZ % 6 == 0) {
                        this.tryGenBunker(random, world, x, y, z);
                    }
                }
            }
        }
    }

    private void tryGenBunker(Random random, World world, int x, int y, int z) {
        BiomeGenBase base = world.getBiomeGenForCoords(x, z);
        if (base == BiomeGenBase.birchForest || base == BiomeGenBase.birchForestHills || base == BiomeGenBase.forest || base == BiomeGenBase.forestHills || base == BiomeGenBase.roofedForest || base == BiomeGenBase.plains || base == BiomeGenBase.taiga || base == BiomeGenBase.taigaHills || base == BiomeGenBase.coldTaigaHills || base == BiomeGenBase.coldTaiga) {
            this.buildStructure(random, this.bunkers, world, x, y, z,random.nextInt(4));
        }
    }
}
