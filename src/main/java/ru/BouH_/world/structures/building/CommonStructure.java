package ru.BouH_.world.structures.building;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;
import ru.BouH_.Main;
import ru.BouH_.misc.RotationHelperNew;
import ru.BouH_.world.biome.BiomeMilitary;
import ru.BouH_.world.structures.base.AStructure;
import ru.BouH_.world.structures.base.StructureHolder;

import java.util.Objects;

public class CommonStructure extends AStructure {
    private Block footing;
    private Block footing2;

    public CommonStructure(StructureHolder structureHolder, int chance, BiomeGenBase... biomes) {
        super(structureHolder, chance, biomes);
        this.footing = Blocks.grass;
        this.footing2 = Blocks.dirt;
    }

    public CommonStructure(StructureHolder structureHolder, Block footing, Block footing2, int chance, BiomeGenBase... biomes) {
        this(structureHolder, chance, biomes);
        this.footing = footing;
        this.footing2 = footing2;
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
            for (int i = dMinX; i <= dMaxX; i++) {
                for (int j = dMinZ; j <= dMaxZ; j++) {
                    int minY = y - 1;

                    if (this.footing2 != null) {
                        while (!world.getBlock(i, minY, j).isOpaqueCube() && minY > 0) {
                            world.setBlock(i, minY, j, this.footing2);
                            minY -= 1;
                        }
                    }

                    for (int k = y + 1; k <= y + this.getStructureHolder().getMaxY(); k++) {
                        world.setBlockToAir(i, k, j);
                    }
                }
            }
            if (this.footing != null) {
                for (int i = dMinX - 1; i <= dMaxX + 1; i++) {
                    if (Main.rand.nextBoolean()) {
                        if (world.getBlock(i, y - 1, dMinZ - 1) == this.footing) {
                            if (world.getBlock(i, y, dMinZ - 1).getMaterial().isReplaceable()) {
                                world.setBlock(i, y, dMinZ - 1, this.footing);
                            }
                        }
                    }
                    if (Main.rand.nextBoolean()) {
                        if (world.getBlock(i, y - 1, dMaxZ + 1) == this.footing) {
                            if (world.getBlock(i, y, dMaxZ + 1).getMaterial().isReplaceable()) {
                                world.setBlock(i, y, dMaxZ + 1, this.footing);
                            }
                        }
                    }
                }
                for (int i = dMinZ; i <= dMaxZ; i++) {
                    if (Main.rand.nextBoolean()) {
                        if (world.getBlock(dMinX - 1, y - 1, i) == this.footing) {
                            if (world.getBlock(dMinX - 1, y, i).getMaterial().isReplaceable()) {
                                world.setBlock(dMinX - 1, y, i, this.footing);
                            }
                        }
                    }
                    if (Main.rand.nextBoolean()) {
                        if (world.getBlock(dMaxX + 1, y - 1, i) == this.footing) {
                            if (world.getBlock(dMaxX + 1, y, i).getMaterial().isReplaceable()) {
                                world.setBlock(dMaxX + 1, y, i, this.footing);
                            }
                        }
                    }
                }
            }
            for (StructureHolder.BlockState blockState : this.getStructureHolder().getBlockStates()) {
                this.translateToWorld(world, x, y, z, blockState, rotation);
            }
            for (StructureHolder.BlockState blockState : this.getStructureHolder().getDecorativeBlockStates()) {
                this.translateToWorld(world, x, y, z, blockState, rotation);
            }
            BiomeGenBase base = world.getBiomeGenForCoords(x, z);

            if (!(base instanceof BiomeMilitary)) {
                float f = base.getFloatTemperature(x, y, z);
                if (f <= 0.15F) {
                    for (int i = dMinX - 1; i <= dMaxX + 1; i++) {
                        for (int j = dMinZ - 1; j <= dMaxZ + 1; j++) {
                            int newY = world.getPrecipitationHeight(i, j);
                            if (Blocks.snow_layer.canPlaceBlockAt(world, i, newY, j)) {
                                world.setBlock(i, newY, j, Blocks.snow_layer);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    private void translateToWorld(World world, int x, int y, int z, StructureHolder.BlockState blockState, int rotation) {
        Block block = Block.getBlockFromName(blockState.getId());
        int posX = x + blockState.getX();
        int posZ = z + blockState.getZ();
        int posY = blockState.getY() + y;

        if (block instanceof BlockBush) {
            if (!block.canPlaceBlockAt(world, posX, posY, posZ)) {
                return;
            }
        }

        if (this.footing != null) {
            if (this.footing != Blocks.grass && block == Blocks.grass) {
                block = this.footing;
            }
        }

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
        if (this.checkBase(world, minX, minY, minZ, maxX, maxZ)) {
            for (int i = minX; i <= maxX; i++) {
                for (int k = minY + 1; k <= maxY; k++) {
                    for (int j = minZ; j <= maxZ; j++) {
                        Block block = world.getBlock(i, k, j);
                        if (!(block.getMaterial().isReplaceable() || block instanceof BlockBush)) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    private boolean checkBase(World world, int minX, int minY, int minZ, int maxX, int maxZ) {
        if (this.footing == null) {
            return true;
        }
        boolean flag1 = world.getBlock(minX, minY, minZ) == this.footing;
        boolean flag2 = world.getBlock(maxX, minY, minZ) == this.footing || world.getBlock(maxX, minY - 1, minZ) == this.footing;
        boolean flag3 = world.getBlock(minX, minY, maxZ) == this.footing || world.getBlock(minX, minY - 1, maxZ) == this.footing;
        boolean flag4 = world.getBlock(maxX, minY, maxZ) == this.footing || world.getBlock(maxX, minY - 1, maxZ) == this.footing;
        return flag1 && flag2 && flag3 && flag4;
    }
}
