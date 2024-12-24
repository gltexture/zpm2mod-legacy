package ru.BouH_.world.structures.base;

import net.minecraft.block.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import ru.BouH_.blocks.BlockTorchLamp;
import ru.BouH_.blocks.BlockWire;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class StructureSaver {
    public static StructureSaver instance = new StructureSaver();
    public Map<EntityPlayer, BlockPos> pos1 = new HashMap<>();
    public Map<EntityPlayer, BlockPos> pos2 = new HashMap<>();
    public int deltaY = 0;

    public void saveFile(World world, EntityPlayer player, String name) {
        StructureHolder structureHolder = new StructureHolder();
        BlockPos pos1 = this.pos1.get(player);
        BlockPos pos2 = this.pos2.get(player);
        int minX = Math.min(pos1.getX(), pos2.getX());
        int maxX = Math.max(pos1.getX(), pos2.getX());
        int minY = Math.min(pos1.getY(), pos2.getY());
        int maxY = Math.max(pos1.getY(), pos2.getY());
        int minZ = Math.min(pos1.getZ(), pos2.getZ());
        int maxZ = Math.max(pos1.getZ(), pos2.getZ());
        for (int i = minX; i <= maxX; i++) {
            for (int j = minY; j <= maxY; j++) {
                for (int k = minZ; k <= maxZ; k++) {
                    int x = i - minX;
                    int y = j - minY;
                    int z = k - minZ;
                    Block block = world.getBlock(i, j, k);
                    String id = Block.blockRegistry.getNameForObject(block);
                    int meta = world.getBlockMetadata(i, j, k);
                    if (!world.isAirBlock(i, j, k)) {
                        if (block instanceof BlockCake || block instanceof BlockCarpet || block instanceof BlockBed || block.getMaterial().isLiquid() || block instanceof BlockTorch || block instanceof BlockTorchLamp || block instanceof BlockRedstoneDiode || block instanceof BlockBasePressurePlate || block instanceof BlockButton || block instanceof BlockRail || block instanceof BlockWire || block instanceof BlockTripWire || block instanceof IGrowable || block instanceof BlockBush || block instanceof BlockVine || block instanceof BlockRedstoneWire || block instanceof BlockLever || block instanceof BlockSign || block instanceof BlockDoor || block instanceof BlockLadder || block instanceof BlockTrapDoor) {
                            structureHolder.addDecorativeBlock(x, y, z, id, meta);
                        } else {
                            structureHolder.addBlock(x, y, z, id, meta);
                        }
                    }
                }
            }
        }
        structureHolder.setMaxX(maxX - minX);
        structureHolder.setMaxY(maxY - minY);
        structureHolder.setMaxZ(maxZ - minZ);
        structureHolder.setDeltaY(this.deltaY);
        this.deltaY = 0;
        this.pos1.replace(player, null);
        this.pos2.replace(player, null);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream("gen\\" + name + ".struct");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(structureHolder);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static class BlockPos {
        private final int x;
        private final int y;
        private final int z;

        public BlockPos(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
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
    }
}