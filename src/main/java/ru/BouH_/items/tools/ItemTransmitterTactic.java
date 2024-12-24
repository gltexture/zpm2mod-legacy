package ru.BouH_.items.tools;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import ru.BouH_.fun.tiles.TileKalibrBlock;
import ru.BouH_.utils.TraceUtils;

import java.util.List;

public class ItemTransmitterTactic extends Item {
    private double distanceTo;

    public ItemTransmitterTactic(String unlocalizedName) {
        this.setUnlocalizedName(unlocalizedName);
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        return stack;
    }

    public int getMaxItemUseDuration(ItemStack stack) {
        return 100;
    }

    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.block;
    }

    public ItemStack onItemUseFinish(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            MovingObjectPosition movingObjectPosition = this.chooseMOV(player);
            if (movingObjectPosition != null) {
                TileKalibrBlock tileKalibrBlock = this.getClosestBlock(player, world);
                if (tileKalibrBlock != null) {
                    tileKalibrBlock.startRocket((EntityPlayerMP) player, movingObjectPosition.hitVec.xCoord, movingObjectPosition.hitVec.yCoord, movingObjectPosition.hitVec.zCoord);
                    return stack;
                }
                ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("fun.tactic.error.connect");
                chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
                player.addChatComponentMessage(chatComponentTranslation);
                return stack;
            }
            ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("fun.tactic.error");
            chatComponentTranslation.getChatStyle().setColor(EnumChatFormatting.RED);
            player.addChatComponentMessage(chatComponentTranslation);
            return stack;
        }
        return stack;
    }

    @SuppressWarnings("unchecked")
    public TileKalibrBlock getClosestBlock(EntityPlayer player, World world) {
        List<TileEntity> list = world.loadedTileEntityList;
        if (!list.isEmpty()) {
            for (TileEntity tileEntity : list) {
                if (tileEntity instanceof TileKalibrBlock) {
                    TileKalibrBlock tileKalibrBlock = (TileKalibrBlock) tileEntity;
                    double d1 = player.getDistance(tileKalibrBlock.xCoord, tileKalibrBlock.yCoord, tileKalibrBlock.zCoord);
                    if (d1 <= 256) {
                        String owner = player.getDisplayName();
                        if (tileKalibrBlock.owner.equals(owner)) {
                            this.distanceTo = d1;
                            return tileKalibrBlock;
                        }
                    }
                }
            }
        }
        return null;
    }

    public double getDistanceTo() {
        return this.distanceTo;
    }

    public MovingObjectPosition chooseMOV(EntityPlayer player) {
        MovingObjectPosition movingObjectPosition = TraceUtils.rayTrace(player, 256);
        if (movingObjectPosition == null || movingObjectPosition.typeOfHit != MovingObjectPosition.MovingObjectType.BLOCK) {
            return null;
        }
        if (!player.worldObj.getChunkFromBlockCoords((int) movingObjectPosition.hitVec.xCoord, (int) movingObjectPosition.hitVec.zCoord).isChunkLoaded) {
            return null;
        }
        return movingObjectPosition;
    }
}
