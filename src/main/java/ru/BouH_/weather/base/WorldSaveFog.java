package ru.BouH_.weather.base;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import ru.BouH_.Main;
import ru.BouH_.weather.managers.WeatherFogManager;
import ru.BouH_.world.generator.save.WorldSaveEvents;

import java.io.*;

public class WorldSaveFog extends WorldSavedData {
    public WorldSaveFog(String id) {
        super(id);
    }

    public static WorldSaveFog getStorage(World world) {
        String ID = WorldSaveFog.class.getName() + "_" + world.provider.dimensionId;
        if (world.mapStorage != null) {
            WorldSaveFog data = (WorldSaveFog) world.mapStorage.loadData(WorldSaveFog.class, ID);
            if (data != null) {
                return data;
            } else {
                data = new WorldSaveFog(ID);
                data.markDirty();
                world.mapStorage.setData(ID, data);
            }
        }
        return null;
    }

    @Override
    public void readFromNBT(NBTTagCompound p_76184_1_) {
        WeatherHandler.instance.fogManagerMap.forEach((t, e) -> {
            try (ByteArrayInputStream byteArrayOutputStream = new ByteArrayInputStream(p_76184_1_.getByteArray(e.name))) {
                try (ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayOutputStream)) {
                    WeatherFogManager weatherFogManager = (WeatherFogManager) objectInputStream.readObject();
                    e.setTimeTicks(weatherFogManager.getTimeTicks());
                    e.setStarted(weatherFogManager.isStarted());
                    e.setTimeUntilStart(weatherFogManager.getTimeUntilStart());
                    e.setDepth(weatherFogManager.getDepth());
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
        WeatherHandler.instance.fogManagerMap.forEach((t, e) -> {
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
