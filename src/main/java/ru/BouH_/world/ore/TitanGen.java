package ru.BouH_.world.ore;

import cpw.mods.fml.common.IWorldGenerator;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import ru.BouH_.Main;
import ru.BouH_.init.BlocksZp;
import java.util.Random;

public class TitanGen implements IWorldGenerator {
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        if (world.provider.dimensionId == 0) {
            for (int i = 0; i < 16; i++) {
                int x = chunkX * 16 + Main.rand.nextInt(16);
                int y = 1 + Main.rand.nextInt(20);
                int z = chunkZ * 16 + Main.rand.nextInt(16);
                if (world.getBlock(x, y, z) == Blocks.stone) {
                    if (world.getBlock(x - 1, y, z).getMaterial() == Material.lava || world.getBlock(x + 1, y, z).getMaterial() == Material.lava || world.getBlock(x, y, z - 1).getMaterial() == Material.lava || world.getBlock(x, y, z + 1).getMaterial() == Material.lava || world.getBlock(x, y - 1, z).getMaterial() == Material.lava || world.getBlock(x, y + 1, z).getMaterial() == Material.lava) {
                        world.setBlock(x, y, z, BlocksZp.titan_ore);
                    }
                } else if (world.getBlock(x, y, z) == Blocks.lava) {
                    world.setBlock(x, y, z, BlocksZp.titan_ore);
                }
            }
        }
    }
}
