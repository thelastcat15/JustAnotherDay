package utils;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import main.GamePanel;
import Ui.UiManager;

public class MapManager {
    GamePanel gp;
    
    public int[][][] MapLoaded;
    public Rectangle[][] TilesAround = {{null, null, null},{null, null, null},{null, null, null}};
    TileManager MapAsset;
    
    int Layers = 0;
    int[] MapSize = {100, 100};
    int[] TileSize = {20, 20, 0, 0, 0, 0};
    int[] Dirts = {460, 468, 469, 532, 533};
    
    String AssetPath = "/tiles/town.png";
    String[] Maps = {
        "/Map1/"
    };
    
    
    
    public MapManager(GamePanel gp, int MapNum) {
        this.gp = gp;
        LoadResource();
        ChangeMap(MapNum);
        
    }
    
    public void ChangeMap(int MapNum) {
        try {
            File[] FolderMap = new File("src/main/resources/maps/" + Maps[MapNum]).listFiles();
            Layers = FolderMap.length+4;
            int Row = 100;
            int Col = 100;
            MapLoaded = new int[Row][Col][Layers];
            int layer = 0;
            for (File file : FolderMap) {
                System.out.println(file.getName());
                InputStream is = getClass().getResourceAsStream("/maps/"+Maps[MapNum]+"/"+file.getName());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                String line;
                int row = 0;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    int col = 0;
                    for (int i=0; i < values.length; i++) {
                        MapLoaded[row][col++][layer] = Integer.parseInt(values[i]);
                    }
                    row++;
                }
                br.close();
                layer++;
            }
            for (int row=0; row<Row; row++) {
                for (int col=0; col<Col; col++) {
                    for (int i=0; i<4; i++) {
                        MapLoaded[row][col][layer+i] = -2;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void LoadResource() {
        MapAsset = new TileManager(AssetPath, TileSize, false, true);
    }
    
    public boolean CheckItemInArray(int arr[], int key) {
        for (int element : arr) {
            if (element == key) {
                return true;
            }
        }
        return false;
    }
    
    public void draw(Graphics2D g2) {
        int tileStartScreenX = gp.player.worldX - gp.player.screenX;
        int tileStartScreenY = gp.player.worldY - gp.player.screenY;
        
        int startRow = (tileStartScreenY / gp.tileSize);
        int startCol = tileStartScreenX / gp.tileSize;
        int endRow = startRow + gp.MaxScreenRow;
        int endCol = startCol + gp.MaxScreenCol;

        int centerRow = startRow + endRow;
        int centerCol = startCol + endCol;
        int centerRowLeft = (centerRow / 2) - 1;
        int centerRowRight = (centerRow / 2) + 1;
        int centerColTop = (centerCol / 2) - 1;
        int centerColBottom = (centerCol / 2) + 1;
        
        boolean IsDirt = false;
        int mouseX = gp.getMouseX();
        int mouseY = gp.getMouseY();
        for (int row = startRow-1; row <= endRow; row++) {
            for (int col = startCol; col <= endCol; col++) {
                int TileWorldY = (row * gp.tileSize) - tileStartScreenY + 20;
                int TileWorldX = (col * gp.tileSize) - tileStartScreenX;
                
                for (int layer = 1; layer < Layers; layer++) {
                    int tileNum = MapLoaded[row][col][layer];
                    if (tileNum == -1 || tileNum == -2) continue;

                    g2.drawImage(
                        MapAsset.GetTile(0, tileNum),
                        TileWorldX,
                        TileWorldY,
                        gp.tileSize,
                        gp.tileSize,
                        null
                    );
                }
                
                
                if (!IsDirt && !gp.UiManage.MyBackpack.Open) {
                    if (CheckItemInArray(Dirts, MapLoaded[row][col][2])) {
                        int EndTileWorldX = TileWorldX+gp.tileSize;
                        int EndTileWorldY = TileWorldY+gp.tileSize;
                        if (TileWorldX <= mouseX && mouseX <= EndTileWorldX) {
                            if (TileWorldY <= mouseY && mouseY <= EndTileWorldY) {
                                IsDirt = true;
                                gp.UiManage.UiTileSelect.Pos[0][0] = new int[]{TileWorldX, TileWorldY};
                                gp.UiManage.UiTileSelect.Pos[0][1] = new int[]{EndTileWorldX, TileWorldY};
                                gp.UiManage.UiTileSelect.Pos[1][0] = new int[]{TileWorldX, EndTileWorldY};
                                gp.UiManage.UiTileSelect.Pos[1][1] = new int[]{EndTileWorldX, EndTileWorldY};
                                gp.UiManage.UiTileSelect.Row = row;
                                gp.UiManage.UiTileSelect.Col = col;
                            }
                        }
                    }
                }
                
                if (centerRowLeft <= row && row <= centerRowRight && centerColTop <= col && col <= centerColBottom) {
                    if (MapLoaded[row][col][0] == 0) {
                        TilesAround[row-centerRowLeft][col-centerColTop] = new Rectangle(TileWorldX, TileWorldY, gp.tileSize, gp.tileSize);
                    } else {
                        TilesAround[row-centerRowLeft][col-centerColTop] = null;
                    }
                }
            }
        }
        gp.UiManage.UiTileSelect.setVisible(IsDirt);
        if (!IsDirt) {
            gp.UiManage.UiTileSelect.Row = -1;
            gp.UiManage.UiTileSelect.Col = -1;
        }
    }
}