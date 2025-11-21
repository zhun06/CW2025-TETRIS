package com.comp2042.sfx;

import com.comp2042.util.SfxEvent;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public final class SoundLoader {
    private static MediaPlayer bgMusicPlayer;
    private static final Map<SfxEvent, AudioClip> soundCache = new HashMap<>();

    // We don't want to instantiate this utility class
    private SoundLoader() {}

    public static void loadAll() {
        String bgPath = "/audio/bg_music.mp3";
        URL bgMusic = SoundLoader.class.getResource(bgPath);
        if (bgMusic != null) {
            Media media = new Media(bgMusic.toExternalForm());
            bgMusicPlayer = new MediaPlayer(media);
            bgMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop
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

    public static void playMusic() {
        if (bgMusicPlayer != null) bgMusicPlayer.play();
    }

    public static void pauseMusic() {
        if (bgMusicPlayer != null) bgMusicPlayer.pause();
    }

    public static void stopMusic() {
        if (bgMusicPlayer != null) bgMusicPlayer.stop();
    }

}

