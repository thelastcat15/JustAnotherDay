package Ui;

import java.awt.image.BufferedImage;

public class Item {
    int itemId;
    String Name;
    public int Amount = 0;
    BufferedImage Image;
    String Type = "Item";
    public int State[][];
    Item(int itemId, BufferedImage Image, String Name, int Amount) {
        this.itemId = itemId;
        this.Image = Image;
        this.Name = Name;
        this.Amount = Amount;
    }
}
