package com.comp2042.sfx;

import com.comp2042.util.SfxEvent;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility class to load and play audio resources for the game.
 * Includes both SFX clips and background music.
 */
public final class SoundLoader {
    private static MediaPlayer bgMusicPlayer;
    private static final Map<SfxEvent, AudioClip> soundCache = new HashMap<>();

    /** Private constructor to prevent instantiation. */
    private SoundLoader() {}

    /**
     * Loads all audio resources (background music + SFX) into memory.
     * Background music loops indefinitely.
     */
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

    /**
     * Plays a cached audio clip for a given SFX event.
     * @param event The SFX event to play.
     */
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

