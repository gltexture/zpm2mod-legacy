package ru.BouH_.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.entity.particle.EntityParticleColoredCloud;
import ru.BouH_.init.ItemsZp;

import java.util.Random;

public class BlockTempStone extends Block {
    public BlockTempStone() {
        super(Material.iron);
        this.setTickRandomly(true);
    }

    public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_) {
        return null;
    }

    public void updateTick(World worldIn, int x, int y, int z, Random random) {
        if (random.nextBoolean()) {
            worldIn.breakBlock(x, y, z, false);
        }
    }
}
