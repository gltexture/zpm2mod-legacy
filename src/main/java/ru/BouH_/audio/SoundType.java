package ru.BouH_.audio;

import ru.BouH_.audio.data.SoundSource;

public enum SoundType {
    AMBIENT(new SoundSource(false, true)),
    BACKGROUND_MUSIC(new SoundSource(false, true)),
    BACKGROUND_EVENT(new SoundSource(false, false)),
    RELATIVE(new SoundSource(true, false)),
    RELATIVE_LOOP(new SoundSource(true, true)),
    EVENT(new SoundSource(false, false));

    private final SoundSource soundSource;

    SoundType(SoundSource soundSource) {
        this.soundSource = soundSource;
    }

    public SoundSource getSoundSource() {
        return this.soundSource;
    }
}
