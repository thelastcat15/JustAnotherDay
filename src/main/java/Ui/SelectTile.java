package Ui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import utils.FileReader;

public class SelectTile extends BaseUi {
    BufferedImage SelectBox[] = new BufferedImage[4];
    
    private boolean Visible = false;
    
    public int Row = -1;
    public int Col = -1;
    public int Pos[][][] = new int[2][2][2];
    private int size;
    private int offset;
        
    public SelectTile(UiManager UiManage) {
        super();
        this.gp = UiManage.gp;
        this.size = gp.tileSize/2;
        this.offset = gp.tileSize/2;
        LoadFile();
    }
    
    public SelectTile(UiManager UiManage, int size, int offset) {
        super();
        this.gp = UiManage.gp;
        this.size = size;
        this.offset = offset;
        LoadFile();
    }
    
    private void LoadFile() {
        SelectBox[0] = FileReader.loadImage("/Ui/SelectBox/selectbox_tl.png");
        SelectBox[1] = FileReader.loadImage("/Ui/SelectBox/selectbox_tr.png");
        SelectBox[2] = FileReader.loadImage("/Ui/SelectBox/selectbox_bl.png");
        SelectBox[3] = FileReader.loadImage("/Ui/SelectBox/selectbox_br.png");
    }
    
    public void setVisible(boolean x) {
        this.Visible = x;
    }
    
    public boolean getVisible() {
        return this.Visible;
    }
    
    public void draw(Graphics2D g2) {
        if (Visible) {
            g2.drawImage(
                SelectBox[0],
                Pos[0][0][0]-offset,
                Pos[0][0][1]-offset,
                size,
                size,
                null
            );
            g2.drawImage(
                SelectBox[1],
                Pos[0][1][0]+offset-size,
                Pos[0][1][1]-offset,
                size,
                size,
                null
            );
            g2.drawImage(
                SelectBox[2],
                Pos[1][0][0]-offset,
                Pos[1][0][1]+offset-size,
                size,
                size,
                null
            );
            g2.drawImage(
                SelectBox[3],
                Pos[1][1][0]-size+offset,
                Pos[1][1][1]-size+offset,
                size,
                size,
                null
            );
        }
    }
}
