package entity;

import java.awt.image.BufferedImage;

import Maps.TileManager;

public class AnimationManager {
    // Variable
    int[] actionSize;
    int[] actionIndex;
    String[] actions = {"idle", "run"};
    BufferedImage[][][] animation;
    int aniTick, aniIndex, aniSpeed = 5;
    
    public AnimationManager(String pathFile, int[] column,int[] size) {
        this.actionSize = size;
        this.actionIndex = column;
        this.animation = new BufferedImage[actions.length][2][];
        for (int i=0; i<actions.length; i++) {
            TileManager Tile = new TileManager(String.format("/%s/%s.png", pathFile, actions[i]), this.actionSize, true, false);
            this.animation[i][0] = Tile.TileSetFlip[0];
            this.animation[i][1] = Tile.TileSet[0];
        }
    }
    
    public void setTick(int aniTick, int aniIndex, int aniSpeed) {
        this.aniTick = aniTick;
        this.aniIndex = aniIndex;
        this.aniSpeed = aniSpeed;
    }
    
    public void updateAnimationTick(int CharacterAction) {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= actionIndex[CharacterAction]) {
                aniIndex = 0;
            }
        }
    }
    
    public BufferedImage getFrame(int CharacterAction, int CharacterDirection) {
//        System.out.println("CharacterAction: " + CharacterAction + ", aniIndex: " + aniIndex + ", CharacterDirection: " + CharacterDirection);
        return this.animation[CharacterAction][CharacterDirection][aniIndex];
    }
}
