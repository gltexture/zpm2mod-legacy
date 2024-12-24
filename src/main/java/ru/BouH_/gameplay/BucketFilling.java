package ru.BouH_.gameplay;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import ru.BouH_.Main;
import ru.BouH_.init.FluidsZp;
import ru.BouH_.init.ItemsZp;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.packets.particles.ParticleItemCrack;
import ru.BouH_.utils.PluginUtils;

public class BucketFilling {
    public static BucketFilling instance = new BucketFilling();

    @SubscribeEvent
    public void onBucketFill(FillBucketEvent ev) {
        int i = ev.target.blockX;
        int j = ev.target.blockY;
        int k = ev.target.blockZ;
        switch (ev.target.sideHit) {
            case 0: {
                --j;
                break;
            }
            case 1: {
                ++j;
                break;
            }
            case 2: {
                --k;
                break;
            }
            case 3: {
                ++k;
                break;
            }
            case 4: {
                --i;
                break;
            }
            case 5: {
                ++i;
                break;
            }
        }
        if (ev.world.getBlock(i, j, k).getMaterial() == Material.portal) {
            ev.setCanceled(true);
        }
        if (!ev.world.isRemote) {
            if (PluginUtils.canBreak(ev.entityPlayer, i, j, k)) {
                if (ev.current.getItem() == Items.lava_bucket) {
                    ev.setCanceled(true);
                    if (!ev.entityPlayer.capabilities.isCreativeMode) {
                        ev.entityPlayer.setCurrentItemOrArmor(0, new ItemStack(Items.bucket));
                    }
                    ev.world.setBlock(i, j, k, FluidsZp.lava_new);
                }
                if ((ev.world.getBlock(ev.target.blockX, ev.target.blockY, ev.target.blockZ) == FluidsZp.acidblock && Main.rand.nextFloat() >= 0.03f) || (ev.world.getBlock(ev.target.blockX, ev.target.blockY, ev.target.blockZ).getMaterial() == Material.lava && Main.rand.nextFloat() >= 0.1f)) {
                    ev.setCanceled(true);
                    ev.world.markBlockForUpdate(ev.target.blockX, ev.target.blockY, ev.target.blockZ);
                    NetworkHandler.NETWORK.sendToAllAround(new ParticleItemCrack(ev.entityLiving.getEntityId(), Item.getIdFromItem(Items.bucket)), new NetworkRegistry.TargetPoint(ev.entityLiving.dimension, ev.entityLiving.posX, ev.entityLiving.posY, ev.entityLiving.posZ, 256));
                    ev.world.playSoundAtEntity(ev.entityPlayer, "random.fizz", 0.6F, 0.8F);
                    ev.current.stackSize--;
                } else {
                    ItemStack result = fillCustomBucket(ev.world, ev.target);
                    if (result == null) {
                        return;
                    }
                    ev.result = result;
                    ev.setResult(Event.Result.ALLOW);
                }
            }
        }
    }

    private ItemStack fillCustomBucket(World world, MovingObjectPosition pos) {
        Block block = world.getBlock(pos.blockX, pos.blockY, pos.blockZ);
        Item bucket = block == FluidsZp.acidblock ? ItemsZp.acid_bucket : block == FluidsZp.toxicwater_block ? ItemsZp.toxicwater_bucket : null;
        if (bucket != null && world.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ) == 0) {
            world.setBlockToAir(pos.blockX, pos.blockY, pos.blockZ);
            return new ItemStack(bucket);
        } else {
            return null;
        }
    }
}