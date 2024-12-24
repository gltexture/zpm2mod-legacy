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

import java.util.Objects;
import java.util.Set;

public class CityStructure extends AStructure {
    private Block footing;
    private Block footing2;

    public CityStructure(StructureHolder structureHolder, int chance) {
        super(structureHolder, chance);
        this.footing = Blocks.grass;
        this.footing2 = Blocks.dirt;
    }

    public CityStructure(StructureHolder structureHolder, Block footing, Block footing2, int chance) {
        this(structureHolder, chance);
        this.footing = footing;
        this.footing2 = footing2;
    }

    @Override
    public boolean runGenerator(World world, int x, int y, int z, int rotation) {
        int maxX = 0;
        int maxZ = 0;
        switch (rotation) {
            case 1: {
                x += this.getStructureHolder().getMaxZ() / 2 + 1;
                z -= this.getStructureHolder().getMaxX() / 2;
                maxX = x - this.getStructureHolder().getMaxZ();
                maxZ = z + this.getStructureHolder().getMaxX();
                break;
            }
            case 2: {
                x += this.getStructureHolder().getMaxX() / 2 + 1;
                z += this.getStructureHolder().getMaxZ() / 2 + 1;
                maxX = x - this.getStructureHolder().getMaxX();
                maxZ = z - this.getStructureHolder().getMaxZ();
                break;
            }
            case 3: {
                x -= this.getStructureHolder().getMaxZ() / 2;
                z += this.getStructureHolder().getMaxX() / 2 + 1;
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

        int gravelWidthX = (32 - this.getStructureHolder().getMaxX()) / 2;
        int gravelWidthZ = (32 - this.getStructureHolder().getMaxZ()) / 2;

        for (int i = dMinX - gravelWidthX; i <= dMinX; i++) {
            for (int j = dMinZ - gravelWidthZ; j <= dMaxZ + gravelWidthZ; j++) {
                if (Main.rand.nextFloat() <= 0.3f) {
                    if (world.getBlock(i, y, j) == this.footing) {
                        world.setBlock(i, y, j, Blocks.gravel);
                    }
                }
            }
        }

        for (int i = dMaxX; i <= dMaxX + gravelWidthX; i++) {
            for (int j = dMinZ - gravelWidthZ; j <= dMaxZ + gravelWidthZ; j++) {
                if (Main.rand.nextFloat() <= 0.3f) {
                    if (world.getBlock(i, y, j) == this.footing) {
                        world.setBlock(i, y, j, Blocks.gravel);
                    }
                }
            }
        }

        for (int i = dMinX; i <= dMaxX; i++) {
            for (int j = dMinZ - gravelWidthZ; j <= dMinZ; j++) {
                if (Main.rand.nextFloat() <= 0.3f) {
                    if (world.getBlock(i, y, j) == this.footing) {
                        world.setBlock(i, y, j, Blocks.gravel);
                    }
                }
            }
        }

        for (int i = dMinX; i <= dMaxX; i++) {
            for (int j = dMaxZ; j <= dMaxZ + gravelWidthZ; j++) {
                if (Main.rand.nextFloat() <= 0.3f) {
                    if (world.getBlock(i, y, j) == this.footing) {
                        world.setBlock(i, y, j, Blocks.gravel);
                    }
                }
            }
        }

        y -= this.getStructureHolder().getDeltaY();

        for (int i = dMinX; i <= dMaxX; i++) {
            for (int j = dMinZ; j <= dMaxZ; j++) {
                int minY = y - 1;
                while (!world.getBlock(i, minY, j).isOpaqueCube() && minY > 0) {
                    world.setBlock(i, minY, j, this.footing2);
                    minY -= 1;
                }
                for (int k = y + 1; k <= y + this.getStructureHolder().getMaxY(); k++) {
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
        return true;
    }

    @Override
    public Set<BiomeGenBase> allowedBiomes() {
        return null;
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
}