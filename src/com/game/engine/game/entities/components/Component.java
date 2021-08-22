package com.game.engine.game.entities.components;


import com.game.engine.engine.core.EngineAPI;
import com.game.engine.engine.core.Renderer;
import com.game.engine.game.states.GameState;
import com.game.engine.game.entities.GameObject;

public abstract class Component {

    protected String tag;

    public abstract void update(EngineAPI api, GameState gs, float dt);
    public abstract void render(EngineAPI api, Renderer r);
    public abstract void collision(GameObject other);

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
