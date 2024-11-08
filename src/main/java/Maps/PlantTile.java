package Maps;

import java.awt.Graphics2D;

public class PlantTile extends Tile {
    String Type = "Crop";
    boolean Shovel;
    boolean Water = true;
    int DirtId = 1010;
    int WaterId = 2924;
    int State;
    int States[][];
    
    public PlantTile(int row, int col) {
        this.row = row;
        this.col = col;
        
        Shovel = true;
    }
    
    public void Watering() {
        if (!Water) {
            Water = true;
        }
    }
    
    public void Plant(int id, int States[][]) {        
        this.id = id;
        this.States = States;
        
        State = 0;
        Water = false;
    }
    
    public int getId() {
        return id;
    }
    
    public String getType() {
        return Type;
    }
    
    public void update() {
        int diffState = (States.length-1) - State;
//        System.out.println(diffState + " " + State);
        if (Water && diffState > 0) {
            if (diffState == 1) {
                DirtId = 1138;
                CanHavest = true;
            } else {
                Water = false;
            }
            State++;
        }
    }
        
    public void draw(Graphics2D g2, int TileWorldX, int TileWorldY, TileManager MapAsset, int TileSize) {
        if (Shovel) {
            g2.drawImage(
                MapAsset.GetTile(0, DirtId),
                TileWorldX,
                TileWorldY,
                TileSize,
                TileSize,
                null
            );
        }
        if (id != 0) {
            g2.drawImage(
                MapAsset.GetTile(0, States[State][1]),
                TileWorldX,
                TileWorldY,
                TileSize,
                TileSize,
                null
            );
            int idImage = States[State][0];
            if (idImage != -2) {
                g2.drawImage(
                    MapAsset.GetTile(0, idImage),
                    TileWorldX,
                    TileWorldY-TileSize,
                    TileSize,
                    TileSize,
                    null
                );
            }
        }
        if (!Water) {
            g2.drawImage(
                MapAsset.GetTile(0, WaterId),
                TileWorldX-TileSize/2,
                TileWorldY-TileSize/2,
                TileSize,
                TileSize,
                null
            );
        }
    }
}
