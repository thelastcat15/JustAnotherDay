package Ui;

import java.awt.Color;
import java.awt.Graphics2D;

public class DateGame extends BaseUi {
    public DateGame(UiManager UiManage) {
        super();
        this.gp = UiManage.gp;
    }
    
    public void draw(Graphics2D g2){
        g2.setFont(this.PixeloidSans.deriveFont(30f));
        g2.setColor(Color.WHITE);
        g2.drawString("Day : "+gp.player.Day, 20, 40);
    }
}
