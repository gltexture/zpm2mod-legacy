package ru.BouH_.world.structures.building;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;
import ru.BouH_.Main;
import ru.BouH_.init.BlocksZp;
import ru.BouH_.misc.RotationHelperNew;
import ru.BouH_.world.structures.base.AStructure;
import ru.BouH_.world.structures.base.StructureHolder;

public class SubmarineStructure extends AStructure {
    public SubmarineStructure(StructureHolder structureHolder, int chance, BiomeGenBase... biomes) {
        super(structureHolder, chance, biomes);
    }

    @Override
    public boolean runGenerator(World world, int x, int y, int z, int rotation) {
        int maxX = 0;
        int maxZ = 0;
        switch (rotation) {
            case 1: {
                x += this.getStructureHolder().getMaxZ() / 2;
                z -= this.getStructureHolder().getMaxX() / 2;
                maxX = x - this.getStructureHolder().getMaxZ();
                maxZ = z + this.getStructureHolder().getMaxX();
                break;
            }
            case 2: {
                x += this.getStructureHolder().getMaxX() / 2;
                z += this.getStructureHolder().getMaxZ() / 2;
                maxX = x - this.getStructureHolder().getMaxX();
                maxZ = z - this.getStructureHolder().getMaxZ();
                break;
            }
            case 3: {
                x -= this.getStructureHolder().getMaxZ() / 2;
                z += this.getStructureHolder().getMaxX() / 2;
                maxX = x + this.getStructureHolder().getMaxZ();
                maxZ = z - this.getStructureHolder().getMaxX();
                break;
            }
            default: {
                x -= this.getStructureHolder().getMaxX() / 2;
                z -= this.getStructureHolder().getMaxZ() / 2;
                maxX = x + this.getStructureHolder().getMaxX();
                maxZ = z + this.getStructureHolder().getMaxZ();
            }
        }
        int dMinX = Math.min(x, maxX);
        int dMaxX = Math.max(x, maxX);
        int dMinZ = Math.min(z, maxZ);
        int dMaxZ = Math.max(z, maxZ);
        if (this.checkRegion(world, dMinX, y, dMinZ, dMaxX, this.getStructureHolder().getMaxY() + y, dMaxZ)) {
            y -= this.getStructureHolder().getDeltaY();
            for (StructureHolder.BlockState blockState : this.getStructureHolder().getBlockStates()) {
                this.translateToWorld(world, x, y, z, blockState, rotation);
            }
            for (StructureHolder.BlockState blockState : this.getStructureHolder().getDecorativeBlockStates()) {
                this.translateToWorld(world, x, y, z, blockState, rotation);
            }

            for (int i = dMinX; i <= dMaxX; i++) {
                for (int j = dMinZ; j <= dMaxZ; j++) {
                    for (int k = y; k <= y + this.getStructureHolder().getMaxY(); k++) {
                        if (world.getBlock(i, k, j).getMaterial() == Material.air) {
                            world.setBlock(i, k, j, Blocks.water);
                        }
                    }
                }
            }

            int garbageWidthX = 8;
            int garbageWidthZ = 8;

            for (int i = dMinX - garbageWidthX; i <= dMinX - 1; i++) {
                for (int j = dMinZ - garbageWidthZ; j <= dMaxZ + garbageWidthZ; j++) {
                    if (Main.rand.nextFloat() <= 0.25f) {
                        int newY = this.findY(world, i, y + this.getStructureHolder().getMaxY() / 2, j) + 1;
                        if (world.getBlock(i, newY, j).getMaterial().isReplaceable()) {
                            world.setBlock(i, newY, j, Main.rand.nextFloat() <= 0.7f ? Blocks.cobblestone : Main.rand.nextFloat() <= 0.8f ? BlocksZp.scrap : Blocks.obsidian);
                        }
                    }
                }
            }

            for (int i = dMaxX + 1; i <= dMaxX + garbageWidthX; i++) {
                for (int j = dMinZ - garbageWidthZ; j <= dMaxZ + garbageWidthZ; j++) {
                    if (Main.rand.nextFloat() <= 0.25f) {
                        int newY = this.findY(world, i, y + this.getStructureHolder().getMaxY() / 2, j) + 1;
                        if (world.getBlock(i, newY, j).getMaterial().isReplaceable()) {
                            world.setBlock(i, newY, j, Main.rand.nextFloat() <= 0.7f ? Blocks.cobblestone : Main.rand.nextFloat() <= 0.8f ? BlocksZp.scrap : Blocks.obsidian);
                        }
                    }
                }
            }

            for (int i = dMinX; i <= dMaxX; i++) {
                for (int j = dMinZ - garbageWidthZ; j <= dMinZ - 1; j++) {
                    if (Main.rand.nextFloat() <= 0.25f) {
                        int newY = this.findY(world, i, y + this.getStructureHolder().getMaxY() / 2, j) + 1;
                        if (world.getBlock(i, newY, j).getMaterial().isReplaceable()) {
                            world.setBlock(i, newY, j, Main.rand.nextFloat() <= 0.7f ? Blocks.cobblestone : Main.rand.nextFloat() <= 0.8f ? BlocksZp.scrap : Blocks.obsidian);
                        }
                    }
                }
            }

            for (int i = dMinX; i <= dMaxX; i++) {
                for (int j = dMaxZ + 1; j <= dMaxZ + garbageWidthZ + 1; j++) {
                    if (Main.rand.nextFloat() <= 0.25f) {
                        int newY = this.findY(world, i, y + this.getStructureHolder().getMaxY() / 2, j) + 1;
                        if (world.getBlock(i, newY, j).getMaterial().isReplaceable()) {
                            world.setBlock(i, newY, j, Main.rand.nextFloat() <= 0.7f ? Blocks.cobblestone : Main.rand.nextFloat() <= 0.8f ? BlocksZp.scrap : Blocks.obsidian);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    private int findY(World world, int x, int startY, int z) {
        while (!world.getBlock(x, startY, z).isOpaqueCube()) {
            startY -= 1;
        }
        return startY;
    }

    private void translateToWorld(World world, int x, int y, int z, StructureHolder.BlockState blockState, int rotation) {
        Block block = Block.getBlockFromName(blockState.getId());
        int posX = x + blockState.getX();
        int posZ = z + blockState.getZ();
        int posY = blockState.getY() + y;

        switch (rotation) {
            case 1: {
                posX = x - blockState.getZ();
                posZ = z + blockState.getX();
                break;
            }
            case 2: {
                posX = x - blockState.getX();
                posZ = z - blockState.getZ();
                break;
            }
            case 3: {
                posX = x + blockState.getZ();
                posZ = z - blockState.getX();
                break;
            }
        }

        int meta = blockState.getMeta();

        ForgeDirection[] forgeDirections = RotationHelperNew.getValidVanillaBlockRotations(block);
        if (forgeDirections.length > 0) {
            for (int i = 0; i < rotation; i++) {
                int newMeta = RotationHelperNew.rotateBlock(block, posX, posY, posZ, meta, forgeDirections[0]);
                if (newMeta != -1) {
                    meta = newMeta;
                }
            }
        }

        world.setBlock(posX, posY, posZ, block, meta, 2);
        world.setBlockMetadataWithNotify(posX, posY, posZ, meta, 2);
    }

    private boolean checkRegion(World world, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        if (world.getBlock(minX, minY, minZ).isOpaqueCube()) {
            for (int i = minX; i <= maxX; i++) {
                for (int k = minY + 1; k <= maxY + 8; k++) {
                    for (int j = minZ; j <= maxZ; j++) {
                        Block block = world.getBlock(i, k, j);
                        if (block.getMaterial() != Material.water) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
}
