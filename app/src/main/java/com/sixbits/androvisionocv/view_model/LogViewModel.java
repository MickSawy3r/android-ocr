package com.sixbits.androvisionocv.view_model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sixbits.androvisionocv.entity.AttendanceLog;
import com.sixbits.androvisionocv.repo.LogRepository;

import java.util.List;

public class LogViewModel extends AndroidViewModel {
    private LogRepository mRepository;

    private LiveData<List<AttendanceLog>> mAllLogs;

    public LogViewModel(Application application) {
        super(application);
        mRepository = new LogRepository(application);
        mAllLogs = mRepository.getAllLogs();
    }

    public LiveData<List<AttendanceLog>> getAllLogs() {
        return mAllLogs;
    }

    public void insert(AttendanceLog log) {
        mRepository.insert(log);
    }

    public void update(AttendanceLog log) {
        mRepository.update(log);
    }

    public void delete(AttendanceLog log) { mRepository.delete(log);}

    public AttendanceLog getLogById(Integer id){
        return mRepository.getLogById(id);
    }
}
