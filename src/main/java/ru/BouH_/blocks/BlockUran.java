package ru.BouH_.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.entity.particle.EntityParticleColoredCloud;

import java.util.Random;

public class BlockUran extends Block {
    public BlockUran() {
        super(Material.iron);
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World p_149734_1_, int p_149734_2_, int p_149734_3_, int p_149734_4_, Random p_149734_5_) {
        super.randomDisplayTick(p_149734_1_, p_149734_2_, p_149734_3_, p_149734_4_, p_149734_5_);
        p_149734_1_.spawnParticle("townaura", ((float) p_149734_2_ + p_149734_5_.nextFloat()), ((float) p_149734_3_ + 1.1F), ((float) p_149734_4_ + p_149734_5_.nextFloat()), 0.0D, 0.0D, 0.0D);
        if (Main.rand.nextFloat() <= 0.01f) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleColoredCloud(Minecraft.getMinecraft().thePlayer.worldObj, p_149734_2_ + Main.rand.nextFloat(), p_149734_3_, p_149734_4_ + Main.rand.nextFloat(), 0, 0, 0, new float[]{0.3f, 0.9f, 0.3f}, 0.1f));
        }
    }
}
