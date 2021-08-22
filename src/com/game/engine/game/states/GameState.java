package com.game.engine.game.states;

import com.game.engine.engine.core.EngineAPI;
import com.game.engine.engine.core.Renderer;
import com.game.engine.engine.states.State;
import com.game.engine.game.Camera;
import com.game.engine.game.Physics;
import com.game.engine.game.Statistics;
import com.game.engine.game.blocks.Blocks;
import com.game.engine.game.entities.ObjectManager;
import com.game.engine.game.serialisation.LoadManager;
import com.game.engine.game.serialisation.SaveManager;
import com.game.engine.game.serialisation.levels.Level;
import com.game.engine.game.serialisation.levels.LevelManager;
import com.game.engine.game.weather.Weather;
import com.game.engine.game.weather.Wind;

import java.awt.event.MouseEvent;

public class GameState extends State {

    public static final int TS = 16;

    private Camera camera;

    private LevelManager levelManager;
    private ObjectManager objectManager = new ObjectManager();

    private Weather weather = new Weather(this);

    private SaveManager saveManager = new SaveManager(this);
    private LoadManager loadManager = new LoadManager(this);

    private Statistics statistics;

    @Override
    public void init(EngineAPI api) {

        this.levelManager = new LevelManager(api, this);

        Blocks.init(); // Initialise the blocks
        objectManager.initPlayer(-1, -1);
        load(api);

        camera = new Camera("game-exe:player");
    }

    private void load(EngineAPI api) {
        Object[] loadedObject = loadManager.load(api, this);

        if(loadedObject != null) {
            Object[] gameData = (Object[])loadedObject[1];
            loadManager.loadValues(this, loadedObject); // Load those values into the game
            levelManager.init((int)gameData[0]); // Load the level
        }else{
            levelManager.init(0);
            this.statistics = new Statistics(0);
        }
    }

    @Override
    public void update(EngineAPI api, float dt) {

        weather.tick(api, this, dt);

        if(api.getInput().isButton(MouseEvent.BUTTON1)) {
            Wind.power += 0.1;
        }
        if(api.getInput().isButton(MouseEvent.BUTTON3)) {
            Wind.power -= 0.1;
        }


        objectManager.update(api, this, dt);
        Physics.update();
        levelManager.update(api, this, dt);
        camera.update(api, this, dt);
    }

    @Override
    public void render(EngineAPI api, Renderer r) {
        getLevel().render(api, r);
        objectManager.render(api, r);
        camera.render(r);

        r.drawText("FPS: " + api.getFPS(), camera.getOffX(), camera.getOffY(), 0xFFFFFFFF);
    }

    @Override
    public void dispose(EngineAPI api) {
        saveManager.save(api, this);
        Physics.clear();
    }

    public ObjectManager getObjectManager() {
        return objectManager;
    }

    public LevelManager getLevelManager() {
        return levelManager;
    }

    public SaveManager getSaveManager() {
        return saveManager;
    }

    public LoadManager getLoadManager() {
        return loadManager;
    }

    public Level getLevel() {
        return levelManager.getLevel();
    }

    public float distanceBetween(float a, float b) {
        return a-b;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }
}
