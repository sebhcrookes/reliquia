package com.game.exe.engine.util;

public class EngineUtilities {

    public EngineUtilities engineUtilities;

    public long getTotalMemory() {
        return Runtime.getRuntime().totalMemory();
    }

    public long getUsedMemory() {
        Runtime t = Runtime.getRuntime();
        return t.totalMemory() - t.freeMemory();
    }
}
