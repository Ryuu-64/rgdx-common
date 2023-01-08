package org.ryuu.gdx.audio;

import com.badlogic.gdx.audio.Sound;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.ryuu.functional.IAction;
import org.ryuu.functional.IFunc1Arg;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class SoundManager {
    @Getter
    private final static Logger logger = Logger.getLogger(SoundManager.class.getName());
    private final Executor executor;
    private final IFunc1Arg<String, Sound> getSoundByPath;

    public SoundManager(Executor executor, IFunc1Arg<String, Sound> getSoundByPath) {
        this.executor = executor;
        this.getSoundByPath = getSoundByPath;
    }

    public SoundWrapper play(String path) {
        SoundWrapper soundWrapper = new SoundWrapper();
        executor.execute(() -> {
            Sound sound = getSoundByPath.invoke(path);
            long soundId = sound.play();

            soundWrapper.sound = sound;
            soundWrapper.soundId = soundId;

            logger.info(
                    "play {" +
                            "soundManager=" + this +
                            ", sound=" + sound +
                            ", soundId=" + soundId +
                            ", path=" + path +
                            "}"
            );
        });
        return soundWrapper;
    }

    public SoundWrapper play(String path, float volume) {
        SoundWrapper soundWrapper = new SoundWrapper();
        executor.execute(() -> {
            Sound sound = getSoundByPath.invoke(path);
            long soundId = sound.play(volume);

            soundWrapper.sound = sound;
            soundWrapper.soundId = soundId;

            logger.info(
                    "play {" +
                            "soundManager=" + this +
                            ", sound=" + sound +
                            ", soundId=" + soundId +
                            ", path=" + path +
                            ", volume=" + volume +
                            "}"
            );
        });
        return soundWrapper;
    }

    public SoundWrapper play(String path, float volume, float pitch, float pan) {
        SoundWrapper soundWrapper = new SoundWrapper();
        executor.execute(() -> {
            Sound sound = getSoundByPath.invoke(path);
            long soundId = sound.play(volume, pitch, pan);

            soundWrapper.sound = sound;
            soundWrapper.soundId = soundId;

            logger.info(
                    "play {" +
                            "soundManager=" + this +
                            ", sound=" + sound +
                            ", soundId=" + soundId +
                            ", path=" + path +
                            ", volume=" + volume +
                            ", pitch=" + pitch +
                            ", pan=" + pan +
                            "}"
            );
        });
        return soundWrapper;
    }

    public SoundWrapper loop(String path) {
        SoundWrapper soundWrapper = new SoundWrapper();
        executor.execute(() -> {
            Sound sound = getSoundByPath.invoke(path);
            long soundId = sound.loop();

            soundWrapper.sound = sound;
            soundWrapper.soundId = soundId;

            logger.info(
                    "loop {" +
                            "soundManager=" + this +
                            ", sound=" + sound +
                            ", soundId=" + soundId +
                            ", path=" + path +
                            "}"
            );
        });
        return soundWrapper;
    }

    public SoundWrapper loop(String path, float volume) {
        SoundWrapper soundWrapper = new SoundWrapper();
        executor.execute(() -> {
            Sound sound = getSoundByPath.invoke(path);
            long soundId = sound.loop(volume);

            soundWrapper.sound = sound;
            soundWrapper.soundId = soundId;

            logger.info(
                    "loop {" +
                            "soundManager=" + this +
                            ", sound=" + sound +
                            ", soundId=" + soundId +
                            ", path=" + path +
                            ", volume=" + volume +
                            "}"
            );
        });
        return soundWrapper;
    }

    public SoundWrapper loop(String path, float volume, float pitch, float pan) {
        SoundWrapper soundWrapper = new SoundWrapper();
        executor.execute(() -> {
            Sound sound = getSoundByPath.invoke(path);
            long soundId = sound.loop(volume, pitch, pan);

            soundWrapper.sound = sound;
            soundWrapper.soundId = soundId;

            logger.info(
                    "loop {" +
                            "soundManager=" + this +
                            ", sound=" + sound +
                            ", soundId=" + soundId +
                            ", path=" + path +
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
        private volatile long soundId = -1;
        @Getter
        private volatile Sound sound = null;
    }
}