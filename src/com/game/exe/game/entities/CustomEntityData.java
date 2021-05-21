package com.game.exe.game.entities;

import java.io.Serializable;

public class CustomEntityData implements Serializable {

    private Object[][] customData;

    public CustomEntityData() {
        int size = 10;
        this.customData = new Object[size][2];
    }

    public Object getValue(String tag) {
        try{
            for(int i = 0; i < customData.length; i++) {
                if(customData[i][0] == tag) {
                    return customData[i][1];
                }
            }
        }catch(Exception ignored) {}
        return null; // Return null if not found
    }

    public void setValue(String tag, Object value) {
        if(getValue(tag) == null) {
            for(int i = 0; i < customData.length; i++) {
                if(customData[i][0] == null) {
                    customData[i][0] = tag;     // Set tag
                    customData[i][1] = value;   // Set value

                    return; // Set tag and value, now return
                }
            }
        }else{
            for(int i = 0; i < customData.length; i++) {
                if(customData[i][0] == tag) {
                    customData[i][1] = value; // Set value

                    return; // Set value, now return
                }
            }
        }
    }

    public void removeValue(String tag) {
        for(int i = 0; i < customData.length; i++) {
            if(customData[i][0] == tag) {
                customData[i][0] = null;
                customData[i][1] = null;

                return; // Found value, cleared it, now return
            }
        }
    }
}
