package ru.BouH_.gameplay.client;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import ru.BouH_.network.NetworkHandler;
import ru.BouH_.network.packets.misc.ping.PacketPingTab;
import ru.BouH_.network.packets.misc.ping.PacketPlayerPingS;

import java.util.HashMap;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class PingManager {
    public static PingManager instance = new PingManager();
    private final Map<String, Integer> pingMap = new HashMap<>();
    public int localPing;
    private long lastTimeGotPing;
    private int tickPing;
    private int currentStatePing;
    private long tempPing;
    private int timesReceived;
    private PacketState currentPacketState;

    public PingManager() {
        this.currentPacketState = PacketState.WAITING;
        this.lastTimeGotPing = System.currentTimeMillis();
        this.localPing = -1;
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent ev) {
        if (ev.phase == TickEvent.Phase.START) {
            Minecraft mc = Minecraft.getMinecraft();
            EntityPlayer player = mc.thePlayer;
            if (player != null) {
                if (mc.isGamePaused()) {
                    this.localPing = -1;
                    this.currentPacketState = PacketState.WAITING;
                    this.lastTimeGotPing = System.currentTimeMillis();
                } else {
                    if (this.timesReceived >= 20) {
                        this.updatePing();
                        this.tickPing = 0;
                        this.timesReceived = 0;
                    }
                    if (this.currentPacketState == PacketState.WAITING) {
                        this.currentPacketState = PacketState.SENT;
                        NetworkHandler.NETWORK.sendToServer(new PacketPlayerPingS(this.localPing));
                        this.tempPing = System.currentTimeMillis();
                    }
                    if (System.currentTimeMillis() - this.lastTimeGotPing > 10000) {
                        this.localPing = -1;
                        this.currentPacketState = PacketState.WAITING;
                    }
                }
            }
        }
    }

    private void updatePing() {
        this.localPing = (int) (this.tickPing / 20.0f);
        NetworkHandler.NETWORK.sendToServer(new PacketPingTab(this.localPing));
    }

    public Map<String, Integer> getPingMap() {
        return this.pingMap;
    }

    public int getCurrentStatePing() {
        return this.currentStatePing;
    }

    public void setPing() {
        this.lastTimeGotPing = this.tempPing;
        this.currentPacketState = PacketState.WAITING;
        this.currentStatePing = (int) (System.currentTimeMillis() - this.tempPing);
        this.tickPing += this.getCurrentStatePing();
        this.timesReceived += 1;
    }

    private enum PacketState {
        SENT,
        WAITING
    }
}
