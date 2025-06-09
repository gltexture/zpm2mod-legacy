package ru.BouH_.weather.base;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.DimensionManager;
import ru.BouH_.ConfigZp;
import ru.BouH_.Main;
import ru.BouH_.init.BlocksZp;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.packets.misc.PacketRainCheck;
import ru.BouH_.weather.managers.IWeatherInfo;
import ru.BouH_.weather.managers.WeatherRainManager;

public class WeatherRain implements IWeather {
    public float currentRainStrength;
    public float rainStrength;
    public float cloudStrength;
    public float currentCloudStrength;
    public float prevCloudStrength;

    public void sendRainPacketCheck(EntityPlayerMP player, WeatherRainManager weatherRainInfo) {
        if (player != null && weatherRainInfo != null) {
            NetworkHandler.NETWORK.sendTo(new PacketRainCheck(weatherRainInfo.isStarted(), true, weatherRainInfo.getRainStrength(), weatherRainInfo.getCloudyStrength()), player);
        }
    }

    public void startWeatherRain(float strength, float cloudStrength) {
        this.rainStrength = strength;
        this.cloudStrength = cloudStrength;
    }

    public void stopWeatherRain() {
        this.rainStrength = 0;
        this.cloudStrength = 0;
    }

    public void setWeatherRain(float rainStrength, float cloudStrength) {
        WeatherFog weatherFog = WeatherHandler.instance.getWeatherFog();
        float f1 = Math.max(cloudStrength, Math.min(weatherFog.fogDepth, 0.7f));
        this.currentRainStrength = rainStrength;
        this.rainStrength = rainStrength;
        this.cloudStrength = f1;
        this.currentCloudStrength = f1;
        this.prevCloudStrength = cloudStrength;
    }

    public void clearWeatherRain() {
        WeatherFog weatherFog = WeatherHandler.instance.getWeatherFog();
        float f1 = Math.min(weatherFog.fogDepth, 0.7f);
        this.currentRainStrength = 0;
        this.rainStrength = 0;
        this.cloudStrength = f1;
        this.currentCloudStrength = f1;
        this.prevCloudStrength = 0;
    }

    public void tick(IWeatherInfo iWeatherInfo) {
        if (!iWeatherInfo.isStarted()) {
            if (iWeatherInfo.getTimeUntilStart() > 0) {
                iWeatherInfo.setTimeUntilStart(iWeatherInfo.getTimeUntilStart() - 1);
            } else {
                if (Main.rand.nextFloat() <= 0.2f * ConfigZp.rainAndFogChanceMultiplier) {
                    this.startWeatherRainPacket((WeatherRainManager) iWeatherInfo);
                } else {
                    iWeatherInfo.resetTimer();
                }
            }
        } else if (iWeatherInfo.getTimeTicks() > 0) {
            iWeatherInfo.setTimeTicks(iWeatherInfo.getTimeTicks() - 1);
            float strength = ((WeatherRainManager) iWeatherInfo).getRainStrength();
            if (strength >= 0.2f) {
                this.weatherAction(DimensionManager.getWorld(iWeatherInfo.getDimension()), strength);
            }
        } else {
            this.switchWeatherRainPacket((WeatherRainManager) iWeatherInfo);
        }
    }

