package ru.BouH_.world.generator.save;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import ru.BouH_.Main;
import ru.BouH_.world.generator.cities.CityType;
import ru.BouH_.world.generator.cities.SpecialGenerator;

import java.io.*;
import java.util.HashSet;

public class WorldSaveCity extends WorldSavedData {
    public WorldSaveCity(String id) {
        super(id);
    }

    public static WorldSaveCity getStorage(World world) {
        String ID = WorldSaveCity.class.getName() + "_" + world.provider.dimensionId;
        if (world.mapStorage != null) {
            WorldSaveCity data = (WorldSaveCity) world.mapStorage.loadData(WorldSaveCity.class, ID);
            if (data != null) {
                return data;
            } else {
                data = new WorldSaveCity(ID);
                data.markDirty();
                world.mapStorage.setData(ID, data);
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void readFromNBT(NBTTagCompound p_76184_1_) {
        try (ByteArrayInputStream byteArrayOutputStream = new ByteArrayInputStream(p_76184_1_.getByteArray("city_gen"))) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayOutputStream)) {
                SpecialGenerator.instance.cities = (HashSet<CityInfo>) objectInputStream.readObject();
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void writeToNBT(NBTTagCompound p_76187_1_) {
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream)) {
                objectOutputStream.writeObject(SpecialGenerator.instance.cities);
            }
            p_76187_1_.setByteArray("city_gen", byteArrayOutputStream.toByteArray());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static class CityInfo implements Serializable {
        private final int x;
        private final int z;
        private final CityType cityType;

        public CityInfo(CityType cityType, int x, int z) {
            this.cityType = cityType;
            this.x = x;
            this.z = z;
        }

        public CityType getCityType() {
            return this.cityType;
        }

        public int getX() {
            return this.x;
        }

        public int getZ() {
            return this.z;
        }
    }
}
