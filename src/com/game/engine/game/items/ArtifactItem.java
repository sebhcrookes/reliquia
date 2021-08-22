package com.game.engine.game.items;

import com.game.engine.engine.core.EngineAPI;
import com.game.engine.engine.core.Renderer;
import com.game.engine.game.states.GameState;
import com.game.engine.game.entities.GameObject;
import com.game.engine.game.entities.components.AABBComponent;
import com.game.engine.game.entities.player.Artifact;
import com.game.engine.game.entities.player.Player;

public class ArtifactItem extends Item {

    private final float FALL_SPEED = 10F;

    private float fallSpeed = FALL_SPEED;

    private Artifact artifact;

    public ArtifactItem(int posX, int posY, Artifact artifact) {
        this.namespace = "game-exe";
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
        if(other.getNamespacedID().equalsIgnoreCase("game-exe:player") && !isDead()) {
            this.setDead(true);
            Player p = (Player)other;
            p.getArtifacts().unlock(artifact.getNamespace() + ":" + artifact.getIdentifier());
        }
        collideWith("game-exe:tile", other);
    }
}