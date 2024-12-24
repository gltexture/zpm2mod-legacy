package ru.BouH_.world.generator.save;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.WorldSavedData;
import ru.BouH_.Main;
import ru.BouH_.world.generator.DynamicEventsGenerator;

import java.io.*;
import java.util.Set;

public class WorldSaveEvents extends WorldSavedData {
    private static final String ID = Main.MODID + "d4";

    public WorldSaveEvents(String id) {
        super(id);
    }

    public static WorldSaveEvents getStorage(World world) {
        if (world.mapStorage != null) {
            WorldSaveEvents data = (WorldSaveEvents) world.mapStorage.loadData(WorldSaveEvents.class, ID);
            if (data != null) {
                return data;
            } else {
                data = new WorldSaveEvents(ID);
                data.markDirty();
                world.mapStorage.setData(ID, data);
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void readFromNBT(NBTTagCompound p_76184_1_) {
        try (ByteArrayInputStream byteArrayOutputStream = new ByteArrayInputStream(p_76184_1_.getByteArray("event_gen"))) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayOutputStream)) {
                DynamicEventsGenerator.instance.targets = (Set<DynamicEventsGenerator.Target>) objectInputStream.readObject();
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
                objectOutputStream.writeObject(DynamicEventsGenerator.instance.targets);
            }
            p_76187_1_.setByteArray("event_gen", byteArrayOutputStream.toByteArray());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static class CityInfo implements Serializable {
        private final int x;
        private final int z;

        public CityInfo(int x, int z) {
            this.x = x;
            this.z = z;
        }

        public int getX() {
            return this.x;
        }

        public int getZ() {
            return this.z;
        }
    }
}
