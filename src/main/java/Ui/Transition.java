package Ui;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Transition extends BaseUi {
    float Opacity = 0;
    public boolean WIP = false;
    
    public Transition(UiManager UiManage) {
        this.gp = UiManage.gp;
    }
    
    public void trigger() {
        Thread t = new Thread(() -> {
            gp.player.DisableMovement = true;
            WIP = true;
            try {
                for (Opacity=0; Opacity<1; Opacity+=0.01) {
                    Thread.sleep(20);
                }
                gp.Maps.MapModifyLoaded.updateState();
                gp.player.Day++;
                for (; Opacity>0; Opacity-=0.01) {
                    Thread.sleep(20);
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Transition.class.getName()).log(Level.SEVERE, null, ex);
            }
            WIP = false;
            gp.player.DisableMovement = false;
        });
        t.start();
    }
    
    public void draw(Graphics2D g2) {
        if (WIP) {
//            System.err.println(Opacity);
            g2.setColor(Color.BLACK);
            float FixOpacity = Opacity;
            FixOpacity = FixOpacity > 1 ? 1 : FixOpacity < 0 ? 0 : FixOpacity;
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, FixOpacity));
            g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)1));
            g2.setColor(Color.WHITE);
        }
    }
}
