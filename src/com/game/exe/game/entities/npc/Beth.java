package com.game.exe.game.entities.npc;

import com.game.exe.engine.GameContainer;
import com.game.exe.engine.Renderer;
import com.game.exe.game.GameManager;
import com.game.exe.game.entities.GameObject;
import com.game.exe.game.entities.Physics;
import com.game.exe.engine.gfx.Image;

public class Beth extends GameObject {

    private Physics physics;

    private Image rightImage = new Image("/assets/npc/beth/right.png");
    private Image leftImage = new Image("/assets/npc/beth/left.png");
    private Image bethImage = rightImage;

    private Image chatLeft = new Image("/assets/ui/chat/left.png");
    private Image chatRight = new Image("/assets/ui/chat/right.png");


    private GameManager gm;

    private boolean inConversation = false;
    private String output = "";
    private String resultantConversation;
    private int barY = 0;
    private int charPos = 0;
    private boolean conversationFinished = true;
    private int convCount = 0;

    public Beth(GameManager gm, float posX, float posY) {
        this.gm = gm;
        physics = new Physics();
        this.tag = "beth";
        this.posX = posX;
        this.posY = posY;
        this.tileX = (int)posX;
        this.tileY = (int)posY;
        physics.setOffX(0);
        physics.setOffY(0);

        resultantConversation = "Hello, how are you?";

        int speed = 100;
        physics.init(speed);

    }

    @Override
    public void update(GameContainer gc, GameManager gm, float dt) {

        physics.apply(this, gm, dt);

        if(gm.player.posX <= this.posX) {
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

            if(mouseY <= (int)this.posY + bethImage.getW() && mouseY >= (int)this.posY) {
                if(mouseX <= (int)this.posX + bethImage.getH() && mouseX >= (int)this.posX) {
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
        r.drawImage(bethImage, (int)posX, (int)posY);
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
            int textX = (int)posX;
            int textY = (int)posY - 10;

            System.out.println(posX + " : " + textX);
            System.out.println(posY + " : " + textY);

            int offL = 1;
            int offR = 1;

            r.drawImage(chatLeft, textX - (r.textLength(output) / 2) + (bethImage.getW() / 2), textY);
            r.drawImage(chatRight, textX + (r.textLength(output) / 2) + (bethImage.getW() / 2) - chatRight.getW() / 2, textY);
            //r.drawFillRect(textX - (r.textLength(output) / 2) + (bethImage.getW() / 2) + (chatLeft.getW()), textY + 1, r.textLength(output) - chatRight.getW() * 2, chatLeft.getH() - 3, 0xff1dc689);

            r.drawText(output, textX - (r.textLength(output) / 2) + (bethImage.getW() / 2), textY, 0xffffffff);
        }
    }
}
