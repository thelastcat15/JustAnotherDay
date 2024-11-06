package main;

import Ui.UiManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import utils.FileReader;
import utils.MapManager;

public class GamePanel extends JPanel implements Runnable{
    // Screen Setting
    public final int originalTileSize = 20;
    final int scale = 3;
    
    public final int tileSize = originalTileSize * scale;
    public static final int MaxScreenRow = 12;
    public static final int MaxScreenCol = 18;
    public final int screenWidth = tileSize * MaxScreenCol;
    public final int screenHeight = tileSize * MaxScreenRow;
    
    int FPS = 60;
    
    int GameState = 0;
    KeyHandler keyH = new KeyHandler();
    Thread gameThread, updateThread;
    public UiManager UiManage = new UiManager(this, keyH);
    public Player player = new Player(this, keyH);
    public MapManager Maps = new MapManager(this, 0);
    
    private int mouseX = -1;
    private int mouseY = -1;
    private int mouseClick = 0;
    
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    mouseClick = (mouseClick+1)%2;
                    if (GameState == 0) {
                        GameState = 1;
                        startGameThread();
                    }
                }
            }
        });
        
        
    }
    public int getMouseClick() {
        return mouseClick;
    }
    
    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }
    
    public void startGameThread() {
        gameThread = new Thread(this);
        updateThread = new Thread(() -> {
            double delta = 0;
            double drawInterval = 1000000000/FPS;
            long currentTime;
            long lastTime = System.nanoTime();
            while (updateThread != null) {
                currentTime = System.nanoTime();

                delta += (currentTime - lastTime) / drawInterval;
                lastTime = currentTime;

                if (delta >= 1) {
                    update();
                    delta--;
                }
            }
        });
        gameThread.start();
        updateThread.start();
    }
    
    @Override
    public void run() {
        double delta = 0;
        double drawInterval = 1000000000/FPS;
        long currentTime;
        long lastTime = System.nanoTime();
        
        while (gameThread != null) {
            currentTime = System.nanoTime();
            
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            
            if (delta >= 1) {
                repaint();
                delta--;
            }
        }
    }
    
    public void update() {
        player.update();
        UiManage.update();
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        
        if (GameState == 1) {
            Maps.draw(g2);
            player.draw(g2);
            UiManage.draw(g2);
        } else {
            try {
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)0.5));
                g2.drawImage(
                    FileReader.loadImage("/maps/temp/MainMenu.png"),
                    0,
                    0,
                    null
                );
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)1));
                g2.setColor(Color.WHITE);
                
                InputStream is = getClass().getResourceAsStream("/fonts/PixeloidSansBold.ttf");
                Font PixeloidSansBold = Font.createFont(Font.TRUETYPE_FONT, is);
                
                Font font = PixeloidSansBold.deriveFont(60f);
                g2.setFont(font);
                FontMetrics metrics = g2.getFontMetrics(font);
                String Title = "Just Another Day";
                int TextWidth = metrics.stringWidth(Title);
                g2.drawString(Title, (screenWidth/2)-(TextWidth/2), screenHeight/2-50);
                
                font = PixeloidSansBold.deriveFont(40f);
                g2.setFont(font);
                metrics = g2.getFontMetrics(font);
                String SubTitle = "Click To Start";
                int TextWidth2 = metrics.stringWidth(SubTitle);
                g2.drawString(SubTitle, (screenWidth/2)-(TextWidth2/2), screenHeight/2+50);
            } catch (FontFormatException ex) {
                Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(GamePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        g2.dispose();
    }
}