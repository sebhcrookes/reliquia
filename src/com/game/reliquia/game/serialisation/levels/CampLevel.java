package com.game.reliquia.game.serialisation.levels;

import com.game.reliquia.engine.core.EngineAPI;
import com.game.reliquia.game.states.GameState;
import com.game.reliquia.game.blocks.TreeParticleEmitter;
import com.game.reliquia.game.entities.NPC;
import com.game.reliquia.game.items.ArtifactItem;
import com.game.reliquia.game.entities.player.Player;
import com.game.reliquia.game.items.BambooSword;

public class CampLevel extends Level {

    public CampLevel() {
        super("The Camp");
    }

    @Override
    public void onLoad(EngineAPI api, GameState gs) {
        Player p = (Player)gs.getObjectManager().getObject("reliquia:player");

        if(!p.getArtifacts().isUnlocked("reliquia:dash_book")) {
            gs.getObjectManager().addObject(new ArtifactItem(15, 24, p.getArtifacts().get("reliquia:dash_book")));
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
