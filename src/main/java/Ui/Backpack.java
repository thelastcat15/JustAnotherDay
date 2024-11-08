package Ui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import main.KeyHandler;
import Maps.TileManager;


class Seed extends Item {
    public Seed(int itemId, BufferedImage Image, String Name, int Amount, int State[][]) {
        super(itemId, Image, Name, Amount);
        this.State = State;
        this.Type = "Seed";
    }
}

class Crop extends Item {
    public Crop(int itemId, BufferedImage Image, String Name, int Amount) {
        super(itemId, Image, Name, Amount);
        this.Type = "Crop";
    }
}

class Tool extends Item {
    public Tool(int itemId, BufferedImage Image, String Name, int Amount) {
        super(itemId, Image, Name, Amount);
        this.Type = "Tool";
    }
}


public class Backpack extends BaseUi {
    KeyHandler keyH;
    
    public boolean Open = false;
    
    int Clicked = 0;
    int Position[][][] = new int[3][5][4];
    PixelFrame Frames[] = new PixelFrame[30];
    int size[] = {20, 20, 0, 0, 0, 0};
    TileManager CropsAsset = new TileManager("/Ui/Crops.png", size, false, false);
    TileManager ToolsAsset = new TileManager("/Ui/Tools.png", size, false, false);
    SelectTile ObjSelectTile;
    
    public Item Items[] = {
        new Crop(0, CropsAsset.GetTile(0, 1), "Carrot", 10),
        new Crop(1, CropsAsset.GetTile(0, 3), "Pumpkin", 10),
        new Crop(2, CropsAsset.GetTile(0, 5), "Radish", 10),
        new Crop(3, CropsAsset.GetTile(0, 7), "Potato", 10),
        new Crop(4, CropsAsset.GetTile(0, 8), "Cabbage", 10),
        new Seed(5, CropsAsset.GetTile(1, 1), "Carrot Seed", 10, new int[][]{{-2, 819}, {-2, 883}, {947, 1011}, {1075, 1139}}),
        new Seed(6, CropsAsset.GetTile(1, 3), "Pumpkin Seed", 10, new int[][]{{-2, 821}, {-2, 885}, {949, 1013}, {1077, 1141}}),
        new Seed(7, CropsAsset.GetTile(1, 5), "Radish Seed", 10, new int[][]{{-2, 823}, {-2, 887}, {951, 1015}, {1079, 1143}}),
        new Seed(8, CropsAsset.GetTile(1, 7), "Potato Seed", 10, new int[][]{{-2, 825}, {-2, 889}, {953, 1017}, {1081, 1145}}),
        new Seed(9, CropsAsset.GetTile(1, 8), "Cabbage Seed", 10, new int[][]{{-2, 826}, {-2, 890}, {954, 1018}, {1082, 1146}}),
        new Tool(10, ToolsAsset.GetTile(0, 5), "Watering Pot", 1),
        new Tool(11, ToolsAsset.GetTile(4, 5), "Shovel", 1),
        new Tool(12, ToolsAsset.GetTile(1, 5), "Hoe", 1),
    };
    Item ItemFocus = Items[0];
    public Item ItemSelect = ItemFocus;

    int StartX = 460;
    int StartY = 228;
    int Offset = 10;
    int Size = 81;
    
    int MaxRow = 3;
    int MaxCol = 5;
    
    int x = 150;
    int y = 465;
    
    
    // =========================================================
    
    public Backpack(UiManager UiManage) {
        super();
        this.gp = UiManage.gp;
        this.keyH = UiManage.keyH;
        ObjSelectTile = new SelectTile(UiManage, 30, 10);
        
        this.Frames[0] = new PixelFrame(1, 150, 185, 270, 50, 3);
        this.Frames[1] = new PixelFrame(0, 150, 250, 270, 200, 3);
        this.Frames[2] = new PixelFrame(1, 150, 465, 270, 70, 3);
        this.Frames[3] = new PixelFrame(2, 435, 185, 495, 350, 3);
        this.Frames[4] = new PixelFrame(1, 443, 193, 479, 334, 3);
        
        
        int i = 0;
        for (int row=0; row<MaxRow; row++) {
            for (int col=0; col<MaxCol; col++) {
                int SX = StartX + (col*Size) + (col*Offset);
                int SY = StartY + (row*Size) + (row*Offset);
                Position[row][col] = new int[]{ SX, SY, SX+Size, SY+Size };
                this.Frames[5+i] = new PixelFrame(2, SX, SY, Size, Size, 3);
                i++;
            }
        }
        
    }
    
    public Item getItem(int index) {
        return Items[index];
    }
    
