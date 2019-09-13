package com.sixbits.androvisionocv.ui;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sixbits.androvisionocv.R;
import com.wonderkiln.camerakit.CameraView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ScanningActivity extends AppCompatActivity {

    private CameraView cameraView;

    // region Activity Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_android_camera);

        FloatingActionButton btnCaptureCamera = findViewById(R.id.btnCaptureCamera);
        cameraView = findViewById(R.id.cameraView);

        btnCaptureCamera.setOnClickListener((v) -> cameraView.captureImage(cameraKitImage -> {
            // Create Bitmap
            Bitmap imgResult = cameraKitImage.getBitmap();

            // Save to disk
            String path = saveToInternalStorage(imgResult);

            // Send the Result for Processing
            Intent intent = new Intent(this, ProcessImageActivity.class);
            intent.putExtra("ImageSaved", true);
            intent.putExtra("output", path);

            if (getIntent().getExtras().getString("method", null) != null){
                intent.putExtra("method", "google");
            }
            startActivity(intent);
        }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        cameraView.start();
    }

    @Override
    protected void onPause() {
        cameraView.stop();
        super.onPause();
    }
    // endregion

    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        File myPath=new File(directory,System.currentTimeMillis() + ".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(myPath);
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                assert fos != null;
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return myPath.getAbsolutePath();
    }
}
