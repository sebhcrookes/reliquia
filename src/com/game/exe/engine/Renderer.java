package com.game.exe.engine;

import com.game.exe.engine.GameContainer;
import com.game.exe.game.GameManager;
import com.game.exe.engine.gfx.Font;
import com.game.exe.engine.gfx.Image;
import com.game.exe.engine.gfx.ImageRequest;
import com.game.exe.engine.gfx.ImageTile;

import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Renderer {

    private ArrayList<ImageRequest> imageRequest = new ArrayList<ImageRequest>();

    private int pW, pH;
    private int[] p;
    private int[] zb;

    private float camX, camY;

    private int zDepth = 0;

    public Font font = Font.STANDARD;

    private boolean processing = false;

    private int TS;

    private GameContainer gc;
    private GameManager gm;

    public Renderer(GameContainer gc, GameManager gm) {
        this.gm = gm;
        this.TS = gm.TS;
        pW = gc.getWidth();
        pH = gc.getHeight();
        p = ((DataBufferInt)gc.getWindow().getImage().getRaster().getDataBuffer()).getData();
        zb = new int[p.length];

        this.gc = gc;
    }

    public void clear() {
        for (int i = 0; i < p.length; i++) {
            p[i] = gm.AIR_COLOUR;
        }
    }

    public void process() {
        processing = true;

        Collections.sort(imageRequest, new Comparator<ImageRequest>() {
            @Override
            public int compare(ImageRequest i0, ImageRequest i1) {
                if(i0.zDepth < i1.zDepth){
                    return -1;
                }
                if(i0.zDepth < i1.zDepth) {
                    return 1;
                }
                return 0;
            }
        });

        for(int i = 0; i < imageRequest.size(); i++) {
            ImageRequest iR = imageRequest.get(i);
            setzDepth(iR.zDepth);
            drawImage(iR.image, iR.offX, iR.offY);
        }

        imageRequest.clear();
        processing = false;
    }

    public void setPixel(int x, int y, int value) {

        int alpha = ((value >> 24) & 0xff);

        x -= camX;
        y -= camY;


        if((x < 0 || x >= pW || y < 0 || y >= pH) || alpha == 0) {
            return; //Don't render
        }

        int index = x + y * pW;

        if(zb[index] > zDepth) {
            return;
        }

        zb[index] = zDepth;

        if(alpha == 255)
            p[index] = value;
        else {
            int pixelColour = p[index];
            int newRed = ((pixelColour >> 16) & 0xff) - (int) ((((pixelColour >> 16) & 0xff) - ((value >> 16) & 0xff)) * (alpha / 255f));
            int newGreen = ((pixelColour >> 8) & 0xff) - (int) ((((pixelColour >> 8) & 0xff) - ((value >> 8) & 0xff)) * (alpha / 255f));
            int newBlue = (pixelColour & 0xff) - (int) (((pixelColour & 0xff) - (value & 0xff)) * (alpha / 255f));

            //255 << 24 | newRed...
            p[index] = (newRed << 16 | newGreen << 8 | newBlue);
        }

    }

    public void drawText(String text, int offX, int offY, int colour) {

        int offset = 0;

        text = text.toUpperCase();

        for(int i = 0; i < text.length(); i++) {
            int unicode = text.codePointAt(i) - 32;

            for(int y = 0; y < font.getFontImage().getH(); y++) {
                for(int x = 0; x < font.getWidths()[unicode]; x++) {
                    int pixelColour = font.getFontImage().getP()[x + font.getOffsets()[unicode] + y * font.getFontImage().getW()];
                    if(pixelColour != 0xffff00ff && pixelColour != 0xff0000ff && pixelColour != 0xffffff00) {
                        if(pixelColour == colour) {
                            setPixel(x + offX + offset, y + offY, colour);
                        }else{
                            setPixel(x + offX + offset, y + offY, pixelColour);
                        }
                    }
                }
            }
            offset += font.getWidths()[unicode];
        }
    }

    public void drawImage(Image image, int offX, int offY) {

        if(image.isAlpha() && !processing) {
            imageRequest.add(new ImageRequest(image, zDepth, offX, offY));
        }

        int newX = 0;
        int newY = 0;
        int newWidth = image.getW();
        int newHeight = image.getH();


        for(int y = newY; y < newHeight; y++) {
            for(int x = newX; x < newWidth; x++) {
                setPixel(x+offX,y+offY,image.getP()[x+y*image.getW()]);
            }
        }
    }

    public void drawImageTile(ImageTile image, int offX, int offY, int tileX, int tileY, GameManager gm) {
        int newX = 0;
        int newY = 0;
        int newWidth = image.getTileW();
        int newHeight = image.getTileH();


        //Clipping Code
        if(offX < 0) { newX -= offX; }
        if(offY < 0) { newY -= offY; }
        if(newWidth + offX > pW) { newWidth -= (newWidth + offX - pW); }
        if(newHeight + offY > pH) { newHeight -= (newHeight + offY - pH); }


        for(int y = newY; y < newHeight; y++) {
            for(int x = newX; x < newWidth; x++) {
                setPixel(x+offX,y + offY, image.getP()[(x + tileX * image.getTileW()) + (y + tileY * image.getH()) * image.getW()]);
            }
        }
    }

    public void drawRect(int offX, int offY, int width, int height, int colour) {

        for(int y = 0; y <= height; y++) {
            setPixel(offX, y + offY, colour);
            setPixel(offX + width, y + offY, colour);
        }

        for(int x = 0; x <= width; x++) {
            setPixel(offX + x, offY, colour);
            setPixel(offX + x, offY + height, colour);
        }
    }


    public void drawFillRect(int offX, int offY, int width, int height, int colour) {

        for (int y = 0; y <= height; y++) {
            for (int x = 0; x <= width; x++) {
                setPixel(x + offX, y + offY, colour);
            }
        }
    }

    //TODO: Fix drawline method
    public void drawLine(int x1, int y1, int x2, int y2, int colour)
    {
        int m_new = 2 * (y2 - y1);
        int slope_error_new = m_new - (x2 - x1);

        for (int x = x1, y = y1; x <= x2; x++)
        {
            setPixel(x, y, colour);

            // Add slope to increment angle formed
            slope_error_new += m_new;

            // Slope error reached limit, time to
            // increment y and update slope error.
            if (slope_error_new >= 0)
            {
                y++;
                slope_error_new -= 2 * (x2 - x1);
            }
        }
    }

    public int textLength(String text) {

        int offset = 0;

        text = text.toUpperCase();

        for(int i = 0; i < text.length(); i++) {
            int unicode = text.codePointAt(i) - 32;
            offset += font.getWidths()[unicode];
        }

        return offset;
    }

    public int getzDepth() {
        return zDepth;
    }

    public void setzDepth(int zDepth) {
        this.zDepth = zDepth;
    }

    public float getCamX() {
        return camX;
    }

    public void setCamX(float camX) {
        this.camX = camX;
    }

    public float getCamY() {
        return camY;
    }

    public void setCamY(float camY) {
        this.camY = camY;
    }
}
