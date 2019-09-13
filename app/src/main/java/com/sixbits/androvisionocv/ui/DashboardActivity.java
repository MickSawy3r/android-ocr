package com.sixbits.androvisionocv.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.sixbits.androvisionocv.R;

import static com.sixbits.androvisionocv.ui.ReaderActivity.REQUEST_IMAGE_CAPTURE;

public class DashboardActivity extends AppCompatActivity {

    String lastFileName = "test.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    public void goToScanningActivity(View view) {
        RadioButton rb = findViewById(R.id.radio_google);

        if (view.getId() == R.id.btnGoToScanning) {
            Intent takePicIntent = new Intent(this, ScanningActivity.class);
            takePicIntent.putExtra("output", lastFileName);
            if (rb.isChecked())
                takePicIntent.putExtra("method", "google");
            startActivityForResult(takePicIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void goToAdminArea(View view) {
        if (view.getId() == R.id.btnGoToLogin) {
            Intent intent = new Intent(this, AdminLoginActivity.class);
            startActivity(intent);
        }
    }
}
