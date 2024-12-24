package ru.BouH_.gameplay;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.world.gen.feature.WorldGenLakes;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.event.terraingen.WorldTypeEvent;
import ru.BouH_.Main;
import ru.BouH_.init.FluidsZp;
import ru.BouH_.world.biome.BiomeCity;
import ru.BouH_.world.biome.BiomeIndustry;
import ru.BouH_.world.biome.BiomeMilitary;
import ru.BouH_.world.biome.BiomeRad;
import ru.BouH_.world.layer.GenLayerZp;
import ru.BouH_.world.type.WorldTypeZp;

public class WorldGenManager {
    @SubscribeEvent
    public void onOreGen(OreGenEvent.GenerateMinable ev) {
        if (ev.type == OreGenEvent.GenerateMinable.EventType.DIAMOND) {
            ev.setResult(Event.Result.DENY);
        } else if (ev.type != OreGenEvent.GenerateMinable.EventType.DIRT && ev.type != OreGenEvent.GenerateMinable.EventType.GRAVEL && ev.type != OreGenEvent.GenerateMinable.EventType.CUSTOM) {
            if (ev.world.provider.dimensionId == 2 || Main.rand.nextFloat() <= 0.7f) {
                ev.setResult(Event.Result.DENY);
            }
        }
    }

    @SubscribeEvent
    public void biomesInit(WorldTypeEvent.InitBiomeGens event) {
        if (event.worldType instanceof WorldTypeZp) {
            event.newBiomeGens = GenLayerZp.initializeAllBiomeGenerators(event.seed, event.worldType);
        }
    }

    @SubscribeEvent
    public void onGen(PopulateChunkEvent.Populate ev) {
        if (ev.type == PopulateChunkEvent.Populate.EventType.LAVA) {
            ev.setResult(Event.Result.DENY);
        }
        if (ev.world.getBiomeGenForCoords(ev.chunkX * 16, ev.chunkZ * 16) instanceof BiomeCity || ev.world.getBiomeGenForCoords(ev.chunkX * 16, ev.chunkZ * 16) instanceof BiomeMilitary) {
            ev.setResult(Event.Result.DENY);
        }
        if (ev.world.getBiomeGenForCoords(ev.chunkX * 16, ev.chunkZ * 16) instanceof BiomeRad) {
            if (ev.type == PopulateChunkEvent.Populate.EventType.LAKE) {
                ev.setResult(Event.Result.DENY);
                int k1 = ev.chunkX * 16 + Main.rand.nextInt(16) + 8;
                int l1 = Main.rand.nextInt(256);
                int i2 = ev.chunkZ * 16 + Main.rand.nextInt(16) + 8;
                (new WorldGenLakes(Main.rand.nextFloat() <= 0.1f ? FluidsZp.acidblock : FluidsZp.toxicwater_block)).generate(ev.world, Main.rand, k1, l1, i2);
            }
        }
        if (ev.world.getBiomeGenForCoords(ev.chunkX * 16, ev.chunkZ * 16) instanceof BiomeIndustry) {
            if (ev.type == PopulateChunkEvent.Populate.EventType.LAKE) {
                ev.setResult(Event.Result.DENY);
                int k1 = ev.chunkX * 16 + Main.rand.nextInt(16) + 8;
                int l1 = Main.rand.nextInt(256);
                int i2 = ev.chunkZ * 16 + Main.rand.nextInt(16) + 8;
                (new WorldGenLakes(FluidsZp.toxicwater_block)).generate(ev.world, Main.rand, k1, l1, i2);
            }
        }
    }
}
