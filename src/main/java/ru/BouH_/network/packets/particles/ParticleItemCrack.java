package ru.BouH_.network.packets.particles;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import ru.BouH_.network.SimplePacket;

public class ParticleItemCrack extends SimplePacket {
    public ParticleItemCrack() {
    }

    public ParticleItemCrack(int id, int itemId) {
        buf().writeInt(id);
        buf().writeInt(itemId);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void client(EntityPlayer player) {
        int id = buf().readInt(), itemId = buf().readInt();
        if (player.worldObj.getEntityByID(id) != null) {
            ((EntityPlayer) player.worldObj.getEntityByID(id)).renderBrokenItemStack(new ItemStack(Item.getItemById(itemId)));
        }
    }
}
