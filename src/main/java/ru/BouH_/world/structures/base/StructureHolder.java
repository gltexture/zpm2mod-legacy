package ru.BouH_.world.structures.base;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.block.*;
import ru.BouH_.Main;
import ru.BouH_.blocks.BlockTorchLamp;
import ru.BouH_.blocks.BlockWire;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class StructureHolder implements Serializable {
    public static HashMap<String, StructureHolder> cache = new HashMap<>();
    
    private static final long serialVersionUID = -228L;
    private final Set<BlockState> blockStates = new HashSet<>();
    private final Set<BlockState> decorativeBlockStates = new HashSet<>();
    private int maxX;
    private int maxY;
    private int deltaY;
    private int maxZ;

    public StructureHolder() {

    }

    private StructureHolder(String name) {
        if (!this.loadFile(name)) {
            FMLLog.bigWarning("Structure not found: " + name);
        } else {
            FMLLog.info("Structure loaded: " + name);
        }
    }

    public static StructureHolder create(String name) {
        if (StructureHolder.cache.containsKey(name)) {
            return StructureHolder.cache.get(name);
        }
        StructureHolder structureHolder = new StructureHolder(name);
        StructureHolder.cache.put(name, structureHolder);
        return structureHolder;
    }
    
    public static void reconvert(String string, StructureHolder structureHolder) {
        if (structureHolder != null) {
            StructureHolder structureHolder1 = new StructureHolder();
            Set<BlockState> blockStates1 = new HashSet<>();
            blockStates1.addAll(structureHolder.getBlockStates());
            blockStates1.addAll(structureHolder.getDecorativeBlockStates());
            for (BlockState block : blockStates1) {
                Block blocks = Block.getBlockFromName(block.id);
                if (blocks instanceof BlockCake || blocks instanceof BlockCarpet || blocks instanceof BlockBed || blocks.getMaterial().isLiquid() || blocks instanceof BlockTorch || blocks instanceof BlockTorchLamp || blocks instanceof BlockRedstoneDiode || blocks instanceof BlockBasePressurePlate || blocks instanceof BlockButton || blocks instanceof BlockRail || blocks instanceof BlockWire || blocks instanceof BlockTripWire || block instanceof IGrowable || blocks instanceof BlockBush || blocks instanceof BlockVine || blocks instanceof BlockRedstoneWire || blocks instanceof BlockLever || blocks instanceof BlockSign || blocks instanceof BlockDoor || blocks instanceof BlockLadder || blocks instanceof BlockTrapDoor) {
                    structureHolder1.addDecorativeBlock(block.x, block.y, block.z, block.id, block.meta);
                } else {
                    structureHolder1.addBlock(block.x, block.y, block.z, block.id, block.meta);
                }
            }
            structureHolder1.setMaxX(structureHolder.maxX);
            structureHolder1.setMaxY(structureHolder.maxY);
            structureHolder1.setMaxZ(structureHolder.maxZ);
            structureHolder1.setDeltaY(structureHolder.deltaY - 1);
            try {
                FileOutputStream fileOutputStream = new FileOutputStream("gen\\" + string + ".struct");
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(structureHolder1);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void addBlock(int x, int y, int z, String id, int meta) {
        this.getBlockStates().add(new BlockState(x, y, z, id, meta));
    }

    public void addDecorativeBlock(int x, int y, int z, String id, int meta) {
        this.getDecorativeBlockStates().add(new BlockState(x, y, z, id, meta));
    }

    public Set<BlockState> getBlockStates() {
        return this.blockStates;
    }

    public Set<BlockState> getDecorativeBlockStates() {
        return this.decorativeBlockStates;
    }

    public int getMaxX() {
        return this.maxX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public int getMaxY() {
        return this.maxY;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }

    public int getMaxZ() {
        return this.maxZ;
    }

    public void setMaxZ(int maxZ) {
        this.maxZ = maxZ;
    }

    public int getDeltaY() {
        return this.deltaY;
    }

    public void setDeltaY(int deltaY) {
        this.deltaY = deltaY;
    }

    public boolean loadFile(String name) {
        InputStream inputStream = this.getClass().getResourceAsStream("/assets/" + Main.MODID + "/structures/" + name + ".struct");
        if (inputStream == null) {
            return false;
        }
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            StructureHolder structureHolder = (StructureHolder) objectInputStream.readObject();
            if (structureHolder != null) {
                //reconvert(name, structureHolder);
                this.blockStates.addAll(structureHolder.getBlockStates());
                this.decorativeBlockStates.addAll(structureHolder.getDecorativeBlockStates());
                this.maxX = structureHolder.getMaxX();
                this.maxY = structureHolder.getMaxY();
                this.maxZ = structureHolder.getMaxZ();
                this.deltaY = structureHolder.getDeltaY();
                return true;
            } else {
                return false;
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static class BlockState implements Serializable {
        private static final long serialVersionUID = 228L;
        private final int x;
        private final int y;
        private final int z;
        private final String id;
        private final int meta;

        public BlockState(int x, int y, int z, String id, int meta) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.id = id;
            this.meta = meta;
        }

        public int getX() {
            return this.x;
        }

        public int getY() {
            return this.y;
        }

        public int getZ() {
            return this.z;
        }

        public String getId() {
            return this.id;
        }

        public int getMeta() {
            return this.meta;
        }
    }
}
