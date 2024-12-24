package ru.BouH_.fun.projectiles;

import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IProjectile;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.fun.rockets.EntityX101Rocket;
import ru.BouH_.fun.rockets.base.EntityRocketOwnable;

public class ProjectileX101Rocket extends BehaviorProjectileDispense {

    protected IProjectile getProjectileEntity(World par1World, IPosition par2IPosition) {
        return new EntityX101Rocket(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ());
    }

    public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
        World world = source.getWorld();
        IPosition iposition = BlockDispenser.getIPositionFromBlockSource(source);
        EnumFacing enumfacing = BlockDispenser.getFacingDirection(source.getBlockMetadata());
        IProjectile iprojectile = this.getProjectileEntity(world, iposition);
        world.playSoundAtEntity((Entity) iprojectile, Main.MODID + ":rpg_s", 5.0f, Main.rand.nextFloat() * 0.2f + 1);
        iprojectile.setThrowableHeading(enumfacing.getFrontOffsetX(), (float) enumfacing.getFrontOffsetY() + 1.0F, enumfacing.getFrontOffsetZ(), 3.8f, 3.0f);
        world.spawnEntityInWorld((Entity) iprojectile);
        EntityRocketOwnable iOwner = (EntityRocketOwnable) iprojectile;
        if (stack.hasTagCompound()) {
            iOwner.setOwner(stack.getTagCompound().getCompoundTag(Main.MODID).getString("owner"));
        }
        stack.splitStack(1);
        return stack;
    }
}
