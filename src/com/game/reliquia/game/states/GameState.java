package com.game.reliquia.game.states;

import com.game.reliquia.engine.core.EngineAPI;
import com.game.reliquia.engine.core.Renderer;
import com.game.reliquia.engine.states.State;
import com.game.reliquia.game.Camera;
import com.game.reliquia.game.Physics;
import com.game.reliquia.game.Statistics;
import com.game.reliquia.game.blocks.Blocks;
import com.game.reliquia.game.entities.ObjectManager;
import com.game.reliquia.game.serialisation.LoadManager;
import com.game.reliquia.game.serialisation.SaveManager;
import com.game.reliquia.game.serialisation.levels.Level;
import com.game.reliquia.game.serialisation.levels.LevelManager;
import com.game.reliquia.game.time.TimeManager;
import com.game.reliquia.game.weather.Weather;
import com.game.reliquia.game.weather.Wind;

import java.awt.event.MouseEvent;

public class GameState extends State {

    public static final int TS = 16;

    private Camera camera;

    private LevelManager levelManager;
    private ObjectManager objectManager = new ObjectManager();

    private Weather weather = new Weather(this);
    private TimeManager timeManager;

    private SaveManager saveManager = new SaveManager(this);
    private LoadManager loadManager = new LoadManager(this);

    private Statistics statistics;

    @Override
    public void init(EngineAPI api) {

        camera = new Camera("reliquia:player");

        this.levelManager = new LevelManager(api, this);

        Blocks.init(); // Initialise the blocks
        objectManager.initPlayer(-1, -1);
        load(api);
        this.timeManager = new TimeManager(this);
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
        timeManager.update(api, this, dt);

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

    public Camera getCamera() {
        return camera;
    }
}
