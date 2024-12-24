package ru.BouH_.world.generator;

import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import ru.BouH_.proxy.CommonProxy;
import ru.BouH_.world.structures.base.AStructure;
import ru.BouH_.world.structures.base.StructureHolder;
import ru.BouH_.world.structures.building.LabStructure;
import ru.BouH_.world.type.WorldTypeZp;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RadiationGenerator extends AGenerator { //TODO: Make new structure format
    public static RadiationGenerator instance = new RadiationGenerator();
    public final List<AStructure> labs1 = new ArrayList<>();
    public final List<AStructure> labs2 = new ArrayList<>();

    protected RadiationGenerator() {
        super(2);
    }

    public void loadStructures() {
        StructureHolder lab1_1 = StructureHolder.create("lab/lab1_1");
        StructureHolder lab1_2 = StructureHolder.create("lab/lab1_2");
        StructureHolder lab1_3 = StructureHolder.create("lab/lab1_3");
        StructureHolder lab2_1 = StructureHolder.create("lab/lab2_1");

        this.labs1.add(new LabStructure(lab1_1, 1));
        this.labs1.add(new LabStructure(lab1_2, 1));
        this.labs1.add(new LabStructure(lab1_3, 1));
        this.labs2.add(new LabStructure(lab2_1, 1));
    }

    private int findY(World world, int x, int z) {
        return world.getPrecipitationHeight(x, z) - 1;
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        if (world.getWorldInfo().getTerrainType() instanceof WorldTypeZp) {
            int x = chunkX * 16 + 8;
            int z = chunkZ * 16 + 8;
            int y = this.findY(world, x, z) - (random.nextInt(17) + 32);
            if (y <= 1) {
                y = 24;
            }
            if (world.getBlock(x, y, z).isOpaqueCube()) {
                if (random.nextFloat() <= 0.85f) {
                    if (chunkX % 4 == 0 && chunkZ % 4 == 0) {
                        this.tryGenLab(random, world, x, y, z);
                    }
                }
            }
        }
    }

    private void tryGenLab(Random random, World world, int x, int y, int z) {
        BiomeGenBase base = world.getBiomeGenForCoords(x, z);
        if (base == CommonProxy.biome_rad1) {
            this.buildStructure(random, this.labs1, world, x, y, z, random.nextInt(4));
        } else if (base == CommonProxy.biome_rad2) {
            if (random.nextFloat() <= 0.3f) {
                this.buildStructure(random, this.labs2, world, x, y, z, random.nextInt(4));
            } else {
                this.buildStructure(random, this.labs1, world, x, y, z, random.nextInt(4));
            }
        }
    }
}
