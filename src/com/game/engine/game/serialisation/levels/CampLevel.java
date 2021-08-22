package com.game.engine.game.serialisation.levels;

import com.game.engine.engine.core.EngineAPI;
import com.game.engine.game.states.GameState;
import com.game.engine.game.blocks.TreeParticleEmitter;
import com.game.engine.game.entities.NPC;
import com.game.engine.game.items.ArtifactItem;
import com.game.engine.game.entities.player.Player;
import com.game.engine.game.items.BambooSword;

public class CampLevel extends Level {

    public CampLevel() {
        super("The Camp");
    }

    @Override
    public void onLoad(EngineAPI api, GameState gs) {
        Player p = (Player)gs.getObjectManager().getObject("game-exe:player");

        if(!p.getArtifacts().isUnlocked("game-exe:dash_book")) {
            gs.getObjectManager().addObject(new ArtifactItem(15, 24, p.getArtifacts().get("game-exe:dash_book")));
        }
        gs.getObjectManager().addObject(new BambooSword(15, 24));

        String[] npcConversation = new String[] {
                "Welcome to Heiholmeia!",
                "Take this book, you'll need it on your travels"
        };

        gs.getObjectManager().addObject(new NPC(11, 24, npcConversation));

        gs.getObjectManager().addObject(new TreeParticleEmitter(17, 19));
        gs.getObjectManager().addObject(new TreeParticleEmitter(17, 18));
        gs.getObjectManager().addObject(new TreeParticleEmitter(16, 19));
        gs.getObjectManager().addObject(new TreeParticleEmitter(15, 19));
        gs.getObjectManager().addObject(new TreeParticleEmitter(15, 18));
        gs.getObjectManager().addObject(new TreeParticleEmitter(14, 19));
        gs.getObjectManager().addObject(new TreeParticleEmitter(14, 20));

        gs.getObjectManager().addObject(new TreeParticleEmitter(14, 18));
        gs.getObjectManager().addObject(new TreeParticleEmitter(13, 19));
    }

    @Override
    public void update(EngineAPI api, GameState gs, float dt) {}
}
