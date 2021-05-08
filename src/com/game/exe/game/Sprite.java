package com.game.exe.game;

import com.game.exe.engine.gfx.Image;

public class Sprite {

    //Default Keybinding: Ctrl+M+. expands folded code
    //Custom: Ctrl+\ expands folded code

    //To surround code with region: Highlight, then Ctrl+Alt+T

    //region Items
        //region Acid
            public Image acidImage = new Image("/assets/items/acidBottle.png");
        //endregion
        //region Heart Fragment
            public Image heartImage = new Image("/assets/items/heartFragment.png");
        //endregion
        //region Coin
            public Image coinImage = new Image("/assets/items/coin.png");
        //endregion
    //endregion
    //region UI
        public Image titleImage = new Image("/assets/misc/title.png");
        public Image selectedTile = new Image("/assets/ui/selectedTile.png");
        public Image backpack = new Image("/assets/ui/backpack.png");
    //endregion
    //region Particles
        public Image dustImage = new Image("/assets/particles/dust.png");
        public Image rainImage = new Image("/assets/particles/rain.png");
        public Image snowImage = new Image("/assets/particles/snow.png");
    //endregion

    public Sprite() {}
}
