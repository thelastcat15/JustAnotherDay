/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Ui;

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
        
        pos[0] = gp.screenWidth-size-offset;
        pos[1] = gp.screenHeight-size-offset;
        
        ItemSelectFrame = new PixelFrame(2, pos[0], pos[1], size, size, 5);
    }
    
    int States[][][] = {
        {
            {-2, 819}, {-2, 883}, {947, 1011}, {1075, 1139}
        },
        {
            {-2, 821}, {-2, 885}, {949, 1013}, {1077, 1141}
        },
        {
            {-2, 823}, {-2, 887}, {951, 1015}, {1079, 1143}
        },
        {
            {-2, 825}, {-2, 889}, {953, 1017}, {1081, 1145}
        },
        {
            {-2, 826}, {-2, 890}, {954, 1016}, {1082, 1146}
        }
    };
    
    public void update() {
        if (Clicked != gp.getMouseClick()) {
            Clicked = gp.getMouseClick();
            int Row = UiManage.UiTileSelect.Row;
            int Col = UiManage.UiTileSelect.Col;
            if (Row != -1 && Col != -1) {
                int tileNum = gp.Maps.MapLoaded[Row][Col][6];
                if (UiManage.MyBackpack.ItemSelect.Name == "Hoe") {
                    if (tileNum != -2) return;
                    
                    gp.Maps.MapLoaded[Row][Col][6] = 1010;
                } else if (UiManage.MyBackpack.ItemSelect.Type == "Seed") {
                    int tileSeed = gp.Maps.MapLoaded[Row][Col][7];
                    if (tileNum != 1010 || tileSeed != -2) return;
                    if (UiManage.MyBackpack.ItemSelect.Amount > 0) return;
                    
                    UiManage.MyBackpack.ItemSelect.Amount--;
                    
                    gp.Maps.MapLoaded[Row][Col][9] = UiManage.MyBackpack.ItemSelect.State[0][0];
                    gp.Maps.MapLoaded[Row-1][Col][8] = UiManage.MyBackpack.ItemSelect.State[0][0];
                    gp.Maps.MapLoaded[Row][Col][7] = UiManage.MyBackpack.ItemSelect.State[0][1];
                    gp.Maps.MapLoaded[Row][Col][7] = ;
                } else if (UiManage.MyBackpack.ItemSelect.Name == "Shovel") {
                    int cropId = gp.Maps.MapLoaded[Row][Col][7];
                    if (cropId == -2) return;
                    
                    for (int i=0; i<States.length; i++) {
                        if (cropId == States[i][3][1]) {
                            gp.Maps.MapLoaded[Row][Col][6] = -2;
                            gp.Maps.MapLoaded[Row][Col][7] = -2;
                            gp.Maps.MapLoaded[Row-1][Col][8] = -2;
                            UiManage.MyBackpack.Items[i].Amount++;
                            break;
                        }
                    }
                }
            }
        }
    }
    
    public void draw(Graphics2D g2) {
        ItemSelectFrame.draw(g2);
        g2.drawImage(UiManage.MyBackpack.ItemSelect.Image, pos[0]+offsetIn, pos[1]+offsetIn, size-(offsetIn*2), size-(offsetIn*2), null);
    }
}
