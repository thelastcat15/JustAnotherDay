package utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class FileReader {
    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(FileReader.class.getResource(path));
        } catch (IOException | NullPointerException e) {
            System.err.println("Image not found: " + path);
            return null;
        }
    }
}
