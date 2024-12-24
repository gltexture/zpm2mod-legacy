package ru.BouH_.world.structures.building;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLog;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;
import ru.BouH_.Main;
import ru.BouH_.misc.RotationHelperNew;
import ru.BouH_.world.biome.BiomeRad;
import ru.BouH_.world.structures.base.AStructure;
import ru.BouH_.world.structures.base.StructureHolder;

public class LabStructure extends AStructure {
    public LabStructure(StructureHolder structureHolder, int chance, BiomeGenBase... biomes) {
        super(structureHolder, chance, biomes);
    }

    @Override
    public boolean runGenerator(World world, int x, int y, int z, int rotation) {
        int maxX = 0;
        int maxZ = 0;
        int medX = x;
        int medZ = z;
        switch (rotation) {
            case 1: {
                x += this.getStructureHolder().getMaxZ() / 2;
                z -= this.getStructureHolder().getMaxX() / 2;
                maxX = x - this.getStructureHolder().getMaxZ();
                maxZ = z + this.getStructureHolder().getMaxX();
                medZ += 1;
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
                medX += 1;
                break;
            }
            default: {
                x -= this.getStructureHolder().getMaxX() / 2;
                z -= this.getStructureHolder().getMaxZ() / 2;
                medX += 1;
                medZ += 1;
                maxX = x + this.getStructureHolder().getMaxX();
                maxZ = z + this.getStructureHolder().getMaxZ();
            }
        }
        int dMinX = Math.min(x, maxX);
        int dMaxX = Math.max(x, maxX);
        int dMinZ = Math.min(z, maxZ);
        int dMaxZ = Math.max(z, maxZ);
        if (!(world.getBiomeGenForCoords(dMinX, dMinZ) instanceof BiomeRad && world.getBiomeGenForCoords(dMinX, dMaxZ) instanceof BiomeRad && world.getBiomeGenForCoords(dMaxX, dMinZ) instanceof BiomeRad && world.getBiomeGenForCoords(dMaxX, dMaxZ) instanceof BiomeRad)) {
            return false;
        }
        y -= this.getStructureHolder().getDeltaY();
        int curY = y + this.getStructureHolder().getMaxY() + this.getStructureHolder().getDeltaY();
        for (int i = dMinX; i <= dMaxX; i++) {
            for (int j = dMinZ; j <= dMaxZ; j++) {
                int minY = y - 1;
                while (!world.getBlock(i, minY, j).isOpaqueCube() && minY > 0) {
                    world.setBlock(i, minY, j, Blocks.stone);
                    minY -= 1;
                }
                for (int k = y; k <= y + this.getStructureHolder().getMaxY(); k++) {
                    world.setBlockToAir(i, k, j);
                }
            }
        }
        for (StructureHolder.BlockState blockState : this.getStructureHolder().getBlockStates()) {
            this.translateToWorld(world, x, y, z, blockState, rotation);
        }
        for (StructureHolder.BlockState blockState : this.getStructureHolder().getDecorativeBlockStates()) {
            this.translateToWorld(world, x, y, z, blockState, rotation);
        }
        while (!(world.getBlock(medX, curY, medZ) instanceof BlockLog || world.getBlock(medX, curY, medZ) == Blocks.grass || world.canBlockSeeTheSky(medX, curY, medZ))) {
            world.setBlockToAir(medX, curY, medZ);
            world.setBlockToAir(medX - 1, curY, medZ);
            world.setBlockToAir(medX, curY, medZ - 1);
            world.setBlockToAir(medX - 1, curY, medZ - 1);

            world.setBlock(medX + 1, curY, medZ - 2, Blocks.stonebrick, Main.rand.nextFloat() <= 0.2f ? 1 : 0, 2);
            world.setBlock(medX + 1, curY, medZ - 1, Blocks.stonebrick, Main.rand.nextFloat() <= 0.2f ? 1 : 0, 2);
            world.setBlock(medX + 1, curY, medZ, Blocks.stonebrick, Main.rand.nextFloat() <= 0.2f ? 1 : 0, 2);
            world.setBlock(medX + 1, curY, medZ + 1, Blocks.stonebrick, Main.rand.nextFloat() <= 0.2f ? 1 : 0, 2);

            world.setBlock(medX - 2, curY, medZ - 2, Blocks.stonebrick, Main.rand.nextFloat() <= 0.2f ? 1 : 0, 2);
            world.setBlock(medX - 2, curY, medZ - 1, Blocks.stonebrick, Main.rand.nextFloat() <= 0.2f ? 1 : 0, 2);
            world.setBlock(medX - 2, curY, medZ, Blocks.stonebrick, Main.rand.nextFloat() <= 0.2f ? 1 : 0, 2);
            world.setBlock(medX - 2, curY, medZ + 1, Blocks.stonebrick, Main.rand.nextFloat() <= 0.2f ? 1 : 0, 2);

            world.setBlock(medX - 1, curY, medZ + 1, Blocks.stonebrick, Main.rand.nextFloat() <= 0.2f ? 1 : 0, 2);
            world.setBlock(medX, curY, medZ + 1, Blocks.stonebrick, Main.rand.nextFloat() <= 0.2f ? 1 : 0, 2);

            world.setBlock(medX - 1, curY, medZ - 2, Blocks.stonebrick, Main.rand.nextFloat() <= 0.2f ? 1 : 0, 2);
            world.setBlock(medX, curY, medZ - 2, Blocks.stonebrick, Main.rand.nextFloat() <= 0.2f ? 1 : 0, 2);

            switch (rotation) {
                case 0: {
                    if (Main.rand.nextBoolean()) {
                        world.setBlock(medX, curY, medZ - 1, Blocks.ladder, 4, 2);
                    }
                    if (Main.rand.nextBoolean()) {
                        world.setBlock(medX, curY, medZ, Blocks.ladder, 4, 2);
                    }
                    break;
                }
                case 1: {
                    if (Main.rand.nextBoolean()) {
                        world.setBlock(medX - 1, curY, medZ, Blocks.ladder, 2, 2);
                    }
                    if (Main.rand.nextBoolean()) {
                        world.setBlock(medX, curY, medZ, Blocks.ladder, 2, 2);
                    }
                    break;
                }
                case 2: {
                    if (Main.rand.nextBoolean()) {
                        world.setBlock(medX - 1, curY, medZ, Blocks.ladder, 5, 2);
                    }
                    if (Main.rand.nextBoolean()) {
                        world.setBlock(medX - 1, curY, medZ - 1, Blocks.ladder, 5, 2);
                    }
                    break;
                }
                case 3: {
                    if (Main.rand.nextBoolean()) {
                        world.setBlock(medX - 1, curY, medZ - 1, Blocks.ladder, 3, 2);
                    }
                    if (Main.rand.nextBoolean()) {
                        world.setBlock(medX, curY, medZ - 1, Blocks.ladder, 3, 2);
                    }
                    break;
                }
            }
            curY += 1;
        }
        for (int i = medX - 2; i < medX + 2; i++) {
            for (int j = medZ - 2; j < medZ + 2; j++) {
                world.setBlock(i, curY, j, Blocks.dirt, 2, 2);
            }
        }
        return true;
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
}
