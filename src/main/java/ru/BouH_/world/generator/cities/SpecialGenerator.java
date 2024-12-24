package ru.BouH_.world.generator.cities;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraftforge.event.world.WorldEvent;
import ru.BouH_.Main;
import ru.BouH_.proxy.CommonProxy;
import ru.BouH_.world.biome.ICityBiome;
import ru.BouH_.world.generator.AGenerator;
import ru.BouH_.world.generator.save.WorldSaveCity;
import ru.BouH_.world.type.IWorldWithCities;
import ru.BouH_.world.type.WorldTypeCrazyZp;

import java.util.HashSet;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class SpecialGenerator extends AGenerator {
    public static SpecialGenerator instance = new SpecialGenerator();
    private final CityGenerator cityGenerator;
    private final IndustryGenerator industryGenerator;
    private final MilitaryGenerator militaryGenerator;
    public Set<WorldSaveCity.CityInfo> cities;

    protected SpecialGenerator() {
        super(0);
        this.cities = new HashSet<>();
        this.cityGenerator = new CityGenerator(this);
        this.industryGenerator = new IndustryGenerator(this);
        this.militaryGenerator = new MilitaryGenerator(this);
    }

    @SubscribeEvent
    public void onWorldLoad(WorldEvent.Load ev) {
        WorldSaveCity.getStorage(ev.world);
    }

    @SubscribeEvent
    public void onWorldSave(WorldEvent.Save ev) {
        Objects.requireNonNull(WorldSaveCity.getStorage(ev.world)).markDirty();
    }

    //6224601282917129618
    @Override
    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkProvider chunkGenerator, IChunkProvider chunkProvider) {
        if (world.getWorldInfo().getTerrainType() instanceof IWorldWithCities) {
            IWorldWithCities iWorldWithCities = (IWorldWithCities) world.getWorldInfo().getTerrainType();
            int x = chunkX * 16 + 8;
            int z = chunkZ * 16 + 8;
            int y = this.findY(world, x, z);
            if (this.checkRegion(world, x, z)) {
                ICityGen iCityGen = this.chooseCityGen((ICityBiome) world.getBiomeGenForCoords(x, z));
                int i1 = iCityGen.getScale(world);
                int i2 = iCityGen.defaultMatrixSize() + ((world.getWorldInfo().getTerrainType() instanceof WorldTypeCrazyZp) ? -1 : (Main.rand.nextInt(3) - 1));

                int pX = x + (i1 * (i2 - 1)) / 2;
                int pZ = z + (i1 * (i2 - 1)) / 2;
                if (this.isPointInsideCityRange(null, pX, pZ, ((i2 * i1) * 2 + (iCityGen.getScale(world) / 2 + 2)) + 1 + iWorldWithCities.getDistanceBetweenCities())) {
                    return;
                }
                iCityGen.setMatrixSize(i2);
                CityCheckResult cityCheckResult = iCityGen.tryGenCity(world, x, y, z);
                if (cityCheckResult != null) {
                    this.cities.add(new WorldSaveCity.CityInfo(cityCheckResult.getCityType(), pX, pZ));
                    System.out.println(x + " " + z);
                    iCityGen.genCity(random, world, cityCheckResult.getX(), cityCheckResult.getY(), cityCheckResult.getZ());
                    System.gc();

                    for (Object e : world.playerEntities) {
                        EntityPlayer entityPlayer = (EntityPlayer) e;
                        int ix = MathHelper.floor_double(entityPlayer.posX);
                        int iz = MathHelper.floor_double(entityPlayer.posZ);
                        if (this.isPointInsideCity(cityCheckResult, ix, iz, i2 * iCityGen.getScale(world))) {
                            entityPlayer.setPosition(ix, entityPlayer.worldObj.getPrecipitationHeight(ix, iz) + 1, iz);
                        }
                    }
                }
            }
        }
    }

    private ICityGen chooseCityGen(ICityBiome cityBiome) {
        if (cityBiome == CommonProxy.biome_city) {
            return this.cityGenerator;
        }
        if (cityBiome == CommonProxy.biome_industry) {
            return this.industryGenerator;
        }
        if (cityBiome == CommonProxy.biome_military) {
            return this.militaryGenerator;
        }
        return null;
    }

    public boolean checkRegion(World world, int x, int z) {
        return world.getBiomeGenForCoords(x, z) instanceof ICityBiome;
    }

    public boolean isPointInsideCity(CityCheckResult cityCheckResult, int pX, int pZ, int range) {
        int i1 = range / 2;
        int startX = cityCheckResult.getX() - i1;
        int startZ = cityCheckResult.getZ() - i1;
        int widthX = cityCheckResult.getX() + i1;
        int widthZ = cityCheckResult.getZ() + i1;
        return pX >= startX && pZ >= startZ && pX <= widthX && pZ <= widthZ;
    }

    public boolean isPointInsideCityRange(CityType cityType, int pX, int pZ, int range) {
        for (WorldSaveCity.CityInfo cityInfo : this.cities) {
            int i1 = range / 2;
            int startX = cityInfo.getX() - i1;
            int startZ = cityInfo.getZ() - i1;
            int widthX = cityInfo.getX() + i1;
            int widthZ = cityInfo.getZ() + i1;
            if ((cityType == null || cityInfo.getCityType() == cityType) && pX >= startX && pZ >= startZ && pX <= widthX && pZ <= widthZ) {
                return true;
            }
        }
        return false;
    }

    private int findY(World world, int x, int z) {
        return world.getPrecipitationHeight(x, z) - 1;
    }

    @Override
    public void loadStructures() {
        this.cityGenerator.loadStructures();
        this.industryGenerator.loadStructures();
        this.militaryGenerator.loadStructures();
    }
}
