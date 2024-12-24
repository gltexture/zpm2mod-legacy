package ru.BouH_.world.ore;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import ru.BouH_.Main;
import ru.BouH_.init.BlocksZp;

import java.util.Random;

public class CopperGen implements IWorldGenerator {
    private final WorldGenerator copperGen;

    public CopperGen() {
        this.copperGen = new WorldGenMinable(BlocksZp.copper_ore, 6);
    }

    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        if (world.provider.dimensionId == 0) {
            if (Main.rand.nextFloat() <= 0.465f) {
                int i1 = chunkX + random.nextInt(16);
                int j1 = random.nextInt(33) + 32;
                int k1 = chunkZ + random.nextInt(16);
                this.copperGen.generate(world, random, i1, j1, k1);
            }
        }
    }
}
