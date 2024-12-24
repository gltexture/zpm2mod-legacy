package ru.BouH_.hook.server;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemFishFood;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.world.World;
import ru.BouH_.Main;
import ru.BouH_.init.BlocksZp;
import ru.hook.asm.Hook;
import ru.hook.asm.ReturnCondition;

public class ItemHook {
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static String getPotionEffect(ItemFishFood food, ItemStack p_150896_1_) {
        return ItemFishFood.FishType.getFishTypeForItemStack(p_150896_1_) == ItemFishFood.FishType.PUFFERFISH ? PotionHelper.spiderEyeEffect : null;
    }
    @Hook(returnCondition = ReturnCondition.ALWAYS)
    public static ItemStack onItemUseFinish(ItemBucketMilk ev, ItemStack p_77654_1_, World p_77654_2_, EntityPlayer p_77654_3_) {
        if (!p_77654_3_.capabilities.isCreativeMode) {
            --p_77654_1_.stackSize;
        }

        if (!p_77654_2_.isRemote) {
            p_77654_3_.addPotionEffect(new PotionEffect(30, 3600));
        }

        return p_77654_1_.stackSize <= 0 ? new ItemStack(Items.bucket) : p_77654_1_;
    }

    @Hook(returnCondition = ReturnCondition.ON_TRUE)
    public static boolean onItemUse(ItemFlintAndSteel itemFlintAndSteel, ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int p_77648_7_, float p_77648_8_, float p_77648_9_, float p_77648_10_) {
        Block block = world.getBlock(x, y, z);
        if (block == BlocksZp.torch2 || block == BlocksZp.torch3 || block == BlocksZp.torch4 || block == BlocksZp.torch5) {
            int meta = world.getBlockMetadata(x, y, z);
            world.setBlock(x, y, z, BlocksZp.torch1, meta, 2);
            world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "fire.ignite", 1.0F, Main.rand.nextFloat() * 0.4F + 0.8F);
            stack.damageItem(2, player);
            return true;
        }
        return false;
    }
}
