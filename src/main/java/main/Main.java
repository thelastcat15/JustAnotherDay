package main;

import Ui.PixelFrame;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import utils.FileReader;

public class Main {
    public static void main(String[] args){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Just Another Day v1.0");
        window.setIconImage(Toolkit.getDefaultToolkit().getImage(Main.class.getResource("/Ui/JAD_bg.png")));
        
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        
        BufferedImage cursorImage = FileReader.loadImage("/Ui/cursor/Cursor.png");
        Image scaledCursorImage = cursorImage.getScaledInstance(120, 130, Image.SCALE_SMOOTH);
        Point cursorHotSpot = new Point(0, 0);
        Cursor customCursor = toolkit.createCustomCursor(scaledCursorImage, cursorHotSpot, "Custom Cursor");
        window.setCursor(customCursor);
        
        window.pack();
        
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
    }
}