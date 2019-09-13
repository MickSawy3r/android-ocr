package com.sixbits.androvisionocv.repo;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.sixbits.androvisionocv.database.AppDatabase;
import com.sixbits.androvisionocv.database.LogDao;
import com.sixbits.androvisionocv.entity.AttendanceLog;

import java.util.List;

public class LogRepository {
    private static final String TAG = "ML_LogRepository";
    private LogDao mLogDao;
    private LiveData<List<AttendanceLog>> mAllAttendanceLogs;

    public LogRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        mLogDao = db.logDao();
        mAllAttendanceLogs = mLogDao.getAllLogs();
    }

    public LiveData<List<AttendanceLog>> getAllLogs() {
        return mAllAttendanceLogs;
    }

    public void insert(AttendanceLog log) {
        new insertAsyncTask(mLogDao).execute(log);
    }

    public void update(AttendanceLog log) {
        mLogDao.update(log);
    }

    public void delete(AttendanceLog log) {
        mLogDao.delete(log);
        Log.d(TAG, "delete: ");
    }

    public AttendanceLog getLogById(Integer id) {
        return mLogDao.getLogById(id.toString());
    }

    private static class insertAsyncTask extends AsyncTask<AttendanceLog, Void, Void> {

        private LogDao mAsyncTaskDao;

        insertAsyncTask(LogDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final AttendanceLog... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

}
