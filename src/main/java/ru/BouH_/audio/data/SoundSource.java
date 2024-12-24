package ru.BouH_.audio.data;

public class SoundSource {
    private final boolean isRelative;
    private final boolean isLoop;

    public SoundSource(boolean isRelative, boolean isLoop) {
        this.isRelative = isRelative;
        this.isLoop = isLoop;
    }

    public boolean isRelative() {
        return this.isRelative;
    }

    public boolean isLoop() {
        return this.isLoop;
    }
}
