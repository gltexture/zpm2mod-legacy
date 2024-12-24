package ru.BouH_.world.biome;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Blocks;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenDeadBush;
import ru.BouH_.Main;
import ru.BouH_.init.BlocksZp;
import ru.BouH_.init.FluidsZp;

import java.awt.*;

public class BiomeIndustry extends BiomeGenBase implements ICityBiome {
    public BiomeIndustry(int p_i45383_1_) {
        super(p_i45383_1_);
        this.topBlock = BlocksZp.debrisand;
        this.fillerBlock = BlocksZp.debrisand;
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.reedsPerChunk = 0;
        this.temperature = 0.5f;
        this.minHeight = -0.05f;
        this.maxHeight = -0.1f;
        this.theBiomeDecorator.grassPerChunk = 0;
        this.theBiomeDecorator.flowersPerChunk = 0;
        this.theBiomeDecorator.generateLakes = false;
    }

    public BiomeDecorator createBiomeDecorator() {
        return getModdedBiomeDecorator(new Decorator());
    }

    @SideOnly(Side.CLIENT)
    public int getSkyColorByTemp(float p_76731_1_)
    {
        p_76731_1_ /= 3.0F;

        if (p_76731_1_ < -1.0F)
        {
            p_76731_1_ = -1.0F;
        }

        if (p_76731_1_ > 1.0F)
        {
            p_76731_1_ = 1.0F;
        }

        return Color.getHSBColor(0.2F - p_76731_1_ * 0.05F, 0.1F + p_76731_1_ * 0.1F, 0.6F).getRGB();
    }

    @SideOnly(Side.CLIENT)
    public int getWaterColorMultiplier() {
        return 0x784100;
    }

    @SideOnly(Side.CLIENT)
    public int getBiomeGrassColor(int p_150558_1_, int p_150558_2_, int p_150558_3_) {
        return 0xbcb188;
    }

    @SideOnly(Side.CLIENT)
    public int getBiomeFoliageColor(int p_150571_1_, int p_150571_2_, int p_150571_3_) {
        return 0xbcb188;
    }

    public static class Decorator extends BiomeDecorator {
        @Override
        protected void genDecorations(BiomeGenBase gen) {
            super.genDecorations(gen);
            for (int j = 0; j < 18; ++j) {
                int k = this.chunk_X + this.randomGenerator.nextInt(16) + 8;
                int l = this.chunk_Z + this.randomGenerator.nextInt(16) + 8;
                int i1 = this.getInt(this.currentWorld.getHeightValue(k, l) * 2);
                WorldGenDeadBush worldGenDoublePlant = new WorldGenDeadBush(Main.rand.nextFloat() <= 0.2f ? (Main.rand.nextBoolean() ? BlocksZp.sand_layer : BlocksZp.gravel_layer) : Blocks.deadbush);
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
