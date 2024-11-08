package entity;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import main.GamePanel;
import main.KeyHandler;

import static utils.Constants.PlayerConstants.*;

public class Npc extends Entity {

    GamePanel gp;
    KeyHandler keyH;
    boolean ToggleE = false;
    public int Day = 1;
    public int worldX = 2932;
    public int worldY = 2155;
    public Rectangle HRP;

    String pathFile = "entitys/npc";
    int[] column = {6, 8};
    int realSize = 200;
    int[] size = {96, 64, 15, 0, 30, 0};
    AnimationManager myAnimation = new AnimationManager(pathFile, column, size);

    // Property
    int rangeTrigger = 9;
    int CharacterAction = IDLE;
    int CharacterDirection = 0;
    boolean DisableMovement = true;
    double diagSpeed;

    public Npc(GamePanel gp, KeyHandler keyH) {
        this.gp = gp;
        this.keyH = keyH;
        this.speed = 4;
        this.diagSpeed = Math.sqrt(((speed * speed) / 2));

        myAnimation.setTick(0, 0, 8);
    }

    public void setPosition(int x, int y, int speed) {
        this.worldX = x;
        this.worldY = y;
        this.speed = speed;
        this.diagSpeed = Math.sqrt(((speed * speed) / 2));
    }

    public void update() {
        CharacterAction = IDLE;
        CharacterDirection = gp.player.worldX < worldX ? 0 : 1;
        myAnimation.updateAnimationTick(CharacterAction);

        if (ToggleE != keyH.getToggleState(KeyEvent.VK_E)) {
            ToggleE = keyH.getToggleState(KeyEvent.VK_E);
            if (!gp.UiManage.DialogUi.getShowMain()) {
                int diffX = Math.abs(gp.player.worldX - worldX);
                int diffY = Math.abs(gp.player.worldY - worldY);
                double distance = Math.sqrt(diffX + diffY);

                if (distance <= rangeTrigger) {
                    Thread NpcChat;
                    NpcChat = new Thread(() -> {
                        gp.UiManage.DialogUi.setShowMain(true);
                        String options[] = {"Buy", "Sell"};
                        gp.UiManage.DialogUi.setOption(options);
                        int value = gp.UiManage.DialogUi.Typewriter(
                                "Ah, just the person I wanted to see!, what’s it going to be? Buying supplies or selling your harvest?",
                                25,
                                true
                        );
                        if (options[value] == "Buy") {
                            String seeds[] = {
                                "Carrot Seed",
                                "Pumpkin Seed",
                                "Radish Seed",
                                "Potato Seed",
                                "Cabbage Seed",
                                "Exit"
                            };
                            gp.UiManage.DialogUi.setOption(seeds);
                            int seedIndex = gp.UiManage.DialogUi.Typewriter(
                                    "Got some fresh seeds in stock!",
                                    25,
                                    true
                            );
                            if (seeds[seedIndex] == "Exit") {
                                gp.UiManage.DialogUi.Typewriter(
                                        "Thank you for doing business! Good luck with your farm.",
                                        25,
                                        false
                                );
                            } else {
                                String Options[] = {
                                    "1 (10G)",
                                    "5 (50G)",
                                    "10 (100G)",
                                    "Exit"
                                };
                                int amount[] = {1, 5, 10};
                                gp.UiManage.DialogUi.setOption(Options);
                                value = gp.UiManage.DialogUi.Typewriter(
                                        "Great choice! How many would you like?",
                                        25,
                                        true
                                );
                                if (Options[value] == "Exit") {
                                    gp.UiManage.DialogUi.Typewriter(
                                            "Thank you for doing business! Good luck with your farm.",
                                            25,
                                            false
                                    );
                                } else {
                                    if (gp.player.getMoney() >= amount[value] * 10) {
                                        seedIndex += 5;
                                        gp.UiManage.MyBackpack.getItem(seedIndex).Amount += amount[value];
                                        gp.player.setMoney(gp.player.getMoney() - amount[value] * 10);
                                        gp.UiManage.DialogUi.Typewriter(
                                                "Thank you for doing business! Good luck with your farm.",
                                                25,
                                                false
                                        );
                                    } else {
                                        gp.UiManage.DialogUi.Typewriter(
                                                "Sorry, but it doesn’t look like you can afford that right now.",
                                                25,
                                                false
                                        );
                                    }
                                }
                            }
                        } else {
                            String Crops[] = {
                                "Carrot",
                                "Pumpkin",
                                "Radish",
                                "Potato",
                                "Cabbage",
                                "Exit"
                            };
                            gp.UiManage.DialogUi.setOption(Crops);
                            int seedIndex = gp.UiManage.DialogUi.Typewriter(
                                    "Looking to sell some of your fresh harvest? What do you have for me today?",
                                    25,
                                    true
                            );
                            if (Crops[value] == "Exit") {
                                gp.UiManage.DialogUi.Typewriter(
                                        "Thank you for doing business! Good luck with your farm.",
                                        25,
                                        false
                                );
                            } else {
                                String Options[] = {
                                    "1",
                                    "5",
                                    "10",
                                    "Exit"
                                };
                                int amount[] = {1, 5, 10};
                                gp.UiManage.DialogUi.setOption(Options);
                                value = gp.UiManage.DialogUi.Typewriter(
                                        "How many would you like to sell today?",
                                        25,
                                        true
                                );
                                if (Options[value] == "Exit") {
                                    gp.UiManage.DialogUi.Typewriter(
                                            "Thank you for doing business! Good luck with your farm.",
                                            25,
                                            false
                                    );
                                } else {
                                    if (gp.UiManage.MyBackpack.getItem(seedIndex).Amount >= amount[value]) {
                                        gp.UiManage.MyBackpack.getItem(seedIndex).Amount -= amount[value];
                                        gp.player.setMoney(gp.player.getMoney() + amount[value] * 20);
                                        gp.UiManage.DialogUi.Typewriter(
                                                "Thank you for doing business! Good luck with your farm.",
                                                25,
                                                false
                                        );
                                    } else {
                                        gp.UiManage.DialogUi.Typewriter(
                                                "It looks like you don’t have enough to sell. Please check your harvest again.",
                                                25,
                                                false
                                        );
                                    }
                                }
                            }
                        }
                        gp.UiManage.DialogUi.setShowMain(false);
                    });
                    NpcChat.start();
                }
            }
        }
    }

    public void draw(Graphics2D g2) {
//        System.out.println("X : "+worldX+" | Y : "+worldY);

        int StartScreenX = gp.player.worldX - gp.player.screenX;
        int StartScreenY = gp.player.worldY - gp.player.screenY;

        int EndScreenX = StartScreenX + gp.screenWidth;
        int EndScreenY = StartScreenX + gp.screenWidth;

        try {
            if (StartScreenX - realSize <= worldX && worldX <= EndScreenX) {
                if (StartScreenY - realSize <= worldY && worldY <= EndScreenY) {
                    g2.drawImage(
                            myAnimation.getFrame(
                                    CharacterAction,
                                    CharacterDirection
                            ),
                            worldX - StartScreenX,
                            worldY - StartScreenY,
                            realSize,
                            realSize,
                            null
                    );
                }
            }
        } catch (Exception e) {
//            e.printStackTrace();
        }
    }
}
