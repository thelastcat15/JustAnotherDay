package Ui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import utils.FileReader;

public class PixelFrame {
    private BufferedImage topLeft, topCenter, topRight;
    private BufferedImage middleLeft, middleCenter, middleRight;
    private BufferedImage bottomLeft, bottomCenter, bottomRight;
    
    int x, y, width, height, scale;
    int color;
    String Colors[] = {"w", "lt", "dt"};
    
    public PixelFrame(int color, int x, int y, int width, int height) {
        constructor(color, x, y, width, height, 1);
    }
    public PixelFrame(int color, int x, int y, int width, int height, int scale) {
        constructor(color, x, y, width, height, scale);
    }
    
    public void constructor(int color, int x, int y, int width, int height, int scale) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.scale = scale;
        this.color = color;
        loadImages();
    }
    
    private void loadImages() {
        // Load the corner and edge images from provided files
        topLeft = FileReader.loadImage("/Ui/Box/"+Colors[color]+"_box_9slice_tl.png");
        topCenter = FileReader.loadImage("/Ui/Box/"+Colors[color]+"_box_9slice_tc.png");
        topRight = FileReader.loadImage("/Ui/Box/"+Colors[color]+"_box_9slice_tr.png");
        middleLeft = FileReader.loadImage("/Ui/Box/"+Colors[color]+"_box_9slice_lc.png");
        middleCenter = FileReader.loadImage("/Ui/Box/"+Colors[color]+"_box_9slice_c.png");
        middleRight = FileReader.loadImage("/Ui/Box/"+Colors[color]+"_box_9slice_rc.png");
        bottomLeft = FileReader.loadImage("/Ui/Box/"+Colors[color]+"_box_9slice_bl.png");
        bottomCenter = FileReader.loadImage("/Ui/Box/"+Colors[color]+"_box_9slice_bc.png");
        bottomRight = FileReader.loadImage("/Ui/Box/"+Colors[color]+"_box_9slice_br.png");
    }
    
    public void draw(Graphics2D g2) {
        
        int panelWidth = width;
        int panelHeight = height;
        
        int cornerWidth = topLeft.getWidth() * scale;
        int cornerHeight = topLeft.getHeight() * scale;
        
        // Draw the corners
        g2.drawImage(topLeft, x+0, y+0, cornerWidth, cornerHeight, null);
        g2.drawImage(topRight, x+panelWidth - cornerWidth, y+0, cornerWidth, cornerHeight, null);
        g2.drawImage(bottomLeft, x+0, y+panelHeight - cornerHeight, cornerWidth, cornerHeight, null);
        g2.drawImage(bottomRight, x+panelWidth - cornerWidth, y+panelHeight - cornerHeight, cornerWidth, cornerHeight, null);
        
        // Draw the top and bottom edges (scaling the width)
        g2.drawImage(topCenter, x+cornerWidth, y+0, panelWidth - 2 * cornerWidth, cornerHeight, null);
        g2.drawImage(bottomCenter, x+cornerWidth, y+panelHeight - cornerHeight, panelWidth - 2 * cornerWidth, cornerHeight, null);
        
        // Draw the left and right edges (scaling the height)
        g2.drawImage(middleLeft, x+0, y+cornerHeight, cornerWidth, panelHeight - 2 * cornerHeight, null);
        g2.drawImage(middleRight, x+panelWidth - cornerWidth, y+cornerHeight, cornerWidth, panelHeight - 2 * cornerHeight, null);
        
        // Draw the center (scaling both width and height)
        g2.drawImage(middleCenter, x+cornerWidth, y+cornerHeight, panelWidth - 2 * cornerWidth, panelHeight - 2 * cornerHeight, null);
        
    }
}
