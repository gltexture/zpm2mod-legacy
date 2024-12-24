package ru.BouH_.world.type;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.biome.WorldChunkManagerHell;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.FlatGeneratorInfo;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerBiomeEdge;
import net.minecraft.world.gen.layer.GenLayerZoom;
import ru.BouH_.world.layer.GenLayerZp;
import ru.BouH_.world.type.provider.ChunkProviderFlatZp;

public class WorldTypeCrazyZp extends WorldTypeZp {
    public WorldTypeCrazyZp(String name) {
        super(name);
    }

    public GenLayer getBiomeLayer(long worldSeed, GenLayer parentLayer) {
        GenLayer ret = new GenLayerZp(true, 200L, parentLayer);
        ret = GenLayerZoom.magnify(1000L, ret, 2);
        ret = new GenLayerBiomeEdge(1000L, ret);
        return ret;
    }

    public IChunkProvider getChunkGenerator(World world, String generatorOptions) {
        return new ChunkProviderFlatZp(world, world.getSeed(), world.getWorldInfo().isMapFeaturesEnabled(), "2;7,56x1,6x3,2;1;");
    }

    @Override
    public int getDistanceBetweenCities() {
        return 256;
    }
}
