package com.sixbits.androvisionocv.BussinessLogic;

import android.graphics.Bitmap;
import android.util.Log;

import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;
import com.sixbits.androvisionocv.ui.ProcessImageActivity;

public class ProcessImageFirebase {
    private static final String TAG = "ProcessImageFirebase";

    private FirebaseVisionImage image;
    private String resultText;
    private ProcessImageActivity activity;

    public ProcessImageFirebase(ProcessImageActivity activity) {
        this.activity = activity;
    }

    public void parseBitmap(Bitmap bitmap) {
        image = FirebaseVisionImage.fromBitmap(bitmap);
    }

    public void startRecognizingText() {
        FirebaseVisionTextRecognizer detector = FirebaseVision.getInstance()
                .getOnDeviceTextRecognizer();
        detector.processImage(image)
                .addOnSuccessListener(firebaseVisionText -> {
                    resultText = firebaseVisionText.getText();
                    Log.d(TAG, "startRecognizingText: " + resultText);
                    activity.goToReaderActivity(resultText);
                })
                .addOnFailureListener(
                        e -> {
                            Log.d(TAG, "startRecognizingText: " + e.getLocalizedMessage());
                        });
    }
}
