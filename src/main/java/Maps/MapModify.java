package Maps;

public class MapModify {
    int row, col;
    public Tile[][] Tiles;
    
    MapModify(int row, int col) {
        this.row = row;
        this.col = col;
        Tiles = new Tile[row][col];
    }
        
    public void updateState() {
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                Tile TileFocus = Tiles[r][c];
                if (TileFocus == null) continue;
                if (TileFocus.getType() == null) continue;
                if (TileFocus.getId() == 0) continue;
                
                TileFocus.update();
            }
        }
    }
}
