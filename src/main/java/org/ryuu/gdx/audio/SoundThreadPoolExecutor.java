package org.ryuu.gdx.audio;

import com.badlogic.gdx.audio.Sound;
import lombok.Getter;
import lombok.ToString;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

public class SoundThreadPoolExecutor {
    @Getter
    private final static Logger logger = Logger.getLogger(SoundThreadPoolExecutor.class.getName());
    private final Executor executor;

    public SoundThreadPoolExecutor(Executor executor) {
        this.executor = executor;
    }

    public SoundWrapper play(Sound sound) {
        SoundWrapper soundWrapper = new SoundWrapper();
        executor.execute(() -> {
            long soundId = sound.play();

            soundWrapper.sound = sound;
            soundWrapper.soundId.set(soundId);

            logger.info(
                    "play {" +
                            "soundManager=" + this +
                            ", sound=" + sound +
                            ", soundId=" + soundId +
                            "}"
            );
        });
        return soundWrapper;
    }

    public SoundWrapper play(Sound sound, float volume) {
        SoundWrapper soundWrapper = new SoundWrapper();
        executor.execute(() -> {
            long soundId = sound.play(volume);

            soundWrapper.sound = sound;
            soundWrapper.soundId.set(soundId);

            logger.info(
                    "play {" +
                            "soundManager=" + this +
                            ", sound=" + sound +
                            ", soundId=" + soundId +
                            ", volume=" + volume +
                            "}"
            );
        });
        return soundWrapper;
    }

    public SoundWrapper play(Sound sound, float volume, float pitch, float pan) {
        SoundWrapper soundWrapper = new SoundWrapper();
        executor.execute(() -> {
            long soundId = sound.play(volume, pitch, pan);

            soundWrapper.sound = sound;
            soundWrapper.soundId.set(soundId);

            logger.info(
                    "play {" +
                            "soundManager=" + this +
                            ", sound=" + sound +
                            ", soundId=" + soundId +
                            ", volume=" + volume +
                            ", pitch=" + pitch +
                            ", pan=" + pan +
                            "}"
            );
        });
        return soundWrapper;
    }

    public SoundWrapper loop(Sound sound) {
        SoundWrapper soundWrapper = new SoundWrapper();
        executor.execute(() -> {
            long soundId = sound.loop();

            soundWrapper.sound = sound;
            soundWrapper.soundId.set(soundId);

            logger.info(
                    "loop {" +
                            "soundManager=" + this +
                            ", sound=" + sound +
                            ", soundId=" + soundId +
                            "}"
            );
        });
        return soundWrapper;
    }

    public SoundWrapper loop(Sound sound, float volume) {
        SoundWrapper soundWrapper = new SoundWrapper();
        executor.execute(() -> {
            long soundId = sound.loop(volume);

            soundWrapper.sound = sound;
            soundWrapper.soundId.set(soundId);

            logger.info(
                    "loop {" +
                            "soundManager=" + this +
                            ", sound=" + sound +
                            ", soundId=" + soundId +
                            ", volume=" + volume +
                            "}"
            );
        });
        return soundWrapper;
    }

    public SoundWrapper loop(Sound sound, float volume, float pitch, float pan) {
        SoundWrapper soundWrapper = new SoundWrapper();
        executor.execute(() -> {
            long soundId = sound.loop(volume, pitch, pan);

            soundWrapper.sound = sound;
            soundWrapper.soundId.set(soundId);

            logger.info(
                    "loop {" +
                            "soundManager=" + this +
                            ", sound=" + sound +
                            ", soundId=" + soundId +
                            ", volume=" + volume +
                            ", pitch=" + pitch +
                            ", pan=" + pan +
                            "}"
            );
        });
        return soundWrapper;
    }

    @ToString
    public static class SoundWrapper {
        @Getter
        private volatile AtomicLong soundId = new AtomicLong();
        @Getter
        private volatile Sound sound;
    }
}