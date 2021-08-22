package com.game.engine.game.entities;

import com.game.engine.engine.core.EngineAPI;
import com.game.engine.engine.core.Renderer;
import com.game.engine.engine.gfx.Colour;
import com.game.engine.engine.gfx.Image;
import com.game.engine.game.states.GameState;
import com.game.engine.game.entities.components.AABBComponent;

import java.awt.event.KeyEvent;

public class NPC extends GameObject {

    private boolean inConversation = false;
    private String[] text;

    private Image[] images = new Image[] {
            new Image("/objects/npc/0.png"),
            new Image("/objects/npc/1.png")
    };

    private int lineNumber = 0;

    private int boxColour = (int) (Math.random() * Integer.MAX_VALUE);

    public NPC(int posX, int posY, String text) {
        this.text = new String[] {text};
        init(posX, posY);
    }

    public NPC(int posX, int posY, String[] text) {
        this.text = text;
        init(posX, posY);
    }

    public void init(int posX, int posY) {
        this.namespace = "game-exe";
        this.identifier = "player";

        boxColour = Colour.getColour(255, Colour.getRed(boxColour), Colour.getGreen(boxColour), Colour.getBlue(boxColour));

        this.tileX = posX;
        this.tileY = posY;
        this.offX = 0;
        this.offY = 0;
        this.facing = 0;

        this.posX = posX * GameState.TS;
        this.posY = posY * GameState.TS;
        this.width = 11;
        this.height = 16;

        this.health = Health.INFINITE;

        // Padding initialisation
        padding = 0;
        paddingTop = 0;

        this.addComponent(new AABBComponent(this));
    }

    private int boundingBoxSize = 50;

    @Override
    public void update(EngineAPI api, GameState gs, float dt) {
        this.updatePhysics(api, gs, dt, 10);

        this.finalPositionCalculation();
        this.updateComponents(api, gs, dt);

        int playerPosX = (int) gs.getObjectManager().getObject("game-exe:player").getPosX();
        int playerPosY = (int) gs.getObjectManager().getObject("game-exe:player").getPosY();

        int playerWidth = (int) gs.getObjectManager().getObject("game-exe:player").getWidth();
        int playerHeight = (int) gs.getObjectManager().getObject("game-exe:player").getHeight();

        int boxPosX = (int) posX + (width / 2);
        int boxPosY = (int) posY + (height / 2);

        if(playerPosX < posX) {
            this.facing = 1;
        }else{
            this.facing = 0;
        }

        if(inConversation) {
            if(api.getInput().isKeyDown(KeyEvent.VK_ENTER)) { // Cycle through the conversation when the user presses enter
                if(lineNumber < text.length - 1) {
                    lineNumber++;
                } else {
                    lineNumber = 0;
                }
            }
        }

        // Check whether the player is in the bounding box or not
        inConversation = playerPosX > boxPosX - boundingBoxSize - playerWidth && playerPosX < boxPosX + boundingBoxSize && playerPosY < boxPosY + boundingBoxSize && playerPosY > boxPosY - boundingBoxSize - playerHeight;
    }

    @Override
    public void render(EngineAPI api, Renderer r) {
        this.renderComponents(api, r);
        r.drawImage(images[facing], (int)posX, (int)posY);

        if(inConversation) {

            // If in a conversation, calculate the position of the text
            int textX = (int)posX - (r.getFont().getTextLength(text[lineNumber]) / 2) + (width / 2);
            int textY = (int)posY - r.getFont().getHeight() - 5;

            // Then draw the translucent box and the text
            r.drawFillRect(textX - 1, textY - 1, r.getFont().getTextLength(text[lineNumber]) + 2, r.getFont().getHeight() + 1, 0x561DC689);
            r.drawText(text[lineNumber], textX, textY, 0xFFFFFFFF);
        }

        if(api.getSettings().isDebug()) {
            int boxPosX = (int) posX + (width / 2);
            int boxPosY = (int) posY + (height / 2);

            r.drawRect(boxPosX - boundingBoxSize,
                    boxPosY - boundingBoxSize,
                    boundingBoxSize * 2,
                    boundingBoxSize * 2,
                    boxColour);
        }
    }

    @Override
    public void collision(GameObject other) {
        this.collideWith("game-exe:tile", other);
    }
}
