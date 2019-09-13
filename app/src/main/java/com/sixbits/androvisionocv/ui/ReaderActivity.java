package com.sixbits.androvisionocv.ui;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sixbits.androvisionocv.R;
import com.sixbits.androvisionocv.entity.AttendanceLog;
import com.sixbits.androvisionocv.mapper.LogMapper;
import com.sixbits.androvisionocv.view_model.LogViewModel;

public class ReaderActivity extends AppCompatActivity {
    private static final String TAG = "ML_ReaderActivity";


    private LogViewModel mLogViewModel;

    // region Form Views:
    private EditText etName;
    private EditText etGender;
    private EditText etAge;
    private EditText etMobile;
    private EditText etNationality;
    private EditText etContract;
    private EditText etSection;
    private EditText etDivision;
    private EditText etPosition;
    private EditText etStartDate;
    private EditText etEndDate;

    private FloatingActionButton btnStartCamera;
    private TextView tvRecognizeResult;
    private MaterialButton btnSaveToDatabase;
    // endregion

    public static int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reader);

        mLogViewModel = ViewModelProviders.of(this).get(LogViewModel.class);

        initViews();

        if (getIntent().getExtras() != null) {
            if (getIntent().getExtras().getString("result", null) != null) {
                tvRecognizeResult.setText(getIntent().getExtras().getString("result"));

                // Show the Text
                showText(getIntent().getExtras().getString("result"));
            }
        }

        // region Navigation
        btnStartCamera.setOnClickListener((v) -> goToScanning());

        btnSaveToDatabase.setOnClickListener(view -> goToDashboard());
        // endregion
    }

    @MainThread
    private void initViews(){

        btnStartCamera = findViewById(R.id.btnStartCamera);
        tvRecognizeResult = findViewById(R.id.recognize_result);
        btnSaveToDatabase = findViewById(R.id.btn_send_to_db);

        etName = findViewById(R.id.etRecordName);
        etGender = findViewById(R.id.etRecordGender);
        etAge = findViewById(R.id.etRecordAge);
        etNationality = findViewById(R.id.etRecordNationality);
        etMobile = findViewById(R.id.etRecordMobile);
        etContract = findViewById(R.id.etRecordContractType);
        etSection = findViewById(R.id.etRecordSection);
        etDivision = findViewById(R.id.etRecordDivision);
        etPosition = findViewById(R.id.etRecordPosition);
        etStartDate = findViewById(R.id.etRecordStartDate);
        etEndDate = findViewById(R.id.etRecordEndDate);
    }

    // region DB
    void insertNewLog() {
        LogMapper mapper = new LogMapper();
        AttendanceLog log = mapper.toAttendanceLog(tvRecognizeResult.getText().toString());

        Log.d(TAG, "insertNewLog: " + log.toString());
        mLogViewModel.insert(log);
    }
    // endregion

    // region Navigation
    private void goToScanning() {
        Intent takePicIntent = new Intent(this, ScanningActivity.class);
        if (getIntent().getExtras() != null)
            takePicIntent.putExtra("method", getIntent().getExtras().getString("method", null));
        startActivityForResult(takePicIntent, REQUEST_IMAGE_CAPTURE);
    }

    private void goToDashboard() {
        Intent dashboardIntent = new Intent(this, DashboardActivity.class);
        if (!TextUtils.isEmpty(tvRecognizeResult.getText())) {
            insertNewLog();
            startActivity(dashboardIntent);
        } else {
            Toast.makeText(this, "Empty Text", Toast.LENGTH_SHORT).show();
        }
    }
    //endregion

    private void showText(String txt) {
        LogMapper mapper = new LogMapper();
        AttendanceLog log = mapper.toAttendanceLog(txt);

        if (log.getName() != null) {
            etName.setText(log.getName());
        }
        if (log.getNationality() != null){
            etNationality.setText(log.getNationality());
        }
        if (log.getGender() != null) {
            etGender.setText(log.getGender());
        }
        if (log.getAge() != null) {
            etAge.setText(String.valueOf(log.getAge()));
        }
        if (log.getMobile() != null) {
            etMobile.setText(log.getMobile());
        }
        if (log.getContractType() != null) {
            etContract.setText(log.getContractType());
        }
        if (log.getSection() != null) {
            etSection.setText(log.getSection());
        }
        if (log.getDivision() != null) {
            etDivision.setText(log.getDivision());
        }
        if (log.getPosition() != null) {
            etPosition.setText(log.getPosition());
        }
        if (log.getStartDate() != null) {
            etStartDate.setText(log.getStartDate());
        }
        if (log.getEndDate() != null) {
            etEndDate.setText(log.getEndDate());
        }
    }
}
