package ru.BouH_.blocks.fluids;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLilyPad;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import ru.BouH_.Main;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.packets.particles.ParticleCloud;

public class BlockAcid extends BlockFluidClassic {
    @SideOnly(Side.CLIENT)
    protected IIcon stillIcon;
    @SideOnly(Side.CLIENT)
    protected IIcon flowingIcon;

    public BlockAcid(Fluid fluid, Material mat) {
        super(fluid, mat);
        this.setQuantaPerBlock(5);
    }

    public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_) {
        super.onNeighborBlockChange(p_149695_1_, p_149695_2_, p_149695_3_, p_149695_4_, p_149695_5_);
        if (p_149695_1_.getBlock(p_149695_2_, p_149695_3_ + 1, p_149695_4_) instanceof BlockLilyPad) {
            p_149695_1_.playSoundEffect(p_149695_2_ + 0.5f, p_149695_3_ + 1, p_149695_4_ + 0.5f, "random.fizz", 1.0F, 2.6F + (Main.rand.nextFloat() - Main.rand.nextFloat()) * 0.8F);
            NetworkHandler.NETWORK.sendToAllAround(new ParticleCloud(p_149695_2_ + 0.5f, p_149695_3_ + 1, p_149695_4_ + 0.5f, 0, 0.1f, 0, 0.85f, 1.0f, 0.85f, 1, 12), new NetworkRegistry.TargetPoint(p_149695_1_.getWorldInfo().getDimension(), p_149695_2_, p_149695_3_, p_149695_4_, 32));
            p_149695_1_.breakBlock(p_149695_2_, p_149695_3_ + 1, p_149695_4_, false);
        }
    }

    public void onEntityCollidedWithBlock(World worldIn, int x, int y, int z, Entity entityIn) {
        if (entityIn instanceof EntityFishHook || entityIn instanceof EntityBoat || entityIn instanceof EntityMinecart) {
            entityIn.setDead();
        }
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return (side == 0 || side == 1) ? stillIcon : flowingIcon;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister register) {
        stillIcon = register.registerIcon(Main.MODID + ":fluid/acid");
        flowingIcon = register.registerIcon(Main.MODID + ":fluid/acid_flow");
    }

    @Override
    public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
        if (world.getBlock(x, y, z).getMaterial().isLiquid()) {
            return false;
        }
        return super.canDisplace(world, x, y, z);
    }

    @Override
    public boolean displaceIfPossible(World world, int x, int y, int z) {
        if (world.getBlock(x, y, z).getMaterial().isLiquid()) {
            return false;
        }
        return super.displaceIfPossible(world, x, y, z);
    }
}