    protected void weatherAction(WorldServer world, float strength) {
        for (Object o : world.activeChunkSet) {
            ChunkCoordIntPair chunkCoordIntPair = (ChunkCoordIntPair) o;
            int i = (int) ((2.0f - strength) * 13.5f);
            if (Main.rand.nextInt(i) == 0) {
                int x = chunkCoordIntPair.chunkXPos * 16 + Main.rand.nextInt(16) + 8;
                int z = chunkCoordIntPair.chunkZPos * 16 + Main.rand.nextInt(16) + 8;
                int y = world.getPrecipitationHeight(x, z);
                BiomeGenBase biomegenbase = world.getBiomeGenForCoords(x, z);
                if (biomegenbase == BiomeGenBase.desert || biomegenbase == BiomeGenBase.desertHills) {
                    if (world.getBlock(x, y - 1, z) != Blocks.sand && world.getBlock(x, y, z).getMaterial() == Material.air && BlocksZp.sand_layer.canPlaceBlockAt(world, x, y, z)) {
                        world.setBlock(x, y, z, BlocksZp.sand_layer);
                    }
                } else {
                    if (this.canSnowAt(world, biomegenbase, x, y, z)) {
                        if (world.getBlock(x, y, z) == Blocks.snow_layer) {
                            int n1 = world.getBlockMetadata(x, y, z);
                            int checkBlocks = this.checkBlockSummary(world, x, y, z);
                            if (checkBlocks >= 7) {
                                if (n1 < (checkBlocks >= 10 ? 2 : 1)) {
                                    world.setBlockMetadataWithNotify(x, y, z, n1 + 1, 2);
                                }
                            }
                        } else {
                            world.setBlock(x, y, z, Blocks.snow_layer);
                        }
                    }
                }
            }
        }
    }

    private int checkBlockSummary(World world, int x, int y, int z) {
        int block1 = world.getBlock(x - 1, y, z).isOpaqueCube() ? 4 : world.getBlock(x - 1, y, z) == Blocks.snow_layer ? Math.min(world.getBlockMetadata(x - 1, y, z) + 1, 3) : 0;
        int block2 = world.getBlock(x, y, z - 1).isOpaqueCube() ? 4 : world.getBlock(x, y, z - 1) == Blocks.snow_layer ? Math.min(world.getBlockMetadata(x, y, z - 1) + 1, 3) : 0;
        int block3 = world.getBlock(x + 1, y, z).isOpaqueCube() ? 4 : world.getBlock(x + 1, y, z) == Blocks.snow_layer ? Math.min(world.getBlockMetadata(x + 1, y, z) + 1, 3) : 0;
        int block4 = world.getBlock(x, y, z + 1).isOpaqueCube() ? 4 : world.getBlock(x, y, z + 1) == Blocks.snow_layer ? Math.min(world.getBlockMetadata(x, y, z + 1) + 1, 3) : 0;
        return block1 + block2 + block3 + block4;
    }

    public boolean canSnowAt(World world, BiomeGenBase biomeGenBase, int x, int y, int z) {
        float f = biomeGenBase.getFloatTemperature(x, y, z);
        if (f <= 0.15F) {
            if (y >= 0 && y < 256 && world.getSavedLightValue(EnumSkyBlock.Block, x, y, z) < 10) {
                Block block = world.getBlock(x, y, z);
                return (block.getMaterial() == Material.air && Blocks.snow_layer.canPlaceBlockAt(world, x, y, z)) || block == Blocks.snow_layer || block instanceof BlockTallGrass || block instanceof BlockFlower;
            }
        }
        return false;
    }

    public void startWeatherRainPacket(WeatherRainManager weatherRainManager) {
        weatherRainManager.start(DimensionManager.getWorld(weatherRainManager.getDimension()));
        NetworkHandler.NETWORK.sendToDimension(new PacketRainCheck(true, false, weatherRainManager.getRainStrength(), weatherRainManager.getCloudyStrength()), weatherRainManager.getDimension());
    }

    public void stopWeatherRainPacket(WeatherRainManager weatherRainManager) {
        weatherRainManager.stop(DimensionManager.getWorld(weatherRainManager.getDimension()));
        weatherRainManager.resetTimer();
        NetworkHandler.NETWORK.sendToDimension(new PacketRainCheck(false, false, 0, weatherRainManager.getCloudyStrength()), weatherRainManager.getDimension());
    }

    public void switchWeatherRainPacket(WeatherRainManager weatherRainManager) {
        weatherRainManager.switchScript(DimensionManager.getWorld(weatherRainManager.getDimension()));
        weatherRainManager.resetTimer();
        NetworkHandler.NETWORK.sendToDimension(new PacketRainCheck(weatherRainManager.isStarted(), false, weatherRainManager.getRainStrength(), weatherRainManager.getCloudyStrength()), weatherRainManager.getDimension());
    }
}
