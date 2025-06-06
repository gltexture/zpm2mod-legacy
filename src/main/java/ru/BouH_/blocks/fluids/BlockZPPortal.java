package ru.BouH_.blocks.fluids;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import ru.BouH_.Main;
import ru.BouH_.misc.EmptyTeleport;
import ru.BouH_.misc.ZpTeleport;

import java.util.Random;

public class BlockZPPortal extends BlockFluidClassic {
    @SideOnly(Side.CLIENT)
    protected IIcon stillIcon;
    @SideOnly(Side.CLIENT)
    protected IIcon flowingIcon;

    public BlockZPPortal(Fluid fluid, Material mat) {
        super(fluid, mat);
        this.setQuantaPerBlock(0);
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return (side == 0 || side == 1) ? stillIcon : flowingIcon;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IIconRegister register) {
        stillIcon = register.registerIcon(Main.MODID + ":fluid/portal");
        flowingIcon = register.registerIcon(Main.MODID + ":fluid/portal");
    }

    public void onEntityCollidedWithBlock(World worldIn, int x, int y, int z, Entity entityIn) {
    }

    public void updateTick(World world, int x, int y, int z, Random rand) {

    }

    public boolean isReplaceable(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    @Override
    public boolean canDisplace(IBlockAccess world, int x, int y, int z) {
        return true;
    }

    @Override
    public boolean displaceIfPossible(World world, int x, int y, int z) {
        return true;
    }
}