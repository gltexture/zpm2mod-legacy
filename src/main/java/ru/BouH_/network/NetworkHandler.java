package ru.BouH_.network;

import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import ru.BouH_.network.packets.data.*;
import ru.BouH_.network.packets.fun.*;
import ru.BouH_.network.packets.gun.*;
import ru.BouH_.network.packets.gun.modifiers.PacketClearModule;
import ru.BouH_.network.packets.gun.modifiers.PacketSetModule;
import ru.BouH_.network.packets.misc.*;
import ru.BouH_.network.packets.misc.ping.PacketPingClient;
import ru.BouH_.network.packets.misc.ping.PacketPingTab;
import ru.BouH_.network.packets.misc.ping.PacketPlayerPingC;
import ru.BouH_.network.packets.misc.ping.PacketPlayerPingS;
import ru.BouH_.network.packets.misc.sound.PacketSound;
import ru.BouH_.network.packets.moving.PacketLying;
import ru.BouH_.network.packets.moving.PacketLyingC;
import ru.BouH_.network.packets.nbt.*;
import ru.BouH_.network.packets.particles.*;


public final class NetworkHandler {
    public static final SimpleNetworkWrapper NETWORK = NetworkRegistry.INSTANCE.newSimpleChannel("zombieplague2");
    private static short id;

    public static void registerPackets() throws IllegalAccessException {
        NetworkHandler.register(PacketReloading.class, Side.SERVER);
        NetworkHandler.register(PacketPingTab.class, Side.SERVER);
        NetworkHandler.register(PacketPlayerPingS.class, Side.SERVER);
        NetworkHandler.register(PacketShooting.class, Side.SERVER);
        NetworkHandler.register(PacketClearModule.class, Side.SERVER);
        NetworkHandler.register(PacketSetModule.class, Side.SERVER);
        NetworkHandler.register(PacketSwitchFireMode.class, Side.SERVER);
        NetworkHandler.register(PacketClientSettings.class, Side.SERVER);
        NetworkHandler.register(PacketPickUp.class, Side.SERVER);
        NetworkHandler.register(PacketLying.class, Side.SERVER);
        NetworkHandler.register(PacketShootingLauncher.class, Side.SERVER);
        NetworkHandler.register(PacketReloadInterrupt.class, Side.SERVER);
    }

    public static void registerPacketsClient() throws IllegalAccessException {
        NetworkHandler.register(PacketDay.class, Side.CLIENT);
        NetworkHandler.register(PacketSkillProgressData.class, Side.CLIENT);
        NetworkHandler.register(PacketAddSkillPoints.class, Side.CLIENT);
        NetworkHandler.register(PacketTriggerAchievement.class, Side.CLIENT);
        NetworkHandler.register(PacketBrightnessInfo.class, Side.CLIENT);
        NetworkHandler.register(PacketLyingC.class, Side.CLIENT);
        NetworkHandler.register(PacketTacticBlockOwner.class, Side.CLIENT);
        NetworkHandler.register(PacketRocketOwner.class, Side.CLIENT);
        NetworkHandler.register(PacketGuidedTarget.class, Side.CLIENT);
        NetworkHandler.register(PacketFogCheck.class, Side.CLIENT);
        NetworkHandler.register(PacketRainCheck.class, Side.CLIENT);
        NetworkHandler.register(PacketAcid.class, Side.CLIENT);
        NetworkHandler.register(ParticleBookExplode.class, Side.CLIENT);
        NetworkHandler.register(PacketHoly.class, Side.CLIENT);
        NetworkHandler.register(PacketNausea.class, Side.CLIENT);
        NetworkHandler.register(PacketPingClient.class, Side.CLIENT);
        NetworkHandler.register(PacketPlayerPingC.class, Side.CLIENT);
        NetworkHandler.register(PacketShootingC.class, Side.CLIENT);
        NetworkHandler.register(PacketReloadingC.class, Side.CLIENT);
        NetworkHandler.register(PacketReloadFinishC.class, Side.CLIENT);
        NetworkHandler.register(PacketHitscanBlockC.class, Side.CLIENT);
        NetworkHandler.register(PacketHitscanEntC.class, Side.CLIENT);
        NetworkHandler.register(ParticleZombieBlockCrack.class, Side.CLIENT);
        NetworkHandler.register(PacketThirst.class, Side.CLIENT);
        NetworkHandler.register(PacketHunger.class, Side.CLIENT);
        NetworkHandler.register(PacketMiscInfo.class, Side.CLIENT);
        NetworkHandler.register(PacketResetGun.class, Side.CLIENT);
        NetworkHandler.register(PacketMiscPlayerNbtInfo.class, Side.CLIENT);
        NetworkHandler.register(PacketAchSkillData.class, Side.CLIENT);
        NetworkHandler.register(PacketSound.class, Side.CLIENT);
        NetworkHandler.register(ParticleItemCrack.class, Side.CLIENT);
        NetworkHandler.register(ParticleBlood2.class, Side.CLIENT);
        NetworkHandler.register(ParticleSpark.class, Side.CLIENT);
        NetworkHandler.register(ParticleCloud.class, Side.CLIENT);
        NetworkHandler.register(ParticleCowReloading.class, Side.CLIENT);
        NetworkHandler.register(ParticleExplosion.class, Side.CLIENT);
        NetworkHandler.register(ParticleExplosionThermobaric.class, Side.CLIENT);
        NetworkHandler.register(PacketPain.class, Side.CLIENT);
        NetworkHandler.register(PacketShootingLauncherC.class, Side.CLIENT);
        NetworkHandler.register(PacketJavelinInfoC.class, Side.CLIENT);
        NetworkHandler.register(PacketIglaInfoC.class, Side.CLIENT);
    }

    private static void register(Class<? extends SimplePacket> packet, Side side) throws IllegalAccessException {
        try {
            NETWORK.registerMessage(packet.newInstance(), packet, id++, side);
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}