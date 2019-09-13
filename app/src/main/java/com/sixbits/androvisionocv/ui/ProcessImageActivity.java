package com.sixbits.androvisionocv.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.sixbits.androvisionocv.BussinessLogic.CharDetectingOCR;
import com.sixbits.androvisionocv.BussinessLogic.ProcessImage;
import com.sixbits.androvisionocv.BussinessLogic.ProcessImageFirebase;
import com.sixbits.androvisionocv.R;

import static com.sixbits.androvisionocv.ui.ReaderActivity.REQUEST_IMAGE_CAPTURE;

public class ProcessImageActivity extends AppCompatActivity {
    private static final String TAG = "ProcessImageActivity";

    static ProcessImage imageProcessor = new ProcessImage();
    private String lastFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_image);

        // region If Native
        if (getIntent().getExtras().getString("method", null) != null) {

            if (getIntent().getExtras().getBoolean("ImageSaved", false)) {
                lastFileName = getIntent().getExtras().getString("output", null);
                BitmapFactory.Options options = new BitmapFactory.Options();
                Bitmap imageBitmap = BitmapFactory.decodeFile(lastFileName, options);

                ProcessImageFirebase imageFirebase = new ProcessImageFirebase(this);
                imageFirebase.parseBitmap(imageBitmap);
                imageFirebase.startRecognizingText();
            }
        } else {
            new InitTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

            // If Image is Saved Start Processing The Image
            if (getIntent().getExtras() != null) {
                if (getIntent().getExtras().getBoolean("ImageSaved", false)) {
                    lastFileName = getIntent().getExtras().getString("output", null);
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    Bitmap imageBitmap = BitmapFactory.decodeFile(lastFileName, options);
                    if (imageBitmap == null) {
                        // Try again
                        dialogBox("Can not recognize sheet. Please try again", "Retry", "Exist", true);
                        return;
                    }
                    final Bitmap finalImageBitmap = imageBitmap.getWidth() > imageBitmap.getHeight() ? rotateBitmap(imageBitmap, 90) : imageBitmap;
                    displayResult();
                }
            }
            // endregion
        }
    }

    // region DialogBox Maker
    public void dialogBox(String message, String bt1, String bt2, final boolean flagContinue) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage(message);
        alertDialogBuilder.setPositiveButton(bt1, (arg0, arg1) -> {
            if (flagContinue) {
                goToScanning();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
    // endregion

    // region Start OCR Native and Collect Assets
    private class InitTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... data) {
            try {
                CharDetectingOCR.init(getAssets());
                return "";
            } catch (Exception e) {
                Log.e("ML", "Error init data OCR. Message: " + e.getMessage());
            }
            return "";
        }
    }
    // endregion

    public void displayResult() {
        Bitmap bitmap = BitmapFactory.decodeFile(lastFileName);
        if (imageProcessor.parseBitmap(bitmap, 0, 0, 0, 0)) {
            String text = imageProcessor.recognizeResult;
            goToReaderActivity(text);
        } else {
            dialogBox("Can not recognize sheet. Please try again", "Retry", "Exist", true);
        }
    }

    public Bitmap rotateBitmap(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    private void goToScanning() {
        Intent takePicIntent = new Intent(this, ScanningActivity.class);
        takePicIntent.putExtra("output", lastFileName);
        takePicIntent.setFlags(takePicIntent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivityForResult(takePicIntent, REQUEST_IMAGE_CAPTURE);
    }

    public void goToReaderActivity(String result) {
        result = result.replaceAll("[^a-zA-Z0-9\\s]", "");
        Intent intent = new Intent(this, ReaderActivity.class);
        intent.putExtra("result", result);
        intent.putExtra("method", getIntent().getExtras().getString("method", null));
        intent.setFlags(intent.getFlags() | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
}
