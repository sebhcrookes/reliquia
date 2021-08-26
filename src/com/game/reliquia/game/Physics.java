package com.game.reliquia.game;

import com.game.reliquia.game.entities.components.AABBComponent;

import java.util.ArrayList;

public class Physics {

    private static ArrayList<AABBComponent> aabbList = new ArrayList<>();

    public static void addAABBComponent(AABBComponent aabb) {
        aabbList.add(aabb);
    }

    public static void update() {
        for(int i = 0; i < aabbList.size(); i++) {
            for(int j = i + 1; j < aabbList.size(); j++) {
                AABBComponent c0 = aabbList.get(i);
                AABBComponent c1 = aabbList.get(j);
                if(Math.abs(c0.getCenterX() - c1.getCenterX()) < c0.getHalfWidth() + c1.getHalfWidth()) {
                    if(Math.abs(c0.getCenterY() - c1.getCenterY()) < c0.getHalfHeight() + c1.getHalfHeight()) {
                        if(!c0.getParent().getUUID().equals(c1.getParent().getUUID())) {
                            c0.getParent().collision(c1.getParent());
                            c1.getParent().collision(c0.getParent());
                        }
                    }
                }

            }
        }

        aabbList.clear();
    }

    public static void clear() {
        aabbList.clear();
    }
}
