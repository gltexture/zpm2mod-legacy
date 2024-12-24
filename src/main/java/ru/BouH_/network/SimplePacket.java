package ru.BouH_.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;


public class SimplePacket implements IMessage, IMessageHandler<SimplePacket, SimplePacket> {
    private ByteBuf buf;

    @Override
    public SimplePacket onMessage(SimplePacket sp, MessageContext ctx) {
        if (ctx.side.isServer())
            sp.server(ctx.getServerHandler().playerEntity);
        else {
            sp.client(clientPlayer());
        }
        return null;
    }

    protected ByteBuf buf() {
        return buf != null ? buf : (buf = Unpooled.buffer());
    }

    public void client(EntityPlayer player) {
    }

    public void server(EntityPlayerMP player) {
    }

    @Override
    public final void fromBytes(ByteBuf buf) {
        this.buf = buf;
    }

    @Override
    public final void toBytes(ByteBuf buf) {
        if (this.buf != null)
            buf.writeBytes(this.buf);
    }

    @SideOnly(Side.CLIENT)
    private EntityPlayer clientPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }
}
