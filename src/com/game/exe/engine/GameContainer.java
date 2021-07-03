package com.game.exe.engine;

import com.game.engine.engine.util.EngineSettings;
import com.game.exe.engine.util.Logger;
import com.game.exe.engine.util.State;
import com.game.exe.engine.Game;
import com.game.exe.engine.Input;
import com.game.exe.engine.Renderer;
import com.game.exe.engine.Window;

public class GameContainer implements Runnable {

    private Thread thread;
    private Renderer renderer;
    private Window window;
    private Input input;
    private Game game;
    private EngineSettings settings;
    private Logger logger = new Logger();

    public int clearColour = 0xFF000000;

    private boolean running = false;
    public int fps = 0;

    public GameContainer(Game game, EngineSettings settings) {
        this.game = game;
        this.settings = settings;
    }

    public void start() {
        window = new Window(this);
        renderer = new Renderer(this);
        input = new Input(this);
        //logger.init(settings.getTitle());

        thread = new Thread(this);
        thread.start();

        getWindow().getFrame().addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                stop();
            }
        });

    }

    public void stop() {
        this.running = false;
        game.dispose();
        game.getState().dispose();
    }

    public void run() {
        running = true;

        boolean render;
        double firstTime;
        double lastTime = System.nanoTime() / 1000000000.0;
        double passedTime;
        double unprocessedTime = 0;

        double frameTime = 0;
        int frames = 0;

        game.init();

        while(running) {
            render = !settings.isLockFPS(); // Change to uncap frame-rate

            firstTime = System.nanoTime() / 1000000000.0;
            passedTime = firstTime - lastTime;
            lastTime = firstTime;

            unprocessedTime += passedTime;
            frameTime += passedTime;

            while(unprocessedTime >= settings.getUpdateCap()) {
                unprocessedTime -= settings.getUpdateCap();
                render = true;

                if(frameTime >= 1.0) {
                    frameTime = 0;
                    fps = frames;
                    frames = 0;
                }

                game.getState().update(this,(float)settings.getUpdateCap());
                window.update();
                input.update();
            }

            if(render) {
                frames++;
                renderer.clear();
                game.getState().render(this, renderer);
                renderer.process();
            }else{
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int getWidth() {
        return settings.getWidth();
    }

    public void setWidth(int width) {
        settings.setWidth(width);
    }

    public int getHeight() {
        return settings.getHeight();
    }

    public void setHeight(int height) {
        settings.setHeight(height);
    }

    public float getScale() {
        return settings.getScale();
    }

    public String getTitle() {
        return settings.getTitle();
    }

    public void setTitle(String title) {
        settings.setTitle(title);
    }

    public Window getWindow() {
        return window;
    }

    public Input getInput() {
        return input;
    }

    public int getFps() {
        return fps;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public EngineSettings getSettings() {
        return settings;
    }

    public Game getGame() {
        return game;
    }
}
