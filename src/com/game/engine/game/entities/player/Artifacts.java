package com.game.engine.game.entities.player;

import com.game.engine.engine.gfx.Image;
import com.game.engine.engine.util.PropertiesFile;
import com.game.engine.engine.util.terminal.Console;

import java.util.ArrayList;

public class Artifacts {

    private Player player;

    private ArrayList<Artifact> allArtifacts = new ArrayList<>();
    private ArrayList<Artifact> unlockedArtifacts = new ArrayList<>();

    public Artifacts(Player player) {
        this.player = player;
    }

    public void loadAll() {
        String[] artifacts = new String[] {
                "/objects/artifacts/dash.properties"
        };

        for (String s : artifacts) {
            PropertiesFile p = new PropertiesFile(s);
            Artifact artifact = new Artifact();
            artifact.setName(p.get("name"));
            artifact.setNamespace(p.get("namespace"));
            artifact.setIdentifier(p.get("identifier"));
            artifact.setEffector(p.get("effector"));
            artifact.setImage(new Image(p.get("image-path")));
            allArtifacts.add(artifact);
        }
    }

    public Artifact get(String namespaceIdentifier) {
        for(Artifact artifact : allArtifacts) {
            if((artifact.getNamespace() + ":" + artifact.getIdentifier()).equals(namespaceIdentifier)) {
                return artifact;
            }
        }
        return null;
    }

    public void silentlyUnlock(String namespaceIdentifier) {
        if(!isUnlocked(namespaceIdentifier)) {
            for (Artifact artifact : allArtifacts) {
                if ((artifact.getNamespace() + ":" + artifact.getIdentifier()).equals(namespaceIdentifier)) {
                    unlockedArtifacts.add(artifact);
                    return;
                }
            }
        }
    }

    public void unlock(String namespaceIdentifier) {
        if(!isUnlocked(namespaceIdentifier)) {
            for (Artifact artifact : allArtifacts) {
                if ((artifact.getNamespace() + ":" + artifact.getIdentifier()   ).equals(namespaceIdentifier)) {
                    unlockedArtifacts.add(artifact);
                    Console.println("<green>Artifacts - <reset>Artifact obtained: '" + artifact.getName() + "'");
                    return;
                }
            }
        }
    }

    public void lock(String namespaceIdentifier) {
        for(Artifact artifact : unlockedArtifacts) {
            if ((artifact.getNamespace() + ":" + artifact.getIdentifier()).equals(namespaceIdentifier)) {
                unlockedArtifacts.remove(artifact);
                return;
            }
        }
    }

    public boolean isUnlocked(String namespaceIdentifier) {
        for(Artifact artifact : unlockedArtifacts) {
            if((artifact.getNamespace() + ":" + artifact.getIdentifier()).equals(namespaceIdentifier)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEffectorActive(String effector) {
        for(Artifact artifact : unlockedArtifacts) {
            if((artifact.getEffector()).equals(effector)) {
                return true;
            }
        }
        return false;
    }

    public String[] toArray() {
        String[] arr = new String[unlockedArtifacts.size()];
        for(int i = 0; i < arr.length; i++) {
            arr[i] = unlockedArtifacts.get(i).getNamespace() + ":" + unlockedArtifacts.get(i).getIdentifier();
        }
        return arr;
    }
}
