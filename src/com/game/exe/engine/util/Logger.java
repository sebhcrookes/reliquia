package com.game.exe.engine.util;

public class Logger {

    private String log = "";

    public final int INFO = 0;
    public final int ERROR = 1;
    public final int FATAL_ERROR = 2;

    private final String INFO_TEXT = "<game.exe>: Info - ";
    private final String ERROR_TEXT = "<game.exe>: Error - ";
    private final String FATAL_ERROR_TEXT = "<game.exe>: Fatal Error - ";

    public void log(int type, String message) {
        switch(type) {
            case INFO:
                log += "\n" + INFO_TEXT + message;
                System.out.println(INFO_TEXT + message);
                return;
            case ERROR:
                log += "\n" + ERROR_TEXT + message;
                System.out.println(ERROR_TEXT + message);
                return;
            case FATAL_ERROR:
                log += "\n" + FATAL_ERROR_TEXT + message;
                System.out.println(FATAL_ERROR_TEXT + message);
                return;
        }
    }
}
