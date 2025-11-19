package com.comp2042.sfx;

import com.comp2042.util.SfxEvent;

import javafx.scene.media.AudioClip;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public final class SoundLoader {
    private static AudioClip bgMusicClip;
    private static final Map<SfxEvent, AudioClip> soundCache = new HashMap<>();

    private SoundLoader() {}

    public static void loadAll() {
        // Cache background music
        String bgPath = "/audio/bg_music.mp3";
        URL bgMusic = SoundLoader.class.getResource(bgPath);

        if (bgMusic == null) System.err.println("Missing background music: " + bgPath);
        else {
            bgMusicClip = new AudioClip(bgMusic.toExternalForm());
            bgMusicClip.setCycleCount(AudioClip.INDEFINITE); // Loop forever
        }

        // Cache sfx sounds
        for (SfxEvent event : SfxEvent.values()) {
            String path = "/audio/" + event.name().toLowerCase() + ".wav";
            URL url = SoundLoader.class.getResource(path);

            if (url == null) {
                System.err.println("Missing sound: " + path);
                continue;
            }

            soundCache.put(event, new AudioClip(url.toExternalForm()));
        }
    }

    public static void play(SfxEvent event) {
        AudioClip clip = soundCache.get(event);
        if (clip != null) clip.play();
    }

    //
    public static void playMusic() {
        if (bgMusicClip != null) bgMusicClip.play();
    }

    public static void stopMusic() {
        if (bgMusicClip != null) bgMusicClip.stop();
    }

}

