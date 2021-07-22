package com.game.exe.game;

import com.game.exe.engine.GameContainer;
import com.game.exe.engine.Renderer;
import com.game.exe.engine.gfx.Image;
import com.game.exe.engine.gui.GUIButton;
import com.game.exe.engine.position.Vector2;
import com.game.exe.engine.util.Logger;
import com.game.exe.engine.util.State;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class MenuState extends State {

    GUIButton startBtn;
    GUIButton optionsBtn;
    private static boolean start = false;
    private int trig = 0;

    private int fadeColour = 0xFF000000;

    private int titleMaxY;
    private Vector2 menuPosition = new Vector2(0,0);

    public Image titleImage = new Image("/assets/misc/title.png");
    private Image treeImage = new Image("/assets/blocks/environment/tree/tree.png");
    private Image grassImage = new Image("/assets/blocks/testgrass.png");

    @Override
    public void init() {
        startBtn = new GUIButton();
        startBtn.setText("Play");
        startBtn.setBounds(new Vector2(0, 0), 0, 0);
        startBtn.pack();
        startBtn.setClickConsumer(MenuState::startGame);
        startBtn.setBorderColour(0xFF14A670);
        startBtn.setMainColour(0xFF1DC689);
        startBtn.setTextColour(0xFF29f3aa);

        optionsBtn = new GUIButton();
        optionsBtn.setText("Options ");
        optionsBtn.setBounds(new Vector2(0,0), 0,0);
        optionsBtn.pack();
        optionsBtn.setClickConsumer(MenuState::onOptionsClicked);
        optionsBtn.setBorderColour(0xFF14A670);
        optionsBtn.setMainColour(0xFF1DC689);
        optionsBtn.setTextColour(0xFF29f3aa);

        try{
            FileInputStream fi = new FileInputStream(new File(".game.exe/save.save"));
            ObjectInputStream oi = new ObjectInputStream(fi);
            Object[] saveData = (Object[]) oi.readObject();

            Object[] inventory = (Object[]) saveData[1];

            String[] items = (String[]) inventory[0];
            int[] itemCount = (int[]) inventory[1];

            for(int i = 0; i < items.length; i++) {
                System.out.println(items[i] + " : " + itemCount[i]);
            }


        } catch(Exception ignored) {}
    }

    @Override
    public void update(GameContainer gc, float dt) {

        int alpha = ((fadeColour >> 24) & 0xFF);

        if(alpha > 0)
            alpha -= 3;
        if(alpha < 0)
            alpha = 0;

        Color rgba = new Color((fadeColour >> 16) & 0xff, (fadeColour >> 8) & 0xff, (fadeColour) & 0xff, alpha);
        fadeColour = rgba.getRGB();

        if(trig < 2) {
            menuPosition = new Vector2((int)((gc.getWidth() / 2) - (titleImage.getWidth() / 2)), -titleImage.getHeight());
            startBtn.setBounds(new Vector2((int)((gc.getWidth() / 2) - startBtn.width / 2), menuPosition.getPosY()), 0, 0);
            startBtn.pack();
            optionsBtn.position.setPosX((gc.getWidth() / 2) - optionsBtn.width / 2);
            titleMaxY = (int)(gc.getHeight() / 2.25);
            trig++;
        }

        if(menuPosition.getPosY() < titleMaxY) {
            menuPosition.incrementPosY(2);
            startBtn.position.incrementPosY(3);
            optionsBtn.position.setPosY(startBtn.position.getPosY() + 13);
        }

        if(start)
            gc.getGame().setState(new GameState());
        startBtn.update(gc, dt);
        optionsBtn.update(gc, dt);
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawImage(treeImage, treeImage.getWidth(), gc.getHeight() - treeImage.getHeight() - 16);
        r.drawImage(treeImage, gc.getWidth() - treeImage.getWidth() * 2, gc.getHeight() - treeImage.getHeight() - 16);
        for(int i = 0; i < gc.getWidth() / 16; i++) {
            r.drawImage(grassImage, i * 16, gc.getHeight() - 16);
        }
        startBtn.render(gc, r);
        optionsBtn.render(gc, r);
        r.drawImage(titleImage, menuPosition.getPosX(), menuPosition.getPosY());
        r.drawFillRect(0, 0, gc.getWidth(), gc.getHeight(), fadeColour);
    }

    @Override
    public void dispose() {}

    public static void startGame(Object o) {
        Logger.log(Logger.INFO, "Starting game...");
        start = true;
    }

    public static void onOptionsClicked(Object o) {
        Logger.log(Logger.ERROR, "Options menu not implemented");
    }
}
