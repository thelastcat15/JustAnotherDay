package Ui;

import Maps.PlantTile;
import Maps.Tile;
import java.awt.Graphics2D;

public class ItemSlot extends BaseUi {

    UiManager UiManage;

    int Clicked = 0;
    int size = 100;
    int offset = 20;
    int offsetIn = 10;
    int pos[] = new int[2];

    public PixelFrame ItemSelectFrame;

    public ItemSlot(UiManager UiManage) {
        super();
        this.gp = UiManage.gp;
        this.UiManage = UiManage;

        pos[0] = gp.screenWidth - size - offset;
        pos[1] = gp.screenHeight - size - offset;

        ItemSelectFrame = new PixelFrame(2, pos[0], pos[1], size, size, 5);
    }

    public void update() {
        if (Clicked != gp.getMouseClick()) {
            Clicked = gp.getMouseClick();
            int Row = UiManage.UiTileSelect.Row;
            int Col = UiManage.UiTileSelect.Col;
            if (Row != -1 && Col != -1) {
                Tile tile = gp.Maps.MapModifyLoaded.Tiles[Row][Col];
                if (UiManage.MyBackpack.ItemSelect.Name == "Hoe") {
                    if (tile != null) {
                        return;
                    }
                    gp.Maps.MapModifyLoaded.Tiles[Row][Col] = new PlantTile(Row, Col);
                } else if (UiManage.MyBackpack.ItemSelect.Type == "Seed") {
                    if (tile == null || tile.getId() != 0) {
                        return;
                    }
                    if (UiManage.MyBackpack.ItemSelect.Amount <= 0) {
                        return;
                    }
                    UiManage.MyBackpack.ItemSelect.Amount--;
                    tile.Plant(UiManage.MyBackpack.ItemSelect.itemId, UiManage.MyBackpack.ItemSelect.State);
                } else if (UiManage.MyBackpack.ItemSelect.Name == "Shovel") {
                    if (tile == null || !tile.CanHavest) {
                        return;
                    }
                    UiManage.MyBackpack.Items[tile.getId()-5].Amount++;
                    gp.Maps.MapModifyLoaded.Tiles[Row][Col] = null;
                } else if (UiManage.MyBackpack.ItemSelect.Name == "Watering Pot") {
                    if (tile == null || tile.getId() == 0) {
                        return;
                    }
                    
                    tile.Watering();
                }
            }
        }
    }

    public void draw(Graphics2D g2) {
        ItemSelectFrame.draw(g2);
        g2.drawImage(UiManage.MyBackpack.ItemSelect.Image, pos[0] + offsetIn, pos[1] + offsetIn, size - (offsetIn * 2), size - (offsetIn * 2), null);
    }
}
