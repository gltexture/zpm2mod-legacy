package ru.BouH_.audio;

import cpw.mods.fml.common.FMLLog;
import net.minecraft.util.MathHelper;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;
import org.lwjgl.util.WaveData;
import ru.BouH_.Main;
import ru.BouH_.audio.data.SoundSource;
import ru.BouH_.audio.data.SoundSourceData;

import java.net.URL;
import java.nio.IntBuffer;
import java.util.HashSet;
import java.util.Set;

public class ALSoundZp {
    public static final Set<ALSoundZp> soundSetZp = new HashSet<>();
    private final IntBuffer buffer = BufferUtils.createIntBuffer(1);
    private final IntBuffer source = BufferUtils.createIntBuffer(1);
    private final SoundSource soundSource;
    private SoundType soundType;
    private SoundSourceData soundSourceData;
    private boolean smoothPause;
    private boolean smoothStart;

    public ALSoundZp(String soundName, SoundType soundType) {
        this(soundName, soundType.getSoundSource(), 1.0f, 1.0f);
        this.soundType = soundType;
    }

    public ALSoundZp(String soundName, SoundType soundType, float pitch, float gain) {
        this(soundName, soundType.getSoundSource(), pitch, gain);
        this.soundType = soundType;
    }

    protected ALSoundZp(String soundName, SoundSource soundSource, float pitch, float gain) {
        if (!AL.isCreated()) {
            FMLLog.severe("AL FATAL ERROR!");
        }
        AL10.alGenBuffers(this.buffer);
        this.soundSourceData = new SoundSourceData();
        if (AL10.alGetError() != AL10.AL_NO_ERROR) {
            FMLLog.bigWarning("AL-ZP ERROR!");
        }
        URL url = this.getClass().getResource("/assets/" + Main.MODID + "/sounds/wav/" + soundName + ".wav");
        if (url == null) {
            FMLLog.warning(soundName + " NO");
        }
        this.readWave(url, soundSource.isRelative());
        AL10.alGenSources(this.source);
        AL10.alSourcei(this.source.get(0), AL10.AL_LOOPING, AL10.AL_FALSE);
        if (AL10.alGetError() != AL10.AL_NO_ERROR) {
            FMLLog.bigWarning("AL-ZP ERROR!");
        }
        this.soundSource = soundSource;
        this.buildSources();
        this.setPitch(pitch);
        this.setGain(gain);
        ALSoundZp.soundSetZp.add(this);
    }

    private void buildSources() {
        AL10.alSourcei(this.source.get(0), AL10.AL_SOURCE_RELATIVE, this.soundSource.isRelative() ? AL10.AL_FALSE : AL10.AL_TRUE);
        AL10.alSourcei(this.source.get(0), AL10.AL_LOOPING, this.soundSource.isLoop() ? AL10.AL_TRUE : AL10.AL_FALSE);
        AL10.alSourcei(this.source.get(0), AL10.AL_BUFFER, this.buffer.get(0));
        AL10.alDistanceModel(AL11.AL_EXPONENT_DISTANCE);
        AL10.alSource3f(this.source.get(0), AL10.AL_POSITION, 0.0f, 0.0f, 0.0f);
        AL10.alSource3f(this.source.get(0), AL10.AL_VELOCITY, 0.0f, 0.0f, 0.0f);
        if (AL10.alGetError() != AL10.AL_NO_ERROR) {
            FMLLog.bigWarning("AL-ZP ERROR!");
        }
    }

    private void readWave(URL url, boolean relative) {
        WaveData waveData = WaveData.create(url);
        AL10.alBufferData(this.buffer.get(0), relative ? AL10.AL_FORMAT_MONO16 : waveData.format, waveData.data, waveData.samplerate);
        waveData.dispose();
    }

    public void updateSource() {
        if (this.smoothPause) {
            if (this.getGain() > 0) {
                this.setGain(this.getGain() - 0.01f);
            } else {
                this.stopSound();
                this.smoothPause = false;
            }
        } else if (this.smoothStart) {
            if (this.getGain() < 1) {
                this.setGain(this.getGain() + 0.01f);
            } else {
                this.smoothStart = false;
            }
        }
        AL10.alSourcef(this.source.get(0), AL10.AL_REFERENCE_DISTANCE, 0.8f);
        AL10.alSource3f(this.source.get(0), AL10.AL_POSITION, this.getSoundSourceData().getX(), this.getSoundSourceData().getY(), this.getSoundSourceData().getZ());
        AL10.alSource3f(this.source.get(0), AL10.AL_VELOCITY, this.getSoundSourceData().getVx(), this.getSoundSourceData().getVy(), this.getSoundSourceData().getVz());
    }

    public float getGain() {
        return AL10.alGetSourcef(this.source.get(0), AL10.AL_GAIN);
    }

    public void setGain(float gain) {
        AL10.alSourcef(this.source.get(0), AL10.AL_GAIN, MathHelper.clamp_float(gain, 0.0f, 1.0f));
    }

    public float getPitch() {
        return AL10.alGetSourcef(this.source.get(0), AL10.AL_PITCH);
    }

    public void setPitch(float pitch) {
        AL10.alSourcef(this.source.get(0), AL10.AL_PITCH, pitch < 0 ? 0.0f : this.getSoundSource().isRelative() ? pitch * 2.0f : pitch);
    }

    public SoundType getSoundType() {
        return this.soundType;
    }

    public SoundSource getSoundSource() {
        return this.soundSource;
    }

    public void setSoundInfo(SoundSourceData soundSourceData) {
        this.soundSourceData = soundSourceData;
    }

    public boolean isPlaying() {
        return AL.isCreated() && AL10.alGetSourcei(this.source.get(0), AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
    }

    public SoundSourceData getSoundSourceData() {
        return this.soundSourceData;
    }

    public void clear() {
        AL10.alDeleteBuffers(this.buffer);
        AL10.alDeleteSources(this.source);
    }

    public void playSound() {
        this.setGain(1.0f);
        this.smoothPause = false;
        AL10.alSourcePlay(this.source.get(0));
    }

    public void pauseSound() {
        this.smoothStart = false;
        AL10.alSourcePause(this.source.get(0));
    }

    public void smoothStopSound() {
        this.smoothPause = true;
        this.smoothStart = false;
    }

    public void smoothStartSound() {
        if (!this.isPlaying()) {
            this.setGain(0.0f);
            this.smoothStart = true;
            this.smoothPause = false;
            AL10.alSourcePlay(this.source.get(0));
        }
    }

    public void stopSound() {
        AL10.alSourceStop(this.source.get(0));
    }
}