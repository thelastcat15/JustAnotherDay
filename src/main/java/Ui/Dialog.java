package Ui;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dialog extends BaseUi {
    boolean ShowMain = false;
    boolean ShowSub = false;
        
    String[] options;
    String MainText = "";
    
    int Clicked = 0;
    int offsetX = 30;
    int offsetY = 50;
    
    int Main_Pos[] = new int[4]; // top bottom left right
    int Main_Size[] = { 800, 200 };
    
    int Sub_Pos[] = new int[4]; // top bottom left right
    int Sub_Size[] = { 300, 100 };
    
    PixelFrame MainFrame;
    PixelFrame OptionFrame;
    
    public Dialog(UiManager UiManage) {
        super();
        this.gp = UiManage.gp;
        
        Main_Pos[0] = 30;
        Main_Pos[1] = Main_Pos[0] + Main_Size[1];
        Main_Pos[2] = (gp.screenWidth/2) - (Main_Size[0]/2);
        Main_Pos[3] = Main_Pos[2] + Main_Size[0];
        
        Sub_Pos[0] = Main_Pos[1] + 20;
        Sub_Pos[1] = Sub_Pos[0] + Sub_Size[1];
        Sub_Pos[2] = Main_Pos[3] - Sub_Size[0];
        Sub_Pos[3] = Sub_Pos[2] + Sub_Size[0];
        
        MainFrame = new PixelFrame(1, Main_Pos[2], Main_Pos[0], Main_Size[0], Main_Size[1], 3);
        OptionFrame = new PixelFrame(1, Sub_Pos[2], Sub_Pos[0], Sub_Size[0], Sub_Size[1], 3);
    }
    
    public int Typewriter(String text, int delay, boolean haveOption) {
        try {
            gp.player.DisableMovement = true;
            setShowSub(false);
            for(int Cursor = 1; Cursor <= text.length(); Cursor++){
                this.MainText = text.substring(0, Cursor);
                Thread.sleep(delay);
            }
            if (haveOption) {
                setShowSub(true);
                long fps = 1000/60;
                Clicked = gp.getMouseClick();
                while (true) {
                    Thread.sleep(fps);
                    int M_X = gp.getMouseX();
                    int M_Y = gp.getMouseY();
                    if (Clicked != gp.getMouseClick()) {
                        Clicked = gp.getMouseClick();
                        if (Sub_Pos[2] <= M_X && M_X <= Sub_Pos[3]) {
                            if (Sub_Pos[0] <= M_Y && M_Y <= Sub_Pos[1]) {
                                setShowSub(false);
                                Thread.sleep(fps*2);
                                int ButtonSize = (Sub_Pos[1] - Sub_Pos[0]) / options.length;
                                for (int i=1; i<=options.length; i++) {
                                    if (M_Y <= Sub_Pos[0] + (ButtonSize*i)) {
                                        return i-1;
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                Thread.sleep(2000);
                gp.player.DisableMovement = false;
            }
        } catch (InterruptedException ex) {
            System.out.println("Textwriter Error");
        }
        return -1;
    }
    
    public void setOption(String[] options) {
        this.options = options;
    }
    
    public void setShowSub(boolean x) {
        this.ShowSub = x;
    }
    
    public void setShowMain(boolean x) {
        this.ShowMain = x;
    }
    public boolean getShowMain() {
        return this.ShowMain;
    }
    
    public static void drawStringMultiLine(Graphics2D g, String text, int lineWidth, int x, int y) {
        FontMetrics m = g.getFontMetrics();
        if(m.stringWidth(text) < lineWidth) {
            g.drawString(text, x, y);
        } else {
            String[] words = text.split(" ");
            String currentLine = words[0];
            for(int i = 1; i < words.length; i++) {
                if(m.stringWidth(currentLine+words[i]) < lineWidth) {
                    currentLine += " "+words[i];
                } else {
                    g.drawString(currentLine, x, y);
                    y += m.getHeight()+10;
                    currentLine = words[i];
                }
            }
            if(currentLine.trim().length() > 0) {
                g.drawString(currentLine, x, y);
            }
        }
    }
    
    public void update() {}
    
    public void draw(Graphics2D g2) {
        if (ShowMain) {
            MainFrame.draw(g2);
            
            g2.setFont(this.PixeloidSans.deriveFont(24f));
            drawStringMultiLine(
                g2,
                MainText,
                Main_Pos[3] - Main_Pos[2] - (offsetX*2),
                Main_Pos[2] + offsetX,
                Main_Pos[0] + offsetY
            );
        }
        if (ShowSub) {
            FontMetrics m = g2.getFontMetrics();
            
            int plus = m.getHeight()+10;
            int ContentWidth = -1;
            int ContentHeight = (offsetX*2) - 20;
            for (String option:options) {
                int l = m.stringWidth(option);
                ContentWidth = l > ContentWidth ? l : ContentWidth;
                ContentHeight += plus;
            }
            ContentWidth += (offsetX*2);
            
            Sub_Size[0] = ContentWidth;
            Sub_Size[1] = ContentHeight;
            
            Sub_Pos[1] = Sub_Pos[0] + Sub_Size[1];
            Sub_Pos[2] = Sub_Pos[3] - Sub_Size[0];
            
            OptionFrame.Resize(
                Sub_Pos[2],
                Sub_Pos[0],
                ContentWidth,
                ContentHeight
            );
            
            OptionFrame.draw(g2);
            int y = 0;
            for (String option:options) {
                g2.drawString(
                    option,
                    Sub_Pos[2] + offsetX,
                    Sub_Pos[0] + offsetY + y
                );
                y += plus;
            }
            g2.drawRect(Sub_Pos[2], Sub_Pos[0], Sub_Size[0], Sub_Size[1]);
        }
    }
}
