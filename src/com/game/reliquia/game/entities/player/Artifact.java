package com.game.reliquia.game.entities.player;

import com.game.reliquia.engine.gfx.Image;

public class Artifact {

    private String name;
    private String namespace;
    private String identifier;
    private String effector;
    private Image image;

    public Artifact() {}

    public Artifact(String name, String namespace, String identifier, String effector, Image image) {
        this.name = name;
        this.namespace = namespace;
        this.identifier = identifier;
        this.effector = effector;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getEffector() {
        return effector;
    }

    public void setEffector(String effector) {
        this.effector = effector;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
