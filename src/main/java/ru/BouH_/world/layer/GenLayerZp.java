package ru.BouH_.world.layer;

import net.minecraft.util.WeightedRandom;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.layer.*;
import net.minecraftforge.common.BiomeManager;
import ru.BouH_.ConfigZp;
import ru.BouH_.proxy.CommonProxy;

import java.util.ArrayList;
import java.util.List;

public class GenLayerZp extends GenLayer {
    @SuppressWarnings("unchecked")
    private final List<BiomeManager.BiomeEntry>[] biomes = new ArrayList[BiomeManager.BiomeType.values().length];
    private final boolean onlyCities;

    public GenLayerZp(boolean onlyCities, long p_i2122_1_, GenLayer p_i2122_3_) {
        super(p_i2122_1_);
        this.parent = p_i2122_3_;
        this.onlyCities = onlyCities;
        this.setupBiomes();
    }

    //-2603855873415445945
    //-104 100 1317
    public static GenLayer[] initializeAllBiomeGenerators(long p_75901_0_, WorldType p_75901_2_) {
        GenLayerIsland genlayerisland = new GenLayerIsland(1000L);
        GenLayerFuzzyZoom genlayerfuzzyzoom = new GenLayerFuzzyZoom(2000L, genlayerisland);
        GenLayerAddIsland genlayeraddisland = new GenLayerAddIsland(1L, genlayerfuzzyzoom);
        GenLayerZoom genlayerzoom = new GenLayerZoom(2001L, genlayeraddisland);
        genlayeraddisland = new GenLayerAddIsland(2L, genlayerzoom);
        genlayeraddisland = new GenLayerAddIsland(50L, genlayeraddisland);
        genlayeraddisland = new GenLayerAddIsland(70L, genlayeraddisland);
        genlayeraddisland = new GenLayerAddIsland(170L, genlayeraddisland);
        genlayeraddisland = new GenLayerAddIsland(470L, genlayeraddisland);
        GenLayerRemoveTooMuchOcean genlayerremovetoomuchocean = new GenLayerRemoveTooMuchOcean(123120L, genlayeraddisland);
        byte b0 = getModdedBiomeSize(p_75901_2_, (byte) 4);
        GenLayer genlayer12 = GenLayerZoom.magnify(1200L, genlayerremovetoomuchocean, b0);

        GenLayerAddSnow genlayeraddsnow = new GenLayerAddSnow(2L, genlayer12);
        genlayeraddisland = new GenLayerAddIsland(3L, genlayeraddsnow);
        GenLayerEdge genlayeredge = new GenLayerEdge(2L, genlayeraddisland, GenLayerEdge.Mode.COOL_WARM);
        genlayeredge = new GenLayerEdge(2L, genlayeredge, GenLayerEdge.Mode.HEAT_ICE);
        genlayeredge = new GenLayerEdge(3L, genlayeredge, GenLayerEdge.Mode.SPECIAL);
        genlayerzoom = new GenLayerZoom(2002L, genlayeredge);
        genlayerzoom = new GenLayerZoom(2003L, genlayerzoom);
        genlayeraddisland = new GenLayerAddIsland(4L, genlayerzoom);
        GenLayerAddMushroomIsland genlayeraddmushroomisland = new GenLayerAddMushroomIsland(5L, genlayeraddisland);
        GenLayerDeepOcean genlayerdeepocean = new GenLayerDeepOcean(4L, genlayeraddmushroomisland);

        GenLayer genlayer = GenLayerZoom.magnify(1000L, genlayerdeepocean, 0);
        GenLayerRiverInit genlayerriverinit = new GenLayerRiverInit(100L, genlayer);
        GenLayer object = p_75901_2_.getBiomeLayer(p_75901_0_, genlayer);

        GenLayer genlayer1 = GenLayerZoom.magnify(1000L, genlayerriverinit, 2);
        GenLayerHills genlayerhills = new GenLayerHills(1000L, object, genlayer1);
        genlayer = GenLayerZoom.magnify(1000L, genlayerriverinit, 2);
        genlayer = GenLayerZoom.magnify(1000L, genlayer, b0);
        GenLayerRiver genlayerriver = new GenLayerRiver(1L, genlayer);
        GenLayerSmooth genlayersmooth = new GenLayerSmooth(1000L, genlayerriver);
        object = new GenLayerRareBiome(1001L, genlayerhills);

        for (int j = 0; j < b0; ++j) {
            object = new GenLayerZoom(1000 + j, object);

            if (j == 0) {
                object = new GenLayerAddIsland(3L, object);
            }

            if (j == 1) {
                object = new GenLayerShore(1000L, object);
            }
        }

        GenLayerSmooth genlayersmooth1 = new GenLayerSmooth(1000L, object);
        GenLayerRiverMix genlayerrivermix = new GenLayerRiverMix(100L, genlayersmooth1, genlayersmooth);
        GenLayerVoronoiZoom genlayervoronoizoom = new GenLayerVoronoiZoom(10L, genlayerrivermix);
        genlayerrivermix.initWorldGenSeed(p_75901_0_);
        genlayervoronoizoom.initWorldGenSeed(p_75901_0_);
        return new GenLayer[]{genlayerrivermix, genlayervoronoizoom, genlayerrivermix};
    }

