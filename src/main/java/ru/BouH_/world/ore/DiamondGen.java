package ru.BouH_.world.ore;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import ru.BouH_.Main;

import java.util.Random;

public class DiamondGen implements IWorldGenerator {
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        if (world.provider.dimensionId == 0) {
            for (int i = 0; i < 16; i++) {
                int x = chunkX * 16 + Main.rand.nextInt(16);
                int y = 1 + Main.rand.nextInt(18);
                int z = chunkZ * 16 + Main.rand.nextInt(16);
                if (world.getBlock(x, y, z) == Blocks.stone) {
                    if (world.isAirBlock(x - 1, y, z) || world.isAirBlock(x + 1, y, z) || world.isAirBlock(x, y, z - 1) || world.isAirBlock(x, y, z + 1) || world.isAirBlock(x, y - 1, z) || world.isAirBlock(x, y + 1, z)) {
                        world.setBlock(x, y, z, Blocks.diamond_ore);
                    }
                }
            }
        }
    }
}
