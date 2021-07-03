package com.game.exe.game;

import com.game.exe.engine.AbstractGame;
import com.game.exe.engine.GameContainer;
import com.game.exe.engine.Renderer;
import com.game.exe.engine.util.State;
import com.game.exe.game.background.Backgrounds;
import com.game.exe.game.blocks.*;
import com.game.exe.game.entities.EntityManager;
import com.game.exe.game.entities.GameObject;
import com.game.exe.game.entities.Player;
import com.game.exe.game.level.LevelManager;
import com.game.exe.game.particles.ParticleManager;
import com.game.exe.game.serialisation.SerialisationManager;
import com.game.exe.game.ui.UIManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class GameState extends State {

    public static final int TS = 16;
    public static final int AIR_COLOUR = 0xff92f4ff;

    public GameContainer gc;

    public ArrayList<GameObject> objects = new ArrayList<GameObject>();
    private String[] collision;

    public Sprite sprite = new Sprite();
    public Player player;
    public Camera camera = new Camera(this,"player");
    public Inventory inventory;
    public UI ui = new UI(this);
    public Blocks blocks = new Blocks();
    public EntityManager em;
    public Random random = new Random();
    public BlockUpdate updater = new BlockUpdate();
    public Backgrounds backgrounds = new Backgrounds(this);
    public ParticleManager pm = new ParticleManager(this);

    public UIManager um;
    public LevelManager lm;
    public SerialisationManager sm;
    public Controls controls;

    //public int levelNumber = 0;
    public String mapBasePath = "/assets/maps/";
    public int spawnX, spawnY;

    public int sizeX = 512; //500
    public int sizeY = 288; //375

    public boolean trees = true;

    private boolean cinematicMode = false;
    public int cinematicCount = 0;
    private int cinematicMax = 20;

    public boolean loadingSucceeded = false;

    public GameState() {

        lm = new LevelManager(gc,this);
        sm = new SerialisationManager(gc, this);

        controls = new Controls(gc, this);
        um = new UIManager(gc, this);

        em = new EntityManager(this);
        inventory = new Inventory(this);
        blocks.initialise();

        try{
            sm.createFolders();
            sm.loadGame();
        } catch (Exception ignored) {}

        lm.getLevelLoader().load(lm.getLevelNumber());

        if(!loadingSucceeded) {
            player = new Player(spawnX, spawnY);
        }
        objects.add(player);
    }

    @Override
    public void init() {}


    @Override
    public void update(GameContainer gc, float dt) {

        //This is the update function
        ui.update(this,gc);
        updater.update(gc,this,dt);
        pm.update(gc,this,dt);
        lm.update(gc,this,dt);
        um.update(gc,this,dt);

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
        controls.update(gc, this, dt);
        camera.update(gc, this, dt);
    }

    @Override
    public void render(GameContainer gc, Renderer r) {

        camera.render(r);
        backgrounds.render(gc, r);
        pm.render(gc, r);

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
        controls.render(gc,r);
        ui.render(r, gc);
        um.render(gc, r);
    }

    @Override
    public void dispose() {
        save();
    }

    public void save() {
        try {
            sm.saveGame();
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
                    return blocks.blockList.get(i).doesCollide;
                }
            }
        }
        return false;
    }

    public boolean getParticleCollision(int x, int y) {
        if (x < 0 || x >= lm.getLevelW() || y < 0 || y >= lm.getLevelH())
            return false;
        int blockToTest = x + y * lm.getLevelW();
        if (collision[blockToTest] != getBlockIDFromNumber(0)) {
            for (int i = 0; i < blocks.blockList.size(); i++) {
                if (blocks.blockList.get(i).blockID == collision[blockToTest]) {
                    return blocks.blockList.get(i).doesCollide;
                }
            }
        }
        return false;
    }

    public String getCollisionDetails(int x, int y) {
        int blockToTest = x + y * lm.getLevelW();
        try {
            if (collision[blockToTest] != getBlockIDFromNumber(0)) {
                return collision[blockToTest];
            }
        } catch(ArrayIndexOutOfBoundsException e) {}
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
        try {
            return this.collision[position];
        }catch(Exception e) { return "air"; }
    }

    public float distanceBetween(float a, float b) {
        return a-b;
    }

    public int getLevelW() {
        return lm.getLevelW();
    }

    public int getLevelH() {
        return lm.getLevelH();
    }
}