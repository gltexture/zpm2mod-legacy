package ru.BouH_.hook.client;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.client.renderer.tileentity.TileEntityRendererChestHelper;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import ru.BouH_.blocks.lootCases.BlockLootCase;
import ru.BouH_.render.tile.TileFakeChest;
import ru.hook.asm.Hook;
import ru.hook.asm.ReturnCondition;

public class BlockHook {
    private static final TileFakeChest lootCase = new TileFakeChest();

    @Hook(returnCondition = ReturnCondition.ALWAYS, createMethod = true)
    public static AxisAlignedBB getCollisionBoundingBoxFromPool(BlockChest blockChest, World worldIn, int x, int y, int z) {
        return AxisAlignedBB.getBoundingBox(x, y, z, (double) x + 1.0d, (double) y + 0.875F, (double) z + 1.0d);
    }

    @Hook(returnCondition = ReturnCondition.ALWAYS, createMethod = true)
    public static AxisAlignedBB getSelectedBoundingBoxFromPool(BlockChest blockChest, World worldIn, int x, int y, int z) {
        return AxisAlignedBB.getBoundingBox((double) x + blockChest.getBlockBoundsMinX(), (double) y + blockChest.getBlockBoundsMinY(), (double) z + blockChest.getBlockBoundsMinZ(), (double) x + blockChest.getBlockBoundsMaxX(), (double) y + blockChest.getBlockBoundsMaxY(), (double) z + blockChest.getBlockBoundsMaxZ());
    }

    @Hook(returnCondition = ReturnCondition.ON_TRUE)
    public static boolean renderChest(TileEntityRendererChestHelper tileEntityRendererChestHelper, Block p_147715_1_, int p_147715_2_, float p_147715_3_) {
        if (p_147715_1_ instanceof BlockLootCase) {
            BlockLootCase blockLootCase = (BlockLootCase) p_147715_1_;
            lootCase.setType(blockLootCase.chestType);
            TileEntityRendererDispatcher.instance.renderTileEntityAt(lootCase, 0.0D, 0.0D, 0.0D, 0.0F);
            return true;
        }
        return false;
    }
}
