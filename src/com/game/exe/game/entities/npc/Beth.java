package com.game.exe.game.entities.npc;

import com.game.exe.engine.GameContainer;
import com.game.exe.engine.Renderer;
import com.game.exe.engine.position.Vector2;
import com.game.exe.game.GameState;
import com.game.exe.game.entities.GameObject;
import com.game.exe.engine.gfx.Image;

public class Beth extends GameObject {

    private Image rightImage = new Image("/assets/npc/beth/right.png");
    private Image leftImage = new Image("/assets/npc/beth/left.png");
    private Image bethImage = rightImage;

    private Image chatLeft = new Image("/assets/ui/chat/left.png");
    private Image chatRight = new Image("/assets/ui/chat/right.png");

    private GameState gm;

    private boolean inConversation = false;
    private String output = "";
    private String resultantConversation;
    private int barY = 0;
    private int charPos = 0;
    private boolean conversationFinished = true;
    private int convCount = 0;

    public Beth(GameState gm, float posX, float posY) {
        this.gm = gm;
        this.tag = "beth";
        this.position = new Vector2((int)posX, (int)posY);
        this.tileX = (int)posX;
        this.tileY = (int)posY;
        this.setOffX(0);
        this.setOffY(0);

        resultantConversation = "Hello, how are you?";

        int speed = 100;
        this.physicsInit(this, speed);

    }

    @Override
    public void update(GameContainer gc, GameState gm, float dt) {

        this.physicsApply(this, gm, dt);

        if(gm.player.position.getPosX() <= this.position.getPosX()) {
            if(bethImage != leftImage) {
                bethImage = leftImage;
            }
        }else{
            if(bethImage != rightImage) {
                bethImage = rightImage;
            }
        }

        barY = gm.cinematicCount;

        if(gc.getInput().isButtonDown(gm.controls.mainClick)) {

            int mouseX = gc.getInput().getMouseX() + (int)gm.camera.getOffX();
            int mouseY = gc.getInput().getMouseY() + (int)gm.camera.getOffY();

            if(mouseY <= (int)this.position.getPosY() + bethImage.getWidth() && mouseY >= (int)this.position.getPosY()) {
                if(mouseX <= (int)this.position.getPosX() + bethImage.getHeight() && mouseX >= (int)this.position.getPosX()) {
                    gm.toggleBars();
                    if(!inConversation) {
                        inConversation = true;
                        conversationFinished = false;
                    }else{
                        inConversation = false;
                        charPos = 0;
                        convCount = 0;
                        output = "";
                        conversationFinished = true;
                    }
                }
            }
        }
    }

    @Override
    public void render(GameContainer gc, Renderer r) {
        r.drawImage(bethImage, (int)position.getPosX(), (int)position.getPosY());
        if(!conversationFinished) {
            convCount++;
            if (convCount % 1000 == 0) {
                try {
                    output += resultantConversation.charAt(charPos);
                    charPos++;
                } catch (Exception e) {
                    conversationFinished = true;
                }
            }
        }
        if(inConversation) {
            int textX = (int)position.getPosX();
            int textY = (int)position.getPosY() - 10;

            System.out.println(position.getPosX() + " : " + textX);
            System.out.println(position.getPosY() + " : " + textY);

            int offL = 1;
            int offR = 1;

            r.drawImage(chatLeft, textX - (r.textLength(output) / 2) + (bethImage.getWidth() / 2), textY);
            r.drawImage(chatRight, textX + (r.textLength(output) / 2) + (bethImage.getWidth() / 2) - chatRight.getWidth() / 2, textY);
            //r.drawFillRect(textX - (r.textLength(output) / 2) + (bethImage.getW() / 2) + (chatLeft.getW()), textY + 1, r.textLength(output) - chatRight.getW() * 2, chatLeft.getH() - 3, 0xff1dc689);

            r.drawText(output, textX - (r.textLength(output) / 2) + (bethImage.getWidth() / 2), textY, 0xffffffff);
        }
    }
}
