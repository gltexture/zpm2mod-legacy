package ru.BouH_.world.structures.building;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;
import ru.BouH_.Main;
import ru.BouH_.misc.RotationHelperNew;
import ru.BouH_.world.structures.base.AStructure;
import ru.BouH_.world.structures.base.StructureHolder;

public class NatureStructure extends AStructure {
    private Block footing;
    private Block footing2;

    public NatureStructure(StructureHolder structureHolder, int chance, BiomeGenBase... biomes) {
        super(structureHolder, chance, biomes);
        this.footing = Blocks.grass;
        this.footing2 = Blocks.dirt;
    }

    public NatureStructure(StructureHolder structureHolder, Block footing, Block footing2, int chance, BiomeGenBase... biomes) {
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
                    if (this.footing != null) {
                        if (Main.rand.nextBoolean()) {
                            if (i == dMinX) {
                                if (world.getBlock(i - 1, minY - 1, j) == this.footing) {
                                    if (world.getBlock(i - 1, minY, j).getMaterial().isReplaceable()) {
                                        world.setBlock(i - 1, minY, j, this.footing);
                                    }
                                }
                            } else if (i == dMaxX) {
                                if (world.getBlock(i + 1, minY - 1, j) == this.footing) {
                                    if (world.getBlock(i + 1, minY, j).getMaterial().isReplaceable()) {
                                        world.setBlock(i + 1, minY, j, this.footing);
                                    }
                                }
                            } else if (j == dMinZ) {
                                if (world.getBlock(i, minY - 1, j - 1) == this.footing) {
                                    if (world.getBlock(i, minY, j - 1).getMaterial().isReplaceable()) {
                                        world.setBlock(i, minY, j, this.footing);
                                    }
                                }
                            } else if (j == dMaxZ) {
                                if (world.getBlock(i, minY - 1, j + 1) == this.footing) {
                                    if (world.getBlock(i, minY, j + 1).getMaterial().isReplaceable()) {
                                        world.setBlock(i, minY, j + 1, this.footing);
                                    }
                                }
                            }
                        }
                    }

                    if (this.footing2 != null) {
                        while (!world.getBlock(i, minY, j).isOpaqueCube() && minY > 0) {
                            world.setBlock(i, minY, j, this.footing2);
                            minY -= 1;
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
            float f = world.getBiomeGenForCoords(x, z).getFloatTemperature(x, y, z);
            if (f <= 0.15F) {
                for (int i = dMinX - 1; i <= dMaxX + 1; i++) {
                    for (int j = dMinZ - 1; j <= dMaxZ + 1; j++) {
                        int newY = world.getPrecipitationHeight(i, j) + 1;
                        if (Blocks.snow_layer.canPlaceBlockAt(world, i, newY, j)) {
                            world.setBlock(i, newY, j, Blocks.snow_layer);
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
        boolean flag1 = world.getBlock(minX, minY, minZ) == this.footing;
        boolean flag2 = world.getBlock(maxX, minY, minZ) == this.footing || world.getBlock(maxX, minY - 1, minZ) == this.footing;
        boolean flag3 = world.getBlock(minX, minY, maxZ) == this.footing || world.getBlock(minX, minY - 1, maxZ) == this.footing;
        boolean flag4 = world.getBlock(maxX, minY, maxZ) == this.footing || world.getBlock(maxX, minY - 1, maxZ) == this.footing;
        return flag1 && flag2 && flag3 && flag4;
    }
}
