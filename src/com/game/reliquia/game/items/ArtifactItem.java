package com.game.reliquia.game.items;

import com.game.reliquia.engine.core.EngineAPI;
import com.game.reliquia.engine.core.Renderer;
import com.game.reliquia.game.states.GameState;
import com.game.reliquia.game.entities.GameObject;
import com.game.reliquia.game.entities.components.AABBComponent;
import com.game.reliquia.game.entities.player.Artifact;
import com.game.reliquia.game.entities.player.Player;

public class ArtifactItem extends Item {

    private final float FALL_SPEED = 10F;

    private float fallSpeed = FALL_SPEED;

    private Artifact artifact;

    public ArtifactItem(int posX, int posY, Artifact artifact) {
        this.namespace = "reliquia";
        this.identifier = "artifact_item";
        this.width = artifact.getImage().getWidth();
        this.height = artifact.getImage().getHeight();
        this.padding = 0;
        this.paddingTop = 0;
        this.posX = posX * GameState.TS;
        this.posY = posY * GameState.TS;
        this.tileX = posX;
        this.tileY = posY;
        this.offX = 0;
        this.offY = 0;

        this.artifact = artifact;
        this.itemImage = artifact.getImage();

        this.addComponent(new AABBComponent(this));
    }


    @Override
    public void update(EngineAPI api, GameState gs, float dt) {
        updatePhysics(api, gs, dt, fallSpeed);
        finalPositionCalculation();

        this.updateComponents(api, gs, dt);
    }

    @Override
    public void render(EngineAPI api, Renderer r) {
        r.drawImage(this.itemImage, (int)posX, (int)posY);
        this.renderComponents(api, r);
    }

    @Override
    public void collision(GameObject other) {
        if(other.getNamespacedID().equalsIgnoreCase("reliquia:player") && !isDead()) {
            this.setDead(true);
            Player p = (Player)other;
            p.getArtifacts().unlock(artifact.getNamespace() + ":" + artifact.getIdentifier());
        }
        collideWith("reliquia:tile", other);
    }
}