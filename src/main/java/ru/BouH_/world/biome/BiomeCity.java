package ru.BouH_.world.biome;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenDoublePlant;
import ru.BouH_.Main;

public class BiomeCity extends BiomeGenBase implements ICityBiome {
    public BiomeCity(int p_i45383_1_) {
        super(p_i45383_1_);
        this.topBlock = Blocks.grass;
        this.fillerBlock = Blocks.dirt;
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.reedsPerChunk = 10;
        this.minHeight = -0.05f;
        this.maxHeight = -0.1f;
        this.theBiomeDecorator.grassPerChunk = 8;
        this.theBiomeDecorator.flowersPerChunk = 0;
        this.theBiomeDecorator.generateLakes = false;
    }

    public BiomeDecorator createBiomeDecorator() {
        return getModdedBiomeDecorator(new CityDecorator());
    }

    @SideOnly(Side.CLIENT)
    public int getBiomeGrassColor(int p_150558_1_, int p_150558_2_, int p_150558_3_) {
        return 0x9dca56;
    }

    @SideOnly(Side.CLIENT)
    public int getBiomeFoliageColor(int p_150571_1_, int p_150571_2_, int p_150571_3_) {
        return 0x9dca56;
    }

    public static class CityDecorator extends BiomeDecorator {
        @Override
        protected void genDecorations(BiomeGenBase gen) {
            super.genDecorations(gen);
            for (int j = 0; j < 12; ++j) {
                int k = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
                int l = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
                int i1 = this.getInt(this.currentWorld.getHeightValue(k, l) * 2);
                WorldGenDoublePlant worldGenDoublePlant = new WorldGenDoublePlant();
                worldGenDoublePlant.func_150548_a(Main.rand.nextBoolean() ? 2 : 3);
                worldGenDoublePlant.generate(this.currentWorld, this.randomGenerator, k, i1, l);
            }
        }

        private int getInt(int i) {
            if (i <= 1) {
                return 0;
            }
            return Main.rand.nextInt(i);
        }
    }
}
