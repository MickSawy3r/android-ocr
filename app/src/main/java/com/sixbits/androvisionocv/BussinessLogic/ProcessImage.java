package com.sixbits.androvisionocv.BussinessLogic;

import android.graphics.Bitmap;
import android.util.Log;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Size;

import static com.sixbits.androvisionocv.common.CommonUtils.APP_PATH;
import static com.sixbits.androvisionocv.common.CommonUtils.TAG;
import static com.sixbits.androvisionocv.common.CommonUtils.info;
import static org.opencv.imgcodecs.Imgcodecs.imwrite;
import static org.opencv.imgproc.Imgproc.COLOR_BGR2GRAY;
import static org.opencv.imgproc.Imgproc.GaussianBlur;
import static org.opencv.imgproc.Imgproc.INTER_CUBIC;
import static org.opencv.imgproc.Imgproc.MORPH_RECT;
import static org.opencv.imgproc.Imgproc.THRESH_BINARY;
import static org.opencv.imgproc.Imgproc.cvtColor;
import static org.opencv.imgproc.Imgproc.dilate;
import static org.opencv.imgproc.Imgproc.erode;
import static org.opencv.imgproc.Imgproc.getStructuringElement;
import static org.opencv.imgproc.Imgproc.resize;
import static org.opencv.imgproc.Imgproc.threshold;


public class ProcessImage {

    static {
        if (!OpenCVLoader.initDebug()) {
            Log.w(TAG, "Unable to load OpenCV");
        } else {
            info("OpenCV loaded");
        }

        System.loadLibrary("gnustl_shared");
    }

    private int sourceWidth = 1366; // To scale to
    public static int thresholdMin = 85; // Threshold 80 to 105 is Ok
    private int thresholdMax = 255; // Always 255

    public String recognizeResult = "";

    // Write debug image
    private void writeImage(String name, Mat origin) {
        String appPath = APP_PATH;
        info("Writing " + appPath + name + "...");
        imwrite(appPath + name, origin);
    }

    public boolean parseBitmap(Bitmap bitmap, int top, int bot, int right, int left) {
        try {
            Mat origin = new Mat();
            Utils.bitmapToMat(bitmap, origin);
            info("Mat size: " + origin.width() + ":" + origin.height());
            // Crop image to get the resion inside the rectangle only
            info("Crop T: " + top + " B: " + bot + " R: " + right + " L: " + left);
            if (top != 0 && bot != 0 && right != 0 && left != 0) {
                info("Cropping...");
                origin = origin.submat(new Rect(right, top, left, bot));
                writeImage("crop.jpg", origin);
            }

            boolean result = recognizeImage(origin);
            origin.release();
            return result;
        } catch (Exception e) {
            Log.e(TAG, "Error parse image. Message: " + e.getMessage(), e);
        }
        return false;
    }

    private boolean recognizeImage(Mat origin) {
        info("recognizeImage");
        // Reset value
        recognizeResult = "";

        // Resize origin image
        Size imgSize = origin.size();
        resize(origin, origin, new Size(sourceWidth, imgSize.height * sourceWidth / imgSize.width), 1.0, 1.0,
                INTER_CUBIC);

        writeImage("resize.jpg", origin);

        // Convert the image to GRAY
        Mat originGray = new Mat();
        cvtColor(origin, originGray, COLOR_BGR2GRAY);

        // Process noisy, blur, and threshold to get black-white image
        originGray = processNoisy(originGray);

        writeImage("gray.jpg", originGray); // Black-White image

        recognizeResult = matToString(originGray);
        info("Done recognize");

        originGray.release();
        originGray = null;

        info("Result: " + recognizeResult);
        return true;
    }

    private Mat processNoisy(Mat grayMat) {
        Mat element1 = getStructuringElement(MORPH_RECT, new Size(2, 2), new Point(1, 1));
        Mat element2 = getStructuringElement(MORPH_RECT, new Size(2, 2), new Point(1, 1));
        dilate(grayMat, grayMat, element1);
        erode(grayMat, grayMat, element2);

        GaussianBlur(grayMat, grayMat, new Size(3, 3), 0);
        // The thresold value will be used here
        threshold(grayMat, grayMat, thresholdMin, thresholdMax, THRESH_BINARY);

        return grayMat;
    }

    private String matToString(Mat source) {
        int newWidth = source.width()/2;
        resize(source, source, new Size(newWidth, (source.height() * newWidth) / source.width()));
        writeImage("text.jpg", source);
        CharDetectingOCR ocrReader = new CharDetectingOCR();
        String result = ocrReader.getOCRResult(toBitmap(source));
        //result = result.replace("O", "0"); // Replace O to 0 if have.
        return result;
    }

    public static Bitmap toBitmap(Mat mat) {
        Bitmap bitmap = Bitmap.createBitmap(mat.width(), mat.height(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(mat, bitmap);
        return bitmap;
    }
}
