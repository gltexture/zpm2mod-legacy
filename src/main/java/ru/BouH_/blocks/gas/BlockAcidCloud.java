package ru.BouH_.blocks.gas;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.entity.particle.EntityParticleColoredCloud;
import ru.BouH_.utils.SoundUtils;

import java.util.Random;

public class BlockAcidCloud extends BlockGasBase {
    public BlockAcidCloud() {
        super();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        if (rand.nextFloat() < 0.02f) {
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityParticleColoredCloud(Minecraft.getMinecraft().thePlayer.worldObj, x + rand.nextFloat(), y + rand.nextFloat(), z + rand.nextFloat(), 0, 0, 0, new float[]{0.85f, 1.0f, 0.85f}, 1.2f - Main.rand.nextFloat() * 0.4f));
        }
        if (rand.nextFloat() < 0.001f) {
            SoundUtils.playClientSound(x, y, z, "random.fizz", 1.0f, rand.nextFloat() * 0.1F + 1.4F);
        }
    }
}
