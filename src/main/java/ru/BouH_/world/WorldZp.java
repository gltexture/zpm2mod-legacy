package ru.BouH_.world;

import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;
import ru.BouH_.ConfigZp;
import ru.BouH_.proxy.CommonProxy;

public class WorldZp extends WorldProvider {

    public void registerWorldChunkManager() {
        WorldType worldType;
        switch (ConfigZp.plagueZoneType) {
            case 0: {
                worldType = WorldType.FLAT;
                break;
            }
            case 1: {
                worldType = WorldType.DEFAULT;
                break;
            }
            case 2: {
                worldType = CommonProxy.worldTypeZp;
                break;
            }
            case 3: {
                worldType = CommonProxy.worldTypeHardCoreZp;
                break;
            }
            default: {
                worldType = CommonProxy.worldTypeCrazyZp;
                break;
            }
        }
        this.worldChunkMgr = new WorldChunkManager(this.getSeed(), worldType);
        this.dimensionId = 2;
    }

    public String getDimensionName() {
        return "Plague Zone";
    }

    public boolean isDaytime() {
        return !super.isDaytime();
    }

    public long getWorldTime() {
        return super.getWorldTime();
    }

    @Override
    public float calculateCelestialAngle(long par1, float par2) {
        return getProviderForDimension(0).calculateCelestialAngle(super.getWorldTime() + 12000, par2);
    }

    public IChunkProvider createChunkGenerator() {
        return new ChunkProviderGenerate(this.worldObj, this.getSeed(), false);
    }

    @Override
    public int getMoonPhase(long par1) {
        return getProviderForDimension(0).getMoonPhase(par1 + 12000);
    }
}
