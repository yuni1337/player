package org.example;

import java.io.File;
import java.io.FileInputStream;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class AudioFilePlayer {
    private File file;
    private Thread playbackThread;
    private AdvancedPlayer player;
    private boolean isPlaying = false;

    public AudioFilePlayer(File file) {
        this.file = file;
    }

    public void play() {
        // Логика для воспроизведения аудиофайла
        System.out.println("Playing: " + file.getAbsolutePath());
        try {
            if (file.getName().toLowerCase().endsWith(".mp3") && !isPlaying) {
                playbackThread = new Thread(() -> {
                    try (FileInputStream directory = new FileInputStream(file)) {
                        player = new AdvancedPlayer(directory);
                        player.play();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                playbackThread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (player != null) {
            player.close();
        }
        if (playbackThread != null && playbackThread.isAlive()) {
            playbackThread.interrupt();
            playbackThread = null;
        }
        isPlaying = false;
    }
}
