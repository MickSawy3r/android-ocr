package com.sixbits.androvisionocv.common;

import android.os.Environment;
import android.util.Log;

import java.io.File;

public class CommonUtils {
    public static String TAG = "ML";
    public static String APP_PATH = Environment.getExternalStorageDirectory() + "/RecognizeTextOCR/";

    /**
     * Clear temp image in folder APP_PATH
     */
    public static void cleanFolder() {
        info("Create or empty folder");
        String dataPath = APP_PATH;
        File tempPath = new File(dataPath);
        if (!tempPath.exists()) {
            if (!tempPath.mkdir()) {
                // Can not create path
            }
        } else {
            if (tempPath != null &&
                    tempPath.listFiles() != null &&
                    tempPath.listFiles().length > 0)
                for (File child : tempPath.listFiles()) {
                    // Keep only config files
                    if (!child.getName().contains(".txt")) {
                        child.delete();
                    }
                }
        }
    }

    public static void info(Object msg) {
        Log.i(TAG, msg.toString());
    }

}
