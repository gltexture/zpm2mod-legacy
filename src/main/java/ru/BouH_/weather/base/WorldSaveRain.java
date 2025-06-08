package ru.BouH_.weather.base;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import ru.BouH_.Main;
import ru.BouH_.weather.managers.WeatherRainManager;
import ru.BouH_.world.generator.save.WorldSaveEvents;

import java.io.*;

public class WorldSaveRain extends WorldSavedData {
    public WorldSaveRain(String id) {
        super(id);
    }

    public static WorldSaveRain getStorage(World world) {
        String ID = WorldSaveRain.class.getName() + "_" + world.provider.dimensionId;
        if (world.mapStorage != null) {
            WorldSaveRain data = (WorldSaveRain) world.mapStorage.loadData(WorldSaveRain.class, ID);
            if (data != null) {
                return data;
            } else {
                data = new WorldSaveRain(ID);
                data.markDirty();
                world.mapStorage.setData(ID, data);
            }
        }
        return null;
    }

    @Override
    public void readFromNBT(NBTTagCompound p_76184_1_) {
        WeatherHandler.instance.rainManagerMap.forEach((t, e) -> {
            try (ByteArrayInputStream byteArrayOutputStream = new ByteArrayInputStream(p_76184_1_.getByteArray(e.name))) {
                try (ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayOutputStream)) {
                    WeatherRainManager weatherRainManager = (WeatherRainManager) objectInputStream.readObject();
                    e.setTimeTicks(weatherRainManager.getTimeTicks());
                    e.setStarted(weatherRainManager.isStarted());
                    e.setTimeUntilStart(weatherRainManager.getTimeUntilStart());
                    e.setRainStrength(weatherRainManager.getRainStrength());
                    e.setWeatherRainMixer(weatherRainManager.getWeatherRainMixer());
                    e.setCurrentWeatherRain(weatherRainManager.getCurrentWeatherRain());
                    e.setCloudyStrength(weatherRainManager.getCloudyStrength());
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    @Override
    public void writeToNBT(NBTTagCompound p_76187_1_) {
        WeatherHandler.instance.rainManagerMap.forEach((t, e) -> {
            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
                    objectOutputStream.writeObject(e);
                }
                p_76187_1_.setByteArray(e.name, byteArrayOutputStream.toByteArray());
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
    }
}
