package ru.BouH_.blocks;

import net.minecraft.block.BlockTorch;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import ru.BouH_.tiles.TileTorch;

import java.util.Random;

public class BlockTorchBase extends BlockTorch implements ITileEntityProvider {

    public BlockTorchBase() {
        super();
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return null;
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileTorch();
    }
}
