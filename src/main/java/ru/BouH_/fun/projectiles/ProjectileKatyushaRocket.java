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
import ru.BouH_.fun.rockets.EntityKatyushaRocket;
import ru.BouH_.fun.rockets.base.EntityRocketOwnable;

public class ProjectileKatyushaRocket extends BehaviorProjectileDispense {

    protected IProjectile getProjectileEntity(World par1World, IPosition par2IPosition) {
        return new EntityKatyushaRocket(par1World, par2IPosition.getX(), par2IPosition.getY(), par2IPosition.getZ());
    }

    public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
        World world = source.getWorld();
        IPosition iposition = BlockDispenser.getIPositionFromBlockSource(source);
        EnumFacing enumfacing = BlockDispenser.getFacingDirection(source.getBlockMetadata());
        int max = Math.min(stack.stackSize, 3);
        for (int i = 0; i < max; i++) {
            IProjectile iprojectile = this.getProjectileEntity(world, iposition);
            world.playSoundAtEntity((Entity) iprojectile, Main.MODID + ":rpg_s", 5.0f, Main.rand.nextFloat() * 0.2f + 1);
            iprojectile.setThrowableHeading(enumfacing.getFrontOffsetX() + (Main.rand.nextFloat() - Main.rand.nextFloat()) * 0.4f, (float) enumfacing.getFrontOffsetY() + 0.15F + (i * 0.05f), enumfacing.getFrontOffsetZ() + (Main.rand.nextFloat() - Main.rand.nextFloat()) * 0.4f, 2.0f, 1.2f);
            world.spawnEntityInWorld((Entity) iprojectile);
            EntityRocketOwnable iOwner = (EntityRocketOwnable) iprojectile;
            if (stack.hasTagCompound()) {
                iOwner.setOwner(stack.getTagCompound().getCompoundTag(Main.MODID).getString("owner"));
            }
        }
        stack.splitStack(max);
        return stack;
    }
}
