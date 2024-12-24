package ru.BouH_.world.structures.building;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import ru.BouH_.world.structures.base.AStructure;
import ru.BouH_.world.structures.base.StructureHolder;

public class EventStructure extends AStructure {

    public EventStructure(StructureHolder structureHolder, int chance) {
        super(structureHolder, chance);
    }

    @Override
    public boolean runGenerator(World world, int x, int y, int z, int rotation) {
        int dMaxX = this.getStructureHolder().getMaxX();
        int dMaxZ = this.getStructureHolder().getMaxZ();
        y -= this.getStructureHolder().getDeltaY();

        for (int i = x; i <= x + dMaxX; i++) {
            for (int j = z; j <= z + dMaxZ; j++) {
                int minY = y - 1;
                for (int k = y + 1; k <= y + this.getStructureHolder().getMaxY(); k++) {
                    world.setBlockToAir(i, k, j);
                }
            }
        }

        for (StructureHolder.BlockState blockState : this.getStructureHolder().getBlockStates()) {
            this.translateToWorld(world, x, y, z, blockState);
        }

        for (StructureHolder.BlockState blockState : this.getStructureHolder().getDecorativeBlockStates()) {
            this.translateToWorld(world, x, y, z, blockState);
        }

        return true;
    }

    private void translateToWorld(World world, int x, int y, int z, StructureHolder.BlockState blockState) {
        Block block = Block.getBlockFromName(blockState.getId());
        int posX = x + blockState.getX();
        int posZ = z + blockState.getZ();
        int posY = blockState.getY() + y;
        int meta = blockState.getMeta();

        world.setBlock(posX, posY, posZ, block, meta, 2);
        world.setBlockMetadataWithNotify(posX, posY, posZ, meta, 2);
    }
}
