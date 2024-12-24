package ru.BouH_.blocks.gas;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import ru.BouH_.entity.particle.EntityParticleColoredCloud;

import java.util.Random;

public class BlockGas extends BlockGasBase {
    public BlockGas() {
        super();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        if (rand.nextFloat() < 0.02f) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleColoredCloud(Minecraft.getMinecraft().thePlayer.worldObj, x + rand.nextFloat(), y + rand.nextFloat(), z + rand.nextFloat(), 0, 0.1, 0, new float[]{0.3f, 0.8f, 0.3f}, 1.2f - rand.nextFloat() * 0.4f));
        }
    }
}
