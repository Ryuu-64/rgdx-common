package org.ryuu.gdx.audio;

import com.badlogic.gdx.audio.Sound;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.ryuu.functional.IAction;
import org.ryuu.functional.IFunc1Arg;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class SoundManager {
    @Getter
    private final static Logger logger = Logger.getLogger(SoundManager.class.getName());
    private final List<IAction> playSoundList = new ArrayList<>();
    private final List<IAction> executePlaySoundList = new ArrayList<>();
    @Getter
    @Setter
    private long interval;
    @Getter
    @Setter
    private IFunc1Arg<String, Sound> getSoundByPath;

    public SoundManager(long interval, IFunc1Arg<String, Sound> getSoundByPath) {
        this.interval = interval;
        this.getSoundByPath = getSoundByPath;

        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(() -> {
            synchronized (playSoundList) {
                if (!playSoundList.isEmpty()) {
                    executePlaySoundList.addAll(playSoundList);
                    playSoundList.clear();
                }
            }
            while (!executePlaySoundList.isEmpty()) {
                executePlaySoundList.remove(0).invoke();
            }
        }, 0, this.interval, MILLISECONDS);
    }

    public SoundWrapper play(String path) {
        synchronized (playSoundList) {
            SoundWrapper soundWrapper = new SoundWrapper();
            playSoundList.add(() -> {
                Sound sound = getSoundByPath.invoke(path);
                soundWrapper.sound = sound;
                soundWrapper.soundId = sound.play();
                logger.info(
                        "play {" +
                                "soundManager=" + this +
                                ", sound=" + sound +
                                ", soundId=" + soundWrapper.soundId +
                                ", path=" + path +
                                "}"
                );
            });
            return soundWrapper;
        }
    }

    public SoundWrapper play(String path, float volume) {
        synchronized (playSoundList) {
            SoundWrapper soundWrapper = new SoundWrapper();
            playSoundList.add(() -> {
                Sound sound = getSoundByPath.invoke(path);
                soundWrapper.sound = sound;
                soundWrapper.soundId = sound.play(volume);
                logger.info(
                        "play {" +
                                "soundManager=" + this +
                                ", sound=" + sound +
                                ", soundId=" + soundWrapper.soundId +
                                ", path=" + path +
                                ", volume=" + volume +
                                "}"
                );
            });
            return soundWrapper;
        }
    }

    public SoundWrapper play(String path, float volume, float pitch, float pan) {
        synchronized (playSoundList) {
            SoundWrapper soundWrapper = new SoundWrapper();
            playSoundList.add(() -> {
                Sound sound = getSoundByPath.invoke(path);
                soundWrapper.sound = sound;
                soundWrapper.soundId = sound.play(volume, pitch, pan);
                logger.info(
                        "play {" +
                                "soundManager=" + this +
                                ", sound=" + sound +
                                ", soundId=" + soundWrapper.soundId +
                                ", path=" + path +
                                ", volume=" + volume +
                                ", pitch=" + pitch +
                                ", pan=" + pan +
                                "}"
                );
            });
            return soundWrapper;
        }
    }

    public SoundWrapper loop(String path) {
        synchronized (playSoundList) {
            SoundWrapper soundWrapper = new SoundWrapper();
            playSoundList.add(() -> {
                Sound sound = getSoundByPath.invoke(path);
                soundWrapper.sound = sound;
                soundWrapper.soundId = sound.loop();
                logger.info(
                        "loop {" +
                                "soundManager=" + this +
                                ", sound=" + sound +
                                ", soundId=" + soundWrapper.soundId +
                                ", path=" + path +
                                "}"
                );
            });
            return soundWrapper;
        }
    }

    public SoundWrapper loop(String path, float volume) {
        synchronized (playSoundList) {
            SoundWrapper soundWrapper = new SoundWrapper();
            playSoundList.add(() -> {
                Sound sound = getSoundByPath.invoke(path);
                soundWrapper.sound = sound;
                soundWrapper.soundId = sound.loop(volume);
                logger.info(
                        "loop {" +
                                "soundManager=" + this +
                                ", sound=" + sound +
                                ", soundId=" + soundWrapper.soundId +
                                ", path=" + path +
                                ", volume=" + volume +
                                "}"
                );
            });
            return soundWrapper;
        }
    }

    public SoundWrapper loop(String path, float volume, float pitch, float pan) {
        synchronized (playSoundList) {
            SoundWrapper soundWrapper = new SoundWrapper();
            playSoundList.add(() -> {
                Sound sound = getSoundByPath.invoke(path);
                soundWrapper.sound = sound;
                soundWrapper.soundId = sound.loop(volume, pitch, pan);
                logger.info(
                        "loop {" +
                                "soundManager=" + this +
                                ", sound=" + sound +
                                ", soundId=" + soundWrapper.soundId +
                                ", path=" + path +
                                ", volume=" + volume +
                                ", pitch=" + pitch +
                                ", pan=" + pan +
                                "}"
                );
            });
            return soundWrapper;
        }
    }

    @ToString
    public static class SoundWrapper {
        @Getter
        private long soundId = -1;
        @Getter
        private Sound sound;
    }
}