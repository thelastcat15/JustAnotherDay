package Ui;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import main.GamePanel;

public class BaseUi {
    GamePanel gp;
    Font PixeloidMono, PixeloidSans, PixeloidSansBold;
    
    public BaseUi() {
        try {
            InputStream is = getClass().getResourceAsStream("/fonts/PixeloidMono.ttf");
            this.PixeloidMono = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/fonts/PixeloidSans.ttf");
            this.PixeloidSans = Font.createFont(Font.TRUETYPE_FONT, is);
            is = getClass().getResourceAsStream("/fonts/PixeloidSansBold.ttf");
            this.PixeloidSansBold = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
    }
}
