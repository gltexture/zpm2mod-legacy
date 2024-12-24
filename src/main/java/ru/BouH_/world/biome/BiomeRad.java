package ru.BouH_.world.biome;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.world.biome.BiomeGenBase;

import java.awt.*;

public class BiomeRad extends BiomeGenBase {
    public BiomeRad(int p_i45383_1_) {
        super(p_i45383_1_);
        this.minHeight = 0.0f;
        this.maxHeight = 0.05f;
        this.theBiomeDecorator.treesPerChunk = 1;
        this.theBiomeDecorator.flowersPerChunk = 0;
        this.temperature = 1.0f;
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
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

        return Color.getHSBColor(0.2F - p_76731_1_ * 0.05F, 0.1F + p_76731_1_ * 0.1F, 0.8F).getRGB();
    }

    @SideOnly(Side.CLIENT)
    public int getWaterColorMultiplier() {
        return 0xfff65e;
    }

    @SideOnly(Side.CLIENT)
    public int getBiomeGrassColor(int p_150558_1_, int p_150558_2_, int p_150558_3_) {
        return 0xFFCF40;
    }

    @SideOnly(Side.CLIENT)
    public int getBiomeFoliageColor(int p_150571_1_, int p_150571_2_, int p_150571_3_) {
        return 0xFFCF40;
    }
}
