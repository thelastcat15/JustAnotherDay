package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import main.GamePanel;
import main.KeyHandler;

import static utils.Constants.PlayerConstants.*;

public class Player extends Entity{
    public final int centerX;
    public final int centerY;
    public final int screenX;
    public final int screenY;
    
    GamePanel gp;
    KeyHandler keyH;
    boolean ToggleE = false;
    public int Day = 1;
    
    public int worldX = 2092;
    public int worldY = 2315;
    public Rectangle HRP;
    
    String pathFile = "entitys/player";
    int[] column = {9, 8};
    int realSize = 200;
    int[] size = {96, 64, 15, 0, 30, 0};
    AnimationManager myAnimation = new AnimationManager(pathFile, column, size);
    
    // Property
    private int money = 100;
    int CharacterAction = IDLE;
    int CharacterDirection = 0;
    public boolean DisableMovement = false;
    double diagSpeed;
    
    
    public Player(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        this.speed = 4;
        this.diagSpeed = Math.sqrt(((speed*speed)/2));
        
        centerX = gp.screenWidth/2;
        centerY = gp.screenHeight/2;
        
        screenX = centerX - (realSize/2);
        screenY = centerY - (realSize/2);
    }
    
    public void setPosition(int x,int y,int speed) {
        this.worldX = x;
        this.worldY = y;
        this.speed = speed;
        this.diagSpeed = Math.sqrt(((speed*speed)/2));
    }
    
    public int getMoney() {
        return money;
    }
    public void setMoney(int money) {
        this.money = money;
    }
    
    public void update() {
        CharacterAction = IDLE;
//        if (keyH.upPressed && keyH.leftPressed) {
//            worldY -= diagSpeed;
//            worldX -= diagSpeed;
//            CharacterAction = RUNNING;
//            CharacterDirection = 0;
//        } else if (keyH.upPressed && keyH.rightPressed) {
//            worldY -= diagSpeed;
//            worldX += diagSpeed;
//            CharacterAction = RUNNING;
//            CharacterDirection = 1;
        if (!DisableMovement) {
            if (keyH.upPressed) {
                boolean CanCollide = true;
                Rectangle HRP = new Rectangle(centerX-21, centerY+6-speed, 41, 20);
                for (int i=0; i<3; i++) {
                    if (gp.Maps.TilesAround[0][i] != null) {
                        if (HRP.intersects(gp.Maps.TilesAround[0][i])) {
                            CanCollide = false;
                            break;
                        }
                    }
                }
                if (CanCollide) {
                    worldY -= speed;
                    CharacterAction = RUNNING;
                }
    //        } else if (keyH.downPressed && keyH.leftPressed) {
    //            worldY += diagSpeed;
    //            worldX -= diagSpeed;
    //            CharacterAction = RUNNING;
    //            CharacterDirection = 0;
    //        } else if (keyH.downPressed && keyH.rightPressed) {
    //            worldY += diagSpeed;
    //            worldX += diagSpeed;
    //            CharacterAction = RUNNING;
    //            CharacterDirection = 1;
            }
            if (keyH.downPressed) {
                boolean CanCollide = true;
                Rectangle HRP = new Rectangle(centerX-21, centerY+6+speed, 41, 20);
                for (int i=0; i<3; i++) {
                    if (gp.Maps.TilesAround[2][i] != null) {
                        if (HRP.intersects(gp.Maps.TilesAround[2][i])) {
                            CanCollide = false;
                            break;
                        }
                    }
                }
                if (CanCollide) {
                    worldY += speed;
                    CharacterAction = RUNNING;
                }
            }
            if (keyH.leftPressed) {
                boolean CanCollide = true;
                Rectangle HRP = new Rectangle(centerX-21-speed, centerY+6, 41, 20);
                for (int i=0; i<3; i++) {
                    if (gp.Maps.TilesAround[i][0] != null) {
                        if (HRP.intersects(gp.Maps.TilesAround[i][0])) {
                            CanCollide = false;
                            break;
                        }
                    }
                }
                if (CanCollide) {
                    worldX -= speed;
                    CharacterAction = RUNNING;
                    CharacterDirection = 0;
                }
            }
            if (keyH.rightPressed) {
                boolean CanCollide = true;
                Rectangle HRP = new Rectangle(centerX-21+speed, centerY+6, 41, 20);

                for (int i=0; i<3; i++) {
                    if (gp.Maps.TilesAround[i][2] != null) {
                        if (HRP.intersects(gp.Maps.TilesAround[i][2])) {
                            CanCollide = false;
                            break;
                        }
                    }
                }
                if (CanCollide) {
                    worldX += speed;
                    CharacterAction = RUNNING;
                    CharacterDirection = 1;
                }
            }
        }
        myAnimation.updateAnimationTick(CharacterAction);
        
        if (keyH.getToggleState(KeyEvent.VK_I)) {
            this.speed = 20;
        } else {
            this.speed = 4;
        }
        
        if (ToggleE != keyH.getToggleState(KeyEvent.VK_E)) {
            ToggleE = keyH.getToggleState(KeyEvent.VK_E);
            if (!gp.UiManage.transition.WIP) {
                if (2064 <= this.worldX && this.worldX <= 2116) {
                    if (2315 <= this.worldY && this.worldY <= 2355) {
                        gp.UiManage.transition.trigger();
                    }
                }
            }
        }
    }
    
    public void draw(Graphics2D g2) {
//        System.out.println("X : "+worldX+" | Y : "+worldY);
        try {
            g2.drawImage(
                myAnimation.getFrame(
                    CharacterAction,
                    CharacterDirection
                ),
                screenX,
                screenY,
                realSize,
                realSize,
                null
            );
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }
}