    private void setupBiomes() {
        List<BiomeManager.BiomeEntry> list = new ArrayList<>();
        if (this.onlyCities) {
            list.add(new BiomeManager.BiomeEntry(CommonProxy.biome_city, 30));
            list.add(new BiomeManager.BiomeEntry(CommonProxy.biome_industry, 30));
            list.add(new BiomeManager.BiomeEntry(CommonProxy.biome_military, 30));
            biomes[BiomeManager.BiomeType.WARM.ordinal()] = new ArrayList<>(list);
            return;
        }

        list.add(new BiomeManager.BiomeEntry(BiomeGenBase.forest, 1));
        list.add(new BiomeManager.BiomeEntry(BiomeGenBase.roofedForest, 1));
        list.add(new BiomeManager.BiomeEntry(BiomeGenBase.extremeHills, 2));
        list.add(new BiomeManager.BiomeEntry(BiomeGenBase.plains, 64));

        list.add(new BiomeManager.BiomeEntry(CommonProxy.biome_city, ConfigZp.cityBiomeSpawnWeights));
        list.add(new BiomeManager.BiomeEntry(CommonProxy.biome_industry, ConfigZp.cityBiomeSpawnWeights));
        list.add(new BiomeManager.BiomeEntry(CommonProxy.biome_military, ConfigZp.cityBiomeSpawnWeights));

        list.add(new BiomeManager.BiomeEntry(BiomeGenBase.birchForest, 1));
        list.add(new BiomeManager.BiomeEntry(BiomeGenBase.swampland, 1));
        list.add(new BiomeManager.BiomeEntry(BiomeGenBase.jungle, 1));
        list.add(new BiomeManager.BiomeEntry(BiomeGenBase.jungleHills, 1));
        list.add(new BiomeManager.BiomeEntry(BiomeGenBase.jungleEdge, 1));
        biomes[BiomeManager.BiomeType.WARM.ordinal()] = new ArrayList<>(list);

        list.clear();
        list.add(new BiomeManager.BiomeEntry(BiomeGenBase.forest, 1));
        list.add(new BiomeManager.BiomeEntry(BiomeGenBase.extremeHills, 2));
        list.add(new BiomeManager.BiomeEntry(BiomeGenBase.taiga, 1));
        list.add(new BiomeManager.BiomeEntry(BiomeGenBase.plains, 40));
        biomes[BiomeManager.BiomeType.COOL.ordinal()] = new ArrayList<>(list);

        list.clear();
        list.add(new BiomeManager.BiomeEntry(BiomeGenBase.icePlains, 30));
        list.add(new BiomeManager.BiomeEntry(BiomeGenBase.coldTaiga, 10));
        biomes[BiomeManager.BiomeType.ICY.ordinal()] = new ArrayList<>(list);

        list.clear();
        list.add(new BiomeManager.BiomeEntry(CommonProxy.biome_rad1, 40));
        list.add(new BiomeManager.BiomeEntry(CommonProxy.biome_rad2, 20));
        list.add(new BiomeManager.BiomeEntry(BiomeGenBase.desert, 40));
        biomes[BiomeManager.BiomeType.DESERT.ordinal()] = new ArrayList<>(list);
    }

    public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
        int[] aint = this.parent.getInts(areaX, areaY, areaWidth, areaHeight);
        int[] aint1 = IntCache.getIntCache(areaWidth * areaHeight);

        for (int i1 = 0; i1 < areaHeight; ++i1) {
            for (int j1 = 0; j1 < areaWidth; ++j1) {
                this.initChunkSeed(j1 + areaX, i1 + areaY);
                int k1 = aint[j1 + i1 * areaWidth];
                int l1 = (k1 & 3840) >> 8;
                k1 &= -3841;
                if (isBiomeOceanic(k1)) {
                    aint1[j1 + i1 * areaWidth] = k1;
                } else if (k1 == 1) {
                    aint1[j1 + i1 * areaWidth] = getWeightedBiomeEntry(BiomeManager.BiomeType.DESERT).biome.biomeID;
                } else if (k1 == 2) {
                    aint1[j1 + i1 * areaWidth] = getWeightedBiomeEntry(BiomeManager.BiomeType.WARM).biome.biomeID;
                } else if (k1 == 3) {
                    aint1[j1 + i1 * areaWidth] = getWeightedBiomeEntry(BiomeManager.BiomeType.COOL).biome.biomeID;
                } else if (k1 == 4) {
                    aint1[j1 + i1 * areaWidth] = getWeightedBiomeEntry(BiomeManager.BiomeType.ICY).biome.biomeID;
                } else {
                    aint1[j1 + i1 * areaWidth] = getWeightedBiomeEntry(BiomeManager.BiomeType.ICY).biome.biomeID;
                }
            }
        }
        return aint1;
    }

    protected BiomeManager.BiomeEntry getWeightedBiomeEntry(BiomeManager.BiomeType type) {
        if (this.onlyCities) {
            type = BiomeManager.BiomeType.WARM;
        }
        List<BiomeManager.BiomeEntry> biomeList = biomes[type.ordinal()];
        int totalWeight = WeightedRandom.getTotalWeight(biomeList);
        int weight = BiomeManager.isTypeListModded(type) ? nextInt(totalWeight) : nextInt(totalWeight / 10) * 10;
        return (BiomeManager.BiomeEntry) WeightedRandom.getItem(biomeList, weight);
    }
}
