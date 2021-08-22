package com.game.engine.game.entities;

import com.game.engine.engine.core.EngineAPI;
import com.game.engine.engine.core.Renderer;
import com.game.engine.game.states.GameState;
import com.game.engine.game.entities.player.Player;
import com.game.engine.game.items.EmptyItem;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class ObjectManager {

    private ArrayList<GameObject> objects = new ArrayList<>();

    private Player player;

    int UUIDOn = 0;

    public ObjectManager() {}

    public void initPlayer(int x, int y) {
        player = new Player(x, y);
        player.setUUID(Integer.toHexString(0).toUpperCase());
        getNextUUID();
    }

    public void addObject(GameObject object) {
        object.setUUID(Integer.toHexString(UUIDOn).toUpperCase());
        objects.add(object);
        getNextUUID();
    }

    public GameObject getObject(String namespaceIdentifier) {
        if(namespaceIdentifier.equals(player.getNamespacedID())) return player;
        for (GameObject object : objects) {
            if (object.getNamespacedID().equals(namespaceIdentifier)) {
                return object;
            }
        }
        return null;
    }

    @Deprecated
    private void packUUIDs() {
        try {
            int previous = 0;
            for(int i = 0; i < objects.size(); i++) {
                int expected = previous + 1;
                int actual = (int)(Long.parseLong(objects.get(i).getUUID(), 16));
                if(actual != expected) {
                    objects.get(i).setUUID(Integer.toHexString(expected).toUpperCase());
                }
                previous++;
            }
        } catch(Exception ignored) {}
    }

    public void update(EngineAPI api, GameState gs, float dt) {
        if(!player.isDead()) player.update(api, gs, dt);
        else {
            if(api.getInput().isKey(KeyEvent.VK_ENTER)) {
                player.setHealth(new Player(0, 0).getHealth());
                player.setDead(false);
                gs.getLevelManager().loadLevel(gs.getLevelManager().getLevelNumber());
            }
        }

        for(int i = 0; i < objects.size(); i++) {
            objects.get(i).update(api, gs, dt);
            if(objects.get(i).isDead() || objects.get(i).getHealth() <= 0) {
                UUIDOn = (int)Long.parseLong(objects.get(i).getUUID(), 16);
                objects.remove(i);
                i--;
            }
        }
    }

    public void render(EngineAPI api, Renderer r) {
        for(int i = 0; i < objects.size(); i++) {
            objects.get(i).render(api, r);
            if(api.getSettings().isDebug() && objects.get(i).showDebug()) {
                r.drawText(objects.get(i).getUUID(), (int)(objects.get(i).getPosX()), (int)(objects.get(i).getPosY() - r.getFont().getFontHeight() + 1), 0xFF000000);
            }
        }
        player.render(api, r);
        if(api.getSettings().isDebug()) {
            r.drawText(player.getUUID(), (int)(player.getPosX()), (int)(player.getPosY() - r.getFont().getFontHeight() + 1), 0xFF000000);
        }
    }

    public void getNextUUID() {
        int possibleTarget = UUIDOn + 1;

        infinite:
        while(true) {
            for (GameObject object : objects) {
                if (Long.parseLong(object.getUUID(), 16) == possibleTarget) {
                    possibleTarget++;
                    continue infinite;
                }
            }
            break;
        }
        UUIDOn = possibleTarget;
    }

    public GameObject getObjectAt(int x, int y) {
        for(int i = objects.size() - 1; i >= 0; i--) {
            GameObject current = objects.get(i);
            if(x > current.getPosX() && x < current.getPosX() + current.getWidth() && y > current.getPosY() && y < current.getPosY() + current.getHeight()) {
                return objects.get(i);
            }
        }

        if(x > player.getPosX() && x < player.getPosX() + player.getWidth() && y > player.getPosY() && y < player.getPosY() + player.getHeight()) {
            return player;
        }

        return new EmptyItem();
    }
}
