package com.game.exe.engine.util;

import java.io.*;
import java.util.Properties;

public class PropertiesFile {

    private Properties p = new Properties();
    private String[] splitContent;

    public PropertiesFile(String path) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(path)));
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            String ls = "=";
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }

            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
            reader.close();

            String content = stringBuilder.toString();

            this.splitContent = content.split("=");
        }catch(Exception e) { Logger.log(Logger.ERROR, "Failed to load properties file"); }
    }

    public String get(String key) {
        try {
            for(int i = 0; i < this.splitContent.length; i+=2) {
                if(this.splitContent[i].equals(key)) {
                    return this.splitContent[i+1];
                }
            }
        }catch(Exception e) {}
        return "";
    }
}
