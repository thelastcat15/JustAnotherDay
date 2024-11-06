package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed;
    
    // Map to store toggle states for specific keys
    private Map<Integer, Boolean> toggleStates = new HashMap<>();
    private Map<Integer, Boolean> keyPressedStates = new HashMap<>();

    public KeyHandler() {
        // Initialize toggle keys and their states
        initializeToggleKey(KeyEvent.VK_B);
        initializeToggleKey(KeyEvent.VK_I);
        initializeToggleKey(KeyEvent.VK_E);
    }

    // Method to initialize a new toggle key
    private void initializeToggleKey(int keyCode) {
        toggleStates.put(keyCode, false);      // Initial toggle state (off)
        keyPressedStates.put(keyCode, false);  // Initial pressed state (not pressed)
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        // Movement keys
        if (code == KeyEvent.VK_W) upPressed = true;
        if (code == KeyEvent.VK_S) downPressed = true;
        if (code == KeyEvent.VK_A) leftPressed = true;
        if (code == KeyEvent.VK_D) rightPressed = true;

        // For toggle keys
        if (keyPressedStates.containsKey(code)) {
            keyPressedStates.put(code, true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        // Movement keys
        if (code == KeyEvent.VK_W) upPressed = false;
        if (code == KeyEvent.VK_S) downPressed = false;
        if (code == KeyEvent.VK_A) leftPressed = false;
        if (code == KeyEvent.VK_D) rightPressed = false;

        // Toggle action for registered keys
        if (keyPressedStates.containsKey(code) && keyPressedStates.get(code)) {
            keyPressedStates.put(code, false); // Reset the pressed state
            toggleStates.put(code, !toggleStates.get(code)); // Toggle the state
        }
    }

    // Method to check toggle state of a key
    public boolean getToggleState(int keyCode) {
        return toggleStates.getOrDefault(keyCode, false);
    }
}
