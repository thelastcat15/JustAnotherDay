package Ui;

import java.awt.Color;
import java.awt.Graphics2D;

public class StatUi extends BaseUi {
    public StatUi(UiManager UiManage) {
        super();
        this.gp = UiManage.gp;
    }
    
    public void draw(Graphics2D g2){
        g2.setFont(this.PixeloidSans.deriveFont(30f));
        g2.setColor(Color.WHITE);
        g2.drawString("Day : "+gp.player.Day, 20, 40);
        g2.drawString("Money : "+gp.player.getMoney()+"G", 20, gp.screenHeight-20);
    }
}
