package com.sixbits.androvisionocv.ui.database_activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sixbits.androvisionocv.R;
import com.sixbits.androvisionocv.entity.AttendanceLog;
import com.sixbits.androvisionocv.entity.AttendanceResponse;
import com.sixbits.androvisionocv.network.NetworkClient;
import com.sixbits.androvisionocv.network.attendanceAPI;
import com.sixbits.androvisionocv.ui.RecordDetailsActivity;
import com.sixbits.androvisionocv.ui.database_activity.recyclerview.LogListAdapter;
import com.sixbits.androvisionocv.view_model.LogViewModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.sixbits.androvisionocv.consts.CONSTS.KEY_DATA_LOAD;

public class DatabaseExploreActivity extends AppCompatActivity implements DatabaseExploreInterface {
    private static final String TAG = "ML_DEA";
    private LogViewModel mLogViewModel;

    FloatingActionButton btnSync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_explore);

        btnSync = findViewById(R.id.btnSync);

        btnSync.setOnClickListener((v) -> {
            syncWithNetwork();
        });

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final LogListAdapter adapter = new LogListAdapter(this, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mLogViewModel = ViewModelProviders.of(this).get(LogViewModel.class);

        if (getIntent().getExtras() != null && getIntent().getExtras().getString(KEY_DATA_LOAD, null) != null){
            insertNewLog();
        }

        mLogViewModel.getAllLogs().observe(this, adapter::setLogs);

    }

    void insertNewLog(){
        AttendanceLog log = new AttendanceLog();
        log.setName(getIntent().getExtras().getString(KEY_DATA_LOAD));
        Log.d(TAG, "insertNewLog: " + log.toString());
        mLogViewModel.insert(log);
    }

    private void syncWithNetwork() {
        // prepaire json
        JsonObject fullListRequest = new JsonObject();
        JsonArray fullListData = new JsonArray();
        mLogViewModel.getAllLogs().observe(this, attendanceLogs -> {
            for (AttendanceLog log : attendanceLogs)
                fullListData.add(log.toJSON());
        });
        fullListRequest.add("data", fullListData);


        // init network class
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        attendanceAPI api = retrofit.create(attendanceAPI.class);


        // do the request
        Call<AttendanceResponse> call = api.postAttendance(fullListRequest);

        call.enqueue(new Callback<AttendanceResponse>() {
            @Override
            public void onResponse(Call<AttendanceResponse> call, Response<AttendanceResponse> response) {
                android.util.Log.d(TAG, "onResponse: " + response.message());
            }

            @Override
            public void onFailure(Call<AttendanceResponse> call, Throwable t) {
                android.util.Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void goToDetails(Integer id){
        Intent intent = new Intent(this, RecordDetailsActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
    }

}
