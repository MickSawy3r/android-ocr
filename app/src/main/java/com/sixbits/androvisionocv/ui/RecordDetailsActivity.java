package com.sixbits.androvisionocv.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sixbits.androvisionocv.R;
import com.sixbits.androvisionocv.entity.AttendanceLog;
import com.sixbits.androvisionocv.view_model.LogViewModel;

public class RecordDetailsActivity extends AppCompatActivity {
    private static final String TAG = "RecordDetailsActivity";
    LogViewModel mLogViewModel;

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
    private TextView tvRow;

    private FloatingActionButton btnCommit;
    private FloatingActionButton btnDelete;
    // endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_details);

        mLogViewModel = ViewModelProviders.of(this).get(LogViewModel.class);

        initViews();

        Log.d(TAG, "onCreate: " + String.valueOf(getIntent().getExtras().getInt("id")));

        AttendanceLog attendanceLog = null;

        if (getIntent().getExtras() != null )
            if (getIntent().getExtras().getInt("id", -1) != -1)
                attendanceLog = mLogViewModel.getLogById(getIntent().getExtras().getInt("id"));

        if (attendanceLog != null){
            Log.d(TAG, "onCreate: " + attendanceLog.toString());
            showText(attendanceLog);
        }

        btnCommit.setOnClickListener((v) -> {
            Toast.makeText(this, "Saving to DB", Toast.LENGTH_SHORT).show();
            AttendanceLog log = allocateAttendance();
            mLogViewModel.update(log);
            // goToDatabaseViewer();
        });

        btnDelete.setOnClickListener((v) -> {
            AttendanceLog log = allocateAttendance();
            log.setId(getIntent().getExtras().getInt("id"));
            mLogViewModel.delete(log);
            // goToDatabaseViewer();
        });
    }

    @MainThread
    private void initViews(){

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

        btnCommit = findViewById(R.id.btnCommitChangesToDB);
        btnDelete = findViewById(R.id.btnDeleteRecord);
        tvRow = findViewById(R.id.tvRecordRaw);
    }

    private void showText(AttendanceLog log) {

        if (log.getRaw() != null){
            tvRow.setText(log.getDate());
        }

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

    private AttendanceLog allocateAttendance(){
        // region Allocate Attendance Log
        AttendanceLog log = new AttendanceLog();
        log.setId(getIntent().getExtras().getInt("id"));
        log.setName(etName.getText().toString());
        log.setContractType(etContract.getText().toString());
        log.setDivision(etDivision.getText().toString());
        log.setPosition(etPosition.getText().toString());
        log.setMobile(etMobile.getText().toString());
        log.setNationality(etNationality.getText().toString());
        log.setSection(etSection.getText().toString());
        log.setGender(etGender.getText().toString());
        log.setAge(Integer.parseInt(etAge.getText().toString().replaceAll("[^0-9]", "")));
        log.setStartDate(etStartDate.getText().toString());
        log.setEndDate(etEndDate.getText().toString());
        // endregion

        Log.d(TAG, "allocateAttendance: Allocating");
        return log;
    }
}
