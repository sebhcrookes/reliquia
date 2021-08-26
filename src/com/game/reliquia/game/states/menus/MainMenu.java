package com.game.reliquia.game.states.menus;

import com.game.reliquia.engine.audio.AudioClip;
import com.game.reliquia.engine.core.EngineAPI;
import com.game.reliquia.engine.core.Renderer;
import com.game.reliquia.engine.gfx.Image;
import com.game.reliquia.engine.states.State;
import com.game.reliquia.engine.util.terminal.Console;
import com.game.reliquia.game.states.GameState;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class MainMenu extends Menu {

    Image title = new Image("/gui/title.png");

    ArrayList<ArrayList<Object>> options = new ArrayList<>();
    int selected = 0;

    @Override
    public void init(EngineAPI api) {

        api.setClearColour(0xFF18191A);

        Console.println("<cyan>Reliquia - <reset>Started game!");

        options.add(new ArrayList<Object>() {
            { add("Start game"); add(new GameState()); }});
        options.add(new ArrayList<Object>() {
            { add("How to play"); add(".temp"); }}); // TODO: Add a how to play menu
        options.add(new ArrayList<Object>() {
            { add("Exit game"); add(".exit"); }});
    }

    @Override
    public void update(EngineAPI api, MainMenuState mms, float dt) {

        if(api.getInput().isKeyDown(KeyEvent.VK_ENTER) || api.getInput().isKeyDown(KeyEvent.VK_SPACE)) {
            if(options.get(selected).get(1) instanceof State) {
                api.getGame().setState((State)options.get(selected).get(1));
            } else if(options.get(selected).get(1) instanceof Menu) {
                mms.changeMenu((Menu)options.get(selected).get(1));
            } else if(options.get(selected).get(1) instanceof String) {
                String value = String.valueOf(options.get(selected).get(1));
                if(value.equals(".exit")) {
                    System.exit(0);
                } else if(value.equals(".temp")) {}
            } else {
                Console.println("<orange>Main Menu - <reset>Invalid menu item '" + options.get(selected).get(0) + "'");
            }
        }

        if(api.getInput().isKeyDown(KeyEvent.VK_UP)) {
            if (selected > 0) { selected--; AudioClip.getSound("select").play(); }
            else AudioClip.getSound("invalid").play();

        } else if(api.getInput().isKeyDown(KeyEvent.VK_DOWN)) {
            if(selected < options.size() - 1) { selected++; AudioClip.getSound("select").play(); }
            else AudioClip.getSound("invalid").play();
        }
    }

    @Override
    public void render(EngineAPI api, Renderer r) {

        int titlePosY = 50;

        int offY = 30;
        int rectBorder = 8;

        r.drawRect((api.getWidth() / 2) - (title.getWidth() / 2) - rectBorder,
                (titlePosY + offY) - rectBorder,
                title.getWidth() + (rectBorder * 2) - 1,
                (title.getHeight() + (rectBorder * 2) - 1) + options.size() * r.getFont().getFontHeight() + (options.size() * 5) + 20,
                0xFF515151); // Box around menu items

        r.drawImage(title, (api.getWidth() / 2) - (title.getWidth() / 2), titlePosY + offY);

        for(int i = 0; i < options.size(); i++) {
            String text = String.valueOf(options.get(i).get(0));
            int colour = 0xFFACACAC;
            if(i == selected) {
                colour = 0xFFA3ECFA;
                text = "> " + text;
                text += " <";
            }

            r.drawText(text, (api.getWidth() / 2) - (r.getFont().getTextLength(text) / 2), (titlePosY + title.getHeight() + 20) + (i * r.getFont().getFontHeight()) + (i * 5) + offY, colour);
        }

        r.drawRect(1, 1, api.getWidth() - 3, api.getHeight() - 3, 0xFF515151);
    }

    @Override
    public void dispose() {}
}
