package ru.BouH_.world.type;

import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerBiomeEdge;
import net.minecraft.world.gen.layer.GenLayerZoom;
import ru.BouH_.world.layer.GenLayerZp;

public class WorldTypeZp extends WorldType implements IWorldWithCities {
    public WorldTypeZp(String name) {
        super(name);
    }

    public GenLayer getBiomeLayer(long worldSeed, GenLayer parentLayer) {
        GenLayer ret = new GenLayerZp(false, 200L, parentLayer);
        ret = GenLayerZoom.magnify(1000L, ret, 2);
        ret = new GenLayerBiomeEdge(1000L, ret);
        return ret;
    }

    public IChunkProvider getChunkGenerator(World world, String generatorOptions) {
        return new ChunkProviderGenerate(world, world.getSeed(), false);
    }

    @Override
    public int getDistanceBetweenCities() {
        return 128;
    }
}