    public void update() {
        Open = (keyH.getToggleState(KeyEvent.VK_B));
        
        if (Open) {
//        if (keyH.getToggleState(KeyEvent.VK_J)) {
//            x -= 1;
//        }
//        if (keyH.getToggleState(KeyEvent.VK_L)) {
//            x += 1;
//        }
//        if (keyH.getToggleState(KeyEvent.VK_I)) {
//            y -= 1;
//        }
//        if (keyH.getToggleState(KeyEvent.VK_K)) {
//            y += 1;
//        }
//        System.out.println("x: "+x+" y:"+y);

            boolean Detect = false;
            int M_X = gp.getMouseX();
            int M_Y = gp.getMouseY();
            for (int row=0; row<3; row++) {
                for (int col=0; col<5; col++) {
                    if (Position[row][col][0] <= M_X && M_X <= Position[row][col][2]) {
                        if (Position[row][col][1] <= M_Y && M_Y <= Position[row][col][3]) {
                            ObjSelectTile.Pos[0][0] = new int[]{Position[row][col][0], Position[row][col][1]};
                            ObjSelectTile.Pos[0][1] = new int[]{Position[row][col][2], Position[row][col][1]};
                            ObjSelectTile.Pos[1][0] = new int[]{Position[row][col][0], Position[row][col][3]};
                            ObjSelectTile.Pos[1][1] = new int[]{Position[row][col][2], Position[row][col][3]};
                            ObjSelectTile.Row = row;
                            ObjSelectTile.Col = col;
                            Detect = true;
                            break;
                        }
                    }
                }
            }
            ObjSelectTile.setVisible(Detect);

            boolean Detect2 = false;
            if (150 <= M_X && M_X <= 420) {
                if (465 <= M_Y && M_Y <= 535) {
                    Detect2 = true;
                }
            }
            if (Clicked != gp.getMouseClick()) {
                Clicked = gp.getMouseClick();
                if (Detect) {
                    gp.Sounds.playSound("ClickUi1");
                    int index = (ObjSelectTile.Row * 5) + ObjSelectTile.Col;
                    if (index < Items.length) {
                        ItemFocus = Items[index];
                        System.out.println("Change Focus : "+index);
                    }
                } else if (Detect2) {
                    gp.Sounds.playSound("ClickUi2");
                    ItemSelect = ItemFocus;
                }
            }
        } else {
            Clicked = gp.getMouseClick();
        }
    }
    
    public void draw(Graphics2D g2) {
        if (Open) {
            g2.setColor(Color.BLACK);
            for (PixelFrame Frame : this.Frames) {
                if (Frame != null) {
                    Frame.draw(g2);
                }
            }
            
            g2.setFont(this.PixeloidSansBold.deriveFont(24f));
            g2.drawString("Inventory", 210, 217);
            g2.setFont(this.PixeloidSans.deriveFont(24f));
            g2.drawString(ItemFocus.Name, 178, 296);
            g2.setColor(new Color(0, 0, 0, 100));
            g2.drawString("Amount", 178, 331);
            if (ItemFocus.Type == "Seed") {
                g2.drawString("Stage", 178, 366);
            }
            
            FontMetrics metrics = g2.getFontMetrics(this.PixeloidSans.deriveFont(24f));
            
            String Amount = String.valueOf(ItemFocus.Amount);
            int AmountWidth = metrics.stringWidth(Amount);
            g2.drawString(Amount, 392-AmountWidth, 331);
            
            
            if (ItemFocus.Type == "Seed") {
                String State = String.valueOf(ItemFocus.State.length);
                int StateWidth = metrics.stringWidth(State);
                g2.drawString(State, 392-StateWidth, 366);
            }
            
            g2.setColor(Color.BLACK);
            
            if (ItemSelect.Name == ItemFocus.Name) {
                g2.drawString("Selected", 232, 507);
            } else {
                g2.drawString("Select", 243, 507);
            }
            
            int i = 0;
            int OffsetIn = 10;
            int row = 0, col = 0;
            for (Item SelectItem : Items) {
                int SX = StartX + (col*Size) + (col*Offset);
                int SY = StartY + (row*Size) + (row*Offset);
                g2.drawImage(SelectItem.Image, SX+OffsetIn, SY+OffsetIn, Size-(OffsetIn*2), Size-(OffsetIn*2), null);
//                g2.drawRect(SX+OffsetIn, SY+OffsetIn, Size-(OffsetIn*2), Size-(OffsetIn*2));
                col++;
                if (col >= MaxCol) {
                    col = 0;
                    row++;
                }
            }
            
            ObjSelectTile.draw(g2);
        }
    }
}
