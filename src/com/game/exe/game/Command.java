package com.game.exe.game;

public class Command {

    private GameManager gm;

    public Command(GameManager gm) {
        this.gm = gm;
    }

    public void parseCommand(String command) {
        if(command.startsWith("/")) {
            command = command.substring(1);
            if(command.startsWith("weather")) {
                command = command.substring(8);
                gm.setWeather(command);
                return;
            }
            if(command.startsWith("give")) {
                command = command.substring(5);
                if(gm.inventory.canStore(command)) {
                    gm.inventory.pickup(command);
                }
            }
            if(command.startsWith("scale")) {
                command = command.substring(6);
                gm.setScale(Integer.parseInt(command));
            }
            if(command.startsWith("background")) {
                command = command.substring(11);
                if(command.equals("enable")) {
                    gm.trees = true;
                }else if(command.equals("disable")) {
                    gm.trees = false;
                }
            }
        }else{
            return; //It isn't a command, so return
        }
    }

}
