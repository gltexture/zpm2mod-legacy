package ru.BouH_.network.packets.gun.modifiers;

import cpw.mods.fml.common.network.ByteBufUtils;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import ru.BouH_.items.gun.base.AGunBase;
import ru.BouH_.items.gun.modules.base.EnumModule;
import ru.BouH_.network.SimplePacket;

public class PacketClearModule extends SimplePacket {
    public PacketClearModule() {

    }

    public PacketClearModule(EnumModule enumModifier) {
        ByteBufUtils.writeUTF8String(buf(), enumModifier.toString());
    }

    @Override
    public void server(EntityPlayerMP player) {
        String enumModifier = ByteBufUtils.readUTF8String(buf());
        ItemStack stack = player.getHeldItem();
        if (stack != null && stack.hasTagCompound() && stack.getItem() instanceof AGunBase) {
            AGunBase aGunBase = ((AGunBase) stack.getItem());
            aGunBase.clearModifier(player, stack, EnumModule.getEnumMod(enumModifier));
        }
    }
}
