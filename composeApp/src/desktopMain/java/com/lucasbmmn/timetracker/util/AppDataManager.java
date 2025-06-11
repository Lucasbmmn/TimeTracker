package com.lucasbmmn.timetracker.util;

import java.io.File;

public class AppDataManager {
    public static String getAppDataPath(){
        String os = System.getProperty("os.name").toLowerCase();
        String appName = "TimeTracker";

        String appDataPath = System.getProperty("user.home") + File.separator + appName;
        if (os.contains("win")) {
            appDataPath = System.getenv("APPDATA") + File.separator + appName;
        } else if (os.contains("mac")) {
            appDataPath = System.getProperty("user.home") + "/Library/Application Support/" + appName;
        }

        return "C:\\Users\\Lucas\\Desktop";
    }
}
