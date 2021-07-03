package com.game.exe.game;

import com.game.exe.engine.GameContainer;
import com.game.exe.engine.Renderer;
import com.game.exe.engine.gfx.Image;

import java.io.Serializable;

import java.awt.event.KeyEvent;
import java.util.Arrays;

public class UI implements Serializable{

    private String[] lines = new String[4];

    private boolean debugDisplay = false;
    private boolean menu = true;
    private boolean allowMove = true;

    private boolean initHasRun = false;

    private Sound sound = new Sound();
    private Image playerImage;

    private GameState gm;

    private int titleMaxY;
    private int menuY = 0;
    private int menuX;

    private boolean isChat = false;
    private String chatTyped = "";

    private int playerMaxY;
    private int menuPlayerX;
    private int menuPlayerY = 0;

    public int mouseTileX, mouseTileY;

    public UI(GameState gm) {
        this.gm = gm;
    }

    public void init(GameState gm, GameContainer gc) { if(initHasRun) { return; } else { initHasRun = true; }
        playerImage = new Image("/assets/player/" + gm.player.colour + "/0.png");
        titleMaxY = (gc.getHeight() / 2) - (gm.sprite.titleImage.getHeight() / 2);
        menuX = (gc.getWidth() / 2) - (gm.sprite.titleImage.getWidth() / 2);

        playerMaxY = (titleMaxY - (gm.sprite.titleImage.getHeight() / 2)) - playerImage.getHeight() / 2;
        menuPlayerX = (gc.getWidth() / 2) - (playerImage.getWidth() / 2);
    }

    public void update(GameState gm, GameContainer gc) {
        if(!initHasRun) { init(gm, gc); }

        try {
            gm.inventory.update(gm, gc);
        }catch(Exception e) {}


        //Menu
        if(allowMove) { menuY += 3; menuPlayerY += 3; }
        if(gc.getInput().isKeyDown(KeyEvent.VK_ESCAPE) && !menu) {
            menu = true;
            menuY = 0;
            menuPlayerY = 0;
        }
        if(gc.getInput().isKeyDown(KeyEvent.VK_ENTER) && menu) {
            menu = false;
        }

        //Change Colour
        //if(gc.getInput().isKeyDown(KeyEvent.VK_1) && menu) { gm.player.setColour("green"); updateMenuPlayer(gm); }
        //if(gc.getInput().isKeyDown(KeyEvent.VK_2) && menu) { gm.player.setColour("cyan"); updateMenuPlayer(gm); }
        //if(gc.getInput().isKeyDown(KeyEvent.VK_3) && menu) { gm.player.setColour("yellow"); updateMenuPlayer(gm); }
        //if(gc.getInput().isKeyDown(KeyEvent.VK_4) && menu) { gm.player.setColour("red"); updateMenuPlayer(gm); }

        //Toggle Debug Info
        if(gc.getInput().isKey(KeyEvent.VK_CONTROL)) {
            if(gc.getInput().isKeyDown(KeyEvent.VK_SHIFT)) {
                if(debugDisplay == true)
                    debugDisplay = false;
                else
                    debugDisplay = true;
            }
        }

        //Calculate UI positioning
        Arrays.fill(lines,null);

        if(debugDisplay) {
            lines[0] = "FPS: " + gc.getFps() + " ";
        }

        mouseTileX = (int)(gc.getInput().getMouseX()+ gm.camera.getOffX()) / GameState.TS;
        mouseTileY = (int)(gc.getInput().getMouseY()+ gm.camera.getOffY()) / GameState.TS;
    }

    public void render(Renderer r, GameContainer gc) {

        //TODO: Make UI render function less chunky

        //Render Selection Box
        if(gm.getCollisionDetails(mouseTileX, mouseTileY) != null) {
            r.drawImage(gm.sprite.selectedTile, mouseTileX * GameState.TS, mouseTileY * GameState.TS);
            //if(gc.getInput().isButton(MouseEvent.BUTTON1)){
                //gm.setBlock(mouseTileX, mouseTileY, "air");
            //}
        }

        //Render all UI fixed to screen
        r.setCamX(0);
        r.setCamY(0);

        if(isChat) {
            r.drawFillRect(0,0,100,8,0x40000000);
            r.drawText("> " + chatTyped, 0,0,0xffffffff);
        }

        for(int i = 0; i < lines.length; i++) {
            if(lines[i] != null) {
                r.drawText(lines[i], 0, (i * r.font.getHeight()), 0xffffffff);
            }
        }

        if(menu) {
            try {
                if (menuY >= titleMaxY) {
                    menuY = titleMaxY;
                }
                if (menuPlayerY >= playerMaxY) {
                    menuPlayerY = playerMaxY;
                }

                r.drawImage(playerImage, menuPlayerX, menuPlayerY);
                r.drawImage(gm.sprite.titleImage, menuX, menuY);
                allowMove = true;
            } catch(Exception e) {}
        }
        if(!menu) {
            if(menuY < gc.getHeight()) {
                r.drawImage(playerImage, menuPlayerX, menuPlayerY);
                r.drawImage(gm.sprite.titleImage, menuX, menuY);
                allowMove = true;
            }else
                allowMove = false;
        }
        try {
            gm.inventory.render(r, gc);
        }catch(Exception e) {}


    }

    private void updateMenuPlayer(GameState gm) {
        playerImage = new Image("/assets/player/" + gm.player.colour + "/0.png");
        sound.select.play();
    }
}
