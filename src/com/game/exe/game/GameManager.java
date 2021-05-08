package com.game.exe.game;

import com.game.exe.engine.AbstractGame;
import com.game.exe.engine.GameContainer;
import com.game.exe.engine.Renderer;
import com.game.exe.engine.audio.SoundManager;
import com.game.exe.engine.util.PropertiesFile;
import com.game.exe.game.background.Backgrounds;
import com.game.exe.game.blocks.*;
import com.game.exe.game.entities.Entities;
import com.game.exe.game.entities.GameObject;
import com.game.exe.game.entities.Player;
import com.game.exe.game.level.LevelManager;
import com.game.exe.game.particles.Particles;
import com.game.exe.engine.gfx.Image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class GameManager extends AbstractGame implements Serializable {

    public static final int TS = 16;
    public static final int AIR_COLOUR = 0xff92f4ff;
    private String[] collision;

    public ArrayList<GameObject> objects = new ArrayList<GameObject>();

    public GameContainer gc;

    public Sprite sprite = new Sprite();
    public Serialiser serialiser = new Serialiser(this);
    public Player player;
    public Camera camera = new Camera(this,"player");
    public Inventory inventory;
    public UI ui = new UI(this);
    public Blocks blocks = new Blocks();
    public Entities entities;
    public Random random = new Random();
    public BlockUpdate updater = new BlockUpdate();
    public Controls controls = new Controls();
    public Backgrounds backgrounds = new Backgrounds(this);
    public Particles particles = new Particles(this);
    public SoundManager sm = new SoundManager();
    public LevelManager lm = new LevelManager(this.gc, this);

    public int levelAmount = 2;
    public int levelNumber = 0;
    public int spawnX, spawnY;

    public int sizeX = 512; //500
    public int sizeY = 288; //375

    public boolean trees = true;

    private boolean cinematicMode = false;
    public int cinematicCount = 0;
    private int cinematicMax = 20;


    public GameManager(){
        sm.init();
        sm.createSound("playerJump", "/assets/sfx/jump.wav");
        sm.createSound("playerDash", "/assets/sfx/dash.wav");
        sm.createSound("bottleSmash", "/assets/sfx/bottlesmash.wav");

        boolean loadingSucceeded = false;
        entities = new Entities(this);

        //Load the game
        try{
            String[] playerData = serialiser.loadPlayer(this);
            player = new Player(Float.parseFloat(playerData[0]),Float.parseFloat(playerData[1]));
            player.tileX = Integer.parseInt(playerData[2]);
            player.tileY = Integer.parseInt(playerData[3]);
            player.setOffX(Float.parseFloat(playerData[4]));
            player.setOffY(Float.parseFloat(playerData[5]));
            this.levelNumber = Integer.parseInt(playerData[6]);
            player.colour = playerData[7]; player.setColour(playerData[7]);
            loadingSucceeded = true;

        } catch (Exception e) {
            player = new Player((int) spawnX, (int) spawnY);
        }

        inventory = new Inventory(this);
        try{
            serialiser.loadInventory(this);
        }catch(Exception e) {}

        objects.add(player);
        blocks.initialise();
        entities.summonMob(this,"beth", player.tileX, player.tileY);
        lm.getLevelLoader().load(this.levelNumber);

        if(!loadingSucceeded) {
            player.setLocation(spawnX, spawnY);
        }
    }

    @Override
    public void init(GameContainer gc) {
        gc.window.getFrame().addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                save();
                gc.stop();
            }
        });
    }


    @Override
    public void update(GameContainer gc, float dt) {

        ui.update(this,gc);
        updater.update(gc,this,dt);
        particles.update(gc, this, dt);
        lm.update(gc,dt);

        if(gc.getInput().isKeyDown(KeyEvent.VK_F)) {
            toggleBars();
        }
        if(cinematicMode) {
            if(cinematicCount < cinematicMax) {
                cinematicCount++;
            }
        }else{
            if(cinematicCount > 0) {
                cinematicCount--;
            }
        }

        for(int i = 0; i < objects.size(); i++)
        {
            objects.get(i).update(gc,this,dt);
            if(objects.get(i).isDead())
            {
                objects.remove(i);
                i--;
            }
        }
        camera.update(gc, this, dt);
    }

    @Override
    public void render(GameContainer gc, Renderer r) {

        camera.render(r);
        backgrounds.render(gc, r);
        particles.render(gc, r);

        for(int y = 0; y < lm.getLevelH(); y++) {
            for (int x = 0; x < lm.getLevelW(); x++) {

                //Checking if block is off screen
                if (x * TS > gc.getWidth() + camera.getOffX()) continue;
                if (y * TS > gc.getHeight() + camera.getOffY()) continue;
                if (x * TS < -TS + camera.getOffX()) continue;
                if (y * TS < -TS + camera.getOffY()) continue;

                int pos = x + y * lm.getLevelW();
                for (int i = 0; i < blocks.blockList.size(); i++) {
                    if (collision[pos] == blocks.blockList.get(i).blockID && blocks.blockList.get(i).blockID != "air") {

                        //Draw the image if checks return false
                        r.drawImage(blocks.blockList.get(i).sprite, x * TS, y * TS);

                    }
                }
            }
        }
        for(GameObject obj : objects) {
            obj.render(gc, r);
        }
        if(cinematicCount != 0) {
            r.drawFillRect((int)r.getCamX(),(int)r.getCamY(), gc.getWidth(), cinematicCount, 0xff000000);
            r.drawFillRect((int)r.getCamX(),(int)r.getCamY() + gc.getHeight() - cinematicCount, gc.getWidth(), cinematicCount, 0xff000000);
        }
        ui.render(r, gc);
    }

    public void save() {
        try {
            serialiser.createDirectories();
            serialiser.savePlayer(this);
            serialiser.saveInventory(this);
        }catch (Exception e) {
            System.out.println("ERROR: Could not save game.");
        }
    }

    public void addObject(GameObject object) {
        objects.add(object);
    }

    public GameObject getObject(String tag) {
        for(int i = 0; i < objects.size(); i++) {
            if(objects.get(i).getTag().equals(tag)) {
                return objects.get(i);
            }
        }
        return null;
    }

    public boolean getCollision(int x, int y) {
        if (x < 0 || x >= lm.getLevelW() || y < 0 || y >= lm.getLevelH())
            return true;
        int blockToTest = x + y * lm.getLevelW();
        if (collision[blockToTest] != getBlockIDFromNumber(0)) {
            for (int i = 0; i < blocks.blockList.size(); i++) {
                if (blocks.blockList.get(i).blockID == collision[blockToTest]) {
                    if (blocks.blockList.get(i).doesCollide == true) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }

    public String getCollisionDetails(int x, int y) {
        int blockToTest = x + y * lm.getLevelW();
        try {
            if (collision[blockToTest] != getBlockIDFromNumber(0)) {
                for (int i = 0; i < blocks.blockList.size(); i++) {
                    return collision[blockToTest];
                }
            }
        } catch(ArrayIndexOutOfBoundsException e) {
            this.levelNumber = 0;
        }
        return null;
    }

    public void setBlock(int x, int y, String blockType) {
        int blockToSet = x + y * lm.getLevelW();
        try {
            if (collision[blockToSet] != getBlockIDFromNumber(0)) {
                for (int i = 0; i < blocks.blockList.size(); i++) {
                    collision[blockToSet] = blockType;
                }
            }
        } catch(ArrayIndexOutOfBoundsException e) {
            return;
        }
    }

    public void startGame() throws IOException {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        float scaledWindow = 2;

        gc = new GameContainer(this);
        gc.start(this);
    }

    public String getBlockIDFromNumber(int relativeNumber) {
        for(int i = 0; i < blocks.blockList.size(); i++) {
            if(blocks.blockList.get(i).relativeNumber == relativeNumber) {
                return blocks.blockList.get(i).blockID;
            }
        }
        return null;
    }

    public int getBlockNumberFromID(String ID) {
        for(int i = 0; i < blocks.blockList.size(); i++) {
            if(blocks.blockList.get(i).blockID == ID) {
                return blocks.blockList.get(i).relativeNumber;
            }
        }
        return 0;
    }

    public boolean getCollisionFromID(String blockID) {
        if(blockID == null) { return true; }
        try {
            for (int i = 0; i < blocks.blockList.size(); i++) {
                if (blocks.blockList.get(i).blockID == blockID) {
                    return blocks.blockList.get(i).doesCollide;
                }
            }
        } catch(Exception e) {}
        return true;
    }

    public void toggleBars() {
        if(cinematicMode) {
            cinematicMode = false;
        }else {
            cinematicMode = true;
        }
    }

    public float distanceBetween(float a, float b) {
        return a-b;
    }

    public int getlevelW() {
        return lm.getLevelW();
    }

    public int getLevelH() {
        return lm.getLevelH();
    }

    public String[] getCollision() {
        return this.collision;
    }

    public void setCollision(String[] collision) {
        this.collision = collision;
    }

    public void setCollisionValue(String value, int position) {
        this.collision[position] = value;
    }

    public String getCollisionValue(int position) {
        return this.collision[position];
    }

    public void setScale(int scale) {
        gc.setScale(scale);
    }

    public LevelManager getLevelManager() { return lm; }
}