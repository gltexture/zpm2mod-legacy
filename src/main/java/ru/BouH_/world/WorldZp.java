package ru.BouH_.world;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.FMLLaunchHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.WorldChunkManager;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.ChunkProviderGenerate;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.DimensionManager;
import ru.BouH_.ConfigZp;
import ru.BouH_.proxy.CommonProxy;

public class WorldZp extends WorldProvider {
    private WorldType worldType;

    public WorldZp() {
    }

    public void registerWorldChunkManager() {
        WorldType worldType;
        switch (ConfigZp.plagueZoneType) {
            case 0: {
                this.worldType = WorldType.FLAT;
                break;
            }
            case 1: {
                this.worldType = WorldType.DEFAULT;
                break;
            }
            case 2: {
                this.worldType = CommonProxy.worldTypeZp;
                break;
            }
            case 3: {
                this.worldType = CommonProxy.worldTypeHardCoreZp;
                break;
            }
            default: {
                this.worldType = CommonProxy.worldTypeCrazyZp;
                break;
            }
        }
        this.worldChunkMgr = new WorldChunkManager(this.getSeed(), this.worldType);
        this.dimensionId = 2;
    }

    public WorldType getWorldType() {
        return this.worldType;
    }

    public String getDimensionName() {
        return "Plague Zone";
    }

    public void setWorldTime(long time) {
        super.setWorldTime(time);
    }

    @Override
    public boolean isDaytime() {
        if (FMLLaunchHandler.side().isClient()) {
            return super.isDaytime();
        }
        return !MinecraftServer.getServer().worldServers[0].isDaytime();
    }

    public long getWorldTime() {
        if (FMLLaunchHandler.side().isClient()) {
            return super.getWorldTime();
        }
        return MinecraftServer.getServer().worldServers[0].getWorldTime() + 12000;
    }

    public IChunkProvider createChunkGenerator() {
        return new ChunkProviderGenerate(this.worldObj, this.getSeed(), false);
    }
}
