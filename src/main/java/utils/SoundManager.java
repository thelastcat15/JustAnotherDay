package utils;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {

    private final Map<String, String> soundFiles = new HashMap<>();
    private Player currentPlayer;

    public SoundManager() {
//        loadSound("SelectUi", "/Sounds/SelectUi.mp3");s
        loadSound("ClickUi1", "/Sounds/Click1.mp3");
        loadSound("ClickUi2", "/Sounds/Click2.mp3");
    }
    
    public void loadSound(String name, String filePath) {
        soundFiles.put(name, filePath);
    }

    // Plays a sound by its name from resources
    public void playSound(String name) {
        new Thread(() -> {
            String filePath = soundFiles.get(name);

            if (filePath != null) {
//                stopSound();
                try {
                    InputStream is = getClass().getResourceAsStream(filePath);
                    if (is == null) {
                        System.out.println("Sound file not found: " + filePath);
                        return;
                    }
                    BufferedInputStream bis = new BufferedInputStream(is);
                    currentPlayer = new Player(bis);

                    try {
                        currentPlayer.play();
                    } catch (JavaLayerException e) {
                        System.out.println("Error playing sound: " + e.getMessage());
                    }
                } catch (JavaLayerException e) {
                    System.out.println("Error loading sound: " + e.getMessage());
                }
            } else {
                System.out.println("Sound not found: " + name);
            }
        }).start();
    }

    public void stopSound() {
        if (currentPlayer != null) {
            currentPlayer.close();
        }
    }
}
