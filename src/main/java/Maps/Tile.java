package Maps;

import java.awt.Graphics2D;

public abstract class Tile {
    int id;
    int row,col;
    String Type;
    public boolean CanHavest;
    
    public abstract void update();
    public abstract void draw(Graphics2D g2, int TileWorldX, int TileWorldY, TileManager MapAsset, int TileSize);
    public abstract int getId();
    public abstract String getType();
    public abstract void Watering();
    public abstract void Plant(int id, int States[][]);
}
