package com.sixbits.androvisionocv.BussinessLogic;

import android.content.res.AssetManager;
import android.graphics.Bitmap;

import com.googlecode.tesseract.android.TessBaseAPI;
import com.sixbits.androvisionocv.common.CommonUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class CharDetectingOCR {

    private static TessBaseAPI mTess;

    public static boolean init(AssetManager assetManager) {
        mTess = new TessBaseAPI();
        String dataPath = CommonUtils.APP_PATH;
        File dir = new File(dataPath + "tessdata/");
        if (!dir.exists()) {
            dir.mkdirs();
            try {
                // Write training text data to external storage
                // Maybe take time for first install app
                InputStream inStream = assetManager.open("CSDL/eng.traineddata");
                FileOutputStream outStream = new FileOutputStream(dataPath + "tessdata/eng.traineddata");
                byte[] buffer = new byte[1024];
                int readCount = 0;
                while ((readCount = inStream.read(buffer)) != -1) {
                    outStream.write(buffer, 0, readCount);
                }
                outStream.flush();
                outStream.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mTess.init(dataPath, "eng"); // English
        return true;
    }

    String getOCRResult(Bitmap bitmap) {
        mTess.setImage(bitmap);
        String result = mTess.getUTF8Text();
        return result;
    }
}
