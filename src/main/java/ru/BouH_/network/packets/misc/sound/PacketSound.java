package ru.BouH_.network.packets.misc.sound;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import ru.BouH_.Main;
import ru.BouH_.audio.AmbientSounds;
import ru.BouH_.network.SimplePacket;
import ru.BouH_.utils.SoundUtils;

public class PacketSound extends SimplePacket {
    public PacketSound() {

    }

    public PacketSound(int id) {
        buf().writeInt(id);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void client(EntityPlayer player) {
        int id = buf().readInt();
        switch (id) {
            case 0: {
                AmbientSounds.instance.helicrash.playSound();
                break;
            }
            case 1: {
                switch (Main.rand.nextInt(4)) {
                    case 0: {
                        AmbientSounds.instance.the_horror1.playSound();
                        break;
                    }
                    case 1: {
                        AmbientSounds.instance.the_horror2.playSound();
                        break;
                    }
                    case 2: {
                        AmbientSounds.instance.the_horror3.playSound();
                        break;
                    }
                    case 3: {
                        AmbientSounds.instance.the_horror4.playSound();
                        break;
                    }
                }
                break;
            }
            case 2: {
                AmbientSounds.instance.wizz[Main.rand.nextInt(4)].playSound();
                break;
            }
            case 3: {
                SoundUtils.playMonoSound(Main.MODID + ":airdrop");
                break;
            }
            case 4: {
                AmbientSounds.instance.suspence.playSound();
                break;
            }
            case 5: {
                AmbientSounds.instance.seren.playSound();
                break;
            }
        }
    }
}