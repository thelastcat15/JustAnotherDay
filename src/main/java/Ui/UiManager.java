package Ui;

import java.awt.Graphics2D;
import main.GamePanel;
import main.KeyHandler;

public class UiManager {
    GamePanel gp;
    KeyHandler keyH;
    public SelectTile UiTileSelect;
    public Backpack MyBackpack;
    public ItemSlot MyItemSlot;
    public DateGame DateGameUi;
    
    
    public UiManager(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        this.UiTileSelect = new SelectTile(this);
        this.MyBackpack = new Backpack(this);
        this.MyItemSlot = new ItemSlot(this);
        this.DateGameUi = new DateGame(this);
    }
    
    public void update() {
        this.MyBackpack.update();
        this.MyItemSlot.update();
    }
    
    public void draw(Graphics2D g2) {
        this.UiTileSelect.draw(g2);
        this.MyBackpack.draw(g2);
        this.MyItemSlot.draw(g2);
        this.DateGameUi.draw(g2);
    }
}
